
-- Column: C_OLCand.M_SectionCode_ID
-- 2022-09-22T07:57:26.843Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584431,581238,0,19,540244,'M_SectionCode_ID',TO_TIMESTAMP('2022-09-22 10:57:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-09-22 10:57:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-22T07:57:26.847Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584431 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-22T07:57:26.871Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-09-22T07:57:27.838Z
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-09-22T07:57:28.006Z
ALTER TABLE C_OLCand ADD CONSTRAINT MSectionCode_COLCand FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Section Code
-- Column: C_OLCand.M_SectionCode_ID
-- 2022-09-22T07:58:34.474Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584431,707325,0,540282,0,TO_TIMESTAMP('2022-09-22 10:58:34','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.ordercandidate',0,'Y','Y','Y','N','N','N','N','N','Section Code',0,460,0,1,1,TO_TIMESTAMP('2022-09-22 10:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-22T07:58:34.481Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-22T07:58:34.491Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-09-22T07:58:34.522Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707325
;

-- 2022-09-22T07:58:34.530Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707325)
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> sub -> 20 -> org.Section Code
-- Column: C_OLCand.M_SectionCode_ID
-- 2022-09-22T08:08:09.046Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707325,0,540282,613023,541158,'F',TO_TIMESTAMP('2022-09-22 11:08:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',30,0,0,TO_TIMESTAMP('2022-09-22 11:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> sub -> 20 -> org.Section Code
-- Column: C_OLCand.M_SectionCode_ID
-- 2022-09-22T08:08:26.412Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2022-09-22 11:08:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613023
;

-- 2022-09-23T09:53:28.661Z
INSERT INTO t_alter_column values('c_olcand','M_SectionCode_ID','NUMERIC(10)',null,null)
;


-- 2022-09-27T10:42:45.710Z
INSERT INTO C_OLCandAggAndOrder (AD_Client_ID,AD_Column_OLCand_ID,AD_Org_ID,C_OLCandAggAndOrder_ID,C_OLCandProcessor_ID,Created,CreatedBy,GroupBy,IsActive,SplitOrder,Updated,UpdatedBy) VALUES (1000000,584431,1000000,540025,1000003,TO_TIMESTAMP('2022-09-27 13:42:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Y',TO_TIMESTAMP('2022-09-27 13:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-27T10:42:50.145Z
UPDATE C_OLCandAggAndOrder SET OrderBySeqNo=250,Updated=TO_TIMESTAMP('2022-09-27 13:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540025
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Section Code
-- Column: C_OLCand.M_SectionCode_ID
-- 2022-09-27T14:45:13.725Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-27 17:45:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707325
;

