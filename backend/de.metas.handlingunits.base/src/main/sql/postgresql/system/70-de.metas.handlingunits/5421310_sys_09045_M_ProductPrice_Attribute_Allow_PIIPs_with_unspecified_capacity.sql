

-- 07.07.2015 16:56
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1 FROM M_HU_PI_Item_Product piip
	INNER JOIN M_ProductPrice pp ON piip.M_Product_ID=pp.M_Product_ID
		AND pp.M_ProductPrice_ID=@M_ProductPrice_ID@
	WHERE M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID=piip.M_HU_PI_Item_Product_ID
	AND piip.IsActive=''Y''
)',Updated=TO_TIMESTAMP('2015-07-07 16:56:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540211
;

-- 07.07.2015 16:58
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552580,56560,0,20,540523,'N','IsInfiniteCapacity','(select piip.IsInfiniteCapacity from M_HU_PI_Item_Product piip where piip.M_HU_PI_Item_Product_ID=M_ProductPrice_Attribute.M_HU_PI_Item_Product_ID)',TO_TIMESTAMP('2015-07-07 16:58:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pricing.attributebased',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Unbestimmte Kapazität',0,TO_TIMESTAMP('2015-07-07 16:58:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 07.07.2015 16:58
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552580 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;



-- 07.07.2015 16:58
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2015-07-07 16:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550686
;

-- 07.07.2015 16:58
-- URL zum Konzept
UPDATE M_ProductPrice_Attribute SET IsHUPrice='N' WHERE IsHUPrice IS NULL
;


-- 07.07.2015 16:59
-- URL zum Konzept
UPDATE M_ProductPrice_Attribute SET IsHUPrice='N' WHERE IsHUPrice IS NULL
;


-- 07.07.2015 17:00
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@M_HU_PI_Item_Product_ID@!0 & @IsInfiniteCapacity@=''N''',Updated=TO_TIMESTAMP('2015-07-07 17:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554263
;



-- 07.07.2015 17:01
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552580,556220,0,540527,0,TO_TIMESTAMP('2015-07-07 17:01:37','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','N','Unbestimmte Kapazität',67,120,0,TO_TIMESTAMP('2015-07-07 17:01:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 07.07.2015 17:01
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556220 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;






-- 07.07.2015 17:02
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=67,Updated=TO_TIMESTAMP('2015-07-07 17:02:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556220
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-07-07 17:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554263
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553845
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555106
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556220
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554263
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552594
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553958
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553011
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2015-07-07 17:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553006
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2015-07-07 17:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556220
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2015-07-07 17:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554263
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2015-07-07 17:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552594
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2015-07-07 17:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553958
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2015-07-07 17:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553011
;

-- 07.07.2015 17:05
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2015-07-07 17:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553006
;

COMMIT;


-- 07.07.2015 16:58
-- URL zum Konzept
INSERT INTO t_alter_column values('m_productprice_attribute','IsHUPrice','CHAR(1)',null,'N')
;


-- 07.07.2015 16:59
-- URL zum Konzept
INSERT INTO t_alter_column values('m_productprice_attribute','IsHUPrice',null,'NOT NULL',null)
;

-- 07.07.2015 16:59
-- URL zum Konzept
INSERT INTO t_alter_column values('m_productprice_attribute','IsHUPrice','CHAR(1)',null,'N')
;
