
-- Column: C_Project.ExternalId
-- 2022-08-22T12:14:03.377Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584192,543939,0,10,203,'ExternalId',TO_TIMESTAMP('2022-08-22 14:14:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2022-08-22 14:14:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T12:14:03.404Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T12:14:03.495Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939)
;

-- 2022-08-22T12:14:09.679Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN ExternalId VARCHAR(255)')
;


-- 2022-08-22T12:53:51.793Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540705,0,203,TO_TIMESTAMP('2022-08-22 14:53:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_Project_ExternalID_UC','N',TO_TIMESTAMP('2022-08-22 14:53:51','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2022-08-22T12:53:51.818Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540705 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-08-22T12:54:06.563Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584192,541263,540705,0,TO_TIMESTAMP('2022-08-22 14:54:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-08-22 14:54:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T12:54:21.947Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584192,541264,540705,0,TO_TIMESTAMP('2022-08-22 14:54:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2022-08-22 14:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T12:54:27.708Z
CREATE UNIQUE INDEX C_Project_ExternalID_UC ON C_Project (ExternalId,ExternalId) WHERE IsActive='Y'
;

-- 2022-08-24T13:25:39.209Z
UPDATE AD_Index_Column SET AD_Column_ID=1350,Updated=TO_TIMESTAMP('2022-08-24 15:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=541264
;

-- 2022-08-24T13:25:41.938Z
DROP INDEX IF EXISTS c_project_externalid_uc
;

-- 2022-08-24T13:25:41.948Z
CREATE UNIQUE INDEX C_Project_ExternalID_UC ON C_Project (ExternalId,AD_Org_ID) WHERE IsActive='Y'
;


