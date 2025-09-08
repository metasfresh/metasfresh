-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-09T12:01:10.519Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585958,187,0,30,542299,'C_BPartner_ID',TO_TIMESTAMP('2023-02-09 14:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Partner',0,0,TO_TIMESTAMP('2023-02-09 14:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-09T12:01:10.525Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585958 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-09T12:01:10.534Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2023-02-09T12:03:07.787Z
/* DDL */ SELECT public.db_alter_table('M_InOut_Cost','ALTER TABLE public.M_InOut_Cost ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2023-02-09T12:03:07.797Z
ALTER TABLE M_InOut_Cost ADD CONSTRAINT CBPartner_MInOutCost FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InOut_Cost.C_Cost_Type_ID
-- 2023-02-09T12:03:29.051Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585959,582023,0,30,542299,'C_Cost_Type_ID',TO_TIMESTAMP('2023-02-09 14:03:28','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Cost Type',0,0,TO_TIMESTAMP('2023-02-09 14:03:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-09T12:03:29.052Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585959 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-09T12:03:29.055Z
/* DDL */  select update_Column_Translation_From_AD_Element(582023) 
;

-- 2023-02-09T12:03:31.642Z
/* DDL */ SELECT public.db_alter_table('M_InOut_Cost','ALTER TABLE public.M_InOut_Cost ADD COLUMN C_Cost_Type_ID NUMERIC(10)')
;

-- 2023-02-09T12:03:31.649Z
ALTER TABLE M_InOut_Cost ADD CONSTRAINT CCostType_MInOutCost FOREIGN KEY (C_Cost_Type_ID) REFERENCES public.C_Cost_Type DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-09T12:03:35.949Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2023-02-09 14:03:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585958
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Business Partner
-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-09T12:04:10.054Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585958,712278,0,546813,TO_TIMESTAMP('2023-02-09 14:04:09','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-09 14:04:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-09T12:04:10.055Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712278 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-09T12:04:10.057Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-09T12:04:10.071Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712278
;

-- 2023-02-09T12:04:10.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712278)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Cost Type
-- Column: M_InOut_Cost.C_Cost_Type_ID
-- 2023-02-09T12:04:10.175Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585959,712279,0,546813,TO_TIMESTAMP('2023-02-09 14:04:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Type',TO_TIMESTAMP('2023-02-09 14:04:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-09T12:04:10.177Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712279 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-09T12:04:10.178Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582023) 
;

-- 2023-02-09T12:04:10.182Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712279
;

-- 2023-02-09T12:04:10.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712279)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Business Partner
-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-09T12:05:04.415Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712278,0,546813,550365,615659,'F',TO_TIMESTAMP('2023-02-09 14:05:04','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Business Partner',80,0,0,TO_TIMESTAMP('2023-02-09 14:05:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Cost Type
-- Column: M_InOut_Cost.C_Cost_Type_ID
-- 2023-02-09T12:05:11.167Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712279,0,546813,550365,615660,'F',TO_TIMESTAMP('2023-02-09 14:05:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Type',90,0,0,TO_TIMESTAMP('2023-02-09 14:05:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Business Partner
-- Column: M_InOut_Cost.C_BPartner_ID
-- 2023-02-09T12:09:12.232Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-09 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615659
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> references.Cost Type
-- Column: M_InOut_Cost.C_Cost_Type_ID
-- 2023-02-09T12:09:12.241Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-09 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615660
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.UOM
-- Column: M_InOut_Cost.C_UOM_ID
-- 2023-02-09T12:09:12.248Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-09 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615645
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Quantity
-- Column: M_InOut_Cost.Qty
-- 2023-02-09T12:09:12.256Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-02-09 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615646
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Currency
-- Column: M_InOut_Cost.C_Currency_ID
-- 2023-02-09T12:09:12.263Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-02-09 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615647
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Cost Amount
-- Column: M_InOut_Cost.CostAmount
-- 2023-02-09T12:09:12.270Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-02-09 14:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615648
;

