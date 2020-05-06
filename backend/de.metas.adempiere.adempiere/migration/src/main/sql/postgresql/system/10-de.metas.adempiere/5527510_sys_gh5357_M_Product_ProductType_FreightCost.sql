-- 2019-07-17T11:59:13.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541986,270,TO_TIMESTAMP('2019-07-17 14:59:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FreightCost',TO_TIMESTAMP('2019-07-17 14:59:12','YYYY-MM-DD HH24:MI:SS'),100,'F','FreightCost')
;

-- 2019-07-17T11:59:13.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541986 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-07-17T11:59:18.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Frachtkosten',Updated=TO_TIMESTAMP('2019-07-17 14:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541986
;

-- 2019-07-17T11:59:27.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Freight Cost',Updated=TO_TIMESTAMP('2019-07-17 14:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541986
;

-- 2019-07-17T12:00:33.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540446,'ProductType=''F''',TO_TIMESTAMP('2019-07-17 15:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Produkte mit Produktart "Frachtkosten"','D','Y','M_Product Freight Cost','S',TO_TIMESTAMP('2019-07-17 15:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-17T12:02:26.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540446,Updated=TO_TIMESTAMP('2019-07-17 15:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7795
;

-- 2019-07-18T18:07:12.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2019-07-18 21:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7795
;

-- 2019-07-18T18:07:50.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540446,Updated=TO_TIMESTAMP('2019-07-18 21:07:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540286
;


