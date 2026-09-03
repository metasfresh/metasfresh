-- Table: M_HU_Label_Config
-- Table: M_HU_Label_Config
-- 2022-12-08T13:06:58.921Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('6',0,0,0,542272,'N',TO_TIMESTAMP('2022-12-08 15:06:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','Y','N','N','Y','N','Y','N','N','N',0,'HU Labels Configuration','NP','L','M_HU_Label_Config','DTI',TO_TIMESTAMP('2022-12-08 15:06:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:06:58.923Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542272 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-12-08T13:06:59.039Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556064,TO_TIMESTAMP('2022-12-08 15:06:58','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_HU_Label_Config',1,'Y','N','Y','Y','M_HU_Label_Config','N',1000000,TO_TIMESTAMP('2022-12-08 15:06:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T13:06:59.048Z
CREATE SEQUENCE M_HU_LABEL_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_HU_Label_Config.AD_Client_ID
-- Column: M_HU_Label_Config.AD_Client_ID
-- 2022-12-08T13:07:04.926Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585278,102,0,19,542272,'AD_Client_ID',TO_TIMESTAMP('2022-12-08 15:07:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.handlingunits',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-12-08 15:07:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:04.928Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585278 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:04.958Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_HU_Label_Config.AD_Org_ID
-- Column: M_HU_Label_Config.AD_Org_ID
-- 2022-12-08T13:07:05.887Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585279,113,0,30,542272,'AD_Org_ID',TO_TIMESTAMP('2022-12-08 15:07:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.handlingunits',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-12-08 15:07:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:05.889Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585279 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:05.907Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_HU_Label_Config.Created
-- Column: M_HU_Label_Config.Created
-- 2022-12-08T13:07:06.663Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585280,245,0,16,542272,'Created',TO_TIMESTAMP('2022-12-08 15:07:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.handlingunits',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-12-08 15:07:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:06.664Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585280 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:06.667Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_HU_Label_Config.CreatedBy
-- Column: M_HU_Label_Config.CreatedBy
-- 2022-12-08T13:07:07.372Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585281,246,0,18,110,542272,'CreatedBy',TO_TIMESTAMP('2022-12-08 15:07:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.handlingunits',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-12-08 15:07:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:07.374Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585281 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:07.377Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_HU_Label_Config.IsActive
-- Column: M_HU_Label_Config.IsActive
-- 2022-12-08T13:07:08.112Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585282,348,0,20,542272,'IsActive',TO_TIMESTAMP('2022-12-08 15:07:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.handlingunits',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-12-08 15:07:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:08.114Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585282 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:08.116Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_HU_Label_Config.Updated
-- Column: M_HU_Label_Config.Updated
-- 2022-12-08T13:07:08.811Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585283,607,0,16,542272,'Updated',TO_TIMESTAMP('2022-12-08 15:07:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.handlingunits',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-12-08 15:07:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:08.813Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:08.815Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_HU_Label_Config.UpdatedBy
-- Column: M_HU_Label_Config.UpdatedBy
-- 2022-12-08T13:07:09.521Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585284,608,0,18,110,542272,'UpdatedBy',TO_TIMESTAMP('2022-12-08 15:07:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.handlingunits',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-12-08 15:07:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:09.522Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585284 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:09.525Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-12-08T13:07:10.083Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581844,0,'M_HU_Label_Config_ID',TO_TIMESTAMP('2022-12-08 15:07:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','HU Labels Configuration','HU Labels Configuration',TO_TIMESTAMP('2022-12-08 15:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T13:07:10.086Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581844 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_HU_Label_Config.M_HU_Label_Config_ID
-- Column: M_HU_Label_Config.M_HU_Label_Config_ID
-- 2022-12-08T13:07:10.709Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585285,581844,0,13,542272,'M_HU_Label_Config_ID',TO_TIMESTAMP('2022-12-08 15:07:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','HU Labels Configuration',0,0,TO_TIMESTAMP('2022-12-08 15:07:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:07:10.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585285 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:07:10.715Z
/* DDL */  select update_Column_Translation_From_AD_Element(581844) 
;

-- Column: M_HU_Label_Config.IsApplyToLUs
-- Column: M_HU_Label_Config.IsApplyToLUs
-- 2022-12-08T13:15:02.429Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585286,542500,0,20,542272,'IsApplyToLUs',TO_TIMESTAMP('2022-12-08 15:15:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Auf LUs anwenden',0,0,TO_TIMESTAMP('2022-12-08 15:15:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:15:02.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585286 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:15:02.434Z
/* DDL */  select update_Column_Translation_From_AD_Element(542500) 
;

-- Column: M_HU_Label_Config.IsApplyToCUs
-- Column: M_HU_Label_Config.IsApplyToCUs
-- 2022-12-08T13:15:17.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585287,542502,0,20,542272,'IsApplyToCUs',TO_TIMESTAMP('2022-12-08 15:15:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Auf CUs anwenden',0,0,TO_TIMESTAMP('2022-12-08 15:15:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:15:17.096Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585287 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:15:17.098Z
/* DDL */  select update_Column_Translation_From_AD_Element(542502) 
;

-- Column: M_HU_Label_Config.IsApplyToTUs
-- Column: M_HU_Label_Config.IsApplyToTUs
-- 2022-12-08T13:15:33.540Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585288,542501,0,20,542272,'IsApplyToTUs',TO_TIMESTAMP('2022-12-08 15:15:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Auf TUs anwenden',0,0,TO_TIMESTAMP('2022-12-08 15:15:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:15:33.542Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585288 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:15:33.544Z
/* DDL */  select update_Column_Translation_From_AD_Element(542501) 
;

-- 2022-12-08T13:17:55.648Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581845,0,'LabelReport_Process_ID',TO_TIMESTAMP('2022-12-08 15:17:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Label Print Format','Label Print Format',TO_TIMESTAMP('2022-12-08 15:17:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T13:17:55.650Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581845 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_HU_Process.LabelReport_Process_ID
-- Column: M_HU_Process.LabelReport_Process_ID
-- 2022-12-08T13:50:23.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585289,581845,0,30,540706,540607,540604,'LabelReport_Process_ID',TO_TIMESTAMP('2022-12-08 15:50:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Label Print Format',0,0,TO_TIMESTAMP('2022-12-08 15:50:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:50:23.405Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585289 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:50:23.407Z
/* DDL */  select update_Column_Translation_From_AD_Element(581845) 
;

-- Column: M_HU_Process.LabelReport_Process_ID
-- Column: M_HU_Process.LabelReport_Process_ID
-- 2022-12-08T13:50:38.938Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585289
;

-- 2022-12-08T13:50:38.945Z
DELETE FROM AD_Column WHERE AD_Column_ID=585289
;

-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- 2022-12-08T13:51:19.190Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585290,581845,0,30,540706,542272,540604,'LabelReport_Process_ID',TO_TIMESTAMP('2022-12-08 15:51:19','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Label Print Format',0,0,TO_TIMESTAMP('2022-12-08 15:51:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:51:19.191Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585290 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:51:19.195Z
/* DDL */  select update_Column_Translation_From_AD_Element(581845) 
;

-- Column: M_HU_Label_Config.C_BPartner_ID
-- Column: M_HU_Label_Config.C_BPartner_ID
-- 2022-12-08T13:55:46.564Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585291,187,0,30,542272,'C_BPartner_ID',TO_TIMESTAMP('2022-12-08 15:55:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','de.metas.handlingunits',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2022-12-08 15:55:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T13:55:46.566Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585291 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T13:55:46.569Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2022-12-08T13:56:52.502Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581846,0,'HU_SourceDocType',TO_TIMESTAMP('2022-12-08 15:56:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Source Document Type','Source Document Type',TO_TIMESTAMP('2022-12-08 15:56:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T13:56:52.503Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581846 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: HU_SourceDocType (M_HU_Label_Config)
-- 2022-12-08T13:58:11.002Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541696,TO_TIMESTAMP('2022-12-08 15:58:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','HU_SourceDocType (M_HU_Label_Config)',TO_TIMESTAMP('2022-12-08 15:58:10','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-12-08T13:58:11.004Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541696 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: HU_SourceDocType (M_HU_Label_Config)
-- Value: MO
-- ValueName: Manufacturing
-- 2022-12-08T14:01:51.305Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541696,543358,TO_TIMESTAMP('2022-12-08 16:01:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Manufacturing',TO_TIMESTAMP('2022-12-08 16:01:51','YYYY-MM-DD HH24:MI:SS'),100,'MO','Manufacturing')
;

-- 2022-12-08T14:01:51.307Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543358 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: HU_SourceDocType (M_HU_Label_Config)
-- Value: MR
-- ValueName: MaterialReceipt
-- 2022-12-08T14:02:09.591Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541696,543359,TO_TIMESTAMP('2022-12-08 16:02:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Material Receipt',TO_TIMESTAMP('2022-12-08 16:02:09','YYYY-MM-DD HH24:MI:SS'),100,'MR','MaterialReceipt')
;

-- 2022-12-08T14:02:09.592Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543359 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: M_HU_Label_Config.HU_SourceDocType
-- Column: M_HU_Label_Config.HU_SourceDocType
-- 2022-12-08T14:03:04.753Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585292,581846,0,17,541696,542272,'HU_SourceDocType',TO_TIMESTAMP('2022-12-08 16:03:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Source Document Type',0,0,TO_TIMESTAMP('2022-12-08 16:03:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T14:03:04.755Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585292 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T14:03:04.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(581846) 
;

-- Column: M_HU_Label_Config.Line
-- Column: M_HU_Label_Config.Line
-- 2022-12-08T14:03:50.805Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585293,439,0,11,542272,'Line',TO_TIMESTAMP('2022-12-08 16:03:50','YYYY-MM-DD HH24:MI:SS'),100,'N','','Einzelne Zeile in dem Dokument','de.metas.handlingunits',0,10,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zeile Nr.',0,0,TO_TIMESTAMP('2022-12-08 16:03:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T14:03:50.807Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585293 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T14:03:50.810Z
/* DDL */  select update_Column_Translation_From_AD_Element(439) 
;

-- Column: M_HU_Label_Config.Description
-- Column: M_HU_Label_Config.Description
-- 2022-12-08T14:04:25.880Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585294,275,0,14,542272,'Description',TO_TIMESTAMP('2022-12-08 16:04:25','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2022-12-08 16:04:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T14:04:25.881Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585294 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T14:04:25.884Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: M_HU_Label_Config.M_HU_Label_Config_ID
-- Column: M_HU_Label_Config.M_HU_Label_Config_ID
-- 2022-12-08T14:04:49.142Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2022-12-08 16:04:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585285
;

-- Window: HU Labels Configuration, InternalName=null
-- Window: HU Labels Configuration, InternalName=null
-- 2022-12-08T14:06:00.139Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581844,0,541647,TO_TIMESTAMP('2022-12-08 16:05:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y','HU Labels Configuration','N',TO_TIMESTAMP('2022-12-08 16:05:59','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-12-08T14:06:00.141Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541647 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-12-08T14:06:00.145Z
/* DDL */  select update_window_translation_from_ad_element(581844) 
;

-- 2022-12-08T14:06:00.158Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541647
;

-- 2022-12-08T14:06:00.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541647)
;

-- Tab: HU Labels Configuration -> HU Labels Configuration
-- Table: M_HU_Label_Config
-- Tab: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration
-- Table: M_HU_Label_Config
-- 2022-12-08T14:06:25.407Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581844,0,546701,542272,541647,'Y',TO_TIMESTAMP('2022-12-08 16:06:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','N','A','M_HU_Label_Config','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'HU Labels Configuration','N',10,0,TO_TIMESTAMP('2022-12-08 16:06:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:25.409Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546701 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-12-08T14:06:25.411Z
/* DDL */  select update_tab_translation_from_ad_element(581844) 
;

-- 2022-12-08T14:06:25.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546701)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Mandant
-- Column: M_HU_Label_Config.AD_Client_ID
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Mandant
-- Column: M_HU_Label_Config.AD_Client_ID
-- 2022-12-08T14:06:35.831Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585278,708914,0,546701,TO_TIMESTAMP('2022-12-08 16:06:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-12-08 16:06:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:35.832Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:35.834Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-12-08T14:06:36.065Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708914
;

-- 2022-12-08T14:06:36.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708914)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Sektion
-- Column: M_HU_Label_Config.AD_Org_ID
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Sektion
-- Column: M_HU_Label_Config.AD_Org_ID
-- 2022-12-08T14:06:36.161Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585279,708915,0,546701,TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:36.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:36.164Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-12-08T14:06:36.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708915
;

-- 2022-12-08T14:06:36.407Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708915)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Aktiv
-- Column: M_HU_Label_Config.IsActive
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Aktiv
-- Column: M_HU_Label_Config.IsActive
-- 2022-12-08T14:06:36.509Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585282,708916,0,546701,TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:36.510Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:36.512Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-12-08T14:06:36.719Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708916
;

-- 2022-12-08T14:06:36.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708916)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> HU Labels Configuration
-- Column: M_HU_Label_Config.M_HU_Label_Config_ID
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> HU Labels Configuration
-- Column: M_HU_Label_Config.M_HU_Label_Config_ID
-- 2022-12-08T14:06:36.820Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585285,708917,0,546701,TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','HU Labels Configuration',TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:36.821Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:36.823Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581844) 
;

-- 2022-12-08T14:06:36.825Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708917
;

-- 2022-12-08T14:06:36.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708917)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Auf LUs anwenden
-- Column: M_HU_Label_Config.IsApplyToLUs
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Auf LUs anwenden
-- Column: M_HU_Label_Config.IsApplyToLUs
-- 2022-12-08T14:06:36.919Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585286,708918,0,546701,TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Auf LUs anwenden',TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:36.920Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:36.921Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542500) 
;

-- 2022-12-08T14:06:36.924Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708918
;

-- 2022-12-08T14:06:36.925Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708918)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Auf CUs anwenden
-- Column: M_HU_Label_Config.IsApplyToCUs
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Auf CUs anwenden
-- Column: M_HU_Label_Config.IsApplyToCUs
-- 2022-12-08T14:06:37.015Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585287,708919,0,546701,TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Auf CUs anwenden',TO_TIMESTAMP('2022-12-08 16:06:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:37.017Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:37.018Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542502) 
;

-- 2022-12-08T14:06:37.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708919
;

-- 2022-12-08T14:06:37.024Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708919)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Auf TUs anwenden
-- Column: M_HU_Label_Config.IsApplyToTUs
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Auf TUs anwenden
-- Column: M_HU_Label_Config.IsApplyToTUs
-- 2022-12-08T14:06:37.123Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585288,708920,0,546701,TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Auf TUs anwenden',TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:37.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:37.127Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542501) 
;

-- 2022-12-08T14:06:37.130Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708920
;

-- 2022-12-08T14:06:37.130Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708920)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Label Print Format
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Label Print Format
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- 2022-12-08T14:06:37.241Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585290,708921,0,546701,TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Label Print Format',TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:37.243Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:37.244Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581845) 
;

-- 2022-12-08T14:06:37.247Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708921
;

-- 2022-12-08T14:06:37.247Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708921)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Geschäftspartner
-- Column: M_HU_Label_Config.C_BPartner_ID
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Geschäftspartner
-- Column: M_HU_Label_Config.C_BPartner_ID
-- 2022-12-08T14:06:37.346Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585291,708922,0,546701,TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.handlingunits','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:37.348Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:37.349Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2022-12-08T14:06:37.357Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708922
;

-- 2022-12-08T14:06:37.358Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708922)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Source Document Type
-- Column: M_HU_Label_Config.HU_SourceDocType
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Source Document Type
-- Column: M_HU_Label_Config.HU_SourceDocType
-- 2022-12-08T14:06:37.475Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585292,708923,0,546701,TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Source Document Type',TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:37.477Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:37.478Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581846) 
;

-- 2022-12-08T14:06:37.481Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708923
;

-- 2022-12-08T14:06:37.481Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708923)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- 2022-12-08T14:06:37.603Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585293,708924,0,546701,TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument',10,'de.metas.handlingunits','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','N','N','N','N','N','Zeile Nr.',TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:37.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:37.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2022-12-08T14:06:37.613Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708924
;

-- 2022-12-08T14:06:37.614Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708924)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Beschreibung
-- Column: M_HU_Label_Config.Description
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Beschreibung
-- Column: M_HU_Label_Config.Description
-- 2022-12-08T14:06:37.727Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585294,708925,0,546701,TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100,2000,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-12-08 16:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:06:37.729Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T14:06:37.730Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-12-08T14:06:37.788Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708925
;

-- 2022-12-08T14:06:37.790Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708925)
;

-- Table: M_HU_Label_Config
-- Table: M_HU_Label_Config
-- 2022-12-08T14:06:53.837Z
UPDATE AD_Table SET AD_Window_ID=541647,Updated=TO_TIMESTAMP('2022-12-08 16:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542272
;

-- Column: M_HU_Label_Config.C_BPartner_ID
-- Column: M_HU_Label_Config.C_BPartner_ID
-- 2022-12-08T14:07:05.610Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-08 16:07:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585291
;

-- Column: M_HU_Label_Config.HU_SourceDocType
-- Column: M_HU_Label_Config.HU_SourceDocType
-- 2022-12-08T14:07:16.441Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-08 16:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585292
;

-- Column: M_HU_Label_Config.IsApplyToCUs
-- Column: M_HU_Label_Config.IsApplyToCUs
-- 2022-12-08T14:07:22.342Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-08 16:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585287
;

-- Column: M_HU_Label_Config.IsApplyToLUs
-- Column: M_HU_Label_Config.IsApplyToLUs
-- 2022-12-08T14:07:23.844Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-08 16:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585286
;

-- Column: M_HU_Label_Config.IsApplyToTUs
-- Column: M_HU_Label_Config.IsApplyToTUs
-- 2022-12-08T14:07:25.496Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-08 16:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585288
;

-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- 2022-12-08T14:07:29.068Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-08 16:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585290
;

-- Tab: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits)
-- UI Section: main
-- 2022-12-08T14:12:25.366Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546701,545326,TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-12-08T14:12:25.367Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545326 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2022-12-08T14:12:25.556Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546497,545326,TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2022-12-08T14:12:25.647Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546498,545326,TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10
-- UI Element Group: default
-- 2022-12-08T14:12:25.835Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546497,550115,TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-12-08 16:12:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> default.Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- 2022-12-08T14:13:35.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708924,0,546701,550115,613919,'F',TO_TIMESTAMP('2022-12-08 16:13:35','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','Zeile Nr.',10,0,0,TO_TIMESTAMP('2022-12-08 16:13:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Beschreibung
-- Column: M_HU_Label_Config.Description
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> default.Beschreibung
-- Column: M_HU_Label_Config.Description
-- 2022-12-08T14:13:42.584Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708925,0,546701,550115,613920,'F',TO_TIMESTAMP('2022-12-08 16:13:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2022-12-08 16:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10
-- UI Element Group: Matching Criteria
-- 2022-12-08T14:14:20.305Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546497,550116,TO_TIMESTAMP('2022-12-08 16:14:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','Matching Criteria',20,TO_TIMESTAMP('2022-12-08 16:14:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Source Document Type
-- Column: M_HU_Label_Config.HU_SourceDocType
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Source Document Type
-- Column: M_HU_Label_Config.HU_SourceDocType
-- 2022-12-08T14:14:37.679Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708923,0,546701,550116,613921,'F',TO_TIMESTAMP('2022-12-08 16:14:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Source Document Type',10,0,0,TO_TIMESTAMP('2022-12-08 16:14:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Auf LUs anwenden
-- Column: M_HU_Label_Config.IsApplyToLUs
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Auf LUs anwenden
-- Column: M_HU_Label_Config.IsApplyToLUs
-- 2022-12-08T14:14:48.535Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708918,0,546701,550116,613922,'F',TO_TIMESTAMP('2022-12-08 16:14:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Auf LUs anwenden',20,0,0,TO_TIMESTAMP('2022-12-08 16:14:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Auf TUs anwenden
-- Column: M_HU_Label_Config.IsApplyToTUs
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Auf TUs anwenden
-- Column: M_HU_Label_Config.IsApplyToTUs
-- 2022-12-08T14:14:54.698Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708920,0,546701,550116,613923,'F',TO_TIMESTAMP('2022-12-08 16:14:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Auf TUs anwenden',30,0,0,TO_TIMESTAMP('2022-12-08 16:14:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Auf CUs anwenden
-- Column: M_HU_Label_Config.IsApplyToCUs
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Auf CUs anwenden
-- Column: M_HU_Label_Config.IsApplyToCUs
-- 2022-12-08T14:15:00.829Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708919,0,546701,550116,613924,'F',TO_TIMESTAMP('2022-12-08 16:15:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Auf CUs anwenden',40,0,0,TO_TIMESTAMP('2022-12-08 16:15:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Geschäftspartner
-- Column: M_HU_Label_Config.C_BPartner_ID
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Geschäftspartner
-- Column: M_HU_Label_Config.C_BPartner_ID
-- 2022-12-08T14:15:08.663Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708922,0,546701,550116,613925,'F',TO_TIMESTAMP('2022-12-08 16:15:08','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','Geschäftspartner',50,0,0,TO_TIMESTAMP('2022-12-08 16:15:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20
-- UI Element Group: flags
-- 2022-12-08T14:15:29.437Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546498,550117,TO_TIMESTAMP('2022-12-08 16:15:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-12-08 16:15:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Aktiv
-- Column: M_HU_Label_Config.IsActive
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> flags.Aktiv
-- Column: M_HU_Label_Config.IsActive
-- 2022-12-08T14:15:39.795Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708916,0,546701,550117,613926,'F',TO_TIMESTAMP('2022-12-08 16:15:39','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2022-12-08 16:15:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20
-- UI Element Group: Settings to use
-- 2022-12-08T14:16:04.987Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546498,550118,TO_TIMESTAMP('2022-12-08 16:16:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','Settings to use',20,TO_TIMESTAMP('2022-12-08 16:16:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Label Print Format
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> Settings to use.Label Print Format
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- 2022-12-08T14:16:33.600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708921,0,546701,550118,613927,'F',TO_TIMESTAMP('2022-12-08 16:16:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Label Print Format',10,0,0,TO_TIMESTAMP('2022-12-08 16:16:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20
-- UI Element Group: org & client
-- 2022-12-08T14:16:43.047Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546498,550119,TO_TIMESTAMP('2022-12-08 16:16:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','org & client',30,TO_TIMESTAMP('2022-12-08 16:16:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Sektion
-- Column: M_HU_Label_Config.AD_Org_ID
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> org & client.Sektion
-- Column: M_HU_Label_Config.AD_Org_ID
-- 2022-12-08T14:18:08.699Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708915,0,546701,550119,613928,'F',TO_TIMESTAMP('2022-12-08 16:18:08','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2022-12-08 16:18:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Mandant
-- Column: M_HU_Label_Config.AD_Client_ID
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> org & client.Mandant
-- Column: M_HU_Label_Config.AD_Client_ID
-- 2022-12-08T14:18:15.402Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708914,0,546701,550119,613929,'F',TO_TIMESTAMP('2022-12-08 16:18:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2022-12-08 16:18:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> default.Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- 2022-12-08T14:18:58.297Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613919
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Source Document Type
-- Column: M_HU_Label_Config.HU_SourceDocType
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Source Document Type
-- Column: M_HU_Label_Config.HU_SourceDocType
-- 2022-12-08T14:18:58.305Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613921
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Auf LUs anwenden
-- Column: M_HU_Label_Config.IsApplyToLUs
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Auf LUs anwenden
-- Column: M_HU_Label_Config.IsApplyToLUs
-- 2022-12-08T14:18:58.314Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613922
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Auf TUs anwenden
-- Column: M_HU_Label_Config.IsApplyToTUs
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Auf TUs anwenden
-- Column: M_HU_Label_Config.IsApplyToTUs
-- 2022-12-08T14:18:58.322Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613923
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Auf CUs anwenden
-- Column: M_HU_Label_Config.IsApplyToCUs
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Auf CUs anwenden
-- Column: M_HU_Label_Config.IsApplyToCUs
-- 2022-12-08T14:18:58.331Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613924
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Geschäftspartner
-- Column: M_HU_Label_Config.C_BPartner_ID
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> Matching Criteria.Geschäftspartner
-- Column: M_HU_Label_Config.C_BPartner_ID
-- 2022-12-08T14:18:58.338Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613925
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Label Print Format
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> Settings to use.Label Print Format
-- Column: M_HU_Label_Config.LabelReport_Process_ID
-- 2022-12-08T14:18:58.345Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613927
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Aktiv
-- Column: M_HU_Label_Config.IsActive
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> flags.Aktiv
-- Column: M_HU_Label_Config.IsActive
-- 2022-12-08T14:18:58.352Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613926
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Beschreibung
-- Column: M_HU_Label_Config.Description
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 10 -> default.Beschreibung
-- Column: M_HU_Label_Config.Description
-- 2022-12-08T14:18:58.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-12-08 16:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613920
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Zeile Nr.
-- Column: M_HU_Label_Config.Line
-- 2022-12-08T14:19:19.643Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2022-12-08 16:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708924
;

-- Window: HU Labels Configuration, InternalName=huLabelsConfig
-- Window: HU Labels Configuration, InternalName=huLabelsConfig
-- 2022-12-08T14:24:23.856Z
UPDATE AD_Window SET InternalName='huLabelsConfig',Updated=TO_TIMESTAMP('2022-12-08 16:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541647
;

-- Name: HU Labels Configuration
-- Action Type: W
-- Window: HU Labels Configuration(541647,de.metas.handlingunits)
-- 2022-12-08T14:24:35.481Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581844,542029,0,541647,TO_TIMESTAMP('2022-12-08 16:24:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','huLabelsConfig','Y','N','N','N','N','HU Labels Configuration',TO_TIMESTAMP('2022-12-08 16:24:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T14:24:35.483Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542029 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-12-08T14:24:35.485Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542029, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542029)
;

-- 2022-12-08T14:24:35.499Z
/* DDL */  select update_menu_translation_from_ad_element(581844) 
;

-- Reordering children of `Settings`
-- Node name: `Attribute`
-- 2022-12-08T14:24:36.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541598 AND AD_Tree_ID=10
;

-- Node name: `Dimension`
-- 2022-12-08T14:24:36.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- Node name: `Product Translation (M_Product_Trl)`
-- 2022-12-08T14:24:36.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Translation (PP_Product_BOM_Trl)`
-- 2022-12-08T14:24:36.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- Node name: `Product Businesspartner Translation (C_BPartner_Product_Trl)`
-- 2022-12-08T14:24:36.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- Node name: `Unit of Measure (C_UOM)`
-- 2022-12-08T14:24:36.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- Node name: `Unit of Measure Translation (C_UOM_Trl)`
-- 2022-12-08T14:24:36.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- Node name: `Compensation Group Schema (C_CompensationGroup_Schema)`
-- 2022-12-08T14:24:36.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- Node name: `Compensation Group Schema Category (C_CompensationGroup_Schema_Category)`
-- 2022-12-08T14:24:36.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541726 AND AD_Tree_ID=10
;

-- Node name: `Product Category (M_Product_Category)`
-- 2022-12-08T14:24:36.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- Node name: `Shop Category (M_Shop_Category)`
-- 2022-12-08T14:24:36.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541722 AND AD_Tree_ID=10
;

-- Node name: `Product Category Trl (M_Product_Category_Trl)`
-- 2022-12-08T14:24:36.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

-- Node name: `HU Labels Configuration`
-- 2022-12-08T14:24:36.116Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542029 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `HU Labels Configuration`
-- 2022-12-08T14:24:59.061Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000075, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542029 AND AD_Tree_ID=10
;

-- Node name: `Versandkostenpauschale (M_FreightCost)`
-- 2022-12-08T14:24:59.062Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000075, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- Node name: `Freight Cost Shipper (M_FreightCostShipper)`
-- 2022-12-08T14:24:59.063Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000075, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541300 AND AD_Tree_ID=10
;

-- Node name: `Shipper (M_Shipper)`
-- 2022-12-08T14:24:59.064Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000075, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540909 AND AD_Tree_ID=10
;

-- Node name: `Distribution Configuration (DD_NetworkDistribution)`
-- 2022-12-08T14:24:59.065Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000075, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540821 AND AD_Tree_ID=10
;

-- 2022-12-08T14:26:30.430Z
/* DDL */ CREATE TABLE public.M_HU_Label_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BPartner_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000), HU_SourceDocType VARCHAR(2), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsApplyToCUs CHAR(1) DEFAULT 'N' CHECK (IsApplyToCUs IN ('Y','N')) NOT NULL, IsApplyToLUs CHAR(1) DEFAULT 'N' CHECK (IsApplyToLUs IN ('Y','N')) NOT NULL, IsApplyToTUs CHAR(1) DEFAULT 'N' CHECK (IsApplyToTUs IN ('Y','N')) NOT NULL, LabelReport_Process_ID NUMERIC(10) NOT NULL, Line NUMERIC(10) NOT NULL, M_HU_Label_Config_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CBPartner_MHULabelConfig FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT LabelReportProcess_MHULabelConfig FOREIGN KEY (LabelReport_Process_ID) REFERENCES public.AD_Process DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_HU_Label_Config_Key PRIMARY KEY (M_HU_Label_Config_ID))
;

-- Column: M_HU_Label_Config.SeqNo
-- Column: M_HU_Label_Config.SeqNo
-- 2022-12-08T14:43:31.964Z
UPDATE AD_Column SET AD_Element_ID=566, ColumnName='SeqNo', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', Name='Reihenfolge',Updated=TO_TIMESTAMP('2022-12-08 16:43:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585293
;

-- 2022-12-08T14:43:31.966Z
UPDATE AD_Field SET Name='Reihenfolge', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Column_ID=585293
;

-- 2022-12-08T14:43:31.967Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

alter table m_hu_label_config rename column line to seqno;

-- Reference: HU_SourceDocType (M_HU_Label_Config)
-- Value: PI
-- ValueName: Picking
-- 2022-12-08T14:58:05.239Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541696,543360,TO_TIMESTAMP('2022-12-08 16:58:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Picking',TO_TIMESTAMP('2022-12-08 16:58:05','YYYY-MM-DD HH24:MI:SS'),100,'PI','Picking')
;

-- 2022-12-08T14:58:05.240Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543360 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-12-08T20:32:20.790Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581849,0,'IsAutoPrint',TO_TIMESTAMP('2022-12-08 22:32:20','YYYY-MM-DD HH24:MI:SS'),100,'Print directly when the HU becomes Active','D','Y','Print directly','Print directly',TO_TIMESTAMP('2022-12-08 22:32:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T20:32:20.807Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581849 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-08T20:33:18.030Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581850,0,'AutoPrintCopies',TO_TIMESTAMP('2022-12-08 22:33:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Copies to Print Directly','Copies to Print Directly',TO_TIMESTAMP('2022-12-08 22:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T20:33:18.033Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581850 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_HU_Label_Config.IsAutoPrint
-- Column: M_HU_Label_Config.IsAutoPrint
-- 2022-12-08T20:36:31.180Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585307,581849,0,20,542272,'IsAutoPrint',TO_TIMESTAMP('2022-12-08 22:36:31','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Print directly when the HU becomes Active','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Print directly',0,0,TO_TIMESTAMP('2022-12-08 22:36:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T20:36:31.186Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585307 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T20:36:31.199Z
/* DDL */  select update_Column_Translation_From_AD_Element(581849) 
;

-- 2022-12-08T20:36:35.399Z
/* DDL */ SELECT public.db_alter_table('M_HU_Label_Config','ALTER TABLE public.M_HU_Label_Config ADD COLUMN IsAutoPrint CHAR(1) DEFAULT ''N'' CHECK (IsAutoPrint IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_HU_Label_Config.AutoPrintCopies
-- Column: M_HU_Label_Config.AutoPrintCopies
-- 2022-12-08T20:37:06.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585308,581850,0,11,542272,'AutoPrintCopies',TO_TIMESTAMP('2022-12-08 22:37:05','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Copies to Print Directly',0,0,TO_TIMESTAMP('2022-12-08 22:37:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T20:37:06.080Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585308 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T20:37:06.095Z
/* DDL */  select update_Column_Translation_From_AD_Element(581850) 
;

-- 2022-12-08T20:37:08.183Z
/* DDL */ SELECT public.db_alter_table('M_HU_Label_Config','ALTER TABLE public.M_HU_Label_Config ADD COLUMN AutoPrintCopies NUMERIC(10) NOT NULL')
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Print directly
-- Column: M_HU_Label_Config.IsAutoPrint
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Print directly
-- Column: M_HU_Label_Config.IsAutoPrint
-- 2022-12-08T20:37:37.852Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585307,708934,0,546701,TO_TIMESTAMP('2022-12-08 22:37:37','YYYY-MM-DD HH24:MI:SS'),100,'Print directly when the HU becomes Active',1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Print directly',TO_TIMESTAMP('2022-12-08 22:37:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T20:37:37.862Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T20:37:37.868Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581849) 
;

-- 2022-12-08T20:37:37.880Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708934
;

-- 2022-12-08T20:37:37.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708934)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Copies to Print Directly
-- Column: M_HU_Label_Config.AutoPrintCopies
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Copies to Print Directly
-- Column: M_HU_Label_Config.AutoPrintCopies
-- 2022-12-08T20:37:38.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585308,708935,0,546701,TO_TIMESTAMP('2022-12-08 22:37:37','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Copies to Print Directly',TO_TIMESTAMP('2022-12-08 22:37:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T20:37:38.033Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-08T20:37:38.040Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581850) 
;

-- 2022-12-08T20:37:38.048Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708935
;

-- 2022-12-08T20:37:38.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708935)
;

-- UI Column: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20
-- UI Element Group: Configuration to use
-- 2022-12-08T20:38:09.936Z
UPDATE AD_UI_ElementGroup SET Name='Configuration to use',Updated=TO_TIMESTAMP('2022-12-08 22:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550118
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Print directly
-- Column: M_HU_Label_Config.IsAutoPrint
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> Configuration to use.Print directly
-- Column: M_HU_Label_Config.IsAutoPrint
-- 2022-12-08T20:38:28.062Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708934,0,546701,550118,613936,'F',TO_TIMESTAMP('2022-12-08 22:38:27','YYYY-MM-DD HH24:MI:SS'),100,'Print directly when the HU becomes Active','Y','N','Y','N','N','Print directly',20,0,0,TO_TIMESTAMP('2022-12-08 22:38:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: HU Labels Configuration -> HU Labels Configuration.Copies to Print Directly
-- Column: M_HU_Label_Config.AutoPrintCopies
-- UI Element: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> main -> 20 -> Configuration to use.Copies to Print Directly
-- Column: M_HU_Label_Config.AutoPrintCopies
-- 2022-12-08T20:38:34.761Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708935,0,546701,550118,613937,'F',TO_TIMESTAMP('2022-12-08 22:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Copies to Print Directly',30,0,0,TO_TIMESTAMP('2022-12-08 22:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Copies to Print Directly
-- Column: M_HU_Label_Config.AutoPrintCopies
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Copies to Print Directly
-- Column: M_HU_Label_Config.AutoPrintCopies
-- 2022-12-08T20:39:36.098Z
UPDATE AD_Field SET DisplayLogic='@IsAutoPrint/N@=Y',Updated=TO_TIMESTAMP('2022-12-08 22:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708935
;

-- Field: HU Labels Configuration -> HU Labels Configuration -> Print directly
-- Column: M_HU_Label_Config.IsAutoPrint
-- Field: HU Labels Configuration(541647,de.metas.handlingunits) -> HU Labels Configuration(546701,de.metas.handlingunits) -> Print directly
-- Column: M_HU_Label_Config.IsAutoPrint
-- 2022-12-08T20:43:19.706Z
UPDATE AD_Field SET DisplayLogic='@HU_SourceDocType/XX@=MR | @HU_SourceDocType/XX@=PI',Updated=TO_TIMESTAMP('2022-12-08 22:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708934
;

