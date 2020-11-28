-- 2018-10-15T16:43:54.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,Description,AD_Org_ID,AD_Table_ID,Name,EntityType,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation) VALUES (0,'1',0,100,'Y',TO_TIMESTAMP('2018-10-15 16:43:54','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-15 16:43:54','YYYY-MM-DD HH24:MI:SS'),'N','Y','N','N','N','N','L','N',0,100,'N','N','Cofigs for http://www.forum-datenaustausch.ch',0,541145,'HC_Forum_Datenaustausch','de.metas.vertical.healthcare.forum_datenaustausch_ch','HC_Forum_Datenaustausch','NP','N')
;

-- 2018-10-15T16:43:54.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=541145 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-10-15T16:43:54.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (CurrentNext,IsAudited,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,Description,AD_Client_ID,Name,AD_Org_ID) VALUES (1000000,'N','N','Y','Y',TO_TIMESTAMP('2018-10-15 16:43:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2018-10-15 16:43:54','YYYY-MM-DD HH24:MI:SS'),100,554727,'Table HC_Forum_Datenaustausch',0,'HC_Forum_Datenaustausch',0)
;

-- 2018-10-15T16:44:03.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,Description,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','Y','N',TO_TIMESTAMP('2018-10-15 16:44:02','YYYY-MM-DD HH24:MI:SS'),100,541145,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',563274,'Y','Mandant für diese Installation.',0,102,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Mandant','AD_Client_ID')
;

-- 2018-10-15T16:44:03.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563274 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:44:03.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,Description,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Y','N','Y','N',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,541145,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',563275,'Y','Organisatorische Einheit des Mandanten',0,113,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Sektion','AD_Org_ID')
;

-- 2018-10-15T16:44:03.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563275 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:44:03.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,Description,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,541145,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',563276,'Y','Datum, an dem dieser Eintrag erstellt wurde',0,245,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Erstellt','Created')
;

-- 2018-10-15T16:44:03.333
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563276 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:44:03.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,Description,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,541145,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,563277,'Y','Nutzer, der diesen Eintrag erstellt hat',0,246,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Erstellt durch','CreatedBy')
;

-- 2018-10-15T16:44:03.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563277 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:44:03.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,Description,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','N',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,541145,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',563278,'Y','Der Eintrag ist im System aktiv',0,348,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Aktiv','IsActive')
;

-- 2018-10-15T16:44:03.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563278 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:44:03.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,Description,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,541145,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',563279,'Y','Datum, an dem dieser Eintrag aktualisiert wurde',0,607,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Aktualisiert','Updated')
;

-- 2018-10-15T16:44:03.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563279 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:44:03.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,Description,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,541145,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,563280,'Y','Nutzer, der diesen Eintrag aktualisiert hat',0,608,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Aktualisiert durch','UpdatedBy')
;

-- 2018-10-15T16:44:03.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563280 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:44:03.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,Name,ColumnName,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'HC_Forum_Datenaustausch',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,544450,0,'HC_Forum_Datenaustausch','HC_Forum_Datenaustausch_ID','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:44:03.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PrintName,PO_Description,PO_Help,PO_Name,PO_PrintName,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544450 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-15T16:44:04.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,Name,ColumnName) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N',TO_TIMESTAMP('2018-10-15 16:44:03','YYYY-MM-DD HH24:MI:SS'),100,541145,563281,'Y',0,544450,'de.metas.vertical.healthcare.forum_datenaustausch_ch','HC_Forum_Datenaustausch','HC_Forum_Datenaustausch_ID')
;

-- 2018-10-15T16:44:04.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563281 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:45:14.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,Name,ColumnName,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-10-15 16:45:14','YYYY-MM-DD HH24:MI:SS'),100,'Export version',TO_TIMESTAMP('2018-10-15 16:45:14','YYYY-MM-DD HH24:MI:SS'),100,544451,0,'Export version','ExportVersion','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:45:14.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PrintName,PO_Description,PO_Help,PO_Name,PO_PrintName,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544451 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-15T16:45:39.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Export XML Version', Name='Export XML Version', ColumnName='ExportXmlVersion',Updated=TO_TIMESTAMP('2018-10-15 16:45:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544451
;

