/*
delete from ad_field where ad_column_id=588183;
delete from ad_column where ad_column_id=588183;
alter table AD_Process_Access drop column AD_Process_Access_ID;
drop sequence if exists AD_Process_Access_SEQ;
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
        WHERE t.tablename = 'AD_Process_Access'
          AND c.columnname = 'AD_Process_Access_ID';

        IF (v_count > 0) THEN
            RAISE NOTICE 'Column AD_Process_Access.AD_Process_Access_ID already exists. Do nothing';
            RETURN;
        END IF;


        -- Column: AD_Process_Access.AD_Process_Access_ID
        -- 2024-04-15T07:15:49.122Z
        INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name, Updated, UpdatedBy, Version)
        VALUES (0, 588183, 543337, 0, 13, 197, 'AD_Process_Access_ID', TO_TIMESTAMP('2024-04-15 07:15:48.737000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'D', 10, 'Y', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'Process Access', TO_TIMESTAMP('2024-04-15 07:15:48.737000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC',
                100, 1);

        -- 2024-04-15T07:15:49.124Z
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
          AND t.AD_Column_ID = 588183
          AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

        -- 2024-04-15T07:15:49.269Z
        /* DDL */
        PERFORM update_Column_Translation_From_AD_Element(543337);

        -- 2024-04-15T07:15:54.782Z
        DROP SEQUENCE IF EXISTS AD_PROCESS_ACCESS_SEQ;
        CREATE SEQUENCE AD_PROCESS_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000;

        -- 2024-04-15T07:15:54.806Z
        ALTER TABLE AD_Process_Access
            ADD COLUMN AD_Process_Access_ID numeric(10, 0) NOT NULL DEFAULT NEXTVAL('ad_process_access_seq');

        -- 2024-04-15T07:15:54.876Z
        ALTER TABLE AD_Process_Access
            DROP CONSTRAINT IF EXISTS ad_process_access_pkey;

        -- 2024-04-15T07:15:54.890Z
        ALTER TABLE AD_Process_Access
            DROP CONSTRAINT IF EXISTS ad_process_access_key;

        -- 2024-04-15T07:15:54.899Z
        ALTER TABLE AD_Process_Access
            ADD CONSTRAINT ad_process_access_pkey PRIMARY KEY (AD_Process_Access_ID);

        -- Field: Rolle - Verwaltung(111,D) -> Prozess-Zugriff(305,D) -> Process Access
        -- Column: AD_Process_Access.AD_Process_Access_ID
        -- 2024-04-15T07:15:55.435Z
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 588183, 728040, 0, 305, TO_TIMESTAMP('2024-04-15 07:15:55.286000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Process Access', TO_TIMESTAMP('2024-04-15 07:15:55.286000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100);

        -- 2024-04-15T07:15:55.439Z
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
          AND t.AD_Field_ID = 728040
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        -- 2024-04-15T07:15:55.442Z
        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(543337);

        -- 2024-04-15T07:15:55.446Z
        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 728040;

        -- 2024-04-15T07:15:55.447Z
        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(728040);

        -- Field: Bericht & Prozess(165,D) -> Berechtigung Berichts Zugriff(308,D) -> Process Access
        -- Column: AD_Process_Access.AD_Process_Access_ID
        -- 2024-04-15T07:15:55.569Z
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 588183, 728041, 0, 308, TO_TIMESTAMP('2024-04-15 07:15:55.463000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Process Access', TO_TIMESTAMP('2024-04-15 07:15:55.463000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100);

        -- 2024-04-15T07:15:55.572Z
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
          AND t.AD_Field_ID = 728041
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        -- 2024-04-15T07:15:55.575Z
        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(543337);

        -- 2024-04-15T07:15:55.577Z
        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 728041;

        -- 2024-04-15T07:15:55.579Z
        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(728041);

    END;
$$
;
