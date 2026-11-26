CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id numeric)
    RETURNS
        Table
        (
            address          text,
            transportdetails text
        )
    STABLE
    LANGUAGE sql
AS
$$
SELECT (COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '')) AS address, dp.transportdetails
FROM M_ShipperTransportation st
         LEFT JOIN M_shipper sh ON st.m_shipper_id = sh.m_shipper_id
         LEFT JOIN c_bpartner bp ON sh.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN C_BPartner_location bpl ON bpl.c_bpartner_id = bp.c_bpartner_id AND (bpl.isshiptodefault = 'Y' OR bpl.isshipto = 'Y')
         LEFT JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = st.m_delivery_planning_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
LIMIT 1
$$
;
