-- Value: InvoicingPoolNotActive
-- 2023-01-19T10:53:29.560Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545227,0,TO_TIMESTAMP('2023-01-19 12:53:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fakturierungspool ist nicht aktiv!','E',TO_TIMESTAMP('2023-01-19 12:53:29','YYYY-MM-DD HH24:MI:SS'),100,'InvoicingPoolNotActive')
;

-- 2023-01-19T10:53:29.572Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545227 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: InvoicingPoolNotActive
-- 2023-01-19T10:53:39.618Z
UPDATE AD_Message_Trl SET MsgText='Invoicing Pool is not active!',Updated=TO_TIMESTAMP('2023-01-19 12:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545227
;

-- Value: DifferentSOTrxInvoicingPoolDocumentType
-- 2023-01-19T10:54:51.218Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545228,0,TO_TIMESTAMP('2023-01-19 12:54:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Unterschiedliche SoTrx zwischen Fakturierungspool und Belegart!','E',TO_TIMESTAMP('2023-01-19 12:54:51','YYYY-MM-DD HH24:MI:SS'),100,'DifferentSOTrxInvoicingPoolDocumentType')
;

-- 2023-01-19T10:54:51.221Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545228 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: DifferentSOTrxInvoicingPoolDocumentType
-- 2023-01-19T10:54:58.090Z
UPDATE AD_Message_Trl SET MsgText='Different SoTrx between Invoicing Pool and Document Type!',Updated=TO_TIMESTAMP('2023-01-19 12:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545228
;

-- Value: ReferencedInvoicingPool
-- 2023-01-19T11:02:33.750Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545229,0,TO_TIMESTAMP('2023-01-19 13:02:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fakturierungspool wird immer noch in Dokumenttypen referenziert!','E',TO_TIMESTAMP('2023-01-19 13:02:33','YYYY-MM-DD HH24:MI:SS'),100,'ReferencedInvoicingPool')
;

-- 2023-01-19T11:02:33.752Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545229 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ReferencedInvoicingPool
-- 2023-01-19T11:02:41.409Z
UPDATE AD_Message_Trl SET MsgText='Invoicing Pool is still referenced in Document Types!',Updated=TO_TIMESTAMP('2023-01-19 13:02:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545229
;

