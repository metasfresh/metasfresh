-- Reference: C_ProjectType Category
-- Value: A
-- Name: Asset-Projekt
-- 2022-05-31T10:19:49.495Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2022-05-31 13:19:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=572
;

-- Reference: C_ProjectType Category
-- Value: S
-- Name: Service (Charge) Project
-- 2022-05-31T10:20:02.681Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2022-05-31 13:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=574
;

-- Tab: Projekt -> Projekt
-- Table: C_Project
-- 2022-05-31T10:21:58.144Z
UPDATE AD_Tab SET WhereClause='ProjectCategory=N',Updated=TO_TIMESTAMP('2022-05-31 13:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=157
;

-- Name: C_ProjectType_ID with ProjectCategory=General
-- 2022-05-31T10:23:03.628Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540584,'C_ProjectType.ProjectCategory=''N''',TO_TIMESTAMP('2022-05-31 13:23:03','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','C_ProjectType_ID with ProjectCategory=General','S',TO_TIMESTAMP('2022-05-31 13:23:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Projekt -> Projekt -> Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-31T10:23:19.310Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540584,Updated=TO_TIMESTAMP('2022-05-31 13:23:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6548
;

-- Window: Projekt (webUI), InternalName=Projekt (webUI)
-- 2022-05-31T10:24:40.833Z
UPDATE AD_Window SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-31 13:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540668
;

-- Tab: Projekt (webUI) -> Projekt
-- Table: C_Project
-- 2022-05-31T10:24:55.806Z
UPDATE AD_Tab SET WhereClause='ProjectCategory=N',Updated=TO_TIMESTAMP('2022-05-31 13:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541830
;

-- Field: Projekt (webUI) -> Projekt -> Projekt - Projektart
-- Column: C_Project.C_ProjectType_ID
-- 2022-05-31T10:25:25.662Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540584,Updated=TO_TIMESTAMP('2022-05-31 13:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582282
;

-- Window: Projekt (webUI), InternalName=Projekt (webUI)
-- 2022-05-31T10:25:45.103Z
UPDATE AD_Window SET Overrides_Window_ID=130,Updated=TO_TIMESTAMP('2022-05-31 13:25:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540668
;

-- Tab: Projekt (webUI) -> Projekt
-- Table: C_Project
-- 2022-05-31T10:26:44.644Z
UPDATE AD_Tab SET WhereClause='ProjectCategory=''N''',Updated=TO_TIMESTAMP('2022-05-31 13:26:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541830
;

-- Tab: Projekt -> Projekt
-- Table: C_Project
-- 2022-05-31T10:26:55.756Z
UPDATE AD_Tab SET WhereClause='ProjectCategory=''N''',Updated=TO_TIMESTAMP('2022-05-31 13:26:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=157
;

-- Column: C_Project.enddatetime
-- 2022-05-31T10:30:12.772Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2022-05-31 13:30:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568793
;

-- Column: C_Project.startdatetime
-- 2022-05-31T10:31:10.988Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2022-05-31 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568792
;

-- Column: C_Project.startdatetime
-- 2022-05-31T10:32:17.391Z
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2022-05-31 13:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568792
;

-- Column: C_Project.enddatetime
-- 2022-05-31T10:32:21.168Z
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2022-05-31 13:32:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568793
;








-- Name: C_Project_WO_Step_for_C_Project_ID
-- 2022-05-31T10:36:00.557Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540585,'C_Project_WO_Step.C_Project_ID=@C_Project_ID@',TO_TIMESTAMP('2022-05-31 13:36:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_Project_WO_Step_for_C_Project_ID','S',TO_TIMESTAMP('2022-05-31 13:36:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Project_WO_Resource.C_Project_WO_Step_ID
-- 2022-05-31T10:36:15.909Z
UPDATE AD_Column SET AD_Val_Rule_ID=540585, IsUpdateable='N',Updated=TO_TIMESTAMP('2022-05-31 13:36:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583244
;

-- Field: Work Order Project -> Project Resource -> Budget Project
-- Column: C_Project_WO_Resource.Budget_Project_ID
-- 2022-05-31T10:37:09.481Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-31 13:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698027
;

-- Field: Work Order Project -> Project Resource -> Project Resource Budget
-- Column: C_Project_WO_Resource.C_Project_Resource_Budget_ID
-- 2022-05-31T10:37:19.708Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-31 13:37:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698026
;

-- Field: Work Order Project -> Project Resource -> Duration Unit
-- Column: C_Project_WO_Resource.DurationUnit
-- 2022-05-31T10:37:38.230Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-31 13:37:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698024
;

-- Field: Work Order Project -> Project Resource -> Duration
-- Column: C_Project_WO_Resource.Duration
-- 2022-05-31T10:37:41.025Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-31 13:37:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698023
;



