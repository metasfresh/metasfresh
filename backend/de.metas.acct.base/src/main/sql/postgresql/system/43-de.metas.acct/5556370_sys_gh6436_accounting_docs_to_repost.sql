DROP TABLE IF EXISTS "de_metas_acct".accounting_docs_to_repost;
DROP SEQUENCE IF EXISTS "de_metas_acct".accounting_docs_to_repost_seqno;

CREATE SEQUENCE "de_metas_acct".accounting_docs_to_repost_seqno MINVALUE 1;
CREATE TABLE "de_metas_acct".accounting_docs_to_repost
(
    seqno                   numeric(10)         DEFAULT nextval('"de_metas_acct".accounting_docs_to_repost_seqno') NOT NULL,
    --
    tablename               varchar(255)                                                                           NOT NULL,
    record_id               numeric(10)                                                                            NOT NULL,
    ad_client_id            numeric(10)         DEFAULT 1000000                                                    NOT NULL,
    force                   char(1)             DEFAULT 'N'                                                        NOT NULL,
    on_error_notify_user_id numeric(10),
    --
    selection_id            varchar(60),
    created                 time WITH TIME ZONE DEFAULT now()                                                      NOT NULL,
    description             varchar(2000)
);
COMMENT ON TABLE "de_metas_acct".accounting_docs_to_repost IS
    'This table is constantly polled by the accounting server';

