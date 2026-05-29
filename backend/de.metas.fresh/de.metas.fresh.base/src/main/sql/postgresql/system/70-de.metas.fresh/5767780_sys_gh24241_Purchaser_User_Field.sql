-- Run mode: SWING_CLIENT

-- 2025-09-09T09:52:36.569Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583932,0,'Purchaser_User_ID',TO_TIMESTAMP('2025-09-09 09:52:35.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einkauf Verantwortlich','D','Y','Einkäufer','Einkäufer',TO_TIMESTAMP('2025-09-09 09:52:35.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T09:52:36.645Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583932 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Purchaser_User_ID
-- 2025-09-09T09:53:54.557Z
UPDATE AD_Element_Trl SET Description='Purchasing Responsible', IsTranslated='Y', Name='Purchaser', PrintName='Purchaser',Updated=TO_TIMESTAMP('2025-09-09 09:53:54.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583932 AND AD_Language='en_US'
;

-- 2025-09-09T09:53:54.630Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-09T09:53:59.995Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583932,'en_US')
;

-- Column: C_BP_Group.Purchaser_User_ID
-- 2025-09-09T09:54:43.522Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590816,583932,0,30,540401,394,'XX','Purchaser_User_ID',TO_TIMESTAMP('2025-09-09 09:54:42.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Einkauf Verantwortlich','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Einkäufer',0,0,TO_TIMESTAMP('2025-09-09 09:54:42.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-09T09:54:43.599Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-09T09:54:43.746Z
/* DDL */  select update_Column_Translation_From_AD_Element(583932)
;

-- 2025-09-09T09:55:05.820Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN Purchaser_User_ID NUMERIC(10)')
;

-- 2025-09-09T09:55:06.012Z
ALTER TABLE C_BP_Group ADD CONSTRAINT PurchaserUser_CBPGroup FOREIGN KEY (Purchaser_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.Purchaser_User_ID
-- 2025-09-09T09:55:24.718Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-09-09 09:55:24.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590816
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> Einkäufer
-- Column: C_BP_Group.Purchaser_User_ID
-- 2025-09-09T09:57:17.052Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590816,753520,0,322,0,TO_TIMESTAMP('2025-09-09 09:57:15.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einkauf Verantwortlich',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Einkäufer',0,0,320,0,1,1,TO_TIMESTAMP('2025-09-09 09:57:15.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T09:57:17.123Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753520 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T09:57:17.194Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583932)
;

-- 2025-09-09T09:57:17.274Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753520
;

-- 2025-09-09T09:57:17.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753520)
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Einkäufer
-- Column: C_BP_Group.Purchaser_User_ID
-- 2025-09-09T09:57:54.119Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753520,0,322,540480,636958,'F',TO_TIMESTAMP('2025-09-09 09:57:53.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einkauf Verantwortlich','Y','N','N','Y','N','N','N',0,'Einkäufer',40,0,0,TO_TIMESTAMP('2025-09-09 09:57:53.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order.Purchaser_User_ID
-- 2025-09-09T10:13:20.038Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590817,583932,0,18,540401,259,'XX','Purchaser_User_ID','(SELECT bpg.Purchaser_User_ID from C_BP_Group bpg INNER JOIN C_BPartner bp on bpg.C_BP_Group_ID = bp.C_BP_Group_ID where bp.C_BPartner_ID = C_Order.C_BPartner_ID)',TO_TIMESTAMP('2025-09-09 10:13:18.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Einkauf Verantwortlich','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Einkäufer',0,0,TO_TIMESTAMP('2025-09-09 10:13:18.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-09T10:13:20.106Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-09T10:13:20.250Z
/* DDL */  select update_Column_Translation_From_AD_Element(583932)
;

-- Column: C_Order.Purchaser_User_ID
-- 2025-09-09T10:13:37.141Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-09-09 10:13:37.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590817
;

-- Field: Bestellung_OLD(181,D) -> Bestellung(294,D) -> Einkäufer
-- Column: C_Order.Purchaser_User_ID
-- 2025-09-09T10:17:08.879Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590817,753521,0,294,0,TO_TIMESTAMP('2025-09-09 10:17:07.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einkauf Verantwortlich',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Einkäufer',0,0,220,0,1,1,TO_TIMESTAMP('2025-09-09 10:17:07.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T10:17:08.950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753521 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T10:17:09.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583932)
;

-- 2025-09-09T10:17:09.093Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753521
;

-- 2025-09-09T10:17:09.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753521)
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Einkäufer
-- Column: C_Order.Purchaser_User_ID
-- 2025-09-09T10:17:46.595Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753521,0,294,540072,636959,'F',TO_TIMESTAMP('2025-09-09 10:17:45.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einkauf Verantwortlich','Y','N','N','Y','N','N','N',0,'Einkäufer',70,0,0,TO_TIMESTAMP('2025-09-09 10:17:45.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Einkäufer
-- Column: C_Order.Purchaser_User_ID
-- 2025-09-09T10:18:11.484Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-09 10:18:11.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636959
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Lieferung von
-- Column: C_Order.C_BPartner_ID
-- 2025-09-09T10:18:11.908Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-09 10:18:11.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541279
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Rechnung von
-- Column: C_Order.Bill_BPartner_ID
-- 2025-09-09T10:18:12.338Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-09-09 10:18:12.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541280
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Warenannahme
-- Column: C_Order.M_Warehouse_ID
-- 2025-09-09T10:18:12.761Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-09 10:18:12.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541802
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Streckengeschäft
-- Column: C_Order.IsDropShip
-- 2025-09-09T10:18:13.184Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-09-09 10:18:13.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547189
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Streckengeschäft Partner
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-09-09T10:18:13.613Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-09-09 10:18:13.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547190
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> main.Tour
-- Column: C_Order.M_Tour_ID
-- 2025-09-09T10:18:14.037Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-09-09 10:18:14.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=565151
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> pricing.Preissystem
-- Column: C_Order.M_PricingSystem_ID
-- 2025-09-09T10:18:14.498Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-09-09 10:18:14.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541288
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 10 -> pricing.Währung
-- Column: C_Order.C_Currency_ID
-- 2025-09-09T10:18:14.970Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-09-09 10:18:14.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541289
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 20 -> dates.Belegstatus
-- Column: C_Order.DocStatus
-- 2025-09-09T10:18:15.436Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-09-09 10:18:15.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541291
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Order.Posted
-- 2025-09-09T10:18:15.866Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-09-09 10:18:15.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541259
;

-- UI Element: Bestellung_OLD(181,D) -> Bestellung(294,D) -> main -> 20 -> org.Sektion
-- Column: C_Order.AD_Org_ID
-- 2025-09-09T10:18:16.287Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-09-09 10:18:16.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541286
;

-- Column: C_Order.Purchaser_User_ID
-- 2025-09-09T10:19:16.438Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-09-09 10:19:16.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590817
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Einkäufer
-- Column: C_BP_Group.Purchaser_User_ID
-- 2025-09-09T10:23:37.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-09 10:23:37.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636958
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 20 -> flags.MwSt. ausweisen
-- Column: C_BP_Group.IsPrintTax
-- 2025-09-09T10:23:37.466Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-09-09 10:23:37.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544695
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> description.Verkauf Preissystem
-- Column: C_BP_Group.M_PricingSystem_ID
-- 2025-09-09T10:23:37.896Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-09 10:23:37.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544688
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> description.Verkauf Rabatt Schema
-- Column: C_BP_Group.M_DiscountSchema_ID
-- 2025-09-09T10:23:38.323Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-09-09 10:23:38.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544689
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> description.Einkauf Preissystem
-- Column: C_BP_Group.PO_PricingSystem_ID
-- 2025-09-09T10:23:38.750Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-09-09 10:23:38.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544690
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> description.Einkauf Rabatt Schema
-- Column: C_BP_Group.PO_DiscountSchema_ID
-- 2025-09-09T10:23:39.179Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-09-09 10:23:39.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544691
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 20 -> org.Sektion
-- Column: C_BP_Group.AD_Org_ID
-- 2025-09-09T10:23:39.609Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-09-09 10:23:39.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544699
;

-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-09-09T10:36:38.964Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590818,583932,0,30,540401,540270,'XX','Purchaser_User_ID','(SELECT bpg.Purchaser_User_ID from C_BP_Group bpg INNER JOIN C_BPartner bp on bpg.C_BP_Group_ID = bp.C_BP_Group_ID where bp.C_BPartner_ID = C_Invoice_Candidate.Bill_BPartner_ID)',TO_TIMESTAMP('2025-09-09 10:36:38.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Einkauf Verantwortlich','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Einkäufer',0,0,TO_TIMESTAMP('2025-09-09 10:36:38.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-09T10:36:39.038Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-09T10:36:39.180Z
/* DDL */  select update_Column_Translation_From_AD_Element(583932)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Einkäufer
-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-09-09T10:38:25.482Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590818,753522,0,543052,0,TO_TIMESTAMP('2025-09-09 10:38:24.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einkauf Verantwortlich',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Einkäufer',0,0,540,0,1,1,TO_TIMESTAMP('2025-09-09 10:38:24.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T10:38:25.552Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753522 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T10:38:25.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583932)
;

-- 2025-09-09T10:38:25.694Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753522
;

-- 2025-09-09T10:38:25.762Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753522)
;

-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-09-09T10:39:15.816Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-09-09 10:39:15.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590818
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.Einkäufer
-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-09-09T10:40:33.735Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753522,0,543052,544361,636960,'F',TO_TIMESTAMP('2025-09-09 10:40:32.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einkauf Verantwortlich','Y','N','N','Y','N','N','N',0,'Einkäufer',90,0,0,TO_TIMESTAMP('2025-09-09 10:40:32.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.Einkäufer
-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-09-09T10:41:00.170Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-09 10:41:00.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636960
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.C_Order_ID
-- Column: C_Invoice_Candidate.C_Order_ID
-- 2025-09-09T10:41:00.595Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-09 10:41:00.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573384
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.Produkt
-- Column: C_Invoice_Candidate.M_Product_ID
-- 2025-09-09T10:41:01.022Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-09 10:41:01.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573385
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.Produkt-Kategorie
-- Column: C_Invoice_Candidate.M_Product_Category_ID
-- 2025-09-09T10:41:01.442Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-09-09 10:41:01.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573386
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.IsToRecompute
-- Column: C_Invoice_Candidate.IsToRecompute
-- 2025-09-09T10:41:01.867Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-09 10:41:01.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573402
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Freigabe zur Fakturierung
-- Column: C_Invoice_Candidate.ApprovalForInvoicing
-- 2025-09-09T10:41:02.287Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-09-09 10:41:02.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573473
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyOrdered
-- Column: C_Invoice_Candidate.QtyOrdered
-- 2025-09-09T10:41:02.708Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-09-09 10:41:02.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573388
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Gelieferte Menge
-- Column: C_Invoice_Candidate.QtyDelivered
-- 2025-09-09T10:41:03.130Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-09-09 10:41:03.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573389
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Lieferung geschlossen
-- Column: C_Invoice_Candidate.IsDeliveryClosed
-- 2025-09-09T10:41:03.551Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-09-09 10:41:03.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609597
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.Berechnete Menge
-- Column: C_Invoice_Candidate.QtyInvoiced
-- 2025-09-09T10:41:03.970Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-09-09 10:41:03.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573390
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> qtyInStockingUOM.QtyToInvoice
-- Column: C_Invoice_Candidate.QtyToInvoice
-- 2025-09-09T10:41:04.381Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-09-09 10:41:04.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573392
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Abrechnung ab eff.
-- Column: C_Invoice_Candidate.DateToInvoice_Effective
-- 2025-09-09T10:41:04.794Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-09-09 10:41:04.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573406
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Lieferdatum
-- Column: C_Invoice_Candidate.DeliveryDate
-- 2025-09-09T10:41:05.206Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-09-09 10:41:05.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573407
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> override.Total des Auftrags
-- Column: C_Invoice_Candidate.TotalOfOrder
-- 2025-09-09T10:41:05.621Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-09-09 10:41:05.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573397
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> org.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2025-09-09T10:41:06.034Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-09-09 10:41:06.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=573420
;

