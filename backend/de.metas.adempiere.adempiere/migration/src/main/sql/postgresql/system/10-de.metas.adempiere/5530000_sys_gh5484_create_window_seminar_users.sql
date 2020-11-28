-- 2019-09-05T16:18:23.739Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=541034,Updated=TO_TIMESTAMP('2019-09-05 18:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559693
;

-- 2019-09-05T16:18:49.340Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='R_StatusCategory_ID=540010',Updated=TO_TIMESTAMP('2019-09-05 18:18:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541034
;

-- 2019-09-05T16:19:51.377Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET OrderByClause='value',Updated=TO_TIMESTAMP('2019-09-05 18:19:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541034
;

-- 2019-09-05T19:20:01.663Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577049,0,TO_TIMESTAMP('2019-09-05 21:20:01','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Nummer','Nummer',TO_TIMESTAMP('2019-09-05 21:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T19:20:01.667Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577049 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T19:20:18.949Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577049, Name='Nummer',Updated=TO_TIMESTAMP('2019-09-05 21:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583486
;

-- 2019-09-05T19:20:18.974Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577049) 
;

-- 2019-09-05T19:20:18.981Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583486
;

-- 2019-09-05T19:20:18.984Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583486)
;

-- 2019-09-05T19:26:59.748Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y', DefaultValue='540003',Updated=TO_TIMESTAMP('2019-09-05 21:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583494
;

-- 2019-09-05T19:27:18.883Z
-- URL zum Konzept
UPDATE AD_Field SET Help='', AD_Name_ID=600, Name='Art', Description='',Updated=TO_TIMESTAMP('2019-09-05 21:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583494
;

-- 2019-09-05T19:27:18.887Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2019-09-05T19:27:18.912Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583494
;

-- 2019-09-05T19:27:18.913Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583494)
;

-- 2019-09-05T19:36:19.074Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540905,542006,TO_TIMESTAMP('2019-09-05 21:36:19','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Dozent',TO_TIMESTAMP('2019-09-05 21:36:19','YYYY-MM-DD HH24:MI:SS'),100,'SD','Dozent')
;

-- 2019-09-05T19:36:19.077Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542006 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-09-05T19:36:31.549Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540905,542007,TO_TIMESTAMP('2019-09-05 21:36:31','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Teilnehmer',TO_TIMESTAMP('2019-09-05 21:36:31','YYYY-MM-DD HH24:MI:SS'),100,'ST','Teilnehmer')
;

-- 2019-09-05T19:36:31.550Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542007 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-09-05T19:36:45.433Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540905,542008,TO_TIMESTAMP('2019-09-05 21:36:45','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Ansprechpartner Hotel',TO_TIMESTAMP('2019-09-05 21:36:45','YYYY-MM-DD HH24:MI:SS'),100,'SH','Ansprechpartner Hotel')
;

-- 2019-09-05T19:36:45.434Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542008 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-09-05T19:36:59.806Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540905,542009,TO_TIMESTAMP('2019-09-05 21:36:59','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Aussendienst',TO_TIMESTAMP('2019-09-05 21:36:59','YYYY-MM-DD HH24:MI:SS'),100,'SA','Aussendienst')
;

-- 2019-09-05T19:36:59.807Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542009 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-09-05T19:38:15.076Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540449,'ad_reference.value like ''S%''',TO_TIMESTAMP('2019-09-05 21:38:15','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminar_Roles','S',TO_TIMESTAMP('2019-09-05 21:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T19:38:24.326Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=17, AD_Reference_Value_ID=540905, AD_Val_Rule_ID=540449,Updated=TO_TIMESTAMP('2019-09-05 21:38:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583598
;

-- 2019-09-05T19:39:06.417Z
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='value like ''S%''',Updated=TO_TIMESTAMP('2019-09-05 21:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540449
;

-- 2019-09-05T19:39:40.494Z
-- URL zum Konzept
UPDATE AD_Field SET Help='Select Role for with Data Access Restrictions. Beachten Sie, dass Zugriffsinformation im Cache gespeichert werden und daher eine Neuanmeldung oder ein Leeren des Cache erforderlich sind.', AD_Name_ID=572787, Name='Rolle', Description='Role with Data Access Restriction',Updated=TO_TIMESTAMP('2019-09-05 21:39:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583598
;

-- 2019-09-05T19:39:40.497Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572787) 
;

-- 2019-09-05T19:39:40.501Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583598
;

-- 2019-09-05T19:39:40.502Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583598)
;

-- 2019-09-05T19:40:54.741Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583508,0,541865,542751,560729,'F',TO_TIMESTAMP('2019-09-05 21:40:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'VK',35,0,0,TO_TIMESTAMP('2019-09-05 21:40:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T19:41:16.643Z
-- URL zum Konzept
UPDATE AD_Field SET Help='Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.', AD_Name_ID=1416, Name='Preis', Description='Preis',Updated=TO_TIMESTAMP('2019-09-05 21:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583508
;

-- 2019-09-05T19:41:16.646Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1416) 
;

-- 2019-09-05T19:41:16.662Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583508
;

-- 2019-09-05T19:41:16.663Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583508)
;

-- 2019-09-05T19:45:36.352Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541404,'N',TO_TIMESTAMP('2019-09-05 21:45:36','YYYY-MM-DD HH24:MI:SS'),100,'U','N','Y','N','Y','Y','N','N','N','N','N',0,'Projekt Label','NP','L','C_Project_Label',TO_TIMESTAMP('2019-09-05 21:45:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T19:45:36.354Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541404 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-09-05T19:45:36.429Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555050,TO_TIMESTAMP('2019-09-05 21:45:36','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Project_Label',1,'Y','N','Y','Y','C_Project_Label','N',1000000,TO_TIMESTAMP('2019-09-05 21:45:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T19:46:03.845Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568640,102,0,19,541404,'AD_Client_ID',TO_TIMESTAMP('2019-09-05 21:46:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','U',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2019-09-05 21:46:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:03.846Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568640 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:03.849Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2019-09-05T19:46:05.074Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568641,113,0,19,541404,'AD_Org_ID',TO_TIMESTAMP('2019-09-05 21:46:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','U',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2019-09-05 21:46:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:05.080Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568641 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:05.080Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2019-09-05T19:46:05.908Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568642,245,0,16,541404,'Created',TO_TIMESTAMP('2019-09-05 21:46:05','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','U',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2019-09-05 21:46:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:05.913Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568642 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:05.914Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2019-09-05T19:46:06.105Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568643,246,0,18,110,541404,'CreatedBy',TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','U',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:06.106Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568643 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:06.107Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2019-09-05T19:46:06.373Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568644,348,0,20,541404,'IsActive',TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','U',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:06.381Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:06.382Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2019-09-05T19:46:06.695Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568645,607,0,16,541404,'Updated',TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','U',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:06.696Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568645 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:06.697Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2019-09-05T19:46:06.874Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568646,608,0,18,110,541404,'UpdatedBy',TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','U',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:06.875Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568646 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:06.876Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2019-09-05T19:46:07.085Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577050,0,'C_Project_Label_ID',TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Projekt Label','Projekt Label',TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T19:46:07.087Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577050 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T19:46:07.133Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568647,577050,0,13,541404,'C_Project_Label_ID',TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','N','N','N','Y','Y','N','N','Y','N','N','Projekt Label',0,TO_TIMESTAMP('2019-09-05 21:46:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:07.136Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568647 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:07.137Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(577050) 
;

-- 2019-09-05T19:46:27.278Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568648,469,0,10,541404,'Name',TO_TIMESTAMP('2019-09-05 21:46:27','YYYY-MM-DD HH24:MI:SS'),100,'N','','U',40,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','Name',0,1,TO_TIMESTAMP('2019-09-05 21:46:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:27.281Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:27.283Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2019-09-05T19:46:39.381Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568649,275,0,10,541404,'Description',TO_TIMESTAMP('2019-09-05 21:46:39','YYYY-MM-DD HH24:MI:SS'),100,'N','U',255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Beschreibung',0,0,TO_TIMESTAMP('2019-09-05 21:46:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:46:39.382Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:46:39.384Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2019-09-05T19:47:25.443Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568650,1115,0,10,541404,'Note',TO_TIMESTAMP('2019-09-05 21:47:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Optional weitere Information','U',255,'Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Notiz',0,0,TO_TIMESTAMP('2019-09-05 21:47:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T19:47:25.446Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568650 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T19:47:25.449Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1115) 
;

-- 2019-09-05T19:47:30.893Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=2000,Updated=TO_TIMESTAMP('2019-09-05 21:47:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568650
;

-- 2019-09-05T19:50:44.172Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577051,0,TO_TIMESTAMP('2019-09-05 21:50:44','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminarreihe','Seminarreihe',TO_TIMESTAMP('2019-09-05 21:50:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T19:50:44.175Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577051 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T19:50:52.678Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,577051,0,540681,TO_TIMESTAMP('2019-09-05 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','N','Y','Seminarreihe','N',TO_TIMESTAMP('2019-09-05 21:50:52','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-09-05T19:50:52.682Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540681 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-09-05T19:50:52.687Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(577051) 
;

-- 2019-09-05T19:50:52.691Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540681
;

-- 2019-09-05T19:50:52.692Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540681)
;

-- 2019-09-05T19:51:19.863Z
-- URL zum Konzept
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,AD_Org_ID,Name,EntityType,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Element_ID) VALUES (TO_TIMESTAMP('2019-09-05 21:51:19','YYYY-MM-DD HH24:MI:SS'),'N',540681,10,'N',0,TO_TIMESTAMP('2019-09-05 21:51:19','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N','N',0,'Y','N','N','Y','Y','Y','N',541404,541869,'N','Y',0,0,'Projekt Label','U','C_Project_Label','Y','N',577050)
;

-- 2019-09-05T19:51:19.865Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541869 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-05T19:51:19.869Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(577050) 
;

-- 2019-09-05T19:51:19.873Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541869)
;

-- 2019-09-05T19:54:35.697Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Seminarreihe', AD_Element_ID=577051,Updated=TO_TIMESTAMP('2019-09-05 21:54:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541869
;

-- 2019-09-05T19:54:35.700Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(577051) 
;

-- 2019-09-05T19:54:35.705Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541869)
;

-- 2019-09-05T19:56:22.420Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541869,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 21:56:22','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 21:56:22','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',583617,'N',568640,0,'U','Mandant','Mandant für diese Installation.')
;

-- 2019-09-05T19:56:22.421Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T19:56:22.424Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-05T19:56:23.532Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583617
;

-- 2019-09-05T19:56:23.533Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583617)
;

-- 2019-09-05T19:56:23.688Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541869,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 21:56:23','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 21:56:23','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',583618,'N',568641,0,'U','Sektion','Organisatorische Einheit des Mandanten')
;

-- 2019-09-05T19:56:23.689Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583618 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T19:56:23.691Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-05T19:56:24.061Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583618
;

-- 2019-09-05T19:56:24.062Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583618)
;

-- 2019-09-05T19:56:24.145Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541869,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 21:56:24','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 21:56:24','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',583619,'N',568644,0,'U','Aktiv','Der Eintrag ist im System aktiv')
;

-- 2019-09-05T19:56:24.146Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583619 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T19:56:24.150Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-05T19:56:24.926Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583619
;

-- 2019-09-05T19:56:24.927Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583619)
;

-- 2019-09-05T19:56:25.006Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541869,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 21:56:24','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 21:56:24','YYYY-MM-DD HH24:MI:SS'),100,583620,'N',568647,0,'U','Projekt Label')
;

-- 2019-09-05T19:56:25.008Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T19:56:25.011Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577050) 
;

-- 2019-09-05T19:56:25.013Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583620
;

-- 2019-09-05T19:56:25.013Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583620)
;

-- 2019-09-05T19:56:25.068Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541869,'N',40,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 21:56:25','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 21:56:25','YYYY-MM-DD HH24:MI:SS'),100,'',583621,'N',568648,0,'U','Name','')
;

-- 2019-09-05T19:56:25.069Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T19:56:25.072Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-09-05T19:56:25.257Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583621
;

-- 2019-09-05T19:56:25.258Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583621)
;

-- 2019-09-05T19:56:25.322Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541869,'N',255,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 21:56:25','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 21:56:25','YYYY-MM-DD HH24:MI:SS'),100,583622,'N',568649,0,'U','Beschreibung')
;

-- 2019-09-05T19:56:25.323Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T19:56:25.325Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-05T19:56:25.450Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583622
;

-- 2019-09-05T19:56:25.450Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583622)
;

-- 2019-09-05T19:56:25.529Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541869,'N',2000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 21:56:25','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 21:56:25','YYYY-MM-DD HH24:MI:SS'),100,'Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',583623,'N',568650,0,'U','Notiz','Optional weitere Information')
;

-- 2019-09-05T19:56:25.530Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T19:56:25.532Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2019-09-05T19:56:25.552Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583623
;

-- 2019-09-05T19:56:25.553Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583623)
;

-- 2019-09-05T19:56:39.612Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 21:56:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583619
;

-- 2019-09-05T19:56:45.337Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 21:56:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583621
;

-- 2019-09-05T19:56:47.641Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 21:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583622
;

-- 2019-09-05T19:56:58.265Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Project_Label (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Project_Label_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(40), Note VARCHAR(2000), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Project_Label_Key PRIMARY KEY (C_Project_Label_ID))
;

-- 2019-09-05T20:00:37.790Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577052,0,TO_TIMESTAMP('2019-09-05 22:00:37','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminar-Verwaltung','Seminar-Verwaltung',TO_TIMESTAMP('2019-09-05 22:00:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:00:37.792Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577052 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T20:00:50.953Z
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,577052,541340,0,TO_TIMESTAMP('2019-09-05 22:00:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Seminar-Verwaltung','Y','N','N','N','Y','Seminar-Verwaltung',TO_TIMESTAMP('2019-09-05 22:00:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:00:50.955Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541340 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-09-05T20:00:50.959Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541340, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541340)
;

-- 2019-09-05T20:00:50.971Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(577052) 
;

-- 2019-09-05T20:01:14.089Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,577040,541341,0,540680,TO_TIMESTAMP('2019-09-05 22:01:14','YYYY-MM-DD HH24:MI:SS'),100,'U','Seminar','Y','N','N','N','N','Seminar',TO_TIMESTAMP('2019-09-05 22:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:01:14.090Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541341 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-09-05T20:01:14.091Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541341, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541341)
;

-- 2019-09-05T20:01:14.093Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(577040) 
;

-- 2019-09-05T20:01:14.120Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541340, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541341 AND AD_Tree_ID=10
;

-- 2019-09-05T20:01:41.115Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,577051,541342,0,540681,TO_TIMESTAMP('2019-09-05 22:01:41','YYYY-MM-DD HH24:MI:SS'),100,'U','Seminarreihe','Y','N','N','N','N','Seminarreihe',TO_TIMESTAMP('2019-09-05 22:01:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:01:41.115Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541342 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-09-05T20:01:41.116Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541342, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541342)
;

-- 2019-09-05T20:01:41.118Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(577051) 
;

-- 2019-09-05T20:01:41.145Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541340, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541341 AND AD_Tree_ID=10
;

-- 2019-09-05T20:01:41.146Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541340, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541342 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.125Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.125Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.126Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.127Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.127Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.128Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.128Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.129Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.130Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.130Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.131Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.131Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.132Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.132Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.133Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.133Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.134Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.134Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.135Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.135Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.136Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:09.136Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.011Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.011Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.012Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.012Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.013Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.013Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.014Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.014Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.014Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.015Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.016Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.016Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.016Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.017Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.017Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.018Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.018Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.019Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.019Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.019Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.020Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:27.020Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.344Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.345Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.346Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.346Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.347Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.348Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.348Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.349Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.350Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.350Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.351Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.352Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.352Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.353Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.354Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.354Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.355Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.356Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.356Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.357Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.357Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-09-05T20:02:32.358Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-09-05T20:06:10.188Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568651,577050,0,19,203,'C_Project_Label_ID',TO_TIMESTAMP('2019-09-05 22:06:10','YYYY-MM-DD HH24:MI:SS'),100,'N','U',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Projekt Label',0,0,TO_TIMESTAMP('2019-09-05 22:06:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T20:06:10.190Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568651 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T20:06:10.193Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(577050) 
;

-- 2019-09-05T20:06:11.398Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN C_Project_Label_ID NUMERIC(10)')
;

-- 2019-09-05T20:06:11.653Z
-- URL zum Konzept
ALTER TABLE C_Project ADD CONSTRAINT CProjectLabel_CProject FOREIGN KEY (C_Project_Label_ID) REFERENCES public.C_Project_Label DEFERRABLE INITIALLY DEFERRED
;

-- 2019-09-05T20:06:36.426Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,AD_Name_ID,EntityType,Name) VALUES (541865,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:06:36','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-09-05 22:06:36','YYYY-MM-DD HH24:MI:SS'),100,583624,'Y',370,360,1,1,568651,0,577051,'U','Seminarreihe')
;

-- 2019-09-05T20:06:36.427Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:06:36.431Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577051) 
;

-- 2019-09-05T20:06:36.435Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583624
;

-- 2019-09-05T20:06:36.436Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583624)
;

-- 2019-09-05T20:07:17.027Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583624,0,541865,542751,560730,'F',TO_TIMESTAMP('2019-09-05 22:07:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Seminarreihe',45,0,0,TO_TIMESTAMP('2019-09-05 22:07:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:07:22.792Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2019-09-05 22:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560730
;

-- 2019-09-05T20:09:26.888Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560703
;

-- 2019-09-05T20:09:26.895Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560730
;

-- 2019-09-05T20:09:26.899Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560700
;

-- 2019-09-05T20:09:26.903Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560701
;

-- 2019-09-05T20:09:26.907Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560707
;

-- 2019-09-05T20:09:26.910Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560705
;

-- 2019-09-05T20:09:26.914Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560726
;

-- 2019-09-05T20:09:26.917Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560706
;

-- 2019-09-05T20:09:26.920Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560702
;

-- 2019-09-05T20:09:26.924Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-09-05 22:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560704
;

-- 2019-09-05T20:10:35.999Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,577048,0,540682,TO_TIMESTAMP('2019-09-05 22:10:35','YYYY-MM-DD HH24:MI:SS'),100,'U','Seminarbeteiligte','Y','N','N','N','N','Y','Seminarbeteiligte','N',TO_TIMESTAMP('2019-09-05 22:10:35','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-09-05T20:10:36Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540682 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-09-05T20:10:36.002Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(577048) 
;

-- 2019-09-05T20:10:36.003Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540682
;

-- 2019-09-05T20:10:36.003Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540682)
;

-- 2019-09-05T20:11:00.764Z
-- URL zum Konzept
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,AD_Org_ID,Name,EntityType,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Element_ID) VALUES (TO_TIMESTAMP('2019-09-05 22:11:00','YYYY-MM-DD HH24:MI:SS'),'N',540682,10,'N',0,TO_TIMESTAMP('2019-09-05 22:11:00','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N','N',0,'N','N','N','Y','Y','Y','N',540961,541870,'N','Y',0,0,'Projektkontakt','U','C_Project_User','Y','N',543957)
;

-- 2019-09-05T20:11:00.765Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541870 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-05T20:11:00.766Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(543957) 
;

-- 2019-09-05T20:11:00.768Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541870)
;

-- 2019-09-05T20:11:08.704Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541870,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:08','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:08','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',583625,'N',559664,0,'U','Mandant','Mandant für diese Installation.')
;

-- 2019-09-05T20:11:08.706Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583625 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:08.708Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-05T20:11:09.026Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583625
;

-- 2019-09-05T20:11:09.026Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583625)
;

-- 2019-09-05T20:11:09.112Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541870,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',583626,'N',559665,0,'U','Sektion','Organisatorische Einheit des Mandanten')
;

-- 2019-09-05T20:11:09.113Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583626 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:09.114Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-05T20:11:09.265Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583626
;

-- 2019-09-05T20:11:09.265Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583626)
;

-- 2019-09-05T20:11:09.371Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541870,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'The User identifies a unique user in the system. This could be an internal user or a business partner contact',583627,'N',559666,0,'U','Ansprechpartner','User within the system - Internal or Business Partner Contact')
;

-- 2019-09-05T20:11:09.372Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583627 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:09.374Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2019-09-05T20:11:09.386Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583627
;

-- 2019-09-05T20:11:09.387Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583627)
;

-- 2019-09-05T20:11:09.441Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541870,'N',255,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,583628,'N',559669,0,'U','Beschreibung')
;

-- 2019-09-05T20:11:09.442Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583628 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:09.444Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-05T20:11:09.555Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583628
;

-- 2019-09-05T20:11:09.555Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583628)
;

-- 2019-09-05T20:11:09.634Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541870,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:09','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',583629,'N',559670,0,'U','Aktiv','Der Eintrag ist im System aktiv')
;

-- 2019-09-05T20:11:09.635Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583629 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:09.637Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-05T20:11:10.249Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583629
;

-- 2019-09-05T20:11:10.249Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583629)
;

-- 2019-09-05T20:11:10.363Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541870,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,'A Project allows you to track and control internal or external activities.',583630,'N',559675,0,'U','Project','Financial Project')
;

-- 2019-09-05T20:11:10.364Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583630 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:10.365Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-09-05T20:11:10.385Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583630
;

-- 2019-09-05T20:11:10.385Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583630)
;

