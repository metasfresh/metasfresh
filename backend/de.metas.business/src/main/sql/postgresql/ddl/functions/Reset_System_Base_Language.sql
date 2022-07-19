CREATE OR REPLACE FUNCTION Reset_System_Base_Language(p_source_language      varchar,
                                                      p_destination_language varchar) RETURNS VOID
AS
$$
DECLARE
    destination_language  varchar := '';
    source_language       varchar := '';
    table_name            varchar;
    base_table_id         numeric;
    base_table            varchar;
    query_set_template    text    := '';
    final_update_query    text    := '';
    v_columnname          varchar;
    update_query_template varchar := 'WITH X AS (SELECT * FROM base_table_trl WHERE base_table_id = %s AND ad_language =  ''%s'') '
                                         || 'UPDATE base_table as target '
                                         || 'SET %s '
                                         || 'FROM X '
        || 'WHERE target.base_table_id = x.base_table_id ;' ;
BEGIN

    IF (p_source_language IS NULL OR TRIM(p_source_language) = '') THEN
        RAISE WARNING 'No source language is provided';
        RETURN;
    END IF;

    IF (p_destination_language IS NULL OR TRIM(p_destination_language) = '') THEN
        RAISE WARNING 'No destination language is provided';
        RETURN;
    END IF;

    source_language := TRIM(p_source_language);
    destination_language := TRIM(p_destination_language);

    -- update languages
    UPDATE ad_language
    SET isbaselanguage='N', issystemlanguage='Y'
    WHERE ad_language = source_language;

    UPDATE ad_language
    SET isbaselanguage = 'Y', issystemlanguage = 'Y'
    WHERE ad_language = destination_language;

    -- Iterate over Trls tables
    FOR table_name IN SELECT UPPER(tablename) FROM ad_table WHERE UPPER(tablename) LIKE UPPER('%_Trl') ORDER BY 1
        LOOP
            base_table := REPLACE(table_name, '_TRL', '');
            BEGIN
                -- Reset query output
                query_set_template := '';
                FOR v_columnname IN SELECT columnname FROM ad_column WHERE istranslated = 'Y' AND ad_table_id = get_table_id(base_table)
                    LOOP
                        query_set_template := query_set_template || FORMAT('%s = x.%s ,', v_columnname, v_columnname);
                    END LOOP;

                query_set_template := SUBSTRING(query_set_template, 1, LENGTH(query_set_template) - 1);

                FOR base_table_id IN EXECUTE 'SELECT ' || base_table || '_ID FROM ' || base_table
                    LOOP
                        BEGIN
                            final_update_query := FORMAT(REPLACE(update_query_template, 'base_table', base_table), base_table_id::varchar, destination_language, query_set_template);
                            EXECUTE final_update_query;
                        EXCEPTION
                            WHEN OTHERS THEN
                                RAISE WARNING 'Execution Error : % ,  SQL= %', SQLERRM, final_update_query;
                        END;
                    END LOOP;
            END;

        END LOOP;
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION reset_system_base_language(VARCHAR, VARCHAR) IS 'Change/Reset the system base language  given the old ad_language as first param and destination ad_language as second param.
Example:  SELECT reset_system_base_language(''de_DE'', ''en_US'')'
;

