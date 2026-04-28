-- Table: MobileUI_UserProfile_Picking
-- Table: MobileUI_UserProfile_Picking
-- 2023-10-16T16:04:21.401Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542373,'N',TO_TIMESTAMP('2023-10-16 19:04:21','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Mobile UI Picking Profile','NP','L','MobileUI_UserProfile_Picking','DTI',TO_TIMESTAMP('2023-10-16 19:04:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:21.407Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542373 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-10-16T16:04:21.529Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556311,TO_TIMESTAMP('2023-10-16 19:04:21','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table MobileUI_UserProfile_Picking',1,'Y','N','Y','Y','MobileUI_UserProfile_Picking','N',1000000,TO_TIMESTAMP('2023-10-16 19:04:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:04:21.541Z
CREATE SEQUENCE MOBILEUI_USERPROFILE_PICKING_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: MobileUI_UserProfile_Picking.AD_Client_ID
-- Column: MobileUI_UserProfile_Picking.AD_Client_ID
-- 2023-10-16T16:04:32.409Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587554,102,0,19,542373,'AD_Client_ID',TO_TIMESTAMP('2023-10-16 19:04:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-10-16 19:04:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:32.412Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587554 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:32.476Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- 2023-10-16T16:04:34.183Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587555,113,0,30,542373,'AD_Org_ID',TO_TIMESTAMP('2023-10-16 19:04:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-10-16 19:04:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:34.186Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587555 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:34.190Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: MobileUI_UserProfile_Picking.Created
-- Column: MobileUI_UserProfile_Picking.Created
-- 2023-10-16T16:04:35.035Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587556,245,0,16,542373,'Created',TO_TIMESTAMP('2023-10-16 19:04:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-10-16 19:04:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:35.037Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587556 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:35.041Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: MobileUI_UserProfile_Picking.CreatedBy
-- Column: MobileUI_UserProfile_Picking.CreatedBy
-- 2023-10-16T16:04:35.894Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587557,246,0,18,110,542373,'CreatedBy',TO_TIMESTAMP('2023-10-16 19:04:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-10-16 19:04:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:35.896Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587557 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:35.898Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: MobileUI_UserProfile_Picking.IsActive
-- Column: MobileUI_UserProfile_Picking.IsActive
-- 2023-10-16T16:04:36.832Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587558,348,0,20,542373,'IsActive',TO_TIMESTAMP('2023-10-16 19:04:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-10-16 19:04:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:36.835Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587558 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:36.839Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: MobileUI_UserProfile_Picking.Updated
-- Column: MobileUI_UserProfile_Picking.Updated
-- 2023-10-16T16:04:37.561Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587559,607,0,16,542373,'Updated',TO_TIMESTAMP('2023-10-16 19:04:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-10-16 19:04:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:37.563Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587559 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:37.565Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: MobileUI_UserProfile_Picking.UpdatedBy
-- Column: MobileUI_UserProfile_Picking.UpdatedBy
-- 2023-10-16T16:04:38.309Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587560,608,0,18,110,542373,'UpdatedBy',TO_TIMESTAMP('2023-10-16 19:04:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-10-16 19:04:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:38.311Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587560 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:38.314Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-10-16T16:04:38.930Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582765,0,'MobileUI_UserProfile_Picking_ID',TO_TIMESTAMP('2023-10-16 19:04:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mobile UI Picking Profile','Mobile UI Picking Profile',TO_TIMESTAMP('2023-10-16 19:04:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:04:38.933Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582765 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_UserProfile_Picking.MobileUI_UserProfile_Picking_ID
-- Column: MobileUI_UserProfile_Picking.MobileUI_UserProfile_Picking_ID
-- 2023-10-16T16:04:39.563Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587561,582765,0,13,542373,'MobileUI_UserProfile_Picking_ID',TO_TIMESTAMP('2023-10-16 19:04:38','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Mobile UI Picking Profile',0,0,TO_TIMESTAMP('2023-10-16 19:04:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:04:39.565Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587561 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:04:39.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(582765) 
;

-- 2023-10-16T16:04:40.107Z
/* DDL */ CREATE TABLE public.MobileUI_UserProfile_Picking (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MobileUI_UserProfile_Picking_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUI_UserProfile_Picking_Key PRIMARY KEY (MobileUI_UserProfile_Picking_ID))
;

-- 2023-10-16T16:04:40.148Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-10-16T16:04:40.167Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-10-16T16:04:40.176Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-10-16T16:04:40.185Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','IsActive','CHAR(1)',null,null)
;

-- 2023-10-16T16:04:40.213Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-10-16T16:04:40.222Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-10-16T16:04:40.231Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','MobileUI_UserProfile_Picking_ID','NUMERIC(10)',null,null)
;

-- Column: MobileUI_UserProfile_Picking.Name
-- Column: MobileUI_UserProfile_Picking.Name
-- 2023-10-16T16:05:57.492Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587562,469,0,10,542373,'Name',TO_TIMESTAMP('2023-10-16 19:05:57','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-10-16 19:05:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:05:57.494Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587562 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:05:57.497Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2023-10-16T16:05:58.894Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN Name VARCHAR(255) NOT NULL')
;

-- Window: Mobile UI Picking Profile, InternalName=null
-- Window: Mobile UI Picking Profile, InternalName=null
-- 2023-10-16T16:07:45.031Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582765,0,541743,TO_TIMESTAMP('2023-10-16 19:07:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Mobile UI Picking Profile','N',TO_TIMESTAMP('2023-10-16 19:07:44','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-10-16T16:07:45.032Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541743 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-10-16T16:07:45.035Z
/* DDL */  select update_window_translation_from_ad_element(582765) 
;

-- 2023-10-16T16:07:45.046Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541743
;

-- 2023-10-16T16:07:45.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541743)
;

-- Tab: Mobile UI Picking Profile -> Mobile UI Picking Profile
-- Table: MobileUI_UserProfile_Picking
-- Tab: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile
-- Table: MobileUI_UserProfile_Picking
-- 2023-10-16T16:08:36.196Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582765,0,547258,542373,541743,'Y',TO_TIMESTAMP('2023-10-16 19:08:36','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','MobileUI_UserProfile_Picking','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Mobile UI Picking Profile','N',10,0,TO_TIMESTAMP('2023-10-16 19:08:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:08:36.198Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547258 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-16T16:08:36.201Z
/* DDL */  select update_tab_translation_from_ad_element(582765) 
;

-- 2023-10-16T16:08:36.208Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547258)
;

-- Field: Mobile UI Picking Profile -> Mobile UI Picking Profile -> Mandant
-- Column: MobileUI_UserProfile_Picking.AD_Client_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> Mandant
-- Column: MobileUI_UserProfile_Picking.AD_Client_ID
-- 2023-10-16T16:08:43.470Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587554,721585,0,547258,TO_TIMESTAMP('2023-10-16 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-16 19:08:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:08:43.471Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721585 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:08:43.474Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-10-16T16:08:43.836Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721585
;

-- 2023-10-16T16:08:43.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721585)
;

-- Field: Mobile UI Picking Profile -> Mobile UI Picking Profile -> Sektion
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> Sektion
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- 2023-10-16T16:08:44.213Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587555,721586,0,547258,TO_TIMESTAMP('2023-10-16 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-10-16 19:08:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:08:44.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721586 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:08:44.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-10-16T16:08:44.310Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721586
;

-- 2023-10-16T16:08:44.311Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721586)
;

-- Field: Mobile UI Picking Profile -> Mobile UI Picking Profile -> Aktiv
-- Column: MobileUI_UserProfile_Picking.IsActive
-- Field: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> Aktiv
-- Column: MobileUI_UserProfile_Picking.IsActive
-- 2023-10-16T16:08:44.596Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587558,721587,0,547258,TO_TIMESTAMP('2023-10-16 19:08:44','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-16 19:08:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:08:44.597Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721587 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:08:44.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-10-16T16:08:44.703Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721587
;

-- 2023-10-16T16:08:44.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721587)
;

-- Field: Mobile UI Picking Profile -> Mobile UI Picking Profile -> Mobile UI Picking Profile
-- Column: MobileUI_UserProfile_Picking.MobileUI_UserProfile_Picking_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> Mobile UI Picking Profile
-- Column: MobileUI_UserProfile_Picking.MobileUI_UserProfile_Picking_ID
-- 2023-10-16T16:08:44.804Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587561,721588,0,547258,TO_TIMESTAMP('2023-10-16 19:08:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile UI Picking Profile',TO_TIMESTAMP('2023-10-16 19:08:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:08:44.805Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721588 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:08:44.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582765) 
;

-- 2023-10-16T16:08:44.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721588
;

-- 2023-10-16T16:08:44.810Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721588)
;

-- Field: Mobile UI Picking Profile -> Mobile UI Picking Profile -> Name
-- Column: MobileUI_UserProfile_Picking.Name
-- Field: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> Name
-- Column: MobileUI_UserProfile_Picking.Name
-- 2023-10-16T16:08:44.941Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587562,721589,0,547258,TO_TIMESTAMP('2023-10-16 19:08:44','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-10-16 19:08:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:08:44.942Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721589 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:08:44.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-10-16T16:08:44.994Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721589
;

-- 2023-10-16T16:08:44.995Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721589)
;

-- Tab: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D)
-- UI Section: main
-- 2023-10-16T16:09:08.058Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547258,545853,TO_TIMESTAMP('2023-10-16 19:09:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-16 19:09:07','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-10-16T16:09:08.060Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545853 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main
-- UI Column: 10
-- 2023-10-16T16:09:15.768Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547136,545853,TO_TIMESTAMP('2023-10-16 19:09:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-16 19:09:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main
-- UI Column: 20
-- 2023-10-16T16:09:17.381Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547137,545853,TO_TIMESTAMP('2023-10-16 19:09:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-10-16 19:09:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main -> 10
-- UI Element Group: default
-- 2023-10-16T16:09:27.715Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547136,551251,TO_TIMESTAMP('2023-10-16 19:09:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-10-16 19:09:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Picking Profile -> Mobile UI Picking Profile.Name
-- Column: MobileUI_UserProfile_Picking.Name
-- UI Element: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main -> 10 -> default.Name
-- Column: MobileUI_UserProfile_Picking.Name
-- 2023-10-16T16:09:37.928Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721589,0,547258,621115,551251,'F',TO_TIMESTAMP('2023-10-16 19:09:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-10-16 19:09:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main -> 20
-- UI Element Group: flags
-- 2023-10-16T16:09:55.187Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547137,551252,TO_TIMESTAMP('2023-10-16 19:09:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-10-16 19:09:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Picking Profile -> Mobile UI Picking Profile.Aktiv
-- Column: MobileUI_UserProfile_Picking.IsActive
-- UI Element: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main -> 20 -> flags.Aktiv
-- Column: MobileUI_UserProfile_Picking.IsActive
-- 2023-10-16T16:10:09.357Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721587,0,547258,621116,551252,'F',TO_TIMESTAMP('2023-10-16 19:10:09','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-10-16 19:10:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main -> 20
-- UI Element Group: org
-- 2023-10-16T16:10:21.942Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547137,551253,TO_TIMESTAMP('2023-10-16 19:10:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2023-10-16 19:10:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Picking Profile -> Mobile UI Picking Profile.Sektion
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- UI Element: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main -> 20 -> org.Sektion
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- 2023-10-16T16:10:34.371Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721586,0,547258,621117,551253,'F',TO_TIMESTAMP('2023-10-16 19:10:34','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-10-16 19:10:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Picking Profile -> Mobile UI Picking Profile.Mandant
-- Column: MobileUI_UserProfile_Picking.AD_Client_ID
-- UI Element: Mobile UI Picking Profile(541743,D) -> Mobile UI Picking Profile(547258,D) -> main -> 20 -> org.Mandant
-- Column: MobileUI_UserProfile_Picking.AD_Client_ID
-- 2023-10-16T16:10:38.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721585,0,547258,621118,551253,'F',TO_TIMESTAMP('2023-10-16 19:10:38','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-10-16 19:10:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Table: MobileUI_UserProfile_Picking_BPartner
-- Table: MobileUI_UserProfile_Picking_BPartner
-- 2023-10-16T16:17:53.635Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542374,'N',TO_TIMESTAMP('2023-10-16 19:17:53','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Customers','NP','L','MobileUI_UserProfile_Picking_BPartner','DTI',TO_TIMESTAMP('2023-10-16 19:17:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:17:53.643Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542374 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-10-16T16:17:53.740Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556312,TO_TIMESTAMP('2023-10-16 19:17:53','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table MobileUI_UserProfile_Picking_BPartner',1,'Y','N','Y','Y','MobileUI_UserProfile_Picking_BPartner','N',1000000,TO_TIMESTAMP('2023-10-16 19:17:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:17:53.750Z
CREATE SEQUENCE MOBILEUI_USERPROFILE_PICKING_BPARTNER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Client_ID
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Client_ID
-- 2023-10-16T16:18:00.806Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587563,102,0,19,542374,'AD_Client_ID',TO_TIMESTAMP('2023-10-16 19:18:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-10-16 19:18:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:00.809Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587563 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:00.815Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- 2023-10-16T16:18:01.574Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587564,113,0,30,542374,'AD_Org_ID',TO_TIMESTAMP('2023-10-16 19:18:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-10-16 19:18:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:01.576Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587564 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:01.578Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: MobileUI_UserProfile_Picking_BPartner.Created
-- Column: MobileUI_UserProfile_Picking_BPartner.Created
-- 2023-10-16T16:18:02.437Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587565,245,0,16,542374,'Created',TO_TIMESTAMP('2023-10-16 19:18:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-10-16 19:18:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:02.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587565 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:02.440Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: MobileUI_UserProfile_Picking_BPartner.CreatedBy
-- Column: MobileUI_UserProfile_Picking_BPartner.CreatedBy
-- 2023-10-16T16:18:03.269Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587566,246,0,18,110,542374,'CreatedBy',TO_TIMESTAMP('2023-10-16 19:18:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-10-16 19:18:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:03.271Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587566 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:03.273Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: MobileUI_UserProfile_Picking_BPartner.IsActive
-- Column: MobileUI_UserProfile_Picking_BPartner.IsActive
-- 2023-10-16T16:18:04.336Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587567,348,0,20,542374,'IsActive',TO_TIMESTAMP('2023-10-16 19:18:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-10-16 19:18:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:04.338Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587567 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:04.342Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: MobileUI_UserProfile_Picking_BPartner.Updated
-- Column: MobileUI_UserProfile_Picking_BPartner.Updated
-- 2023-10-16T16:18:05.246Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587568,607,0,16,542374,'Updated',TO_TIMESTAMP('2023-10-16 19:18:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-10-16 19:18:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:05.247Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587568 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:05.249Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: MobileUI_UserProfile_Picking_BPartner.UpdatedBy
-- Column: MobileUI_UserProfile_Picking_BPartner.UpdatedBy
-- 2023-10-16T16:18:06.405Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587569,608,0,18,110,542374,'UpdatedBy',TO_TIMESTAMP('2023-10-16 19:18:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-10-16 19:18:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:06.407Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:06.411Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-10-16T16:18:07.276Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582766,0,'MobileUI_UserProfile_Picking_BPartner_ID',TO_TIMESTAMP('2023-10-16 19:18:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Customers','Customers',TO_TIMESTAMP('2023-10-16 19:18:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:18:07.278Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582766 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_BPartner_ID
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_BPartner_ID
-- 2023-10-16T16:18:08.197Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587570,582766,0,13,542374,'MobileUI_UserProfile_Picking_BPartner_ID',TO_TIMESTAMP('2023-10-16 19:18:07','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Customers',0,0,TO_TIMESTAMP('2023-10-16 19:18:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:18:08.198Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:18:08.201Z
/* DDL */  select update_Column_Translation_From_AD_Element(582766) 
;

-- 2023-10-16T16:18:08.859Z
/* DDL */ CREATE TABLE public.MobileUI_UserProfile_Picking_BPartner (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MobileUI_UserProfile_Picking_BPartner_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUI_UserProfile_Picking_BPartner_Key PRIMARY KEY (MobileUI_UserProfile_Picking_BPartner_ID))
;

-- 2023-10-16T16:18:08.877Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-10-16T16:18:08.893Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-10-16T16:18:08.907Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-10-16T16:18:08.920Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','IsActive','CHAR(1)',null,null)
;

-- 2023-10-16T16:18:08.955Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-10-16T16:18:08.964Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-10-16T16:18:08.976Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','MobileUI_UserProfile_Picking_BPartner_ID','NUMERIC(10)',null,null)
;

-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- 2023-10-16T16:20:13.662Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587571,187,0,30,138,542374,'C_BPartner_ID',TO_TIMESTAMP('2023-10-16 19:20:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',0,22,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2023-10-16 19:20:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:20:13.664Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:20:13.667Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2023-10-16T16:20:15.227Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_BPartner','ALTER TABLE public.MobileUI_UserProfile_Picking_BPartner ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2023-10-16T16:20:15.234Z
ALTER TABLE MobileUI_UserProfile_Picking_BPartner ADD CONSTRAINT CBPartner_MobileUIUserProfilePickingBPartner FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_ID
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_ID
-- 2023-10-16T16:20:39.424Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587572,582765,0,19,542374,'MobileUI_UserProfile_Picking_ID',TO_TIMESTAMP('2023-10-16 19:20:39','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mobile UI Picking Profile',0,0,TO_TIMESTAMP('2023-10-16 19:20:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-16T16:20:39.428Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587572 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-16T16:20:39.432Z
/* DDL */  select update_Column_Translation_From_AD_Element(582765) 
;

-- 2023-10-16T16:20:40.522Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_BPartner','ALTER TABLE public.MobileUI_UserProfile_Picking_BPartner ADD COLUMN MobileUI_UserProfile_Picking_ID NUMERIC(10) NOT NULL')
;

-- 2023-10-16T16:20:40.528Z
ALTER TABLE MobileUI_UserProfile_Picking_BPartner ADD CONSTRAINT MobileUIUserProfilePicking_MobileUIUserProfilePickingBPartner FOREIGN KEY (MobileUI_UserProfile_Picking_ID) REFERENCES public.MobileUI_UserProfile_Picking DEFERRABLE INITIALLY DEFERRED
;

-- 2023-10-16T16:20:46.250Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking_bpartner','C_BPartner_ID','NUMERIC(10)',null,null)
;

CREATE UNIQUE INDEX IDX_unique_BPartner_id_and_UserProfilePicking_ID ON MobileUI_UserProfile_Picking_BPartner (MobileUI_UserProfile_Picking_ID,C_BPartner_ID)
;

-- Tab: Mobile UI Picking Profile -> Customers
-- Table: MobileUI_UserProfile_Picking_BPartner
-- Tab: Mobile UI Picking Profile(541743,D) -> Customers
-- Table: MobileUI_UserProfile_Picking_BPartner
-- 2023-10-16T16:38:20.071Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582766,0,547259,542374,541743,'Y',TO_TIMESTAMP('2023-10-16 19:38:19','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','MobileUI_UserProfile_Picking_BPartner','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Customers','N',20,1,TO_TIMESTAMP('2023-10-16 19:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:38:20.072Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547259 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-10-16T16:38:20.073Z
/* DDL */  select update_tab_translation_from_ad_element(582766) 
;

-- 2023-10-16T16:38:20.077Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547259)
;

-- Field: Mobile UI Picking Profile -> Customers -> Mandant
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Client_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> Mandant
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Client_ID
-- 2023-10-16T16:38:25.018Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587563,721590,0,547259,TO_TIMESTAMP('2023-10-16 19:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-10-16 19:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:38:25.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721590 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:38:25.022Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-10-16T16:38:25.111Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721590
;

-- 2023-10-16T16:38:25.112Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721590)
;

-- Field: Mobile UI Picking Profile -> Customers -> Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- 2023-10-16T16:38:25.206Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587564,721591,0,547259,TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:38:25.207Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721591 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:38:25.208Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-10-16T16:38:25.282Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721591
;

-- 2023-10-16T16:38:25.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721591)
;

-- Field: Mobile UI Picking Profile -> Customers -> Aktiv
-- Column: MobileUI_UserProfile_Picking_BPartner.IsActive
-- Field: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> Aktiv
-- Column: MobileUI_UserProfile_Picking_BPartner.IsActive
-- 2023-10-16T16:38:25.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587567,721592,0,547259,TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:38:25.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721592 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:38:25.376Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-10-16T16:38:25.466Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721592
;

-- 2023-10-16T16:38:25.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721592)
;

-- Field: Mobile UI Picking Profile -> Customers -> Customers
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_BPartner_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> Customers
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_BPartner_ID
-- 2023-10-16T16:38:25.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587570,721593,0,547259,TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Customers',TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:38:25.559Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:38:25.560Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582766) 
;

-- 2023-10-16T16:38:25.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721593
;

-- 2023-10-16T16:38:25.563Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721593)
;

-- Field: Mobile UI Picking Profile -> Customers -> Geschäftspartner
-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> Geschäftspartner
-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- 2023-10-16T16:38:25.656Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587571,721594,0,547259,TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',22,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:38:25.657Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721594 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:38:25.658Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-10-16T16:38:25.706Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721594
;

-- 2023-10-16T16:38:25.707Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721594)
;

-- Field: Mobile UI Picking Profile -> Customers -> Mobile UI Picking Profile
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_ID
-- Field: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> Mobile UI Picking Profile
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_ID
-- 2023-10-16T16:38:25.798Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587572,721595,0,547259,TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile UI Picking Profile',TO_TIMESTAMP('2023-10-16 19:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-16T16:38:25.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-16T16:38:25.800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582765) 
;

-- 2023-10-16T16:38:25.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721595
;

-- 2023-10-16T16:38:25.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721595)
;

-- Tab: Mobile UI Picking Profile(541743,D) -> Customers(547259,D)
-- UI Section: main
-- 2023-10-16T16:39:19.868Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547259,545854,TO_TIMESTAMP('2023-10-16 19:39:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-16 19:39:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-10-16T16:39:19.870Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545854 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> main
-- UI Column: 10
-- 2023-10-16T16:39:32.853Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547138,545854,TO_TIMESTAMP('2023-10-16 19:39:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-10-16 19:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> main -> 10
-- UI Element Group: default
-- 2023-10-16T16:39:42.608Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547138,551254,TO_TIMESTAMP('2023-10-16 19:39:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-10-16 19:39:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Picking Profile -> Customers.Geschäftspartner
-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- UI Element: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> main -> 10 -> default.Geschäftspartner
-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- 2023-10-16T16:39:59.951Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721594,0,547259,621119,551254,'F',TO_TIMESTAMP('2023-10-16 19:39:59','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',10,0,0,TO_TIMESTAMP('2023-10-16 19:39:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Picking Profile -> Customers.Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- UI Element: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> main -> 10 -> default.Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- 2023-10-16T16:40:37.936Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721591,0,547259,621120,551254,'F',TO_TIMESTAMP('2023-10-16 19:40:37','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2023-10-16 19:40:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Picking Profile -> Customers.Mandant
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Client_ID
-- UI Element: Mobile UI Picking Profile(541743,D) -> Customers(547259,D) -> main -> 10 -> default.Mandant
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Client_ID
-- 2023-10-16T16:40:42.267Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,721590,0,547259,621121,551254,'F',TO_TIMESTAMP('2023-10-16 19:40:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2023-10-16 19:40:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Mobile UI Picking Profile -> Customers
-- Table: MobileUI_UserProfile_Picking_BPartner
-- Tab: Mobile UI Picking Profile(541743,D) -> Customers
-- Table: MobileUI_UserProfile_Picking_BPartner
-- 2023-10-16T16:46:32.607Z
UPDATE AD_Tab SET AD_Column_ID=587572,Updated=TO_TIMESTAMP('2023-10-16 19:46:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547259
;

-- Name: Mobile UI Picking Profile
-- Action Type: W
-- Window: Mobile UI Picking Profile(541743,D)
-- 2023-10-17T08:37:31.736Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582765,542119,0,541743,TO_TIMESTAMP('2023-10-17 11:37:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Mobile UI Picking Profile','Y','N','N','Y','N','Mobile UI Picking Profile',TO_TIMESTAMP('2023-10-17 11:37:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-17T08:37:31.739Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542119 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-10-17T08:37:31.743Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542119, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542119)
;

-- 2023-10-17T08:37:31.802Z
/* DDL */  select update_menu_translation_from_ad_element(582765) 
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2023-10-17T08:37:32.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2023-10-17T08:37:32.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2023-10-17T08:37:32.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2023-10-17T08:37:32.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2023-10-17T08:37:32.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2023-10-17T08:37:32.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2023-10-17T08:37:32.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2023-10-17T08:37:32.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2023-10-17T08:37:32.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2023-10-17T08:37:32.506Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2023-10-17T08:37:32.506Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2023-10-17T08:37:32.507Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2023-10-17T08:37:32.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-10-17T08:37:32.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2023-10-17T08:37:32.509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2023-10-17T08:37:32.510Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-10-17T08:37:32.510Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2023-10-17T08:37:32.511Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-10-17T08:37:32.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-10-17T08:37:32.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2023-10-17T08:37:32.513Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI Picking Profile`
-- 2023-10-17T08:37:32.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542119 AND AD_Tree_ID=10
;

-- Reordering children of `Picking`
-- Node name: `Mobile UI Picking Profile`
-- 2023-10-17T08:38:46.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542119 AND AD_Tree_ID=10
;

-- Node name: `Picking Job (M_Picking_Job)`
-- 2023-10-17T08:38:46.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541857 AND AD_Tree_ID=10
;

-- Node name: `Picking Job Step (M_Picking_Job_Step)`
-- 2023-10-17T08:38:46.013Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541858 AND AD_Tree_ID=10
;

-- Node name: `Print Picking Slot QR Codes (de.metas.picking.process.M_PickingSlot_PrintQRCodes)`
-- 2023-10-17T08:38:46.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541908 AND AD_Tree_ID=10
;

-- Element: MobileUI_UserProfile_Picking_ID
-- 2023-10-17T10:41:08.131Z
UPDATE AD_Element_Trl SET Name='Mobile UI Kommissionierprofil', PrintName='Mobile UI Kommissionierprofil',Updated=TO_TIMESTAMP('2023-10-17 13:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582765 AND AD_Language='de_CH'
;

-- 2023-10-17T10:41:08.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582765,'de_CH') 
;

-- Element: MobileUI_UserProfile_Picking_ID
-- 2023-10-17T10:41:11.205Z
UPDATE AD_Element_Trl SET Name='Mobile UI Kommissionierprofil', PrintName='Mobile UI Kommissionierprofil',Updated=TO_TIMESTAMP('2023-10-17 13:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582765 AND AD_Language='de_DE'
;

-- 2023-10-17T10:41:11.206Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582765,'de_DE') 
;

-- 2023-10-17T10:41:11.207Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582765,'de_DE') 
;

-- Element: MobileUI_UserProfile_Picking_ID
-- 2023-10-17T10:41:16.378Z
UPDATE AD_Element_Trl SET Name='Mobile UI Kommissionierprofil', PrintName='Mobile UI Kommissionierprofil',Updated=TO_TIMESTAMP('2023-10-17 13:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582765 AND AD_Language='fr_CH'
;

-- 2023-10-17T10:41:16.381Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582765,'fr_CH') 
;

-- Element: MobileUI_UserProfile_Picking_BPartner_ID
-- 2023-10-17T10:41:44.245Z
UPDATE AD_Element_Trl SET Name='Kunden', PrintName='Kunden',Updated=TO_TIMESTAMP('2023-10-17 13:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582766 AND AD_Language='de_CH'
;

-- 2023-10-17T10:41:44.248Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582766,'de_CH') 
;

-- Element: MobileUI_UserProfile_Picking_BPartner_ID
-- 2023-10-17T10:41:46.519Z
UPDATE AD_Element_Trl SET Name='Kunden', PrintName='Kunden',Updated=TO_TIMESTAMP('2023-10-17 13:41:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582766 AND AD_Language='de_DE'
;

-- 2023-10-17T10:41:46.520Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582766,'de_DE') 
;

-- 2023-10-17T10:41:46.521Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582766,'de_DE') 
;

-- Element: MobileUI_UserProfile_Picking_BPartner_ID
-- 2023-10-17T10:41:49.285Z
UPDATE AD_Element_Trl SET Name='Kunden', PrintName='Kunden',Updated=TO_TIMESTAMP('2023-10-17 13:41:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582766 AND AD_Language='fr_CH'
;

-- 2023-10-17T10:41:49.288Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582766,'fr_CH') 
;

