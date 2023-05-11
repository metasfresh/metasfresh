-- Column: C_PaymentTerm.CalculationMethod
-- 2023-04-04T23:55:10.662Z
UPDATE AD_Column SET FieldLength=5,Updated=TO_TIMESTAMP('2023-04-05 01:55:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586413
;

-- 2023-04-04T23:55:14.393Z
INSERT INTO t_alter_column values('c_paymentterm','CalculationMethod','VARCHAR(5)',null,'BLDX')
;

-- 2023-04-04T23:55:14.594Z
UPDATE C_PaymentTerm SET CalculationMethod='BLDX' WHERE CalculationMethod IS NULL
;

-- 2023-04-04T23:55:14.595Z
INSERT INTO t_alter_column values('c_paymentterm','CalculationMethod',null,'NOT NULL',null)
;

