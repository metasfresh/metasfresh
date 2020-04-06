-- 2018-03-29T11:59:50.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=351, Description='Zeigt an, ob dieser Beleg eine Freigabe braucht', Help='Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann', Name='Freigegeben',Updated=TO_TIMESTAMP('2018-03-29 11:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=562716
;

-- 2018-03-29T11:59:50.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351) 
;

