-- Run mode: SWING_CLIENT

-- Column: C_Dunning_Candidate.C_DunningLevel_ID
-- 2026-01-12T17:52:15.557Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2026-01-12 17:52:15.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=547345
;

-- Tab: Mahnart(159,de.metas.dunning) -> Stufen
-- Table: C_DunningLevel
-- 2026-01-12T17:56:17.004Z
UPDATE AD_Tab SET IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2026-01-12 17:56:17.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=268
;

-- Name: C_DunningLevel
-- 2026-01-12T17:58:06.411Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542043,TO_TIMESTAMP('2026-01-12 17:58:05.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','C_DunningLevel',TO_TIMESTAMP('2026-01-12 17:58:05.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2026-01-12T17:58:06.589Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542043 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_DunningLevel
-- Table: C_DunningLevel
-- Key: C_DunningLevel.C_DunningLevel_ID
-- 2026-01-12T17:59:18.881Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,3701,0,542043,331,159,TO_TIMESTAMP('2026-01-12 17:59:18.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N',TO_TIMESTAMP('2026-01-12 17:59:18.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Dunning_Candidate.C_DunningLevel_ID
-- 2026-01-12T18:03:30.400Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540633, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-01-12 18:03:30.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=547345
;

-- Column: C_Dunning_Candidate.C_DunningLevel_ID
-- 2026-01-12T18:04:20.956Z
UPDATE AD_Column SET AD_Reference_Value_ID=542043,Updated=TO_TIMESTAMP('2026-01-12 18:04:20.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=547345
;

