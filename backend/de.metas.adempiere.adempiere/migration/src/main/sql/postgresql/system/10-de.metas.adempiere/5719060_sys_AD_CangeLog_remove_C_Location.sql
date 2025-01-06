
-- the records were identified with "select * from ad_changelog_counts_v;"

DELETE FROM AD_ChangeLog WHERE AD_Column_ID IN (820/*delete C_Location.C_Country_ID AD_ChangeLog records */,
                                                813,/*delete C_Location.CreatedBy AD_ChangeLog records */
                                                812,/*delete C_Location.Created AD_ChangeLog records */
                                                810,/*delete C_Location.AD_Org_ID AD_ChangeLog records */
                                                809,/*delete C_Location.AD_Client_ID AD_ChangeLog records */
                                                808,/*delete C_Location.C_Location_ID AD_ChangeLog records */
                                                567902,/*delete C_Location.Geocoding_Issue_ID AD_ChangeLog records */
                                                567901,/*delete C_Location.GeocodingStatus AD_ChangeLog records */
                                                819,/*delete C_Location.City AD_ChangeLog records */
                                                822,/*delete C_Location.Postal AD_ChangeLog records */
                                                817,/*delete C_Location.Address1 AD_ChangeLog records */
                                                8214,/*delete C_Location.RegionName AD_ChangeLog records */
                                                818,/*delete C_Location.Address2 AD_ChangeLog records */
                                                62851,/*delete C_Location.POBox AD_ChangeLog records */
                                                12530/*delete C_Location.Address3 AD_ChangeLog records */);
-- [2024-03-12 09:36:27] 32,897,251 rows affected in 7 m 1 s 870 ms

UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 820; /* change AD_Column C_Location.C_Country_ID to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 813; /* change AD_Column C_Location.CreatedBy to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 812; /* change AD_Column C_Location.Created to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 810; /* change AD_Column C_Location.AD_Org_ID to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 809; /* change AD_Column C_Location.AD_Client_ID to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 808; /* change AD_Column C_Location.C_Location_ID to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 567902; /* change AD_Column C_Location.Geocoding_Issue_ID to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 567901; /* change AD_Column C_Location.GeocodingStatus to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 819; /* change AD_Column C_Location.City to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 822; /* change AD_Column C_Location.Postal to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 817; /* change AD_Column C_Location.Address1 to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 8214; /* change AD_Column C_Location.RegionName to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 818; /* change AD_Column C_Location.Address2 to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 62851; /* change AD_Column C_Location.POBox to not be AD_ChangeLog'ed anymore */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 12530; /* change AD_Column C_Location.Address3 to not be AD_ChangeLog'ed anymore */             
