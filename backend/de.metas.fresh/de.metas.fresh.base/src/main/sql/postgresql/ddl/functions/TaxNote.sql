DROP FUNCTION IF EXISTS report.TaxNote(IN p_c_invoice_id numeric,
                                       IN p_ad_language  character varying)
;

CREATE OR REPLACE FUNCTION report.TaxNote(IN p_c_invoice_id numeric,
                                          IN p_ad_language  character varying DEFAULT 'de_DE') RETURNS text
AS
$BODY$
DECLARE
    taxnotetext text;
BEGIN
    SELECT STRING_AGG(DISTINCT report.textsnippet(t.ad_boilerplate_id, p_ad_language), '' || E'\n')
    INTO taxnotetext
    FROM c_invoiceline il
             JOIN C_Tax t ON il.c_tax_id = t.c_tax_id
    WHERE il.c_invoice_id = p_C_Invoice_ID
    GROUP BY il.c_invoice_id;

    IF taxnotetext IS NOT NULL THEN
        RETURN taxnotetext;
    ELSE
        RETURN '';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql
;
