
-- 2018-05-11T06:54:38.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540861,TO_TIMESTAMP('2018-05-11 06:54:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Yes_No_Unknown',TO_TIMESTAMP('2018-05-11 06:54:38','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-05-11T06:54:38.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540861 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-05-11T06:55:14.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540861,541630,TO_TIMESTAMP('2018-05-11 06:55:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nein',TO_TIMESTAMP('2018-05-11 06:55:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

-- 2018-05-11T06:55:14.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541630 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-11T06:55:45.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540861,541631,TO_TIMESTAMP('2018-05-11 06:55:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ja',TO_TIMESTAMP('2018-05-11 06:55:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','YES')
;

-- 2018-05-11T06:55:45.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541631 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-11T06:55:51.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=541630
;

-- 2018-05-11T06:55:51.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=541630
;

-- 2018-05-11T06:56:14.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540861,541632,TO_TIMESTAMP('2018-05-11 06:56:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nein',TO_TIMESTAMP('2018-05-11 06:56:14','YYYY-MM-DD HH24:MI:SS'),100,'N','NO')
;

-- 2018-05-11T06:56:14.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541632 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-11T06:56:25.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:56:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541632 AND AD_Language='de_CH'
;

-- 2018-05-11T06:56:31.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:56:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='No' WHERE AD_Ref_List_ID=541632 AND AD_Language='en_US'
;

-- 2018-05-11T06:56:40.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:56:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Yes' WHERE AD_Ref_List_ID=541631 AND AD_Language='en_US'
;

-- 2018-05-11T06:56:46.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:56:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541631 AND AD_Language='de_CH'
;

-- 2018-05-11T06:57:38.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540861,541633,TO_TIMESTAMP('2018-05-11 06:57:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','Unbekannt',TO_TIMESTAMP('2018-05-11 06:57:38','YYYY-MM-DD HH24:MI:SS'),100,'Unknown','UNKNOWN')
;

-- 2018-05-11T06:57:38.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541633 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-05-11T06:57:45.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:57:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541633 AND AD_Language='de_CH'
;

-- 2018-05-11T06:57:58.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:57:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Unknown' WHERE AD_Ref_List_ID=541633 AND AD_Language='en_US'
;

-- 2018-05-11T06:58:18.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:58:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Reference_ID=540861 AND AD_Language='de_CH'
;

-- 2018-05-11T06:58:32.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 06:58:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='N' WHERE AD_Reference_ID=540861 AND AD_Language='de_CH'
;


---- add last-sync-stuff


-- 2018-05-11T07:10:50.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544067,0,'LastSyncedRemoteToLocal',TO_TIMESTAMP('2018-05-11 07:10:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Letzte Synchronisation Platform nach metasfresh','Letzte Synchronisation Platform nach metasfresh',TO_TIMESTAMP('2018-05-11 07:10:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-11T07:10:50.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544067 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-11T07:10:57.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Letzte Synchronisation nach metasfresh', PrintName='Letzte Synchronisation nach metasfresh',Updated=TO_TIMESTAMP('2018-05-11 07:10:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544067
;

-- 2018-05-11T07:10:57.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSyncedRemoteToLocal', Name='Letzte Synchronisation nach metasfresh', Description=NULL, Help=NULL WHERE AD_Element_ID=544067
;

-- 2018-05-11T07:10:57.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncedRemoteToLocal', Name='Letzte Synchronisation nach metasfresh', Description=NULL, Help=NULL, AD_Element_ID=544067 WHERE UPPER(ColumnName)='LASTSYNCEDREMOTETOLOCAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-11T07:10:57.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncedRemoteToLocal', Name='Letzte Synchronisation nach metasfresh', Description=NULL, Help=NULL WHERE AD_Element_ID=544067 AND IsCentrallyMaintained='Y'
;

-- 2018-05-11T07:10:57.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzte Synchronisation nach metasfresh', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544067) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544067)
;

-- 2018-05-11T07:10:57.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzte Synchronisation nach metasfresh', Name='Letzte Synchronisation nach metasfresh' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544067)
;

-- 2018-05-11T07:11:10.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:11:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Letzte Synchronisation nach metasfresh',PrintName='Letzte Synchronisation nach metasfresh' WHERE AD_Element_ID=544067 AND AD_Language='de_CH'
;

-- 2018-05-11T07:11:10.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544067,'de_CH') 
;

-- 2018-05-11T07:11:27.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Letzte Synchronisation auf metasfresh', PrintName='Letzte Synchronisation auf metasfresh',Updated=TO_TIMESTAMP('2018-05-11 07:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544067
;

-- 2018-05-11T07:11:27.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSyncedRemoteToLocal', Name='Letzte Synchronisation auf metasfresh', Description=NULL, Help=NULL WHERE AD_Element_ID=544067
;

-- 2018-05-11T07:11:27.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncedRemoteToLocal', Name='Letzte Synchronisation auf metasfresh', Description=NULL, Help=NULL, AD_Element_ID=544067 WHERE UPPER(ColumnName)='LASTSYNCEDREMOTETOLOCAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-11T07:11:27.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncedRemoteToLocal', Name='Letzte Synchronisation auf metasfresh', Description=NULL, Help=NULL WHERE AD_Element_ID=544067 AND IsCentrallyMaintained='Y'
;

-- 2018-05-11T07:11:27.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzte Synchronisation auf metasfresh', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544067) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544067)
;

-- 2018-05-11T07:11:27.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzte Synchronisation auf metasfresh', Name='Letzte Synchronisation auf metasfresh' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544067)
;

-- 2018-05-11T07:11:36.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:11:36','YYYY-MM-DD HH24:MI:SS'),Name='Letzte Synchronisation auf metasfresh',PrintName='Letzte Synchronisation auf metasfresh' WHERE AD_Element_ID=544067 AND AD_Language='de_CH'
;

-- 2018-05-11T07:11:36.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544067,'de_CH') 
;

-- 2018-05-11T07:11:47.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:11:47','YYYY-MM-DD HH24:MI:SS'),Name='Letzte Synchronisation auf metasfresh',PrintName='Letzte Synchronisation auf metasfresh' WHERE AD_Element_ID=544067 AND AD_Language='nl_NL'
;

-- 2018-05-11T07:11:47.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544067,'nl_NL') 
;

-- 2018-05-11T07:12:06.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:12:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Last synchronisation to metasfresh',PrintName='Last synchronisation to metasfresh' WHERE AD_Element_ID=544067 AND AD_Language='en_US'
;

-- 2018-05-11T07:12:06.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544067,'en_US') 
;

-- 2018-05-11T07:12:29.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544068,0,'LastSyncLocalToRemote',TO_TIMESTAMP('2018-05-11 07:12:29','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Letzte Synchronisation auf metasfresh','Letzte Synchronisation auf metasfresh',TO_TIMESTAMP('2018-05-11 07:12:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-11T07:12:29.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544068 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-11T07:13:02.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='LastSyncOfLocalToRemote', EntityType='D', Name='Letzte Synchronisation auf die Platform', PrintName='Letzte Synchronisation auf die Platform',Updated=TO_TIMESTAMP('2018-05-11 07:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544068
;

-- 2018-05-11T07:13:02.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSyncOfLocalToRemote', Name='Letzte Synchronisation auf die Platform', Description=NULL, Help=NULL WHERE AD_Element_ID=544068
;

-- 2018-05-11T07:13:02.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncOfLocalToRemote', Name='Letzte Synchronisation auf die Platform', Description=NULL, Help=NULL, AD_Element_ID=544068 WHERE UPPER(ColumnName)='LASTSYNCOFLOCALTOREMOTE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-11T07:13:02.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncOfLocalToRemote', Name='Letzte Synchronisation auf die Platform', Description=NULL, Help=NULL WHERE AD_Element_ID=544068 AND IsCentrallyMaintained='Y'
;

-- 2018-05-11T07:13:02.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzte Synchronisation auf die Platform', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544068) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544068)
;

-- 2018-05-11T07:13:02.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzte Synchronisation auf die Platform', Name='Letzte Synchronisation auf die Platform' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544068)
;

-- 2018-05-11T07:13:09.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='LastSyncOfRemoteToLocal',Updated=TO_TIMESTAMP('2018-05-11 07:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544067
;

-- 2018-05-11T07:13:09.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSyncOfRemoteToLocal', Name='Letzte Synchronisation auf metasfresh', Description=NULL, Help=NULL WHERE AD_Element_ID=544067
;

-- 2018-05-11T07:13:09.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncOfRemoteToLocal', Name='Letzte Synchronisation auf metasfresh', Description=NULL, Help=NULL, AD_Element_ID=544067 WHERE UPPER(ColumnName)='LASTSYNCOFREMOTETOLOCAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-11T07:13:09.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncOfRemoteToLocal', Name='Letzte Synchronisation auf metasfresh', Description=NULL, Help=NULL WHERE AD_Element_ID=544067 AND IsCentrallyMaintained='Y'
;

-- 2018-05-11T07:13:26.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:13:26','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=544068 AND AD_Language='en_US'
;

-- 2018-05-11T07:13:26.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544068,'en_US') 
;

-- 2018-05-11T07:13:40.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:13:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Letzte Synchronisation auf die Platform',PrintName='Letzte Synchronisation auf die Platform' WHERE AD_Element_ID=544068 AND AD_Language='de_CH'
;

-- 2018-05-11T07:13:40.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544068,'de_CH') 
;

-- 2018-05-11T07:13:48.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:13:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544068 AND AD_Language='en_US'
;

-- 2018-05-11T07:13:48.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544068,'en_US') 
;

-- 2018-05-11T07:14:12.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:14:12','YYYY-MM-DD HH24:MI:SS'),Name='Last synchronisation to the platform',PrintName='Last synchronisation to the platform' WHERE AD_Element_ID=544068 AND AD_Language='en_US'
;

-- 2018-05-11T07:14:12.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544068,'en_US') 
;

-- 2018-05-11T07:15:38.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='LastSyncStatus', Name='Letzter Synchronisierungsstatus', PrintName='Letzter Synchronisierungsstatus',Updated=TO_TIMESTAMP('2018-05-11 07:15:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544068
;

-- 2018-05-11T07:15:38.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastSyncStatus', Name='Letzter Synchronisierungsstatus', Description=NULL, Help=NULL WHERE AD_Element_ID=544068
;

-- 2018-05-11T07:15:38.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncStatus', Name='Letzter Synchronisierungsstatus', Description=NULL, Help=NULL, AD_Element_ID=544068 WHERE UPPER(ColumnName)='LASTSYNCSTATUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-05-11T07:15:38.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastSyncStatus', Name='Letzter Synchronisierungsstatus', Description=NULL, Help=NULL WHERE AD_Element_ID=544068 AND IsCentrallyMaintained='Y'
;

-- 2018-05-11T07:15:38.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzter Synchronisierungsstatus', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544068) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544068)
;

-- 2018-05-11T07:15:38.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzter Synchronisierungsstatus', Name='Letzter Synchronisierungsstatus' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544068)
;

-- 2018-05-11T07:17:03.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544069,0,'LastSyncDetailMessage',TO_TIMESTAMP('2018-05-11 07:17:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Synchronisierungstatus-Detail','Synchronisierungstatus-Detail',TO_TIMESTAMP('2018-05-11 07:17:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-11T07:17:03.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544069 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-11T07:17:17.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:17:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544069 AND AD_Language='de_CH'
;

-- 2018-05-11T07:17:17.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544069,'de_CH') 
;

-- 2018-05-11T07:17:38.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:17:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Sync detail message',PrintName='Sync detail message' WHERE AD_Element_ID=544069 AND AD_Language='en_US'
;

-- 2018-05-11T07:17:38.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544069,'en_US') 
;

-- 2018-05-11T07:17:59.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:17:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='N' WHERE AD_Element_ID=544068 AND AD_Language='en_US'
;

-- 2018-05-11T07:17:59.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544068,'en_US') 
;

-- 2018-05-11T07:18:03.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:18:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='N' WHERE AD_Element_ID=544068 AND AD_Language='de_CH'
;

-- 2018-05-11T07:18:03.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544068,'de_CH') 
;

-- 2018-05-11T07:19:15.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544070,0,'LastSyncOfLocalToRemote',TO_TIMESTAMP('2018-05-11 07:19:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Letzte Synchronisation auf die Platform','Letzte Synchronisation auf die Platform',TO_TIMESTAMP('2018-05-11 07:19:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-11T07:19:15.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544070 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-11T07:19:21.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:19:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544070 AND AD_Language='de_CH'
;

-- 2018-05-11T07:19:21.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544070,'de_CH') 
;

-- 2018-05-11T07:19:48.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-11 07:19:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Last synchronisation to the platform',PrintName='Last synchronisation to the platform' WHERE AD_Element_ID=544070 AND AD_Language='en_US'
;

-- 2018-05-11T07:19:48.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544070,'en_US') 
;



