-- Table: Mobile_Application_Access
-- Table: Mobile_Application_Access
-- 2024-10-08T04:25:02.597Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('6',0,0,0,542446,'A','N',TO_TIMESTAMP('2024-10-08 07:25:02','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Mobile Applicatoin Role Access','NP','L','Mobile_Application_Access','DTI',TO_TIMESTAMP('2024-10-08 07:25:02','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-10-08T04:25:02.599Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542446 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- -- 2024-10-08T04:25:02.751Z
-- INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556378,TO_TIMESTAMP('2024-10-08 07:25:02','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table Mobile_Application_Access',1,'Y','N','Y','Y','Mobile_Application_Access',1000000,TO_TIMESTAMP('2024-10-08 07:25:02','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- 2024-10-08T04:25:02.763Z
CREATE SEQUENCE MOBILE_APPLICATION_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: Mobile_Application_Access.AD_Client_ID
-- Column: Mobile_Application_Access.AD_Client_ID
-- 2024-10-08T04:25:08.460Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589270,102,0,19,542446,'AD_Client_ID',TO_TIMESTAMP('2024-10-08 07:25:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-10-08 07:25:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:08.464Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589270 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:08.493Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: Mobile_Application_Access.AD_Org_ID
-- Column: Mobile_Application_Access.AD_Org_ID
-- 2024-10-08T04:25:09.274Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589271,113,0,30,542446,'AD_Org_ID',TO_TIMESTAMP('2024-10-08 07:25:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-10-08 07:25:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:09.276Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589271 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:09.278Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: Mobile_Application_Access.Created
-- Column: Mobile_Application_Access.Created
-- 2024-10-08T04:25:09.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589272,245,0,16,542446,'Created',TO_TIMESTAMP('2024-10-08 07:25:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-10-08 07:25:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:09.867Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589272 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:09.871Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: Mobile_Application_Access.CreatedBy
-- Column: Mobile_Application_Access.CreatedBy
-- 2024-10-08T04:25:10.493Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589273,246,0,18,110,542446,'CreatedBy',TO_TIMESTAMP('2024-10-08 07:25:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-10-08 07:25:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:10.496Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589273 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:10.499Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: Mobile_Application_Access.IsActive
-- Column: Mobile_Application_Access.IsActive
-- 2024-10-08T04:25:11.146Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589274,348,0,20,542446,'IsActive',TO_TIMESTAMP('2024-10-08 07:25:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-10-08 07:25:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:11.148Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589274 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:11.152Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: Mobile_Application_Access.Updated
-- Column: Mobile_Application_Access.Updated
-- 2024-10-08T04:25:11.799Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589275,607,0,16,542446,'Updated',TO_TIMESTAMP('2024-10-08 07:25:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-10-08 07:25:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:11.801Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589275 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:11.804Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: Mobile_Application_Access.UpdatedBy
-- Column: Mobile_Application_Access.UpdatedBy
-- 2024-10-08T04:25:12.464Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589276,608,0,18,110,542446,'UpdatedBy',TO_TIMESTAMP('2024-10-08 07:25:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-10-08 07:25:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:12.467Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589276 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:12.471Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-10-08T04:25:12.959Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583313,0,'Mobile_Application_Access_ID',TO_TIMESTAMP('2024-10-08 07:25:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mobile Applicatoin Role Access','Mobile Applicatoin Role Access',TO_TIMESTAMP('2024-10-08 07:25:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:25:12.964Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583313 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: Mobile_Application_Access.Mobile_Application_Access_ID
-- Column: Mobile_Application_Access.Mobile_Application_Access_ID
-- 2024-10-08T04:25:13.562Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589277,583313,0,13,542446,'Mobile_Application_Access_ID',TO_TIMESTAMP('2024-10-08 07:25:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Mobile Applicatoin Role Access',0,0,TO_TIMESTAMP('2024-10-08 07:25:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:13.564Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589277 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:13.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(583313) 
;

-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T04:25:33.158Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589278,583308,0,30,542446,'XX','Mobile_Application_ID',TO_TIMESTAMP('2024-10-08 07:25:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Mobile Application',0,0,TO_TIMESTAMP('2024-10-08 07:25:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:33.160Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589278 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:33.163Z
/* DDL */  select update_Column_Translation_From_AD_Element(583308) 
;

-- Column: Mobile_Application_Access.AD_Role_ID
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2024-10-08T04:25:57.303Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589279,123,0,30,542446,'XX','AD_Role_ID',TO_TIMESTAMP('2024-10-08 07:25:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Responsibility Role','D',0,10,'The Role determines security and access a user who has this Role will have in the System.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Rolle',0,0,TO_TIMESTAMP('2024-10-08 07:25:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T04:25:57.305Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589279 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T04:25:57.308Z
/* DDL */  select update_Column_Translation_From_AD_Element(123) 
;

-- 2024-10-08T04:26:20.683Z
/* DDL */ CREATE TABLE public.Mobile_Application_Access (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Role_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Mobile_Application_Access_ID NUMERIC(10) NOT NULL, Mobile_Application_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADRole_MobileApplicationAccess FOREIGN KEY (AD_Role_ID) REFERENCES public.AD_Role DEFERRABLE INITIALLY DEFERRED, CONSTRAINT Mobile_Application_Access_Key PRIMARY KEY (Mobile_Application_Access_ID), CONSTRAINT MobileApplication_MobileApplicationAccess FOREIGN KEY (Mobile_Application_ID) REFERENCES public.Mobile_Application DEFERRABLE INITIALLY DEFERRED)
;

-- Element: Mobile_Application_Access_ID
-- 2024-10-08T04:27:34.559Z
UPDATE AD_Element_Trl SET Name='Mobile Application Role Access', PrintName='Mobile Application Role Access',Updated=TO_TIMESTAMP('2024-10-08 07:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language='de_CH'
;

-- 2024-10-08T04:27:34.562Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583313,'de_CH') 
;

-- Element: Mobile_Application_Access_ID
-- 2024-10-08T04:27:37.138Z
UPDATE AD_Element_Trl SET Name='Mobile Application Role Access', PrintName='Mobile Application Role Access',Updated=TO_TIMESTAMP('2024-10-08 07:27:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language='en_US'
;

-- 2024-10-08T04:27:37.141Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583313,'en_US') 
;

-- Element: Mobile_Application_Access_ID
-- 2024-10-08T04:27:39.896Z
UPDATE AD_Element_Trl SET Name='Mobile Application Role Access', PrintName='Mobile Application Role Access',Updated=TO_TIMESTAMP('2024-10-08 07:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language='de_DE'
;

-- 2024-10-08T04:27:39.899Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583313,'de_DE') 
;

-- 2024-10-08T04:27:39.905Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583313,'de_DE') 
;

-- Element: Mobile_Application_Access_ID
-- 2024-10-08T04:27:42.363Z
UPDATE AD_Element_Trl SET Name='Mobile Application Role Access', PrintName='Mobile Application Role Access',Updated=TO_TIMESTAMP('2024-10-08 07:27:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583313 AND AD_Language='fr_CH'
;

-- 2024-10-08T04:27:42.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583313,'fr_CH') 
;

-- Tab: Rolle - Verwaltung -> Mobile Application Role Access
-- Table: Mobile_Application_Access
-- Tab: Rolle - Verwaltung(111,D) -> Mobile Application Role Access
-- Table: Mobile_Application_Access
-- 2024-10-08T04:28:25.478Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589279,583313,0,547620,542446,111,'Y',TO_TIMESTAMP('2024-10-08 07:28:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','Mobile_Application_Access','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Mobile Application Role Access',531,'N',150,1,TO_TIMESTAMP('2024-10-08 07:28:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:28:25.481Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547620 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-08T04:28:25.482Z
/* DDL */  select update_tab_translation_from_ad_element(583313) 
;

-- 2024-10-08T04:28:25.494Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547620)
;

-- Table: Mobile_Application_Access
-- Table: Mobile_Application_Access
-- 2024-10-08T04:28:36.978Z
UPDATE AD_Table SET Name='Mobile Application Role Access',Updated=TO_TIMESTAMP('2024-10-08 07:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542446
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Mandant
-- Column: Mobile_Application_Access.AD_Client_ID
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Mandant
-- Column: Mobile_Application_Access.AD_Client_ID
-- 2024-10-08T04:28:52.930Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589270,731866,0,547620,TO_TIMESTAMP('2024-10-08 07:28:52','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-08 07:28:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:28:52.934Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T04:28:52.938Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-08T04:28:53.535Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731866
;

-- 2024-10-08T04:28:53.537Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731866)
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Organisation
-- Column: Mobile_Application_Access.AD_Org_ID
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Organisation
-- Column: Mobile_Application_Access.AD_Org_ID
-- 2024-10-08T04:28:53.672Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589271,731867,0,547620,TO_TIMESTAMP('2024-10-08 07:28:53','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-10-08 07:28:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:28:53.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T04:28:53.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-08T04:28:53.754Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731867
;

-- 2024-10-08T04:28:53.756Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731867)
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2024-10-08T04:28:53.870Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589274,731868,0,547620,TO_TIMESTAMP('2024-10-08 07:28:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-08 07:28:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:28:53.872Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T04:28:53.873Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-08T04:28:53.970Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731868
;

-- 2024-10-08T04:28:53.971Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731868)
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Mobile Application Role Access
-- Column: Mobile_Application_Access.Mobile_Application_Access_ID
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Mobile Application Role Access
-- Column: Mobile_Application_Access.Mobile_Application_Access_ID
-- 2024-10-08T04:28:54.097Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589277,731869,0,547620,TO_TIMESTAMP('2024-10-08 07:28:53','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application Role Access',TO_TIMESTAMP('2024-10-08 07:28:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:28:54.099Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T04:28:54.101Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583313) 
;

-- 2024-10-08T04:28:54.103Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731869
;

-- 2024-10-08T04:28:54.104Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731869)
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T04:28:54.225Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589278,731870,0,547620,TO_TIMESTAMP('2024-10-08 07:28:54','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application',TO_TIMESTAMP('2024-10-08 07:28:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:28:54.228Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T04:28:54.230Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583308) 
;

-- 2024-10-08T04:28:54.233Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731870
;

-- 2024-10-08T04:28:54.234Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731870)
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2024-10-08T04:28:54.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589279,731871,0,547620,TO_TIMESTAMP('2024-10-08 07:28:54','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role',10,'D','The Role determines security and access a user who has this Role will have in the System.','Y','N','N','N','N','N','N','N','Rolle',TO_TIMESTAMP('2024-10-08 07:28:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T04:28:54.366Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T04:28:54.367Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(123) 
;

-- 2024-10-08T04:28:54.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731871
;

-- 2024-10-08T04:28:54.388Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731871)
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T04:29:07.944Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-10-08 07:29:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731870
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2024-10-08T04:29:07.952Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-10-08 07:29:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731868
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T04:29:12.622Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-08 07:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731870
;

-- Field: Rolle - Verwaltung -> Mobile Application Role Access -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2024-10-08T04:29:12.629Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-08 07:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731868
;

alter table mobile_application_access
    alter column mobile_application_access_id set default nextval('mobile_application_access_seq');

-- Window: Mobile Application Role Access, InternalName=null
-- Window: Mobile Application Role Access, InternalName=null
-- 2024-10-08T06:49:31.866Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583313,0,541827,TO_TIMESTAMP('2024-10-08 09:49:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Mobile Application Role Access','N',TO_TIMESTAMP('2024-10-08 09:49:31','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-10-08T06:49:31.872Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541827 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-10-08T06:49:31.876Z
/* DDL */  select update_window_translation_from_ad_element(583313) 
;

-- 2024-10-08T06:49:31.883Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541827
;

-- 2024-10-08T06:49:31.888Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541827)
;

-- Tab: Mobile Application Role Access -> Mobile Application Role Access
-- Table: Mobile_Application_Access
-- Tab: Mobile Application Role Access(541827,D) -> Mobile Application Role Access
-- Table: Mobile_Application_Access
-- 2024-10-08T06:49:59.056Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583313,0,547621,542446,541827,'Y',TO_TIMESTAMP('2024-10-08 09:49:58','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','Mobile_Application_Access','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Mobile Application Role Access','N',10,0,TO_TIMESTAMP('2024-10-08 09:49:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:49:59.058Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547621 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-08T06:49:59.061Z
/* DDL */  select update_tab_translation_from_ad_element(583313) 
;

-- 2024-10-08T06:49:59.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547621)
;

-- Window: Mobile Application Role Access, InternalName=mobileAppAccess
-- Window: Mobile Application Role Access, InternalName=mobileAppAccess
-- 2024-10-08T06:50:08.276Z
UPDATE AD_Window SET InternalName='mobileAppAccess',Updated=TO_TIMESTAMP('2024-10-08 09:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541827
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Mandant
-- Column: Mobile_Application_Access.AD_Client_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Mandant
-- Column: Mobile_Application_Access.AD_Client_ID
-- 2024-10-08T06:50:12.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589270,731872,0,547621,TO_TIMESTAMP('2024-10-08 09:50:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-08 09:50:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:50:12.737Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T06:50:12.741Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-08T06:50:13.494Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731872
;

-- 2024-10-08T06:50:13.496Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731872)
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Organisation
-- Column: Mobile_Application_Access.AD_Org_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Organisation
-- Column: Mobile_Application_Access.AD_Org_ID
-- 2024-10-08T06:50:13.617Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589271,731873,0,547621,TO_TIMESTAMP('2024-10-08 09:50:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-10-08 09:50:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:50:13.618Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T06:50:13.620Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-08T06:50:13.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731873
;

-- 2024-10-08T06:50:13.731Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731873)
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2024-10-08T06:50:13.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589274,731874,0,547621,TO_TIMESTAMP('2024-10-08 09:50:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-08 09:50:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:50:13.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T06:50:13.858Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-08T06:50:13.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731874
;

-- 2024-10-08T06:50:13.998Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731874)
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Mobile Application Role Access
-- Column: Mobile_Application_Access.Mobile_Application_Access_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Mobile Application Role Access
-- Column: Mobile_Application_Access.Mobile_Application_Access_ID
-- 2024-10-08T06:50:14.116Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589277,731875,0,547621,TO_TIMESTAMP('2024-10-08 09:50:14','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application Role Access',TO_TIMESTAMP('2024-10-08 09:50:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:50:14.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T06:50:14.120Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583313) 
;

-- 2024-10-08T06:50:14.126Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731875
;

-- 2024-10-08T06:50:14.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731875)
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T06:50:14.280Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589278,731876,0,547621,TO_TIMESTAMP('2024-10-08 09:50:14','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application',TO_TIMESTAMP('2024-10-08 09:50:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:50:14.283Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T06:50:14.285Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583308) 
;

-- 2024-10-08T06:50:14.288Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731876
;

-- 2024-10-08T06:50:14.289Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731876)
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2024-10-08T06:50:14.407Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589279,731877,0,547621,TO_TIMESTAMP('2024-10-08 09:50:14','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role',10,'D','The Role determines security and access a user who has this Role will have in the System.','Y','N','N','N','N','N','N','N','Rolle',TO_TIMESTAMP('2024-10-08 09:50:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:50:14.408Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T06:50:14.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(123) 
;

-- 2024-10-08T06:50:14.435Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731877
;

-- 2024-10-08T06:50:14.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731877)
;

-- Column: Mobile_Application_Access.AD_Role_ID
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2024-10-08T06:51:01.666Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-10-08 09:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589279
;

-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T06:51:14.709Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-10-08 09:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589278
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T06:51:46.636Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-10-08 09:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731876
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2024-10-08T06:51:46.643Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-10-08 09:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731877
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2024-10-08T06:51:46.650Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2024-10-08 09:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731874
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2024-10-08T06:52:02.133Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-08 09:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731876
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Rolle
-- Column: Mobile_Application_Access.AD_Role_ID
-- 2024-10-08T06:52:02.141Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-08 09:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731877
;

-- Field: Mobile Application Role Access -> Mobile Application Role Access -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2024-10-08T06:52:02.148Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-08 09:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731874
;

-- Name: Mobile Application Role Access
-- Action Type: W
-- Window: Mobile Application Role Access(541827,D)
-- 2024-10-08T06:53:07.508Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583313,542181,0,541827,TO_TIMESTAMP('2024-10-08 09:53:07','YYYY-MM-DD HH24:MI:SS'),100,'D','mobileAppAccess','Y','N','N','N','N','Mobile Application Role Access',TO_TIMESTAMP('2024-10-08 09:53:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T06:53:07.511Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542181 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-10-08T06:53:07.513Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542181, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542181)
;

-- 2024-10-08T06:53:07.521Z
/* DDL */  select update_menu_translation_from_ad_element(583313) 
;

-- Reordering children of `Mobile`
-- Node name: `Mobile Configuration (MobileConfiguration)`
-- 2024-10-08T06:53:08.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542136 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application (Mobile_Application)`
-- 2024-10-08T06:53:08.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542180 AND AD_Tree_ID=10
;

-- Node name: `Mobile Application Role Access`
-- 2024-10-08T06:53:08.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=542179, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542181 AND AD_Tree_ID=10
;

