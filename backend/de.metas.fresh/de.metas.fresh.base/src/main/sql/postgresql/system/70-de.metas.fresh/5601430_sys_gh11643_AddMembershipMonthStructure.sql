-- 2021-08-20T14:43:43.152Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541763,'N',TO_TIMESTAMP('2021-08-20 16:43:42','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Membership Month','NP','L','C_MembershipMonth','DTI',TO_TIMESTAMP('2021-08-20 16:43:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:43:43.206Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541763 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-08-20T14:43:43.286Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555479,TO_TIMESTAMP('2021-08-20 16:43:43','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_MembershipMonth',1,'Y','N','Y','Y','C_MembershipMonth','N',1000000,TO_TIMESTAMP('2021-08-20 16:43:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:43:43.337Z
-- URL zum Konzept
CREATE SEQUENCE C_MEMBERSHIPMONTH_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-08-20T14:44:54.695Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575335,102,0,19,541763,129,'AD_Client_ID',TO_TIMESTAMP('2021-08-20 16:44:54','YYYY-MM-DD HH24:MI:SS'),100,'N','@#AD_Client_ID@','Mandant für diese Installation.','D',22,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','Mandant',0,TO_TIMESTAMP('2021-08-20 16:44:54','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:54.724Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575335 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:54.740Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-08-20T14:44:55.820Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575336,113,0,30,541763,104,'AD_Org_ID',TO_TIMESTAMP('2021-08-20 16:44:55','YYYY-MM-DD HH24:MI:SS'),100,'N','@#AD_Org_ID@','Organisatorische Einheit des Mandanten','D',22,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','N','N','N','Sektion',0,TO_TIMESTAMP('2021-08-20 16:44:55','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:55.829Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575336 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:55.830Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-08-20T14:44:56.433Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579557,0,'C_MembershipMonth_ID',TO_TIMESTAMP('2021-08-20 16:44:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Membership Month','Membership Month',TO_TIMESTAMP('2021-08-20 16:44:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:44:56.442Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579557 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-08-20T14:44:56.939Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575337,579557,0,13,541763,'C_MembershipMonth_ID',TO_TIMESTAMP('2021-08-20 16:44:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',22,'Y','N','N','N','N','N','Y','Y','N','N','N','N','N','Membership Month',0,TO_TIMESTAMP('2021-08-20 16:44:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:56.944Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575337 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:56.945Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579557) 
;

-- 2021-08-20T14:44:57.513Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575338,245,0,16,541763,'Created',TO_TIMESTAMP('2021-08-20 16:44:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt',0,TO_TIMESTAMP('2021-08-20 16:44:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:57.519Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575338 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:57.521Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-08-20T14:44:58.036Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575339,246,0,18,110,541763,'CreatedBy',TO_TIMESTAMP('2021-08-20 16:44:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',22,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-08-20 16:44:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:58.043Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575339 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:58.044Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-08-20T14:44:58.618Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575340,275,0,10,541763,'Description',TO_TIMESTAMP('2021-08-20 16:44:58','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,TO_TIMESTAMP('2021-08-20 16:44:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:58.628Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575340 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:58.630Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2021-08-20T14:44:59.366Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575341,326,0,14,541763,'Help',TO_TIMESTAMP('2021-08-20 16:44:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Comment or Hint','D',2000,'The Help field contains a hint, comment or help about the use of this item.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Kommentar/Hilfe',0,TO_TIMESTAMP('2021-08-20 16:44:59','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:59.374Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575341 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:59.377Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(326) 
;

-- 2021-08-20T14:44:59.979Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575342,348,0,20,541763,'IsActive',TO_TIMESTAMP('2021-08-20 16:44:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Aktiv',0,TO_TIMESTAMP('2021-08-20 16:44:59','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:44:59.984Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575342 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:44:59.985Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-08-20T14:45:01.825Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575343,416,0,20,541763,'IsSummary',TO_TIMESTAMP('2021-08-20 16:45:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Dies ist ein Zusammenfassungseintrag','D',1,'A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Zusammenfassungseintrag',0,TO_TIMESTAMP('2021-08-20 16:45:01','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:45:01.835Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575343 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:45:01.839Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(416) 
;

-- 2021-08-20T14:45:03.521Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575344,469,0,10,541763,'Name',TO_TIMESTAMP('2021-08-20 16:45:03','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',60,'','Y','Y','N','N','N','N','Y','N','Y','N','Y','N','N','Y','Name',2,TO_TIMESTAMP('2021-08-20 16:45:03','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:45:03.544Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575344 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:45:03.546Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2021-08-20T14:45:05.659Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575345,542921,0,18,540608,541763,'Parent_Activity_ID',TO_TIMESTAMP('2021-08-20 16:45:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Hauptkostenstelle',0,TO_TIMESTAMP('2021-08-20 16:45:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-20T14:45:05.670Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575345 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:45:05.671Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(542921) 
;

-- 2021-08-20T14:45:06.214Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575346,607,0,16,541763,'Updated',TO_TIMESTAMP('2021-08-20 16:45:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-08-20 16:45:06','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:45:06.225Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575346 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:45:06.226Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-08-20T14:45:07.077Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575347,608,0,18,110,541763,'UpdatedBy',TO_TIMESTAMP('2021-08-20 16:45:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',22,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-08-20 16:45:07','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:45:07.092Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575347 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:45:07.093Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-08-20T14:45:07.983Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575348,620,0,10,541763,'Value',TO_TIMESTAMP('2021-08-20 16:45:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',40,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','Y','N','Y','N','Y','N','Y','N','N','Y','Suchschlüssel',1,TO_TIMESTAMP('2021-08-20 16:45:07','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-20T14:45:07.992Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575348 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:45:07.993Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- 2021-08-20T14:45:33.551Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575340
;

-- 2021-08-20T14:45:33.632Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575340
;

-- 2021-08-20T14:45:34.612Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575341
;

-- 2021-08-20T14:45:34.616Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575341
;

-- 2021-08-20T14:45:35.211Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575343
;

-- 2021-08-20T14:45:35.217Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575343
;

-- 2021-08-20T14:45:35.854Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575345
;

-- 2021-08-20T14:45:35.859Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575345
;

-- 2021-08-20T14:45:36.420Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575348
;

-- 2021-08-20T14:45:36.462Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575348
;

-- 2021-08-20T14:47:17.182Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=540693, AD_Reference_ID=22, ColumnName='month', Description=NULL, EntityType='de.metas.commission_legacy', FieldLength=10, Help=NULL, IsSelectionColumn='N', Name='month',Updated=TO_TIMESTAMP('2021-08-20 16:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575344
;

-- 2021-08-20T14:47:17.195Z
-- URL zum Konzept
UPDATE AD_Field SET Name='month', Description=NULL, Help=NULL WHERE AD_Column_ID=575344
;

-- 2021-08-20T14:47:17.200Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(540693) 
;

-- 2021-08-20T14:48:26.638Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575349,618,0,15,541763,'ValidTo',TO_TIMESTAMP('2021-08-20 16:48:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Gültig bis inklusiv (letzter Tag)','D',0,7,'"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Gültig bis',0,0,TO_TIMESTAMP('2021-08-20 16:48:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-20T14:48:26.656Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575349 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T14:48:26.659Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(618) 
;

-- 2021-08-20T14:48:42.981Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_MembershipMonth (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_MembershipMonth_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, month NUMERIC NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, ValidTo TIMESTAMP WITHOUT TIME ZONE, CONSTRAINT C_MembershipMonth_Key PRIMARY KEY (C_MembershipMonth_ID))
;

-- 2021-08-20T14:49:56.456Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mitgliedschaftsmonat', PrintName='Mitgliedschaftsmonat',Updated=TO_TIMESTAMP('2021-08-20 16:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579557 AND AD_Language='de_CH'
;

-- 2021-08-20T14:49:56.464Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579557,'de_CH') 
;

-- 2021-08-20T14:50:06.399Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mitgliedschaftsmonat', PrintName='Mitgliedschaftsmonat',Updated=TO_TIMESTAMP('2021-08-20 16:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579557 AND AD_Language='de_DE'
;

-- 2021-08-20T14:50:06.411Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579557,'de_DE') 
;

-- 2021-08-20T14:50:06.571Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579557,'de_DE') 
;

-- 2021-08-20T14:50:06.578Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_MembershipMonth_ID', Name='Mitgliedschaftsmonat', Description=NULL, Help=NULL WHERE AD_Element_ID=579557
;

-- 2021-08-20T14:50:06.579Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_MembershipMonth_ID', Name='Mitgliedschaftsmonat', Description=NULL, Help=NULL, AD_Element_ID=579557 WHERE UPPER(ColumnName)='C_MEMBERSHIPMONTH_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-08-20T14:50:06.590Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_MembershipMonth_ID', Name='Mitgliedschaftsmonat', Description=NULL, Help=NULL WHERE AD_Element_ID=579557 AND IsCentrallyMaintained='Y'
;

-- 2021-08-20T14:50:06.590Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Mitgliedschaftsmonat', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579557) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579557)
;

-- 2021-08-20T14:50:06.627Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Mitgliedschaftsmonat', Name='Mitgliedschaftsmonat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579557)
;

-- 2021-08-20T14:50:06.648Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Mitgliedschaftsmonat', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579557
;

-- 2021-08-20T14:50:06.705Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Mitgliedschaftsmonat', Description=NULL, Help=NULL WHERE AD_Element_ID = 579557
;

-- 2021-08-20T14:50:06.712Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Mitgliedschaftsmonat', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579557
;

-- 2021-08-20T14:50:09.828Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-08-20 16:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579557 AND AD_Language='en_US'
;

-- 2021-08-20T14:50:09.829Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579557,'en_US') 
;

-- 2021-08-20T14:50:33.068Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579557,0,541189,TO_TIMESTAMP('2021-08-20 16:50:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Membership month','Y','N','N','N','N','N','N','Y','Mitgliedschaftsmonat','N',TO_TIMESTAMP('2021-08-20 16:50:32','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-08-20T14:50:33.080Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541189 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-08-20T14:50:33.084Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579557) 
;

-- 2021-08-20T14:50:33.090Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541189
;

-- 2021-08-20T14:50:33.108Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541189)
;

-- 2021-08-20T14:50:48.087Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579557,0,544241,541763,541189,'Y',TO_TIMESTAMP('2021-08-20 16:50:48','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_MembershipMonth','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Mitgliedschaftsmonat','N',10,0,TO_TIMESTAMP('2021-08-20 16:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:50:48.099Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544241 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-08-20T14:50:48.101Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579557) 
;

-- 2021-08-20T14:50:48.110Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544241)
;

-- 2021-08-20T14:50:53.346Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575335,652548,0,544241,TO_TIMESTAMP('2021-08-20 16:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-08-20 16:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:50:53.356Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652548 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-20T14:50:53.364Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-08-20T14:50:54.002Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652548
;

-- 2021-08-20T14:50:54.011Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652548)
;

-- 2021-08-20T14:50:54.089Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575336,652549,0,544241,TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:50:54.092Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652549 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-20T14:50:54.094Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-08-20T14:50:54.289Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652549
;

-- 2021-08-20T14:50:54.289Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652549)
;

-- 2021-08-20T14:50:54.364Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575337,652550,0,544241,TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Mitgliedschaftsmonat',TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:50:54.366Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652550 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-20T14:50:54.367Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579557) 
;

-- 2021-08-20T14:50:54.369Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652550
;

-- 2021-08-20T14:50:54.369Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652550)
;

-- 2021-08-20T14:50:54.429Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575342,652551,0,544241,TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:50:54.432Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652551 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-20T14:50:54.433Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-08-20T14:50:54.757Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652551
;

-- 2021-08-20T14:50:54.757Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652551)
;

-- 2021-08-20T14:50:54.830Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575344,652552,0,544241,TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','month',TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:50:54.833Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652552 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-20T14:50:54.834Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540693) 
;

-- 2021-08-20T14:50:54.837Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652552
;

-- 2021-08-20T14:50:54.837Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652552)
;

-- 2021-08-20T14:50:54.894Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575349,652553,0,544241,TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)',7,'D','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','N','N','N','N','N','Gültig bis',TO_TIMESTAMP('2021-08-20 16:50:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:50:54.897Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652553 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-20T14:50:54.899Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(618) 
;

-- 2021-08-20T14:50:54.923Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652553
;

-- 2021-08-20T14:50:54.924Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652553)
;

-- 2021-08-20T14:51:06.041Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544241,543351,TO_TIMESTAMP('2021-08-20 16:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-08-20 16:51:05','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-08-20T14:51:06.058Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543351 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-08-20T14:51:13.375Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544177,543351,TO_TIMESTAMP('2021-08-20 16:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-08-20 16:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:51:18.827Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544177,546312,TO_TIMESTAMP('2021-08-20 16:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','Main',10,TO_TIMESTAMP('2021-08-20 16:51:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:51:40.292Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652552,0,544241,546312,588055,'F',TO_TIMESTAMP('2021-08-20 16:51:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'month',10,0,0,TO_TIMESTAMP('2021-08-20 16:51:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:51:57.068Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652551,0,544241,546312,588056,'F',TO_TIMESTAMP('2021-08-20 16:51:56','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2021-08-20 16:51:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:52:13.317Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652553,0,544241,546312,588057,'F',TO_TIMESTAMP('2021-08-20 16:52:13','YYYY-MM-DD HH24:MI:SS'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','N','N',0,'Gültig bis',30,0,0,TO_TIMESTAMP('2021-08-20 16:52:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:52:19.529Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652549,0,544241,546312,588058,'F',TO_TIMESTAMP('2021-08-20 16:52:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',40,0,0,TO_TIMESTAMP('2021-08-20 16:52:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:52:25.258Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652548,0,544241,546312,588059,'F',TO_TIMESTAMP('2021-08-20 16:52:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2021-08-20 16:52:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:53:08.106Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Month', PrintName='Month',Updated=TO_TIMESTAMP('2021-08-20 16:53:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540693 AND AD_Language='en_GB'
;

-- 2021-08-20T14:53:08.107Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540693,'en_GB') 
;

-- 2021-08-20T14:53:16.952Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Monat', PrintName='Monat',Updated=TO_TIMESTAMP('2021-08-20 16:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540693 AND AD_Language='de_CH'
;

-- 2021-08-20T14:53:16.953Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540693,'de_CH') 
;

-- 2021-08-20T14:53:24.974Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Monat', PrintName='Monat',Updated=TO_TIMESTAMP('2021-08-20 16:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540693 AND AD_Language='en_US'
;

-- 2021-08-20T14:53:24.975Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540693,'en_US') 
;

-- 2021-08-20T14:53:34.121Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Monat', PrintName='Monat',Updated=TO_TIMESTAMP('2021-08-20 16:53:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540693 AND AD_Language='de_DE'
;

-- 2021-08-20T14:53:34.124Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540693,'de_DE') 
;

-- 2021-08-20T14:53:34.159Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(540693,'de_DE') 
;

-- 2021-08-20T14:53:34.161Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='month', Name='Monat', Description=NULL, Help=NULL WHERE AD_Element_ID=540693
;

-- 2021-08-20T14:53:34.162Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='month', Name='Monat', Description=NULL, Help=NULL, AD_Element_ID=540693 WHERE UPPER(ColumnName)='MONTH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-08-20T14:53:34.165Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='month', Name='Monat', Description=NULL, Help=NULL WHERE AD_Element_ID=540693 AND IsCentrallyMaintained='Y'
;

-- 2021-08-20T14:53:34.165Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Monat', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=540693) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 540693)
;

-- 2021-08-20T14:53:34.217Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Monat', Name='Monat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=540693)
;

-- 2021-08-20T14:53:34.218Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Monat', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 540693
;

-- 2021-08-20T14:53:34.220Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Monat', Description=NULL, Help=NULL WHERE AD_Element_ID = 540693
;

-- 2021-08-20T14:53:34.220Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Monat', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 540693
;

-- 2021-08-20T14:53:44.457Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Month', PrintName='Month',Updated=TO_TIMESTAMP('2021-08-20 16:53:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=540693 AND AD_Language='en_US'
;

-- 2021-08-20T14:53:44.458Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(540693,'en_US') 
;

-- 2021-08-20T14:54:13.574Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=541189,Updated=TO_TIMESTAMP('2021-08-20 16:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541763
;

-- 2021-08-20T14:55:38.650Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,579557,541740,0,541189,TO_TIMESTAMP('2021-08-20 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Membership month','Y','N','N','N','N','Mitgliedschaftsmonat',TO_TIMESTAMP('2021-08-20 16:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T14:55:38.660Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541740 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-08-20T14:55:38.662Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541740, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541740)
;

-- 2021-08-20T14:55:38.669Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579557) 
;

-- 2021-08-20T14:55:46.884Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.914Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.915Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.916Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.917Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.918Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.920Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.921Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541412 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.921Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.930Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.931Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.931Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:46.932Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.652Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.658Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.661Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.662Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.662Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.663Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.663Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.664Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.664Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541412 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.664Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.665Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.666Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- 2021-08-20T14:55:56.666Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-----------


-- 2021-08-20T15:32:14.380Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575350,223,0,19,541763,'C_Year_ID',TO_TIMESTAMP('2021-08-20 17:32:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Kalenderjahr','D',0,10,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Jahr',0,0,TO_TIMESTAMP('2021-08-20 17:32:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-20T15:32:14.511Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575350 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-20T15:32:14.553Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(223) 
;

-- 2021-08-20T15:32:19.213Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-08-20 17:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575350
;

-- 2021-08-20T15:32:22.058Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_MembershipMonth','ALTER TABLE public.C_MembershipMonth ADD COLUMN C_Year_ID NUMERIC(10) NOT NULL')
;

-- 2021-08-20T15:32:22.089Z
-- URL zum Konzept
ALTER TABLE C_MembershipMonth ADD CONSTRAINT CYear_CMembershipMonth FOREIGN KEY (C_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- 2021-08-20T15:32:37.691Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575350,652554,0,544241,TO_TIMESTAMP('2021-08-20 17:32:37','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr',10,'D','"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','N','N','N','N','N','N','Jahr',TO_TIMESTAMP('2021-08-20 17:32:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T15:32:37.722Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652554 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-20T15:32:37.729Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(223) 
;

-- 2021-08-20T15:32:37.795Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652554
;

-- 2021-08-20T15:32:37.800Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652554)
;

-- 2021-08-20T15:32:57.805Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652554,0,544241,546312,588060,'F',TO_TIMESTAMP('2021-08-20 17:32:57','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr','"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','N','Y','N','N','N',0,'Jahr',60,0,0,TO_TIMESTAMP('2021-08-20 17:32:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-20T15:33:15.098Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2021-08-20 17:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=588060
;

