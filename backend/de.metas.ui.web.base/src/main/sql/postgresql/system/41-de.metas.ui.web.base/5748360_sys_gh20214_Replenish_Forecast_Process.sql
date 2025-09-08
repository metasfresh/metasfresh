-- Run mode: SWING_CLIENT

-- Value: WEBUI_M_Replenish_Generate_Forecasts
-- Classname: de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Generate_Forecasts
-- 2025-02-27T12:52:30.587Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585454,'Y','de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Generate_Forecasts','N',TO_TIMESTAMP('2025-02-27 12:52:30.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Prognosen generieren','json','N','N','xls','Java',TO_TIMESTAMP('2025-02-27 12:52:30.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'WEBUI_M_Replenish_Generate_Forecasts')
;

-- 2025-02-27T12:52:30.593Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585454 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_M_Replenish_Generate_Forecasts(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Generate_Forecasts)
-- 2025-02-27T12:52:42.495Z
UPDATE AD_Process_Trl SET Name='Generate Forecasts',Updated=TO_TIMESTAMP('2025-02-27 12:52:42.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585454
;

-- 2025-02-27T12:52:42.496Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: WEBUI_M_Replenish_Generate_Forecasts(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Generate_Forecasts)
-- Table: M_Material_Needs_Planner_V
-- Window: Wiederauffüllung(541869,D)
-- EntityType: D
-- 2025-02-27T12:53:58.763Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585454,542466,541546,541869,TO_TIMESTAMP('2025-02-27 12:53:58.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',TO_TIMESTAMP('2025-02-27 12:53:58.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Y','Y','N')
;

-- Run mode: SWING_CLIENT

-- Process: WEBUI_M_Replenish_Generate_Forecasts(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Generate_Forecasts)
-- ParameterName: DatePromised
-- 2025-02-27T16:09:11.888Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,269,0,585454,542926,16,'DatePromised',TO_TIMESTAMP('2025-02-27 16:09:11.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','D',0,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','Y','N','Zugesagter Termin',10,TO_TIMESTAMP('2025-02-27 16:09:11.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T16:09:11.895Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542926 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Run mode: SWING_CLIENT

-- Value: ALL_SELECTED_RECORDS_SHALL_HAVE_WAREHOUSE_AND_DEMAND_MSG
-- 2025-02-27T16:27:20.062Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545505,0,TO_TIMESTAMP('2025-02-27 16:27:19.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mindestens ein Datensatz ohne Lager oder mit Nullbedarf wird ausgewählt','I',TO_TIMESTAMP('2025-02-27 16:27:19.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ALL_SELECTED_RECORDS_SHALL_HAVE_WAREHOUSE_AND_DEMAND_MSG')
;

-- 2025-02-27T16:27:20.065Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545505 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ALL_SELECTED_RECORDS_SHALL_HAVE_WAREHOUSE_AND_DEMAND_MSG
-- 2025-02-27T16:27:25.294Z
UPDATE AD_Message_Trl SET MsgText='At least one record without warehouse or with zero demand is selected',Updated=TO_TIMESTAMP('2025-02-27 16:27:25.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545505
;

-- 2025-02-27T16:27:25.295Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Run mode: SWING_CLIENT

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Nachfrage
-- Column: M_Material_Needs_Planner_V.Demand
-- 2025-03-03T10:46:11.581Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=630645
;

-- 2025-03-03T10:46:11.604Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740340
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Nachfrage
-- Column: M_Material_Needs_Planner_V.Demand
-- 2025-03-03T10:46:11.617Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=740340
;

-- 2025-03-03T10:46:11.621Z
DELETE FROM AD_Field WHERE AD_Field_ID=740340
;

-- Column: M_Material_Needs_Planner_V.Demand
-- 2025-03-03T10:46:11.652Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589735
;

-- 2025-03-03T10:46:11.656Z
DELETE FROM AD_Column WHERE AD_Column_ID=589735
;

-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-03-03T10:47:55.123Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589752,435,0,12,542466,'XX','Level_Min',TO_TIMESTAMP('2025-03-03 10:47:54.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Minimaler Bestand für dieses Produkt','D',0,22,'Indicates the minimum quantity of this product to be stocked in inventory.
','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Mindestmenge',0,0,TO_TIMESTAMP('2025-03-03 10:47:54.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-03T10:47:55.129Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-03T10:47:55.178Z
/* DDL */  select update_Column_Translation_From_AD_Element(435)
;

-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-03-03T10:48:10.384Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589753,434,0,12,542466,'XX','Level_Max',TO_TIMESTAMP('2025-03-03 10:48:10.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Maximaler Bestand für dieses Produkt','D',0,22,'Gibt die maximale Menge an, die für dieses Produkt auf Lager gehalten werden soll.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Maximalmenge',0,0,TO_TIMESTAMP('2025-03-03 10:48:10.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-03-03T10:48:10.389Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-03-03T10:48:10.545Z
/* DDL */  select update_Column_Translation_From_AD_Element(434)
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-03-03T10:50:04.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589752,740357,0,547920,TO_TIMESTAMP('2025-03-03 10:50:04.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Minimaler Bestand für dieses Produkt',22,'D','Indicates the minimum quantity of this product to be stocked in inventory.
','Y','N','N','N','N','N','N','N','Mindestmenge',TO_TIMESTAMP('2025-03-03 10:50:04.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-03T10:50:04.419Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-03T10:50:04.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(435)
;

-- 2025-03-03T10:50:04.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740357
;

-- 2025-03-03T10:50:04.452Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740357)
;

-- Field: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-03-03T10:50:10.941Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589753,740358,0,547920,TO_TIMESTAMP('2025-03-03 10:50:10.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maximaler Bestand für dieses Produkt',22,'D','Gibt die maximale Menge an, die für dieses Produkt auf Lager gehalten werden soll.','Y','N','N','N','N','N','N','N','Maximalmenge',TO_TIMESTAMP('2025-03-03 10:50:10.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-03T10:50:10.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740358 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-03T10:50:10.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(434)
;

-- 2025-03-03T10:50:10.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740358
;

-- 2025-03-03T10:50:10.959Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740358)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-03-03T10:50:46.896Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,740357,0,547920,630663,552634,'F',TO_TIMESTAMP('2025-03-03 10:50:46.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Minimaler Bestand für dieses Produkt','Indicates the minimum quantity of this product to be stocked in inventory.
','Y','N','N','Y','N','N','N',0,'Mindestmenge',110,0,0,TO_TIMESTAMP('2025-03-03 10:50:46.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-03-03T10:50:54.877Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,740358,0,547920,630664,552634,'F',TO_TIMESTAMP('2025-03-03 10:50:54.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maximaler Bestand für dieses Produkt','Gibt die maximale Menge an, die für dieses Produkt auf Lager gehalten werden soll.','Y','N','N','Y','N','N','N',0,'Maximalmenge',111,0,0,TO_TIMESTAMP('2025-03-03 10:50:54.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Mindestmenge
-- Column: M_Material_Needs_Planner_V.Level_Min
-- 2025-03-03T10:51:06.124Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-03-03 10:51:06.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630663
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Maximalmenge
-- Column: M_Material_Needs_Planner_V.Level_Max
-- 2025-03-03T10:51:06.135Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-03-03 10:51:06.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630664
;

-- UI Element: Wiederauffüllung(541869,D) -> Materialbedarf(547920,D) -> main -> 10 -> default.Lager
-- Column: M_Material_Needs_Planner_V.M_Warehouse_ID
-- 2025-03-03T10:51:06.147Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-03-03 10:51:06.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630652
;

-- Run mode: SWING_CLIENT

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Demand
-- 2025-03-03T11:12:47.317Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542925
;

-- 2025-03-03T11:12:47.322Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542925
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Min
-- 2025-03-03T11:13:18.789Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,435,0,585453,542932,12,'Level_Min',TO_TIMESTAMP('2025-03-03 11:13:18.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Minimaler Bestand für dieses Produkt','D',22,'Indicates the minimum quantity of this product to be stocked in inventory.
','Y','N','Y','N','Y','N','Mindestmenge',30,TO_TIMESTAMP('2025-03-03 11:13:18.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-03T11:13:18.794Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542932 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_Replenish_Add_Update_Demand(de.metas.ui.web.replenish.process.WEBUI_M_Replenish_Add_Update_Demand)
-- ParameterName: Level_Max
-- 2025-03-03T11:13:34.322Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,434,0,585453,542933,12,'Level_Max',TO_TIMESTAMP('2025-03-03 11:13:34.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maximaler Bestand für dieses Produkt','D',22,'Gibt die maximale Menge an, die für dieses Produkt auf Lager gehalten werden soll.','Y','N','Y','N','Y','N','Maximalmenge',40,TO_TIMESTAMP('2025-03-03 11:13:34.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-03T11:13:34.324Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542933 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;
