--
-- show payment-term in invoice-adv-edit
--
-- 2018-10-29T18:38:37.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,Name,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount) VALUES (100,553997,2774,'Y',0,TO_TIMESTAMP('2018-10-29 18:38:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',5,541214,TO_TIMESTAMP('2018-10-29 18:38:37','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,263,'F','Zahlungsbedingung','N','N',0)
;

-- let dunning processes refresh the window after run
-- 2018-10-29T18:40:44.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2018-10-29 18:40:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540268
;

-- 2018-10-29T18:40:54.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='N',Updated=TO_TIMESTAMP('2018-10-29 18:40:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540268
;

-- 2018-10-29T18:41:04.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET IsBetaFunctionality='N', RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2018-10-29 18:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540269
;
