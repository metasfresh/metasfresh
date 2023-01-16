/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


DROP FUNCTION IF EXISTS after_migration_sync_translations();


CREATE FUNCTION after_migration_sync_translations() RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    PERFORM update_TRL_Tables_On_AD_Element_TRL_Update();

    PERFORM sync_translations_of_tables_without_ad_element_id();

    RAISE NOTICE 'Synchronized translations';
END;
$$
;

DROP FUNCTION IF EXISTS sync_translations_of_tables_without_ad_element_id()
;


CREATE OR REPLACE FUNCTION sync_translations_of_tables_without_ad_element_id() RETURNS VOID
AS
$$
DECLARE
    IGNORED_TABLES CONSTANT varchar[] := ARRAY['AD_ELEMENT_TRL', 'AD_MENU_TRL', 'AD_WINDOW_TRL', 'AD_COLUMN_TRL', 'AD_FIELD_TRL', 'AD_TAB_TRL', 'AD_PROCESS_PARA_TRL', 'AD_PRINTFORMATITEM_TRL'];
    base_language  varchar := '';
    destination_language        varchar := '';
    table_name                  varchar;
    base_table                  varchar;
    insert_query_set_template   text    := '';
    update_query_set_template   text    := '';
    update_query_and_template   text    := '';
    final_insert_query          text    := '';
    final_update_query          text    := '';
    v_columnName                varchar;
    v_count                     integer;
    v_count_updated             integer := 0;
    v_count_inserted            integer := 0;
    insert_query_template       varchar := 'INSERT INTO base_table_trl (base_table_id, ad_client_ID, ad_org_id, created, createdBy, updated, updatedBy, isTranslated, isActive, %s) '
                                               || 'SELECT base_table_id, bt.ad_client_ID, bt.ad_org_id, now(), 0, now(), 0, ''N'', bt.IsActive, %s '
                                               || 'FROM base_table bt '
        || 'WHERE NOT EXISTS (SELECT 1 FROM base_table_trl trl WHERE trl.base_table_id = bt.base_table_id AND trl.ad_language = ''%s'')';
    update_query_template       varchar := 'UPDATE base_table as bt '
                                               || 'SET %s '
                                               || 'FROM base_table_trl trl '
                                               || 'WHERE bt.base_table_id = trl.base_table_id '
        || 'AND trl.ad_language = ''%s'' ';
BEGIN

    SELECT ad_language INTO base_language FROM ad_language WHERE isbaselanguage = 'Y';

    IF (base_language IS NULL OR TRIM(base_language) = '') THEN
        RAISE EXCEPTION 'Base language is not set';
    END IF;

    FOR table_name IN SELECT UPPER(tablename) FROM ad_table WHERE UPPER(tablename) LIKE UPPER('%_Trl') AND UPPER(tablename) NOT LIKE ALL( IGNORED_TABLES ) ORDER BY 1
        LOOP
            base_table := SUBSTRING(table_name, 1, LENGTH(table_name) - 4); -- that means without _TRL. e.g. AD_Ref_List
            insert_query_set_template := '';
            update_query_set_template := '';
            update_query_and_template := '';
            FOR v_columnname IN SELECT columnname FROM ad_column WHERE istranslated = 'Y' AND ad_table_id = get_table_id(base_table) -- e.g. AD_Ref_List.Name
                LOOP
                    insert_query_set_template := insert_query_set_template || FORMAT('%s ,', v_columnname); -- Name ,Description ,
                    update_query_set_template := update_query_set_template || FORMAT('%s = trl.%s, ', v_columnname, v_columnname); -- Name = trl.Name ,Description = trl.Description ,
                    update_query_and_template := update_query_and_template || FORMAT('bt.%s <> trl.%s OR ', v_columnname, v_columnname); -- bt.Name <> trl.Name OR bt.Description <> trl.Description OR
                END LOOP;

            insert_query_set_template := insert_query_set_template ; --=> Name ,Description,
            update_query_set_template := SUBSTRING(update_query_set_template, 1, LENGTH(update_query_set_template) - 2); -- delete the last ", " => Name = trl.Name ,Description = trl.Description
            update_query_and_template := 'AND ( ' || SUBSTRING( update_query_and_template, 1, LENGTH(update_query_and_template) - 3) || ' );'; -- delete the last "OR " => AND ( Name <> trl.Name OR Description <> trl.Description )

            FOR destination_language IN EXECUTE 'SELECT DISTINCT ad_language FROM ad_language WHERE isbaselanguage = ''Y'' OR issystemlanguage = ''Y'';'  -- e.g. `en_US`
                LOOP
                    final_insert_query := FORMAT(REPLACE(insert_query_template, 'base_table', base_table),
                                                 insert_query_set_template || ' ad_language',
                                                 insert_query_set_template ||' '''||  destination_language || ''' ' || 'as AD_Language',
                                                 destination_language);
                    /*
                      Example:  destination language is de_DE. The insert query will look like:

                    INSERT INTO AD_Ref_List_Trl (AD_Ref_List_ID, ad_client_ID, ad_org_id, created, createdBy, updated, updatedBy, isTranslated, isActive, Name, Description, AD_Language)
                    SELECT bt.AD_Ref_List_ID,
                           bt.ad_client_ID,
                           bt.ad_org_id,
                           NOW(),
                           0,
                           NOW(),
                           0,
                           'N',
                           bt.isActive,
                           Name,
                           Description,
                           'de_DE' AS AD_Language
                    FROM AD_Ref_List bt
                    WHERE NOT EXISTS(SELECT 1
                                     FROM AD_Ref_List_trl trl
                                     WHERE trl.AD_Ref_List_id = bt.ad_ref_list_id
                                       AND trl.ad_language = 'de_DE')
                    ;
                   */

                    EXECUTE final_insert_query;
                    GET DIAGNOSTICS v_count = ROW_COUNT;
                    IF (v_count > 0) THEN
                        v_count_inserted = v_count_inserted + v_count;
                        RAISE NOTICE 'Inserted % missing translation-rows into % where ad_language = %', v_count, table_name, destination_language;
                    END IF;
                END LOOP;

            final_update_query := FORMAT(REPLACE(update_query_template, 'base_table', base_table), update_query_set_template, base_language);
            final_update_query := final_update_query || update_query_and_template;
            /*
                Example: base language is de_DE. The update query will look like:

                UPDATE AD_Ref_List AS bt
                SET Name = trl.Name, Description = trl.Description
                FROM ad_ref_list_trl trl
                WHERE bt.AD_Ref_List_id = trl.ad_ref_list_id
                  AND trl.ad_language='de_DE'
                  AND (bt.Name <> trl.Name OR bt.Description <> trl.Description)
;
            */

            EXECUTE final_update_query;
            GET DIAGNOSTICS v_count = ROW_COUNT;
            IF (v_count > 0) THEN
                v_count_updated = v_count_updated + v_count;
                RAISE NOTICE '% updated % rows because they did not match baselanguage translation', base_table, v_count;
            END IF;
        END LOOP;
    RAISE NOTICE 'Inserted % missing translation-rows, updated % base-table-rows that were not equal to baselanguage translation', v_count_inserted, v_count_updated;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION sync_translations_of_tables_without_ad_element_id() IS 'Adds missing Translation to translation tables %_trl for all base and system-languages.
    In addition updates all base_tables if the current value is not the same as the translation for the current baselanguage'
;