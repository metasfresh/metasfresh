-- Boni refund engine: make the packaging options a DIRECT (level 1) tab of window 540113 (the WebUI supports only one
-- level of child tabs, so the level-2 tab 549512 created by 5827820 could not render).
--
--   C_Flatrate_RefundConfig_PackingOption gets C_Flatrate_Conditions_ID (mandatory; the link column to the header tab).
--   C_Flatrate_RefundConfig_ID stays mandatory but is no longer the parent link; it is picked by the user, restricted by a
--   validation rule to the refund configs of the same conditions.
--   Tab 549512 becomes TabLevel 1 under the header tab 540331 (AD_Column_ID = the link column, like its sibling tabs),
--   without display logic: the rows only take effect for refund configs with IsPackingOptionFiltered='Y'.
--
-- IDs allocated from idserver.metas.de on 2026-10-05:
--   AD_Column 593710, AD_Field 785610, AD_UI_Element 654937, AD_Val_Rule 540804
--
-- 1) DDL: new link column, backfilled from the parent config, then mandatory
SELECT public.db_alter_table('C_Flatrate_RefundConfig_PackingOption', 'ALTER TABLE public.C_Flatrate_RefundConfig_PackingOption ADD COLUMN C_Flatrate_Conditions_ID NUMERIC(10)')
;
UPDATE C_Flatrate_RefundConfig_PackingOption po
SET C_Flatrate_Conditions_ID = rc.C_Flatrate_Conditions_ID
FROM C_Flatrate_RefundConfig rc
WHERE rc.C_Flatrate_RefundConfig_ID = po.C_Flatrate_RefundConfig_ID
  AND po.C_Flatrate_Conditions_ID IS NULL
;
SELECT public.db_alter_table('C_Flatrate_RefundConfig_PackingOption', 'ALTER TABLE public.C_Flatrate_RefundConfig_PackingOption ALTER COLUMN C_Flatrate_Conditions_ID SET NOT NULL')
;
ALTER TABLE C_Flatrate_RefundConfig_PackingOption
    ADD CONSTRAINT CFlatrateConditions_CFlatrateRefundConfigPackingOption FOREIGN KEY (C_Flatrate_Conditions_ID) REFERENCES public.C_Flatrate_Conditions DEFERRABLE INITIALLY DEFERRED
;
-- 2) AD_Column: new link column (element reused), the old parent column becomes a normal field
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, Help,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
SELECT 593710 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       0, 'de.metas.contracts', e.ColumnName, 542654, e.AD_Element_ID, 19,
       10, e.Name, e.Description, e.Help,
       'Y', 'N', 'N',
       'N', 'Y', 'N', 'N', 'N', 'N',
       'Y', 'NP'
FROM AD_Element e
WHERE e.AD_Element_ID = 541423
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593710
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         Name, Type, Code, EntityType)
VALUES (540804 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_Flatrate_RefundConfig of the same C_Flatrate_Conditions', 'S',
        'C_Flatrate_RefundConfig.C_Flatrate_Conditions_ID=@C_Flatrate_Conditions_ID@', 'de.metas.contracts')
;
UPDATE AD_Column
SET IsParent = 'N', AD_Val_Rule_ID = 540804, Updated = TO_TIMESTAMP('2026-10-05 13:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593704
;
-- 3) Tab 549512: level 1 under the header tab 540331, linked via the new column (like the sibling tabs), no display logic
UPDATE AD_Tab
SET TabLevel = 1, AD_Column_ID = 593710, Parent_Column_ID = NULL, DisplayLogic = NULL,
    Description = 'Verpackungen, auf die sich Rückvergütungskonditionen mit aktivem Verpackungsfilter beschränken.',
    Updated = TO_TIMESTAMP('2026-10-05 13:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549512
;
UPDATE AD_Tab_Trl
SET Description = 'Verpackungen, auf die sich Rückvergütungskonditionen mit aktivem Verpackungsfilter beschränken.',
    Updated = TO_TIMESTAMP('2026-10-05 13:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549512 AND AD_Language IN ('de_DE', 'de_CH')
;
UPDATE AD_Tab_Trl
SET Description = 'Packagings that refund conditions with an active packaging filter are restricted to.',
    Updated = TO_TIMESTAMP('2026-10-05 13:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549512 AND AD_Language = 'en_US'
;
-- 4) Fields: hidden link field; the refund config becomes a visible, user-picked field; Packmittel/Aktiv/Sektion move after it
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Tab_ID, AD_Column_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, SeqNo, SeqNoGrid, SortNo, IsReadOnly, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted)
SELECT 785610 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 13:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 13:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, c.AD_Column_ID, c.Name, c.Description, c.Help, 'de.metas.contracts',
       'N', 'N', 0, 0, 0, 'N', 'N', 'N', 'N', 'N'
FROM AD_Column c
WHERE c.AD_Column_ID = 593710
;
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, f.AD_Field_ID, COALESCE(ct.Name, f.Name), COALESCE(ct.Description, f.Description), f.Help, 'Y',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy
FROM AD_Language l
         JOIN AD_Field f ON f.AD_Field_ID = 785610
         LEFT JOIN AD_Column_Trl ct ON ct.AD_Column_ID = f.AD_Column_ID AND ct.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = f.AD_Field_ID)
;
UPDATE AD_Field SET IsDisplayed = 'Y', IsDisplayedGrid = 'Y', SeqNo = 10, SeqNoGrid = 10, SortNo = 1, Updated = TO_TIMESTAMP('2026-10-05 13:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785607
;
UPDATE AD_Field SET SeqNo = 20, SeqNoGrid = 20, SortNo = 2, Updated = TO_TIMESTAMP('2026-10-05 13:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 785605
;
UPDATE AD_Field SET SeqNo = 30, SeqNoGrid = 30, Updated = TO_TIMESTAMP('2026-10-05 13:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 785606
;
UPDATE AD_Field SET SeqNo = 40, SeqNoGrid = 40, Updated = TO_TIMESTAMP('2026-10-05 13:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_Field_ID = 785608
;
INSERT INTO AD_UI_Element (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType, Name, Description, Help,
                           IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsAdvancedField, SeqNo, SeqNoGrid, SeqNo_SideList, WidgetSize)
SELECT 654937 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-05 13:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-05 13:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
       549512, 555800, f.AD_Field_ID, 'F', f.Name, f.Description, f.Help,
       'Y', 'Y', 'N', 'N', 10, 10, 0, 'L'
FROM AD_Field f
WHERE f.AD_Field_ID = 785607
;
UPDATE AD_UI_Element SET SeqNo = 20, SeqNoGrid = 20, Updated = TO_TIMESTAMP('2026-10-05 13:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 654933
;
UPDATE AD_UI_Element SET SeqNo = 30, SeqNoGrid = 30, Updated = TO_TIMESTAMP('2026-10-05 13:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 654934
;
UPDATE AD_UI_Element SET SeqNo = 40, SeqNoGrid = 40, Updated = TO_TIMESTAMP('2026-10-05 13:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 654935
;
UPDATE AD_UI_Element SET SeqNo = 50, SeqNoGrid = 50, Updated = TO_TIMESTAMP('2026-10-05 13:00:19', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE AD_UI_Element_ID = 654936
;
