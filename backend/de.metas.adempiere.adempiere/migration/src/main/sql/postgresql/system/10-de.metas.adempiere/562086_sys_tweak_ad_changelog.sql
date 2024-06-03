-- C_FlatrateTerm
DELETE FROM AD_ChangeLog WHERE AD_Column_ID=545809;/*delete C_Flatrate_Term.DocAction AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 545809; /* change AD_Column C_Flatrate_Term.DocAction to not be AD_ChangeLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=545811;/*delete C_Flatrate_Term.Processed AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 545811; /* change AD_Column C_Flatrate_Term.Processed to not be AD_ChangeLog'ed anymore */


-- C_Invoice
DELETE FROM AD_ChangeLog WHERE AD_Column_ID=3495;/*delete C_Invoice.DocAction AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 3495; /* change AD_Column C_Invoice.DocAction to not be AD_ChangeLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=3497;/*delete C_Invoice.Processed AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 3497; /* change AD_Column C_Invoice.Processed to not be AD_ChangeLog'ed anymore */
