-- 2020-09-07T08:25:01.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=578054, ColumnName='EdiInvoicRecipientGLN', Description='', FieldLength=255, Help=NULL, IsUpdateable='N', Name='EDI-ID des INVOIC-Empfängers',Updated=TO_TIMESTAMP('2020-09-07 10:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548360
;

-- 2020-09-07T08:25:01.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EDI-ID des INVOIC-Empfängers', Description='', Help=NULL WHERE AD_Column_ID=548360
;

-- 2020-09-07T08:25:01.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578054) 
;

