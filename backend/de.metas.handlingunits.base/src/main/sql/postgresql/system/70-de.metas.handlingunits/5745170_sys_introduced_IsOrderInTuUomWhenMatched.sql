-- 2025-01-29T14:03:46.238Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583460,0,'IsOrderInTuUomWhenMatched',TO_TIMESTAMP('2025-01-29 14:03:45.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Aufräge in Kollo','Aufräge in Kollo',TO_TIMESTAMP('2025-01-29 14:03:45.994000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-29T14:03:46.247Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583460 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsOrderInTuUomWhenMatched
-- 2025-01-29T14:04:04.050Z
UPDATE AD_Element_Trl SET Name='Sales orders in Coli', PrintName='Sales orders in Coli',Updated=TO_TIMESTAMP('2025-01-29 14:04:04.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583460 AND AD_Language='en_US'
;

-- 2025-01-29T14:04:04.083Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583460,'en_US')
;

-- Element: IsOrderInTuUomWhenMatched
-- 2025-01-29T14:08:48.115Z
UPDATE AD_Element_Trl SET Description='If true, orders referencing this packing item shall always use the Coli UOM',Updated=TO_TIMESTAMP('2025-01-29 14:08:48.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583460 AND AD_Language='en_US'
;

-- 2025-01-29T14:08:48.117Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583460,'en_US')
;

-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- 2025-01-29T14:09:21.034Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589626,583460,0,20,540508,'XX','IsOrderInTuUomWhenMatched',TO_TIMESTAMP('2025-01-29 14:09:20.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Aufräge in Kollo',0,0,TO_TIMESTAMP('2025-01-29 14:09:20.864000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-29T14:09:21.037Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589626 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-29T14:09:21.040Z
/* DDL */  select update_Column_Translation_From_AD_Element(583460)
;

-- Field: Produkt -> Packvorschrift Nachweis -> Aufräge in Kollo
-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- Field: Produkt(140,D) -> Packvorschrift Nachweis(540517,de.metas.handlingunits) -> Aufräge in Kollo
-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- 2025-01-29T14:14:22.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589626,735595,0,540517,0,TO_TIMESTAMP('2025-01-29 14:14:22.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N',0,'Aufräge in Kollo',0,0,160,0,1,1,TO_TIMESTAMP('2025-01-29 14:14:22.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-29T14:14:22.466Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=735595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-29T14:14:22.468Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583460)
;

-- 2025-01-29T14:14:22.479Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=735595
;

-- 2025-01-29T14:14:22.483Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(735595)
;

-- UI Element: Produkt -> Packvorschrift Nachweis.Aufräge in Kollo
-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- UI Element: Produkt(140,D) -> Packvorschrift Nachweis(540517,de.metas.handlingunits) -> main -> 10 -> default.Aufräge in Kollo
-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- 2025-01-29T14:15:27.718Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,735595,0,540517,1000019,628429,'F',TO_TIMESTAMP('2025-01-29 14:15:27.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Aufräge in Kollo',150,0,0,TO_TIMESTAMP('2025-01-29 14:15:27.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Produkt -> Packvorschrift Nachweis -> Standard-Packvorschrift
-- Column: M_HU_PI_Item_Product.IsDefaultForProduct
-- Field: Produkt(140,D) -> Packvorschrift Nachweis(540517,de.metas.handlingunits) -> Standard-Packvorschrift
-- Column: M_HU_PI_Item_Product.IsDefaultForProduct
-- 2025-01-29T14:15:45.422Z
UPDATE AD_Field SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2025-01-29 14:15:45.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=593842
;

-- UI Element: Produkt -> Packvorschrift Nachweis.Aufräge in Kollo
-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- UI Element: Produkt(140,D) -> Packvorschrift Nachweis(540517,de.metas.handlingunits) -> main -> 10 -> default.Aufräge in Kollo
-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- 2025-01-29T14:16:14.034Z
UPDATE AD_UI_Element SET SeqNo=125,Updated=TO_TIMESTAMP('2025-01-29 14:16:14.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=628429
;

-- 2025-01-29T16:37:37.090Z
/* DDL */ SELECT public.db_alter_table('M_HU_PI_Item_Product','ALTER TABLE public.M_HU_PI_Item_Product ADD COLUMN IsOrderInTuUomWhenMatched CHAR(1) DEFAULT ''N'' CHECK (IsOrderInTuUomWhenMatched IN (''Y'',''N'')) NOT NULL')
;

-- Element: IsOrderInTuUomWhenMatched
-- 2025-01-31T12:06:28.676Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird bei importierten Auftragsdispo-Zeilen, die diese Packvorschrift verwenden, für die Auftragsmenge die Maßeinheit "Kollo" angenommen.',Updated=TO_TIMESTAMP('2025-01-31 12:06:28.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583460 AND AD_Language='fr_CH'
;

-- 2025-01-31T12:06:28.678Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583460,'fr_CH')
;

-- Element: IsOrderInTuUomWhenMatched
-- 2025-01-31T12:06:29.707Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird bei importierten Auftragsdispo-Zeilen, die diese Packvorschrift verwenden, für die Auftragsmenge die Maßeinheit "Kollo" angenommen.',Updated=TO_TIMESTAMP('2025-01-31 12:06:29.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583460 AND AD_Language='de_DE'
;

-- 2025-01-31T12:06:29.708Z
UPDATE AD_Element SET Description='Wenn angehakt, wird bei importierten Auftragsdispo-Zeilen, die diese Packvorschrift verwenden, für die Auftragsmenge die Maßeinheit "Kollo" angenommen.', Updated=TO_TIMESTAMP('2025-01-31 12:06:29.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=583460
;

-- 2025-01-31T12:06:29.971Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583460,'de_DE')
;

-- 2025-01-31T12:06:29.972Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583460,'de_DE')
;

-- Element: IsOrderInTuUomWhenMatched
-- 2025-01-31T12:06:34.007Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird bei importierten Auftragsdispo-Zeilen, die diese Packvorschrift verwenden, für die Auftragsmenge die Maßeinheit "Kollo" angenommen.',Updated=TO_TIMESTAMP('2025-01-31 12:06:34.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583460 AND AD_Language='de_CH'
;

-- 2025-01-31T12:06:34.009Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583460,'de_CH')
;

-- Element: IsOrderInTuUomWhenMatched
-- 2025-01-31T12:06:46.003Z
UPDATE AD_Element_Trl SET Description='If ticked, the unit of measurement for imported order candidates that use this packing instruction is set to "Collo".',Updated=TO_TIMESTAMP('2025-01-31 12:06:46.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583460 AND AD_Language='en_US'
;

-- 2025-01-31T12:06:46.006Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583460,'en_US')
;

