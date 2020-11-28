
-- 14.01.2016 07:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Help='Hinweis: im Fenster "Rechnungskandidaten - Handler" gibt es einen Prozess, mit dem man nur den jeweils ausgewählten Handler aufrufen kann.', Name='Rechnungsdisposition erstellen (alle Handler)',Updated=TO_TIMESTAMP('2016-01-14 07:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540535
;

-- 14.01.2016 07:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540535
;

-- 14.01.2016 07:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='ACHTUNG: Prozess läuft normalerweise automatisch im Hintergrund. Manueller Start nur durch unterwiesene Benutzer.', IsActive='Y', Name='Rechnungsdisposition erstellen (alle Handler)',Updated=TO_TIMESTAMP('2016-01-14 07:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540586
;

-- 14.01.2016 07:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540586
;

-- 14.01.2016 07:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540637,'de.metas.invoicecandidate.process.C_Invoice_Candidate_Create_Missing_Single_Handler','N',TO_TIMESTAMP('2016-01-14 07:15:32','YYYY-MM-DD HH24:MI:SS'),100,'ACHTUNG: Prozess läuft normalerweise automatisch im Hintergrund. Manueller Start nur durch unterwiesene Benutzer.','de.metas.invoicecandidate','Hinweis: im Fenster "Rechnungskandidaten - Handler" gibt es einen Prozess, mit dem man nur den jeweils ausgewählten Handler aufrufen kann.','Y','N','N','N','N','N',0,'Rechnungsdisposition erstellen (ausgewählter Handler)','N','Y',0,0,'Java',TO_TIMESTAMP('2016-01-14 07:15:32','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Candidate_Create_Missing_Single_Handler')
;

-- 14.01.2016 07:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540637 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 14.01.2016 07:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540637,540340,TO_TIMESTAMP('2016-01-14 07:15:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y',TO_TIMESTAMP('2016-01-14 07:15:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.01.2016 07:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='ACHTUNG: Manueller Start nur durch unterwiesene Benutzer.',Updated=TO_TIMESTAMP('2016-01-14 07:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540535
;

-- 14.01.2016 07:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540535
;

-- 14.01.2016 07:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='ACHTUNG: Manueller Start nur durch unterwiesene Benutzer.', IsActive='Y', Name='Rechnungsdisposition erstellen (alle Handler)',Updated=TO_TIMESTAMP('2016-01-14 07:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540586
;

-- 14.01.2016 07:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540586
;

-- 14.01.2016 07:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='ACHTUNG: Manueller Start nur durch unterwiesene Benutzer.',Updated=TO_TIMESTAMP('2016-01-14 07:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540637
;

-- 14.01.2016 07:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540637
;

-- 14.01.2016 07:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Rechnungsdisposition erstellen (alle aktiven Handler)',Updated=TO_TIMESTAMP('2016-01-14 07:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540535
;

-- 14.01.2016 07:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540535
;

-- 14.01.2016 07:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='ACHTUNG: Manueller Start nur durch unterwiesene Benutzer.', IsActive='Y', Name='Rechnungsdisposition erstellen (alle aktiven Handler)',Updated=TO_TIMESTAMP('2016-01-14 07:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540586
;

-- 14.01.2016 07:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540586
;

