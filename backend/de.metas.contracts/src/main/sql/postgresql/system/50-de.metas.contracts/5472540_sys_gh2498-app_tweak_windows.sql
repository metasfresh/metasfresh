-- 2017-09-25T10:32:08.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@Type_Conditions@=''Subscr''',Updated=TO_TIMESTAMP('2017-09-25 10:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540880
;

-- 2017-09-25T10:34:18.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=10.000000000000,Updated=TO_TIMESTAMP('2017-09-25 10:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560349
;

--
-- DropShip fields are not mandatory (e.g. pause records don't have one)
-- C_Subscription_Process tabs are read-only
--
-- 2017-09-25T16:57:16.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-25 16:57:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547290
;

-- 2017-09-25T16:58:20.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@EventType@=''DE''',Updated=TO_TIMESTAMP('2017-09-25 16:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547290
;

-- 2017-09-25T16:58:32.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_subscriptionprogress','DropShip_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2017-09-25T16:58:32.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_subscriptionprogress','DropShip_BPartner_ID',null,'NULL',null)
;

-- 2017-09-25T16:58:57.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='', MandatoryLogic='@EventType@=''DE''',Updated=TO_TIMESTAMP('2017-09-25 16:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=541679
;

-- 2017-09-25T16:59:06.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_subscriptionprogress','DropShip_Location_ID','NUMERIC(10)',null,null)
;

-- 2017-09-25T16:59:16.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-09-25 16:59:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=541679
;

-- 2017-09-25T16:59:19.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_subscriptionprogress','DropShip_Location_ID','NUMERIC(10)',null,null)
;

-- 2017-09-25T16:59:19.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_subscriptionprogress','DropShip_Location_ID',null,'NULL',null)
;

-- 2017-09-25T17:00:07.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=550469
;

-- 2017-09-25T17:00:19.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547290,560370,0,540423,TO_TIMESTAMP('2017-09-25 17:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to',10,'de.metas.flatrate','If empty the business partner will be shipped to.','Y','Y','Y','N','N','N','N','N','Lieferempfänger',TO_TIMESTAMP('2017-09-25 17:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-25T17:00:19.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-25T17:00:19.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,547299,560371,0,540423,TO_TIMESTAMP('2017-09-25 17:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Contact for drop shipment',10,'de.metas.flatrate','Y','Y','Y','N','N','N','N','N','Lieferkontakt',TO_TIMESTAMP('2017-09-25 17:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-25T17:00:19.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560371 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-25T17:00:33.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560370
;

-- 2017-09-25T17:00:39.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560371
;

-- 2017-09-25T17:01:19.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560356
;

-- 2017-09-25T17:01:21.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:01:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560354
;

-- 2017-09-25T17:01:23.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560357
;

-- 2017-09-25T17:01:49.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:01:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560359
;

-- 2017-09-25T17:01:53.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560358
;

-- 2017-09-25T17:01:56.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560349
;

-- 2017-09-25T17:01:58.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560351
;

-- 2017-09-25T17:02:04.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560348
;

-- 2017-09-25T17:02:07.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:02:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560347
;

-- 2017-09-25T17:02:13.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560352
;

-- 2017-09-25T17:02:19.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:02:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540880
;

-- 2017-09-25T17:02:34.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:02:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540864
;

-- 2017-09-25T17:02:52.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-09-25 17:02:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540423
;

