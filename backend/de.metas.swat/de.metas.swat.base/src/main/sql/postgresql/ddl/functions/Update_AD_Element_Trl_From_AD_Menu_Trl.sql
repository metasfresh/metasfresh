-- Function: public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric)

-- DROP FUNCTION IF EXISTS public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric);

CREATE OR REPLACE FUNCTION public.update_ad_element_trl_from_ad_menu_trl(ad_element_id numeric, ad_menu_id numeric)
  RETURNS void AS
$BODY$




BEGIN


-- AD_Element_TRL via AD_Menu



 UPDATE AD_Element_TRL et SET
		AD_Client_ID = x.AD_Client_ID,
 		AD_Org_ID = x.AD_Org_ID, 
		Created = x.Created,
		CreatedBy = x.CreatedBy, 
		Updated = x.Updated, 
		UpdatedBy = x.UpdatedBy, 
		IsActive = x.IsActive,
		AD_Element_ID = x.AD_Element_ID,
		AD_Language = x.AD_Language, 
		Name = x.Name, 
		PrintName = x.PrintName,
		Description = x.Description, 
		IsTranslated = x.IsTranslated,
		WEBUI_NameBrowse = x.WEBUI_NameBrowse,
		WEBUI_NameNew = x.WEBUI_NameNew,
		WEBUI_NameNewBreadcrumb = x.WEBUI_NameNewBreadcrumb

		FROM
		
	(Select 

menu.AD_Client_ID,
menu.AD_Org_ID,
now() as created,
100 as createdBy,
now() as updated,
100 as updatedBy,
'Y' as IsActive,
update_ad_element_trl_from_ad_menu_trl.ad_element_id,
	menut.AD_Language,
	menut.name,
	menut.name as Printname,
	menut.description,
	menut.IsTranslated,
	menut.WEBUI_NameBrowse,
	menut.WEBUI_NameNew,
	menut.WEBUI_NameNewBreadcrumb
	
	

	FROM AD_Menu menu
	JOIn AD_Menu_TRL menut on menu.ad_menu_id = menut.ad_menu_id 
	WHERE menu.ad_menu_id = update_ad_element_trl_from_ad_menu_trl.ad_menu_id) x
	WHERE et.AD_Element_ID = x.AD_Element_ID AND et.AD_Language = x.AD_Language;



END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_trl_from_ad_menu_trl(numeric, numeric) IS 'When and AD_Element is create with the scope of being set in an AD_Menu entry we must make sure the element translations are copied from the existing AD_Menu_Trl entries.';
