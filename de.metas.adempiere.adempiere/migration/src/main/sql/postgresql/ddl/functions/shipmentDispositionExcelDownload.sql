DROP FUNCTION IF EXISTS shipmentDispositionExcelDownload(TIMESTAMP With Time Zone, numeric);

CREATE OR REPLACE FUNCTION shipmentDispositionExcelDownload(
    IN M_ShipmentSchedule_Deliverydate TIMESTAMP With Time Zone,
	IN M_BPartner_ID numeric )

  RETURNS TABLE
  (
    Deliverydate TIMESTAMP With Time Zone,
    BPartnerid numeric,
    BPdetails character varying,
    Orderid numeric,
    Documentno character varying,
    Productname character varying ,
    Productvalue character varying,
    Qtyordered numeric,
    Qtydelivered numeric
  )
   AS

$BODY$

SELECT
    sps.deliverydate,
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
	WHERE M_ShipmentSchedule_Deliverydate >= sps.deliverydate
	AND CASE WHEN M_BPartner_ID > 0 THEN bp.c_bpartner_id = M_BPartner_ID ELSE 1=1 END

$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;