

-- 2021-08-05T14:51:42.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540066,'de.metas.ordercandidate.api.async.C_OLCandToOrderWorkpackageProcessor',TO_TIMESTAMP('2021-08-05 17:51:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y',TO_TIMESTAMP('2021-08-05 17:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-05T14:51:56.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET InternalName='C_OLCandToOrderWorkpackageProcessor',Updated=TO_TIMESTAMP('2021-08-05 17:51:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540066
;

-- 2021-08-05T14:52:15.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_PackageProcessor SET Description='Automated order',Updated=TO_TIMESTAMP('2021-08-05 17:52:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_PackageProcessor_ID=540066
;

