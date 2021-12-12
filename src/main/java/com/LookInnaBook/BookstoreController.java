package com.LookInnaBook;

import com.LookInnaBook.author.Author;
import com.LookInnaBook.author.AuthorRepository;
import com.LookInnaBook.author_book.AuthorBook;
import com.LookInnaBook.author_book.AuthorBookRepository;
import com.LookInnaBook.basket.Basket;
import com.LookInnaBook.basket.BasketRepository;
import com.LookInnaBook.billinginfo.BillingInfoRepository;
import com.LookInnaBook.book.Book;
import com.LookInnaBook.book.BookRepository;
import com.LookInnaBook.holds.Holds;
import com.LookInnaBook.holds.HoldsRepository;
import com.LookInnaBook.orders.Orders;
import com.LookInnaBook.orders.OrdersRepository;
import com.LookInnaBook.publisher.PublisherRepository;
import com.LookInnaBook.shippinginfo.ShippingInfoRepository;
import com.LookInnaBook.users.Users;
import com.LookInnaBook.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
public class BookstoreController {

    //below are all of the database interfacers

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private PublisherRepository pubRepo;

    @Autowired
    private OrdersRepository orderRepo;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private HoldsRepository holdRepo;

    @Autowired
    private ShippingInfoRepository shipRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private AuthorBookRepository authorBookRepo;

    @Autowired
    private BasketRepository basketRepo;

    @Autowired
    private BillingInfoRepository billRepo;

    public BookstoreController() {
    }

    //gets the home page
    @GetMapping("/")
    public String home() {

        return "forward:/login";

    }

