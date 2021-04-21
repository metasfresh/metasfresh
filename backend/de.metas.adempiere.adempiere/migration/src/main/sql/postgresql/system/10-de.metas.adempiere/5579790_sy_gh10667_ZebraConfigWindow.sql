-- 2021-02-18T11:35:33.375Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541579,'N',TO_TIMESTAMP('2021-02-18 13:35:30','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'AD_Zebra_Config','NP','L','AD_Zebra_Config','DTI',TO_TIMESTAMP('2021-02-18 13:35:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T11:35:34.224Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541579 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-02-18T11:35:34.844Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555249,TO_TIMESTAMP('2021-02-18 13:35:34','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_Zebra_Config',1,'Y','N','Y','Y','AD_Zebra_Config','N',1000000,TO_TIMESTAMP('2021-02-18 13:35:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T11:35:35.008Z
-- URL zum Konzept
CREATE SEQUENCE AD_ZEBRA_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-02-18T11:37:31.485Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572841,102,0,19,541579,'AD_Client_ID',TO_TIMESTAMP('2021-02-18 13:37:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','Mandant',0,TO_TIMESTAMP('2021-02-18 13:37:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:31.808Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572841 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:31.860Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-02-18T11:37:33.211Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572842,113,0,30,541579,'AD_Org_ID',TO_TIMESTAMP('2021-02-18 13:37:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','N','N','N','Sektion',0,TO_TIMESTAMP('2021-02-18 13:37:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:33.476Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572842 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:33.528Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-02-18T11:37:34.844Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572843,126,0,19,541579,'AD_Table_ID',TO_TIMESTAMP('2021-02-18 13:37:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','DB-Tabelle',0,TO_TIMESTAMP('2021-02-18 13:37:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:35.117Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572843 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:35.171Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(126)
;

-- 2021-02-18T11:37:36.576Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572844,2978,0,19,541579,'CM_Template_ID',TO_TIMESTAMP('2021-02-18 13:37:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Vorlage',0,TO_TIMESTAMP('2021-02-18 13:37:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:36.839Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572844 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:36.892Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2978)
;

-- 2021-02-18T11:37:38.170Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578764,0,'AD_Zebra_Config_ID',TO_TIMESTAMP('2021-02-18 13:37:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Zebra_Config','AD_Zebra_Config',TO_TIMESTAMP('2021-02-18 13:37:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T11:37:38.431Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578764 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-18T11:37:39.163Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572845,578764,0,13,541579,'AD_Zebra_Config_ID',TO_TIMESTAMP('2021-02-18 13:37:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','N','N','N','AD_Zebra_Config',0,TO_TIMESTAMP('2021-02-18 13:37:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:39.265Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572845 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:39.315Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578764)
;

-- 2021-02-18T11:37:40.556Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572846,245,0,16,541579,'Created',TO_TIMESTAMP('2021-02-18 13:37:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt',0,TO_TIMESTAMP('2021-02-18 13:37:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:40.879Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572846 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:40.931Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-02-18T11:37:42.431Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572847,246,0,18,110,541579,'CreatedBy',TO_TIMESTAMP('2021-02-18 13:37:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-02-18 13:37:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:42.705Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572847 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:42.758Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-02-18T11:37:44.086Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572848,275,0,10,541579,'Description',TO_TIMESTAMP('2021-02-18 13:37:43','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,TO_TIMESTAMP('2021-02-18 13:37:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:44.350Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572848 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:44.403Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2021-02-18T11:37:45.600Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572849,348,0,20,541579,'IsActive',TO_TIMESTAMP('2021-02-18 13:37:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Aktiv',0,TO_TIMESTAMP('2021-02-18 13:37:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:45.877Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572849 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:45.929Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-02-18T11:37:47.286Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572850,469,0,10,541579,'Name',TO_TIMESTAMP('2021-02-18 13:37:46','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',120,'','Y','Y','N','N','N','N','Y','N','Y','N','Y','N','N','Y','Name',1,TO_TIMESTAMP('2021-02-18 13:37:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:47.552Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572850 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:47.604Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2021-02-18T11:37:48.797Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572851,2642,0,14,541579,'OtherClause',TO_TIMESTAMP('2021-02-18 13:37:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,TO_TIMESTAMP('2021-02-18 13:37:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:49.066Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572851 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:49.119Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2642)
;

-- 2021-02-18T11:37:50.245Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572852,607,0,16,541579,'Updated',TO_TIMESTAMP('2021-02-18 13:37:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-02-18 13:37:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:50.513Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572852 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:50.566Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-02-18T11:37:51.831Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572853,608,0,18,110,541579,'UpdatedBy',TO_TIMESTAMP('2021-02-18 13:37:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-02-18 13:37:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:52.097Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572853 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:52.149Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-02-18T11:37:53.403Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572854,630,0,14,541579,'WhereClause',TO_TIMESTAMP('2021-02-18 13:37:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Sql WHERE',0,TO_TIMESTAMP('2021-02-18 13:37:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T11:37:53.673Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572854 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T11:37:53.725Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(630)
;

-- 2021-02-18T12:03:05.001Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572841
;

-- 2021-02-18T12:03:05.311Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572841
;

-- 2021-02-18T12:03:08.904Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572842
;

-- 2021-02-18T12:03:09.213Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572842
;

-- 2021-02-18T12:03:12.320Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572843
;

-- 2021-02-18T12:03:12.624Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572843
;

-- 2021-02-18T12:03:15.691Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572845
;

-- 2021-02-18T12:03:16.006Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572845
;

-- 2021-02-18T12:03:19.162Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572844
;

-- 2021-02-18T12:03:19.459Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572844
;

-- 2021-02-18T12:03:22.698Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572846
;

-- 2021-02-18T12:03:23.003Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572846
;

-- 2021-02-18T12:03:25.943Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572847
;

-- 2021-02-18T12:03:26.251Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572847
;

-- 2021-02-18T12:03:29.298Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572848
;

-- 2021-02-18T12:03:29.602Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572848
;

-- 2021-02-18T12:03:32.596Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572849
;

-- 2021-02-18T12:03:32.911Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572849
;

-- 2021-02-18T12:03:36.100Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572850
;

-- 2021-02-18T12:03:36.405Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572850
;

-- 2021-02-18T12:03:39.589Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572851
;

-- 2021-02-18T12:03:39.896Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572851
;

-- 2021-02-18T12:03:42.971Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572852
;

-- 2021-02-18T12:03:43.300Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572852
;

-- 2021-02-18T12:03:46.102Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572853
;

-- 2021-02-18T12:03:46.411Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572853
;

-- 2021-02-18T12:03:49.479Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572854
;

-- 2021-02-18T12:03:49.802Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572854
;

-- 2021-02-18T12:05:38.698Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572855,102,0,19,541579,'AD_Client_ID',TO_TIMESTAMP('2021-02-18 14:05:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','Mandant',0,TO_TIMESTAMP('2021-02-18 14:05:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:38.970Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:39.023Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-02-18T12:05:40.503Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572856,113,0,30,541579,'AD_Org_ID',TO_TIMESTAMP('2021-02-18 14:05:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','N','N','N','Sektion',0,TO_TIMESTAMP('2021-02-18 14:05:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:40.826Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:40.875Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-02-18T12:05:42.132Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572857,126,0,19,541579,'AD_Table_ID',TO_TIMESTAMP('2021-02-18 14:05:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','DB-Tabelle',0,TO_TIMESTAMP('2021-02-18 14:05:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:42.396Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572857 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:42.446Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(126)
;

-- 2021-02-18T12:05:43.678Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572858,2978,0,19,541579,'CM_Template_ID',TO_TIMESTAMP('2021-02-18 14:05:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Vorlage',0,TO_TIMESTAMP('2021-02-18 14:05:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:43.948Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572858 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:43.998Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2978)
;

-- 2021-02-18T12:05:45.330Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572859,578764,0,13,541579,'AD_Zebra_Config_ID',TO_TIMESTAMP('2021-02-18 14:05:44','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','N','N','N','AD_Zebra_Config',0,TO_TIMESTAMP('2021-02-18 14:05:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:45.628Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572859 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:45.700Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578764)
;

-- 2021-02-18T12:05:46.956Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572860,245,0,16,541579,'Created',TO_TIMESTAMP('2021-02-18 14:05:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt',0,TO_TIMESTAMP('2021-02-18 14:05:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:47.223Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572860 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:47.273Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-02-18T12:05:48.472Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572861,246,0,18,110,541579,'CreatedBy',TO_TIMESTAMP('2021-02-18 14:05:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-02-18 14:05:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:48.736Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572861 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:48.795Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-02-18T12:05:50.014Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572862,275,0,10,541579,'Description',TO_TIMESTAMP('2021-02-18 14:05:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,TO_TIMESTAMP('2021-02-18 14:05:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:50.272Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572862 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:50.326Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2021-02-18T12:05:51.488Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572863,348,0,20,541579,'IsActive',TO_TIMESTAMP('2021-02-18 14:05:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Aktiv',0,TO_TIMESTAMP('2021-02-18 14:05:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:51.741Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572863 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:51.797Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-02-18T12:05:53.122Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572864,469,0,10,541579,'Name',TO_TIMESTAMP('2021-02-18 14:05:52','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',120,'','Y','Y','N','N','N','N','Y','N','Y','N','Y','N','N','Y','Name',1,TO_TIMESTAMP('2021-02-18 14:05:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:53.416Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572864 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:53.469Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2021-02-18T12:05:54.777Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572865,2642,0,14,541579,'OtherClause',TO_TIMESTAMP('2021-02-18 14:05:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,TO_TIMESTAMP('2021-02-18 14:05:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:55.041Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572865 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:55.095Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2642)
;

-- 2021-02-18T12:05:56.312Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572866,607,0,16,541579,'Updated',TO_TIMESTAMP('2021-02-18 14:05:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-02-18 14:05:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:56.596Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572866 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:56.647Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-02-18T12:05:58.093Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572867,608,0,18,110,541579,'UpdatedBy',TO_TIMESTAMP('2021-02-18 14:05:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-02-18 14:05:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:05:58.359Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572867 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:05:58.411Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-02-18T12:05:59.738Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572868,630,0,14,541579,'WhereClause',TO_TIMESTAMP('2021-02-18 14:05:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Sql WHERE',0,TO_TIMESTAMP('2021-02-18 14:05:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:06:00.011Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572868 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:06:00.063Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(630)
;

-- 2021-02-18T12:06:18.348Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572855
;

-- 2021-02-18T12:06:18.653Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572855
;

-- 2021-02-18T12:06:21.882Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572856
;

-- 2021-02-18T12:06:22.192Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572856
;

-- 2021-02-18T12:06:25.360Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572857
;

-- 2021-02-18T12:06:25.697Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572857
;

-- 2021-02-18T12:06:28.828Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572859
;

-- 2021-02-18T12:06:29.145Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572859
;

-- 2021-02-18T12:06:32.324Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572858
;

-- 2021-02-18T12:06:32.624Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572858
;

-- 2021-02-18T12:06:35.797Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572860
;

-- 2021-02-18T12:06:36.108Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572860
;

-- 2021-02-18T12:06:38.892Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572861
;

-- 2021-02-18T12:06:39.200Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572861
;

-- 2021-02-18T12:06:42.217Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572862
;

-- 2021-02-18T12:06:42.524Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572862
;

-- 2021-02-18T12:06:45.544Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572863
;

-- 2021-02-18T12:06:45.876Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572863
;

-- 2021-02-18T12:06:49.096Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572864
;

-- 2021-02-18T12:06:49.403Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572864
;

-- 2021-02-18T12:06:52.400Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572865
;

-- 2021-02-18T12:06:52.707Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572865
;

-- 2021-02-18T12:06:55.778Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572866
;

-- 2021-02-18T12:06:56.083Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572866
;

-- 2021-02-18T12:06:58.849Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572867
;

-- 2021-02-18T12:06:59.154Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572867
;

-- 2021-02-18T12:07:02.288Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572868
;

-- 2021-02-18T12:07:02.587Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572868
;

-- 2021-02-18T12:07:45.034Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572869,102,0,19,541579,'AD_Client_ID',TO_TIMESTAMP('2021-02-18 14:07:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-02-18 14:07:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:45.295Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572869 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:45.346Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-02-18T12:07:46.632Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572870,113,0,30,541579,'AD_Org_ID',TO_TIMESTAMP('2021-02-18 14:07:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-02-18 14:07:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:46.991Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572870 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:47.056Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-02-18T12:07:48.280Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572871,245,0,16,541579,'Created',TO_TIMESTAMP('2021-02-18 14:07:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-02-18 14:07:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:48.544Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572871 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:48.596Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-02-18T12:07:49.812Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572872,246,0,18,110,541579,'CreatedBy',TO_TIMESTAMP('2021-02-18 14:07:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-02-18 14:07:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:50.080Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572872 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:50.132Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-02-18T12:07:51.326Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572873,348,0,20,541579,'IsActive',TO_TIMESTAMP('2021-02-18 14:07:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-02-18 14:07:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:51.585Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572873 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:51.637Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-02-18T12:07:52.880Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572874,607,0,16,541579,'Updated',TO_TIMESTAMP('2021-02-18 14:07:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-02-18 14:07:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:53.152Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572874 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:53.204Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-02-18T12:07:54.391Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572875,608,0,18,110,541579,'UpdatedBy',TO_TIMESTAMP('2021-02-18 14:07:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-02-18 14:07:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:54.650Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572875 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:54.702Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-02-18T12:07:56.021Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572876,578764,0,13,541579,'AD_Zebra_Config_ID',TO_TIMESTAMP('2021-02-18 14:07:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','AD_Zebra_Config',0,TO_TIMESTAMP('2021-02-18 14:07:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T12:07:56.294Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T12:07:56.345Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578764)
;

-- 2021-02-18T13:10:04.532Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578765,0,'IsDeafult',TO_TIMESTAMP('2021-02-18 15:10:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Default','Deafult',TO_TIMESTAMP('2021-02-18 15:10:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:10:04.825Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578765 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-18T13:10:15.227Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=578765
;

-- 2021-02-18T13:10:15.534Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=578765
;

-- 2021-02-18T13:12:42.885Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572877,1103,0,20,541579,'IsDefault',TO_TIMESTAMP('2021-02-18 15:12:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Default value','D',0,1,'The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Standard',0,0,TO_TIMESTAMP('2021-02-18 15:12:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T13:12:43.058Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T13:12:43.120Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1103)
;

-- 2021-02-18T13:14:16.060Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578766,0,'SQL_Select',TO_TIMESTAMP('2021-02-18 15:14:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SQL Select ','SQL Select ',TO_TIMESTAMP('2021-02-18 15:14:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:14:16.326Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578766 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-18T13:14:44.053Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572878,578766,0,10,541579,'SQL_Select',TO_TIMESTAMP('2021-02-18 15:14:43','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,5000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SQL Select ',0,0,TO_TIMESTAMP('2021-02-18 15:14:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T13:14:44.176Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572878 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T13:14:44.232Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578766)
;

-- 2021-02-18T13:15:41.021Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578767,0,'Header_Line1',TO_TIMESTAMP('2021-02-18 15:15:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Header Line 1','Header Line 1',TO_TIMESTAMP('2021-02-18 15:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:15:41.277Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578767 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-18T13:16:08.515Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578768,0,'Header_Line2',TO_TIMESTAMP('2021-02-18 15:16:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Header Line 2','Header Line 2',TO_TIMESTAMP('2021-02-18 15:16:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:16:08.624Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578768 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-18T13:16:29.962Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='Encoding', Name='Encoding', PrintName='Encoding',Updated=TO_TIMESTAMP('2021-02-18 15:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578768
;

-- 2021-02-18T13:16:30.240Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID=578768
;

-- 2021-02-18T13:16:30.292Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL, AD_Element_ID=578768 WHERE UPPER(ColumnName)='ENCODING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-18T13:16:30.348Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID=578768 AND IsCentrallyMaintained='Y'
;

-- 2021-02-18T13:16:30.398Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Encoding', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578768) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578768)
;

-- 2021-02-18T13:16:30.493Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Encoding', Name='Encoding' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578768)
;

-- 2021-02-18T13:16:30.548Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Encoding', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578768
;

-- 2021-02-18T13:16:30.602Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID = 578768
;

-- 2021-02-18T13:16:30.655Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Encoding', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578768
;

-- 2021-02-18T13:17:51.472Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572879,578767,0,10,541579,'Header_Line1',TO_TIMESTAMP('2021-02-18 15:17:50','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Header Line 1',0,0,TO_TIMESTAMP('2021-02-18 15:17:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T13:17:51.591Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572879 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T13:17:51.675Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578767)
;

-- 2021-02-18T13:18:00.448Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-18 15:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572879
;

-- 2021-02-18T13:19:05.017Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578769,0,'Header_Line2',TO_TIMESTAMP('2021-02-18 15:19:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Header Line 2','Header Line 2',TO_TIMESTAMP('2021-02-18 15:19:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:19:05.296Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578769 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-18T13:19:43.432Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572880,578769,0,10,541579,'Header_Line2',TO_TIMESTAMP('2021-02-18 15:19:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Header Line 2',0,0,TO_TIMESTAMP('2021-02-18 15:19:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T13:19:43.546Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572880 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T13:19:43.599Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578769)
;

-- 2021-02-18T13:20:12.346Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-18 15:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572878
;


-- 2021-02-18T13:27:08.640Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,578764,0,541038,TO_TIMESTAMP('2021-02-18 15:27:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','AD_Zebra_Config','N',TO_TIMESTAMP('2021-02-18 15:27:07','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2021-02-18T13:27:08.901Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541038 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-02-18T13:27:08.953Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(578764)
;

-- 2021-02-18T13:27:09.062Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541038
;

-- 2021-02-18T13:27:09.115Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541038)
;

-- 2021-02-18T13:29:32.630Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,578764,0,543430,541579,541038,'Y',TO_TIMESTAMP('2021-02-18 15:29:31','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','AD_Zebra_Config','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'AD_Zebra_Config','N',10,0,TO_TIMESTAMP('2021-02-18 15:29:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:29:32.754Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543430 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-02-18T13:29:32.842Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(578764)
;

-- 2021-02-18T13:29:32.903Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543430)
;

-- 2021-02-18T13:31:36.240Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543430,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-02-18 15:31:35','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',631834,'N',572869,'D','Mandant','Mandant für diese Installation.',0)
;

-- 2021-02-18T13:31:36.344Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:36.420Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2021-02-18T13:31:36.634Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631834
;

-- 2021-02-18T13:31:36.686Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631834)
;

-- 2021-02-18T13:31:37.408Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543430,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:36','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-18 15:31:36','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',631835,'N',572870,'D','Sektion','Organisatorische Einheit des Mandanten',0)
;

-- 2021-02-18T13:31:37.557Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:37.608Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2021-02-18T13:31:37.840Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631835
;

-- 2021-02-18T13:31:37.890Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631835)
;

-- 2021-02-18T13:31:38.616Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543430,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:37','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-18 15:31:37','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',631836,'N',572873,'D','Aktiv','Der Eintrag ist im System aktiv',0)
;

-- 2021-02-18T13:31:38.722Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:38.773Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2021-02-18T13:31:39.338Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631836
;

-- 2021-02-18T13:31:39.388Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631836)
;

-- 2021-02-18T13:31:40.087Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543430,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-18 15:31:39','YYYY-MM-DD HH24:MI:SS'),100,631837,'N',572876,'D','AD_Zebra_Config',0)
;

-- 2021-02-18T13:31:40.190Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:40.242Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578764)
;

-- 2021-02-18T13:31:40.293Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631837
;

-- 2021-02-18T13:31:40.341Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631837)
;

-- 2021-02-18T13:31:41.038Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543430,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:40','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-18 15:31:40','YYYY-MM-DD HH24:MI:SS'),100,'The Default Checkbox indicates if this record will be used as a default value.',631838,'N',572877,'D','Standard','Default value',0)
;

-- 2021-02-18T13:31:41.144Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:41.195Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1103)
;

-- 2021-02-18T13:31:41.269Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631838
;

-- 2021-02-18T13:31:41.319Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631838)
;

-- 2021-02-18T13:31:42.021Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543430,'N',5000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-18 15:31:41','YYYY-MM-DD HH24:MI:SS'),100,631839,'N',572878,'D','SQL Select ',0)
;

-- 2021-02-18T13:31:42.125Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:42.177Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578766)
;

-- 2021-02-18T13:31:42.229Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631839
;

-- 2021-02-18T13:31:42.279Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631839)
;

-- 2021-02-18T13:31:43.002Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543430,'N',1000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:42','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-18 15:31:42','YYYY-MM-DD HH24:MI:SS'),100,631840,'N',572879,'D','Header Line 1',0)
;

-- 2021-02-18T13:31:43.106Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:43.157Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578767)
;

-- 2021-02-18T13:31:43.207Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631840
;

-- 2021-02-18T13:31:43.256Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631840)
;

-- 2021-02-18T13:31:43.958Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543430,'N',1000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-18 15:31:43','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-18 15:31:43','YYYY-MM-DD HH24:MI:SS'),100,631841,'N',572880,'D','Header Line 2',0)
;

-- 2021-02-18T13:31:44.060Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T13:31:44.113Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578769)
;

-- 2021-02-18T13:31:44.168Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631841
;

-- 2021-02-18T13:31:44.217Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631841)
;

-- 2021-02-18T13:32:27.929Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543430,542607,TO_TIMESTAMP('2021-02-18 15:32:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-02-18 15:32:27','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-02-18T13:32:28.104Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542607 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-02-18T13:32:28.687Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543306,542607,TO_TIMESTAMP('2021-02-18 15:32:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-02-18 15:32:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:32:29.243Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543307,542607,TO_TIMESTAMP('2021-02-18 15:32:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-02-18 15:32:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:32:29.865Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543306,544954,TO_TIMESTAMP('2021-02-18 15:32:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-02-18 15:32:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:34:02.617Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631839,0,543430,544954,577959,'F',TO_TIMESTAMP('2021-02-18 15:34:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SQL Select ',10,0,0,TO_TIMESTAMP('2021-02-18 15:34:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:34:34.440Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631840,0,543430,544954,577960,'F',TO_TIMESTAMP('2021-02-18 15:34:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Header Line 1',20,0,0,TO_TIMESTAMP('2021-02-18 15:34:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:35:04.067Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631841,0,543430,544954,577961,'F',TO_TIMESTAMP('2021-02-18 15:35:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Header Line 2',20,0,0,TO_TIMESTAMP('2021-02-18 15:35:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:48:19.306Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-02-18 15:48:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577961
;

-- 2021-02-18T13:50:54.332Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543307,544955,TO_TIMESTAMP('2021-02-18 15:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2021-02-18 15:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T13:53:55.683Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,543430,544955,577963,'F',TO_TIMESTAMP('2021-02-18 15:53:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsDefault',10,0,0,TO_TIMESTAMP('2021-02-18 15:53:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T14:14:07.538Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=577963
;

-- 2021-02-18T14:14:41.904Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543307,544956,TO_TIMESTAMP('2021-02-18 16:14:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','orgs',20,TO_TIMESTAMP('2021-02-18 16:14:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T14:15:25.211Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631834,0,543430,544956,577965,'F',TO_TIMESTAMP('2021-02-18 16:15:24','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2021-02-18 16:15:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T14:15:54.815Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631835,0,543430,544956,577966,'F',TO_TIMESTAMP('2021-02-18 16:15:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2021-02-18 16:15:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T14:16:14.342Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-02-18 16:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577966
;

-- 2021-02-18T14:17:15.555Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-02-18 16:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577965
;

-- 2021-02-18T14:17:49.107Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631836,0,543430,544955,577968,'F',TO_TIMESTAMP('2021-02-18 16:17:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2021-02-18 16:17:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T14:18:16.101Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631838,0,543430,544955,577969,'F',TO_TIMESTAMP('2021-02-18 16:18:15','YYYY-MM-DD HH24:MI:SS'),100,'Default value','The Default Checkbox indicates if this record will be used as a default value.','Y','N','N','Y','N','N','N',0,'Standard',20,0,0,TO_TIMESTAMP('2021-02-18 16:18:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T14:42:46.971Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,578764,541599,0,541038,TO_TIMESTAMP('2021-02-18 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'D','AD_Zebra_Config','Y','N','N','N','N','AD_Zebra_Config',TO_TIMESTAMP('2021-02-18 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T14:42:47.281Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541599 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-02-18T14:42:47.335Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541599, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541599)
;

-- 2021-02-18T14:42:47.433Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(578764)
;

-- 2021-02-18T14:42:50.314Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.369Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.421Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.470Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.527Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.576Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.624Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.673Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.724Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.777Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.826Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.886Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.940Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:50.988Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.039Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.090Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.141Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.196Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.245Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.295Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.366Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.420Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.469Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.520Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.576Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.625Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.676Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.725Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.776Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.829Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.881Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.931Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:51.982Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.036Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.086Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.141Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.190Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.245Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.293Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.342Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.442Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.490Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.592Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.641Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.693Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.744Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.794Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.845Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.895Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.946Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:52.995Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.049Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.099Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.151Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.203Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.253Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.304Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.355Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541494 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.410Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.458Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.507Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.564Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.613Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.662Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.711Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:53.764Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.552Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.608Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.658Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.707Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.756Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.811Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.858Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.910Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:58.961Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.015Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.063Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.114Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.166Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.218Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.268Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.318Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.369Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.418Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.468Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.520Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.571Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.621Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.672Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.726Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.775Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.827Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.878Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.929Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2021-02-18T14:42:59.981Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.031Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.082Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.133Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.187Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.237Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.287Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.339Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.394Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.445Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.496Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.550Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.600Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.649Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.702Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.757Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.805Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.856Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.906Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:00.958Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.011Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.062Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.120Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.182Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.235Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.284Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.337Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.386Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.439Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.490Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.591Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.644Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541494 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.693Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.746Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.796Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.849Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.898Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:01.951Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2021-02-18T14:43:02.001Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2021-02-18T14:47:53.579Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-02-18 16:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551612
;

-- 2021-02-18T14:47:59.745Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bp_printformat','AD_PrintFormat_ID','NUMERIC(10)',null,null)
;

-- 2021-02-18T14:47:59.803Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_bp_printformat','AD_PrintFormat_ID',null,'NULL',null)
;

-- 2021-02-18T14:52:33.005Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572881,578764,0,19,540638,'AD_Zebra_Config_ID',TO_TIMESTAMP('2021-02-18 16:52:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'AD_Zebra_Config',0,0,TO_TIMESTAMP('2021-02-18 16:52:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T14:52:33.316Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572881 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T14:52:33.366Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578764)
;

-- 2021-02-18T14:53:00.389Z
-- URL zum Konzept
ALTER TABLE C_BP_PrintFormat ADD CONSTRAINT ADZebraConfig_CBPPrintFormat FOREIGN KEY (AD_Zebra_Config_ID) REFERENCES public.AD_Zebra_Config DEFERRABLE INITIALLY DEFERRED
;

-- 2021-02-18T15:22:26.471Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572882,578768,0,10,541579,'Encoding',TO_TIMESTAMP('2021-02-18 17:22:25','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Encoding',0,0,TO_TIMESTAMP('2021-02-18 17:22:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-18T15:22:26.803Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572882 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T15:22:26.854Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578768)
;

-- 2021-02-18T15:24:51.930Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572882
;

-- 2021-02-18T15:24:52.555Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572882
;

-- 2021-02-18T15:30:43.315Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Encoding', PrintName='Encoding',Updated=TO_TIMESTAMP('2021-02-18 17:30:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578768 AND AD_Language='de_CH'
;

-- 2021-02-18T15:30:43.367Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578768,'de_CH')
;

-- 2021-02-18T15:30:58.010Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Encoding', PrintName='Encoding',Updated=TO_TIMESTAMP('2021-02-18 17:30:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578768 AND AD_Language='de_DE'
;

-- 2021-02-18T15:30:58.062Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578768,'de_DE')
;

-- 2021-02-18T15:30:58.176Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578768,'de_DE')
;

-- 2021-02-18T15:30:58.261Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID=578768
;

-- 2021-02-18T15:30:58.359Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL, AD_Element_ID=578768 WHERE UPPER(ColumnName)='ENCODING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-18T15:30:58.460Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID=578768 AND IsCentrallyMaintained='Y'
;

-- 2021-02-18T15:30:58.511Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Encoding', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578768) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578768)
;

-- 2021-02-18T15:30:58.574Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Encoding', Name='Encoding' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578768)
;

-- 2021-02-18T15:30:58.624Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Encoding', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578768
;

-- 2021-02-18T15:30:58.674Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID = 578768
;

-- 2021-02-18T15:30:58.766Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Encoding', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578768
;

-- 2021-02-18T15:32:19.055Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID) VALUES (10,1000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-02-18 17:32:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-02-18 17:32:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541579,'N',572883,'N','N','N','N','N','N','N','N',0,'D','N','N','Encoding','N','Encoding',0,'N',0,0,578768)
;

-- 2021-02-18T15:32:19.309Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572883 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T15:32:19.403Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578768)
;

-- 2021-02-18T15:32:27.612Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-18 17:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572883
;

-- 2021-02-18T15:32:37.014Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_zebra_config','Encoding','VARCHAR(1000)',null,null)
;

-- 2021-02-18T15:32:37.113Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_zebra_config','Encoding',null,'NOT NULL',null)
;

-- 2021-02-18T15:39:18.478Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_zebra_config','Encoding','VARCHAR(1000)',null,null)
;

-- 2021-02-18T15:40:41.112Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572883
;

-- 2021-02-18T15:40:41.420Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=572883
;

-- 2021-02-18T15:42:48.444Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=578768
;

-- 2021-02-18T15:42:48.769Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=578768
;

-- 2021-02-18T15:43:31.339Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578770,0,TO_TIMESTAMP('2021-02-18 17:43:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Encoding','Encoding',TO_TIMESTAMP('2021-02-18 17:43:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T15:43:31.444Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578770 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-18T15:45:08.388Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='Encoding',Updated=TO_TIMESTAMP('2021-02-18 17:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578770
;

-- 2021-02-18T15:45:08.740Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID=578770
;

-- 2021-02-18T15:45:08.821Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL, AD_Element_ID=578770 WHERE UPPER(ColumnName)='ENCODING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-18T15:45:08.873Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Encoding', Description=NULL, Help=NULL WHERE AD_Element_ID=578770 AND IsCentrallyMaintained='Y'
;

-- 2021-02-18T15:45:42.104Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID) VALUES (10,1000,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-02-18 17:45:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-02-18 17:45:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541579,'N',572884,'N','N','N','N','N','N','N','N',0,'D','N','N','Encoding','N','Encoding',0,'N',0,0,578770)
;

-- 2021-02-18T15:45:42.222Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572884 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-18T15:45:42.280Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578770)
;

-- 2021-02-18T15:45:49.877Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-18 17:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572884
;

-- 2021-02-18T15:45:56.605Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_zebra_config','Encoding','VARCHAR(1000)',null,null)
;

-- 2021-02-18T15:47:01.056Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572884,631850,578770,0,543430,0,TO_TIMESTAMP('2021-02-18 17:47:00','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Encoding',10,10,0,1,1,TO_TIMESTAMP('2021-02-18 17:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-18T15:47:01.434Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-18T15:47:01.485Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578770)
;

-- 2021-02-18T15:47:01.540Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631850
;

-- 2021-02-18T15:47:01.589Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631850)
;

-- 2021-02-19T07:12:46.953Z
-- URL zum Konzept
UPDATE AD_Window_Trl SET Name='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Window_ID=541038
;

-- 2021-02-19T07:13:13.629Z
-- URL zum Konzept
UPDATE AD_Window_Trl SET Name='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Window_ID=541038
;

-- 2021-02-19T07:13:19.303Z
-- URL zum Konzept
UPDATE AD_Window_Trl SET Name='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Window_ID=541038
;

-- 2021-02-19T07:13:49.699Z
-- URL zum Konzept
UPDATE AD_Element SET Name='Zebra Config', PrintName='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764
;

-- 2021-02-19T07:13:50.277Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Zebra_Config_ID', Name='Zebra Config', Description=NULL, Help=NULL WHERE AD_Element_ID=578764
;

-- 2021-02-19T07:13:50.328Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Zebra_Config_ID', Name='Zebra Config', Description=NULL, Help=NULL, AD_Element_ID=578764 WHERE UPPER(ColumnName)='AD_ZEBRA_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-19T07:13:50.390Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Zebra_Config_ID', Name='Zebra Config', Description=NULL, Help=NULL WHERE AD_Element_ID=578764 AND IsCentrallyMaintained='Y'
;

-- 2021-02-19T07:13:50.447Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zebra Config', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578764) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578764)
;

-- 2021-02-19T07:13:50.530Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zebra Config', Name='Zebra Config' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578764)
;

-- 2021-02-19T07:13:50.584Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zebra Config', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:13:50.637Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zebra Config', Description=NULL, Help=NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:13:50.695Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zebra Config', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:13:58.923Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zebra Config', PrintName='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764 AND AD_Language='de_CH'
;

-- 2021-02-19T07:13:59.049Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578764,'de_CH') 
;

-- 2021-02-19T07:14:09.533Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zebra Config', PrintName='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:14:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764 AND AD_Language='de_DE'
;

-- 2021-02-19T07:14:09.589Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578764,'de_DE') 
;

-- 2021-02-19T07:14:09.716Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578764,'de_DE') 
;

-- 2021-02-19T07:14:09.769Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Zebra_Config_ID', Name='Zebra Config', Description=NULL, Help=NULL WHERE AD_Element_ID=578764
;

-- 2021-02-19T07:14:09.822Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Zebra_Config_ID', Name='Zebra Config', Description=NULL, Help=NULL, AD_Element_ID=578764 WHERE UPPER(ColumnName)='AD_ZEBRA_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-19T07:14:09.874Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Zebra_Config_ID', Name='Zebra Config', Description=NULL, Help=NULL WHERE AD_Element_ID=578764 AND IsCentrallyMaintained='Y'
;

-- 2021-02-19T07:14:09.928Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zebra Config', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578764) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578764)
;

-- 2021-02-19T07:14:10.011Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zebra Config', Name='Zebra Config' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578764)
;

-- 2021-02-19T07:14:10.066Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zebra Config', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:14:10.127Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zebra Config', Description=NULL, Help=NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:14:10.194Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zebra Config', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:14:19.630Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Zebra Config', PrintName='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:14:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764 AND AD_Language='en_US'
;

-- 2021-02-19T07:14:19.685Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578764,'en_US') 
;

-- 2021-02-19T07:14:27.164Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Zebra Config', PrintName='Zebra Config',Updated=TO_TIMESTAMP('2021-02-19 09:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764 AND AD_Language='nl_NL'
;

-- 2021-02-19T07:14:27.216Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578764,'nl_NL') 
;

-- 2021-02-19T07:14:32.456Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764 AND AD_Language='nl_NL'
;

-- 2021-02-19T07:14:32.510Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578764,'nl_NL') 
;

-- 2021-02-19T07:14:36.872Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764 AND AD_Language='en_US'
;

-- 2021-02-19T07:14:36.924Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578764,'en_US') 
;

-- 2021-02-19T07:15:52.396Z
-- URL zum Konzept
UPDATE AD_Element SET Name='AD_Zebra_Config_ID', PrintName='AD_Zebra_Config_ID',Updated=TO_TIMESTAMP('2021-02-19 09:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578764
;

-- 2021-02-19T07:15:52.717Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Zebra_Config_ID', Name='AD_Zebra_Config_ID', Description=NULL, Help=NULL WHERE AD_Element_ID=578764
;

-- 2021-02-19T07:15:52.770Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Zebra_Config_ID', Name='AD_Zebra_Config_ID', Description=NULL, Help=NULL, AD_Element_ID=578764 WHERE UPPER(ColumnName)='AD_ZEBRA_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-19T07:15:52.821Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Zebra_Config_ID', Name='AD_Zebra_Config_ID', Description=NULL, Help=NULL WHERE AD_Element_ID=578764 AND IsCentrallyMaintained='Y'
;

-- 2021-02-19T07:15:52.873Z
-- URL zum Konzept
UPDATE AD_Field SET Name='AD_Zebra_Config_ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578764) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578764)
;

-- 2021-02-19T07:15:52.970Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='AD_Zebra_Config_ID', Name='AD_Zebra_Config_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578764)
;

-- 2021-02-19T07:15:53.024Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='AD_Zebra_Config_ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:15:53.080Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='AD_Zebra_Config_ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:15:53.133Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'AD_Zebra_Config_ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578764
;

-- 2021-02-19T07:17:27.354Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631850,0,543430,544954,577985,'F',TO_TIMESTAMP('2021-02-19 09:17:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Encoding',40,0,0,TO_TIMESTAMP('2021-02-19 09:17:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T07:29:25.021Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578766 AND AD_Language='de_CH'
;

-- 2021-02-19T07:29:25.087Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578766,'de_CH') 
;

-- 2021-02-19T07:29:29.918Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:29:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578766 AND AD_Language='de_DE'
;

-- 2021-02-19T07:29:29.973Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578766,'de_DE') 
;

-- 2021-02-19T07:29:30.114Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578766,'de_DE') 
;

-- 2021-02-19T07:29:35.373Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578766 AND AD_Language='en_US'
;

-- 2021-02-19T07:29:35.432Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578766,'en_US') 
;

-- 2021-02-19T07:29:40.468Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:29:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578766 AND AD_Language='nl_NL'
;

-- 2021-02-19T07:29:40.521Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578766,'nl_NL') 
;

-- 2021-02-19T07:30:37.664Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kopfzeile 1', PrintName='Kopfzeile 1',Updated=TO_TIMESTAMP('2021-02-19 09:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578767 AND AD_Language='de_CH'
;

-- 2021-02-19T07:30:37.717Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578767,'de_CH') 
;

-- 2021-02-19T07:30:45.686Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kopfzeile 1', PrintName='Kopfzeile 1',Updated=TO_TIMESTAMP('2021-02-19 09:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578767 AND AD_Language='de_DE'
;

-- 2021-02-19T07:30:45.737Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578767,'de_DE') 
;

-- 2021-02-19T07:30:45.849Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578767,'de_DE') 
;

-- 2021-02-19T07:30:45.900Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Header_Line1', Name='Kopfzeile 1', Description=NULL, Help=NULL WHERE AD_Element_ID=578767
;

-- 2021-02-19T07:30:45.951Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Header_Line1', Name='Kopfzeile 1', Description=NULL, Help=NULL, AD_Element_ID=578767 WHERE UPPER(ColumnName)='HEADER_LINE1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-19T07:30:46.003Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Header_Line1', Name='Kopfzeile 1', Description=NULL, Help=NULL WHERE AD_Element_ID=578767 AND IsCentrallyMaintained='Y'
;

-- 2021-02-19T07:30:46.056Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Kopfzeile 1', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578767) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578767)
;

-- 2021-02-19T07:30:46.121Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Kopfzeile 1', Name='Kopfzeile 1' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578767)
;

-- 2021-02-19T07:30:46.182Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Kopfzeile 1', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578767
;

-- 2021-02-19T07:30:46.234Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Kopfzeile 1', Description=NULL, Help=NULL WHERE AD_Element_ID = 578767
;

-- 2021-02-19T07:30:46.287Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Kopfzeile 1', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578767
;

-- 2021-02-19T07:31:02.772Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kopfzeile 1', PrintName='Kopfzeile 1',Updated=TO_TIMESTAMP('2021-02-19 09:31:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578767 AND AD_Language='en_US'
;

-- 2021-02-19T07:31:02.826Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578767,'en_US') 
;

-- 2021-02-19T07:31:14.094Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Header Line 1', PrintName='Header Line 1',Updated=TO_TIMESTAMP('2021-02-19 09:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578767 AND AD_Language='en_US'
;

-- 2021-02-19T07:31:14.146Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578767,'en_US') 
;

-- 2021-02-19T07:31:25.546Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kopfzeile 1', PrintName='Kopfzeile 1',Updated=TO_TIMESTAMP('2021-02-19 09:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578767 AND AD_Language='nl_NL'
;

-- 2021-02-19T07:31:25.612Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578767,'nl_NL') 
;

-- 2021-02-19T07:31:50.462Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kopfzeile 2', PrintName='Kopfzeile 2',Updated=TO_TIMESTAMP('2021-02-19 09:31:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578769 AND AD_Language='de_CH'
;

-- 2021-02-19T07:31:50.515Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578769,'de_CH') 
;

-- 2021-02-19T07:31:57.737Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kopfzeile 2', PrintName='Kopfzeile 2',Updated=TO_TIMESTAMP('2021-02-19 09:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578769 AND AD_Language='de_DE'
;

-- 2021-02-19T07:31:57.790Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578769,'de_DE') 
;

-- 2021-02-19T07:31:57.905Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578769,'de_DE') 
;

-- 2021-02-19T07:31:57.966Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Header_Line2', Name='Kopfzeile 2', Description=NULL, Help=NULL WHERE AD_Element_ID=578769
;

-- 2021-02-19T07:31:58.019Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Header_Line2', Name='Kopfzeile 2', Description=NULL, Help=NULL, AD_Element_ID=578769 WHERE UPPER(ColumnName)='HEADER_LINE2' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-19T07:31:58.069Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Header_Line2', Name='Kopfzeile 2', Description=NULL, Help=NULL WHERE AD_Element_ID=578769 AND IsCentrallyMaintained='Y'
;

-- 2021-02-19T07:31:58.120Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Kopfzeile 2', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578769) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578769)
;

-- 2021-02-19T07:31:58.188Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Kopfzeile 2', Name='Kopfzeile 2' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578769)
;

-- 2021-02-19T07:31:58.250Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Kopfzeile 2', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578769
;

-- 2021-02-19T07:31:58.305Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Kopfzeile 2', Description=NULL, Help=NULL WHERE AD_Element_ID = 578769
;

-- 2021-02-19T07:31:58.357Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Kopfzeile 2', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578769
;

-- 2021-02-19T07:32:04.439Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:32:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578769 AND AD_Language='en_US'
;

-- 2021-02-19T07:32:04.492Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578769,'en_US') 
;

-- 2021-02-19T07:32:12.911Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Kopfzeile 2', PrintName='Kopfzeile 2',Updated=TO_TIMESTAMP('2021-02-19 09:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578769 AND AD_Language='nl_NL'
;

-- 2021-02-19T07:32:12.965Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578769,'nl_NL') 
;

-- 2021-02-19T07:34:37.450Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zeichensatz', PrintName='Zeichensatz',Updated=TO_TIMESTAMP('2021-02-19 09:34:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578770 AND AD_Language='de_CH'
;

-- 2021-02-19T07:34:37.503Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578770,'de_CH') 
;

-- 2021-02-19T07:34:46.809Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zeichensatz', PrintName='Zeichensatz',Updated=TO_TIMESTAMP('2021-02-19 09:34:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578770 AND AD_Language='de_DE'
;

-- 2021-02-19T07:34:46.860Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578770,'de_DE') 
;

-- 2021-02-19T07:34:46.973Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578770,'de_DE') 
;

-- 2021-02-19T07:34:47.028Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Encoding', Name='Zeichensatz', Description=NULL, Help=NULL WHERE AD_Element_ID=578770
;

-- 2021-02-19T07:34:47.080Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Zeichensatz', Description=NULL, Help=NULL, AD_Element_ID=578770 WHERE UPPER(ColumnName)='ENCODING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-19T07:34:47.184Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Encoding', Name='Zeichensatz', Description=NULL, Help=NULL WHERE AD_Element_ID=578770 AND IsCentrallyMaintained='Y'
;

-- 2021-02-19T07:34:47.235Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zeichensatz', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578770) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578770)
;

-- 2021-02-19T07:34:47.300Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zeichensatz', Name='Zeichensatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578770)
;

-- 2021-02-19T07:34:47.357Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zeichensatz', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578770
;

-- 2021-02-19T07:34:47.411Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zeichensatz', Description=NULL, Help=NULL WHERE AD_Element_ID = 578770
;

-- 2021-02-19T07:34:47.463Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zeichensatz', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578770
;

-- 2021-02-19T07:34:54.497Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-19 09:34:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578770 AND AD_Language='en_US'
;

-- 2021-02-19T07:34:54.548Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578770,'en_US') 
;

-- 2021-02-19T07:35:02.363Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zeichensatz', PrintName='Zeichensatz',Updated=TO_TIMESTAMP('2021-02-19 09:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578770 AND AD_Language='nl_NL'
;

-- 2021-02-19T07:35:02.416Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578770,'nl_NL') 
;

-- 2021-02-19T08:04:51.702Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545025,0,TO_TIMESTAMP('2021-02-19 10:04:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The selected EDI DESADV entries have different Zebra Configs','I',TO_TIMESTAMP('2021-02-19 10:04:51','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_ZebraConfigError')
;

-- 2021-02-19T08:04:51.760Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545025 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-02-19T09:28:44.868Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description,AD_Org_ID,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID) VALUES (10,100,0,'N','N','N','Y',1,0,'Y',TO_TIMESTAMP('2021-02-19 11:28:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-02-19 11:28:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541579,'N','',572888,'N','N','N','N','N','N','N','N',0,'D','N','N','Name','N','Name','',0,'N',0,0,469)
;

-- 2021-02-19T09:28:45.516Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572888 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-19T09:28:45.567Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2021-02-19T09:29:26.404Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-19 11:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572888
;

-- 2021-02-19T09:29:35.777Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_zebra_config','Name','VARCHAR(100)',null,null)
;

-- 2021-02-19T09:29:35.838Z
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_zebra_config','Name',null,'NOT NULL',null)
;

-- 2021-02-19T09:37:19.289Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572888,631853,469,0,543430,0,TO_TIMESTAMP('2021-02-19 11:37:18','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D','',0,'Y','Y','Y','N','N','N','N','N','Name',20,20,0,1,1,TO_TIMESTAMP('2021-02-19 11:37:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T09:37:19.709Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=631853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-19T09:37:19.762Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2021-02-19T09:37:20.025Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=631853
;

-- 2021-02-19T09:37:20.078Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(631853)
;

-- 2021-02-19T09:38:07.623Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,631853,0,543430,544954,577988,'F',TO_TIMESTAMP('2021-02-19 11:38:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',50,0,0,TO_TIMESTAMP('2021-02-19 11:38:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T09:39:06.260Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2021-02-19 11:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577988
;

-- 2021-02-19T09:46:41.277Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545026,0,TO_TIMESTAMP('2021-02-19 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zebra Configuration is missing.','E',TO_TIMESTAMP('2021-02-19 11:46:40','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_NoZebraConfigDefined')
;

-- 2021-02-19T09:46:41.334Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545026 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572881,632153,578764,0,540653,0,TO_TIMESTAMP('2021-02-19 15:46:07','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','AD_Zebra_Config_ID',60,50,0,1,1,TO_TIMESTAMP('2021-02-19 15:46:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T13:46:09.420Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=632153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-19T13:46:09.495Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578764)
;

-- 2021-02-19T13:46:09.550Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=632153
;

-- 2021-02-19T13:46:09.609Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(632153)
;

-- 2021-02-19T13:47:27.183Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,632153,0,540653,1000037,578119,'F',TO_TIMESTAMP('2021-02-19 15:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AD_Zebra_Config_ID',45,0,0,TO_TIMESTAMP('2021-02-19 15:47:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-19T13:47:45.061Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-02-19 15:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578119
;

-- 2021-02-19T13:47:45.327Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-02-19 15:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000354
;

-- 2021-02-19T13:47:45.582Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-02-19 15:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546568
;


