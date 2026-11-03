-- Clarify the IsImportLenient texts (it only takes effect on lookup lines with filter operator "Like"),
-- make the {0} of EDI_BPartnerLookup_AmbiguousNoDefault describe all lookup values (not only the GLN),
-- and align the form order of the field with its grid order (after "Entitäts-Art").

UPDATE AD_Element SET Description='Nur bei Suchzeilen mit Filter Operator "Like": Findet die Suche im Import keinen Treffer, wird sie wiederholt und diese Zeile dabei nur auf "nicht gesetzt" geprüft.',Help='Wirkt nur bei Zeilen, deren Wert in der Suche mit dem Filter Operator "Like" verglichen wird; bei anderen Filter Operatoren hat die Option keine Wirkung. Wenn aktiv und der Wert dieser Zeile in den Stammdaten nicht gefunden wird, wird die Suche erneut ausgeführt und diese Zeile dabei nur auf "nicht gesetzt" geprüft. Mehrdeutige Treffer werden wie üblich über das Feld "Replikations Standardwert" des Geschäftspartners aufgelöst.',Updated=TO_TIMESTAMP('2026-10-28 18:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585568
;

UPDATE AD_Element_Trl SET Description='Only for lookup lines with filter operator "Like": if the lookup of an import finds no match, it is repeated and this line is checked only for "not set".',Help='Only takes effect on lines whose value is compared with the filter operator "Like" in the lookup; with any other filter operator the option has no effect. If active and this line''s value is not found in the masterdata, the lookup is repeated with this line checked only for "not set". Ambiguous matches are resolved as usual via the "Replication Lookup Default" flag of the business partner.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-28 18:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585568 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET Description=e.Description,Help=e.Help,IsTranslated=CASE WHEN AD_Element_Trl.AD_Language IN ('de_DE','de_CH') THEN 'Y' ELSE AD_Element_Trl.IsTranslated END,Updated=TO_TIMESTAMP('2026-10-28 18:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 FROM AD_Element e WHERE e.AD_Element_ID=AD_Element_Trl.AD_Element_ID AND AD_Element_Trl.AD_Element_ID=585568 AND AD_Element_Trl.AD_Language IN ('de_DE','de_CH','fr_CH')
;

/* DDL */ select update_Column_Translation_From_AD_Element(585568)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585568)
;

-- Field Description/Help: copy from the element (en_US from its translation)
UPDATE AD_Field SET Description=e.Description,Help=e.Help,Updated=TO_TIMESTAMP('2026-10-28 18:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 FROM AD_Element e WHERE e.AD_Element_ID=585568 AND AD_Field.AD_Field_ID=786268 AND (AD_Field.Description IS NOT NULL OR AD_Field.Help IS NOT NULL)
;

-- Form order: after "Entitäts-Art" (180), like in the grid (180, 185, 187)
UPDATE AD_Field SET SeqNo=185,Updated=TO_TIMESTAMP('2026-10-28 18:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=786268
;

-- Message: {0} = all equality lookup values of the format (GLN and, if set, the store GLN), not only the GLN
UPDATE AD_Message SET MsgText='Suche im Import für die Suchwerte {0}: Das Lookup_Label "{1}" ist in den Stammdaten nicht vorhanden und die Suchwerte passen zu {2} Geschäftspartnern, von denen keiner als "Replikations Standardwert" markiert ist - Auflösung nicht möglich.',Updated=TO_TIMESTAMP('2026-10-28 18:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545967
;

UPDATE AD_Message_Trl SET MsgText='Lookup during import for the lookup values {0}: the Lookup_Label "{1}" is not in the masterdata and the lookup values match {2} business partners, none flagged as "Replication Lookup Default" - cannot resolve.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-28 18:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545967
;

UPDATE AD_Message_Trl SET MsgText=m.MsgText,IsTranslated=CASE WHEN AD_Message_Trl.AD_Language IN ('de_DE','de_CH') THEN 'Y' ELSE AD_Message_Trl.IsTranslated END,Updated=TO_TIMESTAMP('2026-10-28 18:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 FROM AD_Message m WHERE m.AD_Message_ID=AD_Message_Trl.AD_Message_ID AND AD_Message_Trl.AD_Message_ID=545967 AND AD_Message_Trl.AD_Language IN ('de_DE','de_CH','fr_CH')
;
