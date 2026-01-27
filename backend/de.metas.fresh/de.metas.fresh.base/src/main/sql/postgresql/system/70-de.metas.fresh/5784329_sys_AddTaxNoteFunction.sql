DROP FUNCTION IF EXISTS report.TaxNote(IN p_c_invoice_id numeric,
                                       IN p_ad_language  character varying)
;

CREATE OR REPLACE FUNCTION report.TaxNote(IN p_c_invoice_id numeric,
                                          IN p_ad_language  character varying DEFAULT 'de_DE') RETURNS text
AS
$BODY$
BEGIN
    RETURN report.TaxNote(NULL, p_c_invoice_id, p_ad_language);
END;
$BODY$;
