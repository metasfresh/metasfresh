-- Table: MobileUI_HUManager
-- Table: MobileUI_HUManager
-- 2024-07-05T13:14:29.079Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542422,'A','N',TO_TIMESTAMP('2024-07-05 16:14:28','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Mobile UI HU Manager','NP','L','MobileUI_HUManager','DTI',TO_TIMESTAMP('2024-07-05 16:14:28','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-07-05T13:14:29.097Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542422 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-07-05T13:14:29.278Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556352,TO_TIMESTAMP('2024-07-05 16:14:29','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table MobileUI_HUManager',1,'Y','N','Y','Y','MobileUI_HUManager','N',1000000,TO_TIMESTAMP('2024-07-05 16:14:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:14:29.308Z
CREATE SEQUENCE MOBILEUI_HUMANAGER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: MobileUI_HUManager.AD_Client_ID
-- Column: MobileUI_HUManager.AD_Client_ID
-- 2024-07-05T13:15:03.163Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588692,102,0,19,542422,'AD_Client_ID',TO_TIMESTAMP('2024-07-05 16:15:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-07-05 16:15:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:03.168Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588692 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:03.233Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: MobileUI_HUManager.AD_Org_ID
-- Column: MobileUI_HUManager.AD_Org_ID
-- 2024-07-05T13:15:06.470Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588693,113,0,30,542422,'AD_Org_ID',TO_TIMESTAMP('2024-07-05 16:15:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-07-05 16:15:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:06.472Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:06.476Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: MobileUI_HUManager.Created
-- Column: MobileUI_HUManager.Created
-- 2024-07-05T13:15:08.575Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588694,245,0,16,542422,'Created',TO_TIMESTAMP('2024-07-05 16:15:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-07-05 16:15:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:08.579Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588694 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:08.583Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: MobileUI_HUManager.CreatedBy
-- Column: MobileUI_HUManager.CreatedBy
-- 2024-07-05T13:15:10.230Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588695,246,0,18,110,542422,'CreatedBy',TO_TIMESTAMP('2024-07-05 16:15:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-07-05 16:15:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:10.232Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588695 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:10.236Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: MobileUI_HUManager.IsActive
-- Column: MobileUI_HUManager.IsActive
-- 2024-07-05T13:15:11.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588696,348,0,20,542422,'IsActive',TO_TIMESTAMP('2024-07-05 16:15:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-07-05 16:15:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:11.686Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588696 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:11.689Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: MobileUI_HUManager.Updated
-- Column: MobileUI_HUManager.Updated
-- 2024-07-05T13:15:13.111Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588697,607,0,16,542422,'Updated',TO_TIMESTAMP('2024-07-05 16:15:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-07-05 16:15:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:13.131Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588697 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:13.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: MobileUI_HUManager.UpdatedBy
-- Column: MobileUI_HUManager.UpdatedBy
-- 2024-07-05T13:15:14.378Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588698,608,0,18,110,542422,'UpdatedBy',TO_TIMESTAMP('2024-07-05 16:15:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-07-05 16:15:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:14.379Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588698 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:14.381Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-07-05T13:15:15.274Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583174,0,'MobileUI_HUManager_ID',TO_TIMESTAMP('2024-07-05 16:15:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mobile UI HU Manager','Mobile UI HU Manager',TO_TIMESTAMP('2024-07-05 16:15:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:15:15.278Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583174 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_HUManager.MobileUI_HUManager_ID
-- Column: MobileUI_HUManager.MobileUI_HUManager_ID
-- 2024-07-05T13:15:16.285Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588699,583174,0,13,542422,'MobileUI_HUManager_ID',TO_TIMESTAMP('2024-07-05 16:15:15','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Mobile UI HU Manager',0,0,TO_TIMESTAMP('2024-07-05 16:15:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:15:16.286Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588699 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:15:16.288Z
/* DDL */  select update_Column_Translation_From_AD_Element(583174) 
;

-- 2024-07-05T13:15:17.089Z
/* DDL */ CREATE TABLE public.MobileUI_HUManager (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MobileUI_HUManager_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUI_HUManager_Key PRIMARY KEY (MobileUI_HUManager_ID))
;

-- 2024-07-05T13:15:17.203Z
INSERT INTO t_alter_column values('mobileui_humanager','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-07-05T13:15:17.234Z
INSERT INTO t_alter_column values('mobileui_humanager','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-07-05T13:15:17.253Z
INSERT INTO t_alter_column values('mobileui_humanager','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-07-05T13:15:17.273Z
INSERT INTO t_alter_column values('mobileui_humanager','IsActive','CHAR(1)',null,null)
;

-- 2024-07-05T13:15:17.320Z
INSERT INTO t_alter_column values('mobileui_humanager','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-07-05T13:15:17.338Z
INSERT INTO t_alter_column values('mobileui_humanager','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-07-05T13:15:17.355Z
INSERT INTO t_alter_column values('mobileui_humanager','MobileUI_HUManager_ID','NUMERIC(10)',null,null)
;

-- Column: MobileUI_HUManager.Name
-- Column: MobileUI_HUManager.Name
-- 2024-07-05T13:16:11.141Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588700,469,0,10,542422,'XX','Name',TO_TIMESTAMP('2024-07-05 16:16:10','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2024-07-05 16:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:16:11.145Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588700 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:16:11.150Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2024-07-05T13:21:29.384Z
/* DDL */ SELECT public.db_alter_table('MobileUI_HUManager','ALTER TABLE public.MobileUI_HUManager ADD COLUMN Name VARCHAR(255) NOT NULL')
;

-- Table: MobileUI_HUManager_Attribute
-- Table: MobileUI_HUManager_Attribute
-- 2024-07-05T13:22:50.975Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542423,'A','N',TO_TIMESTAMP('2024-07-05 16:22:50','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Merkmale','NP','L','MobileUI_HUManager_Attribute','DTI',TO_TIMESTAMP('2024-07-05 16:22:50','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-07-05T13:22:50.977Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542423 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-07-05T13:22:51.060Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556353,TO_TIMESTAMP('2024-07-05 16:22:50','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table MobileUI_HUManager_Attribute',1,'Y','N','Y','Y','MobileUI_HUManager_Attribute','N',1000000,TO_TIMESTAMP('2024-07-05 16:22:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:22:51.067Z
CREATE SEQUENCE MOBILEUI_HUMANAGER_ATTRIBUTE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: MobileUI_HUManager_Attribute.AD_Client_ID
-- Column: MobileUI_HUManager_Attribute.AD_Client_ID
-- 2024-07-05T13:22:59.759Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588701,102,0,19,542423,'AD_Client_ID',TO_TIMESTAMP('2024-07-05 16:22:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-07-05 16:22:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:22:59.767Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588701 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:22:59.778Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: MobileUI_HUManager_Attribute.AD_Org_ID
-- Column: MobileUI_HUManager_Attribute.AD_Org_ID
-- 2024-07-05T13:23:01.669Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588702,113,0,30,542423,'AD_Org_ID',TO_TIMESTAMP('2024-07-05 16:23:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-07-05 16:23:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:23:01.672Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588702 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:23:01.676Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: MobileUI_HUManager_Attribute.Created
-- Column: MobileUI_HUManager_Attribute.Created
-- 2024-07-05T13:23:03.512Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588703,245,0,16,542423,'Created',TO_TIMESTAMP('2024-07-05 16:23:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-07-05 16:23:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:23:03.514Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588703 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:23:03.518Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: MobileUI_HUManager_Attribute.CreatedBy
-- Column: MobileUI_HUManager_Attribute.CreatedBy
-- 2024-07-05T13:23:05.430Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588704,246,0,18,110,542423,'CreatedBy',TO_TIMESTAMP('2024-07-05 16:23:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-07-05 16:23:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:23:05.433Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588704 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:23:05.439Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: MobileUI_HUManager_Attribute.IsActive
-- Column: MobileUI_HUManager_Attribute.IsActive
-- 2024-07-05T13:23:07.798Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588705,348,0,20,542423,'IsActive',TO_TIMESTAMP('2024-07-05 16:23:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-07-05 16:23:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:23:07.801Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588705 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:23:07.805Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: MobileUI_HUManager_Attribute.Updated
-- Column: MobileUI_HUManager_Attribute.Updated
-- 2024-07-05T13:23:09.901Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588706,607,0,16,542423,'Updated',TO_TIMESTAMP('2024-07-05 16:23:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-07-05 16:23:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:23:09.903Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588706 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:23:09.906Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: MobileUI_HUManager_Attribute.UpdatedBy
-- Column: MobileUI_HUManager_Attribute.UpdatedBy
-- 2024-07-05T13:23:11.632Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588707,608,0,18,110,542423,'UpdatedBy',TO_TIMESTAMP('2024-07-05 16:23:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-07-05 16:23:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:23:11.634Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588707 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:23:11.638Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-07-05T13:23:12.871Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583175,0,'MobileUI_HUManager_Attribute_ID',TO_TIMESTAMP('2024-07-05 16:23:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Merkmale','Merkmale',TO_TIMESTAMP('2024-07-05 16:23:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:23:12.873Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583175 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_Attribute_ID
-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_Attribute_ID
-- 2024-07-05T13:23:14.079Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588708,583175,0,13,542423,'MobileUI_HUManager_Attribute_ID',TO_TIMESTAMP('2024-07-05 16:23:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Merkmale',0,0,TO_TIMESTAMP('2024-07-05 16:23:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:23:14.080Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588708 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:23:14.082Z
/* DDL */  select update_Column_Translation_From_AD_Element(583175) 
;

-- 2024-07-05T13:23:14.888Z
/* DDL */ CREATE TABLE public.MobileUI_HUManager_Attribute (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MobileUI_HUManager_Attribute_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUI_HUManager_Attribute_Key PRIMARY KEY (MobileUI_HUManager_Attribute_ID))
;

-- 2024-07-05T13:23:14.950Z
INSERT INTO t_alter_column values('mobileui_humanager_attribute','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-07-05T13:23:14.961Z
INSERT INTO t_alter_column values('mobileui_humanager_attribute','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-07-05T13:23:14.971Z
INSERT INTO t_alter_column values('mobileui_humanager_attribute','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-07-05T13:23:14.981Z
INSERT INTO t_alter_column values('mobileui_humanager_attribute','IsActive','CHAR(1)',null,null)
;

-- 2024-07-05T13:23:15.006Z
INSERT INTO t_alter_column values('mobileui_humanager_attribute','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-07-05T13:23:15.016Z
INSERT INTO t_alter_column values('mobileui_humanager_attribute','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-07-05T13:23:15.027Z
INSERT INTO t_alter_column values('mobileui_humanager_attribute','MobileUI_HUManager_Attribute_ID','NUMERIC(10)',null,null)
;

-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_ID
-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_ID
-- 2024-07-05T13:24:20.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588709,583174,0,19,542423,'XX','MobileUI_HUManager_ID',TO_TIMESTAMP('2024-07-05 16:24:20','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mobile UI HU Manager',0,0,TO_TIMESTAMP('2024-07-05 16:24:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:24:20.685Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588709 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:24:20.688Z
/* DDL */  select update_Column_Translation_From_AD_Element(583174) 
;

-- 2024-07-05T13:24:22.983Z
/* DDL */ SELECT public.db_alter_table('MobileUI_HUManager_Attribute','ALTER TABLE public.MobileUI_HUManager_Attribute ADD COLUMN MobileUI_HUManager_ID NUMERIC(10) NOT NULL')
;

-- 2024-07-05T13:24:22.995Z
ALTER TABLE MobileUI_HUManager_Attribute ADD CONSTRAINT MobileUIHUManager_MobileUIHUManagerAttribute FOREIGN KEY (MobileUI_HUManager_ID) REFERENCES public.MobileUI_HUManager DEFERRABLE INITIALLY DEFERRED
;

-- Column: MobileUI_HUManager_Attribute.M_Attribute_ID
-- Column: MobileUI_HUManager_Attribute.M_Attribute_ID
-- 2024-07-05T13:28:24.277Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588711,2015,0,30,542423,'XX','M_Attribute_ID',TO_TIMESTAMP('2024-07-05 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt-Merkmal','D',0,22,'Product Attribute like Color, Size','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Merkmal','NP',0,2,TO_TIMESTAMP('2024-07-05 16:28:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:28:24.279Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588711 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:28:24.282Z
/* DDL */  select update_Column_Translation_From_AD_Element(2015) 
;

-- 2024-07-05T13:28:26.496Z
/* DDL */ SELECT public.db_alter_table('MobileUI_HUManager_Attribute','ALTER TABLE public.MobileUI_HUManager_Attribute ADD COLUMN M_Attribute_ID NUMERIC(10) NOT NULL')
;

-- 2024-07-05T13:28:26.536Z
ALTER TABLE MobileUI_HUManager_Attribute ADD CONSTRAINT MAttribute_MobileUIHUManagerAttribute FOREIGN KEY (M_Attribute_ID) REFERENCES public.M_Attribute DEFERRABLE INITIALLY DEFERRED
;

-- Column: MobileUI_HUManager_Attribute.SeqNo
-- Column: MobileUI_HUManager_Attribute.SeqNo
-- 2024-07-05T13:30:30.599Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588712,566,0,11,542423,'XX','SeqNo',TO_TIMESTAMP('2024-07-05 16:30:30','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM MobileUI_HUManager_Attribute WHERE MobileUI_HUManager_ID=@MobileUI_HUManager_ID@','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge','NP',0,0,TO_TIMESTAMP('2024-07-05 16:30:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-05T13:30:30.602Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588712 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:30:30.607Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2024-07-05T13:30:33.070Z
/* DDL */ SELECT public.db_alter_table('MobileUI_HUManager_Attribute','ALTER TABLE public.MobileUI_HUManager_Attribute ADD COLUMN SeqNo NUMERIC(10) NOT NULL')
;

-- Window: Mobile UI HU Manager, InternalName=null
-- Window: Mobile UI HU Manager, InternalName=null
-- 2024-07-05T13:32:53.834Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583174,0,541806,TO_TIMESTAMP('2024-07-05 16:32:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Mobile UI HU Manager','N',TO_TIMESTAMP('2024-07-05 16:32:53','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-07-05T13:32:53.838Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541806 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-07-05T13:32:53.846Z
/* DDL */  select update_window_translation_from_ad_element(583174) 
;

-- 2024-07-05T13:32:53.870Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541806
;

-- 2024-07-05T13:32:53.911Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541806)
;

-- Tab: Mobile UI HU Manager -> Mobile UI HU Manager
-- Table: MobileUI_HUManager
-- Tab: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager
-- Table: MobileUI_HUManager
-- 2024-07-05T13:33:51.869Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583174,0,547557,542422,541806,'Y',TO_TIMESTAMP('2024-07-05 16:33:51','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','MobileUI_HUManager','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Mobile UI HU Manager','N',10,0,TO_TIMESTAMP('2024-07-05 16:33:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:33:51.872Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547557 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-07-05T13:33:51.874Z
/* DDL */  select update_tab_translation_from_ad_element(583174) 
;

-- 2024-07-05T13:33:51.879Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547557)
;

-- Field: Mobile UI HU Manager -> Mobile UI HU Manager -> Mandant
-- Column: MobileUI_HUManager.AD_Client_ID
-- Field: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> Mandant
-- Column: MobileUI_HUManager.AD_Client_ID
-- 2024-07-05T13:33:55.535Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588692,729051,0,547557,TO_TIMESTAMP('2024-07-05 16:33:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-07-05 16:33:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:33:55.539Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:33:55.542Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-07-05T13:33:57.602Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729051
;

-- 2024-07-05T13:33:57.602Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729051)
;

-- Field: Mobile UI HU Manager -> Mobile UI HU Manager -> Sektion
-- Column: MobileUI_HUManager.AD_Org_ID
-- Field: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> Sektion
-- Column: MobileUI_HUManager.AD_Org_ID
-- 2024-07-05T13:33:57.693Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588693,729052,0,547557,TO_TIMESTAMP('2024-07-05 16:33:57','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-07-05 16:33:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:33:57.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:33:57.697Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-07-05T13:33:58.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729052
;

-- 2024-07-05T13:33:58.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729052)
;

-- Field: Mobile UI HU Manager -> Mobile UI HU Manager -> Aktiv
-- Column: MobileUI_HUManager.IsActive
-- Field: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> Aktiv
-- Column: MobileUI_HUManager.IsActive
-- 2024-07-05T13:33:58.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588696,729053,0,547557,TO_TIMESTAMP('2024-07-05 16:33:58','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-07-05 16:33:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:33:58.179Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:33:58.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-07-05T13:33:58.624Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729053
;

-- 2024-07-05T13:33:58.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729053)
;

-- Field: Mobile UI HU Manager -> Mobile UI HU Manager -> Mobile UI HU Manager
-- Column: MobileUI_HUManager.MobileUI_HUManager_ID
-- Field: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> Mobile UI HU Manager
-- Column: MobileUI_HUManager.MobileUI_HUManager_ID
-- 2024-07-05T13:33:58.730Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588699,729054,0,547557,TO_TIMESTAMP('2024-07-05 16:33:58','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile UI HU Manager',TO_TIMESTAMP('2024-07-05 16:33:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:33:58.732Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:33:58.734Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583174) 
;

-- 2024-07-05T13:33:58.740Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729054
;

-- 2024-07-05T13:33:58.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729054)
;

-- Field: Mobile UI HU Manager -> Mobile UI HU Manager -> Name
-- Column: MobileUI_HUManager.Name
-- Field: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> Name
-- Column: MobileUI_HUManager.Name
-- 2024-07-05T13:33:58.846Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588700,729055,0,547557,TO_TIMESTAMP('2024-07-05 16:33:58','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2024-07-05 16:33:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:33:58.848Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:33:58.850Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2024-07-05T13:33:59.060Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729055
;

-- 2024-07-05T13:33:59.061Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729055)
;

-- Tab: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D)
-- UI Section: main
-- 2024-07-05T13:34:12.111Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547557,546141,TO_TIMESTAMP('2024-07-05 16:34:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-07-05 16:34:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-07-05T13:34:12.115Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546141 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main
-- UI Column: 10
-- 2024-07-05T13:34:17.503Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547503,546141,TO_TIMESTAMP('2024-07-05 16:34:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-07-05 16:34:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main
-- UI Column: 20
-- 2024-07-05T13:34:18.780Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547504,546141,TO_TIMESTAMP('2024-07-05 16:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-07-05 16:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 10
-- UI Element Group: default
-- 2024-07-05T13:34:30.298Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547503,551856,TO_TIMESTAMP('2024-07-05 16:34:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-07-05 16:34:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Mobile UI HU Manager.Name
-- Column: MobileUI_HUManager.Name
-- UI Element: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 10 -> default.Name
-- Column: MobileUI_HUManager.Name
-- 2024-07-05T13:34:43.360Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729055,0,547557,624969,551856,'F',TO_TIMESTAMP('2024-07-05 16:34:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2024-07-05 16:34:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 20
-- UI Element Group: flags
-- 2024-07-05T13:34:56.922Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547504,551857,TO_TIMESTAMP('2024-07-05 16:34:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-07-05 16:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Mobile UI HU Manager.Aktiv
-- Column: MobileUI_HUManager.IsActive
-- UI Element: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 20 -> flags.Aktiv
-- Column: MobileUI_HUManager.IsActive
-- 2024-07-05T13:35:10.999Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729053,0,547557,624970,551857,'F',TO_TIMESTAMP('2024-07-05 16:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-07-05 16:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 20
-- UI Element Group: org
-- 2024-07-05T13:35:24.364Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547504,551858,TO_TIMESTAMP('2024-07-05 16:35:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2024-07-05 16:35:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Mobile UI HU Manager.Sektion
-- Column: MobileUI_HUManager.AD_Org_ID
-- UI Element: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 20 -> org.Sektion
-- Column: MobileUI_HUManager.AD_Org_ID
-- 2024-07-05T13:35:33.318Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729052,0,547557,624971,551858,'F',TO_TIMESTAMP('2024-07-05 16:35:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-07-05 16:35:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Mobile UI HU Manager.Mandant
-- Column: MobileUI_HUManager.AD_Client_ID
-- UI Element: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 20 -> org.Mandant
-- Column: MobileUI_HUManager.AD_Client_ID
-- 2024-07-05T13:35:50.680Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729051,0,547557,624972,551858,'F',TO_TIMESTAMP('2024-07-05 16:35:50','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-07-05 16:35:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Mobile UI HU Manager.Name
-- Column: MobileUI_HUManager.Name
-- UI Element: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 10 -> default.Name
-- Column: MobileUI_HUManager.Name
-- 2024-07-05T13:36:30.127Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-07-05 16:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624969
;

-- UI Element: Mobile UI HU Manager -> Mobile UI HU Manager.Aktiv
-- Column: MobileUI_HUManager.IsActive
-- UI Element: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 20 -> flags.Aktiv
-- Column: MobileUI_HUManager.IsActive
-- 2024-07-05T13:36:30.138Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-07-05 16:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624970
;

-- UI Element: Mobile UI HU Manager -> Mobile UI HU Manager.Sektion
-- Column: MobileUI_HUManager.AD_Org_ID
-- UI Element: Mobile UI HU Manager(541806,D) -> Mobile UI HU Manager(547557,D) -> main -> 20 -> org.Sektion
-- Column: MobileUI_HUManager.AD_Org_ID
-- 2024-07-05T13:36:30.149Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-07-05 16:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624971
;

-- Tab: Mobile UI HU Manager -> Merkmale
-- Table: MobileUI_HUManager_Attribute
-- Tab: Mobile UI HU Manager(541806,D) -> Merkmale
-- Table: MobileUI_HUManager_Attribute
-- 2024-07-05T13:37:21.674Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,588709,583175,0,547558,542423,541806,'Y',TO_TIMESTAMP('2024-07-05 16:37:21','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','MobileUI_HUManager_Attribute','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Merkmale','N',20,1,TO_TIMESTAMP('2024-07-05 16:37:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:21.678Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547558 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-07-05T13:37:21.681Z
/* DDL */  select update_tab_translation_from_ad_element(583175) 
;

-- 2024-07-05T13:37:21.688Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547558)
;

-- Field: Mobile UI HU Manager -> Merkmale -> Mandant
-- Column: MobileUI_HUManager_Attribute.AD_Client_ID
-- Field: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> Mandant
-- Column: MobileUI_HUManager_Attribute.AD_Client_ID
-- 2024-07-05T13:37:25.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588701,729056,0,547558,TO_TIMESTAMP('2024-07-05 16:37:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-07-05 16:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:25.712Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:37:25.714Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-07-05T13:37:25.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729056
;

-- 2024-07-05T13:37:25.849Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729056)
;

-- Field: Mobile UI HU Manager -> Merkmale -> Sektion
-- Column: MobileUI_HUManager_Attribute.AD_Org_ID
-- Field: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> Sektion
-- Column: MobileUI_HUManager_Attribute.AD_Org_ID
-- 2024-07-05T13:37:25.942Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588702,729057,0,547558,TO_TIMESTAMP('2024-07-05 16:37:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-07-05 16:37:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:25.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:37:25.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-07-05T13:37:26.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729057
;

-- 2024-07-05T13:37:26.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729057)
;

-- Field: Mobile UI HU Manager -> Merkmale -> Aktiv
-- Column: MobileUI_HUManager_Attribute.IsActive
-- Field: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> Aktiv
-- Column: MobileUI_HUManager_Attribute.IsActive
-- 2024-07-05T13:37:26.162Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588705,729058,0,547558,TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:26.163Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:37:26.165Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-07-05T13:37:26.289Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729058
;

-- 2024-07-05T13:37:26.289Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729058)
;

-- Field: Mobile UI HU Manager -> Merkmale -> Merkmale
-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_Attribute_ID
-- Field: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> Merkmale
-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_Attribute_ID
-- 2024-07-05T13:37:26.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588708,729059,0,547558,TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:26.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:37:26.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583175) 
;

-- 2024-07-05T13:37:26.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729059
;

-- 2024-07-05T13:37:26.372Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729059)
;

-- Field: Mobile UI HU Manager -> Merkmale -> Mobile UI HU Manager
-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_ID
-- Field: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> Mobile UI HU Manager
-- Column: MobileUI_HUManager_Attribute.MobileUI_HUManager_ID
-- 2024-07-05T13:37:26.450Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588709,729060,0,547558,TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Mobile UI HU Manager',TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:26.451Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:37:26.452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583174) 
;

-- 2024-07-05T13:37:26.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729060
;

-- 2024-07-05T13:37:26.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729060)
;

-- Field: Mobile UI HU Manager -> Merkmale -> Merkmal
-- Column: MobileUI_HUManager_Attribute.M_Attribute_ID
-- Field: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> Merkmal
-- Column: MobileUI_HUManager_Attribute.M_Attribute_ID
-- 2024-07-05T13:37:26.552Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588711,729061,0,547558,TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Merkmal',22,'D','Product Attribute like Color, Size','Y','N','N','N','N','N','N','N','Merkmal',TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:26.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:37:26.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2015) 
;

-- 2024-07-05T13:37:26.571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729061
;

-- 2024-07-05T13:37:26.571Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729061)
;

-- Field: Mobile UI HU Manager -> Merkmale -> Reihenfolge
-- Column: MobileUI_HUManager_Attribute.SeqNo
-- Field: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> Reihenfolge
-- Column: MobileUI_HUManager_Attribute.SeqNo
-- 2024-07-05T13:37:26.660Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588712,729062,0,547558,TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2024-07-05 16:37:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:37:26.661Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:37:26.663Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2024-07-05T13:37:26.696Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729062
;

-- 2024-07-05T13:37:26.696Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729062)
;

-- Tab: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D)
-- UI Section: main
-- 2024-07-05T13:37:40.491Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547558,546142,TO_TIMESTAMP('2024-07-05 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-07-05 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-07-05T13:37:40.492Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546142 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> main
-- UI Column: 10
-- 2024-07-05T13:37:46.048Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547505,546142,TO_TIMESTAMP('2024-07-05 16:37:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-07-05 16:37:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> main -> 10
-- UI Element Group: default
-- 2024-07-05T13:37:53.619Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547505,551859,TO_TIMESTAMP('2024-07-05 16:37:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-07-05 16:37:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Merkmale.Reihenfolge
-- Column: MobileUI_HUManager_Attribute.SeqNo
-- UI Element: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> main -> 10 -> default.Reihenfolge
-- Column: MobileUI_HUManager_Attribute.SeqNo
-- 2024-07-05T13:38:17.781Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729062,0,547558,624973,551859,'F',TO_TIMESTAMP('2024-07-05 16:38:17','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2024-07-05 16:38:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Merkmale.Merkmal
-- Column: MobileUI_HUManager_Attribute.M_Attribute_ID
-- UI Element: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> main -> 10 -> default.Merkmal
-- Column: MobileUI_HUManager_Attribute.M_Attribute_ID
-- 2024-07-05T13:38:36.833Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729061,0,547558,624974,551859,'F',TO_TIMESTAMP('2024-07-05 16:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Produkt-Merkmal','Product Attribute like Color, Size','Y','N','N','Y','N','N','N',0,'Merkmal',20,0,0,TO_TIMESTAMP('2024-07-05 16:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Merkmale.Aktiv
-- Column: MobileUI_HUManager_Attribute.IsActive
-- UI Element: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> main -> 10 -> default.Aktiv
-- Column: MobileUI_HUManager_Attribute.IsActive
-- 2024-07-05T13:38:44.729Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729058,0,547558,624975,551859,'F',TO_TIMESTAMP('2024-07-05 16:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',30,0,0,TO_TIMESTAMP('2024-07-05 16:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI HU Manager -> Merkmale.Sektion
-- Column: MobileUI_HUManager_Attribute.AD_Org_ID
-- UI Element: Mobile UI HU Manager(541806,D) -> Merkmale(547558,D) -> main -> 10 -> default.Sektion
-- Column: MobileUI_HUManager_Attribute.AD_Org_ID
-- 2024-07-05T13:38:54.944Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729057,0,547558,624976,551859,'F',TO_TIMESTAMP('2024-07-05 16:38:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',40,0,0,TO_TIMESTAMP('2024-07-05 16:38:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:49:16.980Z
UPDATE AD_Table_Trl SET Name='Attributes',Updated=TO_TIMESTAMP('2024-07-05 16:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542423
;

-- 2024-07-05T13:53:16.129Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540796,0,542422,TO_TIMESTAMP('2024-07-05 16:53:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Nur ein aktives Mobile UI HU Manager ist erlaubt','Y','Y','IDX_unique_HUManager','N',TO_TIMESTAMP('2024-07-05 16:53:15','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2024-07-05T13:53:16.135Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540796 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-07-05T13:53:24.259Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one active Mobile UI Picking Profile is allowed',Updated=TO_TIMESTAMP('2024-07-05 16:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540796 AND AD_Language='en_US'
;

-- 2024-07-05T13:53:41.867Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588693,541410,540796,0,TO_TIMESTAMP('2024-07-05 16:53:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2024-07-05 16:53:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-05T13:53:46.256Z
CREATE UNIQUE INDEX IDX_unique_HUManager ON MobileUI_HUManager (AD_Org_ID) WHERE IsActive='Y'
;

-- 2024-07-08T16:32:22.521Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540797,0,542423,TO_TIMESTAMP('2024-07-08 19:32:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','IDX_unique_HUManager_Attribute','N',TO_TIMESTAMP('2024-07-08 19:32:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-08T16:32:22.548Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540797 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-07-08T16:32:36.542Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588709,541411,540797,0,TO_TIMESTAMP('2024-07-08 19:32:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2024-07-08 19:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-08T16:32:45.762Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588711,541412,540797,0,TO_TIMESTAMP('2024-07-08 19:32:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2024-07-08 19:32:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-08T16:32:49.960Z
CREATE UNIQUE INDEX IDX_unique_HUManager_Attribute ON MobileUI_HUManager_Attribute (MobileUI_HUManager_ID,M_Attribute_ID)
;

-- Name: Mobile UI HU Manager
-- Action Type: W
-- Window: Mobile UI HU Manager(541806,D)
-- 2024-07-08T16:45:30.936Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583174,542163,0,541806,TO_TIMESTAMP('2024-07-08 19:45:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Mobile UI HU Manager','Y','N','N','Y','N','Mobile UI HU Manager',TO_TIMESTAMP('2024-07-08 19:45:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-08T16:45:30.939Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542163 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-07-08T16:45:30.945Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542163, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542163)
;

-- 2024-07-08T16:45:31.023Z
/* DDL */  select update_menu_translation_from_ad_element(583174) 
;

-- Reordering children of `Drucker`
-- Node name: `Drucker-Zuordnung (AD_Printer_Config)`
-- 2024-07-08T16:45:31.749Z
UPDATE AD_TreeNodeMM SET Parent_ID=540428, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540431 AND AD_Tree_ID=10
;

-- Node name: `Drucker-Kalibierung (AD_PrinterHW_Calibration)`
-- 2024-07-08T16:45:31.751Z
UPDATE AD_TreeNodeMM SET Parent_ID=540428, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540451 AND AD_Tree_ID=10
;

-- Node name: `Logical Printer (AD_Printer)`
-- 2024-07-08T16:45:31.752Z
UPDATE AD_TreeNodeMM SET Parent_ID=540428, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540430 AND AD_Tree_ID=10
;

-- Node name: `Hardware-Drucker (AD_PrinterHW)`
-- 2024-07-08T16:45:31.754Z
UPDATE AD_TreeNodeMM SET Parent_ID=540428, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540429 AND AD_Tree_ID=10
;

-- Node name: `Druck-Clients (AD_Print_Clients)`
-- 2024-07-08T16:45:31.756Z
UPDATE AD_TreeNodeMM SET Parent_ID=540428, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540453 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI HU Manager`
-- 2024-07-08T16:45:31.757Z
UPDATE AD_TreeNodeMM SET Parent_ID=540428, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Reordering children of `Handling Units`
-- Node name: `Mobile UI HU Manager`
-- 2024-07-08T16:46:49.248Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Node name: `Packvorschrift (M_HU_PI)`
-- 2024-07-08T16:46:49.252Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540479 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2024-07-08T16:46:49.253Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540481 AND AD_Tree_ID=10
;

-- Node name: `HU-Rückverfolgung (M_HU_Trace)`
-- 2024-07-08T16:46:49.253Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540893 AND AD_Tree_ID=10
;

-- Node name: `HU-Transaktion (M_HU_Trx_Line)`
-- 2024-07-08T16:46:49.254Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540482 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Zuordnung (M_HU_PI_Item_Product)`
-- 2024-07-08T16:46:49.255Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540489 AND AD_Tree_ID=10
;

-- Node name: `Packmittel (M_HU_PackingMaterial)`
-- 2024-07-08T16:46:49.255Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540490 AND AD_Tree_ID=10
;

-- Node name: `POS HU Editor Filter (C_POS_HUEditor_Filter)`
-- 2024-07-08T16:46:49.256Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540600 AND AD_Tree_ID=10
;

-- Node name: `Waschproben bereitstellung (de.metas.handlingunits.materialtracking.process.DD_Order_GenerateForQualityInspectionFlaggedHUs)`
-- 2024-07-08T16:46:49.257Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540659 AND AD_Tree_ID=10
;

-- Node name: `Maintenance`
-- 2024-07-08T16:46:49.257Z
UPDATE AD_TreeNodeMM SET Parent_ID=540478, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540597 AND AD_Tree_ID=10
;
-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2024-07-08T16:51:45.321Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2024-07-08T16:51:45.322Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2024-07-08T16:51:45.323Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2024-07-08T16:51:45.323Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2024-07-08T16:51:45.324Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2024-07-08T16:51:45.325Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2024-07-08T16:51:45.325Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2024-07-08T16:51:45.326Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2024-07-08T16:51:45.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2024-07-08T16:51:45.327Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2024-07-08T16:51:45.328Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2024-07-08T16:51:45.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2024-07-08T16:51:45.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2024-07-08T16:51:45.330Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2024-07-08T16:51:45.330Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2024-07-08T16:51:45.331Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2024-07-08T16:51:45.332Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2024-07-08T16:51:45.332Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2024-07-08T16:51:45.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2024-07-08T16:51:45.333Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2024-07-08T16:51:45.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2024-07-08T16:51:45.334Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2024-07-08T16:51:45.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI HU Manager`
-- 2024-07-08T16:51:45.335Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-07-08T16:51:45.336Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-07-08T16:51:45.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-07-08T16:51:45.337Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2024-07-08T16:51:45.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2024-07-08T16:51:45.338Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2024-07-08T16:51:45.339Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Generate new HU QR Codes (de.metas.handlingunits.process.GenerateHUQRCodes)`
-- 2024-07-08T16:51:45.339Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542152 AND AD_Tree_ID=10
;

