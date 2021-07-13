-- 2019-11-27T07:31:43.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547287
;

-- 2019-11-27T07:31:43.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551760
;

-- 2019-11-27T07:31:43.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547304
;

-- 2019-11-27T07:31:43.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552464
;

-- 2019-11-27T07:31:43.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500231
;

-- 2019-11-27T07:31:43.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545450
;

-- 2019-11-27T07:31:43.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=540358
;

-- 2019-11-27T07:31:43.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552463
;

-- 2019-11-27T07:31:43.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=120,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551759
;

-- 2019-11-27T07:31:43.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=130,Updated=TO_TIMESTAMP('2019-11-27 08:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500222
;

-- 2019-11-27T13:25:28.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,540676,'N',TO_TIMESTAMP('2019-11-27 14:25:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,'Y','N','EDI_DesadvLine_SSCC','N','N','N',TO_TIMESTAMP('2019-11-27 14:25:28','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DesadvLine_SSCC','1.0')
;

-- 2019-11-27T13:25:33.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552514,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540417,550258,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 2019-11-27T13:25:33.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552515,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540417,550259,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 2019-11-27T13:25:33.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552516,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540417,550260,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',30,'E',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 2019-11-27T13:25:33.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552517,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540417,550261,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',40,'R',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 2019-11-27T13:25:33.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552522,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550262,'N','Y','EDI_DesadvLine',50,'R',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DesadvLine_ID')
;

-- 2019-11-27T13:25:33.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552521,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550263,'N','Y','EDI_DesadvLine_SSCC',60,'R',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DesadvLine_SSCC_ID')
;

-- 2019-11-27T13:25:33.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552523,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550264,'N','Y','SSCC18',70,'E',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'IPA_SSCC18')
;

-- 2019-11-27T13:25:33.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552518,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540417,550265,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',80,'E',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 2019-11-27T13:25:33.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552531,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550266,'N','N','Menge CU',90,'E',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'QtyCU')
;

-- 2019-11-27T13:25:33.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552569,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550267,'N','N','Menge CU/LU',100,'E',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsPerLU')
;

-- 2019-11-27T13:25:34.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552532,0,TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550268,'N','N','TU Anzahl',110,'E',TO_TIMESTAMP('2019-11-27 14:25:33','YYYY-MM-DD HH24:MI:SS'),100,'QtyTU')
;

