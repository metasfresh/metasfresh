CREATE INDEX c_bp_bankaccount_IBAN
    ON c_bp_bankaccount(REPLACE(IBAN, ' ', '') , isactive);
;