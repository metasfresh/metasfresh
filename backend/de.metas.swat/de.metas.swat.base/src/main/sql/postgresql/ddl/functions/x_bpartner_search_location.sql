-- task 04167 Umlautsuche in Feld Wo (2013041610000054)

-- Function: x_bpartner_search_location(numeric)

--DROP FUNCTION IF EXISTS x_bpartner_search_location(numeric);

CREATE OR REPLACE FUNCTION x_bpartner_search_location(bp_id numeric)
  RETURNS text AS
$BODY$
DECLARE
  v_search_location text :='';
  row RECORD;
BEGIN
 FOR row IN  
 
   SELECT bp.c_bpartner_id,
   -- make sure that we have in the search_lcoation field all possible forms
   btrim(
   COALESCE(unaccent_string(l.Address1::text, 1), ''::text) || ' '::text || -- replace for example Zu:rich with Zuerich
   COALESCE(unaccent_string(l.Address1::text, 2), ''::text) || ' '::text || -- replace for example Zuerich with 
   COALESCE(l.Address1::text, ''::text) || ' '::text || -- text as it is
   COALESCE(unaccent_string(l.Address2::text, 1), ''::text) || ' '::text || 
   COALESCE(unaccent_string(l.Address2::text, 2), ''::text) || ' '::text || 
   COALESCE(l.Address2::text, ''::text) || ' '::text ||
   COALESCE(unaccent_string(l.Address3::text, 1), ''::text) || ' '::text || 
   COALESCE(unaccent_string(l.Address3::text, 2), ''::text) || ' '::text || 
   COALESCE(l.Address3::text, ''::text) || ' '::text ||
   COALESCE(unaccent_string(l.Address4::text, 1), ''::text) || ' '::text || 
   COALESCE(unaccent_string(l.Address4::text, 2), ''::text) || ' '::text || 
   COALESCE(l.Address4::text, ''::text) || ' '::text ||
   COALESCE(unaccent_string(l.postal::text, 1), ''::text) || ' '::text || 
   COALESCE(unaccent_string(l.postal::text, 2), ''::text) || ' '::text || 
   COALESCE(l.postal::text, ''::text) || ' '::text || 
   COALESCE(unaccent_string(l.city::text, 1), ''::text) || ' '::text || 
   COALESCE(unaccent_string(l.city::text, 2), ''::text) || ' '::text || 
   COALESCE(l.city::text, ''::text) 
   )::text
   AS search_location
   FROM c_bpartner bp
   LEFT JOIN c_bpartner_location bpl on bp.c_bpartner_id = bpl.c_bpartner_id AND bp.AD_Client_ID=bpl.AD_client_ID AND bp.AD_Org_ID=bpl.AD_Org_ID
   LEFT JOIN c_location l on bpl.c_location_id = l.c_location_id AND bpl.AD_Client_ID=l.AD_Client_ID AND bpl.AD_Org_ID=l.AD_Org_ID
   where bp.c_bpartner_id = bp_id
  group by bp.c_bpartner_id, search_location
 LOOP
  v_search_location := v_search_location || ' ' || row.search_location::text;
 END LOOP;
 --
 RETURN v_search_location;
 
END;$BODY$
  LANGUAGE plpgsql IMMUTABLE STRICT
  COST 100;
ALTER FUNCTION x_bpartner_search_location(numeric)
  OWNER TO adempiere;
