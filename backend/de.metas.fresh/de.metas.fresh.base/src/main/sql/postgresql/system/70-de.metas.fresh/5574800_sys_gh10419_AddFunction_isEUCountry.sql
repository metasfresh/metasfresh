CREATE OR REPLACE FUNCTION isEUCountry(IN p_C_Country_ID numeric)
    RETURNS char(1) AS
$BODY$
DECLARE

    isEUCountry char(1);

BEGIN

    isEUCountry := (select case when (count(1) > 0) then 'Y' else 'N' end
                    from C_CountryArea_Assign ca
                    where ca.C_CountryArea_ID = 540000 -- European Union
                      and ca.C_Country_ID = p_C_Country_ID);

    IF isEUCountry IS NOT NUll THEN
        RETURN isEUCountry;
    ELSE
        RETURN 'N';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;