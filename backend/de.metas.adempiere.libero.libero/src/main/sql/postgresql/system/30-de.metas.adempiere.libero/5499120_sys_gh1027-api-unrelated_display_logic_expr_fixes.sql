--
-- solve "Failed evaluating display logic" problems in the PP_Order_Workflow tab

-- 2018-08-09T12:18:10.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ProcessType/None@=''DR''',Updated=TO_TIMESTAMP('2018-08-09 12:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=56693
;

-- 2018-08-09T12:18:14.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@WorkflowType/None@!''M'' | @WorkflowType@!''Q''',Updated=TO_TIMESTAMP('2018-08-09 12:18:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53827
;

-- 2018-08-09T12:18:18.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@WorkflowType/None@!''M'' | @WorkflowType/None@!''Q''',Updated=TO_TIMESTAMP('2018-08-09 12:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53828
;

-- 2018-08-09T12:18:22.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@WorkflowType/None@!''M'' | @WorkflowType/None@!''Q''',Updated=TO_TIMESTAMP('2018-08-09 12:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53827
;

-- 2018-08-09T12:18:45.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@WorkflowType/None@!G',Updated=TO_TIMESTAMP('2018-08-09 12:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53804
;

-- 2018-08-09T12:18:58.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@WorkflowType/None@!''M'' | @WorkflowType/None@!''Q''',Updated=TO_TIMESTAMP('2018-08-09 12:18:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53824
;

