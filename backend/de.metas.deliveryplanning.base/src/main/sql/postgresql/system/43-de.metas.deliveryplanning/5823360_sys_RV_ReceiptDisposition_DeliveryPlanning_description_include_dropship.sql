-- Corrects AD_Table 542644 (RV_ReceiptDisposition_DeliveryPlanning) Name/Description, which 5822740 set to
-- "Streckengeschäft ausgeschlossen" / "dropship rows are excluded". Migration 5823350 widened the view's
-- planned branch to IN ('Incoming', 'Dropship') on the owner's 2026-09-09 ruling, so that sentence is now false
-- about the live row set; Outgoing is the direction the window excludes, and the reason is that an outgoing
-- planning carries a shipment schedule rather than a receipt schedule.
--
-- 5822740 itself is immutable (integrated, already applied), hence this follow-up rather than an edit there.
-- Same mechanism as 5822740: AD_Table has no AD_Element_ID - like AD_Process it is self-owned, so Description
-- is set directly on AD_Table plus every AD_Table_Trl row, never propagated from an element. Name is not
-- changed. Base language is German (AD_Language.IsBaseLanguage='Y' for de_DE), so the base column and de_DE /
-- de_CH carry German and en_US is the English override; the German terms come from the TransportDirection
-- ref-list (AD_Reference 541689: Eingehend / Streckengeschäft / Ausgehend), not from feel.
--
-- fr_CH IS updated here, unlike in 5822740. It holds a verbatim copy of the en_US text (verified on the
-- deep_tundra_release DB), so leaving it would leave the false dropship-exclusion claim live in one language
-- while the other four are corrected; it keeps mirroring en_US, which is the state it was already in.
--
-- AD_Table.Description is varchar(255) (a longer German text aborts the apply), so the wording is kept
-- inside that ceiling. Structural AD metadata, so no backup_table.

UPDATE AD_Table SET
    Description='Eine aktive eingehende Lieferplanung oder eine im Streckengeschäft, beide mit Wareneingangsdisposition, oder eine Wareneingangsdisposition ohne aktive Planung; ausgehende sind ausgeschlossen, da sie keine Wareneingangsdisposition tragen.',
    Updated=TO_TIMESTAMP('2026-09-09 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644
;
UPDATE AD_Table_Trl SET
    Description='Eine aktive eingehende Lieferplanung oder eine im Streckengeschäft, beide mit Wareneingangsdisposition, oder eine Wareneingangsdisposition ohne aktive Planung; ausgehende sind ausgeschlossen, da sie keine Wareneingangsdisposition tragen.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644 AND AD_Language='de_DE'
;
UPDATE AD_Table_Trl SET
    Description='Eine aktive eingehende Lieferplanung oder eine im Streckengeschäft, beide mit Wareneingangsdisposition, oder eine Wareneingangsdisposition ohne aktive Planung; ausgehende sind ausgeschlossen, da sie keine Wareneingangsdisposition tragen.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644 AND AD_Language='de_CH'
;
UPDATE AD_Table_Trl SET
    Description='Either an active Incoming or Dropship delivery planning carrying a receipt schedule, or a receipt schedule no active planning refers to; Outgoing plannings are excluded because they carry no receipt schedule.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644 AND AD_Language='en_US'
;
UPDATE AD_Table_Trl SET
    Description='Either an active Incoming or Dropship delivery planning carrying a receipt schedule, or a receipt schedule no active planning refers to; Outgoing plannings are excluded because they carry no receipt schedule.',
    Updated=TO_TIMESTAMP('2026-09-09 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Table_ID=542644 AND AD_Language='fr_CH'
;
