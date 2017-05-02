

DROP FUNCTION IF EXISTS update_AD_PrintFormatItem_TRL_On_ElementTrl(numeric, character varying);

CREATE OR REPLACE FUNCTION update_AD_PrintFormatItem_TRL_On_ElementTrl(
    AD_Element_ID numeric,
    AD_Language character varying
) RETURNS VOID LANGUAGE plpgsql SECURITY DEFINER AS $$

BEGIN
    UPDATE  AD_PrintFormatItem_TRL pfitrl
	SET printName = x.Printname, isTranslated = x.isTranslated
	FROM
	(
		select e.AD_Element_ID, pfi.AD_PrintFormatItem_ID,   pfit.ad_language, etrl.PrintName, etrl.IsTranslated
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Column c on e.AD_Element_ID = c.AD_Element_ID
		join AD_PrintFormatItem pfi on c.AD_Column_ID = pfi.AD_Column_ID
		join AD_PrintFormatItem_Trl pfit on pfi.AD_PrintFormatItem_ID = pfit.AD_PrintFormatItem_ID
		
		where e.AD_Element_ID = update_AD_PrintFormatItem_TRL_On_ElementTrl.AD_Element_ID and pfi.IsCentrallyMaintained = 'Y'
	) x
WHERE pfitrl.AD_PrintFormatItem_ID = x.AD_PrintFormatItem_ID and pfitrl.ad_language = update_AD_PrintFormatItem_TRL_On_ElementTrl.AD_Language;



END;
$$;
