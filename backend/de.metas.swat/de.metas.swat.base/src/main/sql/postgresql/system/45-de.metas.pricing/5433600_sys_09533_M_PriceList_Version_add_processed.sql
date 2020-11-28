
-- 17.11.2015 11:26
-- URL zum Konzept
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,54077,0,TO_TIMESTAMP('2015-11-17 11:26:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y',NULL,'de.metas.pricing','N',TO_TIMESTAMP('2015-11-17 11:26:03','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 17.11.2015 11:24
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552845,1047,0,20,295,'N','Processed',TO_TIMESTAMP('2015-11-17 11:24:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','D',1,'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Verarbeitet',0,TO_TIMESTAMP('2015-11-17 11:24:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 17.11.2015 11:24
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552845 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 17.11.2015 11:27
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552845,556416,0,238,0,TO_TIMESTAMP('2015-11-17 11:27:15','YYYY-MM-DD HH24:MI:SS'),100,'entscheided, ob die betreffende Preislistenversion zur Verwendung im System freigegeben ist',0,'de.metas.pricing',0,'Y','N','Y','Y','N','N','N','N','Y','Freigegeben',55,55,0,TO_TIMESTAMP('2015-11-17 11:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.11.2015 11:27
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556416 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 17.11.2015 11:27
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-17 11:27:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=295
;

-- 17.11.2015 11:27
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-17 11:27:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=255
;

-- 17.11.2015 11:27
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-17 11:27:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=505135
;

-- 17.11.2015 11:28
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-17 11:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540537
;

-- 17.11.2015 11:28
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-17 11:28:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=53172
;

-- 17.11.2015 11:28
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-17 11:28:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=501646
;


-- 17.11.2015 12:00
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2015-11-17 12:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552845
;

-- 17.11.2015 12:00
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2015-11-17 12:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552845
;

COMMIT;

-- 17.11.2015 11:24
-- URL zum Konzept
ALTER TABLE M_PriceList_Version ADD Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N'))
;


-- 17.11.2015 11:28
-- URL zum Konzept
INSERT INTO t_alter_column values('m_pricelist_version','Processed','CHAR(1)',null,'N')
;
-- 17.11.2015 12:00
-- URL zum Konzept
INSERT INTO t_alter_column values('m_pricelist_version','Processed','CHAR(1)',null,'N')
;
-- 17.11.2015 12:00
-- URL zum Konzept
INSERT INTO t_alter_column values('m_pricelist_version','Processed',null,'NOT NULL',null)
;

COMMIT;

-- 17.11.2015 12:00
-- URL zum Konzept
UPDATE M_PriceList_Version SET Processed='N' WHERE Processed IS NULL
;

