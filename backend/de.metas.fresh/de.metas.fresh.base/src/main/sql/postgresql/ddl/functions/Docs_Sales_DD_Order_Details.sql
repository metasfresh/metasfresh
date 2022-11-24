DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details (IN p_record_ID   numeric,
                                                                                        IN p_AD_Language Character Varying(6))
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


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details(IN p_record_ID   numeric,
                                                                               IN p_AD_Language Character Varying(6))
    RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_DD_Order_Details
AS
$$

SELECT ddol.line,
       COALESCE(pt.Name, p.name)                                                          AS Name,
       CASE
           WHEN LENGTH(att.Attributes) > 15
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
         SELECT STRING_AGG(DISTINCT name, E'\n'
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
                    AND ddol.DD_Order_ID = p_record_ID
                    AND ddol.isActive = 'Y'
              ) x
         GROUP BY DD_Orderline_ID
     ) pi ON ddol.DD_Orderline_ID = pi.DD_Orderline_ID
         -- Product and its translation
         LEFT OUTER JOIN M_Product p ON ddol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Trl pt ON ddol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

         LEFT OUTER JOIN C_UOM uom ON ddol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Trl uomt ON ddol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'

         LEFT OUTER JOIN (
    SELECT STRING_AGG(at.ai_value, ', '
           ORDER BY LENGTH(at.ai_value), at.ai_value)
           FILTER (WHERE at.at_value NOT IN ('HU_BestBeforeDate', 'Lot-Nummer'))
                                                        AS Attributes,

           at.M_AttributeSetInstance_ID,
           STRING_AGG(REPLACE(at.ai_value, 'MHD: ', ''), ', ')
           FILTER (WHERE at.at_value LIKE 'HU_BestBeforeDate')
                                                        AS best_before_date,
           STRING_AGG(ai_value, ', ')
           FILTER (WHERE at.at_value LIKE 'Lot-Nummer') AS lotno

    FROM Report.fresh_Attributes at
             JOIN DD_Orderline ddol
                  ON at.M_AttributeSetInstance_ID = ddol.M_AttributeSetInstance_ID AND ddol.isActive = 'Y'
    WHERE at.IsPrintedInDocument = 'Y'
      
      AND ddol.DD_Order_ID = p_record_ID
    GROUP BY at.M_AttributeSetInstance_ID
) att ON ddol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID

WHERE ddol.DD_Order_ID = p_record_ID
  AND ddol.isActive = 'Y'
  AND ddol.QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY line

$$
    LANGUAGE SQL
    STABLE
;
