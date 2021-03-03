CREATE OR REPLACE FUNCTION getDaysFromGuaranteeMonths(p_months text)
    RETURNS INTEGER AS
$BODY$
BEGIN
    if (p_months = '12') then RETURN 365;
    elseif  (p_months = '24') then RETURN 730;
    elseif (p_months = '36') then RETURN 1095;
    END if;
    RETURN NULL;
END;
$BODY$
    LANGUAGE plpgsql;
COMMENT ON FUNCTION getDaysFromGuaranteeMonths(text) IS
    'Return the corresponding days for months 12, 24, 36. For other values return NULL';