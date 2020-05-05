-- 2019-07-08T16:03:09.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=5,Updated=TO_TIMESTAMP('2019-07-08 16:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556819
;

-- 2019-07-08T16:03:11.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_ui_element','WidgetSize','VARCHAR(5)',null,null)
;

-- 2019-07-08T16:03:58.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541973,540724,TO_TIMESTAMP('2019-07-08 16:03:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Extra Large',TO_TIMESTAMP('2019-07-08 16:03:57','YYYY-MM-DD HH24:MI:SS'),100,'XL','ExtraLarge')
;

-- 2019-07-08T16:03:58.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541973 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-07-08T16:04:24.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541974,540724,TO_TIMESTAMP('2019-07-08 16:04:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Extra Extra Large',TO_TIMESTAMP('2019-07-08 16:04:23','YYYY-MM-DD HH24:MI:SS'),100,'XXL','XXL')
;

-- 2019-07-08T16:04:24.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541974 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

