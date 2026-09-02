-- 2026-09-02
-- Delivery Planning: the two address columns become Search (30) instead of Table (18).
--
--   AD_Column 593414  M_Delivery_Planning.ShipFrom_Location_ID
--   AD_Column 593415  M_Delivery_Planning.ShipTo_Location_ID
--
-- Both were added by 5821150 with AD_Reference_ID=18 (Table) and IsSelectionColumn='Y'.
-- A Table reference PRELOADS the whole referenced list to render its widget; a Search
-- reference queries on typed input and preloads nothing. On a production instance with
-- ~80k business partners the preload makes the Delivery Planning window unusable as soon
-- as either address filter is opened. The scrambled local stack carries only a few hundred
-- C_BPartner_Location rows, which is why this never showed up in local testing.
--
-- Confirmed by the product owner: switching both columns to Search fixes the window.
--
-- Search is also what every sibling location column already uses, so this brings them into
-- line rather than inventing a shape:
--   M_ShipperTransportation.C_BPartner_Location_Loading_ID    ref=30, AD_Reference_Value_ID=159
--   M_ShipperTransportation.C_BPartner_Location_Delivery_ID   ref=30, AD_Reference_Value_ID=159
--   M_Delivery_Planning.C_BPartner_Location_ID                ref=30
--
-- AD_Reference_Value_ID stays 159 (C_BPartner Location) — correct for a Search reference as
-- well, per the two M_ShipperTransportation columns above — so it is deliberately not touched.
--
-- IsSelectionColumn stays 'Y': the filters are KEPT. LoadingAddress and DeliveryAddress are
-- two of the eight AggregationKeyField values (DeliveryPlanningList.AggregationKeyField), so a
-- planner filters on them to find plannings that can be combined into one delivery instruction.
-- Dropping the filters would have removed two of the eight dimensions that decide combinability.

UPDATE AD_Column
SET    AD_Reference_ID = 30,
       Updated         = TO_TIMESTAMP('2026-09-02', 'YYYY-MM-DD'),
       UpdatedBy       = 99
WHERE  AD_Column_ID IN (593414 /* ShipFrom_Location_ID */, 593415 /* ShipTo_Location_ID */)
AND    AD_Reference_ID <> 30
;
