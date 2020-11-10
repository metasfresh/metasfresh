
-- 2020-09-04T11:28:08.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550299
;

-- 2020-09-04T11:28:55.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616969
;

-- 2020-09-04T11:28:55.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=616969
;

-- 2020-09-04T11:28:55.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=616969
;

-- 2020-09-04T11:29:03.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=571149
;

-- 2020-09-04T11:29:03.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=571149
;

-- 2020-09-04T16:10:13.604Z
-- URL zum Konzept
UPDATE AD_Column SET MandatoryLogic='@IsEdiInvoicRecipient@=Y', ReadOnlyLogic='@IsEdiInvoicRecipient@=N',Updated=TO_TIMESTAMP('2020-09-04 18:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571151
;


-- 2020-09-04T16:25:19.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select x12de355 from C_UOM where C_UOM.C_UOM_ID=EDI_DesadvLine.C_UOM_Invoice_ID)',Updated=TO_TIMESTAMP('2020-09-04 18:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571147
;

