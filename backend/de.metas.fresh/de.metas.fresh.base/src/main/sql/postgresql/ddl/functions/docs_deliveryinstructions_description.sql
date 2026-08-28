CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description(p_m_shippertransportation_id numeric)
 RETURNS TABLE(forwarderaddress text, transportdetails text, deliveryaddress text, deliverycontactname character varying, deliverycontactphone character varying, loadingaddress text, loadingdate timestamp without time zone, loadingtime character varying, deliverydate timestamp without time zone, documentno character varying, creator character varying, creatorphone character varying, creatorfax character varying, creatoremail character varying, incoterms character varying, incotermlocation character varying, meansoftransport text)
 LANGUAGE sql
 STABLE
AS $function$
SELECT f.*,
       d.*,
       l.*,
       st.etd        AS loadingdate,
       st.loadingtime,
       st.eta        AS deliverydate,
       st.documentno,
       u.name        AS Creator,
       u.phone       AS CreatorPhone,
       u.fax         AS CreatorFax,
       u.email       AS CreatorEmail,
       ic.name       AS incoterms,
       st.incotermlocation,
       mt.name       AS meansoftransport

FROM de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id) AS l,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id) AS f,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_DeliveryAddress(p_m_shippertransportation_id) AS d,
     M_ShipperTransportation st
         JOIN ad_user u ON st.createdby = u.ad_user_id
         JOIN c_incoterms ic ON ic.c_incoterms_id = st.c_incoterms_id
         LEFT JOIN m_meansoftransportation mt ON mt.m_meansoftransportation_id = st.m_meansoftransportation_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$function$
;
