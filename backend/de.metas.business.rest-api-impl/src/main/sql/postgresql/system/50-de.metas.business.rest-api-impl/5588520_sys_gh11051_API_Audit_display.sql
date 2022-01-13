-- 2021-05-14T11:01:44.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579154, Description=NULL, Help=NULL, Name='Nutzer',Updated=TO_TIMESTAMP('2021-05-14 14:01:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645172
;

-- 2021-05-14T11:01:44.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579154) 
;

-- 2021-05-14T11:01:44.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645172
;

-- 2021-05-14T11:01:44.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645172)
;

