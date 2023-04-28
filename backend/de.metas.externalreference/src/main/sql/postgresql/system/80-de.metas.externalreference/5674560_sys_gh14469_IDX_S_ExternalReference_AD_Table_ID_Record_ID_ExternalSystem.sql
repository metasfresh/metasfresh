

-- 2023-01-31T16:39:17.830Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540721,0,541486,TO_TIMESTAMP('2023-01-31 18:39:17','YYYY-MM-DD HH24:MI:SS'),100,'UNIQUE index for ad_table_id, record_id and externalSystem','U','Y','Y','IDX_S_ExternalReference_AD_Table_ID_Record_ID_ExternalSystem','N',TO_TIMESTAMP('2023-01-31 18:39:17','YYYY-MM-DD HH24:MI:SS'),100,'S_ExternalReference.isActive=''Y''')
;

-- 2023-01-31T16:39:17.853Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540721 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-01-31T16:39:53.098Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570590,541301,540721,0,TO_TIMESTAMP('2023-01-31 18:39:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y',10,TO_TIMESTAMP('2023-01-31 18:39:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T16:40:07.371Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570617,541302,540721,0,TO_TIMESTAMP('2023-01-31 18:40:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y',20,TO_TIMESTAMP('2023-01-31 18:40:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T16:40:20.012Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570585,541303,540721,0,TO_TIMESTAMP('2023-01-31 18:40:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y',30,TO_TIMESTAMP('2023-01-31 18:40:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T16:40:27.422Z
UPDATE AD_Index_Table SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2023-01-31 18:40:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540721
;

-- 2023-01-31T16:43:40.875Z
UPDATE AD_Index_Table SET IsUnique='N',Updated=TO_TIMESTAMP('2023-01-31 18:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540721
;

-- 2023-01-31T16:44:06.872Z
CREATE INDEX IDX_S_ExternalReference_AD_Table_ID_Record_ID_ExternalSystem ON S_ExternalReference (Record_ID,Referenced_AD_Table_ID,ExternalSystem) WHERE S_ExternalReference.isActive='Y'
;

