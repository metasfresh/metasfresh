DROP FUNCTION if exists report.textsnippet(IN p_ad_boilerplate_id numeric);
CREATE OR REPLACE FUNCTION report.textsnippet(IN p_ad_boilerplate_id numeric)
    RETURNS text AS
$BODY$
DECLARE

    p_textsnippet_name text;

BEGIN
    select textsnippet into p_textsnippet_name from ad_boilerplate where ad_boilerplate_id = p_ad_boilerplate_id;

    RETURN p_textsnippet_name;
END;
$BODY$ LANGUAGE plpgsql;


DROP FUNCTION if exists report.taxnote(p_c_invoice_id numeric);

CREATE OR REPLACE FUNCTION report.taxnote(IN p_c_invoice_id numeric) returns text
AS
$BODY$
DECLARE
    taxnotetext text;
BEGIN
    select String_agg(distinct report.textsnippet(t.ad_boilerplate_id), ''||E'\n')
    into taxnotetext
    from c_invoiceline il
             join C_Tax t on il.c_tax_id = t.c_tax_id
    where il.c_invoice_id = p_C_Invoice_ID
    group by t.ad_boilerplate_id;

    IF taxnotetext IS NOT NUll THEN
        RETURN taxnotetext;
    ELSE
        RETURN '';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;
