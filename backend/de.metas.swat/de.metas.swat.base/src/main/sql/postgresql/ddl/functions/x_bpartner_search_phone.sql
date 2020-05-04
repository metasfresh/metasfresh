CREATE OR REPLACE FUNCTION x_bpartner_search_phone(bp_id numeric)
  RETURNS text AS
$BODY$
DECLARE
  v_phone text :='';
  row RECORD;
BEGIN
	FOR row IN  
	-- Ermittle Datenkombinationen zum BPartner
		SELECT bp.c_bpartner_id,
		btrim(
		COALESCE(regexp_replace(u.phone, '[^0-9]+[+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(u.phone2, '[^0-9]+[+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(bpl.phone, '[^0-9]+[+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(bpl.phone2, '[^0-9]+[+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(bpl.isdn, '[^0-9]+[+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(u.phone, '[-+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(u.phone2, '[-+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(bpl.phone, '[-+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(bpl.phone2, '[-+/()* ]', '', 'g')::text, ''::text) || ' '::text ||
		COALESCE(regexp_replace(bpl.isdn, '[-+/()* ]', '', 'g')::text, ''::text) 
		)::text as search
		from ad_user u
		left join c_bpartner bp on u.c_bpartner_id = bp.c_bpartner_id
		left join c_bpartner_location bpl on bp.c_bpartner_id = bpl.c_bpartner_id AND bp.AD_Client_ID=bpl.AD_client_ID AND bp.AD_Org_ID=bpl.AD_Org_ID
		where bp.c_bpartner_id = bp_id
		group by bp.c_bpartner_id, search	
	LOOP
		v_phone := v_phone || ' ' || row.search::text;
	END LOOP;
	--
	RETURN v_phone;
END;$BODY$
  LANGUAGE plpgsql IMMUTABLE STRICT
  COST 100;
ALTER FUNCTION x_bpartner_search_phone(numeric)
  OWNER TO adempiere;
