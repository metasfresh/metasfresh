-- Column: AD_WF_Approval_Request.Status
-- 2023-10-02T15:22:45.722112500Z
UPDATE AD_Column SET FilterDefaultValue='P',Updated=TO_TIMESTAMP('2023-10-02 16:22:45.722','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587363
;

-- Reference Item: AD_WF_Approval_Request_Status -> P_Pending
-- 2023-10-02T15:26:32.851098900Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Ausstehend',Updated=TO_TIMESTAMP('2023-10-02 16:26:32.85','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543545
;

-- Reference Item: AD_WF_Approval_Request_Status -> P_Pending
-- 2023-10-02T15:26:45.646823200Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Ausstehend',Updated=TO_TIMESTAMP('2023-10-02 16:26:45.646','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543545
;

-- 2023-10-02T15:26:45.722689800Z
UPDATE AD_Ref_List SET Name='Ausstehend' WHERE AD_Ref_List_ID=543545
;

-- Reference Item: AD_WF_Approval_Request_Status -> A_Approved
-- 2023-10-02T15:27:15.039782300Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Genehmigt',Updated=TO_TIMESTAMP('2023-10-02 16:27:15.038','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543546
;

-- 2023-10-02T15:27:15.115086200Z
UPDATE AD_Ref_List SET Name='Genehmigt' WHERE AD_Ref_List_ID=543546
;

-- Reference Item: AD_WF_Approval_Request_Status -> A_Approved
-- 2023-10-02T15:27:23.564865500Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Genehmigt',Updated=TO_TIMESTAMP('2023-10-02 16:27:23.564','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543546
;

-- Reference Item: AD_WF_Approval_Request_Status -> R_Rejected
-- 2023-10-02T15:27:51.114781400Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Abgelehnt',Updated=TO_TIMESTAMP('2023-10-02 16:27:51.114','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543547
;

-- Reference Item: AD_WF_Approval_Request_Status -> R_Rejected
-- 2023-10-02T15:27:57.936461600Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Abgelehnt',Updated=TO_TIMESTAMP('2023-10-02 16:27:57.936','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543547
;

-- 2023-10-02T15:27:58.011792300Z
UPDATE AD_Ref_List SET Name='Abgelehnt' WHERE AD_Ref_List_ID=543547
;

