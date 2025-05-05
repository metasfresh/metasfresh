-- Column: M_Product.M_SectionCode_ID
-- 2022-08-19T08:46:56.410Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584124,581238,0,19,208,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-19 09:46:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-19 09:46:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-19T08:46:56.516Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-19T08:46:56.764Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-08-19T08:49:18.181Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-19T08:49:19.511Z
ALTER TABLE M_Product ADD CONSTRAINT MSectionCode_MProduct FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Product -> Product -> Section Code
-- Column: M_Product.M_SectionCode_ID
-- 2022-08-19T08:52:08.803Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584124,705320,0,180,TO_TIMESTAMP('2022-08-19 09:52:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-19 09:52:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-19T08:52:08.909Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-19T08:52:09.016Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-19T08:52:09.125Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705320
;

-- 2022-08-19T08:52:09.221Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705320)
;

-- UI Element: Product -> Product.Section Code
-- Column: M_Product.M_SectionCode_ID
-- 2022-08-19T09:00:31.364Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,705320,0,180,1000015,612115,'F',TO_TIMESTAMP('2022-08-19 10:00:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',70,0,0,TO_TIMESTAMP('2022-08-19 10:00:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Product -> Product.Section Code
-- Column: M_Product.M_SectionCode_ID
-- 2022-08-19T09:02:58.528Z
UPDATE AD_UI_Element SET SeqNo=64,Updated=TO_TIMESTAMP('2022-08-19 10:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612115
;

-- Column: M_Product.M_SectionCode_ID
-- 2022-08-19T09:04:55.045Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-08-19 10:04:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584124
;

