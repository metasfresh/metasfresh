-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:49:10.947Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586207,192,0,19,540774,'C_Country_ID',TO_TIMESTAMP('2023-02-21 11:49:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Land','D',0,10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Land',0,0,TO_TIMESTAMP('2023-02-21 11:49:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-21T10:49:10.951Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586207 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-21T10:49:10.995Z
/* DDL */  select update_Column_Translation_From_AD_Element(192)
;

-- 2023-02-21T10:49:13.095Z
/* DDL */ SELECT public.db_alter_table('C_DocType_Sequence','ALTER TABLE public.C_DocType_Sequence ADD COLUMN C_Country_ID NUMERIC(10)')
;

-- 2023-02-21T10:49:13.109Z
ALTER TABLE C_DocType_Sequence ADD CONSTRAINT CCountry_CDocTypeSequence FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

DROP INDEX c_doctype_sequence_uq
;

CREATE UNIQUE INDEX c_doctype_sequence_uq ON c_doctype_sequence (c_doctype_id, ad_client_id, ad_org_id, coalesce(c_country_id,0))
;

-- Field: Belegart(135,D) -> Sequence(540744,D) -> Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:52:46.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586207,712689,0,540744,TO_TIMESTAMP('2023-02-21 11:52:46','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2023-02-21 11:52:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T10:52:46.451Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T10:52:46.454Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192)
;

-- 2023-02-21T10:52:46.481Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712689
;

-- 2023-02-21T10:52:46.485Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712689)
;

-- Field: Belegart(135,D) -> Sequence(540744,D) -> Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:54:03.441Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-21 11:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712689
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:56:20.710Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712689,0,540744,540404,615896,'F',TO_TIMESTAMP('2023-02-21 11:56:20','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','N','N',0,'Land',0,0,0,TO_TIMESTAMP('2023-02-21 11:56:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T11:02:15.976Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T11:04:37.721Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-21 12:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615896
;

-- Field: Belegart(135,D) -> Sequence(540744,D) -> Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T11:05:20.885Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-02-21 12:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712689
;

