-- Index on M_ShipmentSchedule_Recompute.ChunkUUID.
-- The recompute processors query this table with WHERE ChunkUUID=?; the column was
-- added without a supporting index, forcing sequential scans on a hot, high-churn queue.
-- IF NOT EXISTS: the index may already have been created live on some instances.

CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_Recompute_ChunkUUID
    ON M_ShipmentSchedule_Recompute (ChunkUUID)
;
