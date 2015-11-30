
-- make M_Material_Tracking_ID autocomplete everywhere
-- 25.11.2015 14:18
-- URL zum Konzept
UPDATE AD_Table SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2015-11-25 14:18:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540610
;

--M_Material_Tracking.QtyIssued and QtyReceived
-- 25.11.2015 14:05
-- URL zum Konzept
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2015-11-25 14:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552751
;
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2015-11-25 14:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551173
;

