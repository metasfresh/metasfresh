-- 2023-01-04T14:25:56.177Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581905,0,'IsReserveHUsOnJobStart',TO_TIMESTAMP('2023-01-04 16:25:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Reserve HUs On Picking Job Start','Reserve HUs On Picking Job Start',TO_TIMESTAMP('2023-01-04 16:25:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-04T14:25:56.183Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581905 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-04T14:26:06.195Z
UPDATE AD_Element SET ColumnName='IsReserveHUsOnPickingJobStart',Updated=TO_TIMESTAMP('2023-01-04 16:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581905
;

-- 2023-01-04T14:26:06.197Z
UPDATE AD_Column SET ColumnName='IsReserveHUsOnPickingJobStart' WHERE AD_Element_ID=581905
;

-- 2023-01-04T14:26:06.199Z
UPDATE AD_Process_Para SET ColumnName='IsReserveHUsOnPickingJobStart' WHERE AD_Element_ID=581905
;

-- 2023-01-04T14:26:06.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581905,'de_DE') 
;

-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- 2023-01-04T14:26:23.681Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585447,581905,0,20,541418,'IsReserveHUsOnPickingJobStart',TO_TIMESTAMP('2023-01-04 16:26:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reserve HUs On Picking Job Start',0,0,TO_TIMESTAMP('2023-01-04 16:26:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-04T14:26:23.682Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585447 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-04T14:26:23.686Z
/* DDL */  select update_Column_Translation_From_AD_Element(581905) 
;

-- 2023-01-04T14:26:24.561Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Config_V2','ALTER TABLE public.M_Picking_Config_V2 ADD COLUMN IsReserveHUsOnPickingJobStart CHAR(1) DEFAULT ''Y'' CHECK (IsReserveHUsOnPickingJobStart IN (''Y'',''N'')) NOT NULL')
;

-- Field: Kommissionierkonfiguration (V2) -> Kommissionierkonfiguration (V2) -> Reserve HUs On Picking Job Start
-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- Field: Kommissionierkonfiguration (V2)(540739,de.metas.picking) -> Kommissionierkonfiguration (V2)(542062,de.metas.picking) -> Reserve HUs On Picking Job Start
-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- 2023-01-04T14:27:01.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585447,710122,0,542062,TO_TIMESTAMP('2023-01-04 16:27:01','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.picking','Y','N','N','N','N','N','N','N','Reserve HUs On Picking Job Start',TO_TIMESTAMP('2023-01-04 16:27:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-04T14:27:01.625Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-04T14:27:01.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581905) 
;

-- 2023-01-04T14:27:01.639Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710122
;

-- 2023-01-04T14:27:01.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710122)
;

-- UI Element: Kommissionierkonfiguration (V2) -> Kommissionierkonfiguration (V2).Reserve HUs On Picking Job Start
-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- UI Element: Kommissionierkonfiguration (V2)(540739,de.metas.picking) -> Kommissionierkonfiguration (V2)(542062,de.metas.picking) -> main -> 10 -> default.Reserve HUs On Picking Job Start
-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- 2023-01-04T14:29:10.301Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710122,0,542062,543067,614626,'F',TO_TIMESTAMP('2023-01-04 16:29:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Reserve HUs On Picking Job Start',30,0,0,TO_TIMESTAMP('2023-01-04 16:29:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kommissionierkonfiguration (V2) -> Kommissionierkonfiguration (V2).Reserve HUs On Picking Job Start
-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- UI Element: Kommissionierkonfiguration (V2)(540739,de.metas.picking) -> Kommissionierkonfiguration (V2)(542062,de.metas.picking) -> main -> 10 -> default.Reserve HUs On Picking Job Start
-- Column: M_Picking_Config_V2.IsReserveHUsOnPickingJobStart
-- 2023-01-04T14:29:20.512Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-04 16:29:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614626
;

-- UI Element: Kommissionierkonfiguration (V2) -> Kommissionierkonfiguration (V2).Sektion
-- Column: M_Picking_Config_V2.AD_Org_ID
-- UI Element: Kommissionierkonfiguration (V2)(540739,de.metas.picking) -> Kommissionierkonfiguration (V2)(542062,de.metas.picking) -> main -> 20 -> client & org.Sektion
-- Column: M_Picking_Config_V2.AD_Org_ID
-- 2023-01-04T14:29:20.523Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-04 16:29:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563205
;

