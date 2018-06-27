-- 2018-06-27T11:43:26.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2018-06-27 11:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540446
;

-- 2018-06-27T11:43:28.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS unique_manufacture_bpartner
;

-- 2018-06-27T11:43:28.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX Unique_Manufacture_Bpartner ON M_BannedManufacturer (C_BPartner_ID,Manufacturer_ID) WHERE IsActive = 'Y'
;

