
--
-- aparently the script for dlm_partition.DateNextInspection was lost..
--
-- 22.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555666,543222,0,15,540788,'N','DateNextInspection',TO_TIMESTAMP('2016-11-22 07:31:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',7,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Nächte Prüfung der DLM-Levels',0,TO_TIMESTAMP('2016-11-22 07:31:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 22.11.2016 07:31
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555666 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 22.11.2016 07:31
-- URL zum Konzept
INSERT INTO t_alter_column values('dlm_partition','DateNextInspection','TIMESTAMP WITHOUT TIME ZONE',null,'NULL')
;

-- 22.11.2016 08:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555666,557434,0,540765,0,TO_TIMESTAMP('2016-11-22 08:40:22','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','N','Nächte Prüfung der DLM-Levels',80,80,0,1,1,TO_TIMESTAMP('2016-11-22 08:40:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.11.2016 08:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557434 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 22.11.2016 12:09
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=45, SeqNoGrid=45,Updated=TO_TIMESTAMP('2016-11-22 12:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557414
;

-- 22.11.2016 12:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2016-11-22 12:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557415
;

-- 22.11.2016 14:54
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-11-22 14:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557335
;

-- 22.11.2016 14:54
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-11-22 14:54:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557434
;
