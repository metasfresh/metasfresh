-- -- 2022-06-02T12:08:55.730Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Column SET AD_Reference_Value_ID=541584, IsExcludeFromZoomTargets='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-06-02 14:08:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577844
-- ;
-- 
-- -- 2022-06-02T12:09:10.348Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Column SET IsAutocomplete='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-06-02 14:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577844
-- ;
-- 
-- -- 2022-06-02T12:28:38.718Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Table SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2022-06-02 14:28:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541911
-- ;
------------------
------------------
------------------



-- unrelated - enable changelog history for PP_Product_BOMVersions
-- 2022-06-02T12:28:49.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2022-06-02 14:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541911
;


-- the actual fix - set the AD_Table of the PP_Product_BOMVersions ref-table to actually be PP_Product_BOMVersions (was PP_Product_BOM).
-- 2022-06-02T12:34:18.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=577840, AD_Table_ID=541911, EntityType='D',Updated=TO_TIMESTAMP('2022-06-02 14:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541584
;

-- 2022-06-02T12:34:28.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2022-06-02 14:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541584
;

