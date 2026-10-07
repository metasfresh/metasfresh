-- Run mode: SWING_CLIENT

-- Name: Inbound Charges for AP
-- Action Type: P
-- Process: A_InboundChrgAP(org.compiere.FA.CreateInvoicedAsset)
-- 2026-05-18T12:49:06.107Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=53168
;

-- 2026-05-18T12:49:06.134Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=53168
;

-- 2026-05-18T12:49:06.149Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=53168 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: A_InboundChrgAP
-- Classname: org.compiere.FA.CreateInvoicedAsset
-- 2026-05-18T12:51:19.345Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=53137
;

-- 2026-05-18T12:51:19.361Z
DELETE FROM AD_Process WHERE AD_Process_ID=53137
;

