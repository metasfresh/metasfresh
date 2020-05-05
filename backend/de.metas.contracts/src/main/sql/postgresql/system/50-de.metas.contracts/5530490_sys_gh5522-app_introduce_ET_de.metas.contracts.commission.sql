-- 2019-09-12T04:03:58.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540255,0,TO_TIMESTAMP('2019-09-12 06:03:58','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.contracts.refund','Y','Y','de.metas.contracts.refund.model','de.metas.contracts.refund','N',TO_TIMESTAMP('2019-09-12 06:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-12T04:04:30.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET EntityType='de.metas.contracts.refund',Updated=TO_TIMESTAMP('2019-09-12 06:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541106
;

-- 2019-09-12T04:05:04.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET EntityType='de.metas.contracts.refund',Updated=TO_TIMESTAMP('2019-09-12 06:05:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540980
;

-- 2019-09-12T04:05:27.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='de.metas.contracts.refund',Updated=TO_TIMESTAMP('2019-09-12 06:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541635
;

-- 2019-09-12T04:06:03.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_EntityType SET Description='Commission', ModelPackage='de.metas.contracts.commission.model', Name='de.metas.contracts.commission',Updated=TO_TIMESTAMP('2019-09-12 06:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_EntityType_ID=540005
;

update AD_EntityType Set EntityType='de.metas.contracts.commission' where (AD_EntityType_ID=540005);

-- 2019-09-12T04:08:51.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Einem Leergutveretrag unterliegende Produkte (z.B. Palletten) bei ihrer Annahme oder Abgabe nicht in Rechnung gestellt',Updated=TO_TIMESTAMP('2019-09-12 06:08:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540586
;

-- 2019-09-12T04:09:05.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Dem Leergutvertrag unterliegende Produkte (z.B. Palletten) bei ihrer Annahme oder Abgabe nicht in Rechnung gestellt',Updated=TO_TIMESTAMP('2019-09-12 06:09:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540586
;

-- 2019-09-12T04:10:27.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der abzurechnende Betrag von gelieferten Gütern hängt von einer Qualitäts-Stichprobe ab', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-12 06:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=540826
;

-- 2019-09-12T04:10:36.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der abzurechnende Betrag von gelieferten Gütern hängt von einer Qualitäts-Stichprobe ab.',Updated=TO_TIMESTAMP('2019-09-12 06:10:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=540826
;

-- 2019-09-12T04:10:41.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Der abzurechnende Betrag von gelieferten Gütern hängt von einer Qualitäts-Stichprobe ab.',Updated=TO_TIMESTAMP('2019-09-12 06:10:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=540826
;

-- 2019-09-12T04:10:56.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Der abzurechnende Betrag von gelieferten Gütern hängt von einer Qualitäts-Stichprobe ab.',Updated=TO_TIMESTAMP('2019-09-12 06:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=540826
;

-- 2019-09-12T04:13:31.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540271,542010,TO_TIMESTAMP('2019-09-12 06:13:31','YYYY-MM-DD HH24:MI:SS'),100,'Verkaufserlöse werden (zum Teil) an Provisionsempfänger ausgeschüttet.','de.metas.contracts','Y','Provision',TO_TIMESTAMP('2019-09-12 06:13:31','YYYY-MM-DD HH24:MI:SS'),100,'Commission','Commission')
;

-- 2019-09-12T04:13:31.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=542010 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-09-12T04:16:30.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='de.metas.contracts.commission',Updated=TO_TIMESTAMP('2019-09-12 06:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542010
;

-- 2019-09-12T04:17:07.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Sales revenue is (partially) distributed to commission receivers.', IsTranslated='Y', Name='Commission',Updated=TO_TIMESTAMP('2019-09-12 06:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542010
;

-- 2019-09-12T04:17:19.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-12 06:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542010
;

