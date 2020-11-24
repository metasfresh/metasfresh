DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Root (IN Record_ID numeric, IN AD_Language Character Varying(6))
;

DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Root
;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Root
(
    AD_User_ID             numeric(10, 0),
    AD_Org_ID              numeric(10, 0),
    DD_Order_ID            numeric(10, 0),
    DocStatus              Character(2),
    C_BPartner_ID          numeric(10, 0),
    C_BPartner_Location_ID numeric(10, 0),
    PrintName              Character Varying(60),
    AD_Language            Text,
    email                  Character Varying(50),
    displayhu              text,
    issourcesupplycert     character(1)
)
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Root(IN Record_ID   numeric,
                                                                            IN AD_Language Character Varying(6))
    RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Root
AS
$$
SELECT ddo.ad_user_id,
       ddo.ad_org_id,
       ddo.DD_Order_ID,
       ddo.docstatus,
       ddo.c_bpartner_id,
       ddo.c_bpartner_location_id,
       CASE
           WHEN ddo.DocStatus = 'DR'
               THEN dt.printname
               ELSE COALESCE(dtt.printname, dt.printname)
       END                                                              AS printname,
       CASE WHEN ddo.DocStatus IN ('DR', 'IP') THEN 'de_CH' ELSE $2 END AS AD_Language,
       mb.email,
       CASE
           WHEN
               EXISTS(
                       SELECT 0
                       FROM DD_Orderline ddol
                                INNER JOIN M_Product p ON ddol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
                                INNER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
                           AND ddo.DD_Order_ID = ddol.DD_Order_ID
                           AND ddol.isActive = 'Y'
                   )
               THEN 'Y'
               ELSE 'N'
       END                                                              AS displayhu,
       bp.issourcesupplycert
FROM DD_Order ddo
         JOIN C_BPartner bp ON ddo.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         INNER JOIN C_DocType dt ON ddo.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON ddo.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
    --get the email from AD_MailConfig for org, docbasetype and docsubtype, with fallback to org, docbasetype
         LEFT OUTER JOIN (
    SELECT email, ddo.DD_Order_ID
    FROM DD_Order ddo
             INNER JOIN C_DocType dt ON ddo.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
             LEFT OUTER JOIN AD_MailConfig mc1 ON ddo.AD_Org_ID = mc1.AD_Org_ID AND mc1.DocBaseType = dt.DocBaseType AND mc1.DocSubType = dt.DocSubType AND mc1.isActive = 'Y'
             LEFT OUTER JOIN AD_MailConfig mc2 ON ddo.AD_Org_ID = mc2.AD_Org_ID AND mc2.DocBaseType = dt.DocBaseType AND mc2.DocSubType IS NULL AND mc2.isActive = 'Y'
             LEFT OUTER JOIN AD_MailConfig mc3 ON ddo.AD_Org_ID = mc3.AD_Org_ID AND mc3.DocBaseType IS NULL AND mc3.DocSubType IS NULL AND mc3.isActive = 'Y'
             LEFT OUTER JOIN AD_Mailbox mb ON COALESCE(COALESCE(mc1.AD_Mailbox_ID, mc2.AD_Mailbox_ID), mc3.AD_Mailbox_ID) = mb.AD_Mailbox_ID AND mb.isActive = 'Y'

    WHERE ddo.DD_Order_ID = $1
      AND ddo.isActive = 'Y'
    LIMIT 1
) mb ON mb.DD_Order_ID = ddo.DD_Order_ID

WHERE ddo.DD_Order_ID = $1
  AND ddo.isActive = 'Y'

$$
    LANGUAGE SQL STABLE
;
