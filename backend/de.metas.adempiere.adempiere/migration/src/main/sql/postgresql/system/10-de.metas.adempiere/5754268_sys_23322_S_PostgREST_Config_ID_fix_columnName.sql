
-- Table: S_PostgREST_Config
-- 2025-05-13T11:01:37.321Z
UPDATE AD_Table SET Name='S_PostgREST_Config',Updated=TO_TIMESTAMP('2025-05-13 11:01:37.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=541503
;

-- 2025-05-13T11:01:37.324Z
UPDATE AD_Table_Trl trl SET Name='S_PostgREST_Config' WHERE AD_Table_ID=541503 AND AD_Language='de_DE'
;

-- 2025-05-13T11:03:50.292Z
UPDATE AD_Element SET ColumnName='S_PostgREST_Config_ID',Updated=TO_TIMESTAMP('2025-05-13 11:03:50.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577781
;

-- 2025-05-13T11:03:50.295Z
UPDATE AD_Column SET ColumnName='S_PostgREST_Config_ID' WHERE AD_Element_ID=577781
;

-- 2025-05-13T11:03:50.297Z
UPDATE AD_Process_Para SET ColumnName='S_PostgREST_Config_ID' WHERE AD_Element_ID=577781
;

-- 2025-05-13T11:03:50.301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577781,'de_DE')
;

ALTER TABLE S_PostgREST_Config RENAME COLUMN PostgREST_Config_ID TO S_PostgREST_Config_ID;
