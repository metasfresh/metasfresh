
-- 17.11.2015 15:10
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552846,53276,0,30,540614,'N','PP_Order_ID',TO_TIMESTAMP('2015-11-17 15:10:38','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Produktionsauftrag',0,TO_TIMESTAMP('2015-11-17 15:10:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 17.11.2015 15:10
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552846 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 17.11.2015 15:10
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-11-17 15:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552846
;

-- 17.11.2015 15:11
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2015-11-17 15:11:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552846
;

-- 17.11.2015 16:07
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552847,542532,0,30,53027,'N','M_Material_Tracking_ID',TO_TIMESTAMP('2015-11-17 16:07:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.materialtracking',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Material-Vorgang-ID',0,TO_TIMESTAMP('2015-11-17 16:07:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 17.11.2015 16:07
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552847 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 17.11.2015 16:07
-- URL zum Konzept
UPDATE AD_FieldGroup SET Name='Material-Vorgang',Updated=TO_TIMESTAMP('2015-11-17 16:07:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_FieldGroup_ID=540075
;

-- 17.11.2015 16:07
-- URL zum Konzept
UPDATE AD_FieldGroup_Trl SET IsTranslated='N' WHERE AD_FieldGroup_ID=540075
;

-- 17.11.2015 16:08
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-11-17 16:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554748
;

-- 17.11.2015 16:08
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552847,556417,0,53054,0,TO_TIMESTAMP('2015-11-17 16:08:53','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.materialtracking',0,'Y','Y','Y','Y','N','N','N','Y','N','Material-Vorgang-ID',535,525,0,TO_TIMESTAMP('2015-11-17 16:08:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.11.2015 16:08
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556417 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 17.11.2015 16:09
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2015-11-17 16:09:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556417
;

-- 17.11.2015 16:10
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=65, SeqNoGrid=155,Updated=TO_TIMESTAMP('2015-11-17 16:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556417
;

-- 17.11.2015 16:10
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-17 16:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53027
;

-- 17.11.2015 16:16
-- URL zum Konzept
UPDATE AD_Field SET Description='Kann vor dem Fertigstellen ausgewählt werden, um nur bestimmte HUs zuteilbar zu machen.', IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2015-11-17 16:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556417
;

-- 17.11.2015 16:16
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556417
;


-- 24.11.2015 13:09
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552846,556430,0,540622,0,TO_TIMESTAMP('2015-11-24 13:09:11','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.materialtracking',0,'Y','Y','Y','Y','N','N','N','N','Y','Produktionsauftrag',45,45,0,TO_TIMESTAMP('2015-11-24 13:09:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.11.2015 13:09
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556430 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;


COMMIT;

-- 17.11.2015 16:07
-- URL zum Konzept
ALTER TABLE PP_Order ADD M_Material_Tracking_ID NUMERIC(10) DEFAULT NULL 
;
-- 17.11.2015 15:10
-- URL zum Konzept
ALTER TABLE C_Invoice_Detail ADD PP_Order_ID NUMERIC(10) DEFAULT NULL 
;
