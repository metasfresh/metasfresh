-- Run mode: SWING_CLIENT

-- gh#27441: Add AD_SQLColumn_SourceTableColumn entries for M_ShipmentSchedule virtual columns
-- that read from C_OrderLine. Without these, the WebUI won't refresh
-- InsufficientQtyAvailableForSalesColor_ID and QtyAvailableForSales
-- when the underlying C_OrderLine values change.

-- AD_SQLColumn_SourceTableColumn for M_ShipmentSchedule.InsufficientQtyAvailableForSalesColor_ID
-- Target: M_ShipmentSchedule (500221), Column: InsufficientQtyAvailableForSalesColor_ID (592027)
-- Source: C_OrderLine (260), Column: InsufficientQtyAvailableForSalesColor_ID (567583)
-- Link: C_OrderLine.C_OrderLine_ID (2205) -> M_ShipmentSchedule.C_OrderLine_ID
-- 2026-02-18T16:00:00.000Z
INSERT INTO AD_SQLColumn_SourceTableColumn
(AD_Client_ID, AD_Org_ID, AD_SQLColumn_SourceTableColumn_ID,
 AD_Table_ID, AD_Column_ID,
 Source_Table_ID, Source_Column_ID,
 Link_Column_ID, FetchTargetRecordsMethod,
 IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES
(0, 0, 540194,
 500221, 592027,
 260, 567583,
 2205, 'L',
 'Y', TO_TIMESTAMP('2026-02-18 16:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
 TO_TIMESTAMP('2026-02-18 16:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- AD_SQLColumn_SourceTableColumn for M_ShipmentSchedule.QtyAvailableForSales
-- Target: M_ShipmentSchedule (500221), Column: QtyAvailableForSales (592029)
-- Source: C_OrderLine (260), Column: QtyAvailableForSales (567584)
-- Link: C_OrderLine.C_OrderLine_ID (2205) -> M_ShipmentSchedule.C_OrderLine_ID
-- 2026-02-18T16:00:00.100Z
INSERT INTO AD_SQLColumn_SourceTableColumn
(AD_Client_ID, AD_Org_ID, AD_SQLColumn_SourceTableColumn_ID,
 AD_Table_ID, AD_Column_ID,
 Source_Table_ID, Source_Column_ID,
 Link_Column_ID, FetchTargetRecordsMethod,
 IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES
(0, 0, 540195,
 500221, 592029,
 260, 567584,
 2205, 'L',
 'Y', TO_TIMESTAMP('2026-02-18 16:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100,
 TO_TIMESTAMP('2026-02-18 16:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;
