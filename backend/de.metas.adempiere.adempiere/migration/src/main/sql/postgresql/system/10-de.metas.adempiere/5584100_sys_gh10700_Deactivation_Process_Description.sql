-- 2021-03-26T16:56:32.593292800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET Description='This process is scheduled to be run each night to deactivate bpartners (and their locations, contacts and bank accounts) that were moved to another organization and are scheduled to be deactivated at a given date via AD_OrgChange_History.',Updated=TO_TIMESTAMP('2021-03-26 18:56:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550070
;

-- 2021-03-26T16:57:01.314983400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET TechnicalNote='This process is scheduled to be run each night to deactivate bpartners (and their locations, contacts and bank accounts) that were moved to another organization and are scheduled to be deactivated at a given date via AD_OrgChange_History',Updated=TO_TIMESTAMP('2021-03-26 18:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584814
;

