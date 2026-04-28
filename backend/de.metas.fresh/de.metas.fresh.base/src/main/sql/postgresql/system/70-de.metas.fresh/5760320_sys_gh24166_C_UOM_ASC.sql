-- Run mode: SWING_CLIENT

-- Name: C_UOM_ASC
-- 2025-06-24T09:56:15.416Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541960,TO_TIMESTAMP('2025-06-24 09:56:14.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Unit of Measure selection','D','Y','N','C_UOM_ASC',TO_TIMESTAMP('2025-06-24 09:56:14.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
ON CONFLICT (AD_Reference_ID) DO NOTHING;
;

-- 2025-06-24T09:56:15.479Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541960 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
ON CONFLICT (AD_Reference_ID,AD_Language) DO NOTHING;

-- Reference: C_UOM_ASC
-- Table: C_UOM
-- Key: C_UOM.C_UOM_ID
-- 2025-06-24T09:59:05.536Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,486,485,0,541960,146,TO_TIMESTAMP('2025-06-24 09:59:05.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_UOM.Name','N',TO_TIMESTAMP('2025-06-24 09:59:05.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
ON CONFLICT (AD_Reference_ID) DO NOTHING;

-- Update all C_UOM_ID columns from (Table Direct) to (Table) reference, and set (C_UOM_ASC) as reference value
-- 2025-07-08T14:08:20.512Z
UPDATE AD_Column
SET AD_Reference_ID=18,
    AD_Reference_Value_ID=541960,
    Updated=TO_TIMESTAMP('2025-07-08 14:04:52.260000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Reference_ID = 19
  AND AD_Column_ID IN (SELECT AD_Column_ID
                       FROM AD_Column
                       WHERE AD_Element_ID = 215)
;

-- Update all C_UOM_ID columns with (Table) and (Search) reference to set (C_UOM_ASC) as reference value
-- 2025-07-08T14:08:20.512Z
UPDATE AD_Column
SET AD_Reference_Value_ID=541960,
    Updated=TO_TIMESTAMP('2025-07-08 14:04:52.260000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE (AD_Reference_ID = 18 OR AD_Reference_ID = 30)
  AND AD_Column_ID IN (SELECT AD_Column_ID
                       FROM AD_Column
                       WHERE AD_Element_ID = 215)
;

-- Replace all columns using (C_UOM_Default_First) reference value to (C_UOM_ASC)
-- 2025-07-08T14:08:20.512Z
UPDATE AD_Column
SET AD_Reference_Value_ID=541960,
    Updated=TO_TIMESTAMP('2025-07-08 14:04:52.260000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Reference_Value_ID = 114
;