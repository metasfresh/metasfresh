-- 04.05.2016 11:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554388,542461,0,18,540500,540745,'N','M_HU_PI_Item_Product_Override_ID',TO_TIMESTAMP('2016-05-04 11:03:25','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.procurement',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Packvorschrift-Produkt Zuordnung abw.',0,TO_TIMESTAMP('2016-05-04 11:03:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 04.05.2016 11:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554388 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 04.05.2016 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540330,'

-- GENERAL

(M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DatePromised@'' AND ( ''@DatePromised@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID 
	FROM M_HU_PI_Item i 
	WHERE i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID 
		FROM M_HU_PI_Version v 
		WHERE v.HU_UnitType = ''TU''
		)
	)
	

AND

M_HU_PI_Item_Product_ID IN
(
	SELECT M_HU_PI_Item_Product_ID  FROM M_ProductPrice_Attribute  ppa
	INNER JOIN M_ProductPrice pp ON pp.M_ProductPrice_ID = ppa.M_ProductPrice_ID
	INNER JOIN M_Product p ON p.M_Product_ID = pp.M_Product_ID
	WHERE p.M_Product_ID = @M_Product_ID@ AND ppa.IsActive = ''Y''
	AND pp.M_PriceList_Version_ID =
	(
		SELECT M_PriceList_Version.M_PriceList_Version_ID
		FROM M_PriceList_Version
		WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
		AND M_PriceList_Version.IsActive = ''Y''
		AND M_PriceList_Version.ValidFrom =
		(
			SELECT MAX(M_PriceList_Version.ValidFrom)
			FROM M_PriceList_Version
			WHERE M_PriceList_Version.M_PriceList_ID = @M_PriceList_ID@
			AND M_PriceList_Version.ValidFrom <=  ''@DatePromised@''
			GROUP BY M_PriceList_ID
		)
	)
)

',TO_TIMESTAMP('2016-05-04 11:14:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','M_HU_PI_Item_Product_For_Product_and_DatePromied','S',TO_TIMESTAMP('2016-05-04 11:14:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.05.2016 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540330,Updated=TO_TIMESTAMP('2016-05-04 11:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554388
;

-- 04.05.2016 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2016-05-04 11:14:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554388
;

-- 04.05.2016 11:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PMM_PurchaseCandidate ADD M_HU_PI_Item_Product_Override_ID NUMERIC(10) DEFAULT NULL 
;

-- 04.05.2016 11:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2016-05-04 11:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556689
;

-- 04.05.2016 11:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,554388,556920,0,540725,0,TO_TIMESTAMP('2016-05-04 11:59:44','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.procurement',0,'Y','Y','Y','Y','N','N','N','Y','N','Packvorschrift-Produkt Zuordnung abw.',85,85,1,1,TO_TIMESTAMP('2016-05-04 11:59:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 04.05.2016 11:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556920 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 04.05.2016 11:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-05-04 11:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556920
;

-- 04.05.2016 15:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2016-05-04 15:02:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556920
;

-- 04.05.2016 15:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='Y', IsUpdateable='Y',Updated=TO_TIMESTAMP('2016-05-04 15:30:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554388
;

