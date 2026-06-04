-- virtual column M_InOut.TrackingURL — carrier tracking link(s) for the shipment.
-- Nullable (no coalesce); the ' - ' display fallback belongs to the email-body rendering.
-- Reuses existing AD_Element_ID=2127 (ColumnName 'TrackingURL').
INSERT INTO AD_Column (
  AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,
  AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,
  IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,
  IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,
  IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,
  IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version
) VALUES (
  0,592668 /*From ID Server*/,2127,0,10,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOut'),
  'N','TrackingURL',
  '(SELECT lp.TrackingURL FROM M_ShippingPackage p JOIN Carrier_ShipmentOrder cso ON cso.M_ShipperTransportation_ID = p.M_ShipperTransportation_ID JOIN Carrier_ShipmentOrder_Parcel lp ON lp.Carrier_ShipmentOrder_ID = cso.Carrier_ShipmentOrder_ID WHERE p.M_InOut_ID = M_InOut.M_InOut_ID AND p.IsActive=''Y'' AND cso.IsActive=''Y'' AND lp.IsActive=''Y'' ORDER BY lp.Carrier_ShipmentOrder_Parcel_ID LIMIT 1)',
  TO_TIMESTAMP('2026-06-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',2000,
  'Y','N','N','N','N','N',
  'N','N','N','N','N','N',
  'Y','N','N','N','N','N',
  'N','N','N','Tracking URL','NP',0,TO_TIMESTAMP('2026-06-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,0
);
