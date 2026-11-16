-- virtual column M_InOut.TrackingURL — carrier tracking link(s) for the shipment.
-- Nullable (no coalesce); the ' - ' display fallback belongs to the email-body rendering.
-- Reuses existing AD_Element_ID=2127 (ColumnName 'TrackingURL').
-- Idempotent guard: some customer DBs already carry an M_InOut.TrackingURL column
-- (added customer-side before this core feature). A plain INSERT collides with the
-- ad_column_name unique constraint (AD_Table_ID, ColumnName) and — under
-- ON_ERROR_STOP=1 in a single transaction — aborts the whole migration run, blocking
-- every later script. Insert only when the column is absent; on a DB that already has
-- it this script becomes a no-op (the existing column is left untouched).
INSERT INTO AD_Column (
  AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,
  AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,
  IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,
  IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,
  IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,
  IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version
)
SELECT
  0,592668 /*From ID Server*/,2127,0,10,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOut'),
  'N','TrackingURL',
  '(SELECT lp.TrackingURL FROM M_ShippingPackage p JOIN Carrier_ShipmentOrder cso ON cso.M_ShipperTransportation_ID = p.M_ShipperTransportation_ID JOIN Carrier_ShipmentOrder_Parcel lp ON lp.Carrier_ShipmentOrder_ID = cso.Carrier_ShipmentOrder_ID WHERE p.M_InOut_ID = M_InOut.M_InOut_ID LIMIT 1)',
  TO_TIMESTAMP('2026-06-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',2000,
  'Y','N','N','N','N','N',
  'N','N','N','N','N','N',
  'Y','N','N','N','N','N',
  'N','N','N','Tracking URL','NP',0,TO_TIMESTAMP('2026-06-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,0
WHERE NOT EXISTS (
  SELECT 1 FROM AD_Column
  WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOut')
    AND ColumnName = 'TrackingURL'
);
