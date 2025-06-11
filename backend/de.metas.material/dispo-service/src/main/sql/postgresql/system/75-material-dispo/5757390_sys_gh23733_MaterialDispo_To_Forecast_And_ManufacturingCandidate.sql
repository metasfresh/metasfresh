-- Run mode: SWING_CLIENT

-- Name: M_Forecast target for MD_Candidate
-- 2025-06-11T09:54:00.281Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541957,TO_TIMESTAMP('2025-06-11 09:54:00.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','Y','N','M_Forecast target for MD_Candidate',TO_TIMESTAMP('2025-06-11 09:54:00.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-06-11T09:54:00.291Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541957 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Forecast target for MD_Candidate
-- Table: M_Forecast
-- Key: M_Forecast.M_Forecast_ID
-- 2025-06-11T09:56:03.681Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,11918,0,541957,720,328,TO_TIMESTAMP('2025-06-11 09:56:03.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','Y','N','N',TO_TIMESTAMP('2025-06-11 09:56:03.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'(m_forecast.m_forecast_id = @M_Forecast_ID/-1@)')
;

-- Name: MD_Candidate -> M_Forecast
-- Source Reference: MD_Candidate Source
-- Target Reference: M_Forecast target for MD_Candidate
-- 2025-06-11T09:56:50.792Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540894,541957,540456,TO_TIMESTAMP('2025-06-11 09:56:50.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','Y','N','MD_Candidate -> M_Forecast',TO_TIMESTAMP('2025-06-11 09:56:50.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- Name: PP_Order_Candidate_Target_For_MD_Candidate
-- 2025-06-11T10:23:41.005Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541958,TO_TIMESTAMP('2025-06-11 10:23:40.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','PP_Order_Candidate_Target_For_MD_Candidate',TO_TIMESTAMP('2025-06-11 10:23:40.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-06-11T10:23:41.008Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541958 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Order_Candidate_Target_For_MD_Candidate
-- Table: PP_Order_Candidate
-- Key: PP_Order_Candidate.PP_Order_Candidate_ID
-- 2025-06-11T10:24:14.002Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,577875,0,541958,541913,TO_TIMESTAMP('2025-06-11 10:24:13.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-06-11 10:24:13.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS(SELECT 1               from md_candidate_prod_detail               where md_candidate_prod_detail.md_candidate_id = @MD_Candidate_ID / -1@                 AND md_candidate_prod_detail.pp_order_candidate_id = pp_order_candidate.pp_order_candidate_id)')
;

-- Name: MD_Candidate -> PP_Order_Candidate
-- Source Reference: MD_Candidate Source
-- Target Reference: PP_Order_Candidate_Target_For_MD_Candidate
-- 2025-06-11T10:25:34.422Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540894,541958,540457,TO_TIMESTAMP('2025-06-11 10:25:34.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','MD_Candidate -> PP_Order_Candidate',TO_TIMESTAMP('2025-06-11 10:25:34.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: MD_Candidate_Target_For_PP_Order_Candidate
-- 2025-06-11T10:26:00.794Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541959,TO_TIMESTAMP('2025-06-11 10:26:00.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','MD_Candidate_Target_For_PP_Order_Candidate',TO_TIMESTAMP('2025-06-11 10:26:00.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-06-11T10:26:00.795Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541959 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: MD_Candidate_Target_For_PP_Order_Candidate
-- Table: MD_Candidate
-- Key: MD_Candidate.MD_Candidate_ID
-- 2025-06-11T10:26:30.443Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,556473,0,541959,540808,TO_TIMESTAMP('2025-06-11 10:26:30.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2025-06-11 10:26:30.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS(SELECT 1               from md_candidate_prod_detail               where md_candidate_prod_detail.pp_order_candidate_id = @PP_Order_Candidate_ID / -1@                 AND md_candidate_prod_detail.md_candidate_id = md_candidate.md_candidate_id)')
;

-- Name: PP_Order_Candidate -> MD_Candidate
-- Source Reference: PP_Order_Candidate
-- Target Reference: MD_Candidate_Target_For_PP_Order_Candidate
-- 2025-06-11T10:27:26.905Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541494,541959,540458,TO_TIMESTAMP('2025-06-11 10:27:26.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','PP_Order_Candidate -> MD_Candidate',TO_TIMESTAMP('2025-06-11 10:27:26.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- Field: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> Produktionsdisposition
-- Column: MD_Candidate_Prod_Detail.PP_Order_Candidate_ID
-- 2025-06-11T10:45:46.297Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578393,747595,0,540811,TO_TIMESTAMP('2025-06-11 10:45:46.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Produktionsdisposition',TO_TIMESTAMP('2025-06-11 10:45:46.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-11T10:45:46.300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-11T10:45:46.349Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580109)
;

-- 2025-06-11T10:45:46.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747595
;

-- 2025-06-11T10:45:46.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747595)
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Produktionsdisposition
-- Column: MD_Candidate_Prod_Detail.PP_Order_Candidate_ID
-- 2025-06-11T10:46:37.740Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,747595,0,540811,633964,541175,'F',TO_TIMESTAMP('2025-06-11 10:46:37.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Produktionsdisposition',15,0,0,TO_TIMESTAMP('2025-06-11 10:46:37.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Produktionsdisposition
-- Column: MD_Candidate_Prod_Detail.PP_Order_Candidate_ID
-- 2025-06-11T10:46:45.350Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633964
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Produktionsstätte
-- Column: MD_Candidate_Prod_Detail.PP_Plant_ID
-- 2025-06-11T10:46:45.353Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548835
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Product Planning
-- Column: MD_Candidate_Prod_Detail.PP_Product_Planning_ID
-- 2025-06-11T10:46:45.356Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548834
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.BOM Line
-- Column: MD_Candidate_Prod_Detail.PP_Product_BOMLine_ID
-- 2025-06-11T10:46:45.360Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548833
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Beschreibung
-- Column: MD_Candidate_Prod_Detail.Description
-- 2025-06-11T10:46:45.362Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548836
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Manufacturing Order BOM Line
-- Column: MD_Candidate_Prod_Detail.PP_Order_BOMLine_ID
-- 2025-06-11T10:46:45.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548840
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Belegstatus
-- Column: MD_Candidate_Prod_Detail.PP_Order_DocStatus
-- 2025-06-11T10:46:45.367Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548841
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Geplante Menge
-- Column: MD_Candidate_Prod_Detail.PlannedQty
-- 2025-06-11T10:46:45.370Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551010
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Istmenge
-- Column: MD_Candidate_Prod_Detail.ActualQty
-- 2025-06-11T10:46:45.372Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551011
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Sofort kommissionieren
-- Column: MD_Candidate_Prod_Detail.IsPickDirectlyIfFeasible
-- 2025-06-11T10:46:45.374Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551012
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Vom System vorgeschlagen
-- Column: MD_Candidate_Prod_Detail.IsAdvised
-- 2025-06-11T10:46:45.377Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551009
;

-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Produktionsdetail(540811,de.metas.material.dispo) -> main -> 10 -> default.Sektion
-- Column: MD_Candidate_Prod_Detail.AD_Org_ID
-- 2025-06-11T10:46:45.379Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-06-11 10:46:45.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548831
;

CREATE INDEX IF NOT EXISTS md_candidate_prod_detail_pp_order_candidate_id
    ON md_candidate_prod_detail (pp_order_candidate_id)
;