-- 2018-10-15T16:45:39.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExportXmlVersion', Name='Export XML Version', Description=NULL, Help=NULL WHERE AD_Element_ID=544451
;

-- 2018-10-15T16:45:39.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExportXmlVersion', Name='Export XML Version', Description=NULL, Help=NULL, AD_Element_ID=544451 WHERE UPPER(ColumnName)='EXPORTXMLVERSION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-15T16:45:39.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExportXmlVersion', Name='Export XML Version', Description=NULL, Help=NULL WHERE AD_Element_ID=544451 AND IsCentrallyMaintained='Y'
;

-- 2018-10-15T16:45:39.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Export XML Version', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544451) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544451)
;

-- 2018-10-15T16:45:39.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Export XML Version', Name='Export XML Version' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544451)
;

-- 2018-10-15T16:45:54.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-15 16:45:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Export XML Version',PrintName='Export XML Version' WHERE AD_Element_ID=544451 AND AD_Language='de_CH'
;

-- 2018-10-15T16:45:54.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544451,'de_CH') 
;

-- 2018-10-15T16:46:07.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-15 16:46:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Export XML version',PrintName='Export XML version' WHERE AD_Element_ID=544451 AND AD_Language='en_US'
;

-- 2018-10-15T16:46:07.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544451,'en_US') 
;

-- 2018-10-15T16:46:44.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-10-15 16:46:44','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-15 16:46:44','YYYY-MM-DD HH24:MI:SS'),100,540921,'L','ExportXmlVersion',0,'de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:46:44.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540921 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-10-15T16:47:45.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,Value,EntityType) VALUES (540921,0,'Y',TO_TIMESTAMP('2018-10-15 16:47:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-15 16:47:45','YYYY-MM-DD HH24:MI:SS'),100,541748,'v440',0,'4.4','4.4','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:47:45.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541748 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-15T16:48:14.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (17,5,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-15 16:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-15 16:48:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541145,'N',540921,563282,'N','Y','N','N','N','N','N','N',0,0,544451,'de.metas.vertical.healthcare.forum_datenaustausch_ch','N','N','Export XML Version','ExportXmlVersion')
;

-- 2018-10-15T16:48:14.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563282 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-15T16:50:31.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ CREATE TABLE public.HC_Forum_Datenaustausch (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExportXmlVersion VARCHAR(5) NOT NULL, HC_Forum_Datenaustausch_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT HC_Forum_Datenaustausch_Key PRIMARY KEY (HC_Forum_Datenaustausch_ID))
--;

-- 2018-10-15T16:51:32.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,IsActive,Created,CreatedBy,WindowType,Processing,IsSOTrx,WinHeight,WinWidth,IsBetaFunctionality,IsDefault,Updated,UpdatedBy,IsOneInstanceOnly,AD_Window_ID,Help,InternalName,AD_Org_ID,Name,EntityType,IsEnableRemoteCacheInvalidation) VALUES (0,'Y',TO_TIMESTAMP('2018-10-15 16:51:31','YYYY-MM-DD HH24:MI:SS'),100,'M','N','Y',0,0,'N','N',TO_TIMESTAMP('2018-10-15 16:51:31','YYYY-MM-DD HH24:MI:SS'),100,'N',540490,'http://www.forum-datenaustausch.ch','HC_Forum_Datenaustausch',0,'Forum Datenaustausch','de.metas.vertical.healthcare.forum_datenaustausch_ch','N')
;

