-- 2018-01-04T16:29:11.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Value,AD_Ref_List_ID,ValueName,Description,AD_Org_ID,Name,EntityType,UpdatedBy,Updated) VALUES (540808,0,'Y',TO_TIMESTAMP('2018-01-04 16:29:11','YYYY-MM-DD HH24:MI:SS'),100,'go',541524,'GO','Go! General Overnight Service',0,'GO','de.metas.shipper.gateway.go',100,TO_TIMESTAMP('2018-01-04 16:29:11','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-01-04T16:29:11.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541524 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

