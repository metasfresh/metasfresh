CREATE OR REPLACE FUNCTION extractDiscountDimensions(textInput character varying)
RETURNS TABLE (
 input character varying,	
 discount text,
 fixedPrice text
) AS $$
BEGIN
	RETURN QUERY SELECT textInput as input, 
		CASE
			WHEN strpos(textInput, '€') > 0 THEN '0'
			ELSE regexp_split_to_table(textInput, '[ %€]')
		END AS discount,
		CASE
			WHEN strpos(textInput, '€') > 0 THEN regexp_split_to_table(textInput, '[ %€]')
			ELSE '0'
		END AS fixedPrice
	 limit 1;
END; $$ 
LANGUAGE 'plpgsql';