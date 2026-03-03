-- 2026-03-02
-- https://github.com/metasfresh/me03/issues/28440
-- Add dropship columns to C_PurchaseCandidate

-- Physical columns
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS IsDropShip CHAR(1) DEFAULT 'N' NOT NULL CHECK (IsDropShip IN ('Y','N'));
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_BPartner_ID NUMERIC(10);
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_Location_ID NUMERIC(10);
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_User_ID NUMERIC(10);

-- AD metadata: columns + fields (dynamic ID allocation to avoid collisions across DB instances)
-- TODO: replace with ID-server-allocated IDs once idserver.metas.de is reachable
DO $$
DECLARE
    v_table_id  CONSTANT INT := 540861; -- C_PurchaseCandidate
    v_tab_id    CONSTANT INT := 540894; -- C_PurchaseCandidate tab
    v_col_id    INT;
    v_field_id  INT;
BEGIN
    -- 1) IsDropShip
    IF NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Table_ID = v_table_id AND ColumnName = 'IsDropShip') THEN
        SELECT COALESCE(MAX(AD_Column_ID), 0) + 1 INTO v_col_id FROM AD_Column;
        INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                               ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                               AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                               DefaultValue, EntityType, FieldLength, Version, IsKey, IsParent,
                               IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging)
        VALUES (v_col_id, v_table_id, 2466, 20,
                'IsDropShip', 'Drop Shipment', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'Y', 'Y', 'N',
                'N', 'de.metas.purchasecandidate', 1, 0, 'N', 'N',
                'N', 'N', 'N', 'N', 'Y');

        SELECT COALESCE(MAX(AD_Field_ID), 0) + 1 INTO v_field_id FROM AD_Field;
        INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                              Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                              SeqNo, EntityType)
        VALUES (v_field_id, v_tab_id, v_col_id, NULL,
                'Drop Shipment', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'Y', 'N', 'N',
                310, 'de.metas.purchasecandidate');
    END IF;

    -- 2) DropShip_BPartner_ID
    IF NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Table_ID = v_table_id AND ColumnName = 'DropShip_BPartner_ID') THEN
        SELECT COALESCE(MAX(AD_Column_ID), 0) + 1 INTO v_col_id FROM AD_Column;
        INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                               ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                               AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                               MandatoryLogic,
                               EntityType, FieldLength, Version, IsKey, IsParent,
                               IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging)
        VALUES (v_col_id, v_table_id, 53458, 30, 138,
                'DropShip_BPartner_ID', 'Drop Shipment Partner', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'N', 'Y', 'N',
                '@IsDropShip/''N''@=''Y''',
                'de.metas.purchasecandidate', 10, 0, 'N', 'N',
                'N', 'N', 'N', 'N', 'Y');

        SELECT COALESCE(MAX(AD_Field_ID), 0) + 1 INTO v_field_id FROM AD_Field;
        INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                              Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                              SeqNo, DisplayLogic, EntityType)
        VALUES (v_field_id, v_tab_id, v_col_id, NULL,
                'Drop Shipment Partner', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'Y', 'N', 'N',
                320, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate');
    END IF;

    -- 3) DropShip_Location_ID
    IF NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Table_ID = v_table_id AND ColumnName = 'DropShip_Location_ID') THEN
        SELECT COALESCE(MAX(AD_Column_ID), 0) + 1 INTO v_col_id FROM AD_Column;
        INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                               ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                               AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                               MandatoryLogic,
                               EntityType, FieldLength, Version, IsKey, IsParent,
                               IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging)
        VALUES (v_col_id, v_table_id, 53459, 18, 159,
                'DropShip_Location_ID', 'Drop Shipment Location / Address', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'N', 'Y', 'N',
                '@IsDropShip/''N''@=''Y''',
                'de.metas.purchasecandidate', 10, 0, 'N', 'N',
                'N', 'N', 'N', 'N', 'Y');

        SELECT COALESCE(MAX(AD_Field_ID), 0) + 1 INTO v_field_id FROM AD_Field;
        INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                              Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                              SeqNo, DisplayLogic, EntityType)
        VALUES (v_field_id, v_tab_id, v_col_id, NULL,
                'Drop Shipment Location / Address', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'Y', 'N', 'N',
                330, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate');
    END IF;

    -- 4) DropShip_User_ID
    IF NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Table_ID = v_table_id AND ColumnName = 'DropShip_User_ID') THEN
        SELECT COALESCE(MAX(AD_Column_ID), 0) + 1 INTO v_col_id FROM AD_Column;
        INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                               ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                               AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                               EntityType, FieldLength, Version, IsKey, IsParent,
                               IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging)
        VALUES (v_col_id, v_table_id, 53460, 18, 110,
                'DropShip_User_ID', 'Drop Shipment Contact', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'N', 'Y', 'N',
                'de.metas.purchasecandidate', 10, 0, 'N', 'N',
                'N', 'N', 'N', 'N', 'Y');

        SELECT COALESCE(MAX(AD_Field_ID), 0) + 1 INTO v_field_id FROM AD_Field;
        INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                              Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                              SeqNo, DisplayLogic, EntityType)
        VALUES (v_field_id, v_tab_id, v_col_id, NULL,
                'Drop Shipment Contact', 'Y', NOW(), 100, NOW(), 100,
                0, 0, 'Y', 'N', 'N',
                340, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate');
    END IF;
END $$;
