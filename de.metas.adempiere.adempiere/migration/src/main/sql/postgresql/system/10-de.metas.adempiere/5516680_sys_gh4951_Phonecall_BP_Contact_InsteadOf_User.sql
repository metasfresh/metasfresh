-- 2019-03-20T14:02:54.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=541208, AD_Reference_ID=18, AD_Reference_Value_ID=110, ColumnName='C_BP_Contact_ID', Description=NULL, EntityType='de.metas.swat', Help=NULL, Name='Contact',Updated=TO_TIMESTAMP('2019-03-20 14:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564474
;

-- 2019-03-20T14:02:54.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Contact', Description=NULL, Help=NULL WHERE AD_Column_ID=564474
;

-- 2019-03-20T14:07:10.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=541208, AD_Reference_ID=18, AD_Reference_Value_ID=110, ColumnName='C_BP_Contact_ID', Description=NULL, EntityType='de.metas.swat', Help=NULL, Name='Contact',Updated=TO_TIMESTAMP('2019-03-20 14:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564132
;

-- 2019-03-20T14:07:10.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Contact', Description=NULL, Help=NULL WHERE AD_Column_ID=564132
;

-- 2019-03-20T14:11:28.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-20 14:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564474
;

-- 2019-03-20T14:11:41.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-20 14:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564132
;

-- 2019-03-20T14:22:50.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select phone from ad_user where ad_user_ID = c_phonecall_schedule.c_bp_contact_id)',Updated=TO_TIMESTAMP('2019-03-20 14:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564553
;

