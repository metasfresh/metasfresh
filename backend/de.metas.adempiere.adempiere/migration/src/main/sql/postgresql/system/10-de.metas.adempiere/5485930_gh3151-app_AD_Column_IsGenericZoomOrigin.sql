
--
-- M_Product.M_Product_ID is referenced by too many tables, and some of them (like M_HU) have top level tabs
-- See https://github.com/metasfresh/metasfresh/issues/3151#issuecomment-366716544
--
-- 2018-02-19T14:13:28.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2018-02-19 14:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1402
;

--
-- change the enity type of AD_Column.IsGenericZoomOrigin from de.metas.swat to Dictionary
--
-- 2018-02-19T15:12:12.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2018-02-19 15:12:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546472
;

-- 2018-02-19T15:12:28.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2018-02-19 15:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541640
;

