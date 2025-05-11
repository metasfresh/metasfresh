-- Column: PP_Weighting_Spec.C_UOM_ID
-- 2022-11-12T12:29:12.585Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584937,215,0,30,542256,540608,'C_UOM_ID',TO_TIMESTAMP('2022-11-12 14:29:12.297','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Maßeinheit','EE01',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2022-11-12 14:29:12.297','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-11-12T12:29:12.588Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584937 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-12T12:29:12.619Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- Field: Weighting Specifications -> Weighting Specifications -> Maßeinheit
-- Column: PP_Weighting_Spec.C_UOM_ID
-- 2022-11-12T12:32:08.093Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584937,708030,0,546668,TO_TIMESTAMP('2022-11-12 14:32:07.82','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2022-11-12 14:32:07.82','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-12T12:32:08.094Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-12T12:32:08.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2022-11-12T12:32:08.160Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708030
;

-- 2022-11-12T12:32:08.161Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708030)
;

-- UI Element: Weighting Specifications -> Weighting Specifications.Maßeinheit
-- Column: PP_Weighting_Spec.C_UOM_ID
-- 2022-11-12T12:33:06.355Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708030,0,546668,550005,613444,'F',TO_TIMESTAMP('2022-11-12 14:33:06.092','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',30,0,0,TO_TIMESTAMP('2022-11-12 14:33:06.092','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-11-14T07:08:40.480Z
/* DDL */ SELECT public.db_alter_table('PP_Weighting_Spec','ALTER TABLE public.PP_Weighting_Spec ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2022-11-14T07:08:40.489Z
ALTER TABLE PP_Weighting_Spec ADD CONSTRAINT CUOM_PPWeightingSpec FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: PP_Weighting_Spec.C_UOM_ID
-- 2022-11-14T07:09:03.406Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-11-14 09:09:03.404','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584937
;

-- 2022-11-14T07:09:04.169Z
INSERT INTO t_alter_column values('pp_weighting_spec','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2022-11-14T07:09:04.175Z
INSERT INTO t_alter_column values('pp_weighting_spec','C_UOM_ID',null,'NOT NULL',null)
;

