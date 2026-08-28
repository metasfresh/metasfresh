CREATE OR REPLACE VIEW M_ShipperTransportation_Delivery_Planning_History_V
AS
SELECT dpa.m_delivery_planning_alloc_id,
       dpa.m_shippertransportation_id,
       dpa.m_delivery_planning_id,
       dp.m_product_id,
       dp.releaseno,
       dp.etd,
       dp.eta,
       dp.plannedloadedquantity,
       dp.planneddischargequantity,
       dpa.updated AS dateremoved,
       'Y'::character(1) AS isactive,
       dpa.ad_client_id,
       dpa.ad_org_id,
       dpa.created,
       dpa.createdby,
       dpa.updated,
       dpa.updatedby
FROM M_Delivery_Planning_Alloc dpa
         JOIN M_Delivery_Planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
WHERE dpa.isactive = 'N'
;
