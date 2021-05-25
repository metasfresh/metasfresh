CREATE UNIQUE INDEX IF NOT EXISTS idx_unq_ad_user_alberta
    ON ad_user_alberta USING btree (ad_user_id) WHERE IsActive = 'Y';

CREATE UNIQUE INDEX IF NOT EXISTS idx_unq_c_bpartner_alberta
    ON c_bpartner_alberta USING btree (c_bpartner_id) WHERE IsActive = 'Y';

CREATE UNIQUE INDEX IF NOT EXISTS idx_unq_c_bpartner_albertapatient
    ON c_bpartner_albertapatient USING btree (c_bpartner_id) WHERE IsActive = 'Y';

CREATE UNIQUE INDEX IF NOT EXISTS idx_unq_c_bpartner_albertacaregiver
    ON c_bpartner_albertacaregiver USING btree (c_bpartner_id, c_bpartner_caregiver_id) WHERE IsActive = 'Y';