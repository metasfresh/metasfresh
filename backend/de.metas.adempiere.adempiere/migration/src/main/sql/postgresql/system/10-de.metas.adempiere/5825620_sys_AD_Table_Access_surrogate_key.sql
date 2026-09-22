-- Give AD_Table_Access its standard metasfresh surrogate primary key, mirroring the AD_Window_Access
-- precedent (5462530 / 5721770): AD_Element + AD_Element_Trl, AD_Column (IsKey='Y') + AD_Column_Trl,
-- AD_Sequence, the physical column + PK swap, and a UNIQUE constraint that preserves the previous
-- (AD_Role_ID, AD_Table_ID) uniqueness.
--
-- Without a key column the framework treats every parent-link field as a key and renders AD_Table_ID
-- read-only on a new row, blocking creation of AD_Table_Access rows from the Roles window (window 111,
-- AD_Tab 549493).
--
-- IDs allocated from idserver.metas.de on 2026-09-22:
--   AD_MigrationScript 5825620
--   AD_Element          585481
--   AD_Column           593638
--   AD_Sequence         556657

DO
$$
    DECLARE
        v_count integer;
    BEGIN
        SELECT COUNT(1)
        INTO v_count
        FROM ad_table t
                 INNER JOIN ad_column c ON c.ad_table_id = t.ad_table_id
        WHERE t.tablename = 'AD_Table_Access'
          AND c.columnname = 'AD_Table_Access_ID';

        IF (v_count > 0) THEN
            RAISE NOTICE 'Column AD_Table_Access.AD_Table_Access_ID already exists. Do nothing';
            RETURN;
        END IF;

        -- Element: AD_Table_Access_ID
        INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
        VALUES (0, 585481 /*From ID Server*/, 0, 'AD_Table_Access_ID', TO_TIMESTAMP('2026-09-22 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'AD_Table_Access', 'AD_Table_Access', TO_TIMESTAMP('2026-09-22 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

        INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
        SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
        FROM AD_Language l, AD_Element t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Element_ID = 585481
          AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

        -- Column: AD_Table_Access.AD_Table_Access_ID (the new surrogate key)
        INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AllowZoomTo, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey, IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
        VALUES (0, 593638 /*From ID Server*/, 585481, 0, 13, 565, 'N', 'AD_Table_Access_ID', TO_TIMESTAMP('2026-09-22 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10, 'Y', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'AD_Table_Access', 'NP', TO_TIMESTAMP('2026-09-22 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 1);

        INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
        SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
        FROM AD_Language l, AD_Column t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Column_ID = 593638
          AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

        /* DDL */
        PERFORM update_Column_Translation_From_AD_Element(585481);

        -- AD_Sequence row (belt-and-suspenders metadata; the physical PK sequence below is the one
        -- actually used for nextval — see metasfresh-db skill "New tables — native PK sequence").
        -- A legacy IsTableID='Y' row named 'AD_Table_Access' already exists on this DB (AD_Sequence_ID
        -- 720, seeded with the original ADempiere dictionary, StartNo/CurrentNext/CurrentNextSys
        -- already matching this recipe) — reuse it instead of colliding with it; only create one if a
        -- given instance genuinely lacks it.
        IF NOT EXISTS (SELECT 1 FROM AD_Sequence WHERE Name = 'AD_Table_Access') THEN
            INSERT INTO AD_Sequence (AD_Sequence_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, IsAutoSequence, IncrementNo, StartNo, CurrentNext, CurrentNextSys, IsAudited, IsTableID)
            VALUES (556657 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-22 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-22 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'AD_Table_Access', 'Y', 1, 1000000, 1000000, 50000, 'N', 'Y');
        END IF;

        -- Physical PK sequence (native-sequence naming convention: lower(tablename || '_seq')). Also
        -- pre-exists on this DB as a legacy artifact — reuse it, do not drop/recreate it.
        CREATE SEQUENCE IF NOT EXISTS AD_TABLE_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000;

        -- New physical column, backed by the sequence above (a volatile DEFAULT backfills any
        -- existing rows with a unique value at ADD COLUMN time).
        ALTER TABLE AD_Table_Access
            ADD COLUMN AD_Table_Access_ID numeric(10, 0) NOT NULL DEFAULT NEXTVAL('ad_table_access_seq');

        -- Swap the primary key from the composite (AD_Role_ID, AD_Table_ID) onto the new surrogate key.
        ALTER TABLE AD_Table_Access
            DROP CONSTRAINT IF EXISTS ad_table_access_pkey;

        ALTER TABLE AD_Table_Access
            ADD CONSTRAINT ad_table_access_pkey PRIMARY KEY (AD_Table_Access_ID);

        -- Keep the previous uniqueness as a UNIQUE constraint (one AD_Table_Access row per role+table).
        ALTER TABLE AD_Table_Access
            ADD CONSTRAINT ad_table_access_uq UNIQUE (AD_Role_ID, AD_Table_ID);
    END
$$
;
