--
-- undisplay the processing and sepa xml buttons
--
-- 2017-12-04T15:18:37.935
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-12-04 15:18:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4414
;
-- 2017-12-04T15:41:55.115
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-12-04 15:41:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554509
;

--
-- rename the process class to fit our best-practice
--
-- 2017-12-04T15:43:21.288
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.payment.sepa.process.C_PaySelection_SEPA_XmlExport', Value='C_PaySelection_SEPA_XmlExport',Updated=TO_TIMESTAMP('2017-12-04 15:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540461
;

--
-- AD_Table_Process insert the process into AD_Table_Process
--
-- 2017-12-04T15:43:54.747
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540461,426,TO_TIMESTAMP('2017-12-04 15:43:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sepa','Y',TO_TIMESTAMP('2017-12-04 15:43:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;
-- 2017-12-04T15:44:00.681
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_QuickAction='Y',Updated=TO_TIMESTAMP('2017-12-04 15:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540461 AND AD_Table_ID=426
;

--
-- deactivate C_PaySelection_ReActivate AD_Table_Process record
--
-- 2017-12-04T16:04:59.613
-- URL zum Konzept
UPDATE AD_Table_Process SET IsActive='N',Updated=TO_TIMESTAMP('2017-12-04 16:04:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540650 AND AD_Table_ID=426
;

