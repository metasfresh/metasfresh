-- Function: x_bpartner_search_string(numeric)

-- DROP FUNCTION x_bpartner_search_string(numeric);

CREATE OR REPLACE FUNCTION x_bpartner_search_string(bp_id numeric)
  RETURNS text AS
$BODY$
DECLARE
  v_search text :='';
  row RECORD;
BEGIN
	FOR row IN  
	-- Ermittle Datenkombinationen zum BPartner
			SELECT bp.c_bpartner_id,
			btrim(
			COALESCE(bp.value::text, ''::text) || ' '::text ||
			COALESCE(bp.name::text, ''::text) || ' '::text || 
			COALESCE(bp.name2::text, ''::text) || ' '::text ||
			COALESCE(u.firstname::text, ''::text) || ' '::text || 
			COALESCE(u.name::text, ''::text) || ' '::text || 
			COALESCE(l.postal::text, ''::text) || ' '::text || 
			COALESCE(l.city::text, ''::text) ||
			COALESCE(unaccent_string(bp.name::text, 1), ''::text) || ' '::text ||
			COALESCE(unaccent_string(bp.name2::text, 1), ''::text) || ' '::text ||
			COALESCE(unaccent_string(u.firstname::text, 1), ''::text) || ' '::text ||
			COALESCE(unaccent_string(u.name::text, 1), ''::text) || ' '::text ||
			COALESCE(unaccent_string(bp.name::text, 2), ''::text) || ' '::text ||
			COALESCE(unaccent_string(bp.name2::text, 2), ''::text) || ' '::text ||
			COALESCE(unaccent_string(u.firstname::text, 2), ''::text) || ' '::text ||
			COALESCE(unaccent_string(u.name::text, 2), ''::text) || ' '::text
			)::text
			AS search
			FROM c_bpartner bp
			LEFT JOIN ad_user u ON bp.c_bpartner_id = u.c_bpartner_id AND bp.AD_Client_ID=u.AD_Client_ID AND bp.AD_org_ID=u.AD_Org_ID 
			LEFT JOIN c_bpartner_location bpl on bp.c_bpartner_id = bpl.c_bpartner_id AND bp.AD_Client_ID=bpl.AD_client_ID AND bp.AD_Org_ID=bpl.AD_Org_ID
			LEFT JOIN c_location l on bpl.c_location_id = l.c_location_id AND bpl.AD_Client_ID=l.AD_Client_ID AND bpl.AD_Org_ID=l.AD_Org_ID
			where bp.c_bpartner_id = bp_id
--			and v_search is not null
		group by bp.c_bpartner_id, search
	LOOP
		v_search := v_search || ' ' || row.search::text;
		--v_search := row.search;
	END LOOP;
	--
	RETURN v_search;
	
END;$BODY$
  LANGUAGE plpgsql IMMUTABLE STRICT
  COST 100;
ALTER FUNCTION x_bpartner_search_string(numeric)
  OWNER TO adempiere;
