-- 01.02.2016 11:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2016-02-01 11:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540267
;

-- 01.02.2016 11:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS m_pickingslot_hu_uq
;

-- 01.02.2016 11:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_PickingSlot_HU_UQ ON M_PickingSlot_HU (M_PickingSlot_ID,M_HU_ID)
;

