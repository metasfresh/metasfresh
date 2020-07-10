-- 10.03.2016 12:49
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554221,541447,0,30,540747,'N','C_Flatrate_Term_ID',TO_TIMESTAMP('2016-03-10 12:49:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.procurement',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Pauschale - Vertragsperiode',0,TO_TIMESTAMP('2016-03-10 12:49:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 10.03.2016 12:49
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554221 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 10.03.2016 12:49
-- URL zum Konzept
UPDATE AD_Table SET Name='Lieferplanungsdatensatz',Updated=TO_TIMESTAMP('2016-03-10 12:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540747
;

-- 10.03.2016 12:49
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540747
;

-- 10.03.2016 12:50
-- URL zum Konzept
UPDATE AD_Element SET Name='Lieferplanungsdatensatz', PrintName='Lieferplanungsdatensatz',Updated=TO_TIMESTAMP('2016-03-10 12:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543021
;

-- 10.03.2016 12:50
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543021
;

-- 10.03.2016 12:50
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PMM_QtyReport_Event_ID', Name='Lieferplanungsdatensatz', Description=NULL, Help=NULL WHERE AD_Element_ID=543021
;

-- 10.03.2016 12:50
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_QtyReport_Event_ID', Name='Lieferplanungsdatensatz', Description=NULL, Help=NULL, AD_Element_ID=543021 WHERE UPPER(ColumnName)='PMM_QTYREPORT_EVENT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 10.03.2016 12:50
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_QtyReport_Event_ID', Name='Lieferplanungsdatensatz', Description=NULL, Help=NULL WHERE AD_Element_ID=543021 AND IsCentrallyMaintained='Y'
;

-- 10.03.2016 12:50
-- URL zum Konzept
UPDATE AD_Field SET Name='Lieferplanungsdatensatz', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543021) AND IsCentrallyMaintained='Y'
;

-- 10.03.2016 12:50
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferplanungsdatensatz', Name='Lieferplanungsdatensatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543021)
;

-- 10.03.2016 12:54
-- URL zum Konzept
UPDATE AD_Window SET Name='Lieferplanungsdatensatz',Updated=TO_TIMESTAMP('2016-03-10 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540286
;

-- 10.03.2016 12:54
-- URL zum Konzept
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=540286
;

-- 10.03.2016 12:54
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Lieferplanungsdatensatz',Updated=TO_TIMESTAMP('2016-03-10 12:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540690
;

-- 10.03.2016 12:54
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540690
;

-- 10.03.2016 12:54
-- URL zum Konzept
UPDATE AD_Tab SET Name='Datensatz',Updated=TO_TIMESTAMP('2016-03-10 12:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540727
;

-- 10.03.2016 12:54
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540727
;

-- 10.03.2016 12:54
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554221,556767,0,540727,0,TO_TIMESTAMP('2016-03-10 12:54:55','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.procurement',0,'Y','Y','Y','Y','N','N','N','N','N','Pauschale - Vertragsperiode',160,160,0,1,1,TO_TIMESTAMP('2016-03-10 12:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.03.2016 12:54
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556767 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 10.03.2016 12:55
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Liefervertrag',Updated=TO_TIMESTAMP('2016-03-10 12:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556767
;

-- 10.03.2016 12:55
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=556767
;

-- 10.03.2016 13:07
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554222,2395,0,20,540747,'N','IsError',TO_TIMESTAMP('2016-03-10 13:07:32','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Ein Fehler ist bei der Durchführung aufgetreten','de.metas.procurement',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Fehler',0,TO_TIMESTAMP('2016-03-10 13:07:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 10.03.2016 13:07
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554222 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 10.03.2016 13:08
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=1000,Updated=TO_TIMESTAMP('2016-03-10 13:08:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554145
;

-- 10.03.2016 13:08
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_qtyreport_event','ErrorMsg','VARCHAR(1000)',null,'NULL')
;

-- 10.03.2016 16:32
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=542132, ColumnName='M_HU_PI_Item_Product_ID', Description=NULL, Help=NULL, Name='Packvorschrift-Produkt Zuordnung',Updated=TO_TIMESTAMP('2016-03-10 16:32:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554216
;



-- 11.03.2016 10:46
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-03-11 10:46:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554216
;

COMMIT;


-- 10.03.2016 12:49
-- URL zum Konzept
ALTER TABLE PMM_QtyReport_Event ADD C_Flatrate_Term_ID NUMERIC(10) DEFAULT NULL 
;


-- 10.03.2016 13:07
-- URL zum Konzept
ALTER TABLE PMM_QtyReport_Event ADD IsError CHAR(1) DEFAULT 'N' CHECK (IsError IN ('Y','N')) NOT NULL
;

-- 10.03.2016 16:32
-- URL zum Konzept
ALTER TABLE PMM_Product ADD M_HU_PI_Item_Product_ID NUMERIC(10) NOT NULL
;

ALTER TABLE PMM_Product DROP COLUMN M_HU_PI_Item_Product
;


-- 11.03.2016 10:47
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_product','M_HU_PI_Item_Product_ID','NUMERIC(10)',null,'NULL')
;

-- 11.03.2016 10:47
-- URL zum Konzept
INSERT INTO t_alter_column values('pmm_product','M_HU_PI_Item_Product_ID',null,'NULL',null)
;


