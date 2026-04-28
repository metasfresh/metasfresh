-- 26.01.2017 19:11
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540352,TO_TIMESTAMP('2017-01-26 19:11:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dimension','Y','DIM_Barcode_OnlyStringAttributes','S',TO_TIMESTAMP('2017-01-26 19:11:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.01.2017 19:16
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(@DIM_Dimension_Spec_ID@ = (select from DIM_Dimension_Spec_ID where internalName = ''DIM_Dimension_Spec_ID'')  and  M_Attribute.AttributeValueType = ''S'')
or
(@DIM_Dimension_Spec_ID@ <> (select DIM_Dimension_Spec_ID from DIM_Dimension_Spec where internalName = ''DIM_Dimension_Spec_ID'') )',Updated=TO_TIMESTAMP('2017-01-26 19:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540352
;

-- 26.01.2017 19:17
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540352, IsUpdateable='N',Updated=TO_TIMESTAMP('2017-01-26 19:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552195
;

-- 26.01.2017 19:18
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=NULL, IsUpdateable='N',Updated=TO_TIMESTAMP('2017-01-26 19:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552195
;

-- 26.01.2017 19:18
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540352,Updated=TO_TIMESTAMP('2017-01-26 19:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552181
;

-- 26.01.2017 19:23
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(@DIM_Dimension_Spec_ID@ = (select DIM_Dimension_Spec_ID from DIM_Dimension_Spec where internalName = ''DIM_Dimension_Spec_ID'')  and  M_Attribute.AttributeValueType = ''S'')
or
(@DIM_Dimension_Spec_ID@ <> (select DIM_Dimension_Spec_ID from DIM_Dimension_Spec where internalName = ''DIM_Dimension_Spec_ID'') )',Updated=TO_TIMESTAMP('2017-01-26 19:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540352
;

-- 26.01.2017 19:27
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='(@DIM_Dimension_Spec_ID@ = (select DIM_Dimension_Spec_ID from DIM_Dimension_Spec where internalName = ''DIM_Barcode_Attributes'')  and  M_Attribute.AttributeValueType = ''S'')
or
(@DIM_Dimension_Spec_ID@ <> (select DIM_Dimension_Spec_ID from DIM_Dimension_Spec where internalName = ''DIM_Barcode_Attributes'') )',Updated=TO_TIMESTAMP('2017-01-26 19:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540352
;

