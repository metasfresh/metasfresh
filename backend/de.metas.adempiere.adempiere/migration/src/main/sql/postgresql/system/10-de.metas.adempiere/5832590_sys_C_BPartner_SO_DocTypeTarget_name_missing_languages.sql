-- Impact
--
-- Follow-up to 5832570. The name element of AD_Field 542107 (C_BPartner.SO_DocTypeTarget_ID, AD_Field.AD_Name_ID) has
-- AD_Element_Trl rows for de_DE, de_CH, en_US and fr_CH only, while AD_Field_Trl of the field also has rows for other languages
-- (e.g. en_GB, it_CH). 5832570 only updates existing AD_Element_Trl rows, and the propagation function
-- update_TRL_Tables_On_AD_Element_TRL_Update only copies from existing AD_Element_Trl rows, so those AD_Field_Trl rows kept the old
-- name "Zielbelegart".
--
--   | AD_Field_Trl language of AD_Field 542107 without AD_Element_Trl row | Name old | Name new | IsTranslated |
--   | en_%  (e.g. en_GB)                                                | Zielbelegart | Order Type | Y |
--   | fr_%  (e.g. fr_FR, if fr_CH has no row)                           | Zielbelegart | Type de commande | Y |
--   | de_%  (if de_DE / de_CH have no row)                              | Zielbelegart | Auftragsart | Y |
--   | all other (e.g. it_CH)                                            | Zielbelegart | Auftragsart (German base text) | N |
--
-- Rows: AD_Element_Trl INSERT for each such language (Name / PrintName / Description / Help), then propagation to AD_Field_Trl.Name /
-- Description / Help of AD_Field 542107. Languages that already have an AD_Element_Trl row, and every other field, are not touched.
-- Same guard as 5832570: an element that is also used elsewhere is left alone (NOTICE).

DO
$$
    DECLARE
        v_element_id numeric;
    BEGIN
        SELECT f.AD_Name_ID INTO v_element_id FROM AD_Field f WHERE f.AD_Field_ID = 542107;

        IF v_element_id IS NULL THEN
            RAISE NOTICE 'AD_Field 542107 has no AD_Name_ID: nothing to do';
        ELSIF EXISTS(SELECT 1 FROM AD_Field f WHERE f.AD_Name_ID = v_element_id AND f.AD_Field_ID <> 542107)
            OR EXISTS(SELECT 1 FROM AD_UI_Element e WHERE e.AD_Name_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Column c WHERE c.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Process_Para p WHERE p.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Tab t WHERE t.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Window w WHERE w.AD_Element_ID = v_element_id)
            OR EXISTS(SELECT 1 FROM AD_Menu m WHERE m.AD_Element_ID = v_element_id) THEN
            RAISE NOTICE 'AD_Element % of AD_Field 542107 is also used elsewhere: not changed', v_element_id;
        ELSE
            INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                        Name, PrintName, Description, Help, IsTranslated)
            SELECT ft.AD_Language, v_element_id, 0, 0, 'Y', TO_TIMESTAMP('2026-11-02 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-11-02 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
                   CASE WHEN ft.AD_Language LIKE 'en\_%' THEN 'Order Type' WHEN ft.AD_Language LIKE 'fr\_%' THEN 'Type de commande' ELSE 'Auftragsart' END,
                   CASE WHEN ft.AD_Language LIKE 'en\_%' THEN 'Order Type' WHEN ft.AD_Language LIKE 'fr\_%' THEN 'Type de commande' ELSE 'Auftragsart' END,
                   CASE WHEN ft.AD_Language LIKE 'en\_%'
                            THEN 'Order type that is pre-filled for sales orders and order candidates of this customer.'
                        WHEN ft.AD_Language LIKE 'fr\_%'
                            THEN 'Type de commande prérempli pour les commandes de vente et les candidats de commande de ce client.'
                        ELSE 'Auftragsart, die bei Verkaufsaufträgen und Auftragskandidaten dieses Kunden vorbelegt wird.' END,
                   CASE WHEN ft.AD_Language LIKE 'en\_%'
                            THEN 'The order type (e.g. prepayment order) that is suggested for new sales orders and order candidates of this customer. Leave empty to use the standard order type. An order type given directly on the order or order candidate takes precedence.'
                        WHEN ft.AD_Language LIKE 'fr\_%'
                            THEN 'Le type de commande (p. ex. commande avec paiement anticipé) proposé pour les nouvelles commandes de vente et les candidats de commande de ce client. Laisser vide pour utiliser le type de commande standard. Un type de commande indiqué directement sur la commande ou le candidat de commande est prioritaire.'
                        ELSE 'Die Auftragsart (z.B. Vorauskasseauftrag), die bei neuen Verkaufsaufträgen und Auftragskandidaten dieses Kunden vorgeschlagen wird. Leer lassen, wenn die Standard-Auftragsart gelten soll. Eine direkt am Auftrag oder Auftragskandidaten angegebene Auftragsart hat Vorrang.' END,
                   CASE WHEN ft.AD_Language ~ '^(en|fr|de)_' THEN 'Y' ELSE 'N' END
            FROM AD_Field_Trl ft
            WHERE ft.AD_Field_ID = 542107
              AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl et WHERE et.AD_Element_ID = v_element_id AND et.AD_Language = ft.AD_Language);

            -- sets AD_Field_Trl (name, description, help) from AD_Element_Trl
            PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(v_element_id);
        END IF;
    END
$$
;
