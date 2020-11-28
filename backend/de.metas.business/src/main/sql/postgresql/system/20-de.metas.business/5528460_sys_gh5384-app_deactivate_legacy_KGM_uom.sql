-- 2019-08-02T11:22:05.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET Description='Please don''t use this UOM (anymore). Instead, please use the "KGM" UOM with ID=540017', IsActive='N',Updated=TO_TIMESTAMP('2019-08-02 13:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=1000000
;

-- 2019-08-02T11:22:05.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description='Please don''t use this UOM (anymore). Instead, please use the "KGM" UOM with ID=540017', Name='Kilogramm', UOMSymbol='kg', IsTranslated='Y' WHERE C_UOM_ID=1000000
;

