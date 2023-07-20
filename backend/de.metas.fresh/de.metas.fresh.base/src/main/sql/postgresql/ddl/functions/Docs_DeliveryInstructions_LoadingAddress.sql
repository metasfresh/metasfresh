CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id numeric)
    RETURNS
        text
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(bpl.address, '') AS LoadingAddress
FROM M_ShipperTransportation st
         JOIN C_BPartner_location bpl ON st.c_bpartner_location_loading_id = bpl.c_bpartner_location_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
    ;
$$
;