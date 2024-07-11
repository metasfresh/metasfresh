-- 2022-02-11T07:14:34.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-02-11 09:14:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584980
;

-- 2022-02-11T07:15:44.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET TechnicalNote='Hooked in M_HU_Report. When called, it expects the M_HU_IDs in T_Selection. Will generate the QR codes for them and will call the actual Jasper Report which prints the QR labels.',Updated=TO_TIMESTAMP('2022-02-11 09:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584980
;

-- 2022-02-11T07:16:53.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-02-11 09:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584977
;

-- 2022-02-11T07:17:44.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='QR Code (jasper)', TechnicalNote='Takes the QR Codes as JSON string param and generates the PDF for them.', Value='M_HU_QRCode_PDF',Updated=TO_TIMESTAMP('2022-02-11 09:17:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584977
;

