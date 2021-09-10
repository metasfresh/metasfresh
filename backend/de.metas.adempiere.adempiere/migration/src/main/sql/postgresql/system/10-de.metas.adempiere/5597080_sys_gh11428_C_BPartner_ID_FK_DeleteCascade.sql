ALTER TABLE C_BPartner_Export
    DROP CONSTRAINT CBPartner_CBPartnerExport;


ALTER TABLE C_BPartner_Export
    ADD CONSTRAINT CBPartner_CBPartnerExport
        FOREIGN KEY (C_BPartner_ID)
            REFERENCES C_BPartner
            ON DELETE CASCADE;