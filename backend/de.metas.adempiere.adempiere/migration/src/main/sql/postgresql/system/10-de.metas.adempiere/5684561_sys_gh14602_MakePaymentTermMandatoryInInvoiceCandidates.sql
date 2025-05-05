
-- make it mandatory


-- 2023-04-07T10:25:52.683Z
INSERT INTO t_alter_column values('c_invoice_candidate','C_PaymentTerm_ID','NUMERIC(10)',null,null)
;

-- 2023-04-07T10:25:52.693Z
INSERT INTO t_alter_column values('c_invoice_candidate','C_PaymentTerm_ID',null,'NOT NULL',null)
;