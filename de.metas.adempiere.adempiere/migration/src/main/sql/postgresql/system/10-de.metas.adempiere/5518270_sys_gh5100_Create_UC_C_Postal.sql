
-- -- 2019-04-04T14:27:16.336
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- DROP INDEX IF EXISTS c_postal_unique
-- ;

-- -- 2019-04-04T14:27:16.338
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- CREATE UNIQUE INDEX C_Postal_Unique ON C_Postal (C_Country_ID,Postal) WHERE IsActive='Y'
-- ;

-- COMMENTED OUT BECAUSE WE NEED MORE PERMISSINVE RULES!
-- SEE 5521480_sys_gh5192_C_Postal_UniqueIndex.sql