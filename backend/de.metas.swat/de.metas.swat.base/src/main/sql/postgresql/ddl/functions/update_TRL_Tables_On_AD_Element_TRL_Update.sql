DROP FUNCTION IF EXISTS update_trl_tables_on_ad_element_trl_update(
    p_AD_Element_ID numeric,
    p_AD_Language   character varying
)
;

CREATE OR REPLACE FUNCTION update_trl_tables_on_ad_element_trl_update(
    p_AD_Element_ID numeric = NULL,
    p_AD_Language   character varying = NULL
)
    RETURNS void
AS
$BODY$

BEGIN

    PERFORM update_ad_element_on_ad_element_trl_update(p_AD_Element_ID, p_AD_Language);

    PERFORM update_Column_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    PERFORM update_FieldTranslation_From_AD_Name_Element(p_AD_Element_ID, p_AD_Language);

    PERFORM update_Process_Para_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    PERFORM update_PrintFormatItem_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    PERFORM update_Tab_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    PERFORM update_Window_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

    PERFORM update_Menu_Translation_From_AD_Element(p_AD_Element_ID, p_AD_Language);

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100
;

COMMENT ON FUNCTION update_trl_tables_on_ad_element_trl_update(numeric, character varying) IS
    'When the AD_Element_trl has one of its values changed, the change shall also propagate to the linked table entries:
    -AD_Column_TRL -- name, isTranslated
    -AD_Process_Para_TRL -- name, description, help, isTranslated
    -AD_Field_TRL --name, description, help, isTranslated
    -AD_PrintFormatItem_TRL -- printname, isTranslated
    -AD_Tab_TRL -- name, isTranslated, description, help, commitwarning
    -AD_Window_TRL -- name, isTranslated, description, help
    -AD_Menu_TRL-- Name, IsTranslated, description, help, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb
    The following column are updated from the AD_Element_trl of the base language:
    -AD_Element -- name , printname, description, help, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, po_name, po_printname, po_description, po_help
    -AD_Column -- name, isTranslated
    -AD_Process_Para -- name, description, help, isTranslated
    -AD_Field --name, description, help, isTranslated
    -AD_PrintFormatItem -- printname, isTranslated
    -AD_Tab -- name, isTranslated, description, help, commitwarning
    -AD_Window -- name, isTranslated, description, help
    -AD_Menu -- Name, IsTranslated, description, help, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb
    '
;
