
-- 2022-12-08T13:34:07.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543357,540858,TO_TIMESTAMP('2022-12-08 15:34:07','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','ActiveCampaign',TO_TIMESTAMP('2022-12-08 15:34:07','YYYY-MM-DD HH24:MI:SS'),100,'ActiveCampaign','ActiveCampaign')
;

-- 2022-12-08T13:34:07.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543357 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-12-08T13:34:26.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='https://www.activecampaign.com', EntityType='de.metas.marketing.base',Updated=TO_TIMESTAMP('2022-12-08 15:34:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543357
;

-- 2022-12-08T13:34:53.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='https://www.activecampaign.com/',Updated=TO_TIMESTAMP('2022-12-08 15:34:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543357
;

-- 2022-12-08T13:34:55.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='https://www.activecampaign.com/',Updated=TO_TIMESTAMP('2022-12-08 15:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543357
;

-- 2022-12-08T13:34:56.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='https://www.activecampaign.com/',Updated=TO_TIMESTAMP('2022-12-08 15:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543357
;

-- 2022-12-08T13:34:58.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='https://www.activecampaign.com/',Updated=TO_TIMESTAMP('2022-12-08 15:34:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543357
;

-- 2022-12-08T13:42:10.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540295,0,TO_TIMESTAMP('2022-12-08 15:42:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','Y','Y','de.metas.marketing.activecampaign.model','de.metas.marketing.activecampaign','N',TO_TIMESTAMP('2022-12-08 15:42:10','YYYY-MM-DD HH24:MI:SS'),100)
;


