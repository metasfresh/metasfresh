-- Make the EPCIS-Exportstatus diagnostic tab (AD_Tab 549295, table
-- ExternalSystem_ScriptedExportConversion_Status, on the Lieferung / M_InOut window 169)
-- deactivate-capable, mirroring the sibling EDI_EPCIS_Transmitted_SSCC ledger tab.
--
-- WHY: the reverse/reactivate/void guard on a shipment now also blocks while an EPCIS export is
-- IN-FLIGHT (a status row still Enqueued/SendingStarted), not only after it is confirmed+ledgered.
-- In the degenerate case where the external system never sends the /ok (or /error) callback, that
-- in-flight status row would block the shipment forever. Deactivating the stuck status row is the
-- sanctioned escape-hatch: the guard's in-flight lookup considers ACTIVE rows only, so IsActive='N'
-- releases the shipment. This tab is where support does that.
--
-- HOW: set the tab IsReadOnly='N' (a tab-level IsReadOnly='Y' would block EVERY field's edit,
-- including IsActive) and add a single editable IsActive field placed first; all seven existing
-- DATA fields already carry their own AD_Field.IsReadOnly='Y', so the only editable/actionable
-- field on this tab remains deactivate/reactivate a row.
--
-- IDs from server: AD_Field 781730, AD_UI_Element 652656.
-- Reuses the standard shared "Aktiv"/"Active" element (AD_Element 348, AD_Column 592776) — NOT
-- mutated here, only referenced.

-- 1) Tab: allow editing so the IsActive field's own edit is reachable at all.
UPDATE AD_Tab
SET IsReadOnly = 'N',
    Updated = TO_TIMESTAMP('2026-07-14 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549295;

-- 2) IsActive field — the one editable field on this tab (IsReadOnly='N'), placed first.
INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     Created, CreatedBy, Description, DisplayLength, EntityType,
     Help, IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading, IsMandatory, IsReadOnly,
     IsSameLine, Name, SeqNo, SeqNoGrid,
     SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES
    (0, 592776, 781730 /*From ID Server*/, 0, 549295,
     TO_TIMESTAMP('2026-07-14 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Der Eintrag ist im System aktiv', 1, 'de.metas.externalsystem',
     'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.', 'Y', 'Y', 'Y',
     'N', 'N', 'N', 'N', 'N' /*editable — the one action support may take on this tab*/,
     'N', 'Aktiv', 5, 5,
     0, 1, 1,
     TO_TIMESTAMP('2026-07-14 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781730
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781730;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781730);
UPDATE AD_Field_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-14 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781730 AND AD_Language IN ('de_DE','de_CH','en_US');

-- 3) UI element for the IsActive field — grid + form, placed first (SeqNo 5).
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 781730, 0, 549295, 555432, 652656 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-07-14 10:00:12','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'Y', 'N', 'Aktiv', 5, 5, 0, TO_TIMESTAMP('2026-07-14 10:00:12','YYYY-MM-DD HH24:MI:SS'), 100);
