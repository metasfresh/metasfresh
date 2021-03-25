-- 2021-03-25T15:18:00.035264500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540761,542353,TO_TIMESTAMP('2021-03-25 17:17:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sektionswechsel',TO_TIMESTAMP('2021-03-25 17:17:59','YYYY-MM-DD HH24:MI:SS'),100,'OS','Sektionswechsel')
;

-- 2021-03-25T15:18:00.038494400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542353 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-25T15:18:25.530271800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Organization Switch', ValueName='Organization Switch',Updated=TO_TIMESTAMP('2021-03-25 17:18:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542353
;

-- 2021-03-25T15:18:36.734365400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Sektionswechsel', ValueName='Sektionswechsel',Updated=TO_TIMESTAMP('2021-03-25 17:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542353
;

-- 2021-03-25T15:34:35.258689300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='Os',Updated=TO_TIMESTAMP('2021-03-25 17:34:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542353
;

-- 2021-03-25T15:34:49.852648300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Organization Switch',Updated=TO_TIMESTAMP('2021-03-25 17:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542353
;

-- 2021-03-25T15:35:21.564228800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2021-03-25 17:35:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542353
;

-- 2021-03-25T15:35:48.116444600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='', ValueName='OrgChange',Updated=TO_TIMESTAMP('2021-03-25 17:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542353
;




UPDATE r_requesttype

SET isactive = 'Y', internalname='S_OrgSwitch',
    isdefault = 'N',
    updated = '2021-03-27 07:22:09.000000', updatedBy = 100

    WHERE r_requesttype_ID = 1000002;
	
	
	
	