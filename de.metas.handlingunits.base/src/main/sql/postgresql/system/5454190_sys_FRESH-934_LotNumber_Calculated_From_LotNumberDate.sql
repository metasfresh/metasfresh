-- 08.12.2016 17:20
-- URL zum Konzept
--

-- 14.12.2016 11:14
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=40,Updated=TO_TIMESTAMP('2016-12-14 11:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549455
;












INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543254,0,'LotNumberDate',TO_TIMESTAMP('2016-12-08 17:20:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Tageslot Datum','Tageslot Datum',TO_TIMESTAMP('2016-12-08 17:20:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.12.2016 17:20
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543254 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 08.12.2016 17:20
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555690,543254,0,15,259,'N','LotNumberDate',TO_TIMESTAMP('2016-12-08 17:20:46','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Tageslot Datum',0,TO_TIMESTAMP('2016-12-08 17:20:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 08.12.2016 17:20
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555690 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 08.12.2016 17:20
-- URL zum Konzept
ALTER TABLE C_Order ADD LotNumberDate TIMESTAMP WITHOUT TIME ZONE DEFAULT NULL 
;

-- 08.12.2016 17:24
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555690,557472,0,294,0,TO_TIMESTAMP('2016-12-08 17:24:47','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.swat',0,'Y','Y','Y','Y','N','N','N','N','N','Tageslot Datum',66,66,0,1,1,TO_TIMESTAMP('2016-12-08 17:24:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.12.2016 17:24
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557472 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.12.2016 19:55
-- URL zum Konzept
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540047,540026,0,'org.adempiere.mm.attributes.spi.impl.HULotNumberAttributeHandler',TO_TIMESTAMP('2016-12-09 19:55:54','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.handlingunits','Y','N','LotNumber Attribute Handler',TO_TIMESTAMP('2016-12-09 19:55:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.12.2016 19:56
-- URL zum Konzept
UPDATE M_Attribute SET AD_JavaClass_ID=540047,Updated=TO_TIMESTAMP('2016-12-09 19:56:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540022
;

-- 09.12.2016 19:57
-- URL zum Konzept
UPDATE M_Attribute SET IsAttrDocumentRelevant='Y', IsMatchHUStorage='Y',Updated=TO_TIMESTAMP('2016-12-09 19:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540022
;

-- 10.12.2016 21:08
-- URL zum Konzept
UPDATE M_Attribute SET IsAttrDocumentRelevant='N', IsMatchHUStorage='N',Updated=TO_TIMESTAMP('2016-12-10 21:08:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540022
;

-- 10.12.2016 21:15
-- URL zum Konzept
UPDATE M_Attribute SET IsInstanceAttribute='N',Updated=TO_TIMESTAMP('2016-12-10 21:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540022
;

-- 10.12.2016 21:15
-- URL zum Konzept
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2016-12-10 21:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540022
;

-- 10.12.2016 21:15
-- URL zum Konzept
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540022)
;

-- 10.12.2016 22:47
-- URL zum Konzept
UPDATE M_Attribute SET IsAttrDocumentRelevant='Y',Updated=TO_TIMESTAMP('2016-12-10 22:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540022
;

