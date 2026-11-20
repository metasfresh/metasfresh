-- CU quantity entry: the German description of the picking profile field "Scannable Code Format" (AD_Element 585624, AD_Field 788188) uses the
-- same term as the mode field's name "CU-Mengenerfassung" (AD_Element 585621), instead of a second term for the same concept.

UPDATE AD_Element SET Description='Format des Etiketts, das in den Modi der CU-Mengenerfassung gelesen wird.', Updated=TO_TIMESTAMP('2026-11-20 13:30:00.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585624
;
UPDATE AD_Element_Trl SET Description='Format des Etiketts, das in den Modi der CU-Mengenerfassung gelesen wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-20 13:30:01.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585624
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585624)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585624)
;
