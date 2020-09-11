-- 2020-06-22T07:52:44.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2020-06-22 09:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53576
;

-- 2020-06-22T07:52:44.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_bomline','PP_Order_BOM_ID','NUMERIC(10)',null,null)
;

-- 2020-06-22T07:52:44.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_bomline','PP_Order_BOM_ID',null,'NULL',null)
;
---


-- 2020-06-22T08:13:28.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The warehouse''s default value shall be the PP_Order''s warehouse',Updated=TO_TIMESTAMP('2020-06-22 10:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53574
;

-- 2020-06-22T08:19:34.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540505,'select PP_Order_BOM_ID from PP_Order o where o.PP_Order_ID=@PP_Order_ID/-1@',TO_TIMESTAMP('2020-06-22 10:19:34','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','PP_Order_BOM_ID of parent PP_Order','S',TO_TIMESTAMP('2020-06-22 10:19:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-22T08:20:29.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=540505, IsAutoApplyValidationRule='Y', IsMandatory='Y', IsUpdateable='N', TechnicalNote='I don''t see why it should be mandatory; we can add PP_Order_BOMLines also for components that are not in the respective BOM
But because it was mandatory all the time, i leave it that way and add a default value, to
',Updated=TO_TIMESTAMP('2020-06-22 10:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53576
;

-- 2020-06-22T08:21:39.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540506,'select M_warehouse_ID from PP_Order o where o.PP_Order_ID=@PP_Order_ID/-1@',TO_TIMESTAMP('2020-06-22 10:21:38','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','M_Warehouse_ID of parent PP_Order','S',TO_TIMESTAMP('2020-06-22 10:21:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-22T08:21:57.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=540506, DefaultValue='', IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2020-06-22 10:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53574
;

-- 2020-06-22T08:23:00.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', TechnicalNote='I don''t see why it should be mandatory; we can add PP_Order_BOMLines also for components that are not in the respective BOM
But because it was mandatory all the time, I leave it that way and make sure via AutoApplyValidationRule that it''s set.
',Updated=TO_TIMESTAMP('2020-06-22 10:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53576
;

-- 2020-06-22T08:23:02.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_bomline','PP_Order_BOM_ID','NUMERIC(10)',null,null)
;

-- 2020-06-22T08:23:02.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_bomline','PP_Order_BOM_ID',null,'NOT NULL',null)
;

-- 2020-06-22T08:51:45.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='PP_Order_BOM_ID=(select PP_Order_BOM_ID from PP_Order o where o.PP_Order_ID=@PP_Order_ID/-1@)',Updated=TO_TIMESTAMP('2020-06-22 10:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540505
;

-- 2020-06-22T08:52:17.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Warehouse_ID=(select M_Warehouse_ID from PP_Order o where o.PP_Order_ID=@PP_Order_ID/-1@)',Updated=TO_TIMESTAMP('2020-06-22 10:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540506
;

