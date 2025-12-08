DROP TABLE IF EXISTS "de_metas_acct".fact_acct_openItems_to_update
;

DROP SEQUENCE IF EXISTS "de_metas_acct".fact_acct_openItems_to_update_seqno
;

CREATE SEQUENCE "de_metas_acct".fact_acct_openItems_to_update_seqno MINVALUE 1
;

CREATE TABLE "de_metas_acct".fact_acct_openItems_to_update
(
    SeqNo           numeric(10)         DEFAULT NEXTVAL('"de_metas_acct".fact_acct_openItems_to_update_seqno') NOT NULL,
    created         time WITH TIME ZONE DEFAULT NOW()                                                          NOT NULL,
    --
    OpenItemKey     varchar(255)                                                                               NOT NULL,
    C_AcctSchema_ID numeric(10)                                                                                NOT NULL,
    PostingType     char(1)                                                                                    NOT NULL,
    Account_ID      numeric(10)                                                                                NOT NULL,
    --
    selection_id    varchar(60)
)
;

COMMENT ON TABLE "de_metas_acct".fact_acct_openItems_to_update IS
    'This table is constantly polled by the accounting server'
;

CREATE INDEX fact_acct_openItems_to_update_selectionId ON "de_metas_acct".fact_acct_openItems_to_update (selection_id)
;

