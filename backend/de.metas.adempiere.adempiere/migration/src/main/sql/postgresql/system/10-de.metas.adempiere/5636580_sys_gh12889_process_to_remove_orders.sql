
CREATE OR REPLACE FUNCTION remove_documents_between_dates(
    p_delete CHARACTER,
    from_date DATE,
    to_date  DATE,
    client_id NUMERIC
) RETURNS VOID
    LANGUAGE plpgsql
AS
$$
DECLARE
    removed_count NUMERIC;
BEGIN

    CREATE TEMP TABLE temp_c_order AS
    SELECT c_order_id FROM c_order WHERE date(created) BETWEEN from_date AND to_date;
    CREATE TEMP TABLE temp_c_invoice AS
    SELECT c_invoice_id FROM c_invoice WHERE c_order_id in (SELECT c_order_id from temp_c_order);
    CREATE TEMP TABLE temp_c_orderline AS
    SELECT c_orderline_id FROM c_orderline WHERE c_order_id IN (SELECT c_order_id from temp_c_order);
    CREATE TEMP TABLE temp_m_inout AS
    SELECT m_inout_id FROM m_inout WHERE c_order_id IN (SELECT c_order_id from temp_c_order);
    CREATE TEMP TABLE temp_m_inoutline AS
    SELECT m_inoutline_id FROM m_inoutline WHERE m_inout_id IN (SELECT m_inout_id from temp_m_inout);
    CREATE TEMP TABLE temp_c_invoiceline AS
    SELECT c_invoiceline_id FROM c_invoiceline WHERE c_invoice_id IN (SELECT c_invoice_id from temp_c_invoice)
                                                  OR m_inoutline_id IN (SELECT m_inoutline_id from temp_m_inoutline);
    CREATE TEMP TABLE temp_c_invoice_candidate AS
    SELECT c_invoice_candidate_id FROM c_invoice_candidate WHERE c_order_id IN (SELECT c_order_id from temp_c_order);
    CREATE TEMP TABLE temp_m_shipmentschedule AS
    SELECT m_shipmentschedule_id FROM m_shipmentschedule WHERE c_order_id IN (SELECT c_order_id from temp_c_order);
    CREATE TEMP TABLE temp_m_receiptschedule AS
    SELECT m_receiptschedule_id FROM m_receiptschedule WHERE c_order_id IN (SELECT c_order_id from temp_c_order);
    CREATE TEMP TABLE temp_edi_desadv AS
    SELECT edi_desadv_id FROM edi_desadv WHERE date(created) BETWEEN from_date AND to_date;
    CREATE TEMP TABLE temp_m_matchinv AS
    SELECT m_matchinv_id FROM m_matchinv WHERE c_invoice_id IN (SELECT c_invoice_id from temp_c_invoice)
                                            OR c_invoiceline_id IN (SELECT c_invoiceline_id from temp_c_invoiceline);
    CREATE TEMP TABLE temp_m_inventoryline AS
    SELECT m_inventoryline_id FROM m_inventoryline WHERE m_inoutline_id IN (SELECT m_inoutline_id from temp_m_inoutline);

    IF p_delete = 'Y' THEN
        -- Tables with no foreign key constrains
        DELETE FROM c_ordertax WHERE c_order_id IN (SELECT c_order_id FROM temp_c_order);
        DELETE FROM m_costdetail WHERE m_matchpo_id IN (SELECT m_matchpo_id FROM m_matchpo WHERE c_orderline_id IN (SELECT c_orderline_id FROM temp_c_orderline))
                                    OR m_matchinv_id IN (SELECT m_matchinv_id FROM temp_m_matchinv)
                                    OR c_invoiceline_id IN (SELECT c_invoiceline_id FROM temp_c_invoiceline)
                                    OR m_inoutline_id IN (SELECT m_inoutline_id FROM temp_m_inoutline)
                                    OR m_inventoryline_id in (SELECT m_inventoryline_id FROM temp_m_inventoryline);
        DELETE FROM c_invoice_line_alloc WHERE c_invoiceline_id IN (SELECT c_invoiceline_id FROM temp_c_invoiceline)
                                            OR c_invoice_candidate_id IN (SELECT c_invoice_candidate_id FROM temp_c_invoice_candidate);
        DELETE FROM m_hu_trace WHERE m_shipmentschedule_id IN (SELECT m_shipmentschedule_id FROM temp_m_shipmentschedule)
                                  OR m_inout_id IN (SELECT m_inout_id FROM temp_m_inout);
        DELETE FROM c_order_line_alloc WHERE c_orderline_id IN (SELECT c_orderline_id FROM temp_c_orderline);
        DELETE FROM m_receiptschedule_alloc WHERE m_receiptschedule_id IN (SELECT m_receiptschedule_id FROM temp_m_receiptschedule);
        DELETE FROM c_invoicecandidate_inoutline WHERE c_invoice_candidate_id IN (SELECT c_invoice_candidate_id FROM temp_c_invoice_candidate);
        DELETE FROM m_transaction WHERE m_inoutline_id IN (SELECT m_inoutline_id FROM temp_m_inoutline)
                                    OR m_inventoryline_id in (SELECT m_inventoryline_id FROM temp_m_inventoryline);
        DELETE FROM m_material_balance_detail WHERE m_inoutline_id IN (SELECT m_inoutline_id FROM temp_m_inoutline);
        DELETE FROM m_shipmentschedule_qtypicked WHERE m_shipmentschedule_id IN (SELECT m_shipmentschedule_id FROM temp_m_shipmentschedule);
        DELETE FROM c_bpartner_export WHERE c_order_id IN (SELECT c_order_id FROM temp_c_order);


        -- Tables with one foreign key constrain
        DELETE FROM m_matchinv WHERE m_matchinv_id IN (SELECT m_matchinv_id FROM temp_m_matchinv);
        DELETE FROM m_matchpo WHERE c_orderline_id IN (SELECT c_orderline_id FROM temp_c_orderline);
        DELETE FROM m_receiptschedule WHERE c_order_id IN (SELECT c_order_id FROM temp_c_order);

        -- Tables with multiple foreign key constrain
        DELETE FROM m_inventoryline WHERE m_inventoryline_id in (SELECT m_inventoryline_id FROM temp_m_inventoryline);
        DELETE FROM c_invoice_candidate WHERE c_order_id IN (SELECT c_order_id FROM temp_c_order);
        DELETE FROM m_picking_candidate WHERE m_shipmentschedule_id IN (SELECT m_shipmentschedule_id FROM temp_m_shipmentschedule);
        DELETE FROM c_invoiceline WHERE c_invoiceline_id IN (SELECT c_invoiceline_id FROM temp_c_invoiceline);
        DELETE FROM m_inoutline WHERE m_inoutline_id IN (SELECT m_inoutline_id FROM temp_m_inoutline);
        DELETE FROM m_inout WHERE m_inout_id IN (SELECT m_inout_id from temp_m_inout);
        DELETE FROM c_orderline WHERE c_orderline_id IN (SELECT c_orderline_id from temp_c_orderline);

        DELETE FROM edi_desadvline_pack WHERE edi_desadv_id in (SELECT edi_desadv_id from temp_edi_desadv);
        DELETE FROM edi_desadvline WHERE edi_desadv_id in (SELECT edi_desadv_id from temp_edi_desadv);
        DELETE FROM edi_desadv WHERE date(created) BETWEEN from_date AND to_date;

        DELETE FROM c_invoice WHERE c_invoice_id in (SELECT c_invoice_id from temp_c_invoice);
        DELETE FROM c_order WHERE c_order_id IN (SELECT c_order_id FROM temp_c_order);

        DELETE FROM c_queue_element WHERE ad_table_id = get_table_id('m_inout') AND record_id in (SELECT m_inout_id from temp_m_inout);
        DELETE FROM c_queue_element WHERE ad_table_id = get_table_id('c_invoice') AND record_id in (SELECT c_invoice_id from temp_c_invoice);
        DELETE FROM c_queue_element WHERE ad_table_id = get_table_id('c_order') AND record_id in (SELECT c_order_id FROM temp_c_order);

        removed_count := count(c_order_id) from temp_c_order;
        RAISE NOTICE 'Removed % elements from c_order', removed_count;

    ELSE
        -- Tables with no foreign key constrains
        UPDATE c_ordertax SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_order_id IN (SELECT c_order_id from temp_c_order);
        UPDATE m_costdetail SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_matchpo_id IN (SELECT m_matchpo_id FROM m_matchpo WHERE c_orderline_id IN (SELECT c_orderline_id from temp_c_orderline))
                                                                                             OR m_matchinv_id IN (SELECT m_matchinv_id FROM m_matchinv WHERE c_invoiceline_id IN (SELECT c_invoice_id from temp_c_invoice))
                                                                                             OR c_invoiceline_id IN (SELECT c_invoiceline_id from temp_c_invoiceline)
                                                                                             OR m_inoutline_id IN (SELECT m_inoutline_id from temp_m_inoutline);
        UPDATE c_invoice_line_alloc SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_invoiceline_id IN (SELECT c_invoiceline_id from temp_c_invoiceline)
                                                                                                     OR c_invoice_candidate_id IN (SELECT c_invoice_candidate_id from temp_c_invoice_candidate);
        UPDATE m_hu_trace SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_shipmentschedule_id IN (SELECT m_shipmentschedule_id from temp_m_shipmentschedule)
                                                                                           OR m_inout_id IN (SELECT m_inout_id FROM temp_m_inout);
        UPDATE c_order_line_alloc SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_orderline_id IN (SELECT c_orderline_id from temp_c_orderline);
        UPDATE m_receiptschedule_alloc SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_receiptschedule_id IN (SELECT m_receiptschedule_id from temp_m_receiptschedule);
        UPDATE c_invoicecandidate_inoutline SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_invoice_candidate_id IN (SELECT c_invoice_candidate_id from temp_c_invoice_candidate);
        UPDATE m_transaction SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_inoutline_id IN (SELECT m_inoutline_id from temp_m_inoutline);
        UPDATE m_material_balance_detail SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_inoutline_id IN (SELECT m_inoutline_id from temp_m_inoutline);
        UPDATE m_shipmentschedule_qtypicked SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_shipmentschedule_id IN (SELECT m_shipmentschedule_id from temp_m_shipmentschedule);
        UPDATE c_bpartner_export SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_order_id IN (SELECT c_order_id FROM temp_c_order);

        -- Tables with one foreign key constrain
        UPDATE m_matchinv SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_invoice_id IN (SELECT c_invoice_id from temp_c_invoice)
                                                                                           OR c_invoiceline_id IN (SELECT c_invoiceline_id from temp_c_invoiceline);
        UPDATE m_matchpo SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_orderline_id IN (SELECT c_orderline_id from temp_c_orderline);
        UPDATE m_receiptschedule SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_order_id IN (SELECT c_order_id from temp_c_order);

        -- Tables with multiple foreign key constrain
        UPDATE m_inventoryline SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_inventoryline_id in (SELECT m_inventoryline_id FROM temp_m_inventoryline);
        UPDATE c_invoice_candidate SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_order_id IN (SELECT c_order_id from temp_c_order);
        UPDATE m_picking_candidate SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_shipmentschedule_id IN (SELECT m_shipmentschedule_id from temp_m_shipmentschedule);
        UPDATE c_invoiceline SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_invoiceline_id IN (SELECT c_invoiceline_id from temp_c_invoiceline);
        UPDATE m_inoutline SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE m_inout_id IN (SELECT m_inout_id from temp_m_inout);
        UPDATE m_inout SET ad_client_id = client_id, updated = now(), updatedby = 99  WHERE m_inout_id IN (SELECT m_inout_id from temp_m_inout);
        UPDATE c_orderline SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_orderline_id IN (SELECT c_orderline_id from temp_c_orderline);

        UPDATE edi_desadvline_pack SET ad_client_id = client_id, updated = now(), updatedby = 99  WHERE edi_desadv_id in (SELECT edi_desadv_id from temp_edi_desadv);
        UPDATE edi_desadvline SET ad_client_id = client_id, updated = now(), updatedby = 99  WHERE edi_desadv_id in (SELECT edi_desadv_id from temp_edi_desadv);
        UPDATE edi_desadv SET ad_client_id = client_id, updated = now(), updatedby = 99  WHERE date(created) BETWEEN from_date AND to_date;

        UPDATE c_invoice SET ad_client_id = client_id, updated = now(), updatedby = 99  WHERE c_invoice_id in (SELECT c_invoice_id from temp_c_invoice);
        UPDATE c_order SET ad_client_id = client_id, updated = now(), updatedby = 99 WHERE c_order_id IN (SELECT c_order_id FROM temp_c_order);

    END IF;
    DROP TABLE temp_c_order;
    DROP TABLE temp_c_invoice;
    DROP TABLE temp_c_orderline;
    DROP TABLE temp_m_inout;
    DROP TABLE temp_m_inoutline;
    DROP TABLE temp_c_invoiceline;
    DROP TABLE temp_c_invoice_candidate;
    DROP TABLE temp_m_shipmentschedule;
    DROP TABLE temp_m_receiptschedule;
    DROP TABLE temp_edi_desadv;
    DROP TABLE temp_m_matchinv;
    DROP TABLE temp_m_inventoryline;
