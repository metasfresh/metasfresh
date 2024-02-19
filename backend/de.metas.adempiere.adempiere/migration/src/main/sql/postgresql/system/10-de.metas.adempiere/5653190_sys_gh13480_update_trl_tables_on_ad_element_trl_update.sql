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
    'Propagates AD_Element_Trl changes to all depending entities (e.g. AD_Field, AD_Field_Trl, AD_Window, AD_Window_Trl, etc).'
;
