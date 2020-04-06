-- 2018-05-16T17:22:39.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='de.metas.handlingunits.DD_OrderLine_Enforce_M_HU_PI_Item_Product', Value='N',Updated=TO_TIMESTAMP('2018-05-16 17:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541210
;

-- 2018-05-16T17:28:02.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If this sys config is set on ''Y'', the DD_OrderLine will always have an M_HU_PI_Item_Product if fitting Packing Instructions are found in the db, even if the DD_Orderline was initially for CU only.
Leave it on ''N'' if this functionality is not needed.',Updated=TO_TIMESTAMP('2018-05-16 17:28:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541210
;

-- 2018-05-16T17:33:31.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If this sys config is set on ''Y'', the DD_OrderLine will always have an M_HU_PI_Item_Product if fitting Packing Instructions are found in the db, even if the DD_Orderline was initially for CUs only. This M_HU_PI_Item_Product setting leads to also setting a qtyTU and the qtyCU will change to the qty that fitts those instructions (example: if a box fits 10 CUs, the qtyCU of the DD_OrderLine will be 10, no matter what it was before).
Leave it on ''N'' if this functionality is not needed.',Updated=TO_TIMESTAMP('2018-05-16 17:33:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541210
;

