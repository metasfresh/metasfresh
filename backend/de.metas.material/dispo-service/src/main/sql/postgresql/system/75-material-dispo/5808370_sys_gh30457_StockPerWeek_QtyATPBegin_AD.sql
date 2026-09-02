-- me03 #30457 follow-up – Change B: AD metadata for QtyATPBegin column on MD_Stock_PerWeek_V
-- Adds the new "ATP Beginn" column and relabels/reorders the four ATP/movement grid columns.
--
-- IDs allocated from idserver.metas.de on 2026-06-17:
--   AD_Element  585010  (StockPerWeek_ATPBegin – dedicated label for QtyATPBegin)
--   AD_Column   592828  (MD_Stock_PerWeek_V.QtyATPBegin)
--   AD_Field    781158  (QtyATPBegin field on tab 549289)
--   AD_UI_Element 652304 (UI element for QtyATPBegin)
--
-- Existing dedicated elements mutated (dedicated to MD_Stock_PerWeek_V only):
--   584939  QtyExpectedShipments  → "Erw. Lieferungen" / "Expected Shipments"
--   584940  QtyExpectedReceipts   → "Erw. Wareneingänge" / "Expected Receipts"
--   584945  StockPerWeek_ATP      → "ATP Ende" / "ATP End"  (already AD_Name_ID on QtyATP field)
--
-- Grid order after this script:
--   SeqNoGrid 10 Produkt | 20 Lager | 30 Wochenbeginn | 40 ATP Beginn | 50 Erw. Lieferungen | 60 Erw. Wareneingänge | 70 ATP Ende

-- ===========================================================================
-- 1. Relabel QtyExpectedShipments element (584939) → "Erw. Lieferungen"
--    Dedicated to MD_Stock_PerWeek_V only → safe to mutate AD_Element_Trl.
-- ===========================================================================

UPDATE ad_element
SET    name        = 'Erw. Lieferungen',
       printname   = 'Erw. Lieferungen',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584939;

UPDATE ad_element_trl
SET    name        = 'Erw. Lieferungen',
       printname   = 'Erw. Lieferungen',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584939
  AND  ad_language IN ('de_DE', 'de_CH');

UPDATE ad_element_trl
SET    name        = 'Expected Shipments',
       printname   = 'Expected Shipments',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584939
  AND  ad_language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584939);

-- ===========================================================================
-- 2. Relabel QtyExpectedReceipts element (584940) → "Erw. Wareneingänge"
--    Dedicated to MD_Stock_PerWeek_V only → safe to mutate AD_Element_Trl.
-- ===========================================================================

UPDATE ad_element
SET    name        = 'Erw. Wareneingänge',
       printname   = 'Erw. Wareneingänge',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584940;

UPDATE ad_element_trl
SET    name        = 'Erw. Wareneingänge',
       printname   = 'Erw. Wareneingänge',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584940
  AND  ad_language IN ('de_DE', 'de_CH');

UPDATE ad_element_trl
SET    name        = 'Expected Receipts',
       printname   = 'Expected Receipts',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:05', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584940
  AND  ad_language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584940);

-- ===========================================================================
-- 3. Relabel StockPerWeek_ATP element (584945) → "ATP Ende" / "ATP End"
--    This element is the AD_Name_ID override on the QtyATP field.
--    QtyATP base element (584821) is shared → this dedicated element is the right target.
-- ===========================================================================

-- German description/help for ATP Ende
-- (de_DE has ß variant; de_CH gets ss variant)
UPDATE ad_element
SET    name        = 'ATP Ende',
       printname   = 'ATP Ende',
       description = 'Verfügbare Menge (ATP) am Ende der Woche. Berücksichtigt alle Materialströme.',
       help        = 'Available-to-Promise: die kumulierte projizierte verfügbare Menge am Ende dieser Kalenderwoche. Berücksichtigt alle Ströme: Wareneingänge, Lieferungen, Produktion, Umlagerung, Prognose und Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfließen, muss ATP Ende nicht zwingend gleich (ATP Beginn + Erw. Wareneingänge − Erw. Lieferungen) sein. Quelle: Material-Disposition (MD_Candidate).',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:06', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584945;

UPDATE ad_element_trl
SET    name        = 'ATP Ende',
       printname   = 'ATP Ende',
       description = 'Verfügbare Menge (ATP) am Ende der Woche. Berücksichtigt alle Materialströme.',
       help        = 'Available-to-Promise: die kumulierte projizierte verfügbare Menge am Ende dieser Kalenderwoche. Berücksichtigt alle Ströme: Wareneingänge, Lieferungen, Produktion, Umlagerung, Prognose und Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfließen, muss ATP Ende nicht zwingend gleich (ATP Beginn + Erw. Wareneingänge − Erw. Lieferungen) sein. Quelle: Material-Disposition (MD_Candidate).',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:07', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584945
  AND  ad_language = 'de_DE';

