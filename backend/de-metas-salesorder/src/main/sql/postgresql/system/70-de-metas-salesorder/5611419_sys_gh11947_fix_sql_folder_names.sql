
-- on many exiting systems, these three files were added with projectname='${migration-sql-basedir}' 
-- only now i added them to a proper folder


DO
$$
    BEGIN
        IF EXISTS(SELECT 1 FROM ad_migrationscript WHERE name = '${migration-sql-basedir}->5611420_sys_gh11947_entity_de_metas_salesorder.sql')
        THEN
            PERFORM migrationscript_ignore('70-de-metas-salesorder/5611420_sys_gh11947_entity_de_metas_salesorder.sql');
        END IF;

        IF EXISTS(SELECT 1 FROM ad_migrationscript WHERE name = '${migration-sql-basedir}->5611420_sys_gh11947_entity_de_metas_salesorder.sql')
        THEN
            PERFORM migrationscript_ignore('70-de-metas-salesorder/5611420_sys_gh11947_entity_de_metas_salesorder.sql');
        END IF;

        IF EXISTS(SELECT 1 FROM ad_migrationscript WHERE name = '${migration-sql-basedir}->5612270_sys_gh11947_wp_ProcessOLCandsWorkpackageProcessor.sql')
        THEN
            PERFORM migrationscript_ignore('70-de-metas-salesorder/5612270_sys_gh11947_wp_ProcessOLCandsWorkpackageProcessor.sql');
        END IF;
    END;
$$
;
