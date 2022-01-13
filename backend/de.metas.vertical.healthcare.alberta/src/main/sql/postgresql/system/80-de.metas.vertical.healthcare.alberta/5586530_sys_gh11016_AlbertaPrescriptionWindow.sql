-- 2021-04-26T07:32:25.261Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541296,TO_TIMESTAMP('2021-04-26 09:32:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Rezepttyp',TO_TIMESTAMP('2021-04-26 09:32:25','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-04-26T07:32:25.263Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541296 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-04-26T07:32:33.920Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:32:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541296
;

-- 2021-04-26T07:32:44.489Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541296
;

-- 2021-04-26T07:33:01.610Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Prescription Type',Updated=TO_TIMESTAMP('2021-04-26 09:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541296
;

-- 2021-04-26T07:33:06.490Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:33:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541296
;

-- 2021-04-26T07:36:01.307Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541296,542362,TO_TIMESTAMP('2021-04-26 09:36:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Arzneimittel',TO_TIMESTAMP('2021-04-26 09:36:01','YYYY-MM-DD HH24:MI:SS'),100,'P','Arzneimittel')
;

-- 2021-04-26T07:36:01.309Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542362 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:36:38.595Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541296,542363,TO_TIMESTAMP('2021-04-26 09:36:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verbandstoffe',TO_TIMESTAMP('2021-04-26 09:36:38','YYYY-MM-DD HH24:MI:SS'),100,'BM','Verbandstoffe')
;

-- 2021-04-26T07:36:38.597Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542363 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:37:13.667Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541296,542364,TO_TIMESTAMP('2021-04-26 09:37:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','BtM-Rezept',TO_TIMESTAMP('2021-04-26 09:37:13','YYYY-MM-DD HH24:MI:SS'),100,'NP','BtM-Rezept')
;

-- 2021-04-26T07:37:13.669Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542364 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:37:41.097Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541296,542365,TO_TIMESTAMP('2021-04-26 09:37:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pflegehilfsmittel',TO_TIMESTAMP('2021-04-26 09:37:41','YYYY-MM-DD HH24:MI:SS'),100,'NA','Pflegehilfsmittel')
;

-- 2021-04-26T07:37:41.099Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542365 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:38:06.682Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541296,542366,TO_TIMESTAMP('2021-04-26 09:38:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hilfsmittel zum Verbrauch bestimmt',TO_TIMESTAMP('2021-04-26 09:38:06','YYYY-MM-DD HH24:MI:SS'),100,'AC','Hilfsmittel zum Verbrauch bestimmt')
;

-- 2021-04-26T07:38:06.683Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542366 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:38:32.316Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541296,542367,TO_TIMESTAMP('2021-04-26 09:38:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hilfsmittel zum Gebrauch bestimmt',TO_TIMESTAMP('2021-04-26 09:38:32','YYYY-MM-DD HH24:MI:SS'),100,'AU','Hilfsmittel zum Gebrauch bestimmt')
;

-- 2021-04-26T07:38:32.319Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542367 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:39:23.710Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542362
;

-- 2021-04-26T07:40:22.004Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Pharmaceuticals', IsTranslated='Y', Name='Pharmaceuticals',Updated=TO_TIMESTAMP('2021-04-26 09:40:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542362
;

-- 2021-04-26T07:40:28.673Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:40:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542362
;

-- 2021-04-26T07:40:58.930Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:40:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542363
;

-- 2021-04-26T07:41:00.538Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:41:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542363
;

-- 2021-04-26T07:41:38.639Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Bandaging materials',Updated=TO_TIMESTAMP('2021-04-26 09:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542363
;

-- 2021-04-26T07:41:54.355Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542364
;

-- 2021-04-26T07:41:56.030Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542364
;

-- 2021-04-26T07:42:20.257Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Narcotics Prescription',Updated=TO_TIMESTAMP('2021-04-26 09:42:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542364
;

-- 2021-04-26T07:42:48.454Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nursing aids',Updated=TO_TIMESTAMP('2021-04-26 09:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542365
;

-- 2021-04-26T07:42:52.479Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542365
;

-- 2021-04-26T07:42:53.606Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:42:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542365
;

-- 2021-04-26T07:43:08.943Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542366
;

-- 2021-04-26T07:43:10.590Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542366
;

-- 2021-04-26T07:43:35.734Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Aids for consumption',Updated=TO_TIMESTAMP('2021-04-26 09:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542366
;

-- 2021-04-26T07:43:58.398Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Aids for use',Updated=TO_TIMESTAMP('2021-04-26 09:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542367
;

-- 2021-04-26T07:44:02.603Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542367
;

-- 2021-04-26T07:44:03.974Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:44:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542367
;

-- 2021-04-26T07:45:42.807Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541297,TO_TIMESTAMP('2021-04-26 09:45:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Accounting months',TO_TIMESTAMP('2021-04-26 09:45:42','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-04-26T07:45:42.810Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541297 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-04-26T07:46:48.915Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Versorgungs-/Abrechnungsmonate',Updated=TO_TIMESTAMP('2021-04-26 09:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541297
;

-- 2021-04-26T07:46:56.363Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='Versorgungs-/Abrechnungsmonate',Updated=TO_TIMESTAMP('2021-04-26 09:46:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541297
;

-- 2021-04-26T07:47:02.767Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Versorgungs-/Abrechnungsmonate',Updated=TO_TIMESTAMP('2021-04-26 09:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541297
;

-- 2021-04-26T07:47:55.461Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542368,TO_TIMESTAMP('2021-04-26 09:47:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Januar',TO_TIMESTAMP('2021-04-26 09:47:55','YYYY-MM-DD HH24:MI:SS'),100,'Jan','Januar')
;

-- 2021-04-26T07:47:55.463Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542368 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:48:02.558Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542368
;

-- 2021-04-26T07:48:04.052Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:48:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542368
;

-- 2021-04-26T07:48:15.678Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='January',Updated=TO_TIMESTAMP('2021-04-26 09:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542368
;

-- 2021-04-26T07:48:40.067Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542369,TO_TIMESTAMP('2021-04-26 09:48:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Februar',TO_TIMESTAMP('2021-04-26 09:48:39','YYYY-MM-DD HH24:MI:SS'),100,'Feb','Februar')
;

-- 2021-04-26T07:48:40.069Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542369 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:48:54.471Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='February',Updated=TO_TIMESTAMP('2021-04-26 09:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542369
;

-- 2021-04-26T07:48:57.064Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542369
;

-- 2021-04-26T07:48:58.651Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542369
;

-- 2021-04-26T07:49:54.449Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542370,TO_TIMESTAMP('2021-04-26 09:49:54','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','März',TO_TIMESTAMP('2021-04-26 09:49:54','YYYY-MM-DD HH24:MI:SS'),100,'Mar','März')
;

-- 2021-04-26T07:49:54.451Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542370 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:49:59.698Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542370
;

-- 2021-04-26T07:50:01.265Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542370
;

-- 2021-04-26T07:50:11.328Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='March',Updated=TO_TIMESTAMP('2021-04-26 09:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542370
;

-- 2021-04-26T07:50:48.213Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542371,TO_TIMESTAMP('2021-04-26 09:50:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','April',TO_TIMESTAMP('2021-04-26 09:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Apr','April')
;

-- 2021-04-26T07:50:48.213Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542371 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:50:52.348Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='APR',Updated=TO_TIMESTAMP('2021-04-26 09:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542371
;

-- 2021-04-26T07:50:57.923Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:50:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542371
;

-- 2021-04-26T07:51:01.110Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542371
;

-- 2021-04-26T07:51:05.115Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:51:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542371
;

-- 2021-04-26T07:51:16.399Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='MAR',Updated=TO_TIMESTAMP('2021-04-26 09:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542370
;

-- 2021-04-26T07:51:28.934Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='FEB',Updated=TO_TIMESTAMP('2021-04-26 09:51:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542369
;

-- 2021-04-26T07:51:36.344Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='JAN',Updated=TO_TIMESTAMP('2021-04-26 09:51:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542368
;

-- 2021-04-26T07:52:08.313Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542372,TO_TIMESTAMP('2021-04-26 09:52:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mai',TO_TIMESTAMP('2021-04-26 09:52:08','YYYY-MM-DD HH24:MI:SS'),100,'MAY','Mai')
;

-- 2021-04-26T07:52:08.314Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542372 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:52:16.040Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='May',Updated=TO_TIMESTAMP('2021-04-26 09:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542372
;

-- 2021-04-26T07:52:17.194Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542372
;

-- 2021-04-26T07:52:18.747Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:52:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542372
;

-- 2021-04-26T07:52:43.299Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542373,TO_TIMESTAMP('2021-04-26 09:52:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Juni',TO_TIMESTAMP('2021-04-26 09:52:43','YYYY-MM-DD HH24:MI:SS'),100,'JUN','Juni')
;

-- 2021-04-26T07:52:43.301Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542373 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:52:45.974Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:52:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542373
;

-- 2021-04-26T07:52:47.859Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542373
;

-- 2021-04-26T07:52:55.454Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='June',Updated=TO_TIMESTAMP('2021-04-26 09:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542373
;

-- 2021-04-26T07:53:26.361Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542374,TO_TIMESTAMP('2021-04-26 09:53:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Juli',TO_TIMESTAMP('2021-04-26 09:53:26','YYYY-MM-DD HH24:MI:SS'),100,'JUL','Juli')
;

-- 2021-04-26T07:53:26.361Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542374 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:53:30.071Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:53:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542374
;

-- 2021-04-26T07:53:31.445Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542374
;

-- 2021-04-26T07:53:39.660Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='July',Updated=TO_TIMESTAMP('2021-04-26 09:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542374
;

-- 2021-04-26T07:54:14.055Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542375,TO_TIMESTAMP('2021-04-26 09:54:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Aufgust',TO_TIMESTAMP('2021-04-26 09:54:13','YYYY-MM-DD HH24:MI:SS'),100,'AUG','Aufgust')
;

-- 2021-04-26T07:54:14.057Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542375 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:54:17.442Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542375
;

-- 2021-04-26T07:54:19.016Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:54:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542375
;

-- 2021-04-26T07:54:28.797Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='August',Updated=TO_TIMESTAMP('2021-04-26 09:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542375
;

-- 2021-04-26T07:54:56.154Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542376,TO_TIMESTAMP('2021-04-26 09:54:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','September',TO_TIMESTAMP('2021-04-26 09:54:56','YYYY-MM-DD HH24:MI:SS'),100,'SEP','September')
;

-- 2021-04-26T07:54:56.155Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542376 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:55:01.638Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:55:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542376
;

-- 2021-04-26T07:55:03.060Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:55:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542376
;

-- 2021-04-26T07:55:06.977Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542376
;

-- 2021-04-26T07:55:31.948Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542377,TO_TIMESTAMP('2021-04-26 09:55:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Oktober',TO_TIMESTAMP('2021-04-26 09:55:31','YYYY-MM-DD HH24:MI:SS'),100,'OCT','Oktober')
;

-- 2021-04-26T07:55:31.948Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542377 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:55:34.469Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542377
;

-- 2021-04-26T07:55:35.731Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:55:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542377
;

-- 2021-04-26T07:55:44.320Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='October',Updated=TO_TIMESTAMP('2021-04-26 09:55:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542377
;

-- 2021-04-26T07:56:19.614Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542378,TO_TIMESTAMP('2021-04-26 09:56:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','November',TO_TIMESTAMP('2021-04-26 09:56:19','YYYY-MM-DD HH24:MI:SS'),100,'NOV','November')
;

-- 2021-04-26T07:56:19.616Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542378 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:56:24.030Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542378
;

-- 2021-04-26T07:56:24.674Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542378
;

-- 2021-04-26T07:56:28.860Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542378
;

-- 2021-04-26T07:56:56.303Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541297,542379,TO_TIMESTAMP('2021-04-26 09:56:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dezember',TO_TIMESTAMP('2021-04-26 09:56:56','YYYY-MM-DD HH24:MI:SS'),100,'DEC','Dezember')
;

-- 2021-04-26T07:56:56.305Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542379 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T07:57:00.569Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542379
;

-- 2021-04-26T07:57:01.742Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 09:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542379
;

-- 2021-04-26T07:57:13.326Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='December',Updated=TO_TIMESTAMP('2021-04-26 09:57:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542379
;

-- 2021-04-26T08:03:45.067Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579037,0,TO_TIMESTAMP('2021-04-26 10:03:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Alberta_PrescriptionRequest','Alberta_PrescriptionRequest',TO_TIMESTAMP('2021-04-26 10:03:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:03:45.070Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579037 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:05:27.491Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alberta_Rezeptanforderung', PrintName='Alberta_Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 10:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579037 AND AD_Language='de_CH'
;

-- 2021-04-26T08:05:27.513Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579037,'de_CH') 
;

-- 2021-04-26T08:05:42.991Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alberta_Rezeptanforderung', PrintName='Alberta_Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 10:05:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579037 AND AD_Language='de_DE'
;

-- 2021-04-26T08:05:42.992Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579037,'de_DE') 
;

-- 2021-04-26T08:05:42.999Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579037,'de_DE') 
;

-- 2021-04-26T08:05:43Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Alberta_Rezeptanforderung', Description=NULL, Help=NULL WHERE AD_Element_ID=579037
;

-- 2021-04-26T08:05:43Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Alberta_Rezeptanforderung', Description=NULL, Help=NULL WHERE AD_Element_ID=579037 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T08:05:43Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Alberta_Rezeptanforderung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579037) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579037)
;

-- 2021-04-26T08:05:43.008Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta_Rezeptanforderung', Name='Alberta_Rezeptanforderung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579037)
;

-- 2021-04-26T08:05:43.008Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Alberta_Rezeptanforderung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579037
;

-- 2021-04-26T08:05:43.009Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Alberta_Rezeptanforderung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579037
;

-- 2021-04-26T08:05:43.010Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alberta_Rezeptanforderung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579037
;

-- 2021-04-26T08:05:51.468Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alberta_Rezeptanforderung', PrintName='Alberta_Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 10:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579037 AND AD_Language='nl_NL'
;

-- 2021-04-26T08:05:51.471Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579037,'nl_NL') 
;

-- 2021-04-26T08:08:07.465Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541622,'N',TO_TIMESTAMP('2021-04-26 10:08:07','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Alberta Prescription Request','NP','L','Alberta_PrescriptionRequest','DTI',TO_TIMESTAMP('2021-04-26 10:08:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:08:07.468Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541622 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-04-26T08:08:07.550Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555374,TO_TIMESTAMP('2021-04-26 10:08:07','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table Alberta_PrescriptionRequest',1,'Y','N','Y','Y','Alberta_PrescriptionRequest','N',1000000,TO_TIMESTAMP('2021-04-26 10:08:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:08:07.567Z
-- URL zum Konzept
CREATE SEQUENCE ALBERTA_PRESCRIPTIONREQUEST_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-04-26T08:08:38.664Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573548,102,0,19,541622,'AD_Client_ID',TO_TIMESTAMP('2021-04-26 10:08:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-04-26 10:08:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:38.665Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573548 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:38.665Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-04-26T08:08:39.089Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573549,113,0,30,541622,'AD_Org_ID',TO_TIMESTAMP('2021-04-26 10:08:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-04-26 10:08:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:39.090Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573549 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:39.090Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-04-26T08:08:39.489Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573550,245,0,16,541622,'Created',TO_TIMESTAMP('2021-04-26 10:08:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-04-26 10:08:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:39.489Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573550 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:39.490Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-04-26T08:08:39.863Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573551,246,0,18,110,541622,'CreatedBy',TO_TIMESTAMP('2021-04-26 10:08:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-04-26 10:08:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:39.864Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573551 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:39.865Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-04-26T08:08:40.232Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573552,348,0,20,541622,'IsActive',TO_TIMESTAMP('2021-04-26 10:08:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-04-26 10:08:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:40.233Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573552 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:40.234Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-04-26T08:08:40.650Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573553,607,0,16,541622,'Updated',TO_TIMESTAMP('2021-04-26 10:08:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-04-26 10:08:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:40.651Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573553 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:40.652Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-04-26T08:08:40.997Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573554,608,0,18,110,541622,'UpdatedBy',TO_TIMESTAMP('2021-04-26 10:08:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-04-26 10:08:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:40.999Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573554 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:41Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-04-26T08:08:41.395Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579038,0,'Alberta_PrescriptionRequest_ID',TO_TIMESTAMP('2021-04-26 10:08:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Alberta Prescription Request','Alberta Prescription Request',TO_TIMESTAMP('2021-04-26 10:08:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:08:41.396Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579038 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:08:41.663Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573555,579038,0,13,541622,'Alberta_PrescriptionRequest_ID',TO_TIMESTAMP('2021-04-26 10:08:41','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Alberta Prescription Request',0,TO_TIMESTAMP('2021-04-26 10:08:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:08:41.664Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573555 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:08:41.665Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579038) 
;

-- 2021-04-26T08:15:21.431Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573557,543939,0,10,541622,'ExternalId',TO_TIMESTAMP('2021-04-26 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External ID',0,0,TO_TIMESTAMP('2021-04-26 10:15:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:15:21.432Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573557 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:15:21.432Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- 2021-04-26T08:17:09.334Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579039,0,'C_BPartner_Patient_ID',TO_TIMESTAMP('2021-04-26 10:17:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Patient','Patient',TO_TIMESTAMP('2021-04-26 10:17:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:17:09.334Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579039 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:18:32.174Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573558,579039,0,30,541252,541622,'C_BPartner_Patient_ID',TO_TIMESTAMP('2021-04-26 10:18:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Patient',0,0,TO_TIMESTAMP('2021-04-26 10:18:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:18:32.178Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573558 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:18:32.183Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579039) 
;

-- 2021-04-26T08:21:56.218Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579040,0,'PatientBirthday',TO_TIMESTAMP('2021-04-26 10:21:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Birthday','Birthday',TO_TIMESTAMP('2021-04-26 10:21:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:21:56.221Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579040 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:22:24.505Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Geburstdatum', PrintName='Geburstdatum',Updated=TO_TIMESTAMP('2021-04-26 10:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579040 AND AD_Language='de_CH'
;

-- 2021-04-26T08:22:24.508Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579040,'de_CH') 
;

-- 2021-04-26T08:22:34.276Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Geburstdatum', PrintName='Geburstdatum',Updated=TO_TIMESTAMP('2021-04-26 10:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579040 AND AD_Language='de_DE'
;

-- 2021-04-26T08:22:34.279Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579040,'de_DE') 
;

-- 2021-04-26T08:22:34.304Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579040,'de_DE') 
;

-- 2021-04-26T08:22:34.308Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PatientBirthday', Name='Geburstdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=579040
;

-- 2021-04-26T08:22:34.309Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PatientBirthday', Name='Geburstdatum', Description=NULL, Help=NULL, AD_Element_ID=579040 WHERE UPPER(ColumnName)='PATIENTBIRTHDAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T08:22:34.312Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PatientBirthday', Name='Geburstdatum', Description=NULL, Help=NULL WHERE AD_Element_ID=579040 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T08:22:34.313Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Geburstdatum', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579040) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579040)
;

-- 2021-04-26T08:22:34.330Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Geburstdatum', Name='Geburstdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579040)
;

-- 2021-04-26T08:22:34.331Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Geburstdatum', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579040
;

-- 2021-04-26T08:22:34.333Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Geburstdatum', Description=NULL, Help=NULL WHERE AD_Element_ID = 579040
;

-- 2021-04-26T08:22:34.334Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Geburstdatum', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579040
;

-- 2021-04-26T08:22:39.256Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 10:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579040 AND AD_Language='en_US'
;

-- 2021-04-26T08:22:39.256Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579040,'en_US') 
;

-- 2021-04-26T08:22:44.818Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Geburstdatum', PrintName='Geburstdatum',Updated=TO_TIMESTAMP('2021-04-26 10:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579040 AND AD_Language='nl_NL'
;

-- 2021-04-26T08:22:44.818Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579040,'nl_NL') 
;

-- 2021-04-26T08:23:16.400Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573560,579040,0,15,541622,'PatientBirthday',TO_TIMESTAMP('2021-04-26 10:23:16','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geburstdatum',0,0,TO_TIMESTAMP('2021-04-26 10:23:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:23:16.401Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573560 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:23:16.401Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579040) 
;

-- 2021-04-26T08:24:13.210Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573561,577406,0,10,541622,'ExternalOrderId',TO_TIMESTAMP('2021-04-26 10:24:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe Auftragsnummer',0,0,TO_TIMESTAMP('2021-04-26 10:24:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:24:13.214Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573561 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:24:13.217Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(577406) 
;

-- 2021-04-26T08:25:02.365Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579041,0,'PrescriptionType',TO_TIMESTAMP('2021-04-26 10:25:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PrescriptionType','PrescriptionType',TO_TIMESTAMP('2021-04-26 10:25:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:25:02.367Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579041 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:25:36.343Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezepttyp', PrintName='Rezepttyp',Updated=TO_TIMESTAMP('2021-04-26 10:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579041 AND AD_Language='de_CH'
;

-- 2021-04-26T08:25:36.345Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579041,'de_CH') 
;

-- 2021-04-26T08:25:43.825Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezepttyp', PrintName='Rezepttyp',Updated=TO_TIMESTAMP('2021-04-26 10:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579041 AND AD_Language='de_DE'
;

-- 2021-04-26T08:25:43.828Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579041,'de_DE') 
;

-- 2021-04-26T08:25:43.853Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579041,'de_DE') 
;

-- 2021-04-26T08:25:43.856Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PrescriptionType', Name='Rezepttyp', Description=NULL, Help=NULL WHERE AD_Element_ID=579041
;

-- 2021-04-26T08:25:43.858Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionType', Name='Rezepttyp', Description=NULL, Help=NULL, AD_Element_ID=579041 WHERE UPPER(ColumnName)='PRESCRIPTIONTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T08:25:43.860Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionType', Name='Rezepttyp', Description=NULL, Help=NULL WHERE AD_Element_ID=579041 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T08:25:43.861Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rezepttyp', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579041) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579041)
;

-- 2021-04-26T08:25:43.873Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rezepttyp', Name='Rezepttyp' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579041)
;

-- 2021-04-26T08:25:43.874Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rezepttyp', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579041
;

-- 2021-04-26T08:25:43.876Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rezepttyp', Description=NULL, Help=NULL WHERE AD_Element_ID = 579041
;

-- 2021-04-26T08:25:43.876Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rezepttyp', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579041
;

-- 2021-04-26T08:25:50.957Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rezepttyp', PrintName='Rezepttyp',Updated=TO_TIMESTAMP('2021-04-26 10:25:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579041 AND AD_Language='nl_NL'
;

-- 2021-04-26T08:25:50.959Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579041,'nl_NL') 
;

-- 2021-04-26T08:26:58.989Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573562,579041,0,17,541296,541622,'PrescriptionType',TO_TIMESTAMP('2021-04-26 10:26:58','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rezepttyp',0,0,TO_TIMESTAMP('2021-04-26 10:26:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:26:58.990Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573562 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:26:58.991Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579041) 
;

-- 2021-04-26T08:28:04.212Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579042,0,'PrescriptionRequestCreatedAt',TO_TIMESTAMP('2021-04-26 10:28:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prescription request created','Prescription request created',TO_TIMESTAMP('2021-04-26 10:28:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:28:04.212Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579042 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:29:09.572Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung erstellt', PrintName='Rezeptanforderung erstellt',Updated=TO_TIMESTAMP('2021-04-26 10:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579042 AND AD_Language='de_CH'
;

-- 2021-04-26T08:29:09.572Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579042,'de_CH') 
;

-- 2021-04-26T08:29:16.657Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung erstellt', PrintName='Rezeptanforderung erstellt',Updated=TO_TIMESTAMP('2021-04-26 10:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579042 AND AD_Language='de_DE'
;

-- 2021-04-26T08:29:16.659Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579042,'de_DE') 
;

-- 2021-04-26T08:29:16.684Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579042,'de_DE') 
;

-- 2021-04-26T08:29:16.686Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PrescriptionRequestCreatedAt', Name='Rezeptanforderung erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=579042
;

-- 2021-04-26T08:29:16.688Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestCreatedAt', Name='Rezeptanforderung erstellt', Description=NULL, Help=NULL, AD_Element_ID=579042 WHERE UPPER(ColumnName)='PRESCRIPTIONREQUESTCREATEDAT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T08:29:16.690Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestCreatedAt', Name='Rezeptanforderung erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID=579042 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T08:29:16.691Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rezeptanforderung erstellt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579042) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579042)
;

-- 2021-04-26T08:29:16.708Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rezeptanforderung erstellt', Name='Rezeptanforderung erstellt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579042)
;

-- 2021-04-26T08:29:16.709Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rezeptanforderung erstellt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579042
;

-- 2021-04-26T08:29:16.711Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rezeptanforderung erstellt', Description=NULL, Help=NULL WHERE AD_Element_ID = 579042
;

-- 2021-04-26T08:29:16.712Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rezeptanforderung erstellt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579042
;

-- 2021-04-26T08:29:22.644Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 10:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579042 AND AD_Language='en_US'
;

-- 2021-04-26T08:29:22.646Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579042,'en_US') 
;

-- 2021-04-26T08:29:28.193Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rezeptanforderung erstellt', PrintName='Rezeptanforderung erstellt',Updated=TO_TIMESTAMP('2021-04-26 10:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579042 AND AD_Language='nl_NL'
;

-- 2021-04-26T08:29:28.195Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579042,'nl_NL') 
;

-- 2021-04-26T08:30:02.677Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573563,579042,0,16,541622,'PrescriptionRequestCreatedAt',TO_TIMESTAMP('2021-04-26 10:30:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rezeptanforderung erstellt',0,0,TO_TIMESTAMP('2021-04-26 10:30:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:30:02.680Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573563 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:30:02.684Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579042) 
;

-- 2021-04-26T08:33:13.085Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573564,541376,0,16,541622,'DeliveryDate',TO_TIMESTAMP('2021-04-26 10:33:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferdatum',0,0,TO_TIMESTAMP('2021-04-26 10:33:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:33:13.089Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573564 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:33:13.092Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(541376) 
;

-- 2021-04-26T08:33:38.567Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573565,574,0,15,541622,'StartDate',TO_TIMESTAMP('2021-04-26 10:33:38','YYYY-MM-DD HH24:MI:SS'),100,'N','First effective day (inclusive)','D',0,7,'The Start Date indicates the first or starting date','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Anfangsdatum',0,0,TO_TIMESTAMP('2021-04-26 10:33:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:33:38.570Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573565 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:33:38.574Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(574) 
;

-- 2021-04-26T08:53:08.637Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573566,294,0,15,541622,'EndDate',TO_TIMESTAMP('2021-04-26 10:53:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Last effective date (inclusive)','D',0,7,'The End Date indicates the last date in this range.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Enddatum',0,0,TO_TIMESTAMP('2021-04-26 10:53:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:53:08.638Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573566 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:53:08.639Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(294) 
;

-- 2021-04-26T08:54:22.768Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579043,0,'AccountingMonths',TO_TIMESTAMP('2021-04-26 10:54:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AccountingMonths','AccountingMonths',TO_TIMESTAMP('2021-04-26 10:54:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:54:22.769Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579043 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:55:08.702Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Versorgungs-/Abrechnungsmonate', PrintName='Versorgungs-/Abrechnungsmonate',Updated=TO_TIMESTAMP('2021-04-26 10:55:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579043 AND AD_Language='de_CH'
;

-- 2021-04-26T08:55:08.703Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579043,'de_CH') 
;

-- 2021-04-26T08:55:16.650Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Versorgungs-/Abrechnungsmonate', PrintName='Versorgungs-/Abrechnungsmonate',Updated=TO_TIMESTAMP('2021-04-26 10:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579043 AND AD_Language='de_DE'
;

-- 2021-04-26T08:55:16.653Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579043,'de_DE') 
;

-- 2021-04-26T08:55:16.663Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579043,'de_DE') 
;

-- 2021-04-26T08:55:16.664Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AccountingMonths', Name='Versorgungs-/Abrechnungsmonate', Description=NULL, Help=NULL WHERE AD_Element_ID=579043
;

-- 2021-04-26T08:55:16.664Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AccountingMonths', Name='Versorgungs-/Abrechnungsmonate', Description=NULL, Help=NULL, AD_Element_ID=579043 WHERE UPPER(ColumnName)='ACCOUNTINGMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T08:55:16.664Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AccountingMonths', Name='Versorgungs-/Abrechnungsmonate', Description=NULL, Help=NULL WHERE AD_Element_ID=579043 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T08:55:16.665Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Versorgungs-/Abrechnungsmonate', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579043) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579043)
;

-- 2021-04-26T08:55:16.670Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Versorgungs-/Abrechnungsmonate', Name='Versorgungs-/Abrechnungsmonate' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579043)
;

-- 2021-04-26T08:55:16.671Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Versorgungs-/Abrechnungsmonate', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579043
;

-- 2021-04-26T08:55:16.671Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Versorgungs-/Abrechnungsmonate', Description=NULL, Help=NULL WHERE AD_Element_ID = 579043
;

-- 2021-04-26T08:55:16.672Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Versorgungs-/Abrechnungsmonate', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579043
;

-- 2021-04-26T08:55:32.170Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Accounting months', PrintName='Accounting months',Updated=TO_TIMESTAMP('2021-04-26 10:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579043 AND AD_Language='en_US'
;

-- 2021-04-26T08:55:32.171Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579043,'en_US') 
;

-- 2021-04-26T08:55:38.531Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Versorgungs-/Abrechnungsmonate', PrintName='Versorgungs-/Abrechnungsmonate',Updated=TO_TIMESTAMP('2021-04-26 10:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579043 AND AD_Language='nl_NL'
;

-- 2021-04-26T08:55:38.534Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579043,'nl_NL') 
;

-- 2021-04-26T08:56:19.484Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573567,579043,0,17,541297,541622,'AccountingMonths',TO_TIMESTAMP('2021-04-26 10:56:19','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versorgungs-/Abrechnungsmonate',0,0,TO_TIMESTAMP('2021-04-26 10:56:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:56:19.487Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573567 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:56:19.490Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579043) 
;

-- 2021-04-26T08:57:39.734Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579044,0,'C_BPartner_Doctor_ID',TO_TIMESTAMP('2021-04-26 10:57:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Doctor','Docctor',TO_TIMESTAMP('2021-04-26 10:57:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:57:39.735Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579044 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:58:12.589Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573568,579044,0,30,541252,541622,'C_BPartner_Doctor_ID',TO_TIMESTAMP('2021-04-26 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Doctor',0,0,TO_TIMESTAMP('2021-04-26 10:58:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:58:12.592Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573568 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:58:12.594Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579044) 
;

-- 2021-04-26T08:59:08.468Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579045,0,'C_BPartner_Pharmacy_ID',TO_TIMESTAMP('2021-04-26 10:59:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pharmacy','Pharmacy',TO_TIMESTAMP('2021-04-26 10:59:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T08:59:08.469Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579045 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T08:59:33.737Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573569,579045,0,30,541252,541622,'C_BPartner_Pharmacy_ID',TO_TIMESTAMP('2021-04-26 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Pharmacy',0,0,TO_TIMESTAMP('2021-04-26 10:59:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T08:59:33.740Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T08:59:33.743Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579045) 
;

-- 2021-04-26T09:00:57.492Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573570,578830,0,17,541282,541622,'Therapy',TO_TIMESTAMP('2021-04-26 11:00:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Therapie',0,0,TO_TIMESTAMP('2021-04-26 11:00:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:00:57.496Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:00:57.499Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578830) 
;

-- 2021-04-26T09:02:09.766Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-04-26 11:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573570
;

-- 2021-04-26T09:04:47.547Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579046,0,'PrescriptionRequestCreatedBy',TO_TIMESTAMP('2021-04-26 11:04:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prescription request created by','Prescription request created by',TO_TIMESTAMP('2021-04-26 11:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T09:04:47.547Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579046 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T09:05:39.354Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung erstellt durch', PrintName='Rezeptanforderung erstellt durch',Updated=TO_TIMESTAMP('2021-04-26 11:05:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579046 AND AD_Language='de_CH'
;

-- 2021-04-26T09:05:39.355Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579046,'de_CH') 
;

-- 2021-04-26T09:05:50.993Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung erstellt durch', PrintName='Rezeptanforderung erstellt durch',Updated=TO_TIMESTAMP('2021-04-26 11:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579046 AND AD_Language='de_DE'
;

-- 2021-04-26T09:05:50.995Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579046,'de_DE') 
;

-- 2021-04-26T09:05:51.021Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579046,'de_DE') 
;

-- 2021-04-26T09:05:51.024Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PrescriptionRequestCreatedBy', Name='Rezeptanforderung erstellt durch', Description=NULL, Help=NULL WHERE AD_Element_ID=579046
;

-- 2021-04-26T09:05:51.027Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestCreatedBy', Name='Rezeptanforderung erstellt durch', Description=NULL, Help=NULL, AD_Element_ID=579046 WHERE UPPER(ColumnName)='PRESCRIPTIONREQUESTCREATEDBY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T09:05:51.029Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestCreatedBy', Name='Rezeptanforderung erstellt durch', Description=NULL, Help=NULL WHERE AD_Element_ID=579046 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T09:05:51.031Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rezeptanforderung erstellt durch', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579046) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579046)
;

-- 2021-04-26T09:05:51.052Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rezeptanforderung erstellt durch', Name='Rezeptanforderung erstellt durch' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579046)
;

-- 2021-04-26T09:05:51.053Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rezeptanforderung erstellt durch', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579046
;

-- 2021-04-26T09:05:51.055Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rezeptanforderung erstellt durch', Description=NULL, Help=NULL WHERE AD_Element_ID = 579046
;

-- 2021-04-26T09:05:51.055Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rezeptanforderung erstellt durch', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579046
;

-- 2021-04-26T09:05:57.477Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 11:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579046 AND AD_Language='en_US'
;

-- 2021-04-26T09:05:57.479Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579046,'en_US') 
;

-- 2021-04-26T09:06:04.552Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rezeptanforderung erstellt durch', PrintName='Rezeptanforderung erstellt durch',Updated=TO_TIMESTAMP('2021-04-26 11:06:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579046 AND AD_Language='nl_NL'
;

-- 2021-04-26T09:06:04.554Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579046,'nl_NL') 
;

-- 2021-04-26T09:06:33.709Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573571,579046,0,30,110,541622,'PrescriptionRequestCreatedBy',TO_TIMESTAMP('2021-04-26 11:06:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rezeptanforderung erstellt durch',0,0,TO_TIMESTAMP('2021-04-26 11:06:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:06:33.711Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:06:33.712Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579046) 
;

-- 2021-04-26T09:07:29.430Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573572,1115,0,36,541622,'Note',TO_TIMESTAMP('2021-04-26 11:07:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Optional weitere Information','D',0,255,'Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Notiz',0,0,TO_TIMESTAMP('2021-04-26 11:07:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:07:29.431Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573572 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:07:29.432Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1115) 
;

-- 2021-04-26T09:08:28.224Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579047,0,'Archived',TO_TIMESTAMP('2021-04-26 11:08:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Archived','Archived',TO_TIMESTAMP('2021-04-26 11:08:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T09:08:28.226Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579047 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T09:08:49.013Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Archiviert', PrintName='Archiviert',Updated=TO_TIMESTAMP('2021-04-26 11:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579047 AND AD_Language='de_CH'
;

-- 2021-04-26T09:08:49.014Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579047,'de_CH') 
;

-- 2021-04-26T09:08:54.107Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Archiviert', PrintName='Archiviert',Updated=TO_TIMESTAMP('2021-04-26 11:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579047 AND AD_Language='de_DE'
;

-- 2021-04-26T09:08:54.108Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579047,'de_DE') 
;

-- 2021-04-26T09:08:54.113Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579047,'de_DE') 
;

-- 2021-04-26T09:08:54.114Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Archived', Name='Archiviert', Description=NULL, Help=NULL WHERE AD_Element_ID=579047
;

-- 2021-04-26T09:08:54.114Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Archived', Name='Archiviert', Description=NULL, Help=NULL, AD_Element_ID=579047 WHERE UPPER(ColumnName)='ARCHIVED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T09:08:54.115Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Archived', Name='Archiviert', Description=NULL, Help=NULL WHERE AD_Element_ID=579047 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T09:08:54.115Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Archiviert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579047) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579047)
;

-- 2021-04-26T09:08:54.121Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Archiviert', Name='Archiviert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579047)
;

-- 2021-04-26T09:08:54.122Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Archiviert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579047
;

-- 2021-04-26T09:08:54.122Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Archiviert', Description=NULL, Help=NULL WHERE AD_Element_ID = 579047
;

-- 2021-04-26T09:08:54.123Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Archiviert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579047
;

-- 2021-04-26T09:09:00.902Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Archiviert', PrintName='Archiviert',Updated=TO_TIMESTAMP('2021-04-26 11:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579047 AND AD_Language='nl_NL'
;

-- 2021-04-26T09:09:00.903Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579047,'nl_NL') 
;

-- 2021-04-26T09:09:28.831Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573573,579047,0,20,541622,'Archived',TO_TIMESTAMP('2021-04-26 11:09:28','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Archiviert',0,0,TO_TIMESTAMP('2021-04-26 11:09:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:09:28.835Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573573 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:09:28.837Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579047) 
;

-- 2021-04-26T09:10:53.375Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579048,0,'PrescriptionRequestTimestamp',TO_TIMESTAMP('2021-04-26 11:10:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prescription request timestamp','Prescription request timestamp',TO_TIMESTAMP('2021-04-26 11:10:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T09:10:53.377Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579048 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T09:11:48.080Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung Zeitstempel', PrintName='Rezeptanforderung Zeitstempel',Updated=TO_TIMESTAMP('2021-04-26 11:11:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579048 AND AD_Language='de_CH'
;

-- 2021-04-26T09:11:48.080Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579048,'de_CH') 
;

-- 2021-04-26T09:11:58.625Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung Zeitstempel', PrintName='Rezeptanforderung Zeitstempel',Updated=TO_TIMESTAMP('2021-04-26 11:11:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579048 AND AD_Language='de_DE'
;

-- 2021-04-26T09:11:58.627Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579048,'de_DE') 
;

-- 2021-04-26T09:11:58.635Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579048,'de_DE') 
;

-- 2021-04-26T09:11:58.635Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PrescriptionRequestTimestamp', Name='Rezeptanforderung Zeitstempel', Description=NULL, Help=NULL WHERE AD_Element_ID=579048
;

-- 2021-04-26T09:11:58.636Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestTimestamp', Name='Rezeptanforderung Zeitstempel', Description=NULL, Help=NULL, AD_Element_ID=579048 WHERE UPPER(ColumnName)='PRESCRIPTIONREQUESTTIMESTAMP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T09:11:58.636Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestTimestamp', Name='Rezeptanforderung Zeitstempel', Description=NULL, Help=NULL WHERE AD_Element_ID=579048 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T09:11:58.636Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rezeptanforderung Zeitstempel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579048) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579048)
;

-- 2021-04-26T09:11:58.642Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rezeptanforderung Zeitstempel', Name='Rezeptanforderung Zeitstempel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579048)
;

-- 2021-04-26T09:11:58.642Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rezeptanforderung Zeitstempel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579048
;

-- 2021-04-26T09:11:58.643Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rezeptanforderung Zeitstempel', Description=NULL, Help=NULL WHERE AD_Element_ID = 579048
;

-- 2021-04-26T09:11:58.644Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rezeptanforderung Zeitstempel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579048
;

-- 2021-04-26T09:12:03.596Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 11:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579048 AND AD_Language='en_US'
;

-- 2021-04-26T09:12:03.596Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579048,'en_US') 
;

-- 2021-04-26T09:12:11.129Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rezeptanforderung Zeitstempel', PrintName='Rezeptanforderung Zeitstempel',Updated=TO_TIMESTAMP('2021-04-26 11:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579048 AND AD_Language='nl_NL'
;

-- 2021-04-26T09:12:11.131Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579048,'nl_NL') 
;

-- 2021-04-26T09:12:54.170Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573574,579048,0,16,541622,'PrescriptionRequestTimestamp',TO_TIMESTAMP('2021-04-26 11:12:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rezeptanforderung Zeitstempel',0,0,TO_TIMESTAMP('2021-04-26 11:12:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:12:54.175Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:12:54.178Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579048) 
;

-- 2021-04-26T09:15:12.682Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579049,0,'PrescriptionRequestUpdateAt',TO_TIMESTAMP('2021-04-26 11:15:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prescription request updated','Prescription request updated',TO_TIMESTAMP('2021-04-26 11:15:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T09:15:12.684Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579049 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T09:15:48.693Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung Aktualisiert', PrintName='Rezeptanforderung Aktualisiert',Updated=TO_TIMESTAMP('2021-04-26 11:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579049 AND AD_Language='de_CH'
;

-- 2021-04-26T09:15:48.694Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579049,'de_CH') 
;

-- 2021-04-26T09:15:55.868Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptanforderung Aktualisiert', PrintName='Rezeptanforderung Aktualisiert',Updated=TO_TIMESTAMP('2021-04-26 11:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579049 AND AD_Language='de_DE'
;

-- 2021-04-26T09:15:55.870Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579049,'de_DE') 
;

-- 2021-04-26T09:15:55.878Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579049,'de_DE') 
;

-- 2021-04-26T09:15:55.879Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PrescriptionRequestUpdateAt', Name='Rezeptanforderung Aktualisiert', Description=NULL, Help=NULL WHERE AD_Element_ID=579049
;

-- 2021-04-26T09:15:55.879Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestUpdateAt', Name='Rezeptanforderung Aktualisiert', Description=NULL, Help=NULL, AD_Element_ID=579049 WHERE UPPER(ColumnName)='PRESCRIPTIONREQUESTUPDATEAT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T09:15:55.880Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PrescriptionRequestUpdateAt', Name='Rezeptanforderung Aktualisiert', Description=NULL, Help=NULL WHERE AD_Element_ID=579049 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T09:15:55.880Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rezeptanforderung Aktualisiert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579049) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579049)
;

-- 2021-04-26T09:15:55.885Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rezeptanforderung Aktualisiert', Name='Rezeptanforderung Aktualisiert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579049)
;

-- 2021-04-26T09:15:55.886Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rezeptanforderung Aktualisiert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579049
;

-- 2021-04-26T09:15:55.887Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rezeptanforderung Aktualisiert', Description=NULL, Help=NULL WHERE AD_Element_ID = 579049
;

-- 2021-04-26T09:15:55.887Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rezeptanforderung Aktualisiert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579049
;

-- 2021-04-26T09:16:00.572Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 11:16:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579049 AND AD_Language='en_US'
;

-- 2021-04-26T09:16:00.574Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579049,'en_US') 
;

-- 2021-04-26T09:16:09.224Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rezeptanforderung Aktualisiert', PrintName='Rezeptanforderung Aktualisiert',Updated=TO_TIMESTAMP('2021-04-26 11:16:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579049 AND AD_Language='nl_NL'
;

-- 2021-04-26T09:16:09.224Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579049,'nl_NL') 
;

-- 2021-04-26T09:16:32.462Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573575,579049,0,16,541622,'PrescriptionRequestUpdateAt',TO_TIMESTAMP('2021-04-26 11:16:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rezeptanforderung Aktualisiert',0,0,TO_TIMESTAMP('2021-04-26 11:16:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:16:32.465Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573575 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:16:32.468Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579049) 
;


-- 2021-04-26T09:35:24.219Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541623,'N',TO_TIMESTAMP('2021-04-26 11:35:24','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'AlbertaPrescriptionRequestLine','NP','L','Alberta_PrescriptionRequest_Line','DTI',TO_TIMESTAMP('2021-04-26 11:35:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T09:35:24.220Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541623 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-04-26T09:35:24.263Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555375,TO_TIMESTAMP('2021-04-26 11:35:24','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table Alberta_PrescriptionRequest_Line',1,'Y','N','Y','Y','Alberta_PrescriptionRequest_Line','N',1000000,TO_TIMESTAMP('2021-04-26 11:35:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T09:35:24.269Z
-- URL zum Konzept
CREATE SEQUENCE ALBERTA_PRESCRIPTIONREQUEST_LINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;


-- 2021-04-26T09:40:13.708Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573576,102,0,19,541623,'AD_Client_ID',TO_TIMESTAMP('2021-04-26 11:40:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-04-26 11:40:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:13.709Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573576 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:13.724Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-04-26T09:40:14.318Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573577,113,0,30,541623,'AD_Org_ID',TO_TIMESTAMP('2021-04-26 11:40:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-04-26 11:40:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:14.319Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573577 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:14.320Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-04-26T09:40:14.737Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573578,245,0,16,541623,'Created',TO_TIMESTAMP('2021-04-26 11:40:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-04-26 11:40:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:14.738Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573578 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:14.739Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-04-26T09:40:15.123Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573579,246,0,18,110,541623,'CreatedBy',TO_TIMESTAMP('2021-04-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-04-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:15.124Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573579 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:15.125Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-04-26T09:40:15.505Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573580,348,0,20,541623,'IsActive',TO_TIMESTAMP('2021-04-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-04-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:15.506Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573580 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:15.507Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-04-26T09:40:15.899Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573581,607,0,16,541623,'Updated',TO_TIMESTAMP('2021-04-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-04-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:15.900Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573581 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:15.901Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-04-26T09:40:16.249Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573582,608,0,18,110,541623,'UpdatedBy',TO_TIMESTAMP('2021-04-26 11:40:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-04-26 11:40:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:16.250Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573582 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:16.251Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-04-26T09:40:16.593Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579050,0,'Alberta_PrescriptionRequest_Line_ID',TO_TIMESTAMP('2021-04-26 11:40:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AlbertaPrescriptionRequestLine','AlbertaPrescriptionRequestLine',TO_TIMESTAMP('2021-04-26 11:40:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T09:40:16.594Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579050 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T09:40:16.879Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573583,579050,0,13,541623,'Alberta_PrescriptionRequest_Line_ID',TO_TIMESTAMP('2021-04-26 11:40:16','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','AlbertaPrescriptionRequestLine',0,TO_TIMESTAMP('2021-04-26 11:40:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:40:16.880Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573583 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:40:16.881Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579050) 
;

-- 2021-04-26T09:42:37.859Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573584,543939,0,10,541623,'ExternalId',TO_TIMESTAMP('2021-04-26 11:42:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External ID',0,0,TO_TIMESTAMP('2021-04-26 11:42:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T09:42:37.860Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573584 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T09:42:37.861Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- 2021-04-26T10:02:39.119Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573585,454,0,19,541623,'M_Product_ID',TO_TIMESTAMP('2021-04-26 12:02:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2021-04-26 12:02:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:02:39.120Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573585 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:02:39.121Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2021-04-26T10:03:20.811Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573586,526,0,22,541623,'Qty',TO_TIMESTAMP('2021-04-26 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge','D',0,10,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Menge',0,0,TO_TIMESTAMP('2021-04-26 12:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:03:20.813Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573586 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:03:20.814Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- 2021-04-26T10:08:27.732Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541298,TO_TIMESTAMP('2021-04-26 12:08:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Product_AlbertaPackingUnit',TO_TIMESTAMP('2021-04-26 12:08:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-04-26T10:08:27.734Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541298 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-04-26T10:09:43.818Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,573114,573113,0,541298,541598,TO_TIMESTAMP('2021-04-26 12:09:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-04-26 12:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T10:10:06.585Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573587,578826,0,18,540153,541623,'ArticleUnit',TO_TIMESTAMP('2021-04-26 12:10:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verpackungseinheit',0,0,TO_TIMESTAMP('2021-04-26 12:10:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:10:06.586Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573587 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:10:06.587Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578826) 
;

-- 2021-04-26T10:10:51.729Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573588,1115,0,36,541623,'Note',TO_TIMESTAMP('2021-04-26 12:10:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Optional weitere Information','D',0,255,'Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Notiz',0,0,TO_TIMESTAMP('2021-04-26 12:10:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:10:51.733Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573588 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:10:51.736Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1115) 
;

-- 2021-04-26T10:11:56.945Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579051,0,'LineUpdated',TO_TIMESTAMP('2021-04-26 12:11:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Line updated','Line updated',TO_TIMESTAMP('2021-04-26 12:11:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T10:11:56.947Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579051 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T10:12:24.098Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zeile Aktualisiert', PrintName='Zeile Aktualisiert',Updated=TO_TIMESTAMP('2021-04-26 12:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579051 AND AD_Language='de_CH'
;

-- 2021-04-26T10:12:24.101Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579051,'de_CH') 
;

-- 2021-04-26T10:12:32.075Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zeile Aktualisiert', PrintName='Zeile Aktualisiert',Updated=TO_TIMESTAMP('2021-04-26 12:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579051 AND AD_Language='de_DE'
;

-- 2021-04-26T10:12:32.076Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579051,'de_DE') 
;

-- 2021-04-26T10:12:32.083Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579051,'de_DE') 
;

-- 2021-04-26T10:12:32.084Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='LineUpdated', Name='Zeile Aktualisiert', Description=NULL, Help=NULL WHERE AD_Element_ID=579051
;

-- 2021-04-26T10:12:32.085Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LineUpdated', Name='Zeile Aktualisiert', Description=NULL, Help=NULL, AD_Element_ID=579051 WHERE UPPER(ColumnName)='LINEUPDATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T10:12:32.085Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LineUpdated', Name='Zeile Aktualisiert', Description=NULL, Help=NULL WHERE AD_Element_ID=579051 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T10:12:32.086Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zeile Aktualisiert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579051) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579051)
;

-- 2021-04-26T10:12:32.093Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zeile Aktualisiert', Name='Zeile Aktualisiert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579051)
;

-- 2021-04-26T10:12:32.094Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zeile Aktualisiert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579051
;

-- 2021-04-26T10:12:32.095Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zeile Aktualisiert', Description=NULL, Help=NULL WHERE AD_Element_ID = 579051
;

-- 2021-04-26T10:12:32.095Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zeile Aktualisiert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579051
;

-- 2021-04-26T10:12:37.871Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 12:12:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579051 AND AD_Language='en_US'
;

-- 2021-04-26T10:12:37.872Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579051,'en_US') 
;

-- 2021-04-26T10:12:43.431Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Zeile Aktualisiert', PrintName='Zeile Aktualisiert',Updated=TO_TIMESTAMP('2021-04-26 12:12:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579051 AND AD_Language='nl_NL'
;

-- 2021-04-26T10:12:43.432Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579051,'nl_NL') 
;

-- 2021-04-26T10:13:08.133Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573589,579051,0,16,541623,'LineUpdated',TO_TIMESTAMP('2021-04-26 12:13:08','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zeile Aktualisiert',0,0,TO_TIMESTAMP('2021-04-26 12:13:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:13:08.137Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573589 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:13:08.140Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579051) 
;

-- 2021-04-26T10:28:59.966Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573590,579038,0,30,541623,'Alberta_PrescriptionRequest_ID',TO_TIMESTAMP('2021-04-26 12:28:59','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Alberta Prescription Request',0,0,TO_TIMESTAMP('2021-04-26 12:28:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:28:59.970Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573590 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:28:59.973Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579038) 
;

-- 2021-04-26T10:29:57.190Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=541298,Updated=TO_TIMESTAMP('2021-04-26 12:29:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-26T10:30:39.129Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-04-26 12:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-26T10:30:52.606Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2021-04-26 12:30:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-26T10:47:41.843Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541625,'N',TO_TIMESTAMP('2021-04-26 12:47:41','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,' Alberta Prescription Therapy Type','NP','L','Alberta_PrescriptionRequest_TherapyType','DTI',TO_TIMESTAMP('2021-04-26 12:47:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T10:47:41.844Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541625 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-04-26T10:47:41.893Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555376,TO_TIMESTAMP('2021-04-26 12:47:41','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table Alberta_PrescriptionRequest_TherapyType',1,'Y','N','Y','Y','Alberta_PrescriptionRequest_TherapyType','N',1000000,TO_TIMESTAMP('2021-04-26 12:47:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T10:47:41.897Z
-- URL zum Konzept
CREATE SEQUENCE ALBERTA_PRESCRIPTIONREQUEST_THERAPYTYPE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-04-26T10:47:58.484Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573591,102,0,19,541625,'AD_Client_ID',TO_TIMESTAMP('2021-04-26 12:47:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-04-26 12:47:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:47:58.485Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573591 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:47:58.487Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-04-26T10:47:58.919Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573592,113,0,30,541625,'AD_Org_ID',TO_TIMESTAMP('2021-04-26 12:47:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-04-26 12:47:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:47:58.920Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573592 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:47:58.921Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-04-26T10:47:59.310Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573593,245,0,16,541625,'Created',TO_TIMESTAMP('2021-04-26 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-04-26 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:47:59.313Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573593 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:47:59.316Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-04-26T10:47:59.729Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573594,246,0,18,110,541625,'CreatedBy',TO_TIMESTAMP('2021-04-26 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-04-26 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:47:59.730Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573594 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:47:59.730Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-04-26T10:48:00.085Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573595,348,0,20,541625,'IsActive',TO_TIMESTAMP('2021-04-26 12:48:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-04-26 12:48:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:48:00.086Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573595 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:48:00.087Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-04-26T10:48:00.501Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573596,607,0,16,541625,'Updated',TO_TIMESTAMP('2021-04-26 12:48:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-04-26 12:48:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:48:00.502Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573596 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:48:00.503Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-04-26T10:48:00.863Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573597,608,0,18,110,541625,'UpdatedBy',TO_TIMESTAMP('2021-04-26 12:48:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-04-26 12:48:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:48:00.864Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573597 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:48:00.865Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-04-26T10:48:01.249Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579052,0,'Alberta_PrescriptionRequest_TherapyType_ID',TO_TIMESTAMP('2021-04-26 12:48:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',' Alberta Prescription Therapy Type',' Alberta Prescription Therapy Type',TO_TIMESTAMP('2021-04-26 12:48:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T10:48:01.250Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579052 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T10:48:01.543Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573598,579052,0,13,541625,'Alberta_PrescriptionRequest_TherapyType_ID',TO_TIMESTAMP('2021-04-26 12:48:01','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N',' Alberta Prescription Therapy Type',0,TO_TIMESTAMP('2021-04-26 12:48:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:48:01.544Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573598 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:48:01.545Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579052) 
;

-- 2021-04-26T10:48:30.479Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573599,578967,0,10,541625,'TherapyType',TO_TIMESTAMP('2021-04-26 12:48:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Therapy type',0,0,TO_TIMESTAMP('2021-04-26 12:48:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:48:30.480Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573599 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:48:30.481Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578967) 
;

-- 2021-04-26T10:53:48.553Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579053,0,'TherapyTypeId',TO_TIMESTAMP('2021-04-26 12:53:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Therapy Type Id','Therapy Type Id',TO_TIMESTAMP('2021-04-26 12:53:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T10:53:48.554Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579053 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T10:53:55.786Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='TherapyTypeIds',Updated=TO_TIMESTAMP('2021-04-26 12:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579053
;

-- 2021-04-26T10:53:55.791Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='TherapyTypeIds', Name='Therapy Type Id', Description=NULL, Help=NULL WHERE AD_Element_ID=579053
;

-- 2021-04-26T10:53:55.793Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TherapyTypeIds', Name='Therapy Type Id', Description=NULL, Help=NULL, AD_Element_ID=579053 WHERE UPPER(ColumnName)='THERAPYTYPEIDS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T10:53:55.795Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TherapyTypeIds', Name='Therapy Type Id', Description=NULL, Help=NULL WHERE AD_Element_ID=579053 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T10:54:36.105Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573600,579053,0,10,541622,'TherapyTypeIds',TO_TIMESTAMP('2021-04-26 12:54:36','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Therapy Type Id',0,0,TO_TIMESTAMP('2021-04-26 12:54:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T10:54:36.106Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573600 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T10:54:36.107Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579053) 
;

-- 2021-04-26T10:57:50.431Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579054,0,TO_TIMESTAMP('2021-04-26 12:57:50','YYYY-MM-DD HH24:MI:SS'),100,'Alberta Prescription Request WIndow','D','Y','Alberta Prescription Request','Alberta Prescription Request',TO_TIMESTAMP('2021-04-26 12:57:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T10:57:50.433Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579054 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T10:58:56.632Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Alberta Rezeptanforderung Fenster', IsTranslated='Y', Name='Alberta Rezeptanforderung', PrintName='Alberta Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 12:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579054 AND AD_Language='de_CH'
;

-- 2021-04-26T10:58:56.635Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579054,'de_CH') 
;

-- 2021-04-26T10:59:10.775Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Alberta Rezeptanforderung Fenster', IsTranslated='Y', Name='Alberta Rezeptanforderung', PrintName='Alberta Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 12:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579054 AND AD_Language='de_DE'
;

-- 2021-04-26T10:59:10.777Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579054,'de_DE') 
;

-- 2021-04-26T10:59:10.787Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579054,'de_DE') 
;

-- 2021-04-26T10:59:10.788Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Alberta Rezeptanforderung', Description='Alberta Rezeptanforderung Fenster', Help=NULL WHERE AD_Element_ID=579054
;

-- 2021-04-26T10:59:10.788Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Alberta Rezeptanforderung', Description='Alberta Rezeptanforderung Fenster', Help=NULL WHERE AD_Element_ID=579054 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T10:59:10.789Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Alberta Rezeptanforderung', Description='Alberta Rezeptanforderung Fenster', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579054) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579054)
;

-- 2021-04-26T10:59:10.794Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta Rezeptanforderung', Name='Alberta Rezeptanforderung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579054)
;

-- 2021-04-26T10:59:10.795Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Alberta Rezeptanforderung', Description='Alberta Rezeptanforderung Fenster', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579054
;

-- 2021-04-26T10:59:10.796Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Alberta Rezeptanforderung', Description='Alberta Rezeptanforderung Fenster', Help=NULL WHERE AD_Element_ID = 579054
;

-- 2021-04-26T10:59:10.796Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alberta Rezeptanforderung', Description = 'Alberta Rezeptanforderung Fenster', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579054
;

-- 2021-04-26T10:59:19.610Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Alberta Prescription Request Window', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 12:59:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579054 AND AD_Language='en_US'
;

-- 2021-04-26T10:59:19.610Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579054,'en_US') 
;

-- 2021-04-26T10:59:37.919Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Alberta Rezeptanforderung Fenster', IsTranslated='Y', Name='Alberta Rezeptanforderung', PrintName='Alberta Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 12:59:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579054 AND AD_Language='nl_NL'
;

-- 2021-04-26T10:59:37.920Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579054,'nl_NL') 
;

-- 2021-04-26T11:00:13.391Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579054,0,541117,TO_TIMESTAMP('2021-04-26 13:00:13','YYYY-MM-DD HH24:MI:SS'),100,'Alberta Rezeptanforderung Fenster','D','Y','N','N','N','N','N','N','Y','Alberta Rezeptanforderung','N',TO_TIMESTAMP('2021-04-26 13:00:13','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-04-26T11:00:13.394Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541117 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-04-26T11:00:13.400Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579054) 
;

-- 2021-04-26T11:00:13.413Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541117
;

-- 2021-04-26T11:00:13.415Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541117)
;

-- 2021-04-26T11:01:46.213Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579038,0,543839,541622,541117,'Y',TO_TIMESTAMP('2021-04-26 13:01:46','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Alberta_PrescriptionRequest','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Alberta Prescription Request','N',10,0,TO_TIMESTAMP('2021-04-26 13:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:01:46.217Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543839 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-04-26T11:01:46.220Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579038) 
;

-- 2021-04-26T11:01:46.227Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543839)
;

-- 2021-04-26T11:02:12.132Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573555,643999,0,543839,TO_TIMESTAMP('2021-04-26 13:02:12','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Alberta Prescription Request',TO_TIMESTAMP('2021-04-26 13:02:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:12.133Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=643999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:12.134Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579038) 
;

-- 2021-04-26T11:02:12.136Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=643999
;

-- 2021-04-26T11:02:12.136Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(643999)
;

-- 2021-04-26T11:02:48.632Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573548,644000,0,543839,TO_TIMESTAMP('2021-04-26 13:02:48','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-04-26 13:02:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:48.633Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:48.634Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-04-26T11:02:48.822Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644000
;

-- 2021-04-26T11:02:48.822Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644000)
;

-- 2021-04-26T11:02:48.880Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573549,644001,0,543839,TO_TIMESTAMP('2021-04-26 13:02:48','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-04-26 13:02:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:48.881Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:48.882Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-04-26T11:02:48.971Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644001
;

-- 2021-04-26T11:02:48.971Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644001)
;

-- 2021-04-26T11:02:49.035Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573552,644002,0,543839,TO_TIMESTAMP('2021-04-26 13:02:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-04-26 13:02:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.036Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.037Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-04-26T11:02:49.140Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644002
;

-- 2021-04-26T11:02:49.141Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644002)
;

-- 2021-04-26T11:02:49.216Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573557,644003,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','External ID',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.217Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.218Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2021-04-26T11:02:49.226Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644003
;

-- 2021-04-26T11:02:49.227Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644003)
;

-- 2021-04-26T11:02:49.287Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573558,644004,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Patient',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.290Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.293Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579039) 
;

-- 2021-04-26T11:02:49.300Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644004
;

-- 2021-04-26T11:02:49.301Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644004)
;

-- 2021-04-26T11:02:49.366Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573560,644005,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Geburstdatum',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.369Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.371Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579040) 
;

-- 2021-04-26T11:02:49.378Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644005
;

-- 2021-04-26T11:02:49.379Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644005)
;

-- 2021-04-26T11:02:49.442Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573561,644006,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Externe Auftragsnummer',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.445Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.448Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577406) 
;

-- 2021-04-26T11:02:49.450Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644006
;

-- 2021-04-26T11:02:49.451Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644006)
;

-- 2021-04-26T11:02:49.516Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573562,644007,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,2,'D','Y','N','N','N','N','N','N','N','Rezepttyp',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.518Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.521Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579041) 
;

-- 2021-04-26T11:02:49.522Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644007
;

-- 2021-04-26T11:02:49.522Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644007)
;

-- 2021-04-26T11:02:49.565Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573563,644008,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Rezeptanforderung erstellt',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.565Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.566Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579042) 
;

-- 2021-04-26T11:02:49.566Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644008
;

-- 2021-04-26T11:02:49.567Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644008)
;

-- 2021-04-26T11:02:49.613Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573565,644009,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)',7,'D','The Start Date indicates the first or starting date','Y','N','N','N','N','N','N','N','Anfangsdatum',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.614Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.614Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(574) 
;

-- 2021-04-26T11:02:49.617Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644009
;

-- 2021-04-26T11:02:49.617Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644009)
;

-- 2021-04-26T11:02:49.668Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573566,644010,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)',7,'D','The End Date indicates the last date in this range.','Y','N','N','N','N','N','N','N','Enddatum',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.668Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.669Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(294) 
;

-- 2021-04-26T11:02:49.670Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644010
;

-- 2021-04-26T11:02:49.670Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644010)
;

-- 2021-04-26T11:02:49.723Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573567,644011,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,3,'D','Y','N','N','N','N','N','N','N','Versorgungs-/Abrechnungsmonate',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.724Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.725Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579043) 
;

-- 2021-04-26T11:02:49.725Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644011
;

-- 2021-04-26T11:02:49.725Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644011)
;

-- 2021-04-26T11:02:49.778Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573568,644012,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Doctor',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.780Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.783Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579044) 
;

-- 2021-04-26T11:02:49.784Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644012
;

-- 2021-04-26T11:02:49.785Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644012)
;

-- 2021-04-26T11:02:49.843Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573569,644013,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Pharmacy',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.843Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.844Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579045) 
;

-- 2021-04-26T11:02:49.844Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644013
;

-- 2021-04-26T11:02:49.844Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644013)
;

-- 2021-04-26T11:02:49.890Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573571,644014,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Rezeptanforderung erstellt durch',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.890Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.891Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579046) 
;

-- 2021-04-26T11:02:49.892Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644014
;

-- 2021-04-26T11:02:49.892Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644014)
;

-- 2021-04-26T11:02:49.940Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573572,644015,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',255,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','N','N','N','N','N','Notiz',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:49.942Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:49.945Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2021-04-26T11:02:49.961Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644015
;

-- 2021-04-26T11:02:49.962Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644015)
;

-- 2021-04-26T11:02:50.028Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573573,644016,0,543839,TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Archiviert',TO_TIMESTAMP('2021-04-26 13:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:50.030Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:50.033Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579047) 
;

-- 2021-04-26T11:02:50.034Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644016
;

-- 2021-04-26T11:02:50.035Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644016)
;

-- 2021-04-26T11:02:50.087Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573574,644017,0,543839,TO_TIMESTAMP('2021-04-26 13:02:50','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Rezeptanforderung Zeitstempel',TO_TIMESTAMP('2021-04-26 13:02:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:50.089Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:50.092Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579048) 
;

-- 2021-04-26T11:02:50.094Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644017
;

-- 2021-04-26T11:02:50.095Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644017)
;

-- 2021-04-26T11:02:50.154Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573575,644018,0,543839,TO_TIMESTAMP('2021-04-26 13:02:50','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Rezeptanforderung Aktualisiert',TO_TIMESTAMP('2021-04-26 13:02:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:50.155Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:50.156Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579049) 
;

-- 2021-04-26T11:02:50.156Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644018
;

-- 2021-04-26T11:02:50.156Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644018)
;

-- 2021-04-26T11:02:50.208Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573600,644019,0,543839,TO_TIMESTAMP('2021-04-26 13:02:50','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Therapy Type Id',TO_TIMESTAMP('2021-04-26 13:02:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:02:50.208Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:02:50.209Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579053) 
;

-- 2021-04-26T11:02:50.210Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644019
;

-- 2021-04-26T11:02:50.210Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644019)
;

-- 2021-04-26T11:03:38.442Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579050,0,543840,541623,541117,'Y',TO_TIMESTAMP('2021-04-26 13:03:38','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Alberta_PrescriptionRequest_Line','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'AlbertaPrescriptionRequestLine','N',20,0,TO_TIMESTAMP('2021-04-26 13:03:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:38.442Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543840 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-04-26T11:03:38.443Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579050) 
;

-- 2021-04-26T11:03:38.444Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543840)
;

-- 2021-04-26T11:03:47.744Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573576,644020,0,543840,TO_TIMESTAMP('2021-04-26 13:03:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-04-26 13:03:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:47.744Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:47.745Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-04-26T11:03:47.835Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644020
;

-- 2021-04-26T11:03:47.835Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644020)
;

-- 2021-04-26T11:03:47.902Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573577,644021,0,543840,TO_TIMESTAMP('2021-04-26 13:03:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-04-26 13:03:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:47.904Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:47.907Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-04-26T11:03:48.030Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644021
;

-- 2021-04-26T11:03:48.031Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644021)
;

-- 2021-04-26T11:03:48.089Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573580,644022,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.090Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.091Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-04-26T11:03:48.198Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644022
;

-- 2021-04-26T11:03:48.199Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644022)
;

-- 2021-04-26T11:03:48.254Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573583,644023,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','AlbertaPrescriptionRequestLine',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.255Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.256Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579050) 
;

-- 2021-04-26T11:03:48.257Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644023
;

-- 2021-04-26T11:03:48.257Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644023)
;

-- 2021-04-26T11:03:48.308Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573584,644024,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','External ID',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.309Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.310Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2021-04-26T11:03:48.312Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644024
;

-- 2021-04-26T11:03:48.312Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644024)
;

-- 2021-04-26T11:03:48.361Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573585,644025,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.362Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.363Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2021-04-26T11:03:48.407Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644025
;

-- 2021-04-26T11:03:48.407Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644025)
;

-- 2021-04-26T11:03:48.472Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573586,644026,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,'Menge',10,'D','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.475Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.477Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2021-04-26T11:03:48.506Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644026
;

-- 2021-04-26T11:03:48.506Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644026)
;

-- 2021-04-26T11:03:48.562Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573587,644027,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Verpackungseinheit',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.564Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.566Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578826) 
;

-- 2021-04-26T11:03:48.568Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644027
;

-- 2021-04-26T11:03:48.569Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644027)
;

-- 2021-04-26T11:03:48.636Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573588,644028,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information',255,'D','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','N','N','N','N','N','Notiz',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.638Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.641Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2021-04-26T11:03:48.654Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644028
;

-- 2021-04-26T11:03:48.654Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644028)
;

-- 2021-04-26T11:03:48.717Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573589,644029,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Zeile Aktualisiert',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.718Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.719Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579051) 
;

-- 2021-04-26T11:03:48.719Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644029
;

-- 2021-04-26T11:03:48.720Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644029)
;

-- 2021-04-26T11:03:48.764Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573590,644030,0,543840,TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Alberta Prescription Request',TO_TIMESTAMP('2021-04-26 13:03:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:03:48.767Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:03:48.771Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579038) 
;

-- 2021-04-26T11:03:48.773Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644030
;

-- 2021-04-26T11:03:48.774Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644030)
;

-- 2021-04-26T11:04:08.144Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543839,542994,TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-04-26T11:04:08.144Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542994 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-04-26T11:04:08.215Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543774,542994,TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:04:08.262Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543775,542994,TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:04:08.363Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543774,545683,TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-04-26 13:04:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:04:13.597Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- 2021-04-26T11:06:54.831Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Alberta Rezeptanforderung', WEBUI_NameNew='Neue Alberta Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 13:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579054 AND AD_Language='de_CH'
;

-- 2021-04-26T11:06:54.831Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579054,'de_CH') 
;

-- 2021-04-26T11:07:04.350Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Alberta Rezeptanforderung', WEBUI_NameNew='Neue Alberta Rezeptanforderung',Updated=TO_TIMESTAMP('2021-04-26 13:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579054 AND AD_Language='de_DE'
;

-- 2021-04-26T11:07:04.351Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579054,'de_DE') 
;

-- 2021-04-26T11:07:04.357Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579054,'de_DE') 
;

-- 2021-04-26T11:07:04.358Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alberta Rezeptanforderung', Description = 'Alberta Rezeptanforderung Fenster', WEBUI_NameBrowse = 'Alberta Rezeptanforderung', WEBUI_NameNew = 'Neue Alberta Rezeptanforderung', WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579054
;

-- 2021-04-26T11:07:16.687Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Alberta Prescription Request', WEBUI_NameNew='New Alberta Prescription Request',Updated=TO_TIMESTAMP('2021-04-26 13:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579054 AND AD_Language='en_US'
;

-- 2021-04-26T11:07:16.687Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579054,'en_US') 
;

-- 2021-04-26T11:07:38.690Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse,WEBUI_NameNew) VALUES ('W',0,579054,541703,0,541117,TO_TIMESTAMP('2021-04-26 13:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Alberta Rezeptanforderung Fenster','D','_AlbertaPrescriptionRequest','Y','Y','N','N','N','Alberta Rezeptanforderung',TO_TIMESTAMP('2021-04-26 13:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Alberta Rezeptanforderung','Neue Alberta Rezeptanforderung')
;

-- 2021-04-26T11:07:38.692Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541703 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-04-26T11:07:38.695Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541703, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541703)
;

-- 2021-04-26T11:07:38.702Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579054) 
;

-- 2021-04-26T11:07:39.247Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.247Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.248Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.248Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.248Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.249Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.249Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.249Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.249Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.250Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.250Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:39.250Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.080Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.081Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.081Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.081Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.081Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.082Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.082Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.082Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.082Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.083Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.083Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- 2021-04-26T11:07:46.083Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- 2021-04-26T11:22:27.887Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644004,0,543839,545683,583794,'F',TO_TIMESTAMP('2021-04-26 13:22:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Patient',10,0,0,TO_TIMESTAMP('2021-04-26 13:22:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:23:08.341Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644005,0,543839,545683,583795,'F',TO_TIMESTAMP('2021-04-26 13:23:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geburstdatum',20,0,0,TO_TIMESTAMP('2021-04-26 13:23:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:24:22.370Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644012,0,543839,545683,583796,'F',TO_TIMESTAMP('2021-04-26 13:24:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Doctor',30,0,0,TO_TIMESTAMP('2021-04-26 13:24:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:24:38.017Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644013,0,543839,545683,583797,'F',TO_TIMESTAMP('2021-04-26 13:24:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Pharmacy',40,0,0,TO_TIMESTAMP('2021-04-26 13:24:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:28:05.133Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573570,644031,0,543839,0,TO_TIMESTAMP('2021-04-26 13:28:05','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Therapie',10,10,0,1,1,TO_TIMESTAMP('2021-04-26 13:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:28:05.135Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T11:28:05.139Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578830) 
;

-- 2021-04-26T11:28:05.148Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644031
;

-- 2021-04-26T11:28:05.150Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644031)
;

-- 2021-04-26T11:29:42.982Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644031,0,543839,545683,583798,'F',TO_TIMESTAMP('2021-04-26 13:29:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Therapie',50,0,0,TO_TIMESTAMP('2021-04-26 13:29:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T11:53:03.867Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-04-26 13:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573564
;

-- 2021-04-26T11:53:05.646Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest','DeliveryDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-04-26T11:53:05.647Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest','DeliveryDate',null,'NOT NULL',null)
;

-- 2021-04-26T12:02:31.343Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='0',Updated=TO_TIMESTAMP('2021-04-26 14:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542362
;

-- 2021-04-26T12:02:35.277Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='2',Updated=TO_TIMESTAMP('2021-04-26 14:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542364
;

-- 2021-04-26T12:02:40.247Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='3',Updated=TO_TIMESTAMP('2021-04-26 14:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542365
;

-- 2021-04-26T12:02:49.722Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='1',Updated=TO_TIMESTAMP('2021-04-26 14:02:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542363
;

-- 2021-04-26T12:02:55.411Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='5',Updated=TO_TIMESTAMP('2021-04-26 14:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542367
;

-- 2021-04-26T12:02:58.702Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='4',Updated=TO_TIMESTAMP('2021-04-26 14:02:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542366
;

-- 2021-04-26T12:04:57.004Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='1',Updated=TO_TIMESTAMP('2021-04-26 14:04:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542368
;

-- 2021-04-26T12:05:01.383Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='2',Updated=TO_TIMESTAMP('2021-04-26 14:05:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542369
;

-- 2021-04-26T12:05:18.184Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='3',Updated=TO_TIMESTAMP('2021-04-26 14:05:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542370
;

-- 2021-04-26T12:05:24.533Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='4',Updated=TO_TIMESTAMP('2021-04-26 14:05:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542371
;

-- 2021-04-26T12:05:29.696Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='5',Updated=TO_TIMESTAMP('2021-04-26 14:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542372
;

-- 2021-04-26T12:05:32.042Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='6',Updated=TO_TIMESTAMP('2021-04-26 14:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542373
;

-- 2021-04-26T12:05:36.604Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='7',Updated=TO_TIMESTAMP('2021-04-26 14:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542374
;

-- 2021-04-26T12:05:41.959Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='8',Updated=TO_TIMESTAMP('2021-04-26 14:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542375
;

-- 2021-04-26T12:05:46.228Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='9',Updated=TO_TIMESTAMP('2021-04-26 14:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542376
;

-- 2021-04-26T12:05:51.194Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='10',Updated=TO_TIMESTAMP('2021-04-26 14:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542377
;

-- 2021-04-26T12:05:55.419Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='11',Updated=TO_TIMESTAMP('2021-04-26 14:05:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542378
;

-- 2021-04-26T12:05:59.035Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='12',Updated=TO_TIMESTAMP('2021-04-26 14:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542379
;

-- 2021-04-26T12:10:42.281Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579052,0,543842,541625,541117,'Y',TO_TIMESTAMP('2021-04-26 14:10:42','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Alberta_PrescriptionRequest_TherapyType','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,' Alberta Prescription Therapy Type','N',30,0,TO_TIMESTAMP('2021-04-26 14:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:10:42.282Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543842 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-04-26T12:10:42.283Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579052) 
;

-- 2021-04-26T12:10:42.284Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543842)
;

-- 2021-04-26T12:11:25.449Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,578829,0,543843,541599,541117,'Y',TO_TIMESTAMP('2021-04-26 14:11:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','M_Product_AlbertaTherapy','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Alberta-Therapien','N',40,0,TO_TIMESTAMP('2021-04-26 14:11:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:11:25.450Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543843 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-04-26T12:11:25.451Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(578829) 
;

-- 2021-04-26T12:11:25.454Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543843)
;

-- 2021-04-26T12:11:43.508Z
-- URL zum Konzept
UPDATE AD_Tab SET EntityType='D',Updated=TO_TIMESTAMP('2021-04-26 14:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543842
;

-- 2021-04-26T12:15:24.233Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2021-04-26 14:15:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543840
;

-- 2021-04-26T12:15:32.424Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2021-04-26 14:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543842
;

-- 2021-04-26T12:15:43.655Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2021-04-26 14:15:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543843
;

-- 2021-04-26T12:16:33.793Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=573127, Parent_Column_ID=573570,Updated=TO_TIMESTAMP('2021-04-26 14:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543843
;

-- 2021-04-26T12:17:20.527Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=573598, IsInsertRecord='N', Parent_Column_ID=573600,Updated=TO_TIMESTAMP('2021-04-26 14:17:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543842
;

-- 2021-04-26T12:19:40.769Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543774,545684,TO_TIMESTAMP('2021-04-26 14:19:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','prescription',20,TO_TIMESTAMP('2021-04-26 14:19:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:20:04.317Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644007,0,543839,545684,583799,'F',TO_TIMESTAMP('2021-04-26 14:20:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rezepttyp',10,0,0,TO_TIMESTAMP('2021-04-26 14:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:20:28.904Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644008,0,543839,545684,583800,'F',TO_TIMESTAMP('2021-04-26 14:20:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rezeptanforderung erstellt',20,0,0,TO_TIMESTAMP('2021-04-26 14:20:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:21:46.974Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644011,0,543839,545684,583801,'F',TO_TIMESTAMP('2021-04-26 14:21:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Versorgungs-/Abrechnungsmonate',30,0,0,TO_TIMESTAMP('2021-04-26 14:21:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:22:54.898Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644019,0,543839,545684,583802,'F',TO_TIMESTAMP('2021-04-26 14:22:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',543842,0,'Therapy Type Id',40,0,0,TO_TIMESTAMP('2021-04-26 14:22:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:25:06.365Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Labels_Tab_ID=NULL,Updated=TO_TIMESTAMP('2021-04-26 14:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583802
;

-- 2021-04-26T12:28:21.010Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644014,0,543839,545684,583803,'F',TO_TIMESTAMP('2021-04-26 14:28:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rezeptanforderung erstellt durch',50,0,0,TO_TIMESTAMP('2021-04-26 14:28:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:29:24.673Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543839,542995,TO_TIMESTAMP('2021-04-26 14:29:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-04-26 14:29:24','YYYY-MM-DD HH24:MI:SS'),100,'advancedEdit')
;

-- 2021-04-26T12:29:24.673Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542995 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-04-26T12:29:36.075Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543776,542995,TO_TIMESTAMP('2021-04-26 14:29:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 14:29:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:29:49.310Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543776,545685,TO_TIMESTAMP('2021-04-26 14:29:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced',10,TO_TIMESTAMP('2021-04-26 14:29:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:30:36.479Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644003,0,543839,545685,583804,'F',TO_TIMESTAMP('2021-04-26 14:30:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'External ID',10,0,0,TO_TIMESTAMP('2021-04-26 14:30:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:32:06.987Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644006,0,543839,545685,583805,'F',TO_TIMESTAMP('2021-04-26 14:32:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Externe Auftragsnummer',20,0,0,TO_TIMESTAMP('2021-04-26 14:32:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:33:17.211Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644017,0,543839,545685,583806,'F',TO_TIMESTAMP('2021-04-26 14:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Rezeptanforderung Zeitstempel',30,0,0,TO_TIMESTAMP('2021-04-26 14:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:33:47.476Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644018,0,543839,545685,583807,'F',TO_TIMESTAMP('2021-04-26 14:33:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Rezeptanforderung Aktualisiert',40,0,0,TO_TIMESTAMP('2021-04-26 14:33:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:34:10.192Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644015,0,543839,545685,583808,'F',TO_TIMESTAMP('2021-04-26 14:34:10','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','Y','N','Y','N','N','N',0,'Notiz',50,0,0,TO_TIMESTAMP('2021-04-26 14:34:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:38:39.331Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543775,545686,TO_TIMESTAMP('2021-04-26 14:38:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2021-04-26 14:38:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:39:01.845Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644016,0,543839,545686,583809,'F',TO_TIMESTAMP('2021-04-26 14:39:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Archiviert',10,0,0,TO_TIMESTAMP('2021-04-26 14:39:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:39:49.320Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543775,545687,TO_TIMESTAMP('2021-04-26 14:39:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',20,TO_TIMESTAMP('2021-04-26 14:39:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:41:02.293Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573564,644033,0,543839,0,TO_TIMESTAMP('2021-04-26 14:41:02','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Lieferdatum',20,20,0,1,1,TO_TIMESTAMP('2021-04-26 14:41:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:41:02.296Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T12:41:02.300Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541376) 
;

-- 2021-04-26T12:41:02.316Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644033
;

-- 2021-04-26T12:41:02.317Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644033)
;

-- 2021-04-26T12:41:58.002Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644033,0,543839,545687,583810,'F',TO_TIMESTAMP('2021-04-26 14:41:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lieferdatum',10,0,0,TO_TIMESTAMP('2021-04-26 14:41:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:43:01.569Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644009,0,543839,545687,583811,'F',TO_TIMESTAMP('2021-04-26 14:43:01','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)','The Start Date indicates the first or starting date','Y','N','N','Y','N','N','N',0,'Anfangsdatum',20,0,0,TO_TIMESTAMP('2021-04-26 14:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:43:40.464Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644010,0,543839,545687,583812,'F',TO_TIMESTAMP('2021-04-26 14:43:40','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','The End Date indicates the last date in this range.','Y','N','N','Y','N','N','N',0,'Enddatum',30,0,0,TO_TIMESTAMP('2021-04-26 14:43:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:44:22.531Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543775,545688,TO_TIMESTAMP('2021-04-26 14:44:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2021-04-26 14:44:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:44:37.954Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644001,0,543839,545688,583813,'F',TO_TIMESTAMP('2021-04-26 14:44:37','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2021-04-26 14:44:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T12:44:46.742Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644000,0,543839,545688,583814,'F',TO_TIMESTAMP('2021-04-26 14:44:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-04-26 14:44:46','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2021-04-26T13:08:01.619Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541299,TO_TIMESTAMP('2021-04-26 16:08:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','TherapyType',TO_TIMESTAMP('2021-04-26 16:08:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-04-26T13:08:01.785Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541299 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-04-26T13:08:29.739Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542380,TO_TIMESTAMP('2021-04-26 16:08:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Schwerkraft_PE',TO_TIMESTAMP('2021-04-26 16:08:29','YYYY-MM-DD HH24:MI:SS'),100,'0','Schwerkraft_PE')
;

-- 2021-04-26T13:08:29.783Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542380 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:08:49.073Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542381,TO_TIMESTAMP('2021-04-26 16:08:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pumpe_immobil_PE',TO_TIMESTAMP('2021-04-26 16:08:48','YYYY-MM-DD HH24:MI:SS'),100,'1','Pumpe_immobil_PE')
;

-- 2021-04-26T13:08:49.116Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542381 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:09:28.407Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542382,TO_TIMESTAMP('2021-04-26 16:09:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pumpe_mobil_PE',TO_TIMESTAMP('2021-04-26 16:09:27','YYYY-MM-DD HH24:MI:SS'),100,'2','Pumpe_mobil_PE')
;

-- 2021-04-26T13:09:28.449Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542382 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:09:53.188Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542384,TO_TIMESTAMP('2021-04-26 16:09:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Schwerkraft_EE',TO_TIMESTAMP('2021-04-26 16:09:52','YYYY-MM-DD HH24:MI:SS'),100,'3','Schwerkraft_EE')
;

-- 2021-04-26T13:09:53.229Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542384 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:10:12.979Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542385,TO_TIMESTAMP('2021-04-26 16:10:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pumpe_immobil_EE',TO_TIMESTAMP('2021-04-26 16:10:12','YYYY-MM-DD HH24:MI:SS'),100,'4','Pumpe_immobil_EE')
;

-- 2021-04-26T13:10:13.023Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542385 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:10:28.197Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542386,TO_TIMESTAMP('2021-04-26 16:10:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pumpe_mobil_EE',TO_TIMESTAMP('2021-04-26 16:10:27','YYYY-MM-DD HH24:MI:SS'),100,'5','Pumpe_mobil_EE')
;

-- 2021-04-26T13:10:28.238Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542386 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:10:46.022Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542387,TO_TIMESTAMP('2021-04-26 16:10:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bolus',TO_TIMESTAMP('2021-04-26 16:10:45','YYYY-MM-DD HH24:MI:SS'),100,'6','Bolus')
;

-- 2021-04-26T13:10:46.065Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542387 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:11:04.681Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542388,TO_TIMESTAMP('2021-04-26 16:11:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Trinknahrung',TO_TIMESTAMP('2021-04-26 16:11:04','YYYY-MM-DD HH24:MI:SS'),100,'7','Trinknahrung')
;

-- 2021-04-26T13:11:04.720Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542388 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:11:30.007Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542389,TO_TIMESTAMP('2021-04-26 16:11:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kolostoma',TO_TIMESTAMP('2021-04-26 16:11:29','YYYY-MM-DD HH24:MI:SS'),100,'8','Kolostoma')
;

-- 2021-04-26T13:11:30.047Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542389 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:11:48.314Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542390,TO_TIMESTAMP('2021-04-26 16:11:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Urostoma',TO_TIMESTAMP('2021-04-26 16:11:47','YYYY-MM-DD HH24:MI:SS'),100,'9','Urostoma')
;

-- 2021-04-26T13:11:48.355Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542390 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:12:07.048Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542391,TO_TIMESTAMP('2021-04-26 16:12:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ileostoma',TO_TIMESTAMP('2021-04-26 16:12:06','YYYY-MM-DD HH24:MI:SS'),100,'10','Ileostoma')
;

-- 2021-04-26T13:12:07.089Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542391 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:12:25.953Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542392,TO_TIMESTAMP('2021-04-26 16:12:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tracheostoma_beatmet',TO_TIMESTAMP('2021-04-26 16:12:25','YYYY-MM-DD HH24:MI:SS'),100,'11','Tracheostoma_beatmet')
;

-- 2021-04-26T13:12:25.996Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542392 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:12:45.584Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542393,TO_TIMESTAMP('2021-04-26 16:12:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Laryngektomie_beatmet',TO_TIMESTAMP('2021-04-26 16:12:45','YYYY-MM-DD HH24:MI:SS'),100,'12','Laryngektomie_beatmet')
;

-- 2021-04-26T13:12:45.628Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542393 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:13:01.063Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542394,TO_TIMESTAMP('2021-04-26 16:13:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inkontinenz_ableitend',TO_TIMESTAMP('2021-04-26 16:13:00','YYYY-MM-DD HH24:MI:SS'),100,'13','Inkontinenz_ableitend')
;

-- 2021-04-26T13:13:01.102Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542394 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:13:16.411Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542395,TO_TIMESTAMP('2021-04-26 16:13:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ulcus_cruris',TO_TIMESTAMP('2021-04-26 16:13:15','YYYY-MM-DD HH24:MI:SS'),100,'14','Ulcus_cruris')
;

-- 2021-04-26T13:13:16.457Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542395 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:13:34.786Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542396,TO_TIMESTAMP('2021-04-26 16:13:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dekubitus',TO_TIMESTAMP('2021-04-26 16:13:34','YYYY-MM-DD HH24:MI:SS'),100,'15','Dekubitus')
;

-- 2021-04-26T13:13:34.831Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542396 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:13:51.431Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542397,TO_TIMESTAMP('2021-04-26 16:13:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Diabetisches_Fußsyndrom',TO_TIMESTAMP('2021-04-26 16:13:50','YYYY-MM-DD HH24:MI:SS'),100,'16','Diabetisches_Fußsyndrom')
;

-- 2021-04-26T13:13:51.472Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542397 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:14:07.500Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542398,TO_TIMESTAMP('2021-04-26 16:14:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ulzerierte_Tumore',TO_TIMESTAMP('2021-04-26 16:14:07','YYYY-MM-DD HH24:MI:SS'),100,'17','Ulzerierte_Tumore')
;

-- 2021-04-26T13:14:07.540Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542398 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:14:23.078Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542399,TO_TIMESTAMP('2021-04-26 16:14:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Wundfisteln',TO_TIMESTAMP('2021-04-26 16:14:22','YYYY-MM-DD HH24:MI:SS'),100,'18','Wundfisteln')
;

-- 2021-04-26T13:14:23.117Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542399 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:14:51.281Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542400,TO_TIMESTAMP('2021-04-26 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','sonstige_chronische_Wunden',TO_TIMESTAMP('2021-04-26 16:14:50','YYYY-MM-DD HH24:MI:SS'),100,'19','sonstige_chronische_Wunden')
;

-- 2021-04-26T13:14:51.358Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542400 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:15:14.592Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542401,TO_TIMESTAMP('2021-04-26 16:15:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Diabetes',TO_TIMESTAMP('2021-04-26 16:15:14','YYYY-MM-DD HH24:MI:SS'),100,'20','Diabetes')
;

-- 2021-04-26T13:15:14.635Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542401 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:15:31.524Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542402,TO_TIMESTAMP('2021-04-26 16:15:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pflegehilfsmittel',TO_TIMESTAMP('2021-04-26 16:15:31','YYYY-MM-DD HH24:MI:SS'),100,'21','Pflegehilfsmittel')
;

-- 2021-04-26T13:15:31.565Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542402 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:15:48.057Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542403,TO_TIMESTAMP('2021-04-26 16:15:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inkontinenz_aufsaugend',TO_TIMESTAMP('2021-04-26 16:15:47','YYYY-MM-DD HH24:MI:SS'),100,'22','Inkontinenz_aufsaugend')
;

-- 2021-04-26T13:15:48.099Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542403 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:16:09.354Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542404,TO_TIMESTAMP('2021-04-26 16:16:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Compounding',TO_TIMESTAMP('2021-04-26 16:16:08','YYYY-MM-DD HH24:MI:SS'),100,'23','Compounding')
;

-- 2021-04-26T13:16:09.393Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542404 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:16:27.063Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542405,TO_TIMESTAMP('2021-04-26 16:16:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fistel',TO_TIMESTAMP('2021-04-26 16:16:26','YYYY-MM-DD HH24:MI:SS'),100,'24','Fistel')
;

-- 2021-04-26T13:16:27.105Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542405 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:16:44.544Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542406,TO_TIMESTAMP('2021-04-26 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Antibiose',TO_TIMESTAMP('2021-04-26 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,'25','Antibiose')
;

-- 2021-04-26T13:16:44.603Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542406 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:16:59.915Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542407,TO_TIMESTAMP('2021-04-26 16:16:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tracheostoma_unbeatmet',TO_TIMESTAMP('2021-04-26 16:16:59','YYYY-MM-DD HH24:MI:SS'),100,'26','Tracheostoma_unbeatmet')
;

-- 2021-04-26T13:16:59.961Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542407 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:17:16.632Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542408,TO_TIMESTAMP('2021-04-26 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Laryngektomie_unbeatmet',TO_TIMESTAMP('2021-04-26 16:17:16','YYYY-MM-DD HH24:MI:SS'),100,'27','Laryngektomie_unbeatmet')
;

-- 2021-04-26T13:17:16.672Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542408 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:17:35.171Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542409,TO_TIMESTAMP('2021-04-26 16:17:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Absaugung_außerhalb_von_TS',TO_TIMESTAMP('2021-04-26 16:17:34','YYYY-MM-DD HH24:MI:SS'),100,'28','Absaugung_außerhalb_von_TS')
;

-- 2021-04-26T13:17:35.217Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542409 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:17:51.994Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542410,TO_TIMESTAMP('2021-04-26 16:17:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Beatmung',TO_TIMESTAMP('2021-04-26 16:17:51','YYYY-MM-DD HH24:MI:SS'),100,'29','Beatmung')
;

-- 2021-04-26T13:17:52.036Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542410 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:18:08.087Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542411,TO_TIMESTAMP('2021-04-26 16:18:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','sonstige_Wunden',TO_TIMESTAMP('2021-04-26 16:18:07','YYYY-MM-DD HH24:MI:SS'),100,'30','sonstige_Wunden')
;

-- 2021-04-26T13:18:08.132Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542411 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:18:30.307Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542412,TO_TIMESTAMP('2021-04-26 16:18:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Schmerztherapie',TO_TIMESTAMP('2021-04-26 16:18:29','YYYY-MM-DD HH24:MI:SS'),100,'31','Schmerztherapie')
;

-- 2021-04-26T13:18:30.350Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542412 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:18:50.281Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542413,TO_TIMESTAMP('2021-04-26 16:18:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Beatmung_invasiv',TO_TIMESTAMP('2021-04-26 16:18:49','YYYY-MM-DD HH24:MI:SS'),100,'32','Beatmung_invasiv')
;

-- 2021-04-26T13:18:50.323Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542413 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:19:07.143Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542414,TO_TIMESTAMP('2021-04-26 16:19:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Beatmung_nicht_invasiv',TO_TIMESTAMP('2021-04-26 16:19:06','YYYY-MM-DD HH24:MI:SS'),100,'33','Beatmung_nicht_invasiv')
;

-- 2021-04-26T13:19:07.183Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542414 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:19:22.990Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542415,TO_TIMESTAMP('2021-04-26 16:19:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Befeuchtung_aktiv',TO_TIMESTAMP('2021-04-26 16:19:22','YYYY-MM-DD HH24:MI:SS'),100,'34','Befeuchtung_aktiv')
;

-- 2021-04-26T13:19:23.031Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542415 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:19:41.584Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542416,TO_TIMESTAMP('2021-04-26 16:19:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Befeuchtung_passiv',TO_TIMESTAMP('2021-04-26 16:19:41','YYYY-MM-DD HH24:MI:SS'),100,'35','Befeuchtung_passiv')
;

-- 2021-04-26T13:19:41.623Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542416 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:19:57.559Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542417,TO_TIMESTAMP('2021-04-26 16:19:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Befeuchtung_integriert',TO_TIMESTAMP('2021-04-26 16:19:57','YYYY-MM-DD HH24:MI:SS'),100,'36','Befeuchtung_integriert')
;

-- 2021-04-26T13:19:57.601Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542417 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:20:15.760Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542418,TO_TIMESTAMP('2021-04-26 16:20:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','High_Flow',TO_TIMESTAMP('2021-04-26 16:20:15','YYYY-MM-DD HH24:MI:SS'),100,'37','High_Flow')
;

-- 2021-04-26T13:20:15.801Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542418 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:20:34.566Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542419,TO_TIMESTAMP('2021-04-26 16:20:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CPAP',TO_TIMESTAMP('2021-04-26 16:20:34','YYYY-MM-DD HH24:MI:SS'),100,'38','CPAP')
;

-- 2021-04-26T13:20:34.611Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542419 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:20:49.972Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542420,TO_TIMESTAMP('2021-04-26 16:20:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','APAP',TO_TIMESTAMP('2021-04-26 16:20:49','YYYY-MM-DD HH24:MI:SS'),100,'39','APAP')
;

-- 2021-04-26T13:20:50.017Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542420 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:21:04.722Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542421,TO_TIMESTAMP('2021-04-26 16:21:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bilevel',TO_TIMESTAMP('2021-04-26 16:21:04','YYYY-MM-DD HH24:MI:SS'),100,'40','Bilevel')
;

-- 2021-04-26T13:21:04.764Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542421 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:22:27.313Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542422,TO_TIMESTAMP('2021-04-26 16:22:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bilevel_ST',TO_TIMESTAMP('2021-04-26 16:22:26','YYYY-MM-DD HH24:MI:SS'),100,'41','Bilevel_ST')
;

-- 2021-04-26T13:22:27.362Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542422 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:22:43.438Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542423,TO_TIMESTAMP('2021-04-26 16:22:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auto_Bilevel',TO_TIMESTAMP('2021-04-26 16:22:42','YYYY-MM-DD HH24:MI:SS'),100,'42','Auto_Bilevel')
;

-- 2021-04-26T13:22:43.505Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542423 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:22:58.395Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542424,TO_TIMESTAMP('2021-04-26 16:22:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CS',TO_TIMESTAMP('2021-04-26 16:22:57','YYYY-MM-DD HH24:MI:SS'),100,'43','CS')
;

-- 2021-04-26T13:22:58.440Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542424 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:23:15.040Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542425,TO_TIMESTAMP('2021-04-26 16:23:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hustenhilfen',TO_TIMESTAMP('2021-04-26 16:23:14','YYYY-MM-DD HH24:MI:SS'),100,'44','Hustenhilfen')
;

-- 2021-04-26T13:23:15.083Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542425 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:23:31.853Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542426,TO_TIMESTAMP('2021-04-26 16:23:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sekretmobilisation',TO_TIMESTAMP('2021-04-26 16:23:31','YYYY-MM-DD HH24:MI:SS'),100,'45','Sekretmobilisation')
;

-- 2021-04-26T13:23:31.894Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542426 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:23:51.899Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542427,TO_TIMESTAMP('2021-04-26 16:23:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Absaugung_oral',TO_TIMESTAMP('2021-04-26 16:23:51','YYYY-MM-DD HH24:MI:SS'),100,'46','Absaugung_oral')
;

-- 2021-04-26T13:23:51.939Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542427 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:24:10.179Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542428,TO_TIMESTAMP('2021-04-26 16:24:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Absaugung_subglottisch',TO_TIMESTAMP('2021-04-26 16:24:09','YYYY-MM-DD HH24:MI:SS'),100,'47','Absaugung_subglottisch')
;

-- 2021-04-26T13:24:10.224Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542428 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:24:28.795Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542429,TO_TIMESTAMP('2021-04-26 16:24:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Monitoring',TO_TIMESTAMP('2021-04-26 16:24:28','YYYY-MM-DD HH24:MI:SS'),100,'48','Monitoring')
;

-- 2021-04-26T13:24:28.836Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542429 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:24:45.171Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542430,TO_TIMESTAMP('2021-04-26 16:24:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pulsoximetrie',TO_TIMESTAMP('2021-04-26 16:24:44','YYYY-MM-DD HH24:MI:SS'),100,'49','Pulsoximetrie')
;

-- 2021-04-26T13:24:45.210Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542430 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:25:00.657Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542431,TO_TIMESTAMP('2021-04-26 16:25:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fingerpulsoximetrie',TO_TIMESTAMP('2021-04-26 16:25:00','YYYY-MM-DD HH24:MI:SS'),100,'50','Fingerpulsoximetrie')
;

-- 2021-04-26T13:25:00.702Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542431 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:25:18.249Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542432,TO_TIMESTAMP('2021-04-26 16:25:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kapnometer',TO_TIMESTAMP('2021-04-26 16:25:17','YYYY-MM-DD HH24:MI:SS'),100,'51','Kapnometer')
;

-- 2021-04-26T13:25:18.289Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542432 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:25:32.093Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Blutdruckmessgeraet', Value='52', ValueName='Blutdruckmessgeraet',Updated=TO_TIMESTAMP('2021-04-26 16:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542432
;

-- 2021-04-26T13:26:01.352Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542433,TO_TIMESTAMP('2021-04-26 16:26:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kapnometer',TO_TIMESTAMP('2021-04-26 16:26:00','YYYY-MM-DD HH24:MI:SS'),100,'51','Kapnometer')
;

-- 2021-04-26T13:26:01.410Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542433 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:26:20.970Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542434,TO_TIMESTAMP('2021-04-26 16:26:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sauerstoff_mobil',TO_TIMESTAMP('2021-04-26 16:26:20','YYYY-MM-DD HH24:MI:SS'),100,'53','Sauerstoff_mobil')
;

-- 2021-04-26T13:26:21.012Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542434 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:26:40.109Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542435,TO_TIMESTAMP('2021-04-26 16:26:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sauerstoff_fluessig',TO_TIMESTAMP('2021-04-26 16:26:39','YYYY-MM-DD HH24:MI:SS'),100,'54','Sauerstoff_fluessig')
;

-- 2021-04-26T13:26:40.151Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542435 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:26:56.971Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542436,TO_TIMESTAMP('2021-04-26 16:26:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inhalation',TO_TIMESTAMP('2021-04-26 16:26:56','YYYY-MM-DD HH24:MI:SS'),100,'55','Inhalation')
;

-- 2021-04-26T13:26:57.012Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542436 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:27:15.195Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542437,TO_TIMESTAMP('2021-04-26 16:27:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Medikamentenvernebler',TO_TIMESTAMP('2021-04-26 16:27:14','YYYY-MM-DD HH24:MI:SS'),100,'56','Medikamentenvernebler')
;

-- 2021-04-26T13:27:15.237Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542437 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:27:30.582Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542438,TO_TIMESTAMP('2021-04-26 16:27:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ultraschallvernebler',TO_TIMESTAMP('2021-04-26 16:27:30','YYYY-MM-DD HH24:MI:SS'),100,'57','Ultraschallvernebler')
;

-- 2021-04-26T13:27:30.622Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542438 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:27:48.057Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542439,TO_TIMESTAMP('2021-04-26 16:27:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PEP_Systeme',TO_TIMESTAMP('2021-04-26 16:27:47','YYYY-MM-DD HH24:MI:SS'),100,'58','PEP_Systeme')
;

-- 2021-04-26T13:27:48.099Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542439 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:28:06.002Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542440,TO_TIMESTAMP('2021-04-26 16:28:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IPPB_Inhalation',TO_TIMESTAMP('2021-04-26 16:28:05','YYYY-MM-DD HH24:MI:SS'),100,'59','IPPB_Inhalation')
;

-- 2021-04-26T13:28:06.054Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542440 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:28:22.433Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542441,TO_TIMESTAMP('2021-04-26 16:28:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Atemtherapie',TO_TIMESTAMP('2021-04-26 16:28:21','YYYY-MM-DD HH24:MI:SS'),100,'60','Atemtherapie')
;

-- 2021-04-26T13:28:22.472Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542441 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:28:38.622Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542442,TO_TIMESTAMP('2021-04-26 16:28:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sauerstoff_stationaer',TO_TIMESTAMP('2021-04-26 16:28:38','YYYY-MM-DD HH24:MI:SS'),100,'61','Sauerstoff_stationaer')
;

-- 2021-04-26T13:28:38.669Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542442 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:28:55.694Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542443,TO_TIMESTAMP('2021-04-26 16:28:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pumpe_mobil_ST',TO_TIMESTAMP('2021-04-26 16:28:55','YYYY-MM-DD HH24:MI:SS'),100,'62','Pumpe_mobil_ST')
;

-- 2021-04-26T13:28:55.736Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542443 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:29:15.030Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542444,TO_TIMESTAMP('2021-04-26 16:29:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anti_Dekubitussystem',TO_TIMESTAMP('2021-04-26 16:29:14','YYYY-MM-DD HH24:MI:SS'),100,'63','Anti_Dekubitussystem')
;

-- 2021-04-26T13:29:15.072Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542444 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:29:34.009Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542445,TO_TIMESTAMP('2021-04-26 16:29:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Akute_Wunde',TO_TIMESTAMP('2021-04-26 16:29:33','YYYY-MM-DD HH24:MI:SS'),100,'64','Akute_Wunde')
;

-- 2021-04-26T13:29:34.054Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542445 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:29:50.382Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542446,TO_TIMESTAMP('2021-04-26 16:29:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Transkutane_Harnleiterfistel',TO_TIMESTAMP('2021-04-26 16:29:49','YYYY-MM-DD HH24:MI:SS'),100,'65','Transkutane_Harnleiterfistel')
;

-- 2021-04-26T13:29:50.423Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542446 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:30:09.697Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542447,TO_TIMESTAMP('2021-04-26 16:30:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MehrfachStoma',TO_TIMESTAMP('2021-04-26 16:30:09','YYYY-MM-DD HH24:MI:SS'),100,'66','MehrfachStoma')
;

-- 2021-04-26T13:30:09.738Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542447 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:30:23.911Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542448,TO_TIMESTAMP('2021-04-26 16:30:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pouch',TO_TIMESTAMP('2021-04-26 16:30:23','YYYY-MM-DD HH24:MI:SS'),100,'67','Pouch')
;

-- 2021-04-26T13:30:23.956Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542448 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:30:42.267Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542449,TO_TIMESTAMP('2021-04-26 16:30:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Stomairrigation',TO_TIMESTAMP('2021-04-26 16:30:41','YYYY-MM-DD HH24:MI:SS'),100,'68','Stomairrigation')
;

-- 2021-04-26T13:30:42.310Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542449 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:30:58.816Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542450,TO_TIMESTAMP('2021-04-26 16:30:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','PE_Kinder',TO_TIMESTAMP('2021-04-26 16:30:58','YYYY-MM-DD HH24:MI:SS'),100,'69','PE_Kinder')
;

-- 2021-04-26T13:30:58.858Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542450 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:31:16.231Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542451,TO_TIMESTAMP('2021-04-26 16:31:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EE_Kinder',TO_TIMESTAMP('2021-04-26 16:31:15','YYYY-MM-DD HH24:MI:SS'),100,'70','EE_Kinder')
;

-- 2021-04-26T13:31:16.277Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542451 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:31:33.806Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542452,TO_TIMESTAMP('2021-04-26 16:31:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EE_Technik',TO_TIMESTAMP('2021-04-26 16:31:33','YYYY-MM-DD HH24:MI:SS'),100,'71','EE_Technik')
;

-- 2021-04-26T13:31:33.849Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542452 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:32:00.731Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542453,TO_TIMESTAMP('2021-04-26 16:32:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Wachkoma',TO_TIMESTAMP('2021-04-26 16:32:00','YYYY-MM-DD HH24:MI:SS'),100,'72','Wachkoma')
;

-- 2021-04-26T13:32:00.774Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542453 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:32:16.257Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542454,TO_TIMESTAMP('2021-04-26 16:32:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inhalation_obere_Atemwege',TO_TIMESTAMP('2021-04-26 16:32:15','YYYY-MM-DD HH24:MI:SS'),100,'73','Inhalation_obere_Atemwege')
;

-- 2021-04-26T13:32:16.301Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542454 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:32:34.992Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542455,TO_TIMESTAMP('2021-04-26 16:32:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inhalation_unterer_Atemwege',TO_TIMESTAMP('2021-04-26 16:32:34','YYYY-MM-DD HH24:MI:SS'),100,'74','Inhalation_unterer_Atemwege')
;

-- 2021-04-26T13:32:35.034Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542455 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:32:51.597Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542456,TO_TIMESTAMP('2021-04-26 16:32:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Absaugung_endotracheal',TO_TIMESTAMP('2021-04-26 16:32:51','YYYY-MM-DD HH24:MI:SS'),100,'75','Absaugung_endotracheal')
;

-- 2021-04-26T13:32:51.644Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542456 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:33:09.652Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542457,TO_TIMESTAMP('2021-04-26 16:33:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ISK_FSK',TO_TIMESTAMP('2021-04-26 16:33:09','YYYY-MM-DD HH24:MI:SS'),100,'76','ISK_FSK')
;

-- 2021-04-26T13:33:09.694Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542457 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:33:26.942Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542458,TO_TIMESTAMP('2021-04-26 16:33:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anale_Irrigation',TO_TIMESTAMP('2021-04-26 16:33:26','YYYY-MM-DD HH24:MI:SS'),100,'77','Anale_Irrigation')
;

-- 2021-04-26T13:33:26.985Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542458 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:33:47.495Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542459,TO_TIMESTAMP('2021-04-26 16:33:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pessarversorgung',TO_TIMESTAMP('2021-04-26 16:33:47','YYYY-MM-DD HH24:MI:SS'),100,'78','Pessarversorgung')
;

-- 2021-04-26T13:33:47.538Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542459 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:34:10.356Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542460,TO_TIMESTAMP('2021-04-26 16:34:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Medikamententherapie',TO_TIMESTAMP('2021-04-26 16:34:09','YYYY-MM-DD HH24:MI:SS'),100,'79','Medikamententherapie')
;

-- 2021-04-26T13:34:10.402Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542460 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:34:27.080Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542461,TO_TIMESTAMP('2021-04-26 16:34:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Immunglobulintherapie',TO_TIMESTAMP('2021-04-26 16:34:26','YYYY-MM-DD HH24:MI:SS'),100,'80','Immunglobulintherapie')
;

-- 2021-04-26T13:34:27.121Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542461 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:34:44.932Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542462,TO_TIMESTAMP('2021-04-26 16:34:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Laryngektomie',TO_TIMESTAMP('2021-04-26 16:34:44','YYYY-MM-DD HH24:MI:SS'),100,'81','Laryngektomie')
;

-- 2021-04-26T13:34:44.983Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542462 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:35:02.647Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542463,TO_TIMESTAMP('2021-04-26 16:35:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Schmerztherapie_Pumpe_mobil',TO_TIMESTAMP('2021-04-26 16:35:02','YYYY-MM-DD HH24:MI:SS'),100,'82','Schmerztherapie_Pumpe_mobil')
;

-- 2021-04-26T13:35:02.690Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542463 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:35:19.968Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542464,TO_TIMESTAMP('2021-04-26 16:35:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Haemophilie',TO_TIMESTAMP('2021-04-26 16:35:19','YYYY-MM-DD HH24:MI:SS'),100,'83','Haemophilie')
;

-- 2021-04-26T13:35:20.008Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542464 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:35:39.010Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542465,TO_TIMESTAMP('2021-04-26 16:35:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lungenhochdruck_PAH',TO_TIMESTAMP('2021-04-26 16:35:38','YYYY-MM-DD HH24:MI:SS'),100,'84','Lungenhochdruck_PAH')
;

-- 2021-04-26T13:35:39.051Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542465 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:35:56.559Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542466,TO_TIMESTAMP('2021-04-26 16:35:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Movy',TO_TIMESTAMP('2021-04-26 16:35:56','YYYY-MM-DD HH24:MI:SS'),100,'85','Movy')
;

-- 2021-04-26T13:35:56.601Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542466 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:36:12.228Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542467,TO_TIMESTAMP('2021-04-26 16:36:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hizentra',TO_TIMESTAMP('2021-04-26 16:36:11','YYYY-MM-DD HH24:MI:SS'),100,'86','Hizentra')
;

-- 2021-04-26T13:36:12.269Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542467 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:36:26.914Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542468,TO_TIMESTAMP('2021-04-26 16:36:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Schwerkraft_RH',TO_TIMESTAMP('2021-04-26 16:36:26','YYYY-MM-DD HH24:MI:SS'),100,'87','Schwerkraft_RH')
;

-- 2021-04-26T13:36:26.956Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542468 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:36:45.236Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541299,542469,TO_TIMESTAMP('2021-04-26 16:36:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pumpe_mobil_RH',TO_TIMESTAMP('2021-04-26 16:36:44','YYYY-MM-DD HH24:MI:SS'),100,'88','Pumpe_mobil_RH')
;

-- 2021-04-26T13:36:45.277Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542469 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-04-26T13:39:13.903Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541299,Updated=TO_TIMESTAMP('2021-04-26 16:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573599
;

-- 2021-04-26T13:40:07.990Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_therapytype','TherapyType','VARCHAR(255)',null,null)
;

-- 2021-04-26T13:42:24.604Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573625,579038,0,30,541625,'Alberta_PrescriptionRequest_ID',TO_TIMESTAMP('2021-04-26 16:42:23','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Alberta Prescription Request',0,0,TO_TIMESTAMP('2021-04-26 16:42:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-26T13:42:24.648Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573625 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-26T13:42:24.787Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579038) 
;

-- 2021-04-26T13:42:36.884Z
-- URL zum Konzept
ALTER TABLE Alberta_PrescriptionRequest_TherapyType ADD CONSTRAINT AlbertaPrescriptionRequest_AlbertaPrescriptionRequestTherapyType FOREIGN KEY (Alberta_PrescriptionRequest_ID) REFERENCES public.Alberta_PrescriptionRequest DEFERRABLE INITIALLY DEFERRED
;

-- 2021-04-26T13:50:40.090Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19, IsUpdateable='N',Updated=TO_TIMESTAMP('2021-04-26 16:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573625
;

-- 2021-04-26T13:51:31.453Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=573625, IsInsertRecord='Y', Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2021-04-26 16:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543842
;

-- 2021-04-26T13:53:35.652Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573591,644035,0,543842,TO_TIMESTAMP('2021-04-26 16:53:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-04-26 16:53:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:53:35.698Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T13:53:35.742Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-04-26T13:53:35.897Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644035
;

-- 2021-04-26T13:53:35.941Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644035)
;

-- 2021-04-26T13:53:36.550Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573592,644036,0,543842,TO_TIMESTAMP('2021-04-26 16:53:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-04-26 16:53:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:53:36.593Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T13:53:36.635Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-04-26T13:53:36.758Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644036
;

-- 2021-04-26T13:53:36.798Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644036)
;

-- 2021-04-26T13:53:37.381Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573595,644037,0,543842,TO_TIMESTAMP('2021-04-26 16:53:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-04-26 16:53:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:53:37.427Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T13:53:37.467Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-04-26T13:53:37.606Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644037
;

-- 2021-04-26T13:53:37.649Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644037)
;

-- 2021-04-26T13:53:38.246Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573598,644038,0,543842,TO_TIMESTAMP('2021-04-26 16:53:37','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N',' Alberta Prescription Therapy Type',TO_TIMESTAMP('2021-04-26 16:53:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:53:38.288Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T13:53:38.330Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579052) 
;

-- 2021-04-26T13:53:38.377Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644038
;

-- 2021-04-26T13:53:38.416Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644038)
;

-- 2021-04-26T13:53:39.006Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573599,644039,0,543842,TO_TIMESTAMP('2021-04-26 16:53:38','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Therapy type',TO_TIMESTAMP('2021-04-26 16:53:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:53:39.048Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T13:53:39.089Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578967) 
;

-- 2021-04-26T13:53:39.135Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644039
;

-- 2021-04-26T13:53:39.175Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644039)
;

-- 2021-04-26T13:53:39.796Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573625,644040,0,543842,TO_TIMESTAMP('2021-04-26 16:53:39','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Alberta Prescription Request',TO_TIMESTAMP('2021-04-26 16:53:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:53:39.842Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-26T13:53:39.888Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579038) 
;

-- 2021-04-26T13:53:39.938Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644040
;

-- 2021-04-26T13:53:39.985Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644040)
;

-- 2021-04-26T13:54:28.011Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543842,542996,TO_TIMESTAMP('2021-04-26 16:54:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 16:54:27','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-04-26T13:54:28.054Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542996 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-04-26T13:55:03.261Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543777,542996,TO_TIMESTAMP('2021-04-26 16:55:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 16:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:55:23.045Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543777,545689,TO_TIMESTAMP('2021-04-26 16:55:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-04-26 16:55:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:56:07.311Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644039,0,543842,545689,583815,'F',TO_TIMESTAMP('2021-04-26 16:56:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Therapy type',10,0,0,TO_TIMESTAMP('2021-04-26 16:56:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:56:23.620Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644038,0,543842,545689,583816,'F',TO_TIMESTAMP('2021-04-26 16:56:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,' Alberta Prescription Therapy Type',20,0,0,TO_TIMESTAMP('2021-04-26 16:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:56:40.543Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644037,0,543842,545689,583817,'F',TO_TIMESTAMP('2021-04-26 16:56:40','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',30,0,0,TO_TIMESTAMP('2021-04-26 16:56:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:56:56.794Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644036,0,543842,545689,583818,'F',TO_TIMESTAMP('2021-04-26 16:56:56','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',40,0,0,TO_TIMESTAMP('2021-04-26 16:56:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:57:13.063Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644035,0,543842,545689,583819,'F',TO_TIMESTAMP('2021-04-26 16:57:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2021-04-26 16:57:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T13:58:20.146Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementType='L', Labels_Selector_Field_ID=644039, Labels_Tab_ID=543842,Updated=TO_TIMESTAMP('2021-04-26 16:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583802
;

-- 2021-04-26T14:04:22.722Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=573590,Updated=TO_TIMESTAMP('2021-04-26 17:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543840
;

-- 2021-04-26T14:06:20.632Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543840,542997,TO_TIMESTAMP('2021-04-26 17:06:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 17:06:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-04-26T14:06:20.720Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542997 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-04-26T14:06:32.576Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543778,542997,TO_TIMESTAMP('2021-04-26 17:06:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 17:06:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:06:43.315Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543778,545690,TO_TIMESTAMP('2021-04-26 17:06:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2021-04-26 17:06:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:07:16.294Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644025,0,543840,545690,583820,'F',TO_TIMESTAMP('2021-04-26 17:07:15','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2021-04-26 17:07:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:07:32.364Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644026,0,543840,545690,583821,'F',TO_TIMESTAMP('2021-04-26 17:07:31','YYYY-MM-DD HH24:MI:SS'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',20,0,0,TO_TIMESTAMP('2021-04-26 17:07:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:08:08.991Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644027,0,543840,545690,583822,'F',TO_TIMESTAMP('2021-04-26 17:08:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verpackungseinheit',30,0,0,TO_TIMESTAMP('2021-04-26 17:08:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:09:17.335Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644029,0,543840,545690,583823,'F',TO_TIMESTAMP('2021-04-26 17:09:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zeile Aktualisiert',40,0,0,TO_TIMESTAMP('2021-04-26 17:09:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:09:36.564Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644028,0,543840,545690,583824,'F',TO_TIMESTAMP('2021-04-26 17:09:36','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','N','N','N',0,'Notiz',50,0,0,TO_TIMESTAMP('2021-04-26 17:09:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:10:34.147Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptzeilen', PrintName='Rezeptzeilen',Updated=TO_TIMESTAMP('2021-04-26 17:10:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579050 AND AD_Language='de_CH'
;

-- 2021-04-26T14:10:34.204Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579050,'de_CH') 
;

-- 2021-04-26T14:10:45.633Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rezeptzeilen', PrintName='Rezeptzeilen',Updated=TO_TIMESTAMP('2021-04-26 17:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579050 AND AD_Language='de_DE'
;

-- 2021-04-26T14:10:45.678Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579050,'de_DE') 
;

-- 2021-04-26T14:10:45.773Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579050,'de_DE') 
;

-- 2021-04-26T14:10:45.816Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Alberta_PrescriptionRequest_Line_ID', Name='Rezeptzeilen', Description=NULL, Help=NULL WHERE AD_Element_ID=579050
;

-- 2021-04-26T14:10:45.858Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Alberta_PrescriptionRequest_Line_ID', Name='Rezeptzeilen', Description=NULL, Help=NULL, AD_Element_ID=579050 WHERE UPPER(ColumnName)='ALBERTA_PRESCRIPTIONREQUEST_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T14:10:45.902Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Alberta_PrescriptionRequest_Line_ID', Name='Rezeptzeilen', Description=NULL, Help=NULL WHERE AD_Element_ID=579050 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T14:10:45.945Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Rezeptzeilen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579050) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579050)
;

-- 2021-04-26T14:10:46.033Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Rezeptzeilen', Name='Rezeptzeilen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579050)
;

-- 2021-04-26T14:10:46.074Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Rezeptzeilen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579050
;

-- 2021-04-26T14:10:46.121Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Rezeptzeilen', Description=NULL, Help=NULL WHERE AD_Element_ID = 579050
;

-- 2021-04-26T14:10:46.163Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Rezeptzeilen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579050
;

-- 2021-04-26T14:11:01.454Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Prescription lines', PrintName='Prescription lines',Updated=TO_TIMESTAMP('2021-04-26 17:11:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579050 AND AD_Language='en_US'
;

-- 2021-04-26T14:11:01.494Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579050,'en_US') 
;

-- 2021-04-26T14:11:12.022Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Rezeptzeilen', PrintName='Rezeptzeilen',Updated=TO_TIMESTAMP('2021-04-26 17:11:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579050 AND AD_Language='nl_NL'
;

-- 2021-04-26T14:11:12.062Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579050,'nl_NL') 
;

-- 2021-04-26T14:16:19.687Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2021-04-26 17:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573585
;

-- 2021-04-26T14:16:40.480Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-04-26 17:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573585
;

-- 2021-04-26T14:16:47.603Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-04-26T14:16:47.660Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','M_Product_ID',null,'NOT NULL',null)
;

-- 2021-04-26T14:17:03.169Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-04-26 17:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573586
;

-- 2021-04-26T14:17:11.711Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','Qty','NUMERIC',null,null)
;

-- 2021-04-26T14:17:11.758Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','Qty',null,'NOT NULL',null)
;

-- 2021-04-26T14:20:15.725Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543840,542998,TO_TIMESTAMP('2021-04-26 17:20:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-04-26 17:20:15','YYYY-MM-DD HH24:MI:SS'),100,'advancedEdit')
;

-- 2021-04-26T14:20:15.768Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542998 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-04-26T14:20:24.125Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543779,542998,TO_TIMESTAMP('2021-04-26 17:20:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-26 17:20:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:20:34.749Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543779,545691,TO_TIMESTAMP('2021-04-26 17:20:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2021-04-26 17:20:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:22:24.115Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644024,0,543840,545691,583825,'F',TO_TIMESTAMP('2021-04-26 17:22:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'External ID',10,0,0,TO_TIMESTAMP('2021-04-26 17:22:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:25:07.744Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579057,0,TO_TIMESTAMP('2021-04-26 17:25:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Annotation','Annotation',TO_TIMESTAMP('2021-04-26 17:25:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T14:25:07.805Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579057 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T14:25:23.817Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anmerkung', PrintName='Anmerkung',Updated=TO_TIMESTAMP('2021-04-26 17:25:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579057 AND AD_Language='de_CH'
;

-- 2021-04-26T14:25:23.862Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579057,'de_CH') 
;

-- 2021-04-26T14:25:31.192Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anmerkung', PrintName='Anmerkung',Updated=TO_TIMESTAMP('2021-04-26 17:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579057 AND AD_Language='de_DE'
;

-- 2021-04-26T14:25:31.238Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579057,'de_DE') 
;

-- 2021-04-26T14:25:31.331Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579057,'de_DE') 
;

-- 2021-04-26T14:25:31.371Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Anmerkung', Description=NULL, Help=NULL WHERE AD_Element_ID=579057
;

-- 2021-04-26T14:25:31.413Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Anmerkung', Description=NULL, Help=NULL WHERE AD_Element_ID=579057 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T14:25:31.452Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Anmerkung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579057) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579057)
;

-- 2021-04-26T14:25:31.502Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Anmerkung', Name='Anmerkung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579057)
;

-- 2021-04-26T14:25:31.549Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Anmerkung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579057
;

-- 2021-04-26T14:25:31.594Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Anmerkung', Description=NULL, Help=NULL WHERE AD_Element_ID = 579057
;

-- 2021-04-26T14:25:31.638Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Anmerkung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579057
;

-- 2021-04-26T14:25:36.745Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 17:25:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579057 AND AD_Language='en_US'
;

-- 2021-04-26T14:25:36.789Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579057,'en_US') 
;

-- 2021-04-26T14:25:41.888Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Anmerkung', PrintName='Anmerkung',Updated=TO_TIMESTAMP('2021-04-26 17:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579057 AND AD_Language='nl_NL'
;

-- 2021-04-26T14:25:41.935Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579057,'nl_NL') 
;

-- 2021-04-26T14:26:00.790Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=578978, Description=NULL, Help=NULL, Name='Annotation',Updated=TO_TIMESTAMP('2021-04-26 17:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=644028
;

-- 2021-04-26T14:26:00.833Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578978) 
;

-- 2021-04-26T14:26:00.881Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644028
;

-- 2021-04-26T14:26:00.921Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644028)
;

-- 2021-04-26T14:30:46.678Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=578978, Description=NULL, Help=NULL, Name='Annotation',Updated=TO_TIMESTAMP('2021-04-26 17:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=644015
;

-- 2021-04-26T14:30:46.721Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578978) 
;

-- 2021-04-26T14:30:46.769Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644015
;

-- 2021-04-26T14:30:46.810Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644015)
;

-- 2021-04-26T14:34:15.226Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Therapytyp', PrintName='Therapytyp',Updated=TO_TIMESTAMP('2021-04-26 17:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579053 AND AD_Language='de_CH'
;

-- 2021-04-26T14:34:15.269Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579053,'de_CH') 
;

-- 2021-04-26T14:34:25.585Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Therapytyp', PrintName='Therapytyp',Updated=TO_TIMESTAMP('2021-04-26 17:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579053 AND AD_Language='de_DE'
;

-- 2021-04-26T14:34:25.626Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579053,'de_DE') 
;

-- 2021-04-26T14:34:25.719Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579053,'de_DE') 
;

-- 2021-04-26T14:34:25.765Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='TherapyTypeIds', Name='Therapytyp', Description=NULL, Help=NULL WHERE AD_Element_ID=579053
;

-- 2021-04-26T14:34:25.808Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TherapyTypeIds', Name='Therapytyp', Description=NULL, Help=NULL, AD_Element_ID=579053 WHERE UPPER(ColumnName)='THERAPYTYPEIDS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-26T14:34:25.851Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TherapyTypeIds', Name='Therapytyp', Description=NULL, Help=NULL WHERE AD_Element_ID=579053 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T14:34:25.891Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Therapytyp', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579053) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579053)
;

-- 2021-04-26T14:34:25.971Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Therapytyp', Name='Therapytyp' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579053)
;

-- 2021-04-26T14:34:26.013Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Therapytyp', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579053
;

-- 2021-04-26T14:34:26.055Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Therapytyp', Description=NULL, Help=NULL WHERE AD_Element_ID = 579053
;

-- 2021-04-26T14:34:26.096Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Therapytyp', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579053
;

-- 2021-04-26T14:34:36.979Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Therapy Type', PrintName='Therapy Type',Updated=TO_TIMESTAMP('2021-04-26 17:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579053 AND AD_Language='en_US'
;

-- 2021-04-26T14:34:37.024Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579053,'en_US') 
;

-- 2021-04-26T14:34:50.427Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Therapytyp', PrintName='Therapytyp',Updated=TO_TIMESTAMP('2021-04-26 17:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579053 AND AD_Language='nl_NL'
;

-- 2021-04-26T14:34:50.467Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579053,'nl_NL') 
;

-- 2021-04-26T14:36:39.739Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 17:36:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579053 AND AD_Language='de_DE'
;

-- 2021-04-26T14:36:39.781Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579053,'de_DE') 
;

-- 2021-04-26T14:36:39.886Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579053,'de_DE') 
;

-- 2021-04-26T15:00:58.131Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2021-04-26 18:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573549
;

-- 2021-04-26T15:01:01.741Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-04-26 18:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573573
;

-- 2021-04-26T15:01:04.739Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-04-26 18:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573568
;

-- 2021-04-26T15:01:06.917Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-04-26 18:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573569
;

-- 2021-04-26T15:01:08.965Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2021-04-26 18:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573558
;

-- 2021-04-26T15:01:10.935Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2021-04-26 18:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573570
;

-- 2021-04-26T15:01:13.070Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2021-04-26 18:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573562
;

-- 2021-04-26T15:01:14.835Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2021-04-26 18:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573600
;

-- 2021-04-26T15:03:00.479Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-04-26 18:03:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573600
;

-- 2021-04-26T15:03:02.958Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2021-04-26 18:03:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573568
;

-- 2021-04-26T15:03:05.331Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2021-04-26 18:03:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573569
;

-- 2021-04-26T15:03:07.899Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2021-04-26 18:03:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573558
;

-- 2021-04-26T15:03:10.227Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2021-04-26 18:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573570
;

-- 2021-04-26T15:03:12.494Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2021-04-26 18:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573562
;

-- 2021-04-26T15:03:14.750Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2021-04-26 18:03:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573573
;

-- 2021-04-26T15:03:17.222Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2021-04-26 18:03:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573549
;

-- 2021-04-26T15:06:40.939Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579058,0,TO_TIMESTAMP('2021-04-26 18:06:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delviery Date','Delviery Date',TO_TIMESTAMP('2021-04-26 18:06:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-26T15:06:40.983Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579058 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-26T15:07:06.891Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Lieferdatum der Bestellung, auf der das Rezept basiert', IsTranslated='Y', Name='Lieferdatum', PrintName='Lieferdatum',Updated=TO_TIMESTAMP('2021-04-26 18:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579058 AND AD_Language='de_CH'
;

-- 2021-04-26T15:07:06.932Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579058,'de_CH') 
;

-- 2021-04-26T15:07:17.995Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Lieferdatum der Bestellung, auf der das Rezept basiert', IsTranslated='Y', Name='Lieferdatum', PrintName='Lieferdatum',Updated=TO_TIMESTAMP('2021-04-26 18:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579058 AND AD_Language='de_DE'
;

-- 2021-04-26T15:07:18.040Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579058,'de_DE') 
;

-- 2021-04-26T15:07:18.132Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579058,'de_DE') 
;

-- 2021-04-26T15:07:18.173Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Lieferdatum', Description='Lieferdatum der Bestellung, auf der das Rezept basiert', Help=NULL WHERE AD_Element_ID=579058
;

-- 2021-04-26T15:07:18.214Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Lieferdatum', Description='Lieferdatum der Bestellung, auf der das Rezept basiert', Help=NULL WHERE AD_Element_ID=579058 AND IsCentrallyMaintained='Y'
;

-- 2021-04-26T15:07:18.254Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Lieferdatum', Description='Lieferdatum der Bestellung, auf der das Rezept basiert', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579058) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579058)
;

-- 2021-04-26T15:07:18.358Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferdatum', Name='Lieferdatum' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579058)
;

-- 2021-04-26T15:07:18.399Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lieferdatum', Description='Lieferdatum der Bestellung, auf der das Rezept basiert', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579058
;

-- 2021-04-26T15:07:18.441Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Lieferdatum', Description='Lieferdatum der Bestellung, auf der das Rezept basiert', Help=NULL WHERE AD_Element_ID = 579058
;

-- 2021-04-26T15:07:18.489Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Lieferdatum', Description = 'Lieferdatum der Bestellung, auf der das Rezept basiert', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579058
;

-- 2021-04-26T15:07:30.434Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Lieferdatum der Bestellung, auf der das Rezept basiert', Name='Lieferdatum', PrintName='Lieferdatum',Updated=TO_TIMESTAMP('2021-04-26 18:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579058 AND AD_Language='nl_NL'
;

-- 2021-04-26T15:07:30.481Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579058,'nl_NL') 
;

-- 2021-04-26T15:07:41.417Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Delviery date of the prescription''s order.',Updated=TO_TIMESTAMP('2021-04-26 18:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579058 AND AD_Language='en_US'
;

-- 2021-04-26T15:07:41.459Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579058,'en_US') 
;

-- 2021-04-26T15:07:44.713Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-26 18:07:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579058 AND AD_Language='en_US'
;

-- 2021-04-26T15:07:44.756Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579058,'en_US') 
;

-- 2021-04-27T05:53:49.313Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541628,'N',TO_TIMESTAMP('2021-04-27 08:53:47','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Alberta Prescription Request Accounting','NP','L','Alberta_PrescriptionRequest_Accounting','DTI',TO_TIMESTAMP('2021-04-27 08:53:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T05:53:49.354Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541628 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-04-27T05:53:49.834Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555378,TO_TIMESTAMP('2021-04-27 08:53:49','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table Alberta_PrescriptionRequest_Accounting',1,'Y','N','Y','Y','Alberta_PrescriptionRequest_Accounting','N',1000000,TO_TIMESTAMP('2021-04-27 08:53:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T05:53:49.959Z
-- URL zum Konzept
CREATE SEQUENCE ALBERTA_PRESCRIPTIONREQUEST_ACCOUNTING_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-04-27T05:54:20.255Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573626,102,0,19,541628,'AD_Client_ID',TO_TIMESTAMP('2021-04-27 08:54:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-04-27 08:54:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:20.294Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573626 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:20.374Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-04-27T05:54:24.150Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573627,113,0,30,541628,'AD_Org_ID',TO_TIMESTAMP('2021-04-27 08:54:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-04-27 08:54:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:24.188Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:24.266Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-04-27T05:54:27.294Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573628,245,0,16,541628,'Created',TO_TIMESTAMP('2021-04-27 08:54:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-04-27 08:54:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:27.336Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:27.414Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-04-27T05:54:30.630Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573629,246,0,18,110,541628,'CreatedBy',TO_TIMESTAMP('2021-04-27 08:54:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-04-27 08:54:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:30.668Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:30.747Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-04-27T05:54:33.618Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573630,348,0,20,541628,'IsActive',TO_TIMESTAMP('2021-04-27 08:54:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-04-27 08:54:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:33.661Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573630 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:33.744Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-04-27T05:54:36.745Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573631,607,0,16,541628,'Updated',TO_TIMESTAMP('2021-04-27 08:54:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-04-27 08:54:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:36.785Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573631 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:36.863Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-04-27T05:54:39.574Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573632,608,0,18,110,541628,'UpdatedBy',TO_TIMESTAMP('2021-04-27 08:54:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-04-27 08:54:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:39.616Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573632 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:39.687Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-04-27T05:54:42.513Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579059,0,'Alberta_PrescriptionRequest_Accounting_ID',TO_TIMESTAMP('2021-04-27 08:54:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Alberta Prescription Request Accounting','Alberta Prescription Request Accounting',TO_TIMESTAMP('2021-04-27 08:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T05:54:42.549Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579059 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-27T05:54:44.803Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573633,579059,0,13,541628,'Alberta_PrescriptionRequest_Accounting_ID',TO_TIMESTAMP('2021-04-27 08:54:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Alberta Prescription Request Accounting',0,TO_TIMESTAMP('2021-04-27 08:54:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:54:44.842Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573633 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:54:44.919Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579059) 
;

-- 2021-04-27T05:56:53.629Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573634,579038,0,19,541628,'Alberta_PrescriptionRequest_ID',TO_TIMESTAMP('2021-04-27 08:56:52','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Alberta Prescription Request',0,0,TO_TIMESTAMP('2021-04-27 08:56:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:56:53.668Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573634 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:56:53.746Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579038) 
;

-- 2021-04-27T05:58:03.356Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573635,579043,0,17,541297,541628,'AccountingMonths',TO_TIMESTAMP('2021-04-27 08:58:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versorgungs-/Abrechnungsmonate',0,0,TO_TIMESTAMP('2021-04-27 08:58:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T05:58:03.394Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573635 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T05:58:03.470Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579043) 
;

-- 2021-04-27T06:02:03.960Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_accounting','AccountingMonths','VARCHAR(2)',null,null)
;

-- 2021-04-27T06:04:36.933Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573635,579059,0,543844,541628,541117,'Y',TO_TIMESTAMP('2021-04-27 09:04:36','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Alberta_PrescriptionRequest_Accounting','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Alberta Prescription Request Accounting','N',50,0,TO_TIMESTAMP('2021-04-27 09:04:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:04:36.971Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543844 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-04-27T06:04:37.009Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579059) 
;

-- 2021-04-27T06:04:37.051Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543844)
;

-- 2021-04-27T06:04:53.767Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573626,644042,0,543844,TO_TIMESTAMP('2021-04-27 09:04:53','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-04-27 09:04:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:04:53.809Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-27T06:04:53.853Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-04-27T06:04:54.009Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644042
;

-- 2021-04-27T06:04:54.045Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644042)
;

-- 2021-04-27T06:04:54.576Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573627,644043,0,543844,TO_TIMESTAMP('2021-04-27 09:04:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-04-27 09:04:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:04:54.612Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-27T06:04:54.650Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-04-27T06:04:54.784Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644043
;

-- 2021-04-27T06:04:54.822Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644043)
;

-- 2021-04-27T06:04:55.420Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573630,644044,0,543844,TO_TIMESTAMP('2021-04-27 09:04:54','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-04-27 09:04:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:04:55.458Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-27T06:04:55.497Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-04-27T06:04:55.651Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644044
;

-- 2021-04-27T06:04:55.687Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644044)
;

-- 2021-04-27T06:04:56.229Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573633,644045,0,543844,TO_TIMESTAMP('2021-04-27 09:04:55','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Alberta Prescription Request Accounting',TO_TIMESTAMP('2021-04-27 09:04:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:04:56.267Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-27T06:04:56.304Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579059) 
;

-- 2021-04-27T06:04:56.345Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644045
;

-- 2021-04-27T06:04:56.382Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644045)
;

-- 2021-04-27T06:04:56.916Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573634,644046,0,543844,TO_TIMESTAMP('2021-04-27 09:04:56','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Alberta Prescription Request',TO_TIMESTAMP('2021-04-27 09:04:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:04:56.957Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-27T06:04:56.995Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579038) 
;

-- 2021-04-27T06:04:57.039Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644046
;

-- 2021-04-27T06:04:57.077Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644046)
;

-- 2021-04-27T06:04:57.634Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573635,644047,0,543844,TO_TIMESTAMP('2021-04-27 09:04:57','YYYY-MM-DD HH24:MI:SS'),100,2,'D','Y','N','N','N','N','N','N','N','Versorgungs-/Abrechnungsmonate',TO_TIMESTAMP('2021-04-27 09:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:04:57.673Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-27T06:04:57.709Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579043) 
;

-- 2021-04-27T06:04:57.759Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644047
;

-- 2021-04-27T06:04:57.797Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644047)
;

-- 2021-04-27T06:05:06.197Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2021-04-27 09:05:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543844
;

-- 2021-04-27T06:05:25.657Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543844,542999,TO_TIMESTAMP('2021-04-27 09:05:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-27 09:05:25','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-04-27T06:05:25.694Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=542999 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-04-27T06:05:37.363Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543780,542999,TO_TIMESTAMP('2021-04-27 09:05:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-04-27 09:05:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:05:46.692Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543780,545692,TO_TIMESTAMP('2021-04-27 09:05:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','deafult',10,TO_TIMESTAMP('2021-04-27 09:05:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:06:20.150Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644047,0,543844,545692,583826,'F',TO_TIMESTAMP('2021-04-27 09:06:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Versorgungs-/Abrechnungsmonate',10,0,0,TO_TIMESTAMP('2021-04-27 09:06:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:06:38.421Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644043,0,543844,545692,583827,'F',TO_TIMESTAMP('2021-04-27 09:06:37','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2021-04-27 09:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:06:54.803Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644042,0,543844,545692,583828,'F',TO_TIMESTAMP('2021-04-27 09:06:54','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2021-04-27 09:06:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:07:20.100Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,644046,0,543844,545692,583829,'F',TO_TIMESTAMP('2021-04-27 09:07:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'Alberta Prescription Request',40,0,0,TO_TIMESTAMP('2021-04-27 09:07:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:07:41.385Z
-- URL zum Konzept
UPDATE AD_Tab SET Parent_Column_ID=573555,Updated=TO_TIMESTAMP('2021-04-27 09:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543844
;

-- 2021-04-27T06:08:38.140Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementType='L', Labels_Selector_Field_ID=644047, Labels_Tab_ID=543844,Updated=TO_TIMESTAMP('2021-04-27 09:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583801
;

-- 2021-04-27T06:15:28.929Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, AD_Reference_Value_ID=NULL, FieldLength=2,Updated=TO_TIMESTAMP('2021-04-27 09:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573567
;

-- 2021-04-27T06:15:40.310Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest','AccountingMonths','VARCHAR(2)',null,null)
;

-- 2021-04-27T06:22:31.277Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@#AD_User_ID/-1@',Updated=TO_TIMESTAMP('2021-04-27 09:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573571
;

-- 2021-04-27T06:25:42.764Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest','DeliveryDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-04-27T06:32:33.788Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=583801
;

-- 2021-04-27T06:32:33.861Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644011
;

-- 2021-04-27T06:32:33.898Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=644011
;

-- 2021-04-27T06:32:34.124Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=644011
;

-- 2021-04-27T06:32:47.890Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=573567
;

-- 2021-04-27T06:32:48.120Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=573567
;

-- 2021-04-27T06:33:06.751Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2021-04-27T06:33:41.697Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,573636,543198,0,11,541622,'DLM_Level',TO_TIMESTAMP('2021-04-27 09:33:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',14,'Y','N','N','N','N','N','N','N','N','Y','DLM_Level',TO_TIMESTAMP('2021-04-27 09:33:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T06:33:41.744Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573636 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T06:33:41.825Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543198) 
;

-- 2021-04-27T06:33:44.501Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,573637,543190,0,11,541622,'DLM_Partition_ID',TO_TIMESTAMP('2021-04-27 09:33:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',14,'Y','N','N','N','N','N','N','N','N','Y','Partition',TO_TIMESTAMP('2021-04-27 09:33:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T06:33:44.542Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573637 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T06:33:44.623Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543190) 
;

-- 2021-04-27T06:33:44.732Z
-- URL zum Konzept
UPDATE AD_Table SET IsDLM='Y',Updated=TO_TIMESTAMP('2021-04-27 09:33:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541622
;

-- 2021-04-27T06:38:32.583Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573638,579043,0,10,541622,'AccountingMonths',TO_TIMESTAMP('2021-04-27 09:38:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versorgungs-/Abrechnungsmonate',0,0,TO_TIMESTAMP('2021-04-27 09:38:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-27T06:38:32.623Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573638 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-27T06:38:32.702Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579043) 
;

-- 2021-04-27T06:38:45.750Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest','AccountingMonths','VARCHAR(255)',null,null)
;

-- 2021-04-27T06:39:57.770Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573638,644048,579043,0,543839,0,TO_TIMESTAMP('2021-04-27 09:39:57','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','N','N','N','N','N','N','Versorgungs-/Abrechnungsmonate',30,30,0,1,1,TO_TIMESTAMP('2021-04-27 09:39:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:39:57.810Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=644048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-04-27T06:39:57.848Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579043) 
;

-- 2021-04-27T06:39:57.898Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644048
;

-- 2021-04-27T06:39:57.940Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644048)
;

-- 2021-04-27T06:41:23.798Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,543839,545684,583830,'L',TO_TIMESTAMP('2021-04-27 09:41:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',644047,543844,0,'Versorgungs-/Abrechnungsmonate',60,0,0,TO_TIMESTAMP('2021-04-27 09:41:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T06:42:08.790Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=573634, Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2021-04-27 09:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543844
;

-- 2021-04-27T06:50:00.312Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2021-04-27 09:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-27T06:50:08.177Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','ArticleUnit','VARCHAR(10)',null,null)
;

-- 2021-04-27T06:52:59.871Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=573116,Updated=TO_TIMESTAMP('2021-04-27 09:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541298
;

-- 2021-04-27T06:56:27.396Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541300,TO_TIMESTAMP('2021-04-27 09:56:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_PackingUnit_Validation',TO_TIMESTAMP('2021-04-27 09:56:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-04-27T06:56:27.436Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541300 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-04-27T06:59:04.131Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,573114,573113,0,541300,541598,TO_TIMESTAMP('2021-04-27 09:59:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-04-27 09:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-27T07:00:14.184Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540165,Updated=TO_TIMESTAMP('2021-04-27 10:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-27T07:02:18.649Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=573113,Updated=TO_TIMESTAMP('2021-04-27 10:02:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541298
;

-- 2021-04-27T07:03:32.501Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541298,Updated=TO_TIMESTAMP('2021-04-27 10:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-27T07:03:40.170Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','ArticleUnit','VARCHAR(10)',null,null)
;

-- 2021-04-27T07:14:00.673Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-04-27 10:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583794
;

-- 2021-04-27T07:14:00.822Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-04-27 10:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583796
;

-- 2021-04-27T07:14:01.011Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-04-27 10:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583799
;

-- 2021-04-27T07:14:01.169Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-04-27 10:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583798
;

-- 2021-04-27T07:14:01.355Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-04-27 10:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583803
;

-- 2021-04-27T07:14:01.515Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-04-27 10:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583800
;

-- 2021-04-27T07:14:01.665Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-04-27 10:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583810
;

-- 2021-04-27T07:14:01.827Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-04-27 10:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583813
;

-- 2021-04-27T10:00:19.852Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=573636
;

-- 2021-04-27T10:00:20.056Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=573636
;

-- 2021-04-27T10:00:25.942Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=573637
;

-- 2021-04-27T10:00:26.142Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=573637
;

-- 2021-04-27T10:09:00.796Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=255,Updated=TO_TIMESTAMP('2021-04-27 13:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-27T10:09:10.460Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','ArticleUnit','VARCHAR(255)',null,null)
;

-- 2021-04-27T11:34:24.725Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541280, FieldLength=3,Updated=TO_TIMESTAMP('2021-04-27 14:34:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573587
;

-- 2021-04-27T11:36:02.598Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_line','ArticleUnit','VARCHAR(3)',null,null)
;

-- 2021-04-27T11:39:37.955Z
-- URL zum Konzept
UPDATE AD_Menu SET IsCreateNew='N',Updated=TO_TIMESTAMP('2021-04-27 14:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541703
;
