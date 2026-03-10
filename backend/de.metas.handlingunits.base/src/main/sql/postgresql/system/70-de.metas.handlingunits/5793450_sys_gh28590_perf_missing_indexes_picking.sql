-- gh#28590: Add missing performance indexes for mobileUI (picking tables)
-- These indexes improve picking job listing and HU-to-step reverse lookups.
-- Tables may not exist on older seed DBs — guard with DO blocks.

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'm_picking_job') THEN
        --
        -- M_Picking_Job: draft job listing filter (picking launcher queries)
        -- DocStatus is used to filter drafted vs completed jobs
        --
        CREATE INDEX IF NOT EXISTS M_Picking_Job_DocStatus ON M_Picking_Job (DocStatus);
        CREATE INDEX IF NOT EXISTS M_Picking_Job_Picking_User_ID ON M_Picking_Job (Picking_User_ID) WHERE Processed = 'N';
    END IF;
END $$;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'm_picking_job_step') THEN
        --
        -- M_Picking_Job_Step: warehouse-based filtering and HU reverse lookup
        --
        CREATE INDEX IF NOT EXISTS M_Picking_Job_Step_PickFrom_Warehouse_ID ON M_Picking_Job_Step (PickFrom_Warehouse_ID);
        CREATE INDEX IF NOT EXISTS M_Picking_Job_Step_PickFrom_HU_ID ON M_Picking_Job_Step (PickFrom_HU_ID);
    END IF;
END $$;
