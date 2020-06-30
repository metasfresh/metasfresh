-- 06.07.2015 18:04
-- URL zum Konzept
UPDATE AD_Element SET Description='Anzahl der zu erstellenden/zu druckenden Exemplare',Updated=TO_TIMESTAMP('2015-07-06 18:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=505211
;

-- 06.07.2015 18:04
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=505211
;

-- 06.07.2015 18:04
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Copies', Name='Kopien', Description='Anzahl der zu erstellenden/zu druckenden Exemplare', Help=NULL WHERE AD_Element_ID=505211
;

-- 06.07.2015 18:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Copies', Name='Kopien', Description='Anzahl der zu erstellenden/zu druckenden Exemplare', Help=NULL, AD_Element_ID=505211 WHERE UPPER(ColumnName)='COPIES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.07.2015 18:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Copies', Name='Kopien', Description='Anzahl der zu erstellenden/zu druckenden Exemplare', Help=NULL WHERE AD_Element_ID=505211 AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:04
-- URL zum Konzept
UPDATE AD_Field SET Name='Kopien', Description='Anzahl der zu erstellenden/zu druckenden Exemplare', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=505211) AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:04
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,552574,505211,0,11,540459,'N','Copies',TO_TIMESTAMP('2015-07-06 18:04:56','YYYY-MM-DD HH24:MI:SS'),100,'N','1','Anzahl der zu erstellenden/zu druckenden Exemplare','de.metas.printing',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Kopien',0,TO_TIMESTAMP('2015-07-06 18:04:56','YYYY-MM-DD HH24:MI:SS'),100,'1',0)
;

