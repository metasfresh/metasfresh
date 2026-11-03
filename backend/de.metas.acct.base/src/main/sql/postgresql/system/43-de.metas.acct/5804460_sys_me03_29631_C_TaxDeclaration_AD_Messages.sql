-- Run mode: SWING_CLIENT

-- Value: TaxDeclaration_HasCorrections
-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value,ErrorCode) VALUES (0,545713,0,TIMESTAMP '2026-05-25 00:00:00',100,'de.metas.acct','Y','Berichtigung kann nicht aufgehoben werden — eine Korrektur-Berichtigung verweist auf sie. Bitte zuerst die Korrektur aufheben oder löschen.','E',TIMESTAMP '2026-05-25 00:00:00',100,'TaxDeclaration_HasCorrections','TAXDECLARATION_HAS_CORRECTIONS')
;

-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545713 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: TaxDeclaration_HasCorrections
-- 2026-05-25T00:00:00Z
UPDATE AD_Message_Trl SET MsgText='Cannot reopen — a Correction declaration references this. Reopen / delete the Correction first.',Updated=TIMESTAMP '2026-05-25 00:00:00',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545713
;

-- 2026-05-25T00:00:00Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: TaxDeclaration_OriginalMustBeOriginal
-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value,ErrorCode) VALUES (0,545714,0,TIMESTAMP '2026-05-25 00:00:00',100,'de.metas.acct','Y','Eine Korrektur kann nur eine Original-Berichtigung als Vorlage haben (keine Korrektur einer Korrektur).','E',TIMESTAMP '2026-05-25 00:00:00',100,'TaxDeclaration_OriginalMustBeOriginal','TAXDECLARATION_ORIGINAL_MUST_BE_ORIGINAL')
;

-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545714 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: TaxDeclaration_OriginalMustBeOriginal
-- 2026-05-25T00:00:00Z
UPDATE AD_Message_Trl SET MsgText='A Correction''s Original must itself be IsCorrection=''N'' (no Correction-of-Correction).',Updated=TIMESTAMP '2026-05-25 00:00:00',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545714
;

-- 2026-05-25T00:00:00Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: TaxDeclaration_OriginalRequired
-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value,ErrorCode) VALUES (0,545715,0,TIMESTAMP '2026-05-25 00:00:00',100,'de.metas.acct','Y','Bei IsCorrection=''Y'' ist C_TaxDeclaration_Original_ID erforderlich.','E',TIMESTAMP '2026-05-25 00:00:00',100,'TaxDeclaration_OriginalRequired','TAXDECLARATION_ORIGINAL_REQUIRED')
;

-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545715 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: TaxDeclaration_OriginalRequired
-- 2026-05-25T00:00:00Z
UPDATE AD_Message_Trl SET MsgText='C_TaxDeclaration_Original_ID is required when IsCorrection=''Y''.',Updated=TIMESTAMP '2026-05-25 00:00:00',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545715
;

-- 2026-05-25T00:00:00Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: TaxDeclaration_CorrectionInheritsPeriod
-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value,ErrorCode) VALUES (0,545716,0,TIMESTAMP '2026-05-25 00:00:00',100,'de.metas.acct','Y','Eine Korrektur muss Periode (C_Period_ID), Buchungsdatum (DateAcct) und Buchungskreis (C_AcctSchema_ID) der Original-Berichtigung übernehmen.','E',TIMESTAMP '2026-05-25 00:00:00',100,'TaxDeclaration_CorrectionInheritsPeriod','TAXDECLARATION_CORRECTION_INHERITS_PD')
;

-- 2026-05-25T00:00:00Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545716 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: TaxDeclaration_CorrectionInheritsPeriod
-- 2026-05-25T00:00:00Z
UPDATE AD_Message_Trl SET MsgText='A Correction must inherit C_Period_ID, DateAcct and C_AcctSchema_ID from its Original.',Updated=TIMESTAMP '2026-05-25 00:00:00',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545716
;

-- 2026-05-25T00:00:00Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
