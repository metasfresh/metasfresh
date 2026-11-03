-- Text lines in sales documents: generic carrier table for free-text lines interleaved with a
-- sales document's article lines. Exactly one document FK is set per row (enforced by the check
-- constraint below) -- explicit nullable FKs rather than a polymorphic AD_Table_ID+Record_ID
-- reference, so each report SQL function can join with a plain equijoin.
-- Line is numeric(10,4) -- the SAME numeric space as C_OrderLine.Line (numeric(10,0)) -- so a
-- text line's decimal position (e.g. 10.5) sits between two integer article positions with no
-- renumbering. TextLine is varchar(2048), matching C_Order.DescriptionBottom (the closest
-- existing free-text field). TextLineScope ('F'=belongs with the following lines, 'D'=whole
-- document) is a two-value ref-list, computed once at insert by a later task's process.

-- IDs allocated from idserver.metas.de:
--   AD_Table                542648
--   AD_Element              585461 (C_Doc_TextLine_ID), 585462 (TextLine), 585464 (TextLineScope)
--                           -- Line reuses the existing shared element 439 (ColumnName='Line',
--                           -- also used by C_OrderLine.Line) -- AD_Element.ColumnName is globally
--                           -- unique, and a fresh 'Line' element collides with it.
--   AD_Column                593556..593568 (13: 8 standard + 5 business, in column order)
--   AD_Reference             542144 (C_Doc_TextLine_Scope list)
--   AD_Ref_List              544367 (F), 544368 (D)

-- 1. Physical table
CREATE TABLE C_Doc_TextLine
(
    C_Doc_TextLine_ID  NUMERIC(10)   NOT NULL,
    AD_Client_ID        NUMERIC(10)  NOT NULL,
    AD_Org_ID            NUMERIC(10) NOT NULL,
    IsActive             CHAR(1)     NOT NULL DEFAULT 'Y',
    Created              TIMESTAMP   NOT NULL,
    CreatedBy            NUMERIC(10) NOT NULL,
    Updated              TIMESTAMP   NOT NULL,
    UpdatedBy            NUMERIC(10) NOT NULL,
    C_Order_ID           NUMERIC(10),
    M_InOut_ID            NUMERIC(10),
    TextLine             VARCHAR(2048),
    Line                 NUMERIC(10, 4) NOT NULL,
    TextLineScope        CHAR(1)     NOT NULL,
    CONSTRAINT C_Doc_TextLine_key PRIMARY KEY (C_Doc_TextLine_ID),
    CONSTRAINT C_Doc_TextLine_C_Order_ID_FK FOREIGN KEY (C_Order_ID)
        REFERENCES C_Order (C_Order_ID),
    CONSTRAINT C_Doc_TextLine_M_InOut_ID_FK FOREIGN KEY (M_InOut_ID)
        REFERENCES M_InOut (M_InOut_ID),
    CONSTRAINT C_Doc_TextLine_IsActive_check CHECK (IsActive IN ('Y', 'N')),
    CONSTRAINT C_Doc_TextLine_TextLineScope_Check CHECK (TextLineScope IN ('F', 'D')),
    CONSTRAINT C_Doc_TextLine_OneDocFK_Check CHECK (num_nonnulls(C_Order_ID, M_InOut_ID) = 1)
);

COMMENT ON TABLE C_Doc_TextLine IS 'Free-text lines interleaved with a sales document''s article lines (order, delivery note). Exactly one of C_Order_ID/M_InOut_ID is set per row.';

CREATE INDEX C_Doc_TextLine_C_Order_ID_idx ON C_Doc_TextLine (C_Order_ID);
CREATE INDEX C_Doc_TextLine_M_InOut_ID_idx ON C_Doc_TextLine (M_InOut_ID);

-- 2. AD_Table (cloned from X_TableTemplate 540290)
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, TableName, AccessLevel, IsView, IsSecurityEnabled, IsChangeLog, IsDeleteable,
                       IsHighVolume, LoadSeq, EntityType, ImportTable)
VALUES (542648 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-09-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-09-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_Doc_TextLine', 'C_Doc_TextLine', '1', 'N', 'N', 'Y', 'Y',
        'N', 0, 'D', 'N');

