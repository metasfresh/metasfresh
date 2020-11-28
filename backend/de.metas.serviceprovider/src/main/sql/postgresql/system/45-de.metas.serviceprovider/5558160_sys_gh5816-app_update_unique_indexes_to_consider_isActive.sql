-- 2020-04-28T15:19:02.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='S_Issue.IsActive=''Y''',Updated=TO_TIMESTAMP('2020-04-28 18:19:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540527
;

-- 2020-04-28T15:19:15.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS idx_s_issue_externalurl
;

-- 2020-04-28T15:19:15.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_Issue_ExternalURL ON S_Issue (IssueURL) WHERE S_Issue.IsActive='Y'
;

-- 2020-04-28T15:20:15.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='S_ExternalReference.isActive=''Y''',Updated=TO_TIMESTAMP('2020-04-28 18:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540525
;

-- 2020-04-28T15:20:18.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS idx_s_externalreference_externalsystem_type_externalreferenc
;

-- 2020-04-28T15:20:18.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalReference_ExternalSystem_Type_ExternalReferenc ON S_ExternalReference (ExternalReference,ExternalSystem,Type) WHERE S_ExternalReference.isActive='Y'
;

-- 2020-04-28T15:35:24.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='S_ExternalProjectReference.isActive=''Y''',Updated=TO_TIMESTAMP('2020-04-28 18:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540528
;

-- 2020-04-28T15:35:26.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS idx_s_externalprojectreference_reference_owner_system
;

-- 2020-04-28T15:35:26.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalProjectReference_Reference_Owner_System ON S_ExternalProjectReference (ExternalProjectOwner,ExternalReference,ExternalSystem) WHERE S_ExternalProjectReference.isActive='Y'
;

-- 2020-04-29T07:10:15.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540530,0,541486,TO_TIMESTAMP('2020-04-29 10:10:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','IDX_S_ExternalReference_Record_ID_Type','N',TO_TIMESTAMP('2020-04-29 10:10:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:10:15.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540530 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-29T07:10:34.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570590,541005,540530,0,TO_TIMESTAMP('2020-04-29 10:10:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-29 10:10:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:10:46.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570581,541006,540530,0,TO_TIMESTAMP('2020-04-29 10:10:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',20,TO_TIMESTAMP('2020-04-29 10:10:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:10:50.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_S_ExternalReference_Record_ID_Type ON S_ExternalReference (Record_ID,Type)
;

