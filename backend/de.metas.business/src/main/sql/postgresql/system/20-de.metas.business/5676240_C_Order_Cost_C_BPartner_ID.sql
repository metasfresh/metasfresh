-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-02-08T19:45:51.392Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585956,187,0,30,542296,'C_BPartner_ID',TO_TIMESTAMP('2023-02-08 21:45:51','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Partner',0,0,TO_TIMESTAMP('2023-02-08 21:45:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T19:45:51.400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585956 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T19:45:51.405Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2023-02-08T19:45:54.209Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost','ALTER TABLE public.C_Order_Cost ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2023-02-08T19:45:54.218Z
ALTER TABLE C_Order_Cost ADD CONSTRAINT CBPartner_COrderCost FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Business Partner
-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-02-08T19:46:18.146Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585956,712275,0,546808,TO_TIMESTAMP('2023-02-08 21:46:18','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-08 21:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T19:46:18.148Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T19:46:18.150Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-08T19:46:18.162Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712275
;

-- 2023-02-08T19:46:18.163Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712275)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Business Partner
-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-02-08T19:46:48.531Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712275,0,546808,550345,615654,'F',TO_TIMESTAMP('2023-02-08 21:46:48','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Business Partner',30,0,0,TO_TIMESTAMP('2023-02-08 21:46:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> Business Partner
-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-02-08T19:47:38.517Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585956,712276,0,546811,TO_TIMESTAMP('2023-02-08 21:47:38','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-08 21:47:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T19:47:38.519Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T19:47:38.520Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-08T19:47:38.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712276
;

-- 2023-02-08T19:47:38.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712276)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Cost(546811,D) -> main -> 10 -> primary.Business Partner
-- Column: C_Order_Cost.C_BPartner_ID
-- 2023-02-08T19:47:53.075Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712276,0,546811,550355,615655,'F',TO_TIMESTAMP('2023-02-08 21:47:52','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Business Partner',30,0,0,TO_TIMESTAMP('2023-02-08 21:47:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: C_BPartner_ID
-- 2023-02-08T19:49:18.782Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,585214,542538,30,'C_BPartner_ID',TO_TIMESTAMP('2023-02-08 21:49:18','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Business Partner',5,TO_TIMESTAMP('2023-02-08 21:49:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T19:49:18.784Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542538 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: C_Order_CreateCost(de.metas.ui.web.order.costs.C_Order_CreateCost)
-- ParameterName: C_BPartner_ID
-- 2023-02-08T19:49:36.888Z
UPDATE AD_Process_Para SET DefaultValue='@C_BPartner_ID/-1@',Updated=TO_TIMESTAMP('2023-02-08 21:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542538
;

