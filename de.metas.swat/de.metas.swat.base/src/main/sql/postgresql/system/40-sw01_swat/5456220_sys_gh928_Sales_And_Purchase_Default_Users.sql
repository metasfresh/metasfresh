-- 09.02.2017 12:19
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543274,0,'IsPurchaseContact_Default',TO_TIMESTAMP('2017-02-09 12:19:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','IsPurchaseContact_Default','IsPurchaseContact_Default',TO_TIMESTAMP('2017-02-09 12:19:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2017 12:19
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543274 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2017 12:20
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543275,0,'IsSalesContact_Default',TO_TIMESTAMP('2017-02-09 12:20:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','IsSalesContact_Default','IsSalesContact_Default',TO_TIMESTAMP('2017-02-09 12:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2017 12:20
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543275 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.02.2017 12:21
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556073,543275,0,20,114,'N','IsSalesContact_Default',TO_TIMESTAMP('2017-02-09 12:21:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.swat',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','IsSalesContact_Default',0,TO_TIMESTAMP('2017-02-09 12:21:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.02.2017 12:21
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556073 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.02.2017 12:21
-- URL zum Konzept
ALTER TABLE public.AD_User ADD IsSalesContact_Default CHAR(1) DEFAULT 'N' CHECK (IsSalesContact_Default IN ('Y','N')) NOT NULL
;

-- 09.02.2017 12:21
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556074,543274,0,20,114,'N','IsPurchaseContact_Default',TO_TIMESTAMP('2017-02-09 12:21:40','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.swat',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','IsPurchaseContact_Default',0,TO_TIMESTAMP('2017-02-09 12:21:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.02.2017 12:21
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556074 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.02.2017 12:21
-- URL zum Konzept
ALTER TABLE public.AD_User ADD IsPurchaseContact_Default CHAR(1) DEFAULT 'N' CHECK (IsPurchaseContact_Default IN ('Y','N')) NOT NULL
;

-- 09.02.2017 12:22
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2017-02-09 12:22:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556074
;

-- 09.02.2017 12:22
-- URL zum Konzept
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2017-02-09 12:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556073
;

-- 09.02.2017 12:22
-- URL zum Konzept
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2017-02-09 12:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543275
;

-- 09.02.2017 12:22
-- URL zum Konzept
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2017-02-09 12:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543274
;

-- 09.02.2017 14:12
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556073,557546,0,496,0,TO_TIMESTAMP('2017-02-09 14:12:57','YYYY-MM-DD HH24:MI:SS'),100,1,'D',0,'Y','Y','Y','Y','N','N','N','N','N','IsSalesContact_Default',265,265,1,1,TO_TIMESTAMP('2017-02-09 14:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2017 14:12
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557546 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.02.2017 14:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-02-09 14:13:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557546
;

-- 09.02.2017 14:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=260,Updated=TO_TIMESTAMP('2017-02-09 14:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=544640
;

-- 09.02.2017 14:13
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=265,Updated=TO_TIMESTAMP('2017-02-09 14:13:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557546
;

-- 09.02.2017 16:13
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556074,557547,0,496,0,TO_TIMESTAMP('2017-02-09 16:13:30','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','IsPurchaseContact_Default',255,215,0,1,1,TO_TIMESTAMP('2017-02-09 16:13:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.02.2017 16:13
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557547 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.02.2017 16:13
-- URL zum Konzept
UPDATE AD_Field SET Name='Standard Einkaufskontakt',Updated=TO_TIMESTAMP('2017-02-09 16:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557547
;

-- 09.02.2017 16:13
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=557547
;

-- 09.02.2017 16:14
-- URL zum Konzept
UPDATE AD_Field SET Name='Standard Verkaufskontakt',Updated=TO_TIMESTAMP('2017-02-09 16:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557546
;

-- 09.02.2017 16:14
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=557546
;

