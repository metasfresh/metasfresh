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

-- Window: Ausgewählte Zahlungen, InternalName=206 (Todo: Set Internal Name for UI testing)
-- 2025-10-24T13:38:40.155Z
UPDATE AD_Window SET AD_Element_ID=581920, Description=NULL, Help=NULL, Name='Ausgewählte Zahlungen',Updated=TO_TIMESTAMP('2025-10-24 13:38:40.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=206
;

-- 2025-10-24T13:38:40.170Z
UPDATE AD_Window_Trl trl SET Description=NULL,Help=NULL,Name='Ausgewählte Zahlungen' WHERE AD_Window_ID=206 AND AD_Language='de_DE'
;

-- Name: Ausgewählte Zahlungen
-- Action Type: W
-- Window: Ausgewählte Zahlungen(206,D)
-- 2025-10-24T13:38:40.209Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Ausgewählte Zahlungen',Updated=TO_TIMESTAMP('2025-10-24 13:38:40.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540910
;

-- 2025-10-24T13:38:40.215Z
UPDATE AD_Menu_Trl trl SET Description=NULL,Name='Ausgewählte Zahlungen' WHERE AD_Menu_ID=540910 AND AD_Language='de_DE'
;

-- Name: Ausgewählte Zahlungen
-- Action Type: W
-- Window: Ausgewählte Zahlungen(206,D)
-- 2025-10-24T13:38:40.227Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Ausgewählte Zahlungen',Updated=TO_TIMESTAMP('2025-10-24 13:38:40.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=255
;

-- 2025-10-24T13:38:40.229Z
UPDATE AD_Menu_Trl trl SET Description=NULL,Name='Ausgewählte Zahlungen' WHERE AD_Menu_ID=255 AND AD_Language='de_DE'
;

-- 2025-10-24T13:38:40.277Z
/* DDL */  select update_window_translation_from_ad_element(581920)
;

-- 2025-10-24T13:38:40.308Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=206
;

-- 2025-10-24T13:38:40.325Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(206)
;

-- Field: Ausgewählte Zahlungen(206,D) -> Rechnung auswählen(353,D) -> Bestellung
-- Column: C_PaySelectionLine.C_Order_ID
-- 2025-10-24T13:41:40.943Z
UPDATE AD_Field SET AD_Name_ID=573581, Description=NULL, Help=NULL, Name='Bestellung',Updated=TO_TIMESTAMP('2025-10-24 13:41:40.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754994
;

-- 2025-10-24T13:41:40.949Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Bestellung' WHERE AD_Field_ID=754994 AND AD_Language='de_DE'
;

-- 2025-10-24T13:41:40.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(573581)
;

-- 2025-10-24T13:41:40.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754994
;

-- 2025-10-24T13:41:40.961Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754994)
;

-- Element: null
-- 2025-10-24T13:42:07.994Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-24 13:42:07.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=573581 AND AD_Language='de_CH'
;

-- 2025-10-24T13:42:08Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573581,'de_CH')
;

-- Element: null
-- 2025-10-24T13:42:13.361Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-24 13:42:13.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=573581 AND AD_Language='de_DE'
;

-- 2025-10-24T13:42:13.364Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(573581,'de_DE')
;

-- 2025-10-24T13:42:13.366Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573581,'de_DE')
;

-- Value: de.metas.payment.sepa.SEPA_Export_NoBankName
-- 2025-10-24T13:46:33.818Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545602,0,TO_TIMESTAMP('2025-10-24 13:46:33.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Payment type={}, but line {} has no information about the bank name.','E',TO_TIMESTAMP('2025-10-24 13:46:33.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.payment.sepa.SEPA_Export_NoBankName')
;

-- 2025-10-24T13:46:33.823Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545602 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.payment.sepa.SEPA_Export_NoBankName
-- 2025-10-24T13:47:03.881Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zahlungsart = {}, aber die Zeile {} enthält keine Informationen über den Banknamen.',Updated=TO_TIMESTAMP('2025-10-24 13:47:03.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545602
;

-- 2025-10-24T13:47:03.884Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.payment.sepa.SEPA_Export_NoBankName
-- 2025-10-24T13:47:09.564Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-24 13:47:09.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545602
;

-- Value: de.metas.payment.sepa.SEPA_Export_NoBankName
-- 2025-10-24T13:47:16.623Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zahlungsart = {}, aber die Zeile {} enthält keine Informationen über den Banknamen.',Updated=TO_TIMESTAMP('2025-10-24 13:47:16.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545602
;

-- 2025-10-24T13:47:16.624Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

