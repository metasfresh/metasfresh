DROP FUNCTION IF EXISTS update_Window_Translation_From_AD_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_Window_Translation_From_AD_Element
(
	p_AD_Element_ID numeric,
	p_AD_Language character varying = null
)
RETURNS void
AS
$BODY$
DECLARE
	update_count integer;
BEGIN
	
	UPDATE AD_Window_TRL t
	SET
		IsTranslated = x.IsTranslated,
		Name = x.Name,
		Description = x.Description,
		Help = x.Help,
        Updated = x.Updated
	FROM
	(
		select
			w.AD_Window_ID,
			etrl.AD_Element_ID,
			etrl.ad_language,
			etrl.IsTranslated,
			etrl.Name,
			etrl.description,
			etrl.help,
			etrl.Updated
		from AD_Element_Trl_Effective_v etrl
			join AD_Window w on w.AD_Element_ID = etrl.AD_Element_ID
		where 
			etrl.AD_Element_ID = p_AD_Element_ID  
			and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
	) x
	WHERE
		t.AD_Window_ID = x.AD_Window_ID
		and t.AD_Language = x.AD_Language
		and t.Updated <> x.Updated
	;
	
	GET DIAGNOSTICS update_count = ROW_COUNT;
	raise notice 'Update % AD_Window_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

END;
$BODY$
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
COST 100;

COMMENT ON FUNCTION update_Window_Translation_From_AD_Element(numeric, character varying) IS 
'When the AD_Window.AD_Element_ID is changed, update all the AD_Window_Trl entries of the AD_Window, based on the AD_Element.';
