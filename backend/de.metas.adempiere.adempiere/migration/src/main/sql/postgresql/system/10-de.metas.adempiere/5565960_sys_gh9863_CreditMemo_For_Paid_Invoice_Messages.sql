-- 2020-08-31T11:28:25.800Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544998,0,TO_TIMESTAMP('2020-08-31 14:28:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Wenn die gutgeschriebene Rechnung  = {} referenziert werden soll, dann darf sie noch nicht bezahlt sein.','I',TO_TIMESTAMP('2020-08-31 14:28:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice.service.impl.AbstractInvoiceBL_InvoiceMayNotBePaid')
;

-- 2020-08-31T11:28:25.956Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544998 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-08-31T11:29:16.800Z
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Wenn die gutgeschriebene Rechnung = {0} referenziert werden soll, dann darf sie noch nicht bezahlt sein.',Updated=TO_TIMESTAMP('2020-08-31 14:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544998
;

-- 2020-08-31T11:30:28.250Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='If the credit memo shall reference the credited invoice = {0}, then that invoice may not yet be paid.',Updated=TO_TIMESTAMP('2020-08-31 14:30:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544998
;

-- 2020-08-31T11:31:01.624Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544999,0,TO_TIMESTAMP('2020-08-31 14:31:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Wenn die Gutschrift die gutgeschriebene Rechnung {0} referenzieren soll, dann muss die Rechnung  einen offenen Betrag größer null haben.','E',TO_TIMESTAMP('2020-08-31 14:31:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice.service.impl.AbstractInvoiceBL_InvoiceMayNotHaveOpenAmtZero')
;

-- 2020-08-31T11:31:01.660Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544999 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-08-31T11:31:14.007Z
-- URL zum Konzept
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2020-08-31 14:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544998
;

-- 2020-08-31T11:31:42.099Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgTip='If the credit memo shall reference the credited invoice, then that invoice= {0} may not have Open Amount = 0.',Updated=TO_TIMESTAMP('2020-08-31 14:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544999
;
