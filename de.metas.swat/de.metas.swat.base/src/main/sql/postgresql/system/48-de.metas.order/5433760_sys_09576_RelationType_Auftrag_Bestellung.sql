
-- 18.11.2015 18:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540141,TO_TIMESTAMP('2015-11-18 18:44:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Order(PO)-> C_Order(SO)',TO_TIMESTAMP('2015-11-18 18:44:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.11.2015 18:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540609,TO_TIMESTAMP('2015-11-18 18:44:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order(PO) -> C_Order(SO)',TO_TIMESTAMP('2015-11-18 18:44:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 18.11.2015 18:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540609 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.11.2015 18:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540609,259,181,TO_TIMESTAMP('2015-11-18 18:45:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-18 18:45:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.11.2015 18:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = po.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 18:49:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540609
;

-- 18.11.2015 18:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540609,Updated=TO_TIMESTAMP('2015-11-18 18:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540141
;

-- 18.11.2015 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540610,TO_TIMESTAMP('2015-11-18 18:50:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Order (PO) -> C_Order (SO)',TO_TIMESTAMP('2015-11-18 18:50:05','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 18.11.2015 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540610 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 18.11.2015 18:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540610,259,143,TO_TIMESTAMP('2015-11-18 18:50:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-18 18:50:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.11.2015 18:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
(
select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = po.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 18:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540610
;

-- 18.11.2015 18:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540610,Updated=TO_TIMESTAMP('2015-11-18 18:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540141
;

-- 18.11.2015 18:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
(
select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = po.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @Link_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 18:54:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540610
;

-- 18.11.2015 18:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = po.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @Link_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 18:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540609
;

-- 18.11.2015 18:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = so.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @Link_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 18:57:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540609
;

-- 18.11.2015 18:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
(
select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = so.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 18:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540610
;

-- 18.11.2015 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = so.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 18:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540610
;

-- 18.11.2015 19:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
select 1
	from C_Order so
	join C_Order po on  so.C_Order_ID = po.Link_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		( so.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 19:07:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540610
;

-- 18.11.2015 19:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
select 1
	from C_Order so
	join C_Order po on  so.C_Order_ID = po.Link_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 19:08:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540610
;

-- 18.11.2015 19:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1
	from C_Order po
	join C_Order so on po.Link_Order_ID = so.C_Order_ID 
	
	where
		po.isSoTrx = ''N'' and so.IsSoTrx = ''Y'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		( po.C_Order_ID = @C_Order_ID/-1@ or so.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-18 19:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540609
;

--- above no longer valid. Changed the logic and deleted the rel type above


-- 20.11.2015 11:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540142,TO_TIMESTAMP('2015-11-20 11:57:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Order(SO)-> C_Order(PO)',TO_TIMESTAMP('2015-11-20 11:57:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.11.2015 12:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540611,TO_TIMESTAMP('2015-11-20 12:25:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order_DO -> C_Order_PO',TO_TIMESTAMP('2015-11-20 12:25:56','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 20.11.2015 12:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540611 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 20.11.2015 12:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540611,259,143,TO_TIMESTAMP('2015-11-20 12:26:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-20 12:26:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.11.2015 12:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on o.C_Order_ID = sol.C_Order_ID and s.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 12:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540611
;

-- 20.11.2015 12:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order_SO -> C_Order_PO',Updated=TO_TIMESTAMP('2015-11-20 12:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540611
;

-- 20.11.2015 12:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540611
;

-- 20.11.2015 12:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540612,TO_TIMESTAMP('2015-11-20 12:31:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType C_Order_SO -> C_Order_PO',TO_TIMESTAMP('2015-11-20 12:31:24','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 20.11.2015 12:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540612 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 20.11.2015 12:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540612,259,181,TO_TIMESTAMP('2015-11-20 12:31:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-20 12:31:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.11.2015 12:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on o.C_Order_ID = sol.C_Order_ID and s.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 12:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540612
;

-- 20.11.2015 12:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540611, AD_Reference_Target_ID=540612,Updated=TO_TIMESTAMP('2015-11-20 12:33:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540142
;

-- 20.11.2015 12:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Help='
exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on o.C_Order_ID = sol.C_Order_ID and s.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 12:49:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540612
;

-- 20.11.2015 12:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540612
;

-- 20.11.2015 12:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540141
;

-- 20.11.2015 12:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-11-20 12:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540012
;

-- 20.11.2015 12:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Help='
exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on so.C_Order_ID = sol.C_Order_ID and s.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 12:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540612
;

-- 20.11.2015 12:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540612
;

-- 20.11.2015 12:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on so.C_Order_ID = sol.C_Order_ID and s.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 12:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540611
;

-- 20.11.2015 12:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on so.C_Order_ID = sol.C_Order_ID and so.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = so.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 12:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540611
;

-- 20.11.2015 12:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Help='
exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on so.C_Order_ID = sol.C_Order_ID and so.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 12:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540612
;

-- 20.11.2015 12:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540612
;

-- 20.11.2015 13:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Help=NULL,Updated=TO_TIMESTAMP('2015-11-20 13:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540612
;

-- 20.11.2015 13:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540612
;

-- 20.11.2015 13:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Order so
	join C_OrderLine sol on so.C_Order_ID = sol.C_Order_ID and so.isActive = ''Y'' and sol.isActive = ''Y''
	join C_OrderLine pol on sol.Link_OrderLine_ID = pol.C_OrderLine_ID and pol.isActive = ''Y''
	join C_Order po on pol.C_Order_ID = po.C_Order_ID and po.isActive = ''Y''
	
	where
		so.isSOTrx = ''Y'' and
		po.isSOTrx = ''N'' and
		C_Order.C_Order_ID = po.C_Order_ID and
		(so.C_Order_ID = @C_Order_ID/1@ or po.C_Order_ID = @C_Order_ID/1@)
)',Updated=TO_TIMESTAMP('2015-11-20 13:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540612
;

