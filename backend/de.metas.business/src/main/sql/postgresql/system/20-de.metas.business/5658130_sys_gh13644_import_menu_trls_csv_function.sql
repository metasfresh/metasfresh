DO
$$
    DECLARE
        p_ad_element_id numeric;
        p_ad_menu_id    numeric;
    BEGIN
        FOR p_ad_menu_id IN SELECT DISTINCT ad_menu_id FROM migration_data."AD_Menu_Trad" WHERE LENGTH(TRIM(name_trl)) > 0 ORDER BY 1
            LOOP
                BEGIN
                    -- Get corresponding element
                    SELECT ad_element_id INTO p_ad_element_id FROM ad_menu WHERE ad_menu_id = p_ad_menu_id;

                    -- Update element translation
                    UPDATE ad_element_trl
                    SET name                    = data.name_trl
                      , printname               = data.name_trl
                      , description             = data.description_trl
                      , webui_namenew           = data.webui_namenew_trl
                      , webui_namebrowse        = data.webui_namebrowse_trl
                      , webui_namenewbreadcrumb = data.webui_namenewbreadcrumb_trl
                    FROM migration_data."AD_Menu_Trad" data
                    WHERE ad_element_trl.ad_element_id = p_ad_element_id
                      AND ad_language IN ('fr_CH', 'fr_FR')
                      AND LENGTH(TRIM(data.name_trl)) > 0;

                END;
            END LOOP;

        -- sync elements translation, including menu
        PERFORM update_trl_tables_on_ad_element_trl_update(NULL, NULL);
    END;
$$
;

