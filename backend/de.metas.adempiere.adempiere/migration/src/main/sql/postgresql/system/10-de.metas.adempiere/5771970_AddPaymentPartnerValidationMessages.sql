-- Value: Payment_BPartnerMismatch_Invoice
-- 2025-10-02T06:37:24.885Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545589,0,TO_TIMESTAMP('2025-10-02 06:37:24.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Payment Business Partner ({0}) does not match the Business Partner ({1}) of the invoice {2}.','E',TO_TIMESTAMP('2025-10-02 06:37:24.646000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Payment_BPartnerMismatch_Invoice')
;

-- 2025-10-02T06:37:24.908Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545589 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Payment_BPartnerMismatch_Invoice
-- 2025-10-02T06:37:36.551Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='"Der Geschäftspartner der Zahlung ({0}) stimmt nicht mit dem Geschäftspartner ({1}) der Rechnung {2} überein.',Updated=TO_TIMESTAMP('2025-10-02 06:37:36.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545589
;

-- 2025-10-02T06:37:36.553Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: Payment_BPartnerMismatch_Invoice
-- 2025-10-02T06:37:44.443Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-02 06:37:44.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545589
;

-- Value: Payment_BPartnerMismatch_Invoice
-- 2025-10-02T06:37:49.451Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='"Der Geschäftspartner der Zahlung ({0}) stimmt nicht mit dem Geschäftspartner ({1}) der Rechnung {2} überein.',Updated=TO_TIMESTAMP('2025-10-02 06:37:49.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545589
;

-- 2025-10-02T06:37:49.454Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: Payment_BPartnerMismatch_Order
-- 2025-10-02T06:38:14.782Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545590,0,TO_TIMESTAMP('2025-10-02 06:38:14.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Payment Business Partner ({0}) does not match the Bill-To Business Partner ({1}) of the order {2}.','E',TO_TIMESTAMP('2025-10-02 06:38:14.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Payment_BPartnerMismatch_Order')
;

-- 2025-10-02T06:38:14.785Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545590 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Payment_BPartnerMismatch_Order
-- 2025-10-02T06:38:34.638Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Geschäftspartner der Zahlung ({0}) stimmt nicht mit dem Rechnungsempfänger ({1}) des Auftrags {2} überein.',Updated=TO_TIMESTAMP('2025-10-02 06:38:34.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545590
;

-- 2025-10-02T06:38:34.640Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: Payment_BPartnerMismatch_Order
-- 2025-10-02T06:38:41.616Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-02 06:38:41.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545590
;

-- Value: Payment_BPartnerMismatch_Order
-- 2025-10-02T06:38:47.149Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Geschäftspartner der Zahlung ({0}) stimmt nicht mit dem Rechnungsempfänger ({1}) des Auftrags {2} überein.',Updated=TO_TIMESTAMP('2025-10-02 06:38:47.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545590
;

-- 2025-10-02T06:38:47.150Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

