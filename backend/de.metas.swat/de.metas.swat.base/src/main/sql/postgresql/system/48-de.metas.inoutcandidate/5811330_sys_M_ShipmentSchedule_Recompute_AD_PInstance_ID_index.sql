-- Index on M_ShipmentSchedule_Recompute.AD_PInstance_ID.
-- The recompute processors filter the queue by AD_PInstance_ID (claimed vs. unclaimed rows);
-- without a supporting index this scans the whole hot, high-churn queue.
-- IF NOT EXISTS: the index may already have been created live on some instances.

CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_Recompute_AD_PInstance_ID
    ON M_ShipmentSchedule_Recompute (AD_PInstance_ID)
;
