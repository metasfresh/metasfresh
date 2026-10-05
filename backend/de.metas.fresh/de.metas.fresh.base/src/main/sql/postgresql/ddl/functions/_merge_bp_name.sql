-- Helper function: merges billing partner name with delivery recipient name
-- for revenue report display (gh#28121).
--
-- Formats:
--   Same or no delivery recipient: "BillingPartner"
--   Different delivery recipient:  "BillingPartner (DeliveryRecipient)"
--   Too long (>45 chars):          "BillingPartn… (DeliveryRec…)"
--
-- Max output: 45 chars (fits varchar(60) column budget with room to spare).
CREATE OR REPLACE FUNCTION report._merge_bp_name(billing_bp text, delivery_bp text)
RETURNS text AS
$$
SELECT CASE
    WHEN delivery_bp IS NULL OR delivery_bp = billing_bp
    THEN CASE WHEN length(billing_bp) <= 45 THEN billing_bp ELSE left(billing_bp, 44) || '…' END
    WHEN length(billing_bp) + length(delivery_bp) + 3 <= 45
    THEN billing_bp || ' (' || delivery_bp || ')'
    ELSE left(billing_bp, 24) || '… (' || left(delivery_bp, 14) || '…)'
END
$$
LANGUAGE sql IMMUTABLE;
