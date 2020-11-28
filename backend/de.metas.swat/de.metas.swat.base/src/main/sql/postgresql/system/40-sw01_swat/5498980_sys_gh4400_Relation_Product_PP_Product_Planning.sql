-- 2018-08-07T17:04:39.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=53007,Updated=TO_TIMESTAMP('2018-08-07 17:04:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 2018-08-07T17:11:02.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=2011, AD_Key=1402, AD_Table_ID=208, WhereClause='EXISTS (  select 1 from PP_Product_Planning pp  INNER JOIN M_Product p on pp.M_Product_ID = p.M_Product_ID  where p.M_Product_ID = @M_Product_ID@   AND p.M_Product_ID = M_Product.M_Product_ID )',Updated=TO_TIMESTAMP('2018-08-07 17:11:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 2018-08-07T17:22:17.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2018-08-07 17:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540127
;

-- 2018-08-07T17:22:39.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2018-08-07 17:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540272
;

-- 2018-08-07T17:23:17.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='PP_Product_Planning Target for M_Product',Updated=TO_TIMESTAMP('2018-08-07 17:23:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 2018-08-07T17:23:32.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='PP_Product_Planning Target for M_Product' WHERE AD_Reference_ID=540573 AND AD_Language='it_CH'
;

-- 2018-08-07T17:23:41.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='PP_Product_Planning Target for M_Product' WHERE AD_Reference_ID=540573 AND AD_Language='fr_CH'
;

-- 2018-08-07T17:23:44.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='PP_Product_Planning Target for M_Product' WHERE AD_Reference_ID=540573 AND AD_Language='en_GB'
;

-- 2018-08-07T17:23:46.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='PP_Product_Planning Target for M_Product' WHERE AD_Reference_ID=540573 AND AD_Language='de_CH'
;

-- 2018-08-07T17:23:49.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='PP_Product_Planning Target for M_Product' WHERE AD_Reference_ID=540573 AND AD_Language='en_US'
;

-- 2018-08-07T17:23:52.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET Name='PP_Product_Planning Target for M_Product' WHERE AD_Reference_ID=540573 AND AD_Language='nl_NL'
;

-- 2018-08-07T17:24:27.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=1402, WhereClause='EXISTS (  select 1 from PP_Product_Planning pp  INNER JOIN M_Product p on pp.M_Product_ID = p.M_Product_ID  where p.M_Product_ID = @M_Product_ID@   AND p.M_Product_ID = M_ProductTEST.M_Product_ID )',Updated=TO_TIMESTAMP('2018-08-07 17:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 2018-08-07T17:33:27.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540898,TO_TIMESTAMP('2018-08-07 17:33:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','M_Product Source',TO_TIMESTAMP('2018-08-07 17:33:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-08-07T17:33:27.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540898 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-08-07T17:34:28.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,1402,1402,0,540898,208,140,TO_TIMESTAMP('2018-08-07 17:34:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N',TO_TIMESTAMP('2018-08-07 17:34:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-07T17:35:30.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='PP_Product_Planning Target for M_Product OLD',Updated=TO_TIMESTAMP('2018-08-07 17:35:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 2018-08-07T17:35:35.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540900,TO_TIMESTAMP('2018-08-07 17:35:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N','PP_Product_Planning Target for M_Product',TO_TIMESTAMP('2018-08-07 17:35:35','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-08-07T17:35:35.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540900 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-08-07T17:38:11.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,53398,53398,0,540900,53020,53007,TO_TIMESTAMP('2018-08-07 17:38:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.product','Y','N',TO_TIMESTAMP('2018-08-07 17:38:11','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from PP_Product_Planning pp where pp.PP_Product_Planning_ID = PP_Product_Planning.PP_Product_Planning_ID and pp.M_Product_ID = @M_Product_ID@)')
;

-- 2018-08-07T17:38:23.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540898,540900,540220,TO_TIMESTAMP('2018-08-07 17:38:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','M_Product -> PP_Product_Planning',TO_TIMESTAMP('2018-08-07 17:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-07T17:40:43.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=53389, AD_Key=53389, WhereClause='exists (select 1 from PP_Product_Planning pp where pp.M_Product_ID = PP_Product_Planning.M_Product_ID and pp.M_Product_ID = @M_Product_ID@)',Updated=TO_TIMESTAMP('2018-08-07 17:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540900
;

-- 2018-08-07T17:47:26.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2018-08-07 17:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1402
;

-- 2018-08-07T17:48:15.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540220
;

-- 2018-08-07T17:48:30.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540898
;

-- 2018-08-07T17:48:30.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540898
;

-- 2018-08-07T17:48:38.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540900
;

-- 2018-08-07T17:48:38.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540900
;

-- 2018-08-07T17:48:46.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='PP_Product_Planning Target for M_Product',Updated=TO_TIMESTAMP('2018-08-07 17:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 2018-08-07T17:49:24.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (  select 1 from PP_Product_Planning pp  INNER JOIN M_Product p on pp.M_Product_ID = p.M_Product_ID  where p.M_Product_ID = @M_Product_ID@   AND p.M_Product_ID = M_Product.M_Product_ID )',Updated=TO_TIMESTAMP('2018-08-07 17:49:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

