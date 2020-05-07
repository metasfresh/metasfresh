-- 2019-08-12T06:47:42.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-08-12 09:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=541612
;

-- 2019-08-12T06:53:21.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Help='Price List schema defines calculation rules for price lists', AD_Name_ID=573588, Name='Preislisten-Schema', Description='Preislisten-Schema',Updated=TO_TIMESTAMP('2019-08-12 09:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557540
;

-- 2019-08-12T06:53:21.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(573588) 
;

-- 2019-08-12T06:53:21.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=557540
;

-- 2019-08-12T06:53:21.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(557540)
;

