-- Column: Fact_Acct_Transactions_View.C_Harvesting_Calendar_ID
-- 2023-08-29T06:31:52.688957600Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-29 09:31:52.688','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587326
;

-- Name: C_Year(harvesting)
-- 2023-08-29T06:32:42.297478700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541816,TO_TIMESTAMP('2023-08-29 09:32:42.175','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','C_Year(harvesting)',TO_TIMESTAMP('2023-08-29 09:32:42.175','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-08-29T06:32:42.302759100Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541816 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: C_Year(harvesting)
-- 2023-08-29T06:34:43.961300100Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541816
;

-- 2023-08-29T06:34:43.969824700Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541816
;

-- Column: C_Year.FiscalYear
-- 2023-08-29T06:35:39.220049700Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-29 09:35:39.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=1038
;

-- Column: C_Year.C_Calendar_ID
-- 2023-08-29T06:36:13.449442600Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2023-08-29 09:36:13.449','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=1042
;

-- Column: C_Year.FiscalYear
-- 2023-08-29T06:37:06.571017400Z
UPDATE AD_Column SET SeqNo=2,Updated=TO_TIMESTAMP('2023-08-29 09:37:06.57','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=1038
;

-- Column: C_Year.C_Calendar_ID
-- 2023-08-29T06:37:19.184455900Z
UPDATE AD_Column SET IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2023-08-29 09:37:19.184','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=1042
;
