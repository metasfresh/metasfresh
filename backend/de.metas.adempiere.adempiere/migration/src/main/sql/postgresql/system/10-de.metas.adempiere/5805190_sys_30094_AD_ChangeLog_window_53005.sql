-- 2026-05-28T12:00:00
-- enable AD_ChangeLog on the 5 tables of window 53005 (Workflow / Produktion Arbeitsablauf)
-- so that future deactivations / changes are auditable in the change log.

UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2026-05-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE TableName='AD_Workflow' AND COALESCE(IsChangeLog,'N')<>'Y'
;

UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2026-05-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE TableName='AD_Workflow_Trl' AND COALESCE(IsChangeLog,'N')<>'Y'
;

UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2026-05-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE TableName='AD_WF_Node' AND COALESCE(IsChangeLog,'N')<>'Y'
;

UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2026-05-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE TableName='AD_WF_NodeNext' AND COALESCE(IsChangeLog,'N')<>'Y'
;

UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2026-05-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE TableName='PP_WF_Node_Product' AND COALESCE(IsChangeLog,'N')<>'Y'
;