    //gets the login page
    @GetMapping("/login")
    public String login(@ModelAttribute Users user, Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    //checks to see if the user login is correct
    @PostMapping("/login")
    public String loginhandler(@ModelAttribute Users user, Model model, HttpSession session, @RequestParam String username, @RequestParam String passwd) {


        Users loginUser = userRepo.findByUsernameAndPasswd(username, passwd);


        if (loginUser != null) {
            model.addAttribute(user);

            session.setAttribute("username", loginUser.getUsername());
            session.setAttribute("role", loginUser.getUser_role());
            if (loginUser.getUser_role().equals("ADMIN")) {
                return "redirect:/admindashboard";
            } else {
                return "redirect:/clientdashboard";
            }
        } else {
            return "errorlogin";
        }
    }

    //logs the user out
    @PostMapping("/logout")
    public String logouthandler() {

        return "login";
    }

    //gets the admin dashboard if they are an admin
    @GetMapping("/admindashboard")
    public String admDashboard(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        return "admindashboard";
    }

    //gets the client dashboard if they are a client
    @GetMapping("/clientdashboard")
    public String usrDashboard(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        return "clientdashboard";
    }

    //allows for browsing books
    @GetMapping("/browse")
    public String bookBrowse(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        List<String> genres = bookRepo.findAllGenres();
        model.addAttribute("genres", genres);

        return "bookstore";
    }

    //performs the search for books
    @GetMapping("/searchBooks")
    @ResponseBody
    public List<Book> showStudentBySurname(@ModelAttribute Users user, Model model, HttpSession session,
                                           @RequestParam(required = false, name = "title") String title,
                                           @RequestParam(required = false, name = "author") String author,
                                           @RequestParam(required = false, name = "genre") String genre,
                                           @RequestParam(required = false, name = "isbn") Long isbn) {
        long newisbn = 0;
        if (isbn != null) {
            newisbn = isbn;
        }
        List<Book> search = null;
        //below are all the various search conditions we could have
        if (!title.equals("") && !genre.equals("any") && !(newisbn == 0)) {
            search = bookRepo.findByTitleAndGenreAndIsbn(title, genre, newisbn);

        } else if (!title.equals("") && !genre.equals("any")) {
            search = bookRepo.findByTitleAndGenre(title, genre);

        } else if (!title.equals("") && !(newisbn == 0)) {
            search = bookRepo.findByTitleAndIsbn(title, newisbn);

        } else if (!genre.equals("any") && !(newisbn == 0)) {
            search = bookRepo.findByGenreAndIsbn(genre, newisbn);
        } else if (!title.equals("")) {
            search = bookRepo.findByTitle(title);
        } else if (!genre.equals("any")) {
            search = bookRepo.findByGenre(genre);
        } else if (!(newisbn == 0)) {
            search = bookRepo.findByIsbn(newisbn);
        } else {
            search = (List<Book>) bookRepo.findAll();
        }

        if (!author.equals("")) {
            List<Book> search2 = new ArrayList<Book>();
            String[] authorName = author.split(",");
            for (Book book : search) {

                if (author.equals(bookRepo.getAuthor(book.getIsbn())[0])) {
                    search2.add(book);
                }
            }
            search = search2;
        }
        if (search != null) {
            for (Book book : search) {
                if (book != null) {
                    String[] authorN = bookRepo.getAuthor(book.getIsbn());
                    book.setAuthor(authorN[0]);
                }
            }
        }

        return search;
    }

    //checks out the books in the list
    @PostMapping("/checkout")
    public String checkouthandler(@RequestParam(name = "allBooks") String allBooks, @ModelAttribute Users user, Model model, HttpSession session) {

        String[] allBookVals = allBooks.split(",");
        model.addAttribute("books", allBookVals);
        session.setAttribute("books", allBookVals);
        model.addAttribute("user", user);
        return "redirect:/billandship";
    }

    //gets the billing and shipping form
    @GetMapping("/billandship")
    public String paymentTime(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        String[] books = (String[]) session.getAttribute("books");
        session.setAttribute("books", books);
        model.addAttribute("books", books);
        return "billandship";
    }

    //creates the order
    @PostMapping("/createOrder")
    public String orderCreator(Model model, HttpSession session,
                               @RequestParam(required = false, name = "credit_card") Long credit,
                               @RequestParam(required = false, name = "ccv") int ccv,
                               @RequestParam(required = false, name = "date") String expiration,
                               @RequestParam(required = false, name = "firstname") String firstname,
                               @RequestParam(required = false, name = "lastname") String lastname,
                               @RequestParam(required = false, name = "postal_code1") String postal1,
                               @RequestParam(required = false, name = "country") String country,
                               @RequestParam(required = false, name = "state_province") String state_prov,
                               @RequestParam(required = false, name = "city") String city,
                               @RequestParam(required = false, name = "address") String address,
                               @RequestParam(required = false, name = "postal_code2") String postal2) {

        String username = (String) session.getAttribute("username");

        if (billRepo.findByUsername(username) != null) {
            billRepo.updateRecord(username, credit, ccv, expiration, firstname, lastname, postal1);
        } else {
            billRepo.insertRecord(username, credit, ccv, expiration, firstname, lastname, postal1);
        }
        if (shipRepo.findByUsername(username) != null) {
            shipRepo.updateRecord(username, country, state_prov, city, address, postal2);
        } else {
            shipRepo.insertRecord(username, country, state_prov, city, address, postal2);
        }

        String[] books = (String[]) session.getAttribute("books");
        Basket basket = new Basket(username);

        basketRepo.save(basket);
        int basketId = basket.getBasketId();

        int totalCost = 0;
        for (String book : books) {

            Book currentBook = bookRepo.findByIsbn(Long.parseLong(book)).get(0);
            //updates the total cost
            totalCost += currentBook.getPurchase_cost();
            //simulates sending an email if we have less than 10 books in stock
            if (currentBook.getStock() > 0) {
                bookRepo.updateStock(currentBook.getIsbn(), -1);
            }
            if (currentBook.isOrder_more()) {
                System.out.println("Sending email to publisher to order more novels at email " + pubRepo.findPublisherEmail(currentBook.getPublisher_name()) + " for isbn " + currentBook.getIsbn());
                bookRepo.updateStock(currentBook.getIsbn(), 10);
            }

            Holds hold = holdRepo.findByBasketIdAndIsbn(basketId, Long.parseLong(book));
            if (hold != null) {
                holdRepo.updateQuantity(basketId, Long.parseLong(book));
            } else {
                holdRepo.addNew(basketId, Long.parseLong(book), 1);
            }
        }

        Users user = new Users(username);
        model.addAttribute("user", user);

        Orders order = new Orders(basketId, "Not yet shipped");

        orderRepo.save(order);
        int orderNum = order.getOrderNumber();
        model.addAttribute("order", orderNum);
        session.setAttribute("order", orderNum);
        model.addAttribute("cost", totalCost);
        session.setAttribute("cost", totalCost);

        return "redirect:/ordercomplete";
    }

    //returns the order complete page
    @GetMapping("/ordercomplete")
    public String paymentDone(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        int order = (int) session.getAttribute("order");
        int totalCost = (int) session.getAttribute("cost");
        model.addAttribute("cost", order);
        model.addAttribute("order", totalCost);

        return "ordercomplete";
    }


    //page for checking in on order status
    @GetMapping("/orderCheck")
    public String checkOrder(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        return "ordercheck";
    }

    //returns the order status
    @GetMapping("/trackorder")
    @ResponseBody
    public Orders getTrackingInfo(@ModelAttribute Users user, Model model, HttpSession session,
                                  @RequestParam(required = true, name = "order") Integer order) {

        return orderRepo.findByOrderNumber(order);
    }

    //allows for managing what books are in the store
    @GetMapping("/manage")
    public String manageBooks(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        List<String> genres = bookRepo.findAllGenres();
        model.addAttribute("genres", genres);
        List<String> publishers = pubRepo.findAllPublishers();
        model.addAttribute("publishers", publishers);
        return "managebookstore";
    }

    //removes books
    @PostMapping("/manage")
    @ResponseBody
    public String manage(Model model, HttpSession session, @RequestParam(required = true, name = "isbn") Long isbn) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        bookRepo.deleteById(isbn);
        return "success";
    }

