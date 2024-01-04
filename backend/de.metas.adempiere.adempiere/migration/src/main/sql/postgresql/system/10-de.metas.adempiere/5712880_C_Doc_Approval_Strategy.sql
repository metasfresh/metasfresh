-- Table: C_Doc_Approval_Strategy
-- 2023-12-07T09:10:06.091017300Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542380,'N',TO_TIMESTAMP('2023-12-07 11:10:05.796','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'Document Approval Strategy','NP','L','C_Doc_Approval_Strategy','DTI',TO_TIMESTAMP('2023-12-07 11:10:05.796','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:06.098331800Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542380 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-12-07T09:10:06.622776Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556318,TO_TIMESTAMP('2023-12-07 11:10:06.523','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_Doc_Approval_Strategy',1,'Y','N','Y','Y','C_Doc_Approval_Strategy','N',1000000,TO_TIMESTAMP('2023-12-07 11:10:06.523','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:10:06.641667800Z
CREATE SEQUENCE C_DOC_APPROVAL_STRATEGY_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Doc_Approval_Strategy.AD_Client_ID
-- 2023-12-07T09:10:12.915773900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587690,102,0,19,542380,'AD_Client_ID',TO_TIMESTAMP('2023-12-07 11:10:12.746','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-12-07 11:10:12.746','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:12.918378Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587690 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:13.550820400Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Doc_Approval_Strategy.AD_Org_ID
-- 2023-12-07T09:10:14.425459Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587691,113,0,30,542380,'AD_Org_ID',TO_TIMESTAMP('2023-12-07 11:10:14.029','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-12-07 11:10:14.029','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:14.427571500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587691 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:15.025282400Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Doc_Approval_Strategy.Created
-- 2023-12-07T09:10:15.778638900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587692,245,0,16,542380,'Created',TO_TIMESTAMP('2023-12-07 11:10:15.465','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-12-07 11:10:15.465','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:15.781240300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587692 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:16.358074200Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Doc_Approval_Strategy.CreatedBy
-- 2023-12-07T09:10:17.133962100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587693,246,0,18,110,542380,'CreatedBy',TO_TIMESTAMP('2023-12-07 11:10:16.809','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-12-07 11:10:16.809','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:17.136041500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:17.793748300Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Doc_Approval_Strategy.IsActive
-- 2023-12-07T09:10:18.534212400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587694,348,0,20,542380,'IsActive',TO_TIMESTAMP('2023-12-07 11:10:18.222','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-12-07 11:10:18.222','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:18.536805600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587694 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:19.202316300Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Doc_Approval_Strategy.Updated
-- 2023-12-07T09:10:19.933470200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587695,607,0,16,542380,'Updated',TO_TIMESTAMP('2023-12-07 11:10:19.622','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-12-07 11:10:19.622','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:19.935598500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587695 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:20.595971400Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Doc_Approval_Strategy.UpdatedBy
-- 2023-12-07T09:10:21.352707700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587696,608,0,18,110,542380,'UpdatedBy',TO_TIMESTAMP('2023-12-07 11:10:21.026','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-12-07 11:10:21.026','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:21.354794100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587696 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:22.229068900Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-12-07T09:10:23.010646600Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582841,0,'C_Doc_Approval_Strategy_ID',TO_TIMESTAMP('2023-12-07 11:10:22.911','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Document Approval Strategy','Document Approval Strategy',TO_TIMESTAMP('2023-12-07 11:10:22.911','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:10:23.013275Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582841 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Doc_Approval_Strategy.C_Doc_Approval_Strategy_ID
-- 2023-12-07T09:10:23.855669600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587697,582841,0,13,542380,'C_Doc_Approval_Strategy_ID',TO_TIMESTAMP('2023-12-07 11:10:22.903','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Document Approval Strategy',0,0,TO_TIMESTAMP('2023-12-07 11:10:22.903','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:10:23.858827Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587697 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:10:24.562077Z
/* DDL */  select update_Column_Translation_From_AD_Element(582841) 
;

-- Column: C_Doc_Approval_Strategy.Name
-- 2023-12-07T09:11:33.947956900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587698,469,0,10,542380,'Name',TO_TIMESTAMP('2023-12-07 11:11:33.805','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-12-07 11:11:33.805','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:11:33.950686200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587698 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:11:34.541451700Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Table: C_Doc_Approval_Strategy_Line
-- 2023-12-07T09:12:32.477058900Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542381,'N',TO_TIMESTAMP('2023-12-07 11:12:32.275','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'Document Approval Strategy Line','NP','L','C_Doc_Approval_Strategy_Line','DTI',TO_TIMESTAMP('2023-12-07 11:12:32.275','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:32.479138300Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542381 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-12-07T09:12:33.020359700Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556319,TO_TIMESTAMP('2023-12-07 11:12:32.917','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_Doc_Approval_Strategy_Line',1,'Y','N','Y','Y','C_Doc_Approval_Strategy_Line','N',1000000,TO_TIMESTAMP('2023-12-07 11:12:32.917','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:12:33.031851500Z
CREATE SEQUENCE C_DOC_APPROVAL_STRATEGY_LINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Doc_Approval_Strategy_Line.AD_Client_ID
-- 2023-12-07T09:12:38.675878500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587699,102,0,19,542381,'AD_Client_ID',TO_TIMESTAMP('2023-12-07 11:12:38.507','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-12-07 11:12:38.507','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:38.678518100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587699 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:39.256976100Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Doc_Approval_Strategy_Line.AD_Org_ID
-- 2023-12-07T09:12:40.019817300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587700,113,0,30,542381,'AD_Org_ID',TO_TIMESTAMP('2023-12-07 11:12:39.691','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-12-07 11:12:39.691','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:40.021902900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587700 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:40.609069400Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Doc_Approval_Strategy_Line.Created
-- 2023-12-07T09:12:41.365505500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587701,245,0,16,542381,'Created',TO_TIMESTAMP('2023-12-07 11:12:41.044','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-12-07 11:12:41.044','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:41.367578Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587701 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:41.985011400Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Doc_Approval_Strategy_Line.CreatedBy
-- 2023-12-07T09:12:42.759686300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587702,246,0,18,110,542381,'CreatedBy',TO_TIMESTAMP('2023-12-07 11:12:42.403','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-12-07 11:12:42.403','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:42.761771200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587702 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:43.353808200Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Doc_Approval_Strategy_Line.IsActive
-- 2023-12-07T09:12:44.184989Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587703,348,0,20,542381,'IsActive',TO_TIMESTAMP('2023-12-07 11:12:43.856','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-12-07 11:12:43.856','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:44.187067600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587703 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:44.809787100Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Doc_Approval_Strategy_Line.Updated
-- 2023-12-07T09:12:45.553665300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587704,607,0,16,542381,'Updated',TO_TIMESTAMP('2023-12-07 11:12:45.265','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-12-07 11:12:45.265','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:45.555741600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587704 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:46.218589500Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Doc_Approval_Strategy_Line.UpdatedBy
-- 2023-12-07T09:12:47.004199100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587705,608,0,18,110,542381,'UpdatedBy',TO_TIMESTAMP('2023-12-07 11:12:46.664','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-12-07 11:12:46.664','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:47.006318500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587705 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:47.623857Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-12-07T09:12:48.218262800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582842,0,'C_Doc_Approval_Strategy_Line_ID',TO_TIMESTAMP('2023-12-07 11:12:48.11','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Document Approval Strategy Line','Document Approval Strategy Line',TO_TIMESTAMP('2023-12-07 11:12:48.11','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:12:48.219827500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582842 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Doc_Approval_Strategy_Line.C_Doc_Approval_Strategy_Line_ID
-- 2023-12-07T09:12:48.969962900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587706,582842,0,13,542381,'C_Doc_Approval_Strategy_Line_ID',TO_TIMESTAMP('2023-12-07 11:12:48.107','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Document Approval Strategy Line',0,0,TO_TIMESTAMP('2023-12-07 11:12:48.107','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:12:48.972043400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587706 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:12:49.582400300Z
/* DDL */  select update_Column_Translation_From_AD_Element(582842) 
;

-- Column: C_Doc_Approval_Strategy_Line.C_Doc_Approval_Strategy_ID
-- 2023-12-07T09:13:16.937439600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587707,582841,0,30,542381,'C_Doc_Approval_Strategy_ID',TO_TIMESTAMP('2023-12-07 11:13:15.79','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Document Approval Strategy',0,0,TO_TIMESTAMP('2023-12-07 11:13:15.79','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:13:16.939005500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587707 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:13:17.563517700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582841) 
;

-- Column: C_Doc_Approval_Strategy_Line.SeqNo
-- 2023-12-07T09:13:48.400828400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587708,566,0,11,542381,'SeqNo',TO_TIMESTAMP('2023-12-07 11:13:48.257','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2023-12-07 11:13:48.257','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:13:48.402910300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587708 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:13:49.143160500Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- Column: C_Doc_Approval_Strategy_Line.Type
-- 2023-12-07T09:14:38.721558200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587709,600,0,17,540751,542381,'Type',TO_TIMESTAMP('2023-12-07 11:14:38.499','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,1,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Art',0,0,TO_TIMESTAMP('2023-12-07 11:14:38.499','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:14:38.724153700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587709 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:14:39.300056400Z
/* DDL */  select update_Column_Translation_From_AD_Element(600) 
;

-- Name: C_Doc_Approval_Strategy_Line_Type
-- 2023-12-07T09:15:02.638047400Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541843,TO_TIMESTAMP('2023-12-07 11:15:02.487','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Doc_Approval_Strategy_Line_Type',TO_TIMESTAMP('2023-12-07 11:15:02.487','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-12-07T09:15:02.641217100Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541843 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: SH
-- ValueName: SupervisorsHierarchy
-- 2023-12-07T09:51:19.869090400Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541843,543601,TO_TIMESTAMP('2023-12-07 11:51:19.727','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Supervisors Hierarchy',TO_TIMESTAMP('2023-12-07 11:51:19.727','YYYY-MM-DD HH24:MI:SS.US'),100,'SH','SupervisorsHierarchy')
;

-- 2023-12-07T09:51:19.871175900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543601 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: PM
-- ValueName: ProjectManager
-- 2023-12-07T09:51:35.757756400Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541843,543602,TO_TIMESTAMP('2023-12-07 11:51:35.621','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Project Manager',TO_TIMESTAMP('2023-12-07 11:51:35.621','YYYY-MM-DD HH24:MI:SS.US'),100,'PM','ProjectManager')
;

-- 2023-12-07T09:51:35.759855300Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543602 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: JOB
-- ValueName: Job
-- 2023-12-07T09:52:04.895130600Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541843,543603,TO_TIMESTAMP('2023-12-07 11:52:04.76','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Position / Job',TO_TIMESTAMP('2023-12-07 11:52:04.76','YYYY-MM-DD HH24:MI:SS.US'),100,'JOB','Job')
;

-- 2023-12-07T09:52:04.896692Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543603 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_Doc_Approval_Strategy_Line.Type
-- 2023-12-07T09:52:33.029972300Z
UPDATE AD_Column SET AD_Reference_Value_ID=541843, FieldLength=3,Updated=TO_TIMESTAMP('2023-12-07 11:52:33.029','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587709
;

-- 2023-12-07T09:53:09.178222400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582843,0,'IsProjectManagerSet',TO_TIMESTAMP('2023-12-07 11:53:09.034','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Is Project Manager Set','Is Project Manager Set',TO_TIMESTAMP('2023-12-07 11:53:09.034','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:53:09.180311500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582843 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Doc_Approval_Strategy_Line.IsProjectManagerSet
-- 2023-12-07T09:53:31.121765Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587710,582843,0,17,540528,542381,'IsProjectManagerSet',TO_TIMESTAMP('2023-12-07 11:53:30.984','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Is Project Manager Set',0,0,TO_TIMESTAMP('2023-12-07 11:53:30.984','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:53:31.123859800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587710 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:53:31.690856500Z
/* DDL */  select update_Column_Translation_From_AD_Element(582843) 
;

-- Column: C_Doc_Approval_Strategy_Line.C_Job_ID
-- 2023-12-07T09:53:48.098987600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587711,2761,0,30,542381,'C_Job_ID',TO_TIMESTAMP('2023-12-07 11:53:47.959','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Position in der Firma','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Position',0,0,TO_TIMESTAMP('2023-12-07 11:53:47.959','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:53:48.101079100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587711 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:53:48.709588200Z
/* DDL */  select update_Column_Translation_From_AD_Element(2761) 
;

-- Column: C_Doc_Approval_Strategy_Line.MinimumAmt
-- 2023-12-07T09:54:31.766846100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587712,2177,0,12,542381,'MinimumAmt',TO_TIMESTAMP('2023-12-07 11:54:31.622','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Minumum Amout in Document Currency','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Minimum Amt',0,0,TO_TIMESTAMP('2023-12-07 11:54:31.622','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:54:31.768938600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587712 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:54:32.354494Z
/* DDL */  select update_Column_Translation_From_AD_Element(2177) 
;

-- Column: C_Doc_Approval_Strategy_Line.C_Currency_ID
-- 2023-12-07T09:54:48.698834400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587713,193,0,30,542381,'C_Currency_ID',TO_TIMESTAMP('2023-12-07 11:54:48.544','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','D',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2023-12-07 11:54:48.544','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T09:54:48.700913100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587713 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T09:54:49.242456300Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: C_Doc_Approval_Strategy_Line.C_Currency_ID
-- 2023-12-07T09:54:59.808217400Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-12-07 11:54:59.808','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587713
;

-- 2023-12-07T09:55:33.320610600Z
/* DDL */ CREATE TABLE public.C_Doc_Approval_Strategy (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Doc_Approval_Strategy_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(255) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Doc_Approval_Strategy_Key PRIMARY KEY (C_Doc_Approval_Strategy_ID))
;

-- 2023-12-07T09:55:40.479821600Z
/* DDL */ CREATE TABLE public.C_Doc_Approval_Strategy_Line (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10), C_Doc_Approval_Strategy_ID NUMERIC(10) NOT NULL, C_Doc_Approval_Strategy_Line_ID NUMERIC(10) NOT NULL, C_Job_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsProjectManagerSet CHAR(1), MinimumAmt NUMERIC, SeqNo NUMERIC(10) DEFAULT 0 NOT NULL, Type VARCHAR(3) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCurrency_CDocApprovalStrategyLine FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CDocApprovalStrategy_CDocApprovalStrategyLine FOREIGN KEY (C_Doc_Approval_Strategy_ID) REFERENCES public.C_Doc_Approval_Strategy DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Doc_Approval_Strategy_Line_Key PRIMARY KEY (C_Doc_Approval_Strategy_Line_ID), CONSTRAINT CJob_CDocApprovalStrategyLine FOREIGN KEY (C_Job_ID) REFERENCES public.C_Job DEFERRABLE INITIALLY DEFERRED)
;

-- Window: Document Approval Strategy, InternalName=null
-- 2023-12-07T09:56:18.148673100Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582841,0,541754,TO_TIMESTAMP('2023-12-07 11:56:18.013','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Document Approval Strategy','N',TO_TIMESTAMP('2023-12-07 11:56:18.013','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-12-07T09:56:18.150758100Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541754 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-12-07T09:56:18.153367700Z
/* DDL */  select update_window_translation_from_ad_element(582841) 
;

-- 2023-12-07T09:56:18.164203200Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541754
;

-- 2023-12-07T09:56:18.165855900Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541754)
;

-- Tab: Document Approval Strategy(541754,D) -> Document Approval Strategy
-- Table: C_Doc_Approval_Strategy
-- 2023-12-07T09:56:40.813340400Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582841,0,547339,542380,541754,'Y',TO_TIMESTAMP('2023-12-07 11:56:40.649','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','C_Doc_Approval_Strategy','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Document Approval Strategy','N',10,0,TO_TIMESTAMP('2023-12-07 11:56:40.649','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:56:40.815453300Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547339 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-12-07T09:56:40.817526600Z
/* DDL */  select update_tab_translation_from_ad_element(582841) 
;

-- 2023-12-07T09:56:40.821676800Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547339)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> Mandant
-- Column: C_Doc_Approval_Strategy.AD_Client_ID
-- 2023-12-07T09:56:45.854210700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587690,723104,0,547339,TO_TIMESTAMP('2023-12-07 11:56:45.715','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-12-07 11:56:45.715','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:56:45.856810300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:56:45.858898300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-12-07T09:56:45.939622Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723104
;

-- 2023-12-07T09:56:45.941789800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723104)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> Organisation
-- Column: C_Doc_Approval_Strategy.AD_Org_ID
-- 2023-12-07T09:56:46.061580400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587691,723105,0,547339,TO_TIMESTAMP('2023-12-07 11:56:45.946','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-12-07 11:56:45.946','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:56:46.063676200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:56:46.065299600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-12-07T09:56:46.129749900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723105
;

-- 2023-12-07T09:56:46.131407600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723105)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> Aktiv
-- Column: C_Doc_Approval_Strategy.IsActive
-- 2023-12-07T09:56:46.251408300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587694,723106,0,547339,TO_TIMESTAMP('2023-12-07 11:56:46.134','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-12-07 11:56:46.134','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:56:46.254572700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:56:46.256735200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-12-07T09:56:46.310992700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723106
;

-- 2023-12-07T09:56:46.312150300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723106)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> Document Approval Strategy
-- Column: C_Doc_Approval_Strategy.C_Doc_Approval_Strategy_ID
-- 2023-12-07T09:56:46.427473300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587697,723107,0,547339,TO_TIMESTAMP('2023-12-07 11:56:46.315','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Document Approval Strategy',TO_TIMESTAMP('2023-12-07 11:56:46.315','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:56:46.429643700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:56:46.431710400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582841) 
;

-- 2023-12-07T09:56:46.434306500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723107
;

-- 2023-12-07T09:56:46.434823400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723107)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> Name
-- Column: C_Doc_Approval_Strategy.Name
-- 2023-12-07T09:56:46.550557300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587698,723108,0,547339,TO_TIMESTAMP('2023-12-07 11:56:46.437','YYYY-MM-DD HH24:MI:SS.US'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-12-07 11:56:46.437','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:56:46.553200400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:56:46.554755500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-12-07T09:56:46.574807800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723108
;

-- 2023-12-07T09:56:46.576427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723108)
;

-- Tab: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D)
-- UI Section: main
-- 2023-12-07T09:57:08.463948Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547339,545924,TO_TIMESTAMP('2023-12-07 11:57:08.313','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-12-07 11:57:08.313','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-12-07T09:57:08.466032400Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545924 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main
-- UI Column: 10
-- 2023-12-07T09:57:15.935509Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547220,545924,TO_TIMESTAMP('2023-12-07 11:57:14.768','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-12-07 11:57:14.768','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main
-- UI Column: 20
-- 2023-12-07T09:57:17.534385300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547221,545924,TO_TIMESTAMP('2023-12-07 11:57:17.445','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-12-07 11:57:17.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 10
-- UI Element Group: primary
-- 2023-12-07T09:57:31.500678500Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547220,551390,TO_TIMESTAMP('2023-12-07 11:57:31.34','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2023-12-07 11:57:31.34','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 10 -> primary.Name
-- Column: C_Doc_Approval_Strategy.Name
-- 2023-12-07T09:57:45.398181200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723108,0,547339,551390,621957,'F',TO_TIMESTAMP('2023-12-07 11:57:44.233','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2023-12-07 11:57:44.233','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 20
-- UI Element Group: flags
-- 2023-12-07T09:57:54.504826400Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547221,551391,TO_TIMESTAMP('2023-12-07 11:57:54.346','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-12-07 11:57:54.346','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Doc_Approval_Strategy.IsActive
-- 2023-12-07T09:58:02.988147500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723106,0,547339,551391,621958,'F',TO_TIMESTAMP('2023-12-07 11:58:02.842','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2023-12-07 11:58:02.842','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 20
-- UI Element Group: org&client
-- 2023-12-07T09:58:10.823792800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547221,551392,TO_TIMESTAMP('2023-12-07 11:58:09.692','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org&client',20,TO_TIMESTAMP('2023-12-07 11:58:09.692','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 20 -> org&client.Organisation
-- Column: C_Doc_Approval_Strategy.AD_Org_ID
-- 2023-12-07T09:58:18.989036900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723105,0,547339,551392,621959,'F',TO_TIMESTAMP('2023-12-07 11:58:18.835','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-12-07 11:58:18.835','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 20 -> org&client.Mandant
-- Column: C_Doc_Approval_Strategy.AD_Client_ID
-- 2023-12-07T09:58:25.300625500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723104,0,547339,551392,621960,'F',TO_TIMESTAMP('2023-12-07 11:58:25.141','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2023-12-07 11:58:25.141','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Tab: Document Approval Strategy(541754,D) -> Document Approval Strategy Line
-- Table: C_Doc_Approval_Strategy_Line
-- 2023-12-07T09:58:58.724278500Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582842,0,547340,542381,541754,'Y',TO_TIMESTAMP('2023-12-07 11:58:57.571','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','C_Doc_Approval_Strategy_Line','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Document Approval Strategy Line','N',20,1,TO_TIMESTAMP('2023-12-07 11:58:57.571','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:58:58.726359700Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547340 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-12-07T09:58:58.728967700Z
/* DDL */  select update_tab_translation_from_ad_element(582842) 
;

-- 2023-12-07T09:58:58.733128600Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547340)
;

-- Tab: Document Approval Strategy(541754,D) -> Document Approval Strategy Line
-- Table: C_Doc_Approval_Strategy_Line
-- 2023-12-07T09:59:08.270655400Z
UPDATE AD_Tab SET AD_Column_ID=587707, Parent_Column_ID=587697,Updated=TO_TIMESTAMP('2023-12-07 11:59:08.27','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547340
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Mandant
-- Column: C_Doc_Approval_Strategy_Line.AD_Client_ID
-- 2023-12-07T09:59:28.375494600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587699,723109,0,547340,TO_TIMESTAMP('2023-12-07 11:59:28.237','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-12-07 11:59:28.237','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:28.378190600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:28.379771600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-12-07T09:59:28.472242100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723109
;

-- 2023-12-07T09:59:28.474362400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723109)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Organisation
-- Column: C_Doc_Approval_Strategy_Line.AD_Org_ID
-- 2023-12-07T09:59:28.594201300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587700,723110,0,547340,TO_TIMESTAMP('2023-12-07 11:59:28.478','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-12-07 11:59:28.478','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:28.596296Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:28.597859600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-12-07T09:59:28.667503700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723110
;

-- 2023-12-07T09:59:28.668623800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723110)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Aktiv
-- Column: C_Doc_Approval_Strategy_Line.IsActive
-- 2023-12-07T09:59:28.784331300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587703,723111,0,547340,TO_TIMESTAMP('2023-12-07 11:59:28.671','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-12-07 11:59:28.671','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:28.787789700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:28.790445Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-12-07T09:59:28.844963200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723111
;

-- 2023-12-07T09:59:28.845858300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723111)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Document Approval Strategy Line
-- Column: C_Doc_Approval_Strategy_Line.C_Doc_Approval_Strategy_Line_ID
-- 2023-12-07T09:59:28.962826500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587706,723112,0,547340,TO_TIMESTAMP('2023-12-07 11:59:28.848','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Document Approval Strategy Line',TO_TIMESTAMP('2023-12-07 11:59:28.848','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:28.964898900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:28.966457800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582842) 
;

-- 2023-12-07T09:59:28.969579400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723112
;

-- 2023-12-07T09:59:28.970100400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723112)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Document Approval Strategy
-- Column: C_Doc_Approval_Strategy_Line.C_Doc_Approval_Strategy_ID
-- 2023-12-07T09:59:29.075210500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587707,723113,0,547340,TO_TIMESTAMP('2023-12-07 11:59:28.972','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Document Approval Strategy',TO_TIMESTAMP('2023-12-07 11:59:28.972','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:29.077954600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:29.079511400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582841) 
;

-- 2023-12-07T09:59:29.082107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723113
;

-- 2023-12-07T09:59:29.083154Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723113)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Reihenfolge
-- Column: C_Doc_Approval_Strategy_Line.SeqNo
-- 2023-12-07T09:59:29.187560700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587708,723114,0,547340,TO_TIMESTAMP('2023-12-07 11:59:29.085','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2023-12-07 11:59:29.085','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:29.190775600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:29.193422300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2023-12-07T09:59:29.203356900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723114
;

-- 2023-12-07T09:59:29.204388Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723114)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Art
-- Column: C_Doc_Approval_Strategy_Line.Type
-- 2023-12-07T09:59:29.323458600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587709,723115,0,547340,TO_TIMESTAMP('2023-12-07 11:59:29.209','YYYY-MM-DD HH24:MI:SS.US'),100,'',3,'D','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2023-12-07 11:59:29.209','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:29.325566700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:29.327123600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2023-12-07T09:59:29.331257400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723115
;

-- 2023-12-07T09:59:29.331773400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723115)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Is Project Manager Set
-- Column: C_Doc_Approval_Strategy_Line.IsProjectManagerSet
-- 2023-12-07T09:59:29.451748300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587710,723116,0,547340,TO_TIMESTAMP('2023-12-07 11:59:29.334','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Is Project Manager Set',TO_TIMESTAMP('2023-12-07 11:59:29.334','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:29.453819900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:29.455386700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582843) 
;

-- 2023-12-07T09:59:29.458502800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723116
;

-- 2023-12-07T09:59:29.459025800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723116)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Position
-- Column: C_Doc_Approval_Strategy_Line.C_Job_ID
-- 2023-12-07T09:59:29.580923900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587711,723117,0,547340,TO_TIMESTAMP('2023-12-07 11:59:29.462','YYYY-MM-DD HH24:MI:SS.US'),100,'Position in der Firma',10,'D','Y','N','N','N','N','N','N','N','Position',TO_TIMESTAMP('2023-12-07 11:59:29.462','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:29.583106100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:29.584739800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2761) 
;

-- 2023-12-07T09:59:29.587853600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723117
;

-- 2023-12-07T09:59:29.588903400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723117)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Minimum Amt
-- Column: C_Doc_Approval_Strategy_Line.MinimumAmt
-- 2023-12-07T09:59:29.704712400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587712,723118,0,547340,TO_TIMESTAMP('2023-12-07 11:59:29.591','YYYY-MM-DD HH24:MI:SS.US'),100,'Minumum Amout in Document Currency',10,'D','Y','N','N','N','N','N','N','N','Minimum Amt',TO_TIMESTAMP('2023-12-07 11:59:29.591','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:29.707327600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:29.708889800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2177) 
;

-- 2023-12-07T09:59:29.712531500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723118
;

-- 2023-12-07T09:59:29.713054300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723118)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Währung
-- Column: C_Doc_Approval_Strategy_Line.C_Currency_ID
-- 2023-12-07T09:59:29.835028600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587713,723119,0,547340,TO_TIMESTAMP('2023-12-07 11:59:29.715','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2023-12-07 11:59:29.715','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T09:59:29.837167600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T09:59:29.839250100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-12-07T09:59:29.848596700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723119
;

-- 2023-12-07T09:59:29.849929Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723119)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 10 -> primary.Name
-- Column: C_Doc_Approval_Strategy.Name
-- 2023-12-07T09:59:46.400556200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-12-07 11:59:46.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621957
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Doc_Approval_Strategy.IsActive
-- 2023-12-07T09:59:46.408862700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-12-07 11:59:46.408','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621958
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy(547339,D) -> main -> 20 -> org&client.Organisation
-- Column: C_Doc_Approval_Strategy.AD_Org_ID
-- 2023-12-07T09:59:46.417400200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-12-07 11:59:46.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621959
;

-- Tab: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D)
-- UI Section: main
-- 2023-12-07T10:00:02.116782Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547340,545925,TO_TIMESTAMP('2023-12-07 12:00:01.98','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2023-12-07 12:00:01.98','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-12-07T10:00:02.119486500Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545925 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Tab: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D)
-- UI Section: main
-- 2023-12-07T10:00:05.238387500Z
UPDATE AD_UI_Section SET Name='',Updated=TO_TIMESTAMP('2023-12-07 12:00:05.237','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Section_ID=545925
;

-- 2023-12-07T10:00:05.242026800Z
UPDATE AD_UI_Section_Trl trl SET Name='' WHERE AD_UI_Section_ID=545925 AND AD_Language='de_DE'
;

-- 2023-12-07T10:00:15.934462100Z
UPDATE AD_UI_Section_Trl SET Name='',Updated=TO_TIMESTAMP('2023-12-07 12:00:15.932','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_UI_Section_ID=545925
;

-- 2023-12-07T10:00:17.730220100Z
UPDATE AD_UI_Section_Trl SET Name='',Updated=TO_TIMESTAMP('2023-12-07 12:00:17.728','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_UI_Section_ID=545925
;

-- 2023-12-07T10:00:19.454303Z
UPDATE AD_UI_Section_Trl SET Name='',Updated=TO_TIMESTAMP('2023-12-07 12:00:19.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_UI_Section_ID=545925
;

-- UI Section: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main
-- UI Column: 10
-- 2023-12-07T10:00:23.768419400Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547222,545925,TO_TIMESTAMP('2023-12-07 12:00:23.624','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-12-07 12:00:23.624','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main
-- UI Column: 20
-- 2023-12-07T10:00:25.384139300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547223,545925,TO_TIMESTAMP('2023-12-07 12:00:25.278','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-12-07 12:00:25.278','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10
-- UI Element Group: main
-- 2023-12-07T10:01:39.519854300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547222,551393,TO_TIMESTAMP('2023-12-07 12:01:39.369','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-12-07 12:01:39.369','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Reihenfolge
-- Column: C_Doc_Approval_Strategy_Line.SeqNo
-- 2023-12-07T10:01:51.625533900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723114,0,547340,551393,621961,'F',TO_TIMESTAMP('2023-12-07 12:01:51.49','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','Reihenfolge',10,0,0,TO_TIMESTAMP('2023-12-07 12:01:51.49','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Art
-- Column: C_Doc_Approval_Strategy_Line.Type
-- 2023-12-07T10:01:57.621196Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723115,0,547340,551393,621962,'F',TO_TIMESTAMP('2023-12-07 12:01:57.467','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','Y','N','N','Art',20,0,0,TO_TIMESTAMP('2023-12-07 12:01:57.467','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Position
-- Column: C_Doc_Approval_Strategy_Line.C_Job_ID
-- 2023-12-07T10:02:16.398309800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723117,0,547340,551393,621963,'F',TO_TIMESTAMP('2023-12-07 12:02:16.246','YYYY-MM-DD HH24:MI:SS.US'),100,'Position in der Firma','Y','N','Y','N','N','Position',30,0,0,TO_TIMESTAMP('2023-12-07 12:02:16.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Is Project Manager Set
-- Column: C_Doc_Approval_Strategy_Line.IsProjectManagerSet
-- 2023-12-07T10:02:29.166577500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723116,0,547340,551393,621964,'F',TO_TIMESTAMP('2023-12-07 12:02:29.02','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Is Project Manager Set',40,0,0,TO_TIMESTAMP('2023-12-07 12:02:29.02','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10
-- UI Element Group: amount
-- 2023-12-07T10:02:41.038465100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547222,551394,TO_TIMESTAMP('2023-12-07 12:02:40.903','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','amount',20,TO_TIMESTAMP('2023-12-07 12:02:40.903','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> amount.Minimum Amt
-- Column: C_Doc_Approval_Strategy_Line.MinimumAmt
-- 2023-12-07T10:02:51.448977100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723118,0,547340,551394,621965,'F',TO_TIMESTAMP('2023-12-07 12:02:51.288','YYYY-MM-DD HH24:MI:SS.US'),100,'Minumum Amout in Document Currency','Y','N','Y','N','N','Minimum Amt',10,0,0,TO_TIMESTAMP('2023-12-07 12:02:51.288','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> amount.Währung
-- Column: C_Doc_Approval_Strategy_Line.C_Currency_ID
-- 2023-12-07T10:02:58.072316600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723119,0,547340,551394,621966,'F',TO_TIMESTAMP('2023-12-07 12:02:57.916','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',20,0,0,TO_TIMESTAMP('2023-12-07 12:02:57.916','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 20
-- UI Element Group: flags
-- 2023-12-07T10:03:08.809352Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547223,551395,TO_TIMESTAMP('2023-12-07 12:03:08.67','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2023-12-07 12:03:08.67','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Doc_Approval_Strategy_Line.IsActive
-- 2023-12-07T10:03:18.786878200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723111,0,547340,551395,621967,'F',TO_TIMESTAMP('2023-12-07 12:03:18.635','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2023-12-07 12:03:18.635','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 20
-- UI Element Group: org&client
-- 2023-12-07T10:03:28.182271800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547223,551396,TO_TIMESTAMP('2023-12-07 12:03:25.035','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org&client',20,TO_TIMESTAMP('2023-12-07 12:03:25.035','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 20 -> org&client.Organisation
-- Column: C_Doc_Approval_Strategy_Line.AD_Org_ID
-- 2023-12-07T10:03:37.411584400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723110,0,547340,551396,621968,'F',TO_TIMESTAMP('2023-12-07 12:03:37.259','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-12-07 12:03:37.259','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Reihenfolge
-- Column: C_Doc_Approval_Strategy_Line.SeqNo
-- 2023-12-07T10:04:01.685616600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-12-07 12:04:01.685','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621961
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Art
-- Column: C_Doc_Approval_Strategy_Line.Type
-- 2023-12-07T10:04:01.693434300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-12-07 12:04:01.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621962
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Position
-- Column: C_Doc_Approval_Strategy_Line.C_Job_ID
-- 2023-12-07T10:04:01.701878800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-12-07 12:04:01.701','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621963
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> main.Is Project Manager Set
-- Column: C_Doc_Approval_Strategy_Line.IsProjectManagerSet
-- 2023-12-07T10:04:01.711253100Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-12-07 12:04:01.71','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621964
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> amount.Währung
-- Column: C_Doc_Approval_Strategy_Line.C_Currency_ID
-- 2023-12-07T10:04:01.718808700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-12-07 12:04:01.718','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621966
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 10 -> amount.Minimum Amt
-- Column: C_Doc_Approval_Strategy_Line.MinimumAmt
-- 2023-12-07T10:04:01.726604200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-12-07 12:04:01.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621965
;

-- UI Element: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Doc_Approval_Strategy_Line.IsActive
-- 2023-12-07T10:04:01.733352900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-12-07 12:04:01.733','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621967
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Reihenfolge
-- Column: C_Doc_Approval_Strategy_Line.SeqNo
-- 2023-12-07T10:04:30.677733Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2023-12-07 12:04:30.677','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723114
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Position
-- Column: C_Doc_Approval_Strategy_Line.C_Job_ID
-- 2023-12-07T10:05:14.820420900Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=JOB',Updated=TO_TIMESTAMP('2023-12-07 12:05:14.819','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723117
;

-- Column: C_Doc_Approval_Strategy_Line.C_Job_ID
-- 2023-12-07T10:05:41.433689800Z
UPDATE AD_Column SET MandatoryLogic='@Type/-@=JOB',Updated=TO_TIMESTAMP('2023-12-07 12:05:41.433','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587711
;

-- Window: Document Approval Strategy, InternalName=docApprovalStrategy
-- 2023-12-07T10:08:23.268222500Z
UPDATE AD_Window SET InternalName='docApprovalStrategy',Updated=TO_TIMESTAMP('2023-12-07 12:08:23.266','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541754
;

-- Name: Document Approval Strategy
-- Action Type: W
-- Window: Document Approval Strategy(541754,D)
-- 2023-12-07T10:08:42.237278200Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582841,542132,0,541754,TO_TIMESTAMP('2023-12-07 12:08:42.092','YYYY-MM-DD HH24:MI:SS.US'),100,'D','docApprovalStrategy','Y','N','N','N','N','Document Approval Strategy',TO_TIMESTAMP('2023-12-07 12:08:42.092','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T10:08:42.239377400Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542132 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-12-07T10:08:42.241450100Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542132, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542132)
;

-- 2023-12-07T10:08:42.250779900Z
/* DDL */  select update_menu_translation_from_ad_element(582841) 
;

-- Reordering children of `Settings`
-- Node name: `Custom data entry - tab (DataEntry_Tab)`
-- 2023-12-07T10:08:42.904250200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541181 AND AD_Tree_ID=10
;

-- Node name: `Custom data entry - section (DataEntry_Section)`
-- 2023-12-07T10:08:42.905828300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541201 AND AD_Tree_ID=10
;

-- Node name: `Custom data entry - field (DataEntry_Field)`
-- 2023-12-07T10:08:42.907399400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541180 AND AD_Tree_ID=10
;

-- Node name: `Import - Custom data entry (I_DataEntry_Record)`
-- 2023-12-07T10:08:42.907921600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541285 AND AD_Tree_ID=10
;

-- Node name: `User Query (AD_UserQuery)`
-- 2023-12-07T10:08:42.908963200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540989 AND AD_Tree_ID=10
;

-- Node name: `Country, Region and City (C_Country)`
-- 2023-12-07T10:08:42.910005800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540845 AND AD_Tree_ID=10
;

-- Node name: `Country, City and Postal (C_Postal)`
-- 2023-12-07T10:08:42.911050800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540960 AND AD_Tree_ID=10
;

-- Node name: `Country, Region Translation (C_Country_Trl)`
-- 2023-12-07T10:08:42.912089300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541110 AND AD_Tree_ID=10
;

-- Node name: `Board Settings (WEBUI_Board)`
-- 2023-12-07T10:08:42.913131800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540988 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Process (M_HU_Process)`
-- 2023-12-07T10:08:42.914174400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541081 AND AD_Tree_ID=10
;

-- Node name: `Aggregation (C_Aggregation)`
-- 2023-12-07T10:08:42.915212900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541275 AND AD_Tree_ID=10
;

-- Node name: `Message Translation (AD_Message_Trl)`
-- 2023-12-07T10:08:42.915735100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541105 AND AD_Tree_ID=10
;

-- Node name: `Document Type Translation (C_DocType_Trl)`
-- 2023-12-07T10:08:42.917298700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541106 AND AD_Tree_ID=10
;

-- Node name: `Shipped HUs from legacy application (ServiceRepair_Old_Shipped_HU)`
-- 2023-12-07T10:08:42.917821900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541646 AND AD_Tree_ID=10
;

-- Node name: `Document Approval Strategy`
-- 2023-12-07T10:08:42.918863400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000101, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542132 AND AD_Tree_ID=10
;

