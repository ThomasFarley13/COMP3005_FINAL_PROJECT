--this triggers the buy_books() function if we have less than 11 books on update
CREATE TRIGGER book_order
	AFTER UPDATE OF stock ON book
	FOR EACH ROW
	WHEN (new.stock < 11)
	EXECUTE PROCEDURE buy_books();
