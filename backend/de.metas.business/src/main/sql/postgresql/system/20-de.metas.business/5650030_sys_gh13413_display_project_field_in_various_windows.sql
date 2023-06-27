-- Field: Auftrag -> Auftrag -> Projekt
-- Column: C_Order.C_Project_ID
-- 2022-08-08T13:32:10.260Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2022-08-08 15:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2593
;

-- Field: Auftrag -> Auftragsposition -> Projekt
-- Column: C_OrderLine.C_Project_ID
-- 2022-08-08T13:32:41.040Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2022-08-08 15:32:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12745
;

-- Field: Lieferung -> Lieferung -> Projekt
-- Column: M_InOut.C_Project_ID
-- 2022-08-08T13:35:18.349Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2022-08-08 15:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7831
;

-- 2022-08-08T13:42:32.503Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000015,549703,TO_TIMESTAMP('2022-08-08 15:42:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','diverse',40,TO_TIMESTAMP('2022-08-08 15:42:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferung -> Lieferung.Projekt
-- Column: M_InOut.C_Project_ID
-- 2022-08-08T13:43:02.609Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7831,0,257,549703,611328,'F',TO_TIMESTAMP('2022-08-08 15:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',10,0,0,TO_TIMESTAMP('2022-08-08 15:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T13:44:07.804Z
UPDATE AD_UI_ElementGroup SET SeqNo=25,Updated=TO_TIMESTAMP('2022-08-08 15:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549703
;

-- Field: Rechnung_OLD -> Rechnung -> Projekt
-- Column: C_Invoice.C_Project_ID
-- 2022-08-08T13:47:25.104Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2022-08-08 15:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2775
;

-- UI Element: Rechnung_OLD -> Rechnung.Projekt
-- Column: C_Invoice.C_Project_ID
-- 2022-08-08T13:50:58.924Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2775,0,263,543274,611329,'F',TO_TIMESTAMP('2022-08-08 15:50:58','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',30,0,0,TO_TIMESTAMP('2022-08-08 15:50:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnung_OLD -> Rechnung.Projekt
-- Column: C_Invoice.C_Project_ID
-- 2022-08-08T13:51:57.831Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-08-08 15:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611329
;

-- UI Element: Rechnung_OLD -> Rechnung.Sektion
-- Column: C_Invoice.AD_Org_ID
-- 2022-08-08T13:51:57.836Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-08-08 15:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=540791
;

-- Field: Rechnung_OLD -> Rechnungsposition -> Projekt
-- Column: C_InvoiceLine.C_Project_ID
-- 2022-08-08T13:52:42.066Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-08-08 15:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12746
;

-- Column: M_ShipmentSchedule.C_Project_ID
-- 2022-08-08T14:02:52.345Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584002,208,0,19,500221,'C_Project_ID',TO_TIMESTAMP('2022-08-08 16:02:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','de.metas.inoutcandidate',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2022-08-08 16:02:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-08T14:02:52.346Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584002 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-08T14:02:52.368Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- 2022-08-08T14:03:04.298Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2022-08-08T14:03:04.430Z
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT CProject_MShipmentSchedule FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lieferdisposition -> Auslieferplan -> Projekt
-- Column: M_ShipmentSchedule.C_Project_ID
-- 2022-08-08T14:04:01.655Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584002,703922,0,500221,0,TO_TIMESTAMP('2022-08-08 16:04:01','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',0,'D','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Projekt',0,720,0,1,1,TO_TIMESTAMP('2022-08-08 16:04:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T14:04:01.656Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T14:04:01.658Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-08-08T14:04:01.698Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703922
;

-- 2022-08-08T14:04:01.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703922)
;

-- 2022-08-08T14:05:04.597Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540027,549707,TO_TIMESTAMP('2022-08-08 16:05:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','finance',28,TO_TIMESTAMP('2022-08-08 16:05:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T14:05:31.343Z
UPDATE AD_UI_ElementGroup SET Name='financial',Updated=TO_TIMESTAMP('2022-08-08 16:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549707
;

-- UI Element: Lieferdisposition -> Auslieferplan.Projekt
-- Column: M_ShipmentSchedule.C_Project_ID
-- 2022-08-08T14:05:51.818Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703922,0,500221,549707,611335,'F',TO_TIMESTAMP('2022-08-08 16:05:51','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',10,0,0,TO_TIMESTAMP('2022-08-08 16:05:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferdisposition -> Auslieferplan.Projekt
-- Column: M_ShipmentSchedule.C_Project_ID
-- 2022-08-08T14:13:38.322Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-08-08 16:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611335
;

-- UI Element: Lieferdisposition -> Auslieferplan.Sektion
-- Column: M_ShipmentSchedule.AD_Org_ID
-- 2022-08-08T14:13:38.326Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-08-08 16:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547302
;

-- Field: Rechnungsdisposition_OLD -> Rechnungskandidaten -> Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2022-08-08T14:17:38.819Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572537,703923,0,540279,0,TO_TIMESTAMP('2022-08-08 16:17:38','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',0,'D','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Projekt',0,560,0,1,1,TO_TIMESTAMP('2022-08-08 16:17:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T14:17:38.820Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=703923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-08T14:17:38.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-08-08T14:17:38.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=703923
;

-- 2022-08-08T14:17:38.839Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(703923)
;

-- 2022-08-08T14:18:20.138Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540046,549708,TO_TIMESTAMP('2022-08-08 16:18:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','financial',50,TO_TIMESTAMP('2022-08-08 16:18:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2022-08-08T14:18:34.786Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703923,0,540279,549708,611336,'F',TO_TIMESTAMP('2022-08-08 16:18:34','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',10,0,0,TO_TIMESTAMP('2022-08-08 16:18:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2022-08-08T14:18:47.298Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-08-08 16:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611336
;

-- UI Element: Rechnungsdisposition_OLD -> Rechnungskandidaten.Sektion
-- Column: C_Invoice_Candidate.AD_Org_ID
-- 2022-08-08T14:18:47.303Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-08-08 16:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541953
;

-- 2022-08-08T14:22:17.387Z
UPDATE AD_UI_ElementGroup SET SeqNo=25,Updated=TO_TIMESTAMP('2022-08-08 16:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543527
;

-- 2022-08-08T14:26:01.369Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540035,549709,TO_TIMESTAMP('2022-08-08 16:26:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','financial',28,TO_TIMESTAMP('2022-08-08 16:26:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Vorgang (alle) -> Vorgang.Projekt
-- Column: R_Request.C_Project_ID
-- 2022-08-08T14:26:16.080Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,557236,0,402,549709,611337,'F',TO_TIMESTAMP('2022-08-08 16:26:16','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',10,0,0,TO_TIMESTAMP('2022-08-08 16:26:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T14:52:29.611Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540011,549710,TO_TIMESTAMP('2022-08-08 16:52:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','financial',55,TO_TIMESTAMP('2022-08-08 16:52:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftrag -> Auftrag.Projekt
-- Column: C_Order.C_Project_ID
-- 2022-08-08T14:52:47.222Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2593,0,186,549710,611338,'F',TO_TIMESTAMP('2022-08-08 16:52:47','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',10,0,0,TO_TIMESTAMP('2022-08-08 16:52:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftrag -> Auftrag.Projekt
-- Column: C_Order.C_Project_ID
-- 2022-08-08T14:54:54.591Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544803
;

-- Field: Auftrag -> Auftragsposition -> Projekt
-- Column: C_OrderLine.C_Project_ID
-- 2022-08-08T14:56:40.026Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-08-08 16:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12745
;

-- UI Element: Auftrag -> Auftragsposition.Projekt
-- Column: C_OrderLine.C_Project_ID
-- 2022-08-08T14:57:12.006Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12745,0,187,1000005,611339,'F',TO_TIMESTAMP('2022-08-08 16:57:11','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',440,0,0,TO_TIMESTAMP('2022-08-08 16:57:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T16:27:18.387Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540678,541175,540354,TO_TIMESTAMP('2022-08-08 18:27:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_ShipmentSchedule -> C_Project',TO_TIMESTAMP('2022-08-08 18:27:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_ShipmentSchedule_SOTrx_Source
-- 2022-08-08T16:28:41.807Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541626,TO_TIMESTAMP('2022-08-08 18:28:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_ShipmentSchedule_SOTrx_Source',TO_TIMESTAMP('2022-08-08 18:28:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-08T16:28:41.808Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541626 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_ShipmentSchedule_SOTrx_Source
-- Table: M_ShipmentSchedule_ExportAudit_Item
-- Key: M_ShipmentSchedule_ExportAudit_Item.M_ShipmentSchedule_ID
-- 2022-08-08T16:30:27.531Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,571006,0,541626,541504,TO_TIMESTAMP('2022-08-08 18:30:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-08 18:30:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: M_ShipmentSchedule_SOTrx_Source
-- Table: M_ShipmentSchedule
-- Key: M_ShipmentSchedule.M_ShipmentSchedule_ID
-- 2022-08-08T16:31:13.282Z
UPDATE AD_Ref_Table SET AD_Key=500232, AD_Table_ID=500221,Updated=TO_TIMESTAMP('2022-08-08 18:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541626
;

-- Name: C_Project_Target_For_M_ShipmentSchedule
-- 2022-08-08T16:34:41.561Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541627,TO_TIMESTAMP('2022-08-08 18:34:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project_Target_For_M_ShipmentSchedule',TO_TIMESTAMP('2022-08-08 18:34:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-08T16:34:41.562Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541627 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Project_Target_For_M_ShipmentSchedule
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-08T16:36:39.247Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1349,0,541627,203,TO_TIMESTAMP('2022-08-08 18:36:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-08 18:36:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_Project_Target_For_M_ShipmentSchedule
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-08T16:37:02.303Z
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1  from C_Project p  join M_ShipmentSchedule ss on ss.C_Project_ID = p.C_Project_ID   where ss.M_ShipmentSchedule_ID =@M_ShipmentSchedule_ID/-1@ and C_Project.C_Project_ID = ss.C_Project_ID )',Updated=TO_TIMESTAMP('2022-08-08 18:37:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541627
;

-- 2022-08-08T16:39:14.691Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541626, AD_Reference_Target_ID=541627,Updated=TO_TIMESTAMP('2022-08-08 18:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540354
;

-- 2022-08-08T16:50:10.860Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541626,541627,540355,TO_TIMESTAMP('2022-08-08 18:50:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Invoice_Candidate-> C_Project',TO_TIMESTAMP('2022-08-08 18:50:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: C_Invoice_Candidate_SoTrx_Source
-- 2022-08-08T16:50:52.543Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541628,TO_TIMESTAMP('2022-08-08 18:50:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Invoice_Candidate_SoTrx_Source',TO_TIMESTAMP('2022-08-08 18:50:52','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-08T16:50:52.544Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541628 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Invoice_Candidate_SoTrx_Source
-- Table: C_Invoice_Candidate
-- Key: C_Invoice_Candidate.C_Invoice_Candidate_ID
-- 2022-08-08T16:53:18.564Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,544906,0,541628,540270,TO_TIMESTAMP('2022-08-08 18:53:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-08 18:53:18','YYYY-MM-DD HH24:MI:SS'),100,'IsSoTrx=''Y''')
;

-- 2022-08-08T16:53:42.687Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541628,Updated=TO_TIMESTAMP('2022-08-08 18:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540355
;

-- Name: C_Project_Target_For_C_Invoice_Candidate
-- 2022-08-08T16:54:16.482Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541629,TO_TIMESTAMP('2022-08-08 18:54:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project_Target_For_C_Invoice_Candidate',TO_TIMESTAMP('2022-08-08 18:54:16','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-08T16:54:16.483Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541629 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_Project_Target_For_C_Invoice_Candidate
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-08T16:54:50.276Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,1349,0,541629,203,TO_TIMESTAMP('2022-08-08 18:54:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2022-08-08 18:54:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: C_Project_Target_For_C_Invoice_Candidate
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-08T16:54:54.411Z
UPDATE AD_Ref_Table SET EntityType='D',Updated=TO_TIMESTAMP('2022-08-08 18:54:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541629
;

-- Reference: C_Project_Target_For_C_Invoice_Candidate
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-08T16:57:17.857Z
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1  from C_Project p  join c_invoice_candidate ic on ic.C_Project_ID = p.C_Project_ID where ic.c_invoice_candidate_id=@C_Invoice_Candidate_ID/-1@ and C_Project.C_Project_ID = ic.C_Project_ID )',Updated=TO_TIMESTAMP('2022-08-08 18:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541629
;

-- 2022-08-08T16:58:29.234Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541629,Updated=TO_TIMESTAMP('2022-08-08 18:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540355
;

-- 2022-08-08T16:58:35.241Z
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2022-08-08 18:58:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540355
;

-- Field: Vorgang (alle) -> Vorgang -> Projekt
-- Column: R_Request.C_Project_ID
-- 2022-08-08T17:04:18.629Z
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-08-08 19:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- Field: Vorgang (alle) -> Vorgang -> Projekt
-- Column: R_Request.C_Project_ID
-- 2022-08-08T17:04:40.494Z
UPDATE AD_Field SET DisplayLogic='
',Updated=TO_TIMESTAMP('2022-08-08 19:04:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- Field: Vorgang (alle) -> Vorgang -> Projekt
-- Column: R_Request.C_Project_ID
-- 2022-08-08T17:05:09.399Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2022-08-08 19:05:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557236
;

-- 2022-08-08T17:06:28.227Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541628,541629,540356,TO_TIMESTAMP('2022-08-08 19:06:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','R_Request-> C_Project',TO_TIMESTAMP('2022-08-08 19:06:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: R_Request_Source
-- 2022-08-08T17:06:38.137Z
UPDATE AD_Reference SET Name='R_Request_Source',Updated=TO_TIMESTAMP('2022-08-08 19:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541628
;

-- Name: C_Invoice_Candidate_SoTrx_Source
-- 2022-08-08T17:07:54.466Z
UPDATE AD_Reference SET Name='C_Invoice_Candidate_SoTrx_Source',Updated=TO_TIMESTAMP('2022-08-08 19:07:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541628
;

-- Name: R_Request_Source
-- 2022-08-08T17:08:19.725Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541630,TO_TIMESTAMP('2022-08-08 19:08:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','R_Request_Source',TO_TIMESTAMP('2022-08-08 19:08:19','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-08T17:08:19.726Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541630 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: R_Request_Source
-- Table: R_Request
-- Key: R_Request.R_Request_ID
-- 2022-08-08T17:08:48.291Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,5415,0,541630,417,TO_TIMESTAMP('2022-08-08 19:08:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-08 19:08:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-08T17:09:24.406Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541630,Updated=TO_TIMESTAMP('2022-08-08 19:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540356
;

-- Name: R_Request_Target_For_C_Invoice_Candidate
-- 2022-08-08T17:09:47.926Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541631,TO_TIMESTAMP('2022-08-08 19:09:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','R_Request_Target_For_C_Invoice_Candidate',TO_TIMESTAMP('2022-08-08 19:09:47','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-08T17:09:47.926Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541631 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: R_Request_Target_For_C_Invoice_Candidate
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-08T17:12:24.441Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1349,0,541631,203,TO_TIMESTAMP('2022-08-08 19:12:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-08 19:12:24','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from C_Project p join r_request rr on rr.C_Project_ID = p.C_Project_ID where rr.r_request_id=@R_Request_ID/-1@ and C_Project.C_Project_ID = rr.C_Project_ID )')
;

-- 2022-08-08T17:13:43.436Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541631,Updated=TO_TIMESTAMP('2022-08-08 19:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540356
;

-- Name: C_Project_Target_For_R_Request
-- 2022-08-08T17:13:59.326Z
UPDATE AD_Reference SET Name='C_Project_Target_For_R_Request',Updated=TO_TIMESTAMP('2022-08-08 19:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541631
;

-- 2022-08-08T17:14:40.067Z
UPDATE AD_Reference_Trl SET Name='C_Project_Target_For_R_Request',Updated=TO_TIMESTAMP('2022-08-08 19:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541631
;

-- 2022-08-08T17:14:44.989Z
UPDATE AD_Reference_Trl SET Name='C_Project_Target_For_R_Request',Updated=TO_TIMESTAMP('2022-08-08 19:14:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541631
;

-- 2022-08-08T17:14:50.199Z
UPDATE AD_Reference_Trl SET Name='C_Project_Target_For_R_Request',Updated=TO_TIMESTAMP('2022-08-08 19:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541631
;

-- 2022-08-08T17:14:55.191Z
UPDATE AD_Reference_Trl SET Name='C_Project_Target_For_R_Request',Updated=TO_TIMESTAMP('2022-08-08 19:14:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541631
;

-- UI Element: Vorgang (alle) -> Vorgang.Projekt
-- Column: R_Request.C_Project_ID
-- 2022-08-08T17:17:45.051Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=611337
;

-- 2022-08-08T17:17:49.724Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549709
;

