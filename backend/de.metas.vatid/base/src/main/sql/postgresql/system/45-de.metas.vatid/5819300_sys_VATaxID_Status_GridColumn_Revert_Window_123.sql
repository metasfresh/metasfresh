-- VAT-ID online check: take VATaxIDStatus back OUT of the grid on the core Business Partner window 123.
-- Supersedes 5819230, which put it in. 5819230 is already applied, so it is not edited -- migration
-- immutability is keyed on ProjectName + Name with no content hash, so editing an applied script is a
-- permanent no-op on every database that already ran it.
--
-- WHY. The status MIRRORS the VAT-ID: if VATaxID is not a grid column on a tab, VATaxIDStatus must not be
-- one either -- on the C_BPartner tab and on the C_BPartner_Location tabs alike. VATaxID is
-- IsDisplayedGrid='N' on every tab of every Business Partner window, so the status has to be 'N' there
-- too. A status column next to no VAT-ID column shows a verdict without the value it judges.
--
-- SCOPE. Business Partner windows only. AD_UI_Element 652841 on window 542183 (USt-IdNr.-Prüfprotokoll,
-- tab 549365) is deliberately NOT touched: that is the check log's own window, where the status is the
-- record's primary fact and belongs in the grid.
--
-- Nothing else about these elements changes -- they stay active and stay on the single-record form; only
-- their grid participation is withdrawn. SeqNoGrid is cleared to 0 so no stale ordering is left behind
-- for a future re-add to trip over.

-- window 123, tab 220 (Geschäftspartner) -- the partner header
UPDATE AD_UI_Element
SET IsDisplayedGrid = 'N', SeqNoGrid = 0,
    Updated = TO_TIMESTAMP('2026-08-15 16:30:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652912;

-- window 123, tab 222 (Adresse) -- the partner location
UPDATE AD_UI_Element
SET IsDisplayedGrid = 'N', SeqNoGrid = 0,
    Updated = TO_TIMESTAMP('2026-08-15 16:30:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652915;
