-- Run mode: SWING_CLIENT

-- Value: BPartnerNameConsolidationRule.CANNOT_SET_NAME_TO_NULL
-- 2025-01-14T14:02:21.041Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545488,0,TO_TIMESTAMP('2025-01-14 16:02:20.727','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.rest_api','Y','Fehler, C_BPartner.Name kann nicht auf null gesetzt werden.','E',TO_TIMESTAMP('2025-01-14 16:02:20.727','YYYY-MM-DD HH24:MI:SS.US'),100,'BPartnerNameConsolidationRule.CANNOT_SET_NAME_TO_NULL')
;

-- 2025-01-14T14:02:21.059Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545488 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: BPartnerNameConsolidationRule.CANNOT_SET_NAME_TO_NULL
-- 2025-01-14T14:02:53.561Z
UPDATE AD_Message_Trl SET MsgText='Error, can''t set C_BPartner.Name to null',Updated=TO_TIMESTAMP('2025-01-14 16:02:53.561','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545488
;

-- Value: BPartnerNameConsolidationRule.INCONSISTENT_PROPERTIES
-- 2025-01-14T14:06:20.204Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545489,0,TO_TIMESTAMP('2025-01-14 16:06:20.036','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.rest_api','Y','Fehler, inkonsistente Eigenschaften im Anfragekörper.','E',TO_TIMESTAMP('2025-01-14 16:06:20.036','YYYY-MM-DD HH24:MI:SS.US'),100,'BPartnerNameConsolidationRule.INCONSISTENT_PROPERTIES')
;

-- 2025-01-14T14:06:20.205Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545489 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: BPartnerNameConsolidationRule.INCONSISTENT_PROPERTIES
-- 2025-01-14T14:07:25.087Z
UPDATE AD_Message_Trl SET MsgText='Error, inconsistent properties in the request-body',Updated=TO_TIMESTAMP('2025-01-14 16:07:25.087','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545489
;