-- 2019-11-27T13:25:34.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552519,0,TO_TIMESTAMP('2019-11-27 14:25:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540417,550269,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',120,'E',TO_TIMESTAMP('2019-11-27 14:25:34','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 2019-11-27T13:25:34.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552520,0,TO_TIMESTAMP('2019-11-27 14:25:34','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540417,550270,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',130,'R',TO_TIMESTAMP('2019-11-27 14:25:34','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 2019-11-27T13:25:58.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-11-27 14:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550264
;

-- 2019-11-27T13:26:04.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-11-27 14:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550262
;

-- 2019-11-27T13:26:07.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-11-27 14:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550266
;

-- 2019-11-27T13:26:08.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-11-27 14:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550267
;

-- 2019-11-27T13:26:43.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-11-27 14:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550268
;

-- 2019-11-27T13:27:05.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 14:27:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550262
;

-- 2019-11-27T13:28:15.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_Format SET Name='EDI_Exp_DesadvLine_SSCC',Updated=TO_TIMESTAMP('2019-11-27 14:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540417
;

-- 2019-11-27T13:29:52.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2019-11-27 14:29:52','YYYY-MM-DD HH24:MI:SS'),100,'U',540417,540406,550271,'Y','N','N','EDI_Exp_DesadvLine_SSCC',340,'M',TO_TIMESTAMP('2019-11-27 14:29:52','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DesadvLine_SSCC')
;

-- 2019-11-27T13:30:07.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2019-11-27 14:30:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550271
;

-- 2019-11-27T13:30:38.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='EDI_Desadv',Updated=TO_TIMESTAMP('2019-11-27 14:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540644
;

-- 2019-11-27T13:31:16.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_Format SET Version='*',Updated=TO_TIMESTAMP('2019-11-27 14:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540417
;

-- 2019-11-27T13:31:40.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_Format SET Value='EDI_Exp_DesadvLine_SSCC',Updated=TO_TIMESTAMP('2019-11-27 14:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540417
;

-- 2019-11-27T13:32:26.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Position=95,Updated=TO_TIMESTAMP('2019-11-27 14:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550271
;

-- 2019-11-27T13:45:53.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='metasfresh ticks this flag if there is no HU assigned and the user can manually provide a SSCC18 value.', IsTranslated='Y', Name='manual SSCC18', PrintName='manual SSCC18',Updated=TO_TIMESTAMP('2019-11-27 14:45:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542729 AND AD_Language='en_GB'
;

-- 2019-11-27T13:45:53.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542729,'en_GB') 
;

-- 2019-11-27T13:46:15.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-27 14:46:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542729 AND AD_Language='de_CH'
;

-- 2019-11-27T13:46:15.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542729,'de_CH') 
;

-- 2019-11-27T13:46:29.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-27 14:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542729 AND AD_Language='de_DE'
;

-- 2019-11-27T13:46:29.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542729,'de_DE') 
;

-- 2019-11-27T13:46:29.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542729,'de_DE') 
;

-- 2019-11-27T13:46:29.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsManual_IPA_SSCC18', Name='manuelle SSCC18', Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', Help=NULL WHERE AD_Element_ID=542729
;

-- 2019-11-27T13:46:29.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManual_IPA_SSCC18', Name='manuelle SSCC18', Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', Help=NULL, AD_Element_ID=542729 WHERE UPPER(ColumnName)='ISMANUAL_IPA_SSCC18' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-27T13:46:29.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsManual_IPA_SSCC18', Name='manuelle SSCC18', Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', Help=NULL WHERE AD_Element_ID=542729 AND IsCentrallyMaintained='Y'
;

-- 2019-11-27T13:46:29.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='manuelle SSCC18', Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542729) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542729)
;

-- 2019-11-27T13:46:29.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='manuelle SSCC18', Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542729
;

-- 2019-11-27T13:46:29.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='manuelle SSCC18', Description='Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', Help=NULL WHERE AD_Element_ID = 542729
;

-- 2019-11-27T13:46:29.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'manuelle SSCC18', Description = 'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542729
;

-- 2019-11-27T13:46:38.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='manual SSCC18', PrintName='manual SSCC18',Updated=TO_TIMESTAMP('2019-11-27 14:46:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542729 AND AD_Language='en_US'
;

-- 2019-11-27T13:46:38.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542729,'en_US') 
;

-- 2019-11-27T13:46:46.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='metasfresh ticks this flag if there is no HU assigned and the user can manually provide a SSCC18 value.',Updated=TO_TIMESTAMP('2019-11-27 14:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542729 AND AD_Language='en_US'
;

-- 2019-11-27T13:46:46.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542729,'en_US') 
;

-- 2019-11-27T15:33:57.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577395,0,'SingleSSCC18',TO_TIMESTAMP('2019-11-27 16:33:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','einz. SSCC18','einz. SSCC18',TO_TIMESTAMP('2019-11-27 16:33:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T15:33:57.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577395 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-27T15:34:00.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-27 16:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577395 AND AD_Language='de_CH'
;

-- 2019-11-27T15:34:00.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577395,'de_CH') 
;

-- 2019-11-27T15:34:03.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-27 16:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577395 AND AD_Language='de_DE'
;

-- 2019-11-27T15:34:03.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577395,'de_DE') 
;

-- 2019-11-27T15:34:03.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577395,'de_DE') 
;

-- 2019-11-27T15:35:08.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='SSCC18', PrintName='SSCC18',Updated=TO_TIMESTAMP('2019-11-27 16:35:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577395 AND AD_Language='en_US'
;

-- 2019-11-27T15:35:08.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577395,'en_US') 
;

-- 2019-11-27T15:35:17.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='SSCC18',Updated=TO_TIMESTAMP('2019-11-27 16:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577395 AND AD_Language='de_DE'
;

-- 2019-11-27T15:35:17.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577395,'de_DE') 
;

-- 2019-11-27T15:35:17.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577395,'de_DE') 
;

-- 2019-11-27T15:35:17.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SingleSSCC18', Name='SSCC18', Description=NULL, Help=NULL WHERE AD_Element_ID=577395
;

-- 2019-11-27T15:35:17.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SingleSSCC18', Name='SSCC18', Description=NULL, Help=NULL, AD_Element_ID=577395 WHERE UPPER(ColumnName)='SINGLESSCC18' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-27T15:35:17.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SingleSSCC18', Name='SSCC18', Description=NULL, Help=NULL WHERE AD_Element_ID=577395 AND IsCentrallyMaintained='Y'
;

-- 2019-11-27T15:35:17.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='SSCC18', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577395) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577395)
;

-- 2019-11-27T15:35:17.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='einz. SSCC18', Name='SSCC18' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577395)
;

-- 2019-11-27T15:35:17.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='SSCC18', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577395
;

-- 2019-11-27T15:35:17.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='SSCC18', Description=NULL, Help=NULL WHERE AD_Element_ID = 577395
;

-- 2019-11-27T15:35:17.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'SSCC18', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577395
;

