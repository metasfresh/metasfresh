-- 11.09.2015 17:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AllowZoomTo='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-09-11 17:11:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53389
;

-- 11.09.2015 17:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540127,TO_TIMESTAMP('2015-09-11 17:39:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','M_Product -> PP_Product_Planning',TO_TIMESTAMP('2015-09-11 17:39:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.09.2015 17:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540272,Updated=TO_TIMESTAMP('2015-09-11 17:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540127
;

-- 11.09.2015 17:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540573,TO_TIMESTAMP('2015-09-11 17:40:51','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','RelType M_Product Product -> PP_Product_Planning',TO_TIMESTAMP('2015-09-11 17:40:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 11.09.2015 17:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540573 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 11.09.2015 17:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,53398,0,540573,53020,TO_TIMESTAMP('2015-09-11 17:43:57','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N',TO_TIMESTAMP('2015-09-11 17:43:57','YYYY-MM-DD HH24:MI:SS'),100,'EXISTS
(
	SELECT 1 FROM PP_Product_Planning_ID pp
	INNER JOIN M_Product p on pp.M_Product_ID = p.M_Product_ID
	WHERE M_Product_ID = @M_Product_ID@
		AND p.M_Product_ID = M_Product.M_Product_ID
)')
;

-- 11.09.2015 17:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540573,Updated=TO_TIMESTAMP('2015-09-11 17:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540127
;

-- 11.09.2015 17:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
	SELECT 1 FROM PP_Product_Planning pp
	INNER JOIN M_Product p on pp.M_Product_ID = p.M_Product_ID
	WHERE M_Product_ID = @M_Product_ID@
		AND p.M_Product_ID = M_Product.M_Product_ID
)',Updated=TO_TIMESTAMP('2015-09-11 17:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 11.09.2015 17:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
	SELECT 1 FROM PP_Product_Planning pp
	INNER JOIN M_Product p on pp.M_Product_ID = p.M_Product_ID
	WHERE p.M_Product_ID = @M_Product_ID@
		AND p.M_Product_ID = M_Product.M_Product_ID
)',Updated=TO_TIMESTAMP('2015-09-11 17:47:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

-- 11.09.2015 17:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS
(
	SELECT 1 FROM PP_Product_Planning pp
	INNER JOIN M_Product p on pp.M_Product_ID = p.M_Product_ID
	WHERE p.M_Product_ID = @M_Product_ID@
		AND pp.PP_Product_Planning_ID = PP_Product_Planning.PP_Product_Planning_ID
)',Updated=TO_TIMESTAMP('2015-09-11 17:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540573
;

