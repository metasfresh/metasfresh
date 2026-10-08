-- 2024-09-25T23:48:41.714Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583289,0,'IsShowConfirmationPromptWhenOverPick',TO_TIMESTAMP('2024-09-26 02:48:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Ask User when Over Picking','Ask User when Over Picking',TO_TIMESTAMP('2024-09-26 02:48:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-25T23:48:41.737Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583289 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsShowConfirmationPromptWhenOverPick
-- 2024-09-25T23:48:51.776Z
UPDATE AD_Element_Trl SET Name='Rückfragen bei Überkommissionierung', PrintName='Rückfragen bei Überkommissionierung',Updated=TO_TIMESTAMP('2024-09-26 02:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583289 AND AD_Language='de_CH'
;

-- 2024-09-25T23:48:51.820Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583289,'de_CH') 
;

-- Element: IsShowConfirmationPromptWhenOverPick
-- 2024-09-25T23:48:54.333Z
UPDATE AD_Element_Trl SET Name='Rückfragen bei Überkommissionierung', PrintName='Rückfragen bei Überkommissionierung',Updated=TO_TIMESTAMP('2024-09-26 02:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583289 AND AD_Language='de_DE'
;

-- 2024-09-25T23:48:54.337Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583289,'de_DE') 
;

-- 2024-09-25T23:48:54.349Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583289,'de_DE') 
;

-- Element: IsShowConfirmationPromptWhenOverPick
-- 2024-09-25T23:49:02.197Z
UPDATE AD_Element_Trl SET Name='Rückfragen bei Überkommissionierung', PrintName='Rückfragen bei Überkommissionierung',Updated=TO_TIMESTAMP('2024-09-26 02:49:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583289 AND AD_Language='fr_CH'
;

-- 2024-09-25T23:49:02.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583289,'fr_CH') 
;

-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- 2024-09-25T23:49:21.090Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589168,583289,0,20,542373,'XX','IsShowConfirmationPromptWhenOverPick',TO_TIMESTAMP('2024-09-26 02:49:20','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rückfragen bei Überkommissionierung',0,0,TO_TIMESTAMP('2024-09-26 02:49:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-25T23:49:21.098Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589168 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-25T23:49:21.110Z
/* DDL */  select update_Column_Translation_From_AD_Element(583289) 
;

-- 2024-09-25T23:49:23.988Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsShowConfirmationPromptWhenOverPick CHAR(1) DEFAULT ''N'' CHECK (IsShowConfirmationPromptWhenOverPick IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Rückfragen bei Überkommissionierung
-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Rückfragen bei Überkommissionierung
-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- 2024-09-25T23:53:13.070Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589168,731784,0,547258,TO_TIMESTAMP('2024-09-26 02:53:12','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Rückfragen bei Überkommissionierung',TO_TIMESTAMP('2024-09-26 02:53:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-25T23:53:13.074Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-25T23:53:13.076Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583289)
;

-- 2024-09-25T23:53:13.094Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731784
;

-- 2024-09-25T23:53:13.106Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731784)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Rückfragen bei Überkommissionierung
-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Rückfragen bei Überkommissionierung
-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- 2024-09-25T23:53:39.891Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,731784,0,547258,626092,551252,'F',TO_TIMESTAMP('2024-09-26 02:53:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rückfragen bei Überkommissionierung',90,0,0,TO_TIMESTAMP('2024-09-26 02:53:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick
-- 2024-09-25T23:55:14.132Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-09-26 02:55:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589168
;

