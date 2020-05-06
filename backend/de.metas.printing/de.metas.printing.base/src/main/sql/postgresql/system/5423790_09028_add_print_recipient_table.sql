--
-- add the printing queue recipient table
--
-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,540681,'N',TO_TIMESTAMP('2015-08-12 16:59:39','YYYY-MM-DD HH24:MI:SS'),100,'Benutzer, für den ein Druckjob erstellt werden soll','de.metas.printing','N','Y','N','N','Y','N','N','N',0,'Druck-Empfänger','L','C_Printing_Queue_Recipient',TO_TIMESTAMP('2015-08-12 16:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540681 AND NOT EXISTS (SELECT * FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
CREATE SEQUENCE C_PRINTING_QUEUE_RECIPIENT_SEQ INCREMENT 1 MINVALUE 0 MAXVALUE 99999999 START 1000000
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554253,TO_TIMESTAMP('2015-08-12 16:59:39','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Printing_Queue_Recipient',1,'Y','N','Y','Y','C_Printing_Queue_Recipient','N',1000000,TO_TIMESTAMP('2015-08-12 16:59:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552674,102,0,19,540681,'AD_Client_ID',TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.printing',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552674 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552675,113,0,19,540681,'AD_Org_ID',TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.printing',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','Y','N','N','Y','N','N','Sektion',0,TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552675 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552676,245,0,16,540681,'Created',TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.printing',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552676 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552677,246,0,18,110,540681,'CreatedBy',TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.printing',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552677 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552678,348,0,20,540681,'IsActive',TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.printing',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552678 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552679,607,0,16,540681,'Updated',TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.printing',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2015-08-12 16:59:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552679 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552680,608,0,18,110,540681,'UpdatedBy',TO_TIMESTAMP('2015-08-12 16:59:45','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.printing',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2015-08-12 16:59:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552680 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542879,0,'C_Printing_Queue_Recipient_ID',TO_TIMESTAMP('2015-08-12 16:59:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Druck-Empfänger','Druck-Empfänger',TO_TIMESTAMP('2015-08-12 16:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542879 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552681,542879,0,13,540681,'C_Printing_Queue_Recipient_ID',TO_TIMESTAMP('2015-08-12 16:59:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','N','N','N','Y','Y','N','N','Y','N','N','Druck-Empfänger',0,TO_TIMESTAMP('2015-08-12 16:59:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 16:59
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552681 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 17:00
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552682,541927,0,30,540681,'N','C_Printing_Queue_ID',TO_TIMESTAMP('2015-08-12 17:00:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.printing',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Druck-Warteschlangendatensatz',0,TO_TIMESTAMP('2015-08-12 17:00:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 17:00
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552682 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 17:01
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552683,542878,0,30,110,540681,'N','AD_User_Print_ID',TO_TIMESTAMP('2015-08-12 17:01:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.printing',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ausdruck durch',0,TO_TIMESTAMP('2015-08-12 17:01:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 17:01
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552683 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 17:01
-- URL zum Konzept
UPDATE AD_Element SET Name='Ausdruck für', PrintName='Ausdruck für',Updated=TO_TIMESTAMP('2015-08-12 17:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542878
;

-- 12.08.2015 17:01
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542878
;

-- 12.08.2015 17:01
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_User_Print_ID', Name='Ausdruck für', Description=NULL, Help=NULL WHERE AD_Element_ID=542878
;

-- 12.08.2015 17:01
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Print_ID', Name='Ausdruck für', Description=NULL, Help=NULL, AD_Element_ID=542878 WHERE UPPER(ColumnName)='AD_USER_PRINT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 12.08.2015 17:01
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Print_ID', Name='Ausdruck für', Description=NULL, Help=NULL WHERE AD_Element_ID=542878 AND IsCentrallyMaintained='Y'
;

-- 12.08.2015 17:01
-- URL zum Konzept
UPDATE AD_Field SET Name='Ausdruck für', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542878) AND IsCentrallyMaintained='Y'
;

-- 12.08.2015 17:01
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Ausdruck für', Name='Ausdruck für' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542878)
;

-- 12.08.2015 17:02
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540165,Updated=TO_TIMESTAMP('2015-08-12 17:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540681
;

-- 12.08.2015 17:03
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540701,540681,540165,TO_TIMESTAMP('2015-08-12 17:03:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','N','N','Y','N','Y','N','N','N','N','Y','N','N','Y','Y','N','N','N',0,'Druck-Empfänger','N',20,0,TO_TIMESTAMP('2015-08-12 17:03:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 17:03
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540701 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 12.08.2015 17:03
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-08-12 17:03:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540681
;

-- 12.08.2015 17:03
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=552682,Updated=TO_TIMESTAMP('2015-08-12 17:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540701
;

-- 12.08.2015 17:04
-- URL zum Konzept
UPDATE AD_Tab SET Name='Abw. Druck-Empfänger',Updated=TO_TIMESTAMP('2015-08-12 17:04:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540701
;

-- 12.08.2015 17:04
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540701
;

-- 12.08.2015 17:06
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542880,0,'IsPrintoutForOtherUser',TO_TIMESTAMP('2015-08-12 17:06:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Abw. Druck-Empfänger','Abw. Druck-Empfänger',TO_TIMESTAMP('2015-08-12 17:06:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 17:06
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542880 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 12.08.2015 17:06
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552684,542880,0,20,540435,'N','IsPrintoutForOtherUser',TO_TIMESTAMP('2015-08-12 17:06:34','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.printing',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Abw. Druck-Empfänger',0,TO_TIMESTAMP('2015-08-12 17:06:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.08.2015 17:06
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552684 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.08.2015 17:06
-- URL zum Konzept
ALTER TABLE C_Printing_Queue ADD IsPrintoutForOtherUser CHAR(1) DEFAULT 'N' CHECK (IsPrintoutForOtherUser IN ('Y','N')) NOT NULL
;

-- 12.08.2015 17:07
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=556261
;

-- 12.08.2015 17:07
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=556261
;

-- 12.08.2015 17:07
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552684,556262,0,540460,0,TO_TIMESTAMP('2015-08-12 17:07:34','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','Y','Abw. Druck-Empfänger',62,62,0,TO_TIMESTAMP('2015-08-12 17:07:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.08.2015 17:07
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556262 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 12.08.2015 17:08
-- URL zum Konzept
UPDATE AD_Tab SET DisplayLogic='@IsPrintoutForOtherUser@=Y', IsInsertRecord='Y',Updated=TO_TIMESTAMP('2015-08-12 17:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540701
;

-- 12.08.2015 17:08
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2015-08-12 17:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540701
;

-- 12.08.2015 17:09
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=552673
;

-- 12.08.2015 17:09
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=552673
;

-- 12.08.2015 17:10
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2015-08-12 17:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552684
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_Element SET Name='Ausdruck für', PrintName='Ausdruck für',Updated=TO_TIMESTAMP('2015-08-12 17:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542013
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542013
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_User_ToPrint_ID', Name='Ausdruck für', Description=NULL, Help=NULL WHERE AD_Element_ID=542013
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_ToPrint_ID', Name='Ausdruck für', Description=NULL, Help=NULL, AD_Element_ID=542013 WHERE UPPER(ColumnName)='AD_USER_TOPRINT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_ToPrint_ID', Name='Ausdruck für', Description=NULL, Help=NULL WHERE AD_Element_ID=542013 AND IsCentrallyMaintained='Y'
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_Field SET Name='Ausdruck für', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542013) AND IsCentrallyMaintained='Y'
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Ausdruck für', Name='Ausdruck für' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542013)
;

-- 12.08.2015 17:30
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=542013, AD_Reference_ID=19, ColumnName='AD_User_ToPrint_ID', Description=NULL, Help=NULL, Name='Ausdruck für',Updated=TO_TIMESTAMP('2015-08-12 17:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552683
;

-- 12.08.2015 17:30
-- URL zum Konzept
CREATE TABLE C_Printing_Queue_Recipient (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_User_ToPrint_ID NUMERIC(10) DEFAULT NULL , C_Printing_Queue_ID NUMERIC(10) DEFAULT NULL , C_Printing_Queue_Recipient_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Printing_Queue_Recipient_Key PRIMARY KEY (C_Printing_Queue_Recipient_ID))
;

-----------------
--
-- add an aggregation key column
--
-- 13.08.2015 14:31
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542881,0,'PrintingQueueAggregationKey',TO_TIMESTAMP('2015-08-13 14:31:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Warteschlangen-Aggregationsmerkmal','Warteschlangen-Aggregationsmerkmal',TO_TIMESTAMP('2015-08-13 14:31:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 13.08.2015 14:31
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542881 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 13.08.2015 14:32
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552685,542881,0,10,540435,'N','PrintingQueueAggregationKey',TO_TIMESTAMP('2015-08-13 14:32:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.printing',256,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Warteschlangen-Aggregationsmerkmal',0,TO_TIMESTAMP('2015-08-13 14:32:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 13.08.2015 14:32
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552685 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 13.08.2015 14:32
-- URL zum Konzept
ALTER TABLE C_Printing_Queue ADD PrintingQueueAggregationKey VARCHAR(256) DEFAULT NULL 
;



