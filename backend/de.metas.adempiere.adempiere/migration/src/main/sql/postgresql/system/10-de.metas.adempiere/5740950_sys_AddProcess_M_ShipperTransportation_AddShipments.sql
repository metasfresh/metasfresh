-- Run mode: SWING_CLIENT

-- Value: M_ShipperTransportation_AddShipments
-- Classname: de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments
-- 2024-12-03T16:44:33.433Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585438,'N','de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments','N',TO_TIMESTAMP('2024-12-03 18:44:33.227','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Task 08743','Y','N','Y','N','Y','N','N','N','Y','N','Y',0,'Add shipments','json','N','Y','','Java',TO_TIMESTAMP('2024-12-03 18:44:33.227','YYYY-MM-DD HH24:MI:SS.US'),100,'M_ShipperTransportation_AddShipments')
;

-- 2024-12-03T16:44:33.447Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585438 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_ShipperTransportation_AddShipments(de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments)
-- 2024-12-03T16:44:38.108Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Lieferungen hinzufügen',Updated=TO_TIMESTAMP('2024-12-03 18:44:38.108','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585438
;

-- Process: M_ShipperTransportation_AddShipments(de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments)
-- 2024-12-03T16:44:44.095Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Lieferungen hinzufügen',Updated=TO_TIMESTAMP('2024-12-03 18:44:44.094','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585438
;

-- 2024-12-03T16:44:44.097Z
UPDATE AD_Process SET Name='Lieferungen hinzufügen' WHERE AD_Process_ID=585438
;

-- Process: M_ShipperTransportation_AddShipments(de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments)
-- Table: M_ShipperTransportation
-- EntityType: de.metas.inout
-- 2024-12-03T16:51:21.167Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585438,540030,541537,TO_TIMESTAMP('2024-12-03 18:51:20.981','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inout','Y',TO_TIMESTAMP('2024-12-03 18:51:20.981','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Column: M_ShippingPackage.QtyLU
-- 2024-12-04T08:22:23.693Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589475,542491,0,29,540031,'QtyLU',TO_TIMESTAMP('2024-12-04 10:22:23.5','YYYY-MM-DD HH24:MI:SS.US'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'LU Anzahl',0,0,TO_TIMESTAMP('2024-12-04 10:22:23.5','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-12-04T08:22:23.698Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589475 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-04T08:22:23.738Z
/* DDL */  select update_Column_Translation_From_AD_Element(542491)
;

-- Column: M_ShippingPackage.QtyTU
-- 2024-12-04T08:23:08Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589476,542490,0,29,540031,'QtyTU',TO_TIMESTAMP('2024-12-04 10:23:07.889','YYYY-MM-DD HH24:MI:SS.US'),100,'N','METAS_SHIPPING',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'TU Anzahl',0,0,TO_TIMESTAMP('2024-12-04 10:23:07.889','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-12-04T08:23:08.002Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589476 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-04T08:23:08.007Z
/* DDL */  select update_Column_Translation_From_AD_Element(542490)
;

-- 2024-12-04T08:23:10.216Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN QtyTU NUMERIC')
;

-- 2024-12-04T08:23:16.746Z
/* DDL */ SELECT public.db_alter_table('M_ShippingPackage','ALTER TABLE public.M_ShippingPackage ADD COLUMN QtyLU NUMERIC')
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> LU Anzahl
-- Column: M_ShippingPackage.QtyLU
-- 2024-12-04T08:24:04.111Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589475,734070,0,540097,TO_TIMESTAMP('2024-12-04 10:24:04.028','YYYY-MM-DD HH24:MI:SS.US'),100,10,'METAS_SHIPPING','Y','N','N','N','N','N','N','N','LU Anzahl',TO_TIMESTAMP('2024-12-04 10:24:04.028','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-04T08:24:04.113Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-04T08:24:04.115Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542491)
;

-- 2024-12-04T08:24:04.120Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734070
;

-- 2024-12-04T08:24:04.121Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734070)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> TU Anzahl
-- Column: M_ShippingPackage.QtyTU
-- 2024-12-04T08:24:04.212Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589476,734071,0,540097,TO_TIMESTAMP('2024-12-04 10:24:04.124','YYYY-MM-DD HH24:MI:SS.US'),100,10,'METAS_SHIPPING','Y','N','N','N','N','N','N','N','TU Anzahl',TO_TIMESTAMP('2024-12-04 10:24:04.124','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-04T08:24:04.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-04T08:24:04.216Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542490)
;

-- 2024-12-04T08:24:04.221Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734071
;

-- 2024-12-04T08:24:04.222Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734071)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.LU Anzahl
-- Column: M_ShippingPackage.QtyLU
-- 2024-12-04T08:25:15.212Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,734070,0,540097,540666,627380,'F',TO_TIMESTAMP('2024-12-04 10:25:15.054','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','Y','N','N','LU Anzahl',5,60,0,TO_TIMESTAMP('2024-12-04 10:25:15.054','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.TU Anzahl
-- Column: M_ShippingPackage.QtyTU
-- 2024-12-04T08:25:23.308Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,734071,0,540097,540666,627381,'F',TO_TIMESTAMP('2024-12-04 10:25:23.185','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','Y','N','N','TU Anzahl',5,60,0,TO_TIMESTAMP('2024-12-04 10:25:23.185','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;


-- Value: M_ShipperTransportation_AddShipments
-- Classname: de.metas.handlingunits.shipping.process.M_ShipperTransportation_AddShipments
-- 2024-12-04T13:35:17.200Z
UPDATE AD_Process SET EntityType='METAS_SHIPPING', IsFormatExcelFile='N',Updated=TO_TIMESTAMP('2024-12-04 15:35:17.199','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585438
;

