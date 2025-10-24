-- Run mode: SWING_CLIENT

-- Value: SEPA_Invoice_Nor_Order_Set
-- 2025-10-24T10:52:14.716Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545600,0,TO_TIMESTAMP('2025-10-24 10:52:14.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Neither invoice, nor order were set.','E',TO_TIMESTAMP('2025-10-24 10:52:14.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SEPA_Invoice_Nor_Order_Set')
;

-- 2025-10-24T10:52:14.726Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545600 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: SEPA_Invoice_Nor_Order_Set
-- 2025-10-24T10:53:29.575Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Weder Rechnung noch Bestellung wurden festgelegt.',Updated=TO_TIMESTAMP('2025-10-24 10:53:29.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545600
;

-- 2025-10-24T10:53:29.577Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: SEPA_Invoice_Nor_Order_Set
-- 2025-10-24T10:53:44.362Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Weder Rechnung noch Bestellung wurden festgelegt.',Updated=TO_TIMESTAMP('2025-10-24 10:53:44.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545600
;

-- 2025-10-24T10:53:44.364Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.payment.sepa.C_BP_BankAccount_IBANNotSet
-- 2025-10-24T10:56:52.871Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545601,0,TO_TIMESTAMP('2025-10-24 10:56:52.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Im SEPA-Export muss in den Bankkontodaten {0} eine IBAN hinterlegt sein.','E',TO_TIMESTAMP('2025-10-24 10:56:52.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.payment.sepa.C_BP_BankAccount_IBANNotSet')
;

-- 2025-10-24T10:56:52.873Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545601 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.payment.sepa.C_BP_BankAccount_IBANNotSet
-- 2025-10-24T10:56:58.432Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-24 10:56:58.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545601
;

-- Value: de.metas.payment.sepa.C_BP_BankAccount_IBANNotSet
-- 2025-10-24T10:57:02.100Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-24 10:57:02.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545601
;

-- Value: de.metas.payment.sepa.C_BP_BankAccount_IBANNotSet
-- 2025-10-24T10:57:29.465Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='In the SEPA export, an IBAN must be stored in the bank account details {0}.',Updated=TO_TIMESTAMP('2025-10-24 10:57:29.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545601
;

-- 2025-10-24T10:57:29.467Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

