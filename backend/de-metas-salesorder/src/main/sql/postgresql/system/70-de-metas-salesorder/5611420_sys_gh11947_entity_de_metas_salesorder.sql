DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM ad_migrationscript WHERE name ILIKE '%5611420_sys_gh11947_entity_de_metas_salesorder.sql')
        THEN

-- 2021-11-02T13:29:13.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID, AD_EntityType_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, IsDisplayed, Name, Processing, Updated, UpdatedBy) VALUES (0, 540273, 0, TO_TIMESTAMP('2021-11-02 15:29:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.salesorder', 'Y', 'Y', 'de.metas.salesorder', 'N', TO_TIMESTAMP('2021-11-02 15:29:13', 'YYYY-MM-DD HH24:MI:SS'), 100);

        END IF;
    END;
$$
;
