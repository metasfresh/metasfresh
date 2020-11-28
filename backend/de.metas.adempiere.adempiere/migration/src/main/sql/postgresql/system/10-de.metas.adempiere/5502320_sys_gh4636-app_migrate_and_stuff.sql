-- 2018-09-28T21:17:31.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,AD_Org_ID,AD_Table_ID,Name,EntityType,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation) VALUES (0,'3',0,100,'Y',TO_TIMESTAMP('2018-09-28 21:17:31','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-09-28 21:17:31','YYYY-MM-DD HH24:MI:SS'),'N','N','N','Y','N','N','L','N',0,100,'N','N',0,541144,'AD_AttachmentEntry_ReferencedRecord_v','D','AD_AttachmentEntry_ReferencedRecord_v','NP','N')
;

-- 2018-09-28T21:17:31.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=541144 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-09-28T21:18:41.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='AD_AttachmentEntry',Updated=TO_TIMESTAMP('2018-09-28 21:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540833
;

-- 2018-09-28T21:18:58.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,PrintName,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,Name,ColumnName,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2018-09-28 21:18:58','YYYY-MM-DD HH24:MI:SS'),100,'AD_AttachmentEntry_ReferencedRecord_v',TO_TIMESTAMP('2018-09-28 21:18:58','YYYY-MM-DD HH24:MI:SS'),100,544432,0,'AD_AttachmentEntry_ReferencedRecord_v','AD_AttachmentEntry_ReferencedRecord_v_ID','D')
;

-- 2018-09-28T21:18:58.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PrintName,PO_Description,PO_Help,PO_Name,PO_PrintName,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544432 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-28T21:18:59.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (13,22,1,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N',TO_TIMESTAMP('2018-09-28 21:18:58','YYYY-MM-DD HH24:MI:SS'),100,541144,'AD_AttachmentEntry_ReferencedRecord_v_ID',563223,'Y',0,'AD_AttachmentEntry_ReferencedRecord_v',544432,'D')
;

-- 2018-09-28T21:18:59.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563223 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:18:59.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (30,10,0,'N','Y','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'Eine "Anlage" kann jeder Dokumenten-/Datei-Typ sein und jedem Eintrag im System zugeordnet werden.','AD_Attachment_ID',563224,'N','Anlage zum Eintrag',0,'Anlage',101,'D')
;

-- 2018-09-28T21:18:59.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563224 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:18:59.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (19,'@#AD_Client_ID@',22,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','AD_Client_ID',563225,'Y','Mandant für diese Installation.',0,'Mandant',102,'D')
;

-- 2018-09-28T21:18:59.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563225 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:18:59.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Val_Rule_ID,AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (104,19,'@#AD_Org_ID@',22,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','AD_Org_ID',563226,'Y','Organisatorische Einheit des Mandanten',0,'Sektion',113,'D')
;

-- 2018-09-28T21:18:59.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563226 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:18:59.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (19,22,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'The Database Table provides the information of the table definition','AD_Table_ID',563227,'N','Database Table information',0,'DB-Tabelle',126,'D')
;

-- 2018-09-28T21:18:59.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563227 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:18:59.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (23,0,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'Das Feld "Binärwert" speichert binäre Werte.','BinaryData',563228,'N','Binärer Wert',0,'Binärwert',174,'D')
;

-- 2018-09-28T21:18:59.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563228 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:18:59.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (10,60,1,'N','N','N','N',1,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'ContentType',563229,'N',0,'Content type',543391,'D')
;

-- 2018-09-28T21:18:59.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563229 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:18:59.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (16,7,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Created',563230,'Y','Datum, an dem dieser Eintrag erstellt wurde',0,'Erstellt',245,'D')
;

-- 2018-09-28T21:18:59.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563230 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (18,22,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N',TO_TIMESTAMP('2018-09-28 21:18:59','YYYY-MM-DD HH24:MI:SS'),100,541144,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,'CreatedBy',563231,'Y','Nutzer, der diesen Eintrag erstellt hat',0,'Erstellt durch',246,'D')
;

-- 2018-09-28T21:19:00.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563231 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (14,2000,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'Description',563232,'N',0,'Beschreibung',275,'D')
;

-- 2018-09-28T21:19:00.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563232 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (10,2000,0,'N','N','N','Y',1,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','FileName',563233,'Y','Name of the local file or URL',0,'File Name',2295,'D')
;

-- 2018-09-28T21:19:00.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563233 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (20,'Y',1,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','IsActive',563234,'Y','Der Eintrag ist im System aktiv',0,'Aktiv',348,'D')
;

-- 2018-09-28T21:19:00.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563234 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (28,22,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Record_ID',563235,'N','Direct internal record ID',0,'Datensatz-ID',538,'D')
;

-- 2018-09-28T21:19:00.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563235 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (17,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'',540751,'Type',563236,'Y','',0,'Art',600,'D')
;

-- 2018-09-28T21:19:00.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563236 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (16,7,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Updated',563237,'Y','Datum, an dem dieser Eintrag aktualisiert wurde',0,'Aktualisiert',607,'D')
;

-- 2018-09-28T21:19:00.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563237 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:00.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (18,22,1,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,'UpdatedBy',563238,'Y','Nutzer, der diesen Eintrag aktualisiert hat',0,'Aktualisiert durch',608,'D')
;

-- 2018-09-28T21:19:00.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563238 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:19:01.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,Name,AD_Element_ID,EntityType) VALUES (10,2000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2018-09-28 21:19:00','YYYY-MM-DD HH24:MI:SS'),100,541144,'The URL defines an fully qualified web address like http://www.adempiere.org','URL',563239,'N','Full URL address - e.g. http://www.adempiere.org',0,'URL',983,'D')
;

-- 2018-09-28T21:19:01.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563239 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-28T21:28:43.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=563235, AD_Table_ID=541144,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540850
;

-- 2018-09-28T21:28:43.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563237,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559449
;

-- 2018-09-28T21:28:43.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563225,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559437
;

-- 2018-09-28T21:28:43.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563227,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559439
;

-- 2018-09-28T21:28:43.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563229,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559444
;

-- 2018-09-28T21:28:43.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563224,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559446
;

-- 2018-09-28T21:28:43.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563226,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559438
;

-- 2018-09-28T21:28:43.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563228,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559440
;

-- 2018-09-28T21:28:43.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563238,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559450
;

-- 2018-09-28T21:28:43.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563234,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559441
;

-- 2018-09-28T21:28:43.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563235,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559442
;

-- 2018-09-28T21:28:43.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563230,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559447
;

-- 2018-09-28T21:28:43.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563231,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559448
;

-- 2018-09-28T21:28:43.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563233,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559445
;

-- 2018-09-28T21:28:43.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Column_ID=563232,Updated=TO_TIMESTAMP('2018-09-28 21:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559443
;

-- 2018-09-28T21:28:43.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=559436
;

-- 2018-09-28T21:28:43.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=559436
;

DROP VIEW IF EXISTS AD_AttachmentEntry_ReferencedRecord_v;
CREATE VIEW AD_AttachmentEntry_ReferencedRecord_v AS
SELECT 
	r.AD_Table_ID, 
	r.Record_ID,
	r.AD_Attachment_MultiRef_ID AS AD_AttachmentEntry_ReferencedRecord_v_ID,
	r.AD_Client_ID,
	r.AD_Org_ID,
	e.AD_AttachmentEntry_ID,
	e.BinaryData,
	e.ContentType,
	LEAST(e.Created,r.Created) AS Created,
	CASE WHEN e.CreatedBy < r.CreatedBy THEN e.CreatedBy ELSE r.CreatedBy END AS CreatedBy,
	e.Description,
	e.FileName,
	CASE WHEN e.IsActive='Y' AND r.IsActive='Y' THEN 'Y' ELSE 'N' END AS IsActive,
	e.Type,
	GREATEST(r.Updated, e.Updated) AS Updated,
	CASE WHEN e.Updated > r.Updated THEN e.UpdatedBy ELSE r.UpdatedBy END AS UpdatedBy,
	e.URL
FROM AD_Attachment_MultiRef r
	JOIN AD_AttachmentEntry e ON e.AD_AttachmentEntry_ID=r.AD_AttachmentEntry_ID
;
-- 2018-09-28T21:43:35.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19, IsUpdateable='N', Help=NULL, ColumnName='AD_AttachmentEntry_ID', Description=NULL, Name='Attachment entry', AD_Element_ID=543390,Updated=TO_TIMESTAMP('2018-09-28 21:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563224
;

-- 2018-09-28T21:43:35.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Attachment entry', Description=NULL, Help=NULL WHERE AD_Column_ID=563224
;

-- 2018-09-28T21:59:43.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID=291',Updated=TO_TIMESTAMP('2018-09-28 21:59:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540850
;


DROP VIEW public.x_bpartner_history;

CREATE OR REPLACE VIEW public.x_bpartner_history AS
 SELECT r.c_bpartner_id,
    r.datetrx,
    r.documentno,
    r.summary AS description,
    rt.name AS typ,
    r.ad_client_id,
    r.ad_org_id,
    r.created,
    r.createdby,
    r.updated,
    r.updatedby,
    '417'::text AS ad_table_id,
    r.r_request_id AS record_id,
    (417 * 1000000)::numeric + r.r_request_id AS x_bpartner_history_id
   FROM r_request r
     LEFT JOIN r_requesttype rt ON rt.r_requesttype_id = r.r_requesttype_id
UNION
 SELECT o.c_bpartner_id,
    o.dateordered AS datetrx,
    o.documentno,
    o.description,
    dt.name AS typ,
    o.ad_client_id,
    o.ad_org_id,
    o.created,
    o.createdby,
    o.updated,
    o.updatedby,
    '259'::text AS ad_table_id,
    o.c_order_id AS record_id,
    (259 * 1000000)::numeric + o.c_order_id AS x_bpartner_history_id
   FROM c_order o
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = o.c_doctype_id
UNION
 SELECT i.c_bpartner_id,
    i.dateinvoiced AS datetrx,
    i.documentno,
    i.description,
    dt.name AS typ,
    i.ad_client_id,
    i.ad_org_id,
    i.created,
    i.createdby,
    i.updated,
    i.updatedby,
    '318'::text AS ad_table_id,
    i.c_invoice_id AS record_id,
    (318 * 1000000)::numeric + i.c_invoice_id AS x_bpartner_history_id
   FROM c_invoice i
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = i.c_doctype_id
UNION
 SELECT m.c_bpartner_id,
    m.movementdate AS datetrx,
    m.documentno,
    m.description,
    dt.name AS typ,
    m.ad_client_id,
    m.ad_org_id,
    m.created,
    m.createdby,
    m.updated,
    m.updatedby,
    '319'::text AS ad_table_id,
    m.m_inout_id AS record_id,
    (319 * 1000000)::numeric + m.m_inout_id AS x_bpartner_history_id
   FROM m_inout m
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = m.c_doctype_id
UNION
 SELECT a.record_id AS c_bpartner_id,
    a.created AS datetrx,
    a.ad_attachmententry_id::text AS documentno,
    a.filename AS description,
    'File'::character varying AS typ,
    a.ad_client_id,
    a.ad_org_id,
    a.created,
    a.createdby,
    a.updated,
    a.updatedby,
    a.ad_table_id::text AS ad_table_id,
    a.ad_attachmententry_id AS record_id,
    (291 * 1000000)::numeric + a.AD_AttachmentEntry_ReferencedRecord_v_id AS x_bpartner_history_id
   FROM AD_AttachmentEntry_ReferencedRecord_v a
  WHERE a.ad_table_id = 291::numeric;


 SELECT "de.metas.async".executesqlasync(
'
WITH migratedEntries as (
		INSERT INTO AD_Attachment_MultiRef (
			ad_attachment_multiref_id, -- numeric(10,0) NOT NULL,
			ad_client_id, -- numeric(10,0) NOT NULL,
			ad_org_id, -- numeric(10,0) NOT NULL,
			ad_table_id, -- numeric(10,0),
			created, -- timestamp with time zone NOT NULL,
			createdby, -- numeric(10,0) NOT NULL,
			isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL,
			record_id, -- numeric(10,0),
			updated, -- timestamp with time zone NOT NULL,
			updatedby, -- numeric(10,0) NOT NULL,
			ad_attachmententry_id -- numeric(10,0) NOT NULL
		)
		SELECT 
			nextval(''ad_attachment_multiref_seq''), -- numeric(10,0) NOT NULL,
			e.ad_client_id, -- numeric(10,0) NOT NULL,
			e.ad_org_id, -- numeric(10,0) NOT NULL,
			e.ad_table_id, -- numeric(10,0),
			now() AS created, -- timestamp with time zone NOT NULL,
			99 AS createdby, -- numeric(10,0) NOT NULL,
			e.isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL,
			e.record_id, -- numeric(10,0),
			now() AS updated, -- timestamp with time zone NOT NULL,
			99 AS updatedby, -- numeric(10,0) NOT NULL,
			e.ad_attachmententry_id -- numeric(10,0) NOT NULL
		FROM AD_AttachmentEntry e
		WHERE e.Record_ID IS NOT NULL
		ORDER BY AD_AttachmentEntry_ID DESC
		LIMIT 20000
		RETURNING AD_AttachmentEntry_ID
	)
UPDATE AD_AttachmentEntry e
SET Updated=now(), UpdatedBy=99, AD_Table_ID=null, Record_ID=null
FROM migratedEntries
WHERE e.AD_AttachmentEntry_ID=migratedEntries.AD_AttachmentEntry_ID;
',
' /* Finally, drop the two columns we migrated */
SELECT db_alter_table(''AD_AttachmentEntry'', ''ALTER TABLE AD_AttachmentEntry DROP COLUMN IF EXISTS AD_Table_ID, DROP COLUMN IF EXISTS Record_ID;'');
');
