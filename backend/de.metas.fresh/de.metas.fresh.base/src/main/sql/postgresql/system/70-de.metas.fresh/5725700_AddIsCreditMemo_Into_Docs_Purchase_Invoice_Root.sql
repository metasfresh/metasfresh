DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Root(IN record_id   numeric,
                                                                                      IN ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Root(IN record_id   numeric,
                                                                                         IN ad_language Character Varying(6))
    RETURNS TABLE
            (
                ad_org_id    numeric,
                docstatus    character(2),
                printname    character varying(60),
                displayhu    text,
                isCreditMemo character(1)
            )
AS
$$
SELECT i.AD_Org_ID,
       i.DocStatus,
       dt.PrintName,
       CASE
           WHEN
               EXISTS(SELECT 0
                      FROM C_InvoiceLine il
                               INNER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
                               INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
                      WHERE pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
                        AND il.C_Invoice_ID = i.C_Invoice_ID
                        AND il.isActive = 'Y')
               THEN 'Y'
               ELSE 'N'
       END AS displayhu,
       CASE
           WHEN dt.docbasetype = 'APC'
               THEN 'Y'
               ELSE 'N'
       END AS isCreditMemo
FROM C_Invoice i
         INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON i.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE i.C_Invoice_ID = $1
  AND i.isActive = 'Y'

$$
    LANGUAGE sql STABLE
;