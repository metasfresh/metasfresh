-- 2022-12-15T14:53:15.330Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541585,'S',TO_TIMESTAMP('2022-12-15 16:53:14','YYYY-MM-DD HH24:MI:SS'),100,'Max time to wait when API rate limit is exceeded.','D','Y','de.metas.marketing.activecampaign.restapi.maxSecondsToWaitForLimitReset',TO_TIMESTAMP('2022-12-15 16:53:14','YYYY-MM-DD HH24:MI:SS'),100,'3600')
;


-- 2022-12-16T06:35:21.417Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540717,0,542274,TO_TIMESTAMP('2022-12-16 08:35:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','Y','Y','UC_MKTG_ActiveCampaign_Config','N',TO_TIMESTAMP('2022-12-16 08:35:21','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2022-12-16T06:35:21.426Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540717 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-12-16T06:35:37.780Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,585318,541294,540717,0,TO_TIMESTAMP('2022-12-16 08:35:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','Y',10,TO_TIMESTAMP('2022-12-16 08:35:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-16T06:35:43.717Z
CREATE UNIQUE INDEX UC_MKTG_ActiveCampaign_Config ON MKTG_ActiveCampaign_Config (MKTG_ActiveCampaign_Config_ID) WHERE IsActive='Y'
;

-- 2022-12-16T06:36:46.303Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540718,0,542274,TO_TIMESTAMP('2022-12-16 08:36:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','Y','N','UC_MKTG_ActiveCampaign_Config_API','N',TO_TIMESTAMP('2022-12-16 08:36:46','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2022-12-16T06:36:46.304Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540718 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-12-16T06:36:57.481Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,585321,541295,540718,0,TO_TIMESTAMP('2022-12-16 08:36:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','Y',10,TO_TIMESTAMP('2022-12-16 08:36:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-16T06:37:06.014Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,585320,541296,540718,0,TO_TIMESTAMP('2022-12-16 08:37:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','Y',20,TO_TIMESTAMP('2022-12-16 08:37:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-16T06:37:17.628Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,585312,541297,540718,0,TO_TIMESTAMP('2022-12-16 08:37:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','Y',30,TO_TIMESTAMP('2022-12-16 08:37:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-16T06:37:18.461Z
CREATE INDEX UC_MKTG_ActiveCampaign_Config_API ON MKTG_ActiveCampaign_Config (ApiKey,BaseURL,AD_Org_ID) WHERE IsActive='Y'
;


-- 2022-12-16T11:05:56.049Z
UPDATE AD_Index_Column SET AD_Column_ID=585319,Updated=TO_TIMESTAMP('2022-12-16 13:05:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541294
;

-- 2022-12-16T11:05:57.352Z
DROP INDEX IF EXISTS uc_mktg_activecampaign_config
;

-- 2022-12-16T11:05:57.371Z
CREATE UNIQUE INDEX UC_MKTG_ActiveCampaign_Config ON MKTG_ActiveCampaign_Config (MKTG_Platform_ID) WHERE IsActive='Y'
;


