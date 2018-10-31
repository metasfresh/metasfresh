-- 2018-08-29T16:26:10.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544225,0,'IncludeInAssignedQuantitySum',TO_TIMESTAMP('2018-08-29 16:26:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Zugeordnete Menge in Summe einbez.','Zugeordnete Menge in Summe einbez.',TO_TIMESTAMP('2018-08-29 16:26:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-29T16:26:10.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544225 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-29T16:26:14.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y' WHERE AD_Element_ID=544225 AND AD_Language='de_CH'
;

-- 2018-08-29T16:26:14.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544225,'de_CH') 
;

-- 2018-08-29T16:26:26.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Zugeordnete Menge wird in Summe einbez.', PrintName='Zugeordnete Menge wird in Summe einbez.',Updated=TO_TIMESTAMP('2018-08-29 16:26:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:26:26.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IncludeInAssignedQuantitySum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:26:26.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IncludeInAssignedQuantitySum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL, AD_Element_ID=544225 WHERE UPPER(ColumnName)='INCLUDEINASSIGNEDQUANTITYSUM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-29T16:26:26.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IncludeInAssignedQuantitySum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225 AND IsCentrallyMaintained='Y'
;

-- 2018-08-29T16:26:26.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544225) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544225)
;

-- 2018-08-29T16:26:26.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordnete Menge wird in Summe einbez.', Name='Zugeordnete Menge wird in Summe einbez.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544225)
;

-- 2018-08-29T16:26:43.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AssignedQuantityIncludeInSum',Updated=TO_TIMESTAMP('2018-08-29 16:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:26:43.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AssignedQuantityIncludeInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:26:43.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedQuantityIncludeInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL, AD_Element_ID=544225 WHERE UPPER(ColumnName)='ASSIGNEDQUANTITYINCLUDEINSUM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-29T16:26:43.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedQuantityIncludeInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225 AND IsCentrallyMaintained='Y'
;

-- 2018-08-29T16:26:51.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='AssignedQuantityIncludeInSum' WHERE AD_Element_ID=544225 AND AD_Language='de_CH'
;

-- 2018-08-29T16:26:51.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544225,'de_CH') 
;

-- 2018-08-29T16:26:55.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugeordnete Menge wird in Summe einbez.',PrintName='Zugeordnete Menge wird in Summe einbez.' WHERE AD_Element_ID=544225 AND AD_Language='de_CH'
;

-- 2018-08-29T16:26:55.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544225,'de_CH') 
;

-- 2018-08-29T16:27:13.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Zugeordnete Menge',Updated=TO_TIMESTAMP('2018-08-29 16:27:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560767
;

-- 2018-08-29T16:27:13.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnete Menge', Description=NULL, Help=NULL WHERE AD_Column_ID=560767
;

-- 2018-08-29T16:27:58.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Assigned quantity is part of sum.',PrintName='Assigned quantity is part of sum.',Description='' WHERE AD_Element_ID=544225 AND AD_Language='en_US'
;

-- 2018-08-29T16:27:58.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544225,'en_US') 
;

-- 2018-08-29T16:28:35.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.', Name='Zugeordneter Geldbetrag', PrintName='Zugeordneter Geldbetrag',Updated=TO_TIMESTAMP('2018-08-29 16:28:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544095
;

-- 2018-08-29T16:28:35.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AssignedMoneyAmount', Name='Zugeordneter Geldbetrag', Description='Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE AD_Element_ID=544095
;

-- 2018-08-29T16:28:35.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedMoneyAmount', Name='Zugeordneter Geldbetrag', Description='Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL, AD_Element_ID=544095 WHERE UPPER(ColumnName)='ASSIGNEDMONEYAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-29T16:28:35.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AssignedMoneyAmount', Name='Zugeordneter Geldbetrag', Description='Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE AD_Element_ID=544095 AND IsCentrallyMaintained='Y'
;

-- 2018-08-29T16:28:35.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordneter Geldbetrag', Description='Zugeordneter Geldbetrag, in der Währung des Vertrags-Rechnungskandidaten.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544095) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544095)
;

-- 2018-08-29T16:28:35.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugeordneter Geldbetrag', Name='Zugeordneter Geldbetrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544095)
;

-- 2018-08-29T16:29:19.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Basisbetrag',Updated=TO_TIMESTAMP('2018-08-29 16:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560767
;

-- 2018-08-29T16:29:19.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Basisbetrag', Description=NULL, Help=NULL WHERE AD_Column_ID=560767
;

-- 2018-08-29T16:29:32.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=37,Updated=TO_TIMESTAMP('2018-08-29 16:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560767
;

-- 2018-08-29T16:29:40.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=37,Updated=TO_TIMESTAMP('2018-08-29 16:29:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560197
;

-- 2018-08-29T16:30:05.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsAssignedQuantityIncludeInSum',Updated=TO_TIMESTAMP('2018-08-29 16:30:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:30:05.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAssignedQuantityIncludeInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225
;

-- 2018-08-29T16:30:05.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAssignedQuantityIncludeInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL, AD_Element_ID=544225 WHERE UPPER(ColumnName)='ISASSIGNEDQUANTITYINCLUDEINSUM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-29T16:30:05.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAssignedQuantityIncludeInSum', Name='Zugeordnete Menge wird in Summe einbez.', Description=NULL, Help=NULL WHERE AD_Element_ID=544225 AND IsCentrallyMaintained='Y'
;

