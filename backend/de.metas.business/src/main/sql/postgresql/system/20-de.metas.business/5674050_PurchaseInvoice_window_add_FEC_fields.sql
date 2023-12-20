-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC
-- Column: C_Invoice.IsFEC
-- 2023-01-27T14:45:40.794Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585662,710754,0,290,TO_TIMESTAMP('2023-01-27 16:45:40','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','FEC',TO_TIMESTAMP('2023-01-27 16:45:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T14:45:40.796Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T14:45:40.798Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581947) 
;

-- 2023-01-27T14:45:40.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710754
;

-- 2023-01-27T14:45:40.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710754)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Order Currency
-- Column: C_Invoice.FEC_Order_Currency_ID
-- 2023-01-27T14:45:50.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585663,710755,0,290,TO_TIMESTAMP('2023-01-27 16:45:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Currency',TO_TIMESTAMP('2023-01-27 16:45:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T14:45:50.831Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710755 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T14:45:50.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581964) 
;

-- 2023-01-27T14:45:50.835Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710755
;

-- 2023-01-27T14:45:50.836Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710755)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Foreign Exchange Contract
-- Column: C_Invoice.C_ForeignExchangeContract_ID
-- 2023-01-27T14:45:58.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585664,710756,0,290,TO_TIMESTAMP('2023-01-27 16:45:58','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Foreign Exchange Contract',TO_TIMESTAMP('2023-01-27 16:45:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T14:45:58.831Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710756 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T14:45:58.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581935) 
;

-- 2023-01-27T14:45:58.835Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710756
;

-- 2023-01-27T14:45:58.836Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710756)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency From
-- Column: C_Invoice.FEC_From_Currency_ID
-- 2023-01-27T14:46:15.886Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585665,710757,0,290,TO_TIMESTAMP('2023-01-27 16:46:15','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency From',TO_TIMESTAMP('2023-01-27 16:46:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T14:46:15.887Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710757 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T14:46:15.889Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581967) 
;

-- 2023-01-27T14:46:15.892Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710757
;

-- 2023-01-27T14:46:15.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710757)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency To
-- Column: C_Invoice.FEC_To_Currency_ID
-- 2023-01-27T14:46:22.387Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585666,710758,0,290,TO_TIMESTAMP('2023-01-27 16:46:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency To',TO_TIMESTAMP('2023-01-27 16:46:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T14:46:22.389Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710758 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T14:46:22.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581968) 
;

-- 2023-01-27T14:46:22.393Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710758
;

-- 2023-01-27T14:46:22.393Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710758)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency Rate
-- Column: C_Invoice.FEC_CurrencyRate
-- 2023-01-27T14:46:28.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585601,710759,0,290,TO_TIMESTAMP('2023-01-27 16:46:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency Rate',TO_TIMESTAMP('2023-01-27 16:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T14:46:28.062Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710759 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T14:46:28.063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581963) 
;

-- 2023-01-27T14:46:28.066Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710759
;

-- 2023-01-27T14:46:28.066Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710759)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC
-- Column: C_Invoice.IsFEC
-- 2023-01-27T14:47:06.108Z
UPDATE AD_Field SET DisplayLogic='1=2', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710754
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency Rate
-- Column: C_Invoice.FEC_CurrencyRate
-- 2023-01-27T14:47:22.396Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710759
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency From
-- Column: C_Invoice.FEC_From_Currency_ID
-- 2023-01-27T14:47:24.286Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710757
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Order Currency
-- Column: C_Invoice.FEC_Order_Currency_ID
-- 2023-01-27T14:47:26.208Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710755
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency To
-- Column: C_Invoice.FEC_To_Currency_ID
-- 2023-01-27T14:47:27.927Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710758
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency To
-- Column: C_Invoice.FEC_To_Currency_ID
-- 2023-01-27T14:47:30.620Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710758
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Order Currency
-- Column: C_Invoice.FEC_Order_Currency_ID
-- 2023-01-27T14:47:31.594Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710755
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> FEC Currency From
-- Column: C_Invoice.FEC_From_Currency_ID
-- 2023-01-27T14:47:32.792Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710757
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> Foreign Exchange Contract
-- Column: C_Invoice.C_ForeignExchangeContract_ID
-- 2023-01-27T14:47:55.724Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-27 16:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710756
;

-- UI Column: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20
-- UI Element Group: FEC
-- 2023-01-27T14:48:11.522Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540149,550261,TO_TIMESTAMP('2023-01-27 16:48:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','FEC',15,TO_TIMESTAMP('2023-01-27 16:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> FEC.FEC
-- Column: C_Invoice.IsFEC
-- 2023-01-27T14:48:46.242Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710754,0,290,550261,614898,'F',TO_TIMESTAMP('2023-01-27 16:48:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC',10,0,0,TO_TIMESTAMP('2023-01-27 16:48:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> FEC.Order Currency
-- Column: C_Invoice.FEC_Order_Currency_ID
-- 2023-01-27T14:48:55.224Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710755,0,290,550261,614899,'F',TO_TIMESTAMP('2023-01-27 16:48:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Order Currency',20,0,0,TO_TIMESTAMP('2023-01-27 16:48:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> FEC.Foreign Exchange Contract
-- Column: C_Invoice.C_ForeignExchangeContract_ID
-- 2023-01-27T14:49:02.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710756,0,290,550261,614900,'F',TO_TIMESTAMP('2023-01-27 16:49:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Foreign Exchange Contract',30,0,0,TO_TIMESTAMP('2023-01-27 16:49:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> FEC.FEC Currency From
-- Column: C_Invoice.FEC_From_Currency_ID
-- 2023-01-27T14:49:10.017Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710757,0,290,550261,614901,'F',TO_TIMESTAMP('2023-01-27 16:49:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency From',40,0,0,TO_TIMESTAMP('2023-01-27 16:49:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> FEC.FEC Currency To
-- Column: C_Invoice.FEC_To_Currency_ID
-- 2023-01-27T14:49:16.917Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710758,0,290,550261,614902,'F',TO_TIMESTAMP('2023-01-27 16:49:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency To',50,0,0,TO_TIMESTAMP('2023-01-27 16:49:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> FEC.FEC Currency Rate
-- Column: C_Invoice.FEC_CurrencyRate
-- 2023-01-27T14:49:23.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710759,0,290,550261,614903,'F',TO_TIMESTAMP('2023-01-27 16:49:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency Rate',60,0,0,TO_TIMESTAMP('2023-01-27 16:49:23','YYYY-MM-DD HH24:MI:SS'),100)
;

