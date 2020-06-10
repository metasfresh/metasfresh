-- 2020-05-21T08:10:01.977Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570814,543939,0,10,113,'ExternalId',TO_TIMESTAMP('2020-05-21 11:10:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','External ID',0,0,TO_TIMESTAMP('2020-05-21 11:10:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-05-21T08:10:02.413Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570814 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-05-21T08:10:02.528Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- 2020-05-21T08:10:21.611Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm ADD COLUMN ExternalId VARCHAR(255)')
;

-- 2020-05-21T08:11:15.648Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570814,613712,0,184,0,TO_TIMESTAMP('2020-05-21 11:11:15','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','External ID',260,260,0,1,1,TO_TIMESTAMP('2020-05-21 11:11:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-21T08:11:15.841Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=613712 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-05-21T08:11:15.874Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2020-05-21T08:11:15.923Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=613712
;

-- 2020-05-21T08:11:15.965Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(613712)
;

-- 2020-05-21T08:12:05.349Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,613712,0,184,540546,569747,'F',TO_TIMESTAMP('2020-05-21 11:12:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External ID',50,0,0,TO_TIMESTAMP('2020-05-21 11:12:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-21T08:12:09.851Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2020-05-21 11:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=569747
;
















-- 2020-05-21T10:32:10.837Z
-- URL zum Konzept
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2020-05-21 13:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=113
;

-- 2020-05-21T10:34:19.041Z
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540541,0,113,TO_TIMESTAMP('2020-05-21 13:34:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','C_PaymentTerm_ExternalId_Unique','N',TO_TIMESTAMP('2020-05-21 13:34:18','YYYY-MM-DD HH24:MI:SS'),100,'IsActive = ''Y''')
;

-- 2020-05-21T10:34:19.075Z
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540541 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-05-21T10:35:02.998Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2029,541022,540541,0,TO_TIMESTAMP('2020-05-21 13:35:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2020-05-21 13:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-21T10:38:16.625Z
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND COALESCE(ExternalId, '''') != ''''',Updated=TO_TIMESTAMP('2020-05-21 13:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540541
;

-- 2020-05-21T10:38:55.595Z
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570814,541023,540541,0,TO_TIMESTAMP('2020-05-21 13:38:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2020-05-21 13:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-21T10:38:59.892Z
-- URL zum Konzept
CREATE UNIQUE INDEX C_PaymentTerm_ExternalId_Unique ON C_PaymentTerm (AD_Org_ID,ExternalId) WHERE IsActive='Y' AND COALESCE(ExternalId, '') != ''
;

















