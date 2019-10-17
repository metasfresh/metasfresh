-- 2019-06-13T14:44:20.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-06-13 14:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6581
;

-- 2019-06-13T16:36:54.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=5350
;

-- 2019-06-13T16:36:54.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5350
;

-- 2019-06-13T16:36:54.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=5350
;

-- 2019-06-13T16:40:00.619
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=6716
;

-- 2019-06-13T16:40:00.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=6716
;

