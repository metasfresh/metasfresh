-- Name: AD_Process (HU Label reports)
-- 2022-12-09T22:18:45.610Z
UPDATE AD_Val_Rule SET Name='AD_Process (HU Label reports)',Updated=TO_TIMESTAMP('2022-12-10 00:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540604
;

-- Name: AD_Process (HU Label reports)
-- 2022-12-09T22:19:54.995Z
UPDATE AD_Val_Rule SET Code='AD_Process.JasperReport IS NOT NULL AND (AD_Process.Value ilike ''%HU_Labels%'' OR AD_Process.Value=''M_HU_Report_QRCode'')',Updated=TO_TIMESTAMP('2022-12-10 00:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540604
;

-- Name: AD_Process (HU Label reports)
-- 2022-12-09T22:21:02.920Z
UPDATE AD_Val_Rule SET Code='(AD_Process.JasperReport IS NOT NULL AND AD_Process.Value ilike ''%HU_Labels%'') OR AD_Process.Value=''M_HU_Report_QRCode''',Updated=TO_TIMESTAMP('2022-12-10 00:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540604
;

