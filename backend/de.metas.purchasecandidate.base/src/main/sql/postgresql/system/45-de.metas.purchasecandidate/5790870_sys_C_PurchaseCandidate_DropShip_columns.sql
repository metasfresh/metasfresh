-- 2026-03-02
-- https://github.com/metasfresh/me03/issues/28440
-- Add dropship columns to C_PurchaseCandidate

-- Physical columns
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS IsDropShip CHAR(1) DEFAULT 'N' NOT NULL CHECK (IsDropShip IN ('Y','N'));
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_BPartner_ID NUMERIC(10);
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_Location_ID NUMERIC(10);
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS DropShip_User_ID NUMERIC(10);

-- Ensure existing rows have the default (belt-and-suspenders for mandatory column)
UPDATE C_PurchaseCandidate SET IsDropShip='N' WHERE IsDropShip IS NULL;

-- AD_Column_IDs from idserver.metas.de: 592119, 592120, 592121, 592122
-- AD_Field_IDs from idserver.metas.de: 774767, 774768, 774769, 774770

-- 1) IsDropShip (AD_Column_ID=592119, AD_Field_ID=774767)
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, FieldLength, Version, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
SELECT 592119, 540861, 2466, 20,
       'IsDropShip', 'Drop Shipment', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'Y', 'Y', 'N',
       'N', 'de.metas.purchasecandidate', 1, 0, 'N', 'N',
       'N', 'N', 'N', 'N', 'Y',
       'NP'
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Column_ID=592119);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592119
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                      SeqNo, EntityType)
SELECT 774767, 540894, 592119, NULL,
       'Drop Shipment', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'Y', 'N', 'N',
       310, 'de.metas.purchasecandidate'
WHERE NOT EXISTS (SELECT 1 FROM AD_Field WHERE AD_Field_ID=774767);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=774767
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

-- 2) DropShip_BPartner_ID (AD_Column_ID=592120, AD_Field_ID=774768)
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       MandatoryLogic,
                       EntityType, FieldLength, Version, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
SELECT 592120, 540861, 53458, 30, 138,
       'DropShip_BPartner_ID', 'Drop Shipment Partner', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'N', 'Y', 'N',
       '@IsDropShip/''N''@=''Y''',
       'de.metas.purchasecandidate', 10, 0, 'N', 'N',
       'N', 'N', 'N', 'N', 'Y',
       'P'
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Column_ID=592120);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592120
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                      SeqNo, DisplayLogic, EntityType)
SELECT 774768, 540894, 592120, NULL,
       'Drop Shipment Partner', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'Y', 'N', 'N',
       320, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate'
WHERE NOT EXISTS (SELECT 1 FROM AD_Field WHERE AD_Field_ID=774768);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=774768
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

-- 3) DropShip_Location_ID (AD_Column_ID=592121, AD_Field_ID=774769)
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       MandatoryLogic,
                       EntityType, FieldLength, Version, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
SELECT 592121, 540861, 53459, 18, 159,
       'DropShip_Location_ID', 'Drop Shipment Location / Address', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'N', 'Y', 'N',
       '@IsDropShip/''N''@=''Y''',
       'de.metas.purchasecandidate', 10, 0, 'N', 'N',
       'N', 'N', 'N', 'N', 'Y',
       'SP'
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Column_ID=592121);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592121
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                      SeqNo, DisplayLogic, EntityType)
SELECT 774769, 540894, 592121, NULL,
       'Drop Shipment Location / Address', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'Y', 'N', 'N',
       330, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate'
WHERE NOT EXISTS (SELECT 1 FROM AD_Field WHERE AD_Field_ID=774769);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=774769
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

-- 4) DropShip_User_ID (AD_Column_ID=592122, AD_Field_ID=774770)
INSERT INTO AD_Column (AD_Column_ID, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       ColumnName, Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Client_ID, AD_Org_ID, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       EntityType, FieldLength, Version, IsKey, IsParent,
                       IsSelectionColumn, IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
SELECT 592122, 540861, 53460, 18, 110,
       'DropShip_User_ID', 'Drop Shipment Contact', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'N', 'Y', 'N',
       'de.metas.purchasecandidate', 10, 0, 'N', 'N',
       'N', 'N', 'N', 'N', 'Y',
       'P'
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_Column_ID=592122);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592122
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

INSERT INTO AD_Field (AD_Field_ID, AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Client_ID, AD_Org_ID, IsDisplayed, IsReadOnly, IsSameLine,
                      SeqNo, DisplayLogic, EntityType)
SELECT 774770, 540894, 592122, NULL,
       'Drop Shipment Contact', 'Y', '2026-03-02 10:00'::timestamp, 100, '2026-03-02 10:00'::timestamp, 100,
       0, 0, 'Y', 'N', 'N',
       340, '@IsDropShip/N@=''Y''', 'de.metas.purchasecandidate'
WHERE NOT EXISTS (SELECT 1 FROM AD_Field WHERE AD_Field_ID=774770);

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=774770
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

-- Propagate translations from AD_Element_Trl
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2466);   -- IsDropShip
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53458);  -- DropShip_BPartner_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53459);  -- DropShip_Location_ID
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53460);  -- DropShip_User_ID
