-- Table: C_DocType_Invoicing_Pool
-- 2023-03-22T09:19:30.529Z
UPDATE AD_Table SET Description='Configuration used for aggregating invoices and credit memos into a single document. When the amount to be invoiced is positive, the invoice will be created with the positive doctype of the pool and vice versa for the negative one.', Help='Configuration used for aggregating invoices and credit memos into a single document. When the amount to be invoiced is positive, the invoice will be created with the positive doctype of the pool and vice versa for the negative one.', TechnicalNote='Configuration used for aggregating invoices and credit memos into a single document. When the amount to be invoiced is positive, the invoice will be created with the positive doctype of the pool and vice versa for the negative one.',Updated=TO_TIMESTAMP('2023-03-22 11:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542277
;

-- Element: C_DocType_Invoicing_Pool_ID
-- 2023-03-22T09:20:04.261Z
UPDATE AD_Element_Trl SET Description='Configuration used for aggregating invoices and credit memos into a single document. When the amount to be invoiced is positive, the invoice will be created with the positive doctype of the pool and vice versa for the negative one.', Help='Configuration used for aggregating invoices and credit memos into a single document. When the amount to be invoiced is positive, the invoice will be created with the positive doctype of the pool and vice versa for the negative one.',Updated=TO_TIMESTAMP('2023-03-22 11:20:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='en_US'
;

-- 2023-03-22T09:20:04.295Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'en_US') 
;

