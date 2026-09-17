-- gh#28590: Add missing performance indexes for mobileUI (HU tables)

--
-- M_HU_QRCode_Assignment: QR code scan → HU lookup (critical path, every scan hits this)
-- 975K rows, 512M seq_tup_read on customer DB before adding this index
--
CREATE INDEX IF NOT EXISTS M_HU_QRCode_Assignment_QRCode_ID ON M_HU_QRCode_Assignment (M_HU_QRCode_ID);
