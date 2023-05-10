-- Column: M_ReceiptSchedule.BlockedBPartner
-- Source Table: C_BPartner_BlockStatus
-- 2023-02-24T13:38:27.560Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=586200,Updated=TO_TIMESTAMP('2023-02-24 15:38:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540135
;

-- Column: M_ReceiptSchedule.BlockedBPartner
-- Source Table: C_BPartner_BlockStatus
-- 2023-02-24T13:44:54.013Z
UPDATE AD_SQLColumn_SourceTableColumn SET FetchTargetRecordsMethod='S', SQL_GetTargetRecordIdBySourceRecordId='SELECT rs.M_ReceiptSchedule_ID from M_ReceiptSchedule rs where rs.C_BPartner_ID=@Record_ID@',Updated=TO_TIMESTAMP('2023-02-24 15:44:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540135
;

-- Column: M_ReceiptSchedule.BlockedBPartner
-- Source Table: C_BPartner_BlockStatus
-- 2023-02-24T13:52:59.751Z
UPDATE AD_SQLColumn_SourceTableColumn SET SQL_GetTargetRecordIdBySourceRecordId='SELECT rs.M_ReceiptSchedule_ID
from M_ReceiptSchedule rs
inner join c_bpartner_blockstatus bs on rs.c_bpartner_id = bs.c_bpartner_id
where bs.c_bpartner_blockstatus_id=@Record_ID@',Updated=TO_TIMESTAMP('2023-02-24 15:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540135
;

-- Column: M_ShipmentSchedule.BlockedBPartner
-- Source Table: C_BPartner_BlockStatus
-- 2023-02-24T13:55:47.156Z
UPDATE AD_SQLColumn_SourceTableColumn SET FetchTargetRecordsMethod='S', Source_Column_ID=586200, SQL_GetTargetRecordIdBySourceRecordId='SELECT ss.m_shipmentschedule_id
from m_shipmentschedule ss
inner join c_bpartner_blockstatus bs on ss.c_bpartner_id = bs.c_bpartner_id
where bs.c_bpartner_blockstatus_id=@Record_ID@',Updated=TO_TIMESTAMP('2023-02-24 15:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540134
;

-- Column: M_Delivery_Planning.BlockedBPartner
-- Source Table: C_BPartner_BlockStatus
-- 2023-02-24T13:57:55.814Z
UPDATE AD_SQLColumn_SourceTableColumn SET FetchTargetRecordsMethod='S', Source_Column_ID=586200, SQL_GetTargetRecordIdBySourceRecordId='SELECT dp.m_delivery_planning_id
from m_delivery_planning dp
inner join c_bpartner_blockstatus bs on dp.c_bpartner_id = bs.c_bpartner_id
where bs.c_bpartner_blockstatus_id=@Record_ID@',Updated=TO_TIMESTAMP('2023-02-24 15:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540136
;

