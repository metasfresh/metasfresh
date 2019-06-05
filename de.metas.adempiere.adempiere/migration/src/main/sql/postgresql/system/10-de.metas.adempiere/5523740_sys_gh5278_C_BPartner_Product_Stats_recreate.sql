-- Recreating it because we might have duplicate M_Product_ID, C_BPartner_ID records.
-- The next migration script will enforce an unique index on M_Product_ID, C_BPartner_ID.
select C_BPartner_Product_Stats_refresh();
