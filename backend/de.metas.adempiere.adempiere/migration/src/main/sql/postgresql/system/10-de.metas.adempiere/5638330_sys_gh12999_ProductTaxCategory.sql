-- Table: M_Product_TaxCategory
-- 2022-05-05T09:09:55.668Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542131,140,'N',TO_TIMESTAMP('2022-05-05 12:09:55','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'Product Tax Category','NP','L','M_Product_TaxCategory','DTI',TO_TIMESTAMP('2022-05-05 12:09:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:09:55.670Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542131 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-05-05T09:09:55.788Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555866,TO_TIMESTAMP('2022-05-05 12:09:55','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_TaxCategory',1,'Y','N','Y','Y','M_Product_TaxCategory','N',1000000,TO_TIMESTAMP('2022-05-05 12:09:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:09:55.812Z
CREATE SEQUENCE M_PRODUCT_TAXCATEGORY_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Product_TaxCategory.AD_Client_ID
-- 2022-05-05T09:10:53.815Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582915,102,0,19,542131,'AD_Client_ID',TO_TIMESTAMP('2022-05-05 12:10:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-05-05 12:10:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:10:53.821Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582915 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:10:53.861Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Product_TaxCategory.AD_Org_ID
-- 2022-05-05T09:10:54.965Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582916,113,0,30,542131,'AD_Org_ID',TO_TIMESTAMP('2022-05-05 12:10:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-05-05 12:10:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:10:54.967Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582916 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:10:54.969Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Product_TaxCategory.Created
-- 2022-05-05T09:10:55.936Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582917,245,0,16,542131,'Created',TO_TIMESTAMP('2022-05-05 12:10:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-05-05 12:10:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:10:55.938Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582917 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:10:55.941Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Product_TaxCategory.CreatedBy
-- 2022-05-05T09:10:56.778Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582918,246,0,18,110,542131,'CreatedBy',TO_TIMESTAMP('2022-05-05 12:10:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-05-05 12:10:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:10:56.780Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582918 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:10:56.783Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Product_TaxCategory.IsActive
-- 2022-05-05T09:10:57.598Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582919,348,0,20,542131,'IsActive',TO_TIMESTAMP('2022-05-05 12:10:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-05-05 12:10:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:10:57.601Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582919 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:10:57.603Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Product_TaxCategory.Updated
-- 2022-05-05T09:10:58.383Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582920,607,0,16,542131,'Updated',TO_TIMESTAMP('2022-05-05 12:10:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-05-05 12:10:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:10:58.384Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582920 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:10:58.385Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Product_TaxCategory.UpdatedBy
-- 2022-05-05T09:10:59.162Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582921,608,0,18,110,542131,'UpdatedBy',TO_TIMESTAMP('2022-05-05 12:10:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-05-05 12:10:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:10:59.163Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582921 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:10:59.166Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-05-05T09:11:00.183Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580821,0,'M_Product_TaxCategory_ID',TO_TIMESTAMP('2022-05-05 12:11:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product Tax Category','Product Tax Category',TO_TIMESTAMP('2022-05-05 12:11:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:11:00.186Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580821 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_TaxCategory.M_Product_TaxCategory_ID
-- 2022-05-05T09:11:00.937Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582922,580821,0,13,542131,'M_Product_TaxCategory_ID',TO_TIMESTAMP('2022-05-05 12:11:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Product Tax Category',0,0,TO_TIMESTAMP('2022-05-05 12:11:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:11:00.939Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582922 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:11:00.942Z
/* DDL */  select update_Column_Translation_From_AD_Element(580821) 
;

-- 2022-05-05T09:11:01.826Z
/* DDL */ CREATE TABLE public.M_Product_TaxCategory (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_TaxCategory_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Product_TaxCategory_Key PRIMARY KEY (M_Product_TaxCategory_ID))
;

-- 2022-05-05T09:11:01.882Z
INSERT INTO t_alter_column values('m_product_taxcategory','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2022-05-05T09:11:01.899Z
INSERT INTO t_alter_column values('m_product_taxcategory','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-05-05T09:11:01.922Z
INSERT INTO t_alter_column values('m_product_taxcategory','CreatedBy','NUMERIC(10)',null,null)
;

-- 2022-05-05T09:11:01.936Z
INSERT INTO t_alter_column values('m_product_taxcategory','IsActive','CHAR(1)',null,null)
;

-- 2022-05-05T09:11:01.971Z
INSERT INTO t_alter_column values('m_product_taxcategory','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-05-05T09:11:01.984Z
INSERT INTO t_alter_column values('m_product_taxcategory','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2022-05-05T09:11:02.001Z
INSERT INTO t_alter_column values('m_product_taxcategory','M_Product_TaxCategory_ID','NUMERIC(10)',null,null)
;

-- Column: M_Product_TaxCategory.M_Product_ID
-- 2022-05-05T09:14:41.937Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582923,454,0,30,540272,542131,231,'M_Product_ID',TO_TIMESTAMP('2022-05-05 12:14:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,22,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Produkt','NP',0,0,TO_TIMESTAMP('2022-05-05 12:14:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:14:41.941Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582923 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:14:41.947Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2022-05-05T09:14:43.717Z
/* DDL */ SELECT public.db_alter_table('M_Product_TaxCategory','ALTER TABLE public.M_Product_TaxCategory ADD COLUMN M_Product_ID NUMERIC(10) NOT NULL')
;

-- 2022-05-05T09:14:43.727Z
ALTER TABLE M_Product_TaxCategory ADD CONSTRAINT MProduct_MProductTaxCategory FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Product_TaxCategory.C_TaxCategory_ID
-- 2022-05-05T09:17:08.437Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582924,211,0,19,542131,540543,'C_TaxCategory_ID',TO_TIMESTAMP('2022-05-05 12:17:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Steuerkategorie','D',0,10,'Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Steuerkategorie','NP',0,0,TO_TIMESTAMP('2022-05-05 12:17:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:17:08.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582924 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:17:08.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(211) 
;

-- 2022-05-05T09:17:10.936Z
/* DDL */ SELECT public.db_alter_table('M_Product_TaxCategory','ALTER TABLE public.M_Product_TaxCategory ADD COLUMN C_TaxCategory_ID NUMERIC(10) NOT NULL')
;

-- 2022-05-05T09:17:10.956Z
ALTER TABLE M_Product_TaxCategory ADD CONSTRAINT CTaxCategory_MProductTaxCategory FOREIGN KEY (C_TaxCategory_ID) REFERENCES public.C_TaxCategory DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Product_TaxCategory.C_Country_ID
-- 2022-05-05T09:19:34.504Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582925,192,0,19,542131,'C_Country_ID',TO_TIMESTAMP('2022-05-05 12:19:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Land','D',0,10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Land','NP',0,0,TO_TIMESTAMP('2022-05-05 12:19:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:19:34.506Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582925 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:19:34.510Z
/* DDL */  select update_Column_Translation_From_AD_Element(192) 
;

-- 2022-05-05T09:19:38.496Z
/* DDL */ SELECT public.db_alter_table('M_Product_TaxCategory','ALTER TABLE public.M_Product_TaxCategory ADD COLUMN C_Country_ID NUMERIC(10) NOT NULL')
;

-- 2022-05-05T09:19:38.502Z
ALTER TABLE M_Product_TaxCategory ADD CONSTRAINT CCountry_MProductTaxCategory FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Product_TaxCategory.ValidFrom
-- 2022-05-05T09:22:11.397Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582926,617,0,15,542131,'ValidFrom',TO_TIMESTAMP('2022-05-05 12:22:11','YYYY-MM-DD HH24:MI:SS'),100,'N','@#Date@','Gültig ab inklusiv (erster Tag)','D',0,7,'"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Gültig ab',0,0,TO_TIMESTAMP('2022-05-05 12:22:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-05T09:22:11.399Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582926 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-05T09:22:11.406Z
/* DDL */  select update_Column_Translation_From_AD_Element(617) 
;

-- 2022-05-05T09:22:16.836Z
/* DDL */ SELECT public.db_alter_table('M_Product_TaxCategory','ALTER TABLE public.M_Product_TaxCategory ADD COLUMN ValidFrom TIMESTAMP WITHOUT TIME ZONE NOT NULL')
;

-- Tab: Produkt -> Product Tax Category
-- Table: M_Product_TaxCategory
-- 2022-05-05T09:28:58.039Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582923,580821,0,546102,542131,140,'Y',TO_TIMESTAMP('2022-05-05 12:28:57','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_Product_TaxCategory','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Product Tax Category','N',310,0,TO_TIMESTAMP('2022-05-05 12:28:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:28:58.042Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546102 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-05T09:28:58.047Z
/* DDL */  select update_tab_translation_from_ad_element(580821) 
;

-- 2022-05-05T09:28:58.062Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546102)
;

-- Tab: Produkt -> Product Tax Category
-- Table: M_Product_TaxCategory
-- 2022-05-05T09:29:06.570Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2022-05-05 12:29:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546102
;

-- Field: Produkt -> Product Tax Category -> Mandant
-- Column: M_Product_TaxCategory.AD_Client_ID
-- 2022-05-05T09:29:19.588Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582915,691793,0,546102,TO_TIMESTAMP('2022-05-05 12:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-05-05 12:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:19.591Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:19.599Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-05T09:29:19.838Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691793
;

-- 2022-05-05T09:29:19.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691793)
;

-- Field: Produkt -> Product Tax Category -> Sektion
-- Column: M_Product_TaxCategory.AD_Org_ID
-- 2022-05-05T09:29:19.952Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582916,691794,0,546102,TO_TIMESTAMP('2022-05-05 12:29:19','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-05-05 12:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:19.953Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:19.953Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-05T09:29:20.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691794
;

-- 2022-05-05T09:29:20.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691794)
;

-- Field: Produkt -> Product Tax Category -> Aktiv
-- Column: M_Product_TaxCategory.IsActive
-- 2022-05-05T09:29:20.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582919,691795,0,546102,TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:20.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:20.183Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-05T09:29:20.382Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691795
;

-- 2022-05-05T09:29:20.382Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691795)
;

-- Field: Produkt -> Product Tax Category -> Product Tax Category
-- Column: M_Product_TaxCategory.M_Product_TaxCategory_ID
-- 2022-05-05T09:29:20.502Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582922,691796,0,546102,TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Product Tax Category',TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:20.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:20.505Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580821) 
;

-- 2022-05-05T09:29:20.509Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691796
;

-- 2022-05-05T09:29:20.510Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691796)
;

-- Field: Produkt -> Product Tax Category -> Produkt
-- Column: M_Product_TaxCategory.M_Product_ID
-- 2022-05-05T09:29:20.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582923,691797,0,546102,TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',22,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:20.615Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:20.617Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-05-05T09:29:20.655Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691797
;

-- 2022-05-05T09:29:20.655Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691797)
;

-- Field: Produkt -> Product Tax Category -> Steuerkategorie
-- Column: M_Product_TaxCategory.C_TaxCategory_ID
-- 2022-05-05T09:29:20.782Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582924,691798,0,546102,TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Steuerkategorie',10,'D','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','N','N','N','N','N','N','Steuerkategorie',TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:20.783Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:20.784Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(211) 
;

-- 2022-05-05T09:29:20.789Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691798
;

-- 2022-05-05T09:29:20.789Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691798)
;

-- Field: Produkt -> Product Tax Category -> Land
-- Column: M_Product_TaxCategory.C_Country_ID
-- 2022-05-05T09:29:20.899Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582925,691799,0,546102,TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:20.901Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:20.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192) 
;

-- 2022-05-05T09:29:20.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691799
;

-- 2022-05-05T09:29:20.907Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691799)
;

-- Field: Produkt -> Product Tax Category -> Gültig ab
-- Column: M_Product_TaxCategory.ValidFrom
-- 2022-05-05T09:29:21.017Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582926,691800,0,546102,TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)',7,'D','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','N','N','N','N','N','Gültig ab',TO_TIMESTAMP('2022-05-05 12:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:29:21.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-05T09:29:21.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(617) 
;

-- 2022-05-05T09:29:21.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691800
;

-- 2022-05-05T09:29:21.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691800)
;

-- 2022-05-05T09:29:46.952Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546102,544747,TO_TIMESTAMP('2022-05-05 12:29:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-05 12:29:46','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-05T09:29:46.954Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544747 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-05T09:29:55.159Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545748,544747,TO_TIMESTAMP('2022-05-05 12:29:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-05 12:29:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:30:06.920Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545748,548815,TO_TIMESTAMP('2022-05-05 12:30:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2022-05-05 12:30:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T09:30:13.073Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-05-05 12:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=548815
;

-- UI Element: Produkt -> Product Tax Category.Produkt
-- Column: M_Product_TaxCategory.M_Product_ID
-- 2022-05-05T09:31:08.541Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691797,0,546102,605404,548815,'F',TO_TIMESTAMP('2022-05-05 12:31:08','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2022-05-05 12:31:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product Tax Category.Steuerkategorie
-- Column: M_Product_TaxCategory.C_TaxCategory_ID
-- 2022-05-05T09:31:40.444Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691798,0,546102,605405,548815,'F',TO_TIMESTAMP('2022-05-05 12:31:40','YYYY-MM-DD HH24:MI:SS'),100,'Steuerkategorie','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','N','Y','N','N','N',0,'Steuerkategorie',20,0,0,TO_TIMESTAMP('2022-05-05 12:31:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product Tax Category.Land
-- Column: M_Product_TaxCategory.C_Country_ID
-- 2022-05-05T09:31:59.143Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691799,0,546102,605406,548815,'F',TO_TIMESTAMP('2022-05-05 12:31:58','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','N','N',0,'Land',30,0,0,TO_TIMESTAMP('2022-05-05 12:31:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product Tax Category.Gültig ab
-- Column: M_Product_TaxCategory.ValidFrom
-- 2022-05-05T09:32:06.713Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691800,0,546102,605407,548815,'F',TO_TIMESTAMP('2022-05-05 12:32:06','YYYY-MM-DD HH24:MI:SS'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','N','N',0,'Gültig ab',40,0,0,TO_TIMESTAMP('2022-05-05 12:32:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product Tax Category.Aktiv
-- Column: M_Product_TaxCategory.IsActive
-- 2022-05-05T09:32:19.054Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691795,0,546102,605408,548815,'F',TO_TIMESTAMP('2022-05-05 12:32:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',50,0,0,TO_TIMESTAMP('2022-05-05 12:32:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product Tax Category.Sektion
-- Column: M_Product_TaxCategory.AD_Org_ID
-- 2022-05-05T09:32:42.215Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691794,0,546102,605409,548815,'F',TO_TIMESTAMP('2022-05-05 12:32:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',60,0,0,TO_TIMESTAMP('2022-05-05 12:32:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product Tax Category.Mandant
-- Column: M_Product_TaxCategory.AD_Client_ID
-- 2022-05-05T09:32:49.362Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691793,0,546102,605410,548815,'F',TO_TIMESTAMP('2022-05-05 12:32:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',70,0,0,TO_TIMESTAMP('2022-05-05 12:32:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product Tax Category.Produkt
-- Column: M_Product_TaxCategory.M_Product_ID
-- 2022-05-05T09:33:36.395Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-05 12:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605404
;

-- UI Element: Produkt -> Product Tax Category.Steuerkategorie
-- Column: M_Product_TaxCategory.C_TaxCategory_ID
-- 2022-05-05T09:33:36.401Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-05 12:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605405
;

-- UI Element: Produkt -> Product Tax Category.Land
-- Column: M_Product_TaxCategory.C_Country_ID
-- 2022-05-05T09:33:36.407Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-05 12:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605406
;

-- UI Element: Produkt -> Product Tax Category.Gültig ab
-- Column: M_Product_TaxCategory.ValidFrom
-- 2022-05-05T09:33:36.412Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-05 12:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605407
;

-- UI Element: Produkt -> Product Tax Category.Aktiv
-- Column: M_Product_TaxCategory.IsActive
-- 2022-05-05T09:33:36.418Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-05-05 12:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605408
;

-- UI Element: Produkt -> Product Tax Category.Sektion
-- Column: M_Product_TaxCategory.AD_Org_ID
-- 2022-05-05T09:33:36.423Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-05-05 12:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605409
;

-- Tab: Produkt -> Product Tax Category
-- Table: M_Product_TaxCategory
-- 2022-05-05T09:47:51.304Z
UPDATE AD_Tab SET SeqNo=186,Updated=TO_TIMESTAMP('2022-05-05 12:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546102
;

-- UI Element: Produkt -> Product Tax Category.Produkt
-- Column: M_Product_TaxCategory.M_Product_ID
-- 2022-05-05T09:49:06.876Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-05 12:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605404
;

-- UI Element: Produkt -> Product Tax Category.Steuerkategorie
-- Column: M_Product_TaxCategory.C_TaxCategory_ID
-- 2022-05-05T09:49:06.880Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-05 12:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605405
;

-- UI Element: Produkt -> Product Tax Category.Land
-- Column: M_Product_TaxCategory.C_Country_ID
-- 2022-05-05T09:49:06.884Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-05 12:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605406
;

-- UI Element: Produkt -> Product Tax Category.Gültig ab
-- Column: M_Product_TaxCategory.ValidFrom
-- 2022-05-05T09:49:06.888Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-05 12:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605407
;

-- UI Element: Produkt -> Product Tax Category.Aktiv
-- Column: M_Product_TaxCategory.IsActive
-- 2022-05-05T09:49:06.891Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-05 12:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605408
;

-- UI Element: Produkt -> Product Tax Category.Sektion
-- Column: M_Product_TaxCategory.AD_Org_ID
-- 2022-05-05T09:49:06.894Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-05-05 12:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605409
;

-- Column: M_ProductPrice.C_TaxCategory_ID
-- 2022-05-05T11:14:31.294Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-05-05 14:14:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=505149
;

-- 2022-05-09T11:47:00.273Z
INSERT INTO C_PricingRule (AD_Client_ID,AD_Org_ID,C_PricingRule_ID,Classname,Created,CreatedBy,EntityType,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540018,'de.metas.pricing.rules.ManualPricePricingRule',TO_TIMESTAMP('2022-05-09 14:47:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ManualPricePricingRule',2200,TO_TIMESTAMP('2022-05-09 14:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-09T11:47:18.168Z
UPDATE C_PricingRule SET SeqNo=210,Updated=TO_TIMESTAMP('2022-05-09 14:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_PricingRule_ID=540018
;
