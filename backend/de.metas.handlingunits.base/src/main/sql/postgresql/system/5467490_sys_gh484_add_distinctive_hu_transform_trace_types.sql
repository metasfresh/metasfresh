-- 2017-07-07T23:53:20.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='TRANSFORM_LOAD', ValueName='TRANSFORM_LOAD',Updated=TO_TIMESTAMP('2017-07-07 23:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541285
;

-- 2017-07-07T23:54:06.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541286,540729,TO_TIMESTAMP('2017-07-07 23:54:06','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','HU-Gebindeänderung',TO_TIMESTAMP('2017-07-07 23:54:06','YYYY-MM-DD HH24:MI:SS'),100,'TRANSFORM_PARENT','TRANSFORM_PARENT')
;

-- 2017-07-07T23:54:06.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541286 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2017-07-07T23:54:17.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2017-07-07 23:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541286
;

-- 2017-07-07T23:57:15.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='HU-Gebinde-Änderung',ValueName='TRANSFORM_PARENT',Updated=TO_TIMESTAMP('2017-07-07 23:57:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541286
;
