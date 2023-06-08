-- SysConfig Name: SAPGLJournalLineQuickInputDescriptorFactory.layout
-- SysConfig Value: PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?
-- 2023-06-08T06:11:43.163Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541631,'S',TO_TIMESTAMP('2023-06-08 09:11:42','YYYY-MM-DD HH24:MI:SS'),100,'Comma separated list of field names to be rendered as quick input fields. If a field is suffixed by "?" it means that is not mandatory.

An up to date list of available field names you can find it here: https://github.com/metasfresh/metasfresh/blob/master/backend/de.metas.acct.webui/src/main/java/de/metas/acct/gljournal_sap/quickinput/ISAPGLJournalLineQuickInput.java#L34 (the COLUMNNAME_ constants).','D','Y','SAPGLJournalLineQuickInputDescriptorFactory.layout',TO_TIMESTAMP('2023-06-08 09:11:42','YYYY-MM-DD HH24:MI:SS'),100,'PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?')
;

-- SysConfig Name: SAP_GL_Journal.quickInput.layout
-- SysConfig Value: PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?
-- 2023-06-08T06:14:24.477Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='SAP_GL_Journal.quickInput.layout',Updated=TO_TIMESTAMP('2023-06-08 09:14:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541631
;

-- SysConfig Name: SAP_GLJournalLine.quickInput.layout
-- SysConfig Value: PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?
-- 2023-06-08T07:00:25.902Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='SAP_GLJournalLine.quickInput.layout',Updated=TO_TIMESTAMP('2023-06-08 10:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541631
;

-- SysConfig Name: SAP_GLJournalLine.quickInput.layout
-- SysConfig Value: PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?
-- 2023-06-08T07:01:05.122Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='PostingSign,GL_Account_ID,Amount,M_SectionCode_ID,C_Tax_ID?',Updated=TO_TIMESTAMP('2023-06-08 10:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541631
;

