-- 2017-05-16T18:30:42.370
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551521,558493,0,53272,0,TO_TIMESTAMP('2017-05-16 18:30:42','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','Y','N','N','N','N','N','Packvorschrift-Produkt Zuordnung',330,330,0,1,1,TO_TIMESTAMP('2017-05-16 18:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-16T18:30:42.379
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558493 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-05-16T18:30:51.297
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2017-05-16 18:30:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558493
;

-- 2017-05-16T18:31:12.405
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550444,558494,0,53272,0,TO_TIMESTAMP('2017-05-16 18:31:12','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','N','Menge TU',340,340,0,1,1,TO_TIMESTAMP('2017-05-16 18:31:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-16T18:31:12.407
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558494 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-05-16T18:31:25.862
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=41, SeqNoGrid=41,Updated=TO_TIMESTAMP('2017-05-16 18:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558493
;

-- 2017-05-16T18:31:30.017
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=42, SeqNoGrid=42,Updated=TO_TIMESTAMP('2017-05-16 18:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558494
;



-- 2017-05-16T18:52:36.223
-- URL zum Konzept
UPDATE AD_Field SET ColumnDisplayLength=200, DisplayLength=200,Updated=TO_TIMESTAMP('2017-05-16 18:52:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558493
;

-- 2017-05-16T18:52:40.702
-- URL zum Konzept
UPDATE AD_Field SET ColumnDisplayLength=200, DisplayLength=200,Updated=TO_TIMESTAMP('2017-05-16 18:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558494
;

-- 2017-05-16T18:59:21.993
-- URL zum Konzept
UPDATE AD_Field SET DefaultValue='N',Updated=TO_TIMESTAMP('2017-05-16 18:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556931
;

-- 2017-05-16T19:11:21.343
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556810,542486,0,19,320,'N','M_HU_LUTU_Configuration_ID',TO_TIMESTAMP('2017-05-16 19:11:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Gebindekonfiguration',0,TO_TIMESTAMP('2017-05-16 19:11:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-05-16T19:11:21.348
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556810 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;




-- 2017-05-16T19:21:18.057
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556811,542643,0,20,320,'N','IsHUPrepared',TO_TIMESTAMP('2017-05-16 19:21:17','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Set if this record is prepared from HU module perspective','de.metas.handlingunits',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','HU Prepared',0,TO_TIMESTAMP('2017-05-16 19:21:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-05-16T19:21:18.071
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556811 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

commit;

-- 2017-05-16T19:21:24.228
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inoutline','ALTER TABLE public.M_InOutLine ADD COLUMN IsHUPrepared CHAR(1) DEFAULT ''N'' CHECK (IsHUPrepared IN (''Y'',''N'')) NOT NULL')
;

commit;
-- 2017-05-16T19:24:14.597
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('m_inoutline','ALTER TABLE public.M_InOutLine ADD COLUMN M_HU_LUTU_Configuration_ID NUMERIC(10)')
;

commit;
-- 2017-05-16T19:24:15.288
-- URL zum Konzept
ALTER TABLE M_InOutLine ADD CONSTRAINT MHULUTUConfiguration_MInOutLin FOREIGN KEY (M_HU_LUTU_Configuration_ID) REFERENCES public.M_HU_LUTU_Configuration DEFERRABLE INITIALLY DEFERRED
;






commit;
-- 2017-05-16T21:01:38.851
-- URL zum Konzept
INSERT INTO t_alter_column values('m_inoutline','M_HU_PI_Item_Product_ID','NUMERIC(10)',null,null)
;

commit;

-- 2017-05-16T21:30:38.344
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551523,558495,0,53272,0,TO_TIMESTAMP('2017-05-16 21:30:38','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.handlingunits',0,'Y','Y','Y','Y','N','N','N','N','N','Manual Packing Material',330,330,0,1,1,TO_TIMESTAMP('2017-05-16 21:30:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-16T21:30:38.347
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558495 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-05-16T21:31:06.042
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=199, SeqNoGrid=199,Updated=TO_TIMESTAMP('2017-05-16 21:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558495
;

-- 2017-05-16T22:04:21.344
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540355,'-- GENERAL
M_HU_PI_Item_Product.AD_Org_ID IN (0, @AD_Org_ID@)
AND (M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)

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
(M_HU_PI_Item_Product.ValidFrom <= ''@MovementDate@''  AND ''@MovementDate@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )

',TO_TIMESTAMP('2017-05-16 22:04:21','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','M_HU_PI_Item_Product_For_Org_and_Product_and_MovementDate','S',TO_TIMESTAMP('2017-05-16 22:04:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-16T22:04:32.079
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540355,Updated=TO_TIMESTAMP('2017-05-16 22:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551521
;



