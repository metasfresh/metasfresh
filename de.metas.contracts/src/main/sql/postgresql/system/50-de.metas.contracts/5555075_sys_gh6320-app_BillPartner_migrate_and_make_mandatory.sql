
UPDATE C_Commission_Instance ci 
SET 
    Bill_BPartner_ID=(select Bill_BPartner_ID from C_Invoice_Candidate ic where ic.C_Invoice_Candidate_ID=ci.C_Invoice_Candidate_ID),
    UpdatedBy=99,
    Updated='2020-03-22T10:54:47.844Z'
WHERE ci.Bill_BPartner_ID IS NULL AND ci.C_Invoice_Candidate_ID IS NOT NULL
;

UPDATE C_Commission_Instance ci 
SET 
    Bill_BPartner_ID=(select C_BPartner_ID from C_Invoice i where i.C_Invoice_ID=ci.C_Invoice_ID),
    UpdatedBy=99,
    Updated='2020-03-22T10:54:47.844Z'
WHERE ci.Bill_BPartner_ID IS NULL AND ci.C_Invoice_ID IS NOT NULL
;

-- 2020-03-22T14:50:51.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-22 15:50:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568789
;

-- 2020-03-22T14:59:38.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','Bill_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2020-03-22T14:59:38.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','Bill_BPartner_ID',null,'NOT NULL',null)
;

