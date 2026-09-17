-- 2026-04-25 https://github.com/metasfresh/me03/issues/29368
-- Allow OrderPayScheduleLCService.recomputeLCStep to write DueAmt_Actual.
-- Original migration 5799510 set IsUpdateable='N' + ReadOnlyLogic='1=1' so that
-- end users cannot edit the value via the UI. But the authority function uses
-- InterfaceWrapperHelper which goes through PO's IsUpdateable check and is
-- blocked. We make the column updateable at the AD level (so PO accepts writes
-- from the service) and keep the read-only behavior in the UI via the AD_Field
-- IsReadOnly='Y' setting from migration 5799510.

UPDATE AD_Column
SET IsUpdateable = 'Y',
    Updated      = '2026-04-25 03:00',
    UpdatedBy    = 0
WHERE AD_Column_ID = 592416 /*From ID Server, see migration 5799510*/
  AND ColumnName   = 'DueAmt_Actual';