    //adds books
    @PostMapping("/addbook")
    @ResponseBody
    public String addBook(Model model, HttpSession session,
                          @RequestParam(name = "title") String title,
                          @RequestParam(name = "author") String author,
                          @RequestParam(name = "genre") String genre,
                          @RequestParam(name = "isbn") Long isbn,
                          @RequestParam(name = "page_length") Integer pages,
                          @RequestParam(name = "purchase_cost") Double cost,
                          @RequestParam(name = "publisher") String publisher,
                          @RequestParam(name = "publisher_cut") Double cut,
                          @RequestParam(name = "stock") int stock,
                          @RequestParam(name = "stock_cost") Double stock_cost,
                          @RequestParam(required = false, name = "stock_cost") Double sale) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        boolean order_more = false;
        if (stock < 10) {
            order_more = true;
        }
        if(sale == null){
            sale = 0.00;
        }
        Book addBook = new Book(isbn, title, genre, pages, cost, publisher, cut, stock, stock_cost, order_more, sale);
        String[] authorName = author.split(",");
        List<Author> authors = authorRepo.findByFirstnameLastname(authorName[0], authorName[1]);

        //below makes sure that we have a corresponding author and author_book relationship
        if (authors.isEmpty()) {
            Author a = new Author(authorName[0], authorName[1]);
            authorRepo.save(a);
            bookRepo.save(addBook);
            authorBookRepo.insertNew(a.getId(), addBook.getIsbn());
        } else {
            bookRepo.save(addBook);
            authorBookRepo.insertNew(authors.get(0).getId(), addBook.getIsbn());
        }
        return "success";
    }

    @GetMapping("/reports")
    public String getReportPage(Model model, HttpSession session) {
        Users user = new Users((String) session.getAttribute("username"));
        model.addAttribute("user", user);
        List<String> reports = new ArrayList<String>();
        reports.add("Sales vs Expenditures");
        reports.add("Sales per genre");
        reports.add("Sales per author");
        reports.add("Most copies sold");
        model.addAttribute("reports",reports);
        return "reportpage";
    }

    @GetMapping("/report")
    @ResponseBody
    public List<? extends Object> showStudentBySurname(@ModelAttribute Users user, Model model, HttpSession session,
                                           @RequestParam(required = false, name = "report") String report) {
        List<String> reportValues = new ArrayList<String>();

        if(report.equals("Sales vs Expenditures")){
            List<String> saleExp = holdRepo.getSalesExpenditures();
            double totalSales = 0.00;
            double publisherCut = 0.00;
            double expenses = 0.00;
            for (String s : saleExp) {
                String[] values = s.split(",");
                totalSales += Double.parseDouble(values[0]) * Double.parseDouble(values[1]);
                publisherCut += Double.parseDouble(values[0]) * Double.parseDouble(values[1]) *  (Double.parseDouble(values[2])/100);
                expenses += (Double.parseDouble(values[0]) + Double.parseDouble(values[3])) * Double.parseDouble(values[4]);
            }
            double totalGain = totalSales - publisherCut - expenses;
            saleExp.set(0, Math.round(totalSales * 100.0)/100.0 + "," + Math.round(publisherCut * 100.0)/100.0 + "," + Math.round(expenses*100.0)/100.0 + "," + Math.round(totalGain*100)/100.0);
            return saleExp;
        }
        else if(report.equals("Sales per genre")){
            List<String> genres = bookRepo.findAllGenres();
            List<String> saleGenre = holdRepo.getSalesGenre();
            List<Double> genreSales = new ArrayList<Double>(Collections.nCopies(genres.size(), 0.00));
            for(String s : saleGenre){
                String[] values = s.split(",");
                int index = genres.indexOf(values[0]);
                genreSales.add(index, Double.parseDouble(values[1])* Double.parseDouble(values[2]));
            }
            for(int i =0; i< genres.size(); i+=1){
                genres.set(i, genres.get(i) + "," + Math.round(genreSales.get(i) * 100.0)/100.0);
            }
            return genres;
        }
        else if(report.equals("Sales per author")){
            List<String> authors= authorRepo.findAllAuthors();
            List<String> saleGenre = holdRepo.getSalesAuthor();
            List<Double> authorSales = new ArrayList<Double>(Collections.nCopies(authors.size(), 0.00));
            for(String s : saleGenre){
                String[] values = s.split(",");
                int index = authors.indexOf(values[0] + "," + values[1]);
                authorSales.add(index, Double.parseDouble(values[2])* Double.parseDouble(values[3]));
            }
            for(int i =0; i< authors.size(); i+=1){
                authors.set(i, authors.get(i) + "," + Math.round(authorSales.get(i) * 100.0)/100.0);
            }
            return authors;
        }
        else if(report.equals("Most copies sold")){
            List<String> mostCopy = holdRepo.getMostCopies();
            String[] values = mostCopy.get(0).split(",");
            bookRepo.findByIsbn(Long.parseLong(values[0]));
            mostCopy.set(0, bookRepo.findByIsbn(Long.parseLong(values[0])).get(0).getTitle() + ","+values[1]);
            return mostCopy;
            
        }

        return reportValues;
    }


}
