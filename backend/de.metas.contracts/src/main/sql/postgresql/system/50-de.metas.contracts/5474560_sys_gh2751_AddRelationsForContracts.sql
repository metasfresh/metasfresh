-- 2017-10-18T10:26:53.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540676,540677,540191,TO_TIMESTAMP('2017-10-18 10:26:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Y','C_Order -> C_Flatrate_Term',TO_TIMESTAMP('2017-10-18 10:26:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-18T10:28:06.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540754,TO_TIMESTAMP('2017-10-18 10:28:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Order_C_Flatrate_Term',TO_TIMESTAMP('2017-10-18 10:28:06','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-10-18T10:28:06.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540754 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-10-18T10:29:18.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2169,2161,0,540754,259,143,TO_TIMESTAMP('2017-10-18 10:29:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-10-18 10:29:18','YYYY-MM-DD HH24:MI:SS'),100,'IsSOTrx = ''Y''')
;

-- 2017-10-18T10:29:30.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540754,Updated=TO_TIMESTAMP('2017-10-18 10:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540191
;

-- 2017-10-18T10:30:43.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540755,TO_TIMESTAMP('2017-10-18 10:30:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Flatrate_Term_For_C_Order',TO_TIMESTAMP('2017-10-18 10:30:43','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-10-18T10:30:43.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540755 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-10-18T10:35:40.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,557170,545802,0,540755,540320,540359,TO_TIMESTAMP('2017-10-18 10:35:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-10-18 10:35:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-18T10:50:49.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists(

SELECT 1
	FROM C_Flatrate_Term ft
	JOIN C_OrderLine ol on ft.C_OrderLine_Term_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE 
	o.C_Order_ID = @C_Order_ID/-1@
)',Updated=TO_TIMESTAMP('2017-10-18 10:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;

-- 2017-10-18T10:51:00.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540755,Updated=TO_TIMESTAMP('2017-10-18 10:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540191
;

-- 2017-10-18T10:51:06.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Order <-> C_Flatrate_Term',Updated=TO_TIMESTAMP('2017-10-18 10:51:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540191
;

-- 2017-10-18T10:52:46.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Order -> C_Flatrate_Term',Updated=TO_TIMESTAMP('2017-10-18 10:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540191
;

-- 2017-10-18T10:57:34.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists(

SELECT 1
	FROM C_Flatrate_Term ft
	JOIN C_OrderLine ol on ft.C_OrderLine_Term_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE 
	o.C_Order_ID = @C_Order_ID/-1@
	and AND ft.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)',Updated=TO_TIMESTAMP('2017-10-18 10:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;

-- 2017-10-18T10:59:01.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists(

SELECT 1
	FROM C_Flatrate_Term ft
	JOIN C_OrderLine ol on ft.C_OrderLine_Term_ID = ol.C_OrderLine_ID
	JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID
	WHERE 
	o.C_Order_ID = @C_Order_ID/-1@
	AND ft.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)',Updated=TO_TIMESTAMP('2017-10-18 10:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540755
;



---------------------------------------------


-- 2017-10-18T16:24:34.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,Name,Updated,UpdatedBy) VALUES (0,0,540754,540755,540192,TO_TIMESTAMP('2017-10-18 16:24:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Y','C_BPartner -> C_Flatrate_Term',TO_TIMESTAMP('2017-10-18 16:24:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-18T16:25:12.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540756,TO_TIMESTAMP('2017-10-18 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_BPartner_C_Flatrate_Term',TO_TIMESTAMP('2017-10-18 16:25:12','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-10-18T16:25:12.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540756 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-10-18T16:26:06.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,2893,0,540756,291,123,TO_TIMESTAMP('2017-10-18 16:26:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-10-18 16:26:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-18T16:26:14.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540756,Updated=TO_TIMESTAMP('2017-10-18 16:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540192
;

-- 2017-10-18T16:26:29.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540757,TO_TIMESTAMP('2017-10-18 16:26:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','C_Flatrate_Term_For_C_BPartner',TO_TIMESTAMP('2017-10-18 16:26:28','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-10-18T16:26:29.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540757 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-10-18T16:26:57.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,557170,545802,0,540757,540320,TO_TIMESTAMP('2017-10-18 16:26:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N',TO_TIMESTAMP('2017-10-18 16:26:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-18T16:27:02.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=540359,Updated=TO_TIMESTAMP('2017-10-18 16:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540757
;

-- 2017-10-18T16:28:13.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists(

SELECT 1
	FROM C_Flatrate_Term ft
	WHERE true
	ft.C_BPartner_ID = @C_BPartner_ID/-1@
	AND ft.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)',Updated=TO_TIMESTAMP('2017-10-18 16:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540757
;

-- 2017-10-18T16:28:22.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540757,Updated=TO_TIMESTAMP('2017-10-18 16:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540192
;

-- 2017-10-18T16:31:05.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists(

SELECT 1
	FROM C_Flatrate_Term ft
	WHERE true
	AND ft.Bill_BPartner_ID = @C_BPartner_ID/-1@
	AND ft.C_Flatrate_Term_ID = C_Flatrate_Term.C_Flatrate_Term_ID
)',Updated=TO_TIMESTAMP('2017-10-18 16:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540757
;

