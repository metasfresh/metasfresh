--
-- first misuse the User1_ID AD_Element and add a column with that element
--

-- Jun 1, 2016 4:27 PM
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554400,613,0,10,540644,'N','User1_ID',TO_TIMESTAMP('2016-06-01 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzerdefiniertes Element Nr. 1','de.metas.esb.edi',255,'Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Nutzer 1',0,TO_TIMESTAMP('2016-06-01 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Jun 1, 2016 4:27 PM
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554400 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Jun 1, 2016 4:32 PM
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554400,556936,0,540662,0,TO_TIMESTAMP('2016-06-01 16:32:13','YYYY-MM-DD HH24:MI:SS'),100,'Nutzerdefiniertes Element Nr. 1',0,'@User1_ID@!''''','de.metas.esb.edi','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.',0,'Y','Y','Y','Y','N','N','N','Y','N','Nutzer 1',150,160,0,1,1,TO_TIMESTAMP('2016-06-01 16:32:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Jun 1, 2016 4:32 PM
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556936 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- notice the problems and switch to the new UserFlag AD_Element
--

-- Jun 2, 2016 8:52 AM
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=543082, ColumnName='UserFlag', Description='Can be used to flag records and thus make them selectable from the UI via advanced search.', Help=NULL, Name='UserFlag',Updated=TO_TIMESTAMP('2016-06-02 08:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554400
;

-- Jun 2, 2016 8:52 AM
-- URL zum Konzept
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=554400
;

-- Jun 2, 2016 8:52 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='UserFlag', Description='Can be used to flag records and thus make them selectable from the UI via advanced search.', Help=NULL WHERE AD_Column_ID=554400 AND IsCentrallyMaintained='Y'
;

-- Jun 2, 2016 8:53 AM
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@UserFlag@!''''',Updated=TO_TIMESTAMP('2016-06-02 08:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556936
;

COMMIT;

--
-- DDL
--

-- Jun 2, 2016 8:53 AM
-- URL zum Konzept
ALTER TABLE EDI_Desadv ADD UserFlag VARCHAR(255) DEFAULT NULL 
;

