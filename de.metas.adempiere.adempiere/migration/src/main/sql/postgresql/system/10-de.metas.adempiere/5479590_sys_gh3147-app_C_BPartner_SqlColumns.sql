--
-- fix dis SQL columns
-- * "where" needs to be lower-case
-- * the assumption that there would be just one IsBillToDefault bp_location was wrong..best practice is to always use some aggregate function
--
-- 2017-12-06T10:31:18.324
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select MAX(city) from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-12-06 10:31:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2017-12-06T10:31:26.836
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select MAX(address1) from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-12-06 10:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2017-12-06T10:31:40.010
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select MAX(postal) from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-12-06 10:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557179
;

-- 2017-12-06T10:31:51.502
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT MAX(Email) from AD_User u where u.C_BPartner_ID=C_BPartner.C_BPartner_ID AND u.IsActive=''Y'' AND u.IsDefaultContact=''Y'')',Updated=TO_TIMESTAMP('2017-12-06 10:31:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557181
;

-- 2017-12-06T10:32:34.763
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT MAX(u.AD_User_ID) from AD_User u where u.C_BPartner_ID=C_BPartner.C_BPartner_ID AND u.IsActive=''Y'' AND u.IsDefaultContact=''Y'')',Updated=TO_TIMESTAMP('2017-12-06 10:32:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546704
;

-- 2017-12-06T10:32:39.659
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select MAX(u.AD_User_ID) from AD_User u where u.C_BPartner_ID=C_BPartner.C_BPartner_ID AND u.IsActive=''Y'' AND u.IsDefaultContact=''Y'')',Updated=TO_TIMESTAMP('2017-12-06 10:32:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546704
;

-- 2017-12-06T10:33:05.076
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select MAX(bpl.C_BPartner_Location_ID) from  C_BPartner_Location bpl where bpl.C_BPartner_ID=C_BPartner.C_BPartner_ID AND bpl.IsActive=''Y'' AND bpl.IsShipToDefault=''Y'')',Updated=TO_TIMESTAMP('2017-12-06 10:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546706
;

-- 2017-12-06T10:33:24.298
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select MAX(bpl.C_BPartner_Location_ID) from C_BPartner_Location bpl where bpl.C_BPartner_ID=C_BPartner.C_BPartner_ID AND bpl.IsActive=''Y'' AND bpl.IsBillToDefault=''Y'')',Updated=TO_TIMESTAMP('2017-12-06 10:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546705
;

-- 2017-12-06T10:33:41.322
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select MAX(Email) from AD_User u where u.C_BPartner_ID=C_BPartner.C_BPartner_ID AND u.IsActive=''Y'' AND u.IsDefaultContact=''Y'')',Updated=TO_TIMESTAMP('2017-12-06 10:33:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557181
;

