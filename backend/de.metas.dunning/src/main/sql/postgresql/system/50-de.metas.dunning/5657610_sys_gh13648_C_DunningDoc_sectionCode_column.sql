-- Column: C_DunningDoc.M_SectionCode_ID
-- 2022-09-27T06:50:16.277Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584459,581238,0,19,540401,'M_SectionCode_ID',TO_TIMESTAMP('2022-09-27 09:50:15','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dunning',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-09-27 09:50:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-27T06:50:16.298Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584459 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-27T06:50:16.404Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-09-27T06:50:19.336Z
/* DDL */ SELECT public.db_alter_table('C_DunningDoc','ALTER TABLE public.C_DunningDoc ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-09-27T06:50:19.375Z
ALTER TABLE C_DunningDoc ADD CONSTRAINT MSectionCode_CDunningDoc FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Mahnungen(540155,de.metas.dunning) -> Mahnungen(540430,de.metas.dunning) -> Section Code
-- Column: C_DunningDoc.M_SectionCode_ID
-- 2022-09-27T07:03:35.556Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584459,707341,0,540430,TO_TIMESTAMP('2022-09-27 10:03:35','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.dunning','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-09-27 10:03:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-27T07:03:35.563Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-27T07:03:35.568Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-09-27T07:03:35.605Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707341
;

-- 2022-09-27T07:03:35.621Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707341)
;


-- UI Element: Mahnungen(540155,de.metas.dunning) -> Mahnungen(540430,de.metas.dunning) -> main -> 20 -> org.Section Code
-- Column: C_DunningDoc.M_SectionCode_ID
-- 2022-09-27T07:05:03.926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707341,0,540430,613040,540096,'F',TO_TIMESTAMP('2022-09-27 10:05:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-09-27 10:05:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> Section Code
-- Column: C_Dunning_Candidate.M_SectionCode_ID
-- 2022-09-27T07:06:20.151Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584433,707342,0,540424,TO_TIMESTAMP('2022-09-27 10:06:20','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.dunning','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-09-27 10:06:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-27T07:06:20.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-27T07:06:20.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-09-27T07:06:20.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707342
;

-- 2022-09-27T07:06:20.171Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707342)
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> org.Section Code
-- Column: C_Dunning_Candidate.M_SectionCode_ID
-- 2022-09-27T07:07:06.699Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707342,0,540424,613041,541195,'F',TO_TIMESTAMP('2022-09-27 10:07:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-09-27 10:07:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> Section Code
-- Column: C_Dunning_Candidate.M_SectionCode_ID
-- 2022-09-27T14:51:39.832Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-27 17:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707342
;

-- Field: Mahnungen(540155,de.metas.dunning) -> Mahnungen(540430,de.metas.dunning) -> Section Code
-- Column: C_DunningDoc.M_SectionCode_ID
-- 2022-09-27T14:53:07.613Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-27 17:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707341
;

