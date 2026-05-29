-- Column: PP_Order_Weighting_RunCheck.IsToleranceExceeded
-- 2022-11-11T00:13:40.890Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584934,581659,0,20,542255,'IsToleranceExceeded',TO_TIMESTAMP('2022-11-11 02:13:40.669','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Tolerance Excheeded',0,0,TO_TIMESTAMP('2022-11-11 02:13:40.669','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-11-11T00:13:40.892Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584934 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-11T00:13:40.924Z
/* DDL */  select update_Column_Translation_From_AD_Element(581659) 
;

-- 2022-11-11T00:13:42.809Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Weighting_RunCheck','ALTER TABLE public.PP_Order_Weighting_RunCheck ADD COLUMN IsToleranceExceeded CHAR(1) DEFAULT ''N'' CHECK (IsToleranceExceeded IN (''Y'',''N'')) NOT NULL')
;

-- Field: Weighting Run -> Wighting Check -> Tolerance Excheeded
-- Column: PP_Order_Weighting_RunCheck.IsToleranceExceeded
-- 2022-11-11T00:14:06.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584934,708027,0,546670,TO_TIMESTAMP('2022-11-11 02:14:06.739','YYYY-MM-DD HH24:MI:SS.US'),100,1,'EE01','Y','N','N','N','N','N','N','N','Tolerance Excheeded',TO_TIMESTAMP('2022-11-11 02:14:06.739','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-11T00:14:06.876Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-11T00:14:06.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581659) 
;

-- 2022-11-11T00:14:06.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708027
;

-- 2022-11-11T00:14:06.895Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708027)
;

-- UI Element: Weighting Run -> Wighting Check.Tolerance Excheeded
-- Column: PP_Order_Weighting_RunCheck.IsToleranceExceeded
-- 2022-11-11T00:14:36.604Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708027,0,546670,550009,613440,'F',TO_TIMESTAMP('2022-11-11 02:14:36.457','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Tolerance Excheeded',50,0,0,TO_TIMESTAMP('2022-11-11 02:14:36.457','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Weighting Run -> Wighting Check.Beschreibung
-- Column: PP_Order_Weighting_RunCheck.Description
-- 2022-11-11T00:14:50.171Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2022-11-11 02:14:50.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613439
;

-- UI Element: Weighting Run -> Wighting Check.Tolerance Excheeded
-- Column: PP_Order_Weighting_RunCheck.IsToleranceExceeded
-- 2022-11-11T00:14:53.238Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2022-11-11 02:14:53.238','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613440
;

-- UI Element: Weighting Run -> Wighting Check.Tolerance Excheeded
-- Column: PP_Order_Weighting_RunCheck.IsToleranceExceeded
-- 2022-11-11T00:15:03.556Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-11 02:15:03.556','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613440
;

-- UI Element: Weighting Run -> Wighting Check.Beschreibung
-- Column: PP_Order_Weighting_RunCheck.Description
-- 2022-11-11T00:15:03.562Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-11 02:15:03.562','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613439
;

