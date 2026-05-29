-- Helper function: merges billing partner name with delivery recipient name
-- for revenue report display (gh#28121).
--
-- Formats:
--   Same or no delivery recipient: "BillingPartner"
--   Different delivery recipient:  "BillingPartner (DeliveryRecipient)"
--   Too long (>45 chars):          "BillingPartn… (DeliveryRec…)"
--
-- Max output: 45 chars (fits varchar(60) column budget with room to spare).
CREATE OR REPLACE FUNCTION report._merge_bp_name(p_billing_bp text, p_delivery_bp text)
RETURNS text AS
$$
SELECT CASE
    WHEN p_billing_bp IS NULL THEN p_delivery_bp
    WHEN p_delivery_bp IS NULL OR p_delivery_bp = p_billing_bp
    THEN CASE WHEN length(p_billing_bp) <= 45 THEN p_billing_bp ELSE left(p_billing_bp, 44) || '…' END
    WHEN length(p_billing_bp) + length(p_delivery_bp) + 3 <= 45
    THEN p_billing_bp || ' (' || p_delivery_bp || ')'
    ELSE left(p_billing_bp, 24) || '… (' || left(p_delivery_bp, 14) || '…)'
END
$$
LANGUAGE sql IMMUTABLE;
