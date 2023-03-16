-- Column: M_InOut_Cost.M_CostElement_ID
-- 2023-02-16T13:30:30.223Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586103,2700,0,30,542299,'M_CostElement_ID',TO_TIMESTAMP('2023-02-16 15:30:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Product Cost Element','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cost Element',0,0,TO_TIMESTAMP('2023-02-16 15:30:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-16T13:30:30.225Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586103 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-16T13:30:30.229Z
/* DDL */  select update_Column_Translation_From_AD_Element(2700) 
;

-- 2023-02-16T13:30:31.127Z
/* DDL */ SELECT public.db_alter_table('M_InOut_Cost','ALTER TABLE public.M_InOut_Cost ADD COLUMN M_CostElement_ID NUMERIC(10)')
;

-- 2023-02-16T13:30:31.135Z
ALTER TABLE M_InOut_Cost ADD CONSTRAINT MCostElement_MInOutCost FOREIGN KEY (M_CostElement_ID) REFERENCES public.M_CostElement DEFERRABLE INITIALLY DEFERRED
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Cost Element
-- Column: M_InOut_Cost.M_CostElement_ID
-- 2023-02-16T13:30:53.952Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586103,712621,0,546813,TO_TIMESTAMP('2023-02-16 15:30:53','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element',10,'D','Y','N','N','N','N','N','N','N','Cost Element',TO_TIMESTAMP('2023-02-16 15:30:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-16T13:30:53.953Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-16T13:30:53.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2700) 
;

-- 2023-02-16T13:30:53.961Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712621
;

-- 2023-02-16T13:30:53.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712621)
;

-- Table: M_InOut_Cost
-- 2023-02-16T13:31:09.256Z
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2023-02-16 15:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542299
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Cost Element
-- Column: M_InOut_Cost.M_CostElement_ID
-- 2023-02-16T13:31:45.752Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712621,0,546813,550365,615848,'F',TO_TIMESTAMP('2023-02-16 15:31:45','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element','Y','N','Y','N','N','Cost Element',100,0,0,TO_TIMESTAMP('2023-02-16 15:31:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Order_Cost.M_CostElement_ID
-- 2023-02-16T13:37:59.202Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586104,2700,0,30,542296,'M_CostElement_ID',TO_TIMESTAMP('2023-02-16 15:37:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Product Cost Element','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Cost Element',0,0,TO_TIMESTAMP('2023-02-16 15:37:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-16T13:37:59.204Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586104 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-16T13:37:59.207Z
/* DDL */  select update_Column_Translation_From_AD_Element(2700) 
;

-- 2023-02-16T13:37:59.946Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost','ALTER TABLE public.C_Order_Cost ADD COLUMN M_CostElement_ID NUMERIC(10)')
;

-- 2023-02-16T13:37:59.953Z
ALTER TABLE C_Order_Cost ADD CONSTRAINT MCostElement_COrderCost FOREIGN KEY (M_CostElement_ID) REFERENCES public.M_CostElement DEFERRABLE INITIALLY DEFERRED
;

-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Cost Element
-- Column: C_Order_Cost.M_CostElement_ID
-- 2023-02-16T13:38:16.253Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586104,712622,0,546808,TO_TIMESTAMP('2023-02-16 15:38:16','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element',10,'D','Y','N','N','N','N','N','N','N','Cost Element',TO_TIMESTAMP('2023-02-16 15:38:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-16T13:38:16.255Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-16T13:38:16.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2700) 
;

-- 2023-02-16T13:38:16.261Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712622
;

-- 2023-02-16T13:38:16.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712622)
;

-- UI Element: Order Cost(541676,D) -> Order Cost(546808,D) -> main -> 10 -> primary.Cost Element
-- Column: C_Order_Cost.M_CostElement_ID
-- 2023-02-16T13:38:41.306Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712622,0,546808,550345,615851,'F',TO_TIMESTAMP('2023-02-16 15:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element','Y','N','Y','N','N','Cost Element',40,0,0,TO_TIMESTAMP('2023-02-16 15:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_InOut_Cost.M_CostElement_ID
-- 2023-02-16T13:39:35.649Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-16 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586103
;

