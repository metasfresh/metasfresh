-- Function: public.update_ad_element_on_ad_element_trl_update(numeric, character varying)

-- DROP FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying);

CREATE OR REPLACE FUNCTION public.update_ad_element_on_ad_element_trl_update(
    ad_element_id numeric,
    ad_language character varying)
  RETURNS void AS
$BODY$

BEGIN

    UPDATE AD_Element e
	SET 
		name = x.name, 
		printname = x.printname,
		description = x.description,
		help = x.help,
		commitwarning = x.commitwarning,
		webui_namebrowse = x.webui_namebrowse, 
		webui_namenew = x.webui_namenew, 
		webui_namenewbreadcrumb = x.webui_namenewbreadcrumb,
		po_name = x.po_name,
		po_printname = x.po_printname,
		po_description = x.po_description,
		po_help = x.po_help,
        updated = x.updated
		
	FROM
	(
		select 
			e.AD_Element_ID,  
			etrl.ad_language, 
			etrl.Name, 
			etrl.printname,
			etrl.description,
		    etrl.help,
			etrl.commitwarning,
			etrl.webui_namebrowse,
			etrl.webui_namenew,
			etrl.webui_namenewbreadcrumb,
			etrl.po_name,
			etrl.po_printname,
			etrl.po_description,
			etrl.po_help,
            etrl.updated
			
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		
		where e.AD_Element_ID = update_ad_element_on_ad_element_trl_update.AD_Element_ID and etrl.ad_language =  update_ad_element_on_ad_element_trl_update.AD_Language
			
	) x
WHERE e.AD_Element_ID = x.AD_Element_ID
  AND e.updated <> x.updated;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_on_ad_element_trl_update(numeric, character varying) IS 'When the AD_Element_trl has one of its values changed, the change shall also propagate to the parent AD_Element. 
This is used for automatic updates on AD_Element which has column that are not manually updatable.
';
