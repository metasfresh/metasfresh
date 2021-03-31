-- 2021-03-11T17:26:25.702Z
update ad_table_process set ad_table_id=get_table_id('EDI_Desadv') where ad_table_process_id=540476;

-- 2021-03-11T20:52:15.840Z
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.edi.process.EDI_Desadv_GenerateSSCCLabels', Value='EDI_Desadv_GenerateSSCCLabels',Updated=TO_TIMESTAMP('2021-03-11 22:52:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540574
;

-- 2021-03-11T20:58:23.511Z
-- URL zum Konzept
UPDATE AD_Process SET Name='SSCC18-Label generieren',Updated=TO_TIMESTAMP('2021-03-11 22:58:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540574
;

-- 2021-03-11T21:00:10.985Z
-- URL zum Konzept
UPDATE AD_Process SET Name='SSCC18-Label estellen',Updated=TO_TIMESTAMP('2021-03-11 23:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540574
;

-- 2021-03-11T21:00:18.832Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='SSCC18-Label estellen',Updated=TO_TIMESTAMP('2021-03-11 23:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540574
;

-- 2021-03-11T21:00:40.315Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create SSCC18 Label',Updated=TO_TIMESTAMP('2021-03-11 23:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540574
;

-- 2021-03-11T21:00:49.240Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC18-Label estellen',Updated=TO_TIMESTAMP('2021-03-11 23:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=540574
;

-- 2021-03-11T21:00:53.865Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC18-Label estellen',Updated=TO_TIMESTAMP('2021-03-11 23:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Process_ID=540574
;

-- 2021-03-11T21:01:01.075Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='SSCC18-Label estellen',Updated=TO_TIMESTAMP('2021-03-11 23:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=540574
;

