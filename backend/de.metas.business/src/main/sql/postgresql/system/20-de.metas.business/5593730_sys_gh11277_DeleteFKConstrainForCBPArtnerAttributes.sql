ALTER TABLE ad_user_attribute
    DROP
        CONSTRAINT aduser_aduserattribute
;

ALTER TABLE ad_user_attribute
    ADD
        CONSTRAINT aduser_aduserattribute
            FOREIGN KEY (ad_user_ID)
                REFERENCES ad_user
                ON DELETE CASCADE
;


ALTER TABLE c_bpartner_attribute2
    DROP
        CONSTRAINT cbpartner_cbpartnerattribute2
;

ALTER TABLE c_bpartner_attribute2
    ADD
        CONSTRAINT cbpartner_cbpartnerattribute2
            FOREIGN KEY (c_bpartner_id)
                REFERENCES c_bpartner
                ON DELETE CASCADE
;

ALTER TABLE c_bpartner_attribute3
    DROP
        CONSTRAINT cbpartner_cbpartnerattribute3
;

ALTER TABLE c_bpartner_attribute3
    ADD
        CONSTRAINT cbpartner_cbpartnerattribute3
            FOREIGN KEY (c_bpartner_id)
                REFERENCES c_bpartner
                ON DELETE CASCADE
;

ALTER TABLE c_bpartner_attribute4
    DROP
        CONSTRAINT cbpartner_cbpartnerattribute4
;

ALTER TABLE c_bpartner_attribute4
    ADD
        CONSTRAINT cbpartner_cbpartnerattribute4
            FOREIGN KEY (c_bpartner_id)
                REFERENCES c_bpartner
                ON DELETE CASCADE
;


ALTER TABLE c_bpartner_attribute5
    DROP
        CONSTRAINT cbpartner_cbpartnerattribute5
;

ALTER TABLE c_bpartner_attribute5
    ADD
        CONSTRAINT cbpartner_cbpartnerattribute5
            FOREIGN KEY (c_bpartner_id)
                REFERENCES c_bpartner
                ON DELETE CASCADE
;