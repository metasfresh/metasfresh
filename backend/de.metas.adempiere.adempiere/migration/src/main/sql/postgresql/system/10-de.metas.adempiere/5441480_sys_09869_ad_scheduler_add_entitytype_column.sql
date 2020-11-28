
-- 03.03.2016 11:14
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554179,3052,0,19,688,'N','EntityType_ID',TO_TIMESTAMP('2016-03-03 11:14:22','YYYY-MM-DD HH24:MI:SS'),100,'N','''de.metas.swat''','Systementitäts-Art','D',10,'The entity type determines the ownership of Application Dictionary entries.  The types "Dictionary" and "Adempiere" should not be used and are maintainted by Adempiere (i.e. all changes are reversed during migration to the current definition).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Entitäts-Art',0,TO_TIMESTAMP('2016-03-03 11:14:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 03.03.2016 11:14
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554179 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 03.03.2016 11:15
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=389, DefaultValue='''de.metas.swat''',Updated=TO_TIMESTAMP('2016-03-03 11:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554179
;


-- 03.03.2016 11:24
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554179,556702,0,589,0,TO_TIMESTAMP('2016-03-03 11:24:31','YYYY-MM-DD HH24:MI:SS'),100,'Systementitäts-Art',0,'de.metas.swat','The entity type determines the ownership of Application Dictionary entries.  The types "Dictionary" and "Adempiere" should not be used and are maintainted by Adempiere (i.e. all changes are reversed during migration to the current definition).',0,'Y','Y','Y','Y','N','N','N','N','Y','Entitäts-Art',35,35,0,1,1,TO_TIMESTAMP('2016-03-03 11:24:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 03.03.2016 11:24
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556702 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



COMMIT;

-- 03.03.2016 11:14
-- URL zum Konzept
ALTER TABLE AD_Scheduler ADD EntityType Character Varying(40) DEFAULT 'de.metas.swat' NOT NULL
;
