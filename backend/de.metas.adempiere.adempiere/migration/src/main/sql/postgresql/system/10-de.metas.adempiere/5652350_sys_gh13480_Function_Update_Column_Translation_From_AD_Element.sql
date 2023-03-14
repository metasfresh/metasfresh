DROP FUNCTION IF EXISTS update_Column_Translation_From_AD_Element(
    p_AD_Element_ID numeric,
    p_AD_Language   character varying
)
;

CREATE OR REPLACE FUNCTION update_Column_Translation_From_AD_Element(
    p_AD_Element_ID numeric = NULL,
    p_AD_Language   character varying = NULL
)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN
    UPDATE AD_Column_Trl c_trl
    SET Name         = e_trl.Name,
        Description  = e_trl.Description,
        IsTranslated = e_trl.IsTranslated
    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND c_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1 FROM ad_column c WHERE c.ad_element_id = e_trl.ad_element_id AND c.ad_column_id = c_trl.ad_column_id);
    --
    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Column_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Column c
        SET Name        = e_trl.Name,
            Description = e_trl.Description
        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND c.ad_element_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y';
        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_Column rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;
END ;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100
;

COMMENT ON FUNCTION update_Column_Translation_From_AD_Element(numeric, character varying) IS
    'Update AD_Column_Trl/AD_Column from AD_Element_ID.'
;
