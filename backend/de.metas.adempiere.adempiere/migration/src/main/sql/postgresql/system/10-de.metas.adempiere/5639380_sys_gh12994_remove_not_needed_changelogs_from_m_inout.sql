--Disable changelog for Processed and DocAction columns of M_InOut
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 3518;
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 4324;
DELETE FROM AD_ChangeLog WHERE AD_Column_ID=3518;
DELETE FROM AD_ChangeLog WHERE AD_Column_ID=4324;