-- Function: public.update_ad_element_trl_from_ad_window_trl(numeric, numeric)

-- DROP FUNCTION IF EXISTS public.update_ad_element_trl_from_ad_window_trl(numeric, numeric);

CREATE OR REPLACE FUNCTION public.update_ad_element_trl_from_ad_window_trl(ad_element_id numeric, ad_window_id numeric)
  RETURNS void AS
$BODY$




BEGIN


-- AD_Element_TRL via AD_Window

INSERT INTO AD_Element_TRL
(AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, AD_Element_ID, AD_Language, Name, PrintName, Description, Help, CommitWarning , IsTranslated)
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
	wint.commitWarning,
	wint.IsTranslated

	FROM AD_Window win
	JOIn AD_Window_TRL wint on win.ad_window_id = wint.ad_window_id 
	WHERE win.ad_window_id = update_ad_element_trl_from_ad_window_trl.ad_window_id);



END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_ad_element_trl_from_ad_window_trl(numeric, numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_ad_element_trl_from_ad_window_trl(numeric, numeric) IS 'When and AD_Element is create with the scope of being set in an AD_Window entry we must make sure the element translations are copied from the existing AD_Window_Trl entries.';
