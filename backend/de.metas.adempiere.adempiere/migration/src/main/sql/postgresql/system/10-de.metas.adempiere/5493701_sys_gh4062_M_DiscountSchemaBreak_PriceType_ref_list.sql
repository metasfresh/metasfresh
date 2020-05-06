-- 2018-05-16T15:13:28.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,CreatedBy,EntityType,IsOrderByValue,AD_Reference_ID,ValidationType,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'D','N',540862,'L',0,'M_DiscountSchemaBreak_PriceType',100,TO_TIMESTAMP('2018-05-16 15:13:27','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-16 15:13:27','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-16T15:13:28.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540862 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-05-16T15:13:40.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540862,0,'Y',100,TO_TIMESTAMP('2018-05-16 15:13:40','YYYY-MM-DD HH24:MI:SS'),'N','D',541642,'None',0,'None',100,TO_TIMESTAMP('2018-05-16 15:13:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-16T15:13:40.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541642 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-16T15:14:49.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540862,0,'Y',100,TO_TIMESTAMP('2018-05-16 15:14:49','YYYY-MM-DD HH24:MI:SS'),'P','D',541643,'Pricing System',0,'Pricing System',100,TO_TIMESTAMP('2018-05-16 15:14:49','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-16T15:14:49.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541643 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-16T15:14:59.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,CreatedBy,Created,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540862,0,'Y',100,TO_TIMESTAMP('2018-05-16 15:14:59','YYYY-MM-DD HH24:MI:SS'),'F','D',541644,'Fixed',0,'Fixed',100,TO_TIMESTAMP('2018-05-16 15:14:59','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-16T15:14:59.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541644 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

