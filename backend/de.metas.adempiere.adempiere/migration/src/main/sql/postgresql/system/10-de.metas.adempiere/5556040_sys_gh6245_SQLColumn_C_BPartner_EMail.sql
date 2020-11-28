-- 2020-04-01T19:56:18.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,IsActive,Source_Column_ID,Source_Table_ID,SQL_GetTargetRecordIdBySourceRecordId,Updated,UpdatedBy) VALUES (0,557181,0,540000,291,TO_TIMESTAMP('2020-04-01 22:56:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',5396,114,'select C_BPartner_ID 
from AD_User u 
where u.AD_User_ID=@Record_ID@ 
AND u.IsActive=''Y'' 
AND u.IsDefaultContact=''Y''
',TO_TIMESTAMP('2020-04-01 22:56:18','YYYY-MM-DD HH24:MI:SS'),100)
;

