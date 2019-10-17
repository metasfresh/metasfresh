-- Function: public.update_menu_translation_from_ad_element(numeric)

-- DROP FUNCTION IF EXISTS public.update_menu_translation_from_ad_element(numeric);

CREATE OR REPLACE FUNCTION public.update_menu_translation_from_ad_element(ad_element_id numeric)
  RETURNS void AS
$BODY$

BEGIN


-- AD_Menu_TRL via AD_Element

 UPDATE  AD_Menu_TRL menutrl
	SET 
		name = x.name, 
		isTranslated = x.isTranslated, 
		description = x.description, 
		WEBUI_NameBrowse = x.WEBUI_NameBrowse, 
		WEBUI_NameNew = x.WEBUI_NameNew, 
		WEBUI_NameNewBreadcrumb = x.WEBUI_NameNewBreadcrumb
		
	FROM
	(
		select m.AD_Element_ID, m.AD_Menu_ID,   mt.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.WEBUI_NameBrowse, etrl.WEBUI_NameNew, etrl.WEBUI_NameNewBreadcrumb
	
		from AD_Element_Trl etrl 
		join AD_Menu m on etrl.AD_Element_ID = m.AD_Element_ID
		join AD_Menu_Trl mt on m.AD_Menu_ID = mt.AD_Menu_ID
		where 
			m.AD_Element_ID = update_menu_translation_From_AD_Element.AD_Element_ID  
			and mt.ad_language = etrl.ad_language 
			
	) x
WHERE menutrl.AD_Menu_ID = x.AD_Menu_ID and menutrl.ad_language = x.ad_language;


END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_menu_translation_from_ad_element(numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_menu_translation_from_ad_element(numeric) IS 'When the AD_Menu.AD_Element_ID is changed, update all the AD_Menu_Trl entries of the AD_Menu, based on the AD_Element.';
