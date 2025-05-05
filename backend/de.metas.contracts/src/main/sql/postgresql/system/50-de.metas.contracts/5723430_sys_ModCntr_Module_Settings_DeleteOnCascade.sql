ALTER TABLE modcntr_module drop constraint modcntrsettings_modcntrmodule;
ALTER TABLE modcntr_module
    ADD CONSTRAINT modcntrsettings_modcntrmodule
        FOREIGN KEY (modcntr_settings_id) REFERENCES modcntr_settings
            ON DELETE CASCADE
            DEFERRABLE INITIALLY DEFERRED
;
