

-- 03.03.2017 22:01
-- URL zum Konzept
UPDATE AD_Process_Para SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2017-03-03 22:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541161
;

-- 03.03.2017 22:01
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action = ''CU_To_NewTUs'' | @Action = ''TU_To_NewTUs'' | @Action = ''TU_To_NewLU''',Updated=TO_TIMESTAMP('2017-03-03 22:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541152
;

-- 03.03.2017 22:01
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewTUs'' | @Action = ''TU_To_NewTUs'' | @Action = ''TU_To_NewLU''',Updated=TO_TIMESTAMP('2017-03-03 22:01:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541152
;

-- 03.03.2017 22:01
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_ExistingTU'' | @Action@=''CU_To_NewCU'' | @Action@=''CU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-03 22:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

-- 03.03.2017 22:02
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_NewTUs'' | @Action@ = ''TU_To_NewTUs'' | @Action@ = ''TU_To_NewLU''',Updated=TO_TIMESTAMP('2017-03-03 22:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541152
;

-- 03.03.2017 22:02
-- URL zum Konzept
UPDATE AD_Process_Para SET DisplayLogic='@Action@ = ''CU_To_ExistingTU'' | @Action@ = ''CU_To_NewCU'' | @Action@ = ''CU_To_NewTUs''',Updated=TO_TIMESTAMP('2017-03-03 22:02:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541154
;

--
-- there were also mistakes in the trls
--
-- 03.03.2017 22:05
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-03 22:05:52','YYYY-MM-DD HH24:MI:SS'),Name='Menge TU' WHERE AD_Process_Para_ID=541155 AND AD_Language='en_US'
;

-- 03.03.2017 22:05
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-03 22:05:59','YYYY-MM-DD HH24:MI:SS'),Name='Menge TU' WHERE AD_Process_Para_ID=541155 AND AD_Language='fr_CH'
;

-- 03.03.2017 22:06
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-03 22:06:04','YYYY-MM-DD HH24:MI:SS'),Name='Menge TU' WHERE AD_Process_Para_ID=541155 AND AD_Language='it_CH'
;

