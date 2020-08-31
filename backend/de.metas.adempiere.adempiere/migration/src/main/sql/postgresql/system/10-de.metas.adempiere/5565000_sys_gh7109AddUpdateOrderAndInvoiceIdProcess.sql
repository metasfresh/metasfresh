-- 2020-08-11T09:03:36.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess, Created, CreatedBy, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport, IsServerProcess, IsTranslateExcelHeaders, IsUseBPartnerLanguage, LockWaitTimeout, Name, RefreshAllAfterExecution,
                        ShowHelp, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 584736, 'Y', 'de.metas.banking.process.C_Payment_UpdateOrderAndInvoiceId', 'N', TO_TIMESTAMP('2020-08-11 12:03:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 0, 'updateOrderAndInvoiceID', 'Y', 'N', 'Java', TO_TIMESTAMP('2020-08-11 12:03:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'updateOrderAndInvoiceID')
;

-- 2020-08-11T09:03:36.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Process_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Process_ID = 584736
  AND NOT EXISTS(SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- 2020-08-25T07:33:00.291Z
-- URL zum Konzept
UPDATE AD_Process
SET Name=' Bestellung und Rechnung festlegen', Updated=TO_TIMESTAMP('2020-08-25 10:32:59', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584736
;

-- 2020-08-25T07:33:14.116Z
-- URL zum Konzept
UPDATE AD_Process
SET Name='Bestellung und Rechnung festlegen', Updated=TO_TIMESTAMP('2020-08-25 10:33:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584736
;

-- 2020-08-25T07:33:31.256Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET Name='Bestellung und Rechnung festlegen', Updated=TO_TIMESTAMP('2020-08-25 10:33:31', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Process_ID = 584736
;

-- 2020-08-25T07:33:49.222Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET Name='Bestellung und Rechnung festlegen', Updated=TO_TIMESTAMP('2020-08-25 10:33:49', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Process_ID = 584736
;

-- 2020-08-25T07:34:01.397Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET IsTranslated='Y', Name='Set Order and Invoice', Updated=TO_TIMESTAMP('2020-08-25 10:34:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 584736
;

-- 2020-08-25T07:34:47.225Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET Name='Bestellung und Rechnung festlegen', Updated=TO_TIMESTAMP('2020-08-25 10:34:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Process_ID = 584736
;

-- 2020-08-27T18:12:43.023Z
-- URL zum Konzept
UPDATE AD_Process
SET Name='Auftrag/Bestellung und Rechnung ermitteln', Updated=TO_TIMESTAMP('2020-08-27 21:12:42', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584736
;

-- 2020-08-27T18:12:58.554Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET Name='Auftrag/Bestellung und Rechnung ermitteln', Updated=TO_TIMESTAMP('2020-08-27 21:12:58', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_CH'
  AND AD_Process_ID = 584736
;

-- 2020-08-27T18:13:04.528Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET Name='Auftrag/Bestellung und Rechnung ermitteln', Updated=TO_TIMESTAMP('2020-08-27 21:13:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'de_DE'
  AND AD_Process_ID = 584736
;

-- 2020-08-27T18:13:09.862Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET Name='Update Order and Invoice', Updated=TO_TIMESTAMP('2020-08-27 21:13:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US'
  AND AD_Process_ID = 584736
;

-- 2020-08-27T18:13:13.912Z
-- URL zum Konzept
UPDATE AD_Process_Trl
SET Name='Auftrag/Bestellung und Rechnung ermitteln', Updated=TO_TIMESTAMP('2020-08-27 21:13:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'nl_NL'
  AND AD_Process_ID = 584736
;

-- 2020-08-27T18:14:37.487Z
-- URL zum Konzept
UPDATE AD_Process
SET Description='The process works on unallocated payments. It sets the order referenced by the payment''s externalorderid. Then, it also sets an invoice if one is found for the set order. Also, it''s allocating the payment to an invoice if the order is a prepayment or if the order''s c_payment_id is the same as the payment we''re running the process and an invoice has been found. Also, the order must have a c_payment_id set.',
    Updated=TO_TIMESTAMP('2020-08-27 21:14:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 584736
;

-- 2020-08-28T13:34:01.789Z
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584736,335,540847,TO_TIMESTAMP('2020-08-28 16:34:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2020-08-28 16:34:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

