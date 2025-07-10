-- Run mode: SWING_CLIENT

-- Table: OAuth2_Client
-- 2025-07-10T08:18:02.491Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('6',0,0,0,542509,'N',TO_TIMESTAMP('2025-07-10 11:18:02.211','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'OAuth2 Client','NP','L','OAuth2_Client','DTI',TO_TIMESTAMP('2025-07-10 11:18:02.211','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:02.503Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542509 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-07-10T08:18:02.668Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556512,TO_TIMESTAMP('2025-07-10 11:18:02.543','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table OAuth2_Client',1,'Y','N','Y','Y','OAuth2_Client','N',1000000,TO_TIMESTAMP('2025-07-10 11:18:02.543','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:18:02.688Z
CREATE SEQUENCE OAUTH2_CLIENT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: OAuth2_Client.AD_Client_ID
-- 2025-07-10T08:18:12.085Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590505,102,0,19,542509,'AD_Client_ID',TO_TIMESTAMP('2025-07-10 11:18:11.875','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2025-07-10 11:18:11.875','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:12.091Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590505 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:12.130Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: OAuth2_Client.AD_Org_ID
-- 2025-07-10T08:18:13.161Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590506,113,0,30,542509,'AD_Org_ID',TO_TIMESTAMP('2025-07-10 11:18:12.87','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2025-07-10 11:18:12.87','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:13.166Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590506 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:13.170Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: OAuth2_Client.Created
-- 2025-07-10T08:18:13.718Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590507,245,0,16,542509,'Created',TO_TIMESTAMP('2025-07-10 11:18:13.484','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2025-07-10 11:18:13.484','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:13.723Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590507 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:13.728Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: OAuth2_Client.CreatedBy
-- 2025-07-10T08:18:14.316Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590508,246,0,18,110,542509,'CreatedBy',TO_TIMESTAMP('2025-07-10 11:18:14.077','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2025-07-10 11:18:14.077','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:14.321Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590508 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:14.324Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: OAuth2_Client.IsActive
-- 2025-07-10T08:18:14.845Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590509,348,0,20,542509,'IsActive',TO_TIMESTAMP('2025-07-10 11:18:14.636','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2025-07-10 11:18:14.636','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:14.848Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590509 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:14.852Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: OAuth2_Client.Updated
-- 2025-07-10T08:18:15.409Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590510,607,0,16,542509,'Updated',TO_TIMESTAMP('2025-07-10 11:18:15.171','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2025-07-10 11:18:15.171','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:15.412Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590510 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:15.415Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: OAuth2_Client.UpdatedBy
-- 2025-07-10T08:18:16.524Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590511,608,0,18,110,542509,'UpdatedBy',TO_TIMESTAMP('2025-07-10 11:18:16.294','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2025-07-10 11:18:16.294','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:16.527Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590511 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:16.532Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-07-10T08:18:16.989Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583787,0,'OAuth2_Client_ID',TO_TIMESTAMP('2025-07-10 11:18:16.878','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','OAuth2 Client','OAuth2 Client',TO_TIMESTAMP('2025-07-10 11:18:16.878','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:18:16.994Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583787 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: OAuth2_Client.OAuth2_Client_ID
-- 2025-07-10T08:18:17.405Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590512,583787,0,13,542509,'OAuth2_Client_ID',TO_TIMESTAMP('2025-07-10 11:18:16.873','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','OAuth2 Client',0,0,TO_TIMESTAMP('2025-07-10 11:18:16.873','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:18:17.409Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590512 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:18:17.414Z
/* DDL */  select update_Column_Translation_From_AD_Element(583787)
;

-- Column: OAuth2_Client.InternalName
-- 2025-07-10T08:23:41.723Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590513,541268,0,10,542509,'InternalName',TO_TIMESTAMP('2025-07-10 11:23:41.547','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Generally used to give records a name that can be safely referenced from code.','D',0,40,'E','Hint: make the field/column read-only or not-updatable to avoid accidental changes.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Interner Name',0,0,TO_TIMESTAMP('2025-07-10 11:23:41.547','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:23:41.726Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590513 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:23:41.730Z
/* DDL */  select update_Column_Translation_From_AD_Element(541268)
;

-- Column: OAuth2_Client.Name
-- 2025-07-10T08:24:00.338Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590514,469,0,10,542509,'Name',TO_TIMESTAMP('2025-07-10 11:24:00.163','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2025-07-10 11:24:00.163','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:24:00.342Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590514 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:24:00.345Z
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2025-07-10T08:25:29.988Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583788,0,'OAuth2_ClientId',TO_TIMESTAMP('2025-07-10 11:25:29.805','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Client ID','Client ID',TO_TIMESTAMP('2025-07-10 11:25:29.805','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:25:29.993Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583788 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: OAuth2_Client.OAuth2_ClientId
-- 2025-07-10T08:26:52.989Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590515,583788,0,10,542509,'OAuth2_ClientId',TO_TIMESTAMP('2025-07-10 11:26:52.807','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Client ID',0,0,TO_TIMESTAMP('2025-07-10 11:26:52.807','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:26:52.991Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590515 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:26:52.996Z
/* DDL */  select update_Column_Translation_From_AD_Element(583788)
;

-- 2025-07-10T08:27:40.535Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583789,0,'OAuth2_Client_Secret',TO_TIMESTAMP('2025-07-10 11:27:40.366','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Client Secret','Client Secret',TO_TIMESTAMP('2025-07-10 11:27:40.366','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:27:40.538Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583789 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-07-10T08:28:39.562Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583790,0,'OAuth2_Authentication_Method',TO_TIMESTAMP('2025-07-10 11:28:39.376','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Authentication Method','Authentication Method',TO_TIMESTAMP('2025-07-10 11:28:39.376','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:28:39.567Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583790 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: OAuth2_Authentication_Method
-- 2025-07-10T08:29:00.911Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541966,TO_TIMESTAMP('2025-07-10 11:29:00.738','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','OAuth2_Authentication_Method',TO_TIMESTAMP('2025-07-10 11:29:00.738','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2025-07-10T08:29:00.915Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541966 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: OAuth2_Authentication_Method
-- 2025-07-10T08:29:51.982Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541966
;

-- 2025-07-10T08:29:52.003Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541966
;

-- 2025-07-10T08:29:59.177Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=583790
;

-- 2025-07-10T08:29:59.183Z
DELETE FROM AD_Element WHERE AD_Element_ID=583790
;

-- 2025-07-10T08:31:29.887Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583791,0,'OAuth2_Issuer_URI',TO_TIMESTAMP('2025-07-10 11:31:29.706','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Issuer URI','Issuer URI',TO_TIMESTAMP('2025-07-10 11:31:29.706','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:31:29.890Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583791 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-07-10T08:32:25.614Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583792,0,'OAuth2_Authorization_URI',TO_TIMESTAMP('2025-07-10 11:32:25.453','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Authorization URI','Authorization URI',TO_TIMESTAMP('2025-07-10 11:32:25.453','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:32:25.617Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583792 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-07-10T08:33:04.707Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583793,0,'OAuth2_Token_URI',TO_TIMESTAMP('2025-07-10 11:33:04.52','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Token URI','Token URI',TO_TIMESTAMP('2025-07-10 11:33:04.52','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:33:04.711Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583793 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-07-10T08:33:33.253Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583794,0,'OAuth2_UserInfo_URI',TO_TIMESTAMP('2025-07-10 11:33:33.082','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','User Info URI','User Info URI',TO_TIMESTAMP('2025-07-10 11:33:33.082','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:33:33.256Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583794 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-07-10T08:34:27.865Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583795,0,'OAuth2_JWKS_URI',TO_TIMESTAMP('2025-07-10 11:34:27.689','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','JWK Set URI','JWK Set URI',TO_TIMESTAMP('2025-07-10 11:34:27.689','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:34:27.869Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583795 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: OAuth2_Client.OAuth2_Client_Secret
-- 2025-07-10T08:35:55.837Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590516,583789,0,10,542509,'OAuth2_Client_Secret',TO_TIMESTAMP('2025-07-10 11:35:55.649','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Client Secret',0,0,TO_TIMESTAMP('2025-07-10 11:35:55.649','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:35:55.842Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590516 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:35:55.846Z
/* DDL */  select update_Column_Translation_From_AD_Element(583789)
;

-- Column: OAuth2_Client.OAuth2_Issuer_URI
-- 2025-07-10T08:36:18.386Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590517,583791,0,10,542509,'OAuth2_Issuer_URI',TO_TIMESTAMP('2025-07-10 11:36:18.219','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,256,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Issuer URI',0,0,TO_TIMESTAMP('2025-07-10 11:36:18.219','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:36:18.388Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590517 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:36:18.392Z
/* DDL */  select update_Column_Translation_From_AD_Element(583791)
;

-- Column: OAuth2_Client.OAuth2_Authorization_URI
-- 2025-07-10T08:36:42.396Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590518,583792,0,10,542509,'OAuth2_Authorization_URI',TO_TIMESTAMP('2025-07-10 11:36:42.215','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Authorization URI',0,0,TO_TIMESTAMP('2025-07-10 11:36:42.215','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:36:42.398Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590518 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:36:42.402Z
/* DDL */  select update_Column_Translation_From_AD_Element(583792)
;

-- Column: OAuth2_Client.OAuth2_Issuer_URI
-- 2025-07-10T08:36:54.385Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2025-07-10 11:36:54.385','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=590517
;

-- Column: OAuth2_Client.OAuth2_Token_URI
-- 2025-07-10T08:37:13.708Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590519,583793,0,10,542509,'OAuth2_Token_URI',TO_TIMESTAMP('2025-07-10 11:37:12.512','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Token URI',0,0,TO_TIMESTAMP('2025-07-10 11:37:12.512','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:37:13.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590519 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:37:13.716Z
/* DDL */  select update_Column_Translation_From_AD_Element(583793)
;

-- Column: OAuth2_Client.OAuth2_UserInfo_URI
-- 2025-07-10T08:37:29.348Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590520,583794,0,10,542509,'OAuth2_UserInfo_URI',TO_TIMESTAMP('2025-07-10 11:37:29.167','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'User Info URI',0,0,TO_TIMESTAMP('2025-07-10 11:37:29.167','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:37:29.350Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590520 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:37:29.355Z
/* DDL */  select update_Column_Translation_From_AD_Element(583794)
;

-- Column: OAuth2_Client.OAuth2_JWKS_URI
-- 2025-07-10T08:38:08.183Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590521,583795,0,10,542509,'OAuth2_JWKS_URI',TO_TIMESTAMP('2025-07-10 11:38:08.005','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'JWK Set URI',0,0,TO_TIMESTAMP('2025-07-10 11:38:08.005','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:38:08.187Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590521 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:38:08.192Z
/* DDL */  select update_Column_Translation_From_AD_Element(583795)
;

-- Column: OAuth2_Client.Description
-- 2025-07-10T08:38:45.721Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590522,275,0,36,542509,'Description',TO_TIMESTAMP('2025-07-10 11:38:45.543','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2025-07-10 11:38:45.543','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-10T08:38:45.723Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590522 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-10T08:38:45.726Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2025-07-10T08:39:17.380Z
/* DDL */ CREATE TABLE public.OAuth2_Client (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description TEXT, InternalName VARCHAR(40) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(255) NOT NULL, OAuth2_Authorization_URI VARCHAR(255) NOT NULL, OAuth2_ClientId VARCHAR(255) NOT NULL, OAuth2_Client_ID NUMERIC(10) NOT NULL, OAuth2_Client_Secret VARCHAR(255), OAuth2_Issuer_URI VARCHAR(255) NOT NULL, OAuth2_JWKS_URI VARCHAR(255) NOT NULL, OAuth2_Token_URI VARCHAR(255) NOT NULL, OAuth2_UserInfo_URI VARCHAR(255) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT OAuth2_Client_Key PRIMARY KEY (OAuth2_Client_ID))
;

-- Window: OAuth2 Client, InternalName=null
-- 2025-07-10T08:39:47.209Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583787,0,541911,TO_TIMESTAMP('2025-07-10 11:39:47.029','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','OAuth2 Client','N',TO_TIMESTAMP('2025-07-10 11:39:47.029','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2025-07-10T08:39:47.216Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541911 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-07-10T08:39:47.227Z
/* DDL */  select update_window_translation_from_ad_element(583787)
;

-- 2025-07-10T08:39:47.243Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541911
;

-- 2025-07-10T08:39:47.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541911)
;

-- Table: OAuth2_Client
-- 2025-07-10T08:39:54.123Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2025-07-10 11:39:54.12','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542509
;

-- Tab: OAuth2 Client(541911,D) -> OAuth2 Client
-- Table: OAuth2_Client
-- 2025-07-10T08:40:33.852Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583787,0,548281,542509,541911,'Y',TO_TIMESTAMP('2025-07-10 11:40:33.634','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','OAuth2_Client','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'OAuth2 Client','N',10,0,TO_TIMESTAMP('2025-07-10 11:40:33.634','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:33.857Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548281 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-07-10T08:40:33.862Z
/* DDL */  select update_tab_translation_from_ad_element(583787)
;

-- 2025-07-10T08:40:33.868Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548281)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Mandant
-- Column: OAuth2_Client.AD_Client_ID
-- 2025-07-10T08:40:37.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590505,749952,0,548281,TO_TIMESTAMP('2025-07-10 11:40:37.621','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-07-10 11:40:37.621','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:37.768Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:37.773Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-07-10T08:40:39.277Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749952
;

-- 2025-07-10T08:40:39.279Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749952)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Organisation
-- Column: OAuth2_Client.AD_Org_ID
-- 2025-07-10T08:40:39.411Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590506,749953,0,548281,TO_TIMESTAMP('2025-07-10 11:40:39.286','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2025-07-10 11:40:39.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:39.413Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:39.415Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-07-10T08:40:40.088Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749953
;

-- 2025-07-10T08:40:40.090Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749953)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Aktiv
-- Column: OAuth2_Client.IsActive
-- 2025-07-10T08:40:40.213Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590509,749954,0,548281,TO_TIMESTAMP('2025-07-10 11:40:40.095','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-07-10 11:40:40.095','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:40.214Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:40.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-07-10T08:40:40.682Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749954
;

-- 2025-07-10T08:40:40.684Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749954)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> OAuth2 Client
-- Column: OAuth2_Client.OAuth2_Client_ID
-- 2025-07-10T08:40:40.817Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590512,749955,0,548281,TO_TIMESTAMP('2025-07-10 11:40:40.69','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','OAuth2 Client',TO_TIMESTAMP('2025-07-10 11:40:40.69','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:40.819Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:40.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583787)
;

-- 2025-07-10T08:40:40.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749955
;

-- 2025-07-10T08:40:40.825Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749955)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Interner Name
-- Column: OAuth2_Client.InternalName
-- 2025-07-10T08:40:40.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590513,749956,0,548281,TO_TIMESTAMP('2025-07-10 11:40:40.828','YYYY-MM-DD HH24:MI:SS.US'),100,'Generally used to give records a name that can be safely referenced from code.',40,'D','Hint: make the field/column read-only or not-updatable to avoid accidental changes.','Y','N','N','N','N','N','N','N','Interner Name',TO_TIMESTAMP('2025-07-10 11:40:40.828','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:40.947Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:40.951Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541268)
;

-- 2025-07-10T08:40:40.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749956
;

-- 2025-07-10T08:40:40.970Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749956)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Name
-- Column: OAuth2_Client.Name
-- 2025-07-10T08:40:41.088Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590514,749957,0,548281,TO_TIMESTAMP('2025-07-10 11:40:40.974','YYYY-MM-DD HH24:MI:SS.US'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2025-07-10 11:40:40.974','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:41.091Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:41.094Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-07-10T08:40:41.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749957
;

-- 2025-07-10T08:40:41.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749957)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Client ID
-- Column: OAuth2_Client.OAuth2_ClientId
-- 2025-07-10T08:40:41.409Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590515,749958,0,548281,TO_TIMESTAMP('2025-07-10 11:40:41.287','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Client ID',TO_TIMESTAMP('2025-07-10 11:40:41.287','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:41.412Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:41.414Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583788)
;

-- 2025-07-10T08:40:41.418Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749958
;

-- 2025-07-10T08:40:41.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749958)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Client Secret
-- Column: OAuth2_Client.OAuth2_Client_Secret
-- 2025-07-10T08:40:41.545Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590516,749959,0,548281,TO_TIMESTAMP('2025-07-10 11:40:41.423','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Client Secret',TO_TIMESTAMP('2025-07-10 11:40:41.423','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:41.548Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:41.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583789)
;

-- 2025-07-10T08:40:41.554Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749959
;

-- 2025-07-10T08:40:41.554Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749959)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Issuer URI
-- Column: OAuth2_Client.OAuth2_Issuer_URI
-- 2025-07-10T08:40:41.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590517,749960,0,548281,TO_TIMESTAMP('2025-07-10 11:40:41.558','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Issuer URI',TO_TIMESTAMP('2025-07-10 11:40:41.558','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:41.683Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:41.686Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583791)
;

-- 2025-07-10T08:40:41.690Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749960
;

-- 2025-07-10T08:40:41.691Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749960)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Authorization URI
-- Column: OAuth2_Client.OAuth2_Authorization_URI
-- 2025-07-10T08:40:41.823Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590518,749961,0,548281,TO_TIMESTAMP('2025-07-10 11:40:41.694','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Authorization URI',TO_TIMESTAMP('2025-07-10 11:40:41.694','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:41.826Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:41.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583792)
;

-- 2025-07-10T08:40:41.832Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749961
;

-- 2025-07-10T08:40:41.833Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749961)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Token URI
-- Column: OAuth2_Client.OAuth2_Token_URI
-- 2025-07-10T08:40:41.972Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590519,749962,0,548281,TO_TIMESTAMP('2025-07-10 11:40:41.838','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Token URI',TO_TIMESTAMP('2025-07-10 11:40:41.838','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:41.974Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:41.976Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583793)
;

-- 2025-07-10T08:40:41.978Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749962
;

-- 2025-07-10T08:40:41.980Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749962)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> User Info URI
-- Column: OAuth2_Client.OAuth2_UserInfo_URI
-- 2025-07-10T08:40:42.104Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590520,749963,0,548281,TO_TIMESTAMP('2025-07-10 11:40:41.983','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','User Info URI',TO_TIMESTAMP('2025-07-10 11:40:41.983','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:42.107Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:42.108Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583794)
;

-- 2025-07-10T08:40:42.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749963
;

-- 2025-07-10T08:40:42.111Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749963)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> JWK Set URI
-- Column: OAuth2_Client.OAuth2_JWKS_URI
-- 2025-07-10T08:40:42.236Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590521,749964,0,548281,TO_TIMESTAMP('2025-07-10 11:40:42.114','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','JWK Set URI',TO_TIMESTAMP('2025-07-10 11:40:42.114','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:42.239Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:42.243Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583795)
;

-- 2025-07-10T08:40:42.246Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749964
;

-- 2025-07-10T08:40:42.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749964)
;

-- Field: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> Beschreibung
-- Column: OAuth2_Client.Description
-- 2025-07-10T08:40:42.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590522,749965,0,548281,TO_TIMESTAMP('2025-07-10 11:40:42.252','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-07-10 11:40:42.252','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:40:42.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=749965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-10T08:40:42.380Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-07-10T08:40:42.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=749965
;

-- 2025-07-10T08:40:42.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(749965)
;

-- Tab: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D)
-- UI Section: main
-- 2025-07-10T08:41:00.118Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548281,546809,TO_TIMESTAMP('2025-07-10 11:40:59.895','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-07-10 11:40:59.895','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2025-07-10T08:41:00.122Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546809 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main
-- UI Column: 10
-- 2025-07-10T08:41:00.289Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548288,546809,TO_TIMESTAMP('2025-07-10 11:41:00.158','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-07-10 11:41:00.158','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main
-- UI Column: 20
-- 2025-07-10T08:41:00.415Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548289,546809,TO_TIMESTAMP('2025-07-10 11:41:00.297','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2025-07-10 11:41:00.297','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10
-- UI Element Group: default
-- 2025-07-10T08:41:00.585Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548288,553244,TO_TIMESTAMP('2025-07-10 11:41:00.445','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2025-07-10 11:41:00.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> default.Interner Name
-- Column: OAuth2_Client.InternalName
-- 2025-07-10T08:41:39.998Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749956,0,548281,553244,635151,'F',TO_TIMESTAMP('2025-07-10 11:41:39.797','YYYY-MM-DD HH24:MI:SS.US'),100,'Generally used to give records a name that can be safely referenced from code.','Hint: make the field/column read-only or not-updatable to avoid accidental changes.','Y','N','Y','N','N','Interner Name',10,0,0,TO_TIMESTAMP('2025-07-10 11:41:39.797','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> default.Name
-- Column: OAuth2_Client.Name
-- 2025-07-10T08:41:46.393Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749957,0,548281,553244,635152,'F',TO_TIMESTAMP('2025-07-10 11:41:46.209','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','Y','N','N','Name',20,0,0,TO_TIMESTAMP('2025-07-10 11:41:46.209','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> default.Beschreibung
-- Column: OAuth2_Client.Description
-- 2025-07-10T08:41:56.418Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749965,0,548281,553244,635153,'F',TO_TIMESTAMP('2025-07-10 11:41:56.239','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Beschreibung',30,0,0,TO_TIMESTAMP('2025-07-10 11:41:56.239','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10
-- UI Element Group: config
-- 2025-07-10T08:42:16.046Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548288,553245,TO_TIMESTAMP('2025-07-10 11:42:15.875','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','config',20,TO_TIMESTAMP('2025-07-10 11:42:15.875','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> config.Client ID
-- Column: OAuth2_Client.OAuth2_ClientId
-- 2025-07-10T08:42:37.537Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749958,0,548281,553245,635154,'F',TO_TIMESTAMP('2025-07-10 11:42:37.356','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Client ID',10,0,0,TO_TIMESTAMP('2025-07-10 11:42:37.356','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> config.Client Secret
-- Column: OAuth2_Client.OAuth2_Client_Secret
-- 2025-07-10T08:42:43.452Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749959,0,548281,553245,635155,'F',TO_TIMESTAMP('2025-07-10 11:42:43.276','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Client Secret',20,0,0,TO_TIMESTAMP('2025-07-10 11:42:43.276','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> config.Issuer URI
-- Column: OAuth2_Client.OAuth2_Issuer_URI
-- 2025-07-10T08:42:50.615Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749960,0,548281,553245,635156,'F',TO_TIMESTAMP('2025-07-10 11:42:50.437','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Issuer URI',30,0,0,TO_TIMESTAMP('2025-07-10 11:42:50.437','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> config.Authorization URI
-- Column: OAuth2_Client.OAuth2_Authorization_URI
-- 2025-07-10T08:43:03.950Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749961,0,548281,553245,635157,'F',TO_TIMESTAMP('2025-07-10 11:43:03.775','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Authorization URI',40,0,0,TO_TIMESTAMP('2025-07-10 11:43:03.775','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> config.Token URI
-- Column: OAuth2_Client.OAuth2_Token_URI
-- 2025-07-10T08:43:10.698Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749962,0,548281,553245,635158,'F',TO_TIMESTAMP('2025-07-10 11:43:10.516','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Token URI',50,0,0,TO_TIMESTAMP('2025-07-10 11:43:10.516','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> config.User Info URI
-- Column: OAuth2_Client.OAuth2_UserInfo_URI
-- 2025-07-10T08:43:23.098Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749963,0,548281,553245,635159,'F',TO_TIMESTAMP('2025-07-10 11:43:22.917','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','User Info URI',60,0,0,TO_TIMESTAMP('2025-07-10 11:43:22.917','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> config.JWK Set URI
-- Column: OAuth2_Client.OAuth2_JWKS_URI
-- 2025-07-10T08:43:28.538Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749964,0,548281,553245,635160,'F',TO_TIMESTAMP('2025-07-10 11:43:28.366','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','JWK Set URI',70,0,0,TO_TIMESTAMP('2025-07-10 11:43:28.366','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 20
-- UI Element Group: flags
-- 2025-07-10T08:43:41.331Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548289,553246,TO_TIMESTAMP('2025-07-10 11:43:41.153','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2025-07-10 11:43:41.153','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 20 -> flags.Aktiv
-- Column: OAuth2_Client.IsActive
-- 2025-07-10T08:43:54.506Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749954,0,548281,553246,635161,'F',TO_TIMESTAMP('2025-07-10 11:43:53.314','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2025-07-10 11:43:53.314','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-07-10T08:44:03.595Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548289,553247,TO_TIMESTAMP('2025-07-10 11:44:03.422','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org&client',20,TO_TIMESTAMP('2025-07-10 11:44:03.422','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 20 -> org&client.Organisation
-- Column: OAuth2_Client.AD_Org_ID
-- 2025-07-10T08:44:13.685Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749953,0,548281,553247,635162,'F',TO_TIMESTAMP('2025-07-10 11:44:13.52','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2025-07-10 11:44:13.52','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 20 -> org&client.Mandant
-- Column: OAuth2_Client.AD_Client_ID
-- 2025-07-10T08:44:20.177Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,749952,0,548281,553247,635163,'F',TO_TIMESTAMP('2025-07-10 11:44:20.0','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2025-07-10 11:44:20.0','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> default.Interner Name
-- Column: OAuth2_Client.InternalName
-- 2025-07-10T08:44:50.884Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-07-10 11:44:50.884','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=635151
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> default.Name
-- Column: OAuth2_Client.Name
-- 2025-07-10T08:44:50.894Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-07-10 11:44:50.894','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=635152
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 10 -> default.Beschreibung
-- Column: OAuth2_Client.Description
-- 2025-07-10T08:44:50.906Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-07-10 11:44:50.906','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=635153
;

-- UI Element: OAuth2 Client(541911,D) -> OAuth2 Client(548281,D) -> main -> 20 -> flags.Aktiv
-- Column: OAuth2_Client.IsActive
-- 2025-07-10T08:44:50.916Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-07-10 11:44:50.916','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=635161
;

-- 2025-07-10T08:45:44.602Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583796,0,TO_TIMESTAMP('2025-07-10 11:45:44.415','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','OAuth2','OAuth2',TO_TIMESTAMP('2025-07-10 11:45:44.415','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:45:44.606Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583796 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: OAuth2
-- Action Type: null
-- 2025-07-10T08:46:00.791Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,583796,542236,0,TO_TIMESTAMP('2025-07-10 11:46:00.62','YYYY-MM-DD HH24:MI:SS.US'),100,'D','OAuth2','Y','N','N','N','Y','OAuth2',TO_TIMESTAMP('2025-07-10 11:46:00.62','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:46:00.795Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542236 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-07-10T08:46:00.798Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542236, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542236)
;

-- 2025-07-10T08:46:00.811Z
/* DDL */  select update_menu_translation_from_ad_element(583796)
;

-- Reordering children of `System Rules`
-- Node name: `System (AD_System)`
-- 2025-07-10T08:46:09.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=334 AND AD_Tree_ID=10
;

-- Node name: `System Registration (AD_Registration)`
-- 2025-07-10T08:46:09.120Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=498 AND AD_Tree_ID=10
;

-- Node name: `Language Setup`
-- 2025-07-10T08:46:09.122Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=224 AND AD_Tree_ID=10
;

-- Node name: `Synchronize Doc Translation (org.compiere.process.TranslationDocSync)`
-- 2025-07-10T08:46:09.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=514 AND AD_Tree_ID=10
;

-- Node name: `Translation Import/Export (de.metas.i18n.VTranslationImpExpDialog)`
-- 2025-07-10T08:46:09.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=336 AND AD_Tree_ID=10
;

-- Node name: `System Translation Check (AD_Language)`
-- 2025-07-10T08:46:09.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=341 AND AD_Tree_ID=10
;

-- Node name: `Tree (AD_Tree)`
-- 2025-07-10T08:46:09.127Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=170 AND AD_Tree_ID=10
;

-- Node name: `Tree Maintenance (org.compiere.apps.form.VTreeMaintenance)`
-- 2025-07-10T08:46:09.129Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=465 AND AD_Tree_ID=10
;

-- Node name: `Task (AD_Task)`
-- 2025-07-10T08:46:09.130Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=101 AND AD_Tree_ID=10
;

-- Node name: `System Color (AD_Color)`
-- 2025-07-10T08:46:09.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=294 AND AD_Tree_ID=10
;

-- Node name: `Replication Setup`
-- 2025-07-10T08:46:09.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=395 AND AD_Tree_ID=10
;

-- Node name: `System Image (AD_Image)`
-- 2025-07-10T08:46:09.133Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=296 AND AD_Tree_ID=10
;

-- Node name: `Error Message (AD_Error)`
-- 2025-07-10T08:46:09.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=221 AND AD_Tree_ID=10
;

-- Node name: `Notification Group (AD_NotificationGroup)`
-- 2025-07-10T08:46:09.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541069 AND AD_Tree_ID=10
;

-- Node name: `Notice (AD_Note)`
-- 2025-07-10T08:46:09.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=233 AND AD_Tree_ID=10
;

-- Node name: `Find (indirect use) (AD_Find)`
-- 2025-07-10T08:46:09.137Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=290 AND AD_Tree_ID=10
;

-- Node name: `Country Region and City (C_Country)`
-- 2025-07-10T08:46:09.138Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=109 AND AD_Tree_ID=10
;

-- Node name: `Country area (C_CountryArea)`
-- 2025-07-10T08:46:09.139Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540375 AND AD_Tree_ID=10
;

-- Node name: `System Configurator (AD_SysConfig)`
-- 2025-07-10T08:46:09.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50008 AND AD_Tree_ID=10
;

-- Node name: `OAuth2`
-- 2025-07-10T08:46:09.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542236 AND AD_Tree_ID=10
;

-- Reordering children of `System`
-- Node name: `OAuth2`
-- 2025-07-10T08:46:23.984Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542236 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2025-07-10T08:46:23.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `Costing (Freight etc)`
-- 2025-07-10T08:46:23.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2025-07-10T08:46:23.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config eBay (ExternalSystem_Config_Ebay)`
-- 2025-07-10T08:46:23.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541925 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2025-07-10T08:46:23.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2025-07-10T08:46:23.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2025-07-10T08:46:23.994Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2025-07-10T08:46:23.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2025-07-10T08:46:23.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2025-07-10T08:46:23.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2025-07-10T08:46:23.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2025-07-10T08:46:24Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2025-07-10T08:46:24.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2025-07-10T08:46:24.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2025-07-10T08:46:24.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2025-07-10T08:46:24.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2025-07-10T08:46:24.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2025-07-10T08:46:24.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2025-07-10T08:46:24.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2025-07-10T08:46:24.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2025-07-10T08:46:24.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2025-07-10T08:46:24.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2025-07-10T08:46:24.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2025-07-10T08:46:24.013Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2025-07-10T08:46:24.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2025-07-10T08:46:24.015Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2025-07-10T08:46:24.017Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2025-07-10T08:46:24.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2025-07-10T08:46:24.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2025-07-10T08:46:24.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2025-07-10T08:46:24.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2025-07-10T08:46:24.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2025-07-10T08:46:24.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2025-07-10T08:46:24.025Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2025-07-10T08:46:24.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2025-07-10T08:46:24.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2025-07-10T08:46:24.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2025-07-10T08:46:24.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2025-07-10T08:46:24.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2025-07-10T08:46:24.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2025-07-10T08:46:24.036Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2025-07-10T08:46:24.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2025-07-10T08:46:24.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2025-07-10T08:46:24.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2025-07-10T08:46:24.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2025-07-10T08:46:24.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2025-07-10T08:46:24.043Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2025-07-10T08:46:24.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2025-07-10T08:46:24.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2025-07-10T08:46:24.048Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2025-07-10T08:46:24.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2025-07-10T08:46:24.050Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2025-07-10T08:46:24.051Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2025-07-10T08:46:24.052Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2025-07-10T08:46:24.053Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2025-07-10T08:46:24.054Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2025-07-10T08:46:24.055Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2025-07-10T08:46:24.057Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2025-07-10T08:46:24.058Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2025-07-10T08:46:24.059Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2025-07-10T08:46:24.060Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2025-07-10T08:46:24.061Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2025-07-10T08:46:24.062Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2025-07-10T08:46:24.064Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2025-07-10T08:46:24.065Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2025-07-10T08:46:24.067Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2025-07-10T08:46:24.069Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-07-10T08:46:24.070Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-07-10T08:46:24.071Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-07-10T08:46:24.072Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2025-07-10T08:46:24.074Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2025-07-10T08:46:24.076Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2025-07-10T08:46:24.077Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2025-07-10T08:46:24.078Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2025-07-10T08:46:24.079Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2025-07-10T08:46:24.080Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Change System Base Language (de.metas.process.ExecuteUpdateSQL)`
-- 2025-07-10T08:46:24.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541973 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2025-07-10T08:46:24.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2025-07-10T08:46:24.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `Remove documents between dates (de.metas.process.ExecuteUpdateSQL)`
-- 2025-07-10T08:46:24.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541926 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2025-07-10T08:46:24.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2025-07-10T08:46:24.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2025-07-10T08:46:24.089Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `Import Invoice Candidate (I_Invoice_Candidate)`
-- 2025-07-10T08:46:24.090Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541996 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2025-07-10T08:46:24.091Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2025-07-10T08:46:24.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `External system config SAP (ExternalSystem_Config_SAP)`
-- 2025-07-10T08:46:24.093Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542022 AND AD_Tree_ID=10
;

-- Window: OAuth2 Client, InternalName=oauth2Client
-- 2025-07-10T08:46:44.739Z
UPDATE AD_Window SET InternalName='oauth2Client',Updated=TO_TIMESTAMP('2025-07-10 11:46:44.738','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541911
;

-- Name: OAuth2 Client
-- Action Type: W
-- Window: OAuth2 Client(541911,D)
-- 2025-07-10T08:47:05.351Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583787,542237,0,541911,TO_TIMESTAMP('2025-07-10 11:47:05.187','YYYY-MM-DD HH24:MI:SS.US'),100,'D','oauth2Client','Y','N','N','N','N','OAuth2 Client',TO_TIMESTAMP('2025-07-10 11:47:05.187','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-10T08:47:05.356Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542237 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-07-10T08:47:05.364Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542237, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542237)
;

-- 2025-07-10T08:47:05.368Z
/* DDL */  select update_menu_translation_from_ad_element(583787)
;

-- Reordering children of `OAuth2`
-- Node name: `OAuth2 Client`
-- 2025-07-10T08:47:06.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=542236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542237 AND AD_Tree_ID=10
;

