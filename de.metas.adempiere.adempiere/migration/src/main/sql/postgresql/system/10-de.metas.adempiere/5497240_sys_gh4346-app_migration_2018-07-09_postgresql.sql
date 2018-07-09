-- 2018-07-09T13:29:53.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=215
;

-- 2018-07-09T13:29:53.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=215
;

-- 2018-07-09T13:31:34.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19, IsUpdateable='N', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2018-07-09 13:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8757
;

-- 2018-07-09T13:31:39.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project','C_ProjectType_ID','NUMERIC(10)',null,null)
;

-- 2018-07-09T13:42:59.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Projektart', PrintName='Projektart',Updated=TO_TIMESTAMP('2018-07-09 13:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2033
;

-- 2018-07-09T13:42:59.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_ProjectType_ID', Name='Projektart', Description='Type of the project', Help='Type of the project with optional phases of the project with standard performance information' WHERE AD_Element_ID=2033
;

-- 2018-07-09T13:42:59.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_ProjectType_ID', Name='Projektart', Description='Type of the project', Help='Type of the project with optional phases of the project with standard performance information', AD_Element_ID=2033 WHERE UPPER(ColumnName)='C_PROJECTTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-09T13:42:59.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_ProjectType_ID', Name='Projektart', Description='Type of the project', Help='Type of the project with optional phases of the project with standard performance information' WHERE AD_Element_ID=2033 AND IsCentrallyMaintained='Y'
;

-- 2018-07-09T13:42:59.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Projektart', Description='Type of the project', Help='Type of the project with optional phases of the project with standard performance information' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2033) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2033)
;

-- 2018-07-09T13:42:59.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Projektart', Name='Projektart' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2033)
;

-- 2018-07-09T13:43:09.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-09 13:43:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Projektart',PrintName='Projektart',Description='',Help='' WHERE AD_Element_ID=2033 AND AD_Language='fr_CH'
;

-- 2018-07-09T13:43:09.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2033,'fr_CH') 
;

-- 2018-07-09T13:44:09.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2018-07-09 13:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560665
;

-- 2018-07-09T13:44:19.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Projekt-Nummernfolge', PrintName='Projekt-Nummernfolge',Updated=TO_TIMESTAMP('2018-07-09 13:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544174
;

-- 2018-07-09T13:44:19.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Sequence_ProjectValue_ID', Name='Projekt-Nummernfolge', Description='Nummerfolge für Projekt-Suchschlüssel', Help=NULL WHERE AD_Element_ID=544174
;

-- 2018-07-09T13:44:19.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Sequence_ProjectValue_ID', Name='Projekt-Nummernfolge', Description='Nummerfolge für Projekt-Suchschlüssel', Help=NULL, AD_Element_ID=544174 WHERE UPPER(ColumnName)='AD_SEQUENCE_PROJECTVALUE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-09T13:44:19.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Sequence_ProjectValue_ID', Name='Projekt-Nummernfolge', Description='Nummerfolge für Projekt-Suchschlüssel', Help=NULL WHERE AD_Element_ID=544174 AND IsCentrallyMaintained='Y'
;

-- 2018-07-09T13:44:19.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Projekt-Nummernfolge', Description='Nummerfolge für Projekt-Suchschlüssel', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544174) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544174)
;

-- 2018-07-09T13:44:19.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Projekt-Nummernfolge', Name='Projekt-Nummernfolge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544174)
;

-- 2018-07-09T13:44:22.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Nummernfolge für Projekt-Suchschlüssel',Updated=TO_TIMESTAMP('2018-07-09 13:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544174
;

-- 2018-07-09T13:44:22.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Sequence_ProjectValue_ID', Name='Projekt-Nummernfolge', Description='Nummernfolge für Projekt-Suchschlüssel', Help=NULL WHERE AD_Element_ID=544174
;

-- 2018-07-09T13:44:22.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Sequence_ProjectValue_ID', Name='Projekt-Nummernfolge', Description='Nummernfolge für Projekt-Suchschlüssel', Help=NULL, AD_Element_ID=544174 WHERE UPPER(ColumnName)='AD_SEQUENCE_PROJECTVALUE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-09T13:44:22.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Sequence_ProjectValue_ID', Name='Projekt-Nummernfolge', Description='Nummernfolge für Projekt-Suchschlüssel', Help=NULL WHERE AD_Element_ID=544174 AND IsCentrallyMaintained='Y'
;

-- 2018-07-09T13:44:22.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Projekt-Nummernfolge', Description='Nummernfolge für Projekt-Suchschlüssel', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544174) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544174)
;

-- 2018-07-09T13:44:32.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-09 13:44:32','YYYY-MM-DD HH24:MI:SS'),Name='Projekt-Nummernfolge',PrintName='Projekt-Nummernfolge',Description='Nummernfolge für Projekt-Suchschlüssel' WHERE AD_Element_ID=544174 AND AD_Language='de_CH'
;

-- 2018-07-09T13:44:32.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544174,'de_CH') 
;

-- 2018-07-09T14:21:28.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2018-07-09 14:21:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=216
;

-- 2018-07-09T14:21:37.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2018-07-09 14:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=427
;

-- 2018-07-09T14:21:46.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2018-07-09 14:21:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=426
;

-- 2018-07-09T14:32:53.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540403,'AD_Sequence.AD_Client_ID>0',TO_TIMESTAMP('2018-07-09 14:32:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Sequence_Client_and_Org','S',TO_TIMESTAMP('2018-07-09 14:32:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-09T14:33:05.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540403,Updated=TO_TIMESTAMP('2018-07-09 14:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560665
;

-- 2018-07-09T14:36:54.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Nummernfolgen',Updated=TO_TIMESTAMP('2018-07-09 14:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=112
;

-- 2018-07-09T14:36:54.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Nummernkreise für System und Dokumente verwalten', IsActive='Y', Name='Nummernfolgen',Updated=TO_TIMESTAMP('2018-07-09 14:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=151
;

-- 2018-07-09T14:36:54.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Nummernkreise für System und Dokumente verwalten', IsActive='Y', Name='Nummernfolgen',Updated=TO_TIMESTAMP('2018-07-09 14:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540828
;

-- 2018-07-09T14:36:54.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Description='Nummernkreise für System und Dokumente verwalten', Help='Im Fenster "Nummernkreis" wird definiert, wie die Abfolge von Dokumentennummern ist. Sie können die Art der Nummernerzeugung beeinflussen. Definieren Sie z.B. einen Präfix oder Suffix oder ändern Sie die derzeitige Nummer.', Name='Nummernfolgen',Updated=TO_TIMESTAMP('2018-07-09 14:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=130
;

