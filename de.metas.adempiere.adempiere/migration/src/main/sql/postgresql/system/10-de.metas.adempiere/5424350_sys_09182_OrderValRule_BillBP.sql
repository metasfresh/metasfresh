-- 25.08.2015 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@
OR EXISTS (SELECT 1 FROM C_BP_Relation r WHERE  r.C_BPartner_ID=@C_BPartner_ID@ AND r.IsBillTo=''Y'' AND
r.isPayFrom = ''Y'' AND r.isActive = ''Y''
AND (r.C_BPartner_Location_ID=@C_BPartner_Location_ID@ OR r.C_BPartner_Location_ID IS NULL OR r.C_BPartner_Location_ID = 0))',Updated=TO_TIMESTAMP('2015-08-25 11:14:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=147
;

-- 25.08.2015 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@',Updated=TO_TIMESTAMP('2015-08-25 11:14:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=147
;

-- 25.08.2015 11:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540308,'C_Order.C_BPartner_ID=@C_BPartner_ID@
OR EXISTS (SELECT 1 FROM C_BP_Relation r WHERE  r.C_BPartner_ID=@C_BPartner_ID@ AND r.IsBillTo=''Y'' AND
r.isPayFrom = ''Y'' AND r.isActive = ''Y''
AND (r.C_BPartner_Location_ID=@C_BPartner_Location_ID@ OR r.C_BPartner_Location_ID IS NULL OR r.C_BPartner_Location_ID = 0))',TO_TIMESTAMP('2015-08-25 11:15:14','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','C_Order of C_BPartner Or Relation','S',TO_TIMESTAMP('2015-08-25 11:15:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 25.08.2015 11:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2015-08-25 11:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540308
;

-- 25.08.2015 11:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@
OR EXISTS (SELECT 1 FROM C_BP_Relation r WHERE  r.C_BPartner_ID=@C_BPartner_ID@ AND r.IsBillTo=''Y'' AND
r.isPayFrom = ''Y'' AND r.isActive = ''Y''
AND (r.C_BPartner_Location_ID=@C_BPartner_Location_ID/-1@ OR r.C_BPartner_Location_ID IS NULL ))',Updated=TO_TIMESTAMP('2015-08-25 11:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540308
;

-- 25.08.2015 11:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540308,Updated=TO_TIMESTAMP('2015-08-25 11:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552628
;

-- 25.08.2015 12:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@
OR
C_Order.C_BPartner_ID=@Bill_BPartner_ID@', Name='C_Order of C_BPartner + BillBPartner',Updated=TO_TIMESTAMP('2015-08-25 12:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540308
;

-- 25.08.2015 12:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_Order.C_BPartner_ID=@C_BPartner_ID@
OR
C_Order.Bill_BPartner_ID=@C_BPartner_ID@',Updated=TO_TIMESTAMP('2015-08-25 12:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540308
;



------------------
-- make C_InvoiceLine.C_Order_ID autocomplete
-- 26.08.2015 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2015-08-26 15:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552628
;

