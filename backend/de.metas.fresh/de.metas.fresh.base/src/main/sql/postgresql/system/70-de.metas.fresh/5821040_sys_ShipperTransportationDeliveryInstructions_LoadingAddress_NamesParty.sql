-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/docs_deliveryinstructions_loadingaddress.sql
-- The delivery address names its partner, but the loading address prints only the location's
-- address block, leaving the pickup party unnamed. Join C_BPartner and prepend the name, the
-- same way docs_sales_transportorder_description builds its pickup location. Return type is
-- unchanged (scalar text), so CREATE OR REPLACE is sufficient.
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_loadingaddress(p_m_shippertransportation_id numeric)
 RETURNS text
 LANGUAGE sql
 STABLE
AS $function$
SELECT (COALESCE(bp.name || E'\n', '') || COALESCE(bpl.address, '')) AS LoadingAddress
FROM M_ShipperTransportation st
         JOIN C_BPartner_location bpl ON st.c_bpartner_location_loading_id = bpl.c_bpartner_location_id
         JOIN c_bpartner bp ON bpl.c_bpartner_id = bp.c_bpartner_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$function$
;
