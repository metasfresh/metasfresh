
-- 18.05.2016 09:09
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-05-18 09:09:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551018
;

-- 18.05.2016 09:09
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-05-18 09:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551017
;

-- 18.05.2016 09:17
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543079,0,'FullPaymentString',TO_TIMESTAMP('2016-05-18 09:17:51','YYYY-MM-DD HH24:MI:SS'),100,'Im Fall von ESR oder anderen von Zahlschein gelesenen Zahlungsaufforderungen ist dies der komplette vom Schein eingelesene String','de.metas.payment','Y','Eingelesene Zeichenkette','Eingelesene Zeichenkette',TO_TIMESTAMP('2016-05-18 09:17:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.05.2016 09:17
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543079 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 18.05.2016 09:18
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554397,543079,0,14,540608,'N','FullPaymentString',TO_TIMESTAMP('2016-05-18 09:18:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Im Fall von ESR oder anderen von Zahlschein gelesenen Zahlungsaufforderungen ist dies der komplette vom Schein eingelesene String','de.metas.payment',2000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Eingelesene Zeichenkette',0,TO_TIMESTAMP('2016-05-18 09:18:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 18.05.2016 09:18
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554397 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 18.05.2016 12:07
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554397,556934,0,540658,0,TO_TIMESTAMP('2016-05-18 12:07:00','YYYY-MM-DD HH24:MI:SS'),100,'Im Fall von ESR oder anderen von Zahlschein gelesenen Zahlungsaufforderungen ist dies der komplette vom Schein eingelesene String',0,'U',0,'Y','Y','Y','Y','N','N','N','N','N','Eingelesene Zeichenkette',70,70,0,1,1,TO_TIMESTAMP('2016-05-18 12:07:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 18.05.2016 12:07
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556934 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-05-18 12:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555332
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-05-18 12:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555331
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-05-18 12:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555334
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-05-18 12:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555335
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-05-18 12:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555336
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-05-18 12:08:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555336
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-05-18 12:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555337
;

-- 18.05.2016 12:08
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.payment', IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-05-18 12:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556934
;

-- 18.05.2016 12:09
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-05-18 12:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555336
;

-- 18.05.2016 12:09
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-05-18 12:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555335
;

COMMIT;
--------------
-- DDL

-- 18.05.2016 09:18
-- URL zum Konzept
ALTER TABLE C_Payment_Request ADD FullPaymentString VARCHAR(2000) DEFAULT NULL 
;

