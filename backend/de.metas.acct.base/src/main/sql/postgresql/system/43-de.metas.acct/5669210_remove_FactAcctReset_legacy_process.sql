-- Name: Resubmit Posting
-- Action Type: P
-- Process: Fact_Acct_Reset(org.compiere.process.FactAcctReset)
-- 2022-12-17T08:16:16.136Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=306
;

-- 2022-12-17T08:16:16.141Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=306
;

-- 2022-12-17T08:16:16.144Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=306 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: Reset Accounting
-- Action Type: P
-- Process: Fact_Acct_Reset DELETE(org.compiere.process.FactAcctReset)
-- 2022-12-17T08:16:45.566Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=307
;

-- 2022-12-17T08:16:45.577Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=307
;

-- 2022-12-17T08:16:45.580Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=307 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: Fact_Acct_Reset DELETE
-- Classname: org.compiere.process.FactAcctReset
-- 2022-12-17T08:17:09.831Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=176
;

-- 2022-12-17T08:17:09.837Z
DELETE FROM AD_Process WHERE AD_Process_ID=176
;

-- Value: Fact_Acct_Reset
-- Classname: org.compiere.process.FactAcctReset
-- 2022-12-17T08:17:12.253Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=175
;

-- 2022-12-17T08:17:12.261Z
DELETE FROM AD_Process WHERE AD_Process_ID=175
;

