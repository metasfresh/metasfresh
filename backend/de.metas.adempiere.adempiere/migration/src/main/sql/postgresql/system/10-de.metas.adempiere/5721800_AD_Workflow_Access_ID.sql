/*
delete from ad_field where ad_column_id=588185;
delete from ad_column where ad_column_id=588185;
alter table AD_Workflow_Access drop column AD_Workflow_Access_ID;
drop sequence if exists AD_Workflow_Access_SEQ;
 */

DO
$$
    DECLARE
        v_count integer;
    BEGIN
        SELECT COUNT(1)
        INTO v_count
        FROM ad_table t
                 INNER JOIN ad_column c ON c.ad_table_id = t.ad_table_id
        WHERE t.tablename = 'AD_Workflow_Access'
          AND c.columnname = 'AD_Workflow_Access_ID';

        IF (v_count > 0) THEN
            RAISE NOTICE 'Column AD_Workflow_Access.AD_Workflow_Access_ID already exists. Do nothing';
            RETURN;
        END IF;


        -- Column: AD_Workflow_Access.AD_Workflow_Access_ID
        -- 2024-04-15T07:17:16.284Z
        INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name, Updated, UpdatedBy, Version)
        VALUES (0, 588185, 543339, 0, 13, 202, 'AD_Workflow_Access_ID', TO_TIMESTAMP('2024-04-15 07:17:16.144000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'D', 10, 'Y', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'AD_Workflow_Access',
                TO_TIMESTAMP('2024-04-15 07:17:16.144000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 1);

        -- 2024-04-15T07:17:16.286Z
        INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language,
               t.AD_Column_ID,
               t.Name,
               'N',
               t.AD_Client_ID,
               t.AD_Org_ID,
               t.Created,
               t.Createdby,
               t.Updated,
               t.UpdatedBy,
               'Y'
        FROM AD_Language l,
             AD_Column t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Column_ID = 588185
          AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

        -- 2024-04-15T07:17:16.419Z
        /* DDL */
        PERFORM update_Column_Translation_From_AD_Element(543339);

        -- 2024-04-15T07:17:16.537Z
        DROP SEQUENCE IF EXISTS AD_WORKFLOW_ACCESS_SEQ;
        CREATE SEQUENCE AD_WORKFLOW_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000;

        -- 2024-04-15T07:17:16.547Z
        ALTER TABLE AD_Workflow_Access
            ADD COLUMN AD_Workflow_Access_ID numeric(10, 0) NOT NULL DEFAULT NEXTVAL('ad_workflow_access_seq');

        -- 2024-04-15T07:17:16.583Z
        ALTER TABLE AD_Workflow_Access
            DROP CONSTRAINT IF EXISTS ad_workflow_access_pkey;

        -- 2024-04-15T07:17:16.592Z
        ALTER TABLE AD_Workflow_Access
            DROP CONSTRAINT IF EXISTS ad_workflow_access_key;

        -- 2024-04-15T07:17:16.601Z
        ALTER TABLE AD_Workflow_Access
            ADD CONSTRAINT ad_workflow_access_pkey PRIMARY KEY (AD_Workflow_Access_ID);

        -- Field: Produktion Arbeitsablauf(53005,EE01) -> Access(53020,EE01) -> AD_Workflow_Access
        -- Column: AD_Workflow_Access.AD_Workflow_Access_ID
        -- 2024-04-15T07:17:16.873Z
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 588185, 728044, 0, 53020, TO_TIMESTAMP('2024-04-15 07:17:16.747000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 10, 'EE01', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'AD_Workflow_Access', TO_TIMESTAMP('2024-04-15 07:17:16.747000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100);

        -- 2024-04-15T07:17:16.875Z
        INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language,
               t.AD_Field_ID,
               t.Description,
               t.Help,
               t.Name,
               'N',
               t.AD_Client_ID,
               t.AD_Org_ID,
               t.Created,
               t.Createdby,
               t.Updated,
               t.UpdatedBy,
               'Y'
        FROM AD_Language l,
             AD_Field t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Field_ID = 728044
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        -- 2024-04-15T07:17:16.877Z
        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(543339);

        -- 2024-04-15T07:17:16.879Z
        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 728044;

        -- 2024-04-15T07:17:16.880Z
        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(728044);

        -- Field: Rolle - Verwaltung(111,D) -> Workflow-Zugriff(307,D) -> AD_Workflow_Access
        -- Column: AD_Workflow_Access.AD_Workflow_Access_ID
        -- 2024-04-15T07:17:17.003Z
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 588185, 728045, 0, 307, TO_TIMESTAMP('2024-04-15 07:17:16.897000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'AD_Workflow_Access', TO_TIMESTAMP('2024-04-15 07:17:16.897000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100);

        -- 2024-04-15T07:17:17.006Z
        INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language,
               t.AD_Field_ID,
               t.Description,
               t.Help,
               t.Name,
               'N',
               t.AD_Client_ID,
               t.AD_Org_ID,
               t.Created,
               t.Createdby,
               t.Updated,
               t.UpdatedBy,
               'Y'
        FROM AD_Language l,
             AD_Field t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Field_ID = 728045
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        -- 2024-04-15T07:17:17.007Z
        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(543339);

        -- 2024-04-15T07:17:17.010Z
        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 728045;

        -- 2024-04-15T07:17:17.011Z
        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(728045);

        -- Field: Workflow(113,D) -> Berechtigung(312,D) -> AD_Workflow_Access
        -- Column: AD_Workflow_Access.AD_Workflow_Access_ID
        -- 2024-04-15T07:17:17.137Z
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 588185, 728046, 0, 312, TO_TIMESTAMP('2024-04-15 07:17:17.031000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'AD_Workflow_Access', TO_TIMESTAMP('2024-04-15 07:17:17.031000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100);

        -- 2024-04-15T07:17:17.140Z
        INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language,
               t.AD_Field_ID,
               t.Description,
               t.Help,
               t.Name,
               'N',
               t.AD_Client_ID,
               t.AD_Org_ID,
               t.Created,
               t.Createdby,
               t.Updated,
               t.UpdatedBy,
               'Y'
        FROM AD_Language l,
             AD_Field t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Field_ID = 728046
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        -- 2024-04-15T07:17:17.142Z
        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(543339);

        -- 2024-04-15T07:17:17.144Z
        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 728046;

        -- 2024-04-15T07:17:17.145Z
        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(728046);

    END;
$$
;