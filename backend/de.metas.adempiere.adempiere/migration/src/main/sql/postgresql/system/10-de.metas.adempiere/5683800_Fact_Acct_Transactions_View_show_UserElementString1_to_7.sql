-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString1
-- Column: Fact_Acct_Transactions_View.UserElementString1
-- 2023-04-03T14:26:19.145Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:26:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710023
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString2
-- Column: Fact_Acct_Transactions_View.UserElementString2
-- 2023-04-03T14:26:22.779Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/X@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710024
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString3
-- Column: Fact_Acct_Transactions_View.UserElementString3
-- 2023-04-03T14:26:28.783Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/X@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:26:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710025
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString4
-- Column: Fact_Acct_Transactions_View.UserElementString4
-- 2023-04-03T14:26:33.313Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/X@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:26:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710026
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString5
-- Column: Fact_Acct_Transactions_View.UserElementString5
-- 2023-04-03T14:26:38.946Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/X@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710027
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString6
-- Column: Fact_Acct_Transactions_View.UserElementString6
-- 2023-04-03T14:26:43.543Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/X@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710028
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> UserElementString7
-- Column: Fact_Acct_Transactions_View.UserElementString7
-- 2023-04-03T14:26:47.739Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/X@=Y',Updated=TO_TIMESTAMP('2023-04-03 17:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710029
;

-- UI Column: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20
-- UI Element Group: dimensions
-- 2023-04-03T14:29:27.233Z
UPDATE AD_UI_ElementGroup SET Name='dimensions',Updated=TO_TIMESTAMP('2023-04-03 17:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549951
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.UserElementString1
-- Column: Fact_Acct_Transactions_View.UserElementString1
-- 2023-04-03T14:29:40.228Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710023,0,242,549951,616507,'F',TO_TIMESTAMP('2023-04-03 17:29:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString1',30,0,0,TO_TIMESTAMP('2023-04-03 17:29:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.UserElementString2
-- Column: Fact_Acct_Transactions_View.UserElementString2
-- 2023-04-03T14:29:52.106Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710024,0,242,549951,616508,'F',TO_TIMESTAMP('2023-04-03 17:29:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString2',40,0,0,TO_TIMESTAMP('2023-04-03 17:29:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.UserElementString3
-- Column: Fact_Acct_Transactions_View.UserElementString3
-- 2023-04-03T14:29:58.434Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710025,0,242,549951,616509,'F',TO_TIMESTAMP('2023-04-03 17:29:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString3',50,0,0,TO_TIMESTAMP('2023-04-03 17:29:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.UserElementString4
-- Column: Fact_Acct_Transactions_View.UserElementString4
-- 2023-04-03T14:30:08.051Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710026,0,242,549951,616510,'F',TO_TIMESTAMP('2023-04-03 17:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString4',60,0,0,TO_TIMESTAMP('2023-04-03 17:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.UserElementString5
-- Column: Fact_Acct_Transactions_View.UserElementString5
-- 2023-04-03T14:30:15.362Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710027,0,242,549951,616511,'F',TO_TIMESTAMP('2023-04-03 17:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString5',70,0,0,TO_TIMESTAMP('2023-04-03 17:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.UserElementString6
-- Column: Fact_Acct_Transactions_View.UserElementString6
-- 2023-04-03T14:30:24.019Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710028,0,242,549951,616512,'F',TO_TIMESTAMP('2023-04-03 17:30:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString6',80,0,0,TO_TIMESTAMP('2023-04-03 17:30:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.UserElementString7
-- Column: Fact_Acct_Transactions_View.UserElementString7
-- 2023-04-03T14:30:30.386Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710029,0,242,549951,616513,'F',TO_TIMESTAMP('2023-04-03 17:30:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','UserElementString7',90,0,0,TO_TIMESTAMP('2023-04-03 17:30:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.Section Code
-- Column: Fact_Acct_Transactions_View.M_SectionCode_ID
-- 2023-04-03T14:30:42.924Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2023-04-03 17:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613104
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> dimensions.Sales order
-- Column: Fact_Acct_Transactions_View.C_OrderSO_ID
-- 2023-04-03T14:30:47.035Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2023-04-03 17:30:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614551
;

-- Column: Fact_Acct_Transactions_View.UserElementString1
-- 2023-04-03T14:38:38.055Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-03 17:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585375
;

-- Column: Fact_Acct_Transactions_View.UserElementString2
-- 2023-04-03T14:38:41.784Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-03 17:38:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585376
;

-- Column: Fact_Acct_Transactions_View.UserElementString3
-- 2023-04-03T14:38:44.366Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-03 17:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585377
;

-- Column: Fact_Acct_Transactions_View.UserElementString4
-- 2023-04-03T14:38:46.948Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-03 17:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585378
;

-- Column: Fact_Acct_Transactions_View.UserElementString5
-- 2023-04-03T14:38:50.293Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-03 17:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585379
;

-- Column: Fact_Acct_Transactions_View.UserElementString6
-- 2023-04-03T14:38:53.482Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-03 17:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585380
;

-- Column: Fact_Acct_Transactions_View.UserElementString7
-- 2023-04-03T14:38:56.920Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2023-04-03 17:38:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585381
;

