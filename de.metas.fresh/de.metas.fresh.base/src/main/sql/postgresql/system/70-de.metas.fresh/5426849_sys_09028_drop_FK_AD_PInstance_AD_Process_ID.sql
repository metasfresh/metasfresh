-- REASON: AD_PInstance is a huge table, which is also referenced by a lot of temporary tables
-- Deleting an AD_Process is almost impossible, because the deletion will take ages.

alter table AD_PInstance drop constraint if exists adprocess_adpinstance;

-- 18.09.2015 13:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2015-09-18 13:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2781
;

