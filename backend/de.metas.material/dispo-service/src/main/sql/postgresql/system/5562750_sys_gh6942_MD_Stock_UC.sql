-- 2020-07-02T06:45:42.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540543,0,540891,TO_TIMESTAMP('2020-07-02 08:45:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','MD_Stock_UC','N',TO_TIMESTAMP('2020-07-02 08:45:42','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2020-07-02T06:45:42.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540543 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-07-02T06:46:33.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558483,541026,540543,0,TO_TIMESTAMP('2020-07-02 08:46:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',10,TO_TIMESTAMP('2020-07-02 08:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-02T06:46:59.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558484,541027,540543,0,TO_TIMESTAMP('2020-07-02 08:46:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',20,TO_TIMESTAMP('2020-07-02 08:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-02T06:47:15.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558485,541028,540543,0,TO_TIMESTAMP('2020-07-02 08:47:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',30,TO_TIMESTAMP('2020-07-02 08:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-02T06:47:36.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558476,541029,540543,0,TO_TIMESTAMP('2020-07-02 08:47:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',40,TO_TIMESTAMP('2020-07-02 08:47:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-02T06:47:51.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558475,541030,540543,0,TO_TIMESTAMP('2020-07-02 08:47:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',50,TO_TIMESTAMP('2020-07-02 08:47:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-02T06:47:55.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2020-07-02 08:47:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540543
;


-- delete duplicates
delete from  MD_Stock s_old 
where exists 
(select 1 from MD_Stock s_new where 
 s_old.MD_Stock_ID<s_new.MD_Stock_ID
 and s_old.m_product_id=s_new.m_product_id
 and s_old.attributeskey=s_new.attributeskey
 and s_old.m_warehouse_id=s_new.m_warehouse_id
 and s_old.ad_org_id=s_new.ad_org_id
 and s_old.ad_client_id=s_new.ad_client_id
);

