--
--Remove PiPo from metasfresh gh #541
-- 

--
-- DDL
-- 

DROP TABLE AD_Package_Exp_Detail;
DROP TABLE AD_Package_Exp;
DROP TABLE AD_Package_Exp_Common;
DROP TABLE AD_Package_Imp_Proc;
DROP TABLE AD_Package_Imp_Backup;
DROP TABLE AD_Package_Imp;
DROP TABLE AD_Package_Imp_Detail;
DROP TABLE AD_Package_Imp_Inst;

COMMIT;

--
-- DML
--
--
-- 28.10.2016 13:55
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=50004
;

-- 28.10.2016 13:55
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=50004
;

-- 28.10.2016 13:55
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=50004 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 28.10.2016 13:56
-- URL zum Konzept
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=50003
;

-- 28.10.2016 13:56
-- URL zum Konzept
DELETE FROM AD_Window WHERE AD_Window_ID=50003
;

-- 28.10.2016 13:56
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50006
;

-- 28.10.2016 13:56
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50006
;

-- 28.10.2016 13:58
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50005
;

-- 28.10.2016 13:58
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50005
;

-- 28.10.2016 14:00
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=50005
;

-- 28.10.2016 14:00
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=50005
;

-- 28.10.2016 14:00
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=50005 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 28.10.2016 14:01
-- URL zum Konzept
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=50004
;

-- 28.10.2016 14:01
-- URL zum Konzept
DELETE FROM AD_Window WHERE AD_Window_ID=50004
;

-- 28.10.2016 14:02
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50007
;

-- 28.10.2016 14:02
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50007
;

-- 28.10.2016 14:06
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=50006
;

-- 28.10.2016 14:06
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=50006
;

-- 28.10.2016 14:06
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=50006 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 28.10.2016 14:08
-- URL zum Konzept
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=50008
;

-- 28.10.2016 14:08
-- URL zum Konzept
DELETE FROM AD_Process WHERE AD_Process_ID=50008
;

-- 28.10.2016 14:09
-- URL zum Konzept
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=50005
;

-- 28.10.2016 14:09
-- URL zum Konzept
DELETE FROM AD_Window WHERE AD_Window_ID=50005
;

-- 28.10.2016 14:10
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50008
;

-- 28.10.2016 14:10
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50008
;

-- 28.10.2016 14:11
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=50003
;

-- 28.10.2016 14:11
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=50003
;

-- 28.10.2016 14:11
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=50003 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 28.10.2016 14:11
-- URL zum Konzept
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=50002
;

-- 28.10.2016 14:11
-- URL zum Konzept
DELETE FROM AD_Window WHERE AD_Window_ID=50002
;

-- 28.10.2016 14:12
-- URL zum Konzept
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=50002
;

-- 28.10.2016 14:12
-- URL zum Konzept
DELETE FROM AD_Process WHERE AD_Process_ID=50002
;

-- 28.10.2016 14:12
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50003
;

-- 28.10.2016 14:12
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50003
;

-- 28.10.2016 14:13
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50002
;

-- 28.10.2016 14:13
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50002
;

-- 28.10.2016 14:13
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50004
;

-- 28.10.2016 14:13
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50004
;

-- 28.10.2016 14:17
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=50001
;

-- 28.10.2016 14:17
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=50001
;

-- 28.10.2016 14:17
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=50001,Updated=TO_TIMESTAMP('2016-10-28 14:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=50001
;

-- 28.10.2016 14:17
-- URL zum Konzept
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=50001
;

-- 28.10.2016 14:17
-- URL zum Konzept
DELETE FROM AD_Table WHERE AD_Table_ID=50001
;

-- 28.10.2016 14:18
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=50002
;

-- 28.10.2016 14:18
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=50002
;

-- 28.10.2016 14:18
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=50002 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 28.10.2016 14:18
-- URL zum Konzept
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=50001
;

-- 28.10.2016 14:18
-- URL zum Konzept
DELETE FROM AD_Window WHERE AD_Window_ID=50001
;

-- 28.10.2016 14:20
-- URL zum Konzept
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=50001
;

-- 28.10.2016 14:20
-- URL zum Konzept
DELETE FROM AD_Menu WHERE AD_Menu_ID=50001
;

-- 28.10.2016 14:20
-- URL zum Konzept
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=50001 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;
