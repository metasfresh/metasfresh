-- Review fixes for Package Licensing vendor info PR
-- Addresses review comments on https://github.com/metasfresh/metasfresh/pull/23395

-- ==========================================================================
-- Fix 1: Remove overly-generic AD_Messages that override existing AD_Elements
-- (ProductValue, ProductName, PurchaseQty, ForeignSalesQty, TotalSalesQty, MaterialType
--  already have proper AD_Element translations — our AD_Messages would override them globally)
-- ==========================================================================
DELETE FROM AD_Message_Trl WHERE AD_Message_ID IN (545650, 545651, 545652, 545653, 545654, 545655);
DELETE FROM AD_Message WHERE AD_Message_ID IN (545650, 545651, 545652, 545653, 545654, 545655);

-- ==========================================================================
-- Fix 2: Add NOT NULL constraint on IsPackageLicensingExempt
-- (backfill already done in 5797650, just enforce the constraint)
-- ==========================================================================
ALTER TABLE C_BPartner ALTER COLUMN IsPackageLicensingExempt SET NOT NULL;