-- 2018-10-15T16:51:32.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540490 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2018-10-15T16:52:01.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,AD_Org_ID,Name,EntityType) VALUES (TO_TIMESTAMP('2018-10-15 16:52:01','YYYY-MM-DD HH24:MI:SS'),'N',540490,10,'N',0,TO_TIMESTAMP('2018-10-15 16:52:01','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N','N',0,'Y','N','N','Y','Y','Y','N',541145,541327,'N','Y',0,0,'Konfig','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:52:01.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=541327 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2018-10-15T16:52:16.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='HC_Forum_Datenaustausch_Config', TableName='HC_Forum_Datenaustausch_Config',Updated=TO_TIMESTAMP('2018-10-15 16:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541145
;

-- 2018-10-15T16:52:16.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='HC_Forum_Datenaustausch_Config',Updated=TO_TIMESTAMP('2018-10-15 16:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554727
;

-- 2018-10-15T16:52:23.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2018-10-15 16:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541145
;

-- 2018-10-15T16:56:59.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.HC_Forum_Datenaustausch_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExportXmlVersion VARCHAR(5) NOT NULL, HC_Forum_Datenaustausch_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT HC_Forum_Datenaustausch_Config_Key PRIMARY KEY (HC_Forum_Datenaustausch_ID))
;

-- 2018-10-15T16:58:35.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540490,Updated=TO_TIMESTAMP('2018-10-15 16:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541145
;

-- 2018-10-15T16:58:44.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (541327,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-15 16:58:44','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-15 16:58:44','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',569317,'N',563274,'Mandant für diese Installation.',0,'Mandant','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:58:44.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-15T16:58:44.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (541327,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-15 16:58:44','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-15 16:58:44','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',569318,'N',563275,'Organisatorische Einheit des Mandanten',0,'Sektion','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:58:44.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-15T16:58:45.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (541327,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-15 16:58:44','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-15 16:58:44','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',569319,'N',563278,'Der Eintrag ist im System aktiv',0,'Aktiv','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:58:45.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-15T16:58:45.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (541327,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-15 16:58:45','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-15 16:58:45','YYYY-MM-DD HH24:MI:SS'),100,569320,'N',563281,0,'HC_Forum_Datenaustausch','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:58:45.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-15T16:58:45.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (541327,'N',5,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-15 16:58:45','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-15 16:58:45','YYYY-MM-DD HH24:MI:SS'),100,569321,'N',563282,0,'Export XML Version','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T16:58:45.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-15T16:59:01.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-10-15 16:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569319
;

-- 2018-10-15T16:59:08.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2018-10-15 16:59:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569321
;

-- 2018-10-15T17:00:45.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (AD_Window_ID,Action,AD_Client_ID,CreatedBy,IsActive,Created,IsSummary,IsSOTrx,IsReadOnly,Updated,UpdatedBy,AD_Menu_ID,IsCreateNew,InternalName,AD_Org_ID,Name,EntityType) VALUES (540490,'W',0,100,'Y',TO_TIMESTAMP('2018-10-15 17:00:44','YYYY-MM-DD HH24:MI:SS'),'N','N','N',TO_TIMESTAMP('2018-10-15 17:00:44','YYYY-MM-DD HH24:MI:SS'),100,541145,'N','HC_Forum_Datenaustausch',0,'Forum Datenaustausch','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-15T17:00:45.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541145 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-10-15T17:00:45.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541145, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541145)
;

-- 2018-10-15T17:00:45.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000069, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540996 AND AD_Tree_ID=10
;

-- 2018-10-15T17:00:45.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000069, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541009 AND AD_Tree_ID=10
;

-- 2018-10-15T17:00:45.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000069, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541145 AND AD_Tree_ID=10
;

-- 2018-10-15T17:01:03.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000077, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540998 AND AD_Tree_ID=10
;

-- 2018-10-15T17:01:03.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000077, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541145 AND AD_Tree_ID=10
;

-- 2018-10-15T17:01:03.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000077, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540825 AND AD_Tree_ID=10
;

-- 2018-10-15T17:01:06.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000077, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540998 AND AD_Tree_ID=10
;

-- 2018-10-15T17:01:06.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000077, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540825 AND AD_Tree_ID=10
;

-- 2018-10-15T17:01:06.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000077, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541145 AND AD_Tree_ID=10
;

-- 2018-10-15T17:06:26.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JavaClass_Type (Created,CreatedBy,IsActive,AD_JavaClass_Type_ID,Updated,UpdatedBy,Classname,AD_Client_ID,InternalName,AD_Org_ID,Name,EntityType) VALUES (TO_TIMESTAMP('2018-10-15 17:06:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',540041,TO_TIMESTAMP('2018-10-15 17:06:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter',0,'de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverte',0,'Forum Datenaustausch invoice request converter','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-16T06:39:52.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_EntityType SET ModelPackage='de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model',Updated=TO_TIMESTAMP('2018-10-16 06:39:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_EntityType_ID=540237
;

-- 2018-10-16T06:45:54.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-16 06:45:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','Y','Y','N',TO_TIMESTAMP('2018-10-16 06:45:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541145,'N','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',563283,'N','N','N','N','N','N','N','N','Bezeichnet einen Geschäftspartner',0,0,187,'de.metas.vertical.healthcare.forum_datenaustausch_ch','N','N','Geschäftspartner','C_BPartner_ID')
;

-- 2018-10-16T06:45:54.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-16T06:45:57.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('HC_Forum_Datenaustausch_Config','ALTER TABLE public.HC_Forum_Datenaustausch_Config ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2018-10-16T06:45:57.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE HC_Forum_Datenaustausch_Config ADD CONSTRAINT CBPartner_HCForumDatenaustauschConfig FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2018-10-16T07:06:52.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (CreatedBy,Processing,Created,AD_Client_ID,IsActive,IsUnique,WhereClause,AD_Table_ID,Updated,UpdatedBy,AD_Index_Table_ID,AD_Org_ID,Name,EntityType) VALUES (100,'N',TO_TIMESTAMP('2018-10-16 07:06:52','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y','IsAyctive=''Y''',541145,TO_TIMESTAMP('2018-10-16 07:06:52','YYYY-MM-DD HH24:MI:SS'),100,540453,0,'HC_Forum_Datenaustausch_Config_UC','de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-16T07:06:52.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540453 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-10-16T07:07:32.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,ColumnSQL,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2018-10-16 07:07:32','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-16 07:07:32','YYYY-MM-DD HH24:MI:SS'),0,540453,'Y',563283,10,100,540896,'COLALESCE(C_BPartner_ID,0)',0,'de.metas.vertical.healthcare.forum_datenaustausch_ch')
;

-- 2018-10-16T07:07:41.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2018-10-16 07:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540453
;

-- 2018-10-16T07:07:56.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='COALESCE(C_BPartner_ID,0)',Updated=TO_TIMESTAMP('2018-10-16 07:07:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540896
;

-- 2018-10-16T07:07:58.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX HC_Forum_Datenaustausch_Config_UC ON HC_Forum_Datenaustausch_Config (COALESCE(C_BPartner_ID,0)) WHERE IsActive='Y'
;

-- 2018-10-16T08:21:33.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-16 08:21:33','YYYY-MM-DD HH24:MI:SS'),Description='',Help='' WHERE AD_Element_ID=3020 AND AD_Language='fr_CH'
;

-- 2018-10-16T08:21:33.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(3020,'fr_CH') 
;

-- 2018-10-16T08:21:48.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=3020 AND AD_Language='fr_CH'
;

-- 2018-10-16T08:21:48.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=3020 AND AD_Language='it_CH'
;

-- 2018-10-16T08:21:48.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=3020 AND AD_Language='en_GB'
;

-- 2018-10-16T08:21:54.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-16 08:21:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='',Help='' WHERE AD_Element_ID=3020 AND AD_Language='de_CH'
;

-- 2018-10-16T08:21:54.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(3020,'de_CH') 
;

-- 2018-10-16T08:21:59.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-16 08:21:59','YYYY-MM-DD HH24:MI:SS'),Name='Status' WHERE AD_Element_ID=3020 AND AD_Language='en_US'
;

-- 2018-10-16T08:21:59.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(3020,'en_US') 
;

-- 2018-10-16T08:34:04.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_JavaClass_Type WHERE AD_JavaClass_Type_ID=540041
;

-- 2018-10-16T08:38:49.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_AttachmentEntry_ID IN (
	select r.AD_AttachmentEntry_ID 
	from AD_Attachment_MultiRef r 
	where r.AD_Table_ID=get_Table_ID(''C_DataImport'') and r.Record_ID=@C_DataImport_ID@ )',Updated=TO_TIMESTAMP('2018-10-16 08:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540389
;

