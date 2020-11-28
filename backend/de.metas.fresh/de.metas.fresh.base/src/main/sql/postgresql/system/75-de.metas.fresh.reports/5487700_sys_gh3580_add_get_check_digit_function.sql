CREATE OR REPLACE FUNCTION report.get_check_digit(IN gln text)
  RETURNS integer AS
$BODY$	
  DECLARE

	rez integer;
	s integer := 0;
	
	
  BEGIN
	FOR i IN REVERSE char_length($1)..1 BY 2
	LOOP
		s := s + (3 * CAST (substring($1 from i for 1) AS INTEGER));
	END LOOP;

	FOR i IN REVERSE (char_length($1)-1)..1 BY 2
	LOOP
		s := s + CAST (substring($1 from i for 1) AS INTEGER);
	END LOOP;

	rez := s % 10;
		
    RETURN (10 - rez) % 10;
	
END;
$BODY$
LANGUAGE plpgsql;


COMMENT ON FUNCTION report.get_check_digit(text)
IS 'Example how is calculated 
	17264920102198121
	31313131313131313
	checkdigit=8
	1*3+7*1+2*3+6*1+4*3+9*1+2*3+0*1+1*3+0*1+2*3+1*1+9*3+8*1+1*3+2*1+1*3=102
	110-102 = 8';
