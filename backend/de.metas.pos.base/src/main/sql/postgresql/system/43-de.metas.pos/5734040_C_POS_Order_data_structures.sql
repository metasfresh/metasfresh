-- Table: C_POS_Order
-- Table: C_POS_Order
-- 2024-09-20T13:16:35.552Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,Description,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('1',0,0,0,542434,'A','N',TO_TIMESTAMP('2024-09-20 16:16:35','YYYY-MM-DD HH24:MI:SS'),100,'Point of Sales Order','A','de.metas.pos','N','Y','N','N','Y','N','N','Y','N','N',0,'POS Order','NP','L','C_POS_Order','DTI',TO_TIMESTAMP('2024-09-20 16:16:35','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-09-20T13:16:35.560Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542434 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-09-20T13:16:35.689Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556365,TO_TIMESTAMP('2024-09-20 16:16:35','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_POS_Order',1,'Y','N','Y','Y','C_POS_Order','N',1000000,TO_TIMESTAMP('2024-09-20 16:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:16:35.700Z
CREATE SEQUENCE C_POS_ORDER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_POS_Order.AD_Client_ID
-- Column: C_POS_Order.AD_Client_ID
-- 2024-09-20T13:16:41.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589018,102,0,19,542434,'AD_Client_ID',TO_TIMESTAMP('2024-09-20 16:16:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.pos',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-09-20 16:16:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:41.310Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589018 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:41.349Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_POS_Order.AD_Org_ID
-- Column: C_POS_Order.AD_Org_ID
-- 2024-09-20T13:16:42.262Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589019,113,0,30,542434,'AD_Org_ID',TO_TIMESTAMP('2024-09-20 16:16:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.pos',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-09-20 16:16:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:42.265Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589019 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:42.269Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_POS_Order.Created
-- Column: C_POS_Order.Created
-- 2024-09-20T13:16:42.923Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589020,245,0,16,542434,'Created',TO_TIMESTAMP('2024-09-20 16:16:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.pos',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-09-20 16:16:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:42.927Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589020 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:42.929Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_POS_Order.CreatedBy
-- Column: C_POS_Order.CreatedBy
-- 2024-09-20T13:16:43.629Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589021,246,0,18,110,542434,'CreatedBy',TO_TIMESTAMP('2024-09-20 16:16:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.pos',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-09-20 16:16:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:43.631Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589021 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:43.634Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_POS_Order.IsActive
-- Column: C_POS_Order.IsActive
-- 2024-09-20T13:16:44.411Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589022,348,0,20,542434,'IsActive',TO_TIMESTAMP('2024-09-20 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.pos',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-09-20 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:44.414Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589022 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:44.417Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_POS_Order.Updated
-- Column: C_POS_Order.Updated
-- 2024-09-20T13:16:45.102Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589023,607,0,16,542434,'Updated',TO_TIMESTAMP('2024-09-20 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.pos',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-09-20 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:45.103Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589023 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:45.107Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_POS_Order.UpdatedBy
-- Column: C_POS_Order.UpdatedBy
-- 2024-09-20T13:16:45.822Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589024,608,0,18,110,542434,'UpdatedBy',TO_TIMESTAMP('2024-09-20 16:16:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.pos',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-09-20 16:16:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:45.824Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589024 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:45.827Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-09-20T13:16:46.376Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583266,0,'C_POS_Order_ID',TO_TIMESTAMP('2024-09-20 16:16:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','POS Order','POS Order',TO_TIMESTAMP('2024-09-20 16:16:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:16:46.382Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583266 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Order.C_POS_Order_ID
-- Column: C_POS_Order.C_POS_Order_ID
-- 2024-09-20T13:16:47.005Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589025,583266,0,13,542434,'C_POS_Order_ID',TO_TIMESTAMP('2024-09-20 16:16:46','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','POS Order',0,0,TO_TIMESTAMP('2024-09-20 16:16:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:16:47.007Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589025 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:16:47.011Z
/* DDL */  select update_Column_Translation_From_AD_Element(583266) 
;

-- Column: C_POS_Order.ExternalId
-- Column: C_POS_Order.ExternalId
-- 2024-09-20T13:19:04.926Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589026,543939,0,10,542434,'XX','ExternalId',TO_TIMESTAMP('2024-09-20 16:19:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2024-09-20 16:19:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:19:04.930Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589026 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:19:04.934Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- Name: C_POS_Order_Status
-- 2024-09-20T13:19:51.771Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541890,TO_TIMESTAMP('2024-09-20 16:19:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','N','C_POS_Order_Status',TO_TIMESTAMP('2024-09-20 16:19:51','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-09-20T13:19:51.774Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541890 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_POS_Order_Status
-- Value: DR
-- ValueName: Drafted
-- 2024-09-20T13:20:14.421Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541890,543715,TO_TIMESTAMP('2024-09-20 16:20:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Drafted',TO_TIMESTAMP('2024-09-20 16:20:14','YYYY-MM-DD HH24:MI:SS'),100,'DR','Drafted')
;

-- 2024-09-20T13:20:14.423Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543715 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_Order_Status
-- Value: WP
-- ValueName: WaitingPayment
-- 2024-09-20T13:20:33.180Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541890,543716,TO_TIMESTAMP('2024-09-20 16:20:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Waiting Payment',TO_TIMESTAMP('2024-09-20 16:20:33','YYYY-MM-DD HH24:MI:SS'),100,'WP','WaitingPayment')
;

-- 2024-09-20T13:20:33.182Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543716 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_Order_Status
-- Value: CO
-- ValueName: Completed
-- 2024-09-20T13:20:45.206Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541890,543717,TO_TIMESTAMP('2024-09-20 16:20:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Completed',TO_TIMESTAMP('2024-09-20 16:20:45','YYYY-MM-DD HH24:MI:SS'),100,'CO','Completed')
;

-- 2024-09-20T13:20:45.208Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543717 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_Order_Status
-- Value: VO
-- ValueName: Voided
-- 2024-09-20T13:20:58.784Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541890,543718,TO_TIMESTAMP('2024-09-20 16:20:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Voided',TO_TIMESTAMP('2024-09-20 16:20:58','YYYY-MM-DD HH24:MI:SS'),100,'VO','Voided')
;

-- 2024-09-20T13:20:58.785Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543718 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_POS_Order.Status
-- Column: C_POS_Order.Status
-- 2024-09-20T13:21:18.096Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589027,3020,0,17,541890,542434,'XX','Status',TO_TIMESTAMP('2024-09-20 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.pos',0,2,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Status',0,0,TO_TIMESTAMP('2024-09-20 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:21:18.098Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589027 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:21:18.102Z
/* DDL */  select update_Column_Translation_From_AD_Element(3020) 
;

-- Column: C_POS_Order.C_DocTypeOrder_ID
-- Column: C_POS_Order.C_DocTypeOrder_ID
-- 2024-09-20T13:22:10.150Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589028,577366,0,30,170,542434,208,'XX','C_DocTypeOrder_ID',TO_TIMESTAMP('2024-09-20 16:22:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Document type used for the orders generated from this order candidate','de.metas.pos',0,10,'The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Auftrags-Belegart',0,0,TO_TIMESTAMP('2024-09-20 16:22:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:22:10.153Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589028 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:22:10.158Z
/* DDL */  select update_Column_Translation_From_AD_Element(577366) 
;

-- Column: C_POS_Order.M_PricingSystem_ID
-- Column: C_POS_Order.M_PricingSystem_ID
-- 2024-09-20T13:22:25.077Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589029,505135,0,30,542434,'XX','M_PricingSystem_ID',TO_TIMESTAMP('2024-09-20 16:22:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','de.metas.pos',0,10,'Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preissystem',0,0,TO_TIMESTAMP('2024-09-20 16:22:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:22:25.079Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589029 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:22:25.082Z
/* DDL */  select update_Column_Translation_From_AD_Element(505135) 
;

-- Column: C_POS_Order.M_PriceList_ID
-- Column: C_POS_Order.M_PriceList_ID
-- 2024-09-20T13:22:35.312Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589030,449,0,30,542434,'XX','M_PriceList_ID',TO_TIMESTAMP('2024-09-20 16:22:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnung der Preisliste','de.metas.pos',0,10,'Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preisliste',0,0,TO_TIMESTAMP('2024-09-20 16:22:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:22:35.315Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589030 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:22:35.320Z
/* DDL */  select update_Column_Translation_From_AD_Element(449) 
;

-- Column: C_POS_Order.M_PriceList_ID
-- Column: C_POS_Order.M_PriceList_ID
-- 2024-09-20T13:22:36.600Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-20 16:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589030
;

-- Column: C_POS_Order.C_BP_BankAccount_ID
-- Column: C_POS_Order.C_BP_BankAccount_ID
-- 2024-09-20T13:22:55.203Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589031,837,0,30,542434,'XX','C_BP_BankAccount_ID',TO_TIMESTAMP('2024-09-20 16:22:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Bankverbindung des Geschäftspartners','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bankverbindung',0,0,TO_TIMESTAMP('2024-09-20 16:22:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:22:55.206Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589031 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:22:55.208Z
/* DDL */  select update_Column_Translation_From_AD_Element(837) 
;

-- 2024-09-20T13:23:22.315Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583267,0,'Cashier_ID',TO_TIMESTAMP('2024-09-20 16:23:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Cashier','Cashier',TO_TIMESTAMP('2024-09-20 16:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:23:22.317Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583267 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Order.Cashier_ID
-- Column: C_POS_Order.Cashier_ID
-- 2024-09-20T13:24:00.493Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589059,583267,0,30,110,542434,'XX','Cashier_ID',TO_TIMESTAMP('2024-09-20 16:24:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cashier',0,0,TO_TIMESTAMP('2024-09-20 16:24:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:24:00.495Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589059 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:24:00.497Z
/* DDL */  select update_Column_Translation_From_AD_Element(583267) 
;

-- Column: C_POS_Order.DateTrx
-- Column: C_POS_Order.DateTrx
-- 2024-09-20T13:24:27.659Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589060,1297,0,16,542434,'XX','DateTrx',TO_TIMESTAMP('2024-09-20 16:24:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Vorgangsdatum','de.metas.pos',0,7,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Vorgangsdatum',0,0,TO_TIMESTAMP('2024-09-20 16:24:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:24:27.661Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589060 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:24:27.665Z
/* DDL */  select update_Column_Translation_From_AD_Element(1297) 
;

-- Column: C_POS_Order.C_BPartner_ID
-- Column: C_POS_Order.C_BPartner_ID
-- 2024-09-20T13:24:43.190Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589061,187,0,30,542434,'XX','C_BPartner_ID',TO_TIMESTAMP('2024-09-20 16:24:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','de.metas.pos',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2024-09-20 16:24:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:24:43.194Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589061 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:24:43.198Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: C_POS_Order.C_BPartner_Location_ID
-- Column: C_POS_Order.C_BPartner_Location_ID
-- 2024-09-20T13:26:50.695Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589062,189,0,30,542434,167,'XX','C_BPartner_Location_ID',TO_TIMESTAMP('2024-09-20 16:26:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','de.metas.pos',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2024-09-20 16:26:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:26:50.696Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589062 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:26:50.700Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- Column: C_POS_Order.C_BPartner_Location_ID
-- Column: C_POS_Order.C_BPartner_Location_ID
-- 2024-09-20T13:26:54.463Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-20 16:26:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589062
;

-- Column: C_POS_Order.C_BPartner_Location_Value_ID
-- Column: C_POS_Order.C_BPartner_Location_Value_ID
-- 2024-09-20T13:27:20.649Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589063,579023,0,21,542434,'XX','C_BPartner_Location_Value_ID',TO_TIMESTAMP('2024-09-20 16:27:20','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.pos',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort (Address)',0,0,TO_TIMESTAMP('2024-09-20 16:27:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:27:20.651Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589063 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:27:20.654Z
/* DDL */  select update_Column_Translation_From_AD_Element(579023) 
;

-- Column: C_POS_Order.M_Warehouse_ID
-- Column: C_POS_Order.M_Warehouse_ID
-- 2024-09-20T13:27:43.227Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589064,459,0,30,542434,'XX','M_Warehouse_ID',TO_TIMESTAMP('2024-09-20 16:27:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Lager oder Ort für Dienstleistung','de.metas.pos',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lager',0,0,TO_TIMESTAMP('2024-09-20 16:27:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:27:43.229Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589064 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:27:43.232Z
/* DDL */  select update_Column_Translation_From_AD_Element(459) 
;

-- Column: C_POS_Order.C_Country_ID
-- Column: C_POS_Order.C_Country_ID
-- 2024-09-20T13:27:57.478Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589065,192,0,30,542434,'XX','C_Country_ID',TO_TIMESTAMP('2024-09-20 16:27:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Land','de.metas.pos',0,10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Land',0,0,TO_TIMESTAMP('2024-09-20 16:27:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:27:57.480Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589065 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:27:57.483Z
/* DDL */  select update_Column_Translation_From_AD_Element(192) 
;

-- Column: C_POS_Order.IsTaxIncluded
-- Column: C_POS_Order.IsTaxIncluded
-- 2024-09-20T13:28:12.432Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589066,1065,0,20,542434,'XX','IsTaxIncluded',TO_TIMESTAMP('2024-09-20 16:28:12','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Tax is included in the price ','de.metas.pos',0,1,'The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preis inklusive Steuern',0,0,TO_TIMESTAMP('2024-09-20 16:28:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:28:12.434Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589066 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:28:12.436Z
/* DDL */  select update_Column_Translation_From_AD_Element(1065) 
;

-- Column: C_POS_Order.C_Currency_ID
-- Column: C_POS_Order.C_Currency_ID
-- 2024-09-20T13:28:24.371Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589067,193,0,30,542434,'XX','C_Currency_ID',TO_TIMESTAMP('2024-09-20 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Währung für diesen Eintrag','de.metas.pos',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2024-09-20 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:28:24.373Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589067 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:28:24.376Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: C_POS_Order.GrandTotal
-- Column: C_POS_Order.GrandTotal
-- 2024-09-20T13:28:45.678Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589068,316,0,12,542434,'XX','GrandTotal',TO_TIMESTAMP('2024-09-20 16:28:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Summe über Alles zu diesem Beleg','de.metas.pos',0,10,'Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Summe Gesamt',0,0,TO_TIMESTAMP('2024-09-20 16:28:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:28:45.680Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589068 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:28:45.683Z
/* DDL */  select update_Column_Translation_From_AD_Element(316) 
;

-- Column: C_POS_Order.TaxAmt
-- Column: C_POS_Order.TaxAmt
-- 2024-09-20T13:28:55.721Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589069,1133,0,12,542434,'XX','TaxAmt',TO_TIMESTAMP('2024-09-20 16:28:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Steuerbetrag für diesen Beleg','de.metas.pos',0,10,'Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Steuerbetrag',0,0,TO_TIMESTAMP('2024-09-20 16:28:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:28:55.723Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589069 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:28:55.726Z
/* DDL */  select update_Column_Translation_From_AD_Element(1133) 
;

-- Column: C_POS_Order.PaidAmt
-- Column: C_POS_Order.PaidAmt
-- 2024-09-20T13:29:09.951Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589070,1498,0,12,542434,'XX','PaidAmt',TO_TIMESTAMP('2024-09-20 16:29:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gezahlter Betrag',0,0,TO_TIMESTAMP('2024-09-20 16:29:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:29:09.954Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589070 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:29:09.957Z
/* DDL */  select update_Column_Translation_From_AD_Element(1498) 
;

-- Column: C_POS_Order.OpenAmt
-- Column: C_POS_Order.OpenAmt
-- 2024-09-20T13:29:17.697Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589071,1526,0,12,542434,'XX','OpenAmt',TO_TIMESTAMP('2024-09-20 16:29:17','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Offener Betrag',0,0,TO_TIMESTAMP('2024-09-20 16:29:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:29:17.698Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589071 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:29:17.701Z
/* DDL */  select update_Column_Translation_From_AD_Element(1526) 
;

-- Column: C_POS_Order.DocumentNo
-- Column: C_POS_Order.DocumentNo
-- 2024-09-20T13:30:11.758Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589072,290,0,10,542434,'XX','DocumentNo',TO_TIMESTAMP('2024-09-20 16:30:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Document sequence number of the document','de.metas.pos',0,40,'E','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','Y',0,'Nr.',0,1,TO_TIMESTAMP('2024-09-20 16:30:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:30:11.760Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589072 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:30:11.762Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- Column: C_POS_Order.C_POS_ID
-- Column: C_POS_Order.C_POS_ID
-- 2024-09-20T13:30:39.768Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589073,2581,0,30,542434,'XX','C_POS_ID',TO_TIMESTAMP('2024-09-20 16:30:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Point of Sales Terminal','de.metas.pos',0,10,'"POS-Terminal" definiert Standardwerte und Funktionen fürdas POS-Fenster.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'POS-Terminal',0,0,TO_TIMESTAMP('2024-09-20 16:30:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:30:39.770Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589073 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:30:39.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(2581) 
;

-- 2024-09-20T13:30:48.297Z
/* DDL */ CREATE TABLE public.C_POS_Order (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Cashier_ID NUMERIC(10) NOT NULL, C_BPartner_ID NUMERIC(10) NOT NULL, C_BPartner_Location_ID NUMERIC(10) NOT NULL, C_BPartner_Location_Value_ID NUMERIC(10), C_BP_BankAccount_ID NUMERIC(10), C_Country_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, C_DocTypeOrder_ID NUMERIC(10) NOT NULL, C_POS_ID NUMERIC(10) NOT NULL, C_POS_Order_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateTrx TIMESTAMP WITH TIME ZONE NOT NULL, DocumentNo VARCHAR(40) NOT NULL, ExternalId VARCHAR(255), GrandTotal NUMERIC NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsTaxIncluded CHAR(1) DEFAULT 'N' CHECK (IsTaxIncluded IN ('Y','N')) NOT NULL, M_PriceList_ID NUMERIC(10) NOT NULL, M_PricingSystem_ID NUMERIC(10) NOT NULL, M_Warehouse_ID NUMERIC(10) NOT NULL, OpenAmt NUMERIC NOT NULL, PaidAmt NUMERIC NOT NULL, Status VARCHAR(2) NOT NULL, TaxAmt NUMERIC NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Cashier_CPOSOrder FOREIGN KEY (Cashier_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartner_CPOSOrder FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartnerLocation_CPOSOrder FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPartnerLocationValue_CPOSOrder FOREIGN KEY (C_BPartner_Location_Value_ID) REFERENCES public.C_Location DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CBPBankAccount_CPOSOrder FOREIGN KEY (C_BP_BankAccount_ID) REFERENCES public.C_BP_BankAccount DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CCountry_CPOSOrder FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CCurrency_CPOSOrder FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CDocTypeOrder_CPOSOrder FOREIGN KEY (C_DocTypeOrder_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CPOS_CPOSOrder FOREIGN KEY (C_POS_ID) REFERENCES public.C_POS DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_POS_Order_Key PRIMARY KEY (C_POS_Order_ID), CONSTRAINT MPriceList_CPOSOrder FOREIGN KEY (M_PriceList_ID) REFERENCES public.M_PriceList DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MPricingSystem_CPOSOrder FOREIGN KEY (M_PricingSystem_ID) REFERENCES public.M_PricingSystem DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MWarehouse_CPOSOrder FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED)
;

-- Table: C_POS_OrderLine
-- Table: C_POS_OrderLine
-- 2024-09-20T13:31:12.493Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('1',0,0,0,542436,'A','N',TO_TIMESTAMP('2024-09-20 16:31:12','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.pos','N','Y','N','N','Y','N','N','N','N','N',0,'POS Order Line','NP','L','C_POS_OrderLine','DTI',TO_TIMESTAMP('2024-09-20 16:31:12','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-09-20T13:31:12.495Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542436 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-09-20T13:31:12.608Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556367,TO_TIMESTAMP('2024-09-20 16:31:12','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_POS_OrderLine',1,'Y','N','Y','Y','C_POS_OrderLine','N',1000000,TO_TIMESTAMP('2024-09-20 16:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:31:12.614Z
CREATE SEQUENCE C_POS_ORDERLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_POS_OrderLine.AD_Client_ID
-- Column: C_POS_OrderLine.AD_Client_ID
-- 2024-09-20T13:31:17.465Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589074,102,0,19,542436,'AD_Client_ID',TO_TIMESTAMP('2024-09-20 16:31:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.pos',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-09-20 16:31:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:17.468Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589074 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:17.471Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_POS_OrderLine.AD_Org_ID
-- Column: C_POS_OrderLine.AD_Org_ID
-- 2024-09-20T13:31:18.044Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589075,113,0,30,542436,'AD_Org_ID',TO_TIMESTAMP('2024-09-20 16:31:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.pos',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-09-20 16:31:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:18.047Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589075 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:18.050Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_POS_OrderLine.Created
-- Column: C_POS_OrderLine.Created
-- 2024-09-20T13:31:18.595Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589076,245,0,16,542436,'Created',TO_TIMESTAMP('2024-09-20 16:31:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.pos',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-09-20 16:31:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:18.598Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589076 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:18.602Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_POS_OrderLine.CreatedBy
-- Column: C_POS_OrderLine.CreatedBy
-- 2024-09-20T13:31:19.189Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589077,246,0,18,110,542436,'CreatedBy',TO_TIMESTAMP('2024-09-20 16:31:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.pos',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-09-20 16:31:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:19.191Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589077 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:19.193Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_POS_OrderLine.IsActive
-- Column: C_POS_OrderLine.IsActive
-- 2024-09-20T13:31:19.758Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589078,348,0,20,542436,'IsActive',TO_TIMESTAMP('2024-09-20 16:31:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.pos',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-09-20 16:31:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:19.760Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589078 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:19.762Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_POS_OrderLine.Updated
-- Column: C_POS_OrderLine.Updated
-- 2024-09-20T13:31:20.301Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589079,607,0,16,542436,'Updated',TO_TIMESTAMP('2024-09-20 16:31:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.pos',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-09-20 16:31:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:20.304Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589079 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:20.307Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_POS_OrderLine.UpdatedBy
-- Column: C_POS_OrderLine.UpdatedBy
-- 2024-09-20T13:31:20.887Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589080,608,0,18,110,542436,'UpdatedBy',TO_TIMESTAMP('2024-09-20 16:31:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.pos',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-09-20 16:31:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:20.888Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589080 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:20.891Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-09-20T13:31:21.343Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583268,0,'C_POS_OrderLine_ID',TO_TIMESTAMP('2024-09-20 16:31:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','POS Order Line','POS Order Line',TO_TIMESTAMP('2024-09-20 16:31:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:31:21.345Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583268 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_OrderLine.C_POS_OrderLine_ID
-- Column: C_POS_OrderLine.C_POS_OrderLine_ID
-- 2024-09-20T13:31:21.884Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589081,583268,0,13,542436,'C_POS_OrderLine_ID',TO_TIMESTAMP('2024-09-20 16:31:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','POS Order Line',0,0,TO_TIMESTAMP('2024-09-20 16:31:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:21.885Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589081 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:21.888Z
/* DDL */  select update_Column_Translation_From_AD_Element(583268) 
;

-- Column: C_POS_OrderLine.ExternalId
-- Column: C_POS_OrderLine.ExternalId
-- 2024-09-20T13:31:38.386Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589082,543939,0,10,542436,'XX','ExternalId',TO_TIMESTAMP('2024-09-20 16:31:38','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2024-09-20 16:31:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:31:38.388Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589082 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:31:38.391Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- Column: C_POS_OrderLine.M_Product_ID
-- Column: C_POS_OrderLine.M_Product_ID
-- 2024-09-20T13:32:01.671Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589083,454,0,30,542436,'XX','M_Product_ID',TO_TIMESTAMP('2024-09-20 16:32:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','de.metas.pos',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2024-09-20 16:32:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:32:01.673Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589083 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:32:01.675Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: C_POS_OrderLine.ProductName
-- Column: C_POS_OrderLine.ProductName
-- 2024-09-20T13:32:14.698Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589084,2659,0,10,542436,'XX','ProductName',TO_TIMESTAMP('2024-09-20 16:32:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Name des Produktes','de.metas.pos',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Produktname',0,0,TO_TIMESTAMP('2024-09-20 16:32:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:32:14.700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589084 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:32:14.703Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659) 
;

-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- 2024-09-20T13:32:30.139Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589085,211,0,30,542436,'XX','C_TaxCategory_ID',TO_TIMESTAMP('2024-09-20 16:32:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Steuerkategorie','de.metas.pos',0,10,'Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Steuerkategorie',0,0,TO_TIMESTAMP('2024-09-20 16:32:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:32:30.140Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589085 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:32:30.144Z
/* DDL */  select update_Column_Translation_From_AD_Element(211) 
;

-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- 2024-09-20T13:32:31.796Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-20 16:32:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589085
;

-- Column: C_POS_OrderLine.C_Tax_ID
-- Column: C_POS_OrderLine.C_Tax_ID
-- 2024-09-20T13:32:42.270Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589086,213,0,30,542436,'XX','C_Tax_ID',TO_TIMESTAMP('2024-09-20 16:32:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Steuerart','de.metas.pos',0,10,'Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Steuer',0,0,TO_TIMESTAMP('2024-09-20 16:32:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:32:42.272Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589086 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:32:42.274Z
/* DDL */  select update_Column_Translation_From_AD_Element(213) 
;

-- Column: C_POS_OrderLine.Qty
-- Column: C_POS_OrderLine.Qty
-- 2024-09-20T13:33:00.582Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589087,526,0,29,542436,'XX','Qty',TO_TIMESTAMP('2024-09-20 16:33:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge','de.metas.pos',0,10,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Menge',0,0,TO_TIMESTAMP('2024-09-20 16:33:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:33:00.584Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589087 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:33:00.586Z
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- Column: C_POS_OrderLine.C_UOM_ID
-- Column: C_POS_OrderLine.C_UOM_ID
-- 2024-09-20T13:33:14.606Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589090,215,0,30,542436,'XX','C_UOM_ID',TO_TIMESTAMP('2024-09-20 16:33:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','de.metas.pos',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2024-09-20 16:33:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:33:14.609Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589090 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:33:14.612Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- Column: C_POS_OrderLine.C_UOM_ID
-- Column: C_POS_OrderLine.C_UOM_ID
-- 2024-09-20T13:33:16.348Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-20 16:33:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589090
;

-- Column: C_POS_OrderLine.Price
-- Column: C_POS_OrderLine.Price
-- 2024-09-20T13:33:28.718Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589096,1416,0,37,542436,'XX','Price',TO_TIMESTAMP('2024-09-20 16:33:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Preis','de.metas.pos',0,10,'Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preis',0,0,TO_TIMESTAMP('2024-09-20 16:33:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:33:28.719Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589096 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:33:28.722Z
/* DDL */  select update_Column_Translation_From_AD_Element(1416) 
;

-- Column: C_POS_OrderLine.Amount
-- Column: C_POS_OrderLine.Amount
-- 2024-09-20T13:33:45.015Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589097,1367,0,12,542436,'XX','Amount',TO_TIMESTAMP('2024-09-20 16:33:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Betrag in einer definierten Währung','de.metas.pos',0,10,'"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Betrag',0,0,TO_TIMESTAMP('2024-09-20 16:33:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:33:45.017Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589097 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:33:45.019Z
/* DDL */  select update_Column_Translation_From_AD_Element(1367) 
;

-- Column: C_POS_OrderLine.TaxAmt
-- Column: C_POS_OrderLine.TaxAmt
-- 2024-09-20T13:33:54.517Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589098,1133,0,12,542436,'XX','TaxAmt',TO_TIMESTAMP('2024-09-20 16:33:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Steuerbetrag für diesen Beleg','de.metas.pos',0,10,'Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Steuerbetrag',0,0,TO_TIMESTAMP('2024-09-20 16:33:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:33:54.519Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589098 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:33:54.522Z
/* DDL */  select update_Column_Translation_From_AD_Element(1133) 
;

-- Column: C_POS_OrderLine.C_POS_Order_ID
-- Column: C_POS_OrderLine.C_POS_Order_ID
-- 2024-09-20T13:34:37.458Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589099,583266,0,30,542436,'XX','C_POS_Order_ID',TO_TIMESTAMP('2024-09-20 16:34:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'POS Order',0,0,TO_TIMESTAMP('2024-09-20 16:34:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:34:37.459Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589099 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:34:37.461Z
/* DDL */  select update_Column_Translation_From_AD_Element(583266) 
;

-- Table: C_POS_Payment
-- Table: C_POS_Payment
-- 2024-09-20T13:37:35.568Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,Description,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('1',0,0,0,542437,'A','N',TO_TIMESTAMP('2024-09-20 16:37:35','YYYY-MM-DD HH24:MI:SS'),100,'Point of Sale Payment','A','de.metas.pos','N','Y','N','N','Y','N','N','N','N','N',0,'POS Payment','NP','L','C_POS_Payment','DTI',TO_TIMESTAMP('2024-09-20 16:37:35','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-09-20T13:37:35.571Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542437 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-09-20T13:37:35.682Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556368,TO_TIMESTAMP('2024-09-20 16:37:35','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_POS_Payment',1,'Y','N','Y','Y','C_POS_Payment','N',1000000,TO_TIMESTAMP('2024-09-20 16:37:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:37:35.688Z
CREATE SEQUENCE C_POS_PAYMENT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_POS_Payment.AD_Client_ID
-- Column: C_POS_Payment.AD_Client_ID
-- 2024-09-20T13:37:39.410Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589107,102,0,19,542437,'AD_Client_ID',TO_TIMESTAMP('2024-09-20 16:37:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.pos',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-09-20 16:37:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:39.413Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589107 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:39.416Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_POS_Payment.AD_Org_ID
-- Column: C_POS_Payment.AD_Org_ID
-- 2024-09-20T13:37:40.017Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589108,113,0,30,542437,'AD_Org_ID',TO_TIMESTAMP('2024-09-20 16:37:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.pos',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-09-20 16:37:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:40.019Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589108 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:40.023Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_POS_Payment.Created
-- Column: C_POS_Payment.Created
-- 2024-09-20T13:37:40.621Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589109,245,0,16,542437,'Created',TO_TIMESTAMP('2024-09-20 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.pos',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-09-20 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:40.622Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589109 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:40.625Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_POS_Payment.CreatedBy
-- Column: C_POS_Payment.CreatedBy
-- 2024-09-20T13:37:41.198Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589110,246,0,18,110,542437,'CreatedBy',TO_TIMESTAMP('2024-09-20 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.pos',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-09-20 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:41.200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589110 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:41.203Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_POS_Payment.IsActive
-- Column: C_POS_Payment.IsActive
-- 2024-09-20T13:37:41.769Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589111,348,0,20,542437,'IsActive',TO_TIMESTAMP('2024-09-20 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.pos',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-09-20 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:41.772Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589111 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:41.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_POS_Payment.Updated
-- Column: C_POS_Payment.Updated
-- 2024-09-20T13:37:42.330Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589112,607,0,16,542437,'Updated',TO_TIMESTAMP('2024-09-20 16:37:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.pos',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-09-20 16:37:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:42.332Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589112 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:42.335Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_POS_Payment.UpdatedBy
-- Column: C_POS_Payment.UpdatedBy
-- 2024-09-20T13:37:42.885Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589113,608,0,18,110,542437,'UpdatedBy',TO_TIMESTAMP('2024-09-20 16:37:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.pos',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-09-20 16:37:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:42.887Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589113 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:42.890Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-09-20T13:37:43.371Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583269,0,'C_POS_Payment_ID',TO_TIMESTAMP('2024-09-20 16:37:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','POS Payment','POS Payment',TO_TIMESTAMP('2024-09-20 16:37:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:37:43.373Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583269 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Payment.C_POS_Payment_ID
-- Column: C_POS_Payment.C_POS_Payment_ID
-- 2024-09-20T13:37:43.895Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589114,583269,0,13,542437,'C_POS_Payment_ID',TO_TIMESTAMP('2024-09-20 16:37:43','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','POS Payment',0,0,TO_TIMESTAMP('2024-09-20 16:37:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:37:43.897Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589114 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:37:43.899Z
/* DDL */  select update_Column_Translation_From_AD_Element(583269) 
;

-- Column: C_POS_Payment.ExternalId
-- Column: C_POS_Payment.ExternalId
-- 2024-09-20T13:38:47.726Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589115,543939,0,10,542437,'XX','ExternalId',TO_TIMESTAMP('2024-09-20 16:38:46','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2024-09-20 16:38:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:38:47.728Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589115 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:38:47.731Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- 2024-09-20T13:39:31.919Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583270,0,'POSPaymentMethod',TO_TIMESTAMP('2024-09-20 16:39:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','POS Payment Method','POS Payment Method',TO_TIMESTAMP('2024-09-20 16:39:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:39:31.922Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583270 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: POSPaymentMethod
-- 2024-09-20T13:39:59.216Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541891,TO_TIMESTAMP('2024-09-20 16:39:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','N','POSPaymentMethod',TO_TIMESTAMP('2024-09-20 16:39:59','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-09-20T13:39:59.219Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541891 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: POSPaymentMethod
-- Value: CASH
-- ValueName: Cash
-- 2024-09-20T13:40:35.415Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541891,543719,TO_TIMESTAMP('2024-09-20 16:40:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Cash',TO_TIMESTAMP('2024-09-20 16:40:35','YYYY-MM-DD HH24:MI:SS'),100,'CASH','Cash')
;

-- 2024-09-20T13:40:35.417Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543719 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: POSPaymentMethod
-- Value: CARD
-- ValueName: Card
-- 2024-09-20T13:40:46.260Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541891,543720,TO_TIMESTAMP('2024-09-20 16:40:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Card',TO_TIMESTAMP('2024-09-20 16:40:46','YYYY-MM-DD HH24:MI:SS'),100,'CARD','Card')
;

-- 2024-09-20T13:40:46.262Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543720 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_POS_Payment.POSPaymentMethod
-- Column: C_POS_Payment.POSPaymentMethod
-- 2024-09-20T13:41:08.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589116,583270,0,17,541891,542437,'XX','POSPaymentMethod',TO_TIMESTAMP('2024-09-20 16:41:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'POS Payment Method',0,0,TO_TIMESTAMP('2024-09-20 16:41:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:41:08.306Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589116 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:41:08.308Z
/* DDL */  select update_Column_Translation_From_AD_Element(583270) 
;

-- Column: C_POS_Payment.Amount
-- Column: C_POS_Payment.Amount
-- 2024-09-20T13:41:18.795Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589117,1367,0,12,542437,'XX','Amount',TO_TIMESTAMP('2024-09-20 16:41:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Betrag in einer definierten Währung','de.metas.pos',0,10,'"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Betrag',0,0,TO_TIMESTAMP('2024-09-20 16:41:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:41:18.797Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589117 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:41:18.799Z
/* DDL */  select update_Column_Translation_From_AD_Element(1367) 
;

-- Column: C_POS_Payment.C_POS_Order_ID
-- Column: C_POS_Payment.C_POS_Order_ID
-- 2024-09-20T13:41:47.942Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589118,583266,0,30,542437,'XX','C_POS_Order_ID',TO_TIMESTAMP('2024-09-20 16:41:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'POS Order',0,0,TO_TIMESTAMP('2024-09-20 16:41:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T13:41:47.944Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589118 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:41:47.946Z
/* DDL */  select update_Column_Translation_From_AD_Element(583266) 
;

-- 2024-09-20T13:42:36.006Z
/* DDL */ CREATE TABLE public.C_POS_OrderLine (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, C_POS_Order_ID NUMERIC(10) NOT NULL, C_POS_OrderLine_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_TaxCategory_ID NUMERIC(10) NOT NULL, C_Tax_ID NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10) NOT NULL, ExternalId VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_ID NUMERIC(10) NOT NULL, Price NUMERIC NOT NULL, ProductName VARCHAR(255) NOT NULL, Qty NUMERIC NOT NULL, TaxAmt NUMERIC NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CPOSOrder_CPOSOrderLine FOREIGN KEY (C_POS_Order_ID) REFERENCES public.C_POS_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_POS_OrderLine_Key PRIMARY KEY (C_POS_OrderLine_ID), CONSTRAINT CTaxCategory_CPOSOrderLine FOREIGN KEY (C_TaxCategory_ID) REFERENCES public.C_TaxCategory DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CTax_CPOSOrderLine FOREIGN KEY (C_Tax_ID) REFERENCES public.C_Tax DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CUOM_CPOSOrderLine FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProduct_CPOSOrderLine FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2024-09-20T13:42:43.581Z
/* DDL */ CREATE TABLE public.C_POS_Payment (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, C_POS_Order_ID NUMERIC(10) NOT NULL, C_POS_Payment_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalId VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, POSPaymentMethod VARCHAR(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CPOSOrder_CPOSPayment FOREIGN KEY (C_POS_Order_ID) REFERENCES public.C_POS_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_POS_Payment_Key PRIMARY KEY (C_POS_Payment_ID))
;

-- Window: POS Order, InternalName=posOrder
-- Window: POS Order, InternalName=posOrder
-- 2024-09-20T13:43:31.057Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583266,0,541818,TO_TIMESTAMP('2024-09-20 16:43:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','posOrder','Y','N','N','N','N','N','N','Y','POS Order','N',TO_TIMESTAMP('2024-09-20 16:43:30','YYYY-MM-DD HH24:MI:SS'),100,'T',0,0,100)
;

-- 2024-09-20T13:43:31.061Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541818 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-09-20T13:43:31.064Z
/* DDL */  select update_window_translation_from_ad_element(583266) 
;

-- 2024-09-20T13:43:31.073Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541818
;

-- 2024-09-20T13:43:31.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541818)
;

-- Tab: POS Order -> POS Order
-- Table: C_POS_Order
-- Tab: POS Order(541818,de.metas.pos) -> POS Order
-- Table: C_POS_Order
-- 2024-09-20T13:44:08.859Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583266,0,547591,542434,541818,'Y',TO_TIMESTAMP('2024-09-20 16:44:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','N','N','A','C_POS_Order','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'POS Order','N',10,0,TO_TIMESTAMP('2024-09-20 16:44:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:08.863Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547591 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-09-20T13:44:08.866Z
/* DDL */  select update_tab_translation_from_ad_element(583266) 
;

-- 2024-09-20T13:44:08.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547591)
;

-- Field: POS Order -> POS Order -> Mandant
-- Column: C_POS_Order.AD_Client_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Mandant
-- Column: C_POS_Order.AD_Client_ID
-- 2024-09-20T13:44:11.564Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589018,730866,0,547591,TO_TIMESTAMP('2024-09-20 16:44:11','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.pos','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-09-20 16:44:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:11.569Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:11.574Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-09-20T13:44:12.489Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730866
;

-- 2024-09-20T13:44:12.490Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730866)
;

-- Field: POS Order -> POS Order -> Organisation
-- Column: C_POS_Order.AD_Org_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Organisation
-- Column: C_POS_Order.AD_Org_ID
-- 2024-09-20T13:44:12.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589019,730867,0,547591,TO_TIMESTAMP('2024-09-20 16:44:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.pos','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-09-20 16:44:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:12.614Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:12.615Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-09-20T13:44:12.747Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730867
;

-- 2024-09-20T13:44:12.748Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730867)
;

-- Field: POS Order -> POS Order -> Aktiv
-- Column: C_POS_Order.IsActive
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Aktiv
-- Column: C_POS_Order.IsActive
-- 2024-09-20T13:44:12.860Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589022,730868,0,547591,TO_TIMESTAMP('2024-09-20 16:44:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.pos','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-09-20 16:44:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:12.862Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:12.864Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-09-20T13:44:13.071Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730868
;

-- 2024-09-20T13:44:13.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730868)
;

-- Field: POS Order -> POS Order -> POS Order
-- Column: C_POS_Order.C_POS_Order_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> POS Order
-- Column: C_POS_Order.C_POS_Order_ID
-- 2024-09-20T13:44:13.207Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589025,730869,0,547591,TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Order',TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:13.209Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:13.210Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583266) 
;

-- 2024-09-20T13:44:13.213Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730869
;

-- 2024-09-20T13:44:13.214Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730869)
;

-- Field: POS Order -> POS Order -> Externe ID
-- Column: C_POS_Order.ExternalId
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Externe ID
-- Column: C_POS_Order.ExternalId
-- 2024-09-20T13:44:13.336Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589026,730870,0,547591,TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:13.337Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:13.339Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2024-09-20T13:44:13.348Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730870
;

-- 2024-09-20T13:44:13.349Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730870)
;

-- Field: POS Order -> POS Order -> Status
-- Column: C_POS_Order.Status
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Status
-- Column: C_POS_Order.Status
-- 2024-09-20T13:44:13.456Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589027,730871,0,547591,TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100,'',2,'de.metas.pos','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:13.457Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:13.459Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2024-09-20T13:44:13.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730871
;

-- 2024-09-20T13:44:13.472Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730871)
;

-- Field: POS Order -> POS Order -> Auftrags-Belegart
-- Column: C_POS_Order.C_DocTypeOrder_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Auftrags-Belegart
-- Column: C_POS_Order.C_DocTypeOrder_ID
-- 2024-09-20T13:44:13.599Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589028,730872,0,547591,TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for the orders generated from this order candidate',10,'de.metas.pos','The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.','Y','N','N','N','N','N','N','N','Auftrags-Belegart',TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:13.600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:13.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577366) 
;

-- 2024-09-20T13:44:13.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730872
;

-- 2024-09-20T13:44:13.607Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730872)
;

-- Field: POS Order -> POS Order -> Preissystem
-- Column: C_POS_Order.M_PricingSystem_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Preissystem
-- Column: C_POS_Order.M_PricingSystem_ID
-- 2024-09-20T13:44:13.723Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589029,730873,0,547591,TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',10,'de.metas.pos','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','N','N','N','N','N','Preissystem',TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:13.725Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:13.727Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505135) 
;

-- 2024-09-20T13:44:13.738Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730873
;

-- 2024-09-20T13:44:13.739Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730873)
;

-- Field: POS Order -> POS Order -> Preisliste
-- Column: C_POS_Order.M_PriceList_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Preisliste
-- Column: C_POS_Order.M_PriceList_ID
-- 2024-09-20T13:44:13.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589030,730874,0,547591,TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste',10,'de.metas.pos','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','N','N','N','N','N','N','Preisliste',TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:13.855Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:13.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(449) 
;

-- 2024-09-20T13:44:13.869Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730874
;

-- 2024-09-20T13:44:13.869Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730874)
;

-- Field: POS Order -> POS Order -> Bankverbindung
-- Column: C_POS_Order.C_BP_BankAccount_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Bankverbindung
-- Column: C_POS_Order.C_BP_BankAccount_ID
-- 2024-09-20T13:44:13.978Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589031,730875,0,547591,TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners',10,'de.metas.pos','Y','N','N','N','N','N','N','N','Bankverbindung',TO_TIMESTAMP('2024-09-20 16:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:13.980Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:13.983Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(837) 
;

-- 2024-09-20T13:44:13.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730875
;

-- 2024-09-20T13:44:13.998Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730875)
;

-- Field: POS Order -> POS Order -> Cashier
-- Column: C_POS_Order.Cashier_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Cashier
-- Column: C_POS_Order.Cashier_ID
-- 2024-09-20T13:44:14.104Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589059,730876,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','Cashier',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.106Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.108Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583267) 
;

-- 2024-09-20T13:44:14.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730876
;

-- 2024-09-20T13:44:14.110Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730876)
;

-- Field: POS Order -> POS Order -> Vorgangsdatum
-- Column: C_POS_Order.DateTrx
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Vorgangsdatum
-- Column: C_POS_Order.DateTrx
-- 2024-09-20T13:44:14.217Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589060,730877,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',7,'de.metas.pos','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.220Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.222Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297) 
;

-- 2024-09-20T13:44:14.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730877
;

-- 2024-09-20T13:44:14.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730877)
;

-- Field: POS Order -> POS Order -> Geschäftspartner
-- Column: C_POS_Order.C_BPartner_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Geschäftspartner
-- Column: C_POS_Order.C_BPartner_ID
-- 2024-09-20T13:44:14.355Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589061,730878,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.pos','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.356Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.357Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2024-09-20T13:44:14.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730878
;

-- 2024-09-20T13:44:14.371Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730878)
;

-- Field: POS Order -> POS Order -> Standort
-- Column: C_POS_Order.C_BPartner_Location_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Standort
-- Column: C_POS_Order.C_BPartner_Location_ID
-- 2024-09-20T13:44:14.478Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589062,730879,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.pos','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.480Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.482Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2024-09-20T13:44:14.494Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730879
;

-- 2024-09-20T13:44:14.495Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730879)
;

-- Field: POS Order -> POS Order -> Standort (Address)
-- Column: C_POS_Order.C_BPartner_Location_Value_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Standort (Address)
-- Column: C_POS_Order.C_BPartner_Location_Value_ID
-- 2024-09-20T13:44:14.609Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589063,730880,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.pos','','Y','N','N','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.610Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.611Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023) 
;

-- 2024-09-20T13:44:14.616Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730880
;

-- 2024-09-20T13:44:14.616Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730880)
;

-- Field: POS Order -> POS Order -> Lager
-- Column: C_POS_Order.M_Warehouse_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Lager
-- Column: C_POS_Order.M_Warehouse_ID
-- 2024-09-20T13:44:14.732Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589064,730881,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',10,'de.metas.pos','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.737Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2024-09-20T13:44:14.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730881
;

-- 2024-09-20T13:44:14.760Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730881)
;

-- Field: POS Order -> POS Order -> Land
-- Column: C_POS_Order.C_Country_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Land
-- Column: C_POS_Order.C_Country_ID
-- 2024-09-20T13:44:14.870Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589065,730882,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'de.metas.pos','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.872Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.873Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192) 
;

-- 2024-09-20T13:44:14.881Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730882
;

-- 2024-09-20T13:44:14.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730882)
;

-- Field: POS Order -> POS Order -> Preis inklusive Steuern
-- Column: C_POS_Order.IsTaxIncluded
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Preis inklusive Steuern
-- Column: C_POS_Order.IsTaxIncluded
-- 2024-09-20T13:44:14.995Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589066,730883,0,547591,TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100,'Tax is included in the price ',1,'de.metas.pos','The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.','Y','N','N','N','N','N','N','N','Preis inklusive Steuern',TO_TIMESTAMP('2024-09-20 16:44:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:14.997Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:14.999Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1065) 
;

-- 2024-09-20T13:44:15.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730883
;

-- 2024-09-20T13:44:15.007Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730883)
;

-- Field: POS Order -> POS Order -> Währung
-- Column: C_POS_Order.C_Currency_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Währung
-- Column: C_POS_Order.C_Currency_ID
-- 2024-09-20T13:44:15.128Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589067,730884,0,547591,TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'de.metas.pos','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:15.129Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:15.130Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2024-09-20T13:44:15.159Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730884
;

-- 2024-09-20T13:44:15.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730884)
;

-- Field: POS Order -> POS Order -> Summe Gesamt
-- Column: C_POS_Order.GrandTotal
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Summe Gesamt
-- Column: C_POS_Order.GrandTotal
-- 2024-09-20T13:44:15.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589068,730885,0,547591,TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100,'Summe über Alles zu diesem Beleg',10,'de.metas.pos','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','N','N','N','N','N','N','Summe Gesamt',TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:15.268Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:15.269Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(316) 
;

-- 2024-09-20T13:44:15.283Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730885
;

-- 2024-09-20T13:44:15.284Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730885)
;

-- Field: POS Order -> POS Order -> Steuerbetrag
-- Column: C_POS_Order.TaxAmt
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Steuerbetrag
-- Column: C_POS_Order.TaxAmt
-- 2024-09-20T13:44:15.400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589069,730886,0,547591,TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100,'Steuerbetrag für diesen Beleg',10,'de.metas.pos','Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','N','N','N','N','N','N','Steuerbetrag',TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:15.402Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730886 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:15.403Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1133) 
;

-- 2024-09-20T13:44:15.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730886
;

-- 2024-09-20T13:44:15.407Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730886)
;

-- Field: POS Order -> POS Order -> Gezahlter Betrag
-- Column: C_POS_Order.PaidAmt
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Gezahlter Betrag
-- Column: C_POS_Order.PaidAmt
-- 2024-09-20T13:44:15.526Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589070,730887,0,547591,TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','Gezahlter Betrag',TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:15.528Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:15.530Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1498) 
;

-- 2024-09-20T13:44:15.535Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730887
;

-- 2024-09-20T13:44:15.537Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730887)
;

-- Field: POS Order -> POS Order -> Offener Betrag
-- Column: C_POS_Order.OpenAmt
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Offener Betrag
-- Column: C_POS_Order.OpenAmt
-- 2024-09-20T13:44:15.648Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589071,730888,0,547591,TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','Offener Betrag',TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:15.650Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:15.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1526) 
;

-- 2024-09-20T13:44:15.657Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730888
;

-- 2024-09-20T13:44:15.658Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730888)
;

-- Field: POS Order -> POS Order -> Nr.
-- Column: C_POS_Order.DocumentNo
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> Nr.
-- Column: C_POS_Order.DocumentNo
-- 2024-09-20T13:44:15.775Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589072,730889,0,547591,TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',40,'de.metas.pos','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:15.777Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:15.779Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2024-09-20T13:44:15.790Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730889
;

-- 2024-09-20T13:44:15.791Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730889)
;

-- Field: POS Order -> POS Order -> POS-Terminal
-- Column: C_POS_Order.C_POS_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> POS-Terminal
-- Column: C_POS_Order.C_POS_ID
-- 2024-09-20T13:44:15.904Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589073,730890,0,547591,TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100,'Point of Sales Terminal',10,'de.metas.pos','"POS-Terminal" definiert Standardwerte und Funktionen fürdas POS-Fenster.','Y','N','N','N','N','N','N','N','POS-Terminal',TO_TIMESTAMP('2024-09-20 16:44:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:15.906Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:15.908Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2581) 
;

-- 2024-09-20T13:44:15.915Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730890
;

-- 2024-09-20T13:44:15.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730890)
;

-- Tab: POS Order -> POS Order Line
-- Table: C_POS_OrderLine
-- Tab: POS Order(541818,de.metas.pos) -> POS Order Line
-- Table: C_POS_OrderLine
-- 2024-09-20T13:44:45.014Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589099,583268,0,547592,542436,541818,'Y',TO_TIMESTAMP('2024-09-20 16:44:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','N','N','A','C_POS_OrderLine','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'POS Order Line',589025,'N',20,1,TO_TIMESTAMP('2024-09-20 16:44:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:45.016Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547592 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-09-20T13:44:45.018Z
/* DDL */  select update_tab_translation_from_ad_element(583268) 
;

-- 2024-09-20T13:44:45.024Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547592)
;

-- Field: POS Order -> POS Order Line -> Mandant
-- Column: C_POS_OrderLine.AD_Client_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Mandant
-- Column: C_POS_OrderLine.AD_Client_ID
-- 2024-09-20T13:44:47.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589074,730891,0,547592,TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.pos','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:47.169Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:47.171Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-09-20T13:44:47.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730891
;

-- 2024-09-20T13:44:47.251Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730891)
;

-- Field: POS Order -> POS Order Line -> Organisation
-- Column: C_POS_OrderLine.AD_Org_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Organisation
-- Column: C_POS_OrderLine.AD_Org_ID
-- 2024-09-20T13:44:47.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589075,730892,0,547592,TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.pos','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:47.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:47.369Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-09-20T13:44:47.424Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730892
;

-- 2024-09-20T13:44:47.425Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730892)
;

-- Field: POS Order -> POS Order Line -> Aktiv
-- Column: C_POS_OrderLine.IsActive
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Aktiv
-- Column: C_POS_OrderLine.IsActive
-- 2024-09-20T13:44:47.551Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589078,730893,0,547592,TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.pos','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:47.552Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:47.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-09-20T13:44:47.607Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730893
;

-- 2024-09-20T13:44:47.608Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730893)
;

-- Field: POS Order -> POS Order Line -> POS Order Line
-- Column: C_POS_OrderLine.C_POS_OrderLine_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> POS Order Line
-- Column: C_POS_OrderLine.C_POS_OrderLine_ID
-- 2024-09-20T13:44:47.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589081,730894,0,547592,TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Order Line',TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:47.720Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:47.722Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583268) 
;

-- 2024-09-20T13:44:47.726Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730894
;

-- 2024-09-20T13:44:47.727Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730894)
;

-- Field: POS Order -> POS Order Line -> Externe ID
-- Column: C_POS_OrderLine.ExternalId
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Externe ID
-- Column: C_POS_OrderLine.ExternalId
-- 2024-09-20T13:44:47.831Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589082,730895,0,547592,TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:47.833Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:47.835Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2024-09-20T13:44:47.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730895
;

-- 2024-09-20T13:44:47.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730895)
;

-- Field: POS Order -> POS Order Line -> Produkt
-- Column: C_POS_OrderLine.M_Product_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Produkt
-- Column: C_POS_OrderLine.M_Product_ID
-- 2024-09-20T13:44:47.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589083,730896,0,547592,TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'de.metas.pos','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2024-09-20 16:44:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:47.949Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:47.950Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2024-09-20T13:44:48.027Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730896
;

-- 2024-09-20T13:44:48.028Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730896)
;

-- Field: POS Order -> POS Order Line -> Produktname
-- Column: C_POS_OrderLine.ProductName
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Produktname
-- Column: C_POS_OrderLine.ProductName
-- 2024-09-20T13:44:48.136Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589084,730897,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes',255,'de.metas.pos','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:48.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:48.139Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659) 
;

-- 2024-09-20T13:44:48.144Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730897
;

-- 2024-09-20T13:44:48.145Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730897)
;

-- Field: POS Order -> POS Order Line -> Steuerkategorie
-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Steuerkategorie
-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- 2024-09-20T13:44:48.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589085,730898,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Steuerkategorie',10,'de.metas.pos','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','N','N','N','N','N','N','Steuerkategorie',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:48.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:48.247Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(211) 
;

-- 2024-09-20T13:44:48.265Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730898
;

-- 2024-09-20T13:44:48.265Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730898)
;

-- Field: POS Order -> POS Order Line -> Steuer
-- Column: C_POS_OrderLine.C_Tax_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Steuer
-- Column: C_POS_OrderLine.C_Tax_ID
-- 2024-09-20T13:44:48.377Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589086,730899,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Steuerart',10,'de.metas.pos','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','N','N','N','N','N','N','Steuer',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:48.380Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:48.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(213) 
;

-- 2024-09-20T13:44:48.396Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730899
;

-- 2024-09-20T13:44:48.397Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730899)
;

-- Field: POS Order -> POS Order Line -> Menge
-- Column: C_POS_OrderLine.Qty
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Menge
-- Column: C_POS_OrderLine.Qty
-- 2024-09-20T13:44:48.519Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589087,730900,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Menge',10,'de.metas.pos','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:48.521Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:48.522Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2024-09-20T13:44:48.545Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730900
;

-- 2024-09-20T13:44:48.546Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730900)
;

-- Field: POS Order -> POS Order Line -> Maßeinheit
-- Column: C_POS_OrderLine.C_UOM_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Maßeinheit
-- Column: C_POS_OrderLine.C_UOM_ID
-- 2024-09-20T13:44:48.660Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589090,730901,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'de.metas.pos','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:48.663Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:48.664Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2024-09-20T13:44:48.724Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730901
;

-- 2024-09-20T13:44:48.726Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730901)
;

-- Field: POS Order -> POS Order Line -> Preis
-- Column: C_POS_OrderLine.Price
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Preis
-- Column: C_POS_OrderLine.Price
-- 2024-09-20T13:44:48.844Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589096,730902,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Preis',10,'de.metas.pos','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','N','N','N','N','N','N','Preis',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:48.846Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:48.847Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1416) 
;

-- 2024-09-20T13:44:48.854Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730902
;

-- 2024-09-20T13:44:48.854Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730902)
;

-- Field: POS Order -> POS Order Line -> Betrag
-- Column: C_POS_OrderLine.Amount
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Betrag
-- Column: C_POS_OrderLine.Amount
-- 2024-09-20T13:44:48.970Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589097,730903,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung',10,'de.metas.pos','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','N','N','N','N','N','Betrag',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:48.972Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730903 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:48.974Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2024-09-20T13:44:48.982Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730903
;

-- 2024-09-20T13:44:48.982Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730903)
;

-- Field: POS Order -> POS Order Line -> Steuerbetrag
-- Column: C_POS_OrderLine.TaxAmt
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> Steuerbetrag
-- Column: C_POS_OrderLine.TaxAmt
-- 2024-09-20T13:44:49.093Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589098,730904,0,547592,TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Steuerbetrag für diesen Beleg',10,'de.metas.pos','Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','N','N','N','N','N','N','Steuerbetrag',TO_TIMESTAMP('2024-09-20 16:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:49.094Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:49.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1133) 
;

-- 2024-09-20T13:44:49.098Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730904
;

-- 2024-09-20T13:44:49.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730904)
;

-- Field: POS Order -> POS Order Line -> POS Order
-- Column: C_POS_OrderLine.C_POS_Order_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> POS Order
-- Column: C_POS_OrderLine.C_POS_Order_ID
-- 2024-09-20T13:44:49.213Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589099,730905,0,547592,TO_TIMESTAMP('2024-09-20 16:44:49','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Order',TO_TIMESTAMP('2024-09-20 16:44:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:44:49.216Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:44:49.218Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583266) 
;

-- 2024-09-20T13:44:49.222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730905
;

-- 2024-09-20T13:44:49.223Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730905)
;

-- Tab: POS Order -> POS Payment
-- Table: C_POS_Payment
-- Tab: POS Order(541818,de.metas.pos) -> POS Payment
-- Table: C_POS_Payment
-- 2024-09-20T13:45:21.525Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589118,583269,0,547593,542437,541818,'Y',TO_TIMESTAMP('2024-09-20 16:45:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','N','N','A','C_POS_Payment','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'POS Payment',589025,'N',30,1,TO_TIMESTAMP('2024-09-20 16:45:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:21.526Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547593 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-09-20T13:45:21.527Z
/* DDL */  select update_tab_translation_from_ad_element(583269) 
;

-- 2024-09-20T13:45:21.532Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547593)
;

-- Field: POS Order -> POS Payment -> Mandant
-- Column: C_POS_Payment.AD_Client_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Mandant
-- Column: C_POS_Payment.AD_Client_ID
-- 2024-09-20T13:45:25.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589107,730906,0,547593,TO_TIMESTAMP('2024-09-20 16:45:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.pos','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-09-20 16:45:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:25.778Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:25.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-09-20T13:45:25.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730906
;

-- 2024-09-20T13:45:25.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730906)
;

-- Field: POS Order -> POS Payment -> Organisation
-- Column: C_POS_Payment.AD_Org_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Organisation
-- Column: C_POS_Payment.AD_Org_ID
-- 2024-09-20T13:45:25.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589108,730907,0,547593,TO_TIMESTAMP('2024-09-20 16:45:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.pos','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-09-20 16:45:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:25.976Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730907 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:25.978Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-09-20T13:45:26.040Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730907
;

-- 2024-09-20T13:45:26.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730907)
;

-- Field: POS Order -> POS Payment -> Aktiv
-- Column: C_POS_Payment.IsActive
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Aktiv
-- Column: C_POS_Payment.IsActive
-- 2024-09-20T13:45:26.155Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589111,730908,0,547593,TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.pos','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:26.157Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:26.158Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-09-20T13:45:26.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730908
;

-- 2024-09-20T13:45:26.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730908)
;

-- Field: POS Order -> POS Payment -> POS Payment
-- Column: C_POS_Payment.C_POS_Payment_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> POS Payment
-- Column: C_POS_Payment.C_POS_Payment_ID
-- 2024-09-20T13:45:26.324Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589114,730909,0,547593,TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Payment',TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:26.326Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:26.327Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583269) 
;

-- 2024-09-20T13:45:26.330Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730909
;

-- 2024-09-20T13:45:26.330Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730909)
;

-- Field: POS Order -> POS Payment -> Externe ID
-- Column: C_POS_Payment.ExternalId
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Externe ID
-- Column: C_POS_Payment.ExternalId
-- 2024-09-20T13:45:26.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589115,730910,0,547593,TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:26.437Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:26.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2024-09-20T13:45:26.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730910
;

-- 2024-09-20T13:45:26.444Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730910)
;

-- Field: POS Order -> POS Payment -> POS Payment Method
-- Column: C_POS_Payment.POSPaymentMethod
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> POS Payment Method
-- Column: C_POS_Payment.POSPaymentMethod
-- 2024-09-20T13:45:26.556Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589116,730911,0,547593,TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Payment Method',TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:26.558Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:26.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583270) 
;

-- 2024-09-20T13:45:26.562Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730911
;

-- 2024-09-20T13:45:26.562Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730911)
;

-- Field: POS Order -> POS Payment -> Betrag
-- Column: C_POS_Payment.Amount
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Betrag
-- Column: C_POS_Payment.Amount
-- 2024-09-20T13:45:26.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589117,730912,0,547593,TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung',10,'de.metas.pos','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','N','N','N','N','N','Betrag',TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:26.680Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:26.682Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2024-09-20T13:45:26.685Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730912
;

-- 2024-09-20T13:45:26.686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730912)
;

-- Field: POS Order -> POS Payment -> POS Order
-- Column: C_POS_Payment.C_POS_Order_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> POS Order
-- Column: C_POS_Payment.C_POS_Order_ID
-- 2024-09-20T13:45:26.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589118,730913,0,547593,TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Order',TO_TIMESTAMP('2024-09-20 16:45:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:45:26.807Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T13:45:26.809Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583266) 
;

-- 2024-09-20T13:45:26.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730913
;

-- 2024-09-20T13:45:26.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730913)
;

-- Tab: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos)
-- UI Section: main
-- 2024-09-20T13:45:33.697Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547591,546174,TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-09-20T13:45:33.700Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546174 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main
-- UI Column: 10
-- 2024-09-20T13:45:33.847Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547546,546174,TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main
-- UI Column: 20
-- 2024-09-20T13:45:33.956Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547547,546174,TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10
-- UI Element Group: default
-- 2024-09-20T13:45:34.126Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547546,551949,TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-20 16:45:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos)
-- UI Section: main
-- 2024-09-20T13:45:34.266Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547592,546175,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-09-20T13:45:34.267Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546175 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main
-- UI Column: 10
-- 2024-09-20T13:45:34.370Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547548,546175,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10
-- UI Element Group: default
-- 2024-09-20T13:45:34.474Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547548,551950,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos)
-- UI Section: main
-- 2024-09-20T13:45:34.596Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547593,546176,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-09-20T13:45:34.598Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546176 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main
-- UI Column: 10
-- 2024-09-20T13:45:34.710Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547549,546176,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 10
-- UI Element Group: default
-- 2024-09-20T13:45:34.811Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547549,551951,TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-20 16:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Geschäftspartner
-- Column: C_POS_Order.C_BPartner_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> default.Geschäftspartner
-- Column: C_POS_Order.C_BPartner_ID
-- 2024-09-20T13:46:59.535Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730878,0,547591,551949,625735,'F',TO_TIMESTAMP('2024-09-20 16:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','Geschäftspartner',10,0,0,TO_TIMESTAMP('2024-09-20 16:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Lager
-- Column: C_POS_Order.M_Warehouse_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> default.Lager
-- Column: C_POS_Order.M_Warehouse_ID
-- 2024-09-20T13:47:49.659Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730881,0,547591,551949,625736,'F',TO_TIMESTAMP('2024-09-20 16:47:49','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','Lager',20,0,0,TO_TIMESTAMP('2024-09-20 16:47:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Land
-- Column: C_POS_Order.C_Country_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> default.Land
-- Column: C_POS_Order.C_Country_ID
-- 2024-09-20T13:48:23.891Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730882,0,547591,551949,625737,'F',TO_TIMESTAMP('2024-09-20 16:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','Land',30,0,0,TO_TIMESTAMP('2024-09-20 16:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10
-- UI Element Group: ship from
-- 2024-09-20T13:48:58.446Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547546,551952,TO_TIMESTAMP('2024-09-20 16:48:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','ship from',20,TO_TIMESTAMP('2024-09-20 16:48:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Lager
-- Column: C_POS_Order.M_Warehouse_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> ship from.Lager
-- Column: C_POS_Order.M_Warehouse_ID
-- 2024-09-20T13:49:14.051Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551952, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-20 16:49:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625736
;

-- UI Element: POS Order -> POS Order.Land
-- Column: C_POS_Order.C_Country_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> ship from.Land
-- Column: C_POS_Order.C_Country_ID
-- 2024-09-20T13:49:21.999Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551952, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-09-20 16:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625737
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10
-- UI Element Group: pricing
-- 2024-09-20T13:49:37.979Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547546,551953,TO_TIMESTAMP('2024-09-20 16:49:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','pricing',30,TO_TIMESTAMP('2024-09-20 16:49:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Preissystem
-- Column: C_POS_Order.M_PricingSystem_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> pricing.Preissystem
-- Column: C_POS_Order.M_PricingSystem_ID
-- 2024-09-20T13:50:08.738Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730873,0,547591,551953,625738,'F',TO_TIMESTAMP('2024-09-20 16:50:08','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','Y','N','N','Preissystem',10,0,0,TO_TIMESTAMP('2024-09-20 16:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Preisliste
-- Column: C_POS_Order.M_PriceList_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> pricing.Preisliste
-- Column: C_POS_Order.M_PriceList_ID
-- 2024-09-20T13:50:21.905Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730874,0,547591,551953,625739,'F',TO_TIMESTAMP('2024-09-20 16:50:20','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','Y','N','N','Preisliste',20,0,0,TO_TIMESTAMP('2024-09-20 16:50:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Preis inklusive Steuern
-- Column: C_POS_Order.IsTaxIncluded
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> pricing.Preis inklusive Steuern
-- Column: C_POS_Order.IsTaxIncluded
-- 2024-09-20T13:50:52.657Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730883,0,547591,551953,625740,'F',TO_TIMESTAMP('2024-09-20 16:50:52','YYYY-MM-DD HH24:MI:SS'),100,'Tax is included in the price ','The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.','Y','N','Y','N','N','Preis inklusive Steuern',30,0,0,TO_TIMESTAMP('2024-09-20 16:50:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Währung
-- Column: C_POS_Order.C_Currency_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> pricing.Währung
-- Column: C_POS_Order.C_Currency_ID
-- 2024-09-20T13:51:05.978Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730884,0,547591,551953,625741,'F',TO_TIMESTAMP('2024-09-20 16:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',40,0,0,TO_TIMESTAMP('2024-09-20 16:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20
-- UI Element Group: doc info
-- 2024-09-20T13:52:19.128Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547547,551954,TO_TIMESTAMP('2024-09-20 16:52:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','doc info',10,TO_TIMESTAMP('2024-09-20 16:52:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Nr.
-- Column: C_POS_Order.DocumentNo
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Nr.
-- Column: C_POS_Order.DocumentNo
-- 2024-09-20T13:52:33.739Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730889,0,547591,551954,625742,'F',TO_TIMESTAMP('2024-09-20 16:52:33','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Nr.',10,0,0,TO_TIMESTAMP('2024-09-20 16:52:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Vorgangsdatum
-- Column: C_POS_Order.DateTrx
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Vorgangsdatum
-- Column: C_POS_Order.DateTrx
-- 2024-09-20T13:53:05.474Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730877,0,547591,551954,625743,'F',TO_TIMESTAMP('2024-09-20 16:53:04','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','Vorgangsdatum',20,0,0,TO_TIMESTAMP('2024-09-20 16:53:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20
-- UI Element Group: payment
-- 2024-09-20T13:53:27.220Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547547,551955,TO_TIMESTAMP('2024-09-20 16:53:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','payment',20,TO_TIMESTAMP('2024-09-20 16:53:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Bankverbindung
-- Column: C_POS_Order.C_BP_BankAccount_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> payment.Bankverbindung
-- Column: C_POS_Order.C_BP_BankAccount_ID
-- 2024-09-20T13:54:24.192Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730875,0,547591,551955,625744,'F',TO_TIMESTAMP('2024-09-20 16:54:24','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','Y','N','Y','N','N','Bankverbindung',10,0,0,TO_TIMESTAMP('2024-09-20 16:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.POS-Terminal
-- Column: C_POS_Order.C_POS_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> payment.POS-Terminal
-- Column: C_POS_Order.C_POS_ID
-- 2024-09-20T13:54:34.845Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730890,0,547591,551955,625745,'F',TO_TIMESTAMP('2024-09-20 16:54:34','YYYY-MM-DD HH24:MI:SS'),100,'Point of Sales Terminal','"POS-Terminal" definiert Standardwerte und Funktionen fürdas POS-Fenster.','Y','N','Y','N','N','POS-Terminal',20,0,0,TO_TIMESTAMP('2024-09-20 16:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Status
-- Column: C_POS_Order.Status
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Status
-- Column: C_POS_Order.Status
-- 2024-09-20T13:54:47.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730871,0,547591,551954,625746,'F',TO_TIMESTAMP('2024-09-20 16:54:47','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Status',30,0,0,TO_TIMESTAMP('2024-09-20 16:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Cashier
-- Column: C_POS_Order.Cashier_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Cashier
-- Column: C_POS_Order.Cashier_ID
-- 2024-09-20T13:54:54.813Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730876,0,547591,551954,625747,'F',TO_TIMESTAMP('2024-09-20 16:54:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cashier',40,0,0,TO_TIMESTAMP('2024-09-20 16:54:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20
-- UI Element Group: amounts
-- 2024-09-20T13:55:03.142Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547547,551956,TO_TIMESTAMP('2024-09-20 16:55:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts',30,TO_TIMESTAMP('2024-09-20 16:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Summe Gesamt
-- Column: C_POS_Order.GrandTotal
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> amounts.Summe Gesamt
-- Column: C_POS_Order.GrandTotal
-- 2024-09-20T13:55:18.227Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730885,0,547591,551956,625748,'F',TO_TIMESTAMP('2024-09-20 16:55:18','YYYY-MM-DD HH24:MI:SS'),100,'Summe über Alles zu diesem Beleg','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','Y','N','N','Summe Gesamt',10,0,0,TO_TIMESTAMP('2024-09-20 16:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Steuerbetrag
-- Column: C_POS_Order.TaxAmt
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> amounts.Steuerbetrag
-- Column: C_POS_Order.TaxAmt
-- 2024-09-20T13:55:26.356Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730886,0,547591,551956,625749,'F',TO_TIMESTAMP('2024-09-20 16:55:26','YYYY-MM-DD HH24:MI:SS'),100,'Steuerbetrag für diesen Beleg','Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','Y','N','N','Steuerbetrag',20,0,0,TO_TIMESTAMP('2024-09-20 16:55:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Gezahlter Betrag
-- Column: C_POS_Order.PaidAmt
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> amounts.Gezahlter Betrag
-- Column: C_POS_Order.PaidAmt
-- 2024-09-20T13:55:35.263Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730887,0,547591,551956,625750,'F',TO_TIMESTAMP('2024-09-20 16:55:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gezahlter Betrag',30,0,0,TO_TIMESTAMP('2024-09-20 16:55:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Offener Betrag
-- Column: C_POS_Order.OpenAmt
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> amounts.Offener Betrag
-- Column: C_POS_Order.OpenAmt
-- 2024-09-20T13:55:41.937Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730888,0,547591,551956,625751,'F',TO_TIMESTAMP('2024-09-20 16:55:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Offener Betrag',40,0,0,TO_TIMESTAMP('2024-09-20 16:55:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20
-- UI Element Group: org
-- 2024-09-20T13:55:56.546Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547547,551957,TO_TIMESTAMP('2024-09-20 16:55:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2024-09-20 16:55:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Organisation
-- Column: C_POS_Order.AD_Org_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> org.Organisation
-- Column: C_POS_Order.AD_Org_ID
-- 2024-09-20T13:56:09.864Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730867,0,547591,551957,625752,'F',TO_TIMESTAMP('2024-09-20 16:56:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2024-09-20 16:56:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Mandant
-- Column: C_POS_Order.AD_Client_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> org.Mandant
-- Column: C_POS_Order.AD_Client_ID
-- 2024-09-20T13:56:22.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730866,0,547591,551957,625753,'F',TO_TIMESTAMP('2024-09-20 16:56:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-09-20 16:56:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T13:58:21.634Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,730879,0,541873,625735,TO_TIMESTAMP('2024-09-20 16:58:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2024-09-20 16:58:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos)
-- UI Section: advanced
-- 2024-09-20T13:58:34.491Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547591,546177,TO_TIMESTAMP('2024-09-20 16:58:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-20 16:58:34','YYYY-MM-DD HH24:MI:SS'),100,'advanced')
;

-- 2024-09-20T13:58:34.492Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546177 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> advanced
-- UI Column: 10
-- 2024-09-20T13:58:38.330Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547550,546177,TO_TIMESTAMP('2024-09-20 16:58:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 16:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> advanced -> 10
-- UI Element Group: advanced
-- 2024-09-20T13:58:44.701Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547550,551958,TO_TIMESTAMP('2024-09-20 16:58:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced',10,TO_TIMESTAMP('2024-09-20 16:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Auftrags-Belegart
-- Column: C_POS_Order.C_DocTypeOrder_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> advanced -> 10 -> advanced.Auftrags-Belegart
-- Column: C_POS_Order.C_DocTypeOrder_ID
-- 2024-09-20T13:59:10.223Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730872,0,547591,551958,625754,'F',TO_TIMESTAMP('2024-09-20 16:59:10','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for the orders generated from this order candidate','The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.','Y','N','Y','N','N','Auftrags-Belegart',10,0,0,TO_TIMESTAMP('2024-09-20 16:59:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Externe ID
-- Column: C_POS_Order.ExternalId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> advanced -> 10 -> advanced.Externe ID
-- Column: C_POS_Order.ExternalId
-- 2024-09-20T13:59:18.349Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730870,0,547591,551958,625755,'F',TO_TIMESTAMP('2024-09-20 16:59:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Externe ID',20,0,0,TO_TIMESTAMP('2024-09-20 16:59:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order.Auftrags-Belegart
-- Column: C_POS_Order.C_DocTypeOrder_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> advanced -> 10 -> advanced.Auftrags-Belegart
-- Column: C_POS_Order.C_DocTypeOrder_ID
-- 2024-09-20T13:59:24.999Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-20 16:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625754
;

-- UI Element: POS Order -> POS Order.Externe ID
-- Column: C_POS_Order.ExternalId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> advanced -> 10 -> advanced.Externe ID
-- Column: C_POS_Order.ExternalId
-- 2024-09-20T13:59:27.515Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-20 16:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625755
;

-- UI Element: POS Order -> POS Order Line.Produkt
-- Column: C_POS_OrderLine.M_Product_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> default.Produkt
-- Column: C_POS_OrderLine.M_Product_ID
-- 2024-09-20T14:00:06.289Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730896,0,547592,551950,625756,'F',TO_TIMESTAMP('2024-09-20 17:00:06','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2024-09-20 17:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Produktname
-- Column: C_POS_OrderLine.ProductName
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> default.Produktname
-- Column: C_POS_OrderLine.ProductName
-- 2024-09-20T14:00:15.501Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730897,0,547592,551950,625757,'F',TO_TIMESTAMP('2024-09-20 17:00:14','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes','Y','N','Y','N','N','Produktname',20,0,0,TO_TIMESTAMP('2024-09-20 17:00:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10
-- UI Element Group: qty
-- 2024-09-20T14:01:00.776Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547548,551959,TO_TIMESTAMP('2024-09-20 17:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','qty',20,TO_TIMESTAMP('2024-09-20 17:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Maßeinheit
-- Column: C_POS_OrderLine.C_UOM_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> qty.Maßeinheit
-- Column: C_POS_OrderLine.C_UOM_ID
-- 2024-09-20T14:01:09.901Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730901,0,547592,551959,625758,'F',TO_TIMESTAMP('2024-09-20 17:01:09','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',10,0,0,TO_TIMESTAMP('2024-09-20 17:01:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Menge
-- Column: C_POS_OrderLine.Qty
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> qty.Menge
-- Column: C_POS_OrderLine.Qty
-- 2024-09-20T14:01:17.668Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730900,0,547592,551959,625759,'F',TO_TIMESTAMP('2024-09-20 17:01:17','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','Menge',20,0,0,TO_TIMESTAMP('2024-09-20 17:01:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10
-- UI Element Group: price & tax
-- 2024-09-20T14:01:28.487Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547548,551960,TO_TIMESTAMP('2024-09-20 17:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','price & tax',30,TO_TIMESTAMP('2024-09-20 17:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Steuerkategorie
-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> price & tax.Steuerkategorie
-- Column: C_POS_OrderLine.C_TaxCategory_ID
-- 2024-09-20T14:01:41.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730898,0,547592,551960,625760,'F',TO_TIMESTAMP('2024-09-20 17:01:41','YYYY-MM-DD HH24:MI:SS'),100,'Steuerkategorie','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','Y','N','N','Steuerkategorie',10,0,0,TO_TIMESTAMP('2024-09-20 17:01:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Steuer
-- Column: C_POS_OrderLine.C_Tax_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> price & tax.Steuer
-- Column: C_POS_OrderLine.C_Tax_ID
-- 2024-09-20T14:01:47.846Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730899,0,547592,551960,625761,'F',TO_TIMESTAMP('2024-09-20 17:01:47','YYYY-MM-DD HH24:MI:SS'),100,'Steuerart','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','Y','N','N','Steuer',20,0,0,TO_TIMESTAMP('2024-09-20 17:01:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Preis
-- Column: C_POS_OrderLine.Price
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> price & tax.Preis
-- Column: C_POS_OrderLine.Price
-- 2024-09-20T14:01:57.126Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730902,0,547592,551960,625762,'F',TO_TIMESTAMP('2024-09-20 17:01:55','YYYY-MM-DD HH24:MI:SS'),100,'Preis','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','N','Preis',30,0,0,TO_TIMESTAMP('2024-09-20 17:01:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main
-- UI Column: 20
-- 2024-09-20T14:02:06.184Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547551,546175,TO_TIMESTAMP('2024-09-20 17:02:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-20 17:02:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 20
-- UI Element Group: amounts
-- 2024-09-20T14:02:13.727Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547551,551961,TO_TIMESTAMP('2024-09-20 17:02:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','amounts',10,TO_TIMESTAMP('2024-09-20 17:02:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Betrag
-- Column: C_POS_OrderLine.Amount
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 20 -> amounts.Betrag
-- Column: C_POS_OrderLine.Amount
-- 2024-09-20T14:02:24.311Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730903,0,547592,551961,625763,'F',TO_TIMESTAMP('2024-09-20 17:02:24','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','Betrag',10,0,0,TO_TIMESTAMP('2024-09-20 17:02:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Steuerbetrag
-- Column: C_POS_OrderLine.TaxAmt
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 20 -> amounts.Steuerbetrag
-- Column: C_POS_OrderLine.TaxAmt
-- 2024-09-20T14:02:33.193Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730904,0,547592,551961,625764,'F',TO_TIMESTAMP('2024-09-20 17:02:33','YYYY-MM-DD HH24:MI:SS'),100,'Steuerbetrag für diesen Beleg','Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','Y','N','N','Steuerbetrag',20,0,0,TO_TIMESTAMP('2024-09-20 17:02:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos)
-- UI Section: advanced
-- 2024-09-20T14:02:47.979Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547592,546178,TO_TIMESTAMP('2024-09-20 17:02:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-20 17:02:47','YYYY-MM-DD HH24:MI:SS'),100,'advanced')
;

-- 2024-09-20T14:02:47.980Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546178 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> advanced
-- UI Column: 10
-- 2024-09-20T14:02:51.821Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547552,546178,TO_TIMESTAMP('2024-09-20 17:02:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 17:02:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> advanced -> 10
-- UI Element Group: advanced
-- 2024-09-20T14:03:00.398Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547552,551962,TO_TIMESTAMP('2024-09-20 17:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced',10,TO_TIMESTAMP('2024-09-20 17:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Externe ID
-- Column: C_POS_OrderLine.ExternalId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> advanced -> 10 -> advanced.Externe ID
-- Column: C_POS_OrderLine.ExternalId
-- 2024-09-20T14:03:16.974Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730895,0,547592,551962,625765,'F',TO_TIMESTAMP('2024-09-20 17:03:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Externe ID',10,0,0,TO_TIMESTAMP('2024-09-20 17:03:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Order Line.Externe ID
-- Column: C_POS_OrderLine.ExternalId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> advanced -> 10 -> advanced.Externe ID
-- Column: C_POS_OrderLine.ExternalId
-- 2024-09-20T14:03:20.959Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-20 17:03:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625765
;

-- UI Element: POS Order -> POS Order Line.Produkt
-- Column: C_POS_OrderLine.M_Product_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> default.Produkt
-- Column: C_POS_OrderLine.M_Product_ID
-- 2024-09-20T14:04:03.969Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-20 17:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625756
;

-- UI Element: POS Order -> POS Order Line.Maßeinheit
-- Column: C_POS_OrderLine.C_UOM_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> qty.Maßeinheit
-- Column: C_POS_OrderLine.C_UOM_ID
-- 2024-09-20T14:04:03.975Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-20 17:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625758
;

-- UI Element: POS Order -> POS Order Line.Menge
-- Column: C_POS_OrderLine.Qty
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> qty.Menge
-- Column: C_POS_OrderLine.Qty
-- 2024-09-20T14:04:03.981Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-20 17:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625759
;

-- UI Element: POS Order -> POS Order Line.Preis
-- Column: C_POS_OrderLine.Price
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> price & tax.Preis
-- Column: C_POS_OrderLine.Price
-- 2024-09-20T14:04:03.986Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-20 17:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625762
;

-- UI Element: POS Order -> POS Order Line.Steuer
-- Column: C_POS_OrderLine.C_Tax_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 10 -> price & tax.Steuer
-- Column: C_POS_OrderLine.C_Tax_ID
-- 2024-09-20T14:04:03.992Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-09-20 17:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625761
;

-- UI Element: POS Order -> POS Order Line.Betrag
-- Column: C_POS_OrderLine.Amount
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 20 -> amounts.Betrag
-- Column: C_POS_OrderLine.Amount
-- 2024-09-20T14:04:03.997Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-20 17:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625763
;

-- UI Element: POS Order -> POS Order Line.Steuerbetrag
-- Column: C_POS_OrderLine.TaxAmt
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order Line(547592,de.metas.pos) -> main -> 20 -> amounts.Steuerbetrag
-- Column: C_POS_OrderLine.TaxAmt
-- 2024-09-20T14:04:04.001Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-20 17:04:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625764
;

-- UI Element: POS Order -> POS Order.Nr.
-- Column: C_POS_Order.DocumentNo
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Nr.
-- Column: C_POS_Order.DocumentNo
-- 2024-09-20T14:05:27.584Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-20 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625742
;

-- UI Element: POS Order -> POS Order.Vorgangsdatum
-- Column: C_POS_Order.DateTrx
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Vorgangsdatum
-- Column: C_POS_Order.DateTrx
-- 2024-09-20T14:05:27.591Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-20 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625743
;

-- UI Element: POS Order -> POS Order.Status
-- Column: C_POS_Order.Status
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Status
-- Column: C_POS_Order.Status
-- 2024-09-20T14:05:27.597Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-20 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625746
;

-- UI Element: POS Order -> POS Order.Geschäftspartner
-- Column: C_POS_Order.C_BPartner_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 10 -> default.Geschäftspartner
-- Column: C_POS_Order.C_BPartner_ID
-- 2024-09-20T14:05:27.602Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-20 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625735
;

-- UI Element: POS Order -> POS Order.Summe Gesamt
-- Column: C_POS_Order.GrandTotal
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> amounts.Summe Gesamt
-- Column: C_POS_Order.GrandTotal
-- 2024-09-20T14:05:27.607Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-09-20 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625748
;

-- UI Element: POS Order -> POS Order.Cashier
-- Column: C_POS_Order.Cashier_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Order(547591,de.metas.pos) -> main -> 20 -> doc info.Cashier
-- Column: C_POS_Order.Cashier_ID
-- 2024-09-20T14:05:27.612Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-20 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625747
;

-- Column: C_POS_Order.Cashier_ID
-- Column: C_POS_Order.Cashier_ID
-- 2024-09-20T14:06:00.350Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-20 17:06:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589059
;

-- Column: C_POS_Order.C_BPartner_ID
-- Column: C_POS_Order.C_BPartner_ID
-- 2024-09-20T14:06:15.732Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-20 17:06:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589061
;

-- Column: C_POS_Order.C_POS_ID
-- Column: C_POS_Order.C_POS_ID
-- 2024-09-20T14:06:19.820Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-20 17:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589073
;

-- Column: C_POS_Order.DateTrx
-- Column: C_POS_Order.DateTrx
-- 2024-09-20T14:06:36.232Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-20 17:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589060
;

-- UI Element: POS Order -> POS Payment.POS Payment Method
-- Column: C_POS_Payment.POSPaymentMethod
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 10 -> default.POS Payment Method
-- Column: C_POS_Payment.POSPaymentMethod
-- 2024-09-20T14:08:02.169Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730911,0,547593,551951,625766,'F',TO_TIMESTAMP('2024-09-20 17:08:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Payment Method',10,0,0,TO_TIMESTAMP('2024-09-20 17:08:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Betrag
-- Column: C_POS_Payment.Amount
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 10 -> default.Betrag
-- Column: C_POS_Payment.Amount
-- 2024-09-20T14:08:08.622Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730912,0,547593,551951,625767,'F',TO_TIMESTAMP('2024-09-20 17:08:08','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','Betrag',20,0,0,TO_TIMESTAMP('2024-09-20 17:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos)
-- UI Section: advanced
-- 2024-09-20T14:08:21.091Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547593,546179,TO_TIMESTAMP('2024-09-20 17:08:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-20 17:08:20','YYYY-MM-DD HH24:MI:SS'),100,'advanced')
;

-- 2024-09-20T14:08:21.093Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546179 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> advanced
-- UI Column: 10
-- 2024-09-20T14:08:24.709Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547553,546179,TO_TIMESTAMP('2024-09-20 17:08:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-20 17:08:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> advanced -> 10
-- UI Element Group: advanced
-- 2024-09-20T14:08:33.213Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547553,551963,TO_TIMESTAMP('2024-09-20 17:08:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced',10,TO_TIMESTAMP('2024-09-20 17:08:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Externe ID
-- Column: C_POS_Payment.ExternalId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> advanced -> 10 -> advanced.Externe ID
-- Column: C_POS_Payment.ExternalId
-- 2024-09-20T14:08:44.762Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730910,0,547593,551963,625768,'F',TO_TIMESTAMP('2024-09-20 17:08:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Externe ID',10,0,0,TO_TIMESTAMP('2024-09-20 17:08:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Externe ID
-- Column: C_POS_Payment.ExternalId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> advanced -> 10 -> advanced.Externe ID
-- Column: C_POS_Payment.ExternalId
-- 2024-09-20T14:08:51.432Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-09-20 17:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625768
;

-- UI Element: POS Order -> POS Payment.Betrag
-- Column: C_POS_Payment.Amount
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 10 -> default.Betrag
-- Column: C_POS_Payment.Amount
-- 2024-09-20T14:08:58.121Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-20 17:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625767
;

-- UI Element: POS Order -> POS Payment.POS Payment Method
-- Column: C_POS_Payment.POSPaymentMethod
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 10 -> default.POS Payment Method
-- Column: C_POS_Payment.POSPaymentMethod
-- 2024-09-20T14:08:58.128Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-20 17:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625766
;

-- Column: C_POS_OrderLine.ExternalId
-- Column: C_POS_OrderLine.ExternalId
-- 2024-09-20T17:47:55.696Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-20 20:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589082
;

-- 2024-09-20T17:47:56.278Z
INSERT INTO t_alter_column values('c_pos_orderline','ExternalId','VARCHAR(255)',null,null)
;

-- 2024-09-20T17:47:56.284Z
INSERT INTO t_alter_column values('c_pos_orderline','ExternalId',null,'NOT NULL',null)
;

-- Column: C_POS_Order.ExternalId
-- Column: C_POS_Order.ExternalId
-- 2024-09-20T17:48:11.965Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-20 20:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589026
;

-- 2024-09-20T17:48:12.502Z
INSERT INTO t_alter_column values('c_pos_order','ExternalId','VARCHAR(255)',null,null)
;

-- 2024-09-20T17:48:12.507Z
INSERT INTO t_alter_column values('c_pos_order','ExternalId',null,'NOT NULL',null)
;

-- Column: C_POS_Payment.ExternalId
-- Column: C_POS_Payment.ExternalId
-- 2024-09-20T17:48:25.966Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-20 20:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589115
;

-- 2024-09-20T17:48:26.497Z
INSERT INTO t_alter_column values('c_pos_payment','ExternalId','VARCHAR(255)',null,null)
;

-- 2024-09-20T17:48:26.506Z
INSERT INTO t_alter_column values('c_pos_payment','ExternalId',null,'NOT NULL',null)
;

-- Table: C_POS_Payment
-- Table: C_POS_Payment
-- 2024-09-20T17:57:28.446Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2024-09-20 20:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542437
;

-- Table: C_POS_OrderLine
-- Table: C_POS_OrderLine
-- 2024-09-20T17:57:30.074Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2024-09-20 20:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542436
;

-- Table: C_POS_Order
-- Table: C_POS_Order
-- 2024-09-20T17:57:32.006Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2024-09-20 20:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542434
;

-- 2024-09-20T17:59:31.577Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583271,0,TO_TIMESTAMP('2024-09-20 20:59:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Point of Sale (POS)','Point of Sale (POS)',TO_TIMESTAMP('2024-09-20 20:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T17:59:31.582Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583271 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: Point of Sale (POS)
-- Action Type: null
-- 2024-09-20T17:59:54.426Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,583271,542171,0,TO_TIMESTAMP('2024-09-20 20:59:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Point of Sale (POS)','Y','N','N','N','Y','Point of Sale (POS)',TO_TIMESTAMP('2024-09-20 20:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T17:59:54.428Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542171 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-09-20T17:59:54.433Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542171, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542171)
;

-- 2024-09-20T17:59:54.445Z
/* DDL */  select update_menu_translation_from_ad_element(583271) 
;

-- Reordering children of `Sales`
-- Node name: `CreditPass configuration (CS_Creditpass_Config)`
-- 2024-09-20T18:00:02.594Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- Node name: `Sales Order (C_Order)`
-- 2024-09-20T18:00:02.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- Node name: `Alberta Prescription Request (Alberta_PrescriptionRequest)`
-- 2024-09-20T18:00:02.597Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- Node name: `Sales Order Disposition (C_OLCand)`
-- 2024-09-20T18:00:02.598Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- Node name: `Order Control (C_Order_MFGWarehouse_Report)`
-- 2024-09-20T18:00:02.600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- Node name: `Sales Opportunity Board (Prototype)`
-- 2024-09-20T18:00:02.601Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Type (C_CreditLimit_Type)`
-- 2024-09-20T18:00:02.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-09-20T18:00:02.602Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-09-20T18:00:02.603Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-09-20T18:00:02.604Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- Node name: `CreditPass transaction results (CS_Transaction_Result)`
-- 2024-09-20T18:00:02.604Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- Node name: `Commission`
-- 2024-09-20T18:00:02.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- Node name: `Incoterm (C_Incoterms)`
-- 2024-09-20T18:00:02.606Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541784 AND AD_Tree_ID=10
;

-- Node name: `Point of Sale (POS)`
-- 2024-09-20T18:00:02.608Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542171 AND AD_Tree_ID=10
;

-- Name: POS Order
-- Action Type: W
-- Window: POS Order(541818,de.metas.pos)
-- 2024-09-20T18:00:31.960Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583266,542172,0,541818,TO_TIMESTAMP('2024-09-20 21:00:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','posOrder','Y','N','N','Y','N','POS Order',TO_TIMESTAMP('2024-09-20 21:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T18:00:31.962Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542172 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-09-20T18:00:31.963Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542172, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542172)
;

-- 2024-09-20T18:00:31.964Z
/* DDL */  select update_menu_translation_from_ad_element(583266) 
;

-- Reordering children of `Human Resource & Payroll`
-- Node name: `Position Category (C_JobCategory)`
-- 2024-09-20T18:00:40.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=53108, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=531 AND AD_Tree_ID=10
;

-- Node name: `Setup Human Resource & Payroll`
-- 2024-09-20T18:00:40.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=53108, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53124 AND AD_Tree_ID=10
;

-- Node name: `Position (C_Job)`
-- 2024-09-20T18:00:40.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=53108, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=530 AND AD_Tree_ID=10
;

-- Node name: `Human Resource`
-- 2024-09-20T18:00:40.143Z
UPDATE AD_TreeNodeMM SET Parent_ID=53108, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53109 AND AD_Tree_ID=10
;

-- Node name: `Payroll`
-- 2024-09-20T18:00:40.143Z
UPDATE AD_TreeNodeMM SET Parent_ID=53108, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53114 AND AD_Tree_ID=10
;

-- Node name: `POS Order`
-- 2024-09-20T18:00:40.144Z
UPDATE AD_TreeNodeMM SET Parent_ID=53108, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542172 AND AD_Tree_ID=10
;

-- Reordering children of `Sales`
-- Node name: `CreditPass configuration (CS_Creditpass_Config)`
-- 2024-09-20T18:01:44.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- Node name: `Sales Order (C_Order)`
-- 2024-09-20T18:01:44.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- Node name: `Alberta Prescription Request (Alberta_PrescriptionRequest)`
-- 2024-09-20T18:01:44.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- Node name: `Sales Order Disposition (C_OLCand)`
-- 2024-09-20T18:01:44.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- Node name: `Order Control (C_Order_MFGWarehouse_Report)`
-- 2024-09-20T18:01:44.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- Node name: `Sales Opportunity Board (Prototype)`
-- 2024-09-20T18:01:44.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Type (C_CreditLimit_Type)`
-- 2024-09-20T18:01:44.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-09-20T18:01:44.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-09-20T18:01:44.116Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-09-20T18:01:44.117Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- Node name: `CreditPass transaction results (CS_Transaction_Result)`
-- 2024-09-20T18:01:44.117Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- Node name: `Commission`
-- 2024-09-20T18:01:44.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- Node name: `Incoterm (C_Incoterms)`
-- 2024-09-20T18:01:44.119Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541784 AND AD_Tree_ID=10
;

-- Node name: `Point of Sale (POS)`
-- 2024-09-20T18:01:44.120Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542171 AND AD_Tree_ID=10
;

-- Node name: `POS Order (C_POS_Order)`
-- 2024-09-20T18:01:44.121Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542172 AND AD_Tree_ID=10
;

-- Reordering children of `Point of Sale (POS)`
-- Node name: `POS Order (C_POS_Order)`
-- 2024-09-20T18:01:46.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542172 AND AD_Tree_ID=10
;

