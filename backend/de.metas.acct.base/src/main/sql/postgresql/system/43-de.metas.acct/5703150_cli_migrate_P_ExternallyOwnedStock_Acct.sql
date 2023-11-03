UPDATE c_acctschema_default
SET p_externallyownedstock_acct=p_expense_acct
WHERE p_externallyownedstock_acct IS NULL
;

UPDATE m_product_category_acct
SET p_externallyownedstock_acct=p_expense_acct
WHERE p_externallyownedstock_acct IS NULL
;

UPDATE m_product_acct
SET p_externallyownedstock_acct=p_expense_acct
WHERE p_externallyownedstock_acct IS NULL
;

