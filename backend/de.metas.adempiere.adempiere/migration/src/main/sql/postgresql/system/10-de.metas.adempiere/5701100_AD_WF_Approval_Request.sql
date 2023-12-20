-- Table: AD_WF_Approval_Request
-- 2023-08-31T13:21:31.093044300Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542364,'N',TO_TIMESTAMP('2023-08-31 16:21:30.67','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','N','N','Y','N','N','N',0,'Workflow Approval Request','NP','L','AD_WF_Approval_Request','DTI',TO_TIMESTAMP('2023-08-31 16:21:30.67','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:31.095649600Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542364 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-08-31T13:21:31.646055400Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556301,TO_TIMESTAMP('2023-08-31 16:21:31.475','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table AD_WF_Approval_Request',1,'Y','N','Y','Y','AD_WF_Approval_Request','N',1000000,TO_TIMESTAMP('2023-08-31 16:21:31.475','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:21:31.759406Z
CREATE SEQUENCE AD_WF_APPROVAL_REQUEST_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_WF_Approval_Request.AD_Client_ID
-- 2023-08-31T13:21:44.493998700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587352,102,0,19,542364,'AD_Client_ID',TO_TIMESTAMP('2023-08-31 16:21:44.344','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-08-31 16:21:44.344','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:44.497189100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587352 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:45.153242500Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_WF_Approval_Request.AD_Org_ID
-- 2023-08-31T13:21:45.946926800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587353,113,0,30,542364,'AD_Org_ID',TO_TIMESTAMP('2023-08-31 16:21:45.612','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-08-31 16:21:45.612','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:45.949007900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587353 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:46.540013800Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_WF_Approval_Request.Created
-- 2023-08-31T13:21:47.188843300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587354,245,0,16,542364,'Created',TO_TIMESTAMP('2023-08-31 16:21:46.929','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-08-31 16:21:46.929','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:47.191470900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587354 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:47.773864900Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_WF_Approval_Request.CreatedBy
-- 2023-08-31T13:21:48.457975700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587355,246,0,18,110,542364,'CreatedBy',TO_TIMESTAMP('2023-08-31 16:21:48.17','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-08-31 16:21:48.17','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:48.460088Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587355 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:48.986933600Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_WF_Approval_Request.IsActive
-- 2023-08-31T13:21:50.021433400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587356,348,0,20,542364,'IsActive',TO_TIMESTAMP('2023-08-31 16:21:49.678','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-08-31 16:21:49.678','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:50.025315200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587356 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:50.758559600Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_WF_Approval_Request.Updated
-- 2023-08-31T13:21:51.635287700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587357,607,0,16,542364,'Updated',TO_TIMESTAMP('2023-08-31 16:21:51.275','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-08-31 16:21:51.275','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:51.639058800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587357 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:52.285946400Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_WF_Approval_Request.UpdatedBy
-- 2023-08-31T13:21:53.171990700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587358,608,0,18,110,542364,'UpdatedBy',TO_TIMESTAMP('2023-08-31 16:21:52.798','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-08-31 16:21:52.798','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:53.175777400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587358 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:53.808933800Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-08-31T13:21:54.355167400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582675,0,'AD_WF_Approval_Request_ID',TO_TIMESTAMP('2023-08-31 16:21:54.262','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Workflow Approval Request','Workflow Approval Request',TO_TIMESTAMP('2023-08-31 16:21:54.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:21:54.357284500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582675 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_WF_Approval_Request.AD_WF_Approval_Request_ID
-- 2023-08-31T13:21:55.161123400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587359,582675,0,13,542364,'AD_WF_Approval_Request_ID',TO_TIMESTAMP('2023-08-31 16:21:54.259','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Workflow Approval Request',0,0,TO_TIMESTAMP('2023-08-31 16:21:54.259','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:21:55.163236900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587359 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:21:55.821375700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582675) 
;

-- Column: AD_WF_Approval_Request.AD_Table_ID
-- 2023-08-31T13:26:44.223049100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587360,126,0,30,542364,'AD_Table_ID',TO_TIMESTAMP('2023-08-31 16:26:44.084','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'DB-Tabelle',0,0,TO_TIMESTAMP('2023-08-31 16:26:44.084','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:26:44.225137900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587360 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:26:44.726711400Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- Column: AD_WF_Approval_Request.Record_ID
-- 2023-08-31T13:28:52.097014700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587361,538,0,13,542364,'Record_ID',TO_TIMESTAMP('2023-08-31 16:28:51.958','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Direct internal record ID','D',0,10,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Datensatz-ID',0,0,TO_TIMESTAMP('2023-08-31 16:28:51.958','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:28:52.099104500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587361 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:28:52.659428600Z
/* DDL */  select update_Column_Translation_From_AD_Element(538) 
;

-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-08-31T13:29:42.851182800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587362,138,0,30,542364,540606,'AD_User_ID',TO_TIMESTAMP('2023-08-31 16:29:42.703','YYYY-MM-DD HH24:MI:SS.US'),100,'N','User within the system - Internal or Business Partner Contact','D',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2023-08-31 16:29:42.703','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:29:42.853279400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587362 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:29:43.411793800Z
/* DDL */  select update_Column_Translation_From_AD_Element(138) 
;

-- Name: AD_WF_Approval_Request_Status
-- 2023-08-31T13:31:00.534045900Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541819,TO_TIMESTAMP('2023-08-31 16:31:00.399','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','AD_WF_Approval_Request_Status',TO_TIMESTAMP('2023-08-31 16:31:00.399','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-08-31T13:31:00.535612600Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541819 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_WF_Approval_Request_Status
-- Value: P
-- ValueName: Pending
-- 2023-08-31T13:31:12.993943600Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541819,543545,TO_TIMESTAMP('2023-08-31 16:31:12.858','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Pending',TO_TIMESTAMP('2023-08-31 16:31:12.858','YYYY-MM-DD HH24:MI:SS.US'),100,'P','Pending')
;

-- 2023-08-31T13:31:12.996027200Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543545 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_WF_Approval_Request_Status
-- Value: A
-- ValueName: Approved
-- 2023-08-31T13:31:24.357294900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541819,543546,TO_TIMESTAMP('2023-08-31 16:31:24.228','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Approved',TO_TIMESTAMP('2023-08-31 16:31:24.228','YYYY-MM-DD HH24:MI:SS.US'),100,'A','Approved')
;

-- 2023-08-31T13:31:24.358856900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543546 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_WF_Approval_Request_Status
-- Value: R
-- ValueName: Rejected
-- 2023-08-31T13:31:35.269441300Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541819,543547,TO_TIMESTAMP('2023-08-31 16:31:35.133','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rejected',TO_TIMESTAMP('2023-08-31 16:31:35.133','YYYY-MM-DD HH24:MI:SS.US'),100,'R','Rejected')
;

-- 2023-08-31T13:31:35.271530500Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543547 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_WF_Approval_Request.Status
-- 2023-08-31T13:31:52.892200200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587363,3020,0,17,541819,542364,'Status',TO_TIMESTAMP('2023-08-31 16:31:52.685','YYYY-MM-DD HH24:MI:SS.US'),100,'N','P','','D',0,1,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Status',0,0,TO_TIMESTAMP('2023-08-31 16:31:52.685','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:31:52.894812500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587363 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:31:53.401934800Z
/* DDL */  select update_Column_Translation_From_AD_Element(3020) 
;

-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-08-31T13:32:12.230157300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587364,290,0,10,542364,'DocumentNo',TO_TIMESTAMP('2023-08-31 16:32:12.084','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Document sequence number of the document','D',0,40,'E','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','Y',0,'Nr.',0,1,TO_TIMESTAMP('2023-08-31 16:32:12.084','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:32:12.232777500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587364 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:32:12.813088400Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-08-31T13:33:15.605133800Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2023-08-31 16:33:15.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587364
;

-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-08-31T13:33:32.139760300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587365,865,0,17,183,542364,'DocBaseType',TO_TIMESTAMP('2023-08-31 16:33:31.92','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,3,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Dokument Basis Typ',0,0,TO_TIMESTAMP('2023-08-31 16:33:31.92','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:33:32.141855300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587365 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:33:32.701748300Z
/* DDL */  select update_Column_Translation_From_AD_Element(865) 
;

-- Column: AD_WF_Approval_Request.AD_WF_Activity_ID
-- 2023-08-31T13:33:55.914033Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587366,2307,0,30,542364,'AD_WF_Activity_ID',TO_TIMESTAMP('2023-08-31 16:33:55.774','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Workflow-Aktivität','D',0,10,'The Workflow Activity is the actual Workflow Node in a Workflow Process instance','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Workflow-Aktivität',0,0,TO_TIMESTAMP('2023-08-31 16:33:55.774','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:33:55.916118500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587366 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:33:56.430586Z
/* DDL */  select update_Column_Translation_From_AD_Element(2307) 
;

-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-08-31T13:36:17.280241800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587367,2389,0,16,542364,'DateResponse',TO_TIMESTAMP('2023-08-31 16:36:17.132','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum der Antwort','D',0,7,'Datum der Antwort','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Datum Antwort',0,0,TO_TIMESTAMP('2023-08-31 16:36:17.132','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:36:17.282320900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587367 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:36:17.857401500Z
/* DDL */  select update_Column_Translation_From_AD_Element(2389) 
;

-- 2023-08-31T13:37:02.849402100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582676,0,'DateRequest',TO_TIMESTAMP('2023-08-31 16:37:02.713','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Request Date','Request Date',TO_TIMESTAMP('2023-08-31 16:37:02.713','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:37:02.851485600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582676 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-08-31T13:37:17.280905600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587368,582676,0,16,542364,'DateRequest',TO_TIMESTAMP('2023-08-31 16:37:17.149','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Request Date',0,0,TO_TIMESTAMP('2023-08-31 16:37:17.149','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:37:17.282981500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587368 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:37:17.867014100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582676) 
;

-- Column: AD_WF_Approval_Request.AD_WF_Process_ID
-- 2023-08-31T13:38:33.240282400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587369,2312,0,30,542364,'AD_WF_Process_ID',TO_TIMESTAMP('2023-08-31 16:38:33.082','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Actual Workflow Process Instance','D',0,10,'Instance of a workflow execution','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Workflow-Prozess',0,0,TO_TIMESTAMP('2023-08-31 16:38:33.082','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T13:38:33.242355600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587369 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T13:38:33.761991400Z
/* DDL */  select update_Column_Translation_From_AD_Element(2312) 
;

-- Element: AD_WF_Approval_Request_ID
-- 2023-08-31T13:43:01.174377700Z
UPDATE AD_Element_Trl SET Name='Approval Requests', PrintName='Approval Requests',Updated=TO_TIMESTAMP('2023-08-31 16:43:01.174','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582675 AND AD_Language='de_CH'
;

-- 2023-08-31T13:43:01.180086700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582675,'de_CH') 
;

-- Element: AD_WF_Approval_Request_ID
-- 2023-08-31T13:43:03.972879500Z
UPDATE AD_Element_Trl SET Name='Approval Requests', PrintName='Approval Requests',Updated=TO_TIMESTAMP('2023-08-31 16:43:03.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582675 AND AD_Language='de_DE'
;

-- 2023-08-31T13:43:03.975481500Z
UPDATE AD_Element SET Name='Approval Requests', PrintName='Approval Requests' WHERE AD_Element_ID=582675
;

-- 2023-08-31T13:43:04.312253200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582675,'de_DE') 
;

-- 2023-08-31T13:43:04.314325800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582675,'de_DE') 
;

-- Element: AD_WF_Approval_Request_ID
-- 2023-08-31T13:43:07.793440600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Approval Requests', PrintName='Approval Requests',Updated=TO_TIMESTAMP('2023-08-31 16:43:07.793','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582675 AND AD_Language='en_US'
;

-- 2023-08-31T13:43:07.796553200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582675,'en_US') 
;

-- Element: AD_WF_Approval_Request_ID
-- 2023-08-31T13:43:11.546397300Z
UPDATE AD_Element_Trl SET Name='Approval Requests', PrintName='Approval Requests',Updated=TO_TIMESTAMP('2023-08-31 16:43:11.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582675 AND AD_Language='fr_CH'
;

-- 2023-08-31T13:43:11.550031300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582675,'fr_CH') 
;

-- Window: Workflow Approval Request, InternalName=null
-- 2023-08-31T13:43:22.287078200Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582675,0,541730,TO_TIMESTAMP('2023-08-31 16:43:22.142','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Approval Requests','N',TO_TIMESTAMP('2023-08-31 16:43:22.142','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-08-31T13:43:22.288631300Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541730 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-08-31T13:43:22.291232900Z
/* DDL */  select update_window_translation_from_ad_element(582675) 
;

-- 2023-08-31T13:43:22.294879600Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541730
;

-- 2023-08-31T13:43:22.295923500Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541730)
;

-- Tab: Approval Requests(541730,D) -> Approval Requests
-- Table: AD_WF_Approval_Request
-- 2023-08-31T13:43:50.443416500Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582675,0,547209,542364,541730,'Y',TO_TIMESTAMP('2023-08-31 16:43:49.307','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','AD_WF_Approval_Request','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Approval Requests','N',10,0,TO_TIMESTAMP('2023-08-31 16:43:49.307','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:43:50.445505800Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547209 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-08-31T13:43:50.448102700Z
/* DDL */  select update_tab_translation_from_ad_element(582675) 
;

-- 2023-08-31T13:43:50.451767400Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547209)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Mandant
-- Column: AD_WF_Approval_Request.AD_Client_ID
-- 2023-08-31T13:44:11.240855500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587352,720323,0,547209,TO_TIMESTAMP('2023-08-31 16:44:11.095','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-08-31 16:44:11.095','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:11.243455600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:11.245022800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-08-31T13:44:11.325911700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720323
;

-- 2023-08-31T13:44:11.327701700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720323)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Organisation
-- Column: AD_WF_Approval_Request.AD_Org_ID
-- 2023-08-31T13:44:11.444971900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587353,720324,0,547209,TO_TIMESTAMP('2023-08-31 16:44:11.332','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-08-31 16:44:11.332','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:11.447073100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:11.448647100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-08-31T13:44:11.517081Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720324
;

-- 2023-08-31T13:44:11.518700700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720324)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Aktiv
-- Column: AD_WF_Approval_Request.IsActive
-- 2023-08-31T13:44:11.628927500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587356,720325,0,547209,TO_TIMESTAMP('2023-08-31 16:44:11.521','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-08-31 16:44:11.521','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:11.631614100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:11.633170700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-08-31T13:44:11.678513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720325
;

-- 2023-08-31T13:44:11.679654400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720325)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Approval Requests
-- Column: AD_WF_Approval_Request.AD_WF_Approval_Request_ID
-- 2023-08-31T13:44:11.790822500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587359,720326,0,547209,TO_TIMESTAMP('2023-08-31 16:44:11.683','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Approval Requests',TO_TIMESTAMP('2023-08-31 16:44:11.683','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:11.792902200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:11.795025300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582675) 
;

-- 2023-08-31T13:44:11.797619900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720326
;

-- 2023-08-31T13:44:11.798146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720326)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> DB-Tabelle
-- Column: AD_WF_Approval_Request.AD_Table_ID
-- 2023-08-31T13:44:11.908178900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587360,720327,0,547209,TO_TIMESTAMP('2023-08-31 16:44:11.801','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2023-08-31 16:44:11.801','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:11.910266100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:11.911816200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2023-08-31T13:44:11.920610400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720327
;

-- 2023-08-31T13:44:11.921643300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720327)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Datensatz-ID
-- Column: AD_WF_Approval_Request.Record_ID
-- 2023-08-31T13:44:12.030414900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587361,720328,0,547209,TO_TIMESTAMP('2023-08-31 16:44:11.924','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID',10,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2023-08-31 16:44:11.924','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.032505400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.034581100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2023-08-31T13:44:12.043919500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720328
;

-- 2023-08-31T13:44:12.044454500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720328)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Ansprechpartner
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-08-31T13:44:12.151705400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587362,720329,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.047','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact',10,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2023-08-31 16:44:12.047','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.153791500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.155357600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2023-08-31T13:44:12.160540Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720329
;

-- 2023-08-31T13:44:12.161585600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720329)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Status
-- Column: AD_WF_Approval_Request.Status
-- 2023-08-31T13:44:12.271921500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587363,720330,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.164','YYYY-MM-DD HH24:MI:SS.US'),100,'',1,'D','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2023-08-31 16:44:12.164','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.274019900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.275578100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2023-08-31T13:44:12.279202Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720330
;

-- 2023-08-31T13:44:12.280243400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720330)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Nr.
-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-08-31T13:44:12.392260700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587364,720331,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.282','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document',255,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2023-08-31 16:44:12.282','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.394352400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.395915700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2023-08-31T13:44:12.401118500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720331
;

-- 2023-08-31T13:44:12.402174400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720331)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Dokument Basis Typ
-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-08-31T13:44:12.503216100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587365,720332,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.405','YYYY-MM-DD HH24:MI:SS.US'),100,'',3,'D','','Y','N','N','N','N','N','N','N','Dokument Basis Typ',TO_TIMESTAMP('2023-08-31 16:44:12.405','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.505332400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.506903400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(865) 
;

-- 2023-08-31T13:44:12.510594300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720332
;

-- 2023-08-31T13:44:12.511645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720332)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Workflow-Aktivität
-- Column: AD_WF_Approval_Request.AD_WF_Activity_ID
-- 2023-08-31T13:44:12.604918100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587366,720333,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.514','YYYY-MM-DD HH24:MI:SS.US'),100,'Workflow-Aktivität',10,'D','The Workflow Activity is the actual Workflow Node in a Workflow Process instance','Y','N','N','N','N','N','N','N','Workflow-Aktivität',TO_TIMESTAMP('2023-08-31 16:44:12.514','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.607029700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.608616100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2307) 
;

-- 2023-08-31T13:44:12.611257600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720333
;

-- 2023-08-31T13:44:12.612314900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720333)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-08-31T13:44:12.720497200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587367,720334,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.614','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum der Antwort',7,'D','Datum der Antwort','Y','N','N','N','N','N','N','N','Datum Antwort',TO_TIMESTAMP('2023-08-31 16:44:12.614','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.723266400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.725416800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2389) 
;

-- 2023-08-31T13:44:12.728064800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720334
;

-- 2023-08-31T13:44:12.729111100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720334)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Request Date
-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-08-31T13:44:12.842986300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587368,720335,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.731','YYYY-MM-DD HH24:MI:SS.US'),100,7,'D','Y','N','N','N','N','N','N','N','Request Date',TO_TIMESTAMP('2023-08-31 16:44:12.731','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.844546900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.846622600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582676) 
;

-- 2023-08-31T13:44:12.849208500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720335
;

-- 2023-08-31T13:44:12.849762400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720335)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Workflow-Prozess
-- Column: AD_WF_Approval_Request.AD_WF_Process_ID
-- 2023-08-31T13:44:12.960365200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587369,720336,0,547209,TO_TIMESTAMP('2023-08-31 16:44:12.852','YYYY-MM-DD HH24:MI:SS.US'),100,'Actual Workflow Process Instance',10,'D','Instance of a workflow execution','Y','N','N','N','N','N','N','N','Workflow-Prozess',TO_TIMESTAMP('2023-08-31 16:44:12.852','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:44:12.962468700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720336 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T13:44:12.964560900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2312) 
;

-- 2023-08-31T13:44:12.967674900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720336
;

-- 2023-08-31T13:44:12.968197800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720336)
;

-- Tab: Approval Requests(541730,D) -> Approval Requests(547209,D)
-- UI Section: main
-- 2023-08-31T13:44:22.858199600Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547209,545803,TO_TIMESTAMP('2023-08-31 16:44:22.718','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-31 16:44:22.718','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-08-31T13:44:22.859774800Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545803 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main
-- UI Column: 10
-- 2023-08-31T13:45:03.232374200Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547066,545803,TO_TIMESTAMP('2023-08-31 16:45:03.097','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-31 16:45:03.097','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main
-- UI Column: 20
-- 2023-08-31T13:45:04.874487700Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547067,545803,TO_TIMESTAMP('2023-08-31 16:45:04.778','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-08-31 16:45:04.778','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10
-- UI Element Group: document info
-- 2023-08-31T13:45:20.427098100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547066,551118,TO_TIMESTAMP('2023-08-31 16:45:20.298','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','document info',10,TO_TIMESTAMP('2023-08-31 16:45:20.298','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> document info.Nr.
-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-08-31T13:45:45.779953500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720331,0,547209,551118,620387,'F',TO_TIMESTAMP('2023-08-31 16:45:45.636','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Nr.',10,0,0,TO_TIMESTAMP('2023-08-31 16:45:45.636','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> document info.Dokument Basis Typ
-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-08-31T13:45:53.490860800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720332,0,547209,551118,620388,'F',TO_TIMESTAMP('2023-08-31 16:45:53.356','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','Y','N','N','Dokument Basis Typ',20,0,0,TO_TIMESTAMP('2023-08-31 16:45:53.356','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> document info.DB-Tabelle
-- Column: AD_WF_Approval_Request.AD_Table_ID
-- 2023-08-31T13:46:04.969956400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720327,0,547209,551118,620389,'F',TO_TIMESTAMP('2023-08-31 16:46:03.816','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','N','N','DB-Tabelle',30,0,0,TO_TIMESTAMP('2023-08-31 16:46:03.816','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> document info.Datensatz-ID
-- Column: AD_WF_Approval_Request.Record_ID
-- 2023-08-31T13:46:11.936044400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720328,0,547209,551118,620390,'F',TO_TIMESTAMP('2023-08-31 16:46:11.78','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','Datensatz-ID',40,0,0,TO_TIMESTAMP('2023-08-31 16:46:11.78','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10
-- UI Element Group: workflow ref
-- 2023-08-31T13:46:19.635562600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547066,551119,TO_TIMESTAMP('2023-08-31 16:46:19.509','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','workflow ref',20,TO_TIMESTAMP('2023-08-31 16:46:19.509','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> workflow ref.Workflow-Prozess
-- Column: AD_WF_Approval_Request.AD_WF_Process_ID
-- 2023-08-31T13:46:34.164837900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720336,0,547209,551119,620391,'F',TO_TIMESTAMP('2023-08-31 16:46:33.014','YYYY-MM-DD HH24:MI:SS.US'),100,'Actual Workflow Process Instance','Instance of a workflow execution','Y','N','Y','N','N','Workflow-Prozess',10,0,0,TO_TIMESTAMP('2023-08-31 16:46:33.014','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> workflow ref.Workflow-Aktivität
-- Column: AD_WF_Approval_Request.AD_WF_Activity_ID
-- 2023-08-31T13:46:40.406815Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720333,0,547209,551119,620392,'F',TO_TIMESTAMP('2023-08-31 16:46:40.255','YYYY-MM-DD HH24:MI:SS.US'),100,'Workflow-Aktivität','The Workflow Activity is the actual Workflow Node in a Workflow Process instance','Y','N','Y','N','N','Workflow-Aktivität',20,0,0,TO_TIMESTAMP('2023-08-31 16:46:40.255','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20
-- UI Element Group: date & status
-- 2023-08-31T13:47:30.368941700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547067,551120,TO_TIMESTAMP('2023-08-31 16:47:30.235','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','date & status',10,TO_TIMESTAMP('2023-08-31 16:47:30.235','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Request Date
-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-08-31T13:47:51.913146Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720335,0,547209,551120,620393,'F',TO_TIMESTAMP('2023-08-31 16:47:51.767','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Request Date',10,0,0,TO_TIMESTAMP('2023-08-31 16:47:51.767','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-08-31T13:48:07.482605100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720334,0,547209,551120,620394,'F',TO_TIMESTAMP('2023-08-31 16:48:06.338','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum der Antwort','Datum der Antwort','Y','N','Y','N','N','Datum Antwort',20,0,0,TO_TIMESTAMP('2023-08-31 16:48:06.338','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Ansprechpartner
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-08-31T13:48:21.650927200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720329,0,547209,551120,620395,'F',TO_TIMESTAMP('2023-08-31 16:48:21.494','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','Ansprechpartner',30,0,0,TO_TIMESTAMP('2023-08-31 16:48:21.494','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Status
-- Column: AD_WF_Approval_Request.Status
-- 2023-08-31T13:48:28.239702700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720330,0,547209,551120,620396,'F',TO_TIMESTAMP('2023-08-31 16:48:28.057','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','Y','N','N','Status',40,0,0,TO_TIMESTAMP('2023-08-31 16:48:28.057','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-08-31T13:48:51.459919100Z
UPDATE AD_Field SET DisplayLogic='@DateResponse/@!''''',Updated=TO_TIMESTAMP('2023-08-31 16:48:51.459','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720334
;

-- Window: Approval Requests, InternalName=wfApprovalRequests
-- 2023-08-31T13:50:34.454464500Z
UPDATE AD_Window SET InternalName='wfApprovalRequests',Updated=TO_TIMESTAMP('2023-08-31 16:50:34.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541730
;

-- Name: Approval Requests
-- Action Type: W
-- Window: Approval Requests(541730,D)
-- 2023-08-31T13:50:40.233837300Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582675,542110,0,541730,TO_TIMESTAMP('2023-08-31 16:50:40.089','YYYY-MM-DD HH24:MI:SS.US'),100,'D','wfApprovalRequests','Y','N','N','N','N','Approval Requests',TO_TIMESTAMP('2023-08-31 16:50:40.089','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T13:50:40.235914900Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542110 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-08-31T13:50:40.238012300Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542110, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542110)
;

-- 2023-08-31T13:50:40.247837200Z
/* DDL */  select update_menu_translation_from_ad_element(582675) 
;

-- Reordering children of `System`
-- Node name: `Costing (Freight etc)`
-- 2023-08-31T13:50:48.494612400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2023-08-31T13:50:48.495674400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `External system config eBay (ExternalSystem_Config_Ebay)`
-- 2023-08-31T13:50:48.496718400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541925 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2023-08-31T13:50:48.497754600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2023-08-31T13:50:48.498793200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2023-08-31T13:50:48.499835300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2023-08-31T13:50:48.500879100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2023-08-31T13:50:48.501927700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2023-08-31T13:50:48.502977700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2023-08-31T13:50:48.504023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2023-08-31T13:50:48.505074500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2023-08-31T13:50:48.506115500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2023-08-31T13:50:48.507159Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2023-08-31T13:50:48.508203500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2023-08-31T13:50:48.509246700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2023-08-31T13:50:48.510316800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2023-08-31T13:50:48.511360700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2023-08-31T13:50:48.512399100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2023-08-31T13:50:48.513446Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2023-08-31T13:50:48.514492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2023-08-31T13:50:48.515520100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2023-08-31T13:50:48.516058Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2023-08-31T13:50:48.517101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2023-08-31T13:50:48.518149Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2023-08-31T13:50:48.519193Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2023-08-31T13:50:48.520239900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2023-08-31T13:50:48.521282700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2023-08-31T13:50:48.522327600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2023-08-31T13:50:48.523371300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2023-08-31T13:50:48.524419200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2023-08-31T13:50:48.525486700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2023-08-31T13:50:48.526532900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2023-08-31T13:50:48.527573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2023-08-31T13:50:48.528093300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2023-08-31T13:50:48.529130Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2023-08-31T13:50:48.530170500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2023-08-31T13:50:48.531210900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2023-08-31T13:50:48.532256200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2023-08-31T13:50:48.533301300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2023-08-31T13:50:48.534340700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2023-08-31T13:50:48.534860700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2023-08-31T13:50:48.535909900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2023-08-31T13:50:48.536958500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2023-08-31T13:50:48.538005300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2023-08-31T13:50:48.539055600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2023-08-31T13:50:48.539575600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2023-08-31T13:50:48.540646Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2023-08-31T13:50:48.541683400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2023-08-31T13:50:48.542730900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2023-08-31T13:50:48.543772700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2023-08-31T13:50:48.544818500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2023-08-31T13:50:48.545861900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2023-08-31T13:50:48.546907600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2023-08-31T13:50:48.547949600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2023-08-31T13:50:48.548992400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2023-08-31T13:50:48.549512700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2023-08-31T13:50:48.550557700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2023-08-31T13:50:48.551603900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2023-08-31T13:50:48.552650Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2023-08-31T13:50:48.553695400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2023-08-31T13:50:48.554750300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2023-08-31T13:50:48.555840400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2023-08-31T13:50:48.557422500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2023-08-31T13:50:48.558473900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2023-08-31T13:50:48.559522400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2023-08-31T13:50:48.560563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2023-08-31T13:50:48.561610Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-08-31T13:50:48.562654600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-08-31T13:50:48.563694500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-08-31T13:50:48.564741800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2023-08-31T13:50:48.565788500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2023-08-31T13:50:48.566809300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2023-08-31T13:50:48.567866300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2023-08-31T13:50:48.568913700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2023-08-31T13:50:48.569961800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2023-08-31T13:50:48.571557Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Change System Base Language (de.metas.process.ExecuteUpdateSQL)`
-- 2023-08-31T13:50:48.572608200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541973 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2023-08-31T13:50:48.574182900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2023-08-31T13:50:48.576292400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2023-08-31T13:50:48.577923900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `Remove documents between dates (de.metas.process.ExecuteUpdateSQL)`
-- 2023-08-31T13:50:48.578965600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541926 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2023-08-31T13:50:48.581621100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2023-08-31T13:50:48.584674500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Import Invoice Candidate (I_Invoice_Candidate)`
-- 2023-08-31T13:50:48.588480300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541996 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2023-08-31T13:50:48.592957300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2023-08-31T13:50:48.611093300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `External system config SAP (ExternalSystem_Config_SAP)`
-- 2023-08-31T13:50:48.619842800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542022 AND AD_Tree_ID=10
;

-- Node name: `Approval Requests`
-- 2023-08-31T13:50:48.629749900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542110 AND AD_Tree_ID=10
;

-- 2023-08-31T13:52:59.354331100Z
/* DDL */ CREATE TABLE public.AD_WF_Approval_Request (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, AD_User_ID NUMERIC(10) NOT NULL, AD_WF_Activity_ID NUMERIC(10), AD_WF_Approval_Request_ID NUMERIC(10) NOT NULL, AD_WF_Process_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateRequest TIMESTAMP WITH TIME ZONE NOT NULL, DateResponse TIMESTAMP WITH TIME ZONE, DocBaseType VARCHAR(3), DocumentNo VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Record_ID NUMERIC(10) NOT NULL, Status CHAR(1) DEFAULT 'P' NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADTable_ADWFApprovalRequest FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ADUser_ADWFApprovalRequest FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ADWFActivity_ADWFApprovalRequest FOREIGN KEY (AD_WF_Activity_ID) REFERENCES public.AD_WF_Activity DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_WF_Approval_Request_Key PRIMARY KEY (AD_WF_Approval_Request_ID), CONSTRAINT ADWFProcess_ADWFApprovalRequest FOREIGN KEY (AD_WF_Process_ID) REFERENCES public.AD_WF_Process DEFERRABLE INITIALLY DEFERRED)
;

-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-08-31T18:42:04.738098900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587370,566,0,11,542364,'SeqNo',TO_TIMESTAMP('2023-08-31 21:42:04.056','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2023-08-31 21:42:04.056','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-31T18:42:04.741226500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587370 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-31T18:42:05.359341700Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2023-08-31T18:42:07.730196Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Approval_Request','ALTER TABLE public.AD_WF_Approval_Request ADD COLUMN SeqNo NUMERIC(10) NOT NULL')
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-08-31T18:42:18.960120500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587370,720354,0,547209,TO_TIMESTAMP('2023-08-31 21:42:18.809','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2023-08-31 21:42:18.809','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-31T18:42:18.962204100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-31T18:42:18.964280100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2023-08-31T18:42:18.971018900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720354
;

-- 2023-08-31T18:42:18.972059700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720354)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-08-31T18:42:53.694992400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720354,0,547209,551120,620409,'F',TO_TIMESTAMP('2023-08-31 21:42:53.494','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','Reihenfolge',50,0,0,TO_TIMESTAMP('2023-08-31 21:42:53.494','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-08-31T18:43:09.113438400Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2023-08-31 21:43:09.113','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620409
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> document info.Dokument Basis Typ
-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-08-31T18:44:00.817540500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-08-31 21:44:00.817','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620388
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 10 -> document info.Nr.
-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-08-31T18:44:00.826439500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-08-31 21:44:00.826','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620387
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-08-31T18:44:00.834765200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-08-31 21:44:00.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620409
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Ansprechpartner
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-08-31T18:44:00.842537300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-08-31 21:44:00.842','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620395
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Status
-- Column: AD_WF_Approval_Request.Status
-- 2023-08-31T18:44:00.850344300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-08-31 21:44:00.85','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620396
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Request Date
-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-08-31T18:44:00.857619600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-08-31 21:44:00.857','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620393
;

-- UI Element: Approval Requests(541730,D) -> Approval Requests(547209,D) -> main -> 20 -> date & status.Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-08-31T18:44:00.865473500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-08-31 21:44:00.865','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620394
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Dokument Basis Typ
-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-08-31T18:44:28.579883700Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2023-08-31 21:44:28.579','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720332
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Nr.
-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-08-31T18:44:37.701946200Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2023-08-31 21:44:37.701','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720331
;

-- Field: Approval Requests(541730,D) -> Approval Requests(547209,D) -> Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-08-31T18:44:54.592864600Z
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2023-08-31 21:44:54.592','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720354
;

-- Column: AD_WF_Approval_Request.Status
-- 2023-08-31T18:45:41.491789500Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-31 21:45:41.491','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587363
;

-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-08-31T18:45:55.732957600Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-31 21:45:55.732','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587365
;

-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-08-31T18:46:08.916357600Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-31 21:46:08.916','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587362
;

-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-08-31T19:57:40.995980500Z
UPDATE AD_Column SET IsUseDocSequence='N',Updated=TO_TIMESTAMP('2023-08-31 22:57:40.995','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587364
;

