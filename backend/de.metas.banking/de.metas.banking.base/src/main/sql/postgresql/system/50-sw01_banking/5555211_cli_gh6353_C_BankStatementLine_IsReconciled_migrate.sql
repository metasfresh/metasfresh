UPDATE c_bankstatementline
SET isreconciled=(CASE
                      WHEN c_payment_id > 0 OR (ismultiplepaymentorinvoice = 'Y' AND ismultiplepayment = 'Y') THEN 'Y'
                                                                                                              ELSE 'N'
                  END)
WHERE TRUE;

UPDATE C_BankStatement bs
SET IsReconciled=(CASE WHEN (SELECT COUNT(1) FROM C_BankStatementLine bsl WHERE bsl.C_BankStatement_ID = bs.C_BankStatement_ID AND bsl.IsReconciled = 'N') = 0 THEN 'Y' ELSE 'N' END)
WHERE TRUE;

