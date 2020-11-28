
-- 07.12.2015 14:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540916,'S',TO_TIMESTAMP('2015-12-07 14:17:17','YYYY-MM-DD HH24:MI:SS'),100,'If yset to Y and there is an exception on document posting, the user who last updated the document in question receives a note. Default = ''N''','D','Y','org.compiere.acct.Doc.createNoteOnPostError',TO_TIMESTAMP('2015-12-07 14:17:17','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 07.12.2015 14:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If set to Y and there is an exception on document posting, the user who last updated the document in question receives a note.',Updated=TO_TIMESTAMP('2015-12-07 14:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540916
;


-- 08.12.2015 10:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If set to Y and there is an exception on document posting, the user who last updated the document in question receives a note.',Updated=TO_TIMESTAMP('2015-12-08 10:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540916
;
