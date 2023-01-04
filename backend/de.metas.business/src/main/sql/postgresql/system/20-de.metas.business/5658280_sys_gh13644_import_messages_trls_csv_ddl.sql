
CREATE TABLE migration_data.ad_message_trad
(
    ad_message_id numeric NOT NULL,
    msgtext_de    text,
    msgtext_trad  text,
    msgtext_trl   text,
    msgtip_de     text,
    msgtip_trad   text,
    msgtip_trl    text
)
;

ALTER TABLE migration_data.ad_message_trad
    OWNER TO metasfresh
;

