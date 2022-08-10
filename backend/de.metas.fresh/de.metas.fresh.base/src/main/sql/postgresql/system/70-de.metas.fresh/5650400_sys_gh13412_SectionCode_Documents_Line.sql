-- 2022-08-10T18:02:39.009Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584013,581238,0,19,260,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-10 19:02:36','YYYY-MM-DD HH24:MI:SS'),100,'N','@M_SectionCode_ID@','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-10 19:02:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-10T18:02:39.115Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584013 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-10T18:02:39.347Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238)
;

-- 2022-08-10T18:03:49.182Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2022-08-10 19:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584013
;

-- 2022-08-10T18:04:07.253Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-10T18:04:08.276Z
-- URL zum Konzept
ALTER TABLE C_OrderLine ADD CONSTRAINT MSectionCode_COrderLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;



-- 2022-08-10T18:06:24.630Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T18:06:24.732Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T18:06:24.839Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704255
;

-- 2022-08-10T18:06:24.936Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704255)
;

-- 2022-08-10T18:06:47.642Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-08-10 19:06:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584013
;

-- 2022-08-10T18:07:14.646Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@M_SectionCode_ID@',Updated=TO_TIMESTAMP('2022-08-10 19:07:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584013
;

-- 2022-08-10T18:12:59.917Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584013,704256,0,293,TO_TIMESTAMP('2022-08-10 19:12:58','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-10 19:12:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:13:00.019Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T18:13:00.122Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T18:13:00.224Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704256
;

-- 2022-08-10T18:13:00.321Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704256)
;

-- 2022-08-10T18:14:03.419Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,704256,0,293,540927,611507,'F',TO_TIMESTAMP('2022-08-10 19:14:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',185,0,0,TO_TIMESTAMP('2022-08-10 19:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:16:42.717Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584014,581238,0,30,333,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-10 19:16:41','YYYY-MM-DD HH24:MI:SS'),100,'N','@M_SectionCode_ID@','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-10 19:16:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-10T18:16:42.824Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584014 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-10T18:16:43.136Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238)
;

-- 2022-08-10T18:17:06.265Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-10T18:17:06.761Z
-- URL zum Konzept
ALTER TABLE C_InvoiceLine ADD CONSTRAINT MSectionCode_CInvoiceLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-10T18:26:49.164Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584014,704257,0,270,TO_TIMESTAMP('2022-08-10 19:26:47','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-10 19:26:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:26:49.265Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T18:26:49.367Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T18:26:49.470Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704257
;

-- 2022-08-10T18:26:49.567Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704257)
;

-- 2022-08-10T18:27:55.186Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,704257,0,270,540023,611508,'F',TO_TIMESTAMP('2022-08-10 19:27:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',198,0,0,TO_TIMESTAMP('2022-08-10 19:27:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:31:31.600Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584014,704258,0,291,TO_TIMESTAMP('2022-08-10 19:31:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-10 19:31:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:31:31.706Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T18:31:31.811Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T18:31:31.914Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704258
;

-- 2022-08-10T18:31:32.020Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704258)
;

-- 2022-08-10T18:32:39.658Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,704258,0,291,540219,611509,'F',TO_TIMESTAMP('2022-08-10 19:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',85,0,0,TO_TIMESTAMP('2022-08-10 19:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:34:14.633Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-10 19:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611509
;

-- 2022-08-10T18:34:19.658Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-08-10 19:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611509
;

-- 2022-08-10T18:34:20.066Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-10 19:34:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549111
;

-- 2022-08-10T18:35:15.648Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2022-08-10 19:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611508
;

-- 2022-08-10T18:35:22.621Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2022-08-10 19:35:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611508
;

-- 2022-08-10T18:35:23.023Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2022-08-10 19:35:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548928
;

-- 2022-08-10T18:38:17.538Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2022-08-10 19:38:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611506
;

-- 2022-08-10T18:46:08.977Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2022-08-10 19:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611507
;

-- 2022-08-10T18:51:55.819Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584015,581238,0,30,320,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-10 19:51:54','YYYY-MM-DD HH24:MI:SS'),100,'N','@M_SectionCode_ID@','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-10 19:51:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-10T18:51:55.951Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584015 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-10T18:51:56.191Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238)
;

-- 2022-08-10T18:52:17.583Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-10T18:52:18.177Z
-- URL zum Konzept
ALTER TABLE M_InOutLine ADD CONSTRAINT MSectionCode_MInOutLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-10T18:54:19.645Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584015,704259,0,258,TO_TIMESTAMP('2022-08-10 19:54:18','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-10 19:54:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:54:19.746Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T18:54:19.849Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T18:54:19.953Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704259
;

-- 2022-08-10T18:54:20.050Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704259)
;

-- 2022-08-10T18:55:38.754Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,704259,0,258,540976,611510,'F',TO_TIMESTAMP('2022-08-10 19:55:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',145,0,0,TO_TIMESTAMP('2022-08-10 19:55:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:56:00.090Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-08-10 19:56:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611510
;

-- 2022-08-10T18:58:46.855Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584015,704260,0,297,TO_TIMESTAMP('2022-08-10 19:58:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-10 19:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T18:58:46.974Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T18:58:47.094Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T18:58:47.193Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704260
;

-- 2022-08-10T18:58:47.292Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704260)
;

-- 2022-08-10T19:01:05.149Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,704260,0,297,540078,611511,'F',TO_TIMESTAMP('2022-08-10 20:01:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',135,0,0,TO_TIMESTAMP('2022-08-10 20:01:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T19:01:21.393Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-08-10 20:01:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611511
;

-- 2022-08-10T19:01:28.472Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-08-10 20:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611511
;

-- 2022-08-10T19:01:28.871Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-08-10 20:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549119
;

-- 2022-08-10T19:06:25.573Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584016,581238,0,30,226,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-10 20:06:23','YYYY-MM-DD HH24:MI:SS'),100,'N','@M_SectionCode_ID@','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-10 20:06:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-10T19:06:25.679Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584016 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-10T19:06:25.886Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581238)
;

-- 2022-08-10T19:06:48.767Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-10T19:06:48.904Z
-- URL zum Konzept
ALTER TABLE GL_JournalLine ADD CONSTRAINT MSectionCode_GLJournalLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-10T19:07:31.895Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584016,704261,0,540855,TO_TIMESTAMP('2022-08-10 20:07:30','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-10 20:07:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T19:07:31.997Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T19:07:32.098Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T19:07:32.213Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704261
;

-- 2022-08-10T19:07:32.312Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704261)
;

-- 2022-08-10T19:09:12.998Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,704261,0,540855,540995,611512,'F',TO_TIMESTAMP('2022-08-10 20:09:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',305,0,0,TO_TIMESTAMP('2022-08-10 20:09:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T19:09:48.402Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-10 20:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611512
;

-- 2022-08-10T19:09:53.141Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-08-10 20:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611512
;

-- 2022-08-10T19:09:53.539Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-10 20:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547486
;

-- 2022-08-10T21:26:42.709Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584013,704262,0,187,TO_TIMESTAMP('2022-08-10 22:26:41','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-10 22:26:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T21:26:42.787Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=704262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-10T21:26:42.879Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238)
;

-- 2022-08-10T21:26:42.959Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=704262
;

-- 2022-08-10T21:26:43.027Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(704262)
;

-- 2022-08-10T21:28:24.349Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,704262,0,187,1000005,611513,'F',TO_TIMESTAMP('2022-08-10 22:28:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',205,0,0,TO_TIMESTAMP('2022-08-10 22:28:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-10T21:28:38.470Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2022-08-10 22:28:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611513
;

