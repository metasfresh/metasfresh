CREATE VIEW Delivery_Planning_Export_View_v AS
SELECT m_delivery_planning.m_delivery_planning_id                                                                                   AS Delivery_Planning_Export_View_v_ID,
       m_delivery_planning.m_delivery_planning_id,
       div.documentno,
       (SELECT bpl.Name FROM C_BPartner_Location bpl WHERE bpl.C_BPartner_Location_ID = M_Delivery_Planning.C_BPartner_Location_ID) AS shipToLocation_Name,
       p.name                                                                                                                       AS productName,
       p.value                                                                                                                      AS productCode,
       w.name                                                                                                                       AS warehouseName,
       fromC.name                                                                                                                   AS originCountry,
       div.deliverydate                                                                                                             AS deliveryDate,
       m_delivery_planning.batch,
       m_delivery_planning.releaseno,
       m_delivery_planning.plannedloadingdate                                                                                       AS plannedloadingdate,
       m_delivery_planning.actualloadingdate                                                                                        AS actualloadingdate,
       m_delivery_planning.plannedloadedquantity,
       m_delivery_planning.actualloadqty,
       m_delivery_planning.planneddeliverydate                                                                                      AS planneddeliverydate,
       m_delivery_planning.actualdeliverydate                                                                                       AS actualdeliverydate,
       m_delivery_planning.planneddischargequantity,
       m_delivery_planning.actualdischargequantity
FROM m_delivery_planning
         LEFT JOIN m_delivery_planning_delivery_instructions_v div ON m_delivery_planning.m_delivery_planning_id = div.m_delivery_planning_id

         LEFT JOIN c_bpartner_location loadingL ON div.c_bpartner_location_loading_id = loadingL.c_bpartner_location_id
         LEFT JOIN m_product p ON m_delivery_planning.m_product_id = p.m_product_id
         LEFT JOIN m_warehouse W ON m_delivery_planning.m_warehouse_id = W.m_warehouse_id
         LEFT JOIN c_country fromC ON m_delivery_planning.c_origincountry_id = fromC.c_country_id

;

COMMENT ON VIEW Delivery_Planning_Export_View_v
    IS 'View used to export delivery planning relevant columns.'
;
