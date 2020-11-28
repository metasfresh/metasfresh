-- 2020-08-18T04:27:04.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,570186,0,540016,541468,TO_TIMESTAMP('2020-08-18 07:27:04','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',571097,570201,541468,TO_TIMESTAMP('2020-08-18 07:27:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-08-18T04:45:42.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SQLColumn_SourceTableColumn SET FetchTargetRecordsMethod='S', Link_Column_ID=570758, SQL_GetTargetRecordIdBySourceRecordId='select effortChild.s_parent_issue_id
from s_issue effortChild
    inner join s_issue parentBudget on effortChild.s_parent_issue_id = parentBudget.s_issue_id
where effortChild.s_issue_id = @S_Issue_ID@
  and parentBudget.iseffortissue = ''N'';',Updated=TO_TIMESTAMP('2020-08-18 07:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540016
;

-- 2020-08-18T05:19:13.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SQLColumn_SourceTableColumn SET SQL_GetTargetRecordIdBySourceRecordId='select effortChild.s_parent_issue_id
from s_issue effortChild
    inner join s_issue parentBudget on effortChild.s_parent_issue_id = parentBudget.s_issue_id
where effortChild.s_issue_id = @Record_ID/-1@
  and parentBudget.iseffortissue = ''N'';',Updated=TO_TIMESTAMP('2020-08-18 08:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540016
;

-- 2020-08-18T05:38:50.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET RefreshAllAfterExecution='N',Updated=TO_TIMESTAMP('2020-08-18 08:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584735
;

