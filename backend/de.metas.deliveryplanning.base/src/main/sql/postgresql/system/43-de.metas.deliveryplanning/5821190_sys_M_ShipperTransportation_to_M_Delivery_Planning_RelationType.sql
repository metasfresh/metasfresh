-- Related Documents: Delivery Instruction -> its ACTIVE Delivery Plannings, via
-- M_Delivery_Planning_Alloc. The reverse direction (planning -> instruction) already exists as
-- AD_RelationType 540381. Built like the in-tree precedent pair 540463 / 540468 for these same two
-- document tables: IsExplicit='N', a ValidationType='T' source reference plus a ValidationType='T'
-- target reference carrying the context-variable WhereClause.
--
-- The existing generic source reference 542013 is reused rather than duplicated. Neither relation gets
-- a source WhereClause: each target WhereClause already scopes itself by data, so a window or
-- document-type filter would hide true links. AD_Display stays NULL -- setting it adds an
-- ad_column_reftable_display FK that must stay valid on every instance, for no gain here.
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,ValidationType) VALUES
  (0,0,542134 /*From ID Server*/,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Delivery_Planning target for M_ShipperTransportation',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES
  (0,0,542134,542259,584986,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,
   'EXISTS (SELECT 1 FROM M_Delivery_Planning_Alloc dpa WHERE dpa.M_ShipperTransportation_ID = @M_ShipperTransportation_ID / -1@ AND dpa.IsActive=''Y'' AND M_Delivery_Planning.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID)')
;

INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsExplicit,IsTableRecordIDTarget,Name,InternalName,AD_Reference_Source_ID,AD_Reference_Target_ID,Updated,UpdatedBy) VALUES
  (0,0,540505 /*From ID Server*/,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','M_ShipperTransportation -> M_Delivery_Planning','M_ShipperTransportation_to_M_Delivery_Planning',542013,542134,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- M_ShipperTransportation.M_ShipperTransportation_ID needs IsGenericZoomOrigin='Y': without it
-- matchesAsSource() rejects the source before it ever looks at the reference's WhereClause, leaving
-- both this relation and the already-shipped 540468 as dead metadata. The flag is read only inside the
-- related-documents machinery, and the table has a single key column, so it resolves unambiguously.
UPDATE AD_Column
   SET IsGenericZoomOrigin='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=540426
;

-- ... and drop the generic duplicate this relation supersedes: with M_ShipperTransportation a valid
-- zoom origin, the References panel would list "Delivery Planning" twice -- once through this relation
-- and once as the generic FK-derived entry over M_Delivery_Planning.M_ShipperTransportation_ID. The
-- generic entry follows the legacy 1:1 pointer; this relation follows the active allocation, which is
-- the authoritative link once a planning can sit on more than one instruction. ad_table_related_windows_v
-- resolves the exclusion as COALESCE(NULLIF(AD_Field.IsExcludeFromZoomTargets,''),
-- AD_Column.IsExcludeFromZoomTargets), and the view keys the generic entry on this one direction, so
-- nothing else loses a zoom.
UPDATE AD_Column
   SET IsExcludeFromZoomTargets='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=585602
;
