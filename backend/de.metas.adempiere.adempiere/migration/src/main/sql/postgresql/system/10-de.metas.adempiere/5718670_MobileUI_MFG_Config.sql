-- Table: MobileUI_MFG_Config
-- Table: MobileUI_MFG_Config
-- 2024-03-06T15:58:31.825Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('2',0,0,0,542397,'A','N',TO_TIMESTAMP('2024-03-06 17:58:31','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'MobileUI Manufacturing Configuration','NP','L','MobileUI_MFG_Config','DTI',TO_TIMESTAMP('2024-03-06 17:58:31','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-03-06T15:58:31.830Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542397 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-03-06T15:58:31.946Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556333,TO_TIMESTAMP('2024-03-06 17:58:31','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table MobileUI_MFG_Config',1,'Y','N','Y','Y','MobileUI_MFG_Config','N',1000000,TO_TIMESTAMP('2024-03-06 17:58:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T15:58:31.956Z
CREATE SEQUENCE MOBILEUI_MFG_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: MobileUI_MFG_Config
-- Table: MobileUI_MFG_Config
-- 2024-03-06T15:58:38.255Z
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2024-03-06 17:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542397
;

-- Column: MobileUI_MFG_Config.AD_Client_ID
-- Column: MobileUI_MFG_Config.AD_Client_ID
-- 2024-03-06T15:58:45.034Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587969,102,0,19,542397,'AD_Client_ID',TO_TIMESTAMP('2024-03-06 17:58:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-03-06 17:58:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:45.036Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587969 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:45.040Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: MobileUI_MFG_Config.AD_Org_ID
-- Column: MobileUI_MFG_Config.AD_Org_ID
-- 2024-03-06T15:58:45.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587970,113,0,30,542397,'AD_Org_ID',TO_TIMESTAMP('2024-03-06 17:58:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-03-06 17:58:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:45.727Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587970 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:45.730Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: MobileUI_MFG_Config.Created
-- Column: MobileUI_MFG_Config.Created
-- 2024-03-06T15:58:46.373Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587971,245,0,16,542397,'Created',TO_TIMESTAMP('2024-03-06 17:58:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-03-06 17:58:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:46.375Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587971 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:46.377Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: MobileUI_MFG_Config.CreatedBy
-- Column: MobileUI_MFG_Config.CreatedBy
-- 2024-03-06T15:58:47.006Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587972,246,0,18,110,542397,'CreatedBy',TO_TIMESTAMP('2024-03-06 17:58:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-03-06 17:58:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:47.008Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587972 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:47.010Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: MobileUI_MFG_Config.IsActive
-- Column: MobileUI_MFG_Config.IsActive
-- 2024-03-06T15:58:47.677Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587973,348,0,20,542397,'IsActive',TO_TIMESTAMP('2024-03-06 17:58:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-03-06 17:58:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:47.678Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587973 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:47.681Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: MobileUI_MFG_Config.Updated
-- Column: MobileUI_MFG_Config.Updated
-- 2024-03-06T15:58:48.322Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587974,607,0,16,542397,'Updated',TO_TIMESTAMP('2024-03-06 17:58:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-03-06 17:58:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:48.324Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587974 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:48.326Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: MobileUI_MFG_Config.UpdatedBy
-- Column: MobileUI_MFG_Config.UpdatedBy
-- 2024-03-06T15:58:48.984Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587975,608,0,18,110,542397,'UpdatedBy',TO_TIMESTAMP('2024-03-06 17:58:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-03-06 17:58:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:48.986Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587975 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:48.988Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-03-06T15:58:49.498Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583019,0,'MobileUI_MFG_Config_ID',TO_TIMESTAMP('2024-03-06 17:58:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MobileUI Manufacturing Configuration','MobileUI Manufacturing Configuration',TO_TIMESTAMP('2024-03-06 17:58:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T15:58:49.502Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583019 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_MFG_Config.MobileUI_MFG_Config_ID
-- Column: MobileUI_MFG_Config.MobileUI_MFG_Config_ID
-- 2024-03-06T15:58:50.131Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587976,583019,0,13,542397,'MobileUI_MFG_Config_ID',TO_TIMESTAMP('2024-03-06 17:58:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','MobileUI Manufacturing Configuration',0,0,TO_TIMESTAMP('2024-03-06 17:58:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:58:50.133Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587976 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:58:50.135Z
/* DDL */  select update_Column_Translation_From_AD_Element(583019) 
;

-- Column: MobileUI_UserProfile_MFG.IsScanResourceRequired
-- Column: MobileUI_UserProfile_MFG.IsScanResourceRequired
-- 2024-03-06T15:59:27.778Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=319, IsMandatory='N',Updated=TO_TIMESTAMP('2024-03-06 17:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585109
;

-- 2024-03-06T15:59:28.404Z
INSERT INTO t_alter_column values('mobileui_userprofile_mfg','IsScanResourceRequired','CHAR(1)',null,'N')
;

-- 2024-03-06T15:59:28.421Z
INSERT INTO t_alter_column values('mobileui_userprofile_mfg','IsScanResourceRequired',null,'NULL',null)
;

-- Column: MobileUI_MFG_Config.IsScanResourceRequired
-- Column: MobileUI_MFG_Config.IsScanResourceRequired
-- 2024-03-06T15:59:46.380Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587977,581714,0,20,542397,'XX','IsScanResourceRequired',TO_TIMESTAMP('2024-03-06 17:59:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Scan der Ressource erforderlich',0,0,TO_TIMESTAMP('2024-03-06 17:59:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-06T15:59:46.382Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587977 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-06T15:59:46.385Z
/* DDL */  select update_Column_Translation_From_AD_Element(581714) 
;

-- 2024-03-06T15:59:47.329Z
/* DDL */ CREATE TABLE public.MobileUI_MFG_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsScanResourceRequired CHAR(1) DEFAULT 'N' CHECK (IsScanResourceRequired IN ('Y','N')) NOT NULL, MobileUI_MFG_Config_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUI_MFG_Config_Key PRIMARY KEY (MobileUI_MFG_Config_ID))
;

-- Window: MobileUI Manufacturing Configuration, InternalName=null
-- Window: MobileUI Manufacturing Configuration, InternalName=null
-- 2024-03-06T16:00:17.270Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583019,0,541788,TO_TIMESTAMP('2024-03-06 18:00:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','MobileUI Manufacturing Configuration','N',TO_TIMESTAMP('2024-03-06 18:00:17','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-03-06T16:00:17.272Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541788 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-03-06T16:00:17.275Z
/* DDL */  select update_window_translation_from_ad_element(583019) 
;

-- 2024-03-06T16:00:17.278Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541788
;

-- 2024-03-06T16:00:17.279Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541788)
;

-- Tab: MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration
-- Table: MobileUI_MFG_Config
-- Tab: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration
-- Table: MobileUI_MFG_Config
-- 2024-03-06T16:00:42.175Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583019,0,547483,542397,541788,'Y',TO_TIMESTAMP('2024-03-06 18:00:42','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','MobileUI_MFG_Config','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'MobileUI Manufacturing Configuration','N',10,0,TO_TIMESTAMP('2024-03-06 18:00:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T16:00:42.178Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547483 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-03-06T16:00:42.180Z
/* DDL */  select update_tab_translation_from_ad_element(583019) 
;

-- 2024-03-06T16:00:42.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547483)
;

-- Field: MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration -> Mandant
-- Column: MobileUI_MFG_Config.AD_Client_ID
-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Mandant
-- Column: MobileUI_MFG_Config.AD_Client_ID
-- 2024-03-06T16:01:03.230Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587969,726564,0,547483,TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T16:01:03.232Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-06T16:01:03.235Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-03-06T16:01:03.392Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726564
;

-- 2024-03-06T16:01:03.394Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726564)
;

-- Field: MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration -> Sektion
-- Column: MobileUI_MFG_Config.AD_Org_ID
-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Sektion
-- Column: MobileUI_MFG_Config.AD_Org_ID
-- 2024-03-06T16:01:03.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587970,726565,0,547483,TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T16:01:03.497Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-06T16:01:03.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-03-06T16:01:03.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726565
;

-- 2024-03-06T16:01:03.572Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726565)
;

-- Field: MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration -> Aktiv
-- Column: MobileUI_MFG_Config.IsActive
-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Aktiv
-- Column: MobileUI_MFG_Config.IsActive
-- 2024-03-06T16:01:03.682Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587973,726566,0,547483,TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T16:01:03.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726566 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-06T16:01:03.685Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-03-06T16:01:03.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726566
;

-- 2024-03-06T16:01:03.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726566)
;

-- Field: MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration
-- Column: MobileUI_MFG_Config.MobileUI_MFG_Config_ID
-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> MobileUI Manufacturing Configuration
-- Column: MobileUI_MFG_Config.MobileUI_MFG_Config_ID
-- 2024-03-06T16:01:03.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587976,726567,0,547483,TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','MobileUI Manufacturing Configuration',TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T16:01:03.858Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726567 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-06T16:01:03.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583019) 
;

-- 2024-03-06T16:01:03.865Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726567
;

-- 2024-03-06T16:01:03.865Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726567)
;

-- Field: MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration -> Scan der Ressource erforderlich
-- Column: MobileUI_MFG_Config.IsScanResourceRequired
-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Scan der Ressource erforderlich
-- Column: MobileUI_MFG_Config.IsScanResourceRequired
-- 2024-03-06T16:01:03.971Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587977,726568,0,547483,TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','N','N','N','N','N','N','N','Scan der Ressource erforderlich',TO_TIMESTAMP('2024-03-06 18:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T16:01:03.973Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726568 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-06T16:01:03.974Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581714) 
;

-- 2024-03-06T16:01:03.977Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726568
;

-- 2024-03-06T16:01:03.978Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726568)
;

-- Tab: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D)
-- UI Section: main
-- 2024-03-06T16:01:17.202Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547483,546062,TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-03-06T16:01:17.203Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546062 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main
-- UI Column: 10
-- 2024-03-06T16:01:17.338Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547403,546062,TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main
-- UI Column: 20
-- 2024-03-06T16:01:17.429Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547404,546062,TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 10
-- UI Element Group: default
-- 2024-03-06T16:01:17.563Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547403,551689,TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-03-06 18:01:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 20
-- UI Element Group: flags
-- 2024-03-06T16:01:54.172Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547404,551690,TO_TIMESTAMP('2024-03-06 18:01:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-03-06 18:01:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: MobileUI Manufacturing Configuration -> MobileUI Manufacturing Configuration.Scan der Ressource erforderlich
-- Column: MobileUI_MFG_Config.IsScanResourceRequired
-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 20 -> flags.Scan der Ressource erforderlich
-- Column: MobileUI_MFG_Config.IsScanResourceRequired
-- 2024-03-06T16:02:04.867Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726568,0,547483,551690,623746,'F',TO_TIMESTAMP('2024-03-06 18:02:04','YYYY-MM-DD HH24:MI:SS'),100,'','Y','N','Y','N','N','Scan der Ressource erforderlich',10,0,0,TO_TIMESTAMP('2024-03-06 18:02:04','YYYY-MM-DD HH24:MI:SS'),100)
;


create unique index MobileUI_MFG_Config_UQ on MobileUI_MFG_Config (AD_Client_ID);

-- Window: MobileUI Manufacturing Configuration, InternalName=mobileMfgConfig
-- Window: MobileUI Manufacturing Configuration, InternalName=mobileMfgConfig
-- 2024-03-06T16:04:42.016Z
UPDATE AD_Window SET InternalName='mobileMfgConfig',Updated=TO_TIMESTAMP('2024-03-06 18:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541788
;

-- Name: MobileUI Manufacturing Configuration
-- Action Type: W
-- Window: MobileUI Manufacturing Configuration(541788,D)
-- 2024-03-06T16:04:54.521Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583019,542141,0,541788,TO_TIMESTAMP('2024-03-06 18:04:54','YYYY-MM-DD HH24:MI:SS'),100,'D','mobileMfgConfig','Y','N','N','N','N','MobileUI Manufacturing Configuration',TO_TIMESTAMP('2024-03-06 18:04:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-06T16:04:54.523Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542141 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-03-06T16:04:54.525Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542141, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542141)
;

-- 2024-03-06T16:04:54.535Z
/* DDL */  select update_menu_translation_from_ad_element(583019) 
;

-- Reordering children of `Manufacturing`
-- Node name: `Maturing candidate (PP_Order_Candidate)`
-- 2024-03-06T16:05:02.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542135 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing candidate (PP_Order_Candidate)`
-- 2024-03-06T16:05:02.804Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541831 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Order (PP_Order)`
-- 2024-03-06T16:05:02.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2024-03-06T16:05:02.806Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-03-06T16:05:02.817Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-03-06T16:05:02.818Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-03-06T16:05:02.819Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

-- Node name: `MobileUI Manufacturing Configuration`
-- 2024-03-06T16:05:02.821Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542141 AND AD_Tree_ID=10
;

-- Reordering children of `Manufacturing`
-- Node name: `Maturing candidate (PP_Order_Candidate)`
-- 2024-03-06T16:05:08.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542135 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing candidate (PP_Order_Candidate)`
-- 2024-03-06T16:05:08.474Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541831 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Order (PP_Order)`
-- 2024-03-06T16:05:08.475Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000081 AND AD_Tree_ID=10
;

-- Node name: `Cost Collector (PP_Cost_Collector)`
-- 2024-03-06T16:05:08.476Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541397 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-03-06T16:05:08.477Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000055 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-03-06T16:05:08.478Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000063 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-03-06T16:05:08.480Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000071 AND AD_Tree_ID=10
;

-- Node name: `MobileUI Manufacturing Configuration`
-- 2024-03-06T16:05:08.480Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000014, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542141 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `Product Planning (PP_Product_Planning)`
-- 2024-03-06T16:05:12.837Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- Node name: `Product Planning Schema (M_Product_PlanningSchema)`
-- 2024-03-06T16:05:12.838Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- Node name: `Product Planning (M_Product)`
-- 2024-03-06T16:05:12.847Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- Node name: `Create Default Product Planning Data (de.metas.product.process.M_ProductPlanning_Create_Default_ProductPlanningData)`
-- 2024-03-06T16:05:12.849Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2024-03-06T16:05:12.850Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Standard Activity`
-- 2024-03-06T16:05:12.851Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Transitions (AD_WF_NodeNext)`
-- 2024-03-06T16:05:12.851Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- Node name: `Resource (S_Resource)`
-- 2024-03-06T16:05:12.852Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- Node name: `Resource Type (S_ResourceType)`
-- 2024-03-06T16:05:12.853Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- Node name: `Component Generator (PP_ComponentGenerator)`
-- 2024-03-06T16:05:12.854Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Activity (AD_WF_Node)`
-- 2024-03-06T16:05:12.855Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

-- Node name: `MobileUI Manufacturing Configuration`
-- 2024-03-06T16:05:12.855Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542141 AND AD_Tree_ID=10
;

-- Node name: `Maturing Configuration (M_Maturing_Configuration)`
-- 2024-03-06T16:05:12.857Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542133 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `Product Planning (PP_Product_Planning)`
-- 2024-03-06T16:05:16.381Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- Node name: `Product Planning Schema (M_Product_PlanningSchema)`
-- 2024-03-06T16:05:16.383Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- Node name: `Product Planning (M_Product)`
-- 2024-03-06T16:05:16.384Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- Node name: `Create Default Product Planning Data (de.metas.product.process.M_ProductPlanning_Create_Default_ProductPlanningData)`
-- 2024-03-06T16:05:16.385Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2024-03-06T16:05:16.386Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Standard Activity`
-- 2024-03-06T16:05:16.387Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Transitions (AD_WF_NodeNext)`
-- 2024-03-06T16:05:16.388Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- Node name: `Resource (S_Resource)`
-- 2024-03-06T16:05:16.389Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- Node name: `Resource Type (S_ResourceType)`
-- 2024-03-06T16:05:16.390Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- Node name: `Component Generator (PP_ComponentGenerator)`
-- 2024-03-06T16:05:16.390Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Activity (AD_WF_Node)`
-- 2024-03-06T16:05:16.391Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

-- Node name: `Maturing Configuration (M_Maturing_Configuration)`
-- 2024-03-06T16:05:16.393Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542133 AND AD_Tree_ID=10
;

-- Node name: `MobileUI Manufacturing Configuration`
-- 2024-03-06T16:05:16.394Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542141 AND AD_Tree_ID=10
;