-- 2019-09-05T20:11:10.441Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541870,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,583631,'N',559677,0,'U','Projektkontakt')
;

-- 2019-09-05T20:11:10.441Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583631 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:10.443Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543957) 
;

-- 2019-09-05T20:11:10.444Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583631
;

-- 2019-09-05T20:11:10.444Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583631)
;

-- 2019-09-05T20:11:10.497Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541870,'N',2,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,583632,'N',560808,0,'U','Projektrolle')
;

-- 2019-09-05T20:11:10.498Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583632 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:10.499Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544216) 
;

-- 2019-09-05T20:11:10.500Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583632
;

-- 2019-09-05T20:11:10.500Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583632)
;

-- 2019-09-05T20:11:10.546Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name,Description) VALUES (541870,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-09-05 22:11:10','YYYY-MM-DD HH24:MI:SS'),100,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',583633,'N',568516,0,'U','Geschäftspartner','Bezeichnet einen Geschäftspartner')
;

-- 2019-09-05T20:11:10.547Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583633 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T20:11:10.548Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-09-05T20:11:10.552Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583633
;

-- 2019-09-05T20:11:10.553Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583633)
;

-- 2019-09-05T20:11:50.207Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 22:11:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583627
;

-- 2019-09-05T20:11:52.607Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 22:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583630
;

-- 2019-09-05T20:11:53.628Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 22:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583632
;

