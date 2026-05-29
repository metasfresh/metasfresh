-- Run mode: SWING_CLIENT

-- Name: AD_Table eligible for AttributeSet tab assignment
-- 2025-03-27T09:02:44.586Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540701,'    AD_Table.TableName NOT LIKE ''%\_Trl'' ESCAPE ''\''
    AND AD_Table.TableName NOT LIKE ''I\_%'' ESCAPE ''\''
        AND AD_Table.TableName NOT LIKE ''X\_%'' ESCAPE ''\''
            AND EXISTS (SELECT 1
                        FROM ad_tab tt
                        WHERE tt.ad_table_id = AD_Table.AD_Table_ID
                          AND tt.IsActive = ''Y''
                          AND tt.tablevel = 0)
',TO_TIMESTAMP('2025-03-27 09:02:43.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','AD_Table eligible for AttributeSet tab assignment','S',TO_TIMESTAMP('2025-03-27 09:02:43.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: M_AttributeSet_IncludedTab.AD_Table_ID
-- 2025-03-27T09:03:06.648Z
UPDATE AD_Column SET AD_Val_Rule_ID=540701, IsUpdateable='N',Updated=TO_TIMESTAMP('2025-03-27 09:03:06.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589445
;

