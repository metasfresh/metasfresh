
CREATE INDEX IF NOT EXISTS m_hu_item_snapshot_m_hu_id
  ON public.m_hu_item_snapshot
  USING btree
  (m_hu_id);
COMMENT ON INDEX m_hu_item_snapshot_m_hu_id IS 'Needed to support the implementation of AbstractSnapshotHandler.retrieveModelSnapshotsByParent();
Issue https://github.com/metasfresh/metasfresh/issues/2398';

CREATE INDEX IF NOT EXISTS m_hu_storage_snapshot_m_hu_id
  ON public.m_hu_storage_snapshot
  USING btree
  (m_hu_id);
COMMENT ON INDEX m_hu_storage_snapshot_m_hu_id IS 'Needed to support the implementation of AbstractSnapshotHandler.retrieveModelSnapshotsByParent();
Issue https://github.com/metasfresh/metasfresh/issues/2398';

CREATE INDEX IF NOT EXISTS m_hu_item_storage_snapshot_m_hu_item_id
  ON public.m_hu_item_storage_snapshot
  USING btree
  (m_hu_item_id);
COMMENT ON INDEX m_hu_item_storage_snapshot_m_hu_item_id IS 'Needed to support the implementation of AbstractSnapshotHandler.retrieveModelSnapshotsByParent();
Issue https://github.com/metasfresh/metasfresh/issues/2398';
