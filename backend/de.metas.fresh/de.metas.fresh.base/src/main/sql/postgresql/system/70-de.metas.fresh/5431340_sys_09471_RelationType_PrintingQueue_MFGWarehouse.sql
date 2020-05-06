
-- 26.10.2015 18:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540132,TO_TIMESTAMP('2015-10-26 18:16:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Printing_Queue -> C_Order_MFGWarehouse_Report',TO_TIMESTAMP('2015-10-26 18:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2015 18:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540589,TO_TIMESTAMP('2015-10-26 18:17:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','C_Printing_Queue -> C_Order_MFGWarehouse_Report',TO_TIMESTAMP('2015-10-26 18:17:19','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.10.2015 18:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540589 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.10.2015 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,548048,0,540589,540435,TO_TIMESTAMP('2015-10-26 18:18:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2015-10-26 18:18:14','YYYY-MM-DD HH24:MI:SS'),100,'exists
(
	select 1 
	from C_Printing_Queue pq
	join AD_Archive a on pq.AD_Archive_ID = a.AD_Archive_ID and a.isActive = ''Y'' and pq.isActive = ''Y''
	join C_Order_MFGWarehouse_Report mfg on 
		(
			a.AD_Table_ID = (select ad_table_ID from ad_Table where tablename = ''C_Order_MFGWarehouse_Report'') 
		)
		and a.Record_ID = mfg.C_Order_MFGWarehouse_Report_ID
		and mfg.isActive = ''Y''
		
	WHERE
		C_Printing_Queue.C_Printing_Queue_ID = pq.C_Printing_Queue_ID
		pq.C_Printing_Queue_ID = @C_Printing_Queue_ID/-1@
		or
		mfg.C_Order_MFGWarehouse_Report_ID = @C_Order_MFGWarehouse_Report_ID/-1@
)')
;

-- 26.10.2015 18:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540589,Updated=TO_TIMESTAMP('2015-10-26 18:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540132
;

-- 26.10.2015 18:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540590,TO_TIMESTAMP('2015-10-26 18:21:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','RelType C_Printing_Queue -> C_Order_MFGWarehouse_Report',TO_TIMESTAMP('2015-10-26 18:21:56','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 26.10.2015 18:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540590 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.10.2015 18:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,552733,0,540590,540683,TO_TIMESTAMP('2015-10-26 18:22:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2015-10-26 18:22:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2015 18:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Printing_Queue pq
	join AD_Archive a on pq.AD_Archive_ID = a.AD_Archive_ID and a.isActive = ''Y'' and pq.isActive = ''Y''
	join C_Order_MFGWarehouse_Report mfg on 
		(
			a.AD_Table_ID = (select ad_table_ID from ad_Table where tablename = ''C_Order_MFGWarehouse_Report'') 
		)
		and a.Record_ID = mfg.C_Order_MFGWarehouse_Report_ID
		and mfg.isActive = ''Y''
		
	WHERE
		C_Printing_Queue.C_Printing_Queue_ID = pq.C_Printing_Queue_ID
		pq.C_Printing_Queue_ID = @C_Printing_Queue_ID/-1@
		or
		mfg.C_Order_MFGWarehouse_Report_ID = @C_Order_MFGWarehouse_Report_ID/-1@
)
	',Updated=TO_TIMESTAMP('2015-10-26 18:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540590
;

-- 26.10.2015 18:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540274,Updated=TO_TIMESTAMP('2015-10-26 18:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540590
;

-- 26.10.2015 18:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540590, EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2015-10-26 18:23:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540132
;

-- 27.10.2015 11:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 
	from C_Printing_Queue pq
	join AD_Archive a on pq.AD_Archive_ID = a.AD_Archive_ID and a.isActive = ''Y'' and pq.isActive = ''Y''
	join C_Order_MFGWarehouse_Report mfg on 
		(
			a.AD_Table_ID = (select ad_table_ID from ad_Table where tablename = ''C_Order_MFGWarehouse_Report'') 
		)
		and a.Record_ID = mfg.C_Order_MFGWarehouse_Report_ID
		and mfg.isActive = ''Y''
		
	WHERE
		C_Printing_Queue.C_Printing_Queue_ID = pq.C_Printing_Queue_ID and
		(pq.C_Printing_Queue_ID = @C_Printing_Queue_ID/-1@
		or
		mfg.C_Order_MFGWarehouse_Report_ID = @C_Order_MFGWarehouse_Report_ID/-1@)
)',Updated=TO_TIMESTAMP('2015-10-27 11:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540589
;

-- 27.10.2015 11:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Printing_Queue pq
	join AD_Archive a on pq.AD_Archive_ID = a.AD_Archive_ID and a.isActive = ''Y'' and pq.isActive = ''Y''
	join C_Order_MFGWarehouse_Report mfg on 
		(
			a.AD_Table_ID = (select ad_table_ID from ad_Table where tablename = ''C_Order_MFGWarehouse_Report'') 
		)
		and a.Record_ID = mfg.C_Order_MFGWarehouse_Report_ID
		and mfg.isActive = ''Y''
		
	WHERE
		C_Printing_Queue.C_Printing_Queue_ID = pq.C_Printing_Queue_ID and
		(pq.C_Printing_Queue_ID = @C_Printing_Queue_ID/-1@
		or
		mfg.C_Order_MFGWarehouse_Report_ID = @C_Order_MFGWarehouse_Report_ID/-1@)
)
	',Updated=TO_TIMESTAMP('2015-10-27 11:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540590
;

-- 27.10.2015 11:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Printing_Queue pq
	join AD_Archive a on pq.AD_Archive_ID = a.AD_Archive_ID and a.isActive = ''Y'' and pq.isActive = ''Y''
	join C_Order_MFGWarehouse_Report mfg on 
		(
			a.AD_Table_ID = (select ad_table_ID from ad_Table where tablename = ''C_Order_MFGWarehouse_Report'') 
		)
		and a.Record_ID = mfg.C_Order_MFGWarehouse_Report_ID
		and mfg.isActive = ''Y''
		
	WHERE
		C_Order_MFGWarehouse_Report.C__Order_MFGWarehouse_Report_ID = mfg.C__Order_MFGWarehouse_Report_ID and
		(pq.C_Printing_Queue_ID = @C_Printing_Queue_ID/-1@
		or
		mfg.C_Order_MFGWarehouse_Report_ID = @C_Order_MFGWarehouse_Report_ID/-1@)
)
	',Updated=TO_TIMESTAMP('2015-10-27 11:08:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540590
;

-- 27.10.2015 11:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Printing_Queue pq
	join AD_Archive a on pq.AD_Archive_ID = a.AD_Archive_ID and a.isActive = ''Y'' and pq.isActive = ''Y''
	join C_Order_MFGWarehouse_Report mfg on 
		(
			a.AD_Table_ID = (select ad_table_ID from ad_Table where tablename = ''C_Order_MFGWarehouse_Report'') 
		)
		and a.Record_ID = mfg.C_Order_MFGWarehouse_Report_ID
		and mfg.isActive = ''Y''
		
	WHERE
		C_Order_MFGWarehouse_Report.C__Order_MFGWarehouse_Report_ID = mfg.C_Order_MFGWarehouse_Report_ID and
		(pq.C_Printing_Queue_ID = @C_Printing_Queue_ID/-1@
		or
		mfg.C_Order_MFGWarehouse_Report_ID = @C_Order_MFGWarehouse_Report_ID/-1@)
)
	',Updated=TO_TIMESTAMP('2015-10-27 11:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540590
;

-- 27.10.2015 11:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 
	from C_Printing_Queue pq
	join AD_Archive a on pq.AD_Archive_ID = a.AD_Archive_ID and a.isActive = ''Y'' and pq.isActive = ''Y''
	join C_Order_MFGWarehouse_Report mfg on 
		(
			a.AD_Table_ID = (select ad_table_ID from ad_Table where tablename = ''C_Order_MFGWarehouse_Report'') 
		)
		and a.Record_ID = mfg.C_Order_MFGWarehouse_Report_ID
		and mfg.isActive = ''Y''
		
	WHERE
		C_Order_MFGWarehouse_Report.C_Order_MFGWarehouse_Report_ID = mfg.C_Order_MFGWarehouse_Report_ID and
		(pq.C_Printing_Queue_ID = @C_Printing_Queue_ID/-1@
		or
		mfg.C_Order_MFGWarehouse_Report_ID = @C_Order_MFGWarehouse_Report_ID/-1@)
)
	',Updated=TO_TIMESTAMP('2015-10-27 11:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540590
;

