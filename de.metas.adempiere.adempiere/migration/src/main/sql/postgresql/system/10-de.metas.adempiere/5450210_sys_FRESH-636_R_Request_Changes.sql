-- 06.09.2016 14:01
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555039,264,0,15,417,'N','DateDelivered',TO_TIMESTAMP('2016-09-06 14:01:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, zu dem die Ware geliefert wurde','D',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Lieferdatum',0,TO_TIMESTAMP('2016-09-06 14:01:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.09.2016 14:01
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555039 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.09.2016 14:03
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-06 14:03:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555039
;

-- 06.09.2016 14:03
-- URL zum Konzept
ALTER TABLE R_Request ADD DateDelivered TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL 
;

-- 06.09.2016 14:03
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555039,557234,0,402,0,TO_TIMESTAMP('2016-09-06 14:03:54','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde',0,'de.metas.swat',0,'Y','Y','Y','Y','N','N','N','N','N','Lieferdatum',360,360,0,1,1,TO_TIMESTAMP('2016-09-06 14:03:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.09.2016 14:03
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557234 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.09.2016 14:04
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-09-06 14:04:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5173
;

-- 06.09.2016 14:04
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5173
;

-- 06.09.2016 14:04
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=5173
;

-- 06.09.2016 14:04
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-09-06 14:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5175
;

-- 06.09.2016 14:05
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5175
;

-- 06.09.2016 14:05
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=5175
;

-- 06.09.2016 14:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-09-06 14:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5191
;

-- 06.09.2016 14:05
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5191
;

-- 06.09.2016 14:05
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=5191
;

-- 06.09.2016 14:07
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=70, SeqNoGrid=70,Updated=TO_TIMESTAMP('2016-09-06 14:07:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557234
;

-- 06.09.2016 14:08
-- URL zum Konzept
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-09-06 14:08:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5436
;

-- 06.09.2016 14:41
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543174,0,'IsMaterialReturned',TO_TIMESTAMP('2016-09-06 14:41:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Rücklieferung','Rücklieferung',TO_TIMESTAMP('2016-09-06 14:41:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.09.2016 14:41
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543174 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.09.2016 14:45
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540688,TO_TIMESTAMP('2016-09-06 14:45:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Tes_No_Partially',TO_TIMESTAMP('2016-09-06 14:45:45','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 06.09.2016 14:45
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540688 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 06.09.2016 14:46
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540688,541215,TO_TIMESTAMP('2016-09-06 14:46:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Ja',TO_TIMESTAMP('2016-09-06 14:46:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','Ja')
;

-- 06.09.2016 14:46
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541215 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 06.09.2016 14:46
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540688,541216,TO_TIMESTAMP('2016-09-06 14:46:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Nein',TO_TIMESTAMP('2016-09-06 14:46:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Ja')
;

-- 06.09.2016 14:46
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541216 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 06.09.2016 14:46
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='Nein',Updated=TO_TIMESTAMP('2016-09-06 14:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541216
;

-- 06.09.2016 14:47
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540688,541217,TO_TIMESTAMP('2016-09-06 14:47:23','YYYY-MM-DD HH24:MI:SS'),100,'Partially','de.metas.swat','Y','Teilweise',TO_TIMESTAMP('2016-09-06 14:47:23','YYYY-MM-DD HH24:MI:SS'),100,'P','Teilweise')
;

-- 06.09.2016 14:47
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541217 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 06.09.2016 14:47
-- URL zum Konzept
UPDATE AD_Reference SET Name='Yes_No_Partially',Updated=TO_TIMESTAMP('2016-09-06 14:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540688
;

-- 06.09.2016 14:47
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540688
;

-- 06.09.2016 14:47
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555040,543174,0,17,540688,417,'N','IsMaterialReturned',TO_TIMESTAMP('2016-09-06 14:47:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Rücklieferung',0,TO_TIMESTAMP('2016-09-06 14:47:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 06.09.2016 14:47
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555040 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.09.2016 14:47
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2016-09-06 14:47:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555040
;

-- 06.09.2016 14:48
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-09-06 14:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11490
;

-- 06.09.2016 14:48
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=11490
;

-- 06.09.2016 14:48
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=11490
;

-- 06.09.2016 14:49
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555040,557235,0,402,0,TO_TIMESTAMP('2016-09-06 14:49:21','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.swat',0,'Y','Y','Y','Y','N','N','N','N','N','Rücklieferung',110,110,0,1,1,TO_TIMESTAMP('2016-09-06 14:49:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.09.2016 14:49
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557235 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.09.2016 14:49
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-06 14:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557235
;

-- 06.09.2016 14:50
-- URL zum Konzept
UPDATE AD_Field SET Name='Ansprechpartner',Updated=TO_TIMESTAMP('2016-09-06 14:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5170
;

-- 06.09.2016 14:50
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=5170
;

-- 06.09.2016 14:51
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-06 14:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557234
;

-- 06.09.2016 14:51
-- URL zum Konzept
ALTER TABLE R_Request ADD IsMaterialReturned CHAR(1) DEFAULT 'N' NOT NULL
;

-- 06.09.2016 15:08
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=35, SeqNoGrid=35,Updated=TO_TIMESTAMP('2016-09-06 15:08:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5192
;

-- 06.09.2016 15:09
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-09-06 15:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11449
;

-- 06.09.2016 15:09
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=11449
;

-- 06.09.2016 15:09
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=11449
;

-- 06.09.2016 15:09
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5177
;

-- 06.09.2016 15:09
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=5177
;

-- 06.09.2016 15:09
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=5196
;

-- 06.09.2016 15:09
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=5196
;

-- 06.09.2016 15:10
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=11458
;

-- 06.09.2016 15:10
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=11458
;

-- 06.09.2016 15:10
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=11454
;

-- 06.09.2016 15:10
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=11454
;

-- 06.09.2016 15:13
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-06 15:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5860
;

-- 06.09.2016 15:13
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-06 15:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11462
;

-- 06.09.2016 15:13
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-06 15:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11460
;

-- 06.09.2016 15:14
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-06 15:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5183
;

-- 06.09.2016 15:14
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-06 15:14:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5190
;

-- 06.09.2016 15:19
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=140, SeqNoGrid=140,Updated=TO_TIMESTAMP('2016-09-06 15:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5189
;

-- 06.09.2016 15:19
-- URL zum Konzept
UPDATE AD_Field SET Name='Beanstandung',Updated=TO_TIMESTAMP('2016-09-06 15:19:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5189
;

-- 06.09.2016 15:19
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=5189
;

-- 06.09.2016 15:48
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2016-09-06 15:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5182
;

-- 07.09.2016 09:58
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5799,557236,0,402,0,TO_TIMESTAMP('2016-09-07 09:58:34','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',0,'U','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','Y','N','N','N','N','N','Project',50,50,0,1,1,TO_TIMESTAMP('2016-09-07 09:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 09:58
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557236 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 09:58
-- URL zum Konzept
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2016-09-07 09:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 11:08
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2016-09-07 11:08:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 11:09
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=40, SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-09-07 11:09:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5192
;

-- 07.09.2016 11:09
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-09-07 11:09:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5170
;

-- 07.09.2016 11:09
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=70, SeqNoGrid=70,Updated=TO_TIMESTAMP('2016-09-07 11:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5174
;

-- 07.09.2016 11:10
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=80, SeqNoGrid=80,Updated=TO_TIMESTAMP('2016-09-07 11:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557234
;

-- 07.09.2016 11:10
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5437,557237,0,402,0,TO_TIMESTAMP('2016-09-07 11:10:35','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',0,'D','The Invoice Document.',0,'Y','Y','Y','Y','N','N','N','N','N','Rechnung',90,90,0,1,1,TO_TIMESTAMP('2016-09-07 11:10:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:10
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557237 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:11
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=100, SeqNoGrid=100,Updated=TO_TIMESTAMP('2016-09-07 11:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5181
;

-- 07.09.2016 11:11
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5438,557238,0,402,0,TO_TIMESTAMP('2016-09-07 11:11:59','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung',0,'D','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).',0,'Y','Y','Y','Y','N','N','N','N','N','Zahlung',110,110,0,1,1,TO_TIMESTAMP('2016-09-07 11:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:11
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557238 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:12
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=120, SeqNoGrid=120,Updated=TO_TIMESTAMP('2016-09-07 11:12:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11491
;

-- 07.09.2016 11:12
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13574,557239,0,402,0,TO_TIMESTAMP('2016-09-07 11:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization',0,'D','A Return Material Authorization may be required to accept returns and to create Credit Memos',0,'Y','Y','Y','Y','N','N','N','N','N','RMA',360,360,0,1,1,TO_TIMESTAMP('2016-09-07 11:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:12
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557239 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:12
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=130, SeqNoGrid=130,Updated=TO_TIMESTAMP('2016-09-07 11:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557239
;

-- 07.09.2016 11:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=140, SeqNoGrid=140,Updated=TO_TIMESTAMP('2016-09-07 11:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557235
;

-- 07.09.2016 11:15
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=115, SeqNoGrid=115,Updated=TO_TIMESTAMP('2016-09-07 11:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5172
;

-- 07.09.2016 11:16
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=150, SeqNoGrid=150,Updated=TO_TIMESTAMP('2016-09-07 11:16:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11179
;

-- 07.09.2016 11:17
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13498,557240,0,402,0,TO_TIMESTAMP('2016-09-07 11:17:39','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',0,'D','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','Y','N','N','N','N','N','Kostenstelle',160,160,0,1,1,TO_TIMESTAMP('2016-09-07 11:17:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:17
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557240 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:24
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-07 11:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557240
;

-- 07.09.2016 11:30
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5445,557241,0,402,0,TO_TIMESTAMP('2016-09-07 11:30:47','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on',0,'U','The Date Next Action indicates the next scheduled date for an action to occur for this request.',0,'Y','Y','Y','Y','N','N','N','N','N','Date next action',180,180,0,1,1,TO_TIMESTAMP('2016-09-07 11:30:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:30
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557241 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:30
-- URL zum Konzept
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2016-09-07 11:30:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557241
;

-- 07.09.2016 11:33
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5427,557242,0,402,0,TO_TIMESTAMP('2016-09-07 11:33:37','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request',0,'D','The Due Type indicates if this request is Due, Overdue or Scheduled.',0,'Y','Y','Y','Y','N','N','N','N','N','Due type',190,190,0,1,1,TO_TIMESTAMP('2016-09-07 11:33:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:33
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557242 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:33
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 11:33:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557242
;

-- 07.09.2016 11:35
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNo=200, SeqNoGrid=200,Updated=TO_TIMESTAMP('2016-09-07 11:35:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5182
;

-- 07.09.2016 11:35
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=210, SeqNoGrid=210,Updated=TO_TIMESTAMP('2016-09-07 11:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5860
;

-- 07.09.2016 11:37
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13482,557244,0,402,0,TO_TIMESTAMP('2016-09-07 11:37:01','YYYY-MM-DD HH24:MI:SS'),100,'Request Group',0,'D','Group of requests (e.g. version numbers, responsibility, ...)',0,'Y','Y','Y','Y','N','N','N','N','N','Group',360,360,0,1,1,TO_TIMESTAMP('2016-09-07 11:37:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:37
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557244 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:38
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555041,542322,0,10,417,'N','QualityNote',TO_TIMESTAMP('2016-09-07 11:38:25','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',250,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Qualität-Notiz',0,TO_TIMESTAMP('2016-09-07 11:38:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.09.2016 11:38
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555041 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 07.09.2016 11:38
-- URL zum Konzept
ALTER TABLE R_Request ADD QualityNote VARCHAR(250) DEFAULT NULL 
;

-- 07.09.2016 11:39
-- URL zum Konzept
UPDATE AD_Field SET Name='Projekt',Updated=TO_TIMESTAMP('2016-09-07 11:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557244
;

-- 07.09.2016 11:39
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=557244
;

-- 07.09.2016 11:39
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=220, SeqNoGrid=220,Updated=TO_TIMESTAMP('2016-09-07 11:39:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557244
;

-- 07.09.2016 11:40
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555041,557245,0,402,0,TO_TIMESTAMP('2016-09-07 11:40:23','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Qualität-Notiz',230,230,0,1,1,TO_TIMESTAMP('2016-09-07 11:40:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:40
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557245 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:47
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=240, SeqNoGrid=240,Updated=TO_TIMESTAMP('2016-09-07 11:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11462
;

-- 07.09.2016 11:50
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=240, SeqNoGrid=240,Updated=TO_TIMESTAMP('2016-09-07 11:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11460
;

-- 07.09.2016 11:51
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=250, SeqNoGrid=250,Updated=TO_TIMESTAMP('2016-09-07 11:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557245
;

-- 07.09.2016 11:52
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=260, SeqNoGrid=260,Updated=TO_TIMESTAMP('2016-09-07 11:52:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5183
;

-- 07.09.2016 11:52
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13486,557247,0,402,0,TO_TIMESTAMP('2016-09-07 11:52:57','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User',0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','User Importance',270,270,0,1,1,TO_TIMESTAMP('2016-09-07 11:52:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 11:52
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557247 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 11:53
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 11:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557247
;

-- 07.09.2016 11:55
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=320, SeqNoGrid=320,Updated=TO_TIMESTAMP('2016-09-07 11:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11462
;

-- 07.09.2016 11:56
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=280, SeqNoGrid=280,Updated=TO_TIMESTAMP('2016-09-07 11:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5190
;

-- 07.09.2016 11:57
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=290, SeqNoGrid=290250,Updated=TO_TIMESTAMP('2016-09-07 11:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5169
;

-- 07.09.2016 11:57
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=300, SeqNoGrid=300,Updated=TO_TIMESTAMP('2016-09-07 11:57:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11461
;

-- 07.09.2016 11:57
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=290,Updated=TO_TIMESTAMP('2016-09-07 11:57:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5169
;

-- 07.09.2016 11:57
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=310, SeqNoGrid=310,Updated=TO_TIMESTAMP('2016-09-07 11:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5189
;

-- 07.09.2016 11:58
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=330, SeqNoGrid=330,Updated=TO_TIMESTAMP('2016-09-07 11:58:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5180
;

-- 07.09.2016 12:01
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=340, SeqNoGrid=340,Updated=TO_TIMESTAMP('2016-09-07 12:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5176
;

-- 07.09.2016 12:01
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=350, SeqNoGrid=350,Updated=TO_TIMESTAMP('2016-09-07 12:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5180
;

-- 07.09.2016 12:01
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=360, SeqNoGrid=360,Updated=TO_TIMESTAMP('2016-09-07 12:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5842
;

-- 07.09.2016 12:01
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=370, SeqNoGrid=370,Updated=TO_TIMESTAMP('2016-09-07 12:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5841
;

-- 07.09.2016 12:02
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=380, SeqNoGrid=380,Updated=TO_TIMESTAMP('2016-09-07 12:02:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5844
;

-- 07.09.2016 12:02
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=390, SeqNoGrid=390,Updated=TO_TIMESTAMP('2016-09-07 12:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5843
;

-- 07.09.2016 12:04
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=105, SeqNo=400, SeqNoGrid=400,Updated=TO_TIMESTAMP('2016-09-07 12:04:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5184
;

-- 07.09.2016 12:17
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 12:17:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557237
;

-- 07.09.2016 12:17
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 12:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557238
;

-- 07.09.2016 12:18
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 12:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5172
;

-- 07.09.2016 12:18
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 12:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557239
;

-- 07.09.2016 12:18
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 12:18:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5860
;

-- 07.09.2016 12:19
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 12:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557244
;

-- 07.09.2016 12:20
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-09-07 12:20:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11460
;

-- 07.09.2016 13:13
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 13:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557245
;

-- 07.09.2016 13:15
-- URL zum Konzept
UPDATE AD_Field SET Name='Beanstandung',Updated=TO_TIMESTAMP('2016-09-07 13:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11460
;

-- 07.09.2016 13:15
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=11460
;

-- 07.09.2016 13:15
-- URL zum Konzept
UPDATE AD_Field SET Name='Ergebnis',Updated=TO_TIMESTAMP('2016-09-07 13:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5189
;

-- 07.09.2016 13:15
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=5189
;

-- 07.09.2016 13:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 13:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5180
;

-- 07.09.2016 13:17
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-09-07 13:17:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5184
;

-- 07.09.2016 14:10
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555042,541268,0,10,529,'N','InternalName',TO_TIMESTAMP('2016-09-07 14:10:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Generally used to give records a name that can be safely referenced from code.','de.metas.swat',250,'Hint: make the field/column read-only or not-updatable to avoid accidental changes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Interner Name',0,TO_TIMESTAMP('2016-09-07 14:10:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.09.2016 14:10
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555042 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 07.09.2016 14:10
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2016-09-07 14:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555042
;

-- 07.09.2016 14:12
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555042,557248,0,437,0,TO_TIMESTAMP('2016-09-07 14:12:00','YYYY-MM-DD HH24:MI:SS'),100,'Generally used to give records a name that can be safely referenced from code.',0,'D','Hint: make the field/column read-only or not-updatable to avoid accidental changes.',0,'Y','Y','Y','Y','N','N','N','N','N','Interner Name',210,210,0,1,1,TO_TIMESTAMP('2016-09-07 14:12:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 14:12
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557248 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 14:12
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-09-07 14:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557248
;

-- 07.09.2016 14:14
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 14:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557248
;

-- 07.09.2016 14:15
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=35, SeqNoGrid=35,Updated=TO_TIMESTAMP('2016-09-07 14:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557248
;

-- 07.09.2016 14:16
-- URL zum Konzept
ALTER TABLE R_RequestType ADD InternalName VARCHAR(250) DEFAULT NULL 
;

-- 07.09.2016 14:18
-- URL zum Konzept
UPDATE AD_Field SET DisplayLength=0,Updated=TO_TIMESTAMP('2016-09-07 14:18:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5864
;

-- 07.09.2016 14:19
-- URL zum Konzept
UPDATE AD_Field SET SpanX=1,Updated=TO_TIMESTAMP('2016-09-07 14:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5864
;

-- 07.09.2016 14:21
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2016-09-07 14:21:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557248
;

-- 07.09.2016 14:44
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543175,0,'R_RequestType_InternalName',TO_TIMESTAMP('2016-09-07 14:44:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Request Type Interner Name','Request Type Interner Name',TO_TIMESTAMP('2016-09-07 14:44:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 14:44
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543175 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 07.09.2016 14:44
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555043,543175,0,10,417,'N','R_RequestType_InternalName',TO_TIMESTAMP('2016-09-07 14:44:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',250,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Request Type Interner Name',0,TO_TIMESTAMP('2016-09-07 14:44:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.09.2016 14:44
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555043 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 07.09.2016 14:45
-- URL zum Konzept
ALTER TABLE R_Request ADD R_RequestType_InternalName VARCHAR(250) DEFAULT NULL 
;

-- 07.09.2016 14:49
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555043,557249,0,402,0,TO_TIMESTAMP('2016-09-07 14:49:49','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','Request Type Interner Name',215,410,0,1,1,TO_TIMESTAMP('2016-09-07 14:49:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.09.2016 14:49
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557249 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 07.09.2016 14:50
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-09-07 14:50:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557249
;

-- 07.09.2016 14:54
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-09-07 14:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557249
;

-- 07.09.2016 15:11
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=355, SeqNoGrid=355,Updated=TO_TIMESTAMP('2016-09-07 15:11:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5180
;

-- 07.09.2016 15:12
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=105,Updated=TO_TIMESTAMP('2016-09-07 15:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5195
;

-- 07.09.2016 15:12
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-07 15:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557247
;

-- 07.09.2016 15:12
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-07 15:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557245
;

-- 07.09.2016 15:12
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-07 15:12:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557244
;

-- 07.09.2016 15:12
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-07 15:12:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557249
;

-- 07.09.2016 15:12
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-07 15:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557242
;

-- 07.09.2016 15:13
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=114,Updated=TO_TIMESTAMP('2016-09-07 15:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557241
;



-- 07.09.2016 15:20
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaintt'')',Updated=TO_TIMESTAMP('2016-09-07 15:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:23
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaintt'')',Updated=TO_TIMESTAMP('2016-09-07 15:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557240
;

-- 07.09.2016 15:24
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:24:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557240
;

-- 07.09.2016 15:24
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:24
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557241
;

-- 07.09.2016 15:24
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557237
;

-- 07.09.2016 15:25
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5181
;

-- 07.09.2016 15:25
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557242
;

-- 07.09.2016 15:25
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557244
;

-- 07.09.2016 15:25
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557247
;

-- 07.09.2016 15:27
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName/''''@ ! ''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:29
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName/''''@ !''A_CustomerComplaint'') & (@R_RequestType_InternalName@ ! ''B_VendorComplaint'')',Updated=TO_TIMESTAMP('2016-09-07 15:29:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:29
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName/''''@ !"A_CustomerComplaint") & (@R_RequestType_InternalName@ ! "B_VendorComplaint")',Updated=TO_TIMESTAMP('2016-09-07 15:29:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:30
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName@ !"A_CustomerComplaint") & (@R_RequestType_InternalName@ ! "B_VendorComplaint")',Updated=TO_TIMESTAMP('2016-09-07 15:30:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:33
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:36
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-09-07 15:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555043
;

-- 07.09.2016 15:37
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2016-09-07 15:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555043
;

-- 07.09.2016 15:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557237
;

-- 07.09.2016 15:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:45:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5181
;

-- 07.09.2016 15:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557240
;

-- 07.09.2016 15:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557241
;

-- 07.09.2016 15:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:45:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557242
;

-- 07.09.2016 15:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557244
;

-- 07.09.2016 15:45
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ !''A_CustomerComplaint'' & @R_RequestType_InternalName@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:45:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557247
;

-- 07.09.2016 15:46
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName@ =''A_CustomerComplaint'' | @R_RequestType_InternalName@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557235
;

-- 07.09.2016 15:48
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/''_''@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/''_''@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:48
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:48:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 07.09.2016 15:55
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557237
;

-- 07.09.2016 15:55
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5181
;

-- 07.09.2016 15:55
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557240
;

-- 07.09.2016 15:55
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:55:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557241
;

-- 07.09.2016 15:55
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557242
;

-- 07.09.2016 15:55
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557244
;

-- 07.09.2016 15:55
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:55:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557247
;

-- 07.09.2016 15:56
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557235
;

-- 07.09.2016 15:57
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5182
;

-- 07.09.2016 15:57
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:57:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557245
;

-- 07.09.2016 15:57
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 15:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557234
;

-- 07.09.2016 15:59
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-09-07 15:59:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5860
;


-- 07.09.2016 16:31
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2016-09-07 16:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555042
;

-- 07.09.2016 17:13
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-07 17:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11988
;

-- 07.09.2016 17:14
-- URL zum Konzept
UPDATE AD_Field SET Name='Beleg Nr',Updated=TO_TIMESTAMP('2016-09-07 17:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11993
;

-- 07.09.2016 17:14
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=11993
;

-- 07.09.2016 17:15
-- URL zum Konzept
UPDATE AD_Field SET Name='Erstellt',Updated=TO_TIMESTAMP('2016-09-07 17:15:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11999
;

-- 07.09.2016 17:15
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=11999
;

-- 07.09.2016 17:15
-- URL zum Konzept
UPDATE AD_Field SET Name='Erstellt durch',Updated=TO_TIMESTAMP('2016-09-07 17:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12000
;

-- 07.09.2016 17:15
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=12000
;

-- 07.09.2016 17:26
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N', IsSameLine='N',Updated=TO_TIMESTAMP('2016-09-07 17:26:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11988
;

-- 07.09.2016 17:33
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2016-09-07 17:33:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11988
;

-- 07.09.2016 17:35
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2016-09-07 17:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11988
;

-- 08.09.2016 11:08
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555039
;

-- 08.09.2016 11:08
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555040
;

-- 08.09.2016 11:08
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555039
;

-- 08.09.2016 11:08
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555040
;

-- 08.09.2016 11:11
-- URL zum Konzept
UPDATE AD_Reference SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:11:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540688
;

-- 08.09.2016 11:11
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:11:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541216
;

-- 08.09.2016 11:11
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541217
;

-- 08.09.2016 11:11
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:11:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541215
;

-- 08.09.2016 11:20
-- URL zum Konzept
UPDATE AD_Element SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2016-09-08 11:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543174
;

-- 09.09.2016 15:58
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint''',Updated=TO_TIMESTAMP('2016-09-09 15:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557238
;

