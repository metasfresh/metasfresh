
-- AD_Field_TRL --name, description, help, isTranslated


DROP FUNCTION IF EXISTS update_AD_Field_TRL_On_ElementTrl(numeric, character varying);

CREATE OR REPLACE FUNCTION update_AD_Field_TRL_On_ElementTrl(
    AD_Element_ID numeric,
    AD_Language character varying
) RETURNS VOID LANGUAGE plpgsql SECURITY DEFINER AS $$

BEGIN
    UPDATE  AD_Field_TRL ftrl
	SET name = x.name, isTranslated = x.isTranslated, description = x.description, help = x.help
	FROM
	(
		select e.AD_Element_ID, f.AD_Field_ID,   ft.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.help
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Column c on e.AD_Element_ID = c.AD_Element_ID
		join AD_Field f on c.AD_Column_ID = f.AD_Column_ID
		join AD_Field_Trl ft on f.AD_Field_ID = ft.AD_Field_ID
		
		where e.AD_Element_ID = update_AD_Field_TRL_On_ElementTrl.AD_Element_ID and f.IsCentrallyMaintained = 'Y'
	) x
WHERE ftrl.AD_Field_ID = x.AD_Field_ID and ftrl.ad_language = update_AD_Field_TRL_On_ElementTrl.AD_Language;



END;
$$;

