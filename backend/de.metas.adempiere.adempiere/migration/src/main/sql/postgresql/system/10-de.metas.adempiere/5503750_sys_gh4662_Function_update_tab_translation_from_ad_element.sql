-- Function: public.update_tab_translation_from_ad_element(numeric)

-- DROP FUNCTION IF EXISTS public.update_tab_translation_from_ad_element(numeric);

CREATE OR REPLACE FUNCTION public.update_tab_translation_from_ad_element(ad_element_id numeric)
  RETURNS void AS
$BODY$

BEGIN


-- AD_Tab_TRL via AD_Element

 UPDATE  AD_Tab_TRL tabtrl
	SET name = x.name, isTranslated = x.isTranslated, description = x.description, help = x.help, commitwarning = x.commitwarning
	FROM
	(
		select t.AD_Element_ID, t.AD_Tab_ID,   tt.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.help, etrl.commitwarning
	
		from AD_Element_Trl etrl 
		join AD_Tab t on etrl.AD_Element_ID = t.AD_Element_ID
		join AD_Tab_Trl tt on t.AD_Tab_ID = tt.AD_Tab_ID
		where 
			t.AD_Element_ID = update_tab_translation_From_AD_Element.AD_Element_ID  
			and tt.ad_language = etrl.ad_language 
			
	) x
WHERE tabtrl.AD_Tab_ID = x.AD_Tab_ID and tabtrl.ad_language = x.ad_language;





END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_tab_translation_from_ad_element(numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_tab_translation_from_ad_element(numeric) IS 'When the AD_Tab.AD_Element_ID is changed, update all the AD_Tab_Trl entries of the AD_Tab, based on the AD_Element.';
