-- 11.01.2017 15:39
-- URL zum Konzept
UPDATE AD_Tab SET OrderByClause='',Updated=TO_TIMESTAMP('2017-01-11 15:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540725
;

-- 11.01.2017 15:41
-- URL zum Konzept
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2017-01-11 15:41:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556654
;

-- 11.01.2017 15:41
-- URL zum Konzept
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2017-01-11 15:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556653
;


-- 11.01.2017 16:46
-- URL zum Konzept
UPDATE AD_Field SET SortNo=-3.000000000000,Updated=TO_TIMESTAMP('2017-01-11 16:46:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556657
;

-- NOTE: This column  shall not be set eny more since it is not working in webui.
-- It was exceptionally set for this task (#800) because there was no other option for the requirement to be accomplished.
-- For the order to be kept in webui, the sortNo values above are needed.

-- 11.01.2017 16:50
-- URL zum Konzept
UPDATE AD_Tab SET OrderByClause='(select p.Name from M_Product p where p.M_Product_ID=PMM_PurchaseCandidate.M_Product_ID) ASC,  (select bp.Name from C_BPartner bp where bp.C_BPartner_ID=PMM_PurchaseCandidate.C_BPartner_ID) ASC, DatePromised DESC',Updated=TO_TIMESTAMP('2017-01-11 16:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540725
;


