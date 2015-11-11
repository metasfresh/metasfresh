

-- 30.10.2015 09:20
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552792,289,0,10,540530,'N','DocStatus','(select io.DocStatus from M_InOutLine iol join M_InOut io ON io.M_InOut_ID=iol.M_InOut_ID where iol.M_InOutLine_ID = M_ReceiptSchedule_Alloc.M_InOutLine_ID)',TO_TIMESTAMP('2015-10-30 09:20:38','YYYY-MM-DD HH24:MI:SS'),100,'N','The current status of the document','de.metas.inoutcandidate',2,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Belegstatus',0,TO_TIMESTAMP('2015-10-30 09:20:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 30.10.2015 09:20
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552792 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 30.10.2015 09:21
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zugeordneter Wareneingang',Updated=TO_TIMESTAMP('2015-10-30 09:21:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540530
;

-- 30.10.2015 09:21
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540530
;

--
-- docStatus-Field
--
-- 30.10.2015 09:22
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552792,556372,0,540530,0,TO_TIMESTAMP('2015-10-30 09:22:38','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',0,'de.metas.inoutcandidate','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','Y','Y','Y','N','N','N','N','Y','Belegstatus',35,35,0,TO_TIMESTAMP('2015-10-30 09:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 30.10.2015 09:22
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556372 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
-- 30.10.2015 09:25
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=150, SeqNoGrid=150,Updated=TO_TIMESTAMP('2015-10-30 09:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553169
;

-- 30.10.2015 09:26
-- URL zum Konzept
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2015-10-30 09:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540524
;

-- 30.10.2015 09:28
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=131,Updated=TO_TIMESTAMP('2015-10-30 09:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552792
;
