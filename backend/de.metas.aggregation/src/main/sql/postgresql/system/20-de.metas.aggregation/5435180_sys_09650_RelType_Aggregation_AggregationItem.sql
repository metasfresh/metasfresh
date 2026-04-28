-- 10.12.2015 13:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540146,TO_TIMESTAMP('2015-12-10 13:54:47','YYYY-MM-DD HH24:MI:SS'),100,'Relation type between Aggregation and the Aggregation Types that include it','de.metas.aggregation','Y','N','N','C_Aggregation -> C_AggregationItem',TO_TIMESTAMP('2015-12-10 13:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.12.2015 13:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540620,TO_TIMESTAMP('2015-12-10 13:55:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y','N','C_Aggregation -> C_AggregationItem',TO_TIMESTAMP('2015-12-10 13:55:17','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 10.12.2015 13:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540620 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 10.12.2015 13:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,551846,0,540620,540649,540259,TO_TIMESTAMP('2015-12-10 13:55:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y','N',TO_TIMESTAMP('2015-12-10 13:55:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.12.2015 14:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	join C_Aggregation item_Aggregation on ai.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID
	where 
		ai.type = ''INC'' and
		C_Aggregation.C_Aggregation_ID = a.C_Aggregation_ID and
		( a.C_Aggregation_ID = @C_Aggregation_ID/-1@ or item_Aggregation.C_Aggregation_ID = @C_Aggregation_ID/-1@ )
)
',Updated=TO_TIMESTAMP('2015-12-10 14:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540620
;

-- 10.12.2015 14:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540620,Updated=TO_TIMESTAMP('2015-12-10 14:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540621,TO_TIMESTAMP('2015-12-10 14:00:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y','N','RelType C_AggregationItem -> C_Aggregation',TO_TIMESTAMP('2015-12-10 14:00:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 10.12.2015 14:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540621 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 10.12.2015 14:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,551846,0,540621,540649,540259,TO_TIMESTAMP('2015-12-10 14:01:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y','N',TO_TIMESTAMP('2015-12-10 14:01:14','YYYY-MM-DD HH24:MI:SS'),100,'

exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	join C_Aggregation item_Aggregation on ai.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID
	where 
		ai.type = ''INC'' and
		C_Aggregation.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID and
		( a.C_Aggregation_ID = @C_Aggregation_ID/-1@ or item_Aggregation.C_Aggregation_ID = @C_Aggregation_ID/-1@ )
)')
;

-- 10.12.2015 14:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540621,Updated=TO_TIMESTAMP('2015-12-10 14:01:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET ValidationType='L',Updated=TO_TIMESTAMP('2015-12-10 14:04:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET ValidationType='T',Updated=TO_TIMESTAMP('2015-12-10 14:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2015-12-10 14:04:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=2',Updated=TO_TIMESTAMP('2015-12-10 14:05:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=1',Updated=TO_TIMESTAMP('2015-12-10 14:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	
	where 
		ai.type = ''INC'' and
		C_Aggregation.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID and
		( a.C_Aggregation_ID = @C_Aggregation_ID/-1@ or item_Aggregation.C_Aggregation_ID = @C_Aggregation_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-12-10 14:07:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	where 
		ai.type = ''INC'' and
		( a.C_Aggregation_ID = @C_Aggregation_ID/-1@ or ai.C_Aggregation_ID = @C_Aggregation_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-12-10 14:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	where 
		ai.type = ''INC'' and
		( a.C_Aggregation_ID = @C_Aggregation_ID/-1@ or ai.C_AggregationItem_ID = @C_AggregationItem_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-12-10 14:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Aggregation.C_Aggregation_ID > 0',Updated=TO_TIMESTAMP('2015-12-10 14:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540259,Updated=TO_TIMESTAMP('2015-12-10 14:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=2',Updated=TO_TIMESTAMP('2015-12-10 14:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=NULL,Updated=TO_TIMESTAMP('2015-12-10 14:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL, IsExplicit='Y',Updated=TO_TIMESTAMP('2015-12-10 14:27:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540621,Updated=TO_TIMESTAMP('2015-12-10 14:28:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1-2',Updated=TO_TIMESTAMP('2015-12-10 14:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET ValidationType='D',Updated=TO_TIMESTAMP('2015-12-10 14:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET ValidationType='T',Updated=TO_TIMESTAMP('2015-12-10 14:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Aggregation.isActive = ''Y''',Updated=TO_TIMESTAMP('2015-12-10 14:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsExplicit='N',Updated=TO_TIMESTAMP('2015-12-10 14:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2015-12-10 14:30:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='1=2',Updated=TO_TIMESTAMP('2015-12-10 14:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2015-12-10 14:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	join C_Aggregation item_Aggregation on ai.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID
	where 
		ai.type = ''INC'' and
		C_Aggregation.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID and
		( a.C_Aggregation_ID = @C_Aggregation_ID/-1@ or item_Aggregation.C_Aggregation_ID = @C_Aggregation_ID/-1@ )
)
',Updated=TO_TIMESTAMP('2015-12-10 14:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540621,Updated=TO_TIMESTAMP('2015-12-10 14:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540146
;

-- 10.12.2015 14:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	join C_Aggregation item_Aggregation on ai.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID
	where 
		ai.type = ''INC'' and
		C_Aggregation.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID and
		(  item_Aggregation.C_Aggregation_ID = @C_Aggregation_ID/-1@ )
)
',Updated=TO_TIMESTAMP('2015-12-10 14:40:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

-- 10.12.2015 14:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='
exists
(
	select 1 from C_Aggregation a
	join C_AggregationItem ai on a.C_Aggregation_ID = ai.included_Aggregation_ID
	join C_Aggregation item_Aggregation on ai.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID
	where 
		ai.type = ''INC'' and
		C_Aggregation.C_Aggregation_ID = item_Aggregation.C_Aggregation_ID and
		(  a.C_Aggregation_ID = @C_Aggregation_ID/-1@)
)
',Updated=TO_TIMESTAMP('2015-12-10 14:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540621
;

