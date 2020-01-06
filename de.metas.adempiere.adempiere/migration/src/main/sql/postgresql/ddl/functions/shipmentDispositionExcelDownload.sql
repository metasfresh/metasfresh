DROP FUNCTION IF EXISTS shipmentDispositionExcelDownload(TIMESTAMP With Time Zone, numeric);

CREATE OR REPLACE FUNCTION shipmentDispositionExcelDownload(IN p_C_ShipmentSchedule_Deliverydate TIMESTAMP With Time Zone,
                                                            IN p_C_BPartner_ID numeric,
                                                            IN p_AD_Org_ID numeric)

    RETURNS TABLE
            (
                Deliverydate TIMESTAMP With Time Zone,
                BPartnerid   numeric,
                BPdetails    character varying,
                Orderid      numeric,
                Documentno   character varying,
                Productname  character varying,
                Productvalue character varying,
                Qtyordered   numeric,
                Qtydelivered numeric
            )
AS

$BODY$

SELECT sps.deliverydate,
       sps.c_bpartner_id,
       CONCAT(bp.value, ' ', bp.name) as bpdetails,
       o.c_order_id,
       o.documentno,
       pt.name,
       pt.value,
       sps.qtyordered_tu,
       sps.qtydelivered

FROM M_ShipmentSchedule sps
         LEFT OUTER JOIN c_bpartner bp on bp.c_bpartner_id = sps.c_bpartner_id
         LEFT OUTER JOIN c_order o on o.c_order_id = sps.c_order_id
         LEFT OUTER JOIN m_product pt on pt.m_product_id = sps.m_product_id
WHERE p_C_ShipmentSchedule_Deliverydate >= sps.deliverydate
  AND CASE WHEN p_C_BPartner_ID > 0 THEN bp.c_bpartner_id = p_C_BPartner_ID ELSE 1 = 1 END
  AND sps.isActive = 'Y'
  AND sps.AD_Org_ID = p_AD_Org_ID

$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;
