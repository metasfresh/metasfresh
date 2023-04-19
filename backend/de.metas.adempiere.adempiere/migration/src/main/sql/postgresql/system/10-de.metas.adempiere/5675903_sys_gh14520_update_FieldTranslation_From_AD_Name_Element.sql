DROP FUNCTION IF EXISTS update_FieldTranslation_From_AD_Name_Element(p_AD_Element_ID numeric,
                                                                     p_AD_Language character varying)
;

CREATE OR REPLACE FUNCTION update_FieldTranslation_From_AD_Name_Element(p_AD_Element_ID numeric = NULL,
                                                                        p_AD_Language character varying = NULL)
    RETURNS void
AS
$BODY$
DECLARE
    update_count_via_AD_Column integer;
    update_count_via_AD_Name   integer;
BEGIN
    --
    -- AD_Field_Trl via AD_Column
    UPDATE AD_Field_Trl f_trl
    SET IsTranslated = e_trl.IsTranslated,
        Name         = e_trl.Name,
        Description  = e_trl.Description,
        Help         = e_trl.Help,
        Updated      = e_trl.updated

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND f_trl.ad_language = e_trl.ad_language

      AND EXISTS(SELECT 1
                 FROM ad_column c
                          JOIN AD_Field f ON c.ad_column_id = f.ad_column_id
                 WHERE c.ad_element_id = e_trl.ad_element_id
                   AND f_trl.ad_field_id = f.ad_field_id
                   AND f.ad_name_id IS NULL)
      AND f_trl.updated <> e_trl.updated;
    --
    GET DIAGNOSTICS update_count_via_AD_Column = ROW_COUNT;


    --
    -- AD_Field_Trl via AD_Element -> AD_Name_ID
    UPDATE AD_Field_Trl f_trl
    SET IsTranslated = e_trl.IsTranslated,
        Name         = e_trl.Name,
        Description  = e_trl.Description,
        Help         = e_trl.Help,
        Updated      = e_trl.updated

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND f_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1
                 FROM AD_Field f
                 WHERE f.ad_name_id = e_trl.ad_element_id
                   AND f.ad_field_id = f_trl.ad_field_id)
      AND f_trl.updated <> e_trl.updated;

    --
    GET DIAGNOSTICS update_count_via_AD_Name = ROW_COUNT;

    RAISE NOTICE 'Updated AD_Field_Trl for AD_Element_ID=%, AD_Language=%: % rows via AD_Column, % rows via AD_Name',
        p_AD_Element_ID, p_AD_Language, update_count_via_AD_Column, update_count_via_AD_Name;

    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Field f
        SET Name        = e_trl.Name,
            Description = e_trl.Description,
            Updated     = e_trl.updated

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE f.AD_Name_ID IS NULL
          AND (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND EXISTS(SELECT 1
                     FROM AD_Column c
                     WHERE c.ad_element_id = e_trl.ad_element_id
                       AND c.ad_column_id = f.ad_column_id)
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND f.updated <> e_trl.updated;

        GET DIAGNOSTICS update_count_via_AD_Column = ROW_COUNT;

        UPDATE AD_Field f
        SET Name        = e_trl.Name,
            Description = e_trl.Description,
            Updated     = e_trl.updated

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND f.ad_Name_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND f.updated <> e_trl.updated;
        --
        GET DIAGNOSTICS update_count_via_AD_Name = ROW_COUNT;


        RAISE NOTICE 'Updated AD_Field for AD_Element_ID=%, AD_Language=%: % rows via AD_Column, % rows via AD_Name',
            p_AD_Element_ID, p_AD_Language, update_count_via_AD_Column, update_count_via_AD_Name;
    END IF;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100
;

COMMENT ON FUNCTION update_FieldTranslation_From_AD_Name_Element(numeric, character varying) IS
    'When the AD_Field.AD_Name_ID is changed, update all the AD_Field_Trl entries of the AD_Field, based on the AD_Element behind the AD_Name'
;
