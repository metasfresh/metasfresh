-- 1. Target AD_Reference (ValidationType 'T') ---------------------------------
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542140,TO_TIMESTAMP('2026-09-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Carrier_ShipmentOrder_Parcel_Target_For_M_ShipmentSchedule',TO_TIMESTAMP('2026-09-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542140
AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2. Target AD_Ref_Table: Carrier_ShipmentOrder_Parcel, filtered to the -------
--    parcels of the shipment(s) the selected shipment schedule was shipped on.
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,AD_Window_ID,Created,CreatedBy,Updated,UpdatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,WhereClause)
VALUES (0,0,542140,542535,591136,541957,TO_TIMESTAMP('2026-09-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',
'exists
(
	select 1
	from M_ShipmentSchedule_QtyPicked qp
	join M_InOutLine iol on iol.M_InOutLine_ID = qp.M_InOutLine_ID
	join M_ShippingPackage sp on sp.M_InOut_ID = iol.M_InOut_ID
	where
	qp.M_ShipmentSchedule_ID = @M_ShipmentSchedule_ID/-1@
	and Carrier_ShipmentOrder_Parcel.M_Package_ID = sp.M_Package_ID
)')
;

-- 3. Relation type: reused M_ShipmentSchedule source (540553) -> parcel target (542140)
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,Updated,UpdatedBy,EntityType,IsActive,Name,InternalName,IsTableRecordIDTarget,AD_Reference_Source_ID,AD_Reference_Target_ID)
VALUES (0,0,540507,TO_TIMESTAMP('2026-09-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-10 12:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','M_ShipmentSchedule -> Carrier Shipment Order Parcel','M_ShipmentSchedule_to_Carrier_ShipmentOrder_Parcel','N',540553,542140)
;

-- 4. Index coverage:
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_QtyPicked_ShipmentSchedule ON M_ShipmentSchedule_QtyPicked (M_ShipmentSchedule_ID);
CREATE INDEX IF NOT EXISTS M_ShippingPackage_InOut                       ON M_ShippingPackage (M_InOut_ID);
CREATE INDEX IF NOT EXISTS Carrier_ShipmentOrder_Parcel_Package          ON Carrier_ShipmentOrder_Parcel (M_Package_ID);
