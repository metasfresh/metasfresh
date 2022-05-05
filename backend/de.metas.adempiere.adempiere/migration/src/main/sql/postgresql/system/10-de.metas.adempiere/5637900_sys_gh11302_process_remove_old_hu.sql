INSERT INTO m_hu (ad_client_id, ad_org_id, created, createdby, isactive, m_hu_id,m_hu_pi_version_id, value, updated, updatedby, hustatus)
VALUES (1000000,1000000,date('2022-05-05'),100,'Y',100,101,100.,date('2022-05-05'),100,'A');

CREATE OR REPLACE FUNCTION remove_hu_entries_between_dates(
    months NUMERIC,
    placeholder NUMERIC
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN
    CREATE TEMP TABLE temp_m_hu AS
    SELECT m_hu_id FROM m_hu WHERE hustatus != 'A' AND updated <= now() - (months || ' month')::INTERVAL LIMIT 10;
    CREATE TEMP TABLE temp_m_hu_item AS
    SELECT m_hu_item_id FROM m_hu_item WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    CREATE TEMP TABLE temp_m_inventoryline AS
    SELECT m_inventoryline_id FROM m_inventoryline WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    -- No FK-Constrains
    DELETE FROM m_hu_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_attribute_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_storage_snapshot WHERE m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);
    DELETE FROM m_hu_storage_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_receiptschedule_alloc SET m_lu_hu_id = placeholder, m_tu_hu_id = placeholder WHERE m_lu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_tu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);

    DELETE FROM m_picking_candidate WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_pickingslot_trx WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_shipmentschedule_qtypicked SET m_lu_hu_id = placeholder, m_tu_hu_id = placeholder WHERE m_lu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_tu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR vhu_id IN (SELECT m_hu_id FROM temp_m_hu);

    DELETE FROM m_hu_trx_attribute WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_assignment WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_trace WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_source_hu WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_package_hu SET m_hu_id = placeholder WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_inventoryline_hu SET m_hu_id = placeholder WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE pp_order_productattribute SET m_hu_id = placeholder  WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE pp_order_qty SET m_hu_id = placeholder WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);


    -- One FK-Constrains
    DELETE FROM m_hu_attribute WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_storage WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_storage WHERE m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);

    CREATE TEMP TABLE temp_m_hu_trx_line AS
    SELECT m_hu_trx_line_id FROM m_hu_trx_line WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_hu_trx_line SET parent_hu_trx_line_id = NULL WHERE parent_hu_trx_line_id IN (SELECT m_hu_trx_line_id FROM temp_m_hu_trx_line);
    DELETE FROM m_hu_trx_line WHERE m_hu_trx_line_id IN (SELECT m_hu_trx_line_id FROM temp_m_hu_trx_line);

    -- Multiple FK-Constrains
    UPDATE m_inventoryline SET m_hu_id = placeholder WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    DROP TABLE temp_m_hu;
    DROP TABLE temp_m_hu_item;
    DROP TABLE temp_m_inventoryline;
    DROP TABLE temp_m_hu_trx_line;

END
$$;


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

