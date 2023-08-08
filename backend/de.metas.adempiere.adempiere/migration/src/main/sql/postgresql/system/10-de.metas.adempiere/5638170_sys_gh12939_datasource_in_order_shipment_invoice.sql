-- Column: M_ShipmentSchedule.AD_InputDataSource_ID
-- 2022-05-06T07:48:22.983Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582928,541291,0,30,500221,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:48:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:48:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:48:22.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:48:23.006Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:48:23.829Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:48:23.976Z
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT ADInputDataSource_MShipmentSchedule FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.AD_InputDataSource_ID
-- 2022-05-06T07:49:02.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582929,541291,0,30,318,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:49:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:49:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:49:02.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582929 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:49:02.959Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:49:03.514Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:49:04.307Z
ALTER TABLE C_Invoice ADD CONSTRAINT ADInputDataSource_CInvoice FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Candidate.AD_InputDataSource_ID
-- 2022-05-06T07:49:56.480Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582930,541291,0,30,540270,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:49:56','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:49:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:49:56.484Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582930 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:49:56.489Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:49:57.001Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:49:57.322Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT ADInputDataSource_CInvoiceCandidate FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InOut.AD_InputDataSource_ID
-- 2022-05-06T07:50:54.944Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582931,541291,0,30,319,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:50:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:50:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:50:54.948Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582931 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:50:54.952Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:50:55.454Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:50:55.959Z
ALTER TABLE M_InOut ADD CONSTRAINT ADInputDataSource_MInOut FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.AD_InputDataSource_ID
-- 2022-05-06T09:12:48.243Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-06 11:12:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582929
;

-- Column: M_InOut.AD_InputDataSource_ID
-- 2022-05-06T09:15:50.510Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-06 11:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582931
;

-- Column: C_Order.AD_InputDataSource_ID
-- 2022-06-02T12:47:12.745Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-06-02 14:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552597
;

-- UI Element: Auftrag -> Auftrag.Eingabequelle
-- Column: C_Order.AD_InputDataSource_ID
-- 2022-06-02T12:50:31.597Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556234,0,186,1000001,608973,'F',TO_TIMESTAMP('2022-06-02 14:50:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Eingabequelle',50,0,0,TO_TIMESTAMP('2022-06-02 14:50:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Lieferung -> Lieferung -> Eingabequelle
-- Column: M_InOut.AD_InputDataSource_ID
-- 2022-06-02T12:52:17.781Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582931,698904,0,257,0,TO_TIMESTAMP('2022-06-02 14:52:17','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Eingabequelle',0,530,0,1,1,TO_TIMESTAMP('2022-06-02 14:52:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-02T12:52:17.785Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-02T12:52:17.827Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541291)
;

-- 2022-06-02T12:52:17.847Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698904
;

-- 2022-06-02T12:52:17.851Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698904)
;

-- UI Element: Lieferung -> Lieferung.Eingabequelle
-- Column: M_InOut.AD_InputDataSource_ID
-- 2022-06-02T12:53:00.839Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698904,0,257,1000029,608974,'F',TO_TIMESTAMP('2022-06-02 14:53:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Eingabequelle',65,0,0,TO_TIMESTAMP('2022-06-02 14:53:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Rechnung -> Rechnung -> Eingabequelle
-- Column: C_Invoice.AD_InputDataSource_ID
-- 2022-06-02T12:53:31.522Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582929,698905,0,263,0,TO_TIMESTAMP('2022-06-02 14:53:31','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Eingabequelle',0,510,0,1,1,TO_TIMESTAMP('2022-06-02 14:53:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-02T12:53:31.524Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-02T12:53:31.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541291)
;

-- 2022-06-02T12:53:31.537Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698905
;

-- 2022-06-02T12:53:31.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698905)
;

-- UI Element: Rechnung -> Rechnung.Eingabequelle
-- Column: C_Invoice.AD_InputDataSource_ID
-- 2022-06-02T12:54:04.047Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698905,0,263,540027,608975,'F',TO_TIMESTAMP('2022-06-02 14:54:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Eingabequelle',35,0,0,TO_TIMESTAMP('2022-06-02 14:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Auftrag -> Auftrag -> Eingabequelle
-- Column: C_Order.AD_InputDataSource_ID
-- 2022-06-02T13:04:04.634Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-06-02 15:04:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556234
;

-- Field: Lieferdisposition -> Auslieferplan -> Eingabequelle
-- Column: M_ShipmentSchedule.AD_InputDataSource_ID
-- 2022-06-02T13:08:36.738Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582928,698906,0,500221,0,TO_TIMESTAMP('2022-06-02 15:08:36','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Eingabequelle',0,720,0,1,1,TO_TIMESTAMP('2022-06-02 15:08:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-06-02T13:08:36.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-06-02T13:08:36.770Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541291)
;

-- 2022-06-02T13:08:36.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698906
;

-- 2022-06-02T13:08:36.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698906)
;

-- UI Element: Lieferdisposition -> Auslieferplan.Eingabequelle
-- Column: M_ShipmentSchedule.AD_InputDataSource_ID
-- 2022-06-02T13:09:19.803Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,698906,0,500221,540048,608976,'F',TO_TIMESTAMP('2022-06-02 15:09:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Eingabequelle',25,0,0,TO_TIMESTAMP('2022-06-02 15:09:19','YYYY-MM-DD HH24:MI:SS'),100)
;


update ad_column set entitytype='D', updated='2022-06-08 07:00' where columnname ilike '%AD_InputDataSource_ID%' and ad_table_id=get_table_id('c_order');
