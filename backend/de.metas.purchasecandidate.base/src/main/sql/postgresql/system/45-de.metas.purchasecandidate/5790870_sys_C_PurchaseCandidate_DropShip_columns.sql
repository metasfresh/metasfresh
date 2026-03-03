-- 2026-03-02
-- https://github.com/metasfresh/me03/issues/28440
-- Add dropship columns to C_PurchaseCandidate
-- TODO: replace AD_Column_IDs/AD_Field_IDs with values from ID server (idserver.metas.de) once reachable

-- Physical columns
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS IsDropShip CHAR(1) DEFAULT 'N' NOT NULL CHECK (IsDropShip IN ('Y','N'));
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_BPartner_ID NUMERIC(10);
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_Location_ID NUMERIC(10);
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_User_ID NUMERIC(10);

-- AD_Column: IsDropShip
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                        ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                        DefaultValue, EntityType, FieldLength, Version, IsKey, IsParent,
                        IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                        IsAllowLogging)
VALUES (1000063, 540861, 2466, 20,
        'IsDropShip', 'Drop Shipment', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'Y', 'Y', 'N',
        'N', 'de.metas.purchasecandidate', 1, 0, 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y');

-- AD_Column: DropShip_BPartner_ID (MandatoryLogic: show as mandatory when IsDropShip='Y')
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                        ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                        MandatoryLogic,
                        EntityType, FieldLength, Version, IsKey, IsParent,
                        IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                        IsAllowLogging)
VALUES (1000064, 540861, 53458, 30, 138,
        'DropShip_BPartner_ID', 'Drop Shipment Partner', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'N', 'Y', 'N',
        '@IsDropShip/''N''@=''Y''',
        'de.metas.purchasecandidate', 10, 0, 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y');

-- AD_Column: DropShip_Location_ID (MandatoryLogic: show as mandatory when IsDropShip='Y')
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                        ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                        MandatoryLogic,
                        EntityType, FieldLength, Version, IsKey, IsParent,
                        IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                        IsAllowLogging)
VALUES (1000065, 540861, 53459, 18, 159,
        'DropShip_Location_ID', 'Drop Shipment Location / Address', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'N', 'Y', 'N',
        '@IsDropShip/''N''@=''Y''',
        'de.metas.purchasecandidate', 10, 0, 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y');

-- AD_Column: DropShip_User_ID
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                        ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                        EntityType, FieldLength, Version, IsKey, IsParent,
                        IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted,
                        IsAllowLogging)
VALUES (1000066, 540861, 53460, 18, 110,
        'DropShip_User_ID', 'Drop Shipment Contact', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'N', 'Y', 'N',
        'de.metas.purchasecandidate', 10, 0, 'N', 'N',
        'N', 'N', 'N', 'N',
        'Y');

-- AD_Field: IsDropShip (SeqNo 310)
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                       Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                       SeqNo, EntityType)
VALUES (1000007, 540894, 1000063, NULL,
        'Drop Shipment', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'Y', 'N', 'N',
        310, 'de.metas.purchasecandidate');

-- AD_Field: DropShip_BPartner_ID (SeqNo 320, DisplayLogic: @IsDropShip/N@='Y')
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                       Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                       SeqNo, DisplayLogic, EntityType)
VALUES (1000008, 540894, 1000064, NULL,
        'Drop Shipment Partner', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'Y', 'N', 'N',
        320, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate');

-- AD_Field: DropShip_Location_ID (SeqNo 330, DisplayLogic: @IsDropShip/N@='Y')
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                       Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                       SeqNo, DisplayLogic, EntityType)
VALUES (1000009, 540894, 1000065, NULL,
        'Drop Shipment Location / Address', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'Y', 'N', 'N',
        330, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate');

-- AD_Field: DropShip_User_ID (SeqNo 340, DisplayLogic: @IsDropShip/N@='Y')
INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                       Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                       SeqNo, DisplayLogic, EntityType)
VALUES (1000010, 540894, 1000066, NULL,
        'Drop Shipment Contact', 'Y', NOW(), 100, NOW(), 100,
        0, 0, 'Y', 'N', 'N',
        340, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate');
