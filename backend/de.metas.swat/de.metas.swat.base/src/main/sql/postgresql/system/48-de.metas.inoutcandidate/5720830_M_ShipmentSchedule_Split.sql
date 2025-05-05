-- Run mode: SWING_CLIENT

-- Table: M_ShipmentSchedule_Split
-- 2024-04-04T14:34:09.301Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542406,'N',TO_TIMESTAMP('2024-04-04 17:34:09.12','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inoutcandidate','N','Y','N','N','Y','N','N','N','N','N',0,'Split Shipment Schedule','NP','L','M_ShipmentSchedule_Split','DTI',TO_TIMESTAMP('2024-04-04 17:34:09.12','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:09.309Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542406 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-04-04T14:34:09.433Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556342,TO_TIMESTAMP('2024-04-04 17:34:09.334','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table M_ShipmentSchedule_Split',1,'Y','N','Y','Y','M_ShipmentSchedule_Split','N',1000000,TO_TIMESTAMP('2024-04-04 17:34:09.334','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:34:09.445Z
CREATE SEQUENCE M_SHIPMENTSCHEDULE_SPLIT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_ShipmentSchedule_Split.AD_Client_ID
-- 2024-04-04T14:34:38.331Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588115,102,0,19,542406,'AD_Client_ID',TO_TIMESTAMP('2024-04-04 17:34:38.157','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.inoutcandidate',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-04-04 17:34:38.157','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:38.336Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588115 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:38.369Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: M_ShipmentSchedule_Split.AD_Org_ID
-- 2024-04-04T14:34:39.104Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588116,113,0,30,542406,'AD_Org_ID',TO_TIMESTAMP('2024-04-04 17:34:38.829','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.inoutcandidate',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-04-04 17:34:38.829','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:39.106Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588116 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:39.109Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: M_ShipmentSchedule_Split.Created
-- 2024-04-04T14:34:39.708Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588117,245,0,16,542406,'Created',TO_TIMESTAMP('2024-04-04 17:34:39.488','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.inoutcandidate',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-04-04 17:34:39.488','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:39.710Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588117 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:39.713Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: M_ShipmentSchedule_Split.CreatedBy
-- 2024-04-04T14:34:40.353Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588118,246,0,18,110,542406,'CreatedBy',TO_TIMESTAMP('2024-04-04 17:34:40.103','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.inoutcandidate',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-04-04 17:34:40.103','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:40.356Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588118 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:40.359Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: M_ShipmentSchedule_Split.IsActive
-- 2024-04-04T14:34:40.916Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588119,348,0,20,542406,'IsActive',TO_TIMESTAMP('2024-04-04 17:34:40.693','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.inoutcandidate',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-04-04 17:34:40.693','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:40.918Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588119 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:40.922Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: M_ShipmentSchedule_Split.Updated
-- 2024-04-04T14:34:41.584Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588120,607,0,16,542406,'Updated',TO_TIMESTAMP('2024-04-04 17:34:41.332','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.inoutcandidate',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-04-04 17:34:41.332','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:41.586Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588120 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:41.589Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: M_ShipmentSchedule_Split.UpdatedBy
-- 2024-04-04T14:34:42.225Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588121,608,0,18,110,542406,'UpdatedBy',TO_TIMESTAMP('2024-04-04 17:34:41.979','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.inoutcandidate',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-04-04 17:34:41.979','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:42.228Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588121 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:42.232Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2024-04-04T14:34:42.717Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583072,0,'M_ShipmentSchedule_Split_ID',TO_TIMESTAMP('2024-04-04 17:34:42.626','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inoutcandidate','Y','Split Shipment Schedule','Split Shipment Schedule',TO_TIMESTAMP('2024-04-04 17:34:42.626','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:34:42.723Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583072 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_Split_ID
-- 2024-04-04T14:34:43.292Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588122,583072,0,13,542406,'M_ShipmentSchedule_Split_ID',TO_TIMESTAMP('2024-04-04 17:34:42.622','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Split Shipment Schedule',0,0,TO_TIMESTAMP('2024-04-04 17:34:42.622','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:34:43.294Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588122 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:34:43.298Z
/* DDL */  select update_Column_Translation_From_AD_Element(583072)
;

-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_ID
-- 2024-04-04T14:35:24.813Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588123,500221,0,30,542406,'M_ShipmentSchedule_ID',TO_TIMESTAMP('2024-04-04 17:35:24.644','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Lieferdisposition',0,0,TO_TIMESTAMP('2024-04-04 17:35:24.644','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:35:24.816Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588123 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:35:24.820Z
/* DDL */  select update_Column_Translation_From_AD_Element(500221)
;

-- Column: M_ShipmentSchedule_Split.Processed
-- 2024-04-04T14:35:50.230Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588124,1047,0,20,542406,'Processed',TO_TIMESTAMP('2024-04-04 17:35:50.084','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','de.metas.inoutcandidate',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2024-04-04 17:35:50.084','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:35:50.234Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:35:50.238Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047)
;

-- Column: M_ShipmentSchedule_Split.M_InOut_ID
-- 2024-04-04T14:36:12.681Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588125,1025,0,30,542406,'M_InOut_ID',TO_TIMESTAMP('2024-04-04 17:36:12.51','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Material Shipment Document','de.metas.inoutcandidate',0,10,'The Material Shipment / Receipt ','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferung/Wareneingang',0,0,TO_TIMESTAMP('2024-04-04 17:36:12.51','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:36:12.684Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:36:12.688Z
/* DDL */  select update_Column_Translation_From_AD_Element(1025)
;

-- Column: M_ShipmentSchedule_Split.M_InOutLine_ID
-- 2024-04-04T14:36:26.668Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588126,1026,0,30,542406,190,'M_InOutLine_ID',TO_TIMESTAMP('2024-04-04 17:36:26.514','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Position auf Versand- oder Wareneingangsbeleg','de.metas.inoutcandidate',0,10,'"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versand-/Wareneingangsposition',0,0,TO_TIMESTAMP('2024-04-04 17:36:26.514','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:36:26.671Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588126 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:36:26.674Z
/* DDL */  select update_Column_Translation_From_AD_Element(1026)
;

-- Column: M_ShipmentSchedule_Split.DeliveryDate
-- 2024-04-04T14:38:56.678Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588127,541376,0,15,542406,'DeliveryDate',TO_TIMESTAMP('2024-04-04 17:38:56.515','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferdatum',0,0,TO_TIMESTAMP('2024-04-04 17:38:56.515','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:38:56.680Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588127 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:38:56.683Z
/* DDL */  select update_Column_Translation_From_AD_Element(541376)
;

-- Column: M_ShipmentSchedule_Split.C_UOM_ID
-- 2024-04-04T14:39:32.958Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588128,215,0,30,542406,'C_UOM_ID',TO_TIMESTAMP('2024-04-04 17:39:32.813','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Maßeinheit','de.metas.inoutcandidate',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2024-04-04 17:39:32.813','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:39:32.961Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588128 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:39:32.965Z
/* DDL */  select update_Column_Translation_From_AD_Element(215)
;

-- Column: M_ShipmentSchedule_Split.DeliveryDate
-- 2024-04-04T14:39:40.104Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-04 17:39:40.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588127
;

-- Column: M_ShipmentSchedule_Split.QtyToDeliver
-- 2024-04-04T14:40:23.211Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588129,1250,0,29,542406,'QtyToDeliver',TO_TIMESTAMP('2024-04-04 17:40:23.064','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ausliefermenge',0,0,TO_TIMESTAMP('2024-04-04 17:40:23.064','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:40:23.214Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588129 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:40:23.217Z
/* DDL */  select update_Column_Translation_From_AD_Element(1250)
;

-- Column: M_ShipmentSchedule_Split.UserElementString1
-- 2024-04-04T14:40:49.057Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588130,578653,0,10,542406,'UserElementString1',TO_TIMESTAMP('2024-04-04 17:40:48.914','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementString1',0,0,TO_TIMESTAMP('2024-04-04 17:40:48.914','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:40:49.059Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588130 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:40:49.063Z
/* DDL */  select update_Column_Translation_From_AD_Element(578653)
;

-- Column: M_ShipmentSchedule_Split.UserElementString2
-- 2024-04-04T14:41:19.022Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588131,578654,0,10,542406,'UserElementString2',TO_TIMESTAMP('2024-04-04 17:41:18.877','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UserElementString2',0,0,TO_TIMESTAMP('2024-04-04 17:41:18.877','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:41:19.025Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588131 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:41:19.028Z
/* DDL */  select update_Column_Translation_From_AD_Element(578654)
;

-- 2024-04-04T14:41:32.695Z
/* DDL */ CREATE TABLE public.M_ShipmentSchedule_Split (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10) NOT NULL, DeliveryDate TIMESTAMP WITHOUT TIME ZONE NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_InOut_ID NUMERIC(10), M_InOutLine_ID NUMERIC(10), M_ShipmentSchedule_ID NUMERIC(10) NOT NULL, M_ShipmentSchedule_Split_ID NUMERIC(10) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, QtyToDeliver NUMERIC NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, UserElementString1 VARCHAR(1000), UserElementString2 VARCHAR(1000), CONSTRAINT CUOM_MShipmentScheduleSplit FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MInOut_MShipmentScheduleSplit FOREIGN KEY (M_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MInOutLine_MShipmentScheduleSplit FOREIGN KEY (M_InOutLine_ID) REFERENCES public.M_InOutLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MShipmentSchedule_MShipmentScheduleSplit FOREIGN KEY (M_ShipmentSchedule_ID) REFERENCES public.M_ShipmentSchedule DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_ShipmentSchedule_Split_Key PRIMARY KEY (M_ShipmentSchedule_Split_ID))
;

-- Column: M_ShipmentSchedule_Split.DeliveryDate
-- 2024-04-04T14:42:15.557Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-04-04 17:42:15.557','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588127
;

-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_ID
-- 2024-04-04T14:42:40.041Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-04 17:42:40.04','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588123
;

-- Column: M_ShipmentSchedule_Split.Processed
-- 2024-04-04T14:42:52.612Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-04-04 17:42:52.612','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588124
;

-- Column: M_ShipmentSchedule_Split.UserElementString1
-- 2024-04-04T14:43:03.874Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-04-04 17:43:03.873','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588130
;

-- Column: M_ShipmentSchedule_Split.UserElementString2
-- 2024-04-04T14:43:08.019Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-04-04 17:43:08.019','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588131
;

-- Window: Split Shipment Schedule, InternalName=null
-- 2024-04-04T14:43:54.498Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583072,0,541794,TO_TIMESTAMP('2024-04-04 17:43:54.297','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','Y','Split Shipment Schedule','N',TO_TIMESTAMP('2024-04-04 17:43:54.297','YYYY-MM-DD HH24:MI:SS.US'),100,'T',0,0,100)
;

-- 2024-04-04T14:43:54.502Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541794 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-04-04T14:43:54.505Z
/* DDL */  select update_window_translation_from_ad_element(583072)
;

-- 2024-04-04T14:43:54.515Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541794
;

-- 2024-04-04T14:43:54.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541794)
;

-- Tab: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule
-- Table: M_ShipmentSchedule_Split
-- 2024-04-04T14:44:14.631Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583072,0,547500,542406,541794,'Y',TO_TIMESTAMP('2024-04-04 17:44:14.484','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inoutcandidate','N','N','A','M_ShipmentSchedule_Split','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Split Shipment Schedule','N',10,0,TO_TIMESTAMP('2024-04-04 17:44:14.484','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:14.634Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547500 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-04-04T14:44:14.638Z
/* DDL */  select update_tab_translation_from_ad_element(583072)
;

-- 2024-04-04T14:44:14.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547500)
;

-- Tab: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule
-- Table: M_ShipmentSchedule_Split
-- 2024-04-04T14:44:22.531Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-04 17:44:22.531','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547500
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Mandant
-- Column: M_ShipmentSchedule_Split.AD_Client_ID
-- 2024-04-04T14:44:27.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588115,727321,0,547500,TO_TIMESTAMP('2024-04-04 17:44:27.815','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.inoutcandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-04-04 17:44:27.815','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:27.979Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:27.983Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-04-04T14:44:28.912Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727321
;

-- 2024-04-04T14:44:28.914Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727321)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Organisation
-- Column: M_ShipmentSchedule_Split.AD_Org_ID
-- 2024-04-04T14:44:29.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588116,727322,0,547500,TO_TIMESTAMP('2024-04-04 17:44:28.919','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.inoutcandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-04-04 17:44:28.919','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:29.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:29.030Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-04-04T14:44:29.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727322
;

-- 2024-04-04T14:44:29.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727322)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Aktiv
-- Column: M_ShipmentSchedule_Split.IsActive
-- 2024-04-04T14:44:29.762Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588119,727323,0,547500,TO_TIMESTAMP('2024-04-04 17:44:29.669','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.inoutcandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-04-04 17:44:29.669','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:29.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:29.767Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-04-04T14:44:29.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727323
;

-- 2024-04-04T14:44:29.884Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727323)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Split Shipment Schedule
-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_Split_ID
-- 2024-04-04T14:44:29.995Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588122,727324,0,547500,TO_TIMESTAMP('2024-04-04 17:44:29.887','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Split Shipment Schedule',TO_TIMESTAMP('2024-04-04 17:44:29.887','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:29.997Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:29.999Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583072)
;

-- 2024-04-04T14:44:30.002Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727324
;

-- 2024-04-04T14:44:30.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727324)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Lieferdisposition
-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_ID
-- 2024-04-04T14:44:30.104Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588123,727325,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.006','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Lieferdisposition',TO_TIMESTAMP('2024-04-04 17:44:30.006','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:30.106Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:30.108Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500221)
;

-- 2024-04-04T14:44:30.115Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727325
;

-- 2024-04-04T14:44:30.116Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727325)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Verarbeitet
-- Column: M_ShipmentSchedule_Split.Processed
-- 2024-04-04T14:44:30.229Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588124,727326,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.119','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'de.metas.inoutcandidate','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2024-04-04 17:44:30.119','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:30.231Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:30.233Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2024-04-04T14:44:30.263Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727326
;

-- 2024-04-04T14:44:30.265Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727326)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Lieferung/Wareneingang
-- Column: M_ShipmentSchedule_Split.M_InOut_ID
-- 2024-04-04T14:44:30.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588125,727327,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.269','YYYY-MM-DD HH24:MI:SS.US'),100,'Material Shipment Document',10,'de.metas.inoutcandidate','The Material Shipment / Receipt ','Y','N','N','N','N','N','N','N','Lieferung/Wareneingang',TO_TIMESTAMP('2024-04-04 17:44:30.269','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:30.385Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:30.387Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025)
;

-- 2024-04-04T14:44:30.396Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727327
;

-- 2024-04-04T14:44:30.397Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727327)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Versand-/Wareneingangsposition
-- Column: M_ShipmentSchedule_Split.M_InOutLine_ID
-- 2024-04-04T14:44:30.504Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588126,727328,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.4','YYYY-MM-DD HH24:MI:SS.US'),100,'Position auf Versand- oder Wareneingangsbeleg',10,'de.metas.inoutcandidate','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','N','N','N','N','N','N','Versand-/Wareneingangsposition',TO_TIMESTAMP('2024-04-04 17:44:30.4','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:30.506Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:30.508Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1026)
;

-- 2024-04-04T14:44:30.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727328
;

-- 2024-04-04T14:44:30.529Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727328)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Lieferdatum
-- Column: M_ShipmentSchedule_Split.DeliveryDate
-- 2024-04-04T14:44:30.631Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588127,727329,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.532','YYYY-MM-DD HH24:MI:SS.US'),100,7,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Lieferdatum',TO_TIMESTAMP('2024-04-04 17:44:30.532','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:30.633Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:30.635Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541376)
;

-- 2024-04-04T14:44:30.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727329
;

-- 2024-04-04T14:44:30.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727329)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Maßeinheit
-- Column: M_ShipmentSchedule_Split.C_UOM_ID
-- 2024-04-04T14:44:30.745Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588128,727330,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.644','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'de.metas.inoutcandidate','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-04-04 17:44:30.644','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:30.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:30.750Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2024-04-04T14:44:30.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727330
;

-- 2024-04-04T14:44:30.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727330)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Ausliefermenge
-- Column: M_ShipmentSchedule_Split.QtyToDeliver
-- 2024-04-04T14:44:30.908Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588129,727331,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.817','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Ausliefermenge',TO_TIMESTAMP('2024-04-04 17:44:30.817','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:30.910Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:30.912Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1250)
;

-- 2024-04-04T14:44:30.916Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727331
;

-- 2024-04-04T14:44:30.917Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727331)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> UserElementString1
-- Column: M_ShipmentSchedule_Split.UserElementString1
-- 2024-04-04T14:44:31.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588130,727332,0,547500,TO_TIMESTAMP('2024-04-04 17:44:30.92','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','UserElementString1',TO_TIMESTAMP('2024-04-04 17:44:30.92','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:31.023Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:31.025Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653)
;

-- 2024-04-04T14:44:31.028Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727332
;

-- 2024-04-04T14:44:31.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727332)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> UserElementString2
-- Column: M_ShipmentSchedule_Split.UserElementString2
-- 2024-04-04T14:44:31.131Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588131,727333,0,547500,TO_TIMESTAMP('2024-04-04 17:44:31.033','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2024-04-04 17:44:31.033','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T14:44:31.133Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T14:44:31.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654)
;

-- 2024-04-04T14:44:31.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727333
;

-- 2024-04-04T14:44:31.138Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727333)
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Organisation
-- Column: M_ShipmentSchedule_Split.AD_Org_ID
-- 2024-04-04T14:44:40.704Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-04 17:44:40.704','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727322
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Split Shipment Schedule
-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_Split_ID
-- 2024-04-04T14:44:53.023Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-04 17:44:53.023','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727324
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Lieferdisposition
-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_ID
-- 2024-04-04T14:44:54.116Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-04 17:44:54.116','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727325
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Verarbeitet
-- Column: M_ShipmentSchedule_Split.Processed
-- 2024-04-04T14:44:56.576Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-04 17:44:56.576','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727326
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Lieferung/Wareneingang
-- Column: M_ShipmentSchedule_Split.M_InOut_ID
-- 2024-04-04T14:45:00.232Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-04 17:45:00.231','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727327
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Versand-/Wareneingangsposition
-- Column: M_ShipmentSchedule_Split.M_InOutLine_ID
-- 2024-04-04T14:45:01.723Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-04-04 17:45:01.723','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727328
;

-- Tab: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule
-- Table: M_ShipmentSchedule_Split
-- 2024-04-04T14:45:23.192Z
UPDATE AD_Tab SET IsInsertRecord='Y', IsReadOnly='N',Updated=TO_TIMESTAMP('2024-04-04 17:45:23.191','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547500
;

-- Tab: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule
-- Table: M_ShipmentSchedule_Split
-- 2024-04-04T14:45:25.822Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2024-04-04 17:45:25.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547500
;

-- 2024-04-04T14:45:33.015Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- Tab: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate)
-- UI Section: main
-- 2024-04-04T14:45:39.665Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547500,546081,TO_TIMESTAMP('2024-04-04 17:45:39.52','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-04-04 17:45:39.52','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-04-04T14:45:39.667Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546081 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main
-- UI Column: 10
-- 2024-04-04T14:45:39.802Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547431,546081,TO_TIMESTAMP('2024-04-04 17:45:39.69','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-04-04 17:45:39.69','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main
-- UI Column: 20
-- 2024-04-04T14:45:39.910Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547432,546081,TO_TIMESTAMP('2024-04-04 17:45:39.808','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2024-04-04 17:45:39.808','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: default
-- 2024-04-04T14:45:40.038Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547431,551741,TO_TIMESTAMP('2024-04-04 17:45:39.935','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-04-04 17:45:39.935','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Lieferdisposition
-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_ID
-- 2024-04-04T14:46:30.227Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727325,0,547500,551741,624029,'F',TO_TIMESTAMP('2024-04-04 17:46:30.063','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Lieferdisposition',10,0,0,TO_TIMESTAMP('2024-04-04 17:46:30.063','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Lieferdatum
-- Column: M_ShipmentSchedule_Split.DeliveryDate
-- 2024-04-04T14:46:38.794Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727329,0,547500,551741,624030,'F',TO_TIMESTAMP('2024-04-04 17:46:38.651','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Lieferdatum',20,0,0,TO_TIMESTAMP('2024-04-04 17:46:38.651','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShipmentSchedule_Split.C_UOM_ID
-- 2024-04-04T14:46:46.043Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727330,0,547500,551741,624031,'F',TO_TIMESTAMP('2024-04-04 17:46:45.895','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',30,0,0,TO_TIMESTAMP('2024-04-04 17:46:45.895','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Ausliefermenge
-- Column: M_ShipmentSchedule_Split.QtyToDeliver
-- 2024-04-04T14:46:54.478Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727331,0,547500,551741,624032,'F',TO_TIMESTAMP('2024-04-04 17:46:54.344','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Ausliefermenge',40,0,0,TO_TIMESTAMP('2024-04-04 17:46:54.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.UserElementString1
-- Column: M_ShipmentSchedule_Split.UserElementString1
-- 2024-04-04T14:47:03.252Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727332,0,547500,551741,624033,'F',TO_TIMESTAMP('2024-04-04 17:47:03.101','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','UserElementString1',50,0,0,TO_TIMESTAMP('2024-04-04 17:47:03.101','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.UserElementString2
-- Column: M_ShipmentSchedule_Split.UserElementString2
-- 2024-04-04T14:47:12.456Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727333,0,547500,551741,624034,'F',TO_TIMESTAMP('2024-04-04 17:47:12.316','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','UserElementString2',60,0,0,TO_TIMESTAMP('2024-04-04 17:47:12.316','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20
-- UI Element Group: flags
-- 2024-04-04T14:47:25.063Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547432,551742,TO_TIMESTAMP('2024-04-04 17:47:24.923','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2024-04-04 17:47:24.923','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20 -> flags.Aktiv
-- Column: M_ShipmentSchedule_Split.IsActive
-- 2024-04-04T14:47:35.513Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727323,0,547500,551742,624035,'F',TO_TIMESTAMP('2024-04-04 17:47:35.276','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-04-04 17:47:35.276','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20 -> flags.Verarbeitet
-- Column: M_ShipmentSchedule_Split.Processed
-- 2024-04-04T14:47:45.512Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727326,0,547500,551742,624036,'F',TO_TIMESTAMP('2024-04-04 17:47:45.364','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',20,0,0,TO_TIMESTAMP('2024-04-04 17:47:45.364','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20
-- UI Element Group: shipment
-- 2024-04-04T14:47:53.786Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547432,551743,TO_TIMESTAMP('2024-04-04 17:47:53.636','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','shipment',20,TO_TIMESTAMP('2024-04-04 17:47:53.636','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20 -> shipment.Lieferung/Wareneingang
-- Column: M_ShipmentSchedule_Split.M_InOut_ID
-- 2024-04-04T14:48:03.112Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727327,0,547500,551743,624037,'F',TO_TIMESTAMP('2024-04-04 17:48:01.938','YYYY-MM-DD HH24:MI:SS.US'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','Y','N','N','Lieferung/Wareneingang',10,0,0,TO_TIMESTAMP('2024-04-04 17:48:01.938','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20 -> shipment.Versand-/Wareneingangsposition
-- Column: M_ShipmentSchedule_Split.M_InOutLine_ID
-- 2024-04-04T14:48:10.141Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727328,0,547500,551743,624038,'F',TO_TIMESTAMP('2024-04-04 17:48:09.992','YYYY-MM-DD HH24:MI:SS.US'),100,'Position auf Versand- oder Wareneingangsbeleg','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','Y','N','N','Versand-/Wareneingangsposition',20,0,0,TO_TIMESTAMP('2024-04-04 17:48:09.992','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Lieferdisposition
-- Column: M_ShipmentSchedule_Split.M_ShipmentSchedule_ID
-- 2024-04-04T14:48:34.371Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.371','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624029
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Lieferdatum
-- Column: M_ShipmentSchedule_Split.DeliveryDate
-- 2024-04-04T14:48:34.380Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.379','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624030
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShipmentSchedule_Split.C_UOM_ID
-- 2024-04-04T14:48:34.387Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.387','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624031
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.Ausliefermenge
-- Column: M_ShipmentSchedule_Split.QtyToDeliver
-- 2024-04-04T14:48:34.395Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624032
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.UserElementString1
-- Column: M_ShipmentSchedule_Split.UserElementString1
-- 2024-04-04T14:48:34.402Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.402','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624033
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 10 -> default.UserElementString2
-- Column: M_ShipmentSchedule_Split.UserElementString2
-- 2024-04-04T14:48:34.410Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.41','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624034
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20 -> flags.Verarbeitet
-- Column: M_ShipmentSchedule_Split.Processed
-- 2024-04-04T14:48:34.419Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.419','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624036
;

-- UI Element: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> main -> 20 -> shipment.Versand-/Wareneingangsposition
-- Column: M_ShipmentSchedule_Split.M_InOutLine_ID
-- 2024-04-04T14:48:34.426Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-04-04 17:48:34.426','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624038
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Lieferung/Wareneingang
-- Column: M_ShipmentSchedule_Split.M_InOut_ID
-- 2024-04-04T14:48:54.837Z
UPDATE AD_Field SET DisplayLogic='@Processed/X@=Y',Updated=TO_TIMESTAMP('2024-04-04 17:48:54.836','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727327
;

-- Field: Split Shipment Schedule(541794,de.metas.inoutcandidate) -> Split Shipment Schedule(547500,de.metas.inoutcandidate) -> Versand-/Wareneingangsposition
-- Column: M_ShipmentSchedule_Split.M_InOutLine_ID
-- 2024-04-04T14:48:58.657Z
UPDATE AD_Field SET DisplayLogic='@Processed/X@=Y',Updated=TO_TIMESTAMP('2024-04-04 17:48:58.657','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=727328
;

