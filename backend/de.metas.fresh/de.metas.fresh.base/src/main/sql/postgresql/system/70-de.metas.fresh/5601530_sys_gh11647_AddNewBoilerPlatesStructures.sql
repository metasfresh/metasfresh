-- 2021-08-23T15:12:57.459Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541765,'N',TO_TIMESTAMP('2021-08-23 17:12:57','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Product Category Boiler Plate','NP','L','AD_Product_Category_BoilerPlate','DTI',TO_TIMESTAMP('2021-08-23 17:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:12:57.530Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541765 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-08-23T15:12:57.626Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555481,TO_TIMESTAMP('2021-08-23 17:12:57','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_Product_Category_BoilerPlate',1,'Y','N','Y','Y','AD_Product_Category_BoilerPlate','N',1000000,TO_TIMESTAMP('2021-08-23 17:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:12:57.657Z
-- URL zum Konzept
CREATE SEQUENCE AD_PRODUCT_CATEGORY_BOILERPLATE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-08-23T15:15:31.486Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575373,102,0,19,541765,129,'AD_Client_ID',TO_TIMESTAMP('2021-08-23 17:15:31','YYYY-MM-DD HH24:MI:SS'),100,'N','@#AD_Client_ID@','Mandant für diese Installation.','D',22,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','Mandant',0,TO_TIMESTAMP('2021-08-23 17:15:31','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:31.510Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575373 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:31.517Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-08-23T15:15:32.530Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575374,113,0,30,541765,104,'AD_Org_ID',TO_TIMESTAMP('2021-08-23 17:15:32','YYYY-MM-DD HH24:MI:SS'),100,'N','@#AD_Org_ID@','Organisatorische Einheit des Mandanten','D',22,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','N','N','N','Sektion',0,TO_TIMESTAMP('2021-08-23 17:15:32','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:32.539Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575374 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:32.540Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-08-23T15:15:33.264Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579561,0,'AD_Product_Category_BoilerPlate_ID',TO_TIMESTAMP('2021-08-23 17:15:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product Category Boiler Plate','Product Category Boiler Plate',TO_TIMESTAMP('2021-08-23 17:15:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:15:33.274Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579561 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-08-23T15:15:33.720Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575375,579561,0,13,541765,'AD_Product_Category_BoilerPlate_ID',TO_TIMESTAMP('2021-08-23 17:15:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',22,'Y','N','N','N','N','N','Y','Y','N','N','N','N','N','Product Category Boiler Plate',0,TO_TIMESTAMP('2021-08-23 17:15:33','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:33.724Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575375 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:33.726Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579561) 
;

-- 2021-08-23T15:15:34.373Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575376,245,0,16,541765,'Created',TO_TIMESTAMP('2021-08-23 17:15:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt',0,TO_TIMESTAMP('2021-08-23 17:15:34','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:34.403Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575376 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:34.404Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-08-23T15:15:35.196Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575377,246,0,18,110,541765,'CreatedBy',TO_TIMESTAMP('2021-08-23 17:15:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',22,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-08-23 17:15:35','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:35.203Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575377 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:35.220Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-08-23T15:15:36.087Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575378,275,0,10,541765,'Description',TO_TIMESTAMP('2021-08-23 17:15:35','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,TO_TIMESTAMP('2021-08-23 17:15:35','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:36.120Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575378 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:36.122Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2021-08-23T15:15:36.946Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575379,326,0,14,541765,'Help',TO_TIMESTAMP('2021-08-23 17:15:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Comment or Hint','D',2000,'The Help field contains a hint, comment or help about the use of this item.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Kommentar/Hilfe',0,TO_TIMESTAMP('2021-08-23 17:15:36','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:36.959Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575379 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:36.961Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(326) 
;

-- 2021-08-23T15:15:37.888Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575380,348,0,20,541765,'IsActive',TO_TIMESTAMP('2021-08-23 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Aktiv',0,TO_TIMESTAMP('2021-08-23 17:15:37','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:37.896Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575380 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:37.898Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-08-23T15:15:38.662Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575381,416,0,20,541765,'IsSummary',TO_TIMESTAMP('2021-08-23 17:15:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Dies ist ein Zusammenfassungseintrag','D',1,'A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Zusammenfassungseintrag',0,TO_TIMESTAMP('2021-08-23 17:15:38','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:38.673Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575381 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:38.674Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(416) 
;

-- 2021-08-23T15:15:39.470Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575382,469,0,10,541765,'Name',TO_TIMESTAMP('2021-08-23 17:15:39','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',60,'','Y','Y','N','N','N','N','Y','N','Y','N','Y','N','N','Y','Name',2,TO_TIMESTAMP('2021-08-23 17:15:39','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:39.480Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575382 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:39.481Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2021-08-23T15:15:40.510Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575383,542921,0,18,540608,541765,'Parent_Activity_ID',TO_TIMESTAMP('2021-08-23 17:15:40','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Hauptkostenstelle',0,TO_TIMESTAMP('2021-08-23 17:15:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-23T15:15:40.520Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575383 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:40.521Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(542921) 
;

-- 2021-08-23T15:15:40.966Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575384,607,0,16,541765,'Updated',TO_TIMESTAMP('2021-08-23 17:15:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-08-23 17:15:40','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:40.976Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575384 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:40.977Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-08-23T15:15:41.483Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575385,608,0,18,110,541765,'UpdatedBy',TO_TIMESTAMP('2021-08-23 17:15:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',22,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-08-23 17:15:41','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:41.490Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575385 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:41.491Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-08-23T15:15:42.093Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575386,620,0,10,541765,'Value',TO_TIMESTAMP('2021-08-23 17:15:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',40,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','Y','N','Y','N','Y','N','Y','N','N','Y','Suchschlüssel',1,TO_TIMESTAMP('2021-08-23 17:15:42','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:15:42.102Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575386 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:15:42.105Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- 2021-08-23T15:16:05.347Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575378
;

-- 2021-08-23T15:16:05.411Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575378
;

-- 2021-08-23T15:16:06.914Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575379
;

-- 2021-08-23T15:16:06.939Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575379
;

-- 2021-08-23T15:16:07.643Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575381
;

-- 2021-08-23T15:16:07.645Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575381
;

-- 2021-08-23T15:16:08.171Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575382
;

-- 2021-08-23T15:16:08.174Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575382
;

-- 2021-08-23T15:16:08.915Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575383
;

-- 2021-08-23T15:16:08.919Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575383
;

-- 2021-08-23T15:16:09.556Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=575386
;

-- 2021-08-23T15:16:09.571Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=575386
;

-- 2021-08-23T15:16:24.431Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575387,504410,0,19,541765,'AD_BoilerPlate_ID',TO_TIMESTAMP('2021-08-23 17:16:24','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.letters',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Textbaustein',0,0,TO_TIMESTAMP('2021-08-23 17:16:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-23T15:16:24.465Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575387 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:16:24.470Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(504410) 
;

-- 2021-08-23T15:17:01.183Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575388,439,0,10,541765,'Line',TO_TIMESTAMP('2021-08-23 17:17:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Einzelne Zeile in dem Dokument','D',0,10,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zeile Nr.',0,0,TO_TIMESTAMP('2021-08-23 17:17:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-23T15:17:01.200Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575388 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:17:01.233Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(439) 
;

-- 2021-08-23T15:17:43.340Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', FieldLength=22, IsMandatory='Y',Updated=TO_TIMESTAMP('2021-08-23 17:17:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575388
;

-- 2021-08-23T15:19:06.973Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=566, ColumnName='SeqNo', DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_InOutLine WHERE M_InOut_ID=@M_InOut_ID@', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', Name='Reihenfolge',Updated=TO_TIMESTAMP('2021-08-23 17:19:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575388
;

-- 2021-08-23T15:19:06.984Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Reihenfolge', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Column_ID=575388
;

-- 2021-08-23T15:19:06.985Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2021-08-23T15:20:01.151Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_Product_Category_BoilerPlate WHERE M_Product_Category_ID=@M_Product_Category_ID@',Updated=TO_TIMESTAMP('2021-08-23 17:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575388
;

-- 2021-08-23T15:20:19.500Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.AD_Product_Category_BoilerPlate (AD_BoilerPlate_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Product_Category_BoilerPlate_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, SeqNo NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADBoilerPlate_ADProductCategoryBoilerPlate FOREIGN KEY (AD_BoilerPlate_ID) REFERENCES public.AD_BoilerPlate DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_Product_Category_BoilerPlate_Key PRIMARY KEY (AD_Product_Category_BoilerPlate_ID))
;

-- 2021-08-23T15:20:33.919Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2021-08-23 17:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541765
;

-- 2021-08-23T15:22:50.076Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktkategorie textbaustein', PrintName='Produktkategorie textbaustein',Updated=TO_TIMESTAMP('2021-08-23 17:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579561 AND AD_Language='de_CH'
;

-- 2021-08-23T15:22:50.081Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579561,'de_CH') 
;

-- 2021-08-23T15:23:00.093Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktkategorie textbaustein', PrintName='Produktkategorie textbaustein',Updated=TO_TIMESTAMP('2021-08-23 17:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579561 AND AD_Language='de_DE'
;

-- 2021-08-23T15:23:00.095Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579561,'de_DE') 
;

-- 2021-08-23T15:23:00.157Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579561,'de_DE') 
;

-- 2021-08-23T15:23:00.159Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Product_Category_BoilerPlate_ID', Name='Produktkategorie textbaustein', Description=NULL, Help=NULL WHERE AD_Element_ID=579561
;

-- 2021-08-23T15:23:00.161Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Product_Category_BoilerPlate_ID', Name='Produktkategorie textbaustein', Description=NULL, Help=NULL, AD_Element_ID=579561 WHERE UPPER(ColumnName)='AD_PRODUCT_CATEGORY_BOILERPLATE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-08-23T15:23:00.166Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Product_Category_BoilerPlate_ID', Name='Produktkategorie textbaustein', Description=NULL, Help=NULL WHERE AD_Element_ID=579561 AND IsCentrallyMaintained='Y'
;

-- 2021-08-23T15:23:00.167Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Produktkategorie textbaustein', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579561) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579561)
;

-- 2021-08-23T15:23:00.191Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Produktkategorie textbaustein', Name='Produktkategorie textbaustein' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579561)
;

-- 2021-08-23T15:23:00.193Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Produktkategorie textbaustein', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579561
;

-- 2021-08-23T15:23:00.195Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Produktkategorie textbaustein', Description=NULL, Help=NULL WHERE AD_Element_ID = 579561
;

-- 2021-08-23T15:23:00.195Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Produktkategorie textbaustein', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579561
;

-- 2021-08-23T15:23:03.523Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-08-23 17:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579561 AND AD_Language='en_US'
;

-- 2021-08-23T15:23:03.524Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579561,'en_US') 
;

-- 2021-08-23T15:23:08.986Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579561,0,544243,541765,140,'Y',TO_TIMESTAMP('2021-08-23 17:23:08','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','AD_Product_Category_BoilerPlate','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Produktkategorie textbaustein','N',240,0,TO_TIMESTAMP('2021-08-23 17:23:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:09.005Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544243 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-08-23T15:23:09.008Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579561) 
;

-- 2021-08-23T15:23:09.016Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544243)
;

-- 2021-08-23T15:23:10.830Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575373,652563,0,544243,TO_TIMESTAMP('2021-08-23 17:23:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-08-23 17:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:10.838Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:23:10.841Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-08-23T15:23:11.283Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652563
;

-- 2021-08-23T15:23:11.284Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652563)
;

-- 2021-08-23T15:23:11.361Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575374,652564,0,544243,TO_TIMESTAMP('2021-08-23 17:23:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-08-23 17:23:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:11.363Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:23:11.364Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-08-23T15:23:11.541Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652564
;

-- 2021-08-23T15:23:11.542Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652564)
;

-- 2021-08-23T15:23:11.609Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575375,652565,0,544243,TO_TIMESTAMP('2021-08-23 17:23:11','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Produktkategorie textbaustein',TO_TIMESTAMP('2021-08-23 17:23:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:11.612Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:23:11.613Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579561) 
;

-- 2021-08-23T15:23:11.615Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652565
;

-- 2021-08-23T15:23:11.615Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652565)
;

-- 2021-08-23T15:23:11.673Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575380,652566,0,544243,TO_TIMESTAMP('2021-08-23 17:23:11','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-08-23 17:23:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:11.676Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652566 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:23:11.677Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-08-23T15:23:12.003Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652566
;

-- 2021-08-23T15:23:12.003Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652566)
;

-- 2021-08-23T15:23:12.067Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575387,652567,0,544243,TO_TIMESTAMP('2021-08-23 17:23:12','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Textbaustein',TO_TIMESTAMP('2021-08-23 17:23:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:12.070Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652567 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:23:12.071Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(504410) 
;

-- 2021-08-23T15:23:12.087Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652567
;

-- 2021-08-23T15:23:12.091Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652567)
;

-- 2021-08-23T15:23:12.153Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575388,652568,0,544243,TO_TIMESTAMP('2021-08-23 17:23:12','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2021-08-23 17:23:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:12.155Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652568 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:23:12.156Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2021-08-23T15:23:12.177Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652568
;

-- 2021-08-23T15:23:12.178Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652568)
;

-- 2021-08-23T15:23:22.154Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544243,543353,TO_TIMESTAMP('2021-08-23 17:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-08-23 17:23:22','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-08-23T15:23:22.166Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543353 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-08-23T15:23:26.784Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544179,543353,TO_TIMESTAMP('2021-08-23 17:23:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-08-23 17:23:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:31.911Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544179,546314,TO_TIMESTAMP('2021-08-23 17:23:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-08-23 17:23:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:23:52.078Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652567,0,544243,546314,588064,'F',TO_TIMESTAMP('2021-08-23 17:23:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Textbaustein',10,0,0,TO_TIMESTAMP('2021-08-23 17:23:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:24:01.231Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652566,0,544243,546314,588065,'F',TO_TIMESTAMP('2021-08-23 17:24:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2021-08-23 17:24:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:24:11.014Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652568,0,544243,546314,588066,'F',TO_TIMESTAMP('2021-08-23 17:24:10','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',30,0,0,TO_TIMESTAMP('2021-08-23 17:24:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:24:17.375Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652564,0,544243,546314,588067,'F',TO_TIMESTAMP('2021-08-23 17:24:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',40,0,0,TO_TIMESTAMP('2021-08-23 17:24:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:24:22.097Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652563,0,544243,546314,588068,'F',TO_TIMESTAMP('2021-08-23 17:24:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2021-08-23 17:24:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:25:01.843Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575389,453,0,19,541765,'M_Product_Category_ID',TO_TIMESTAMP('2021-08-23 17:25:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Kategorie eines Produktes','D',0,10,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Produkt Kategorie',0,0,TO_TIMESTAMP('2021-08-23 17:25:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-23T15:25:01.854Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575389 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:25:01.863Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(453) 
;

-- 2021-08-23T15:25:06.399Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_Product_Category_BoilerPlate','ALTER TABLE public.AD_Product_Category_BoilerPlate ADD COLUMN M_Product_Category_ID NUMERIC(10) NOT NULL')
;

-- 2021-08-23T15:25:06.413Z
-- URL zum Konzept
ALTER TABLE AD_Product_Category_BoilerPlate ADD CONSTRAINT MProductCategory_ADProductCategoryBoilerPlate FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED
;

-- 2021-08-23T15:25:11.795Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2021-08-23 17:25:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544243
;

-- 2021-08-23T15:25:12.910Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575389,652569,0,544243,TO_TIMESTAMP('2021-08-23 17:25:12','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes',10,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','N','N','N','N','N','Produkt Kategorie',TO_TIMESTAMP('2021-08-23 17:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:25:12.916Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652569 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:25:12.919Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453) 
;

-- 2021-08-23T15:25:12.974Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652569
;

-- 2021-08-23T15:25:12.978Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652569)
;

-- 2021-08-23T15:25:49.079Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=575389,Updated=TO_TIMESTAMP('2021-08-23 17:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544243
;

-- 2021-08-23T15:26:53.894Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541766,140,'N',TO_TIMESTAMP('2021-08-23 17:26:53','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Doc Type Boiler Plate','NP','L','AD_DocType_BoilerPlate','DTI',TO_TIMESTAMP('2021-08-23 17:26:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:26:53.900Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541766 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-08-23T15:26:53.956Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555482,TO_TIMESTAMP('2021-08-23 17:26:53','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_DocType_BoilerPlate',1,'Y','N','Y','Y','AD_DocType_BoilerPlate','N',1000000,TO_TIMESTAMP('2021-08-23 17:26:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:26:53.969Z
-- URL zum Konzept
CREATE SEQUENCE AD_DOCTYPE_BOILERPLATE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-08-23T15:26:59.866Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575390,504410,0,19,541766,'AD_BoilerPlate_ID',TO_TIMESTAMP('2021-08-23 17:26:59','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Textbaustein',0,TO_TIMESTAMP('2021-08-23 17:26:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-23T15:26:59.879Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575390 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:26:59.882Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(504410) 
;

-- 2021-08-23T15:27:00.310Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575391,102,0,19,541766,129,'AD_Client_ID',TO_TIMESTAMP('2021-08-23 17:27:00','YYYY-MM-DD HH24:MI:SS'),100,'N','@#AD_Client_ID@','Mandant für diese Installation.','D',22,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','Mandant',0,TO_TIMESTAMP('2021-08-23 17:27:00','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:00.316Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575391 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:00.317Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-08-23T15:27:00.960Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575392,113,0,30,541766,104,'AD_Org_ID',TO_TIMESTAMP('2021-08-23 17:27:00','YYYY-MM-DD HH24:MI:SS'),100,'N','@#AD_Org_ID@','Organisatorische Einheit des Mandanten','D',22,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','N','N','N','Sektion',0,TO_TIMESTAMP('2021-08-23 17:27:00','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:00.970Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575392 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:00.971Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-08-23T15:27:01.858Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579562,0,'AD_DocType_BoilerPlate_ID',TO_TIMESTAMP('2021-08-23 17:27:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Doc Type Boiler Plate','Doc Type Boiler Plate',TO_TIMESTAMP('2021-08-23 17:27:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:27:01.885Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579562 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-08-23T15:27:02.446Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575393,579562,0,13,541766,'AD_DocType_BoilerPlate_ID',TO_TIMESTAMP('2021-08-23 17:27:01','YYYY-MM-DD HH24:MI:SS'),100,'N','D',22,'Y','N','N','N','N','N','Y','Y','N','N','N','N','N','Doc Type Boiler Plate',0,TO_TIMESTAMP('2021-08-23 17:27:01','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:02.450Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575393 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:02.451Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579562) 
;

-- 2021-08-23T15:27:02.952Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575394,245,0,16,541766,'Created',TO_TIMESTAMP('2021-08-23 17:27:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt',0,TO_TIMESTAMP('2021-08-23 17:27:02','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:02.958Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575394 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:02.960Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-08-23T15:27:03.494Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575395,246,0,18,110,541766,'CreatedBy',TO_TIMESTAMP('2021-08-23 17:27:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',22,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-08-23 17:27:03','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:03.501Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575395 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:03.502Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-08-23T15:27:04.031Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575396,348,0,20,541766,'IsActive',TO_TIMESTAMP('2021-08-23 17:27:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Aktiv',0,TO_TIMESTAMP('2021-08-23 17:27:03','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:04.039Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575396 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:04.040Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-08-23T15:27:04.949Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575397,453,0,19,541766,'M_Product_Category_ID',TO_TIMESTAMP('2021-08-23 17:27:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Kategorie eines Produktes','D',10,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Produkt Kategorie',0,TO_TIMESTAMP('2021-08-23 17:27:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-23T15:27:04.958Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575397 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:04.962Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(453) 
;

-- 2021-08-23T15:27:05.439Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575398,566,0,11,541766,'SeqNo',TO_TIMESTAMP('2021-08-23 17:27:05','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_Product_Category_BoilerPlate WHERE M_Product_Category_ID=@M_Product_Category_ID@','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','Y','Reihenfolge',0,TO_TIMESTAMP('2021-08-23 17:27:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-23T15:27:05.447Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575398 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:05.448Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2021-08-23T15:27:05.845Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575399,607,0,16,541766,'Updated',TO_TIMESTAMP('2021-08-23 17:27:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-08-23 17:27:05','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:05.849Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575399 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:05.850Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-08-23T15:27:06.265Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575400,608,0,18,110,541766,'UpdatedBy',TO_TIMESTAMP('2021-08-23 17:27:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',22,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-08-23 17:27:06','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2021-08-23T15:27:06.269Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575400 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-23T15:27:06.270Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-08-23T15:29:13.673Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=196, ColumnName='C_DocType_ID', Description='Belegart oder Verarbeitungsvorgaben', Help='Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.', Name='Belegart',Updated=TO_TIMESTAMP('2021-08-23 17:29:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575397
;

-- 2021-08-23T15:29:13.683Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Belegart', Description='Belegart oder Verarbeitungsvorgaben', Help='Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.' WHERE AD_Column_ID=575397
;

-- 2021-08-23T15:29:13.686Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- 2021-08-23T15:29:40.495Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.AD_DocType_BoilerPlate (AD_BoilerPlate_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_DocType_BoilerPlate_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_DocType_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, SeqNo NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADBoilerPlate_ADDocTypeBoilerPlate FOREIGN KEY (AD_BoilerPlate_ID) REFERENCES public.AD_BoilerPlate DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_DocType_BoilerPlate_Key PRIMARY KEY (AD_DocType_BoilerPlate_ID), CONSTRAINT CDocType_ADDocTypeBoilerPlate FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-08-23T15:29:51.726Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=135,Updated=TO_TIMESTAMP('2021-08-23 17:29:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541766
;

-- 2021-08-23T15:30:27.331Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,575397,579562,0,544244,541766,135,'Y',TO_TIMESTAMP('2021-08-23 17:30:27','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','AD_DocType_BoilerPlate','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Doc Type Boiler Plate','N',40,1,TO_TIMESTAMP('2021-08-23 17:30:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:30:27.357Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544244 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-08-23T15:30:27.359Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579562) 
;

-- 2021-08-23T15:30:27.376Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544244)
;

-- 2021-08-23T15:30:35.479Z
-- URL zum Konzept
UPDATE AD_Tab SET Parent_Column_ID=1501,Updated=TO_TIMESTAMP('2021-08-23 17:30:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544244
;

-- 2021-08-23T15:31:18.396Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Belegart textbaustein', PrintName='Belegart textbaustein',Updated=TO_TIMESTAMP('2021-08-23 17:31:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579562 AND AD_Language='de_CH'
;

-- 2021-08-23T15:31:18.397Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579562,'de_CH') 
;

-- 2021-08-23T15:31:26.319Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Belegart textbaustein', PrintName='Belegart textbaustein',Updated=TO_TIMESTAMP('2021-08-23 17:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579562 AND AD_Language='de_DE'
;

-- 2021-08-23T15:31:26.320Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579562,'de_DE') 
;

-- 2021-08-23T15:31:26.378Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579562,'de_DE') 
;

-- 2021-08-23T15:31:26.381Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_DocType_BoilerPlate_ID', Name='Belegart textbaustein', Description=NULL, Help=NULL WHERE AD_Element_ID=579562
;

-- 2021-08-23T15:31:26.382Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_DocType_BoilerPlate_ID', Name='Belegart textbaustein', Description=NULL, Help=NULL, AD_Element_ID=579562 WHERE UPPER(ColumnName)='AD_DOCTYPE_BOILERPLATE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-08-23T15:31:26.388Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_DocType_BoilerPlate_ID', Name='Belegart textbaustein', Description=NULL, Help=NULL WHERE AD_Element_ID=579562 AND IsCentrallyMaintained='Y'
;

-- 2021-08-23T15:31:26.388Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Belegart textbaustein', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579562) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579562)
;

-- 2021-08-23T15:31:26.410Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Belegart textbaustein', Name='Belegart textbaustein' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579562)
;

-- 2021-08-23T15:31:26.412Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Belegart textbaustein', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579562
;

-- 2021-08-23T15:31:26.413Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Belegart textbaustein', Description=NULL, Help=NULL WHERE AD_Element_ID = 579562
;

-- 2021-08-23T15:31:26.413Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Belegart textbaustein', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579562
;

-- 2021-08-23T15:31:33.628Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575390,652570,0,544244,TO_TIMESTAMP('2021-08-23 17:31:33','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Textbaustein',TO_TIMESTAMP('2021-08-23 17:31:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:33.633Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652570 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:31:33.636Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(504410) 
;

-- 2021-08-23T15:31:33.648Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652570
;

-- 2021-08-23T15:31:33.657Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652570)
;

-- 2021-08-23T15:31:33.728Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575391,652571,0,544244,TO_TIMESTAMP('2021-08-23 17:31:33','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-08-23 17:31:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:33.731Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652571 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:31:33.732Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-08-23T15:31:34.243Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652571
;

-- 2021-08-23T15:31:34.244Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652571)
;

-- 2021-08-23T15:31:34.308Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575392,652572,0,544244,TO_TIMESTAMP('2021-08-23 17:31:34','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-08-23 17:31:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:34.312Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652572 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:31:34.314Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-08-23T15:31:34.585Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652572
;

-- 2021-08-23T15:31:34.586Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652572)
;

-- 2021-08-23T15:31:34.658Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575393,652573,0,544244,TO_TIMESTAMP('2021-08-23 17:31:34','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Belegart textbaustein',TO_TIMESTAMP('2021-08-23 17:31:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:34.661Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652573 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:31:34.662Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579562) 
;

-- 2021-08-23T15:31:34.666Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652573
;

-- 2021-08-23T15:31:34.666Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652573)
;

-- 2021-08-23T15:31:34.723Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575396,652574,0,544244,TO_TIMESTAMP('2021-08-23 17:31:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-08-23 17:31:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:34.726Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652574 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:31:34.727Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-08-23T15:31:35.767Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652574
;

-- 2021-08-23T15:31:35.767Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652574)
;

-- 2021-08-23T15:31:35.865Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575397,652575,0,544244,TO_TIMESTAMP('2021-08-23 17:31:35','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben',10,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2021-08-23 17:31:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:35.872Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652575 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:31:35.874Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2021-08-23T15:31:36.036Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652575
;

-- 2021-08-23T15:31:36.036Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652575)
;

-- 2021-08-23T15:31:36.113Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575398,652576,0,544244,TO_TIMESTAMP('2021-08-23 17:31:36','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2021-08-23 17:31:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:36.120Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652576 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-23T15:31:36.124Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2021-08-23T15:31:36.234Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652576
;

-- 2021-08-23T15:31:36.234Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652576)
;

-- 2021-08-23T15:31:51.447Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544244,543354,TO_TIMESTAMP('2021-08-23 17:31:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','maine',10,TO_TIMESTAMP('2021-08-23 17:31:51','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-08-23T15:31:51.450Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543354 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-08-23T15:31:54.694Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544180,543354,TO_TIMESTAMP('2021-08-23 17:31:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-08-23 17:31:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:31:59.513Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544180,546315,TO_TIMESTAMP('2021-08-23 17:31:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-08-23 17:31:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:32:26.767Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652570,0,544244,546315,588069,'F',TO_TIMESTAMP('2021-08-23 17:32:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Textbaustein',10,0,0,TO_TIMESTAMP('2021-08-23 17:32:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:32:33.974Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652574,0,544244,546315,588070,'F',TO_TIMESTAMP('2021-08-23 17:32:33','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2021-08-23 17:32:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:32:45.459Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652576,0,544244,546315,588071,'F',TO_TIMESTAMP('2021-08-23 17:32:45','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',30,0,0,TO_TIMESTAMP('2021-08-23 17:32:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:32:50.202Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652572,0,544244,546315,588072,'F',TO_TIMESTAMP('2021-08-23 17:32:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',40,0,0,TO_TIMESTAMP('2021-08-23 17:32:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-23T15:33:00.383Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652571,0,544244,546315,588073,'F',TO_TIMESTAMP('2021-08-23 17:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2021-08-23 17:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;


-------------


-- 2021-08-24T09:05:25.579Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579561,0,544245,541765,144,'Y',TO_TIMESTAMP('2021-08-24 11:05:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','AD_Product_Category_BoilerPlate','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Produktkategorie textbaustein','N',40,0,TO_TIMESTAMP('2021-08-24 11:05:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:25.651Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544245 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-08-24T09:05:25.690Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579561) 
;

-- 2021-08-24T09:05:25.709Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544245)
;

-- 2021-08-24T09:05:44.998Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=575389, Parent_Column_ID=2020, TabLevel=1,Updated=TO_TIMESTAMP('2021-08-24 11:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544245
;

-- 2021-08-24T09:05:51.113Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575373,652580,0,544245,TO_TIMESTAMP('2021-08-24 11:05:51','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-08-24 11:05:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:51.125Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652580 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-24T09:05:51.134Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-08-24T09:05:52.486Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652580
;

-- 2021-08-24T09:05:52.510Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652580)
;

-- 2021-08-24T09:05:52.636Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575374,652581,0,544245,TO_TIMESTAMP('2021-08-24 11:05:52','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-08-24 11:05:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:52.649Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652581 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-24T09:05:52.650Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-08-24T09:05:53.184Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652581
;

-- 2021-08-24T09:05:53.192Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652581)
;

-- 2021-08-24T09:05:53.265Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575375,652582,0,544245,TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Produktkategorie textbaustein',TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:53.269Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652582 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-24T09:05:53.270Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579561) 
;

-- 2021-08-24T09:05:53.273Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652582
;

-- 2021-08-24T09:05:53.274Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652582)
;

-- 2021-08-24T09:05:53.328Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575380,652583,0,544245,TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:53.331Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652583 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-24T09:05:53.332Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-08-24T09:05:53.758Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652583
;

-- 2021-08-24T09:05:53.758Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652583)
;

-- 2021-08-24T09:05:53.850Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575387,652584,0,544245,TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Textbaustein',TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:53.855Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652584 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-24T09:05:53.856Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(504410) 
;

-- 2021-08-24T09:05:53.865Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652584
;

-- 2021-08-24T09:05:53.866Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652584)
;

-- 2021-08-24T09:05:53.929Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575388,652585,0,544245,TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:53.933Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652585 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-24T09:05:53.934Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2021-08-24T09:05:53.951Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652585
;

-- 2021-08-24T09:05:53.951Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652585)
;

-- 2021-08-24T09:05:54.025Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575389,652586,0,544245,TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes',10,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','N','N','N','N','N','Produkt Kategorie',TO_TIMESTAMP('2021-08-24 11:05:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:05:54.030Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=652586 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-08-24T09:05:54.032Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453) 
;

-- 2021-08-24T09:05:54.086Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652586
;

-- 2021-08-24T09:05:54.086Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(652586)
;

-- 2021-08-24T09:06:08.148Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544245,543355,TO_TIMESTAMP('2021-08-24 11:06:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-08-24 11:06:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-08-24T09:06:08.158Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543355 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-08-24T09:06:11.476Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544181,543355,TO_TIMESTAMP('2021-08-24 11:06:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-08-24 11:06:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:06:17.315Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544181,546316,TO_TIMESTAMP('2021-08-24 11:06:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-08-24 11:06:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:06:27.501Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652584,0,544245,546316,588077,'F',TO_TIMESTAMP('2021-08-24 11:06:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Textbaustein',10,0,0,TO_TIMESTAMP('2021-08-24 11:06:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:06:34.807Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652583,0,544245,546316,588078,'F',TO_TIMESTAMP('2021-08-24 11:06:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2021-08-24 11:06:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:06:47.543Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652585,0,544245,546316,588079,'F',TO_TIMESTAMP('2021-08-24 11:06:47','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',30,0,0,TO_TIMESTAMP('2021-08-24 11:06:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:06:54.942Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652581,0,544245,546316,588080,'F',TO_TIMESTAMP('2021-08-24 11:06:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',40,0,0,TO_TIMESTAMP('2021-08-24 11:06:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:07:01.020Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,652580,0,544245,546316,588081,'F',TO_TIMESTAMP('2021-08-24 11:07:00','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2021-08-24 11:07:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-24T09:07:16.437Z
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=144,Updated=TO_TIMESTAMP('2021-08-24 11:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541765
;

-- 2021-08-24T09:08:16.074Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=588068
;

-- 2021-08-24T09:08:16.089Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652563
;

-- 2021-08-24T09:08:16.094Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=652563
;

-- 2021-08-24T09:08:16.101Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=652563
;

-- 2021-08-24T09:08:16.116Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=588067
;

-- 2021-08-24T09:08:16.120Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652564
;

-- 2021-08-24T09:08:16.120Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=652564
;

-- 2021-08-24T09:08:16.122Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=652564
;

-- 2021-08-24T09:08:16.129Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652565
;

-- 2021-08-24T09:08:16.129Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=652565
;

-- 2021-08-24T09:08:16.134Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=652565
;

-- 2021-08-24T09:08:16.143Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=588065
;

-- 2021-08-24T09:08:16.146Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652566
;

-- 2021-08-24T09:08:16.146Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=652566
;

-- 2021-08-24T09:08:16.147Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=652566
;

-- 2021-08-24T09:08:16.158Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=588064
;

-- 2021-08-24T09:08:16.161Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652567
;

-- 2021-08-24T09:08:16.161Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=652567
;

-- 2021-08-24T09:08:16.162Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=652567
;

-- 2021-08-24T09:08:16.171Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=588066
;

-- 2021-08-24T09:08:16.173Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652568
;

-- 2021-08-24T09:08:16.174Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=652568
;

-- 2021-08-24T09:08:16.175Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=652568
;

-- 2021-08-24T09:08:16.182Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=652569
;

-- 2021-08-24T09:08:16.183Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=652569
;

-- 2021-08-24T09:08:16.184Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=652569
;

-- 2021-08-24T09:08:16.194Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=546314
;

-- 2021-08-24T09:08:16.197Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=544179
;

-- 2021-08-24T09:08:16.199Z
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=543353
;

-- 2021-08-24T09:08:16.203Z
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=543353
;

-- 2021-08-24T09:08:16.206Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=544243
;

-- 2021-08-24T09:08:16.207Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=544243
;



----------------
--- increase field length for description in C_DocType_trl

-- 2021-08-24T12:11:47.586Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=36, FieldLength=1024,Updated=TO_TIMESTAMP('2021-08-24 14:11:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557776
;

-- 2021-08-24T12:11:49.994Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_doctype_trl','Description','TEXT',null,null)
;
