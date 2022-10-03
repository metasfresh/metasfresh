DO
$$
    BEGIN

        PERFORM backup_table('AD_Form_Trl');

        UPDATE AD_Form_Trl
        SET name        = data.name_trl
          , description = data.description_trl
          , help        = data.help_trl
          , updated     = NOW()
          , updatedby   = 99
        FROM migration_data."AD_Form_trad" DATA
        WHERE ad_form_trl.ad_form_id = data.ad_form_id
          AND LENGTH(TRIM(data.name_trl)) > 0
          AND ad_form_trl.ad_language IN ('fr_CH', 'fr_FR');
    END;
$$
;

