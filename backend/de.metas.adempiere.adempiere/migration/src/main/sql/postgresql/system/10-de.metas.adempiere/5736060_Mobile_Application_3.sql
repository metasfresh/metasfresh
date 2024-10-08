-- 2024-10-07T15:39:00.983Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:39:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',540002,'Picking',TO_TIMESTAMP('2024-10-07 18:39:00','YYYY-MM-DD HH24:MI:SS'),100,'picking')
;

-- 2024-10-07T15:39:00.987Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540002 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-07T15:53:52.941Z
UPDATE Mobile_Application SET Name='Kommissionierung',Updated=TO_TIMESTAMP('2024-10-07 18:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Mobile_Application_ID=540002
;

-- 2024-10-07T15:53:52.944Z
UPDATE Mobile_Application_Trl trl SET Name='Kommissionierung'  WHERE Mobile_Application_ID=540002 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2024-10-07T15:54:10.741Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='Picking',Updated=TO_TIMESTAMP('2024-10-07 18:54:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540002
;

-- 2024-10-07T15:54:12.112Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540002
;

-- 2024-10-07T15:54:13.120Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540002
;

-- 2024-10-07T15:54:56.663Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:54:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',540003,'Produktion',TO_TIMESTAMP('2024-10-07 18:54:56','YYYY-MM-DD HH24:MI:SS'),100,'mfg')
;

-- 2024-10-07T15:54:56.666Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540003 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-07T15:55:06.116Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540003
;

-- 2024-10-07T15:55:07.341Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:55:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540003
;

-- 2024-10-07T15:55:14.058Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='Production',Updated=TO_TIMESTAMP('2024-10-07 18:55:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540003
;

-- 2024-10-07T15:56:13.202Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:56:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',540004,'Bereitstellung',TO_TIMESTAMP('2024-10-07 18:56:12','YYYY-MM-DD HH24:MI:SS'),100,'distribution')
;

-- 2024-10-07T15:56:13.204Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540004 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-07T15:56:17.054Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:56:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540004
;

-- 2024-10-07T15:56:17.777Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:56:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540004
;

-- 2024-10-07T15:56:24.191Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='Distribution',Updated=TO_TIMESTAMP('2024-10-07 18:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540004
;

-- 2024-10-07T15:56:50.440Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:56:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',540005,'HU Manager',TO_TIMESTAMP('2024-10-07 18:56:50','YYYY-MM-DD HH24:MI:SS'),100,'huManager')
;

-- 2024-10-07T15:56:50.445Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540005 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-07T15:56:55.863Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:56:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540005
;

-- 2024-10-07T15:56:56.657Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:56:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540005
;

-- 2024-10-07T15:56:57.664Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:56:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540005
;

-- 2024-10-07T15:57:49.092Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:57:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',540006,'Arbeitsplatz',TO_TIMESTAMP('2024-10-07 18:57:48','YYYY-MM-DD HH24:MI:SS'),100,'workplaceManager')
;

-- 2024-10-07T15:57:49.094Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540006 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-07T15:58:13.537Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:58:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',540007,'Arbeitsstation',TO_TIMESTAMP('2024-10-07 18:58:13','YYYY-MM-DD HH24:MI:SS'),100,'1000000')
;

-- 2024-10-07T15:58:13.540Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540007 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-07T15:58:19.419Z
UPDATE Mobile_Application SET Value='workstationManager',Updated=TO_TIMESTAMP('2024-10-07 18:58:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Mobile_Application_ID=540007
;

-- 2024-10-07T15:58:26.706Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:58:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540007
;

-- 2024-10-07T15:58:27.482Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540007
;

-- 2024-10-07T15:58:34.694Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='Workstation',Updated=TO_TIMESTAMP('2024-10-07 18:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540007
;

-- 2024-10-07T15:58:40.186Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND Mobile_Application_ID=540006
;

-- 2024-10-07T15:58:41.590Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-07 18:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND Mobile_Application_ID=540006
;

-- 2024-10-07T15:58:45.982Z
UPDATE Mobile_Application_Trl SET IsTranslated='Y', Name='Workplace',Updated=TO_TIMESTAMP('2024-10-07 18:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND Mobile_Application_ID=540006
;

-- 2024-10-07T15:59:03.460Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540008,'Scan',TO_TIMESTAMP('2024-10-07 18:59:03','YYYY-MM-DD HH24:MI:SS'),100,'scanAnything')
;

-- 2024-10-07T15:59:03.462Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540008 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-07T15:59:43.116Z
INSERT INTO Mobile_Application (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsShowInMainMenu,Mobile_Application_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-10-07 18:59:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',540009,'POS',TO_TIMESTAMP('2024-10-07 18:59:42','YYYY-MM-DD HH24:MI:SS'),100,'pos')
;

-- 2024-10-07T15:59:43.118Z
INSERT INTO Mobile_Application_Trl (AD_Language,Mobile_Application_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.Mobile_Application_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, Mobile_Application t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.Mobile_Application_ID=540009 AND NOT EXISTS (SELECT 1 FROM Mobile_Application_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.Mobile_Application_ID=t.Mobile_Application_ID)
;

-- 2024-10-08T04:19:52.571Z
UPDATE Mobile_Application SET IsShowInMainMenu='N',Updated=TO_TIMESTAMP('2024-10-08 07:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Mobile_Application_ID=540006
;

-- 2024-10-08T04:19:55.479Z
UPDATE Mobile_Application SET IsShowInMainMenu='N',Updated=TO_TIMESTAMP('2024-10-08 07:19:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Mobile_Application_ID=540007
;

-- 2024-10-08T04:19:56.930Z
UPDATE Mobile_Application SET IsShowInMainMenu='Y',Updated=TO_TIMESTAMP('2024-10-08 07:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE Mobile_Application_ID=540008
;

