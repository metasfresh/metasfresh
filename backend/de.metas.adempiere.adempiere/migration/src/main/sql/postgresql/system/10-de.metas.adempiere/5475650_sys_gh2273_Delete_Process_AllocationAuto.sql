-- 2017-10-27T11:42:43.412
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=543
;

-- 2017-10-27T11:42:43.422
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=543
;

-- 2017-10-27T11:42:47.126
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542
;

-- 2017-10-27T11:42:47.126
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542
;

-- 2017-10-27T11:42:48.956
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=575
;

-- 2017-10-27T11:42:48.956
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=575
;

-- 2017-10-27T11:42:50.930
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=574
;

-- 2017-10-27T11:42:50.930
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=574
;




-- 2017-10-27T11:43:54.890
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=497
;

-- 2017-10-27T11:43:54.912
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=497
;

-- 2017-10-27T11:43:54.912
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=497 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;



-- 2017-10-27T11:45:12.234
-- URL zum Konzept
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=302
;

-- 2017-10-27T11:45:12.234
-- URL zum Konzept
DELETE FROM AD_Process WHERE AD_Process_ID=302
;

