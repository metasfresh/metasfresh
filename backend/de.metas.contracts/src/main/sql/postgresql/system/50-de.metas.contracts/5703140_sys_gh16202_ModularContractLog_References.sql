-- Run mode: SWING_CLIENT

-- Reference: C_Year
-- Table: C_Year
-- Key: C_Year.C_Year_ID
-- 2023-09-18T08:46:10.573Z
UPDATE AD_Ref_Table SET AD_Window_ID=117,Updated=TO_TIMESTAMP('2023-09-18 11:46:10.573','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540133
;

-- Run mode: SWING_CLIENT

-- Name: ModCntr_Module
-- 2023-09-18T09:22:16.270Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541830,TO_TIMESTAMP('2023-09-18 12:22:16.119','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','ModCntr_Module',TO_TIMESTAMP('2023-09-18 12:22:16.119','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-18T09:22:16.280Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541830 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Module
-- Table: ModCntr_Module
-- Key: ModCntr_Module.ModCntr_Module_ID
-- 2023-09-18T09:22:51.861Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,586805,586802,0,541830,542340,541712,TO_TIMESTAMP('2023-09-18 12:22:51.825','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-09-18 12:22:51.825','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Log.ModCntr_Module_ID
-- 2023-09-18T09:23:10.510Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541830, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-09-18 12:23:10.51','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587468
;

