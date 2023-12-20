-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC
-- Column: C_Invoice.IsFEC
-- 2023-01-30T13:35:11.146Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585662,710768,0,263,TO_TIMESTAMP('2023-01-30 15:35:10','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','FEC',TO_TIMESTAMP('2023-01-30 15:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T13:35:11.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710768 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T13:35:11.150Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581947) 
;

-- 2023-01-30T13:35:11.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710768
;

-- 2023-01-30T13:35:11.157Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710768)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> Foreign Exchange Contract
-- Column: C_Invoice.C_ForeignExchangeContract_ID
-- 2023-01-30T13:35:21.386Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585664,710769,0,263,TO_TIMESTAMP('2023-01-30 15:35:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Foreign Exchange Contract',TO_TIMESTAMP('2023-01-30 15:35:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T13:35:21.388Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T13:35:21.389Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581935) 
;

-- 2023-01-30T13:35:21.393Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710769
;

-- 2023-01-30T13:35:21.394Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710769)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> Order Currency
-- Column: C_Invoice.FEC_Order_Currency_ID
-- 2023-01-30T13:35:27.760Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585663,710770,0,263,TO_TIMESTAMP('2023-01-30 15:35:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Currency',TO_TIMESTAMP('2023-01-30 15:35:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T13:35:27.762Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T13:35:27.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581964) 
;

-- 2023-01-30T13:35:27.770Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710770
;

-- 2023-01-30T13:35:27.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710770)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC Currency From
-- Column: C_Invoice.FEC_From_Currency_ID
-- 2023-01-30T13:35:33.741Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585665,710771,0,263,TO_TIMESTAMP('2023-01-30 15:35:33','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency From',TO_TIMESTAMP('2023-01-30 15:35:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T13:35:33.743Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T13:35:33.745Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581967) 
;

-- 2023-01-30T13:35:33.750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710771
;

-- 2023-01-30T13:35:33.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710771)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC Currency To
-- Column: C_Invoice.FEC_To_Currency_ID
-- 2023-01-30T13:35:39.344Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585666,710772,0,263,TO_TIMESTAMP('2023-01-30 15:35:39','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency To',TO_TIMESTAMP('2023-01-30 15:35:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T13:35:39.346Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T13:35:39.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581968) 
;

-- 2023-01-30T13:35:39.350Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710772
;

-- 2023-01-30T13:35:39.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710772)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC Currency Rate
-- Column: C_Invoice.FEC_CurrencyRate
-- 2023-01-30T13:35:45.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585601,710773,0,263,TO_TIMESTAMP('2023-01-30 15:35:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency Rate',TO_TIMESTAMP('2023-01-30 15:35:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T13:35:45.710Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T13:35:45.712Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581963) 
;

-- 2023-01-30T13:35:45.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710773
;

-- 2023-01-30T13:35:45.715Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710773)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC
-- Column: C_Invoice.IsFEC
-- 2023-01-30T13:36:01.616Z
UPDATE AD_Field SET DisplayLogic='1=2',Updated=TO_TIMESTAMP('2023-01-30 15:36:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710768
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC
-- Column: C_Invoice.IsFEC
-- 2023-01-30T13:36:04.625Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 15:36:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710768
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> Foreign Exchange Contract
-- Column: C_Invoice.C_ForeignExchangeContract_ID
-- 2023-01-30T13:36:31.163Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 15:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710769
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC Currency Rate
-- Column: C_Invoice.FEC_CurrencyRate
-- 2023-01-30T13:37:01.529Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 15:37:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710773
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC Currency From
-- Column: C_Invoice.FEC_From_Currency_ID
-- 2023-01-30T13:37:08.812Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 15:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710771
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> Order Currency
-- Column: C_Invoice.FEC_Order_Currency_ID
-- 2023-01-30T13:37:11.978Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 15:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710770
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> FEC Currency To
-- Column: C_Invoice.FEC_To_Currency_ID
-- 2023-01-30T13:37:15.910Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 15:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710772
;

-- UI Column: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20
-- UI Element Group: FEC
-- 2023-01-30T13:39:04.738Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540029,550263,TO_TIMESTAMP('2023-01-30 15:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','FEC',25,TO_TIMESTAMP('2023-01-30 15:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> FEC.FEC
-- Column: C_Invoice.IsFEC
-- 2023-01-30T13:39:19.002Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710768,0,263,550263,614910,'F',TO_TIMESTAMP('2023-01-30 15:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC',10,0,0,TO_TIMESTAMP('2023-01-30 15:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> FEC.Order Currency
-- Column: C_Invoice.FEC_Order_Currency_ID
-- 2023-01-30T13:39:26.694Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710770,0,263,550263,614911,'F',TO_TIMESTAMP('2023-01-30 15:39:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Order Currency',20,0,0,TO_TIMESTAMP('2023-01-30 15:39:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> FEC.Foreign Exchange Contract
-- Column: C_Invoice.C_ForeignExchangeContract_ID
-- 2023-01-30T13:39:35.568Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710769,0,263,550263,614912,'F',TO_TIMESTAMP('2023-01-30 15:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Foreign Exchange Contract',30,0,0,TO_TIMESTAMP('2023-01-30 15:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> FEC.FEC Currency From
-- Column: C_Invoice.FEC_From_Currency_ID
-- 2023-01-30T13:39:42.240Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710771,0,263,550263,614913,'F',TO_TIMESTAMP('2023-01-30 15:39:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency From',40,0,0,TO_TIMESTAMP('2023-01-30 15:39:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> FEC.FEC Currency To
-- Column: C_Invoice.FEC_To_Currency_ID
-- 2023-01-30T13:39:48.943Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710772,0,263,550263,614914,'F',TO_TIMESTAMP('2023-01-30 15:39:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency To',50,0,0,TO_TIMESTAMP('2023-01-30 15:39:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> FEC.FEC Currency Rate
-- Column: C_Invoice.FEC_CurrencyRate
-- 2023-01-30T13:39:55.725Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710773,0,263,550263,614915,'F',TO_TIMESTAMP('2023-01-30 15:39:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency Rate',60,0,0,TO_TIMESTAMP('2023-01-30 15:39:55','YYYY-MM-DD HH24:MI:SS'),100)
;

