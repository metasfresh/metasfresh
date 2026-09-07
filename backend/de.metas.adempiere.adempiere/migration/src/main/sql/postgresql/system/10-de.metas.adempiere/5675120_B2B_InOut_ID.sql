-- 2023-02-02T09:03:02.925Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581996,0,'B2B_InOut_ID',TO_TIMESTAMP('2023-02-02 11:03:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','B2B Shipment / Receipt','B2B Shipment / Receipt',TO_TIMESTAMP('2023-02-02 11:03:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T09:03:02.931Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581996 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:03:27.060Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585741,581996,0,30,337,319,'B2B_InOut_ID',TO_TIMESTAMP('2023-02-02 11:03:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'B2B Shipment / Receipt',0,0,TO_TIMESTAMP('2023-02-02 11:03:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T09:03:27.061Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T09:03:27.092Z
/* DDL */  select update_Column_Translation_From_AD_Element(581996) 
;

-- 2023-02-02T09:03:28.139Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN B2B_InOut_ID NUMERIC(10)')
;

-- 2023-02-02T09:03:28.794Z
ALTER TABLE M_InOut ADD CONSTRAINT B2BInOut_MInOut FOREIGN KEY (B2B_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:03:59.984Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585741,711717,0,296,TO_TIMESTAMP('2023-02-02 11:03:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','B2B Shipment / Receipt',TO_TIMESTAMP('2023-02-02 11:03:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T09:03:59.985Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=711717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T09:03:59.987Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581996) 
;

-- 2023-02-02T09:04:00.001Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=711717
;

-- 2023-02-02T09:04:00.002Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(711717)
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:05:17.467Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-02 11:05:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=711717
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 20 -> dates.B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:05:52.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,711717,0,296,540081,615364,'F',TO_TIMESTAMP('2023-02-02 11:05:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','B2B Shipment / Receipt',80,0,0,TO_TIMESTAMP('2023-02-02 11:05:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Receipt(184,D) -> Material Receipt(296,D) -> main -> 20 -> dates.B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:06:05.370Z
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2023-02-02 11:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615364
;

-- Field: Material Receipt(184,D) -> Material Receipt(296,D) -> B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:06:16.809Z
UPDATE AD_Field SET DisplayLogic='@B2B_InOut_ID/0@>0',Updated=TO_TIMESTAMP('2023-02-02 11:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=711717
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:06:45.973Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585741,711718,0,257,TO_TIMESTAMP('2023-02-02 11:06:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','B2B Shipment / Receipt',TO_TIMESTAMP('2023-02-02 11:06:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T09:06:45.975Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=711718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T09:06:45.976Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581996) 
;

-- 2023-02-02T09:06:45.981Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=711718
;

-- 2023-02-02T09:06:45.982Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(711718)
;

-- Field: Shipment (Customer)(169,D) -> Shipment(257,D) -> B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:07:03.638Z
UPDATE AD_Field SET DisplayLogic='@B2B_InOut_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-02 11:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=711718
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 20 -> dates.B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:07:40.434Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,711718,0,257,1000029,615365,'F',TO_TIMESTAMP('2023-02-02 11:07:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','B2B Shipment / Receipt',80,0,0,TO_TIMESTAMP('2023-02-02 11:07:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 20 -> dates.B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:08:07.630Z
UPDATE AD_UI_Element SET SeqNo=66,Updated=TO_TIMESTAMP('2023-02-02 11:08:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615365
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment(257,D) -> main -> 20 -> dates.B2B Shipment / Receipt
-- Column: M_InOut.B2B_InOut_ID
-- 2023-02-02T09:37:32.540Z
UPDATE AD_UI_Element SET SeqNo=64,Updated=TO_TIMESTAMP('2023-02-02 11:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615365
;

