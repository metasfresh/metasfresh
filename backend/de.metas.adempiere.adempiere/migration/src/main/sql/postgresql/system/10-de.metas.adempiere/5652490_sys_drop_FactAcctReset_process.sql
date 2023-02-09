-- Name: Buchführung zurücksetzen
-- Action Type: P
-- Process: Fact_Acct_Reset DELETE(org.compiere.process.FactAcctReset)
-- 2022-08-23T06:55:41.442Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=307
;

-- 2022-08-23T06:55:41.449Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=307
;

-- 2022-08-23T06:55:41.451Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=307 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: Fact_Acct_Reset DELETE
-- Classname: org.compiere.process.FactAcctReset
-- 2022-08-23T06:55:48.674Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=176
;

-- 2022-08-23T06:55:48.680Z
DELETE FROM AD_Process WHERE AD_Process_ID=176
;

-- Name: Erneut Verbuchen
-- Action Type: P
-- Process: Fact_Acct_Reset(org.compiere.process.FactAcctReset)
-- 2022-08-23T06:56:03.264Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=306
;

-- 2022-08-23T06:56:03.271Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=306
;

-- 2022-08-23T06:56:03.276Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=306 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: Fact_Acct_Reset
-- Classname: org.compiere.process.FactAcctReset
-- 2022-08-23T06:56:07.157Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=175
;

-- 2022-08-23T06:56:07.160Z
DELETE FROM AD_Process WHERE AD_Process_ID=175
;

