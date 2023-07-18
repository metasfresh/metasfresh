-- Column: C_ForeignExchangeContract.M_SectionCode_ID
-- 2023-07-13T03:22:26.717Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587101,581238,0,30,541698,542281,'XX','M_SectionCode_ID',TO_TIMESTAMP('2023-07-13 06:21:41','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-07-13 06:21:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-13T03:22:26.724Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587101 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-13T03:22:27.209Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: C_ForeignExchangeContract.M_SectionCode_ID
-- 2023-07-13T03:23:13.721Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2023-07-13 06:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587101
;

-- 2023-07-13T03:23:16.646Z
/* DDL */ SELECT public.db_alter_table('C_ForeignExchangeContract','ALTER TABLE public.C_ForeignExchangeContract ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-07-13T03:23:16.721Z
ALTER TABLE C_ForeignExchangeContract ADD CONSTRAINT MSectionCode_CForeignExchangeContract FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_ForeignExchangeContract_Alloc.M_SectionCode_ID
-- 2023-07-13T03:24:32.614Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587102,581238,0,30,541698,542283,'XX','M_SectionCode_ID',TO_TIMESTAMP('2023-07-13 06:24:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-07-13 06:24:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-13T03:24:32.618Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587102 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-13T03:24:33.967Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-07-13T03:24:49.166Z
/* DDL */ SELECT public.db_alter_table('C_ForeignExchangeContract_Alloc','ALTER TABLE public.C_ForeignExchangeContract_Alloc ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-07-13T03:24:49.185Z
ALTER TABLE C_ForeignExchangeContract_Alloc ADD CONSTRAINT MSectionCode_CForeignExchangeContractAlloc FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> Section Code
-- Column: C_ForeignExchangeContract.M_SectionCode_ID
-- 2023-07-13T16:07:37.934Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587101,716668,0,546745,TO_TIMESTAMP('2023-07-13 19:07:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-07-13 19:07:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-13T16:07:37.945Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716668 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-13T16:07:37.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-07-13T16:07:38.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716668
;

-- 2023-07-13T16:07:38.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716668)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Section Code
-- Column: C_ForeignExchangeContract.M_SectionCode_ID
-- 2023-07-13T16:09:39.574Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716668,0,546745,618251,550235,'F',TO_TIMESTAMP('2023-07-13 19:09:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',50,0,0,TO_TIMESTAMP('2023-07-13 19:09:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> main -> 10 -> primary.Section Code
-- Column: C_ForeignExchangeContract.M_SectionCode_ID
-- 2023-07-13T16:10:13.472Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2023-07-13 19:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618251
;
