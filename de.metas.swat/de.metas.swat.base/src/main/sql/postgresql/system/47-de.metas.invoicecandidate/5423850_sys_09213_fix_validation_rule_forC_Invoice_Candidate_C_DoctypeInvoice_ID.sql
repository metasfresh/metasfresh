

-- 17.08.2015 09:37
-- URL zum Konzept
UPDATE AD_Val_Rule SET Description='Document Type AR/AP Invoice and Credit Memos, filtered by @IsSOTrx@', Name='C_DocType AR/AP Invoices and Credit Memos IsSOTrx-Filter',Updated=TO_TIMESTAMP('2015-08-17 09:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=124
;

-- 17.08.2015 09:38
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540304,'C_DocType.DocBaseType IN (''ARI'', ''API'',''ARC'',''APC'')',TO_TIMESTAMP('2015-08-17 09:38:48','YYYY-MM-DD HH24:MI:SS'),100,'Document Type AR/AP Invoice and Credit Memos, *not* filtered by @IsSOTrx@','de.metas.invoicecandidate','Y','C_DocType AR/AP Invoices and Credit Memos','S',TO_TIMESTAMP('2015-08-17 09:38:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.08.2015 09:39
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540304,Updated=TO_TIMESTAMP('2015-08-17 09:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551286
;

