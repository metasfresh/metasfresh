CREATE TABLE IF NOT EXISTS datevacctexport
(
    ad_client_id             numeric(10)              NOT NULL,
    ad_org_id                numeric(10)              NOT NULL,
    created                  timestamp WITH TIME ZONE NOT NULL,
    createdby                numeric(10)              NOT NULL,
    datevacctexport_id       numeric(10)              NOT NULL
    CONSTRAINT datevacctexport_key
    PRIMARY KEY,
    exporttype               varchar(100)             NOT NULL,
    isactive                 char                     NOT NULL
    CONSTRAINT datevacctexport_isactive_check
    CHECK (isactive = ANY (ARRAY ['Y'::bpchar, 'N'::bpchar])),
    processed                char DEFAULT 'N'::bpchar NOT NULL
    CONSTRAINT datevacctexport_processed_check
    CHECK (processed = ANY (ARRAY ['Y'::bpchar, 'N'::bpchar])),
    updated                  timestamp WITH TIME ZONE NOT NULL,
    updatedby                numeric(10)              NOT NULL,
    isexcludealreadyexported char DEFAULT 'N'::bpchar NOT NULL
    CONSTRAINT datevacctexport_isexcludealreadyexported_check
    CHECK (isexcludealreadyexported = ANY (ARRAY ['Y'::bpchar, 'N'::bpchar])),
    c_period_id              numeric(10)              NOT NULL
    CONSTRAINT cperiod_datevacctexport
    REFERENCES c_period
    DEFERRABLE INITIALLY DEFERRED
)
;