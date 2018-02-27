-- 2017-09-15T09:58:51.789
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=5103,Updated=TO_TIMESTAMP('2017-09-15 09:58:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540747
;

-- 2017-09-15T10:14:05.188
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543421,0,'AD_Column_ReferenceTarget',TO_TIMESTAMP('2017-09-15 10:14:05','YYYY-MM-DD HH24:MI:SS'),100,'D','','Y','AD_Column_ReferenceTarget','AD_Column_ReferenceTarget',TO_TIMESTAMP('2017-09-15 10:14:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T10:14:05.194
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543421 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-15T10:18:09.613
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540371,TO_TIMESTAMP('2017-09-15 10:18:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Column_Record_ID_CtxTable','S',TO_TIMESTAMP('2017-09-15 10:18:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T10:58:02.823
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_Column.AD_Table_ID = @AD_Table_ID@ AND AD>Column.Columnname like ''%Record_ID''',Updated=TO_TIMESTAMP('2017-09-15 10:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540371
;

-- 2017-09-15T11:00:08.789
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='AD_Column.AD_Table_ID = @AD_Table_ID@ AND AD_Column.Columnname like ''%Record_ID''',Updated=TO_TIMESTAMP('2017-09-15 11:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540371
;


-- 2017-09-15T11:05:26.362
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540748,TO_TIMESTAMP('2017-09-15 11:05:26','YYYY-MM-DD HH24:MI:SS'),100,'AD_Column table','D','Y','N','AD_Column',TO_TIMESTAMP('2017-09-15 11:05:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2017-09-15T11:05:26.365
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540748 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2017-09-15T11:05:54.625
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,116,109,0,540748,101,TO_TIMESTAMP('2017-09-15 11:05:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2017-09-15 11:05:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T11:08:16.924
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='AD_Column_ReferenceTarget_ID', Name='AD_Column_ReferenceTarget_ID', PrintName='AD_Column_ReferenceTarget_ID',Updated=TO_TIMESTAMP('2017-09-15 11:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543421
;

-- 2017-09-15T11:08:16.929
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Column_ReferenceTarget_ID', Name='AD_Column_ReferenceTarget_ID', Description=NULL, Help='' WHERE AD_Element_ID=543421
;

-- 2017-09-15T11:08:16.945
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Column_ReferenceTarget_ID', Name='AD_Column_ReferenceTarget_ID', Description=NULL, Help='', AD_Element_ID=543421 WHERE UPPER(ColumnName)='AD_COLUMN_REFERENCETARGET_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-09-15T11:08:16.947
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Column_ReferenceTarget_ID', Name='AD_Column_ReferenceTarget_ID', Description=NULL, Help='' WHERE AD_Element_ID=543421 AND IsCentrallyMaintained='Y'
;

-- 2017-09-15T11:08:16.949
-- URL zum Konzept
UPDATE AD_Field SET Name='AD_Column_ReferenceTarget_ID', Description=NULL, Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543421) AND IsCentrallyMaintained='Y'
;

-- 2017-09-15T11:08:16.959
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='AD_Column_ReferenceTarget_ID', Name='AD_Column_ReferenceTarget_ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543421)
;


-- 2017-09-15T17:16:11.069
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557177,543421,0,18,540748,103,540371,'N','AD_Column_ReferenceTarget_ID',TO_TIMESTAMP('2017-09-15 17:16:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','AD_Column_ReferenceTarget_ID',0,0,TO_TIMESTAMP('2017-09-15 17:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-15T17:16:11.073
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557177 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;






-- 2017-09-15T17:30:13.355
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557177,559952,0,103,0,TO_TIMESTAMP('2017-09-15 17:30:13','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsReferenceTarget@ = ''Y''','D','',0,'Y','Y','Y','Y','N','N','N','N','N','AD_Column_ReferenceTarget_ID',140,140,0,1,1,TO_TIMESTAMP('2017-09-15 17:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-15T17:30:13.359
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-15T17:30:39.510
-- URL zum Konzept
UPDATE AD_Column SET MandatoryLogic='@IsReferenceTarget@ = ''Y''',Updated=TO_TIMESTAMP('2017-09-15 17:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557177
;