UPDATE ad_element_trl
SET    name        = 'ATP Ende',
       printname   = 'ATP Ende',
       description = 'Verfügbare Menge (ATP) am Ende der Woche. Berücksichtigt alle Materialströme.',
       help        = 'Available-to-Promise: die kumulierte projizierte verfügbare Menge am Ende dieser Kalenderwoche. Berücksichtigt alle Ströme: Wareneingänge, Lieferungen, Produktion, Umlagerung, Prognose und Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfliessen, muss ATP Ende nicht zwingend gleich (ATP Beginn + Erw. Wareneingänge − Erw. Lieferungen) sein. Quelle: Material-Disposition (MD_Candidate).',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:08', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584945
  AND  ad_language = 'de_CH';

UPDATE ad_element_trl
SET    name        = 'ATP End',
       printname   = 'ATP End',
       description = 'Available-to-Promise quantity at the end of the week. Reflects all material streams.',
       help        = 'Available-to-Promise: the cumulative projected available quantity at the end of this calendar week. Reflects all streams: receipts, shipments, production, distribution, forecast and on-hand stock. Because movements such as production and distribution also contribute, ATP End is not necessarily equal to (ATP Begin + Expected Receipts − Expected Shipments). Source: material disposition (MD_Candidate).',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:00:09', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 584945
  AND  ad_language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584945);

-- ===========================================================================
-- 4. Reorder existing three movement/ATP fields to make room for ATP Beginn at 40
--    Current: QtyExpectedShipments=40, QtyExpectedReceipts=50, QtyATP=60
--    Target:  QtyExpectedShipments=50, QtyExpectedReceipts=60, QtyATP=70
-- ===========================================================================

