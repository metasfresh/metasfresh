-- Run mode: SWING_CLIENT

-- Table: M_Product_Category_MaxNetAmount
-- 2025-06-10T09:23:51.974Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542501,'A','N',TO_TIMESTAMP('2025-06-10 09:23:51.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','Y','Y','N','N','N','N','N',0,'Product Category Maximum Net Amount','NP','L','M_Product_Category_MaxNetAmount','DTI',TO_TIMESTAMP('2025-06-10 09:23:51.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2025-06-10T09:23:51.976Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542501 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-06-10T09:23:52.102Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556504,TO_TIMESTAMP('2025-06-10 09:23:52.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table M_Product_Category_MaxNetAmount',1,'Y','N','Y','Y','M_Product_Category_MaxNetAmount',1000000,TO_TIMESTAMP('2025-06-10 09:23:52.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T09:23:52.114Z
CREATE SEQUENCE M_PRODUCT_CATEGORY_MAXNETAMOUNT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Product_Category_MaxNetAmount.AD_Client_ID
-- 2025-06-10T09:23:58.554Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590382,102,0,19,542501,'AD_Client_ID',TO_TIMESTAMP('2025-06-10 09:23:58.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2025-06-10 09:23:58.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:23:58.556Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590382 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:23:58.729Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: M_Product_Category_MaxNetAmount.AD_Org_ID
-- 2025-06-10T09:23:59.433Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590383,113,0,30,542501,'AD_Org_ID',TO_TIMESTAMP('2025-06-10 09:23:59.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2025-06-10 09:23:59.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:23:59.434Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590383 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:23:59.436Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: M_Product_Category_MaxNetAmount.Created
-- 2025-06-10T09:24:00.011Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590384,245,0,16,542501,'Created',TO_TIMESTAMP('2025-06-10 09:23:59.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2025-06-10 09:23:59.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:24:00.017Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590384 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:24:00.022Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: M_Product_Category_MaxNetAmount.CreatedBy
-- 2025-06-10T09:24:00.596Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590385,246,0,18,110,542501,'CreatedBy',TO_TIMESTAMP('2025-06-10 09:24:00.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2025-06-10 09:24:00.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:24:00.598Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590385 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:24:00.600Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: M_Product_Category_MaxNetAmount.IsActive
-- 2025-06-10T09:24:01.136Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590386,348,0,20,542501,'IsActive',TO_TIMESTAMP('2025-06-10 09:24:00.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2025-06-10 09:24:00.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:24:01.138Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590386 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:24:01.140Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: M_Product_Category_MaxNetAmount.Updated
-- 2025-06-10T09:24:01.699Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590387,607,0,16,542501,'Updated',TO_TIMESTAMP('2025-06-10 09:24:01.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2025-06-10 09:24:01.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:24:01.701Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590387 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:24:01.703Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: M_Product_Category_MaxNetAmount.UpdatedBy
-- 2025-06-10T09:24:02.410Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590388,608,0,18,110,542501,'UpdatedBy',TO_TIMESTAMP('2025-06-10 09:24:02.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2025-06-10 09:24:02.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:24:02.412Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590388 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:24:02.415Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-06-10T09:24:02.877Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583713,0,'M_Product_Category_MaxNetAmount_ID',TO_TIMESTAMP('2025-06-10 09:24:02.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Product Category Maximum Net Amount','Product Category Maximum Net Amount',TO_TIMESTAMP('2025-06-10 09:24:02.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T09:24:02.881Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583713 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Category_MaxNetAmount.M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T09:24:03.406Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590389,583713,0,13,542501,'M_Product_Category_MaxNetAmount_ID',TO_TIMESTAMP('2025-06-10 09:24:02.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Product Category Maximum Net Amount',0,0,TO_TIMESTAMP('2025-06-10 09:24:02.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:24:03.407Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590389 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:24:03.410Z
/* DDL */  select update_Column_Translation_From_AD_Element(583713)
;

-- Column: M_Product_Category_MaxNetAmount.M_Product_Category_ID
-- 2025-06-10T09:32:23.621Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590390,453,0,30,542501,'XX','M_Product_Category_ID',TO_TIMESTAMP('2025-06-10 09:32:23.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Kategorie eines Produktes','D',0,10,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt Kategorie',0,0,TO_TIMESTAMP('2025-06-10 09:32:23.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:32:23.622Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590390 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:32:23.625Z
/* DDL */  select update_Column_Translation_From_AD_Element(453)
;











-- Run mode: SWING_CLIENT

-- 2025-06-10T09:37:06.285Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583714,0,'MaxNetAmount',TO_TIMESTAMP('2025-06-10 09:37:06.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Maximum Net Amount','Maximum Net Amount',TO_TIMESTAMP('2025-06-10 09:37:06.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T09:37:06.294Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583714 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Category_MaxNetAmount.MaxNetAmount
-- 2025-06-10T09:39:07.025Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590391,583714,0,12,542501,'XX','MaxNetAmount',TO_TIMESTAMP('2025-06-10 09:39:06.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Maximum Net Amount',0,0,TO_TIMESTAMP('2025-06-10 09:39:06.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:39:07.027Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590391 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:39:07.184Z
/* DDL */  select update_Column_Translation_From_AD_Element(583714)
;

-- Column: M_Product_Category_MaxNetAmount.C_Currency_ID
-- 2025-06-10T09:39:23.185Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590392,193,0,30,542501,'XX','C_Currency_ID',TO_TIMESTAMP('2025-06-10 09:39:22.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Die Währung für diesen Eintrag','D',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2025-06-10 09:39:22.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-10T09:39:23.187Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590392 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-10T09:39:23.190Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- Column: M_Product_Category_MaxNetAmount.C_Currency_ID
-- 2025-06-10T09:39:47.528Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-10 09:39:47.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590392
;

-- Column: M_Product_Category_MaxNetAmount.M_Product_Category_ID
-- 2025-06-10T09:40:14.766Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-10 09:40:14.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590390
;

-- 2025-06-10T09:40:39.810Z
/* DDL */ CREATE TABLE public.M_Product_Category_MaxNetAmount (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MaxNetAmount NUMERIC NOT NULL, M_Product_Category_ID NUMERIC(10) NOT NULL, M_Product_Category_MaxNetAmount_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCurrency_MProductCategoryMaxNetAmount FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProductCategory_MProductCategoryMaxNetAmount FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Product_Category_MaxNetAmount_Key PRIMARY KEY (M_Product_Category_MaxNetAmount_ID))
;

-- Element: MaxNetAmount
-- 2025-06-10T09:46:19.915Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. Zeilennetto', PrintName='Max. Zeilennetto',Updated=TO_TIMESTAMP('2025-06-10 09:46:19.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='de_CH'
;

-- 2025-06-10T09:46:19.916Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T09:46:20.222Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'de_CH')
;

-- Element: MaxNetAmount
-- 2025-06-10T09:46:25.290Z
UPDATE AD_Element_Trl SET Name='Max. Zeilennetto', PrintName='Max. Zeilennetto',Updated=TO_TIMESTAMP('2025-06-10 09:46:25.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='fr_CH'
;

-- 2025-06-10T09:46:25.291Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T09:46:25.663Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'fr_CH')
;

-- Element: MaxNetAmount
-- 2025-06-10T09:47:09.251Z
UPDATE AD_Element_Trl SET Name='Max. Line Net Amount', PrintName='Max. Line Net Amount',Updated=TO_TIMESTAMP('2025-06-10 09:47:09.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='en_US'
;

-- 2025-06-10T09:47:09.252Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T09:47:09.587Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'en_US')
;

-- Element: MaxNetAmount
-- 2025-06-10T09:47:11.481Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-10 09:47:11.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='en_US'
;

-- 2025-06-10T09:47:11.483Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'en_US')
;

-- Element: MaxNetAmount
-- 2025-06-10T09:47:29.421Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. Zeilennetto', PrintName='Max. Zeilennetto',Updated=TO_TIMESTAMP('2025-06-10 09:47:29.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='de_DE'
;

-- 2025-06-10T09:47:29.422Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T09:47:30.152Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583714,'de_DE')
;

-- 2025-06-10T09:47:30.154Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'de_DE')
;

-- Table: M_Product_Category_MaxNetAmount
-- 2025-06-10T09:56:08.759Z
UPDATE AD_Table SET Name=' Max Net Amount per Product Category',Updated=TO_TIMESTAMP('2025-06-10 09:56:08.758000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542501
;

-- 2025-06-10T09:56:08.761Z
UPDATE AD_Table_Trl trl SET Name=' Max Net Amount per Product Category' WHERE AD_Table_ID=542501 AND AD_Language='de_DE'
;

-- Element: MaxNetAmount
-- 2025-06-10T10:01:06.456Z
UPDATE AD_Element_Trl SET Name='Max. Nettobetrag', PrintName='Max. Nettobetrag',Updated=TO_TIMESTAMP('2025-06-10 10:01:06.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='de_DE'
;

-- 2025-06-10T10:01:06.458Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:01:07.234Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583714,'de_DE')
;

-- 2025-06-10T10:01:07.235Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'de_DE')
;

-- Element: MaxNetAmount
-- 2025-06-10T10:01:17.246Z
UPDATE AD_Element_Trl SET Name='Max. Nettobetrag', PrintName='Max. Nettobetrag',Updated=TO_TIMESTAMP('2025-06-10 10:01:17.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='fr_CH'
;

-- 2025-06-10T10:01:17.252Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:01:18.322Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'fr_CH')
;

-- Element: MaxNetAmount
-- 2025-06-10T10:01:23.861Z
UPDATE AD_Element_Trl SET Name='Max. Nettobetrag', PrintName='Max. Nettobetrag',Updated=TO_TIMESTAMP('2025-06-10 10:01:23.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='de_CH'
;

-- 2025-06-10T10:01:23.862Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:01:24.249Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'de_CH')
;

-- Element: MaxNetAmount
-- 2025-06-10T10:01:58.409Z
UPDATE AD_Element_Trl SET Name='Max. Net Amount', PrintName='Max. Net Amount',Updated=TO_TIMESTAMP('2025-06-10 10:01:58.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583714 AND AD_Language='en_US'
;

-- 2025-06-10T10:01:58.410Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:01:58.781Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583714,'en_US')
;

-- 2025-06-10T10:02:22.315Z
UPDATE AD_Table_Trl SET Name='Max. Nettobetrag pro Produktkategorie ',Updated=TO_TIMESTAMP('2025-06-10 10:02:22.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542501
;

-- 2025-06-10T10:02:22.316Z
UPDATE AD_Table base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Table_Trl trl  WHERE trl.AD_Table_ID=base.AD_Table_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:02:57.618Z
UPDATE AD_Table_Trl SET Name='Max. Nettobetrag pro Produktkategorie ',Updated=TO_TIMESTAMP('2025-06-10 10:02:57.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542501
;

-- 2025-06-10T10:02:57.619Z
UPDATE AD_Table base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Table_Trl trl  WHERE trl.AD_Table_ID=base.AD_Table_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:03:00.147Z
UPDATE AD_Table_Trl SET Name='Max. Nettobetrag pro Produktkategorie ',Updated=TO_TIMESTAMP('2025-06-10 10:03:00.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542501
;

-- 2025-06-10T10:03:00.148Z
UPDATE AD_Table base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Table_Trl trl  WHERE trl.AD_Table_ID=base.AD_Table_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:03:32.829Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Max Net Amount per Product Category',Updated=TO_TIMESTAMP('2025-06-10 10:03:32.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542501
;

-- 2025-06-10T10:03:32.830Z
UPDATE AD_Table base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Table_Trl trl  WHERE trl.AD_Table_ID=base.AD_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Element: M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T10:04:12.171Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. Nettobetrag pro Produktkategorie ', PrintName='Max. Nettobetrag pro Produktkategorie ',Updated=TO_TIMESTAMP('2025-06-10 10:04:12.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583713 AND AD_Language='de_CH'
;

-- 2025-06-10T10:04:12.176Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:04:14.108Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583713,'de_CH')
;

-- Element: M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T10:04:21.004Z
UPDATE AD_Element_Trl SET Name='Max. Nettobetrag pro Produktkategorie', PrintName='Max. Nettobetrag pro Produktkategorie',Updated=TO_TIMESTAMP('2025-06-10 10:04:21.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583713 AND AD_Language='fr_CH'
;

-- 2025-06-10T10:04:21.005Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:04:21.401Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583713,'fr_CH')
;

-- Element: M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T10:04:23.620Z
UPDATE AD_Element_Trl SET Name='Max. Nettobetrag pro Produktkategorie', PrintName='Max. Nettobetrag pro Produktkategorie',Updated=TO_TIMESTAMP('2025-06-10 10:04:23.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583713 AND AD_Language='de_CH'
;

-- 2025-06-10T10:04:23.621Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:04:24Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583713,'de_CH')
;

-- Element: M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T10:04:31.389Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-10 10:04:31.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583713 AND AD_Language='en_US'
;

-- 2025-06-10T10:04:31.399Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583713,'en_US')
;

-- Element: M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T10:04:41.343Z
UPDATE AD_Element_Trl SET Name='Max. Nettobetrag pro Produktkategorie', PrintName='Max. Nettobetrag pro Produktkategorie',Updated=TO_TIMESTAMP('2025-06-10 10:04:41.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583713 AND AD_Language='de_DE'
;

-- 2025-06-10T10:04:41.349Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:04:43.543Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583713,'de_DE')
;

-- 2025-06-10T10:04:43.544Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583713,'de_DE')
;

-- Element: M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T10:05:11.750Z
UPDATE AD_Element_Trl SET Name='Max. Net Amount per Product Category', PrintName='Max. Net Amount per Product Category',Updated=TO_TIMESTAMP('2025-06-10 10:05:11.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583713 AND AD_Language='en_US'
;

-- 2025-06-10T10:05:11.751Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-10T10:05:12.097Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583713,'en_US')
;

-- Tab: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie
-- Table: M_Product_Category_MaxNetAmount
-- 2025-06-10T10:14:35.204Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,590390,583713,0,548191,542501,541762,'Y',TO_TIMESTAMP('2025-06-10 10:14:34.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.endcustomer.gk196','N','N','A','M_Product_Category_MaxNetAmount','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Max. Nettobetrag pro Produktkategorie',2020,'N',50,1,TO_TIMESTAMP('2025-06-10 10:14:34.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:35.215Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548191 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-06-10T10:14:35.226Z
/* DDL */  select update_tab_translation_from_ad_element(583713)
;

-- 2025-06-10T10:14:35.302Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548191)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Mandant
-- Column: M_Product_Category_MaxNetAmount.AD_Client_ID
-- 2025-06-10T10:14:43.262Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590382,747508,0,548191,TO_TIMESTAMP('2025-06-10 10:14:43.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.endcustomer.gk196','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-06-10 10:14:43.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:43.265Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:43.267Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-06-10T10:14:43.720Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747508
;

-- 2025-06-10T10:14:43.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747508)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Organisation
-- Column: M_Product_Category_MaxNetAmount.AD_Org_ID
-- 2025-06-10T10:14:43.818Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590383,747509,0,548191,TO_TIMESTAMP('2025-06-10 10:14:43.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.endcustomer.gk196','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2025-06-10 10:14:43.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:43.819Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747509 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:43.820Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-06-10T10:14:43.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747509
;

-- 2025-06-10T10:14:43.928Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747509)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Erstellt
-- Column: M_Product_Category_MaxNetAmount.Created
-- 2025-06-10T10:14:44.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590384,747510,0,548191,TO_TIMESTAMP('2025-06-10 10:14:43.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.endcustomer.gk196','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2025-06-10 10:14:43.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:44.022Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:44.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-06-10T10:14:44.115Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747510
;

-- 2025-06-10T10:14:44.116Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747510)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Erstellt durch
-- Column: M_Product_Category_MaxNetAmount.CreatedBy
-- 2025-06-10T10:14:44.209Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590385,747511,0,548191,TO_TIMESTAMP('2025-06-10 10:14:44.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.endcustomer.gk196','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2025-06-10 10:14:44.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:44.211Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747511 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:44.212Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-06-10T10:14:44.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747511
;

-- 2025-06-10T10:14:44.309Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747511)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Aktiv
-- Column: M_Product_Category_MaxNetAmount.IsActive
-- 2025-06-10T10:14:44.399Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590386,747512,0,548191,TO_TIMESTAMP('2025-06-10 10:14:44.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.endcustomer.gk196','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-06-10 10:14:44.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:44.401Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747512 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:44.402Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-06-10T10:14:44.524Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747512
;

-- 2025-06-10T10:14:44.525Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747512)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Aktualisiert
-- Column: M_Product_Category_MaxNetAmount.Updated
-- 2025-06-10T10:14:44.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590387,747513,0,548191,TO_TIMESTAMP('2025-06-10 10:14:44.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.endcustomer.gk196','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2025-06-10 10:14:44.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:44.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747513 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:44.624Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2025-06-10T10:14:44.683Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747513
;

-- 2025-06-10T10:14:44.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747513)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Aktualisiert durch
-- Column: M_Product_Category_MaxNetAmount.UpdatedBy
-- 2025-06-10T10:14:44.792Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590388,747514,0,548191,TO_TIMESTAMP('2025-06-10 10:14:44.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.endcustomer.gk196','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2025-06-10 10:14:44.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:44.793Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747514 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:44.795Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2025-06-10T10:14:44.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747514
;

-- 2025-06-10T10:14:44.847Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747514)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie
-- Column: M_Product_Category_MaxNetAmount.M_Product_Category_MaxNetAmount_ID
-- 2025-06-10T10:14:44.942Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590389,747515,0,548191,TO_TIMESTAMP('2025-06-10 10:14:44.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.endcustomer.gk196','Y','N','N','N','N','N','N','N','Max. Nettobetrag pro Produktkategorie',TO_TIMESTAMP('2025-06-10 10:14:44.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:44.944Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747515 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:44.945Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583713)
;

-- 2025-06-10T10:14:44.947Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747515
;

-- 2025-06-10T10:14:44.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747515)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Produkt Kategorie
-- Column: M_Product_Category_MaxNetAmount.M_Product_Category_ID
-- 2025-06-10T10:14:45.045Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590390,747516,0,548191,TO_TIMESTAMP('2025-06-10 10:14:44.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',10,'de.metas.endcustomer.gk196','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','Y','N','N','N','N','N','Produkt Kategorie',TO_TIMESTAMP('2025-06-10 10:14:44.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:45.046Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747516 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:45.048Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2025-06-10T10:14:45.071Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747516
;

-- 2025-06-10T10:14:45.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747516)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Max. Nettobetrag
-- Column: M_Product_Category_MaxNetAmount.MaxNetAmount
-- 2025-06-10T10:14:45.165Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590391,747517,0,548191,TO_TIMESTAMP('2025-06-10 10:14:45.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.endcustomer.gk196','Y','Y','N','N','N','N','N','Max. Nettobetrag',TO_TIMESTAMP('2025-06-10 10:14:45.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:45.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747517 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:45.168Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583714)
;

-- 2025-06-10T10:14:45.170Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747517
;

-- 2025-06-10T10:14:45.171Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747517)
;

-- Field: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> Währung
-- Column: M_Product_Category_MaxNetAmount.C_Currency_ID
-- 2025-06-10T10:14:45.262Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590392,747518,0,548191,TO_TIMESTAMP('2025-06-10 10:14:45.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag',10,'de.metas.endcustomer.gk196','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','N','N','N','N','N','Währung',TO_TIMESTAMP('2025-06-10 10:14:45.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-10T10:14:45.263Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747518 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-10T10:14:45.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2025-06-10T10:14:45.297Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747518
;

-- 2025-06-10T10:14:45.298Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747518)
;

-- Tab: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196)
-- UI Section: main
-- 2025-06-10T10:32:15.897Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,UIStyle,Updated,UpdatedBy,Value) VALUES (0,0,548191,546731,TO_TIMESTAMP('2025-06-10 10:32:15.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'primary',TO_TIMESTAMP('2025-06-10 10:32:15.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-06-10T10:32:15.899Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546731 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main
-- UI Column: 10
-- 2025-06-10T10:32:21.200Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548198,546731,TO_TIMESTAMP('2025-06-10 10:32:21.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-06-10 10:32:21.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10
-- UI Element Group: main
-- 2025-06-10T10:32:38.887Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548198,553094,TO_TIMESTAMP('2025-06-10 10:32:38.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,'primary',TO_TIMESTAMP('2025-06-10 10:32:38.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196)
-- UI Section: main
-- 2025-06-10T10:33:12.555Z
UPDATE AD_UI_Section SET UIStyle='',Updated=TO_TIMESTAMP('2025-06-10 10:33:12.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Section_ID=546731
;

-- UI Column: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10
-- UI Element Group: default
-- 2025-06-10T10:33:21.318Z
UPDATE AD_UI_ElementGroup SET Name='default',Updated=TO_TIMESTAMP('2025-06-10 10:33:21.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553094
;

-- UI Element: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10 -> default.Max. Nettobetrag
-- Column: M_Product_Category_MaxNetAmount.MaxNetAmount
-- 2025-06-10T10:34:05.130Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747517,0,548191,553094,633918,'F',TO_TIMESTAMP('2025-06-10 10:34:04.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Max. Nettobetrag',10,0,0,TO_TIMESTAMP('2025-06-10 10:34:04.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10 -> default.Währung
-- Column: M_Product_Category_MaxNetAmount.C_Currency_ID
-- 2025-06-10T10:34:16.029Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747518,0,548191,553094,633919,'F',TO_TIMESTAMP('2025-06-10 10:34:15.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',20,0,0,TO_TIMESTAMP('2025-06-10 10:34:15.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10 -> default.Aktiv
-- Column: M_Product_Category_MaxNetAmount.IsActive
-- 2025-06-10T10:34:28.155Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747512,0,548191,553094,633920,'F',TO_TIMESTAMP('2025-06-10 10:34:28.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',30,0,0,TO_TIMESTAMP('2025-06-10 10:34:28.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10 -> default.Aktiv
-- Column: M_Product_Category_MaxNetAmount.IsActive
-- 2025-06-10T10:34:48.283Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2025-06-10 10:34:48.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633920
;

-- UI Element: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10 -> default.Währung
-- Column: M_Product_Category_MaxNetAmount.C_Currency_ID
-- 2025-06-10T10:38:22.598Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2025-06-10 10:38:22.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633919
;

-- UI Element: Produkt Kategorie(541762,de.metas.endcustomer.gk196) -> Max. Nettobetrag pro Produktkategorie(548191,de.metas.endcustomer.gk196) -> main -> 10 -> default.Max. Nettobetrag
-- Column: M_Product_Category_MaxNetAmount.MaxNetAmount
-- 2025-06-10T10:38:24.480Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2025-06-10 10:38:24.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633918
;




CREATE UNIQUE INDEX IF NOT EXISTS m_product_category_maxnetamount_c_currency_id_m_product_categor
    ON m_product_category_maxnetamount (c_currency_id, m_product_category_id)
    WHERE (isactive = 'Y'::bpchar)
;










