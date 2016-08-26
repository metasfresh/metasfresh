-- 11.08.2016 16:22
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue=NULL, DisplayLogic=NULL,Updated=TO_TIMESTAMP('2016-08-11 16:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540713
;

-- 11.08.2016 16:51
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,EntityType,Name,IsAdvancedText,AD_Table_ID,IsLazyLoading,IsCalculated,AD_Org_ID,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,IsGenericZoomKeyColumn,AD_Column_ID,Description,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence) VALUES (10,40,0,'N','N','N','Y',1,0,'Y',TO_TIMESTAMP('2016-08-11 16:51:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-08-11 16:51:07','YYYY-MM-DD HH24:MI:SS'),100,469,'Y','N','Y','N','N','N','Y','N','de.metas.handlingunits','Name','N',540675,'N','N',0,'N','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Name','N','N',555002,'Alphanumeric identifier of the entity','N','Y','N','N')
;

-- 11.08.2016 16:51
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555002 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 11.08.2016 16:51
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Namen vergeben',Updated=TO_TIMESTAMP('2016-08-11 16:51:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555002
;

-- 11.08.2016 16:51
-- URL zum Konzept
ALTER TABLE M_Material_Balance_Config ADD Name VARCHAR(40) DEFAULT 'Namen vergeben' NOT NULL
;

-- 11.08.2016 16:52
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,AD_Column_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadOnly,IsCentrallyMaintained,ColumnDisplayLength,IncludedTabHeight,Name,AD_Org_ID,Help,AD_Field_ID,Description,EntityType,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY) VALUES (540697,555002,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2016-08-11 16:52:23','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-08-11 16:52:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Y',0,0,'Name',0,'The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',557181,'Alphanumeric identifier of the entity','U','Y',160,160,1,1)
;

-- 11.08.2016 16:52
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name,Help,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Name,t.Help,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557181 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557181
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556161
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556160
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556157
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556156
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556154
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556155
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556159
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556158
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556153
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556162
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556163
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556164
;

-- 11.08.2016 16:52
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2016-08-11 16:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556151
;

-- 11.08.2016 16:53
-- URL zum Konzept
INSERT INTO AD_Reference (ValidationType,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsOrderByValue,Name,AD_Org_ID,AD_Reference_ID,EntityType) VALUES ('T',0,'Y',TO_TIMESTAMP('2016-08-11 16:53:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-08-11 16:53:01','YYYY-MM-DD HH24:MI:SS'),100,'N','M_Material_Balance_Config',0,540665,'de.metas.handlingunits')
;

-- 11.08.2016 16:53
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540665 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 11.08.2016 16:53
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Display,AD_Client_ID,UpdatedBy,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,AD_Table_ID,AD_Org_ID,EntityType) VALUES (540665,552486,555002,0,100,'Y',TO_TIMESTAMP('2016-08-11 16:53:34','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-08-11 16:53:34','YYYY-MM-DD HH24:MI:SS'),'N',540271,540675,0,'U')
;

-- 11.08.2016 16:54
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540665,Updated=TO_TIMESTAMP('2016-08-11 16:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540713
;

-- 11.08.2016 16:54
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-08-11 16:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540713
;


-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557181
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556161
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556160
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556157
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556156
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556154
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556155
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556159
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556158
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556153
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556162
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556163
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556164
;

-- 11.08.2016 17:00
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2016-08-11 17:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556151
;
