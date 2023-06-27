-- Table: ExternalSystem_Config_Metasfresh
-- 2022-11-07T12:12:10.510Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542253,'N',TO_TIMESTAMP('2022-11-07 14:12:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','N','N','N','N','N','N',0,'ExternalSystem_Config_Metasfresh','NP','L','ExternalSystem_Config_Metasfresh','DTI',TO_TIMESTAMP('2022-11-07 14:12:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:12:10.514Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542253 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-11-07T12:12:10.631Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556045,TO_TIMESTAMP('2022-11-07 14:12:10','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_Metasfresh',1,'Y','N','Y','Y','ExternalSystem_Config_Metasfresh','N',1000000,TO_TIMESTAMP('2022-11-07 14:12:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:12:10.646Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_METASFRESH_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ExternalSystem_Config_Metasfresh.AD_Client_ID
-- 2022-11-07T12:12:31.905Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584869,102,0,19,542253,'AD_Client_ID',TO_TIMESTAMP('2022-11-07 14:12:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-11-07 14:12:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:31.909Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584869 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:31.965Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ExternalSystem_Config_Metasfresh.AD_Org_ID
-- 2022-11-07T12:12:33.793Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584870,113,0,30,542253,'AD_Org_ID',TO_TIMESTAMP('2022-11-07 14:12:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-11-07 14:12:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:33.795Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584870 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:33.798Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ExternalSystem_Config_Metasfresh.Created
-- 2022-11-07T12:12:34.602Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584871,245,0,16,542253,'Created',TO_TIMESTAMP('2022-11-07 14:12:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-11-07 14:12:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:34.604Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584871 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:34.606Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ExternalSystem_Config_Metasfresh.CreatedBy
-- 2022-11-07T12:12:35.331Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584872,246,0,18,110,542253,'CreatedBy',TO_TIMESTAMP('2022-11-07 14:12:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-11-07 14:12:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:35.334Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584872 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:35.337Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ExternalSystem_Config_Metasfresh.IsActive
-- 2022-11-07T12:12:35.982Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584873,348,0,20,542253,'IsActive',TO_TIMESTAMP('2022-11-07 14:12:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-11-07 14:12:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:35.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584873 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:35.987Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ExternalSystem_Config_Metasfresh.Updated
-- 2022-11-07T12:12:36.773Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584874,607,0,16,542253,'Updated',TO_TIMESTAMP('2022-11-07 14:12:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-11-07 14:12:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:36.775Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584874 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:36.777Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ExternalSystem_Config_Metasfresh.UpdatedBy
-- 2022-11-07T12:12:37.485Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584875,608,0,18,110,542253,'UpdatedBy',TO_TIMESTAMP('2022-11-07 14:12:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-11-07 14:12:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:37.487Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584875 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:37.490Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-11-07T12:12:38.086Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581641,0,'ExternalSystem_Config_Metasfresh_ID',TO_TIMESTAMP('2022-11-07 14:12:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_Config_Metasfresh','ExternalSystem_Config_Metasfresh',TO_TIMESTAMP('2022-11-07 14:12:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:12:38.089Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581641 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_Metasfresh_ID
-- 2022-11-07T12:12:38.485Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584876,581641,0,13,542253,'ExternalSystem_Config_Metasfresh_ID',TO_TIMESTAMP('2022-11-07 14:12:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','ExternalSystem_Config_Metasfresh',0,0,TO_TIMESTAMP('2022-11-07 14:12:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:12:38.488Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:12:38.491Z
/* DDL */  select update_Column_Translation_From_AD_Element(581641) 
;

-- 2022-11-07T12:12:38.894Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_Metasfresh (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalSystem_Config_Metasfresh_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Config_Metasfresh_Key PRIMARY KEY (ExternalSystem_Config_Metasfresh_ID))
;

-- 2022-11-07T12:12:38.932Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2022-11-07T12:12:38.942Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-11-07T12:12:38.949Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','CreatedBy','NUMERIC(10)',null,null)
;

-- 2022-11-07T12:12:38.959Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','IsActive','CHAR(1)',null,null)
;

-- 2022-11-07T12:12:38.978Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-11-07T12:12:38.987Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2022-11-07T12:12:38.994Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','ExternalSystem_Config_Metasfresh_ID','NUMERIC(10)',null,null)
;

-- Column: ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey
-- 2022-11-07T12:13:18.363Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584877,579512,0,10,542253,'CamelHttpResourceAuthKey',TO_TIMESTAMP('2022-11-07 14:13:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'User Authentication Token ',0,0,TO_TIMESTAMP('2022-11-07 14:13:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:13:18.365Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:13:18.369Z
/* DDL */  select update_Column_Translation_From_AD_Element(579512) 
;

-- 2022-11-07T12:13:19.797Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Metasfresh','ALTER TABLE public.ExternalSystem_Config_Metasfresh ADD COLUMN CamelHttpResourceAuthKey VARCHAR(255)')
;

-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_ID
-- 2022-11-07T12:13:56.763Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584878,578728,0,19,542253,'ExternalSystem_Config_ID',TO_TIMESTAMP('2022-11-07 14:13:56','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'External System Config',0,0,TO_TIMESTAMP('2022-11-07 14:13:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:13:56.768Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584878 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:13:56.778Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- 2022-11-07T12:13:58.191Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Metasfresh','ALTER TABLE public.ExternalSystem_Config_Metasfresh ADD COLUMN ExternalSystem_Config_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-07T12:13:58.198Z
ALTER TABLE ExternalSystem_Config_Metasfresh ADD CONSTRAINT ExternalSystemConfig_ExternalSystemConfigMetasfresh FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- Column: ExternalSystem_Config_Metasfresh.ExternalSystemValue
-- 2022-11-07T12:14:33.481Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584879,578788,0,10,542253,'ExternalSystemValue',TO_TIMESTAMP('2022-11-07 14:14:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2022-11-07 14:14:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-07T12:14:33.485Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584879 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-07T12:14:33.490Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788) 
;

-- Tab: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh
-- Table: ExternalSystem_Config_Metasfresh
-- 2022-11-07T12:19:41.376Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584878,581641,0,546666,542253,541024,'Y',TO_TIMESTAMP('2022-11-07 14:19:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_Metasfresh','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_Metasfresh','N',80,1,TO_TIMESTAMP('2022-11-07 14:19:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:19:41.382Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546666 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-07T12:19:41.389Z
/* DDL */  select update_tab_translation_from_ad_element(581641) 
;

-- 2022-11-07T12:19:41.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546666)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_Metasfresh.AD_Client_ID
-- 2022-11-07T12:19:59.629Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584869,707974,0,546666,TO_TIMESTAMP('2022-11-07 14:19:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-07 14:19:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:19:59.633Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:19:59.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-07T12:20:01.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707974
;

-- 2022-11-07T12:20:01.135Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707974)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_Metasfresh.AD_Org_ID
-- 2022-11-07T12:20:01.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584870,707975,0,546666,TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:01.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:01.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-07T12:20:01.840Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707975
;

-- 2022-11-07T12:20:01.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707975)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_Metasfresh.IsActive
-- 2022-11-07T12:20:02.136Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584873,707976,0,546666,TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-07 14:20:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:02.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:02.139Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-07T12:20:03.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707976
;

-- 2022-11-07T12:20:03.785Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707976)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_Metasfresh_ID
-- 2022-11-07T12:20:04.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584876,707977,0,546666,TO_TIMESTAMP('2022-11-07 14:20:03','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_Metasfresh',TO_TIMESTAMP('2022-11-07 14:20:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.120Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.122Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581641) 
;

-- 2022-11-07T12:20:04.127Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707977
;

-- 2022-11-07T12:20:04.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707977)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> User Authentication Token
-- Column: ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey
-- 2022-11-07T12:20:04.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584877,707978,0,546666,TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','User Authentication Token ',TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.230Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579512) 
;

-- 2022-11-07T12:20:04.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707978
;

-- 2022-11-07T12:20:04.238Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707978)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> External System Config
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_ID
-- 2022-11-07T12:20:04.330Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584878,707979,0,546666,TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.332Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2022-11-07T12:20:04.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707979
;

-- 2022-11-07T12:20:04.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707979)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Suchschlüssel
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystemValue
-- 2022-11-07T12:20:04.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584879,707980,0,546666,TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2022-11-07 14:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:20:04.454Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-07T12:20:04.456Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788) 
;

-- 2022-11-07T12:20:04.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707980
;

-- 2022-11-07T12:20:04.463Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707980)
;

-- Tab: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem)
-- UI Section: main
-- 2022-11-07T12:20:20.581Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546666,545291,TO_TIMESTAMP('2022-11-07 14:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-07 14:20:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-07T12:20:20.583Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545291 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2022-11-07T12:20:27.784Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546438,545291,TO_TIMESTAMP('2022-11-07 14:20:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-07 14:20:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10
-- UI Element Group: default
-- 2022-11-07T12:20:43.492Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546438,550001,TO_TIMESTAMP('2022-11-07 14:20:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-07 14:20:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.ExternalSystem_Config_Metasfresh
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystem_Config_Metasfresh_ID
-- 2022-11-07T12:55:39.792Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707977,0,546666,613402,550001,'F',TO_TIMESTAMP('2022-11-07 14:55:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalSystem_Config_Metasfresh',10,0,0,TO_TIMESTAMP('2022-11-07 14:55:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_Metasfresh.ExternalSystemValue
-- 2022-11-07T12:55:53.985Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707980,0,546666,613403,550001,'F',TO_TIMESTAMP('2022-11-07 14:55:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',20,0,0,TO_TIMESTAMP('2022-11-07 14:55:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.User Authentication Token
-- Column: ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey
-- 2022-11-07T12:56:19.038Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707978,0,546666,613404,550001,'F',TO_TIMESTAMP('2022-11-07 14:56:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'User Authentication Token ',30,0,0,TO_TIMESTAMP('2022-11-07 14:56:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T12:57:19.674Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Metasfresh','ALTER TABLE public.ExternalSystem_Config_Metasfresh ADD COLUMN ExternalSystemValue VARCHAR(255) NOT NULL')
;

-- 2022-11-07T16:52:29.011Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543332,541255,TO_TIMESTAMP('2022-11-07 18:52:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Metasfresh',TO_TIMESTAMP('2022-11-07 18:52:28','YYYY-MM-DD HH24:MI:SS'),100,'Metasfresh','Metasfresh')
;

-- 2022-11-07T16:52:29.017Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543332 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Value: Call_External_System_Metasfresh
-- Classname: de.metas.externalsystem.process.InvokeMetasfreshAction
-- 2022-11-07T17:06:09.683Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585141,'Y','de.metas.externalsystem.process.InvokeMetasfreshAction','N',TO_TIMESTAMP('2022-11-07 19:06:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','Y','N','N','N','Y','Y',0,'Invoke Metasfresh','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-07 19:06:09','YYYY-MM-DD HH24:MI:SS'),100,'Call_External_System_Metasfresh')
;

-- 2022-11-07T17:06:09.688Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585141 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Name: External_Request_Metasfresh
-- 2022-11-07T17:08:16.820Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541688,TO_TIMESTAMP('2022-11-07 19:08:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','External_Request_Metasfresh',TO_TIMESTAMP('2022-11-07 19:08:16','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-11-07T17:08:16.825Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541688 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: External_Request_Metasfresh
-- Value: enableRestAPI
-- ValueName: API Starten
-- 2022-11-07T17:14:46.916Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543333,541688,TO_TIMESTAMP('2022-11-07 19:14:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','API Starten',TO_TIMESTAMP('2022-11-07 19:14:46','YYYY-MM-DD HH24:MI:SS'),100,'enableRestAPI','API Starten')
;

-- 2022-11-07T17:14:46.918Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543333 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request_Metasfresh -> enableRestAPI_API Starten
-- 2022-11-07T17:15:06.155Z
UPDATE AD_Ref_List_Trl SET Name='Start API',Updated=TO_TIMESTAMP('2022-11-07 19:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543333
;

-- Reference: External_Request_Metasfresh
-- Value: disableRestAPI
-- ValueName: API Stoppen
-- 2022-11-07T17:15:26.282Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543334,541688,TO_TIMESTAMP('2022-11-07 19:15:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','API Stoppen',TO_TIMESTAMP('2022-11-07 19:15:26','YYYY-MM-DD HH24:MI:SS'),100,'disableRestAPI','API Stoppen')
;

-- 2022-11-07T17:15:26.283Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543334 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request_Metasfresh -> disableRestAPI_API Stoppen
-- 2022-11-07T17:15:34.097Z
UPDATE AD_Ref_List_Trl SET Name='Stop API',Updated=TO_TIMESTAMP('2022-11-07 19:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543334
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- ParameterName: External_Request
-- 2022-11-07T17:16:10.192Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578757,0,585141,542361,17,'External_Request',TO_TIMESTAMP('2022-11-07 19:16:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',30,'Y','N','Y','N','Y','N','Befehl',10,TO_TIMESTAMP('2022-11-07 19:16:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-07T17:16:10.198Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542361 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- ParameterName: External_Request
-- 2022-11-07T17:16:17.136Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541688,Updated=TO_TIMESTAMP('2022-11-07 19:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542361
;


-- Process: Call_External_System_Metasfresh(de.metas.externalsystem.process.InvokeMetasfreshAction)
-- Table: ExternalSystem_Config
-- EntityType: de.metas.externalsystem
-- 2022-11-08T09:49:20.741Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585141,541576,541303,TO_TIMESTAMP('2022-11-08 11:49:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2022-11-08 11:49:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2022-11-09T14:09:45.355Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581654,0,'FeedbackResourceAuthToken',TO_TIMESTAMP('2022-11-09 16:09:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Feedback Authentication Token','Feedback Authentication Token',TO_TIMESTAMP('2022-11-09 16:09:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T14:09:45.366Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581654 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-09T14:10:49.270Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581655,0,'FeedbackResourceURL',TO_TIMESTAMP('2022-11-09 16:10:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Feedback URL','Feedback URL',TO_TIMESTAMP('2022-11-09 16:10:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T14:10:49.274Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581655 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_Metasfresh.FeedbackResourceURL
-- 2022-11-09T14:11:26.775Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584882,581655,0,10,542253,'FeedbackResourceURL',TO_TIMESTAMP('2022-11-09 16:11:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Feedback URL',0,0,TO_TIMESTAMP('2022-11-09 16:11:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-09T14:11:26.780Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584882 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-09T14:11:26.826Z
/* DDL */  select update_Column_Translation_From_AD_Element(581655) 
;

-- 2022-11-09T14:11:56.357Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Metasfresh','ALTER TABLE public.ExternalSystem_Config_Metasfresh ADD COLUMN FeedbackResourceURL VARCHAR(255)')
;

-- Column: ExternalSystem_Config_Metasfresh.FeedbackResourceAuthToken
-- 2022-11-09T14:12:23.870Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584883,581654,0,10,542253,'FeedbackResourceAuthToken',TO_TIMESTAMP('2022-11-09 16:12:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Feedback Authentication Token',0,0,TO_TIMESTAMP('2022-11-09 16:12:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-09T14:12:23.875Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584883 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-09T14:12:23.883Z
/* DDL */  select update_Column_Translation_From_AD_Element(581654) 
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Feedback URL
-- Column: ExternalSystem_Config_Metasfresh.FeedbackResourceURL
-- 2022-11-09T14:13:13.511Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584882,707989,0,546666,TO_TIMESTAMP('2022-11-09 16:13:13','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Feedback URL',TO_TIMESTAMP('2022-11-09 16:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T14:13:13.513Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-09T14:13:13.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581655) 
;

-- 2022-11-09T14:13:13.533Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707989
;

-- 2022-11-09T14:13:13.538Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707989)
;

-- Field: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> Feedback Authentication Token
-- Column: ExternalSystem_Config_Metasfresh.FeedbackResourceAuthToken
-- 2022-11-09T14:13:13.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584883,707990,0,546666,TO_TIMESTAMP('2022-11-09 16:13:13','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Feedback Authentication Token',TO_TIMESTAMP('2022-11-09 16:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T14:13:13.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-09T14:13:13.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581654) 
;

-- 2022-11-09T14:13:13.651Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707990
;

-- 2022-11-09T14:13:13.652Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707990)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.Feedback URL
-- Column: ExternalSystem_Config_Metasfresh.FeedbackResourceURL
-- 2022-11-09T14:13:48.701Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707989,0,546666,613410,550001,'F',TO_TIMESTAMP('2022-11-09 16:13:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Feedback URL',40,0,0,TO_TIMESTAMP('2022-11-09 16:13:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> ExternalSystem_Config_Metasfresh(546666,de.metas.externalsystem) -> main -> 10 -> default.Feedback Authentication Token
-- Column: ExternalSystem_Config_Metasfresh.FeedbackResourceAuthToken
-- 2022-11-09T14:13:54.455Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707990,0,546666,613411,550001,'F',TO_TIMESTAMP('2022-11-09 16:13:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Feedback Authentication Token',50,0,0,TO_TIMESTAMP('2022-11-09 16:13:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T14:15:06.869Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Metasfresh','ALTER TABLE public.ExternalSystem_Config_Metasfresh ADD COLUMN FeedbackResourceAuthToken VARCHAR(255)')
;

-- 2022-11-09T14:15:15.800Z
INSERT INTO t_alter_column values('externalsystem_config_metasfresh','FeedbackResourceURL','VARCHAR(255)',null,null)
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-11T11:06:06.043Z
UPDATE AD_Element_Trl SET Name='Metasfresh', PrintName='Metasfresh',Updated=TO_TIMESTAMP('2022-11-11 13:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='de_CH'
;

-- 2022-11-11T11:06:06.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'de_CH') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-11T11:06:09.176Z
UPDATE AD_Element_Trl SET Name='Metasfresh', PrintName='Metasfresh',Updated=TO_TIMESTAMP('2022-11-11 13:06:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='de_DE'
;

-- 2022-11-11T11:06:09.178Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581641,'de_DE') 
;

-- 2022-11-11T11:06:09.189Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'de_DE') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-11T11:06:11.281Z
UPDATE AD_Element_Trl SET Name='Metasfresh', PrintName='Metasfresh',Updated=TO_TIMESTAMP('2022-11-11 13:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='en_US'
;

-- 2022-11-11T11:06:11.283Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'en_US') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-11T11:06:13.642Z
UPDATE AD_Element_Trl SET Name='Metasfresh', PrintName='Metasfresh',Updated=TO_TIMESTAMP('2022-11-11 13:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='fr_CH'
;

-- 2022-11-11T11:06:13.644Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'fr_CH') 
;

-- Element: ExternalSystem_Config_Metasfresh_ID
-- 2022-11-11T11:06:16.137Z
UPDATE AD_Element_Trl SET Name='Metasfresh', PrintName='Metasfresh',Updated=TO_TIMESTAMP('2022-11-11 13:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581641 AND AD_Language='nl_NL'
;

-- 2022-11-11T11:06:16.139Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581641,'nl_NL') 
;
