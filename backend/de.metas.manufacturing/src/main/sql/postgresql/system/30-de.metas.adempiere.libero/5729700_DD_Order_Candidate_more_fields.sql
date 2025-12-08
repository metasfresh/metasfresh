-- Column: DD_Order_Candidate.IsSimulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2024-07-18T17:02:01.500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588859,580611,0,20,542424,'XX','IsSimulated',TO_TIMESTAMP('2024-07-18 20:02:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Simulated',0,0,TO_TIMESTAMP('2024-07-18 20:02:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-18T17:02:01.503Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588859 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-18T17:02:01.531Z
/* DDL */  select update_Column_Translation_From_AD_Element(580611) 
;

-- 2024-07-18T17:02:02.449Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN IsSimulated CHAR(1) DEFAULT ''N'' CHECK (IsSimulated IN (''Y'',''N'')) NOT NULL')
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2024-07-18T17:02:31.044Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588859,729101,0,547559,TO_TIMESTAMP('2024-07-18 20:02:30','YYYY-MM-DD HH24:MI:SS'),100,1,'EE01','Y','N','N','N','N','N','N','N','Simulated',TO_TIMESTAMP('2024-07-18 20:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-18T17:02:31.048Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-18T17:02:31.053Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580611) 
;

-- 2024-07-18T17:02:31.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729101
;

-- 2024-07-18T17:02:31.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729101)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2024-07-18T17:02:51.231Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-18 20:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729101
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> flags.Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2024-07-18T17:03:35.778Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729101,0,547559,551863,625016,'F',TO_TIMESTAMP('2024-07-18 20:03:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Simulated',20,0,0,TO_TIMESTAMP('2024-07-18 20:03:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- 2024-07-18T17:23:29.983Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588860,2019,0,35,542424,'XX','M_AttributeSetInstance_ID',TO_TIMESTAMP('2024-07-18 20:23:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Merkmals Ausprägungen zum Produkt','EE01',0,10,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Merkmale',0,0,TO_TIMESTAMP('2024-07-18 20:23:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-18T17:23:29.986Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588860 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-18T17:23:29.989Z
/* DDL */  select update_Column_Translation_From_AD_Element(2019) 
;

-- 2024-07-18T17:23:30.820Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN M_AttributeSetInstance_ID NUMERIC(10)')
;

-- 2024-07-18T17:23:30.829Z
ALTER TABLE DD_Order_Candidate ADD CONSTRAINT MAttributeSetInstance_DDOrderCandidate FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES public.M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- 2024-07-18T17:23:53.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588860,729102,0,547559,TO_TIMESTAMP('2024-07-18 20:23:52','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',10,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2024-07-18 20:23:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-18T17:23:53.089Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-18T17:23:53.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2024-07-18T17:23:53.183Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729102
;

-- 2024-07-18T17:23:53.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729102)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- 2024-07-18T17:26:16.271Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729102,0,547559,551861,625017,'F',TO_TIMESTAMP('2024-07-18 20:26:16','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','Merkmale',60,0,0,TO_TIMESTAMP('2024-07-18 20:26:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- 2024-07-18T17:27:10.490Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-07-18 20:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729102
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> dates.Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- 2024-07-18T17:28:42.058Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-07-18 20:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624996
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- 2024-07-18T17:28:42.066Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-07-18 20:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624990
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2024-07-18T17:28:42.075Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-07-18 20:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624991
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Menge
-- Column: DD_Order_Candidate.QtyEntered
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Menge
-- Column: DD_Order_Candidate.QtyEntered
-- 2024-07-18T17:28:42.083Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-07-18 20:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624993
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2024-07-18T17:28:42.090Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-07-18 20:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624984
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2024-07-18T17:28:42.097Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-07-18 20:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624987
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> flags.Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2024-07-18T17:28:42.108Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-07-18 20:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625016
;

