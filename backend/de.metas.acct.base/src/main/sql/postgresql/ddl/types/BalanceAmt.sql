DROP TYPE IF EXISTS de_metas_acct.BalanceAmt
;

CREATE TYPE de_metas_acct.BalanceAmt AS
(
    Balance numeric
    , Debit numeric
    , Credit numeric
);
