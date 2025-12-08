-- UI Column: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10
-- UI Element Group: product & asi & qty
-- 2023-02-15T15:09:39.515Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541919,550393,TO_TIMESTAMP('2023-02-15 17:09:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','product & asi & qty',40,TO_TIMESTAMP('2023-02-15 17:09:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> product & asi & qty.Produkt
-- Column: M_MatchInv.M_Product_ID
-- 2023-02-15T15:09:50.912Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550393, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-15 17:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561866
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> product & asi & qty.Merkmale
-- Column: M_MatchInv.M_AttributeSetInstance_ID
-- 2023-02-15T15:10:05.718Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550393, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-02-15 17:10:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561867
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> product & asi & qty.Menge
-- Column: M_MatchInv.Qty
-- 2023-02-15T15:10:19.523Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550393, IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2023-02-15 17:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561877
;

-- UI Column: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10
-- UI Element Group: product & asi & qty
-- 2023-02-15T15:10:27.529Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-02-15 17:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550393
;

-- UI Column: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10
-- UI Element Group: Cost Matching
-- 2023-02-15T15:10:33.198Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2023-02-15 17:10:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550392
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> UOM
-- Column: M_MatchInv.C_UOM_ID
-- 2023-02-15T15:11:08.093Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568542,712600,0,408,TO_TIMESTAMP('2023-02-15 17:11:07','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure',10,'D','The UOM defines a unique non monetary Unit of Measure','Y','N','N','N','N','N','N','N','UOM',TO_TIMESTAMP('2023-02-15 17:11:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T15:11:08.095Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T15:11:08.097Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-02-15T15:11:08.135Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712600
;

-- 2023-02-15T15:11:08.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712600)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Quantity in UOM
-- Column: M_MatchInv.QtyInUOM
-- 2023-02-15T15:11:23.620Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568543,712601,0,408,TO_TIMESTAMP('2023-02-15 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Quantity in UOM',TO_TIMESTAMP('2023-02-15 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T15:11:23.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T15:11:23.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576984) 
;

-- 2023-02-15T15:11:23.629Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712601
;

-- 2023-02-15T15:11:23.630Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712601)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> product & asi & qty.Quantity in UOM
-- Column: M_MatchInv.QtyInUOM
-- 2023-02-15T15:11:50.800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712601,0,408,550393,615835,'F',TO_TIMESTAMP('2023-02-15 17:11:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Quantity in UOM',40,0,0,TO_TIMESTAMP('2023-02-15 17:11:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> product & asi & qty.UOM
-- Column: M_MatchInv.C_UOM_ID
-- 2023-02-15T15:11:56.418Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712600,0,408,550393,615836,'F',TO_TIMESTAMP('2023-02-15 17:11:56','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure','The UOM defines a unique non monetary Unit of Measure','Y','N','Y','N','N','UOM',50,0,0,TO_TIMESTAMP('2023-02-15 17:11:56','YYYY-MM-DD HH24:MI:SS'),100)
;

