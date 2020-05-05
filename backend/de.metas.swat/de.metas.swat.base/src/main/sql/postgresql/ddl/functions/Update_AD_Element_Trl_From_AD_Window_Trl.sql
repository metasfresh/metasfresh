-- Function: public.update_ad_element_trl_from_ad_window_trl(numeric, numeric)

-- DROP FUNCTION IF EXISTS public.update_ad_element_trl_from_ad_window_trl(numeric, numeric);

CREATE OR REPLACE FUNCTION public.update_ad_element_trl_from_ad_window_trl(ad_element_id numeric, ad_window_id numeric)
  RETURNS void AS
$BODY$




BEGIN


-- AD_Element_TRL via AD_Window

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
		IsTranslated = x.IsTranslated

		FROM
		
	(Select 

win.AD_Client_ID,
win.AD_Org_ID,
now() as created,
100 as createdBy,
now() as updated,
100 as updatedBy,
'Y' as IsActive,
update_ad_element_trl_from_ad_window_trl.ad_element_id,
	wint.AD_Language,
	wint.name,
	wint.name as Printname,
	wint.description,
	wint.help,
	wint.IsTranslated

	FROM AD_Window win
	JOIn AD_Window_TRL wint on win.ad_window_id = wint.ad_window_id 
	WHERE win.ad_window_id = update_ad_element_trl_from_ad_window_trl.ad_window_id) x
		WHERE et.AD_Element_ID = x.AD_Element_ID AND et.AD_Language = x.AD_Language;



		
		
		
END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_ad_element_trl_from_ad_window_trl(numeric, numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_trl_from_ad_window_trl(numeric, numeric) IS 'When and AD_Element is create with the scope of being set in an AD_Window entry we must make sure the element translations are copied from the existing AD_Window_Trl entries.';
