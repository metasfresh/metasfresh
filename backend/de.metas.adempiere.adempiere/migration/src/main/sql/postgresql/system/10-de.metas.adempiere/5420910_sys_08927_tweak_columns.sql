--
-- Field "Gutgeschriebener Betrag erneut abrechenbar" in Rechnung window
-- 02.07.2015 16:21
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-07-02 16:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556177
;

-- 02.07.2015 16:28
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=53, SeqNoGrid=53,Updated=TO_TIMESTAMP('2015-07-02 16:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556177
;


--
-- SQL column and field DocBaseType
--
-- 02.07.2015 16:35
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552573,865,0,10,318,'N','DocBaseType','(SELECT dt.DocBaseType FROM C_DocType dt WHERE dt.C_DocType_ID=COALESCE(C_Invoice.C_DocType_ID,C_Invoice.C_DocTypeTarget_ID))',TO_TIMESTAMP('2015-07-02 16:35:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Logical type of document','D',3,'The Document Base Type identifies the base or starting point for a document.  Multiple document types may share a single document base type.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Document BaseType',0,TO_TIMESTAMP('2015-07-02 16:35:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 02.07.2015 16:35
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552573 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 02.07.2015 16:36
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552573,556214,0,263,0,TO_TIMESTAMP('2015-07-02 16:36:00','YYYY-MM-DD HH24:MI:SS'),100,'Logical type of document',0,'D','The Document Base Type identifies the base or starting point for a document.  Multiple document types may share a single document base type.',0,'Y','Y','N','N','N','N','N','N','N','Document BaseType',420,440,0,TO_TIMESTAMP('2015-07-02 16:36:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 02.07.2015 16:36
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556214 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- Field "Gutgeschriebener Betrag erneut abrechenbar" in Rechnung window
--
-- 02.07.2015 16:37
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''ARC''',Updated=TO_TIMESTAMP('2015-07-02 16:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556177
;
