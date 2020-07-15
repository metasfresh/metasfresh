-------------------- ad relation for bankstatement line -----------------------

-- 31.08.2015 17:41:57
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540539,540538,540113,TO_TIMESTAMP('2015-08-31 17:41:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','BankStatement <-> C_AllocationLine',TO_TIMESTAMP('2015-08-31 17:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.08.2015 17:42:41
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540549,TO_TIMESTAMP('2015-08-31 17:42:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_AllocationLine_for_C_BankStatement',TO_TIMESTAMP('2015-08-31 17:42:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 31.08.2015 17:42:41
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540549 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 31.08.2015 18:16:42
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,5494,12331,0,540549,390,TO_TIMESTAMP('2015-08-31 18:16:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-08-31 18:16:42','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS (SELECT 1 FROM ESR_ImportLine esrl WHERE esrl.C_Payment_ID=C_Payment.C_Payment_ID and esrl.C_Payment_ID = @C_Payment_ID@)')
;

-- 31.08.2015 18:16:54
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
EXISTS (SELECT 1 FROM C_BankStatementLine_Ref bslr  
left outer join C_BankStatementLine bsl on (bsl.C_BankStatementLine_ID=bslr.C_BankStatementLine_ID) 
left outer join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) WHERE bslr.C_Payment_ID=@C_Payment_ID@ and bs.DocStatus IN (''''CO'''', ''''CL''''))',Updated=TO_TIMESTAMP('2015-08-31 18:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540549
;

-- 31.08.2015 18:17:07
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540549,Updated=TO_TIMESTAMP('2015-08-31 18:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540113
;

-- 31.08.2015 18:17:57
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540550,TO_TIMESTAMP('2015-08-31 18:17:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_BankStatement for C_AllocationLine',TO_TIMESTAMP('2015-08-31 18:17:57','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 31.08.2015 18:17:58
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540550 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 31.08.2015 23:15:41
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,541645,0,540550,392,TO_TIMESTAMP('2015-08-31 23:15:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-08-31 23:15:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.08.2015 23:17:24
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2015-08-31 23:17:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540113
;

-- 31.08.2015 23:25:41
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,53331,541113,TO_TIMESTAMP('2015-08-31 23:25:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Zuordnung',TO_TIMESTAMP('2015-08-31 23:25:40','YYYY-MM-DD HH24:MI:SS'),100,'C_AllocationLine','Zuordnung')
;

-- 31.08.2015 23:25:41
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541113 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 31.08.2015 23:26:25
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,53331,541114,TO_TIMESTAMP('2015-08-31 23:26:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Bankauszug',TO_TIMESTAMP('2015-08-31 23:26:25','YYYY-MM-DD HH24:MI:SS'),100,'C_BankStatement','Bankauszug')
;

-- 31.08.2015 23:26:25
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541114 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 31.08.2015 23:27:11
-- URL zum Konzept
UPDATE AD_RelationType SET Role_Source='C_AllocationLine',Updated=TO_TIMESTAMP('2015-08-31 23:27:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540113
;

-- 31.08.2015 23:27:30
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540550, Role_Target='C_BankStatement',Updated=TO_TIMESTAMP('2015-08-31 23:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540113
;

-- 31.08.2015 23:30:07
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='
EXISTS (SELECT 1 FROM C_BankStatementLine_Ref bslr  
left outer join C_BankStatementLine bsl on (bsl.C_BankStatementLine_ID=bslr.C_BankStatementLine_ID) 
left outer join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) WHERE bslr.C_Payment_ID=@C_Payment_ID@ and bs.DocStatus IN (''CO'', ''CL''))',Updated=TO_TIMESTAMP('2015-08-31 23:30:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540549
;

-- 31.08.2015 23:32:43
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540550, AD_Reference_Target_ID=540549,Updated=TO_TIMESTAMP('2015-08-31 23:32:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540113
;

-- 31.08.2015 23:36:22
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 FROM c_allocationline  WHERE C_BankStatementLine.C_Payment_ID=@C_Payment_ID@ and C_BankStatementLine.C_Payment_ID = al.C_Payment_ID)',Updated=TO_TIMESTAMP('2015-08-31 23:36:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:38:25
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 FROM C_BankStatementLine bsl 
	left outer join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) 
	WHERE bsl.C_Payment_ID=@C_Payment_ID@ and bs.DocStatus IN (''CO'', ''CL'') and C_AllocationLine.C_Payment_ID = bsl.C_Payment_ID)',Updated=TO_TIMESTAMP('2015-08-31 23:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540549
;

-- 31.08.2015 23:39:07
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 FROM c_allocationline  al WHERE C_BankStatementLine.C_Payment_ID=@C_Payment_ID@ and C_BankStatementLine.C_Payment_ID = al.C_Payment_ID)',Updated=TO_TIMESTAMP('2015-08-31 23:39:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:39:50
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=4937, AD_Table_ID=393,Updated=TO_TIMESTAMP('2015-08-31 23:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:40:45
-- URL zum Konzept
UPDATE AD_RelationType SET Role_Source='C_BankStatement', Role_Target='C_AllocationLine',Updated=TO_TIMESTAMP('2015-08-31 23:40:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540113
;

-- 31.08.2015 23:41:28
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_BankStatementLine for C_AllocationLine',Updated=TO_TIMESTAMP('2015-08-31 23:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:41:28
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:41:52
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_AllocationLine_for_C_BankStatementLine',Updated=TO_TIMESTAMP('2015-08-31 23:41:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540549
;

-- 31.08.2015 23:41:52
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540549
;

-- 31.08.2015 23:42:13
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 FROM C_BankStatementLine bsl 
	join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) 
	WHERE bsl.C_Payment_ID=@C_Payment_ID@ and bs.DocStatus IN (''CO'', ''CL'') and C_AllocationLine.C_Payment_ID = bsl.C_Payment_ID)',Updated=TO_TIMESTAMP('2015-08-31 23:42:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540549
;

-- 31.08.2015 23:42:45
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2015-08-31 23:42:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540549
;

-- 31.08.2015 23:43:10
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=4926,Updated=TO_TIMESTAMP('2015-08-31 23:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:43:20
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=4937,Updated=TO_TIMESTAMP('2015-08-31 23:43:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:43:28
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Display=4884,Updated=TO_TIMESTAMP('2015-08-31 23:43:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540549
;


-------------------- ad relation for bankstatement line reference -----------------------




-- 31.08.2015 23:48:19
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,540550,540549,540114,TO_TIMESTAMP('2015-08-31 23:48:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','BankStatementLineRef <-> C_AllocationLine','C_BankStatement','C_AllocationLine',TO_TIMESTAMP('2015-08-31 23:48:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.08.2015 23:48:27
-- URL zum Konzept
UPDATE AD_RelationType SET Name='BankStatementLine <-> C_AllocationLine',Updated=TO_TIMESTAMP('2015-08-31 23:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540113
;

-- 31.08.2015 23:48:50
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_BankStatementLineRef for C_AllocationLine',Updated=TO_TIMESTAMP('2015-08-31 23:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:48:50
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:48:57
-- URL zum Konzept
UPDATE AD_Reference SET Name='C_BankStatementLine for C_AllocationLine',Updated=TO_TIMESTAMP('2015-08-31 23:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:48:57
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540550
;

-- 31.08.2015 23:49:08
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540551,TO_TIMESTAMP('2015-08-31 23:49:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_BankStatementLineRef for C_AllocationLine',TO_TIMESTAMP('2015-08-31 23:49:07','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 31.08.2015 23:49:08
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540551 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 31.08.2015 23:50:01
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,54388,54395,0,540551,53065,TO_TIMESTAMP('2015-08-31 23:50:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-08-31 23:50:01','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS (SELECT 1 FROM c_allocationline  al WHERE C_BankStatementLine_Ref.C_Payment_ID=@C_Payment_ID@ and C_BankStatementLine_Ref.C_Payment_ID = al.C_Payment_ID)')
;

-- 31.08.2015 23:50:12
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Source_ID=540551,Updated=TO_TIMESTAMP('2015-08-31 23:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540114
;

-- 31.08.2015 23:50:31
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540552,TO_TIMESTAMP('2015-08-31 23:50:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_AllocationLine_for_C_BankStatementLine_Ref',TO_TIMESTAMP('2015-08-31 23:50:31','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 31.08.2015 23:50:31
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540552 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 31.08.2015 23:53:24
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,4884,12331,0,540552,390,TO_TIMESTAMP('2015-08-31 23:53:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-08-31 23:53:24','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS (SELECT 1 FROM C_BankStatementLine_Ref bslr 
	join C_BankStatementLine bsl on (bslC_BankStatementLine_ID=bslr.C_BankStatementLine_ID) 
	join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) 
	WHERE bslr.C_Payment_ID=@C_Payment_ID@ and bs.DocStatus IN (''CO'', ''CL'') and C_AllocationLine.C_Payment_ID = bslr.C_Payment_ID)')
;

-- 31.08.2015 23:53:37
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=540552,Updated=TO_TIMESTAMP('2015-08-31 23:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540114
;

-- 31.08.2015 23:58:00
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1 FROM C_BankStatementLine_Ref bslr 
	join C_BankStatementLine bsl on (bsl.C_BankStatementLine_ID=bslr.C_BankStatementLine_ID) 
	join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) 
	WHERE bslr.C_Payment_ID=@C_Payment_ID@ and bs.DocStatus IN (''CO'', ''CL'') and C_AllocationLine.C_Payment_ID = bslr.C_Payment_ID)',Updated=TO_TIMESTAMP('2015-08-31 23:58:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540552
;

