
-- 2019-05-17T08:46:15.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1099,0,186,1000026,559145,'F',TO_TIMESTAMP('2019-05-17 08:46:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_Order_PaymentTerm_ID',60,0,0,TO_TIMESTAMP('2019-05-17 08:46:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-17T08:46:35.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544780
;

-- 2019-05-17T09:00:21.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_PaymentTerm_Default_First',Updated=TO_TIMESTAMP('2019-05-17 09:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=227
;

-- 2019-05-17T09:01:33.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=141, OrderByClause='C_PaymentTerm.IsDefault DESC',Updated=TO_TIMESTAMP('2019-05-17 09:01:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=227
;

-- 2019-05-17T09:02:01.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=227, IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2019-05-17 09:02:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2187
;

-- 2019-05-17T09:27:02.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a new record is created where this column or field is empty, then insert the first possible record reference. If set, then AD_Val_Rule_ID is used for filtering and AD_Reference_Value_ID is used for ordering.', Help='Works for new recrods (model interceptor) and new UI-documents (tab callout).',Updated=TO_TIMESTAMP('2019-05-17 09:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='en_US'
;

-- 2019-05-17T09:27:12.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a new record is created where this column or field is empty, then insert the first possible record reference. If set, then AD_Val_Rule_ID is used for filtering and AD_Reference_Value_ID is used for ordering.',Updated=TO_TIMESTAMP('2019-05-17 09:27:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2019-05-17T09:27:17.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a new record is created where this column or field is empty, then insert the first possible record reference. If set, then AD_Val_Rule_ID is used for filtering and AD_Reference_Value_ID is used for ordering.',Updated=TO_TIMESTAMP('2019-05-17 09:27:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_DE'
;

-- 2019-05-17T09:27:20.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If a new record is created where this column or field is empty, then insert the first possible record reference. If set, then AD_Val_Rule_ID is used for filtering and AD_Reference_Value_ID is used for ordering.',Updated=TO_TIMESTAMP('2019-05-17 09:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='nl_NL'
;

-- 2019-05-17T09:27:31.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Works for new recrods (model interceptor) and new UI-documents (tab callout).',Updated=TO_TIMESTAMP('2019-05-17 09:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='nl_NL'
;

-- 2019-05-17T09:27:34.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Works for new recrods (model interceptor) and new UI-documents (tab callout).',Updated=TO_TIMESTAMP('2019-05-17 09:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_DE'
;

-- 2019-05-17T09:28:26.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Works for new recrods (model interceptor) and new UI-documents (tab callout).',Updated=TO_TIMESTAMP('2019-05-17 09:28:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2019-05-17T09:28:31.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Validierungsregel automatisch anwenden.',Updated=TO_TIMESTAMP('2019-05-17 09:28:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2019-05-17T09:28:34.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Validierungsregel automatisch anwenden',Updated=TO_TIMESTAMP('2019-05-17 09:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2019-05-17T09:28:44.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Validierungsregel automatisch anwenden.',Updated=TO_TIMESTAMP('2019-05-17 09:28:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2019-05-17T09:28:47.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Validierungsregel automatisch anwenden',Updated=TO_TIMESTAMP('2019-05-17 09:28:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2019-05-17T09:57:34.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Works for new records (model interceptor) and new UI-documents (tab callout).',Updated=TO_TIMESTAMP('2019-05-17 09:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575835 AND AD_Language='de_CH'
;

-- 2019-05-17T10:57:34.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2019-05-17 10:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4940
;

-- 2019-05-17T10:58:21.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2019-05-17 10:58:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1760
;

