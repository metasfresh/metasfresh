/*
delete from ad_field where ad_field_id in (728038, 728039);
delete from ad_column where ad_column_id=588182;
alter table AD_Window_Access drop column AD_Window_Access_ID;
drop sequence if exists AD_WINDOW_ACCESS_SEQ;

select currval('ad_window_access_seq');
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
        WHERE t.tablename = 'AD_Window_Access'
          AND c.columnname = 'AD_Window_Access_ID';

        IF (v_count > 0) THEN
            RAISE NOTICE 'Column AD_Window_Access.AD_Window_Access_ID already exists. Do nothing';
            RETURN;
        END IF;

        -- Column: AD_Window_Access.AD_Window_Access_ID
        -- 2024-04-15T07:02:41.802Z
        INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name, Updated, UpdatedBy, Version)
        VALUES (0, 588182, 543334, 0, 13, 201, 'AD_Window_Access_ID', TO_TIMESTAMP('2024-04-15 07:02:41.653000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'D', 10, 'Y', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'AD_Window_Access', TO_TIMESTAMP('2024-04-15 07:02:41.653000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC',
                100, 1);

        -- 2024-04-15T07:02:41.810Z
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
          AND t.AD_Column_ID = 588182
          AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

        -- 2024-04-15T07:02:41.816Z
        /* DDL */
        PERFORM update_Column_Translation_From_AD_Element(543334);

        -- 2024-04-15T07:02:54.709Z
        DROP SEQUENCE IF EXISTS AD_WINDOW_ACCESS_SEQ;
        CREATE SEQUENCE AD_WINDOW_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000;

        -- 2024-04-15T07:02:54.720Z
        ALTER TABLE AD_Window_Access
            ADD COLUMN AD_Window_Access_ID numeric(10, 0) NOT NULL DEFAULT NEXTVAL('ad_window_access_seq');

        -- 2024-04-15T07:02:54.799Z
        ALTER TABLE AD_Window_Access
            DROP CONSTRAINT IF EXISTS ad_window_access_pkey;

        -- 2024-04-15T07:02:54.811Z
        ALTER TABLE AD_Window_Access
            DROP CONSTRAINT IF EXISTS ad_window_access_key;

        -- 2024-04-15T07:02:54.825Z
        ALTER TABLE AD_Window_Access
            ADD CONSTRAINT ad_window_access_pkey PRIMARY KEY (AD_Window_Access_ID);

        -- Field: Rolle - Verwaltung(111,D) -> Fenster-Zugriff(304,D) -> AD_Window_Access
        -- Column: AD_Window_Access.AD_Window_Access_ID
        -- 2024-04-15T07:02:55.162Z
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 588182, 728038, 0, 304, TO_TIMESTAMP('2024-04-15 07:02:54.910000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'AD_Window_Access', TO_TIMESTAMP('2024-04-15 07:02:54.910000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100);

        -- 2024-04-15T07:02:55.166Z
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
          AND t.AD_Field_ID = 728038
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        -- 2024-04-15T07:02:55.170Z
        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(543334);

        -- 2024-04-15T07:02:55.196Z
        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 728038;

        -- 2024-04-15T07:02:55.199Z
        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(728038);

        -- Field: Fenster Verwaltung(102,D) -> Berechtigung(311,D) -> AD_Window_Access
        -- Column: AD_Window_Access.AD_Window_Access_ID
        -- 2024-04-15T07:02:55.325Z
        INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
        VALUES (0, 588182, 728039, 0, 311, TO_TIMESTAMP('2024-04-15 07:02:55.221000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 10, 'D', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'AD_Window_Access', TO_TIMESTAMP('2024-04-15 07:02:55.221000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100);

        -- 2024-04-15T07:02:55.328Z
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
          AND t.AD_Field_ID = 728039
          AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

        -- 2024-04-15T07:02:55.332Z
        /* DDL */
        PERFORM update_FieldTranslation_From_AD_Name_Element(543334);

        -- 2024-04-15T07:02:55.335Z
        DELETE FROM AD_Element_Link WHERE AD_Field_ID = 728039;

        -- 2024-04-15T07:02:55.336Z
        /* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(728039);
    END
$$
;
