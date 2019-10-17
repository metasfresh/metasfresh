-- 2019-09-10T08:22:01.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=NULL,Updated=TO_TIMESTAMP('2019-09-10 10:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8192
;

-- 2019-09-10T08:22:01.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577058) 
;

-- 2019-09-10T08:22:01.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=8192
;

-- 2019-09-10T08:22:01.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(8192)
;

-- 2019-09-10T08:23:05.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Date of inventory', PrintName='Date of inventory',Updated=TO_TIMESTAMP('2019-09-10 10:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577058 AND AD_Language='en_US'
;

-- 2019-09-10T08:23:05.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577058,'en_US') 
;

