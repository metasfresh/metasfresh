-- 2017-09-19T13:51:48.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('4',0,0,0,540840,'N',TO_TIMESTAMP('2017-09-19 13:51:48','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N',0,'Import Request','L','I_Request',TO_TIMESTAMP('2017-09-19 13:51:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T13:51:48.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540840 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2017-09-19T13:51:49.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554423,TO_TIMESTAMP('2017-09-19 13:51:49','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table I_Request',1,'Y','N','Y','Y','I_Request','N',1000000,TO_TIMESTAMP('2017-09-19 13:51:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T13:52:06.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='2',Updated=TO_TIMESTAMP('2017-09-19 13:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540840
;

-- 2017-09-19T13:53:46.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557192,102,0,19,540840,'AD_Client_ID',TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:46.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:46.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557193,113,0,19,540840,'AD_Org_ID',TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:46.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:46.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557194,245,0,16,540840,'Created',TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:46.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:46.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557195,246,0,18,110,540840,'CreatedBy',TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:46.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557195 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:46.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557196,912,0,10,540840,'I_ErrorMsg',TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','D',2000,'"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','N','N','N','N','N','N','N','Y','Import-Fehlermeldung',0,TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:46.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557196 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:46.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557197,913,0,17,540745,540840,'I_IsImported',TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Ist dieser Import verarbeitet worden?','D',1,'DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','N','N','Y','N','N','N','N','Y','Importiert',0,TO_TIMESTAMP('2017-09-19 13:53:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:46.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:47.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557198,348,0,20,540840,'IsActive',TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:47.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557198 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:47.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557199,1047,0,20,540840,'Processed',TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','D',1,'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','Y','N','N','N','N','Y','Verarbeitet',0,TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:47.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557199 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:47.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557200,607,0,16,540840,'Updated',TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:47.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557200 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:47.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557201,608,0,18,110,540840,'UpdatedBy',TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:47.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557201 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:53:47.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543424,0,'I_Request_ID',TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Import Request','Import Request',TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T13:53:47.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543424 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-19T13:53:47.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557202,543424,0,13,540840,'I_Request_ID',TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','Y','Y','N','N','Y','N','N','Import Request',0,TO_TIMESTAMP('2017-09-19 13:53:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:53:47.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557202 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:54:38.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557203,620,0,10,540840,'N','Value',TO_TIMESTAMP('2017-09-19 13:54:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',40,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Suchschlüssel',0,0,TO_TIMESTAMP('2017-09-19 13:54:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:54:38.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557203 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:56:14.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557204,1297,0,15,540840,'N','DateTrx',TO_TIMESTAMP('2017-09-19 13:56:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Vorgangsdatum','D',7,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Vorgangsdatum',0,0,TO_TIMESTAMP('2017-09-19 13:56:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:56:14.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557204 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:56:23.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557205,543388,0,15,540840,'N','ReminderDate',TO_TIMESTAMP('2017-09-19 13:56:23','YYYY-MM-DD HH24:MI:SS'),100,'N','D',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Wiedervorlage Datum',0,0,TO_TIMESTAMP('2017-09-19 13:56:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:56:23.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557205 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:56:57.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557206,3020,0,10,540840,'N','Status',TO_TIMESTAMP('2017-09-19 13:56:57','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',40,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Status',0,0,TO_TIMESTAMP('2017-09-19 13:56:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:56:57.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557206 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:57:07.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557207,2706,0,19,540840,'N','R_Status_ID',TO_TIMESTAMP('2017-09-19 13:57:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Request Status','D',10,'Status if the request (open, closed, investigating, ..)','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Status',0,0,TO_TIMESTAMP('2017-09-19 13:57:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:57:07.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557207 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:57:39.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557208,1521,0,36,540840,'N','Summary',TO_TIMESTAMP('2017-09-19 13:57:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Textual summary of this request','D',2000,'The Summary allows free form text entry of a recap of this request.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Summary',0,0,TO_TIMESTAMP('2017-09-19 13:57:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:57:39.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557208 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:58:04.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=255,Updated=TO_TIMESTAMP('2017-09-19 13:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557208
;

-- 2017-09-19T13:58:35.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557209,546,0,36,540840,'N','Result',TO_TIMESTAMP('2017-09-19 13:58:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Result of the action taken','D',2000,'The Result indicates the result of any action taken on this request.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ergebnis',0,0,TO_TIMESTAMP('2017-09-19 13:58:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T13:58:35.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557209 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T13:58:49.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.I_Request (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateTrx TIMESTAMP WITHOUT TIME ZONE, I_ErrorMsg VARCHAR(2000), I_IsImported CHAR(1) DEFAULT 'N' NOT NULL, I_Request_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, R_Status_ID NUMERIC(10), ReminderDate TIMESTAMP WITHOUT TIME ZONE, Result TEXT, Status VARCHAR(40), Summary VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(40), CONSTRAINT I_Request_Key PRIMARY KEY (I_Request_ID), CONSTRAINT RStatus_IRequest FOREIGN KEY (R_Status_ID) REFERENCES public.R_Status DEFERRABLE INITIALLY DEFERRED)
;

-- 2017-09-19T15:10:27.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557210,187,0,19,540840,'N','C_BPartner_ID',TO_TIMESTAMP('2017-09-19 15:10:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geschäftspartner',0,0,TO_TIMESTAMP('2017-09-19 15:10:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T15:10:27.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557210 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T15:10:47.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_request','ALTER TABLE public.I_Request ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2017-09-19T15:10:47.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Request ADD CONSTRAINT CBPartner_IRequest FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2017-09-19T15:15:09.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557211,3063,0,10,540840,'N','RequestType',TO_TIMESTAMP('2017-09-19 15:15:09','YYYY-MM-DD HH24:MI:SS'),100,'N','D',40,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Anfrageart',0,0,TO_TIMESTAMP('2017-09-19 15:15:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T15:15:09.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557211 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T15:15:17.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_request','ALTER TABLE public.I_Request ADD COLUMN RequestType VARCHAR(40)')
;

-- 2017-09-19T15:15:34.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557212,1894,0,19,540840,'N','R_RequestType_ID',TO_TIMESTAMP('2017-09-19 15:15:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Type of request (e.g. Inquiry, Complaint, ..)','D',10,'Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Request Type',0,0,TO_TIMESTAMP('2017-09-19 15:15:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T15:15:34.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557212 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T15:15:37.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_request','ALTER TABLE public.I_Request ADD COLUMN R_RequestType_ID NUMERIC(10) NOT NULL')
;

-- 2017-09-19T15:15:37.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Request ADD CONSTRAINT RRequestType_IRequest FOREIGN KEY (R_RequestType_ID) REFERENCES public.R_RequestType DEFERRABLE INITIALLY DEFERRED
;

-- 2017-09-19T15:15:49.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-09-19 15:15:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557210
;

-- 2017-09-19T15:15:53.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2017-09-19T15:15:53.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','C_BPartner_ID',null,'NOT NULL',null)
;

-- 2017-09-19T15:16:26.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-09-19 15:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557208
;

-- 2017-09-19T15:16:29.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Summary','VARCHAR(255)',null,null)
;

-- 2017-09-19T15:16:29.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Summary',null,'NOT NULL',null)
;

-- 2017-09-19T15:16:36.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-09-19 15:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557209
;

-- 2017-09-19T15:16:43.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Result','TEXT',null,null)
;

-- 2017-09-19T15:16:43.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Result',null,'NOT NULL',null)
;

-- 2017-09-19T15:16:58.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-19 15:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557212
;

-- 2017-09-19T15:17:01.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','R_RequestType_ID','NUMERIC(10)',null,null)
;

-- 2017-09-19T15:17:01.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','R_RequestType_ID',null,'NULL',null)
;

-- 2017-09-19T15:17:11.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-19 15:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557210
;

-- 2017-09-19T15:17:14.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2017-09-19T15:17:14.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','C_BPartner_ID',null,'NULL',null)
;

-- 2017-09-19T15:17:22.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-19 15:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557208
;

-- 2017-09-19T15:17:24.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Summary','VARCHAR(255)',null,null)
;

-- 2017-09-19T15:17:24.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Summary',null,'NULL',null)
;

-- 2017-09-19T15:17:34.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-19 15:17:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557209
;

-- 2017-09-19T15:17:36.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Result','TEXT',null,null)
;

-- 2017-09-19T15:17:36.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_request','Result',null,'NULL',null)
;

-- 2017-09-19T15:40:06.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557213,1516,0,19,540840,'N','R_Request_ID',TO_TIMESTAMP('2017-09-19 15:40:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Request from a Business Partner or Prospect','D',10,'The Request identifies a unique request from a Business Partner or Prospect.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Aufgabe',0,0,TO_TIMESTAMP('2017-09-19 15:40:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T15:40:06.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T15:40:33.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_request','ALTER TABLE public.I_Request ADD COLUMN R_Request_ID NUMERIC(10)')
;

-- 2017-09-19T15:40:33.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE I_Request ADD CONSTRAINT RRequest_IRequest FOREIGN KEY (R_Request_ID) REFERENCES public.R_Request DEFERRABLE INITIALLY DEFERRED
;

-- 2017-09-19T16:01:22.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1503, ColumnName='DateNextAction', Description='Date that this request should be acted on', Help='The Date Next Action indicates the next scheduled date for an action to occur for this request.', Name='Date next action',Updated=TO_TIMESTAMP('2017-09-19 16:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557205
;

-- 2017-09-19T16:01:22.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Date next action', Description='Date that this request should be acted on', Help='The Date Next Action indicates the next scheduled date for an action to occur for this request.' WHERE AD_Column_ID=557205 AND IsCentrallyMaintained='Y'
;

-- 2017-09-19T16:01:25.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_request','ALTER TABLE public.I_Request ADD COLUMN DateNextAction TIMESTAMP WITHOUT TIME ZONE')
;

ALTER TABLE i_request DROP COLUMN ReminderDate;



-- 2017-09-19T16:06:00.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540367,TO_TIMESTAMP('2017-09-19 16:06:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N','N','Y','Import Request','N',TO_TIMESTAMP('2017-09-19 16:06:00','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2017-09-19T16:06:00.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540367 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2017-09-19T16:07:10.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540878,540840,540367,TO_TIMESTAMP('2017-09-19 16:07:09','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','Y','N','N','Y','Y','N','N','Y','Y','Y','N','N',0,'Import Request','N',10,0,TO_TIMESTAMP('2017-09-19 16:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:10.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540878 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-09-19T16:07:24.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557192,560300,0,540878,TO_TIMESTAMP('2017-09-19 16:07:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2017-09-19 16:07:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557193,560301,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557196,560302,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'D','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','Y','Y','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557197,560303,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',1,'D','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','Y','Y','N','N','N','N','N','Importiert',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557198,560304,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557199,560305,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'D','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557202,560306,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Import Request',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557203,560307,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','Y','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:24.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557204,560308,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',7,'D','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','Y','Y','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:24.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557205,560309,0,540878,TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on',7,'D','The Date Next Action indicates the next scheduled date for an action to occur for this request.','Y','Y','Y','N','N','N','N','N','Date next action',TO_TIMESTAMP('2017-09-19 16:07:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557206,560310,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,'',40,'D','','Y','Y','Y','N','N','N','N','N','Status',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557207,560311,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,'Request Status',10,'D','Status if the request (open, closed, investigating, ..)','Y','Y','Y','N','N','N','N','N','Status',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557208,560312,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request',255,'D','The Summary allows free form text entry of a recap of this request.','Y','Y','Y','N','N','N','N','N','Summary',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557209,560313,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken',2000,'D','The Result indicates the result of any action taken on this request.','Y','Y','Y','N','N','N','N','N','Ergebnis',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557210,560314,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557211,560315,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','Y','Y','N','N','N','N','N','Anfrageart',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557212,560316,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,'Type of request (e.g. Inquiry, Complaint, ..)',10,'D','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.','Y','Y','Y','N','N','N','N','N','Request Type',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:07:25.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557213,560317,0,540878,TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100,'Request from a Business Partner or Prospect',10,'D','The Request identifies a unique request from a Business Partner or Prospect.','Y','Y','Y','N','N','N','N','N','Aufgabe',TO_TIMESTAMP('2017-09-19 16:07:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:07:25.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T16:10:48.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560304
;

-- 2017-09-19T16:10:48.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560300
;

-- 2017-09-19T16:10:48.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560301
;

-- 2017-09-19T16:10:48.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560307
;

-- 2017-09-19T16:10:48.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560314
;

-- 2017-09-19T16:10:48.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560315
;

-- 2017-09-19T16:10:48.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560316
;

-- 2017-09-19T16:10:48.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560310
;

-- 2017-09-19T16:10:48.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560311
;

-- 2017-09-19T16:10:48.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560308
;

-- 2017-09-19T16:10:48.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560309
;

-- 2017-09-19T16:10:48.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560312
;

-- 2017-09-19T16:10:48.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560313
;

-- 2017-09-19T16:10:48.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560303
;

-- 2017-09-19T16:10:48.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560305
;

-- 2017-09-19T16:10:48.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560302
;

-- 2017-09-19T16:10:48.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2017-09-19 16:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560317
;

-- 2017-09-19T16:10:56.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-19 16:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560301
;

-- 2017-09-19T16:11:00.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-19 16:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560314
;

-- 2017-09-19T16:11:04.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-19 16:11:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560316
;

-- 2017-09-19T16:11:07.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-19 16:11:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560311
;

-- 2017-09-19T16:11:10.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-19 16:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560309
;

-- 2017-09-19T16:11:25.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-19 16:11:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560305
;

-- 2017-09-19T16:11:50.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540367,Updated=TO_TIMESTAMP('2017-09-19 16:11:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540840
;



-------------------------------------------------
----------------- Import format -----------------
-------------------------------------------------



-- 2017-09-19T16:14:13.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FormatType,IsActive,Name,Processing,Updated,UpdatedBy) VALUES (0,540011,0,540840,TO_TIMESTAMP('2017-09-19 16:14:13','YYYY-MM-DD HH24:MI:SS'),100,'T','Y','Requests','N',TO_TIMESTAMP('2017-09-19 16:14:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:14:56.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557203,540011,540128,0,TO_TIMESTAMP('2017-09-19 16:14:56','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Geschäftspartner-Schlüssel',10,1,TO_TIMESTAMP('2017-09-19 16:14:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:15:34.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557211,540011,540129,0,TO_TIMESTAMP('2017-09-19 16:15:34','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Anfrageart',20,2,TO_TIMESTAMP('2017-09-19 16:15:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:16:05.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557206,540011,540130,0,TO_TIMESTAMP('2017-09-19 16:16:05','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Status',30,0,TO_TIMESTAMP('2017-09-19 16:16:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:16:09.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2017-09-19 16:16:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540130
;

-- 2017-09-19T16:16:54.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557204,540011,540131,0,TO_TIMESTAMP('2017-09-19 16:16:54','YYYY-MM-DD HH24:MI:SS'),100,'yyyy-MM-dd','D','.','N',0,'Y','Vorgangsdatum',40,4,TO_TIMESTAMP('2017-09-19 16:16:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:17:14.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557205,540011,540132,0,TO_TIMESTAMP('2017-09-19 16:17:14','YYYY-MM-DD HH24:MI:SS'),100,'yyyy-MM-dd','D','.','N',0,'Y','Date next action',50,5,TO_TIMESTAMP('2017-09-19 16:17:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:17:35.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557208,540011,540133,0,TO_TIMESTAMP('2017-09-19 16:17:35','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Summary',60,6,TO_TIMESTAMP('2017-09-19 16:17:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:17:58.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,557209,540011,540134,0,TO_TIMESTAMP('2017-09-19 16:17:57','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Ergebnis',70,7,TO_TIMESTAMP('2017-09-19 16:17:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:29:26.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('2',0,0,540834,'Y','org.adempiere.impexp.RequestImportProcess','N',TO_TIMESTAMP('2017-09-19 16:29:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y',0,'Request Import Process','N','Y','Java',TO_TIMESTAMP('2017-09-19 16:29:26','YYYY-MM-DD HH24:MI:SS'),100,'RequestImportProcess')
;

-- 2017-09-19T16:29:26.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540834 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-09-19T16:29:45.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540834,540840,TO_TIMESTAMP('2017-09-19 16:29:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2017-09-19 16:29:45','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;



-------------------------------------------------
----------------- Add to the menu -----------------
-------------------------------------------------



-- 2017-09-19T16:32:08.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,540962,0,540367,TO_TIMESTAMP('2017-09-19 16:32:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Import Request','Y','N','N','N','N','Import Request',TO_TIMESTAMP('2017-09-19 16:32:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T16:32:08.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540962 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-09-19T16:32:08.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540962, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540962)
;

-- 2017-09-19T16:32:08.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=165 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=372 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=271 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=528 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=414 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=238 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=396 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540594 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- 2017-09-19T16:32:08.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540962 AND AD_Tree_ID=10
;


---------------------------------------------------------------------------------------------------------------------------------------------------



-- 2017-09-19T17:46:33.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557214,290,0,10,540840,'N','DocumentNo',TO_TIMESTAMP('2017-09-19 17:46:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Document sequence number of the document','D',40,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','Y','N','Beleg Nr.',0,1,TO_TIMESTAMP('2017-09-19 17:46:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-19T17:46:33.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557214 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-19T17:46:36.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('i_request','ALTER TABLE public.I_Request ADD COLUMN DocumentNo VARCHAR(40)')
;

-- 2017-09-19T17:47:56.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557214,560318,0,540878,0,TO_TIMESTAMP('2017-09-19 17:47:56','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',0,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','Y','N','N','N','N','N','Beleg Nr.',170,10,0,1,1,TO_TIMESTAMP('2017-09-19 17:47:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-19T17:47:56.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-19T17:48:08.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-09-19 17:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560313
;

-- 2017-09-19T17:48:08.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2017-09-19 17:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560303
;

-- 2017-09-19T17:48:08.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2017-09-19 17:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560305
;

-- 2017-09-19T17:48:08.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2017-09-19 17:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560302
;

-- 2017-09-19T17:48:08.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2017-09-19 17:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560317
;

-- 2017-09-19T17:48:08.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2017-09-19 17:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560318
;

-- 2017-09-19T17:48:18.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560318
;

-- 2017-09-19T17:48:18.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560300
;

-- 2017-09-19T17:48:18.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560301
;

-- 2017-09-19T17:48:18.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560307
;

-- 2017-09-19T17:48:18.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560314
;

-- 2017-09-19T17:48:18.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560315
;

-- 2017-09-19T17:48:18.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560316
;

-- 2017-09-19T17:48:18.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560310
;

-- 2017-09-19T17:48:18.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560311
;

-- 2017-09-19T17:48:18.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560308
;

-- 2017-09-19T17:48:18.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560309
;

-- 2017-09-19T17:48:18.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560312
;

-- 2017-09-19T17:48:18.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560303
;

-- 2017-09-19T17:48:18.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560305
;

-- 2017-09-19T17:48:18.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560302
;

-- 2017-09-19T17:48:18.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2017-09-19 17:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560317
;

-- 2017-09-19T17:48:50.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=557214, Name='Beleg Nr.',Updated=TO_TIMESTAMP('2017-09-19 17:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=540134
;

-- 2017-09-19T18:00:41.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AccessLevel='3',Updated=TO_TIMESTAMP('2017-09-19 18:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540834
;

-- 2017-09-19T18:00:51.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2017-09-19 18:00:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540834
;

------------------------------------------------------------------------------------------------------------------------------------------------

-- 2017-09-19T18:31:14.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='org.compiere.process.ImportRequest',Updated=TO_TIMESTAMP('2017-09-19 18:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540834
;

