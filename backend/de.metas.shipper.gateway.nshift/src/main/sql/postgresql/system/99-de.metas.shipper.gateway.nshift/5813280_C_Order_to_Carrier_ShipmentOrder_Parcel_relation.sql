-- Relation type: C_Order -> Carrier_ShipmentOrder_Parcel (References panel / Alt+6).
-- Lets a user jump from a sales order to the carrier shipment-order parcels of its shipments,
-- where the tracking URL lives (Carrier_ShipmentOrder_Parcel.TrackingURL).
-- Source: reuse of the standard C_Order SO-trx source reference (540666, IsSOTrx='Y').
-- Target: new reference -> Carrier_ShipmentOrder_Parcel window (541957 "Versandauftragspaket").
-- Navigation path: C_Order <- M_InOut <- M_ShippingPackage(M_Package_ID) -> Carrier_ShipmentOrder_Parcel.
-- The target window id is the BASE window; the WebUI resolves any customer override at navigation time
-- (DocumentZoomIntoInfo.overrideWindowIdIfPossible), so no override id is hard-coded here.

-- Target AD_Reference
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542116 /*From ID Server*/,TO_TIMESTAMP('2026-07-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Carrier_ShipmentOrder_Parcel_Target_For_C_Order',TO_TIMESTAMP('2026-07-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542116
AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Target AD_Ref_Table: Carrier_ShipmentOrder_Parcel filtered to the current order's parcels
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,AD_Window_ID,Created,CreatedBy,Updated,UpdatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,WhereClause)
VALUES (0,0,542116,542535,591136,541957,TO_TIMESTAMP('2026-07-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-07-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',
'exists
(
	select 1
	from M_ShippingPackage sp
	join M_InOut io on io.M_InOut_ID = sp.M_InOut_ID
	where
	io.C_Order_ID = @C_Order_ID/-1@ and Carrier_ShipmentOrder_Parcel.M_Package_ID = sp.M_Package_ID
)')
;

-- Relation type linking the reused C_Order SO source (540666) to the parcel target (542116)
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,Updated,UpdatedBy,EntityType,IsActive,Name,InternalName,IsTableRecordIDTarget,AD_Reference_Source_ID,AD_Reference_Target_ID)
VALUES (0,0,540500 /*From ID Server*/,TO_TIMESTAMP('2026-07-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-07-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','C_Order -> Carrier Shipment Order Parcel','C_Order_to_Carrier_ShipmentOrder_Parcel','N',540666,542116)
;
