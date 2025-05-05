DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Root(numeric,
                                                                              varchar)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_request_root(record_id  numeric,
                                                                      p_language character varying)
    RETURNS TABLE
            (
                ad_org_id numeric,
                docstatus character,
                printname character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT r.AD_Org_ID,
       r.DocStatus,
       dt.PrintName
FROM M_Requisition r
         INNER JOIN C_DocType dt ON r.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID
    AND dtt.AD_Language = p_language AND dtt.isActive = 'Y'
WHERE r.M_Requisition_ID = record_id
  AND r.isActive = 'Y'
$$
;

ALTER FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Request_Root(numeric, varchar) OWNER TO metasfresh
;
