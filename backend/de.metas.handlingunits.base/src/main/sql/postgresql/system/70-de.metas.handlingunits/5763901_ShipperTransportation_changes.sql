-- Run mode: SWING_CLIENT

-- Column: M_ShipperTransportation.Shipper_Location_ID
-- 2025-08-26T09:08:19.383Z
UPDATE AD_Column SET AD_Val_Rule_ID=131,Updated=TO_TIMESTAMP('2025-08-26 09:08:19.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=540447
;

-- Name: C_BPartner Manufacturer OR OrgBPs
-- 2025-08-26T09:12:44.275Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541973,TO_TIMESTAMP('2025-08-26 09:12:43.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','Y','N','C_BPartner Manufacturer OR OrgBPs',TO_TIMESTAMP('2025-08-26 09:12:43.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-08-26T09:12:44.277Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541973 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_BPartner Manufacturer OR OrgBPs
-- Table: C_BPartner
-- Key: C_BPartner.C_BPartner_ID
-- 2025-08-26T09:16:07.117Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2893,0,541973,291,TO_TIMESTAMP('2025-08-26 09:16:07.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','AD_OrgBP_ID NULLS LAST','N',TO_TIMESTAMP('2025-08-26 09:16:07.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_OrgBP_ID IS NOT NULL OR isManufacturer = ''Y''')
;

-- Column: M_ShipperTransportation.Shipper_BPartner_ID
-- 2025-08-26T09:16:24.462Z
UPDATE AD_Column SET AD_Reference_Value_ID=541973,Updated=TO_TIMESTAMP('2025-08-26 09:16:24.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=540430
;

-- Column: M_ShipperTransportation.IsSOTrx
-- 2025-08-26T09:16:55.017Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590639,1106,0,20,540030,'XX','IsSOTrx',TO_TIMESTAMP('2025-08-26 09:16:54.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','Dies ist eine Verkaufstransaktion','METAS_SHIPPING',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verkaufstransaktion',0,0,TO_TIMESTAMP('2025-08-26 09:16:54.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-26T09:16:55.020Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590639 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-26T09:16:55.460Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106)
;

-- 2025-08-26T09:17:18.015Z
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''Y'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_ShipperTransportation.IsSOTrx
-- 2025-08-26T09:43:16.605Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-08-26 09:43:16.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590639
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> Verkaufstransaktion
-- Column: M_ShipperTransportation.IsSOTrx
-- 2025-08-26T09:43:38.873Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590639,752420,0,540096,0,TO_TIMESTAMP('2025-08-26 09:43:38.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufstransaktion',0,'D',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Verkaufstransaktion',0,0,200,0,1,1,TO_TIMESTAMP('2025-08-26 09:43:38.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-26T09:43:38.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=752420 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-26T09:43:38.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106)
;

-- 2025-08-26T09:43:38.904Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=752420
;

-- 2025-08-26T09:43:38.907Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(752420)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> Verkaufstransaktion
-- Column: M_ShipperTransportation.IsSOTrx
-- 2025-08-26T11:02:46.558Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-08-26 11:02:46.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=752420
;

-- UI Column: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20
-- UI Element Group: flags
-- 2025-08-26T11:03:40.010Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540388,553415,TO_TIMESTAMP('2025-08-26 11:03:39.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',5,TO_TIMESTAMP('2025-08-26 11:03:39.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Speditionslieferung(540096,METAS_SHIPPING) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: M_ShipperTransportation.IsSOTrx
-- 2025-08-26T11:03:56.544Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,752420,0,540096,553415,636395,'F',TO_TIMESTAMP('2025-08-26 11:03:56.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',10,0,0,TO_TIMESTAMP('2025-08-26 11:03:56.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_BPartner Vendor OR OrgBPs
-- 2025-08-26T14:42:06.137Z
UPDATE AD_Reference SET Name='C_BPartner Vendor OR OrgBPs',Updated=TO_TIMESTAMP('2025-08-26 14:42:06.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541973
;

-- 2025-08-26T14:42:06.139Z
UPDATE AD_Reference_Trl trl SET Name='C_BPartner Vendor OR OrgBPs' WHERE AD_Reference_ID=541973 AND AD_Language='de_DE'
;

-- Reference: C_BPartner Vendor OR OrgBPs
-- Table: C_BPartner
-- Key: C_BPartner.C_BPartner_ID
-- 2025-08-26T14:42:29.446Z
UPDATE AD_Ref_Table SET WhereClause='AD_OrgBP_ID IS NOT NULL OR isVendor = ''Y''',Updated=TO_TIMESTAMP('2025-08-26 14:42:29.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=541973
;

-- Name: Shipper_C_BPartner_Loc
-- 2025-08-26T15:06:10.561Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540741,'C_BPartner_Location.C_BPartner_ID=@Shipper_BPartner_ID/-1@ AND C_BPartner_Location.IsActive=''Y''',TO_TIMESTAMP('2025-08-26 15:06:10.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Shipper_C_BPartner_Loc','S',TO_TIMESTAMP('2025-08-26 15:06:10.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_ShipperTransportation.Shipper_Location_ID
-- 2025-08-26T15:06:30.610Z
UPDATE AD_Column SET AD_Val_Rule_ID=540741,Updated=TO_TIMESTAMP('2025-08-26 15:06:30.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=540447
;

