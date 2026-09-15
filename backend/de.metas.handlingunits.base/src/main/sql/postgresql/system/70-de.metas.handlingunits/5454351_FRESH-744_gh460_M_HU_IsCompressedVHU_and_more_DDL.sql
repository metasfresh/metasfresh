
-- Dec 13, 2016 4:14 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Item ADD ItemType VARCHAR(2)
;

-- Dec 13, 2016 4:15 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Item ADD M_HU_PackingMaterial_ID NUMERIC(10)
;

-- Dec 13, 2016 4:15 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_HU_Item ADD CONSTRAINT MHUPackingMaterial_MHUItem FOREIGN KEY (M_HU_PackingMaterial_ID) REFERENCES M_HU_PackingMaterial DEFERRABLE INITIALLY DEFERRED
;

