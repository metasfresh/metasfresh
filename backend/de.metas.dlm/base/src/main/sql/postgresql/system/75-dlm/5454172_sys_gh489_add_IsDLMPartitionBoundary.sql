-- 07.12.2016 14:57
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543253,0,'IsPartitionBoundary',TO_TIMESTAMP('2016-12-07 14:57:30','YYYY-MM-DD HH24:MI:SS'),100,'Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.','de.metas.dlm','Y','Partitionsgrenze','Partitionsgrenze',TO_TIMESTAMP('2016-12-07 14:57:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.12.2016 14:57
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543253 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 07.12.2016 14:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555687,543253,0,20,540792,'N','IsPartitionBoundary',TO_TIMESTAMP('2016-12-07 14:57:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.','de.metas.dlm',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Partitionsgrenze',0,TO_TIMESTAMP('2016-12-07 14:57:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.12.2016 14:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555687 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 07.12.2016 14:58
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555688,275,0,10,540792,'N','Description',TO_TIMESTAMP('2016-12-07 14:58:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',2000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Beschreibung',0,TO_TIMESTAMP('2016-12-07 14:58:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.12.2016 14:58
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555688 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;



-- 07.12.2016 14:59
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555687,557469,0,540764,0,TO_TIMESTAMP('2016-12-07 14:59:24','YYYY-MM-DD HH24:MI:SS'),100,'Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.',0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','N','Partitionsgrenze',50,50,0,1,1,TO_TIMESTAMP('2016-12-07 14:59:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.12.2016 14:59
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557469 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.12.2016 14:59
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555688,557470,0,540764,0,TO_TIMESTAMP('2016-12-07 14:59:37','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,0,1,1,TO_TIMESTAMP('2016-12-07 14:59:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.12.2016 14:59
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557470 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.12.2016 14:59
-- URL zum Konzept
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2016-12-07 14:59:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557470
;

-- 07.12.2016 15:07
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='IsDLMPartitionBoundary',Updated=TO_TIMESTAMP('2016-12-07 15:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543253
;

-- 07.12.2016 15:07
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.', Help=NULL WHERE AD_Element_ID=543253
;

-- 07.12.2016 15:07
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.', Help=NULL, AD_Element_ID=543253 WHERE UPPER(ColumnName)='ISDLMPARTITIONBOUNDARY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.12.2016 15:07
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.', Help=NULL WHERE AD_Element_ID=543253 AND IsCentrallyMaintained='Y'
;

-- 07.12.2016 15:11
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555689,543253,0,20,101,'N','IsDLMPartitionBoundary',TO_TIMESTAMP('2016-12-07 15:11:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.','de.metas.dlm',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Partitionsgrenze',0,TO_TIMESTAMP('2016-12-07 15:11:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.12.2016 15:11
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555689 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 07.12.2016 15:14
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555689,557471,0,101,0,TO_TIMESTAMP('2016-12-07 15:14:40','YYYY-MM-DD HH24:MI:SS'),100,'Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.',0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','N','Partitionsgrenze',520,520,0,1,1,TO_TIMESTAMP('2016-12-07 15:14:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.12.2016 15:14
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557471 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.12.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select c.IsDLMPartitionBoundary from AD_Column c where c.AD_Column_ID=DLM_Partition_Config_Reference.DLM_Referencing_Column_ID)', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-12-07 15:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555687
;

-- 07.12.2016 15:17
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2016-12-07 15:17:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555687
;

