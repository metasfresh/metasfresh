
-- remove Production window
DELETE FROM AD_Menu WHERE ad_window_id=191;

-- 2018-02-21T15:25:53.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=191
;

-- 2018-02-21T15:25:53.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=191
;

DELETE FROM AD_PrintFormat WHERE AD_Table_ID=get_table_id('M_Production');

-- 2018-02-21T15:29:00.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=325
;

-- 2018-02-21T15:29:00.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=325
;

DELETE FROM ad_columncallout where ad_table_id=get_table_id('M_ProductionLine');
DELETE FROM ad_ref_table where ad_table_id=get_table_id('M_ProductionLine');
-- 2018-02-21T15:32:27.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=326
;

-- 2018-02-21T15:32:27.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=326
;

-- M_ProductionLineMA
-- 2018-02-21T15:32:53.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=765
;

-- 2018-02-21T15:32:53.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=765
;

DELETE FROM AD_PrintFormat WHERE AD_Table_ID=get_table_id('M_ProductionPlan');
-- 2018-02-21T15:33:41.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=385
;

-- 2018-02-21T15:33:41.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=385
;

-- view rv_transaction
DROP VIEW RV_Transaction;

DELETE FROM AD_PrintFormat WHERE AD_Table_ID=get_table_id('RV_Transaction');

-- process RV_Transaction
DELETE FROM AD_Menu where (ad_process_id)=(237);-- 2018-02-21T15:38:23.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=237
;

-- 2018-02-21T15:38:23.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=237
;

-- 2018-02-21T15:38:38.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ReportView WHERE AD_ReportView_ID=136
;

-- 2018-02-21T15:38:49.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=629
;

-- 2018-02-21T15:38:49.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=629
;

-- drop M_Transaction_v
-- 2018-02-21T15:47:11.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=766
;

-- 2018-02-21T15:47:11.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=766
;

DROP VIEW IF EXISTS M_Transaction_v;

-- cleanup M_Transaction
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (SELECT AD_Field_id FROM AD_Field WHERE AD_Column_ID=3674);
DELETE FROM AD_Field WHERE AD_Column_ID=3674;
-- 2018-02-21T15:43:29.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=3674
;

-- 2018-02-21T15:43:29.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=3674
;

SELECT db_alter_table('M_Transaction', 'ALTER TABLE M_Transaction DROP COLUMN IF EXISTS M_ProductionLine_ID;');

DROP TABLE M_ProductionLineMA;

--
-- M_TransactionAllocation
--
DROP TABLE M_TransactionAllocation;
-- 2018-02-21T15:57:17.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=636
;

-- 2018-02-21T15:57:17.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=636
;

--
-- M_CostDetail
--
DELETE FROM ad_ui_element where AD_Field_ID IN (select ad_field_id from AD_Field where (ad_column_id)=(14406));
delete from AD_Field where (ad_column_id)=(14406);

-- 2018-02-21T16:05:47.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=14406
;

-- 2018-02-21T16:05:47.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=14406
;
select db_alter_table('M_CostDetail', 'ALTER TABLE M_CostDetail DROP COLUMN IF EXISTS M_ProductionLine_ID');

--
-- T_Tranaction
--
-- process "M_TransactionXRef"
delete from ad_menu where (ad_process_id)=(322);

-- 2018-02-21T16:12:50.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=322
;

-- 2018-02-21T16:12:50.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=322
;

-- 2018-02-21T16:12:59.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ReportView WHERE AD_ReportView_ID=159
;

-- 2018-02-21T16:13:07.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=758
;

-- 2018-02-21T16:13:07.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=758
;

DROP TABLE T_Transaction;
DROP TABLE M_ProductionLine;
DROP TABLE M_ProductionPlan;

--
-- remove M_production from the "unposted" view
--
CREATE OR REPLACE VIEW public.rv_unposted AS 
 SELECT gl_journal.ad_client_id,
    gl_journal.ad_org_id,
    gl_journal.created,
    gl_journal.createdby,
    gl_journal.updated,
    gl_journal.updatedby,
    gl_journal.isactive,
    gl_journal.documentno,
    gl_journal.datedoc,
    gl_journal.dateacct,
    224 AS ad_table_id,
    gl_journal.gl_journal_id AS record_id,
    'N'::text AS issotrx,
    gl_journal.posted,
    gl_journal.processing,
    gl_journal.processed,
    gl_journal.docstatus
   FROM gl_journal
  WHERE gl_journal.posted <> 'Y'::bpchar AND gl_journal.docstatus <> 'VO'::bpchar AND gl_journal.processed = 'Y'::bpchar
UNION
 SELECT pi.ad_client_id,
    pi.ad_org_id,
    pi.created,
    pi.createdby,
    pi.updated,
    pi.updatedby,
    pi.isactive,
    (p.name::text || '_'::text) || pi.line::text AS documentno,
    pi.movementdate AS datedoc,
    pi.movementdate AS dateacct,
    623 AS ad_table_id,
    pi.c_projectissue_id AS record_id,
    'N'::text AS issotrx,
    pi.posted,
    pi.processing,
    pi.processed,
    'CO'::bpchar AS docstatus
   FROM c_projectissue pi
     JOIN c_project p ON pi.c_project_id = p.c_project_id
  WHERE pi.posted <> 'Y'::bpchar AND pi.processed = 'Y'::bpchar
