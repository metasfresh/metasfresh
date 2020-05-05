-- 2019-03-12T17:37:32.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-03-12 17:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8764
;

-- NOTE: After the feature will be fixed, we shall run the UPDATE below again to set the Sales Order's Bill_Partner_ID field as Table instead of Search.
-- 2019-03-12T17:41:29.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=138, AD_Val_Rule_ID=540230,Updated=TO_TIMESTAMP('2019-03-12 17:41:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6559
;

-- 2019-03-13T10:05:08.820
-- workaround until the feature is fixed: set Sales Order's Bill_Partner_ID field as Search.
UPDATE AD_Field SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-03-13 10:05:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6559
;


