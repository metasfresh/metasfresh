-- gh#28590: Add missing performance indexes for mobileUI (picking tables)

--
-- M_Picking_Job: filter by status (442M seq_tup_read on customer DB before this index)
--
CREATE INDEX IF NOT EXISTS M_Picking_Job_DocStatus ON M_Picking_Job (DocStatus);

--
-- M_Picking_Job: find active jobs for a user (partial — only unprocessed jobs)
--
CREATE INDEX IF NOT EXISTS M_Picking_Job_Picking_User_ID ON M_Picking_Job (Picking_User_ID) WHERE Processed = 'N';

--
-- M_Picking_Job_Step: warehouse-based filtering and HU reverse lookup
-- 149M seq_tup_read on customer DB before these indexes
--
CREATE INDEX IF NOT EXISTS M_Picking_Job_Step_PickFrom_Warehouse_ID ON M_Picking_Job_Step (PickFrom_Warehouse_ID);
CREATE INDEX IF NOT EXISTS M_Picking_Job_Step_PickFrom_HU_ID ON M_Picking_Job_Step (PickFrom_HU_ID);