UNION
 SELECT c_invoice.ad_client_id,
    c_invoice.ad_org_id,
    c_invoice.created,
    c_invoice.createdby,
    c_invoice.updated,
    c_invoice.updatedby,
    c_invoice.isactive,
    c_invoice.documentno,
    c_invoice.dateinvoiced AS datedoc,
    c_invoice.dateacct,
    318 AS ad_table_id,
    c_invoice.c_invoice_id AS record_id,
    c_invoice.issotrx,
    c_invoice.posted,
    c_invoice.processing,
    c_invoice.processed,
    c_invoice.docstatus
   FROM c_invoice
  WHERE c_invoice.posted <> 'Y'::bpchar AND c_invoice.docstatus <> 'VO'::bpchar AND c_invoice.processed = 'Y'::bpchar
UNION
 SELECT m_inout.ad_client_id,
    m_inout.ad_org_id,
    m_inout.created,
    m_inout.createdby,
    m_inout.updated,
    m_inout.updatedby,
    m_inout.isactive,
    m_inout.documentno,
    m_inout.movementdate AS datedoc,
    m_inout.dateacct,
    319 AS ad_table_id,
    m_inout.m_inout_id AS record_id,
    m_inout.issotrx,
    m_inout.posted,
    m_inout.processing,
    m_inout.processed,
    m_inout.docstatus
   FROM m_inout
  WHERE m_inout.posted <> 'Y'::bpchar AND m_inout.docstatus <> 'VO'::bpchar AND m_inout.processed = 'Y'::bpchar
UNION
 SELECT m_inventory.ad_client_id,
    m_inventory.ad_org_id,
    m_inventory.created,
    m_inventory.createdby,
    m_inventory.updated,
    m_inventory.updatedby,
    m_inventory.isactive,
    m_inventory.documentno,
    m_inventory.movementdate AS datedoc,
    m_inventory.movementdate AS dateacct,
    321 AS ad_table_id,
    m_inventory.m_inventory_id AS record_id,
    'N'::text AS issotrx,
    m_inventory.posted,
    m_inventory.processing,
    m_inventory.processed,
    m_inventory.docstatus
   FROM m_inventory
  WHERE m_inventory.posted <> 'Y'::bpchar AND m_inventory.docstatus <> 'VO'::bpchar AND m_inventory.processed = 'Y'::bpchar
UNION
 SELECT m_movement.ad_client_id,
    m_movement.ad_org_id,
    m_movement.created,
    m_movement.createdby,
    m_movement.updated,
    m_movement.updatedby,
    m_movement.isactive,
    m_movement.documentno,
    m_movement.movementdate AS datedoc,
    m_movement.movementdate AS dateacct,
    323 AS ad_table_id,
    m_movement.m_movement_id AS record_id,
    'N'::text AS issotrx,
    m_movement.posted,
    m_movement.processing,
    m_movement.processed,
    m_movement.docstatus
   FROM m_movement
  WHERE m_movement.posted <> 'Y'::bpchar AND m_movement.docstatus <> 'VO'::bpchar AND m_movement.processed = 'Y'::bpchar
UNION
 SELECT c_cash.ad_client_id,
    c_cash.ad_org_id,
    c_cash.created,
    c_cash.createdby,
    c_cash.updated,
    c_cash.updatedby,
    c_cash.isactive,
    c_cash.name AS documentno,
    c_cash.statementdate AS datedoc,
    c_cash.dateacct,
    407 AS ad_table_id,
    c_cash.c_cash_id AS record_id,
    'N'::text AS issotrx,
    c_cash.posted,
    c_cash.processing,
    c_cash.processed,
    c_cash.docstatus
   FROM c_cash
  WHERE c_cash.posted <> 'Y'::bpchar AND c_cash.docstatus <> 'VO'::bpchar AND c_cash.processed = 'Y'::bpchar
UNION
 SELECT c_payment.ad_client_id,
    c_payment.ad_org_id,
    c_payment.created,
    c_payment.createdby,
    c_payment.updated,
    c_payment.updatedby,
    c_payment.isactive,
    c_payment.documentno,
    c_payment.datetrx AS datedoc,
    c_payment.datetrx AS dateacct,
    335 AS ad_table_id,
    c_payment.c_payment_id AS record_id,
    'N'::text AS issotrx,
    c_payment.posted,
    c_payment.processing,
    c_payment.processed,
    c_payment.docstatus
   FROM c_payment
  WHERE c_payment.posted <> 'Y'::bpchar AND c_payment.docstatus <> 'VO'::bpchar AND c_payment.processed = 'Y'::bpchar
