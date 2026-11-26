DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_productdetails(numeric, character varying);

CREATE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_productdetails(p_m_shippertransportation_id numeric, p_ad_language character varying)
 RETURNS TABLE(warehousename character varying, plannedloadedquantity numeric, qtyordered numeric, productvalue character varying, productname character varying, uom character varying, orderno character varying, referenceno character varying)
 LANGUAGE sql
 STABLE
AS $function$
SELECT STRING_AGG(DISTINCT wh.name, ', ' ORDER BY wh.name)         AS warehouseName,
       SUM(dp.plannedloadedquantity)                               AS plannedloadedquantity,
       SUM(dp.qtyordered)                                          AS qtyordered,
       p.value                                                     AS productValue,
       p.name                                                      AS productName,
       COALESCE(MIN(uomt.uomsymbol), MIN(uom.uomsymbol))           AS uom,
       STRING_AGG(DISTINCT o.documentno, ', ' ORDER BY o.documentno)   AS orderno,
       STRING_AGG(DISTINCT o.poreference, ', ' ORDER BY o.poreference) AS referenceno
FROM M_ShipperTransportation st
         JOIN m_delivery_planning_alloc dpa ON dpa.m_shippertransportation_id = st.m_shippertransportation_id AND dpa.isactive = 'Y'
         JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
         LEFT JOIN m_warehouse wh ON dp.m_warehouse_id = wh.m_warehouse_id
         JOIN M_product p ON dp.m_product_id = p.m_product_id
         JOIN C_UOM uom ON dp.c_uom_id = uom.c_uom_id
         LEFT JOIN C_UOM_trl uomt ON dp.c_uom_id = uomt.c_uom_id and uomt.ad_language=p_ad_language
         LEFT JOIN C_Order o ON o.c_order_id = dp.c_order_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
GROUP BY p.m_product_id, dp.c_uom_id
$function$
;
