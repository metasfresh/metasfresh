
-- 2017-07-19T16:27:00.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544448,0,TO_TIMESTAMP('2017-07-19 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Es muss eine HU-Zeile ausgewählt sein.','I',TO_TIMESTAMP('2017-07-19 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_AddQtyToHU_SelectHU')
;

-- 2017-07-19T16:27:00.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544448 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-19T16:27:25.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 16:27:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='A HU item shall be selected' WHERE AD_Message_ID=544448 AND AD_Language='en_US'
;

-- 2017-07-19T16:34:59.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544445
;

-- 2017-07-19T16:34:59.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544445
;

-- 2017-07-19T16:43:01.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='WEBUI_Picking_SelectHU',Updated=TO_TIMESTAMP('2017-07-19 16:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544448
;

-- 2017-07-19T16:49:05.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_PickToNewHU', EntityType='de.metas.picking', Name='In neue HU kommissionieren', Value='WEBUI_Picking_PickToNewHU',Updated=TO_TIMESTAMP('2017-07-19 16:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540807
;

-- 2017-07-19T16:49:22.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 16:49:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pick to new HU' WHERE AD_Process_ID=540807 AND AD_Language='en_US'
;

-- 2017-07-19T17:17:25.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_PickToExistingHU', Value='WEBUI_Picking_PickToExistingHU',Updated=TO_TIMESTAMP('2017-07-19 17:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540808
;

-- 2017-07-19T17:17:39.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='In bestehende HU kommissionieren',Updated=TO_TIMESTAMP('2017-07-19 17:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540808
;

-- 2017-07-19T17:17:48.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 17:17:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540808 AND AD_Language='en_US'
;

-- 2017-07-19T17:18:05.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 17:18:05','YYYY-MM-DD HH24:MI:SS'),Name='Pick to existing HU' WHERE AD_Process_ID=540808 AND AD_Language='en_US'
;

-- 2017-07-20T07:00:09.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543383,0,'IsCandidateClosed',TO_TIMESTAMP('2017-07-20 07:00:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Geschlossen','Geschlossen',TO_TIMESTAMP('2017-07-20 07:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-20T07:00:09.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543383 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-07-20T07:00:27.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 07:00:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Closed',PrintName='Closed' WHERE AD_Element_ID=543383 AND AD_Language='en_US'
;

-- 2017-07-20T07:00:27.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543383,'en_US') 
;

-- 2017-07-20T07:00:55.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=543383
;

-- 2017-07-20T07:00:55.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=543383
;

-- 2017-07-20T07:01:28.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='',Updated=TO_TIMESTAMP('2017-07-20 07:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=3020
;

-- 2017-07-20T07:01:28.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Status', Name='Status', Description='', Help='' WHERE AD_Element_ID=3020
;

-- 2017-07-20T07:01:28.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Status', Name='Status', Description='', Help='', AD_Element_ID=3020 WHERE UPPER(ColumnName)='STATUS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-20T07:01:28.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Status', Name='Status', Description='', Help='' WHERE AD_Element_ID=3020 AND IsCentrallyMaintained='Y'
;

-- 2017-07-20T07:01:28.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Status', Description='', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=3020) AND IsCentrallyMaintained='Y'
;

-- 2017-07-20T07:09:30.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540734,TO_TIMESTAMP('2017-07-20 07:09:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','N','M_Picking_Candidate_Status',TO_TIMESTAMP('2017-07-20 07:09:30','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2017-07-20T07:09:30.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540734 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-07-20T07:23:17.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541287,540734,TO_TIMESTAMP('2017-07-20 07:23:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Geschlossen',TO_TIMESTAMP('2017-07-20 07:23:17','YYYY-MM-DD HH24:MI:SS'),100,'CL','CL')
;

-- 2017-07-20T07:23:17.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541287 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2017-07-20T07:23:52.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541288,540734,TO_TIMESTAMP('2017-07-20 07:23:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','In Bearbeitung',TO_TIMESTAMP('2017-07-20 07:23:52','YYYY-MM-DD HH24:MI:SS'),100,'IP','IP')
;

-- 2017-07-20T07:23:52.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541288 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2017-07-20T07:23:56.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='CL',Updated=TO_TIMESTAMP('2017-07-20 07:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541287
;

-- 2017-07-20T07:24:27.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541289,540734,TO_TIMESTAMP('2017-07-20 07:24:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Verarbeitet',TO_TIMESTAMP('2017-07-20 07:24:27','YYYY-MM-DD HH24:MI:SS'),100,'PR','PR')
;

-- 2017-07-20T07:24:27.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541289 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2017-07-20T07:24:36.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 07:24:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Processed' WHERE AD_Ref_List_ID=541289 AND AD_Language='en_US'
;

-- 2017-07-20T07:24:50.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 07:24:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='In progress' WHERE AD_Ref_List_ID=541288 AND AD_Language='en_US'
;

-- 2017-07-20T07:25:03.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-20 07:25:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Closed' WHERE AD_Ref_List_ID=541287 AND AD_Language='en_US'
;

-- 2017-07-20T07:25:46.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557018,3020,0,17,540734,540831,'N','Status',TO_TIMESTAMP('2017-07-20 07:25:46','YYYY-MM-DD HH24:MI:SS'),100,'N','IP','','de.metas.picking',1,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Status',0,TO_TIMESTAMP('2017-07-20 07:25:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-20T07:25:46.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557018 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-20T07:29:05.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2,Updated=TO_TIMESTAMP('2017-07-20 07:29:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557018
;

-- 2017-07-20T07:29:07.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('m_picking_candidate','ALTER TABLE public.M_Picking_Candidate ADD COLUMN Status VARCHAR(2) DEFAULT ''IP'' NOT NULL')
;

-- 2017-07-20T07:30:23.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=541287
;

-- 2017-07-20T07:30:23.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=541287
;

-- 2017-07-20T07:35:01.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=557013
;

-- 2017-07-20T07:35:01.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=557013
;

-- 2017-07-20T07:38:00.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2017-07-20 07:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556981
;

