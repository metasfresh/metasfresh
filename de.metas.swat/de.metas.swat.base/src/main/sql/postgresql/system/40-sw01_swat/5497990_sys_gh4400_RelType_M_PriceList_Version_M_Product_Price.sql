-- 2018-07-25T19:00:34.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540885,TO_TIMESTAMP('2018-07-25 19:00:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','M_ProductPrice Targer for M_PriceList_Version',TO_TIMESTAMP('2018-07-25 19:00:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-25T19:00:34.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540885 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-25T19:04:01.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,500043,500043,0,540885,251,540325,TO_TIMESTAMP('2018-07-25 19:04:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2018-07-25 19:04:01','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from M_ProductPrice pp where pp.M_ProductPrice_ID = M_ProductPrice.M_ProductPrice_ID  and pp.M_PriceList_Version_ID = @M_PriceList_Version_ID@)')
;

-- 2018-07-25T19:04:16.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,188,540885,540210,TO_TIMESTAMP('2018-07-25 19:04:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Y','N','M_PriceList_Version -> M_ProductPrice',TO_TIMESTAMP('2018-07-25 19:04:16','YYYY-MM-DD HH24:MI:SS'),100)
;

