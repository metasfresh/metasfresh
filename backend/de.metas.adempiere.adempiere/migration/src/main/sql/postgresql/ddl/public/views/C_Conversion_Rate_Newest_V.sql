DROP VIEW IF EXISTS C_Conversion_Rate_Newest_V
;

-- Newest conversion rate per (client, org, from-currency, to-currency, conversion-type) combo.
-- ROW_NUMBER() partitions by the full combo and orders newest-ValidFrom first (PK as the deterministic
-- tie-breaker), so the newest row per combo carries newest_rn = 1. Consumers filter newest_rn = 1 to get
-- exactly one (the most recently valid) rate per combo in the DB, avoiding an unbounded all-rows load.
-- Single synthetic key = the underlying c_conversion_rate_id (the newest row's PK is unique per combo).
CREATE OR REPLACE VIEW C_Conversion_Rate_Newest_V AS
SELECT cr.C_Conversion_Rate_ID AS C_Conversion_Rate_Newest_V_ID,
       cr.C_Conversion_Rate_ID,
       cr.AD_Client_ID,
       cr.AD_Org_ID,
       cr.C_Currency_ID,
       cr.C_Currency_ID_To,
       cr.C_ConversionType_ID,
       cr.ValidFrom,
       cr.ValidTo,
       cr.MultiplyRate,
       cr.DivideRate,
       cr.IsActive,
       cr.Created,
       cr.CreatedBy,
       cr.Updated,
       cr.UpdatedBy,
       ROW_NUMBER() OVER (
           PARTITION BY cr.AD_Client_ID, cr.AD_Org_ID, cr.C_Currency_ID, cr.C_Currency_ID_To, cr.C_ConversionType_ID
           ORDER BY cr.ValidFrom DESC, cr.C_Conversion_Rate_ID DESC
       ) AS newest_rn
FROM C_Conversion_Rate cr
;
