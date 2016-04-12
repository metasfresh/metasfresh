
-- Apr 12, 2016 2:04 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540376,0,540751,TO_TIMESTAMP('2016-04-12 14:04:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','Y','PMM_Product_UC','N',TO_TIMESTAMP('2016-04-12 14:04:51','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- Apr 12, 2016 2:04 PM
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540376 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- Apr 12, 2016 2:07 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554219,540748,540376,0,'COALESCE(C_BPartner_ID,0)',TO_TIMESTAMP('2016-04-12 14:07:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',10,TO_TIMESTAMP('2016-04-12 14:07:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Apr 12, 2016 2:08 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554215,540749,540376,0,TO_TIMESTAMP('2016-04-12 14:08:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',20,TO_TIMESTAMP('2016-04-12 14:08:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Apr 12, 2016 2:12 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554216,540750,540376,0,'COALESCE(M_HU_PI_Item_Product_ID,0)',TO_TIMESTAMP('2016-04-12 14:12:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',30,TO_TIMESTAMP('2016-04-12 14:12:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Apr 12, 2016 2:13 PM
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,554319,540751,540376,0,'COALESCE(M_AttributeSetInstance_ID,0)',TO_TIMESTAMP('2016-04-12 14:13:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y',40,TO_TIMESTAMP('2016-04-12 14:13:28','YYYY-MM-DD HH24:MI:SS'),100)
;

COMMIT;

-- Apr 12, 2016 2:23 PM
-- URL zum Konzept
CREATE UNIQUE INDEX PMM_Product_UC ON PMM_Product (COALESCE(C_BPartner_ID,0),M_Product_ID,COALESCE(M_HU_PI_Item_Product_ID,0),COALESCE(M_AttributeSetInstance_ID,0)) WHERE IsActive='Y'
;

