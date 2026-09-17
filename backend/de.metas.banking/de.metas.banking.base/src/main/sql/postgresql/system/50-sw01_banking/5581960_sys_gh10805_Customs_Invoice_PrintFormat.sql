
-- 2021-03-12T09:03:42.032Z
-- URL zum Konzept
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_Table_ID,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540006,'None',540111,102,541360,TO_TIMESTAMP('2021-03-12 10:03:42','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y','Customs Invoice',TO_TIMESTAMP('2021-03-12 10:03:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-12T09:03:46.860Z
-- URL zum Konzept
UPDATE AD_PrintFormat SET JasperProcess_ID=541141,Updated=TO_TIMESTAMP('2021-03-12 10:03:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540111
;

-- 2021-03-12T09:04:14.477Z
-- URL zum Konzept
UPDATE C_DocType SET AD_PrintFormat_ID=540111,Updated=TO_TIMESTAMP('2021-03-12 10:04:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540973
;