-- 2019-11-27T15:35:23.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='SSCC18', PrintName='SSCC18',Updated=TO_TIMESTAMP('2019-11-27 16:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577395 AND AD_Language='de_CH'
;

-- 2019-11-27T15:35:23.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577395,'de_CH') 
;

-- 2019-11-27T15:35:26.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='SSCC18',Updated=TO_TIMESTAMP('2019-11-27 16:35:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577395 AND AD_Language='de_DE'
;

-- 2019-11-27T15:35:26.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577395,'de_DE') 
;

-- 2019-11-27T15:35:26.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577395,'de_DE') 
;

-- 2019-11-27T15:35:26.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='SSCC18', Name='SSCC18' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577395)
;

-- 2019-11-27T15:35:58.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=577395
;

-- 2019-11-27T15:35:58.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=577395
;

-- 2019-11-27T16:02:52.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:02:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:02:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540676,'N',569674,'N','N','N','N','N','N','N','N',0,542140,'de.metas.esb.edi','N','N','M_HU_ID','N','Handling Unit',0)
;

-- 2019-11-27T16:02:52.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569674 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:02:52.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542140) 
;

-- 2019-11-27T16:03:04.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_SSCC','ALTER TABLE public.EDI_DesadvLine_SSCC ADD COLUMN M_HU_ID NUMERIC(10)')
;

-- 2019-11-27T16:03:04.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_DesadvLine_SSCC ADD CONSTRAINT MHU_EDIDesadvLineSSCC FOREIGN KEY (M_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- 2019-11-27T16:05:14.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI Lieferavis SSCC (DESADV)',Updated=TO_TIMESTAMP('2019-11-27 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542840 AND AD_Language='en_GB'
;

-- 2019-11-27T16:05:14.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542840,'en_GB') 
;

-- 2019-11-27T16:05:21.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI Lieferavis SSCC (DESADV)',Updated=TO_TIMESTAMP('2019-11-27 17:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542840 AND AD_Language='de_CH'
;

-- 2019-11-27T16:05:21.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542840,'de_CH') 
;

-- 2019-11-27T16:05:33.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI Lieferavis SSCC (DESADV)', PrintName='EDI Lieferavis SSCC (DESADV)',Updated=TO_TIMESTAMP('2019-11-27 17:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542840 AND AD_Language='de_DE'
;

-- 2019-11-27T16:05:33.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542840,'de_DE') 
;

-- 2019-11-27T16:05:33.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542840,'de_DE') 
;

-- 2019-11-27T16:05:33.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='EDI_DesadvLine_SSCC_ID', Name='EDI Lieferavis SSCC (DESADV)', Description=NULL, Help=NULL WHERE AD_Element_ID=542840
;

-- 2019-11-27T16:05:33.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_DesadvLine_SSCC_ID', Name='EDI Lieferavis SSCC (DESADV)', Description=NULL, Help=NULL, AD_Element_ID=542840 WHERE UPPER(ColumnName)='EDI_DESADVLINE_SSCC_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-27T16:05:33.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='EDI_DesadvLine_SSCC_ID', Name='EDI Lieferavis SSCC (DESADV)', Description=NULL, Help=NULL WHERE AD_Element_ID=542840 AND IsCentrallyMaintained='Y'
;

-- 2019-11-27T16:05:33.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='EDI Lieferavis SSCC (DESADV)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542840) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542840)
;

-- 2019-11-27T16:05:33.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='EDI Lieferavis SSCC (DESADV)', Name='EDI Lieferavis SSCC (DESADV)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542840)
;

-- 2019-11-27T16:05:33.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='EDI Lieferavis SSCC (DESADV)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542840
;

-- 2019-11-27T16:05:33.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='EDI Lieferavis SSCC (DESADV)', Description=NULL, Help=NULL WHERE AD_Element_ID = 542840
;

-- 2019-11-27T16:05:33.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'EDI Lieferavis SSCC (DESADV)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542840
;

-- 2019-11-27T16:05:37.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='EDI Lieferavis SSCC (DESADV)',Updated=TO_TIMESTAMP('2019-11-27 17:05:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542840 AND AD_Language='de_CH'
;

-- 2019-11-27T16:05:37.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542840,'de_CH') 
;

-- 2019-11-27T16:05:43.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='EDI Lieferavis SSCC (DESADV)',Updated=TO_TIMESTAMP('2019-11-27 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542840 AND AD_Language='en_GB'
;

-- 2019-11-27T16:05:43.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542840,'en_GB') 
;

-- 2019-11-27T16:06:08.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI Lieferavis SSCC (DESADV)', PrintName='EDI Lieferavis SSCC (DESADV)',Updated=TO_TIMESTAMP('2019-11-27 17:06:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542840 AND AD_Language='en_US'
;

-- 2019-11-27T16:06:08.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542840,'en_US') 
;

