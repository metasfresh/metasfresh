-- 2018-10-04T18:18:56.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541736,540734,TO_TIMESTAMP('2018-10-04 18:18:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Storniert',TO_TIMESTAMP('2018-10-04 18:18:56','YYYY-MM-DD HH24:MI:SS'),100,'VO','Voided')
;

-- 2018-10-04T18:18:56.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541736 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-10-04T18:19:07.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-04 18:19:07','YYYY-MM-DD HH24:MI:SS'),Name='Voided' WHERE AD_Ref_List_ID=541736 AND AD_Language='en_US'
;

update AD_Ref_List set ValueName='Closed' where AD_Reference_ID=540734 and Value='CL';
update AD_Ref_List set ValueName='InProgress' where AD_Reference_ID=540734 and Value='IP';
update AD_Ref_List set ValueName='Processed' where AD_Reference_ID=540734 and Value='PR';
update AD_Ref_List set ValueName='Voided' where AD_Reference_ID=540734 and Value='VO';

