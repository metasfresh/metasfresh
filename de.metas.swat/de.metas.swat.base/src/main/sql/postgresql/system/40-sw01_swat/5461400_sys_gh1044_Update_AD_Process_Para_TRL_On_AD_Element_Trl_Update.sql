
-- AD_Process_Para_TRL -- name, description, help, isTranslated

DROP FUNCTION IF EXISTS update_AD_Process_Para_TRL_On_ElementTrl(numeric, character varying);

CREATE OR REPLACE FUNCTION update_AD_Process_Para_TRL_On_ElementTrl(
    AD_Element_ID numeric,
    AD_Language character varying
) RETURNS VOID LANGUAGE plpgsql SECURITY DEFINER AS $$

BEGIN
    UPDATE  AD_Process_Para_TRL pptrl
	SET name = x.name, isTranslated = x.isTranslated, description = x.description, help = x.help
	FROM
	(
		select e.AD_Element_ID, pp.AD_Process_Para_ID,   ppt.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.help
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Process_Para pp on e.AD_Element_ID = pp.AD_Element_ID
		join AD_Process_Para_Trl ppt on pp.AD_Process_Para_ID = ppt.AD_Process_Para_ID
		
		where e.AD_Element_ID = update_AD_Process_Para_TRL_On_ElementTrl.AD_Element_ID and pp.IsCentrallyMaintained = 'Y'
	) x
WHERE pptrl.AD_Process_Para_ID = x.AD_Process_Para_ID and pptrl.ad_language = update_AD_Process_Para_TRL_On_ElementTrl.AD_Language;



END;
$$;


