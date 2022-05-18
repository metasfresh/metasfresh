INSERT INTO m_hu (ad_client_id, ad_org_id, created, createdby, isactive, m_hu_id,m_hu_pi_version_id, value, updated, updatedby, hustatus)
VALUES (1000000,1000000,date('2022-05-05'),100,'Y',100,101,100.,date('2022-05-05'),100,'A');

CREATE OR REPLACE FUNCTION delete_hu_entries_older_than_given_months(
    p_past_months NUMERIC,
    p_m_hu_placeholder_id NUMERIC
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN

    CREATE TEMP TABLE temp_m_hu AS
    SELECT m_hu_id FROM m_hu WHERE hustatus != 'A' AND updated <= now() - (p_past_months || ' month')::INTERVAL;
    CREATE TEMP TABLE temp_m_hu_item AS
    SELECT m_hu_item_id FROM m_hu_item WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    CREATE TEMP TABLE temp_m_inventoryline AS
    SELECT m_inventoryline_id FROM m_inventoryline WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    CREATE TEMP TABLE temp_m_hu_trx_line AS
    SELECT m_hu_trx_line_id FROM m_hu_trx_line WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    --We delete from or update these tables first, because of the fk constrains of other tables
    DELETE FROM m_hu_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_attribute_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_storage_snapshot WHERE m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);
    DELETE FROM m_hu_storage_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_picking_candidate WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_pickingslot_trx WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_trx_attribute WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_assignment WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_trace WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_source_hu WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    UPDATE m_receiptschedule_alloc SET m_lu_hu_id = p_m_hu_placeholder_id, m_tu_hu_id = p_m_hu_placeholder_id WHERE m_lu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_tu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);
    UPDATE m_shipmentschedule_qtypicked SET m_lu_hu_id = p_m_hu_placeholder_id, m_tu_hu_id = p_m_hu_placeholder_id WHERE m_lu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_tu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR vhu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_package_hu SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_inventoryline_hu SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE pp_order_productattribute SET m_hu_id = p_m_hu_placeholder_id  WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE pp_order_qty SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);


    --Tables below here have fk constrains
    DELETE FROM m_hu_attribute WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_storage WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_storage WHERE m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);

    --The parents id must be set to null, or else we can't delete the entries
    UPDATE m_hu_trx_line SET parent_hu_trx_line_id = NULL WHERE parent_hu_trx_line_id IN (SELECT m_hu_trx_line_id FROM temp_m_hu_trx_line);
    DELETE FROM m_hu_trx_line WHERE m_hu_trx_line_id IN (SELECT m_hu_trx_line_id FROM temp_m_hu_trx_line);


    UPDATE m_inventoryline SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    DROP TABLE temp_m_hu;
    DROP TABLE temp_m_hu_item;
    DROP TABLE temp_m_inventoryline;
    DROP TABLE temp_m_hu_trx_line;

END
$$;

COMMENT ON FUNCTION delete_hu_entries_older_than_given_months(p_past_months NUMERIC, p_m_hu_placeholder_id NUMERIC) IS
    'Deletes all handling unit entries that have an inactive status and are older than the specified months. The placeholder is used in other tables linking to the removed handling unit.';


-- 2022-05-05T07:17:33.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585049,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2022-05-05 09:17:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'remove_hu_entries_between_dates','json','N','N','xls','SELECT remove_hu_entries_between_dates(%months%, %placeholder%);','SQL',TO_TIMESTAMP('2022-05-05 09:17:32','YYYY-MM-DD HH24:MI:SS'),100,'remove_hu_entries_between_dates')
;

