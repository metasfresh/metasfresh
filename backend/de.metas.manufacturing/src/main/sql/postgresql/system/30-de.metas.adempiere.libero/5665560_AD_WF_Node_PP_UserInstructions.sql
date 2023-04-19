-- 2022-11-22T08:38:29.655Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581715,0,'PP_UserInstructions',TO_TIMESTAMP('2022-11-22 10:38:29','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','User Instructions','User Instructions',TO_TIMESTAMP('2022-11-22 10:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T08:38:29.662Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581715 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_WF_Node.PP_UserInstructions
-- 2022-11-22T08:38:50.552Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585110,581715,0,10,129,'PP_UserInstructions',TO_TIMESTAMP('2022-11-22 10:38:50','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'User Instructions',0,0,TO_TIMESTAMP('2022-11-22 10:38:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-22T08:38:50.553Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585110 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-22T08:38:50.557Z
/* DDL */  select update_Column_Translation_From_AD_Element(581715) 
;

-- 2022-11-22T08:38:54.129Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Node','ALTER TABLE public.AD_WF_Node ADD COLUMN PP_UserInstructions VARCHAR(2000)')
;

-- Field: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> User Instructions
-- Column: AD_WF_Node.PP_UserInstructions
-- 2022-11-22T08:39:40.527Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585110,708142,0,53025,TO_TIMESTAMP('2022-11-22 10:39:40','YYYY-MM-DD HH24:MI:SS'),100,2000,'EE01','Y','N','N','N','N','N','N','N','User Instructions',TO_TIMESTAMP('2022-11-22 10:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T08:39:40.529Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-22T08:39:40.532Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581715) 
;

-- 2022-11-22T08:39:40.534Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708142
;

-- 2022-11-22T08:39:40.536Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708142)
;

-- UI Element: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> main -> 10 -> default.User Instructions
-- Column: AD_WF_Node.PP_UserInstructions
-- 2022-11-22T08:40:13.488Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708142,0,53025,540370,613541,'F',TO_TIMESTAMP('2022-11-22 10:40:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','User Instructions',160,0,0,TO_TIMESTAMP('2022-11-22 10:40:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> main -> 10 -> default.Beschreibung
-- Column: AD_WF_Node.Description
-- 2022-11-22T08:41:42.609Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-11-22 10:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543832
;

-- UI Element: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> main -> 10 -> default.User Instructions
-- Column: AD_WF_Node.PP_UserInstructions
-- 2022-11-22T08:41:42.618Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-22 10:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613541
;

