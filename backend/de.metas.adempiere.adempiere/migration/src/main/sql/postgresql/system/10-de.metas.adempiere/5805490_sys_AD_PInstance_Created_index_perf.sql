-- Performance: add an index on AD_PInstance.Created matching the Process-Instances
-- window default ordering (Created DESC NULLS LAST, AD_PInstance_ID ASC).
-- AD_PInstance is the process/report execution log and grows very large; without this
-- index the window's view-selection does a full scan + external on-disk sort of the whole
-- table just to take the most-recent rows, making the window extremely slow to open.
-- NULLS LAST must match the window ORDER BY exactly, otherwise the planner ignores the
-- index and still sorts. IF NOT EXISTS keeps it a no-op where already present.
-- Note: on a very large AD_PInstance this index build locks writes briefly during deploy.
CREATE INDEX IF NOT EXISTS ad_pinstance_created
    ON ad_pinstance (Created DESC NULLS LAST, AD_PInstance_ID);
