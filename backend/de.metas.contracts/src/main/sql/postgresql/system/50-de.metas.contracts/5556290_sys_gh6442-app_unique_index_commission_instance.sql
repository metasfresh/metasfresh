-- 2020-04-03T15:51:50.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET PersonalDataCategory='P',Updated=TO_TIMESTAMP('2020-04-03 17:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541405
;

-- 2020-04-03T15:52:54.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540522,0,541405,TO_TIMESTAMP('2020-04-03 17:52:54','YYYY-MM-DD HH24:MI:SS'),100,'Each commission trigger has just one instance; admins may still set trigger FKs to 0 or null.','de.metas.contracts.commission','Y','Y','C_Commission_Trigger_UC','N',TO_TIMESTAMP('2020-04-03 17:52:54','YYYY-MM-DD HH24:MI:SS'),100,
	'IsActive=''Y'' AND (COALESCE(C_Invoice_Candidate_ID, 0) > 0 OR COALESCE(C_InvoiceLine_ID, 0) > 0)')
;

-- 2020-04-03T15:52:54.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540522 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-03T15:54:46.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,568754,540992,540522,0,'COALESCE(C_Invoice_Candidate_ID, 0)',TO_TIMESTAMP('2020-04-03 17:54:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',10,TO_TIMESTAMP('2020-04-03 17:54:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-03T15:55:30.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570117,540993,540522,0,'COALESCE(C_InvoiceLine_ID, 0)',TO_TIMESTAMP('2020-04-03 17:55:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',20,TO_TIMESTAMP('2020-04-03 17:55:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-03T15:55:46.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Commission_Trigger_UC ON C_Commission_Instance (COALESCE(C_Invoice_Candidate_ID, 0),COALESCE(C_InvoiceLine_ID, 0)) WHERE IsActive='Y' AND (COALESCE(C_Invoice_Candidate_ID, 0) > 0 OR COALESCE(C_InvoiceLine_ID, 0) > 0);
;

