

-- 04.03.2016 08:03
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554193,541268,0,10,540521,'N','InternalName',TO_TIMESTAMP('2016-03-04 08:03:34','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',100,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Interner Name',0,TO_TIMESTAMP('2016-03-04 08:03:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.03.2016 08:03
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554193 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.03.2016 08:04
-- URL zum Konzept
UPDATE AD_Element SET Description='Generally used to give records a name that can be safely referenced from code.', Help='Hint: make the field/column read-only or not-updatable to avoid accidental changes.',Updated=TO_TIMESTAMP('2016-03-04 08:04:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541268
;

-- 04.03.2016 08:04
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=541268
;

-- 04.03.2016 08:04
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='InternalName', Name='Interner Name', Description='Generally used to give records a name that can be safely referenced from code.', Help='Hint: make the field/column read-only or not-updatable to avoid accidental changes.' WHERE AD_Element_ID=541268
;

-- 04.03.2016 08:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='InternalName', Name='Interner Name', Description='Generally used to give records a name that can be safely referenced from code.', Help='Hint: make the field/column read-only or not-updatable to avoid accidental changes.', AD_Element_ID=541268 WHERE UPPER(ColumnName)='INTERNALNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 04.03.2016 08:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='InternalName', Name='Interner Name', Description='Generally used to give records a name that can be safely referenced from code.', Help='Hint: make the field/column read-only or not-updatable to avoid accidental changes.' WHERE AD_Element_ID=541268 AND IsCentrallyMaintained='Y'
;

-- 04.03.2016 08:04
-- URL zum Konzept
UPDATE AD_Field SET Name='Interner Name', Description='Generally used to give records a name that can be safely referenced from code.', Help='Hint: make the field/column read-only or not-updatable to avoid accidental changes.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541268) AND IsCentrallyMaintained='Y'
;

-- 04.03.2016 08:04
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2016-03-04 08:04:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554193
;

-- 04.03.2016 08:05
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554193,556716,0,540522,0,TO_TIMESTAMP('2016-03-04 08:05:41','YYYY-MM-DD HH24:MI:SS'),100,'Generally used to give records a name that can be safely referenced from code.',0,'de.metas.swat','Hint: make the field/column read-only or not-updatable to avoid accidental changes.',0,'Y','Y','Y','Y','N','N','N','N','Y','Interner Name',35,35,0,1,1,TO_TIMESTAMP('2016-03-04 08:05:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.03.2016 08:05
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556716 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

COMMIT; 

-- 04.03.2016 08:03
-- URL zum Konzept
ALTER TABLE AD_JavaClass_Type ADD InternalName VARCHAR(100) DEFAULT NULL 
;
