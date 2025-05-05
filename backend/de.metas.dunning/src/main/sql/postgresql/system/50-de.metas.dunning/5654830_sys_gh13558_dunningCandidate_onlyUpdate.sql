-- 2022-09-05T09:10:15.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,540268,542297,30,'AD_Org_ID',TO_TIMESTAMP('2022-09-05 12:10:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.dunning',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',20,TO_TIMESTAMP('2022-09-05 12:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-05T09:10:15.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542297 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-09-05T09:13:27.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581415,0,'IsOnlyUpdate',TO_TIMESTAMP('2022-09-05 12:13:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dunning','Y','Only update','Only update',TO_TIMESTAMP('2022-09-05 12:13:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-05T09:13:27.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581415 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-05T09:15:30.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581415,0,540268,542298,20,'IsOnlyUpdate',TO_TIMESTAMP('2022-09-05 12:15:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dunning',0,'Y','N','Y','N','Y','N','Only update',30,TO_TIMESTAMP('2022-09-05 12:15:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-05T09:15:30.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542298 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-09-05T09:16:06.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2022-09-05 12:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542297
;

-- 2022-09-05T09:16:19.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2022-09-05 12:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542298
;

-- 2022-09-05T09:16:49.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=15,Updated=TO_TIMESTAMP('2022-09-05 12:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542298
;

-- 2022-09-06T17:19:56.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=130,Updated=TO_TIMESTAMP('2022-09-06 20:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542297
;

-- 2022-09-06T18:13:30.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nur aktualisieren', PrintName='Nur aktualisieren',Updated=TO_TIMESTAMP('2022-09-06 21:13:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581415 AND AD_Language='de_CH'
;

-- 2022-09-06T18:13:30.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581415,'de_CH') 
;

-- 2022-09-06T18:13:33.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nur aktualisieren', PrintName='Nur aktualisieren',Updated=TO_TIMESTAMP('2022-09-06 21:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581415 AND AD_Language='de_DE'
;

-- 2022-09-06T18:13:33.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581415,'de_DE') 
;

-- 2022-09-06T18:13:33.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581415,'de_DE') 
;

-- 2022-09-06T18:13:33.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsOnlyUpdate', Name='Nur aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID=581415
;

-- 2022-09-06T18:13:33.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyUpdate', Name='Nur aktualisieren', Description=NULL, Help=NULL, AD_Element_ID=581415 WHERE UPPER(ColumnName)='ISONLYUPDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-06T18:13:33.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsOnlyUpdate', Name='Nur aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID=581415 AND IsCentrallyMaintained='Y'
;

-- 2022-09-06T18:13:33.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nur aktualisieren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581415) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581415)
;

-- 2022-09-06T18:13:33.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nur aktualisieren', Name='Nur aktualisieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581415)
;

-- 2022-09-06T18:13:33.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nur aktualisieren', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581415
;

-- 2022-09-06T18:13:33.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nur aktualisieren', Description=NULL, Help=NULL WHERE AD_Element_ID = 581415
;

-- 2022-09-06T18:13:33.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nur aktualisieren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581415
;

-- 2022-09-06T18:13:36.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nur aktualisieren', PrintName='Nur aktualisieren',Updated=TO_TIMESTAMP('2022-09-06 21:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581415 AND AD_Language='nl_NL'
;

-- 2022-09-06T18:13:36.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581415,'nl_NL') 
;

-- 2022-09-07T13:08:13.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=550586
;

-- 2022-09-07T13:08:13.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=550586
;

-- 2022-09-07T13:08:13.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=550586
;

-- 2022-09-07T13:08:13.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Dunning_Candidate','ALTER TABLE C_Dunning_Candidate DROP COLUMN IF EXISTS IsStaled')
;

-- 2022-09-07T13:08:13.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=547342
;

DELETE FROM AD_Field WHERE AD_Column_ID=547342;

-- 2022-09-07T13:08:13.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=547342
;

-- 2022-09-07T13:37:06.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540390
;

-- 2022-09-07T13:37:06.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540390
;

DROP INDEX IF EXISTS c_dunning_candidate_recompute_c_dunning_candidate;

DROP TABLE IF EXISTS c_dunning_candidate_recompute;