END
$$;



INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('6',0,0,585040,'Y','de.metas.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2022-04-11 17:04:36','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'remove_documents_between_dates','json','N','N','xls','SELECT remove_documents_between_dates(''@IsDelete@'',''@DateFrom@'',''@DateTo@'',@AD_Client_ID@);','','SQL',TO_TIMESTAMP('2022-04-11 17:04:36','YYYY-MM-DD HH24:MI:SS'),100,'remove_documents_between_dates')
;

-- 2022-04-11T15:04:36.289Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585040 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;


-- 2022-04-14T14:22:40.919Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580785,0,'IsDelete',TO_TIMESTAMP('2022-04-14 16:22:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delete','Delete',TO_TIMESTAMP('2022-04-14 16:22:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T14:22:40.927Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580785 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2022-04-11T15:05:48.855Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585040,542245,16,'from_date',TO_TIMESTAMP('2022-04-11 17:05:48','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','from_date',10,TO_TIMESTAMP('2022-04-11 17:05:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-11T15:05:48.858Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542245 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-04-11T15:06:53.954Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585040,542246,20,'p_delete',TO_TIMESTAMP('2022-04-11 17:06:53','YYYY-MM-DD HH24:MI:SS'),100,'N','U',0,'Y','N','Y','N','N','N','p_delete',20,TO_TIMESTAMP('2022-04-11 17:06:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-11T15:06:53.956Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542246 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-04-11T15:07:16.993Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585040,542247,16,'to_date',TO_TIMESTAMP('2022-04-11 17:07:16','YYYY-MM-DD HH24:MI:SS'),100,'U',0,'Y','N','Y','N','Y','N','to_date',30,TO_TIMESTAMP('2022-04-11 17:07:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-11T15:07:16.995Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542247 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-04-11T15:08:09.320Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,585040,542248,22,'ad_client_id',TO_TIMESTAMP('2022-04-11 17:08:09','YYYY-MM-DD HH24:MI:SS'),100,'99','U',0,'Y','N','Y','N','N','N','ad_client_id',40,TO_TIMESTAMP('2022-04-11 17:08:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-11T15:08:09.322Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542248 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;



-- 2022-04-14T14:18:22.860Z
UPDATE AD_Process_Para SET AD_Element_ID=1581, ColumnName='DateFrom', Description='Startdatum eines Abschnittes', Help='Datum von bezeichnet das Startdatum eines Abschnittes', Name='Datum von',Updated=TO_TIMESTAMP('2022-04-14 16:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542245
;

-- 2022-04-14T14:20:27.170Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2022-04-14 16:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542245
;


-- 2022-04-14T14:23:57.823Z
UPDATE AD_Process_Para SET AD_Element_ID=580785, ColumnName='IsDelete', EntityType='D', IsMandatory='Y', Name='Delete',Updated=TO_TIMESTAMP('2022-04-14 16:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542246
;

-- 2022-04-14T14:24:17.749Z
UPDATE AD_Process_Para SET AD_Element_ID=1582, ColumnName='DateTo', Description='Enddatum eines Abschnittes', EntityType='D', Help='Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)', Name='Datum bis',Updated=TO_TIMESTAMP('2022-04-14 16:24:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542247
;

-- 2022-04-14T14:26:30.630Z
UPDATE AD_Process_Para SET AD_Element_ID=102, ColumnName='AD_Client_ID', Description='Mandant für diese Installation.', EntityType='D', Help='Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .', Name='Mandant',Updated=TO_TIMESTAMP('2022-04-14 16:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542248
;

-- 2022-04-14T14:29:51.693Z
UPDATE AD_Process SET SQLStatement='SELECT remove_documents_between_dates(@IsDelete@,''@DateFrom@'',''@DateTo@'',@AD_Client_ID@);',Updated=TO_TIMESTAMP('2022-04-14 16:29:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585040
;



-- 2022-04-19T12:42:16.780Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580791,0,TO_TIMESTAMP('2022-04-19 14:42:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','remove_documents_between_dates','remove_documents_between_dates',TO_TIMESTAMP('2022-04-19 14:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-19T12:42:16.790Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580791 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2022-04-19T12:45:32.796Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,580791,541926,0,TO_TIMESTAMP('2022-04-19 14:45:32','YYYY-MM-DD HH24:MI:SS'),100,'D','remove_documents_between_dates','Y','N','N','N','N','remove_documents_between_dates',TO_TIMESTAMP('2022-04-19 14:45:32','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE AD_Menu SET Action='P', AD_Process_ID=585040, EntityType='U',Updated=TO_TIMESTAMP('2022-04-20 11:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541926
;

-- 2022-04-19T12:45:32.799Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541926 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-04-19T12:45:32.801Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541926, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541926);

-- Reordering children of `System`
-- Node name: `API Aufruf`
-- 2022-04-20T09:13:29.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2022-04-20T09:13:29.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System Config (ExternalSystem_Config)`
-- 2022-04-20T09:13:29.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2022-04-20T09:13:29.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2022-04-20T09:13:29.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2022-04-20T09:13:29.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2022-04-20T09:13:29.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2022-04-20T09:13:29.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2022-04-20T09:13:29.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchrone Verarbeitung`
-- 2022-04-20T09:13:29.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2022-04-20T09:13:29.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Asynchrone Verarbeitungswarteschlange (C_Queue_WorkPackage)`
-- 2022-04-20T09:13:29.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Ablaufsteuerung (AD_Scheduler)`
-- 2022-04-20T09:13:29.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Stapel (C_Async_Batch)`
-- 2022-04-20T09:13:29.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Rolle - Verwaltung (AD_Role)`
-- 2022-04-20T09:13:29.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Stapel Art (C_Async_Batch_Type)`
-- 2022-04-20T09:13:29.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `Nutzer (AD_User)`
-- 2022-04-20T09:13:29.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Gegenbeleg (C_DocTypeCounter)`
-- 2022-04-20T09:13:29.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Nutzergruppe (AD_UserGroup)`
-- 2022-04-20T09:13:29.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2022-04-20T09:13:29.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Sprache (AD_Language)`
-- 2022-04-20T09:13:29.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menü (AD_Menu)`
-- 2022-04-20T09:13:29.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `KPI Dashboard (WEBUI_Dashboard)`
-- 2022-04-20T09:13:29.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2022-04-20T09:13:29.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Belegart (C_DocType)`
-- 2022-04-20T09:13:29.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Textbausteine (AD_BoilerPlate)`
-- 2022-04-20T09:13:29.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Textbaustein Übersetzung (AD_BoilerPlate_Trl)`
-- 2022-04-20T09:13:29.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2022-04-20T09:13:29.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Textvariablen (AD_BoilerPlate_Var)`
-- 2022-04-20T09:13:29.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Druck - Format (AD_PrintFormat)`
-- 2022-04-20T09:13:29.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `AD_Zebra_Config_ID (AD_Zebra_Config)`
-- 2022-04-20T09:13:29.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Nummernfolgen (AD_Sequence)`
-- 2022-04-20T09:13:29.116Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `Mein Profil (AD_User)`
-- 2022-04-20T09:13:29.117Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Druck Warteschlange (C_Printing_Queue)`
-- 2022-04-20T09:13:29.117Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2022-04-20T09:13:29.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2022-04-20T09:13:29.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Drucker (AD_PrinterHW)`
-- 2022-04-20T09:13:29.119Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Drucker Zuordnung (AD_Printer_Config)`
-- 2022-04-20T09:13:29.120Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Drucker-Schacht-Zuordnung (AD_Printer_Matching)`
-- 2022-04-20T09:13:29.120Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `Fehlender/Unvollständiger Gegenbeleg (RV_Missing_Counter_Documents)`
-- 2022-04-20T09:13:29.121Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System-Konfigurator (AD_SysConfig)`
-- 2022-04-20T09:13:29.121Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2022-04-20T09:13:29.122Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Sitzungs-Revision (AD_Session)`
-- 2022-04-20T09:13:29.123Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2022-04-20T09:13:29.123Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Änderungs Historie (AD_ChangeLog)`
-- 2022-04-20T09:13:29.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Geschäftspartner (I_BPartner)`
-- 2022-04-20T09:13:29.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2022-04-20T09:13:29.125Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Produkt (I_Product)`
-- 2022-04-20T09:13:29.126Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2022-04-20T09:13:29.127Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventur (I_Inventory)`
-- 2022-04-20T09:13:29.128Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2022-04-20T09:13:29.128Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Kontenrahmen (I_ElementValue)`
-- 2022-04-20T09:13:29.129Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Formate (AD_ImpFormat)`
-- 2022-04-20T09:13:29.130Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Daten Import (C_DataImport)`
-- 2022-04-20T09:13:29.130Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2022-04-20T09:13:29.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2022-04-20T09:13:29.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Prozessor (IMP_Processor)`
-- 2022-04-20T09:13:29.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Prozessor Log (IMP_ProcessorLog)`
-- 2022-04-20T09:13:29.133Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2022-04-20T09:13:29.133Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Buchungen Export Format (DATEV_ExportFormat)`
-- 2022-04-20T09:13:29.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Meldung (AD_Message)`
-- 2022-04-20T09:13:29.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Ereignis Log (AD_EventLog)`
-- 2022-04-20T09:13:29.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2022-04-20T09:13:29.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Aktionen`
-- 2022-04-20T09:13:29.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Berichte`
-- 2022-04-20T09:13:29.137Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen`
-- 2022-04-20T09:13:29.138Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Erweiterte Fenster`
-- 2022-04-20T09:13:29.139Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Anhang Änderungslog (AD_Attachment_Log)`
-- 2022-04-20T09:13:29.139Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (dba_seq_check_native) (de.metas.process.ExecuteUpdateSQL)`
-- 2022-04-20T09:13:29.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Rollen-Zugriff aktualisieren (org.compiere.process.RoleAccessUpdate)`
-- 2022-04-20T09:13:29.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `Datensatzberechtigungen für Vertriebspartner aktualisieren (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2022-04-20T09:13:29.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2022-04-20T09:13:29.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Konfiguration (GeocodingConfig)`
-- 2022-04-20T09:13:29.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exportierte Daten Revision (Data_Export_Audit)`
-- 2022-04-20T09:13:29.143Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2022-04-20T09:13:29.144Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2022-04-20T09:13:29.144Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2022-04-20T09:13:29.145Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `remove_documents_between_dates`
-- 2022-04-20T09:13:29.145Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541926 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2022-04-20T09:13:29.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- 2022-04-21T10:47:50.570Z
UPDATE AD_Process SET SQLStatement='SELECT remove_documents_between_dates(''@IsDelete@'',''@DateFrom@'',''@DateTo@'',@AD_Client_ID@);',Updated=TO_TIMESTAMP('2022-04-21 12:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585040
;

-- 2022-04-21T10:49:56.189Z
UPDATE AD_Menu SET Description='Removes entries from orders, invoices, shipments within the given timeframe.', IsActive='Y', Name='remove_documents_between_dates',Updated=TO_TIMESTAMP('2022-04-21 12:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541926
;

-- 2022-04-21T10:50:34.281Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Auftraege ',Updated=TO_TIMESTAMP('2022-04-21 12:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585040
;

-- 2022-04-21T10:50:34.284Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Auftraege ',Updated=TO_TIMESTAMP('2022-04-21 12:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541926
;


-- 2022-04-21T10:56:35.178Z
UPDATE AD_Process SET Description='Elemente aus Aufträgen, Rechnungen und Lieferungen in angeben Zeitraum werden gelöscht oder in einen anderen Mandanten verschoben. ', EntityType='D' ,Help='Elemente aus Aufträgen, Rechnungen und Lieferungen in angeben Zeitraum werden gelöscht oder in einen anderen Mandanten verschoben. ', Name='Auftrags Elemente entfernen',Updated=TO_TIMESTAMP('2022-04-21 12:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585040
;

-- 2022-04-21T10:56:35.183Z
UPDATE AD_Menu SET Description='Elemente aus Aufträgen, Rechnungen und Lieferungen in angeben Zeitraum werden gelöscht oder in einen anderen Mandanten verschoben. ', IsActive='Y', Name='Auftrags Elemente entfernen',Updated=TO_TIMESTAMP('2022-04-21 12:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541926
;

-- 2022-04-21T10:56:35.172Z
UPDATE AD_Process_Trl SET Description='Elemente aus Aufträgen, Rechnungen und Lieferungen in angeben Zeitraum werden gelöscht oder in einen anderen Mandanten verschoben. ', Help='Elemente aus Aufträgen, Rechnungen und Lieferungen in angeben Zeitraum werden gelöscht oder in einen anderen Mandanten verschoben. ', IsTranslated='Y', Name='Auftrags Elemente entfernen',Updated=TO_TIMESTAMP('2022-04-21 12:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585040
;

-- 2022-04-21T10:57:53.737Z
UPDATE AD_Process_Trl SET Description='Elements from orders, invoices and shipments in the given timeframe will be deleted or moved to a different client.', Help='Elements from orders, invoices and shipments in the given timeframe will be deleted or moved to a different client.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-04-21 12:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585040
;

