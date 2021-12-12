--this triggers the buy_books() function if we have less than 10 books on update
CREATE TRIGGER book_order
	AFTER UPDATE OF stock ON book
	FOR EACH ROW
	WHEN (new.stock < 10)
	EXECUTE PROCEDURE buy_books();