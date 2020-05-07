--
-- "C_Order -> C_Flatrate_Term"..rename to C_Flatrate_Term_For_C_OrderLine and change it accordingly..this should also work, with the webui-api
--
-- 2017-12-06T16:31:27.659
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_Flatrate_Term_For_C_OrderLine',Updated=TO_TIMESTAMP('2017-12-06 16:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;

-- 2017-12-06T16:40:39.686
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='C_OrderLine_Term_ID = @C_OrderLine_ID/-1@',Updated=TO_TIMESTAMP('2017-12-06 16:40:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;


-- 2017-12-06T16:34:00.608
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_OrderLine_C_Flatrate_Term',Updated=TO_TIMESTAMP('2017-12-06 16:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-06T16:37:31.372
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=NULL, AD_Key=2205, AD_Table_ID=260, WhereClause='C_Flatrate_Conditions_ID IS NOT NULL',Updated=TO_TIMESTAMP('2017-12-06 16:37:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-06T16:37:49.542
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_OrderLine_with_C_Flatrate_Conditions_ID',Updated=TO_TIMESTAMP('2017-12-06 16:37:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540754
;

-- 2017-12-06T16:38:38.817
-- URL zum Konzept
UPDATE AD_RelationType SET IsActive='Y', Name='C_OrderLine -> C_Flatrate_Term',Updated=TO_TIMESTAMP('2017-12-06 16:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540191
;



-- 
-- C_Order_ID sql column in MD_Candidate..needs to be changed to a physical column
--
-- 2017-12-06T14:57:54.738
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='NULL',Updated=TO_TIMESTAMP('2017-12-06 14:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557892
;

-- 2017-12-06T15:33:20.541
-- URL zum Konzept
--
-- C_Order -> C_Invoice_Candidate
-- the target reference where clause was still aiming at bi-directions relation types and was a total perf-mightmare, just so that it could also work as *source*
--
UPDATE AD_Ref_Table SET WhereClause='C_Order_ID = @C_Order_ID/-1@',Updated=TO_TIMESTAMP('2017-12-06 15:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540760
;

