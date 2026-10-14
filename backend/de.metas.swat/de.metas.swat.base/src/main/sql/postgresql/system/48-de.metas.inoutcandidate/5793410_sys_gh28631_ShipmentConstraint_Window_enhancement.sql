-- gh#28631: Enhance Shipment Restrictions window (AD_Window_ID=540370, AD_Tab_ID=540882)
-- - Add fields: IsManual, DeliveryStopReason, Updated, UpdatedBy
-- - Hide unused fields: SourceDoc_Table_ID, SourceDoc_Record_ID, C_Invoice_ID, IsPaid

-- ==========================================================================
-- 1. New AD_Field: IsManual
-- ==========================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsReadOnly, IsSameLine, IsEncrypted,
                      EntityType)
VALUES (774884 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 592210, NULL,
        'Manuell',
        'Kennzeichnet Sperren, die durch das Setzen von ''Liefer-/Auftragssperre'' am Geschäftspartner entstanden sind (im Unterschied zu vom System per Mahnung erzeugten Einträgen). Wird automatisch gepflegt; nicht manuell editieren.',
        'Y', 'Y', 'N', 'N',
        'de.metas.inoutcandidate');

-- ==========================================================================
-- 2. New AD_Field: DeliveryStopReason
-- ==========================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name, Description,
                      IsDisplayed, IsReadOnly, IsSameLine, IsEncrypted,
                      EntityType, ReadOnlyLogic)
VALUES (774885 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 592211, NULL,
        'Lieferstopp Grund',
        'Begründung der Liefer-/Auftragssperre. Bei manuellen Sperren wird der am Geschäftspartner eingegebene Text gespiegelt; bei vom System (z.B. Mahnung) erzeugten Sperren wird der Hinweis programmatisch befüllt (z.B. "Dunning: Mahnstufe 3").',
        'Y', 'N', 'N', 'N',
        'de.metas.inoutcandidate', '@IsManual/Y@=N');

-- ==========================================================================
-- 3. New AD_Field: Updated (AD_Column_ID=557307, AD_Element_ID=607)
-- ==========================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name,
                      IsDisplayed, IsReadOnly, IsSameLine, IsEncrypted,
                      EntityType)
VALUES (774886 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 557307, NULL,
        'Aktualisiert',
        'Y', 'Y', 'N', 'N',
        'de.metas.inoutcandidate');

-- ==========================================================================
-- 4. New AD_Field: UpdatedBy (AD_Column_ID=557308, AD_Element_ID=608)
-- ==========================================================================
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, AD_Name_ID,
                      Name,
                      IsDisplayed, IsReadOnly, IsSameLine, IsEncrypted,
                      EntityType)
VALUES (774887 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 557308, NULL,
        'Aktualisiert durch',
        'Y', 'Y', 'N', 'N',
        'de.metas.inoutcandidate');

-- ==========================================================================
-- 5. Hide unused UI elements: SourceDoc_Table_ID, SourceDoc_Record_ID, C_Invoice_ID, IsPaid
-- ==========================================================================
-- Deactivate SourceDoc_Table_ID (AD_UI_Element_ID=549078)
UPDATE AD_UI_Element SET IsActive = 'N', Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 549078;

-- Deactivate SourceDoc_Record_ID (AD_UI_Element_ID=549079)
UPDATE AD_UI_Element SET IsActive = 'N', Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 549079;

-- Deactivate C_Invoice_ID (AD_UI_Element_ID=551170)
UPDATE AD_UI_Element SET IsActive = 'N', Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 551170;

-- Deactivate IsPaid (AD_UI_Element_ID=551171)
UPDATE AD_UI_Element SET IsActive = 'N', Updated = TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 0
WHERE AD_UI_Element_ID = 551171;

-- ==========================================================================
-- 6. New UI Elements for the new/shown fields
-- ==========================================================================
-- IsManual in "flags" group (AD_UI_ElementGroup_ID=541208, col 2)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField)
VALUES (648532 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 541208, 774884, 'Manuell', 25, 'N');

-- DeliveryStopReason in "default" group (AD_UI_ElementGroup_ID=541207, col 1)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField)
VALUES (648533 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 541207, 774885, 'Lieferstopp Grund', 20, 'N');

-- Updated in "data" group (AD_UI_ElementGroup_ID=541210, col 1)
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField)
VALUES (648534 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 541210, 774886, 'Aktualisiert', 10, 'N');

-- UpdatedBy in "data" group
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, Name, SeqNo, IsAdvancedField)
VALUES (648535 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 01:00', 'YYYY-MM-DD HH24:MI'), 0,
        540882, 541210, 774887, 'Aktualisiert durch', 20, 'N');
