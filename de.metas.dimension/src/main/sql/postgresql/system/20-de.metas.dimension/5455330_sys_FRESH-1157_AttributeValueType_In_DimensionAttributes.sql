-- 17.01.2017 15:27
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2017-01-17 15:27:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552181
;




-- 17.01.2017 17:11
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556034,2574,0,10,540662,'N','AttributeValueType',TO_TIMESTAMP('2017-01-17 17:11:47','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL = SELECT AttributeValueType AS DefaultValue FROM  M_Attribute WHERE M_Attribute_ID = @M_Attribute_ID@','Type of Attribute Value','de.metas.dimension',25,'The Attribute Value type deternines the data/validation type','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Attribute Value Type',0,TO_TIMESTAMP('2017-01-17 17:11:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 17.01.2017 17:11
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556034 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 17.01.2017 17:13
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2017-01-17 17:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556034
;

-- 17.01.2017 17:13
-- URL zum Konzept
ALTER TABLE public.DIM_Dimension_Spec_Attribute ADD AttributeValueType VARCHAR(25) DEFAULT NULL 
;



UPDATE DIM_Dimension_Spec_Attribute  dsa set AttributeValueType = x.AttributeValueType
FROM 
(
	Select M_Attribute_ID, AttributeValueType
	FROM M_Attribute
) x
WHERE dsa.M_Attribute_ID = x.M_Attribute_ID;


-- 17.01.2017 18:31
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2017-01-17 18:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556034
;

-- 17.01.2017 18:33
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556034,557499,0,540681,0,TO_TIMESTAMP('2017-01-17 18:33:20','YYYY-MM-DD HH24:MI:SS'),100,'Type of Attribute Value',0,'U','The Attribute Value type deternines the data/validation type',0,'Y','Y','Y','Y','N','N','N','Y','Y','Attribute Value Type',35,35,0,1,1,TO_TIMESTAMP('2017-01-17 18:33:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.01.2017 18:33
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557499 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 17.01.2017 18:33
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.dimension',Updated=TO_TIMESTAMP('2017-01-17 18:33:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557499
;








-- 17.01.2017 22:45
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=326,Updated=TO_TIMESTAMP('2017-01-17 22:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556034
;

commit;

-- 17.01.2017 22:45
-- URL zum Konzept
INSERT INTO t_alter_column values('dim_dimension_spec_attribute','AttributeValueType','VARCHAR(25)',null,'NULL')
;

commit;




-- 18.01.2017 14:22
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@AttributeValueType@ = ''L''',Updated=TO_TIMESTAMP('2017-01-18 14:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555811
;

-- 18.01.2017 14:37
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=35,Updated=TO_TIMESTAMP('2017-01-18 14:37:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555811
;

-- 18.01.2017 14:37
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=30,Updated=TO_TIMESTAMP('2017-01-18 14:37:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557499
;

-- 18.01.2017 14:37
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=35,Updated=TO_TIMESTAMP('2017-01-18 14:37:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555811
;

-- 18.01.2017 14:37
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-01-18 14:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557499
;

-- 18.01.2017 14:37
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-01-18 14:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555811
;

-- 18.01.2017 14:37
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2017-01-18 14:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557499
;

-- 18.01.2017 14:38
-- URL zum Konzept
UPDATE AD_Tab SET DisplayLogic='@DIM_Dimension_Type_InternalName@=DIM_Dimension_Type_Attribute & @IsIncludeAllAttributeValues@=''N'' & @AttributeValueType@ = ''L''',Updated=TO_TIMESTAMP('2017-01-18 14:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540682
;