-- 2019-09-05T20:11:56.029Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 22:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583633
;

-- 2019-09-05T20:12:03.541Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541870,541423,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-09-05T20:12:03.542Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541423 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-05T20:12:03.604Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541824,541423,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:03.646Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541825,541423,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:03.692Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541824,542759,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:03.731Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583627,0,541870,542759,560731,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','Ansprechpartner',10,0,0,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:03.783Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583630,0,541870,542759,560732,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','Project',20,0,0,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:03.821Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583632,0,541870,542759,560733,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Projektrolle',30,0,0,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:03.870Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583633,0,540410,560731,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 22:12:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:14.511Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541825
;

-- 2019-09-05T20:12:49.616Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583633,0,541870,542759,560734,'F',TO_TIMESTAMP('2019-09-05 22:12:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geschäftspartner',40,0,0,TO_TIMESTAMP('2019-09-05 22:12:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:12:58.134Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-09-05 22:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560732
;

-- 2019-09-05T20:12:58.137Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-09-05 22:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560734
;

-- 2019-09-05T20:12:58.138Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-09-05 22:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560731
;

-- 2019-09-05T20:12:58.145Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-09-05 22:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560733
;

-- 2019-09-05T20:13:15.813Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-09-05 22:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568516
;

-- 2019-09-05T20:13:16.689Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-09-05 22:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559675
;

-- 2019-09-05T20:13:18.870Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-09-05 22:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560808
;

-- 2019-09-05T20:14:16.855Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,577048,541343,0,540682,TO_TIMESTAMP('2019-09-05 22:14:16','YYYY-MM-DD HH24:MI:SS'),100,'U','Seminarbeteiligte','Y','N','N','N','N','Seminarbeteiligte',TO_TIMESTAMP('2019-09-05 22:14:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:14:16.858Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541343 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-09-05T20:14:16.860Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541343, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541343)
;

-- 2019-09-05T20:14:16.862Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(577048) 
;

-- 2019-09-05T20:14:16.889Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541340, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541341 AND AD_Tree_ID=10
;

-- 2019-09-05T20:14:16.890Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541340, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541342 AND AD_Tree_ID=10
;

-- 2019-09-05T20:14:16.890Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541340, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541343 AND AD_Tree_ID=10
;

-- 2019-09-05T20:17:01.686Z
-- URL zum Konzept
UPDATE AD_Field SET Help=NULL, AD_Name_ID=577040, Name='Seminar', Description=NULL,Updated=TO_TIMESTAMP('2019-09-05 22:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583630
;

-- 2019-09-05T20:17:01.689Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577040) 
;

-- 2019-09-05T20:17:01.705Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583630
;

-- 2019-09-05T20:17:01.707Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583630)
;

-- 2019-09-05T20:17:27.750Z
-- URL zum Konzept
UPDATE AD_Field SET Help='Select Role for with Data Access Restrictions. Beachten Sie, dass Zugriffsinformation im Cache gespeichert werden und daher eine Neuanmeldung oder ein Leeren des Cache erforderlich sind.', AD_Name_ID=572787, Name='Rolle', Description='Role with Data Access Restriction',Updated=TO_TIMESTAMP('2019-09-05 22:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583632
;

-- 2019-09-05T20:17:27.753Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572787) 
;

-- 2019-09-05T20:17:27.760Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583632
;

-- 2019-09-05T20:17:27.763Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583632)
;

-- 2019-09-05T20:17:54.076Z
-- URL zum Konzept
UPDATE AD_Field SET Help='The User identifies a unique user in the system. This could be an internal user or a business partner contact', AD_Name_ID=1002856, Name='Ansprechpartner', Description='User within the system - Internal or Business Partner Contact',Updated=TO_TIMESTAMP('2019-09-05 22:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583631
;

-- 2019-09-05T20:17:54.078Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002856) 
;

-- 2019-09-05T20:17:54.083Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583631
;

-- 2019-09-05T20:17:54.084Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583631)
;


-- 2019-09-05T20:43:00.600Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541035,TO_TIMESTAMP('2019-09-05 22:43:00','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Seminarreihe',TO_TIMESTAMP('2019-09-05 22:43:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-05T20:43:00.602Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541035 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-05T20:43:43.087Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,568648,568647,0,541035,541404,540681,TO_TIMESTAMP('2019-09-05 22:43:43','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2019-09-05 22:43:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T20:43:55.500Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=541035,Updated=TO_TIMESTAMP('2019-09-05 22:43:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583624
;

-- 2019-09-05T20:44:41.565Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-09-05 22:44:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583623
;

