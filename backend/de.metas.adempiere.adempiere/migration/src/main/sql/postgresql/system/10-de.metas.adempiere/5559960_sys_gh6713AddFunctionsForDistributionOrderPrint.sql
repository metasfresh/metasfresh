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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details (IN Record_ID numeric, IN AD_Language Character Varying(6))
;

DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details
;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details
(
    Line                 Numeric(10, 0),
    Name                 Character Varying,
    Attributes           Text,
    HUQty                Numeric,
    HUName               Text,
    qtyEntered           Numeric,
    UOMSymbol            Character Varying(10),
    LineNetAmt           Numeric,
    Description          Character Varying,
    p_value              character varying(30),
    p_description        character varying(255),
    dd_order_description character varying(255),
    locator_from         character varying(255),
    locator_to           character varying(255)
)
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details(IN Record_ID   numeric,
                                                                               IN AD_Language Character Varying(6))
    RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details
AS
$$

SELECT ddol.line,
       COALESCE(pt.Name, p.name)                                                          AS Name,
       CASE
           WHEN Length(att.Attributes) > 15
               THEN att.Attributes || E'\n'
               ELSE att.Attributes
       END                                                                                AS Attributes,
       ddol.QtyEnteredTU                                                                  AS HUQty,
       pi.name                                                                            AS HUName,
       ddol.qtyentered                                                                    AS QtyEntered,
       uomt.uomsymbol                                                                     AS UOMSymbol,

       ddol.linenetamt                                                                    AS linenetamt,

       ddol.Description,
       -- in case there is no C_BPartner_Product, fallback to the default ones
       p.value                                                                            AS p_value,
       p.description                                                                      AS p_description,
       ddo.description                                                                    AS dd_order_description,
       (SELECT loc.value FROM m_locator loc WHERE ddol.m_locator_id = loc.m_locator_id)   AS locator_from,
       (SELECT loc.value FROM m_locator loc WHERE ddol.m_locatorto_id = loc.m_locator_id) AS locator_to

FROM DD_Orderline ddol
         INNER JOIN DD_Order ddo ON ddol.DD_Order_ID = ddo.DD_Order_ID AND ddo.isActive = 'Y'

    -- Get Packing instruction
         LEFT OUTER JOIN
     (
         SELECT String_Agg(DISTINCT name, E'\n'
                           ORDER BY name) AS Name,
                DD_Orderline_ID
         FROM (
                  SELECT DISTINCT
                      -- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
                      COALESCE(pifb.name, pi.name) AS name,
                      ddol.DD_Orderline_ID
                  FROM DD_Orderline ddol
                           -- Get PI directly from InOutLine (1 to 1)
                           LEFT OUTER JOIN M_HU_PI_Item_Product pi
                                           ON ddol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
                           LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'
                      -- Get PI from HU assignments (1 to n)
                      -- if the HU was set manually don't check the assignments
                           LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                      AND asgn.Record_ID = ddol.DD_Orderline_ID AND asgn.isActive = 'Y'
                           LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
                           LEFT OUTER JOIN M_HU_PI_Item_Product pifb
                                           ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
                           LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
                      --
                           LEFT OUTER JOIN M_HU_PI_Version piv
                                           ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND piv.isActive = 'Y'
                  WHERE piv.M_HU_PI_Version_ID != 101
                    AND ddol.DD_Order_ID = $1
                    AND ddol.isActive = 'Y'
              ) x
         GROUP BY DD_Orderline_ID
     ) pi ON ddol.DD_Orderline_ID = pi.DD_Orderline_ID
         -- Product and its translation
         LEFT OUTER JOIN M_Product p ON ddol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Trl pt ON ddol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

         LEFT OUTER JOIN C_UOM uom ON ddol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Trl uomt ON ddol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'

         LEFT OUTER JOIN (
    SELECT String_agg(at.ai_value, ', '
           ORDER BY Length(at.ai_value), at.ai_value)
           FILTER (WHERE at.at_value NOT IN ('HU_BestBeforeDate', 'Lot-Nummer'))
                                                        AS Attributes,

           at.M_AttributeSetInstance_ID,
           String_agg(replace(at.ai_value, 'MHD: ', ''), ', ')
           FILTER (WHERE at.at_value LIKE 'HU_BestBeforeDate')
                                                        AS best_before_date,
           String_agg(ai_value, ', ')
           FILTER (WHERE at.at_value LIKE 'Lot-Nummer') AS lotno

    FROM Report.fresh_Attributes at
             JOIN DD_Orderline ddol
                  ON at.M_AttributeSetInstance_ID = ddol.M_AttributeSetInstance_ID AND ddol.isActive = 'Y'
    WHERE at.at_value IN ('1000002', '1000001', '1000030', '1000015', 'HU_BestBeforeDate', 'Lot-Nummer')
      -- Label, Herkunft, Aktionen, Marke (ADR)
      AND ddol.DD_Order_ID = $1
    GROUP BY at.M_AttributeSetInstance_ID
) att ON ddol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID

WHERE ddol.DD_Order_ID = $1
  AND ddol.isActive = 'Y'
  AND ddol.QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY line

$$
    LANGUAGE SQL
    STABLE
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description (IN Record_ID numeric, IN AD_Language Character Varying(6))
;

DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description
;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description
(
    Description Character Varying(255),
    DocumentNo  Character Varying(30),
    dateordered  Timestamp WITHOUT TIME ZONE,
    Reference   Character Varying(40),
    BP_Value    Character Varying(40),
    Cont_Name   Character Varying(40),
    Cont_Phone  Character Varying(40),
    Cont_Fax    Character Varying(40),
    Cont_Email  Character Varying(60),
    SR_Name     Text,
    SR_Phone    Character Varying(40),
    SR_Fax      Character Varying(40),
    SR_Email    Character Varying(60),
    PrintName   Character Varying(60),
    order_docno Character Varying(30)
)
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description(IN Record_ID   numeric,
                                                                                   IN AD_Language Character Varying(6))
    RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Description
AS
$$
SELECT ddo.description                       AS description,
       ddo.documentno                        AS documentno,
       ddo.dateordered,
       ddo.poreference                       AS reference,
       bp.value                              AS bp_value,
       Coalesce(cogr.name, '') ||
       Coalesce(' ' || cont.title, '') ||
       Coalesce(' ' || cont.firstName, '') ||
       Coalesce(' ' || cont.lastName, '')    AS cont_name,
       cont.phone                            AS cont_phone,
       cont.fax                              AS cont_fax,
       cont.email                            AS cont_email,
       Coalesce(srgr.name, '') ||
       Coalesce(' ' || srep.title, '') ||
       Coalesce(' ' || srep.firstName, '') ||
       Coalesce(' ' || srep.lastName, '')    AS sr_name,
       srep.phone                            AS sr_phone,
       srep.fax                              AS sr_fax,
       srep.email                            AS sr_email,
       COALESCE(dtt.printname, dt.printname) AS printname,
       o.docno                               AS order_docno
FROM dd_order ddo
         INNER JOIN C_DocType dt ON ddo.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
         INNER JOIN c_bpartner bp ON ddo.c_bpartner_id = bp.c_bpartner_id AND bp.isActive = 'Y'
         LEFT OUTER JOIN AD_User srep ON ddo.SalesRep_ID = srep.AD_User_ID AND srep.AD_User_ID <> 100 AND srep.isActive = 'Y'
         LEFT OUTER JOIN AD_User cont ON ddo.AD_User_ID = cont.AD_User_ID AND cont.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting cogr ON cont.C_Greeting_ID = cogr.C_Greeting_ID AND cogr.isActive = 'Y'
         LEFT OUTER JOIN C_Greeting srgr ON srep.C_Greeting_ID = srgr.C_Greeting_ID AND srgr.isActive = 'Y'

         LEFT JOIN LATERAL
    (
    SELECT First_Agg(o.DocumentNo ORDER BY o.DocumentNo) ||
           CASE WHEN Count(DISTINCT o.documentNo) > 1 THEN ' ff.' ELSE '' END AS DocNo
    FROM dd_orderline ddol
             JOIN C_OrderLine ol ON ddol.C_OrderLineSO_ID = ol.C_OrderLine_ID
             JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID

    WHERE ddol.DD_Order_ID = $1
    ) o ON TRUE
WHERE ddo.dd_order_id = $1
  AND ddo.isActive = 'Y'
$$
    LANGUAGE SQL STABLE
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_DD_Order_Details_Footer( IN DD_Order_ID numeric, IN AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DD_Order_Details_Footer(IN DD_Order_ID numeric,
                                                                                           IN AD_Language Character Varying(6))

    RETURNS TABLE
            (
                textleft    text,
                textcenter  text,
                language    character varying,
                dd_order_id numeric(10, 0),
                tag         text,
                pozition    integer

            )

AS
$$

SELECT *
FROM (
         --Docnote DE
         SELECT NULL                                                                                AS textleft,
                CASE
                    WHEN ddo.description IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote                                                              AS textcenter,
                (SELECT AD_Language FROM AD_Language WHERE IsBaseLanguage = 'Y' AND isActive = 'Y') AS language,
                ddo.dd_order_id                                                                     AS dd_order_id,
                'docnote'                                                                           AS tag,
                3                                                                                   AS pozition
         FROM dd_order ddo
                  LEFT JOIN c_doctype dt ON ddo.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
         WHERE ddo.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Docnote TRL
         SELECT NULL                   AS textleft,
                CASE
                    WHEN ddo.description IS NOT NULL
                        THEN E'\n\n\n'
                        ELSE ''
                END || dt.documentnote AS textcenter,
                dt.ad_language         AS language,
                ddo.dd_order_id        AS dd_order_id,
                'docnote'              AS tag,
                3                      AS pozition
         FROM dd_order ddo
                  LEFT JOIN c_doctype_trl dt ON ddo.c_doctype_id = dt.c_doctype_id AND dt.isActive = 'Y'
         WHERE ddo.isActive = 'Y'
         UNION
         ---------------------------------------------------------------------------------------------
         --Descriptionbottom
         SELECT ddo.description AS textleft,
                NULL            AS textcenter,
                NULL            AS language,
                ddo.dd_order_id AS dd_order_id,
                'descr'         AS tag,
                2               AS pozition
         FROM dd_order ddo
         WHERE ddo.isActive = 'Y'
     ) footer
WHERE footer.dd_order_id = $1
  AND (footer.language = $2 OR footer.language IS NULL)
  AND (textleft <> '' OR textcenter <> '')
  AND (textleft IS NULL OR textcenter IS NULL)
ORDER BY pozition

$$
    LANGUAGE SQL STABLE
;

