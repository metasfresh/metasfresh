
-- 12.10.2015 14:53
-- URL zum Konzept
UPDATE AD_Table SET Name='Workpackage Processor Definition',Updated=TO_TIMESTAMP('2015-10-12 14:53:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540486
;

-- 12.10.2015 14:53
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540486
;

-- 12.10.2015 14:54
-- URL zum Konzept
UPDATE AD_Table SET Name='Workpackage Queue',Updated=TO_TIMESTAMP('2015-10-12 14:54:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540486
;

-- 12.10.2015 14:54
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540486
;

-- 12.10.2015 14:54
-- URL zum Konzept
UPDATE AD_Table SET Name='WorkPackage',Updated=TO_TIMESTAMP('2015-10-12 14:54:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540425
;

-- 12.10.2015 14:54
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540425
;

-- 12.10.2015 14:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552780,541268,0,10,540485,'N','InternalName',TO_TIMESTAMP('2015-10-12 14:57:55','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.async',100,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Interner Name',0,TO_TIMESTAMP('2015-10-12 14:57:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 12.10.2015 14:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552780 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 12.10.2015 14:58
-- URL zum Konzept
ALTER TABLE C_Queue_PackageProcessor ADD InternalName VARCHAR(100) DEFAULT NULL 
;

-- 12.10.2015 14:58
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=15, SeqNoGrid=15,Updated=TO_TIMESTAMP('2015-10-12 14:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551805
;

-- 12.10.2015 14:59
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552780,556361,0,540490,0,TO_TIMESTAMP('2015-10-12 14:59:47','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.async',0,'Y','N','Y','Y','N','N','N','N','N','l',25,25,0,TO_TIMESTAMP('2015-10-12 14:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.10.2015 14:59
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556361 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 12.10.2015 14:59
-- URL zum Konzept
UPDATE AD_Field SET Name='Interner Name',Updated=TO_TIMESTAMP('2015-10-12 14:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556361
;

-- 12.10.2015 14:59
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556361
;

-- 12.10.2015 15:30
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-10-12 15:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556361
;

