--
-- C_BPartner_Location.GLN
--
-- 2018-10-14T17:34:01.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D', TechnicalNote='I think the column has IsCalculculated because generally each location has its own GLN, so there is no point in coyping it.',Updated=TO_TIMESTAMP('2018-10-14 17:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548323
;


-- cleanup
DELETE FROM AD_Menu WHERE AD_Process_ID=53070; -- 'PrepareMigrationScripts'
DELETE FROM AD_Process WHERE AD_Process_ID=53070; 
