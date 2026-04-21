-- Run mode: SWING_CLIENT

-- Element: IsCreateDraftShipmentOnly
-- 2026-04-21T08:00:00Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584768 /*From ID Server*/,0,'IsCreateDraftShipmentOnly',TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,'D','Y','Nur als Entwurf versenden','Nur als Entwurf versenden',TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0)
;

-- 2026-04-21T08:00:00Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584768 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsCreateDraftShipmentOnly (en_US translation)
-- 2026-04-21T08:00:00Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Create Draft Shipment Only', PrintName='Create Draft Shipment Only',Updated=TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=0 WHERE AD_Element_ID=584768 AND AD_Language='en_US'
;

-- 2026-04-21T08:00:00Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-21T08:00:00Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584768,'en_US')
;

-- Element: IsCreateDraftShipmentOnly (de_CH translation)
-- 2026-04-21T08:00:00Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=0 WHERE AD_Element_ID=584768 AND AD_Language='de_CH'
;

-- 2026-04-21T08:00:00Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584768,'de_CH')
;

-- Element: IsCreateDraftShipmentOnly (de_DE translation)
-- 2026-04-21T08:00:00Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=0 WHERE AD_Element_ID=584768 AND AD_Language='de_DE'
;

-- 2026-04-21T08:00:00Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584768,'de_DE')
;

-- 2026-04-21T08:00:00Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584768,'de_DE')
;

-- Column: Carrier_Config.IsCreateDraftShipmentOnly
-- 2026-04-21T08:00:00Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592375 /*From ID Server*/,584768,0,20,542540,'XX','IsCreateDraftShipmentOnly',TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Nur als Entwurf versenden',0,0,TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,0)
;

-- 2026-04-21T08:00:00Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592375 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-04-21T08:00:00Z
/* DDL */  select update_Column_Translation_From_AD_Element(584768)
;

-- DDL: Add physical column to Carrier_Config table
-- 2026-04-21T08:00:00Z
/* DDL */ SELECT public.db_alter_table('Carrier_Config','ADD COLUMN IF NOT EXISTS IsCreateDraftShipmentOnly CHAR(1) DEFAULT ''N'' NOT NULL')
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Nur als Entwurf versenden
-- Column: Carrier_Config.IsCreateDraftShipmentOnly
-- 2026-04-21T08:00:00Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,592375,777087 /*From ID Server*/,0,548455,0,TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Nur als Entwurf versenden',0,0,20,0,1,1,TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0)
;

-- 2026-04-21T08:00:00Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-21T08:00:00Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584768)
;

-- 2026-04-21T08:00:00Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777087
;

-- 2026-04-21T08:00:00Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777087)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nur als Entwurf versenden
-- Column: Carrier_Config.IsCreateDraftShipmentOnly
-- 2026-04-21T08:00:00Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777087,0,548455,553597,649836 /*From ID Server*/,'F',TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,'Y','N','N','Y','N','N','N',0,'Nur als Entwurf versenden',90,0,0,TO_TIMESTAMP('2026-04-21 08:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0)
;