-- AD_Field SeqNo + SeqNoGrid
UPDATE ad_field
SET    seqnogrid = 50,
       seqno     = 50,
       updated   = TO_TIMESTAMP('2026-06-17 10:00:10', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby = 100
WHERE  ad_field_id = 780692;  -- QtyExpectedShipments

UPDATE ad_field
SET    seqnogrid = 60,
       seqno     = 60,
       updated   = TO_TIMESTAMP('2026-06-17 10:00:11', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby = 100
WHERE  ad_field_id = 780693;  -- QtyExpectedReceipts

UPDATE ad_field
SET    seqnogrid = 70,
       seqno     = 70,
       updated   = TO_TIMESTAMP('2026-06-17 10:00:12', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby = 100
WHERE  ad_field_id = 780694;  -- QtyATP

-- AD_UI_Element SeqNo + SeqNoGrid (mirror)
UPDATE ad_ui_element
SET    seqnogrid = 50,
       seqno     = 50,
       updated   = TO_TIMESTAMP('2026-06-17 10:00:13', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby = 100
WHERE  ad_ui_element_id = 651992;  -- QtyExpectedShipments UIE

UPDATE ad_ui_element
SET    seqnogrid = 60,
       seqno     = 60,
       updated   = TO_TIMESTAMP('2026-06-17 10:00:14', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby = 100
WHERE  ad_ui_element_id = 651993;  -- QtyExpectedReceipts UIE

UPDATE ad_ui_element
SET    seqnogrid = 70,
       seqno     = 70,
       updated   = TO_TIMESTAMP('2026-06-17 10:00:15', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby = 100
WHERE  ad_ui_element_id = 651994;  -- QtyATP UIE

-- ===========================================================================
-- 5. New AD_Element for QtyATPBegin (dedicated to this view)
--    Base column: German ("ATP Beginn"); en_US translated separately.
-- ===========================================================================

INSERT INTO ad_element
    (ad_element_id,         ad_client_id, ad_org_id, isactive,
     columnname,            name,         printname,
     description,
     help,
     created,                                                                                          createdby,
     updated,                                                                                          updatedby,
     entitytype)
VALUES
    (585010 /*From ID Server*/, 0,            0,         'Y',
     'QtyATPBegin',         'ATP Beginn', 'ATP Beginn',
     'Verfügbare Menge (ATP) zu Beginn der Woche. Berücksichtigt alle Materialströme.',
     'Available-to-Promise: die projizierte verfügbare Menge zu Beginn dieser Kalenderwoche. Berücksichtigt alle Ströme: Wareneingänge, Lieferungen, Produktion, Umlagerung, Prognose und Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfließen, muss ATP Beginn + Erw. Wareneingänge − Erw. Lieferungen nicht zwingend gleich ATP Ende sein. Quelle: Material-Disposition (MD_Candidate).',
     TO_TIMESTAMP('2026-06-17 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-17 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.material.dispo');

-- Seed _Trl rows for all active system languages
INSERT INTO ad_element_trl
    (ad_language, ad_element_id, name, printname, description, help,
     po_name, po_printname, po_description, po_help,
     istranslated, ad_client_id, ad_org_id, isactive,
     created, createdby, updated, updatedby)
SELECT l.ad_language, t.ad_element_id, t.name, t.printname, t.description, t.help,
       '', '', '', '',
       'N', t.ad_client_id, t.ad_org_id, 'Y',
       TO_TIMESTAMP('2026-06-17 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-17 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   ad_language l, ad_element t
WHERE  l.isactive = 'Y' AND l.issystemlanguage = 'Y'
  AND  t.ad_element_id = 585010
  AND  NOT EXISTS (
           SELECT 1 FROM ad_element_trl tt
           WHERE  tt.ad_language = l.ad_language
             AND  tt.ad_element_id = t.ad_element_id);

-- de_DE translation (with ß)
UPDATE ad_element_trl
SET    name        = 'ATP Beginn',
       printname   = 'ATP Beginn',
       description = 'Verfügbare Menge (ATP) zu Beginn der Woche. Berücksichtigt alle Materialströme.',
       help        = 'Available-to-Promise: die projizierte verfügbare Menge zu Beginn dieser Kalenderwoche. Berücksichtigt alle Ströme: Wareneingänge, Lieferungen, Produktion, Umlagerung, Prognose und Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfließen, muss ATP Beginn + Erw. Wareneingänge − Erw. Lieferungen nicht zwingend gleich ATP Ende sein. Quelle: Material-Disposition (MD_Candidate).',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:01:12', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 585010 AND ad_language = 'de_DE';

-- de_CH translation (ss instead of ß)
UPDATE ad_element_trl
SET    name        = 'ATP Beginn',
       printname   = 'ATP Beginn',
       description = 'Verfügbare Menge (ATP) zu Beginn der Woche. Berücksichtigt alle Materialströme.',
       help        = 'Available-to-Promise: die projizierte verfügbare Menge zu Beginn dieser Kalenderwoche. Berücksichtigt alle Ströme: Wareneingänge, Lieferungen, Produktion, Umlagerung, Prognose und Bestand. Da auch Vorgänge wie Produktion und Umlagerung einfliessen, muss ATP Beginn + Erw. Wareneingänge − Erw. Lieferungen nicht zwingend gleich ATP Ende sein. Quelle: Material-Disposition (MD_Candidate).',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:01:18', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 585010 AND ad_language = 'de_CH';

-- en_US translation
UPDATE ad_element_trl
SET    name        = 'ATP Begin',
       printname   = 'ATP Begin',
       description = 'Available-to-Promise quantity at the beginning of the week. Reflects all material streams.',
       help        = 'Available-to-Promise: the projected available quantity at the beginning of this calendar week. Reflects all streams: receipts, shipments, production, distribution, forecast and on-hand stock. Because movements such as production and distribution also contribute, ATP Begin + Expected Receipts − Expected Shipments is not necessarily equal to ATP End. Source: material disposition (MD_Candidate).',
       istranslated = 'Y',
       updated     = TO_TIMESTAMP('2026-06-17 10:01:24', 'YYYY-MM-DD HH24:MI:SS'),
       updatedby   = 100
WHERE  ad_element_id = 585010 AND ad_language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585010);

-- ===========================================================================
-- 6. New AD_Column for QtyATPBegin on MD_Stock_PerWeek_V (AD_Table_ID 542612)
--    Mirrors QtyATP (AD_Column_ID 592711): AD_Reference_ID=29 (Quantity/Amount), not mandatory.
-- ===========================================================================

INSERT INTO ad_column
    (ad_column_id,          ad_client_id, ad_org_id, isactive,
     columnname,            name,
     ad_table_id,           ad_element_id,
     ad_reference_id,
     fieldlength,           iskey,        isparent,     ismandatory,
     isupdateable,          isidentifier, isencrypted,  issyncdatabase,
     isalwaysupdateable,    isallowlogging,
     version,
     personaldatacategory,
     entitytype,
     created,                                                                                       createdby,
     updated,                                                                                       updatedby)
VALUES
    (592828 /*From ID Server*/, 0,            0,         'Y',
     'QtyATPBegin',          'ATP Beginn',
     542612,                 585010 /*From ID Server*/,
     29,                     -- Quantity/Amount (same as QtyATP col 592711)
     26,                     'N',          'N',          'N',
     'N',                    'N',          'N',          'N',
     'N',                    'Y',
     0,
     'NP',
     'de.metas.material.dispo',
     TO_TIMESTAMP('2026-06-17 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-17 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Seed AD_Column_Trl rows
INSERT INTO ad_column_trl
    (ad_language, ad_column_id, name, istranslated, ad_client_id, ad_org_id, isactive,
     created, createdby, updated, updatedby)
SELECT l.ad_language, t.ad_column_id, t.name, 'N', t.ad_client_id, t.ad_org_id, 'Y',
       TO_TIMESTAMP('2026-06-17 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-17 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   ad_language l, ad_column t
WHERE  l.isactive = 'Y' AND l.issystemlanguage = 'Y'
  AND  t.ad_column_id = 592828
  AND  NOT EXISTS (
           SELECT 1 FROM ad_column_trl tt
           WHERE  tt.ad_language = l.ad_language
             AND  tt.ad_column_id = t.ad_column_id);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585010);

-- ===========================================================================
-- 7. New AD_Field for QtyATPBegin on tab 549289
--    AD_Name_ID = 585010 (dedicated element) — same pattern as QtyATP field
--    SeqNoGrid = 40, SeqNo = 40 (first of the four ATP/movement columns)
--    IsDisplayed = Y
-- ===========================================================================

INSERT INTO ad_field
    (ad_field_id,           ad_client_id, ad_org_id, isactive,
     name,
     ad_tab_id,             ad_column_id,
     ad_name_id,
     isdisplayed,           isreadonly,   ismandatory,  issameline,
     isheading,             isencrypted,  displaylogic,
     seqno,                 seqnogrid,
     sortno,
     entitytype,
     created,                                                                                       createdby,
     updated,                                                                                       updatedby)
VALUES
    (781158 /*From ID Server*/, 0,            0,         'Y',
     'ATP Beginn',
     549289,                592828 /*From ID Server*/,
     585010 /*From ID Server*/,
     'Y',                   'N',          'N',          'N',
     'N',                   'N',          NULL,
     40,                    40,
     0,
     'de.metas.material.dispo',
     TO_TIMESTAMP('2026-06-17 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-17 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Seed AD_Field_Trl rows
INSERT INTO ad_field_trl
    (ad_language, ad_field_id, name, description, help, istranslated,
     ad_client_id, ad_org_id, isactive,
     created, createdby, updated, updatedby)
SELECT l.ad_language, t.ad_field_id, t.name, t.description, t.help, 'N',
       t.ad_client_id, t.ad_org_id, 'Y',
       TO_TIMESTAMP('2026-06-17 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-17 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   ad_language l, ad_field t
WHERE  l.isactive = 'Y' AND l.issystemlanguage = 'Y'
  AND  t.ad_field_id = 781158
  AND  NOT EXISTS (
           SELECT 1 FROM ad_field_trl tt
           WHERE  tt.ad_language = l.ad_language
             AND  tt.ad_field_id = t.ad_field_id);

-- Propagate element translations → field (pass AD_Name_ID element, which is 585010)
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585010);

-- Rebuild element links
DELETE FROM ad_element_link WHERE ad_field_id = 781158;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781158);

-- ===========================================================================
-- 8. New AD_UI_Element for QtyATPBegin (paired with the AD_Field)
--    Same AD_UI_ElementGroup as all other fields: 555423
--    SeqNoGrid = 40, SeqNo = 40
--    IsDisplayedGrid = Y
-- ===========================================================================

INSERT INTO ad_ui_element
    (ad_ui_element_id,      ad_client_id, ad_org_id, isactive,
     name,
     ad_tab_id,             ad_field_id,
     ad_ui_elementgroup_id,
     ad_ui_elementtype,
     seqno,                 seqnogrid,
     isdisplayed,           isdisplayedgrid,
     isadvancedfield,
     created,                                                                                            createdby,
     updated,                                                                                            updatedby)
VALUES
    (652304 /*From ID Server*/, 0,            0,         'Y',
     'ATP Beginn',
     549289,                781158 /*From ID Server*/,
     555423,
     'F',
     40,                    40,
     'Y',                   'Y',
     'N',
     TO_TIMESTAMP('2026-06-17 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-17 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
