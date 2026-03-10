-- gh#28590: Add missing performance indexes for mobileUI picking flow (core tables)
-- These indexes are critical for large databases where the mobileUI becomes slow.

--
-- M_ShipmentSchedule: picking launchers query by product, warehouse, partner, and processed status
--
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_M_Product_ID ON M_ShipmentSchedule (M_Product_ID);
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_M_Warehouse_ID ON M_ShipmentSchedule (M_Warehouse_ID);
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_C_BPartner_ID ON M_ShipmentSchedule (C_BPartner_ID);
CREATE INDEX IF NOT EXISTS M_ShipmentSchedule_Processed_IsActive ON M_ShipmentSchedule (Processed, IsActive) WHERE Processed = 'N' AND IsActive = 'Y';

--
-- DD_Order: distribution launchers filter by DocStatus
--
CREATE INDEX IF NOT EXISTS DD_Order_DocStatus ON DD_Order (DocStatus);

--
-- DD_OrderLine: child-to-parent navigation
--
CREATE INDEX IF NOT EXISTS DD_OrderLine_DD_Order_ID ON DD_OrderLine (DD_Order_ID);

--
-- C_Queue_WorkPackage: async processing polling performance
-- The polling query (FOR UPDATE SKIP LOCKED) filters by Processed, IsError, IsReadyForProcessing
--
CREATE INDEX IF NOT EXISTS C_Queue_WorkPackage_ReadyToProcess
    ON C_Queue_WorkPackage (Priority, Created)
    WHERE Processed = 'N' AND IsError = 'N' AND IsReadyForProcessing = 'Y';

--
-- API_Request_Audit: performance diagnostics (find slow API calls by time range)
--
CREATE INDEX IF NOT EXISTS API_Request_Audit_Created ON API_Request_Audit (Created);
CREATE INDEX IF NOT EXISTS API_Request_Audit_Path ON API_Request_Audit (Path) WHERE Path LIKE '/api/v2/%';