-- 2019-11-27T16:06:47.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,542840,0,540780,TO_TIMESTAMP('2019-11-27 17:06:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','N','N','N','N','Y','EDI Lieferavis SSCC (DESADV)','N',TO_TIMESTAMP('2019-11-27 17:06:46','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-11-27T16:06:47.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540780 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-11-27T16:06:47.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(542840) 
;

-- 2019-11-27T16:06:47.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540780
;

-- 2019-11-27T16:06:47.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540780)
;

-- 2019-11-27T16:11:03.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,542840,0,542152,540676,540780,'Y',TO_TIMESTAMP('2019-11-27 17:11:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','N','N','EDI_DesadvLine_SSCC','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'EDI Lieferavis SSCC (DESADV)','N',10,0,TO_TIMESTAMP('2019-11-27 17:11:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:03.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542152 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-11-27T16:11:03.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(542840) 
;

-- 2019-11-27T16:11:03.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542152)
;

-- 2019-11-27T16:11:23.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552514,592802,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.esb.edi','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','N','N','N','Y','N','Mandant',0,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:23.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:23.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-11-27T16:11:23.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592802
;

-- 2019-11-27T16:11:23.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592802)
;

-- 2019-11-27T16:11:23.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552515,592803,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.esb.edi','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','N','N','N','N','N','Sektion',0,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:23.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:23.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-11-27T16:11:23.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592803
;

-- 2019-11-27T16:11:23.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592803)
;

-- 2019-11-27T16:11:23.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552522,592804,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi',0,'Y','Y','Y','N','N','N','N','N','EDI_DesadvLine',10,10,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:23.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:23.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542692) 
;

-- 2019-11-27T16:11:23.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592804
;

-- 2019-11-27T16:11:23.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592804)
;

-- 2019-11-27T16:11:23.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552523,592805,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,20,'de.metas.esb.edi',0,'Y','Y','Y','N','N','N','N','N','SSCC18',20,20,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:23.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:23.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542693) 
;

-- 2019-11-27T16:11:23.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592805
;

-- 2019-11-27T16:11:23.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592805)
;

-- 2019-11-27T16:11:23.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552531,592806,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.esb.edi',0,'Y','Y','Y','N','N','N','N','N','Menge CU',30,30,0,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:23.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:23.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542492) 
;

-- 2019-11-27T16:11:23.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592806
;

-- 2019-11-27T16:11:23.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592806)
;

-- 2019-11-27T16:11:23.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552532,592807,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.esb.edi',0,'Y','Y','Y','N','N','N','N','N','TU Anzahl',40,40,0,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:23.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:23.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542490) 
;

-- 2019-11-27T16:11:23.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592807
;

-- 2019-11-27T16:11:23.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592807)
;

-- 2019-11-27T16:11:23.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552569,592808,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi',0,'Y','Y','Y','N','N','N','N','N','Menge CU/LU',45,45,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:23.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:23.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542862) 
;

-- 2019-11-27T16:11:23.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592808
;

-- 2019-11-27T16:11:23.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592808)
;

-- 2019-11-27T16:11:24.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552518,592809,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.esb.edi','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2019-11-27 17:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:24.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:24.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-11-27T16:11:24.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592809
;

-- 2019-11-27T16:11:24.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592809)
;

-- 2019-11-27T16:11:24.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552521,592810,0,542152,0,TO_TIMESTAMP('2019-11-27 17:11:24','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi',0,'Y','N','N','N','N','N','N','N','EDI Lieferavis SSCC (DESADV)',1,1,TO_TIMESTAMP('2019-11-27 17:11:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:11:24.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:11:24.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542840) 
;

-- 2019-11-27T16:11:24.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592810
;

-- 2019-11-27T16:11:24.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592810)
;

-- 2019-11-27T16:13:42.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,ColumnSQL,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:13:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:13:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540676,'N',569675,'N','N','N','N','N','N','','N','N',0,542691,'de.metas.esb.edi','N','N','EDI_Desadv_ID','N','DESADV',0)
;

-- 2019-11-27T16:13:42.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569675 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:13:42.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542691) 
;

-- 2019-11-27T16:13:42.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_SSCC','ALTER TABLE public.EDI_DesadvLine_SSCC ADD COLUMN EDI_Desadv_ID NUMERIC(10)')
;

UPDATE EDI_DesadvLine_SSCC s SET EDI_Desadv_ID=l.EDI_Desadv_ID
FROM EDI_DesadvLine l
WHERE l.EDI_DesadvLine_ID=s.EDI_DesadvLine_ID;

-- 2019-11-27T16:16:36.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-11-27 17:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569675
;

-- 2019-11-27T16:16:38.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline_sscc','EDI_Desadv_ID','NUMERIC(10)',null,null)
;

