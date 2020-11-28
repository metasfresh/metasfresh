
-- AD_AttachmentEntry_for_C_DataImport_ID
-- 2018-10-16T08:38:49.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_AttachmentEntry_ID IN (
	select r.AD_AttachmentEntry_ID 
	from AD_Attachment_MultiRef r 
	where r.AD_Table_ID=get_Table_ID(''C_DataImport'') and r.Record_ID=@C_DataImport_ID@ )',Updated=TO_TIMESTAMP('2018-10-16 08:38:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540389
;

-- ESR_Import_AD_AttachmentEntry
-- 2018-10-16T08:43:12.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_AttachmentEntry_ID IN (
	select r.AD_AttachmentEntry_ID 
	from AD_Attachment_MultiRef r 
	where r.AD_Table_ID=get_Table_ID(''ESR_Import'') and r.Record_ID=@@ESR_Import_ID@ )',Updated=TO_TIMESTAMP('2018-10-16 08:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540370
;

