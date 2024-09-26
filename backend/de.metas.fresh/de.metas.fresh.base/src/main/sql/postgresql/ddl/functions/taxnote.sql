DROP FUNCTION if exists report.taxnote(p_c_invoice_id numeric, p_ad_language Character Varying);

CREATE OR REPLACE FUNCTION report.taxnote(p_c_invoice_id numeric, p_ad_language Character Varying (6) default 'de_DE') returns text
AS
$BODY$
DECLARE
    taxnotetext text;
BEGIN
    select String_agg(distinct report.textsnippet(t.ad_boilerplate_id, p_ad_language), ''||E'\n')
    into taxnotetext
    from c_invoiceline il
             join C_Tax t on il.c_tax_id = t.c_tax_id
    where il.c_invoice_id = p_C_Invoice_ID
    group by il.c_invoice_id;

    IF taxnotetext IS NOT NUll THEN
        RETURN taxnotetext;
    ELSE
        RETURN '';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;