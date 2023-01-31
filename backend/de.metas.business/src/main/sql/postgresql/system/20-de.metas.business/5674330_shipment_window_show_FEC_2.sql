-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-30T14:30:28.861Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585652,710774,0,257,TO_TIMESTAMP('2023-01-30 16:30:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Currency',TO_TIMESTAMP('2023-01-30 16:30:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T14:30:28.863Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710774 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T14:30:28.865Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581964) 
;

-- 2023-01-30T14:30:28.870Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710774
;

-- 2023-01-30T14:30:28.872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710774)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-30T14:30:38.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585653,710775,0,257,TO_TIMESTAMP('2023-01-30 16:30:38','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency From',TO_TIMESTAMP('2023-01-30 16:30:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T14:30:38.545Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T14:30:38.546Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581967) 
;

-- 2023-01-30T14:30:38.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710775
;

-- 2023-01-30T14:30:38.550Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710775)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-30T14:30:43.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585654,710776,0,257,TO_TIMESTAMP('2023-01-30 16:30:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','FEC Currency To',TO_TIMESTAMP('2023-01-30 16:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T14:30:43.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T14:30:43.120Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581968) 
;

-- 2023-01-30T14:30:43.123Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710776
;

-- 2023-01-30T14:30:43.123Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710776)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC
-- Column: M_InOut.IsFEC
-- 2023-01-30T14:30:55.536Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585656,710777,0,257,TO_TIMESTAMP('2023-01-30 16:30:55','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','FEC',TO_TIMESTAMP('2023-01-30 16:30:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-30T14:30:55.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710777 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-30T14:30:55.540Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581947) 
;

-- 2023-01-30T14:30:55.546Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710777
;

-- 2023-01-30T14:30:55.547Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710777)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-30T14:31:14.642Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-30 16:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710524
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC
-- Column: M_InOut.IsFEC
-- 2023-01-30T14:31:28.439Z
UPDATE AD_Field SET DisplayLogic='1=2', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 16:31:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710777
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-30T14:31:37.428Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y',Updated=TO_TIMESTAMP('2023-01-30 16:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710681
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-30T14:31:41.333Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 16:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710775
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-30T14:31:44.905Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 16:31:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710774
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-30T14:31:48.104Z
UPDATE AD_Field SET DisplayLogic='@IsFEC/N@=Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-01-30 16:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710776
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC
-- Column: M_InOut.IsFEC
-- 2023-01-30T14:33:06.958Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710777,0,257,550253,614916,'F',TO_TIMESTAMP('2023-01-30 16:33:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC',30,0,0,TO_TIMESTAMP('2023-01-30 16:33:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-30T14:33:15.128Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710774,0,257,550253,614917,'F',TO_TIMESTAMP('2023-01-30 16:33:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Order Currency',40,0,0,TO_TIMESTAMP('2023-01-30 16:33:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-30T14:33:22.079Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710775,0,257,550253,614918,'F',TO_TIMESTAMP('2023-01-30 16:33:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency From',50,0,0,TO_TIMESTAMP('2023-01-30 16:33:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-30T14:33:28.949Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710776,0,257,550253,614919,'F',TO_TIMESTAMP('2023-01-30 16:33:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','FEC Currency To',60,0,0,TO_TIMESTAMP('2023-01-30 16:33:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC
-- Column: M_InOut.IsFEC
-- 2023-01-30T14:33:37.102Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-01-30 16:33:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614916
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.Order Currency
-- Column: M_InOut.FEC_Order_Currency_ID
-- 2023-01-30T14:33:43.759Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-01-30 16:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614917
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.Foreign Exchange Contract
-- Column: M_InOut.C_ForeignExchangeContract_ID
-- 2023-01-30T14:33:50.780Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-01-30 16:33:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614850
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC Currency From
-- Column: M_InOut.FEC_From_Currency_ID
-- 2023-01-30T14:34:03.790Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-01-30 16:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614918
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC Currency To
-- Column: M_InOut.FEC_To_Currency_ID
-- 2023-01-30T14:34:06.906Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-01-30 16:34:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614919
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 10 -> FEC.FEC Currency Rate
-- Column: M_InOut.FEC_CurrencyRate
-- 2023-01-30T14:34:09.102Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2023-01-30 16:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614879
;

