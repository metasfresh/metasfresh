DROP TABLE IF EXISTS M_Additive_Trl;

CREATE TABLE M_Additive_Trl
(
    ad_client_id  numeric(10)              NOT NULL,
    ad_language   varchar(6)               NOT NULL
        CONSTRAINT adlanguage_mproducttrl
            REFERENCES ad_language
            DEFERRABLE INITIALLY DEFERRED,
    ad_org_id     numeric(10)              NOT NULL,
    created       timestamp WITH TIME ZONE NOT NULL,
    createdby     numeric(10)              NOT NULL,
    description   varchar(255),
    isactive      char DEFAULT 'Y'::bpchar NOT NULL
        CONSTRAINT m_additive_trl_isactive_check
            CHECK (isactive = ANY (ARRAY ['Y'::bpchar, 'N'::bpchar])),
    istranslated  char DEFAULT 'N'::bpchar NOT NULL
        CONSTRAINT m_additive_trl_istranslated_check
            CHECK (istranslated = ANY (ARRAY ['Y'::bpchar, 'N'::bpchar])),
    m_additive_id numeric(10)              NOT NULL
        CONSTRAINT madditive_madditivetrl
            REFERENCES m_additive
            DEFERRABLE INITIALLY DEFERRED,
    name          varchar(60)              NOT NULL,
    updated       timestamp WITH TIME ZONE NOT NULL,
    updatedby     numeric(10)              NOT NULL,
    PRIMARY KEY (m_additive_id, ad_language)
)
;

ALTER TABLE M_Additive_Trl
    OWNER TO metasfresh
;