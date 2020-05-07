CREATE OR REPLACE FUNCTION sscc18_extract_serialNumber(p_sscc18 text)
RETURNS varchar
AS
$BODY$
BEGIN
	return substr(p_sscc18, 9, 9);
END;
$BODY$
LANGUAGE plpgsql IMMUTABLE 
COST 100;
--
