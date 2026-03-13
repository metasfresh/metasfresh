-- gh#28590: Add missing performance indexes for mobileUI (HU tables)
-- These indexes are critical for HU scan, pick, and move operations on large databases.

--
-- M_HU: every HU-by-warehouse query (scan, pick, move)
--
CREATE INDEX IF NOT EXISTS M_HU_M_Locator_ID ON M_HU (M_Locator_ID);

--
-- M_HU_QRCode_Assignment: QR code scan -> HU lookup (critical path for every scan operation)
-- Table may not exist on older seed DBs — guard with DO block
--
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'm_hu_qrcode_assignment') THEN
        CREATE INDEX IF NOT EXISTS M_HU_QRCode_Assignment_QRCode_ID ON M_HU_QRCode_Assignment (M_HU_QRCode_ID);
    END IF;
END $$;
