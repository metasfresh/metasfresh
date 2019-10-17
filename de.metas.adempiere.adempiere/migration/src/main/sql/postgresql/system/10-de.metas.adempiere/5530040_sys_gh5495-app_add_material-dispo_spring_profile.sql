-- 2019-09-06T13:28:32.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541286,'S',TO_TIMESTAMP('2019-09-06 15:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Activate this to run the material-dispo service within the metasfresh-app service.
Alternatively, you can run material-dispo as standalone service (we have a docker image!), or depending on your requirements you might decide not to run it at all.
','de.metas.material.dispo','Y','de.metas.spring.profiles.active_material-dispo',TO_TIMESTAMP('2019-09-06 15:28:32','YYYY-MM-DD HH24:MI:SS'),100,'material-dispo')
;

-- 2019-09-06T13:28:40.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET IsActive='Y',Updated=TO_TIMESTAMP('2019-09-06 15:28:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541286
;
