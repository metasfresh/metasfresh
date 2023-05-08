DROP INDEX IF EXISTS unique_vatcode_organization;

DELETE FROM AD_Index_Table_Trl WHERE AD_Index_Table_ID=540714;
DELETE FROM AD_Index_Column WHERE AD_Index_Table_ID=540714;
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540714;
