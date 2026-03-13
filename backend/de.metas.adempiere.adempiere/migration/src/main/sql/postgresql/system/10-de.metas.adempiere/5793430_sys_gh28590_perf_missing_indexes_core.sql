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
-- API_Request_Audit: performance diagnostics (find slow API calls by time range)
--
CREATE INDEX IF NOT EXISTS API_Request_Audit_Created ON API_Request_Audit (Created);
CREATE INDEX IF NOT EXISTS API_Request_Audit_Path ON API_Request_Audit (Path) WHERE Path LIKE '/api/v2/%';
