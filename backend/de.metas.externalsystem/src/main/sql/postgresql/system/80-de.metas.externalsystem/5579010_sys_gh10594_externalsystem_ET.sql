
-- 2021-02-12T11:09:16.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540267,0,TO_TIMESTAMP('2021-02-12 12:09:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Y','de.metas.externalsystem.model','de.metas.externalsystem','N',TO_TIMESTAMP('2021-02-12 12:09:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- switch the entitype to 'de.metas.externalsystem' for now, so we can have the model classes in here
update AD_Table set entitytype='de.metas.externalsystem' where ad_Table_id=541576; -- ExternalSystem_Config
update AD_Column set entitytype='de.metas.externalsystem' where ad_Table_id=541576; -- ExternalSystem_Config

update AD_Table set entitytype='de.metas.externalsystem' where ad_Table_id=541577; -- ExternalSystem_Config_Alberta
update AD_Column set entitytype='de.metas.externalsystem' where ad_Table_id=541577; -- ExternalSystem_Config_Alberta
