-- Run mode: SWING_CLIENT

-- 2025-12-01T14:11:28.739Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584306,0,'IsConfirmedBySupplier',TO_TIMESTAMP('2025-12-01 14:11:28.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Bestätigt durch Lieferant','Bestätigt durch Lieferant',TO_TIMESTAMP('2025-12-01 14:11:28.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T14:11:28.836Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584306 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-01T14:12:29.840Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591613,584306,0,20,259,'XX','IsConfirmedBySupplier',TO_TIMESTAMP('2025-12-01 14:12:29.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bestätigt durch Lieferant',0,0,TO_TIMESTAMP('2025-12-01 14:12:29.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-01T14:12:29.935Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591613 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-01T14:12:30.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(584306)
;

-- 2025-12-01T14:17:39.913Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN IsConfirmedBySupplier CHAR(1) DEFAULT ''N'' CHECK (IsConfirmedBySupplier IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-01T14:39:33.510Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-12-01 14:39:33.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591613
;

-- 2025-12-01T14:32:56.952Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584308,0,'QtyConfirmedBySupplier',TO_TIMESTAMP('2025-12-01 14:32:56.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Bestellt - bestätigt','Bestellt - bestätigt',TO_TIMESTAMP('2025-12-01 14:32:56.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T14:32:57.039Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584308 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-12-01T14:33:42.162Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584309,0,'QtyUnconfirmedBySupplier',TO_TIMESTAMP('2025-12-01 14:33:41.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Bestellt - unbestätigt','Bestellt - unbestätigt',TO_TIMESTAMP('2025-12-01 14:33:41.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-01T14:33:42.247Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584309 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsConfirmedBySupplier
-- 2025-12-02T14:35:13.756Z
UPDATE AD_Element_Trl SET Name='Confirmed by supplier', PrintName='Confirmed by supplier',Updated=TO_TIMESTAMP('2025-12-02 14:35:13.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584306 AND AD_Language='en_US'
;

-- 2025-12-02T14:35:13.768Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-02T14:35:14.167Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584306,'en_US')
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Bestätigt durch Lieferant
-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-02T14:37:09.158Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591613,758516,0,294,0,TO_TIMESTAMP('2025-12-02 14:37:08.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Bestätigt durch Lieferant',0,0,260,0,1,1,TO_TIMESTAMP('2025-12-02 14:37:08.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T14:37:09.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758516 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T14:37:09.165Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584306)
;

-- 2025-12-02T14:37:09.181Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758516
;

-- 2025-12-02T14:37:09.186Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758516)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Bestätigt durch Lieferant
-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-02T14:37:37.960Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758516,0,294,540961,639730,'F',TO_TIMESTAMP('2025-12-02 14:37:37.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestätigt durch Lieferant',300,0,0,TO_TIMESTAMP('2025-12-02 14:37:37.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Bestätigt durch Lieferant
-- Column: C_Order.IsConfirmedBySupplier
-- 2025-12-02T14:37:42.644Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-12-02 14:37:42.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639730
;

-- Column: QtyDemand_QtySupply_V.QtyConfirmedBySupplier
-- 2025-12-02T14:38:48.860Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591652,584308,0,29,542218,'XX','QtyConfirmedBySupplier',TO_TIMESTAMP('2025-12-02 14:38:48.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.material.cockpit',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Bestellt - bestätigt',0,0,TO_TIMESTAMP('2025-12-02 14:38:48.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-02T14:38:48.863Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591652 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-02T14:38:48.866Z
/* DDL */  select update_Column_Translation_From_AD_Element(584308)
;

-- Column: QtyDemand_QtySupply_V.QtyUnconfirmedBySupplier
-- 2025-12-02T14:39:05.928Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591653,584309,0,29,542218,'XX','QtyUnconfirmedBySupplier',TO_TIMESTAMP('2025-12-02 14:39:05.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.material.cockpit',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Bestellt - unbestätigt',0,0,TO_TIMESTAMP('2025-12-02 14:39:05.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-02T14:39:05.930Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591653 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-02T14:39:06.070Z
/* DDL */  select update_Column_Translation_From_AD_Element(584309)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Bestellt - bestätigt
-- Column: QtyDemand_QtySupply_V.QtyConfirmedBySupplier
-- 2025-12-02T15:18:56.058Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591652,758538,0,548476,0,TO_TIMESTAMP('2025-12-02 15:18:54.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Bestellt - bestätigt',0,0,20,0,1,1,TO_TIMESTAMP('2025-12-02 15:18:54.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:18:56.145Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758538 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:18:56.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584308)
;

-- 2025-12-02T15:18:56.350Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758538
;

-- 2025-12-02T15:18:56.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758538)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Bestellt - bestätigt
-- Column: QtyDemand_QtySupply_V.QtyConfirmedBySupplier
-- 2025-12-02T15:19:15.105Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2025-12-02 15:19:15.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=758538
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Bestellt - unbestätigt
-- Column: QtyDemand_QtySupply_V.QtyUnconfirmedBySupplier
-- 2025-12-02T15:19:49.347Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591653,758539,0,548476,0,TO_TIMESTAMP('2025-12-02 15:19:48.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Bestellt - unbestätigt',0,0,30,0,1,1,TO_TIMESTAMP('2025-12-02 15:19:48.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:19:49.434Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:19:49.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584309)
;

-- 2025-12-02T15:19:49.611Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758539
;

-- 2025-12-02T15:19:49.691Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758539)
;

-- 2025-12-02T15:22:34.837Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584322,0,TO_TIMESTAMP('2025-12-02 15:22:34.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Auftragsbestand','Auftragsbestand',TO_TIMESTAMP('2025-12-02 15:22:34.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:22:34.923Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584322 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Auftragsbestand
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-12-02T15:23:16.926Z
UPDATE AD_Field SET AD_Name_ID=584322, Description=NULL, Help=NULL, Name='Auftragsbestand',Updated=TO_TIMESTAMP('2025-12-02 15:23:16.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755068
;

-- 2025-12-02T15:23:17.011Z
UPDATE AD_Field_Trl trl SET Name='Auftragsbestand' WHERE AD_Field_ID=755068 AND AD_Language='de_DE'
;

-- 2025-12-02T15:23:17.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584322)
;

-- 2025-12-02T15:23:17.186Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755068
;

-- 2025-12-02T15:23:17.271Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755068)
;

-- 2025-12-02T15:23:42.576Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584323,0,TO_TIMESTAMP('2025-12-02 15:23:42.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Bestellt - gesamt','Bestellt - gesamt',TO_TIMESTAMP('2025-12-02 15:23:42.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:23:42.669Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584323 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Bestellt - gesamt
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-12-02T15:24:28.780Z
UPDATE AD_Field SET AD_Name_ID=584323, Description=NULL, Help=NULL, Name='Bestellt - gesamt',Updated=TO_TIMESTAMP('2025-12-02 15:24:28.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755069
;

-- 2025-12-02T15:24:28.868Z
UPDATE AD_Field_Trl trl SET Name='Bestellt - gesamt' WHERE AD_Field_ID=755069 AND AD_Language='de_DE'
;

-- 2025-12-02T15:24:28.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584323)
;

-- 2025-12-02T15:24:29.042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755069
;

-- 2025-12-02T15:24:29.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755069)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Produktion
-- Column: QtyDemand_QtySupply_V.QtyToProduce
-- 2025-12-02T15:25:04.603Z
UPDATE AD_Field SET AD_Name_ID=575601, Description=NULL, Help=NULL, Name='Produktion',Updated=TO_TIMESTAMP('2025-12-02 15:25:04.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755074
;

-- 2025-12-02T15:25:04.687Z
UPDATE AD_Field_Trl trl SET Name='Produktion' WHERE AD_Field_ID=755074 AND AD_Language='de_DE'
;

-- 2025-12-02T15:25:04.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575601)
;

-- 2025-12-02T15:25:04.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755074
;

-- 2025-12-02T15:25:04.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755074)
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Prognose
-- Column: QtyDemand_QtySupply_V.QtyForecasted
-- 2025-12-02T15:25:39.602Z
UPDATE AD_Field SET AD_Name_ID=572964, Description=NULL, Help=NULL, Name='Prognose',Updated=TO_TIMESTAMP('2025-12-02 15:25:39.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755075
;

-- 2025-12-02T15:25:39.687Z
UPDATE AD_Field_Trl trl SET Name='Prognose' WHERE AD_Field_ID=755075 AND AD_Language='de_DE'
;

-- 2025-12-02T15:25:39.778Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572964)
;

-- 2025-12-02T15:25:39.865Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755075
;

-- 2025-12-02T15:25:39.944Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755075)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestand
-- Column: QtyDemand_QtySupply_V.QtyStock
-- 2025-12-02T15:26:20.911Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2025-12-02 15:26:20.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637942
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Reservierte Menge
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-12-02T15:26:33.860Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-12-02 15:26:33.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637938
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Produktion - offen
-- Column: QtyDemand_QtySupply_V.QtyToProduce
-- 2025-12-02T15:26:39.763Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2025-12-02 15:26:39.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637940
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Prognose - offen
-- Column: QtyDemand_QtySupply_V.QtyForecasted
-- 2025-12-02T15:26:47.355Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2025-12-02 15:26:47.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637941
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - offen
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-12-02T15:26:57.253Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2025-12-02 15:26:57.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637939
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - unbestätigt
-- Column: QtyDemand_QtySupply_V.QtyUnconfirmedBySupplier
-- 2025-12-02T15:27:37.503Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758539,0,548476,553651,639732,'F',TO_TIMESTAMP('2025-12-02 15:27:36.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestellt - unbestätigt',30,0,0,TO_TIMESTAMP('2025-12-02 15:27:36.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - bestätigt
-- Column: QtyDemand_QtySupply_V.QtyConfirmedBySupplier
-- 2025-12-02T15:28:04.016Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758538,0,548476,553651,639733,'F',TO_TIMESTAMP('2025-12-02 15:28:03.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Bestellt - bestätigt',40,0,0,TO_TIMESTAMP('2025-12-02 15:28:03.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestand
-- Column: QtyDemand_QtySupply_V.QtyStock
-- 2025-12-02T15:28:59.597Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-02 15:28:59.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637942
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Reservierte Menge
-- Column: QtyDemand_QtySupply_V.QtyReserved
-- 2025-12-02T15:29:00.107Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-02 15:29:00.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637938
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - unbestätigt
-- Column: QtyDemand_QtySupply_V.QtyUnconfirmedBySupplier
-- 2025-12-02T15:29:00.613Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-12-02 15:29:00.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639732
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - bestätigt
-- Column: QtyDemand_QtySupply_V.QtyConfirmedBySupplier
-- 2025-12-02T15:29:01.110Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-12-02 15:29:01.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639733
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Bestellt - offen
-- Column: QtyDemand_QtySupply_V.QtyToMove
-- 2025-12-02T15:29:01.617Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-12-02 15:29:01.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637939
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Produktion - offen
-- Column: QtyDemand_QtySupply_V.QtyToProduce
-- 2025-12-02T15:29:02.111Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-12-02 15:29:02.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637940
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> qties.Prognose - offen
-- Column: QtyDemand_QtySupply_V.QtyForecasted
-- 2025-12-02T15:29:02.617Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-12-02 15:29:02.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637941
;

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 20 -> org.Sektion
-- Column: QtyDemand_QtySupply_V.AD_Org_ID
-- 2025-12-02T15:29:03.107Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-12-02 15:29:03.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637937
;

