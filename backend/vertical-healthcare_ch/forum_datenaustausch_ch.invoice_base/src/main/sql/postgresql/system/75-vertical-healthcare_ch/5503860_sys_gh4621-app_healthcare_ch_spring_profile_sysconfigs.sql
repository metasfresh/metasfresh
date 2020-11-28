-- unrelated - fix description
-- 2018-10-17T07:50:25.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This is a template for a sysconfig entry that activates a particular spring profile for the app.
To activate a profile via AD_SysConfig, just 
* first copy this template, reactivate it and replace the N with something meaningful (there can be multiple sysconfigs with the prefix de.metas.spring.profiles.active)
* then set as value the profile name that shall be active
* finally, restart',Updated=TO_TIMESTAMP('2018-10-17 07:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541237
;

--add inactive records if they don't exist

-- 2018-10-17T08:16:12.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,Description,AD_Org_ID,Name,EntityType) 
--VALUES (
SELECT 0,TO_TIMESTAMP('2018-10-17 08:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','S',TO_TIMESTAMP('2018-10-17 08:16:11','YYYY-MM-DD HH24:MI:SS'),100,541243,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Descides if the de.metas.vertical.healthcare.forum_datenaustausch_ch spring profile is active. within the app server
Changes need restart',0,'de.metas.spring.profiles.active1_forum_datenaustausch_ch','de.metas.vertical.healthcare.forum_datenaustausch_ch'
WHERE NOT EXISTS (select 1 from AD_SysConfig where AD_SysConfig_ID=541243)
--)
;

-- 2018-10-17T08:32:46.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET IsActive='N', Description='Decides if the de.metas.vertical.healthcare.forum_datenaustausch_ch spring profile is active within the app server.
A restart is needed after having changed this.',Updated=TO_TIMESTAMP('2018-10-17 08:32:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541243
;

-- 2018-10-17T08:43:56.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,Created,CreatedBy,IsActive,ConfigurationLevel,Updated,UpdatedBy,AD_SysConfig_ID,Value,Description,AD_Org_ID,Name,EntityType) 
--VALUES (
SELECT 0,TO_TIMESTAMP('2018-10-17 08:43:56','YYYY-MM-DD HH24:MI:SS'),100,'N','S',TO_TIMESTAMP('2018-10-17 08:43:56','YYYY-MM-DD HH24:MI:SS'),100,541245,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Decides if the de.metas.vertical.healthcare.forum_datenaustausch_ch spring profile is active within the webui-api server. 
A restart is needed after having changed this.',0,'de.metas.ui.web.spring.profiles.active1_forum_datenaustausch_ch','de.metas.vertical.healthcare.forum_datenaustausch_ch'
WHERE NOT EXISTS (select 1 from AD_SysConfig where AD_SysConfig_ID=541245)
--)
;

