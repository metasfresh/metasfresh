-- 2020-06-22T08:51:45.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='PP_Order_BOM_ID=(select PP_Order_BOM_ID from PP_Order o where o.PP_Order_ID=@PP_Order_ID/-1@)',Updated=TO_TIMESTAMP('2020-06-22 10:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540505
;

-- 2020-06-22T08:52:17.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Warehouse_ID=(select M_Warehouse_ID from PP_Order o where o.PP_Order_ID=@PP_Order_ID/-1@)',Updated=TO_TIMESTAMP('2020-06-22 10:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540506
;

-- 2020-06-22T18:33:12.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@RoutingType/L@ ! L', ValueMin='0',Updated=TO_TIMESTAMP('2020-06-22 20:33:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551506
;

-- 2020-06-22T18:33:25.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@RoutingType/''L''@!''L''',Updated=TO_TIMESTAMP('2020-06-22 20:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551506
;

-- 2020-06-22T18:34:15.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RoutingType/''P''@=''L''',Updated=TO_TIMESTAMP('2020-06-22 20:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555185
;

-- 2020-06-22T18:34:23.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RoutingType/''L''@=''P''',Updated=TO_TIMESTAMP('2020-06-22 20:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555109
;

-- 2020-06-22T18:34:36.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@RoutingType/''L''@=''P''',Updated=TO_TIMESTAMP('2020-06-22 20:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555108
;

-- 2020-06-22T18:35:01.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='AD_Printer',Updated=TO_TIMESTAMP('2020-06-22 20:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540168
;

-- 2020-06-22T18:35:18.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2020-06-22 20:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540282
;

-- 2020-06-22T18:39:43.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ValueMin='1',Updated=TO_TIMESTAMP('2020-06-22 20:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551506
;

