-- Task Q14 fix round (delivery planning quantities): a shipping package with NO active
-- M_Delivery_Planning_Alloc behind it must show 0, not a blank cell.
--
-- 5822240 made all four quantity figures on M_ShippingPackage derived, each as a correlated subselect
-- through M_Delivery_Planning_Alloc. Every one of them requires an ACTIVE allocation - and not every
-- package has one: PurchaseOrderToShipperTransportationRepository#addPurchaseOrderToShipperTransportation
-- creates M_ShippingPackage rows straight off a purchase order, with no delivery planning and therefore
-- no allocation at all. For those rows the subselect returns no row, the ColumnSQL evaluates to NULL,
-- and the WebUI renders an empty cell where ActualLoadQty used to be a physical 0.
--
-- Verified on the local deep_tundra_uat stack: exactly five such active packages exist today
-- (1000105, 1000133, 1000161, 1000364, 1000421 - istobefetched='Y', c_orderline_id set, no active alloc),
-- and the pre-change backup of that table shows every one of them that pre-dates 5822230 carried
-- actualloadqty=0 / actualdischargequantity=0. They render on Transport Auftrag (540020) as well as on
-- Lieferanweisungen (541657).
--
-- So: coalesce to 0. It restores exactly what those rows displayed before this task, which keeps a task
-- about ADDING two planned figures from silently changing what the other two showed on rows it was never
-- about. Where an allocation DOES exist nothing changes - coalesce is transparent for a subselect that
-- returns a row, including one whose value is a real 0.
--
-- Still "exactly one value or NULL" (virtual-column rule 4): coalesce of a scalar subselect is a scalar.
-- IsLazyLoading and the AD_SQLColumn_SourceTableColumn wiring from 5822240 are untouched - this changes
-- only the expression.

-- ActualLoadQty (585497)
UPDATE AD_Column
SET ColumnSQL = 'coalesce((select dp.ActualLoadQty
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y''), 0)',
    Updated   = TO_TIMESTAMP('2026-09-03 11:20:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 585497;

-- ActualDischargeQuantity (585498)
UPDATE AD_Column
SET ColumnSQL = 'coalesce((select dp.ActualDischargeQuantity
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y''), 0)',
    Updated   = TO_TIMESTAMP('2026-09-03 11:20:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 585498;

-- PlannedLoadedQuantity (593470)
UPDATE AD_Column
SET ColumnSQL = 'coalesce((select dp.PlannedLoadedQuantity
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y''), 0)',
    Updated   = TO_TIMESTAMP('2026-09-03 11:20:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 593470;

-- PlannedDischargeQuantity (593471)
UPDATE AD_Column
SET ColumnSQL = 'coalesce((select dp.PlannedDischargeQuantity
     from M_Delivery_Planning_Alloc dpa
     join M_Delivery_Planning dp on dp.M_Delivery_Planning_ID = dpa.M_Delivery_Planning_ID
     where dpa.M_ShippingPackage_ID = M_ShippingPackage.M_ShippingPackage_ID
       and dpa.IsActive = ''Y''), 0)',
    Updated   = TO_TIMESTAMP('2026-09-03 11:20:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 593471;
