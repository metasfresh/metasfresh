-- 2024-10-03T07:28:39.428Z
INSERT INTO C_Doc_Outbound_Config (AD_Client_ID,AD_Org_ID,AD_Table_ID,C_Doc_Outbound_Config_ID,Created,CreatedBy,IsActive,IsDirectEnqueue,IsDirectProcessQueueItem,Updated,UpdatedBy) VALUES (1000000,1000000,542434,540020,TO_TIMESTAMP('2024-10-03 10:28:39','YYYY-MM-DD HH24:MI:SS'),540116,'Y','Y','N',TO_TIMESTAMP('2024-10-03 10:28:39','YYYY-MM-DD HH24:MI:SS'),540116)
;

-- 2024-10-03T07:28:54.084Z
UPDATE C_Doc_Outbound_Config SET IsDirectProcessQueueItem='Y',Updated=TO_TIMESTAMP('2024-10-03 10:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE C_Doc_Outbound_Config_ID=540020
;

-- 2024-10-03T07:29:00.725Z
UPDATE C_Doc_Outbound_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2024-10-03 10:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE C_Doc_Outbound_Config_ID=540020
;

-- 2024-10-03T08:43:18.055Z
UPDATE C_Doc_Outbound_Config SET AD_PrintFormat_ID=540137,Updated=TO_TIMESTAMP('2024-10-03 11:43:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE C_Doc_Outbound_Config_ID=540020
;

-- update C_Doc_Outbound_Config set IsDirectProcessQueueItem='N' WHERE C_Doc_Outbound_Config_ID=540020;

