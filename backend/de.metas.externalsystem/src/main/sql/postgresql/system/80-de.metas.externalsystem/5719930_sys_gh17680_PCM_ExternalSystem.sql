-- Table: ExternalSystem_Config_ProCareManagement
-- 2024-03-19T18:48:40.349Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542399,'N',TO_TIMESTAMP('2024-03-19 20:48:40','YYYY-MM-DD HH24:MI:SS'),100,'U','N','Y','N','N','Y','N','N','N','N','N',0,'ExternalSystem_Config_ProCareManagement','NP','L','ExternalSystem_Config_ProCareManagement','DTI',TO_TIMESTAMP('2024-03-19 20:48:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:48:40.354Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542399 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-03-19T18:48:40.503Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556335,TO_TIMESTAMP('2024-03-19 20:48:40','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_ProCareManagement',1,'Y','N','Y','Y','ExternalSystem_Config_ProCareManagement','N',1000000,TO_TIMESTAMP('2024-03-19 20:48:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:48:40.521Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_PROCAREMANAGEMENT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: ExternalSystem_Config_ProCareManagement
-- 2024-03-19T18:48:53.243Z
UPDATE AD_Table SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2024-03-19 20:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542399
;

-- Column: ExternalSystem_Config_ProCareManagement.AD_Client_ID
-- 2024-03-19T18:49:03.106Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587994,102,0,19,542399,'AD_Client_ID',TO_TIMESTAMP('2024-03-19 20:49:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-03-19 20:49:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:03.111Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587994 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:03.157Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ExternalSystem_Config_ProCareManagement.AD_Org_ID
-- 2024-03-19T18:49:04.330Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587995,113,0,30,542399,'AD_Org_ID',TO_TIMESTAMP('2024-03-19 20:49:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-03-19 20:49:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:04.333Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587995 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:04.336Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ExternalSystem_Config_ProCareManagement.Created
-- 2024-03-19T18:49:04.876Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587996,245,0,16,542399,'Created',TO_TIMESTAMP('2024-03-19 20:49:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-03-19 20:49:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:04.878Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587996 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:04.881Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ExternalSystem_Config_ProCareManagement.CreatedBy
-- 2024-03-19T18:49:05.426Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587997,246,0,18,110,542399,'CreatedBy',TO_TIMESTAMP('2024-03-19 20:49:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-03-19 20:49:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:05.429Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587997 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:05.432Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ExternalSystem_Config_ProCareManagement.IsActive
-- 2024-03-19T18:49:06.002Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587998,348,0,20,542399,'IsActive',TO_TIMESTAMP('2024-03-19 20:49:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-03-19 20:49:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:06.005Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587998 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:06.009Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ExternalSystem_Config_ProCareManagement.Updated
-- 2024-03-19T18:49:06.556Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587999,607,0,16,542399,'Updated',TO_TIMESTAMP('2024-03-19 20:49:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-03-19 20:49:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:06.558Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587999 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:06.561Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ExternalSystem_Config_ProCareManagement.UpdatedBy
-- 2024-03-19T18:49:07.144Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588000,608,0,18,110,542399,'UpdatedBy',TO_TIMESTAMP('2024-03-19 20:49:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-03-19 20:49:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:07.145Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588000 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:07.147Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-03-19T18:49:07.772Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583027,0,'ExternalSystem_Config_ProCareManagement_ID',TO_TIMESTAMP('2024-03-19 20:49:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_Config_ProCareManagement','ExternalSystem_Config_ProCareManagement',TO_TIMESTAMP('2024-03-19 20:49:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:49:07.775Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583027 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-19T18:49:08.230Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588001,583027,0,13,542399,'ExternalSystem_Config_ProCareManagement_ID',TO_TIMESTAMP('2024-03-19 20:49:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','ExternalSystem_Config_ProCareManagement',0,0,TO_TIMESTAMP('2024-03-19 20:49:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:49:08.233Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588001 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:49:08.237Z
/* DDL */  select update_Column_Translation_From_AD_Element(583027) 
;

-- 2024-03-19T18:49:08.686Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_ProCareManagement (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalSystem_Config_ProCareManagement_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Config_ProCareManagement_Key PRIMARY KEY (ExternalSystem_Config_ProCareManagement_ID))
;

-- 2024-03-19T18:49:08.742Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-03-19T18:49:08.752Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-03-19T18:49:08.761Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-03-19T18:49:08.777Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement','IsActive','CHAR(1)',null,null)
;

-- 2024-03-19T18:49:08.798Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-03-19T18:49:08.806Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-03-19T18:49:08.815Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement','ExternalSystem_Config_ProCareManagement_ID','NUMERIC(10)',null,null)
;

-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ID
-- 2024-03-19T18:50:35.192Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588002,578728,0,19,542399,'ExternalSystem_Config_ID',TO_TIMESTAMP('2024-03-19 20:50:35','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2024-03-19 20:50:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:50:35.195Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588002 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:50:35.203Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- 2024-03-19T18:50:36.549Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement','ALTER TABLE public.ExternalSystem_Config_ProCareManagement ADD COLUMN ExternalSystem_Config_ID NUMERIC(10) NOT NULL')
;

-- 2024-03-19T18:50:36.578Z
ALTER TABLE ExternalSystem_Config_ProCareManagement ADD CONSTRAINT ExternalSystemConfig_ExternalSystemConfigProCareManagement FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystemValue
-- 2024-03-19T18:51:19.633Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588003,578788,0,10,542399,'ExternalSystemValue',TO_TIMESTAMP('2024-03-19 20:51:19','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2024-03-19 20:51:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-19T18:51:19.635Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588003 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-19T18:51:19.640Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788) 
;

-- 2024-03-19T18:51:21.439Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement','ALTER TABLE public.ExternalSystem_Config_ProCareManagement ADD COLUMN ExternalSystemValue VARCHAR(255) NOT NULL')
;

-- Tab: Externes System -> ExternalSystem_Config_ProCareManagement
-- Table: ExternalSystem_Config_ProCareManagement
-- 2024-03-19T18:54:39.018Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,588002,583027,0,547485,542399,541024,'Y',TO_TIMESTAMP('2024-03-19 20:54:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_ProCareManagement','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_ProCareManagement','N',80,1,TO_TIMESTAMP('2024-03-19 20:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:54:39.022Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547485 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-03-19T18:54:39.028Z
/* DDL */  select update_tab_translation_from_ad_element(583027) 
;

-- 2024-03-19T18:54:39.043Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547485)
;

-- Field: Externes System -> ExternalSystem_Config_ProCareManagement -> Mandant
-- Column: ExternalSystem_Config_ProCareManagement.AD_Client_ID
-- 2024-03-19T18:54:56.529Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587994,726583,0,547485,TO_TIMESTAMP('2024-03-19 20:54:56','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-03-19 20:54:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:54:56.532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726583 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T18:54:56.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-03-19T18:54:57.412Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726583
;

-- 2024-03-19T18:54:57.416Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726583)
;

-- Field: Externes System -> ExternalSystem_Config_ProCareManagement -> Sektion
-- Column: ExternalSystem_Config_ProCareManagement.AD_Org_ID
-- 2024-03-19T18:54:57.568Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587995,726584,0,547485,TO_TIMESTAMP('2024-03-19 20:54:57','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-03-19 20:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:54:57.568Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726584 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T18:54:57.570Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-03-19T18:54:57.693Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726584
;

-- 2024-03-19T18:54:57.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726584)
;

-- Field: Externes System -> ExternalSystem_Config_ProCareManagement -> Aktiv
-- Column: ExternalSystem_Config_ProCareManagement.IsActive
-- 2024-03-19T18:54:57.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587998,726585,0,547485,TO_TIMESTAMP('2024-03-19 20:54:57','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-03-19 20:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:54:57.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726585 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T18:54:57.858Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-03-19T18:54:57.979Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726585
;

-- 2024-03-19T18:54:57.980Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726585)
;

-- Field: Externes System -> ExternalSystem_Config_ProCareManagement -> ExternalSystem_Config_ProCareManagement
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-19T18:54:58.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588001,726586,0,547485,TO_TIMESTAMP('2024-03-19 20:54:57','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_ProCareManagement',TO_TIMESTAMP('2024-03-19 20:54:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:54:58.231Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726586 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T18:54:58.233Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583027) 
;

-- 2024-03-19T18:54:58.239Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726586
;

-- 2024-03-19T18:54:58.240Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726586)
;

-- Field: Externes System -> ExternalSystem_Config_ProCareManagement -> External System Config
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ID
-- 2024-03-19T18:54:58.345Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588002,726587,0,547485,TO_TIMESTAMP('2024-03-19 20:54:58','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2024-03-19 20:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:54:58.348Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726587 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T18:54:58.350Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2024-03-19T18:54:58.355Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726587
;

-- 2024-03-19T18:54:58.356Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726587)
;

-- Field: Externes System -> ExternalSystem_Config_ProCareManagement -> Suchschlüssel
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystemValue
-- 2024-03-19T18:54:58.457Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588003,726588,0,547485,TO_TIMESTAMP('2024-03-19 20:54:58','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2024-03-19 20:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:54:58.459Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726588 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T18:54:58.460Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788) 
;

-- 2024-03-19T18:54:58.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726588
;

-- 2024-03-19T18:54:58.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726588)
;

-- 2024-03-19T18:55:15.575Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547485,546064,TO_TIMESTAMP('2024-03-19 20:55:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-19 20:55:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-03-19T18:55:15.578Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546064 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2024-03-19T18:55:19.907Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547407,546064,TO_TIMESTAMP('2024-03-19 20:55:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-19 20:55:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:55:28.087Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547407,551695,TO_TIMESTAMP('2024-03-19 20:55:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2024-03-19 20:55:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-19T18:55:44.781Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726586,0,547485,623760,551695,'F',TO_TIMESTAMP('2024-03-19 20:55:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalSystem_Config_ProCareManagement',10,0,0,TO_TIMESTAMP('2024-03-19 20:55:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_ProCareManagement.Suchschlüssel
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystemValue
-- 2024-03-19T18:56:30.006Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726588,0,547485,623761,551695,'F',TO_TIMESTAMP('2024-03-19 20:56:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',5,0,0,TO_TIMESTAMP('2024-03-19 20:56:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:56:34.985Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2024-03-19 20:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551695
;

-- 2024-03-19T18:56:43.057Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547408,546064,TO_TIMESTAMP('2024-03-19 20:56:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-03-19 20:56:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:56:55.665Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547408,551696,TO_TIMESTAMP('2024-03-19 20:56:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-03-19 20:56:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_ProCareManagement.Aktiv
-- Column: ExternalSystem_Config_ProCareManagement.IsActive
-- 2024-03-19T18:57:03.255Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726585,0,547485,623762,551696,'F',TO_TIMESTAMP('2024-03-19 20:57:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-03-19 20:57:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T18:57:08.008Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547408,551697,TO_TIMESTAMP('2024-03-19 20:57:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2024-03-19 20:57:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_ProCareManagement.Mandant
-- Column: ExternalSystem_Config_ProCareManagement.AD_Client_ID
-- 2024-03-19T18:57:22.009Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726583,0,547485,623763,551697,'F',TO_TIMESTAMP('2024-03-19 20:57:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2024-03-19 20:57:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_ProCareManagement.Sektion
-- Column: ExternalSystem_Config_ProCareManagement.AD_Org_ID
-- 2024-03-19T18:57:27.614Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726584,0,547485,623764,551697,'F',TO_TIMESTAMP('2024-03-19 20:57:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2024-03-19 20:57:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-19T18:57:39.684Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-03-19 20:57:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623760
;

-- UI Element: Externes System -> ExternalSystem_Config_ProCareManagement.Suchschlüssel
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystemValue
-- 2024-03-19T18:57:39.688Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-03-19 20:57:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623761
;

-- 2024-03-19T19:00:05.921Z
UPDATE AD_Element_Trl SET Name='Pro Care Management', PrintName='Pro Care Management',Updated=TO_TIMESTAMP('2024-03-19 21:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583027 AND AD_Language='de_CH'
;

-- 2024-03-19T19:00:05.924Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583027,'de_CH') 
;

-- 2024-03-19T19:00:08.307Z
UPDATE AD_Element_Trl SET Name='Pro Care Management', PrintName='Pro Care Management',Updated=TO_TIMESTAMP('2024-03-19 21:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583027 AND AD_Language='de_DE'
;

-- 2024-03-19T19:00:08.309Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583027,'de_DE') 
;

-- 2024-03-19T19:00:08.311Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583027,'de_DE') 
;

-- 2024-03-19T19:00:10.819Z
UPDATE AD_Element_Trl SET Name='Pro Care Management', PrintName='Pro Care Management',Updated=TO_TIMESTAMP('2024-03-19 21:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583027 AND AD_Language='en_US'
;

-- 2024-03-19T19:00:10.820Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583027,'en_US') 
;

-- 2024-03-19T19:00:13.140Z
UPDATE AD_Element_Trl SET Name='Pro Care Management', PrintName='Pro Care Management',Updated=TO_TIMESTAMP('2024-03-19 21:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583027 AND AD_Language='fr_CH'
;

-- 2024-03-19T19:00:13.142Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583027,'fr_CH') 
;

-- 2024-03-19T19:00:15.527Z
UPDATE AD_Element_Trl SET Name='Pro Care Management', PrintName='Pro Care Management',Updated=TO_TIMESTAMP('2024-03-19 21:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583027 AND AD_Language='nl_NL'
;

-- 2024-03-19T19:00:15.529Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583027,'nl_NL') 
;

-- 2024-03-19T19:06:34.357Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583028,0,TO_TIMESTAMP('2024-03-19 21:06:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Pro Care Management Einstellungen','Pro Care Management Einstellungen',TO_TIMESTAMP('2024-03-19 21:06:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:06:34.360Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583028 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-19T19:06:44.630Z
UPDATE AD_Element_Trl SET Name='Pro Care Management Settings', PrintName='Pro Care Management Settings',Updated=TO_TIMESTAMP('2024-03-19 21:06:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583028 AND AD_Language='en_US'
;

-- 2024-03-19T19:06:44.632Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583028,'en_US') 
;

-- Field: Externes System -> Pro Care Management -> Pro Care Management Einstellungen
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-19T19:07:04.328Z
UPDATE AD_Field SET AD_Name_ID=583028, Description=NULL, Help=NULL, Name='Pro Care Management Einstellungen',Updated=TO_TIMESTAMP('2024-03-19 21:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=726586
;

-- 2024-03-19T19:07:04.329Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583028) 
;

-- 2024-03-19T19:07:04.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726586
;

-- 2024-03-19T19:07:04.333Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726586)
;

-- 2024-03-19T19:08:39.135Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583029,0,TO_TIMESTAMP('2024-03-19 21:08:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Externe System Konfiguration PCM','Externe System Konfiguration PCM',TO_TIMESTAMP('2024-03-19 21:08:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:08:39.138Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583029 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Externe System Konfiguration PCM, InternalName=null
-- 2024-03-19T19:09:30.927Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583029,0,541790,TO_TIMESTAMP('2024-03-19 21:09:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','N','N','N','Y','Externe System Konfiguration PCM','N',TO_TIMESTAMP('2024-03-19 21:09:30','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-03-19T19:09:30.930Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541790 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-03-19T19:09:30.934Z
/* DDL */  select update_window_translation_from_ad_element(583029) 
;

-- 2024-03-19T19:09:30.939Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541790
;

-- 2024-03-19T19:09:30.940Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541790)
;

-- 2024-03-19T19:10:49.023Z
UPDATE AD_Element_Trl SET Name='External system config PCM', PrintName='External system config PCM',Updated=TO_TIMESTAMP('2024-03-19 21:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583029 AND AD_Language='en_US'
;

-- 2024-03-19T19:10:49.025Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583029,'en_US') 
;

-- Tab: Externe System Konfiguration PCM -> Pro Care Management
-- Table: ExternalSystem_Config_ProCareManagement
-- 2024-03-19T19:12:07.321Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583027,0,547486,542399,541790,'Y',TO_TIMESTAMP('2024-03-19 21:12:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_ProCareManagement','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Pro Care Management','N',10,0,TO_TIMESTAMP('2024-03-19 21:12:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:12:07.324Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547486 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-03-19T19:12:07.326Z
/* DDL */  select update_tab_translation_from_ad_element(583027) 
;

-- 2024-03-19T19:12:07.334Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547486)
;

-- Field: Externe System Konfiguration PCM -> Pro Care Management -> Mandant
-- Column: ExternalSystem_Config_ProCareManagement.AD_Client_ID
-- 2024-03-19T19:12:14.582Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587994,726589,0,547486,TO_TIMESTAMP('2024-03-19 21:12:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-03-19 21:12:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:12:14.584Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726589 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T19:12:14.588Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-03-19T19:12:14.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726589
;

-- 2024-03-19T19:12:14.678Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726589)
;

-- Field: Externe System Konfiguration PCM -> Pro Care Management -> Sektion
-- Column: ExternalSystem_Config_ProCareManagement.AD_Org_ID
-- 2024-03-19T19:12:14.793Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587995,726590,0,547486,TO_TIMESTAMP('2024-03-19 21:12:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-03-19 21:12:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:12:14.796Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726590 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T19:12:14.796Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-03-19T19:12:14.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726590
;

-- 2024-03-19T19:12:14.888Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726590)
;

-- Field: Externe System Konfiguration PCM -> Pro Care Management -> Aktiv
-- Column: ExternalSystem_Config_ProCareManagement.IsActive
-- 2024-03-19T19:12:14.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587998,726591,0,547486,TO_TIMESTAMP('2024-03-19 21:12:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-03-19 21:12:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:12:15.001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726591 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T19:12:15.003Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-03-19T19:12:15.083Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726591
;

-- 2024-03-19T19:12:15.084Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726591)
;

-- Field: Externe System Konfiguration PCM -> Pro Care Management -> Pro Care Management
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-19T19:12:15.194Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588001,726592,0,547486,TO_TIMESTAMP('2024-03-19 21:12:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Pro Care Management',TO_TIMESTAMP('2024-03-19 21:12:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:12:15.195Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726592 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T19:12:15.197Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583027) 
;

-- 2024-03-19T19:12:15.203Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726592
;

-- 2024-03-19T19:12:15.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726592)
;

-- Field: Externe System Konfiguration PCM -> Pro Care Management -> External System Config
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ID
-- 2024-03-19T19:12:15.319Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588002,726593,0,547486,TO_TIMESTAMP('2024-03-19 21:12:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2024-03-19 21:12:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:12:15.321Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T19:12:15.323Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2024-03-19T19:12:15.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726593
;

-- 2024-03-19T19:12:15.329Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726593)
;

-- Field: Externe System Konfiguration PCM -> Pro Care Management -> Suchschlüssel
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystemValue
-- 2024-03-19T19:12:15.436Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588003,726594,0,547486,TO_TIMESTAMP('2024-03-19 21:12:15','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2024-03-19 21:12:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:12:15.437Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726594 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-19T19:12:15.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788) 
;

-- 2024-03-19T19:12:15.445Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726594
;

-- 2024-03-19T19:12:15.446Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726594)
;

-- 2024-03-19T19:12:49.546Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547486,546065,TO_TIMESTAMP('2024-03-19 21:12:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-19 21:12:49','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-03-19T19:12:49.547Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546065 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2024-03-19T19:12:55.461Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547409,546065,TO_TIMESTAMP('2024-03-19 21:12:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-19 21:12:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:13:02.233Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547409,551698,TO_TIMESTAMP('2024-03-19 21:13:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2024-03-19 21:13:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Pro Care Management.External System Config
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ID
-- 2024-03-19T19:13:13.959Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726593,0,547486,623765,551698,'F',TO_TIMESTAMP('2024-03-19 21:13:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External System Config',10,0,0,TO_TIMESTAMP('2024-03-19 21:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Pro Care Management.Suchschlüssel
-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystemValue
-- 2024-03-19T19:13:20.902Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726594,0,547486,623766,551698,'F',TO_TIMESTAMP('2024-03-19 21:13:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',20,0,0,TO_TIMESTAMP('2024-03-19 21:13:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:13:26.457Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547410,546065,TO_TIMESTAMP('2024-03-19 21:13:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-03-19 21:13:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:13:33.552Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547410,551699,TO_TIMESTAMP('2024-03-19 21:13:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-03-19 21:13:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Pro Care Management.Aktiv
-- Column: ExternalSystem_Config_ProCareManagement.IsActive
-- 2024-03-19T19:13:42.309Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726591,0,547486,623767,551699,'F',TO_TIMESTAMP('2024-03-19 21:13:42','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-03-19 21:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-19T19:13:48.092Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547410,551700,TO_TIMESTAMP('2024-03-19 21:13:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2024-03-19 21:13:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Pro Care Management.Sektion
-- Column: ExternalSystem_Config_ProCareManagement.AD_Org_ID
-- 2024-03-19T19:13:55.717Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726590,0,547486,623768,551700,'F',TO_TIMESTAMP('2024-03-19 21:13:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-03-19 21:13:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Pro Care Management.Mandant
-- Column: ExternalSystem_Config_ProCareManagement.AD_Client_ID
-- 2024-03-19T19:14:02.602Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726589,0,547486,623769,551700,'F',TO_TIMESTAMP('2024-03-19 21:14:02','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-03-19 21:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Table: ExternalSystem_Config_ProCareManagement_LocalFile
-- 2024-03-20T11:01:20.047Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542401,'N',TO_TIMESTAMP('2024-03-20 13:01:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'Local file','NP','L','ExternalSystem_Config_ProCareManagement_LocalFile','DTI',TO_TIMESTAMP('2024-03-20 13:01:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:01:20.058Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542401 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-03-20T11:01:20.197Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556337,TO_TIMESTAMP('2024-03-20 13:01:20','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_ProCareManagement_LocalFile',1,'Y','N','Y','Y','ExternalSystem_Config_ProCareManagement_LocalFile','N',1000000,TO_TIMESTAMP('2024-03-20 13:01:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:01:20.216Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_PROCAREMANAGEMENT_LOCALFILE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.AD_Client_ID
-- 2024-03-20T11:01:43.487Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588024,102,0,19,542401,'AD_Client_ID',TO_TIMESTAMP('2024-03-20 13:01:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-03-20 13:01:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:43.491Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588024 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:43.535Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.AD_Org_ID
-- 2024-03-20T11:01:44.213Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588025,113,0,30,542401,'AD_Org_ID',TO_TIMESTAMP('2024-03-20 13:01:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-03-20 13:01:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:44.216Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588025 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:44.221Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Created
-- 2024-03-20T11:01:44.853Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588026,245,0,16,542401,'Created',TO_TIMESTAMP('2024-03-20 13:01:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-03-20 13:01:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:44.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588026 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:44.857Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.CreatedBy
-- 2024-03-20T11:01:45.460Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588027,246,0,18,110,542401,'CreatedBy',TO_TIMESTAMP('2024-03-20 13:01:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-03-20 13:01:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:45.463Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588027 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:45.466Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.IsActive
-- 2024-03-20T11:01:46.076Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588028,348,0,20,542401,'IsActive',TO_TIMESTAMP('2024-03-20 13:01:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-03-20 13:01:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:46.079Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588028 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:46.082Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Updated
-- 2024-03-20T11:01:46.643Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588029,607,0,16,542401,'Updated',TO_TIMESTAMP('2024-03-20 13:01:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-03-20 13:01:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:46.645Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588029 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:46.648Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.UpdatedBy
-- 2024-03-20T11:01:47.274Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588030,608,0,18,110,542401,'UpdatedBy',TO_TIMESTAMP('2024-03-20 13:01:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-03-20 13:01:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:47.276Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588030 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:47.279Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-03-20T11:01:47.894Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583038,0,'ExternalSystem_Config_ProCareManagement_LocalFile_ID',TO_TIMESTAMP('2024-03-20 13:01:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Local file','Local file',TO_TIMESTAMP('2024-03-20 13:01:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:01:47.897Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583038 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ExternalSystem_Config_ProCareManagement_LocalFile_ID
-- 2024-03-20T11:01:48.423Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588031,583038,0,13,542401,'ExternalSystem_Config_ProCareManagement_LocalFile_ID',TO_TIMESTAMP('2024-03-20 13:01:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Local file',0,0,TO_TIMESTAMP('2024-03-20 13:01:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:01:48.424Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588031 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:01:48.426Z
/* DDL */  select update_Column_Translation_From_AD_Element(583038) 
;

-- Column: ExternalSystem_Config_ProCareManagement.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-20T11:03:10.184Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-03-20 13:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588001
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-20T11:04:42.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588032,583027,0,19,542401,'ExternalSystem_Config_ProCareManagement_ID',TO_TIMESTAMP('2024-03-20 13:04:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Pro Care Management',0,0,TO_TIMESTAMP('2024-03-20 13:04:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:04:42.688Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588032 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:04:42.698Z
/* DDL */  select update_Column_Translation_From_AD_Element(583027) 
;

-- 2024-03-20T11:04:45.248Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalSystem_Config_ProCareManagement_ID NUMERIC(10) NOT NULL, ExternalSystem_Config_ProCareManagement_LocalFile_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystemConfigProCareManagement_ExternalSystemConfigProCareManag FOREIGN KEY (ExternalSystem_Config_ProCareManagement_ID) REFERENCES public.ExternalSystem_Config_ProCareManagement DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ExternalSystem_Config_ProCareManagement_LocalFile_Key PRIMARY KEY (ExternalSystem_Config_ProCareManagement_LocalFile_ID))
;

-- 2024-03-20T11:15:33.379Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583042,0,'LocalRootLocation',TO_TIMESTAMP('2024-03-20 13:15:33','YYYY-MM-DD HH24:MI:SS'),100,'Stammverzeichnis des lokalen Rechners.','de.metas.externalsystem','Y','Lokales Stammverzeichnis','Lokales Stammverzeichnis',TO_TIMESTAMP('2024-03-20 13:15:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:15:33.380Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583042 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-20T11:16:07.971Z
UPDATE AD_Element_Trl SET Description='Local machine root location', Name='Local Root Location', PrintName='Local Root Location',Updated=TO_TIMESTAMP('2024-03-20 13:16:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583042 AND AD_Language='en_US'
;

-- 2024-03-20T11:16:07.973Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583042,'en_US') 
;

-- 2024-03-20T11:17:45.508Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583043,0,'ProductFileNamePattern',TO_TIMESTAMP('2024-03-20 13:17:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Produkt Dateinamen-Muster','Produkt Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:17:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:17:45.510Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583043 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-20T11:17:57.755Z
UPDATE AD_Element_Trl SET Name='Product File Name Pattern', PrintName='Product File Name Pattern',Updated=TO_TIMESTAMP('2024-03-20 13:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583043 AND AD_Language='en_US'
;

-- 2024-03-20T11:17:57.757Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583043,'en_US') 
;

-- 2024-03-20T11:18:32.913Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583044,0,'BPartnerFileNamePattern',TO_TIMESTAMP('2024-03-20 13:18:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Geschäftspartner Dateinamen-Muster','Geschäftspartner Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:18:32.914Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583044 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-20T11:18:55.050Z
UPDATE AD_Element_Trl SET Name='Business Partner File Name Pattern', PrintName='Business Partner File Name Pattern',Updated=TO_TIMESTAMP('2024-03-20 13:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583044 AND AD_Language='en_US'
;

-- 2024-03-20T11:18:55.051Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583044,'en_US') 
;

-- 2024-03-20T11:19:22.458Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583045,0,'WarehouseFileNamePattern',TO_TIMESTAMP('2024-03-20 13:19:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lager Dateinamen-Muster','Lager Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:19:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:19:22.461Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583045 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-20T11:19:55.196Z
UPDATE AD_Element_Trl SET Name='Warehouse File Name Pattern', PrintName='Warehouse File Name Pattern',Updated=TO_TIMESTAMP('2024-03-20 13:19:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583045 AND AD_Language='en_US'
;

-- 2024-03-20T11:19:55.199Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583045,'en_US') 
;

-- 2024-03-20T11:20:20.645Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583046,0,'PurchaseOrderFileNamePattern',TO_TIMESTAMP('2024-03-20 13:20:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Bestellungen Dateinamen-Muster','Bestellungen Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:20:20.646Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583046 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-20T11:20:29.542Z
UPDATE AD_Element_Trl SET Name='Purchase Order File Name Pattern', PrintName='Purchase Order File Name Pattern',Updated=TO_TIMESTAMP('2024-03-20 13:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583046 AND AD_Language='en_US'
;

-- 2024-03-20T11:20:29.544Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583046,'en_US') 
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-20T11:21:10.353Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588034,581583,0,10,542401,'ProcessedDirectory',TO_TIMESTAMP('2024-03-20 13:21:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen Zielort sein)','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet-Verzeichnis',0,0,TO_TIMESTAMP('2024-03-20 13:21:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:21:10.357Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588034 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:21:10.362Z
/* DDL */  select update_Column_Translation_From_AD_Element(581583) 
;

-- 2024-03-20T11:21:11.122Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN ProcessedDirectory VARCHAR(255) NOT NULL')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-20T11:21:22.398Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588035,581584,0,10,542401,'ErroredDirectory',TO_TIMESTAMP('2024-03-20 13:21:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. (Der Pfad sollte relativ zum aktuellen Zielort sein)','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Fehler-Verzeichnis',0,0,TO_TIMESTAMP('2024-03-20 13:21:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:21:22.400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588035 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:21:22.404Z
/* DDL */  select update_Column_Translation_From_AD_Element(581584) 
;

-- 2024-03-20T11:21:23.136Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN ErroredDirectory VARCHAR(255) NOT NULL')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Frequency
-- 2024-03-20T11:22:06.155Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588036,1506,0,22,542401,'Frequency',TO_TIMESTAMP('2024-03-20 13:22:05','YYYY-MM-DD HH24:MI:SS'),100,'N','1000','Häufigkeit von Ereignissen','de.metas.externalsystem',0,22,'The frequency is used in conjunction with the frequency type in determining an event. Example: If the Frequency Type is Week and the Frequency is 2 - it is every two weeks.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Häufigkeit',0,0,TO_TIMESTAMP('2024-03-20 13:22:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:22:06.158Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588036 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:22:06.160Z
/* DDL */  select update_Column_Translation_From_AD_Element(1506) 
;

-- 2024-03-20T11:22:07.520Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN Frequency NUMERIC DEFAULT 1000')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.LocalRootLocation
-- 2024-03-20T11:22:39.852Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588037,583042,0,10,542401,'LocalRootLocation',TO_TIMESTAMP('2024-03-20 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Stammverzeichnis des lokalen Rechners.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lokales Stammverzeichnis',0,0,TO_TIMESTAMP('2024-03-20 13:22:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:22:39.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588037 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:22:39.858Z
/* DDL */  select update_Column_Translation_From_AD_Element(583042) 
;

-- 2024-03-20T11:22:41.371Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN LocalRootLocation VARCHAR(255) NOT NULL')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.BPartnerFileNamePattern
-- 2024-03-20T11:23:04.574Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588038,583044,0,10,542401,'BPartnerFileNamePattern',TO_TIMESTAMP('2024-03-20 13:23:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner Dateinamen-Muster',0,0,TO_TIMESTAMP('2024-03-20 13:23:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:23:04.575Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588038 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:23:04.578Z
/* DDL */  select update_Column_Translation_From_AD_Element(583044) 
;

-- 2024-03-20T11:23:05.232Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN BPartnerFileNamePattern VARCHAR(255)')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProductFileNamePattern
-- 2024-03-20T11:23:27.395Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588039,583043,0,10,542401,'ProductFileNamePattern',TO_TIMESTAMP('2024-03-20 13:23:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt Dateinamen-Muster',0,0,TO_TIMESTAMP('2024-03-20 13:23:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:23:27.397Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588039 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:23:27.399Z
/* DDL */  select update_Column_Translation_From_AD_Element(583043) 
;

-- 2024-03-20T11:23:28.339Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN ProductFileNamePattern VARCHAR(255)')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.WarehouseFileNamePattern
-- 2024-03-20T11:23:43.062Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588040,583045,0,10,542401,'WarehouseFileNamePattern',TO_TIMESTAMP('2024-03-20 13:23:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lager Dateinamen-Muster',0,0,TO_TIMESTAMP('2024-03-20 13:23:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:23:43.064Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588040 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:23:43.066Z
/* DDL */  select update_Column_Translation_From_AD_Element(583045) 
;

-- 2024-03-20T11:23:43.700Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN WarehouseFileNamePattern VARCHAR(255)')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.PurchaseOrderFileNamePattern
-- 2024-03-20T11:23:54.407Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588041,583046,0,10,542401,'PurchaseOrderFileNamePattern',TO_TIMESTAMP('2024-03-20 13:23:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bestellungen Dateinamen-Muster',0,0,TO_TIMESTAMP('2024-03-20 13:23:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-20T11:23:54.408Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588041 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-20T11:23:54.411Z
/* DDL */  select update_Column_Translation_From_AD_Element(583046) 
;

-- 2024-03-20T11:23:55.708Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN PurchaseOrderFileNamePattern VARCHAR(255)')
;

-- Tab: Externe System Konfiguration PCM -> Local file
-- Table: ExternalSystem_Config_ProCareManagement_LocalFile
-- 2024-03-20T11:25:10.309Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583038,0,547488,542401,541790,'Y',TO_TIMESTAMP('2024-03-20 13:25:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_ProCareManagement_LocalFile','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Local file','N',20,0,TO_TIMESTAMP('2024-03-20 13:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:10.312Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547488 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-03-20T11:25:10.315Z
/* DDL */  select update_tab_translation_from_ad_element(583038) 
;

-- 2024-03-20T11:25:10.343Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547488)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Mandant
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.AD_Client_ID
-- 2024-03-20T11:25:15.040Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588024,726612,0,547488,TO_TIMESTAMP('2024-03-20 13:25:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-03-20 13:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:15.044Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:15.049Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-03-20T11:25:15.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726612
;

-- 2024-03-20T11:25:15.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726612)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Sektion
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.AD_Org_ID
-- 2024-03-20T11:25:15.595Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588025,726613,0,547488,TO_TIMESTAMP('2024-03-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-03-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:15.597Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:15.599Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-03-20T11:25:15.925Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726613
;

-- 2024-03-20T11:25:15.925Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726613)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Aktiv
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.IsActive
-- 2024-03-20T11:25:16.056Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588028,726614,0,547488,TO_TIMESTAMP('2024-03-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-03-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:16.058Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:16.061Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-03-20T11:25:16.346Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726614
;

-- 2024-03-20T11:25:16.347Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726614)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Local file
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ExternalSystem_Config_ProCareManagement_LocalFile_ID
-- 2024-03-20T11:25:16.445Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588031,726615,0,547488,TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Local file',TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:16.446Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:16.448Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583038) 
;

-- 2024-03-20T11:25:16.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726615
;

-- 2024-03-20T11:25:16.455Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726615)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Pro Care Management
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ExternalSystem_Config_ProCareManagement_ID
-- 2024-03-20T11:25:16.577Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588032,726616,0,547488,TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Pro Care Management',TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:16.578Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726616 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:16.579Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583027) 
;

-- 2024-03-20T11:25:16.584Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726616
;

-- 2024-03-20T11:25:16.584Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726616)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-20T11:25:16.706Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588034,726617,0,547488,TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen Zielort sein)',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Verarbeitet-Verzeichnis',TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:16.707Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:16.708Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581583) 
;

-- 2024-03-20T11:25:16.718Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726617
;

-- 2024-03-20T11:25:16.718Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726617)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Fehler-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-20T11:25:16.818Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588035,726618,0,547488,TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. (Der Pfad sollte relativ zum aktuellen Zielort sein)',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Fehler-Verzeichnis',TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:16.819Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726618 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:16.820Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581584) 
;

-- 2024-03-20T11:25:16.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726618
;

-- 2024-03-20T11:25:16.824Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726618)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Häufigkeit
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Frequency
-- 2024-03-20T11:25:16.925Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588036,726619,0,547488,TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,'Häufigkeit von Ereignissen',22,'de.metas.externalsystem','The frequency is used in conjunction with the frequency type in determining an event. Example: If the Frequency Type is Week and the Frequency is 2 - it is every two weeks.','Y','N','N','N','N','N','N','N','Häufigkeit',TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:16.926Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726619 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:16.928Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1506) 
;

-- 2024-03-20T11:25:16.936Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726619
;

-- 2024-03-20T11:25:16.936Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726619)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Lokales Stammverzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.LocalRootLocation
-- 2024-03-20T11:25:17.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588037,726620,0,547488,TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100,'Stammverzeichnis des lokalen Rechners.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Lokales Stammverzeichnis',TO_TIMESTAMP('2024-03-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:17.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:17.032Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583042) 
;

-- 2024-03-20T11:25:17.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726620
;

-- 2024-03-20T11:25:17.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726620)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.BPartnerFileNamePattern
-- 2024-03-20T11:25:17.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588038,726621,0,547488,TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Geschäftspartner Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:17.139Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:17.141Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583044) 
;

-- 2024-03-20T11:25:17.147Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726621
;

-- 2024-03-20T11:25:17.147Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726621)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProductFileNamePattern
-- 2024-03-20T11:25:17.250Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588039,726622,0,547488,TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Produkt Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:17.252Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:17.254Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583043) 
;

-- 2024-03-20T11:25:17.261Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726622
;

-- 2024-03-20T11:25:17.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726622)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Lager Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.WarehouseFileNamePattern
-- 2024-03-20T11:25:17.379Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588040,726623,0,547488,TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Lager Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:17.381Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:17.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583045) 
;

-- 2024-03-20T11:25:17.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726623
;

-- 2024-03-20T11:25:17.386Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726623)
;

-- Field: Externe System Konfiguration PCM -> Local file -> Bestellungen Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.PurchaseOrderFileNamePattern
-- 2024-03-20T11:25:17.481Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588041,726624,0,547488,TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Bestellungen Dateinamen-Muster',TO_TIMESTAMP('2024-03-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:17.483Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-20T11:25:17.485Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583046) 
;

-- 2024-03-20T11:25:17.490Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726624
;

-- 2024-03-20T11:25:17.490Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726624)
;

-- Tab: Externe System Konfiguration PCM -> Local file
-- Table: ExternalSystem_Config_ProCareManagement_LocalFile
-- 2024-03-20T11:25:30.776Z
UPDATE AD_Tab SET AD_Column_ID=588032,Updated=TO_TIMESTAMP('2024-03-20 13:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547488
;

-- 2024-03-20T11:25:39.306Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547488,546067,TO_TIMESTAMP('2024-03-20 13:25:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-20 13:25:38','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-03-20T11:25:39.308Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546067 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2024-03-20T11:25:43.758Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547413,546067,TO_TIMESTAMP('2024-03-20 13:25:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-20 13:25:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:25:54.833Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547413,551705,TO_TIMESTAMP('2024-03-20 13:25:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2024-03-20 13:25:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-20T11:26:05.624Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726617,0,547488,623785,551705,'F',TO_TIMESTAMP('2024-03-20 13:26:05','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen Zielort sein)','Y','N','N','Y','N','N','N',0,'Verarbeitet-Verzeichnis',10,0,0,TO_TIMESTAMP('2024-03-20 13:26:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Fehler-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-20T11:26:12.099Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726618,0,547488,623786,551705,'F',TO_TIMESTAMP('2024-03-20 13:26:11','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. (Der Pfad sollte relativ zum aktuellen Zielort sein)','Y','N','N','Y','N','N','N',0,'Fehler-Verzeichnis',20,0,0,TO_TIMESTAMP('2024-03-20 13:26:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Häufigkeit
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Frequency
-- 2024-03-20T11:26:18.825Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726619,0,547488,623787,551705,'F',TO_TIMESTAMP('2024-03-20 13:26:18','YYYY-MM-DD HH24:MI:SS'),100,'Häufigkeit von Ereignissen','The frequency is used in conjunction with the frequency type in determining an event. Example: If the Frequency Type is Week and the Frequency is 2 - it is every two weeks.','Y','N','N','Y','N','N','N',0,'Häufigkeit',30,0,0,TO_TIMESTAMP('2024-03-20 13:26:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Lokales Stammverzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.LocalRootLocation
-- 2024-03-20T11:26:27.982Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726620,0,547488,623788,551705,'F',TO_TIMESTAMP('2024-03-20 13:26:27','YYYY-MM-DD HH24:MI:SS'),100,'Stammverzeichnis des lokalen Rechners.','Y','N','N','Y','N','N','N',0,'Lokales Stammverzeichnis',40,0,0,TO_TIMESTAMP('2024-03-20 13:26:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:26:33.266Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547414,546067,TO_TIMESTAMP('2024-03-20 13:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-03-20 13:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:26:41.478Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547414,551706,TO_TIMESTAMP('2024-03-20 13:26:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-03-20 13:26:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Aktiv
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.IsActive
-- 2024-03-20T11:26:48.030Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726614,0,547488,623789,551706,'F',TO_TIMESTAMP('2024-03-20 13:26:47','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-03-20 13:26:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:26:53.878Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547414,551707,TO_TIMESTAMP('2024-03-20 13:26:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2024-03-20 13:26:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Mandant
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.AD_Client_ID
-- 2024-03-20T11:27:03.529Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726612,0,547488,623790,551707,'F',TO_TIMESTAMP('2024-03-20 13:27:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2024-03-20 13:27:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Sektion
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.AD_Org_ID
-- 2024-03-20T11:27:09.414Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726613,0,547488,623791,551707,'F',TO_TIMESTAMP('2024-03-20 13:27:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2024-03-20 13:27:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:28:09.957Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547413,551708,TO_TIMESTAMP('2024-03-20 13:28:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','partner pattern',20,TO_TIMESTAMP('2024-03-20 13:28:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.BPartnerFileNamePattern
-- 2024-03-20T11:28:17.311Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726621,0,547488,623792,551708,'F',TO_TIMESTAMP('2024-03-20 13:28:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geschäftspartner Dateinamen-Muster',10,0,0,TO_TIMESTAMP('2024-03-20 13:28:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:28:29.526Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547413,551709,TO_TIMESTAMP('2024-03-20 13:28:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','product pattern',30,TO_TIMESTAMP('2024-03-20 13:28:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProductFileNamePattern
-- 2024-03-20T11:28:38.502Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726622,0,547488,623793,551709,'F',TO_TIMESTAMP('2024-03-20 13:28:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Produkt Dateinamen-Muster',10,0,0,TO_TIMESTAMP('2024-03-20 13:28:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:28:45.625Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547413,551710,TO_TIMESTAMP('2024-03-20 13:28:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','warehouse pattern',40,TO_TIMESTAMP('2024-03-20 13:28:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Lager Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.WarehouseFileNamePattern
-- 2024-03-20T11:28:54.206Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726623,0,547488,623794,551710,'F',TO_TIMESTAMP('2024-03-20 13:28:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lager Dateinamen-Muster',10,0,0,TO_TIMESTAMP('2024-03-20 13:28:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:29:00.064Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547413,551711,TO_TIMESTAMP('2024-03-20 13:28:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','order pattern',50,TO_TIMESTAMP('2024-03-20 13:28:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Bestellungen Dateinamen-Muster
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.PurchaseOrderFileNamePattern
-- 2024-03-20T11:29:09.518Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726624,0,547488,623795,551711,'F',TO_TIMESTAMP('2024-03-20 13:29:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bestellungen Dateinamen-Muster',10,0,0,TO_TIMESTAMP('2024-03-20 13:29:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Externe System Konfiguration PCM -> Local file
-- Table: ExternalSystem_Config_ProCareManagement_LocalFile
-- 2024-03-20T11:29:35.630Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2024-03-20 13:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547488
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Lokales Stammverzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.LocalRootLocation
-- 2024-03-20T11:31:11.680Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-03-20 13:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623788
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-20T11:31:11.686Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-03-20 13:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623785
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Fehler-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-20T11:31:11.693Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-03-20 13:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623786
;

-- UI Element: Externe System Konfiguration PCM -> Local file.Häufigkeit
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Frequency
-- 2024-03-20T11:31:11.699Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-03-20 13:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623787
;

-- 2024-03-20T11:35:41.813Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583029,542142,0,541790,TO_TIMESTAMP('2024-03-20 13:35:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','541790','Y','N','N','N','N','Externe System Konfiguration PCM',TO_TIMESTAMP('2024-03-20 13:35:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T11:35:41.816Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542142 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-03-20T11:35:41.821Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542142, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542142)
;

-- 2024-03-20T11:35:41.842Z
/* DDL */  select update_menu_translation_from_ad_element(583029) 
;

-- Reordering children of `System Rules`
-- Node name: `System (AD_System)`
-- 2024-03-20T11:35:50.122Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=334 AND AD_Tree_ID=10
;

-- Node name: `System Registration (AD_Registration)`
-- 2024-03-20T11:35:50.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=498 AND AD_Tree_ID=10
;

-- Node name: `Language Setup`
-- 2024-03-20T11:35:50.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=224 AND AD_Tree_ID=10
;

-- Node name: `Synchronize Doc Translation (org.compiere.process.TranslationDocSync)`
-- 2024-03-20T11:35:50.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=514 AND AD_Tree_ID=10
;

-- Node name: `Translation Import/Export (de.metas.i18n.VTranslationImpExpDialog)`
-- 2024-03-20T11:35:50.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=336 AND AD_Tree_ID=10
;

-- Node name: `System Translation Check (AD_Language)`
-- 2024-03-20T11:35:50.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=341 AND AD_Tree_ID=10
;

-- Node name: `Tree (AD_Tree)`
-- 2024-03-20T11:35:50.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=170 AND AD_Tree_ID=10
;

-- Node name: `Tree Maintenance (org.compiere.apps.form.VTreeMaintenance)`
-- 2024-03-20T11:35:50.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=465 AND AD_Tree_ID=10
;

-- Node name: `Task (AD_Task)`
-- 2024-03-20T11:35:50.127Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=101 AND AD_Tree_ID=10
;

-- Node name: `System Color (AD_Color)`
-- 2024-03-20T11:35:50.128Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=294 AND AD_Tree_ID=10
;

-- Node name: `Replication Setup`
-- 2024-03-20T11:35:50.128Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=395 AND AD_Tree_ID=10
;

-- Node name: `System Image (AD_Image)`
-- 2024-03-20T11:35:50.129Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=296 AND AD_Tree_ID=10
;

-- Node name: `Error Message (AD_Error)`
-- 2024-03-20T11:35:50.130Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=221 AND AD_Tree_ID=10
;

-- Node name: `Notification Group (AD_NotificationGroup)`
-- 2024-03-20T11:35:50.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541069 AND AD_Tree_ID=10
;

-- Node name: `Notice (AD_Note)`
-- 2024-03-20T11:35:50.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=233 AND AD_Tree_ID=10
;

-- Node name: `Find (indirect use) (AD_Find)`
-- 2024-03-20T11:35:50.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=290 AND AD_Tree_ID=10
;

-- Node name: `Country Region and City (C_Country)`
-- 2024-03-20T11:35:50.133Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=109 AND AD_Tree_ID=10
;

-- Node name: `Country area (C_CountryArea)`
-- 2024-03-20T11:35:50.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540375 AND AD_Tree_ID=10
;

-- Node name: `System Configurator (AD_SysConfig)`
-- 2024-03-20T11:35:50.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50008 AND AD_Tree_ID=10
;

-- Node name: `Externe System Konfiguration PCM`
-- 2024-03-20T11:35:50.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542142 AND AD_Tree_ID=10
;

-- Reordering children of `System`
-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2024-03-20T11:36:16.688Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2024-03-20T11:36:16.689Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2024-03-20T11:36:16.689Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `Externe System Konfiguration PCM`
-- 2024-03-20T11:36:16.690Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542142 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2024-03-20T11:36:16.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2024-03-20T11:36:16.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2024-03-20T11:36:16.692Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2024-03-20T11:36:16.693Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2024-03-20T11:36:16.693Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2024-03-20T11:36:16.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2024-03-20T11:36:16.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2024-03-20T11:36:16.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2024-03-20T11:36:16.696Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2024-03-20T11:36:16.697Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2024-03-20T11:36:16.697Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2024-03-20T11:36:16.698Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2024-03-20T11:36:16.698Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2024-03-20T11:36:16.699Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2024-03-20T11:36:16.699Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2024-03-20T11:36:16.700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2024-03-20T11:36:16.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2024-03-20T11:36:16.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2024-03-20T11:36:16.702Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2024-03-20T11:36:16.703Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2024-03-20T11:36:16.703Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2024-03-20T11:36:16.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2024-03-20T11:36:16.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2024-03-20T11:36:16.705Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2024-03-20T11:36:16.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2024-03-20T11:36:16.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2024-03-20T11:36:16.707Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2024-03-20T11:36:16.707Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2024-03-20T11:36:16.708Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2024-03-20T11:36:16.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2024-03-20T11:36:16.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2024-03-20T11:36:16.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2024-03-20T11:36:16.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2024-03-20T11:36:16.711Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2024-03-20T11:36:16.711Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2024-03-20T11:36:16.712Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2024-03-20T11:36:16.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2024-03-20T11:36:16.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2024-03-20T11:36:16.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2024-03-20T11:36:16.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2024-03-20T11:36:16.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2024-03-20T11:36:16.716Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2024-03-20T11:36:16.717Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2024-03-20T11:36:16.717Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2024-03-20T11:36:16.718Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2024-03-20T11:36:16.719Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2024-03-20T11:36:16.720Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2024-03-20T11:36:16.721Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2024-03-20T11:36:16.722Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2024-03-20T11:36:16.723Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2024-03-20T11:36:16.723Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2024-03-20T11:36:16.724Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2024-03-20T11:36:16.725Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2024-03-20T11:36:16.726Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2024-03-20T11:36:16.727Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2024-03-20T11:36:16.728Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2024-03-20T11:36:16.729Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2024-03-20T11:36:16.730Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2024-03-20T11:36:16.731Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2024-03-20T11:36:16.732Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2024-03-20T11:36:16.732Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-03-20T11:36:16.733Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-03-20T11:36:16.734Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-03-20T11:36:16.735Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2024-03-20T11:36:16.736Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2024-03-20T11:36:16.736Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2024-03-20T11:36:16.737Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2024-03-20T11:36:16.738Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2024-03-20T11:36:16.739Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2024-03-20T11:36:16.740Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2024-03-20T11:36:16.740Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2024-03-20T11:36:16.741Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2024-03-20T11:36:16.741Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2024-03-20T11:36:16.742Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2024-03-20T11:36:16.742Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2024-03-20T11:36:16.743Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- 2024-03-20T15:28:38.045Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585362,'Y','de.metas.externalsystem.process.InvokePCMAction','N',TO_TIMESTAMP('2024-03-20 17:28:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Pro Care Management Aufrufen','json','N','N','xls','Java',TO_TIMESTAMP('2024-03-20 17:28:37','YYYY-MM-DD HH24:MI:SS'),100,'Call_external_system_ProCareManagement')
;

-- 2024-03-20T15:28:38.052Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585362 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2024-03-20T15:28:56.452Z
UPDATE AD_Process_Trl SET Name='Call Pro Care Management',Updated=TO_TIMESTAMP('2024-03-20 17:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585362
;

-- 2024-03-20T15:30:14.023Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541858,TO_TIMESTAMP('2024-03-20 17:30:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','External_Request Pro Care Management',TO_TIMESTAMP('2024-03-20 17:30:13','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-03-20T15:30:14.026Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541858 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2024-03-20T15:35:43.213Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543640,541858,TO_TIMESTAMP('2024-03-20 17:35:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Produkt-Import - Start',TO_TIMESTAMP('2024-03-20 17:35:43','YYYY-MM-DD HH24:MI:SS'),100,'startProductSyncLocalFile','Lokale Datei - Produkt-Import - Start')
;

-- 2024-03-20T15:35:43.215Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543640 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-20T15:36:09.197Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Product Import - Start',Updated=TO_TIMESTAMP('2024-03-20 17:36:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543640
;

-- 2024-03-20T15:36:39.554Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543641,541858,TO_TIMESTAMP('2024-03-20 17:36:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Produkt-Import - Stop',TO_TIMESTAMP('2024-03-20 17:36:39','YYYY-MM-DD HH24:MI:SS'),100,'stopProductSyncLocalFile','Lokale Datei - Produkt-Import - Stop')
;

-- 2024-03-20T15:36:39.556Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543641 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-20T15:36:58.551Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Product Import - Start',Updated=TO_TIMESTAMP('2024-03-20 17:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543641
;

-- 2024-03-20T15:37:02.725Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Product Import - Stop',Updated=TO_TIMESTAMP('2024-03-20 17:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543641
;

-- 2024-03-20T15:39:31.231Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578757,0,585362,542791,17,541858,'External_Request',TO_TIMESTAMP('2024-03-20 17:39:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference',50,'Y','N','Y','N','Y','N','Befehl',10,TO_TIMESTAMP('2024-03-20 17:39:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T15:39:31.235Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542791 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-03-20T15:40:15.825Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585362,541576,541466,TO_TIMESTAMP('2024-03-20 17:40:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2024-03-20 17:40:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2024-03-20T15:40:38.314Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585362,542399,541467,TO_TIMESTAMP('2024-03-20 17:40:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2024-03-20 17:40:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Frequency
-- 2024-03-20T16:07:45.096Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2024-03-20 18:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588036
;

-- 2024-03-20T16:07:48.662Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement_localfile','Frequency','NUMERIC(10)',null,'0')
;

-- 2024-03-20T16:07:49.163Z
UPDATE ExternalSystem_Config_ProCareManagement_LocalFile SET Frequency=0 WHERE Frequency IS NULL
;

-- 2024-03-20T16:07:49.236Z
INSERT INTO t_alter_column values('externalsystem_config_procaremanagement_localfile','Frequency',null,'NOT NULL',null)
;

-- 2024-03-20T17:35:14.441Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543642,541858,TO_TIMESTAMP('2024-03-20 19:35:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Geschäftspartner-Import - Start',TO_TIMESTAMP('2024-03-20 19:35:14','YYYY-MM-DD HH24:MI:SS'),100,'startBPartnerSyncLocalFile','Lokale Datei - Geschäftspartner-Import - Start')
;

-- 2024-03-20T17:35:14.449Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543642 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-20T17:35:48.632Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Business Partners Import - Start',Updated=TO_TIMESTAMP('2024-03-20 19:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543642
;

-- 2024-03-20T17:36:20.396Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543643,541858,TO_TIMESTAMP('2024-03-20 19:36:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Geschäftspartner-Import - Stop',TO_TIMESTAMP('2024-03-20 19:36:20','YYYY-MM-DD HH24:MI:SS'),100,'stopBPartnerSyncLocalFile','Lokale Datei - Geschäftspartner-Import - Stop')
;

-- 2024-03-20T17:36:20.397Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543643 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-20T17:36:56.424Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Business Partners Import - Stop',Updated=TO_TIMESTAMP('2024-03-20 19:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543643
;

-- 2024-03-20T17:37:57.499Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543644,541858,TO_TIMESTAMP('2024-03-20 19:37:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Lager-Import - Start',TO_TIMESTAMP('2024-03-20 19:37:57','YYYY-MM-DD HH24:MI:SS'),100,'startWarehouseLocalFile','Lokale Datei - Lager-Import - Start')
;

-- 2024-03-20T17:37:57.504Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543644 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-20T17:38:11.004Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Warehouse Import - Start',Updated=TO_TIMESTAMP('2024-03-20 19:38:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543644
;

-- 2024-03-20T17:38:31.053Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543645,541858,TO_TIMESTAMP('2024-03-20 19:38:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Lager-Import - Stop',TO_TIMESTAMP('2024-03-20 19:38:30','YYYY-MM-DD HH24:MI:SS'),100,'stopWarehouseLocalFile','Lokale Datei - Lager-Import - Stop')
;

-- 2024-03-20T17:38:31.054Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543645 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-20T17:38:48.725Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Warehouse Import - Stop',Updated=TO_TIMESTAMP('2024-03-20 19:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543645
;

-- 2024-03-20T17:40:21.750Z
UPDATE AD_Ref_List SET Value='stopWarehouseSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-20 19:40:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543645
;

-- 2024-03-20T17:40:28.631Z
UPDATE AD_Ref_List SET Value='startWarehouseSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-20 19:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543644
;

-- 2024-03-20T17:41:01.587Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543646,541858,TO_TIMESTAMP('2024-03-20 19:41:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Bestellungen-Import - Start',TO_TIMESTAMP('2024-03-20 19:41:01','YYYY-MM-DD HH24:MI:SS'),100,'startPurchaseOrderSyncLocalFile','Lokale Datei - Bestellungen-Import - Start')
;

-- 2024-03-20T17:41:01.589Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543646 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;


-- 2024-03-20T17:42:30.301Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Purchase Order Import - Start',Updated=TO_TIMESTAMP('2024-03-20 19:42:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543646
;

-- 2024-03-20T17:42:55.458Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543647,541858,TO_TIMESTAMP('2024-03-20 19:42:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Lokale Datei - Bestellungen-Import - Stop',TO_TIMESTAMP('2024-03-20 19:42:55','YYYY-MM-DD HH24:MI:SS'),100,'stopPurchaseOrderSyncLocalFile','Lokale Datei - Bestellungen-Import - Stop')
;

-- 2024-03-20T17:42:55.460Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543647 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-20T17:43:07.181Z
UPDATE AD_Ref_List_Trl SET Name='Local File - Purchase Order Import - Stop',Updated=TO_TIMESTAMP('2024-03-20 19:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543647
;

-- 2024-03-20T17:50:54.863Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540790,0,542401,TO_TIMESTAMP('2024-03-20 19:50:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Y','idx_externalsystempcm_local_file_unique_parent_id','N',TO_TIMESTAMP('2024-03-20 19:50:54','YYYY-MM-DD HH24:MI:SS'),100,'isactive=''Y''')
;

-- 2024-03-20T17:50:54.872Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540790 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-03-20T17:51:16.010Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588032,541399,540790,0,TO_TIMESTAMP('2024-03-20 19:51:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2024-03-20 19:51:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-20T17:51:20.466Z
CREATE UNIQUE INDEX idx_externalsystempcm_local_file_unique_parent_id ON ExternalSystem_Config_ProCareManagement_LocalFile (ExternalSystem_Config_ProCareManagement_ID) WHERE isactive='Y'
;

-- 2024-03-20T18:07:44.026Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545383,0,TO_TIMESTAMP('2024-03-20 20:07:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Details der doppelten Dateisuche !','E',TO_TIMESTAMP('2024-03-20 20:07:43','YYYY-MM-DD HH24:MI:SS'),100,'ExternalSystemConfigPCMDuplicateFileLookupDetails')
;

-- 2024-03-20T18:07:44.029Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545383 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2024-03-20T18:07:50.896Z
UPDATE AD_Message_Trl SET MsgText='Duplicate file lookup details !',Updated=TO_TIMESTAMP('2024-03-20 20:07:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545383
;

-- 2024-03-21T15:22:20.846Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-03-21 17:22:20','YYYY-MM-DD HH24:MI:SS'),100,540013,'Y','Local-File Sync-BPartners','PCM',TO_TIMESTAMP('2024-03-21 17:22:20','YYYY-MM-DD HH24:MI:SS'),100,'LocalFileSyncBPartnersPCM')
;

-- 2024-03-21T15:22:45.399Z
UPDATE ExternalSystem_Service SET Description='Local-File Sync-BPartners',Updated=TO_TIMESTAMP('2024-03-21 17:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540013
;

-- 2024-03-21T15:22:56.085Z
UPDATE ExternalSystem_Service SET EnableCommand='startBPartnerSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540013
;

-- 2024-03-21T15:23:05.881Z
UPDATE ExternalSystem_Service SET DisableCommand='stopBPartnerSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540013
;

-- 2024-03-21T15:25:04.244Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-03-21 17:25:04','YYYY-MM-DD HH24:MI:SS'),100,540014,'Y','Local-File Sync-Products','PCM',TO_TIMESTAMP('2024-03-21 17:25:04','YYYY-MM-DD HH24:MI:SS'),100,'LocalFileSyncProductsPCM')
;

-- 2024-03-21T15:25:05.612Z
UPDATE ExternalSystem_Service SET Description='Local-File Sync-Products',Updated=TO_TIMESTAMP('2024-03-21 17:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540014
;

-- 2024-03-21T15:25:12.663Z
UPDATE ExternalSystem_Service SET EnableCommand='startProductSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540014
;

-- 2024-03-21T15:25:16.512Z
UPDATE ExternalSystem_Service SET DisableCommand='stopProductSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540014
;

-- 2024-03-21T15:25:43.584Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-03-21 17:25:43','YYYY-MM-DD HH24:MI:SS'),100,540015,'Y','Local-File Sync-Warehouses','PCM',TO_TIMESTAMP('2024-03-21 17:25:43','YYYY-MM-DD HH24:MI:SS'),100,'LocalFileSyncWarehousesPCM')
;

-- 2024-03-21T15:25:54.334Z
UPDATE ExternalSystem_Service SET Description='Local-File Sync-Warehouses',Updated=TO_TIMESTAMP('2024-03-21 17:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540015
;

-- 2024-03-21T15:25:57.234Z
UPDATE ExternalSystem_Service SET EnableCommand='startWarehouseSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:25:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540015
;

-- 2024-03-21T15:25:59.937Z
UPDATE ExternalSystem_Service SET DisableCommand='stopWarehouseSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540015
;

-- 2024-03-21T15:26:25.730Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2024-03-21 17:26:25','YYYY-MM-DD HH24:MI:SS'),100,540016,'Y','Local-File Sync-PurchaseOrders','PCM',TO_TIMESTAMP('2024-03-21 17:26:25','YYYY-MM-DD HH24:MI:SS'),100,'LocalFileSyncPurchaseOrdersPCM')
;

-- 2024-03-21T15:26:27.790Z
UPDATE ExternalSystem_Service SET Description='Local-File Sync-PurchaseOrders',Updated=TO_TIMESTAMP('2024-03-21 17:26:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540016
;

-- 2024-03-21T15:26:33.572Z
UPDATE ExternalSystem_Service SET EnableCommand='startPurchaseOrderSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:26:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540016
;

-- 2024-03-21T15:26:36.370Z
UPDATE ExternalSystem_Service SET DisableCommand='stopPurchaseOrderSyncLocalFile',Updated=TO_TIMESTAMP('2024-03-21 17:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540016
;

-- 2024-03-21T15:34:52.057Z
INSERT INTO ExternalSystem_Config_ProCareManagement (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Config_ID,ExternalSystem_Config_ProCareManagement_ID,ExternalSystemValue,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-03-21 17:34:52','YYYY-MM-DD HH24:MI:SS'),100,540018,540000,'Pro Care Management-Konfig','Y',TO_TIMESTAMP('2024-03-21 17:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T15:35:36.190Z
INSERT INTO ExternalSystem_Config_ProCareManagement_LocalFile (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ErroredDirectory,ExternalSystem_Config_ProCareManagement_ID,ExternalSystem_Config_ProCareManagement_LocalFile_ID,Frequency,IsActive,LocalRootLocation,ProcessedDirectory,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-03-21 17:35:36','YYYY-MM-DD HH24:MI:SS'),100,'error',540000,540000,1000,'Y','Lokales-Stammverzeichnis','move',TO_TIMESTAMP('2024-03-21 17:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T15:58:20.262Z
UPDATE AD_Table_Trl SET Name='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 17:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542401
;

-- 2024-03-21T15:58:22.487Z
UPDATE AD_Table_Trl SET Name='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 17:58:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=542401
;

-- 2024-03-21T15:58:26.763Z
UPDATE AD_Table_Trl SET Name='Local File',Updated=TO_TIMESTAMP('2024-03-21 17:58:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542401
;

-- 2024-03-21T15:58:29.409Z
UPDATE AD_Table_Trl SET Name='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 17:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542401
;

-- 2024-03-21T15:58:32.002Z
UPDATE AD_Table_Trl SET Name='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 17:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542401
;

-- 2024-03-21T15:59:54.924Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583048,0,TO_TIMESTAMP('2024-03-21 17:59:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Abfragefrequenz in Millisekunden','Abfragefrequenz in Millisekunden',TO_TIMESTAMP('2024-03-21 17:59:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T15:59:54.927Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583048 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-21T16:00:10.738Z
UPDATE AD_Element_Trl SET Name='Frequency In Milliseconds', PrintName='Frequency In Milliseconds',Updated=TO_TIMESTAMP('2024-03-21 18:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583048 AND AD_Language='en_US'
;

-- 2024-03-21T16:00:10.782Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583048,'en_US') 
;

-- Field: Externe System Konfiguration PCM -> Local file -> Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.Frequency
-- 2024-03-21T16:00:25.384Z
UPDATE AD_Field SET AD_Name_ID=583048, Description=NULL, Help=NULL, Name='Abfragefrequenz in Millisekunden',Updated=TO_TIMESTAMP('2024-03-21 18:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=726619
;

-- 2024-03-21T16:00:25.387Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583048) 
;

-- 2024-03-21T16:00:25.404Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726619
;

-- 2024-03-21T16:00:25.409Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726619)
;

-- 2024-03-21T16:01:14.215Z
UPDATE AD_Element_Trl SET Name='Lokale Datei', PrintName='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 18:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583038 AND AD_Language='nl_NL'
;

-- 2024-03-21T16:01:14.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583038,'nl_NL') 
;

-- 2024-03-21T16:01:17.894Z
UPDATE AD_Element_Trl SET Name='Lokale Datei', PrintName='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 18:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583038 AND AD_Language='fr_CH'
;

-- 2024-03-21T16:01:17.895Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583038,'fr_CH') 
;

-- 2024-03-21T16:01:25.297Z
UPDATE AD_Element_Trl SET Name='Local File', PrintName='Local File',Updated=TO_TIMESTAMP('2024-03-21 18:01:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583038 AND AD_Language='en_US'
;

-- 2024-03-21T16:01:25.299Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583038,'en_US') 
;

-- 2024-03-21T16:01:27.525Z
UPDATE AD_Element_Trl SET Name='Lokale Datei', PrintName='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 18:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583038 AND AD_Language='de_DE'
;

-- 2024-03-21T16:01:27.526Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583038,'de_DE') 
;

-- 2024-03-21T16:01:27.527Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583038,'de_DE') 
;

-- 2024-03-21T16:01:29.990Z
UPDATE AD_Element_Trl SET Name='Lokale Datei', PrintName='Lokale Datei',Updated=TO_TIMESTAMP('2024-03-21 18:01:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583038 AND AD_Language='de_CH'
;

-- 2024-03-21T16:01:29.991Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583038,'de_CH') 
;

-- 2024-03-21T19:37:02.272Z
UPDATE ExternalSystem_Config SET Name='ProCareManagement',Updated=TO_TIMESTAMP('2024-03-21 21:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=540018
;

-- 2024-03-21T19:59:02.559Z
UPDATE ExternalSystem_Config_ProCareManagement SET ExternalSystemValue='ProCareManagement-Konfig',Updated=TO_TIMESTAMP('2024-03-21 21:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ProCareManagement_ID=540000
;
