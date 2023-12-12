-- At this point, the fix is applicable if the baselanguage is de_DE
-- So far I didn't try to solve the general case

DO
$$
    BEGIN
        IF EXISTS(SELECT 1 FROM ad_language WHERE isbaselanguage = 'Y' AND ad_language = 'de_DE')
        THEN

            RAISE NOTICE 'Baselang=de_DE => make sure that we don''t have any colliding AD_Element_Trl.Names with that language';
            -- backup
            CREATE TABLE backup.ad_window_20221116_6 AS
            SELECT *
            FROM ad_window
            ;

            /* how to get back
            UPDATE ad_window w SET ad_Element_id=bkp.ad_element_id FROM backup.ad_window_20201116 bkp WHERE bkp.ad_window_ID = w.ad_window_id AND bkp.ad_element_id != w.ad_element_id;
            */

            -- select different ad_elements that
            -- - are referenced by different AD_Windows
            -- - have the same name and language
            CREATE OR REPLACE VIEW ad_element_trl_windows_fixing_v AS
            SELECT e.ad_element_id       AS e_ad_element_id,
                   e.isusecustomization  AS e_isusecustomization,
                   e2.ad_element_id      AS e2_ad_element_id,
                   e2.isusecustomization AS e2_isusecustomization,
                   e.name                AS e_name,
                   e.ad_language         AS e_ad_language,
                   w.ad_window_id        AS w_ad_window_id,
                   w.name                AS w_name,
                   w2.ad_window_id       AS w2_ad_window_id,
                   w2.name               AS w2_name
            FROM ad_element_trl e
                     JOIN AD_Window w ON w.ad_element_id = e.ad_element_id
                     JOIN ad_element_trl e2
                          ON e.ad_element_id != e2.ad_element_id AND e.ad_language = e2.ad_language
                              AND e.name = e2.name
                     JOIN AD_Window w2 ON w2.ad_element_id = e2.ad_element_id AND w2.ad_window_id > w.ad_window_id
            WHERE e.ad_language = 'de_DE' /*i.e. our base language*/
            ;

            -- store the data in a table to have a less movable target
            CREATE TABLE backup.ad_element_trl_windows_fixing_20221116_6 AS
            SELECT *
            FROM ad_element_trl_windows_fixing_v
            ;

            -- update  the AD_Element_Trl names from the AD_Windows' names so that when stuff is synched back by after_migration_sync_translations(), there are no collissions
            UPDATE ad_element_trl e
            SET name=data.w_name, updatedby=99, updated='2022-11-16 18:26'
            FROM backup.ad_element_trl_windows_fixing_20221116_6 data
            WHERE data.e_ad_element_id = e.ad_element_id
              AND name != data.w_name;

        ELSE
            RAISE NOTICE 'Baselang!=de_DE => nothing to do for this script';
        END IF;

    END;
$$;