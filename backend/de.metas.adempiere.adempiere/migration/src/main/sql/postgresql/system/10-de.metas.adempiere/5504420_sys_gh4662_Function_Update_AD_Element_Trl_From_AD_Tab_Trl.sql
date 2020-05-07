-- Function: public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric)

-- DROP FUNCTION IF EXISTS public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric);

CREATE OR REPLACE FUNCTION public.update_ad_element_trl_from_ad_tab_trl(ad_element_id numeric, ad_tab_id numeric)
  RETURNS void AS
$BODY$




BEGIN


-- AD_Element_TRL via AD_Tab
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
		Help = x.Help, 
		CommitWarning = x.CommitWarning, 
		IsTranslated = x.IsTranslated

		FROM
		
	(Select 

	tab.AD_Client_ID,
	tab.AD_Org_ID,
	now() as created,
	100 as createdBy,
	now() as updated,
	100 as updatedBy,
	'Y' as IsActive,
	update_ad_element_trl_from_ad_tab_trl.ad_element_id as AD_Element_ID,
		tabt.AD_Language,
		tabt.name,
		tabt.name as Printname,
		tabt.description,
		tabt.help,
		tabt.commitWarning,
		tabt.IsTranslated

		FROM AD_Tab tab
		JOIn AD_Tab_TRL tabt on tab.AD_Tab_ID = tabt.AD_Tab_ID 
		WHERE tab.ad_tab_ID = update_ad_element_trl_from_ad_tab_trl.ad_tab_ID)x
			WHERE et.AD_Element_ID = x.AD_Element_ID AND et.AD_Language = x.AD_Language;



END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_trl_from_ad_tab_trl(numeric, numeric) IS 'When and AD_Element is create with the scope of being set in an AD_Tab entry we must make sure the element translations are copied from the existing ad_tab_trl entries.';
