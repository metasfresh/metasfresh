-- 2022-11-15T08:19:47.409Z
-- URL zum Konzept
INSERT INTO API_Audit_Config (AD_Client_ID,AD_Org_ID,API_Audit_Config_ID,Created,CreatedBy,IsActive,IsBypassAudit,IsForceProcessedAsync,IsSynchronousAuditLoggingEnabled,IsWrapApiResponse,KeepRequestBodyDays,KeepRequestDays,KeepResponseBodyDays,KeepResponseDays,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,540007,TO_TIMESTAMP('2022-11-15 10:19:47','YYYY-MM-DD HH24:MI:SS'),540116,'Y','N','N','Y','Y',0,0,0,0,10000,TO_TIMESTAMP('2022-11-15 10:19:47','YYYY-MM-DD HH24:MI:SS'),540116)
;

-- 2022-11-15T08:19:52.822Z
-- URL zum Konzept
UPDATE API_Audit_Config SET SeqNo=300,Updated=TO_TIMESTAMP('2022-11-15 10:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE API_Audit_Config_ID=540007
;

-- 2022-11-15T08:20:18.025Z
-- URL zum Konzept
UPDATE API_Audit_Config SET PathPrefix='**/images/**',Updated=TO_TIMESTAMP('2022-11-15 10:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE API_Audit_Config_ID=540007
;

-- 2022-11-15T08:20:49.337Z
-- URL zum Konzept
UPDATE API_Audit_Config SET IsBypassAudit='Y',Updated=TO_TIMESTAMP('2022-11-15 10:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE API_Audit_Config_ID=540007
;

-- 2022-11-15T08:21:19.964Z
-- URL zum Konzept
UPDATE API_Audit_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2022-11-15 10:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE API_Audit_Config_ID=540007
;

-- 2022-11-15T08:21:30.165Z
-- URL zum Konzept
UPDATE API_Audit_Config SET IsSynchronousAuditLoggingEnabled='N', IsWrapApiResponse='N',Updated=TO_TIMESTAMP('2022-11-15 10:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE API_Audit_Config_ID=540007
;