-- 2022-05-05T07:17:33.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585049 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-05-05T07:20:06.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1113,0,585049,542256,22,'NoMonths',TO_TIMESTAMP('2022-05-05 09:20:06','YYYY-MM-DD HH24:MI:SS'),100,'D',2,'Y','N','Y','N','Y','N','Number of Months',10,TO_TIMESTAMP('2022-05-05 09:20:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T07:20:06.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542256 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-05-05T07:21:58.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542140,0,585049,542257,22,'M_HU_ID',TO_TIMESTAMP('2022-05-05 09:21:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Handling Unit',20,TO_TIMESTAMP('2022-05-05 09:21:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T07:21:58.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542257 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-05-05T07:38:13.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,585049,0,550087,TO_TIMESTAMP('2022-05-05 09:38:13','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'D','Y','N',7,'N','remove_hu_entries_between_dates','N','P','F','NEW',100,TO_TIMESTAMP('2022-05-05 09:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T07:38:33.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (0,0,542257,550087,540038,TO_TIMESTAMP('2022-05-05 09:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','100',TO_TIMESTAMP('2022-05-05 09:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T07:38:56.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (0,0,542256,550087,540039,TO_TIMESTAMP('2022-05-05 09:38:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','24',TO_TIMESTAMP('2022-05-05 09:38:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-05T08:07:28.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Removes all handling unit entries that have an inactive status and are older than the specified months. The placeholder is used in other tables linking to the removed handling unit.',Updated=TO_TIMESTAMP('2022-05-05 10:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585049
;

-- 2022-05-05T08:07:49.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='100', IsMandatory='Y',Updated=TO_TIMESTAMP('2022-05-05 10:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542257
;


-- 2022-05-05T12:12:35.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern=NULL, Description='Removes old, inactive handling units', Name='Remove old Handling Units',Updated=TO_TIMESTAMP('2022-05-05 14:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550087
;

-- 2022-05-05T12:12:48.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2022-05-05 14:12:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550087
;

-- 2022-05-17T05:35:09.566Z
UPDATE AD_Process SET Description='Deletes from the DB all handling unit entries that have an inactive status and are older than the specified months. The placeholder is used in other tables linking to the removed handling unit.', Name='delete_hu_entries_older_than_given_months', SQLStatement='SELECT delete_hu_entries_older_than_given_months(%months%, %placeholder%);', Value='delete_hu_entries_older_than_given_months',Updated=TO_TIMESTAMP('2022-05-17 07:35:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585049
;

-- 2022-05-17T05:37:06.502Z
UPDATE AD_Process SET SQLStatement='SELECT delete_hu_entries_older_than_given_months(%NoMonths%, %M_HU_ID%);',Updated=TO_TIMESTAMP('2022-05-17 07:37:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585049
;

-- 2022-05-17T05:37:56.132Z
UPDATE AD_Process SET Name='Delete old HU Entries from DB',Updated=TO_TIMESTAMP('2022-05-17 07:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585049
;

-- 2022-05-17T05:42:56.586Z
UPDATE AD_Process SET Description='Löscht Handling Unit Einträge aus der Datenbank die einen inaktiven Status haben und älter als die angebenen Monate sind. Ein HU-Platzhalter wird in den Tabellen eingetragen, die auf die gelöschten Einträge verweisen. ', Help=NULL, Name='Löscht alte HU Einträge aus der Datenbank',Updated=TO_TIMESTAMP('2022-05-17 07:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585049
;

-- 2022-05-17T05:42:56.579Z
UPDATE AD_Process_Trl SET Description='Löscht Handling Unit Einträge aus der Datenbank die einen inaktiven Status haben und älter als die angebenen Monate sind. Ein HU-Platzhalter wird in den Tabellen eingetragen, die auf die gelöschten Einträge verweisen. ', Name='Löscht alte HU Einträge aus der Datenbank',Updated=TO_TIMESTAMP('2022-05-17 07:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585049
;

-- 2022-05-17T05:44:47.962Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-17 07:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585049
;

-- 2022-05-17T05:47:04.166Z
UPDATE AD_Process_Trl SET Description='Deletes from the DB all handling unit entries that have an inactive status and are older than the specified months. The placeholder is used in other tables linking to the removed handling unit.', IsTranslated='Y', Name='Delete old HU entries from the DB',Updated=TO_TIMESTAMP('2022-05-17 07:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585049
;

-- 2022-05-17T05:47:42.220Z
UPDATE AD_Process SET Description='Löscht Handling Unit Einträge aus der Datenbank, die einen inaktiven Status haben und älter als die angebenen Monate sind. Ein HU-Platzhalter wird in den Tabellen eingetragen, die auf die gelöschten Einträge verweisen. ', Help=NULL, Name='Lösche alte HU Einträge aus der Datenbank',Updated=TO_TIMESTAMP('2022-05-17 07:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585049
;

-- 2022-05-17T05:47:42.214Z
UPDATE AD_Process_Trl SET Description='Löscht Handling Unit Einträge aus der Datenbank, die einen inaktiven Status haben und älter als die angebenen Monate sind. Ein HU-Platzhalter wird in den Tabellen eingetragen, die auf die gelöschten Einträge verweisen. ', Name='Lösche alte HU Einträge aus der Datenbank',Updated=TO_TIMESTAMP('2022-05-17 07:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585049
;

-- 2022-05-17T05:51:25.852Z
UPDATE AD_Scheduler SET CronPattern='0 3 * * *', Description='Removes old, inactive handling units that are older than 24 months', ScheduleType='C',Updated=TO_TIMESTAMP('2022-05-17 07:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550087
;

-- 2022-05-17T20:26:36.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580853,0,'PastMonths',TO_TIMESTAMP('2022-05-17 22:26:35','YYYY-MM-DD HH24:MI:SS'),100,'Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ','D','Y','PastMonths','Verangenen Monate',TO_TIMESTAMP('2022-05-17 22:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-17T20:26:36.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580853 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-17T20:26:52.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-17 22:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='de_DE'
;

-- 2022-05-17T20:26:52.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'de_DE')
;

-- 2022-05-17T20:26:53.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580853,'de_DE')
;

-- 2022-05-17T20:27:54.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The Age of HU Entries, in Months, after which ', PrintName='Past Months',Updated=TO_TIMESTAMP('2022-05-17 22:27:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='en_US'
;

-- 2022-05-17T20:27:54.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'en_US')
;

-- 2022-05-17T20:29:06.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The age of HU entries  in months after being deleted. ',Updated=TO_TIMESTAMP('2022-05-17 22:29:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='en_US'
;

-- 2022-05-17T20:29:06.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'en_US')
;

-- 2022-05-17T20:29:30.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-17 22:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='en_US'
;

-- 2022-05-17T20:29:30.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'en_US')
;

-- 2022-05-17T20:30:16.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=580853, ColumnName='PastMonths', Description='Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', Name='PastMonths',Updated=TO_TIMESTAMP('2022-05-17 22:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542256
;

-- 2022-05-17T20:31:36.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580854,0,TO_TIMESTAMP('2022-05-17 22:31:36','YYYY-MM-DD HH24:MI:SS'),100,'Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ','D','Y','Placeholder_HU_ID','Platzhalter HU',TO_TIMESTAMP('2022-05-17 22:31:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-17T20:31:36.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580854 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-17T20:32:05.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The HU ID that is used as placeholder for deleted entries. Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', IsTranslated='Y', PrintName='Placeholder HU',Updated=TO_TIMESTAMP('2022-05-17 22:32:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580854 AND AD_Language='en_US'
;

-- 2022-05-17T20:32:05.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580854,'en_US')
;

-- 2022-05-17T20:32:15.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The HU ID that is used as placeholder for deleted entries.',Updated=TO_TIMESTAMP('2022-05-17 22:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580854 AND AD_Language='en_US'
;

-- 2022-05-17T20:32:15.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580854,'en_US')
;

-- 2022-05-17T20:32:24.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-17 22:32:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580854 AND AD_Language='de_DE'
;

-- 2022-05-17T20:32:24.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580854,'de_DE')
;

-- 2022-05-17T20:32:24.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580854,'de_DE')
;

-- 2022-05-17T20:33:09.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Placeholder_HU_ID',Updated=TO_TIMESTAMP('2022-05-17 22:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580854
;

-- 2022-05-17T20:33:09.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Placeholder_HU_ID', Name='Placeholder_HU_ID', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL WHERE AD_Element_ID=580854
;

-- 2022-05-17T20:33:09.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Placeholder_HU_ID', Name='Placeholder_HU_ID', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL, AD_Element_ID=580854 WHERE UPPER(ColumnName)='PLACEHOLDER_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-17T20:33:09.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Placeholder_HU_ID', Name='Placeholder_HU_ID', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL WHERE AD_Element_ID=580854 AND IsCentrallyMaintained='Y'
;

-- 2022-05-17T20:33:32.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=580854, ColumnName='Placeholder_HU_ID', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Name='Placeholder_HU_ID',Updated=TO_TIMESTAMP('2022-05-17 22:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542257
;

-- 2022-05-17T20:34:02.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-17 22:34:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542257
;

-- 2022-05-17T20:34:41.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT delete_hu_entries_older_than_given_months(%PastMonths%, %Placeholder_HU_ID%);',Updated=TO_TIMESTAMP('2022-05-17 22:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585049
;

-- 2022-05-18T10:53:05.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verangenen Monate',Updated=TO_TIMESTAMP('2022-05-18 12:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='de_DE'
;

-- 2022-05-18T10:53:05.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'de_DE')
;

-- 2022-05-18T10:53:05.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580853,'de_DE')
;

-- 2022-05-18T10:53:05.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PastMonths', Name='Verangenen Monate', Description='Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', Help=NULL WHERE AD_Element_ID=580853
;

-- 2022-05-18T10:53:05.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PastMonths', Name='Verangenen Monate', Description='Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', Help=NULL, AD_Element_ID=580853 WHERE UPPER(ColumnName)='PASTMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-18T10:53:05.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PastMonths', Name='Verangenen Monate', Description='Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', Help=NULL WHERE AD_Element_ID=580853 AND IsCentrallyMaintained='Y'
;

-- 2022-05-18T10:53:05.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verangenen Monate', Description='Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580853) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580853)
;

-- 2022-05-18T10:53:05.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verangenen Monate', Name='Verangenen Monate' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580853)
;

-- 2022-05-18T10:53:05.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verangenen Monate', Description='Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580853
;

-- 2022-05-18T10:53:05.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verangenen Monate', Description='Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', Help=NULL WHERE AD_Element_ID = 580853
;

-- 2022-05-18T10:53:05.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verangenen Monate', Description = 'Das Alter der HU Einträge in Monaten nachdem gelöscht wird. ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580853
;

-- 2022-05-18T10:53:20.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Verangenen Monate',Updated=TO_TIMESTAMP('2022-05-18 12:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='de_CH'
;

-- 2022-05-18T10:53:20.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'de_CH')
;

-- 2022-05-18T10:53:29.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-18 12:53:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='de_CH'
;

-- 2022-05-18T10:53:29.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'de_CH')
;

-- 2022-05-18T10:53:50.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Past Months ',Updated=TO_TIMESTAMP('2022-05-18 12:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580853 AND AD_Language='en_US'
;

-- 2022-05-18T10:53:50.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580853,'en_US')
;

-- 2022-05-18T11:12:45.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Platzhalter HU',Updated=TO_TIMESTAMP('2022-05-18 13:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580854 AND AD_Language='de_CH'
;

-- 2022-05-18T11:12:45.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580854,'de_CH')
;

-- 2022-05-18T11:13:00.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Platzhalter HU',Updated=TO_TIMESTAMP('2022-05-18 13:13:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580854 AND AD_Language='de_DE'
;

-- 2022-05-18T11:13:00.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580854,'de_DE')
;

-- 2022-05-18T11:13:00.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580854,'de_DE')
;

-- 2022-05-18T11:13:00.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Placeholder_HU_ID', Name='Platzhalter HU', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL WHERE AD_Element_ID=580854
;

-- 2022-05-18T11:13:00.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Placeholder_HU_ID', Name='Platzhalter HU', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL, AD_Element_ID=580854 WHERE UPPER(ColumnName)='PLACEHOLDER_HU_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-18T11:13:00.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Placeholder_HU_ID', Name='Platzhalter HU', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL WHERE AD_Element_ID=580854 AND IsCentrallyMaintained='Y'
;

-- 2022-05-18T11:13:00.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Platzhalter HU', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580854) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580854)
;

-- 2022-05-18T11:13:00.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Platzhalter HU', Name='Platzhalter HU' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580854)
;

-- 2022-05-18T11:13:00.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Platzhalter HU', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580854
;

-- 2022-05-18T11:13:00.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Platzhalter HU', Description='Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', Help=NULL WHERE AD_Element_ID = 580854
;

-- 2022-05-18T11:13:00.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Platzhalter HU', Description = 'Die HU ID die als Platzhalter für gelöschte Einträge verwendet wird. ', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580854
;

-- 2022-05-18T15:47:09.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Placeholder HU',Updated=TO_TIMESTAMP('2022-05-18 17:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580854 AND AD_Language='en_US'
;

-- 2022-05-18T15:47:09.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580854,'en_US')
;

