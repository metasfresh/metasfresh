DROP FUNCTION IF EXISTS update_Menu_Translation_From_AD_Element(p_AD_Element_ID numeric,
                                                                p_AD_Language character varying)
;

CREATE OR REPLACE FUNCTION update_Menu_Translation_From_AD_Element(p_AD_Element_ID numeric = NULL,
                                                                   p_AD_Language character varying = NULL)
    RETURNS void
AS
$BODY$
DECLARE
    update_count integer;
BEGIN

    UPDATE AD_Menu_Trl m_trl
    SET IsTranslated            = e_trl.IsTranslated,
        Name                    = e_trl.Name,
        Description             = e_trl.Description,
        webui_namebrowse        = e_trl.webui_namebrowse,
        webui_namenew           = e_trl.webui_namenew,
        webui_namenewbreadcrumb = e_trl.webui_namenewbreadcrumb,
        Updated                 = NOW(),
        UpdatedBy               = 99

    FROM AD_Element_Trl_Effective_v e_trl
    WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
      AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
      AND m_trl.ad_language = e_trl.ad_language
      AND EXISTS(
            SELECT 1
            FROM ad_menu m
            WHERE m.ad_element_id = e_trl.ad_element_id
              AND m.ad_menu_id = m_trl.ad_menu_id
              AND ((COALESCE(m_trl.Name, '') NOT LIKE COALESCE(e_trl.Name, ''))
                OR (COALESCE(m_trl.Description, '') NOT LIKE COALESCE(e_trl.Description, ''))
                OR (COALESCE(m_trl.webui_namebrowse, '') NOT LIKE COALESCE(e_trl.webui_namebrowse, ''))
                OR (COALESCE(m_trl.webui_namenew, '') NOT LIKE COALESCE(e_trl.webui_namenew, ''))
                OR (COALESCE(m_trl.webui_namenewbreadcrumb, '') NOT LIKE COALESCE(e_trl.webui_namenewbreadcrumb, ''))
                OR m_trl.IsTranslated NOT LIKE e_trl.IsTranslated));

    GET DIAGNOSTICS update_count = ROW_COUNT;
    RAISE NOTICE 'Update % AD_Menu_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;


    IF (p_AD_Language IS NULL OR isBaseAD_Language(p_AD_Language) = 'Y') THEN
        UPDATE AD_Menu m
        SET Name                    = e_trl.Name,
            Description             = e_trl.Description,
            webui_namebrowse        = e_trl.webui_namebrowse,
            webui_namenew           = e_trl.webui_namenew,
            webui_namenewbreadcrumb = e_trl.webui_namenewbreadcrumb,
            Updated                 = NOW(),
            UpdatedBy               = 99

        FROM AD_Element_Trl_Effective_v e_trl
        WHERE (p_AD_Element_ID IS NULL OR e_trl.AD_Element_ID = p_AD_Element_ID)
          AND (p_AD_Language IS NULL OR e_trl.AD_Language = p_AD_Language)
          AND m.ad_element_id = e_trl.ad_element_id
          AND isbasead_language(e_trl.ad_language) = 'Y'
          AND EXISTS(SELECT 1
                     FROM AD_Menu_Trl m_trl
                     WHERE m_trl.ad_menu_id = m.ad_menu_id
                       AND m_trl.ad_language = e_trl.ad_language
                       AND (COALESCE(m.Name, '') NOT LIKE COALESCE(e_trl.Name, '')
                         OR COALESCE(m.Description, '') NOT LIKE COALESCE(e_trl.Description, '')
                         OR COALESCE(m.webui_namebrowse, '') NOT LIKE COALESCE(e_trl.webui_namebrowse, '')
                         OR COALESCE(m.webui_namenew, '') NOT LIKE COALESCE(e_trl.webui_namenew, '')
                         OR COALESCE(m.webui_namenewbreadcrumb, '') NOT LIKE
                            COALESCE(e_trl.webui_namenewbreadcrumb, '')));
        --
        GET DIAGNOSTICS update_count = ROW_COUNT;
        RAISE NOTICE 'Update % AD_menu rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;
    END IF;

END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    SECURITY DEFINER
    COST 100
;

COMMENT ON FUNCTION update_Menu_Translation_From_AD_Element(numeric, character varying) IS
    'When the AD_Menu.AD_Element_ID is changed, update all the AD_Menu_Trl entries of the AD_Menu, based on the AD_Element.'
;
