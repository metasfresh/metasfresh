
-- When the AD_Element_trl has its name or the IsTranslated flag changed, the change shall also propagate to the linked AD_Column_Trl entries

DROP FUNCTION IF EXISTS update_AD_Column_TRL_On_ElementTrl(numeric, character varying);

CREATE OR REPLACE FUNCTION update_AD_Column_TRL_On_ElementTrl(
    AD_Element_ID numeric,
    AD_Language character varying
) RETURNS VOID LANGUAGE plpgsql SECURITY DEFINER AS $$

BEGIN
    UPDATE AD_Column_TRL ctrl
	SET name = x.name, isTranslated = x.isTranslated
	FROM
	(
		select e.AD_Element_ID,  c.AD_Column_ID, ct.ad_language, etrl.Name, etrl.IsTranslated
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Column c on e.AD_Element_ID = c.AD_Element_ID
		join AD_Column_TRL ct on c.AD_Column_ID = ct.AD_Column_ID
		
		where e.AD_Element_ID = update_AD_Column_TRL_On_ElementTrl.AD_Element_ID
			
	) x
WHERE ctrl.AD_Column_ID = x.AD_Column_ID and ctrl.ad_language = update_AD_Column_TRL_On_ElementTrl.AD_Language;


END;
$$;
