-- Impact
--
-- Field name of AD_Field 542107 (C_BPartner.SO_DocTypeTarget_ID), via its own AD_Element (resolved by FK, local id 1000117):
--   AD_Element_Trl.Name / PrintName / Description / Help and, via propagation, AD_Field_Trl + AD_Field (name, description, help)
--   | language     | Name old    | Name new         |
--   | de_DE, de_CH | Zielbelegart | Auftragsart     |
--   | en_US        | Target Document Type | Order Type |
--   | fr_CH        | Type de document cible | Type de commande |
--   | other active languages (e.g. en_GB, it_CH) | old text | Order Type (English fallback, only rows that exist) |
-- Validation rule: AD_Column 540377 (C_BPartner.SO_DocTypeTarget_ID) AD_Val_Rule_ID old 540285 -> new 540810 (new row).
-- Layout: new AD_UI_Element 655461 on AD_Tab 223 (form view; grid placement is done by the follow-up script 5832580).
--
-- NOT affected:
--   - AD_Element 197 (C_DocTypeTarget_ID, "Zielbelegart") and AD_Element 52020 (OrderType / "Auftragsart"): not touched
--   - AD_Val_Rule 540285: not changed (still used by the invoice aggregation columns), only AD_Column 540377 stops using it
--   - hidden or inactive fields that use the same column rule switch (AD_Field 545328, 558980, 559102, 559961, 562007,
--     562185, 583064, and 543234 in the inactive window "Cockpit"): they stay hidden / inactive, no layout change
--
-- Business Partner window (AD_Window 123), tab "Kunde" (AD_Tab 223): shows the partner's order type
-- C_BPartner.SO_DocTypeTarget_ID (AD_Field 542107, AD_Column 540377), which was hidden so far.
-- The order window and the order candidates take it as the order type of a customer's sales orders.
-- 1. A new validation rule offers only sales order types (not quotations, cost estimates, frame contracts,
--    call-offs, returns); the column used a rule that belongs to the invoice aggregation columns.
-- 2. The field's own name element is rewritten (in place; only this field uses it) to "Auftragsart" / "Order Type".
-- 3. The field is placed on the tab, after "Zahlungsweise Info" and before "Zahlungsbedingung".

