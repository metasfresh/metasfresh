-- Function: public.update_window_translation_from_ad_element(numeric)

-- DROP FUNCTION IF EXISTS public.update_window_translation_from_ad_element(numeric);

CREATE OR REPLACE FUNCTION public.update_window_translation_from_ad_element(ad_element_id numeric)
  RETURNS void AS
$BODY$

BEGIN


-- AD_Window_TRL via AD_Element

 UPDATE  AD_Window_TRL wtrl
	SET name = x.name, isTranslated = x.isTranslated, description = x.description, help = x.help
	FROM
	(
		select w.AD_Element_ID, w.AD_Window_ID,   wt.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.help
	
		from AD_Element_Trl etrl 
		join AD_Window w on etrl.AD_Element_ID = w.AD_Element_ID
		join AD_Window_Trl wt on w.AD_Window_ID = wt.AD_Window_ID
		where 
			w.AD_Element_ID = update_window_translation_From_AD_Element.AD_Element_ID  
			and wt.ad_language = etrl.ad_language 
			
	) x
WHERE wtrl.AD_Window_ID = x.AD_Window_ID and wtrl.ad_language = x.ad_language;





END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_window_translation_from_ad_element(numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_window_translation_from_ad_element(numeric) IS 'When the AD_Window.AD_Element_ID is changed, update all the AD_Window_Trl entries of the AD_Window, based on the AD_Element.';
