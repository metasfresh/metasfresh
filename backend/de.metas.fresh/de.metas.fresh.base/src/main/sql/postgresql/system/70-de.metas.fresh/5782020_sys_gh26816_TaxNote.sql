DROP FUNCTION IF EXISTS report.TaxNote(IN p_invoice_id numeric,
                                       IN p_ad_language  character varying)
;

DROP FUNCTION IF EXISTS report.TaxNote(IN p_order_id numeric,
                                       IN p_invoice_id numeric,
                                       IN p_ad_language  character varying)
;

CREATE OR REPLACE FUNCTION report.TaxNote(IN p_order_id numeric,
                                          IN p_invoice_id numeric,
                                          IN p_ad_language  character varying DEFAULT 'de_DE') RETURNS text
AS
$BODY$
DECLARE
    taxnotetext text;
BEGIN
    IF p_order_id IS NOT NULL THEN
        SELECT STRING_AGG(DISTINCT report.textsnippet(t.ad_boilerplate_id, p_ad_language), '' || E'\n')
        INTO taxnotetext
        FROM c_orderline ol
                 JOIN C_Tax t ON ol.c_tax_id = t.c_tax_id
        WHERE ol.c_order_id = p_order_id
        GROUP BY ol.c_order_id;
    ELSIF p_invoice_id IS NOT NULL THEN
        SELECT STRING_AGG(DISTINCT report.textsnippet(t.ad_boilerplate_id, p_ad_language), '' || E'\n')
        INTO taxnotetext
        FROM c_invoiceline il
                 JOIN C_Tax t ON il.c_tax_id = t.c_tax_id
        WHERE il.c_invoice_id = p_invoice_id
        GROUP BY il.c_invoice_id;
    END IF;

    IF taxnotetext IS NOT NULL THEN
        RETURN taxnotetext;
    ELSE
        RETURN '';
    END IF;
END;
$BODY$
    LANGUAGE plpgsql
;

-- Refactor legacy function

CREATE OR REPLACE FUNCTION report.TaxNote(
    IN p_c_invoice_id numeric,
    IN p_ad_language  character varying DEFAULT 'de_DE'
)
    RETURNS text
    LANGUAGE plpgsql
AS
$BODY$
BEGIN
    RETURN report.TaxNote(NULL, p_c_invoice_id, p_ad_language);
END;
$BODY$;

