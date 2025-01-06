-- 2024-06-17T08:50:19.324Z
UPDATE EXP_Format SET Name='EDI_Exp_Desadv',Updated=TO_TIMESTAMP('2024-06-17 08:50:19.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-17T08:51:54.340Z
UPDATE EXP_Format SET Name='EXP_M_InOut_Desadv_V',Updated=TO_TIMESTAMP('2024-06-17 08:51:54.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-17T08:52:14.503Z
UPDATE EXP_Format SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 08:52:14.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540405
;

-- 2024-06-17T08:52:35.728Z
UPDATE EXP_Format SET Value='EDI_Exp_Desadv',Updated=TO_TIMESTAMP('2024-06-17 08:52:35.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-17T08:54:26.123Z
UPDATE EXP_Format SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 08:54:26.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540406
;

-- 2024-06-17T08:54:32.608Z
UPDATE EXP_Format SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 08:54:32.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540417
;

-- 2024-06-17T08:54:46.281Z
UPDATE EXP_Format SET Value='EDI_Exp_DesadvLine',Updated=TO_TIMESTAMP('2024-06-17 08:54:46.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540429
;

-- 2024-06-17T08:55:19.517Z
UPDATE EXP_Format SET Value='EDI_Exp_DesadvLine_Pack',Updated=TO_TIMESTAMP('2024-06-17 08:55:19.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540430
;

-- 2024-06-18T06:14:11.881Z
UPDATE EXP_Format SET Value='EXP_M_InOut_DesadvLine_V',Updated=TO_TIMESTAMP('2024-06-18 06:14:11.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540429
;

-- 2024-06-18T06:15:39.885Z
UPDATE EXP_FormatLine SET Name='EDI_Exp_DesadvLine', Value='EDI_Exp_DesadvLine',Updated=TO_TIMESTAMP('2024-06-18 06:15:39.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550695
;

-- 2024-06-18T06:15:48.746Z
UPDATE EXP_Format SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-18 06:15:48.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540406
;

-- 2024-06-18T06:16:49.028Z
UPDATE EXP_FormatLine SET Name='EDI_Exp_DesadvLine_Pack', Value='EDI_Exp_DesadvLine_Pack',Updated=TO_TIMESTAMP('2024-06-18 06:16:49.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550696
;

-- 2024-06-18T06:17:03.484Z
UPDATE EXP_Format SET Value='EXP_M_InOut_DesadvLine_Pack_V',Updated=TO_TIMESTAMP('2024-06-18 06:17:03.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540430
;

-- 2024-06-18T06:17:13.143Z
UPDATE EXP_Format SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-18 06:17:13.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540417
;

-- Reference: EDI_ExportStatus
-- Value: SS
-- ValueName: ShipmentSent
-- 2024-06-18T06:45:26.304Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543696,540381,TO_TIMESTAMP('2024-06-18 06:45:26.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','Shipment Sent',TO_TIMESTAMP('2024-06-18 06:45:26.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SS','ShipmentSent')
;

-- 2024-06-18T06:45:26.313Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543696 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: EDI_ExportStatus
-- Value: SS
-- ValueName: ShipmentSent
-- 2024-06-18T07:12:03.019Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543696
;

-- 2024-06-18T07:12:03.034Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543696
;

-- Value: EDI_Desadv_InOut_EnqueueForExport
-- Classname: de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport
-- 2024-06-18T08:21:11.704Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585400,'Y','de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport','N',TO_TIMESTAMP('2024-06-18 08:21:11.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Enqueue Shipment-Desadv for export','json','N','N','xls','Java',TO_TIMESTAMP('2024-06-18 08:21:11.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Desadv_InOut_EnqueueForExport')
;

-- 2024-06-18T08:21:11.721Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585400 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Field: EDI Lieferavis (DESADV) -> Zugeordnete Lieferungen -> EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- Field: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> Zugeordnete Lieferungen(540664,de.metas.esb.edi) -> EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2024-06-18T08:28:00.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549871,728999,0,540664,0,TO_TIMESTAMP('2024-06-18 08:28:00.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.esb.edi',0,0,'Y','Y','Y','N','N','N','N','Y','N',0,'EDI-Sendestatus',0,0,110,0,1,1,TO_TIMESTAMP('2024-06-18 08:28:00.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-06-18T08:28:00.619Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-18T08:28:00.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541997) 
;

-- 2024-06-18T08:28:00.668Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728999
;

-- 2024-06-18T08:28:00.674Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728999)
;

-- UI Element: EDI Lieferavis (DESADV) -> Zugeordnete Lieferungen.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- UI Element: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> Zugeordnete Lieferungen(540664,de.metas.esb.edi) -> main -> 10 -> default.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2024-06-18T08:28:46.011Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,728999,0,540664,624944,541227,'F',TO_TIMESTAMP('2024-06-18 08:28:45.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-Sendestatus',100,0,0,TO_TIMESTAMP('2024-06-18 08:28:45.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: EDI Lieferavis (DESADV) -> Zugeordnete Lieferungen.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- UI Element: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> Zugeordnete Lieferungen(540664,de.metas.esb.edi) -> main -> 10 -> default.EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2024-06-18T08:28:55.025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-06-18 08:28:55.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=624944
;

-- UI Element: EDI Lieferavis (DESADV) -> Zugeordnete Lieferungen.Sektion
-- Column: M_InOut.AD_Org_ID
-- UI Element: EDI Lieferavis (DESADV)(540256,de.metas.esb.edi) -> Zugeordnete Lieferungen(540664,de.metas.esb.edi) -> main -> 10 -> default.Sektion
-- Column: M_InOut.AD_Org_ID
-- 2024-06-18T08:28:55.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-06-18 08:28:55.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549254
;

-- Process: EDI_Desadv_InOut_EnqueueForExport(de.metas.edi.process.EDI_Desadv_InOut_EnqueueForExport)
-- Table: EDI_Desadv
-- EntityType: de.metas.esb.edi
-- 2024-06-18T08:36:45.359Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585400,540644,541496,TO_TIMESTAMP('2024-06-18 08:36:45.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y',TO_TIMESTAMP('2024-06-18 08:36:45.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;

-- Column: M_InOut_Desadv_V.M_InOut_Desadv_ID
-- Column: M_InOut_Desadv_V.M_InOut_Desadv_ID
-- 2024-06-18T09:00:12.967Z
UPDATE AD_Column SET AD_Reference_ID=13, IsUpdateable='N',Updated=TO_TIMESTAMP('2024-06-18 09:00:12.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588376
;

-- Column: M_InOut_DesadvLine_V.M_InOut_Desadv_ID
-- Column: M_InOut_DesadvLine_V.M_InOut_Desadv_ID
-- 2024-06-18T09:00:41.845Z
UPDATE AD_Column SET AD_Reference_ID=13,Updated=TO_TIMESTAMP('2024-06-18 09:00:41.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588587
;

-- 2024-06-18T09:39:41.454Z
UPDATE EXP_Format SET Value='EDI_Exp_Desadv_InOut',Updated=TO_TIMESTAMP('2024-06-18 09:39:41.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

