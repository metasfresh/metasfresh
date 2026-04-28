-- gh#28590: Add missing performance indexes for mobileUI (core tables)

--
-- DD_Order: distribution launchers filter by DocStatus
--
CREATE INDEX IF NOT EXISTS DD_Order_DocStatus ON DD_Order (DocStatus);

--
-- DD_OrderLine: child-to-parent navigation
--
CREATE INDEX IF NOT EXISTS DD_OrderLine_DD_Order_ID ON DD_OrderLine (DD_Order_ID);

--
-- M_ShipmentSchedule: picking launchers filter by warehouse
--
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_M_Warehouse_ID ON M_ShipmentSchedule (M_Warehouse_ID);

--
-- M_ShipmentSchedule: filter unprocessed active schedules (high selectivity: ~2K of 1M rows)
--
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_Processed_IsActive ON M_ShipmentSchedule (Processed, IsActive) WHERE Processed = 'N' AND IsActive = 'Y';

--
-- API_Request_Audit: performance diagnostics (find slow API calls by time range)
--
CREATE INDEX IF NOT EXISTS API_Request_Audit_Created ON API_Request_Audit (Created);
CREATE INDEX IF NOT EXISTS API_Request_Audit_Path ON API_Request_Audit (Path) WHERE Path LIKE '/api/v2/%';
