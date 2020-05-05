ALTER TABLE m_inout
    DROP CONSTRAINT IF EXISTS aduser_minout;

ALTER TABLE m_inout
    ADD CONSTRAINT aduser_minout
        FOREIGN KEY (c_bpartner_id, ad_user_id)
            REFERENCES ad_user (c_bpartner_id, ad_user_id)
            DEFERRABLE INITIALLY DEFERRED;
