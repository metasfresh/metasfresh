DROP FUNCTION IF EXISTS report.Get_AdditionalText(p_C_DocType_ID  numeric,
                                                  p_C_BPartner_ID numeric)
;

CREATE OR REPLACE FUNCTION report.Get_AdditionalText(p_C_DocType_ID  numeric,
                                                     p_C_BPartner_ID numeric) RETURNS text
AS
$BODY$
SELECT prt.additionaltext
FROM C_BPartner_Report_Text prt
         INNER JOIN C_BPartner_DocType pdt ON pdt.c_bpartner_report_text_id = prt.c_bpartner_report_text_id
WHERE pdt.c_doctype_id = p_C_DocType_ID
  AND pdt.c_bpartner_id = p_C_BPartner_ID
$BODY$
    LANGUAGE sql STABLE
;
