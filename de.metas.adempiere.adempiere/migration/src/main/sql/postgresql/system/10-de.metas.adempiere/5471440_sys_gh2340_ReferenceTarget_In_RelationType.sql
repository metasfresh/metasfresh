-- 2017-09-12T14:39:40.248
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543415,0,'IsReferenceTarget',TO_TIMESTAMP('2017-09-12 14:39:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsReferenceTarget','IsReferenceTarget',TO_TIMESTAMP('2017-09-12 14:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-12T14:39:40.287
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543415 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-12T14:39:51.927
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557147,543415,0,20,53246,'N','IsReferenceTarget',TO_TIMESTAMP('2017-09-12 14:39:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','IsReferenceTarget',0,0,TO_TIMESTAMP('2017-09-12 14:39:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-12T14:39:51.929
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-12T14:39:53.765
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ad_relationtype','ALTER TABLE public.AD_RelationType ADD COLUMN IsReferenceTarget CHAR(1) DEFAULT ''N'' CHECK (IsReferenceTarget IN (''Y'',''N'')) NOT NULL')
;




-- 2017-09-12T14:41:26.203
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557147,559899,0,53285,0,TO_TIMESTAMP('2017-09-12 14:41:26','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','Y','N','N','N','N','N','IsReferenceTarget',140,140,0,1,1,TO_TIMESTAMP('2017-09-12 14:41:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-12T14:41:26.210
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-12T14:41:30.383
-- URL zum Konzept
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2017-09-12 14:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559899
;

-- 2017-09-12T14:41:52.125
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=45, SeqNoGrid=45,Updated=TO_TIMESTAMP('2017-09-12 14:41:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559899
;

-- 2017-09-12T14:42:04.860
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-09-12 14:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559899
;

-- 2017-09-12T14:45:26.526
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsReferenceTarget@=N',Updated=TO_TIMESTAMP('2017-09-12 14:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58071
;

-- 2017-09-12T14:46:04.524
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsReferenceTarget@=''N''',Updated=TO_TIMESTAMP('2017-09-12 14:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58071
;

-- 2017-09-12T14:46:26.998
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsReferenceTarget@=''N''',Updated=TO_TIMESTAMP('2017-09-12 14:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58067
;

-- 2017-09-12T14:49:20.012
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsReferenceTarget@=''N''',Updated=TO_TIMESTAMP('2017-09-12 14:49:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58072
;