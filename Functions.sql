--this function will update the order_more boolean to true if we have less than 10 books in stock
CREATE OR REPLACE FUNCTION buy_books() returns trigger as
	$$
	begin
		UPDATE book 
		SET order_more = TRUE
		WHERE new.stock < 10;
	RETURN new;
	end;
	$$
language 'plpgsql';	