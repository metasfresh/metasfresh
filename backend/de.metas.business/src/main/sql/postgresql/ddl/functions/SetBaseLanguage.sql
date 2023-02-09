DROP FUNCTION IF EXISTS setBaseLanguage( numeric) ;


CREATE OR REPLACE FUNCTION setBaseLanguage(p_ad_language_id numeric = -1) RETURNS VOID
AS
$$
DECLARE
    destination_language  varchar := '';
    source_language       varchar := '';
    table_name            varchar;
    base_table_id         numeric;
    base_table            varchar;
    insert_query_set_template    text    := '';
    update_query_set_template    text    := '';
    final_insert_query    text    := '';
    final_update_query    text    := '';
    v_columnname          varchar;
    v_count               integer;
    insert_query_template varchar := 'INSERT INTO base_table_trl (base_table_id, ad_client_ID, ad_org_id, created, createdBy, updated, updatedBy, isTranslated, isActive, %s) '
                                         || 'SELECT base_table_id, bt.ad_client_ID, bt.ad_org_id, now(), 0, now(), 0, ''Y'', bt.IsActive, %s '
                                         || 'FROM base_table bt '
                                         || 'WHERE bt.base_table_id= %s '
        || 'AND NOT EXISTS (SELECT 1 FROM base_table_trl btt WHERE btt.base_table_id = %s AND btt.ad_language = ''%s'')';
    update_query_template varchar := 'WITH X AS (SELECT * FROM base_table_trl WHERE base_table_id = %s AND ad_language =  ''%s'') '
                                         || 'UPDATE base_table as target '
                                         || 'SET %s '
                                         || 'FROM X '
        || 'WHERE target.base_table_id = x.base_table_id ;' ;
BEGIN

    PERFORM backup_table('AD_Element');
    PERFORM backup_table('AD_Element_TRL');


    IF (p_ad_language_id IS NULL OR p_ad_language_id <= 0) THEN
        RAISE EXCEPTION 'No language found for AD_Language_ID=%', p_ad_language_id;
    END IF;

    SELECT l.ad_language INTO source_language FROM ad_language l WHERE l.isbaselanguage = 'Y';
    SELECT l.ad_language INTO destination_language FROM ad_language l WHERE l.ad_language_id = p_ad_language_id;

    IF (source_language IS NULL OR TRIM(source_language) = '') THEN
        RAISE EXCEPTION 'Previous base AD_Language was not found';
    END IF;

    IF (destination_language IS NULL OR TRIM(destination_language) = '') THEN
        RAISE EXCEPTION 'No language found for AD_Language_ID=%', p_ad_language_id;
    END IF;

    IF (source_language = destination_language) THEN
        RAISE NOTICE '% is already a base language', destination_language;
        RETURN;
    END IF;

    UPDATE ad_language
    SET isbaselanguage='N', issystemlanguage='Y'
    WHERE ad_language = source_language;

    UPDATE ad_language
    SET isbaselanguage = 'Y', issystemlanguage = 'N'
    WHERE ad_language = destination_language;


    FOR table_name IN SELECT UPPER(tablename) FROM ad_table WHERE UPPER(tablename) LIKE UPPER('%_Trl') ORDER BY 1
        LOOP
            base_table := SUBSTRING(table_name, 1, LENGTH(table_name) - 4); -- that means without _TRL. e.g. AD_Window
            BEGIN
                insert_query_set_template := '';
                update_query_set_template := '';
                FOR v_columnname IN SELECT columnname FROM ad_column WHERE istranslated = 'Y' AND ad_table_id = get_table_id(base_table) -- e.g. AD_Window.Name
                    LOOP
                    insert_query_set_template := insert_query_set_template || FORMAT('%s ,', v_columnname); -- Name ,Description ,
                        update_query_set_template := update_query_set_template || FORMAT('%s = x.%s ,', v_columnname, v_columnname); -- Name = x.Name ,Description = x.Description ,
                    END LOOP;

                insert_query_set_template := insert_query_set_template ; --=> Name ,Description,
                update_query_set_template := SUBSTRING(update_query_set_template, 1, LENGTH(update_query_set_template) - 1); -- delete the last "," => Name = x.Name ,Description = x.Description

                FOR base_table_id IN EXECUTE 'SELECT ' || base_table || '_ID FROM ' || base_table -- e.g. AD_Window_ID = 123456
                    LOOP
                        BEGIN

                            final_insert_query := FORMAT(REPLACE(insert_query_template, 'base_table', base_table),
                                insert_query_set_template || 'ad_language',
                                insert_query_set_template||' '''||  destination_language || ''' ' || 'as AD_Language',
                                base_table_id::varchar,
                                base_table_id::varchar,
                                destination_language);
                            /*
                              Example:  destination language is en_US, one of the updated entries is the AD_Window with the ID 123456. The insert query will look like:

                            INSERT INTO AD_Window_Trl (AD_Window_ID, ad_client_ID, ad_org_id, created, createdBy, updated, updatedBy, isTranslated, isActive, Name, Description, Help, AD_Language)
                            SELECT bt.AD_Window_ID,
                                   bt.ad_client_ID,
                                   bt.ad_org_id,
                                   NOW(),
                                   0,
                                   NOW(),
                                   0,
                                   'Y',
                                   bt.isActive,
                                   Name,
                                   Description,
                                   Help,
                                   'en_US' AS AD_Language
                            FROM AD_Window bt
                            WHERE bt.ad_window_id = 123456
                              AND NOT EXISTS(SELECT 1
                                             FROM AD_Window_trl btt
                                             WHERE btt.ad_window_id = 123456
                                               AND btt.ad_language = 'en_US;
                           */

                            final_update_query := FORMAT(REPLACE(update_query_template, 'base_table', base_table), base_table_id::varchar, destination_language, update_query_set_template);
                            /*
                               Example: destination language is en_US, one of the updated entries is the AD_Window with the ID 123456. The update query will look like:

                               WITH X AS (SELECT * FROM AD_Window_trl WHERE AD_Window_id = 123456 AND ad_language =  'en_US')
                                                                         UPDATE AD_Window as target
                                                                         SET Name = x.Name , Description = x.Description
                                                                         FROM X
                                        WHERE target.AD_Window_id = x.AD_Window_id ;
                                */

                            EXECUTE final_insert_query;


                            GET DIAGNOSTICS v_count = ROW_COUNT;
                            RAISE NOTICE 'Table %, %: % rows inserted', table_name, base_table || '_ID = ' || base_table_id, v_count;


                            EXECUTE final_update_query;

                            GET DIAGNOSTICS v_count = ROW_COUNT;
                            RAISE NOTICE 'Table %, %: % rows updated', table_name, base_table || '_ID = ' || base_table_id, v_count;

                        END;
                    END LOOP;
            END;

        END LOOP;

    PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(NULL,
                                                       destination_language);
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION setBaseLanguage(Numeric) IS 'Change/Reset the system base language  given the new base language ad_language  as a parameter.
For all the translation tables (ending with _TRL), update the translated columns (AD_Column.IsTranslated = ''Y'') of their corresponding base tables (identical name, without the _TRL suffix), by setting the values from the translations in the destination language.
Example:  SELECT setBaseLanguage(192);  /*en_US*/ '
;

