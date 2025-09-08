-- Table: ExternalSystem_Config_PrintingClient
-- Table: ExternalSystem_Config_PrintingClient
-- 2023-07-19T10:36:21.105Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542353,'N',TO_TIMESTAMP('2023-07-19 12:36:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'ExternalSystem_Config_PrintingClient','NP','L','ExternalSystem_Config_PrintingClient','DTI',TO_TIMESTAMP('2023-07-19 12:36:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:36:21.107Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542353 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-07-19T10:36:21.391Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556288,TO_TIMESTAMP('2023-07-19 12:36:21','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_PrintingClient',1,'Y','N','Y','Y','ExternalSystem_Config_PrintingClient','N',1000000,TO_TIMESTAMP('2023-07-19 12:36:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T10:36:21.407Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_PRINTINGCLIENT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ExternalSystem_Config_PrintingClient.AD_Client_ID
-- Column: ExternalSystem_Config_PrintingClient.AD_Client_ID
-- 2023-07-19T10:37:16.565Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587140,102,0,19,542353,'AD_Client_ID',TO_TIMESTAMP('2023-07-19 12:37:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-07-19 12:37:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:16.569Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587140 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:16.578Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ExternalSystem_Config_PrintingClient.AD_Org_ID
-- Column: ExternalSystem_Config_PrintingClient.AD_Org_ID
-- 2023-07-19T10:37:17.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587141,113,0,30,542353,'AD_Org_ID',TO_TIMESTAMP('2023-07-19 12:37:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-07-19 12:37:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:17.474Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587141 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:17.477Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ExternalSystem_Config_PrintingClient.Created
-- Column: ExternalSystem_Config_PrintingClient.Created
-- 2023-07-19T10:37:18.807Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587142,245,0,16,542353,'Created',TO_TIMESTAMP('2023-07-19 12:37:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-07-19 12:37:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:18.808Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587142 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:18.811Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ExternalSystem_Config_PrintingClient.CreatedBy
-- Column: ExternalSystem_Config_PrintingClient.CreatedBy
-- 2023-07-19T10:37:19.457Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587143,246,0,18,110,542353,'CreatedBy',TO_TIMESTAMP('2023-07-19 12:37:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-07-19 12:37:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:19.458Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587143 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:19.460Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ExternalSystem_Config_PrintingClient.IsActive
-- Column: ExternalSystem_Config_PrintingClient.IsActive
-- 2023-07-19T10:37:20.086Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587144,348,0,20,542353,'IsActive',TO_TIMESTAMP('2023-07-19 12:37:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-07-19 12:37:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:20.087Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587144 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:20.089Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ExternalSystem_Config_PrintingClient.Updated
-- Column: ExternalSystem_Config_PrintingClient.Updated
-- 2023-07-19T10:37:20.709Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587145,607,0,16,542353,'Updated',TO_TIMESTAMP('2023-07-19 12:37:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-07-19 12:37:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:20.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587145 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:20.713Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ExternalSystem_Config_PrintingClient.UpdatedBy
-- Column: ExternalSystem_Config_PrintingClient.UpdatedBy
-- 2023-07-19T10:37:21.287Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587146,608,0,18,110,542353,'UpdatedBy',TO_TIMESTAMP('2023-07-19 12:37:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-07-19 12:37:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:21.288Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587146 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:21.290Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-07-19T10:37:21.750Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582566,0,'ExternalSystem_Config_PrintingClient_ID',TO_TIMESTAMP('2023-07-19 12:37:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_Config_PrintingClient','ExternalSystem_Config_PrintingClient',TO_TIMESTAMP('2023-07-19 12:37:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T10:37:21.754Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582566 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_PrintingClient_ID
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_PrintingClient_ID
-- 2023-07-19T10:37:22.268Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587147,582566,0,13,542353,'ExternalSystem_Config_PrintingClient_ID',TO_TIMESTAMP('2023-07-19 12:37:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','ExternalSystem_Config_PrintingClient',0,0,TO_TIMESTAMP('2023-07-19 12:37:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:37:22.270Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:37:22.272Z
/* DDL */  select update_Column_Translation_From_AD_Element(582566) 
;

-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_ID
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_ID
-- 2023-07-19T10:38:51.196Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587148,578728,0,19,542353,'ExternalSystem_Config_ID',TO_TIMESTAMP('2023-07-19 12:38:50','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2023-07-19 12:38:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:38:51.198Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:38:51.201Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- Element: Target_Directory
-- 2023-07-19T13:15:45.142Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zielordner', PrintName='Zielordner',Updated=TO_TIMESTAMP('2023-07-19 15:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=50027 AND AD_Language='de_CH'
;

-- 2023-07-19T13:15:45.148Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(50027,'de_CH') 
;

-- Element: Target_Directory
-- 2023-07-19T13:15:55.992Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zielordner', PrintName='Zielordner',Updated=TO_TIMESTAMP('2023-07-19 15:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=50027 AND AD_Language='de_DE'
;

-- 2023-07-19T13:15:55.995Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(50027,'de_DE') 
;

-- 2023-07-19T13:15:56.003Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(50027,'de_DE') 
;

-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- 2023-07-19T13:18:07.560Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587150,50027,0,10,542353,'Target_Directory',TO_TIMESTAMP('2023-07-19 15:18:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,120,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zielordner',0,0,TO_TIMESTAMP('2023-07-19 15:18:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T13:18:07.563Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587150 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T13:18:07.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(50027) 
;

-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_ID
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_ID
-- 2023-07-19T13:19:04.408Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-07-19 15:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587148
;

-- 2023-07-19T13:20:34.295Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_PrintingClient (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalSystem_Config_ID NUMERIC(10) NOT NULL, ExternalSystem_Config_PrintingClient_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Target_Directory VARCHAR(120) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystemConfig_ExternalSystemConfigPrintingClient FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ExternalSystem_Config_PrintingClient_Key PRIMARY KEY (ExternalSystem_Config_PrintingClient_ID))
;

-- Tab: Externes System -> ExternalSystem_Config_PrintingClient
-- Table: ExternalSystem_Config_PrintingClient
-- Tab: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient
-- Table: ExternalSystem_Config_PrintingClient
-- 2023-07-19T13:26:24.049Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582566,0,547026,542353,541024,'Y',TO_TIMESTAMP('2023-07-19 15:26:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_PrintingClient','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_PrintingClient',572724,'N',90,1,TO_TIMESTAMP('2023-07-19 15:26:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T13:26:24.053Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547026 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-07-19T13:26:24.057Z
/* DDL */  select update_tab_translation_from_ad_element(582566) 
;

-- 2023-07-19T13:26:24.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547026)
;

-- Field: Externes System -> ExternalSystem_Config_PrintingClient -> Mandant
-- Column: ExternalSystem_Config_PrintingClient.AD_Client_ID
-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_PrintingClient.AD_Client_ID
-- 2023-07-19T13:27:32.129Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587140,716713,0,547026,TO_TIMESTAMP('2023-07-19 15:27:31','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-07-19 15:27:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T13:27:32.134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716713 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T13:27:32.136Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-07-19T13:27:33.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716713
;

-- 2023-07-19T13:27:33.571Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716713)
;

-- Field: Externes System -> ExternalSystem_Config_PrintingClient -> Sektion
-- Column: ExternalSystem_Config_PrintingClient.AD_Org_ID
-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_PrintingClient.AD_Org_ID
-- 2023-07-19T13:27:33.691Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587141,716714,0,547026,TO_TIMESTAMP('2023-07-19 15:27:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-07-19 15:27:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T13:27:33.692Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716714 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T13:27:33.693Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-07-19T13:27:34.077Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716714
;

-- 2023-07-19T13:27:34.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716714)
;

-- Field: Externes System -> ExternalSystem_Config_PrintingClient -> Aktiv
-- Column: ExternalSystem_Config_PrintingClient.IsActive
-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_PrintingClient.IsActive
-- 2023-07-19T13:27:34.175Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587144,716715,0,547026,TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T13:27:34.176Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716715 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T13:27:34.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-07-19T13:27:34.590Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716715
;

-- 2023-07-19T13:27:34.591Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716715)
;

-- Field: Externes System -> ExternalSystem_Config_PrintingClient -> ExternalSystem_Config_PrintingClient
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_PrintingClient_ID
-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_PrintingClient_ID
-- 2023-07-19T13:27:34.693Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587147,716716,0,547026,TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_PrintingClient',TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T13:27:34.694Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716716 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T13:27:34.696Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582566) 
;

-- 2023-07-19T13:27:34.698Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716716
;

-- 2023-07-19T13:27:34.699Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716716)
;

-- Field: Externes System -> ExternalSystem_Config_PrintingClient -> External System Config
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_ID
-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> External System Config
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystem_Config_ID
-- 2023-07-19T13:27:34.797Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587148,716717,0,547026,TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T13:27:34.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T13:27:34.802Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2023-07-19T13:27:34.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716717
;

-- 2023-07-19T13:27:34.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716717)
;

-- Field: Externes System -> ExternalSystem_Config_PrintingClient -> Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- 2023-07-19T13:27:34.915Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587150,716718,0,547026,TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100,120,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Zielordner',TO_TIMESTAMP('2023-07-19 15:27:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T13:27:34.916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T13:27:34.917Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(50027) 
;

-- 2023-07-19T13:27:34.923Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716718
;

-- 2023-07-19T13:27:34.924Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716718)
;

-- Tab: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem)
-- UI Section: main
-- 2023-07-19T13:30:12.036Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547026,545635,TO_TIMESTAMP('2023-07-19 15:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-07-19 15:30:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-07-19T13:30:12.039Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545635 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2023-07-19T13:30:34.605Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546884,545635,TO_TIMESTAMP('2023-07-19 15:30:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-07-19 15:30:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2023-07-19T13:30:44.975Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546884,550829,TO_TIMESTAMP('2023-07-19 15:30:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-07-19 15:30:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_PrintingClient.Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> main -> 10 -> main.Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- 2023-07-19T13:31:58.090Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716718,0,547026,550829,618294,'F',TO_TIMESTAMP('2023-07-19 15:31:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zielordner',10,0,0,TO_TIMESTAMP('2023-07-19 15:31:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> ExternalSystem_Config_PrintingClient.Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_PrintingClient(547026,de.metas.externalsystem) -> main -> 10 -> main.Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- 2023-07-19T13:32:13.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-07-19 15:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618294
;

-- Element: ExternalSystem_Config_PrintingClient_ID
-- 2023-07-19T13:37:48.088Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Druck Client', PrintName='Druck Client',Updated=TO_TIMESTAMP('2023-07-19 15:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582566 AND AD_Language='de_CH'
;

-- 2023-07-19T13:37:48.091Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582566,'de_CH') 
;

-- Element: ExternalSystem_Config_PrintingClient_ID
-- 2023-07-19T13:38:10.793Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Printing Client', PrintName='Printing Client',Updated=TO_TIMESTAMP('2023-07-19 15:38:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582566 AND AD_Language='en_US'
;

-- 2023-07-19T13:38:10.795Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582566,'en_US') 
;

-- Element: ExternalSystem_Config_PrintingClient_ID
-- 2023-07-19T13:38:51.297Z
UPDATE AD_Element_Trl SET Name='Druck Client', PrintName='Druck Client',Updated=TO_TIMESTAMP('2023-07-19 15:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582566 AND AD_Language='de_DE'
;

-- 2023-07-19T13:38:51.299Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582566,'de_DE') 
;

-- 2023-07-19T13:38:51.308Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582566,'de_DE') 
;

-- Element: Target_Directory
-- 2023-07-19T13:40:20.439Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Target Directory', PrintName='Target Directory',Updated=TO_TIMESTAMP('2023-07-19 15:40:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=50027 AND AD_Language='en_GB'
;

-- 2023-07-19T13:40:20.442Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(50027,'en_GB') 
;

-- Element: Target_Directory
-- 2023-07-19T14:05:03.096Z
UPDATE AD_Element_Trl SET Name='Target Directory', PrintName='Target Directory',Updated=TO_TIMESTAMP('2023-07-19 16:05:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=50027 AND AD_Language='en_US'
;

-- 2023-07-19T14:05:03.102Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(50027,'en_US') 
;

-- Element: ExternalSystem_Config_PrintingClient_ID
-- 2023-07-19T13:40:36.684Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-19 15:40:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582566 AND AD_Language='de_DE'
;

-- 2023-07-19T13:40:36.686Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582566,'de_DE') 
;

-- 2023-07-19T13:40:36.687Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582566,'de_DE') 
;

-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- 2023-07-26T17:05:16.213Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587173,578788,0,10,542353,'ExternalSystemValue',TO_TIMESTAMP('2023-07-26 19:05:16','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2023-07-26 19:05:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-26T17:05:16.216Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587173 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-26T17:05:16.244Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788)
;

-- 2023-07-26T17:05:34.574Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_PrintingClient','ALTER TABLE public.ExternalSystem_Config_PrintingClient ADD COLUMN ExternalSystemValue VARCHAR(255)')
;

-- Field: Externes System -> Druck Client -> Suchschlüssel
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- Field: Externes System(541024,de.metas.externalsystem) -> Druck Client(547026,de.metas.externalsystem) -> Suchschlüssel
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- 2023-07-26T17:07:11.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587173,717251,0,547026,TO_TIMESTAMP('2023-07-26 19:07:10','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2023-07-26 19:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-26T17:07:11.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=717251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-26T17:07:11.031Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788)
;

-- 2023-07-26T17:07:11.044Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717251
;

-- 2023-07-26T17:07:11.048Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717251)
;

-- UI Element: Externes System -> Druck Client.Suchschlüssel
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- UI Element: Externes System(541024,de.metas.externalsystem) -> Druck Client(547026,de.metas.externalsystem) -> main -> 10 -> main.Suchschlüssel
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- 2023-07-26T17:09:14.408Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,717251,0,547026,550829,618574,'F',TO_TIMESTAMP('2023-07-26 19:09:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',20,0,0,TO_TIMESTAMP('2023-07-26 19:09:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> Druck Client.Suchschlüssel
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- UI Element: Externes System(541024,de.metas.externalsystem) -> Druck Client(547026,de.metas.externalsystem) -> main -> 10 -> main.Suchschlüssel
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- 2023-07-26T17:09:33.706Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-07-26 19:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618574
;

-- UI Element: Externes System -> Druck Client.Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- UI Element: Externes System(541024,de.metas.externalsystem) -> Druck Client(547026,de.metas.externalsystem) -> main -> 10 -> main.Zielordner
-- Column: ExternalSystem_Config_PrintingClient.Target_Directory
-- 2023-07-26T17:09:33.714Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-07-26 19:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618294
;

-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- Column: ExternalSystem_Config_PrintingClient.ExternalSystemValue
-- 2023-07-26T17:15:36.161Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-07-26 19:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587173
;

-- 2023-07-26T17:16:54.066Z
INSERT INTO t_alter_column values('externalsystem_config_printingclient','ExternalSystemValue','VARCHAR(255)',null,null)
;

-- 2023-07-26T17:16:54.071Z
INSERT INTO t_alter_column values('externalsystem_config_printingclient','ExternalSystemValue',null,'NOT NULL',null)
;

