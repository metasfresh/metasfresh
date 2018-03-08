CREATE OR REPLACE FUNCTION report.get_barcode(IN M_HU_ID numeric)
  RETURNS text AS
$BODY$	
  DECLARE

	ai text := '00';
	extensionDigit text := '0';
	gs1 text;
	serialReference text;
	gln text;
	checkDigit text;

  BEGIN
	
	gs1 := (SELECT value FROM AD_SysConfig WHERE name LIKE 'de.metas.handlingunit.GS1ManufacturerCode');
	gs1 := rpad(gs1::text, 8, '0'::text);
	serialReference := lpad($1::text, 8, '0'::text);
	
	gln := ai || extensionDigit || gs1 || serialReference;
	
	checkDigit := report.get_check_digit(gln);
	
	RETURN (gln || checkDigit);
	

END;
$BODY$
LANGUAGE plpgsql;

COMMENT ON FUNCTION report.get_check_digit(text)
IS 'Example how is calculated 
	Application identifier (fix) 00
	Extension Digit (fix) 0
	GS1 (config) 1234567 can have 7 or 8 digits. rpad 0 to have 8: 12345670
	serialReference (param) lpad 0 to have 8 digits 1000111: 01000111
	checkdigit (calculated) = 2 
	
	Rezult: 00011111110010001112';