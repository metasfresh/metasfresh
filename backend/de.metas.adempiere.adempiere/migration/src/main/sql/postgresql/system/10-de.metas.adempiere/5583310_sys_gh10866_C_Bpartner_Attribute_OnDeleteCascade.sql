
-- 2021-03-22T14:04:01.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TechnicalNote='We currently don''t have java-model-classes for this table.
To allow deleting BPartners, we change the C_BPartner_ID-FK to "on delete cascade"',Updated=TO_TIMESTAMP('2021-03-22 15:04:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540839
;

ALTER TABLE public.c_bpartner_attribute DROP CONSTRAINT cbpartner_cbpartnerattribute;

ALTER TABLE public.c_bpartner_attribute
    ADD CONSTRAINT cbpartner_cbpartnerattribute FOREIGN KEY (c_bpartner_id)
        REFERENCES public.c_bpartner (c_bpartner_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        DEFERRABLE INITIALLY DEFERRED;
