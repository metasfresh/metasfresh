ALTER TABLE c_invoice
    DROP CONSTRAINT IF EXISTS aduser_cinvoice;

ALTER TABLE c_invoice
    ADD CONSTRAINT aduser_cinvoice
        FOREIGN KEY (c_bpartner_id, ad_user_id)
            REFERENCES ad_user (c_bpartner_id, ad_user_id)
            DEFERRABLE INITIALLY DEFERRED;
