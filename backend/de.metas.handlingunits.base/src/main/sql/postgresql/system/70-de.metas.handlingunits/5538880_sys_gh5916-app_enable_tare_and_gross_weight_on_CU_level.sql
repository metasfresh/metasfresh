-- 2019-12-12T16:44:28.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI SET Description='Packing instructions for CUs (a.k.a. virtual HUs)',Updated=TO_TIMESTAMP('2019-12-12 17:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_ID=101
;

-- 2019-12-12T16:44:38.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Version SET Description='Packing instructions for CUs (a.k.a. virtual HUs)',Updated=TO_TIMESTAMP('2019-12-12 17:44:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Version_ID=101
;

-- 2019-12-12T16:44:43.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI_Version SET Description='Packing instructions version for CUs (a.k.a. virtual HUs)',Updated=TO_TIMESTAMP('2019-12-12 17:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Version_ID=101
;

-- Generally activate gross-weight and tare for "no packing items" (i.e. CUs / VHUs)
-- the attribute-value-callout will decide whether it's *really* displayed
UPDATE M_HU_PI_Attribute SET IsActive='Y', UpdatedBy=99, Updated='2019-12-12 16:49:14.203799+00' where M_Attribute_ID=540005 AND M_HU_PI_Version_ID=101;
UPDATE M_HU_PI_Attribute SET IsActive='Y', UpdatedBy=99, Updated='2019-12-12 16:49:14.203799+00' where M_Attribute_ID=540006 AND M_HU_PI_Version_ID=101;