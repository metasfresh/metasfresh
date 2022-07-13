-- Tab: Prüf Projekt_OLD -> Prüfgegenstand
-- Table: C_Project_WO_ObjectUnderTest
-- 2022-07-13T15:05:32.488Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-13 16:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546433
;

-- Column: C_Project_WO_ObjectUnderTest.NumberOfObjectsUnderTest
-- 2022-07-13T15:22:34.915Z
UPDATE AD_Column SET ValueMin='0',Updated=TO_TIMESTAMP('2022-07-13 16:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583628
;

-- 2022-07-13T15:27:58.540Z
INSERT INTO t_alter_column values('c_project_wo_objectundertest','NumberOfObjectsUnderTest','NUMERIC(10)',null,'1')
;

-- 2022-07-13T15:27:58.647Z
UPDATE C_Project_WO_ObjectUnderTest SET NumberOfObjectsUnderTest=1 WHERE NumberOfObjectsUnderTest IS NULL
;

