-- 2024-07-19T09:21:08.498Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583192,0,'IsAllowNewLU',TO_TIMESTAMP('2024-07-19 12:21:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Neues Gebinde zulassen','Neues Gebinde zulassen',TO_TIMESTAMP('2024-07-19 12:21:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-19T09:21:08.517Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583192 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAllowNewLU
-- 2024-07-19T09:21:21.376Z
UPDATE AD_Element_Trl SET Name='Allow New LU', PrintName='Allow New LU',Updated=TO_TIMESTAMP('2024-07-19 12:21:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583192 AND AD_Language='en_US'
;

-- 2024-07-19T09:21:21.413Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583192,'en_US') 
;

-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- 2024-07-19T09:22:31.252Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588864,583192,0,20,542373,'XX','IsAllowNewLU',TO_TIMESTAMP('2024-07-19 12:22:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Neues Gebinde zulassen',0,0,TO_TIMESTAMP('2024-07-19 12:22:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-19T09:22:31.256Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588864 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-19T09:22:31.263Z
/* DDL */  select update_Column_Translation_From_AD_Element(583192) 
;

-- 2024-07-19T09:22:33.263Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsAllowNewLU CHAR(1) DEFAULT ''Y'' CHECK (IsAllowNewLU IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Neues Gebinde zulassen
-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Neues Gebinde zulassen
-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- 2024-07-19T09:23:22.207Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588864,729105,0,547258,TO_TIMESTAMP('2024-07-19 12:23:21','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Neues Gebinde zulassen',TO_TIMESTAMP('2024-07-19 12:23:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-19T09:23:22.211Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-19T09:23:22.216Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583192) 
;

-- 2024-07-19T09:23:22.236Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729105
;

-- 2024-07-19T09:23:22.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729105)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Neues Gebinde zulassen
-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Neues Gebinde zulassen
-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- 2024-07-19T09:23:54.969Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729105,0,547258,625021,551252,'F',TO_TIMESTAMP('2024-07-19 12:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Neues Gebinde zulassen',40,0,0,TO_TIMESTAMP('2024-07-19 12:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-19T09:26:13.099Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-07-19 12:26:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583192
;

-- 2024-07-19T09:26:13.106Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583192,'de_DE') 
;

-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- Column: MobileUI_UserProfile_Picking.IsAllowNewLU
-- 2024-07-19T09:26:41.866Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-07-19 12:26:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588864
;

-- 2024-07-19T09:26:45.238Z
INSERT INTO t_alter_column values('mobileui_userprofile_picking','IsAllowNewLU','CHAR(1)',null,'Y')
;

-- 2024-07-19T09:26:45.288Z
UPDATE MobileUI_UserProfile_Picking SET IsAllowNewLU='Y' WHERE IsAllowNewLU IS NULL
;

-- Element: IsAllowNewLU
-- 2024-07-19T11:14:12.905Z
UPDATE AD_Element_Trl SET Name='Allow new LU', PrintName='Allow new LU',Updated=TO_TIMESTAMP('2024-07-19 14:14:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583192 AND AD_Language='en_US'
;

-- 2024-07-19T11:14:12.995Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583192,'en_US') 
;
