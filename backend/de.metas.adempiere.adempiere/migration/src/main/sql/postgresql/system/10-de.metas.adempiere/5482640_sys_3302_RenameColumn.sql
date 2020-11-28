-- 2018-01-15T11:06:20.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543766,0,'C_PaymentTerm_Override_ID',TO_TIMESTAMP('2018-01-15 11:06:20','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs','D','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','Zahlungsbedingung abw.','Zahlungsbedingung abw.',TO_TIMESTAMP('2018-01-15 11:06:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-15T11:06:20.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543766 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-15T11:06:49.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543766, AD_Reference_ID=18, AD_Reference_Value_ID=227, ColumnName='C_PaymentTerm_Override_ID', Description='Die Bedingungen für die Bezahlung dieses Vorgangs', Help='Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.', Name='Zahlungsbedingung abw.',Updated=TO_TIMESTAMP('2018-01-15 11:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558576
;

-- 2018-01-15T11:06:49.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlungsbedingung abw.', Description='Die Bedingungen für die Bezahlung dieses Vorgangs', Help='Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.' WHERE AD_Column_ID=558576
;

-- 2018-01-15T11:06:53.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN C_PaymentTerm_Override_ID NUMERIC(10)')
;

-- 2018-01-15T11:06:54.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OrderLine ADD CONSTRAINT CPaymentTermOverride_COrderLin FOREIGN KEY (C_PaymentTerm_Override_ID) REFERENCES public.C_PaymentTerm DEFERRABLE INITIALLY DEFERRED
;


SELECT public.db_alter_table('C_OrderLine','ALTER TABLE C_OrderLine DROP COLUMN C_PaymentTerm_ID');



