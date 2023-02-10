DROP FUNCTION IF EXISTS update_PrintFormatItem_Translation_From_AD_Element(p_AD_Element_ID numeric,
                                                                           p_AD_Language character varying)
;

CREATE OR REPLACE FUNCTION update_PrintFormatItem_Translation_From_AD_Element(p_AD_Element_ID numeric = NULL,
                                                                              p_AD_Language character varying = NULL)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE ad_printformatitem_trl p_trl
    SET IsTranslated = e_trl.IsTranslated,
        printname    = e_trl.printname,
        Updated      = e_trl.updated

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND p_trl.ad_language = e_trl.ad_language
      AND EXISTS(SELECT 1
                 FROM ad_column c
                          JOIN ad_printformatitem p ON c.ad_column_id = p.ad_column_id
                 WHERE c.ad_element_id = e_trl.ad_element_id
                   AND p_trl.ad_printformatitem_id = p.ad_printformatitem_id)
      AND p_trl.updated <> e_trl.updated;


    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_PrintFormatItem_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE ad_printformatitem p
        SET Name      = e_trl.Name,
            printname = e_trl.printname,
            Updated   = e_trl.updated

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND EXISTS(SELECT 1
                     FROM AD_Column c
                     WHERE c.ad_element_id = e_trl.ad_element_id
                       AND c.ad_column_id = p.ad_column_id)
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND p.updated <> e_trl.updated;

        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_PrintFormatItem rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100
;

COMMENT ON FUNCTION update_PrintFormatItem_Translation_From_AD_Element(numeric, character varying) IS
    'When the AD_Process_Para.AD_Element_ID is changed, update all the AD_Process_Para_Trl entries of the AD_Process_Para, based on the AD_Element.'
;
