DO
$$
    BEGIN
        
        PERFORM backup_table('AD_Message_Trl');
        
        UPDATE ad_message_trl
        SET msgtext   = data.msgtext_trl
          , msgtip    = data.msgtip_trl
          , updated   = NOW()
          , updatedby = 99
        FROM migration_data.ad_message_trad data
        WHERE ad_message_trl.ad_message_id = data.ad_message_id
          AND LENGTH(TRIM(data.msgtext_trl)) > 0
          AND ad_message_trl.ad_language IN ('fr_CH', 'fr_FR');

    END;
$$
;
