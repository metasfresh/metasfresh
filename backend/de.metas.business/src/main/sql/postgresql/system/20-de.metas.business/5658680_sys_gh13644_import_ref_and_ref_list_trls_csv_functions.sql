DO
$$
    BEGIN

        PERFORM backup_table('ad_ref_list_trl');
        PERFORM backup_table('ad_reference_trl');

        UPDATE ad_ref_list_trl
        SET name        = data.name_trl
          , description = data.description_trl
        FROM migration_data."AD_Ref_List_Trad" data
        WHERE ad_ref_list_trl.ad_ref_list_id = data.ad_ref_list_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;

        UPDATE ad_reference_trl
        SET name        = data.name_trl
          , description = data.description_trl
        FROM migration_data."AD_Reference_trad" data
        WHERE ad_reference_trl.ad_reference_id = data.ad_reference_id
          AND ad_language IN ('fr_CH', 'fr_FR')
          AND LENGTH(TRIM(data.name_trl)) > 0;

    END;
$$
;