-- 1. Validation rule: active sales order types except quotation (ON, OB), cost estimate (CE), frame contract (FA),
--    call-off (CAO, OC) and return (RM)
INSERT INTO AD_Val_Rule (AD_Client_ID, AD_Org_ID, AD_Val_Rule_ID, Code, Created, CreatedBy, Description, EntityType, IsActive, Name, Type, Updated, UpdatedBy)
VALUES (0, 0, 540810 /*From ID Server*/,
        'C_DocType.IsActive=''Y'' AND C_DocType.DocBaseType=''SOO'' AND C_DocType.IsSOTrx=''Y'' AND COALESCE(C_DocType.DocSubType,'' '') NOT IN (''RM'',''ON'',''OB'',''CE'',''FA'',''CAO'',''OC'')',
        TO_TIMESTAMP('2026-11-02 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Sales order types a customer can have as its order type', 'D', 'Y', 'C_DocType_SalesOrderType_ForBPartner', 'S',
        TO_TIMESTAMP('2026-11-02 19:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

UPDATE AD_Column
SET AD_Val_Rule_ID = 540810 /*From ID Server*/, Updated = TO_TIMESTAMP('2026-11-02 19:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 540377
;

-- 2. Name, description and help of the field. Needed: the element id is resolved by FK (not hard-coded: its id on other instances is
--    not verified), and a shared element is left alone and the rename skipped with a notice (never abort the migration run, which
--    would strand the rollout of other customers).
DO
$$
    DECLARE
        v_element_id numeric;
    BEGIN
        SELECT f.AD_Name_ID INTO v_element_id FROM AD_Field f WHERE f.AD_Field_ID = 542107;

        IF v_element_id IS NULL THEN
            RAISE NOTICE 'AD_Field 542107 has no AD_Name_ID: its name, description and help are NOT changed';
        ELSIF EXISTS(SELECT 1 FROM AD_Field f WHERE f.AD_Name_ID = v_element_id AND f.AD_Field_ID <> 542107)
            OR EXISTS(SELECT 1 FROM AD_UI_Element e WHERE e.AD_Name_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Column c WHERE c.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Process_Para p WHERE p.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Tab t WHERE t.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Window w WHERE w.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Menu m WHERE m.AD_Element_ID = v_element_id) THEN
            RAISE NOTICE 'AD_Element % of AD_Field 542107 is also used elsewhere: its name, description and help are NOT changed', v_element_id;
        ELSE
            UPDATE AD_Element_Trl
            SET Name = 'Auftragsart', PrintName = 'Auftragsart',
                Description = 'Auftragsart, die bei Verkaufsaufträgen und Auftragskandidaten dieses Kunden vorbelegt wird.',
                Help = 'Die Auftragsart (z.B. Vorauskasseauftrag), die bei neuen Verkaufsaufträgen und Auftragskandidaten dieses Kunden vorgeschlagen wird. Leer lassen, wenn die Standard-Auftragsart gelten soll. Eine direkt am Auftrag oder Auftragskandidaten angegebene Auftragsart hat Vorrang.',
                IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-02 19:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
            WHERE AD_Element_ID = v_element_id AND AD_Language IN ('de_DE', 'de_CH');

            UPDATE AD_Element_Trl
            SET Name = 'Order Type', PrintName = 'Order Type',
                Description = 'Order type that is pre-filled for sales orders and order candidates of this customer.',
                Help = 'The order type (e.g. prepayment order) that is suggested for new sales orders and order candidates of this customer. Leave empty to use the standard order type. An order type given directly on the order or order candidate takes precedence.',
                IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-02 19:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
            WHERE AD_Element_ID = v_element_id AND AD_Language = 'en_US';

            UPDATE AD_Element_Trl
            SET Name = 'Type de commande', PrintName = 'Type de commande',
                Description = 'Type de commande prérempli pour les commandes de vente et les candidats de commande de ce client.',
                Help = 'Le type de commande (p. ex. commande avec paiement anticipé) proposé pour les nouvelles commandes de vente et les candidats de commande de ce client. Laisser vide pour utiliser le type de commande standard. Un type de commande indiqué directement sur la commande ou le candidat de commande est prioritaire.',
                IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-02 19:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
            WHERE AD_Element_ID = v_element_id AND AD_Language = 'fr_CH';

            -- other active languages (e.g. en_GB, it_CH): English fallback, so the label is not left on the old text
            UPDATE AD_Element_Trl
            SET Name = 'Order Type', PrintName = 'Order Type',
                Description = 'Order type that is pre-filled for sales orders and order candidates of this customer.',
                Help = 'The order type (e.g. prepayment order) that is suggested for new sales orders and order candidates of this customer. Leave empty to use the standard order type. An order type given directly on the order or order candidate takes precedence.',
                IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-02 19:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
            WHERE AD_Element_ID = v_element_id AND AD_Language NOT IN ('de_DE', 'de_CH', 'en_US', 'fr_CH');

            -- sets AD_Field_Trl (name, description, help) and the base AD_Field name and description; not the base AD_Field.Help
            PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(v_element_id);

            UPDATE AD_Field
            SET Help = 'Die Auftragsart (z.B. Vorauskasseauftrag), die bei neuen Verkaufsaufträgen und Auftragskandidaten dieses Kunden vorgeschlagen wird. Leer lassen, wenn die Standard-Auftragsart gelten soll. Eine direkt am Auftrag oder Auftragskandidaten angegebene Auftragsart hat Vorrang.',
                Updated = TO_TIMESTAMP('2026-11-02 19:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
            WHERE AD_Field_ID = 542107;
        END IF;
    END
$$
;

-- 3. Place the field on tab "Kunde", element group "payment conditions" (AD_UI_ElementGroup 540672): form view only
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 542107, 0, 223, 540672, 655461 /*From ID Server*/, 'F', TO_TIMESTAMP('2026-11-02 19:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N', 'Auftragsart', 20, 0, 0, TO_TIMESTAMP('2026-11-02 19:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
