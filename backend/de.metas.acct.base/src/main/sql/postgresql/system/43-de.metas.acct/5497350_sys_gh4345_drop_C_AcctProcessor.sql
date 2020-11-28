-- 2018-07-11T12:13:15.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=440
;

-- 2018-07-11T12:13:15.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=440
;

-- 2018-07-11T12:13:15.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=440 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 2018-07-11T12:13:22.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2018-07-11 12:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=695
;

-- 2018-07-11T12:13:29.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2018-07-11 12:13:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=694
;

-- 2018-07-11T12:13:34.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=311
;

-- 2018-07-11T12:13:34.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=311
;

-- 2018-07-11T12:14:30.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=694
;

-- 2018-07-11T12:14:30.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=694
;

-- 2018-07-11T12:14:44.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=695
;

-- 2018-07-11T12:14:44.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=695
;

-- 2018-07-11T12:15:03.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=2368
;

-- 2018-07-11T12:15:03.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=2368
;

-- 2018-07-11T12:15:03.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=2369
;

-- 2018-07-11T12:15:03.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=2369
;

delete from AD_Sequence where Name in ('C_AcctProcessor', 'C_AcctProcessorLog') and IsTableID='Y';
drop sequence if exists C_AcctProcessor_Seq;
drop sequence if exists C_AcctProcessorLog_Seq;


drop view if exists "de.metas.monitoring".c_acctprocessor_aktualisieren_v;
drop view if exists "de.metas.monitoring".c_acctprocessorlog_error_count_v;

create table backup.C_AcctProcessor_BKP as select * from C_AcctProcessor;
create table backup.C_AcctProcessorLog_BKP as select * from C_AcctProcessorLog;

drop table if exists C_AcctProcessorLog;
drop table if exists C_AcctProcessor;