-- 3. AD_Element for the table's own ID column
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES (585461 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-09-15 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-09-15 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_Doc_TextLine_ID', 'Freitextzeile', 'Freitextzeile',
        'Freitextzeile in einem Verkaufsdokument.', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585461
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Name = 'Text Line', PrintName = 'Text Line',
    Description = 'Free-text line in a sales document.',
    Updated = TO_TIMESTAMP('2026-09-15 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585461;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-15 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585461;

-- 4. AD_Elements for the new business columns (C_Order_ID/M_InOut_ID reuse the standard shared
--    elements 558/1025; Line reuses the existing shared element 439 -- no new element for those
--    three).
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES
    (585462 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-09-15 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'TextLine', 'Text', 'Text',
     'Der Freitext dieser Zeile; kann leer sein und wird dann als Leerzeile gedruckt.', 'D'),
    (585464 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-09-15 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'TextLineScope', 'Geltungsbereich', 'Geltungsbereich',
     'Ob sich der Text auf die folgenden Zeilen oder auf das gesamte Dokument bezieht.', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID IN (585462, 585464)
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Text', PrintName = 'Text',
    Description = 'The free text of this line; may be empty, which prints as a blank line.',
    Updated = TO_TIMESTAMP('2026-09-15 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585462;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Scope', PrintName = 'Scope',
    Description = 'Whether the text applies to the following lines or to the whole document.',
    Updated = TO_TIMESTAMP('2026-09-15 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585464;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-15 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID IN (585462, 585464);

-- 5. AD_Reference + AD_Ref_List for TextLineScope ('F'=Following, 'D'=Document)
INSERT INTO AD_Reference (AD_Client_ID, IsActive, Created, CreatedBy, IsOrderByValue,
                           Updated, UpdatedBy, AD_Reference_ID, ValidationType, Name, AD_Org_ID, EntityType)
VALUES (0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N',
        TO_TIMESTAMP('2026-09-15 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
        542144 /*From ID Server*/, 'L', 'C_Doc_TextLine_Scope', 0, 'D');

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description,
                               IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Reference_ID = 542144
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID);

UPDATE AD_Reference_Trl SET IsTranslated = 'Y', Name = 'Text Line Scope',
    Updated = TO_TIMESTAMP('2026-09-15 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Reference_ID = 542144;

UPDATE AD_Reference_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-15 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Reference_ID = 542144;

INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
                          Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES
    (542144, 0, 'Y',
     TO_TIMESTAMP('2026-09-15 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Folgezeilen',
     TO_TIMESTAMP('2026-09-15 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     544367 /*From ID Server*/, 'Following', 'F', 0,
     'Der Text bezieht sich auf die folgenden Zeilen.', 'D'),
    (542144, 0, 'Y',
     TO_TIMESTAMP('2026-09-15 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Dokument',
     TO_TIMESTAMP('2026-09-15 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     544368 /*From ID Server*/, 'Document', 'D', 0,
     'Der Text bezieht sich auf das gesamte Dokument.', 'D');

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
                              IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Ref_List_ID IN (544367, 544368)
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID);

UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y', Name = 'Following Lines', Description = 'The text applies to the following lines.',
    Updated = TO_TIMESTAMP('2026-09-15 10:00:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Ref_List_ID = 544367;

UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y', Name = 'Document', Description = 'The text applies to the whole document.',
    Updated = TO_TIMESTAMP('2026-09-15 10:00:36', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Ref_List_ID = 544368;

UPDATE AD_Ref_List_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-15 10:00:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Ref_List_ID IN (544367, 544368);

-- 6. AD_Column -- standard columns (cloned from X_TableTemplate 540290, reusing the shared standard elements)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        EntityType, PersonalDataCategory, Version)
VALUES
    (593556 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 585461, 'C_Doc_TextLine_ID', 13, 10,
     'Y', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593557 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 102, 'AD_Client_ID', 19, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593558 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 113, 'AD_Org_ID', 30, 10,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593559 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 348, 'IsActive', 20, 1,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593560 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 245, 'Created', 16, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593561 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 246, 'CreatedBy', 18, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593562 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 607, 'Updated', 16, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593563 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 608, 'UpdatedBy', 18, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0);

-- 7. AD_Column -- business columns
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        EntityType, PersonalDataCategory, Version)
VALUES
    (593564 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 558, 'C_Order_ID', 19, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593565 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 1025, 'M_InOut_ID', 19, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593566 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 585462, 'TextLine', 14, NULL, 2048,
     'N', 'N', 'N', 'Y', 'N', 'Y', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593567 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 439, 'Line', 22, NULL, 22,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593568 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-15 10:00:54', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-09-15 10:00:54', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542648, 585464, 'TextLineScope', 17, 542144, 1,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0);

-- 8. AD_Column_Trl skeleton rows for all 13 columns
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Table_ID = 542648
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 9. Propagate element translations to the newly-created columns
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(x.AD_Element_ID)
FROM (VALUES (585461), (585462), (585464), (558), (1025), (439)) AS x(AD_Element_ID);
