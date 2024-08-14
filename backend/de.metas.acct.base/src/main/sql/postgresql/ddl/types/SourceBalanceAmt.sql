DROP TYPE IF EXISTS de_metas_acct.SourceBalanceAmt
;

CREATE TYPE de_metas_acct.SourceBalanceAmt AS
(
    c_currency_id1 numeric,
    balance1       numeric,
    c_currency_id2 numeric,
    balance2       numeric,
    c_currency_id3 numeric,
    balance3       numeric,
    c_currency_id4 numeric,
    balance4       numeric,
    c_currency_id5 numeric,
    balance5       numeric
)
;
