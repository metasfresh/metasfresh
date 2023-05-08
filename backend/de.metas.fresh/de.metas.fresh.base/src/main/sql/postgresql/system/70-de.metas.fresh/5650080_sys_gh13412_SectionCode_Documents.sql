-- 2022-08-08T15:51:48.108Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584003,581238,0,30,335,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-08 16:51:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-08 16:51:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-08T15:51:48.209Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584003 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-08T15:51:48.425Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-08-08T15:52:16.361Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-08-08 16:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584003
;

-- 2022-08-08T15:54:35.929Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-08T15:54:36.149Z
-- URL zum Konzept
ALTER TABLE C_Payment ADD CONSTRAINT MSectionCode_CPayment FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-08T15:55:54.466Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584003,703924,0,330,TO_TIMESTAMP('2022-08-08 16:55:53','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 16:55:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T15:55:54.564Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T15:55:54.661Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T15:55:54.764Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703924
;

-- 2022-08-08T15:55:54.859Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703924)
;

-- 2022-08-08T15:58:17.949Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703924,0,330,540957,611340,'F',TO_TIMESTAMP('2022-08-08 16:58:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-08-08 16:58:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:06:07.138Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-08 17:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611340
;

-- 2022-08-08T16:06:07.522Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-08 17:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541152
;

-- 2022-08-08T16:06:07.900Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-08 17:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547168
;

-- 2022-08-08T16:19:15.799Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584004,581238,0,19,259,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-08 17:19:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-08 17:19:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-08T16:19:15.903Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584004 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-08T16:19:16.119Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-08-08T16:20:22.677Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-08T16:20:23.710Z
-- URL zum Konzept
ALTER TABLE C_Order ADD CONSTRAINT MSectionCode_COrder FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-08T16:22:02.817Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584004,703925,0,186,TO_TIMESTAMP('2022-08-08 17:22:01','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 17:22:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:22:02.923Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T16:22:03.022Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T16:22:03.121Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703925
;

-- 2022-08-08T16:22:03.216Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703925)
;

-- 2022-08-08T16:23:16.420Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703925,0,186,1000009,611341,'F',TO_TIMESTAMP('2022-08-08 17:23:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-08-08 17:23:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:24:11.985Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-08-08 17:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584004
;

-- 2022-08-08T16:24:56.087Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-08 17:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611341
;

-- 2022-08-08T16:24:56.465Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-08 17:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544788
;

-- 2022-08-08T16:24:56.852Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-08 17:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 2022-08-08T16:33:03.657Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584005,581238,0,30,319,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-08 17:33:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-08 17:33:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-08T16:33:03.761Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584005 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-08T16:33:03.960Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-08-08T16:33:26.578Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-08T16:33:27.123Z
-- URL zum Konzept
ALTER TABLE M_InOut ADD CONSTRAINT MSectionCode_MInOut FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-08T16:35:20.166Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584005,703926,0,257,TO_TIMESTAMP('2022-08-08 17:35:18','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 17:35:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:35:20.264Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T16:35:20.360Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T16:35:20.456Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703926
;

-- 2022-08-08T16:35:20.548Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703926)
;

-- 2022-08-08T16:36:43.798Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703926,0,257,540019,611342,'F',TO_TIMESTAMP('2022-08-08 17:36:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',7,0,0,TO_TIMESTAMP('2022-08-08 17:36:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:37:02.219Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-08 17:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611342
;

-- 2022-08-08T16:37:02.597Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-08-08 17:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564599
;

-- 2022-08-08T16:37:02.976Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-08-08 17:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540617
;

-- 2022-08-08T16:38:28.105Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-08 17:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564599
;

-- 2022-08-08T16:38:28.491Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-08-08 17:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611342
;

-- 2022-08-08T16:46:52.025Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584006,581238,0,30,318,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-08 17:46:50','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-08 17:46:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-08T16:46:52.129Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584006 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-08T16:46:52.361Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-08-08T16:47:50.210Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-08T16:47:50.998Z
-- URL zum Konzept
ALTER TABLE C_Invoice ADD CONSTRAINT MSectionCode_CInvoice FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-08T16:50:44.753Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584006,703927,0,263,TO_TIMESTAMP('2022-08-08 17:50:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 17:50:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:50:44.851Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T16:50:44.949Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T16:50:45.045Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703927
;

-- 2022-08-08T16:50:45.137Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703927)
;

-- 2022-08-08T16:51:43.173Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703927,0,263,540028,611343,'F',TO_TIMESTAMP('2022-08-08 17:51:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-08-08 17:51:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:52:09.593Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-08 17:52:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611343
;

-- 2022-08-08T16:52:10Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-08 17:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564735
;

-- 2022-08-08T16:52:10.379Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-08 17:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540791
;

-- 2022-08-08T17:17:36.141Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584004,703928,0,294,TO_TIMESTAMP('2022-08-08 18:17:34','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 18:17:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:17:36.247Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T17:17:36.466Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T17:17:36.652Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703928
;

-- 2022-08-08T17:17:37.101Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703928)
;

-- 2022-08-08T17:20:27.955Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703928,0,294,540074,611344,'F',TO_TIMESTAMP('2022-08-08 18:20:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',30,0,0,TO_TIMESTAMP('2022-08-08 18:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:20:44.653Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-08-08 18:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611344
;

-- 2022-08-08T17:20:45.101Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-08-08 18:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541286
;

-- 2022-08-08T17:23:52.983Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2022-08-08 18:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611344
;

-- 2022-08-08T17:29:11.829Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584005,703929,0,296,TO_TIMESTAMP('2022-08-08 18:29:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 18:29:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:29:11.949Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T17:29:12.058Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T17:29:12.170Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703929
;

-- 2022-08-08T17:29:12.715Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703929)
;

-- 2022-08-08T17:30:55.146Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703929,0,296,540082,611345,'F',TO_TIMESTAMP('2022-08-08 18:30:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',15,0,0,TO_TIMESTAMP('2022-08-08 18:30:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:31:14.988Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-08 18:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611345
;

-- 2022-08-08T17:31:19.820Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-08-08 18:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611345
;

-- 2022-08-08T17:31:20.198Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-08 18:31:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541353
;

-- 2022-08-08T17:39:42.974Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584006,703930,0,290,TO_TIMESTAMP('2022-08-08 18:39:41','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 18:39:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:39:43.072Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703930 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T17:39:43.169Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T17:39:43.265Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703930
;

-- 2022-08-08T17:39:43.356Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703930)
;

-- 2022-08-08T17:41:36.305Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703930,0,290,540227,611346,'F',TO_TIMESTAMP('2022-08-08 18:41:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-08-08 18:41:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:41:53.684Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-08 18:41:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611346
;

-- 2022-08-08T17:41:54.067Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-08-08 18:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542725
;

-- 2022-08-08T17:53:59.113Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584007,581238,0,30,224,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-08 18:53:57','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-08 18:53:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-08T17:53:59.213Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584007 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-08T17:53:59.420Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-08-08T17:54:36.441Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-08-08 18:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584007
;

-- 2022-08-08T17:54:55.318Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('GL_Journal','ALTER TABLE public.GL_Journal ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-08T17:54:55.479Z
-- URL zum Konzept
ALTER TABLE GL_Journal ADD CONSTRAINT MSectionCode_GLJournal FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-08T17:55:39.208Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584007,703931,0,540854,TO_TIMESTAMP('2022-08-08 18:55:38','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-08 18:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:55:39.305Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T17:55:39.400Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-08T17:55:39.497Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703931
;

-- 2022-08-08T17:55:39.591Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(703931)
;

-- 2022-08-08T17:56:48.022Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703931,0,540854,541000,611347,'F',TO_TIMESTAMP('2022-08-08 18:56:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',5,0,0,TO_TIMESTAMP('2022-08-08 18:56:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:57:07.234Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-08 18:57:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611347
;

-- 2022-08-08T17:57:07.620Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-08 18:57:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547528
;



