-- 2024-03-12T07:44:28.695Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541713,'S',TO_TIMESTAMP('2024-03-12 09:44:28','YYYY-MM-DD HH24:MI:SS'),100,'When adding a manual compensation line to an order, where to position it:
B - Before generated compensation lines
L - Last (after all generated compesantion lines)','D','Y','ManualCompensationLinePosition',TO_TIMESTAMP('2024-03-12 09:44:28','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-03-12T07:45:00.726Z
UPDATE AD_SysConfig SET ConfigurationLevel='C',Updated=TO_TIMESTAMP('2024-03-12 09:45:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541713
;

