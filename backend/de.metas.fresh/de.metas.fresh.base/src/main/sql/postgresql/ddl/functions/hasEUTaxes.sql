CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.hasFreeEUTaxes(IN p_C_Invoice_ID numeric)
    RETURNS char(1) AS
$BODY$
DECLARE

    hasEUTax char(1);

BEGIN

    hasEUTax := (SELECT CASE WHEN (COUNT(1) > 0) THEN 'Y' ELSE 'N' END
                 FROM c_invoiceline il
                          JOIN C_Tax t ON il.c_tax_id = t.c_tax_id
                 WHERE t.TypeOfDestCountry = 'WITHIN_COUNTRY_AREA'
                   AND t.isTaxexempt = 'Y'
                   AND il.c_invoice_id = p_C_Invoice_ID);

    IF hasEUTax IS NOT NUll THEN
        RETURN hasEUTax;
    ELSE
        RETURN 'N';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
