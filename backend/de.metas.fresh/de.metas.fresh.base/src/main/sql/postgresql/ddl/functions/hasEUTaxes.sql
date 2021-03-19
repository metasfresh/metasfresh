CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.hasFreeEUTaxes(IN p_C_Invoice_ID numeric)
    RETURNS char(1) AS
$BODY$
DECLARE

    hasEUTax char(1);

BEGIN

    hasEUTax := (select case when (count(1) > 0) then 'Y' else 'N' end
                 from c_invoiceline il
                          join C_Tax t on il.c_tax_id = t.c_tax_id
                 where t.istoeulocation = 'Y' and t.isTaxexempt='Y'
                   and il.c_invoice_id = p_C_Invoice_ID);

    IF hasEUTax IS NOT NUll THEN
        RETURN hasEUTax;
    ELSE
        RETURN 'N';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
