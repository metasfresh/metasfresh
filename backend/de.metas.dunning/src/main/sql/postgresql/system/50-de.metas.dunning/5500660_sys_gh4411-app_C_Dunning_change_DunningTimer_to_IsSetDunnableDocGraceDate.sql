-- 2018-09-04T11:34:23.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544227,0,'IsUpdateDunnableDocGraceDate',TO_TIMESTAMP('2018-09-04 11:34:23','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten selbst setzt.','de.metas.dunning','Y','Mahnkarenz aktualisieren','Mahnkarenz aktualisieren',TO_TIMESTAMP('2018-09-04 11:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-04T11:34:23.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544227 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-04T11:34:27.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544227 AND AD_Language='de_CH'
;

-- 2018-09-04T11:34:27.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544227,'de_CH') 
;

-- 2018-09-04T11:35:28.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Update dunning grace date',PrintName='Update dunning grace date',Description='Determines whether the system automatically sets the dunning grace date of dunnable documents.',Help='' WHERE AD_Element_ID=544227 AND AD_Language='en_US'
;

-- 2018-09-04T11:35:28.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544227,'en_US') 
;

-- 2018-09-04T11:35:36.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.' WHERE AD_Element_ID=544227 AND AD_Language='nl_NL'
;

-- 2018-09-04T11:35:36.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544227,'nl_NL') 
;

-- 2018-09-04T11:35:41.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.' WHERE AD_Element_ID=544227 AND AD_Language='de_CH'
;

-- 2018-09-04T11:35:41.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544227,'de_CH') 
;

-- 2018-09-04T11:35:44.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.',Updated=TO_TIMESTAMP('2018-09-04 11:35:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544227
;

-- 2018-09-04T11:35:44.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsUpdateDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE AD_Element_ID=544227
;

-- 2018-09-04T11:35:44.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL, AD_Element_ID=544227 WHERE UPPER(ColumnName)='ISUPDATEDUNNABLEDOCGRACEDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-04T11:35:44.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsUpdateDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE AD_Element_ID=544227 AND IsCentrallyMaintained='Y'
;

-- 2018-09-04T11:35:44.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544227) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544227)
;

-- 2018-09-04T11:38:16.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsSetDunnableDocGraceDate',Updated=TO_TIMESTAMP('2018-09-04 11:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544227
;

-- 2018-09-04T11:38:16.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSetDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE AD_Element_ID=544227
;

-- 2018-09-04T11:38:16.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSetDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL, AD_Element_ID=544227 WHERE UPPER(ColumnName)='ISSETDUNNABLEDOCGRACEDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-04T11:38:16.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSetDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE AD_Element_ID=544227 AND IsCentrallyMaintained='Y'
;

-- 2018-09-04T11:39:02.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544227, AD_Reference_ID=20, AD_Reference_Value_ID=NULL, ColumnName='IsSetDunnableDocGraceDate', DefaultValue='N', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL, Name='Mahnkarenz aktualisieren',Updated=TO_TIMESTAMP('2018-09-04 11:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548498
;

-- 2018-09-04T11:39:02.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE AD_Column_ID=548498
;

-- 2018-09-04T11:39:06.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Dunning','ALTER TABLE public.C_Dunning ADD COLUMN IsSetDunnableDocGraceDate CHAR(1) DEFAULT ''N'' CHECK (IsSetDunnableDocGraceDate IN (''Y'',''N'')) NOT NULL')
;


-- 2018-09-04T11:44:09.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsManageDunnableDocGraceDate',Updated=TO_TIMESTAMP('2018-09-04 11:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544227
;

-- 2018-09-04T11:44:09.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsManageDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE AD_Element_ID=544227
;

-- 2018-09-04T11:44:09.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManageDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL, AD_Element_ID=544227 WHERE UPPER(ColumnName)='ISMANAGEDUNNABLEDOCGRACEDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-09-04T11:44:09.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManageDunnableDocGraceDate', Name='Mahnkarenz aktualisieren', Description='Entscheidet, ob das System die Mahnkarenz der zu mahnenden Dokumenten automatisch selbst setzt.', Help=NULL WHERE AD_Element_ID=544227 AND IsCentrallyMaintained='Y'
;

select db_alter_table('C_Dunning','ALTER TABLE public.C_Dunning RENAME COLUMN IsSetDunnableDocGraceDate TO IsManageDunnableDocGraceDate;');

UPDATE public.C_Dunning SET IsManageDunnableDocGraceDate='Y' WHERE DunningTimer='A';
select db_alter_table('C_Dunning','ALTER TABLE public.C_Dunning DROP COLUMN DunningTimer;');

-- delete the "Dunning timer" list reference
-- 2018-09-04T11:54:24.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540382
;

-- 2018-09-04T11:54:24.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540382
;

-- delete the DunningTimer AD_Element
-- 2018-09-04T11:55:16.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=542004
;

-- 2018-09-04T11:55:16.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=542004
;

