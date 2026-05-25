-- Run mode: SWING_CLIENT

-- Value: TaxDeclaration_ProcessedLocked
-- 2026-05-26T00:00:00Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545717,0,TIMESTAMP '2026-05-26 00:00:00',100,'de.metas.acct','Y','Berichtigung ist fertiggestellt (Processed=Y). Nur IsCorrectionNeeded und CorrectionNeededReason dürfen geändert werden.','E',TIMESTAMP '2026-05-26 00:00:00',100,'TaxDeclaration_ProcessedLocked')
;

-- 2026-05-26T00:00:00Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545717 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: TaxDeclaration_ProcessedLocked
-- 2026-05-26T00:00:00Z
UPDATE AD_Message_Trl SET MsgText='Tax Declaration is locked (Processed=Y). Only IsCorrectionNeeded and CorrectionNeededReason can be modified.',Updated=TIMESTAMP '2026-05-26 00:00:00',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545717
;

-- 2026-05-26T00:00:00Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
