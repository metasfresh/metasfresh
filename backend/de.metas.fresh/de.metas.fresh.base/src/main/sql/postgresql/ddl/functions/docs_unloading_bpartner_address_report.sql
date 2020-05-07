-- Function: de_metas_endcustomer_fresh_reports.docs_unloading_bpartner_address_report(numeric)

-- DROP FUNCTION de_metas_endcustomer_fresh_reports.docs_unloading_bpartner_address_report(numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_unloading_bpartner_address_report(record_id numeric)
  RETURNS  TABLE ( 
		unload_bpartner_name character varying,
		unload_bpartner_address character varying
	) AS 
$$
SELECT
	bploc.Name, bploc.Address
FROM
	M_InOut io
JOIN C_Order o ON io.c_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
JOIN C_BPartner_Location bploc ON o.Handover_Location_ID = bploc.C_BPartner_Location_ID AND bploc.isActive = 'Y'

WHERE io.M_InOut_ID = $1 AND io.isActive = 'Y'
$$
LANGUAGE sql STABLE;