UNION
 SELECT c_allocationhdr.ad_client_id,
    c_allocationhdr.ad_org_id,
    c_allocationhdr.created,
    c_allocationhdr.createdby,
    c_allocationhdr.updated,
    c_allocationhdr.updatedby,
    c_allocationhdr.isactive,
    c_allocationhdr.documentno,
    c_allocationhdr.datetrx AS datedoc,
    c_allocationhdr.datetrx AS dateacct,
    735 AS ad_table_id,
    c_allocationhdr.c_allocationhdr_id AS record_id,
    'N'::text AS issotrx,
    c_allocationhdr.posted,
    c_allocationhdr.processing,
    c_allocationhdr.processed,
    c_allocationhdr.docstatus
   FROM c_allocationhdr
  WHERE c_allocationhdr.posted <> 'Y'::bpchar AND (c_allocationhdr.docstatus <> ALL (ARRAY['VO'::bpchar, 'RE'::bpchar])) AND c_allocationhdr.processed = 'Y'::bpchar
UNION
 SELECT c_bankstatement.ad_client_id,
    c_bankstatement.ad_org_id,
    c_bankstatement.created,
    c_bankstatement.createdby,
    c_bankstatement.updated,
    c_bankstatement.updatedby,
    c_bankstatement.isactive,
    c_bankstatement.name AS documentno,
    c_bankstatement.statementdate AS datedoc,
    c_bankstatement.statementdate AS dateacct,
    392 AS ad_table_id,
    c_bankstatement.c_bankstatement_id AS record_id,
    'N'::text AS issotrx,
    c_bankstatement.posted,
    c_bankstatement.processing,
    c_bankstatement.processed,
    c_bankstatement.docstatus
   FROM c_bankstatement
  WHERE c_bankstatement.posted <> 'Y'::bpchar AND c_bankstatement.docstatus <> 'VO'::bpchar AND c_bankstatement.processed = 'Y'::bpchar
UNION
 SELECT m_matchinv.ad_client_id,
    m_matchinv.ad_org_id,
    m_matchinv.created,
    m_matchinv.createdby,
    m_matchinv.updated,
    m_matchinv.updatedby,
    m_matchinv.isactive,
    m_matchinv.documentno,
    m_matchinv.datetrx AS datedoc,
    m_matchinv.datetrx AS dateacct,
    472 AS ad_table_id,
    m_matchinv.m_matchinv_id AS record_id,
    'N'::text AS issotrx,
    m_matchinv.posted,
    m_matchinv.processing,
    m_matchinv.processed,
    'CO'::bpchar AS docstatus
   FROM m_matchinv
  WHERE m_matchinv.posted <> 'Y'::bpchar AND m_matchinv.processed = 'Y'::bpchar
UNION
 SELECT m_matchpo.ad_client_id,
    m_matchpo.ad_org_id,
    m_matchpo.created,
    m_matchpo.createdby,
    m_matchpo.updated,
    m_matchpo.updatedby,
    m_matchpo.isactive,
    m_matchpo.documentno,
    m_matchpo.datetrx AS datedoc,
    m_matchpo.datetrx AS dateacct,
    473 AS ad_table_id,
    m_matchpo.m_matchpo_id AS record_id,
    'N'::text AS issotrx,
    m_matchpo.posted,
    m_matchpo.processing,
    m_matchpo.processed,
    'CO'::bpchar AS docstatus
   FROM m_matchpo
  WHERE m_matchpo.posted <> 'Y'::bpchar AND m_matchpo.processed = 'Y'::bpchar
UNION
 SELECT c_order.ad_client_id,
    c_order.ad_org_id,
    c_order.created,
    c_order.createdby,
    c_order.updated,
    c_order.updatedby,
    c_order.isactive,
    c_order.documentno,
    c_order.dateordered AS datedoc,
    c_order.dateacct,
    259 AS ad_table_id,
    c_order.c_order_id AS record_id,
    c_order.issotrx,
    c_order.posted,
    c_order.processing,
    c_order.processed,
    c_order.docstatus
   FROM c_order
  WHERE c_order.posted <> 'Y'::bpchar AND (c_order.docstatus <> ALL (ARRAY['VO'::bpchar, 'WP'::bpchar])) AND c_order.processed = 'Y'::bpchar
UNION
 SELECT m_requisition.ad_client_id,
    m_requisition.ad_org_id,
    m_requisition.created,
    m_requisition.createdby,
    m_requisition.updated,
    m_requisition.updatedby,
    m_requisition.isactive,
    m_requisition.documentno,
    m_requisition.daterequired AS datedoc,
    m_requisition.daterequired AS dateacct,
    702 AS ad_table_id,
    m_requisition.m_requisition_id AS record_id,
    'N'::text AS issotrx,
    m_requisition.posted,
    m_requisition.processing,
    m_requisition.processed,
    m_requisition.docstatus
   FROM m_requisition
  WHERE m_requisition.posted <> 'Y'::bpchar AND m_requisition.docstatus <> 'VO'::bpchar AND m_requisition.processed = 'Y'::bpchar;

DROP TABLE M_Production;

--
-- process org.compiere.process.M_Production_Run

-- 2018-02-21T16:22:58.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=137
;

-- 2018-02-21T16:22:58.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=137
;


--
-- DocBaseType MMP

-- 2018-02-21T16:34:09.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=491
;

-- 2018-02-21T16:34:09.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=491
;

