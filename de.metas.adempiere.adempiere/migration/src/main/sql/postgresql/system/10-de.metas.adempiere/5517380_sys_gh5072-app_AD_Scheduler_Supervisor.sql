-- 2019-03-26T22:14:51.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540401,Updated=TO_TIMESTAMP('2019-03-26 22:14:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=11249
;

-- 2019-03-26T22:18:47.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576303,0,TO_TIMESTAMP('2019-03-26 22:18:47','YYYY-MM-DD HH24:MI:SS'),100,'Benutzer, mit dessen Rechten der Prozess ausgeführt wird, und der bei Problemen benachrichtigt wird, falls SysConfig org.compiere.server.Scheduler.notifyOnNotOK=Y','D','Y','Ausführen als','Ausführen als',TO_TIMESTAMP('2019-03-26 22:18:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T22:18:47.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576303 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-26T22:18:59.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=603411
;

-- 2019-03-26T22:18:59.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1522,628239,9432,0,305,TO_TIMESTAMP('2019-03-26 22:18:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-26 22:18:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T22:19:05.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576303, Description='Benutzer, mit dessen Rechten der Prozess ausgeführt wird, und der bei Problemen benachrichtigt wird, falls SysConfig org.compiere.server.Scheduler.notifyOnNotOK=Y', Name='Ausführen als',Updated=TO_TIMESTAMP('2019-03-26 22:19:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9432
;

-- 2019-03-26T22:19:05.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=628239
;

-- 2019-03-26T22:19:05.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,576303,628240,9432,0,305,TO_TIMESTAMP('2019-03-26 22:19:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-03-26 22:19:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T22:19:05.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576303) 
;

