DROP FUNCTION IF EXISTS update_trl_tables_on_ad_element_trl_update(numeric, character varying);

CREATE OR REPLACE FUNCTION update_trl_tables_on_ad_element_trl_update
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
	--
	-- AD_Column_Trl
	PERFORM update_Column_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

	--
	-- AD_Field_TRL
	PERFORM update_FieldTranslation_From_AD_Name_Element(p_AD_Element_ID, p_AD_Language);

	--
	-- AD_Process_Para_Trl
	UPDATE AD_Process_Para_Trl t
	SET
		IsTranslated = x.IsTranslated,
		Name = x.Name,
		Description = x.Description,
		Help = x.Help
	FROM
	(
		select
			pp.AD_Process_Para_ID,
			etrl.AD_Element_ID,
			etrl.ad_language,
			etrl.IsTranslated,
			etrl.Name,
			etrl.Description,
			etrl.Help
		from AD_Element_Trl_Effective_v etrl
			join AD_Process_Para pp on pp.AD_Element_ID = etrl.AD_Element_ID
		where
			etrl.AD_Element_ID = p_AD_Element_ID
			and pp.IsCentrallyMaintained = 'Y'
			and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
	) x
	WHERE
		t.AD_Process_Para_ID = x.AD_Process_Para_ID 
		and t.AD_Language = x.AD_Language
	;
	--
	GET DIAGNOSTICS update_count = ROW_COUNT;
	raise notice 'Update % AD_Process_Para_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


--
-- AD_PrintFormatItem_TRL
	UPDATE  AD_PrintFormatItem_Trl t
	SET
		IsTranslated = x.IsTranslated,
		PrintName = x.PrintName
	FROM
	(
		select
			pfi.AD_PrintFormatItem_ID,
			etrl.AD_Element_ID,
			etrl.AD_Language,
			etrl.IsTranslated,
			etrl.PrintName
		from AD_Element_Trl_Effective_v etrl
			join AD_Column c on c.AD_Element_ID = etrl.AD_Element_ID
			join AD_PrintFormatItem pfi on c.AD_Column_ID = pfi.AD_Column_ID
		where
			etrl.AD_Element_ID = p_AD_Element_ID
			and pfi.IsCentrallyMaintained = 'Y'
			and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
	) x
	WHERE
		t.AD_PrintFormatItem_ID = x.AD_PrintFormatItem_ID
		and t.AD_Language = x.AD_Language
	;
	--
	GET DIAGNOSTICS update_count = ROW_COUNT;
	raise notice 'Update % AD_PrintFormatItem_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

	--
	-- AD_Tab_TRL
	PERFORM update_Tab_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

	--
	-- AD_Window_TRL
	PERFORM update_Window_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

	--
	-- AD_Menu_TRL
	PERFORM update_Menu_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

END;
$BODY$
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
COST 100;

COMMENT ON FUNCTION update_trl_tables_on_ad_element_trl_update(numeric, character varying) IS
'When the AD_Element_trl has one of its values changed, the change shall also propagate to the linked table entries:
-AD_Column_TRL -- name, isTranslated
-AD_Process_Para_TRL -- name, description, help, isTranslated
-AD_Field_TRL --name, description, help, isTranslated
-AD_PrintFormatItem_TRL -- printname, isTranslated
-AD_Tab_TRL -- name, isTranslated, description, help, commitwarning
-AD_Window_TRL -- name, isTranslated, description, help
-AD_Menu_TRL-- Name, IsTranslated, description, help, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb
';
