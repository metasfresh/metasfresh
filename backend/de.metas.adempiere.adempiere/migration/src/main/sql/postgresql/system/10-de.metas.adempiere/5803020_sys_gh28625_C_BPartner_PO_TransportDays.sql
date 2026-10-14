-- PO Date Provisioning: add C_BPartner.PO_TransportDays (vendor-level default transport days)
--
-- AD_Element_ID : 584888  /*From ID Server*/
-- AD_Column_ID  : 592551  /*From ID Server*/
-- AD_Field_ID   : 780245  /*From ID Server*/
-- AD_UI_Element_ID : 651686  /*From ID Server*/
-- Vendor tab (Lieferant): AD_Tab_ID=224, AD_UI_ElementGroup_ID=1000033

-- =============================================================================
-- 1. AD_Element
--    Timestamps: element at 14:00:00, column at 14:01:00, field/UI at 14:02:xx.
--    Element_Trl UPDATEs below use 14:03:xx — deliberately later than all
--    downstream skeleton-insert timestamps so update_TRL_Tables fires correctly.
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help)
VALUES (584888 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-18 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-18 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'PO_TransportDays',
        'Einkauf Transporttage',
        'Einkauf Transporttage',
        'Lieferzeit des Lieferanten in Tagen', NULL);

-- Skeleton Trl rows for AD_Element
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
       t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584888
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation (en_US) — timestamp MUST be later than all downstream skeleton timestamps
UPDATE AD_Element_Trl
SET Name         = 'Purchase Transport Days',
    PrintName    = 'Purchase Transport Days',
    Description  = 'Vendor transport time in days',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-18 14:03:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584888 AND AD_Language = 'en_US';

-- German (de_DE — base language)
UPDATE AD_Element_Trl
SET Name         = 'Einkauf Transporttage',
    PrintName    = 'Einkauf Transporttage',
    Description  = 'Lieferzeit des Lieferanten in Tagen',
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-05-18 14:03:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584888 AND AD_Language = 'de_DE';

-- German (de_CH — same as de_DE)
UPDATE AD_Element_Trl
SET Name         = 'Einkauf Transporttage',
    PrintName    = 'Einkauf Transporttage',
    Description  = 'Lieferzeit des Lieferanten in Tagen',
    IsTranslated = 'N',
    Updated      = TO_TIMESTAMP('2026-05-18 14:03:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584888 AND AD_Language = 'de_CH';

-- =============================================================================
-- 2. AD_Column on C_BPartner (AD_Table_ID=291)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592551 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-18 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-18 14:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 291, 584888, 11,
        'PO_TransportDays',
        'Einkauf Transporttage',
        NULL, NULL,
        10, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows for AD_Column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592551
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 3. Physical DDL — new column (t_alter_column only works on existing columns)
-- =============================================================================
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS PO_TransportDays INTEGER;

-- =============================================================================
-- 4. AD_Field on vendor tab (AD_Tab_ID=224, Geschäftspartner window 123)
--    SeqNo=0: intentional — other PO fields on this tab (e.g. PO_InvoiceRule) also use SeqNo=0,
--    meaning the Swing form-view does not render them; grid-view is controlled by SeqNoGrid=200.
--    WebUI rendering is governed by AD_UI_Element below (step 5).
-- =============================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
                      Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine,
                      SeqNo, SeqNoGrid, SortNo, EntityType)
VALUES (780245 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-18 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-18 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        224, 592551, NULL,
        'Einkauf Transporttage', NULL,
        'Y', 'Y', 'N', 'N',
        0, 200, 0, 'D');

-- Skeleton Trl rows for AD_Field
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help,
                          IsTranslated, AD_Client_ID, AD_Org_ID,
                          Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Field_ID = 780245
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Element link (enables zoom/reference navigation from this field)
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 780245;
SELECT AD_Element_Link_Create_Missing_Field(780245);

-- =============================================================================
-- 5. AD_UI_Element (WebUI layout — AD_Field alone is not rendered in WebUI)
--    Group: 1000033 (default PO group on vendor tab)
--    SeqNo=150 (after PO_InvoiceRule at 148), SeqNoGrid=130 (after current max of 120)
-- =============================================================================
INSERT INTO AD_UI_Element (
    AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
    AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
    Created, CreatedBy, IsActive, IsAdvancedField,
    IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
    Name, SeqNo, SeqNoGrid, SeqNo_SideList,
    Updated, UpdatedBy
) VALUES (
    0, 780245, 0, 224,
    1000033, 651686 /*From ID Server*/, 'F',
    TO_TIMESTAMP('2026-05-18 14:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'Y',
    'Y', 'Y', 'N',
    'Einkauf Transporttage', 150, 130, 0,
    TO_TIMESTAMP('2026-05-18 14:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
);

-- =============================================================================
-- 6. Propagate translations from AD_Element_Trl to AD_Column_Trl / AD_Field_Trl
--    AD_Element_Trl UPDATEs above use timestamps 14:03:xx, which are later than
--    all skeleton-insert timestamps (≤14:02:01), so the guard fires correctly.
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584888);