-- 2019-11-27T16:16:38.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline_sscc','EDI_Desadv_ID',null,'NOT NULL',null)
;

-- 2019-11-27T16:17:01.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540780,Updated=TO_TIMESTAMP('2019-11-27 17:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540676
;

-- 2019-11-27T16:17:24.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2019-11-27 17:17:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569675
;

-- 2019-11-27T16:17:24.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2019-11-27 17:17:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569674
;

-- 2019-11-27T16:17:24.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-11-27 17:17:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552515
;

-- 2019-11-27T16:17:45.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569674,592811,0,542152,TO_TIMESTAMP('2019-11-27 17:17:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','Y','N','N','N','N','N','Handling Unit',TO_TIMESTAMP('2019-11-27 17:17:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:17:45.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:17:45.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140) 
;

-- 2019-11-27T16:17:45.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592811
;

-- 2019-11-27T16:17:45.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592811)
;

-- 2019-11-27T16:17:45.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569675,592812,0,542152,TO_TIMESTAMP('2019-11-27 17:17:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','Y','N','N','N','N','N','DESADV',TO_TIMESTAMP('2019-11-27 17:17:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:17:45.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:17:45.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542691) 
;

-- 2019-11-27T16:17:45.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592812
;

-- 2019-11-27T16:17:45.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592812)
;

-- 2019-11-27T16:18:08.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-27 17:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=592803
;

-- 2019-11-27T16:18:19.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-27 17:18:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=592811
;

-- 2019-11-27T16:18:52.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:18:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:18:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540676,'N',569676,'N','Y','N','N','N','N','N','N',0,542729,'de.metas.esb.edi','N','N','IsManual_IPA_SSCC18','N','manuelle SSCC18',0,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.')
;

-- 2019-11-27T16:18:52.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569676 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:18:52.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542729) 
;

-- 2019-11-27T16:18:53.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_SSCC','ALTER TABLE public.EDI_DesadvLine_SSCC ADD COLUMN IsManual_IPA_SSCC18 CHAR(1) DEFAULT ''N'' CHECK (IsManual_IPA_SSCC18 IN (''Y'',''N'')) NOT NULL')
;

-- 2019-11-27T16:19:17.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-11-27 17:19:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569676
;

-- 2019-11-27T16:19:17.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-11-27 17:19:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552523
;

-- 2019-11-27T16:19:17.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2019-11-27 17:19:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552515
;

-- 2019-11-27T16:19:44.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569676,592813,0,542152,TO_TIMESTAMP('2019-11-27 17:19:44','YYYY-MM-DD HH24:MI:SS'),100,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.',1,'de.metas.esb.edi','Y','Y','N','N','N','N','N','manuelle SSCC18',TO_TIMESTAMP('2019-11-27 17:19:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:19:44.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:19:44.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542729) 
;

-- 2019-11-27T16:19:44.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592813
;

-- 2019-11-27T16:19:44.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592813)
;

