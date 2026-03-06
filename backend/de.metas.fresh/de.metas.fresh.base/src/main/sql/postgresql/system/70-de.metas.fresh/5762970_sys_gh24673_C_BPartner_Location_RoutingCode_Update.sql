-- Run mode: SWING_CLIENT

-- Name: M_Shipper_RoutingCode
-- 2025-08-18T13:59:02.457Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541972,TO_TIMESTAMP('2025-08-18 13:59:01.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','M_Shipper_RoutingCode',TO_TIMESTAMP('2025-08-18 13:59:01.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-08-18T13:59:02.517Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541972 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Shipper_RoutingCode
-- Table: M_Shipper_RoutingCode
-- Key: M_Shipper_RoutingCode.M_Shipper_RoutingCode_ID
-- 2025-08-18T14:00:08.451Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,590617,590615,0,541972,542512,TO_TIMESTAMP('2025-08-18 14:00:08.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Y','N',TO_TIMESTAMP('2025-08-18 14:00:08.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Reference: M_Shipper_RoutingCode
-- Table: M_Shipper_RoutingCode
-- Key: M_Shipper_RoutingCode.M_Shipper_RoutingCode_ID
-- 2025-08-18T14:00:16.084Z
UPDATE AD_Ref_Table SET EntityType='D',Updated=TO_TIMESTAMP('2025-08-18 14:00:16.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541972
;

-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-08-18T14:00:43.957Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590619,583860,0,18,541972,293,'XX','M_Shipper_RoutingCode_ID',TO_TIMESTAMP('2025-08-18 14:00:42.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Leitcode',0,0,TO_TIMESTAMP('2025-08-18 14:00:42.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-18T14:00:44.018Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590619 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-18T14:00:44.139Z
/* DDL */  select update_Column_Translation_From_AD_Element(583860)
;

-- Name: M_Shipper_RoutingCode_for_M_Shipper_ID
-- 2025-08-18T14:04:06.175Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540740,'M_Shipper_RoutingCode_ID IN ( select r.M_Shipper_RoutingCode_ID from M_Shipper_RoutingCode r where r.M_Shipper_ID=@M_Shipper_ID@ )',TO_TIMESTAMP('2025-08-18 14:04:05.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','M_Shipper_RoutingCode_for_M_Shipper_ID','S',TO_TIMESTAMP('2025-08-18 14:04:05.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-08-18T14:04:59.665Z
UPDATE AD_Column SET AD_Val_Rule_ID=540740,Updated=TO_TIMESTAMP('2025-08-18 14:04:59.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590619
;

-- 2025-08-18T14:05:36.309Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN M_Shipper_RoutingCode_ID NUMERIC(10)')
;

-- 2025-08-18T14:05:36.826Z
ALTER TABLE C_BPartner_Location ADD CONSTRAINT MShipperRoutingCode_CBPartnerLocation FOREIGN KEY (M_Shipper_RoutingCode_ID) REFERENCES public.M_Shipper_RoutingCode DEFERRABLE INITIALLY DEFERRED
;

-- 2025-08-18T14:05:50.026Z
INSERT INTO t_alter_column values('c_bpartner_location','M_Shipper_RoutingCode_ID','NUMERIC(10)',null,null)
;

-- Field: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> Leitcode
-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-08-18T14:08:20.992Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590619,751870,0,222,0,TO_TIMESTAMP('2025-08-18 14:08:19.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Leitcode',0,0,220,0,1,1,TO_TIMESTAMP('2025-08-18 14:08:19.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-18T14:08:21.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-18T14:08:21.114Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583860)
;

-- 2025-08-18T14:08:21.177Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751870
;

-- 2025-08-18T14:08:21.237Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751870)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> main -> 10 -> default.Leitcode
-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-08-18T14:09:21.008Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,751870,0,222,1000034,636149,'F',TO_TIMESTAMP('2025-08-18 14:09:20.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Leitcode',126,0,0,TO_TIMESTAMP('2025-08-18 14:09:20.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;



-- 2025-08-18T14:28:04.415Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583861,0,'IsShipperHasRoutingcode',TO_TIMESTAMP('2025-08-18 14:28:03.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Shipper Has Routingcode','Shipper Has Routingcode',TO_TIMESTAMP('2025-08-18 14:28:03.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-18T14:28:04.475Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583861 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_Location.IsShipperHasRoutingcode
-- 2025-08-18T14:36:46.875Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590620,583861,0,20,293,'XX','IsShipperHasRoutingcode','( CASE WHEN EXISTS (SELECT 1 from M_Shipper_RoutingCode sr where sr.M_Shipper_ID=C_BPartner_Location.M_Shipper_ID) THEN ''Y'' ELSE ''N'' END)',TO_TIMESTAMP('2025-08-18 14:36:45.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Shipper Has Routingcode',0,0,TO_TIMESTAMP('2025-08-18 14:36:45.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-18T14:36:46.935Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590620 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-18T14:36:47.058Z
/* DDL */  select update_Column_Translation_From_AD_Element(583861)
;

-- Field: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> Shipper Has Routingcode
-- Column: C_BPartner_Location.IsShipperHasRoutingcode
-- 2025-08-18T14:37:50.734Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590620,751871,0,222,0,TO_TIMESTAMP('2025-08-18 14:37:49.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Shipper Has Routingcode',0,0,230,0,1,1,TO_TIMESTAMP('2025-08-18 14:37:49.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-18T14:37:50.793Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-18T14:37:50.855Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583861)
;

-- 2025-08-18T14:37:50.915Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751871
;

-- 2025-08-18T14:37:50.974Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751871)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> main -> 10 -> default.Shipper Has Routingcode
-- Column: C_BPartner_Location.IsShipperHasRoutingcode
-- 2025-08-18T14:38:33.824Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,751871,0,222,1000034,636150,'F',TO_TIMESTAMP('2025-08-18 14:38:33.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Shipper Has Routingcode',128,0,0,TO_TIMESTAMP('2025-08-18 14:38:33.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> Leitcode
-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-08-18T14:44:00.843Z
UPDATE AD_Field SET DisplayLogic='@IsShipperHasRoutingcode@=''Y''',Updated=TO_TIMESTAMP('2025-08-18 14:44:00.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=751870
;

-- Field: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> Shipper Has Routingcode
-- Column: C_BPartner_Location.IsShipperHasRoutingcode
-- 2025-08-18T14:45:23.178Z
UPDATE AD_Field SET DisplayLogic='1=2',Updated=TO_TIMESTAMP('2025-08-18 14:45:23.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=751871
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> main -> 10 -> default.Leitweg Nr.
-- Column: C_BPartner_Location.RouteNo
-- 2025-08-18T14:46:53.711Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=635305
;

-- 2025-08-18T14:47:21.736Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750394
;

-- Field: Geschäftspartner_OLD(123,D) -> Adresse(222,D) -> Leitweg Nr.
-- Column: C_BPartner_Location.RouteNo
-- 2025-08-18T14:47:21.921Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=750394
;

-- 2025-08-18T14:47:22.273Z
DELETE FROM AD_Field WHERE AD_Field_ID=750394
;

