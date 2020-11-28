-- 2020-05-04T15:09:20.836Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2020-05-04 17:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559492
;

-- 2020-05-04T15:09:20.876Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2020-05-04 17:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3324
;

-- 2020-05-04T15:09:20.891Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2020-05-04 17:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3330
;

-- 2020-05-04T15:09:20.904Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2020-05-04 17:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3322
;

-- 2020-05-04T15:09:20.915Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2020-05-04 17:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3321
;

-- 2020-05-04T15:09:20.925Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2020-05-04 17:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3332
;

-- 2020-05-04T15:47:42.705Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570681,187,0,19,540862,'C_BPartner_ID','SELECT C_Bpartner_ID from C_Bpartner_Product cbp where cbp.C_Bpartner_Product_ID = C_Bpartner_Product_Trl.C_Bpartner_Product_Id',TO_TIMESTAMP('2020-05-04 17:47:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2020-05-04 17:47:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-05-04T15:47:42.727Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570681 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-05-04T15:47:42.791Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2020-05-04T15:47:49.994Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2020-05-04 17:47:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557909
;

-- 2020-05-04T15:47:50.011Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2020-05-04 17:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557915
;

-- 2020-05-04T15:47:50.022Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2020-05-04 17:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:48:25.589Z
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2020-05-04 17:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:48:35.154Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT C_Bpartner_ID from C_Bpartner_Product cbp where cbp.C_Bpartner_Product_ID = C_Bpartner_Product_Trl.C_Bpartner_Product_Id)',Updated=TO_TIMESTAMP('2020-05-04 17:48:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:49:52.511Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT cbp.C_BPartner_ID from C_Bpartner_Product cbp where cbp.C_Bpartner_Product_ID = C_Bpartner_Product_Trl.C_Bpartner_Product_Id)',Updated=TO_TIMESTAMP('2020-05-04 17:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:51:13.369Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2020-05-04 17:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557940
;

-- 2020-05-04T15:51:13.391Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2020-05-04 17:51:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:51:24.630Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570682,454,0,19,540862,'M_Product_ID',TO_TIMESTAMP('2020-05-04 17:51:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2020-05-04 17:51:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-05-04T15:51:24.662Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570682 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-05-04T15:51:24.667Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2020-05-04T15:51:41.494Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT cbp.M_Product_ID from C_Bpartner_Product cbp where cbp.C_Bpartner_Product_ID = C_Bpartner_Product_Trl.C_Bpartner_Product_Id)', IsUpdateable='N',Updated=TO_TIMESTAMP('2020-05-04 17:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570682
;

-- 2020-05-04T15:51:47.238Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2020-05-04 17:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570682
;

-- 2020-05-04T15:52:46.120Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT cbp.M_Product_ID from C_Bpartner_Product cbp where cbp.C_BPartner_Product_ID = C_Bpartner_Product_Trl.C_Bpartner_Product_Id)',Updated=TO_TIMESTAMP('2020-05-04 17:52:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570682
;

-- 2020-05-04T15:53:13.961Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2020-05-04 17:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570682
;

-- 2020-05-04T15:54:45.724Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2020-05-04 17:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570682
;

-- 2020-05-04T15:54:45.738Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2020-05-04 17:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557940
;

-- 2020-05-04T15:54:45.750Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2020-05-04 17:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:54:53.449Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2020-05-04 17:54:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:54:53.465Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2020-05-04 17:54:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557940
;

-- 2020-05-04T15:55:22.030Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2020-05-04 17:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557940
;

-- 2020-05-04T15:55:22.045Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2020-05-04 17:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570682
;

-- 2020-05-04T15:55:22.056Z
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2020-05-04 17:55:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570681
;

-- 2020-05-04T15:56:17.832Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,AD_Column_ID,Updated,UpdatedBy,ColumnDisplayLength,IncludedTabHeight,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,EntityType,AD_Org_ID,Name,Description) VALUES (541060,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-05-04 17:56:17','YYYY-MM-DD HH24:MI:SS'),100,'N',570681,TO_TIMESTAMP('2020-05-04 17:56:17','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',606063,'Y',90,20,1,1,'D',0,'Geschäftspartner','Bezeichnet einen Geschäftspartner')
;

-- 2020-05-04T15:56:17.851Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=606063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-05-04T15:56:17.854Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2020-05-04T15:56:17.948Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=606063
;

-- 2020-05-04T15:56:17.962Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(606063)
;

-- 2020-05-04T15:56:31.405Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,AD_Column_ID,Updated,UpdatedBy,ColumnDisplayLength,IncludedTabHeight,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,EntityType,AD_Org_ID,Name,Description) VALUES (541060,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-05-04 17:56:31','YYYY-MM-DD HH24:MI:SS'),100,'N',570682,TO_TIMESTAMP('2020-05-04 17:56:31','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',606064,'Y',100,30,1,1,'D',0,'Produkt','Produkt, Leistung, Artikel')
;

-- 2020-05-04T15:56:31.412Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=606064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-05-04T15:56:31.414Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2020-05-04T15:56:31.631Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=606064
;

-- 2020-05-04T15:56:31.632Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(606064)
;

-- 2020-05-04T15:56:44.360Z
-- URL zum Konzept
UPDATE AD_Field SET EntityType='U',Updated=TO_TIMESTAMP('2020-05-04 17:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=606063
;

-- 2020-05-04T15:56:50.435Z
-- URL zum Konzept
UPDATE AD_Field SET EntityType='U',Updated=TO_TIMESTAMP('2020-05-04 17:56:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=606064
;

-- 2020-05-04T15:57:11.306Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,606063,0,541060,541529,568198,'F',TO_TIMESTAMP('2020-05-04 17:57:11','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',50,0,0,TO_TIMESTAMP('2020-05-04 17:57:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-04T15:57:21.459Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,606064,0,541060,541529,568199,'F',TO_TIMESTAMP('2020-05-04 17:57:21','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',60,0,0,TO_TIMESTAMP('2020-05-04 17:57:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-04T15:58:35.578Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=568198
;

-- 2020-05-04T15:58:35.585Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551388
;

-- 2020-05-04T15:58:35.589Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=568199
;

-- 2020-05-04T15:58:35.593Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551386
;

-- 2020-05-04T15:58:35.597Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551389
;

-- 2020-05-04T15:58:35.601Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551387
;

-- 2020-05-04T15:58:35.604Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551390
;

-- 2020-05-04T15:58:35.608Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2020-05-04 17:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551385
;

-- 2020-05-04T15:58:51.997Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-05-04 17:58:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=568199
;

-- 2020-05-04T15:58:52.003Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-05-04 17:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551386
;

-- 2020-05-04T15:58:52.007Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-05-04 17:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551389
;

-- 2020-05-04T15:58:52.009Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-05-04 17:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551387
;

-- 2020-05-04T15:58:52.013Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-05-04 17:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551390
;

-- 2020-05-04T15:58:52.015Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-05-04 17:58:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551385
;

-- 2020-05-04T15:58:59.064Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=568199
;

-- 2020-05-04T15:58:59.071Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=606064
;

-- 2020-05-04T15:58:59.072Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=606064
;

-- 2020-05-04T15:58:59.075Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=606064
;

-- 2020-05-06T15:47:45.041Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-05-06 17:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552503
;

-- 2020-05-06T15:47:45.081Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-05-06 17:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551218
;

-- 2020-05-06T15:47:45.085Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2020-05-06 17:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551219
;

-- 2020-05-06T15:47:45.091Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2020-05-06 17:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551220
;

-- 2020-05-06T15:47:45.096Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2020-05-06 17:47:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551213
;


