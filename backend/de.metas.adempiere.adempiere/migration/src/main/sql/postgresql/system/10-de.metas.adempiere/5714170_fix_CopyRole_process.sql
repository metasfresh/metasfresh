-- remove duplicate parameter

-- Run mode: SWING_CLIENT

-- Process: CopyRole(org.compiere.process.CopyRole)
-- ParameterName: AD_Role_ID
-- 2023-12-20T19:24:36.109Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=50001
;

-- 2023-12-20T19:24:36.114Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=50001
;

