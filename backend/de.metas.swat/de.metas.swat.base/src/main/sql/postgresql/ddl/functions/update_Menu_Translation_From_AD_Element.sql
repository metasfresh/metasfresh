DROP FUNCTION IF EXISTS update_Menu_Translation_From_AD_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_Menu_Translation_From_AD_Element
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

	UPDATE AD_Menu_Trl t
	SET
		IsTranslated = x.IsTranslated,
		Name = x.Name,
		Description = x.Description,
		webui_namebrowse = x.webui_namebrowse,
		webui_namenew = x.webui_namenew,
		webui_namenewbreadcrumb = x.webui_namenewbreadcrumb,
        Updated = x.Updated
	FROM
	(
		select
			m.AD_Menu_ID,
			etrl.AD_Element_ID,
			etrl.AD_Language,
			etrl.IsTranslated,
			etrl.Name,
			etrl.Description,
			etrl.webui_namebrowse, 
			etrl.webui_namenew, 
			etrl.webui_namenewbreadcrumb,
			etrl.Updated
		from AD_Element_Trl_Effective_v etrl
			join AD_Menu m on m.AD_Element_ID = etrl.AD_Element_ID
		where 
			etrl.AD_Element_ID = p_AD_Element_ID  
			and (p_AD_Language is null OR etrl.ad_language = p_AD_Language)
	) x
	WHERE
		t.AD_Menu_ID = x.AD_Menu_ID
		and t.AD_Language = x.AD_Language
		and t.Updated <> x.Updated
	;

	GET DIAGNOSTICS update_count = ROW_COUNT;
	raise notice 'Update % AD_Menu_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

END;
$BODY$
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
COST 100;

COMMENT ON FUNCTION update_Menu_Translation_From_AD_Element(numeric, character varying) IS
'When the AD_Menu.AD_Element_ID is changed, update all the AD_Menu_Trl entries of the AD_Menu, based on the AD_Element.';
