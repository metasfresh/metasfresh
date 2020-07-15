
-- 17.12.2015 09:14
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542934,0,'IsTraded',TO_TIMESTAMP('2015-12-17 09:14:46','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ','de.metas.fresh','Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen auftragsposition ein Eintrag erstellt','Y','Wird gehandelt','Wird gehandelt',TO_TIMESTAMP('2015-12-17 09:14:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.12.2015 09:14
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542934 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 17.12.2015 09:15
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552944,542934,0,20,53020,'N','IsTraded',TO_TIMESTAMP('2015-12-17 09:15:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ','de.metas.fresh',1,'Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen auftragsposition ein Eintrag erstellt','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Wird gehandelt',0,TO_TIMESTAMP('2015-12-17 09:15:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 17.12.2015 09:15
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552944 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 17.12.2015 09:15
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=319,Updated=TO_TIMESTAMP('2015-12-17 09:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552944
;


-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Element SET Name='Produziert', PrintName='Produziert',Updated=TO_TIMESTAMP('2015-12-17 09:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542424
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542424
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsManufactured', Name='Produziert', Description=NULL, Help=NULL WHERE AD_Element_ID=542424
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManufactured', Name='Produziert', Description=NULL, Help=NULL, AD_Element_ID=542424 WHERE UPPER(ColumnName)='ISMANUFACTURED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManufactured', Name='Produziert', Description=NULL, Help=NULL WHERE AD_Element_ID=542424 AND IsCentrallyMaintained='Y'
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Field SET Name='Produziert', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542424) AND IsCentrallyMaintained='Y'
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Produziert', Name='Produziert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542424)
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Element SET Name='Wird produziert', PrintName='Wird produziert',Updated=TO_TIMESTAMP('2015-12-17 09:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542424
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542424
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsManufactured', Name='Wird produziert', Description=NULL, Help=NULL WHERE AD_Element_ID=542424
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManufactured', Name='Wird produziert', Description=NULL, Help=NULL, AD_Element_ID=542424 WHERE UPPER(ColumnName)='ISMANUFACTURED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsManufactured', Name='Wird produziert', Description=NULL, Help=NULL WHERE AD_Element_ID=542424 AND IsCentrallyMaintained='Y'
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_Field SET Name='Wird produziert', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542424) AND IsCentrallyMaintained='Y'
;

-- 17.12.2015 09:16
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Wird produziert', Name='Wird produziert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542424)
;

-- 17.12.2015 09:18
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552944,556495,0,53030,0,TO_TIMESTAMP('2015-12-17 09:18:29','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ',0,'de.metas.fresh','Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen auftragsposition ein Eintrag erstellt',0,'Y','Y','Y','Y','N','N','N','N','Y','Wird gehandelt',105,175,0,TO_TIMESTAMP('2015-12-17 09:18:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.12.2015 09:18
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556495 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 17.12.2015 09:18
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=103,Updated=TO_TIMESTAMP('2015-12-17 09:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556495
;

COMMIT;


-- 17.12.2015 09:15
-- URL zum Konzept
ALTER TABLE PP_Product_Planning ADD IsTraded CHAR(1) DEFAULT NULL 
;


-- 17.12.2015 09:16
-- URL zum Konzept
INSERT INTO t_alter_column values('pp_product_planning','IsTraded','CHAR(1)',null,'NULL')
;

