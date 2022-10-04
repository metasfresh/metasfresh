DO
$$
    BEGIN

        PERFORM backup_table('AD_Process_Trl');
        PERFORM backup_table('AD_Process_Para_Trl');

        UPDATE ad_process_trl
        SET name        = data.name_trl
          , description = data.description_trl
        FROM migration_data."AD_Process_trad" data
        WHERE ad_process_trl.ad_process_id = data.ad_process_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;

        UPDATE ad_process_para_trl
        SET name        = data.name_trl
          , description = data.description_trl
          , help        = data.help_trl
        FROM migration_data."AD_Process_para_trad" data
        WHERE ad_process_para_trl.ad_process_para_id = data.ad_process_para_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;

    END;
$$
;
