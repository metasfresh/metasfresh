-- IDs allocated from idserver.metas.de on 2026-09-02:
--   AD_Element 585414 (label for the m_hu_trace_report(numeric) link_basis column)

-- AD_Element for the Excel column header of m_hu_trace_report(numeric).link_basis, which tells
-- the reader whether a receipt-to-shipment pairing was proven from the HU trace graph or is a
-- lot- or product-level guess. Resolved directly by ColumnName, not attached to any AD_Column,
-- AD_Field, or window.
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType,
    Name, PrintName, Description, Help
) VALUES (
    585414 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-02 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-02 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Link_Basis', 'de.metas.handlingunits',
    'Verknüpfung', 'Verknüpfung',
    'Gibt an, ob die Zuordnung von Wareneingang zu Warenausgang im Rückverfolgungsbericht nachgewiesen oder nur aus Los- bzw. Produktangaben geschätzt wurde.', NULL
);

-- Seed skeleton AD_Element_Trl rows for every active system language, copying the base (German) text.
INSERT INTO AD_Element_Trl (
    AD_Language, AD_Element_ID,
    CommitWarning, Description, Help, Name, PO_Description, PO_Help,
    PO_Name, PO_PrintName, PrintName,
    WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
    IsTranslated, AD_Client_ID, AD_Org_ID,
    Created, CreatedBy, Updated, UpdatedBy
)
SELECT l.AD_Language, t.AD_Element_ID,
       t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help,
       t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 585414
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- de_DE / de_CH already carry the correct German text from the seed above; just mark them translated.
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-02 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585414 AND AD_Language IN ('de_DE', 'de_CH');

-- en_US gets the English override.
UPDATE AD_Element_Trl
SET Name = 'Link basis', PrintName = 'Link basis',
    Description = 'States whether the receipt-to-shipment pairing in the traceability report was proven from the trace graph or is only a lot- or product-level estimate.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-09-02 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585414 AND AD_Language = 'en_US';

-- No AD_Column/AD_Field/AD_Window/AD_Tab/AD_Menu references this element (it is looked up
-- directly by ColumnName as an Excel header label), so no further propagation call is needed.
