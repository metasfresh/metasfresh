-- Value: DocumentApprovalRequest
-- 2023-09-05T16:10:06.234975800Z
UPDATE AD_Message SET MsgText='Benutzer {0} hat Sie um die Genehmigung von {1} gebeten.',Updated=TO_TIMESTAMP('2023-09-05 19:10:06.231','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545005
;

-- 2023-09-05T16:10:06.238611600Z
UPDATE AD_Message_Trl trl SET MsgText='Benutzer {0} hat Sie um die Genehmigung von {1} gebeten.' WHERE AD_Message_ID=545005 AND AD_Language='de_DE'
;

-- Value: DocumentApprovalRequest
-- 2023-09-05T16:10:12.407340500Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Benutzer {0} hat Sie um die Genehmigung von {1} gebeten.',Updated=TO_TIMESTAMP('2023-09-05 19:10:12.406','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545005
;

-- Value: DocumentApprovalRequest
-- 2023-09-05T16:10:14.468220400Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-05 19:10:14.467','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545005
;

-- Value: DocumentApprovalRequest
-- 2023-09-05T16:10:22.904162900Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-05 19:10:22.903','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545005
;

-- Value: DocumentSentToApproval
-- 2023-09-05T16:11:07.572014700Z
UPDATE AD_Message SET MsgText='Dokument {0} wurde zur Genehmigung an {1} gesendet',Updated=TO_TIMESTAMP('2023-09-05 19:11:07.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545006
;

-- 2023-09-05T16:11:07.573565200Z
UPDATE AD_Message_Trl trl SET MsgText='Dokument {0} wurde zur Genehmigung an {1} gesendet' WHERE AD_Message_ID=545006 AND AD_Language='de_DE'
;

-- Value: DocumentSentToApproval
-- 2023-09-05T16:11:15.123897400Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Dokument {0} wurde zur Genehmigung an {1} gesendet',Updated=TO_TIMESTAMP('2023-09-05 19:11:15.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545006
;

-- Value: DocumentSentToApproval
-- 2023-09-05T16:11:17.012314800Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-05 19:11:17.011','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545006
;

-- Value: DocumentSentToApproval
-- 2023-09-05T16:11:20.141744900Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-05 19:11:20.141','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545006
;