-- 06.07.2015 18:04
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552574 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.07.2015 18:08
-- URL zum Konzept
UPDATE AD_Tab SET Name='Paket-Info',Updated=TO_TIMESTAMP('2015-07-06 18:08:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540480
;

-- 06.07.2015 18:08
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540480
;

-- 06.07.2015 18:09
-- URL zum Konzept
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2015-07-06 18:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540459
;

-- 06.07.2015 18:09
-- URL zum Konzept
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2015-07-06 18:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540459
;

-- 06.07.2015 18:11
-- URL zum Konzept
UPDATE AD_Table SET Name='Druckpaket',Updated=TO_TIMESTAMP('2015-07-06 18:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540459
;

-- 06.07.2015 18:11
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540459
;

-- 06.07.2015 18:11
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-06 18:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548272
;

-- 06.07.2015 18:11
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-06 18:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548274
;

-- 06.07.2015 18:11
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-06 18:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548269
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-06 18:12:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548264
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_Element SET Name='Druckpaket', PrintName='Druckpaket',Updated=TO_TIMESTAMP('2015-07-06 18:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541955
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541955
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_Package_ID', Name='Druckpaket', Description=NULL, Help=NULL WHERE AD_Element_ID=541955
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Package_ID', Name='Druckpaket', Description=NULL, Help=NULL, AD_Element_ID=541955 WHERE UPPER(ColumnName)='C_PRINT_PACKAGE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Package_ID', Name='Druckpaket', Description=NULL, Help=NULL WHERE AD_Element_ID=541955 AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_Field SET Name='Druckpaket', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541955) AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:12
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druckpaket', Name='Druckpaket' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541955)
;

-- 06.07.2015 18:13
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-06 18:13:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540480
;

-- 06.07.2015 18:13
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-06 18:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540479
;

-- 06.07.2015 18:13
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-06 18:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548256
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2015-07-06 18:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548787
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_Element SET Description=NULL, Name='Seitenzahl', PrintName='Seitenzahl',Updated=TO_TIMESTAMP('2015-07-06 18:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541957
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541957
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PageCount', Name='Seitenzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=541957
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PageCount', Name='Seitenzahl', Description=NULL, Help=NULL, AD_Element_ID=541957 WHERE UPPER(ColumnName)='PAGECOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PageCount', Name='Seitenzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=541957 AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_Field SET Name='Seitenzahl', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541957) AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:14
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Seitenzahl', Name='Seitenzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541957)
;

-- 06.07.2015 18:15
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-06 18:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551470
;

-- 06.07.2015 18:15
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-06 18:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551473
;

-- 06.07.2015 18:15
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-06 18:15:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551468
;

-- 06.07.2015 18:15
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-06 18:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551511
;

-- 06.07.2015 18:15
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=15, SeqNoGrid=15,Updated=TO_TIMESTAMP('2015-07-06 18:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551461
;

-- 06.07.2015 18:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-06 18:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551459
;

-- 06.07.2015 18:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=35, SeqNoGrid=35,Updated=TO_TIMESTAMP('2015-07-06 18:16:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551454
;

-- 06.07.2015 18:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-06 18:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551450
;

-- 06.07.2015 18:17
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=5, SeqNoGrid=5,Updated=TO_TIMESTAMP('2015-07-06 18:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551447
;

-- 06.07.2015 18:18
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552574,556215,0,540480,0,TO_TIMESTAMP('2015-07-06 18:18:07','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu erstellenden/zu druckenden Exemplare',0,'de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','N','Kopien',35,35,0,TO_TIMESTAMP('2015-07-06 18:18:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.07.2015 18:18
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556215 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 06.07.2015 18:18
-- URL zum Konzept
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,552574,0,TO_TIMESTAMP('2015-07-06 18:18:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',540214,550142,'Y','Y','N','Copies',200,'E',TO_TIMESTAMP('2015-07-06 18:18:40','YYYY-MM-DD HH24:MI:SS'),100,'Copies')
;

-- 06.07.2015 18:27
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=34, SeqNoGrid=34,Updated=TO_TIMESTAMP('2015-07-06 18:27:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551510
;

-- 06.07.2015 18:28
-- URL zum Konzept
UPDATE AD_Element SET Name='Druck-Warteschlangendatensatz', PrintName='Druck-Warteschlangendatensatz',Updated=TO_TIMESTAMP('2015-07-06 18:28:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541927
;

-- 06.07.2015 18:28
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541927
;

-- 06.07.2015 18:28
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Printing_Queue_ID', Name='Druck-Warteschlangendatensatz', Description=NULL, Help=NULL WHERE AD_Element_ID=541927
;

-- 06.07.2015 18:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Printing_Queue_ID', Name='Druck-Warteschlangendatensatz', Description=NULL, Help=NULL, AD_Element_ID=541927 WHERE UPPER(ColumnName)='C_PRINTING_QUEUE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.07.2015 18:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Printing_Queue_ID', Name='Druck-Warteschlangendatensatz', Description=NULL, Help=NULL WHERE AD_Element_ID=541927 AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:28
-- URL zum Konzept
UPDATE AD_Field SET Name='Druck-Warteschlangendatensatz', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541927) AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:28
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druck-Warteschlangendatensatz', Name='Druck-Warteschlangendatensatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541927)
;

-- 06.07.2015 18:32
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,552575,505211,0,11,540435,'N','Copies',TO_TIMESTAMP('2015-07-06 18:32:25','YYYY-MM-DD HH24:MI:SS'),100,'N','1','Anzahl der zu erstellenden/zu druckenden Exemplare','de.metas.printing',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Kopien',0,TO_TIMESTAMP('2015-07-06 18:32:25','YYYY-MM-DD HH24:MI:SS'),100,'1',0)
;

-- 06.07.2015 18:32
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552575 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 06.07.2015 18:33
-- URL zum Konzept
UPDATE AD_Table SET Name='Druckjob Position',Updated=TO_TIMESTAMP('2015-07-06 18:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540436
;

-- 06.07.2015 18:33
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540436
;

-- 06.07.2015 18:33
-- URL zum Konzept
UPDATE AD_Table SET Name='Druckjob',Updated=TO_TIMESTAMP('2015-07-06 18:33:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540437
;

-- 06.07.2015 18:33
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540437
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Element SET Name='Druckjob', PrintName='Druckjob',Updated=TO_TIMESTAMP('2015-07-06 18:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541929
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541929
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_Job_ID', Name='Druckjob', Description=NULL, Help=NULL WHERE AD_Element_ID=541929
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_ID', Name='Druckjob', Description=NULL, Help=NULL, AD_Element_ID=541929 WHERE UPPER(ColumnName)='C_PRINT_JOB_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_ID', Name='Druckjob', Description=NULL, Help=NULL WHERE AD_Element_ID=541929 AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Field SET Name='Druckjob', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541929) AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druckjob', Name='Druckjob' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541929)
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Element SET Name='Druckjob Position', PrintName='Druckjob Position',Updated=TO_TIMESTAMP('2015-07-06 18:34:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541928
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541928
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_Job_Line_ID', Name='Druckjob Position', Description=NULL, Help=NULL WHERE AD_Element_ID=541928
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Line_ID', Name='Druckjob Position', Description=NULL, Help=NULL, AD_Element_ID=541928 WHERE UPPER(ColumnName)='C_PRINT_JOB_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Line_ID', Name='Druckjob Position', Description=NULL, Help=NULL WHERE AD_Element_ID=541928 AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_Field SET Name='Druckjob Position', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541928) AND IsCentrallyMaintained='Y'
;

-- 06.07.2015 18:34
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druckjob Position', Name='Druckjob Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541928)
;



-- 06.07.2015 18:52
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=15, SeqNoGrid=15,Updated=TO_TIMESTAMP('2015-07-06 18:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551216
;

-- 06.07.2015 18:52
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=5, SeqNoGrid=5,Updated=TO_TIMESTAMP('2015-07-06 18:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551216
;

-- 06.07.2015 18:53
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-06 18:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551662
;



--
-- setting parent column in print-package into subtab!
--
-- 07.07.2015 12:03
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=548269,Updated=TO_TIMESTAMP('2015-07-07 12:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540480
;

DELETE FROM exp_formatline WHERE AD_Column_ID=552574;

-- 07.07.2015 12:12
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=556215
;

-- 07.07.2015 12:12
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=552574
;

-- 07.07.2015 12:12
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=552574
;




-- 07.07.2015 12:13
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,552578,505211,0,11,540458,'N','Copies',TO_TIMESTAMP('2015-07-07 12:13:12','YYYY-MM-DD HH24:MI:SS'),100,'N','1','Anzahl der zu erstellenden/zu druckenden Exemplare','de.metas.printing',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Kopien',0,TO_TIMESTAMP('2015-07-07 12:13:12','YYYY-MM-DD HH24:MI:SS'),100,'1',0)
;

-- 07.07.2015 12:13
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552578 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 07.07.2015 12:14
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552578,556217,0,540479,0,TO_TIMESTAMP('2015-07-07 12:14:59','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu erstellenden/zu druckenden Exemplare',0,'de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','Y','Kopien',8,8,0,TO_TIMESTAMP('2015-07-07 12:14:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.07.2015 12:14
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556217 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Element SET Name='Anz Druckpaket-Infos', PrintName='Anz Druckpaket-Infos',Updated=TO_TIMESTAMP('2015-07-07 12:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541958
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541958
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PackageInfoCount', Name='Anz Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL WHERE AD_Element_ID=541958
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PackageInfoCount', Name='Anz Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL, AD_Element_ID=541958 WHERE UPPER(ColumnName)='PACKAGEINFOCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PackageInfoCount', Name='Anz Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL WHERE AD_Element_ID=541958 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Field SET Name='Anz Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541958) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Anz Druckpaket-Infos', Name='Anz Druckpaket-Infos' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541958)
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Element SET Name='Anz. Druckpaket-Infos', PrintName='Anz. Druckpaket-Infos',Updated=TO_TIMESTAMP('2015-07-07 12:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541958
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541958
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PackageInfoCount', Name='Anz. Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL WHERE AD_Element_ID=541958
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PackageInfoCount', Name='Anz. Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL, AD_Element_ID=541958 WHERE UPPER(ColumnName)='PACKAGEINFOCOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PackageInfoCount', Name='Anz. Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL WHERE AD_Element_ID=541958 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_Field SET Name='Anz. Druckpaket-Infos', Description='Number of different package infos for a given print package.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541958) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 12:15
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Anz. Druckpaket-Infos', Name='Anz. Druckpaket-Infos' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541958)
;


-- 07.07.2015 12:16
-- URL zum Konzept
UPDATE AD_Element SET Name='Transaktions-ID', PrintName='Transaktions-ID',Updated=TO_TIMESTAMP('2015-07-07 12:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541956
;

-- 07.07.2015 12:16
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541956
;

-- 07.07.2015 12:16
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='TransactionID', Name='Transaktions-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=541956
;

-- 07.07.2015 12:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TransactionID', Name='Transaktions-ID', Description=NULL, Help=NULL, AD_Element_ID=541956 WHERE UPPER(ColumnName)='TRANSACTIONID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 12:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='TransactionID', Name='Transaktions-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=541956 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 12:16
-- URL zum Konzept
UPDATE AD_Field SET Name='Transaktions-ID', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541956) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 12:16
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Transaktions-ID', Name='Transaktions-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541956)
;


-- 07.07.2015 13:23
-- URL zum Konzept
UPDATE AD_Element SET Name='Druckpaket-Info', PrintName='Druckpaket-Info',Updated=TO_TIMESTAMP('2015-07-07 13:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541959
;

-- 07.07.2015 13:23
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541959
;

-- 07.07.2015 13:23
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_PackageInfo_ID', Name='Druckpaket-Info', Description='Contains details for the print package, like printer, tray, pages from/to and print service name.', Help=NULL WHERE AD_Element_ID=541959
;

-- 07.07.2015 13:23
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_PackageInfo_ID', Name='Druckpaket-Info', Description='Contains details for the print package, like printer, tray, pages from/to and print service name.', Help=NULL, AD_Element_ID=541959 WHERE UPPER(ColumnName)='C_PRINT_PACKAGEINFO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 13:23
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_PackageInfo_ID', Name='Druckpaket-Info', Description='Contains details for the print package, like printer, tray, pages from/to and print service name.', Help=NULL WHERE AD_Element_ID=541959 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:23
-- URL zum Konzept
UPDATE AD_Field SET Name='Druckpaket-Info', Description='Contains details for the print package, like printer, tray, pages from/to and print service name.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541959) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:23
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druckpaket-Info', Name='Druckpaket-Info' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541959)
;


-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Element SET Name='Druck-Job Anweisung', PrintName='Druck-Job Anweisung',Updated=TO_TIMESTAMP('2015-07-07 13:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542012
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542012
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_Job_Instructions_ID', Name='Druck-Job Anweisung', Description=NULL, Help=NULL WHERE AD_Element_ID=542012
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Instructions_ID', Name='Druck-Job Anweisung', Description=NULL, Help=NULL, AD_Element_ID=542012 WHERE UPPER(ColumnName)='C_PRINT_JOB_INSTRUCTIONS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Instructions_ID', Name='Druck-Job Anweisung', Description=NULL, Help=NULL WHERE AD_Element_ID=542012 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Field SET Name='Druck-Job Anweisung', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542012) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druck-Job Anweisung', Name='Druck-Job Anweisung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542012)
;


-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Element SET Name='Binärformat', PrintName='Binärformat',Updated=TO_TIMESTAMP('2015-07-07 13:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541965
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541965
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='BinaryFormat', Name='Binärformat', Description='Binary format of the print package (e.g. postscript vs pdf)', Help=NULL WHERE AD_Element_ID=541965
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='BinaryFormat', Name='Binärformat', Description='Binary format of the print package (e.g. postscript vs pdf)', Help=NULL, AD_Element_ID=541965 WHERE UPPER(ColumnName)='BINARYFORMAT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='BinaryFormat', Name='Binärformat', Description='Binary format of the print package (e.g. postscript vs pdf)', Help=NULL WHERE AD_Element_ID=541965 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Field SET Name='Binärformat', Description='Binary format of the print package (e.g. postscript vs pdf)', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541965) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Binärformat', Name='Binärformat' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541965)
;


-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Element SET Name='Nutzersitzung', PrintName='Nutzersitzung',Updated=TO_TIMESTAMP('2015-07-07 13:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2029
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2029
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Session_ID', Name='Nutzersitzung', Description='User Session Online or Web', Help='Online or Web Session Information' WHERE AD_Element_ID=2029
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Session_ID', Name='Nutzersitzung', Description='User Session Online or Web', Help='Online or Web Session Information', AD_Element_ID=2029 WHERE UPPER(ColumnName)='AD_SESSION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Session_ID', Name='Nutzersitzung', Description='User Session Online or Web', Help='Online or Web Session Information' WHERE AD_Element_ID=2029 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_Field SET Name='Nutzersitzung', Description='User Session Online or Web', Help='Online or Web Session Information' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2029) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Nutzersitzung', Name='Nutzersitzung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2029)
;

-- 07.07.2015 13:24
-- URL zum Konzept
UPDATE C_Queue_WorkPackage SET AD_Issue_ID=3429044, IsError='N', LastDurationMillis=982, LastEndTime=TO_TIMESTAMP('2015-07-07 13:24:52','YYYY-MM-DD HH24:MI:SS'), Processed='N', SkippedAt=TO_TIMESTAMP('2015-07-07 13:24:52','YYYY-MM-DD HH24:MI:SS'), Skipped_Count=4659, Skipped_Last_Reason='Not processed yet. Postponed!', SkipTimeoutMillis=60000,Updated=TO_TIMESTAMP('2015-07-07 13:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=2187913 WHERE C_Queue_WorkPackage_ID=1406136
;


-- 07.07.2015 13:25
-- URL zum Konzept
UPDATE AD_Table SET Name='Druckpaket',Updated=TO_TIMESTAMP('2015-07-07 13:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540458
;

-- 07.07.2015 13:25
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540458
;


-- 07.07.2015 13:25
-- URL zum Konzept
UPDATE AD_Table SET Name='Druck-Job',Updated=TO_TIMESTAMP('2015-07-07 13:25:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540437
;

-- 07.07.2015 13:25
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540437
;


-- 07.07.2015 13:25
-- URL zum Konzept
UPDATE AD_Element SET Name='Druck-Job', PrintName='Druck-Job',Updated=TO_TIMESTAMP('2015-07-07 13:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541929
;

-- 07.07.2015 13:25
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541929
;

-- 07.07.2015 13:25
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_Job_ID', Name='Druck-Job', Description=NULL, Help=NULL WHERE AD_Element_ID=541929
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_ID', Name='Druck-Job', Description=NULL, Help=NULL, AD_Element_ID=541929 WHERE UPPER(ColumnName)='C_PRINT_JOB_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_ID', Name='Druck-Job', Description=NULL, Help=NULL WHERE AD_Element_ID=541929 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Field SET Name='Druck-Job', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541929) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druck-Job', Name='Druck-Job' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541929)
;


-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Table SET Name='Druck-Job Detail',Updated=TO_TIMESTAMP('2015-07-07 13:26:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540457
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540457
;


-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Element SET Name='Druck-Job Detail', PrintName='Druck-Job Detail',Updated=TO_TIMESTAMP('2015-07-07 13:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541952
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541952
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_Job_Detail_ID', Name='Druck-Job Detail', Description=NULL, Help=NULL WHERE AD_Element_ID=541952
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Detail_ID', Name='Druck-Job Detail', Description=NULL, Help=NULL, AD_Element_ID=541952 WHERE UPPER(ColumnName)='C_PRINT_JOB_DETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Detail_ID', Name='Druck-Job Detail', Description=NULL, Help=NULL WHERE AD_Element_ID=541952 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Field SET Name='Druck-Job Detail', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541952) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druck-Job Detail', Name='Druck-Job Detail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541952)
;


-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Element SET Name='Druck-Job Position', PrintName='Druck-Job Position',Updated=TO_TIMESTAMP('2015-07-07 13:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541928
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541928
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Print_Job_Line_ID', Name='Druck-Job Position', Description=NULL, Help=NULL WHERE AD_Element_ID=541928
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Line_ID', Name='Druck-Job Position', Description=NULL, Help=NULL, AD_Element_ID=541928 WHERE UPPER(ColumnName)='C_PRINT_JOB_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Print_Job_Line_ID', Name='Druck-Job Position', Description=NULL, Help=NULL WHERE AD_Element_ID=541928 AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_Field SET Name='Druck-Job Position', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541928) AND IsCentrallyMaintained='Y'
;

-- 07.07.2015 13:26
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druck-Job Position', Name='Druck-Job Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541928)
;


-- 07.07.2015 16:39
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,552579,505211,0,11,540473,'N','Copies',TO_TIMESTAMP('2015-07-07 16:39:54','YYYY-MM-DD HH24:MI:SS'),100,'N','1','Anzahl der zu erstellenden/zu druckenden Exemplare','de.metas.printing',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Kopien',0,TO_TIMESTAMP('2015-07-07 16:39:54','YYYY-MM-DD HH24:MI:SS'),100,'1',0)
;

-- 07.07.2015 16:39
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552579 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 07.07.2015 16:39
-- URL zum Konzept
ALTER TABLE C_Print_Job_Instructions ADD Copies NUMERIC(10) DEFAULT 1 NOT NULL
;

-- 07.07.2015 16:40
-- URL zum Konzept
UPDATE AD_Tab SET Name='Job-Druckanweisung',Updated=TO_TIMESTAMP('2015-07-07 16:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540486
;

-- 07.07.2015 16:40
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540486
;

-- 07.07.2015 16:41
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-07 16:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551676
;

-- 07.07.2015 16:41
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552579,556219,0,540486,0,TO_TIMESTAMP('2015-07-07 16:41:26','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu erstellenden/zu druckenden Exemplare',0,'de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','N','Kopien',35,35,0,TO_TIMESTAMP('2015-07-07 16:41:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.07.2015 16:41
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556219 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
	

-- 08.07.2015 14:41
-- URL zum Konzept
ALTER TABLE C_Printing_Queue ADD Copies NUMERIC(10) DEFAULT 1 NOT NULL
;

-- 08.07.2015 14:42
-- URL zum Konzept
UPDATE AD_Element SET Name='Sprache', PrintName='Sprache',Updated=TO_TIMESTAMP('2015-07-08 14:42:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2159
;

-- 08.07.2015 14:42
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=2159
;

-- 08.07.2015 14:42
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Language_ID', Name='Sprache', Description=NULL, Help=NULL WHERE AD_Element_ID=2159
;

-- 08.07.2015 14:42
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Language_ID', Name='Sprache', Description=NULL, Help=NULL, AD_Element_ID=2159 WHERE UPPER(ColumnName)='AD_LANGUAGE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 08.07.2015 14:42
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Language_ID', Name='Sprache', Description=NULL, Help=NULL WHERE AD_Element_ID=2159 AND IsCentrallyMaintained='Y'
;

-- 08.07.2015 14:42
-- URL zum Konzept
UPDATE AD_Field SET Name='Sprache', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2159) AND IsCentrallyMaintained='Y'
;

-- 08.07.2015 14:42
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Sprache', Name='Sprache' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2159)
;

-- 08.07.2015 14:43
-- URL zum Konzept
UPDATE AD_Table SET Name='Druck-Warteschlange',Updated=TO_TIMESTAMP('2015-07-08 14:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540435
;

-- 08.07.2015 14:43
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540435
;

-- 08.07.2015 14:44
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-08 14:44:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551770
;

-- 08.07.2015 14:45
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552575,556221,0,540460,0,TO_TIMESTAMP('2015-07-08 14:45:34','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der zu erstellenden/zu druckenden Exemplare',0,'de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','N','Kopien',65,65,0,TO_TIMESTAMP('2015-07-08 14:45:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.07.2015 14:45
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556221 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 08.07.2015 14:46
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=92, SeqNoGrid=92,Updated=TO_TIMESTAMP('2015-07-08 14:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551680
;

-- 08.07.2015 14:46
-- URL zum Konzept
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2015-07-08 14:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540460
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551556
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551191
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551194
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551510
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551198
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551199
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551197
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551557
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551582
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551680
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551771
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551195
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551770
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556183
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551193
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551196
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551559
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551558
;

-- 08.07.2015 14:47
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-08 14:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551192
;

-- 08.07.2015 14:48
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-07-08 14:48:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540435
;

-- 08.07.2015 14:49
-- URL zum Konzept
ALTER TABLE C_Print_Package ADD Copies NUMERIC(10) DEFAULT 1 NOT NULL
;

-- 08.07.2015 14:51
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-08 14:51:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551512
;

