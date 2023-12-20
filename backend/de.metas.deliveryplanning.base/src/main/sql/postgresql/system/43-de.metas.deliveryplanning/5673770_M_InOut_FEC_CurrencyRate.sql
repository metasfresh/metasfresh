-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:10:40.593Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585651,581963,0,22,319,'FEC_CurrencyRate',TO_TIMESTAMP('2023-01-26 15:10:39','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'FEC Currency Rate',0,0,TO_TIMESTAMP('2023-01-26 15:10:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T13:10:40.595Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585651 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T13:10:40.634Z
/* DDL */  select update_Column_Translation_From_AD_Element(581963) 
;

-- 2023-01-26T13:10:43.834Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN FEC_CurrencyRate NUMERIC')
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:11:38.731Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585651,710680,0,296,TO_TIMESTAMP('2023-01-26 15:11:38','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','FEC Currency Rate',TO_TIMESTAMP('2023-01-26 15:11:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T13:11:38.733Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710680 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T13:11:38.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581963) 
;

-- 2023-01-26T13:11:38.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710680
;

-- 2023-01-26T13:11:38.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710680)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:12:19.454Z
UPDATE AD_Field SET DisplayLogic='@FEC_CurrencyRate/0@!0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-26 15:12:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710680
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 10 -> FEC.FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:12:47.756Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710680,0,296,550254,614878,'F',TO_TIMESTAMP('2023-01-26 15:12:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency Rate',20,0,0,TO_TIMESTAMP('2023-01-26 15:12:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:14:23.587Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585651,710681,0,257,TO_TIMESTAMP('2023-01-26 15:14:23','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','FEC Currency Rate',TO_TIMESTAMP('2023-01-26 15:14:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T13:14:23.589Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T13:14:23.590Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581963) 
;

-- 2023-01-26T13:14:23.594Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710681
;

-- 2023-01-26T13:14:23.595Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710681)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:14:45.497Z
UPDATE AD_Field SET DisplayLogic='@FEC_CurrencyRate/0@!0',Updated=TO_TIMESTAMP('2023-01-26 15:14:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710681
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:15:13.347Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710681,0,257,550253,614879,'F',TO_TIMESTAMP('2023-01-26 15:15:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency Rate',20,0,0,TO_TIMESTAMP('2023-01-26 15:15:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-26T13:15:20.752Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-26 15:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710681
;

