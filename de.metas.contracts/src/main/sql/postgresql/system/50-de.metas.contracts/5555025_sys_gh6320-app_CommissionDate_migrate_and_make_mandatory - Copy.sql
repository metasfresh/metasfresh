
UPDATE C_Commission_Instance ci 
SET 
    CommissionDate=(select DateOrdered from C_Invoice_Candidate ic where ic.C_Invoice_Candidate_ID=ci.C_Invoice_Candidate_ID),
    UpdatedBy=99,
    Updated='2020-03-22T10:54:47.844Z'
WHERE ci.CommissionDate IS NULL AND ci.C_Invoice_Candidate_ID IS NOT NULL;
;

UPDATE C_Commission_Instance ci 
SET 
    CommissionDate=(select DateInvoiced from C_Invoice i where i.C_Invoice_ID=ci.C_Invoice_ID),
    UpdatedBy=99,
    Updated='2020-03-22T10:54:47.844Z'
WHERE ci.CommissionDate IS NULL AND ci.C_Invoice_ID IS NOT NULL
;

-- 2020-03-22T11:02:13.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-22 12:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570122
;

-- 2020-03-22T11:04:25.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','CommissionDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2020-03-22T11:04:25.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','CommissionDate',null,'NOT NULL',null)
;

