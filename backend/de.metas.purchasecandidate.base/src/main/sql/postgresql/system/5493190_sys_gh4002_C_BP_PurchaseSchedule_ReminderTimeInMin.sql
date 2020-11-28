-- 2018-05-10T20:35:37.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Wiedervorlage (min)', ColumnName='ReminderTimeInMin', Name='Wiedervorlage (min)',Updated=TO_TIMESTAMP('2018-05-10 20:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544046
;

-- 2018-05-10T20:35:37.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ReminderTimeInMin', Name='Wiedervorlage (min)', Description=NULL, Help=NULL WHERE AD_Element_ID=544046
;

-- 2018-05-10T20:35:37.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReminderTimeInMin', Name='Wiedervorlage (min)', Description=NULL, Help=NULL, AD_Element_ID=544046 WHERE UPPER(ColumnName)='REMINDERTIMEINMIN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-10T20:35:37.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ReminderTimeInMin', Name='Wiedervorlage (min)', Description=NULL, Help=NULL WHERE AD_Element_ID=544046 AND IsCentrallyMaintained='Y'
;

-- 2018-05-10T20:35:37.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wiedervorlage (min)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544046) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544046)
;

-- 2018-05-10T20:35:37.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wiedervorlage (min)', Name='Wiedervorlage (min)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544046)
;

alter table C_BP_PurchaseSchedule drop column if exists ReminderTime;

-- 2018-05-10T20:36:55.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', ValueMin='0', IsMandatory='Y', FieldLength=10,Updated=TO_TIMESTAMP('2018-05-10 20:36:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560046
;

-- 2018-05-10T20:37:02.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BP_PurchaseSchedule','ALTER TABLE public.C_BP_PurchaseSchedule ADD COLUMN ReminderTimeInMin NUMERIC(10) DEFAULT 0 NOT NULL')
;

