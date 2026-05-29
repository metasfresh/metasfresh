DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight (IN Record_ID     numeric,
                                                                                        IN p_AD_Language Character Varying)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(IN Record_ID     numeric,
                                                                               IN p_AD_Language Character Varying)
    RETURNS TABLE
            (
                catchweight numeric,
                weight_uom  character varying
            )
AS
$$
SELECT SUM(
               COALESCE((COALESCE(qtydeliveredcatch,
                                  uomConvert(iol.M_Product_ID,
                                             iol.C_UOM_ID,
                                             (SELECT c_uom_Id FROM C_uom WHERE isactive = 'Y' AND x12de355 = 'KGM'),
                                             qtyentered))),
                        iol.qtyentered * p.weight)) AS catchweight,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)      AS weight_uom
FROM M_InOutline iol
         LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = COALESCE(iol.catch_uom_id, (SELECT c_uom_Id FROM C_uom WHERE isactive = 'Y' AND x12de355 = 'KGM'))
         LEFT OUTER JOIN C_UOM_Trl uomt ON uomt.c_UOM_ID = uom.C_UOM_ID AND uomt.AD_Language = p_AD_Language
         INNER JOIN M_Product p ON p.M_Product_ID = iol.M_Product_ID
WHERE iol.m_inout_id = Record_ID
GROUP BY uomt.UOMSymbol, uom.UOMSymbol, iol.m_inout_id

$$
    LANGUAGE sql
    STABLE
;
