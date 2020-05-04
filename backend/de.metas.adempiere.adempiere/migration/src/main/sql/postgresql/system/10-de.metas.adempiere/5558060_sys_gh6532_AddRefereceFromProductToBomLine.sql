-- 2020-04-28T04:45:29.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-04-28 07:45:28','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2020-04-28 07:45:28','YYYY-MM-DD HH24:MI:SS'),100,541135,'T','PP_Product_BOMLine for M_Product',0,'D')
;

-- 2020-04-28T04:45:29.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541135 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-04-28T04:48:15.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541135,53365,'(SELECT 1  from PP_Product_BOMLine pbl           INNER JOIN pp_product_bom pb on pbl.pp_product_bom_id = pb.pp_product_bom_id  where pb.m_product_id = M_Product.M_Product_ID)',0,'Y',TO_TIMESTAMP('2020-04-28 07:48:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-04-28 07:48:15','YYYY-MM-DD HH24:MI:SS'),'N',53006,100,53019,0,'D')
;

-- 2020-04-28T04:49:01.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,AD_Reference_Target_ID,IsTableRecordIdTarget,IsDirected,AD_Reference_Source_ID,Name,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2020-04-28 07:49:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2020-04-28 07:49:01','YYYY-MM-DD HH24:MI:SS'),100,540246,541135,'N','Y',540272,'M_Product -> PP_Product_BOMLine',0,'D')
;

-- 2020-04-28T05:02:45.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (SELECT 1        from PP_Product_BOMLine pbl        where pbl.pp_product_bomline_id = PP_Product_BOMLine.pp_product_bomline_id              and pbl.m_product_id = @M_Product_ID/-1@)',Updated=TO_TIMESTAMP('2020-04-28 08:02:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541135
;

-- 2020-04-28T05:09:45.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=53334, AD_Table_ID=53018,Updated=TO_TIMESTAMP('2020-04-28 08:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541135
;

-- 2020-04-28T05:14:50.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=53365, WhereClause='EXISTS (SELECT 1  from PP_Product_BOMLine pbl  where pbl.pp_product_bomline_id = PP_Product_BOMLine.pp_product_bomline_id    AND pbl.m_product_id = @M_Product_ID / -1@)', AD_Table_ID=53019,Updated=TO_TIMESTAMP('2020-04-28 08:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541135
;

-- 2020-04-28T05:18:04.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=53334, WhereClause=' EXISTS (SELECT 1  from PP_Product_BOMLine pbl  where pbl.PP_Product_BOM_ID = PP_Product_BOM.PP_Product_BOM_ID    AND pbl.M_Product_ID = @M_Product_ID / -1@)', AD_Table_ID=53018,Updated=TO_TIMESTAMP('2020-04-28 08:18:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541135
;

