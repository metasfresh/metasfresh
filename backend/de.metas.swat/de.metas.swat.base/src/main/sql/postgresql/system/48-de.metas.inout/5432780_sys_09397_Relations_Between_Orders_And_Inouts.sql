-- 09.11.2015 16:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540604,TO_TIMESTAMP('2015-11-09 16:36:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','C_Order -> M_InOut (SO)',TO_TIMESTAMP('2015-11-09 16:36:31','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 09.11.2015 16:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540604 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 09.11.2015 16:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2161,0,540604,259,TO_TIMESTAMP('2015-11-09 16:36:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2015-11-09 16:36:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2015 16:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=143,Updated=TO_TIMESTAMP('2015-11-09 16:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540604
;

-- 09.11.2015 16:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='


exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isActive = ''Y''
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID and iol.isActive = ''Y''
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID and io.isActive = ''Y''
	where
		io.isSOTrx = ''Y'' and
		C_Order.C_Order_ID = o.C_Order_ID and
		( io.M_InOut_ID = @M_InOut_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-09 16:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540604
;

-- 09.11.2015 16:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540604,540139,TO_TIMESTAMP('2015-11-09 16:49:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','C_Order -> M_InOut (SO)',TO_TIMESTAMP('2015-11-09 16:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2015 16:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2015-11-09 16:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540139
;

-- 09.11.2015 16:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 16:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540139
;

-- 09.11.2015 16:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2015-11-09 16:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540139
;

-- 09.11.2015 17:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540605,TO_TIMESTAMP('2015-11-09 17:00:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','RelType C_Order -> M_InOut (SO)',TO_TIMESTAMP('2015-11-09 17:00:15','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 09.11.2015 17:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540605 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 09.11.2015 17:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,3521,0,540605,319,TO_TIMESTAMP('2015-11-09 17:00:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2015-11-09 17:00:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=169, WhereClause='
exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isActive = ''Y''
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID and iol.isActive = ''Y''
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID and io.isActive = ''Y''
	where
		io.isSOTrx = ''Y'' and
		M_InOut.M_InOut_ID = io.M_InOut_ID and
		( io.M_InOut_ID = @M_InOut_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@ )
)',Updated=TO_TIMESTAMP('2015-11-09 17:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540605
;

-- 09.11.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540605,Updated=TO_TIMESTAMP('2015-11-09 17:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540139
;

-- 09.11.2015 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2015-11-09 17:02:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=1000002
;

-- 09.11.2015 17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2015-11-09 17:03:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=1000002
;

-- 09.11.2015 17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=NULL,Updated=TO_TIMESTAMP('2015-11-09 17:03:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=1000002
;

-- 09.11.2015 17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540055
;

-- 09.11.2015 17:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540055
;

-- 09.11.2015 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=NULL,Updated=TO_TIMESTAMP('2015-11-09 17:04:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=1000002
;

-- 09.11.2015 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540056
;

-- 09.11.2015 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540056
;

-- 09.11.2015 17:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=1000002
;






-- 09.11.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540606,TO_TIMESTAMP('2015-11-09 17:15:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','C_Order -> M_InOut (PO)',TO_TIMESTAMP('2015-11-09 17:15:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 09.11.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540606 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 09.11.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,540606,259,181,TO_TIMESTAMP('2015-11-09 17:15:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2015-11-09 17:15:45','YYYY-MM-DD HH24:MI:SS'),100,'


exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isActive = ''Y''
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID and iol.isActive = ''Y''
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID and io.isActive = ''Y''
	where
		io.isSOTrx = ''N'' and
		C_Order.C_Order_ID = o.C_Order_ID and
		( io.M_InOut_ID = @M_InOut_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@ )
)')
;

-- 09.11.2015 17:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540140,TO_TIMESTAMP('2015-11-09 17:15:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','C_Order -> M_InOut (PO)',TO_TIMESTAMP('2015-11-09 17:15:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2015 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540606,Updated=TO_TIMESTAMP('2015-11-09 17:16:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540140
;

-- 09.11.2015 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540607,TO_TIMESTAMP('2015-11-09 17:16:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','RelType C_Order -> M_InOut (PO)',TO_TIMESTAMP('2015-11-09 17:16:36','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 09.11.2015 17:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540607 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 09.11.2015 17:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,3521,0,540607,319,184,TO_TIMESTAMP('2015-11-09 17:17:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N',TO_TIMESTAMP('2015-11-09 17:17:14','YYYY-MM-DD HH24:MI:SS'),100,'
exists
(
	select 1
	from C_Order o
	join C_OrderLine ol on o.C_Order_ID = ol.C_Order_ID and o.isActive = ''Y'' and ol.isActive = ''Y''
	join M_InOutLine iol on ol.C_OrderLine_ID = iol.C_OrderLine_ID and iol.isActive = ''Y''
	join M_InOut io on iol.M_InOut_ID = io.M_InOut_ID and io.isActive = ''Y''
	where
		io.isSOTrx = ''N'' and
		M_InOut.M_InOut_ID = io.M_InOut_ID and
		( io.M_InOut_ID = @M_InOut_ID/-1@ or o.C_Order_ID = @C_Order_ID/-1@ )
)')
;

-- 09.11.2015 17:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540607,Updated=TO_TIMESTAMP('2015-11-09 17:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540140
;

-- 09.11.2015 17:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540139
;

-- 09.11.2015 17:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540604
;

-- 09.11.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:57:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540605
;

-- 09.11.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540605
;

-- 09.11.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540604
;

-- 09.11.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:57:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540140
;

-- 09.11.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540606
;

-- 09.11.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540606
;

-- 09.11.2015 17:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:57:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540607
;

-- 09.11.2015 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-11-09 17:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540607
;

