--
-- change C_BPartner.AD_OrgBP_ID from "Button" to "Table" and remove the process reference. The process is still assigned to the C_BPartner table.
--

-- 2017-09-06T15:52:14.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=NULL, AD_Reference_ID=18, ReadOnlyLogic='',Updated=TO_TIMESTAMP('2017-09-06 15:52:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=10927
;

-- 2017-09-06T15:53:32.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-06 15:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=9620
;

