-- 2021-02-16T08:33:09.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541257,TO_TIMESTAMP('2021-02-16 10:33:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BP_BankAccount(isIdentifier)',TO_TIMESTAMP('2021-02-16 10:33:09','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-02-16T08:33:09.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541257 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-16T08:33:39.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3094,0,541257,298,TO_TIMESTAMP('2021-02-16 10:33:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-02-16 10:33:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-16T08:35:00.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541257,Updated=TO_TIMESTAMP('2021-02-16 10:35:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572572
;

-- 2021-02-16T08:35:11.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541257,Updated=TO_TIMESTAMP('2021-02-16 10:35:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572573
;

-- 2021-02-16T08:46:31.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=542083,Updated=TO_TIMESTAMP('2021-02-16 10:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541257
;

-- 2021-02-16T10:10:16.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=336,Updated=TO_TIMESTAMP('2021-02-16 12:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572601
;

-- 2021-02-16T11:19:57.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@InvoiceIdentifier/''''@!''''',Updated=TO_TIMESTAMP('2021-02-16 13:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628793
;

-- 2021-02-16T11:21:17.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_BPartner_ID/-1@ > 0',Updated=TO_TIMESTAMP('2021-02-16 13:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628796
;

-- 2021-02-16T11:21:34.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-02-16 13:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628796
;

-- 2021-02-16T11:22:02.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_BPartner_ID/-1@ > 0',Updated=TO_TIMESTAMP('2021-02-16 13:22:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628819
;

-- 2021-02-16T11:24:25.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ExternalInvoiceDocBaseType/''''@!''''', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-02-16 13:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628795
;

-- 2021-02-16T11:24:48.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ExternalInvoiceDocBaseType/''''@!''''',Updated=TO_TIMESTAMP('2021-02-16 13:24:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628818
;

-- 2021-02-16T11:26:25.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ServiceFeeVatRate/-1@ > 0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-02-16 13:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628800
;

-- 2021-02-16T11:27:46.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@InvoiceDate/''1970-01-01''@ > ''1970-01-01''', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-02-16 13:27:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628794
;

-- 2021-02-16T11:28:02.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@InvoiceDate/''1970-01-01''@ > ''1970-01-01''',Updated=TO_TIMESTAMP('2021-02-16 13:28:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628817
;

-- 2021-02-16T11:31:34.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ServiceFeeVatRate/-1@ > 0',Updated=TO_TIMESTAMP('2021-02-16 13:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628820
;

-- 2021-02-16T11:32:47.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,628794,0,543320,577954,544779,'F',TO_TIMESTAMP('2021-02-16 13:32:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Invoice date',250,0,0,TO_TIMESTAMP('2021-02-16 13:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-16T11:33:38.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,628800,0,543320,577955,544779,'F',TO_TIMESTAMP('2021-02-16 13:33:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Service fee vat rate',260,0,0,TO_TIMESTAMP('2021-02-16 13:33:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-16T11:36:24.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=16,Updated=TO_TIMESTAMP('2021-02-16 13:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577954
;

-- 2021-02-16T11:36:44.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=17,Updated=TO_TIMESTAMP('2021-02-16 13:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576429
;

-- 2021-02-16T11:37:01.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=18,Updated=TO_TIMESTAMP('2021-02-16 13:37:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576422
;

-- 2021-02-16T11:37:05.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=19,Updated=TO_TIMESTAMP('2021-02-16 13:37:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577955
;

