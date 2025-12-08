-- 2024-02-16T14:30:28.943Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541702,'S',TO_TIMESTAMP('2024-02-16 16:30:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onSuccess.beep.frequency',TO_TIMESTAMP('2024-02-16 16:30:28','YYYY-MM-DD HH24:MI:SS'),100,'1000')
;

-- 2024-02-16T14:30:49.401Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541703,'S',TO_TIMESTAMP('2024-02-16 16:30:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onSuccess.beep.volume',TO_TIMESTAMP('2024-02-16 16:30:49','YYYY-MM-DD HH24:MI:SS'),100,'0.1')
;

-- 2024-02-16T14:31:02.762Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541704,'S',TO_TIMESTAMP('2024-02-16 16:31:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onSuccess.beep.durationMillis',TO_TIMESTAMP('2024-02-16 16:31:02','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2024-02-16T14:31:27.430Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541705,'S',TO_TIMESTAMP('2024-02-16 16:31:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onSuccess.vibrate.durationMillis',TO_TIMESTAMP('2024-02-16 16:31:27','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2024-02-16T14:31:42.095Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541706,'S',TO_TIMESTAMP('2024-02-16 16:31:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onError.beep.frequency',TO_TIMESTAMP('2024-02-16 16:31:41','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2024-02-16T14:32:00.237Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541707,'S',TO_TIMESTAMP('2024-02-16 16:32:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onError.beep.volume',TO_TIMESTAMP('2024-02-16 16:32:00','YYYY-MM-DD HH24:MI:SS'),100,'0.1')
;

-- 2024-02-16T14:32:12.851Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541708,'S',TO_TIMESTAMP('2024-02-16 16:32:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onError.beep.durationMillis',TO_TIMESTAMP('2024-02-16 16:32:11','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2024-02-16T14:32:24.030Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541709,'S',TO_TIMESTAMP('2024-02-16 16:32:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','barcodeScanner.onError.vibrate.durationMillis',TO_TIMESTAMP('2024-02-16 16:32:23','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2024-02-16T14:32:44.382Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onError.beep.durationMillis',Updated=TO_TIMESTAMP('2024-02-16 16:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541708
;

-- 2024-02-16T14:32:46.839Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onError.beep.frequency',Updated=TO_TIMESTAMP('2024-02-16 16:32:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541706
;

-- 2024-02-16T14:32:48.933Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onError.beep.volume',Updated=TO_TIMESTAMP('2024-02-16 16:32:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541707
;

-- 2024-02-16T14:32:51.827Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onError.vibrate.durationMillis',Updated=TO_TIMESTAMP('2024-02-16 16:32:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541709
;

-- 2024-02-16T14:32:54.153Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onSuccess.beep.durationMillis',Updated=TO_TIMESTAMP('2024-02-16 16:32:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541704
;

-- 2024-02-16T14:32:56.286Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onSuccess.beep.frequency',Updated=TO_TIMESTAMP('2024-02-16 16:32:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541702
;

-- 2024-02-16T14:32:59.457Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onSuccess.beep.volume',Updated=TO_TIMESTAMP('2024-02-16 16:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541703
;

-- 2024-02-16T14:33:02.940Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='mobileui.frontend.barcodeScanner.onSuccess.vibrate.durationMillis',Updated=TO_TIMESTAMP('2024-02-16 16:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541705
;