-- 2019-11-27T16:20:07.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542152,541676,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-11-27T16:20:07.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541676 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-11-27T16:20:07.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542131,541676,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:07.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542132,541676,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:07.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542131,543218,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:07.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592802,0,542152,543218,564352,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:07.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592803,0,542152,543218,564353,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:07.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592811,0,542152,543218,564354,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Handling Unit',10,30,0,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:07.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592812,0,542152,543218,564355,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','DESADV',20,40,0,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:08.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592813,0,542152,543218,564356,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.','Y','N','Y','Y','N','manuelle SSCC18',30,50,0,TO_TIMESTAMP('2019-11-27 17:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:08.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592804,0,542152,543218,564357,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','EDI_DesadvLine',40,60,0,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:08.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592805,0,542152,543218,564358,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','SSCC18',50,70,0,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:08.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592806,0,542152,543218,564359,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Menge CU',60,80,0,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:08.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592807,0,542152,543218,564360,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','TU Anzahl',70,90,0,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:08.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592808,0,542152,543218,564361,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Menge CU/LU',80,100,0,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:20:08.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592809,0,542152,543218,564362,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',90,110,0,TO_TIMESTAMP('2019-11-27 17:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:21:42.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='AD_Client_ID',Updated=TO_TIMESTAMP('2019-11-27 17:21:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564352
;

-- 2019-11-27T16:21:48.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='AD_Org_ID',Updated=TO_TIMESTAMP('2019-11-27 17:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564353
;

-- 2019-11-27T16:21:54.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='M_HU_ID',Updated=TO_TIMESTAMP('2019-11-27 17:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564354
;

-- 2019-11-27T16:22:02.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='EDI_Desadv_ID',Updated=TO_TIMESTAMP('2019-11-27 17:22:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564355
;

-- 2019-11-27T16:22:12.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='IsManual_IPA_SSCC18',Updated=TO_TIMESTAMP('2019-11-27 17:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564356
;

-- 2019-11-27T16:22:24.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='EDI_DesadvLine_ID',Updated=TO_TIMESTAMP('2019-11-27 17:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564357
;

-- 2019-11-27T16:22:36.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='IPA_SSCC18',Updated=TO_TIMESTAMP('2019-11-27 17:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564358
;

-- 2019-11-27T16:22:44.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyCU',Updated=TO_TIMESTAMP('2019-11-27 17:22:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564359
;

-- 2019-11-27T16:22:53.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyTU',Updated=TO_TIMESTAMP('2019-11-27 17:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564360
;

-- 2019-11-27T16:23:03.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyCUsPerLU',Updated=TO_TIMESTAMP('2019-11-27 17:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564361
;

-- 2019-11-27T16:23:11.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='IsActive',Updated=TO_TIMESTAMP('2019-11-27 17:23:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2019-11-27T16:25:13.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564352
;

-- 2019-11-27T16:25:13.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564353
;

-- 2019-11-27T16:25:13.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564355
;

-- 2019-11-27T16:25:13.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564357
;

-- 2019-11-27T16:25:13.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564356
;

-- 2019-11-27T16:25:13.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564354
;

-- 2019-11-27T16:25:13.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564358
;

-- 2019-11-27T16:25:13.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564359
;

-- 2019-11-27T16:25:13.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564360
;

-- 2019-11-27T16:25:13.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564361
;

-- 2019-11-27T16:25:13.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-11-27 17:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2019-11-27T16:26:30.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2019-11-27 17:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551741
;

-- 2019-11-27T16:26:46.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-11-27 17:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551741
;

-- 2019-11-27T16:27:07.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsManual_IPA_SSCC18/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-27 17:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551749
;

-- 2019-11-27T16:27:39.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:27:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540698
;

-- 2019-11-27T16:29:11.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='EDI_DesadvLine_SSCC',Updated=TO_TIMESTAMP('2019-11-27 17:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540780
;

-- 2019-11-27T16:29:18.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,542840,541406,0,540780,TO_TIMESTAMP('2019-11-27 17:29:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','EDI_DesadvLine_SSCC','Y','N','N','N','N','EDI Lieferavis SSCC (DESADV)',TO_TIMESTAMP('2019-11-27 17:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:29:18.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541406 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-11-27T16:29:18.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541406, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541406)
;

-- 2019-11-27T16:29:18.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(542840) 
;

-- 2019-11-27T16:29:18.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:18.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2019-11-27T16:29:20.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- 2019-11-27T16:30:08.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsAutocomplete='Y', IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-11-27 17:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540676
;

-- 2019-11-27T16:30:21.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2019-11-27 17:30:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569675
;

-- 2019-11-27T16:32:07.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540474,'EDI_DesadvLine.EDI_Desadv_ID=@EDI_Desadv_ID/0@',TO_TIMESTAMP('2019-11-27 17:32:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','EDI_DesadvLine of EDI_Desadv','S',TO_TIMESTAMP('2019-11-27 17:32:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:32:19.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540474, AD_Reference_ID=19, IsUpdateable='N',Updated=TO_TIMESTAMP('2019-11-27 17:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552522
;

-- 2019-11-27T16:32:34.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20, IsUpdateable='N',Updated=TO_TIMESTAMP('2019-11-27 17:32:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552522
;

-- 2019-11-27T16:32:44.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2019-11-27 17:32:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569675
;

-- 2019-11-27T16:32:57.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2019-11-27 17:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552523
;

-- 2019-11-27T16:33:07.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='Y',Updated=TO_TIMESTAMP('2019-11-27 17:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540676
;

-- 2019-11-27T16:33:11.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2019-11-27 17:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540676
;

-- 2019-11-27T16:41:17.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:41:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551755
;

-- 2019-11-27T16:41:41.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551897
;

-- 2019-11-27T16:42:20.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:42:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551749
;

-- 2019-11-27T16:47:17.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (15,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:47:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:47:17','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540676,'N',569677,'N','N','N','N','N','N','N','N',0,577375,'de.metas.esb.edi','N','N','BestBeforeDate','N','Mindesthaltbarkeitsdatum',0)
;

-- 2019-11-27T16:47:17.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569677 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:47:17.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577375) 
;

-- 2019-11-27T16:47:20.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_SSCC','ALTER TABLE public.EDI_DesadvLine_SSCC ADD COLUMN BestBeforeDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2019-11-27T16:47:36.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569677,592814,0,542152,TO_TIMESTAMP('2019-11-27 17:47:36','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.esb.edi','Y','Y','N','N','N','N','N','Mindesthaltbarkeitsdatum',TO_TIMESTAMP('2019-11-27 17:47:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:47:36.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=592814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-27T16:47:36.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577375) 
;

-- 2019-11-27T16:47:36.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=592814
;

-- 2019-11-27T16:47:36.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(592814)
;

-- 2019-11-27T16:48:09.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,592814,0,542152,543218,564364,'F',TO_TIMESTAMP('2019-11-27 17:48:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'BestBeforeDate',100,0,0,TO_TIMESTAMP('2019-11-27 17:48:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-27T16:48:17.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-11-27 17:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564364
;

-- 2019-11-27T16:48:17.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-11-27 17:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564362
;

-- 2019-11-27T16:51:32.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:51:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569642
;

-- 2019-11-27T16:51:56.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569677,0,TO_TIMESTAMP('2019-11-27 17:51:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550272,'N','N','Mindesthaltbarkeitsdatum',140,'E',TO_TIMESTAMP('2019-11-27 17:51:56','YYYY-MM-DD HH24:MI:SS'),100,'BestBeforeDate')
;

-- 2019-11-27T16:51:57.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569675,0,TO_TIMESTAMP('2019-11-27 17:51:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550273,'Y','Y','Y','DESADV',150,'R',TO_TIMESTAMP('2019-11-27 17:51:56','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_ID')
;

-- 2019-11-27T16:51:57.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569676,0,TO_TIMESTAMP('2019-11-27 17:51:57','YYYY-MM-DD HH24:MI:SS'),100,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.','de.metas.esb.edi',540417,550274,'N','Y','manuelle SSCC18',160,'E',TO_TIMESTAMP('2019-11-27 17:51:57','YYYY-MM-DD HH24:MI:SS'),100,'IsManual_IPA_SSCC18')
;

-- 2019-11-27T16:51:57.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569674,0,TO_TIMESTAMP('2019-11-27 17:51:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550275,'N','N','Handling Unit',170,'R',TO_TIMESTAMP('2019-11-27 17:51:57','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_ID')
;

-- 2019-11-27T16:52:50.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550257
;

-- 2019-11-27T16:56:52.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Val_Rule_ID,AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (540463,18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:56:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:56:52','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540676,'N',541064,569678,'N','N','N','N','N','N','N','N',0,577199,'de.metas.esb.edi','N','N','M_HU_PackagingCode_LU_ID','N','LU Verpackungscode',0)
;

-- 2019-11-27T16:56:52.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569678 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:56:52.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577199) 
;

-- 2019-11-27T16:56:54.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_SSCC','ALTER TABLE public.EDI_DesadvLine_SSCC ADD COLUMN M_HU_PackagingCode_LU_ID NUMERIC(10)')
;

-- 2019-11-27T16:56:54.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_DesadvLine_SSCC ADD CONSTRAINT MHUPackagingCodeLU_EDIDesadvLineSSCC FOREIGN KEY (M_HU_PackagingCode_LU_ID) REFERENCES public.M_HU_PackagingCode DEFERRABLE INITIALLY DEFERRED
;

-- 2019-11-27T16:57:34.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,ColumnSQL,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,TechnicalNote,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (10,4,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:57:34','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:57:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',540676,'N','The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',569679,'N','N','N','N','N','N','(select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_LU_ID)','N','N',0,577201,'de.metas.esb.edi','N','The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export. The exporter makes sure that we won''t export a stale value here.','N','M_HU_PackagingCode_LU_Text','N','M_HU_PackagingCode_LU_Text',0)
;

-- 2019-11-27T16:57:34.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569679 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:57:34.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577201) 
;

-- 2019-11-27T16:57:46.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569179
;

-- 2019-11-27T16:57:54.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='N',Updated=TO_TIMESTAMP('2019-11-27 17:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569679
;

-- 2019-11-27T16:58:46.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Val_Rule_ID,AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (540464,18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:58:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:58:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540676,'N',541064,569680,'N','N','N','N','N','N','N','N',0,577200,'de.metas.esb.edi','N','N','M_HU_PackagingCode_TU_ID','N','TU Verpackungscode',0)
;

-- 2019-11-27T16:58:46.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569680 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:58:46.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577200) 
;

-- 2019-11-27T16:59:26.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,ColumnSQL,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,TechnicalNote,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (10,4,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-27 17:59:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-27 17:59:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540676,'N','The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',569681,'N','N','N','N','N','N','(select PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=M_HU_PackagingCode_TU_ID)','N','N',0,577202,'de.metas.esb.edi','N','The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for DESADV-export. The exporter makes sure that we won''t export a stale value here.','N','M_HU_PackagingCode_TU_Text','N','M_HU_PackagingCode_TU_Text',0)
;

-- 2019-11-27T16:59:26.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569681 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-27T16:59:26.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577202) 
;

-- 2019-11-27T16:59:33.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569180
;

-- 2019-11-27T16:59:36.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 17:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569177
;

-- 2019-11-27T16:59:49.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine_SSCC','ALTER TABLE public.EDI_DesadvLine_SSCC ADD COLUMN M_HU_PackagingCode_TU_ID NUMERIC(10)')
;

-- 2019-11-27T16:59:49.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE EDI_DesadvLine_SSCC ADD CONSTRAINT MHUPackagingCodeTU_EDIDesadvLineSSCC FOREIGN KEY (M_HU_PackagingCode_TU_ID) REFERENCES public.M_HU_PackagingCode DEFERRABLE INITIALLY DEFERRED
;

-- 2019-11-27T17:02:29.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-27 18:02:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569178
;

-- 2019-11-28T05:43:10.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-28 06:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1047 AND AD_Language='de_CH'
;

-- 2019-11-28T05:43:10.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1047,'de_CH') 
;

-- 2019-11-28T05:43:16.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-28 06:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1047 AND AD_Language='de_DE'
;

-- 2019-11-28T05:43:16.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1047,'de_DE') 
;

-- 2019-11-28T05:43:16.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1047,'de_DE') 
;

-- 2019-11-28T05:43:27.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Processed', PrintName='Processed',Updated=TO_TIMESTAMP('2019-11-28 06:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1047 AND AD_Language='en_GB'
;

-- 2019-11-28T05:43:27.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1047,'en_GB') 
;

-- 2019-11-28T05:43:38.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',Updated=TO_TIMESTAMP('2019-11-28 06:43:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1047 AND AD_Language='de_CH'
;

-- 2019-11-28T05:43:38.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1047,'de_CH') 
;

-- 2019-11-28T05:43:52.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',Updated=TO_TIMESTAMP('2019-11-28 06:43:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1047 AND AD_Language='de_DE'
;

-- 2019-11-28T05:43:52.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1047,'de_DE') 
;

-- 2019-11-28T05:43:52.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1047,'de_DE') 
;

-- 2019-11-28T05:43:52.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Processed', Name='Verarbeitet', Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.' WHERE AD_Element_ID=1047
;

-- 2019-11-28T05:43:52.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Processed', Name='Verarbeitet', Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.', AD_Element_ID=1047 WHERE UPPER(ColumnName)='PROCESSED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-28T05:43:52.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Processed', Name='Verarbeitet', Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.' WHERE AD_Element_ID=1047 AND IsCentrallyMaintained='Y'
;

-- 2019-11-28T05:43:52.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verarbeitet', Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1047) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1047)
;

-- 2019-11-28T05:43:52.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verarbeitet', Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.', CommitWarning = NULL WHERE AD_Element_ID = 1047
;

-- 2019-11-28T05:43:52.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verarbeitet', Description='Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', Help='Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.' WHERE AD_Element_ID = 1047
;

-- 2019-11-28T05:43:52.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verarbeitet', Description = 'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1047
;

-- 2019-11-28T08:30:32.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-28 09:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='de_CH'
;

-- 2019-11-28T08:30:32.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'de_CH') 
;

-- 2019-11-28T08:30:40.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-28 09:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='de_DE'
;

-- 2019-11-28T08:30:40.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'de_DE') 
;

-- 2019-11-28T08:30:40.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542492,'de_DE') 
;

-- 2019-11-28T08:30:40.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyCU', Name='Menge CU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE AD_Element_ID=542492
;

-- 2019-11-28T08:30:40.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyCU', Name='Menge CU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL, AD_Element_ID=542492 WHERE UPPER(ColumnName)='QTYCU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-28T08:30:40.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyCU', Name='Menge CU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE AD_Element_ID=542492 AND IsCentrallyMaintained='Y'
;

-- 2019-11-28T08:30:40.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge CU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542492) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542492)
;

-- 2019-11-28T08:30:40.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge CU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542492
;

-- 2019-11-28T08:30:40.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge CU', Description='Menge der CUs pro Einzelgebinde (normalerweise TU)', Help=NULL WHERE AD_Element_ID = 542492
;

-- 2019-11-28T08:30:40.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge CU', Description = 'Menge der CUs pro Einzelgebinde (normalerweise TU)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542492
;

-- 2019-11-28T08:30:58.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Number of CUs per package (usually TU).',Updated=TO_TIMESTAMP('2019-11-28 09:30:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='en_US'
;

-- 2019-11-28T08:30:58.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'en_US') 
;

-- 2019-11-28T08:31:04.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Number of CUs per package (usually TU)',Updated=TO_TIMESTAMP('2019-11-28 09:31:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542492 AND AD_Language='en_US'
;

-- 2019-11-28T08:31:04.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542492,'en_US') 
;
