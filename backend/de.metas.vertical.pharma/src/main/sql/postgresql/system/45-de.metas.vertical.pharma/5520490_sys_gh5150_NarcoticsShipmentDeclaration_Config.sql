

-- 2019-04-25T11:29:42.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Shipment_Declaration_Config (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocumentLinesNumber,IsActive,IsOnlyNarcoticProducts,M_Shipment_Declaration_Config_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,540970,TO_TIMESTAMP('2019-04-25 11:29:42','YYYY-MM-DD HH24:MI:SS'),2188223,6,'Y','N',540000,'Narcotic Shipment Declaration Config',TO_TIMESTAMP('2019-04-25 11:29:42','YYYY-MM-DD HH24:MI:SS'),2188223)
;

-- 2019-04-25T11:29:58.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Shipment_Declaration_Config SET C_DocType_Correction_ID=540972,Updated=TO_TIMESTAMP('2019-04-25 11:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188223 WHERE M_Shipment_Declaration_Config_ID=540000
;

-- 2019-04-25T11:30:00.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Shipment_Declaration_Config SET IsOnlyNarcoticProducts='Y',Updated=TO_TIMESTAMP('2019-04-25 11:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2188223 WHERE M_Shipment_Declaration_Config_ID=540000
;
