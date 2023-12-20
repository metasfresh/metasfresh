-- 2023-01-25T13:44:40.507Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,RplImportMode,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,540573,'N',TO_TIMESTAMP('2023-01-25 15:44:40','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.process.rpl',540426,'Y','N','EXP_ReplicationTrx_Update','N','L','N','N',TO_TIMESTAMP('2023-01-25 15:44:40','YYYY-MM-DD HH24:MI:SS'),100,'EXP_ReplicationTrx_Update','*')
;

-- 2023-01-25T13:45:08.910Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,550412,0,TO_TIMESTAMP('2023-01-25 15:45:08','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.process.rpl',540426,550584,'Y','Y','Y','Name',10,'E',TO_TIMESTAMP('2023-01-25 15:45:08','YYYY-MM-DD HH24:MI:SS'),100,'Name')
;

-- 2023-01-25T13:45:28.205Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,585607,0,TO_TIMESTAMP('2023-01-25 15:45:28','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.process.rpl',540426,550585,'Y','N','N','IsReplicationTrxFinished',20,'E',TO_TIMESTAMP('2023-01-25 15:45:28','YYYY-MM-DD HH24:MI:SS'),100,'IsReplicationTrxFinished')
;


-- 2023-01-26T07:39:19.843Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,585610,0,TO_TIMESTAMP('2023-01-26 09:39:19','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.process.rpl',540426,550590,'Y','N','N','IsError',30,'E',TO_TIMESTAMP('2023-01-26 09:39:19','YYYY-MM-DD HH24:MI:SS'),100,'IsError')
;

-- 2023-01-26T07:39:30.868Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,585611,0,TO_TIMESTAMP('2023-01-26 09:39:30','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.process.rpl',540426,550591,'Y','N','N','ErrorMsg',40,'E',TO_TIMESTAMP('2023-01-26 09:39:30','YYYY-MM-DD HH24:MI:SS'),100,'ErrorMsg')
;

-- 2023-01-27T09:34:05.685Z
UPDATE EXP_Format SET EntityType='de.metas.esb.edi', Name='EDI_ReplicationTrx_Update', Value='EDI_ReplicationTrx_Update',Updated=TO_TIMESTAMP('2023-01-27 11:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540426
;


