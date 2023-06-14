-- Table: Contract_Module_Type
-- 2023-06-07T08:43:10.227984851Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542337,'N',TO_TIMESTAMP('2023-06-07 09:43:09.818','YYYY-MM-DD HH24:MI:SS.US'),100,'Contract Module Type','D','N','Y','N','N','Y','N','N','N','N','N',0,'Contract Module Type','NP','L','Contract_Module_Type','DTI',TO_TIMESTAMP('2023-06-07 09:43:09.818','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:43:10.243567052Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542337 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-06-07T08:43:11.052035172Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556273,TO_TIMESTAMP('2023-06-07 09:43:10.914','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table Contract_Module_Type',1,'Y','N','Y','Y','Contract_Module_Type','N',1000000,TO_TIMESTAMP('2023-06-07 09:43:10.914','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:43:11.088233618Z
CREATE SEQUENCE CONTRACT_MODULE_TYPE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: Contract_Module_Type.AD_Client_ID
-- 2023-06-07T08:44:36.849096204Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586740,102,0,19,542337,'AD_Client_ID',TO_TIMESTAMP('2023-06-07 09:44:36.6','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-06-07 09:44:36.6','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:36.852136258Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:37.804677101Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: Contract_Module_Type.AD_Org_ID
-- 2023-06-07T08:44:39.195924222Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586741,113,0,30,542337,'AD_Org_ID',TO_TIMESTAMP('2023-06-07 09:44:38.711','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-06-07 09:44:38.711','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:39.198237922Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:40.104771233Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: Contract_Module_Type.Created
-- 2023-06-07T08:44:41.198123858Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586742,245,0,16,542337,'Created',TO_TIMESTAMP('2023-06-07 09:44:40.807','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-06-07 09:44:40.807','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:41.203431227Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:41.998839914Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: Contract_Module_Type.CreatedBy
-- 2023-06-07T08:44:43.283844863Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586743,246,0,18,110,542337,'CreatedBy',TO_TIMESTAMP('2023-06-07 09:44:42.777','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-06-07 09:44:42.777','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:43.287569478Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586743 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:44.175832514Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: Contract_Module_Type.IsActive
-- 2023-06-07T08:44:45.369638910Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586744,348,0,20,542337,'IsActive',TO_TIMESTAMP('2023-06-07 09:44:44.883','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-06-07 09:44:44.883','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:45.371915791Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:46.306122271Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: Contract_Module_Type.Updated
-- 2023-06-07T08:44:47.463979967Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586745,607,0,16,542337,'Updated',TO_TIMESTAMP('2023-06-07 09:44:47.019','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-06-07 09:44:47.019','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:47.465789789Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:48.411432828Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: Contract_Module_Type.UpdatedBy
-- 2023-06-07T08:44:49.597566226Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586746,608,0,18,110,542337,'UpdatedBy',TO_TIMESTAMP('2023-06-07 09:44:49.179','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-06-07 09:44:49.179','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:49.601610618Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:50.613929287Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2023-06-07T08:44:51.482685593Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582395,0,'Contract_Module_Type_ID',TO_TIMESTAMP('2023-06-07 09:44:51.323','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Contract Module Type','Contract Module Type',TO_TIMESTAMP('2023-06-07 09:44:51.323','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:44:51.488308983Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582395 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T08:44:52.584802889Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586747,582395,0,13,542337,'Contract_Module_Type_ID',TO_TIMESTAMP('2023-06-07 09:44:51.311','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Contract Module Type',0,0,TO_TIMESTAMP('2023-06-07 09:44:51.311','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:44:52.586453307Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:44:53.523300859Z
/* DDL */  select update_Column_Translation_From_AD_Element(582395)
;

-- 2023-06-07T08:44:54.166443070Z
/* DDL */ CREATE TABLE public.Contract_Module_Type (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Contract_Module_Type_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Contract_Module_Type_Key PRIMARY KEY (Contract_Module_Type_ID))
;

-- 2023-06-07T08:44:54.187663540Z
INSERT INTO t_alter_column values('contract_module_type','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-06-07T08:44:54.202777163Z
INSERT INTO t_alter_column values('contract_module_type','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-06-07T08:44:54.214255594Z
INSERT INTO t_alter_column values('contract_module_type','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-06-07T08:44:54.225385898Z
INSERT INTO t_alter_column values('contract_module_type','IsActive','CHAR(1)',null,null)
;

-- 2023-06-07T08:44:54.251118049Z
INSERT INTO t_alter_column values('contract_module_type','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-06-07T08:44:54.262715554Z
INSERT INTO t_alter_column values('contract_module_type','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-06-07T08:44:54.273557433Z
INSERT INTO t_alter_column values('contract_module_type','Contract_Module_Type_ID','NUMERIC(10)',null,null)
;

-- Column: Contract_Module_Type.Value
-- 2023-06-07T08:45:41.582034715Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586748,620,0,10,542337,'Value',TO_TIMESTAMP('2023-06-07 09:45:41.353','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',0,40,'E','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','Y',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2023-06-07 09:45:41.353','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:45:41.585517410Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:45:42.523110006Z
/* DDL */  select update_Column_Translation_From_AD_Element(620)
;

-- 2023-06-07T08:45:59.239960013Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Type','ALTER TABLE public.Contract_Module_Type ADD COLUMN Value VARCHAR(40) NOT NULL')
;

-- Column: Contract_Module_Type.Name
-- 2023-06-07T08:46:20.280489272Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586749,469,0,10,542337,'Name',TO_TIMESTAMP('2023-06-07 09:46:20.024','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,40,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-06-07 09:46:20.024','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:46:20.283196907Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:46:21.121147435Z
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2023-06-07T08:46:29.546054040Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Type','ALTER TABLE public.Contract_Module_Type ADD COLUMN Name VARCHAR(40) NOT NULL')
;

-- Column: Contract_Module_Type.Description
-- 2023-06-07T08:46:52.803324753Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586750,275,0,10,542337,'Description',TO_TIMESTAMP('2023-06-07 09:46:52.563','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2023-06-07 09:46:52.563','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:46:52.807170722Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586750 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:46:53.642742303Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2023-06-07T08:47:02.833601033Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Type','ALTER TABLE public.Contract_Module_Type ADD COLUMN Description VARCHAR(255)')
;

-- 2023-06-07T08:48:23.209277854Z
UPDATE AD_Table_Trl SET Name='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-07 09:48:23.207','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542337
;

-- 2023-06-07T08:48:23.221337815Z
UPDATE AD_Table SET Name='Vertragsbaustein Typ' WHERE AD_Table_ID=542337
;

-- Column: Contract_Module_Type.Classname
-- 2023-06-07T08:49:51.672690970Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586751,1299,0,10,542337,'Classname',TO_TIMESTAMP('2023-06-07 09:49:51.448','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Java-Klasse',0,0,TO_TIMESTAMP('2023-06-07 09:49:51.448','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-07T08:49:51.676701707Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586751 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T08:49:52.471249437Z
/* DDL */  select update_Column_Translation_From_AD_Element(1299)
;

-- 2023-06-07T08:49:56.859590800Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Type','ALTER TABLE public.Contract_Module_Type ADD COLUMN Classname VARCHAR(255)')
;

-- 2023-06-07T08:56:09.284334647Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582396,0,TO_TIMESTAMP('2023-06-07 09:56:08.999','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Contract Module Type','Contract Module Type',TO_TIMESTAMP('2023-06-07 09:56:08.999','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:56:09.286230745Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582396 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-06-07T08:56:52.522459365Z
UPDATE AD_Element_Trl SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-07 09:56:52.522','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='de_CH'
;

-- 2023-06-07T08:56:52.527052370Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'de_CH')
;

-- Element: null
-- 2023-06-07T08:57:01.145292464Z
UPDATE AD_Element_Trl SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-07 09:57:01.144','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='de_DE'
;

-- 2023-06-07T08:57:01.147465309Z
UPDATE AD_Element SET Name='Vertragsbaustein Typ', PrintName='Vertragsbaustein Typ' WHERE AD_Element_ID=582396
;

-- 2023-06-07T08:57:01.731343522Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582396,'de_DE')
;

-- 2023-06-07T08:57:01.733396188Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'de_DE')
;

-- Element: null
-- 2023-06-07T08:57:05.899758251Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-07 09:57:05.899','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='en_US'
;

-- 2023-06-07T08:57:05.902434876Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'en_US')
;

-- Window: Vertragsbaustein Typ, InternalName=null
-- 2023-06-07T08:57:39.915551366Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582396,0,541710,TO_TIMESTAMP('2023-06-07 09:57:39.67','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','Y','N','N','N','Y','Vertragsbaustein Typ','N',TO_TIMESTAMP('2023-06-07 09:57:39.67','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-06-07T08:57:39.919414702Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541710 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-06-07T08:57:39.924714829Z
/* DDL */  select update_window_translation_from_ad_element(582396)
;

-- 2023-06-07T08:57:39.937467751Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541710
;

-- 2023-06-07T08:57:39.940403333Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541710)
;

-- Window: Vertragsbaustein Typ, InternalName=Vertragsbaustein Typ
-- 2023-06-07T08:57:48.744959807Z
UPDATE AD_Window SET InternalName='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-07 09:57:48.742','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541710
;

-- Tab: Vertragsbaustein Typ(541710,D) -> Contract Module Type
-- Table: Contract_Module_Type
-- 2023-06-07T08:58:09.993526988Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582395,0,547011,542337,541710,'Y',TO_TIMESTAMP('2023-06-07 09:58:09.745','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','Contract_Module_Type','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Contract Module Type','N',10,0,TO_TIMESTAMP('2023-06-07 09:58:09.745','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:09.997358845Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547011 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-06-07T08:58:10.001263856Z
/* DDL */  select update_tab_translation_from_ad_element(582395)
;

-- 2023-06-07T08:58:10.012489056Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547011)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Mandant
-- Column: Contract_Module_Type.AD_Client_ID
-- 2023-06-07T08:58:48.594365427Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586740,716264,0,547011,TO_TIMESTAMP('2023-06-07 09:58:48.371','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-06-07 09:58:48.371','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:48.596659398Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:48.598844727Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-06-07T08:58:49.638025684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716264
;

-- 2023-06-07T08:58:49.639077526Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716264)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Organisation
-- Column: Contract_Module_Type.AD_Org_ID
-- 2023-06-07T08:58:49.800839391Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586741,716265,0,547011,TO_TIMESTAMP('2023-06-07 09:58:49.642','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-06-07 09:58:49.642','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:49.803521473Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:49.806315544Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-06-07T08:58:50.442315740Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716265
;

-- 2023-06-07T08:58:50.442941330Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716265)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Erstellt
-- Column: Contract_Module_Type.Created
-- 2023-06-07T08:58:50.597117146Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586742,716266,0,547011,TO_TIMESTAMP('2023-06-07 09:58:50.445','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-06-07 09:58:50.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:50.599665507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:50.602507962Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2023-06-07T08:58:50.773863571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716266
;

-- 2023-06-07T08:58:50.774511966Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716266)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Erstellt durch
-- Column: Contract_Module_Type.CreatedBy
-- 2023-06-07T08:58:50.924776081Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586743,716267,0,547011,TO_TIMESTAMP('2023-06-07 09:58:50.776','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-06-07 09:58:50.776','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:50.926213342Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:50.927942123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2023-06-07T08:58:51.042784129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716267
;

-- 2023-06-07T08:58:51.043676590Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716267)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Aktiv
-- Column: Contract_Module_Type.IsActive
-- 2023-06-07T08:58:51.211108437Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586744,716268,0,547011,TO_TIMESTAMP('2023-06-07 09:58:51.047','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-06-07 09:58:51.047','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:51.212376091Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:51.213760784Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-06-07T08:58:51.384872429Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716268
;

-- 2023-06-07T08:58:51.385609080Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716268)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Aktualisiert
-- Column: Contract_Module_Type.Updated
-- 2023-06-07T08:58:51.528340847Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586745,716269,0,547011,TO_TIMESTAMP('2023-06-07 09:58:51.389','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-06-07 09:58:51.389','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:51.529512632Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:51.530852093Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2023-06-07T08:58:51.674480209Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716269
;

-- 2023-06-07T08:58:51.675038579Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716269)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Aktualisiert durch
-- Column: Contract_Module_Type.UpdatedBy
-- 2023-06-07T08:58:51.817181503Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586746,716270,0,547011,TO_TIMESTAMP('2023-06-07 09:58:51.678','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-06-07 09:58:51.678','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:51.818386846Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:51.819743453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2023-06-07T08:58:51.915084139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716270
;

-- 2023-06-07T08:58:51.915818406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716270)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T08:58:52.080081204Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586747,716271,0,547011,TO_TIMESTAMP('2023-06-07 09:58:51.918','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Contract Module Type',TO_TIMESTAMP('2023-06-07 09:58:51.918','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:52.082492172Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:52.085258406Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582395)
;

-- 2023-06-07T08:58:52.093869622Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716271
;

-- 2023-06-07T08:58:52.094916951Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716271)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Suchschlüssel
-- Column: Contract_Module_Type.Value
-- 2023-06-07T08:58:52.253460662Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586748,716272,0,547011,TO_TIMESTAMP('2023-06-07 09:58:52.099','YYYY-MM-DD HH24:MI:SS.US'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2023-06-07 09:58:52.099','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:52.256516225Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:52.259241156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2023-06-07T08:58:52.304600291Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716272
;

-- 2023-06-07T08:58:52.305307182Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716272)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Name
-- Column: Contract_Module_Type.Name
-- 2023-06-07T08:58:52.471587877Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586749,716273,0,547011,TO_TIMESTAMP('2023-06-07 09:58:52.308','YYYY-MM-DD HH24:MI:SS.US'),100,'',40,'D','','Y','Y','N','N','N','N','N','Name',TO_TIMESTAMP('2023-06-07 09:58:52.308','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:52.473998204Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:52.476797063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2023-06-07T08:58:52.625097594Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716273
;

-- 2023-06-07T08:58:52.625690783Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716273)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Beschreibung
-- Column: Contract_Module_Type.Description
-- 2023-06-07T08:58:52.770442146Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586750,716274,0,547011,TO_TIMESTAMP('2023-06-07 09:58:52.628','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','Y','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-06-07 09:58:52.628','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:52.773066001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:52.775898484Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2023-06-07T08:58:52.985544654Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716274
;

-- 2023-06-07T08:58:52.986240155Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716274)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Java-Klasse
-- Column: Contract_Module_Type.Classname
-- 2023-06-07T08:58:53.126414262Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586751,716275,0,547011,TO_TIMESTAMP('2023-06-07 09:58:52.988','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','Y','N','N','N','N','N','Java-Klasse',TO_TIMESTAMP('2023-06-07 09:58:52.988','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T08:58:53.127890121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T08:58:53.129484986Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1299)
;

-- 2023-06-07T08:58:53.142241013Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716275
;

-- 2023-06-07T08:58:53.142877215Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716275)
;

-- Tab: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D)
-- UI Section: main
-- 2023-06-07T08:59:55.977153479Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547011,545617,TO_TIMESTAMP('2023-06-07 09:59:55.724','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-07 09:59:55.724','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-06-07T08:59:55.978909986Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545617 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Tab: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D)
-- UI Section: advanced
-- 2023-06-07T09:00:09.903176856Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547011,545618,TO_TIMESTAMP('2023-06-07 10:00:09.714','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-06-07 10:00:09.714','YYYY-MM-DD HH24:MI:SS.US'),100,'advanced')
;

-- 2023-06-07T09:00:09.905126177Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545618 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main
-- UI Column: 10
-- 2023-06-07T09:00:16.936876866Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546860,545617,TO_TIMESTAMP('2023-06-07 10:00:16.718','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-07 10:00:16.718','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main
-- UI Column: 20
-- 2023-06-07T09:00:21.592649473Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546861,545617,TO_TIMESTAMP('2023-06-07 10:00:21.451','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-06-07 10:00:21.451','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10
-- UI Element Group: primary
-- 2023-06-07T09:00:34.645054972Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546860,550768,TO_TIMESTAMP('2023-06-07 10:00:34.366','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','primary',10,TO_TIMESTAMP('2023-06-07 10:00:34.366','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> primary.Suchschlüssel
-- Column: Contract_Module_Type.Value
-- 2023-06-07T09:00:53.946198878Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716272,0,547011,550768,617948,'F',TO_TIMESTAMP('2023-06-07 10:00:53.743','YYYY-MM-DD HH24:MI:SS.US'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2023-06-07 10:00:53.743','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> primary.Name
-- Column: Contract_Module_Type.Name
-- 2023-06-07T09:01:05.320596199Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716273,0,547011,550768,617949,'F',TO_TIMESTAMP('2023-06-07 10:01:05.06','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2023-06-07 10:01:05.06','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10
-- UI Element Group: infos
-- 2023-06-07T09:01:35.415704651Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546860,550769,TO_TIMESTAMP('2023-06-07 10:01:35.208','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','infos',20,TO_TIMESTAMP('2023-06-07 10:01:35.208','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> infos.Beschreibung
-- Column: Contract_Module_Type.Description
-- 2023-06-07T09:01:55.692725546Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716274,0,547011,550769,617950,'F',TO_TIMESTAMP('2023-06-07 10:01:55.462','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2023-06-07 10:01:55.462','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> infos.Java-Klasse
-- Column: Contract_Module_Type.Classname
-- 2023-06-07T09:02:32.413461628Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716275,0,547011,550769,617951,'F',TO_TIMESTAMP('2023-06-07 10:02:32.174','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Java-Klasse',20,0,0,TO_TIMESTAMP('2023-06-07 10:02:32.174','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20
-- UI Element Group: flags
-- 2023-06-07T09:02:43.530972401Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546861,550770,TO_TIMESTAMP('2023-06-07 10:02:43.333','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-06-07 10:02:43.333','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20 -> flags.Aktiv
-- Column: Contract_Module_Type.IsActive
-- 2023-06-07T09:03:07.837313914Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716268,0,547011,550770,617952,'F',TO_TIMESTAMP('2023-06-07 10:03:07.599','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-06-07 10:03:07.599','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20
-- UI Element Group: org
-- 2023-06-07T09:05:40.647789813Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546861,550771,TO_TIMESTAMP('2023-06-07 10:05:40.423','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',20,TO_TIMESTAMP('2023-06-07 10:05:40.423','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Type.AD_Org_ID
-- 2023-06-07T09:05:52.750810961Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716265,0,547011,550771,617953,'F',TO_TIMESTAMP('2023-06-07 10:05:52.566','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-06-07 10:05:52.566','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20 -> org.Mandant
-- Column: Contract_Module_Type.AD_Client_ID
-- 2023-06-07T09:06:02.166005147Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716264,0,547011,550771,617954,'F',TO_TIMESTAMP('2023-06-07 10:06:01.969','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-06-07 10:06:01.969','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> primary.Suchschlüssel
-- Column: Contract_Module_Type.Value
-- 2023-06-07T09:06:25.507434124Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-06-07 10:06:25.507','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617948
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> primary.Name
-- Column: Contract_Module_Type.Name
-- 2023-06-07T09:06:25.522403588Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-06-07 10:06:25.522','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617949
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> infos.Beschreibung
-- Column: Contract_Module_Type.Description
-- 2023-06-07T09:06:25.535041711Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-06-07 10:06:25.534','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617950
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20 -> flags.Aktiv
-- Column: Contract_Module_Type.IsActive
-- 2023-06-07T09:06:25.547139556Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-06-07 10:06:25.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617952
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> infos.Java-Klasse
-- Column: Contract_Module_Type.Classname
-- 2023-06-07T09:06:25.558595609Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-06-07 10:06:25.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617951
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Type.AD_Org_ID
-- 2023-06-07T09:06:25.567641696Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-06-07 10:06:25.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617953
;

-- Name: Vertragsbaustein Typ
-- Action Type: W
-- Window: Vertragsbaustein Typ(541710,D)
-- 2023-06-07T09:09:11.978095042Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582396,542086,0,541710,TO_TIMESTAMP('2023-06-07 10:09:11.739','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Vertragsbaustein Typ','Y','N','N','N','N','Vertragsbaustein Typ',TO_TIMESTAMP('2023-06-07 10:09:11.739','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T09:09:11.982208630Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542086 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-06-07T09:09:11.988794649Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542086, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542086)
;

-- 2023-06-07T09:09:11.999513519Z
/* DDL */  select update_menu_translation_from_ad_element(582396)
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-06-07T09:09:20.088387554Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-06-07T09:09:20.089259276Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-06-07T09:09:20.090224786Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-06-07T09:09:20.091026823Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-06-07T09:09:20.091919241Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-06-07T09:09:20.092667894Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-06-07T09:09:20.093458488Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-06-07T09:09:20.094265496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Abo-Rabatt (C_SubscrDiscount)`
-- 2023-06-07T09:09:20.095107645Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Flatrate Term (C_InterimInvoice_FlatrateTerm)`
-- 2023-06-07T09:09:20.095912730Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541979 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Settings (C_Interim_Invoice_Settings)`
-- 2023-06-07T09:09:20.096687825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541974 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-06-07T09:09:20.097404281Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-06-07T09:09:20.098278368Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-06-07T09:09:20.098934032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-06-07T09:09:20.099650023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-06-07T09:09:20.100589007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Vertragsbaustein Typ`
-- 2023-06-07T09:09:20.101354085Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-06-07T09:09:30.877911998Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-06-07T09:09:30.878989298Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-06-07T09:09:30.879987094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-06-07T09:09:30.880991949Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-06-07T09:09:30.881866995Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-06-07T09:09:30.882743639Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Vertragsbaustein Typ`
-- 2023-06-07T09:09:30.883641649Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-06-07T09:09:30.884542392Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-06-07T09:09:30.885474509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Abo-Rabatt (C_SubscrDiscount)`
-- 2023-06-07T09:09:30.886506491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Flatrate Term (C_InterimInvoice_FlatrateTerm)`
-- 2023-06-07T09:09:30.887591929Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541979 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Settings (C_Interim_Invoice_Settings)`
-- 2023-06-07T09:09:30.888726311Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541974 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-06-07T09:09:30.889878460Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-06-07T09:09:30.890918814Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-06-07T09:09:30.892152678Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-06-07T09:09:30.893296200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-06-07T09:09:30.894297018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Element: null
-- 2023-06-07T09:23:39.210736477Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-07 10:23:39.21','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='de_CH'
;

-- 2023-06-07T09:23:39.213010406Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'de_CH')
;

-- Element: null
-- 2023-06-07T09:23:56.631579948Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Vertragsbaustein Typ',Updated=TO_TIMESTAMP('2023-06-07 10:23:56.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='de_DE'
;

-- 2023-06-07T09:23:56.632463435Z
UPDATE AD_Element SET WEBUI_NameBrowse='Vertragsbaustein Typ' WHERE AD_Element_ID=582396
;

-- 2023-06-07T09:23:57.322913266Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582396,'de_DE')
;

-- 2023-06-07T09:23:57.324227228Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'de_DE')
;

-- Element: null
-- 2023-06-07T09:24:05.777904585Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Contract Module Type',Updated=TO_TIMESTAMP('2023-06-07 10:24:05.777','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='en_US'
;

-- 2023-06-07T09:24:05.779887895Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'en_US')
;

-- Element: null
-- 2023-06-07T09:24:12.615161448Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Contract Module Type',Updated=TO_TIMESTAMP('2023-06-07 10:24:12.614','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='fr_CH'
;

-- 2023-06-07T09:24:12.619103736Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'fr_CH')
;

-- 2023-06-07T09:24:27.053607264Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-06-07 10:24:27.052','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396
;

-- 2023-06-07T09:24:27.055692790Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'de_DE')
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:00:26.900298224Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-06-07 11:00:26.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716271
;

-- UI Section: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced
-- UI Column: 10
-- 2023-06-07T10:01:20.721088154Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546862,545618,TO_TIMESTAMP('2023-06-07 11:01:20.44','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-06-07 11:01:20.44','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10
-- UI Element Group: main
-- 2023-06-07T10:01:44.065518646Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546862,550772,TO_TIMESTAMP('2023-06-07 11:01:43.862','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2023-06-07 11:01:43.862','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:01:59.827028094Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716271,0,547011,550772,617955,'F',TO_TIMESTAMP('2023-06-07 11:01:59.588','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Contract Module Type',10,0,0,TO_TIMESTAMP('2023-06-07 11:01:59.588','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: Contract_Module_Type.Description
-- 2023-06-07T10:10:14.410847259Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2023-06-07 11:10:14.41','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586750
;

-- Column: Contract_Module_Type.AD_Org_ID
-- 2023-06-07T10:10:28.711301955Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2023-06-07 11:10:28.711','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586741
;

-- Table: Contract_Module_Type
-- 2023-06-07T10:11:15.202531826Z
UPDATE AD_Table SET AD_Window_ID=541710,Updated=TO_TIMESTAMP('2023-06-07 11:11:15.201','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542337
;

-- Table: Contract_Module_Type
-- 2023-06-07T10:11:23.669640098Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-06-07 11:11:23.668','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542337
;

-- Table: Contract_Module_Type
-- 2023-06-07T10:11:28.973250284Z
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2023-06-07 11:11:28.97','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542337
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:12:47.608009439Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-06-07 11:12:47.607','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617955
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:13:40.222284369Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-06-07 11:13:40.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617955
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:13:55.214647406Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-06-07 11:13:55.214','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617955
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:14:11.165229965Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-06-07 11:14:11.165','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716271
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:14:37.282436927Z
UPDATE AD_UI_Element SET IsAdvancedField='Y', IsDisplayed='N',Updated=TO_TIMESTAMP('2023-06-07 11:14:37.282','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617955
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:14:45.452678917Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-06-07 11:14:45.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617955
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:16:39.103933575Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2023-06-07 11:16:39.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617955
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:18:00.528335858Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2023-06-07 11:18:00.528','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716271
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:20:12.171433362Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-06-07 11:20:12.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617955
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> primary.Suchschlüssel
-- Column: Contract_Module_Type.Value
-- 2023-06-07T10:20:12.176460131Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-06-07 11:20:12.176','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617948
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> primary.Name
-- Column: Contract_Module_Type.Name
-- 2023-06-07T10:20:12.180439636Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-06-07 11:20:12.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617949
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> infos.Beschreibung
-- Column: Contract_Module_Type.Description
-- 2023-06-07T10:20:12.184811559Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-06-07 11:20:12.184','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617950
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20 -> flags.Aktiv
-- Column: Contract_Module_Type.IsActive
-- 2023-06-07T10:20:12.188502072Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-06-07 11:20:12.188','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617952
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10 -> infos.Java-Klasse
-- Column: Contract_Module_Type.Classname
-- 2023-06-07T10:20:12.192994854Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-06-07 11:20:12.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617951
;

-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 20 -> org.Organisation
-- Column: Contract_Module_Type.AD_Org_ID
-- 2023-06-07T10:20:12.195546882Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-06-07 11:20:12.195','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617953
;

-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T10:21:30.097484702Z
UPDATE AD_Column SET IsCalculated='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-06-07 11:21:30.097','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586747
;

-- 2023-06-07T10:21:33.955742567Z
INSERT INTO t_alter_column values('contract_module_type','Contract_Module_Type_ID','NUMERIC(10)',null,null)
;

-- 2023-06-07T10:23:34.907807790Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582397,0,'AD_Ref_Table_ID',TO_TIMESTAMP('2023-06-07 11:23:34.613','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','AD_Ref_Table','AD_Ref_Table',TO_TIMESTAMP('2023-06-07 11:23:34.613','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-07T10:23:34.908867372Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582397 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Ref_Table.AD_Ref_Table_ID
-- 2023-06-07T10:23:35.502495225Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586752,582397,0,13,103,'AD_Ref_Table_ID',TO_TIMESTAMP('2023-06-07 11:23:34.611','YYYY-MM-DD HH24:MI:SS.US'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_Ref_Table',TO_TIMESTAMP('2023-06-07 11:23:34.611','YYYY-MM-DD HH24:MI:SS.US'),100,1)
;

-- 2023-06-07T10:23:35.506948799Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T10:23:36.185250221Z
/* DDL */  select update_Column_Translation_From_AD_Element(582397)
;

-- Field: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T13:52:43.130220190Z
UPDATE AD_Field SET IsActive='Y',Updated=TO_TIMESTAMP('2023-06-07 14:52:43.13','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716271
;

-- Tab: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D)
-- UI Section: advanced
-- UI Section: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced
-- UI Column: 10
-- UI Column: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10
-- UI Element Group: main
-- UI Element: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> advanced -> 10 -> main.Contract Module Type
-- Column: Contract_Module_Type.Contract_Module_Type_ID
-- 2023-06-07T13:54:36.143594040Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617955
;

-- 2023-06-07T13:54:36.146355296Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550772
;

-- 2023-06-07T13:54:36.148459808Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=546862
;

-- 2023-06-07T13:54:36.149447810Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=545618
;

-- 2023-06-07T13:54:36.151674619Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=545618
;

-- UI Column: Vertragsbaustein Typ(541710,D) -> Contract Module Type(547011,D) -> main -> 10
-- UI Element Group: infos
-- 2023-06-07T14:05:07.104835411Z
UPDATE AD_UI_ElementGroup SET Name='infos', UIStyle='primary',Updated=TO_TIMESTAMP('2023-06-07 15:05:07.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550768
;