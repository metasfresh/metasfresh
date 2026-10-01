-- Value: Export Delivery Planning Lines (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@/de/metas/docs/deliveryplanning/deliveryplanning.xls
-- 2023-03-03T12:53:34.607Z
UPDATE AD_Process SET SQLStatement='SELECT div.documentno,
       COALESCE(deliveryL.bpartnername, deliveryBP.name) || E''\n'' || deliveryL.address AS shipToLocation_name,
       p.name                                                                          AS productName,
       p.value                                                                         AS productCode,
       w.name                                                                          AS warehouseName,
       fromC.name                                                                      AS originCountry,
       div.deliverydate                                                                AS deliveryDate,
       m_delivery_planning.batch,
       m_delivery_planning.releaseno,
       m_delivery_planning.plannedloadingdate                                          AS plannedloadingdate,
       m_delivery_planning.actualloadingdate                                           AS actualloadingdate,
       m_delivery_planning.plannedloadedquantity,
       m_delivery_planning.actualloadqty,
       m_delivery_planning.planneddeliverydate                                         AS planneddeliverydate,
       m_delivery_planning.actualdeliverydate                                          AS actualdeliverydate,
       m_delivery_planning.planneddischargequantity,
       m_delivery_planning.actualdischargequantity
FROM m_delivery_planning_delivery_instructions_v div
         INNER JOIN m_delivery_planning ON m_delivery_planning.m_delivery_planning_id = div.m_delivery_planning_id
         INNER JOIN c_bpartner_location deliveryL ON div.c_bpartner_location_delivery_id = deliveryL.c_bpartner_location_id
         INNER JOIN c_bpartner_location loadingL ON div.c_bpartner_location_loading_id = loadingL.c_bpartner_location_id
         INNER JOIN c_bpartner deliveryBP ON deliveryL.c_bpartner_id = deliveryBP.c_bpartner_id
         LEFT JOIN m_product p ON m_delivery_planning.m_product_id = p.m_product_id
         LEFT JOIN m_warehouse W ON m_delivery_planning.m_warehouse_id = W.m_warehouse_id
         LEFT JOIN ad_language l ON l.isbaselanguage = ''Y''
         LEFT JOIN c_country_trl fromC ON m_delivery_planning.c_origincountry_id = fromC.c_country_id AND fromC.ad_language = l.ad_language WHERE @SELECTION_WHERECLAUSE/false@',Updated=TO_TIMESTAMP('2023-03-03 14:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585207
;

