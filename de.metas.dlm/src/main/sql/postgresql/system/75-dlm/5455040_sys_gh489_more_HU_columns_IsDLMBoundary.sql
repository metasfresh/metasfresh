--
-- DDL
--
select dlm.recreate_dlm_triggers('PP_Order_BOMLine') WHERE (select IsDLM from AD_Table WHERE TableName='PP_Order_BOMLine')='Y';
select dlm.recreate_dlm_triggers('M_Package_HU') WHERE (select IsDLM from AD_Table WHERE TableName='M_Package_HU')='Y';
select dlm.recreate_dlm_triggers('M_PickingSlot') WHERE (select IsDLM from AD_Table WHERE TableName='M_PickingSlot')='Y';
select dlm.recreate_dlm_triggers('M_PickingSlot_HU') WHERE (select IsDLM from AD_Table WHERE TableName='M_PickingSlot_HU')='Y';
select dlm.recreate_dlm_triggers('M_PickingSlot_Trx') WHERE (select IsDLM from AD_Table WHERE TableName='M_PickingSlot_Trx')='Y';
select dlm.recreate_dlm_triggers('EDI_DesadvLine') WHERE (select IsDLM from AD_Table WHERE TableName='EDI_DesadvLine')='Y';
select dlm.recreate_dlm_triggers('PP_Order_ProductAttribute') WHERE (select IsDLM from AD_Table WHERE TableName='PP_Order_ProductAttribute')='Y';
select dlm.recreate_dlm_triggers('DD_OrderLine_HU_Candidate') WHERE (select IsDLM from AD_Table WHERE TableName='DD_OrderLine_HU_Candidate')='Y';
select dlm.recreate_dlm_triggers('PP_Order') WHERE (select IsDLM from AD_Table WHERE TableName='PP_Order')='Y';
select dlm.recreate_dlm_triggers('M_ShipmentSchedule_QtyPicked') WHERE (select IsDLM from AD_Table WHERE TableName='M_ShipmentSchedule_QtyPicked')='Y';

--
-- DML
--
-- 03.01.2017 08:57
-- URL zum Konzept
-- "PP_Order_BOMLine"
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-03 08:57:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550972
;

-- 03.01.2017 13:12
-- URL zum Konzept
-- M_Package_HU
UPDATE AD_Column SET IsDLMPartitionBoundary='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-01-03 13:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549973
;



-- 04.01.2017 12:33
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549951
;

-- 04.01.2017 12:34
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549958
;

-- 04.01.2017 12:35
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550672
;

-- 04.01.2017 12:35
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551755
;

-- 04.01.2017 12:36
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552594
;

-- 04.01.2017 12:36
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552808
;

-- 04.01.2017 12:38
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550971
;

-- 04.01.2017 12:46
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551319
;
