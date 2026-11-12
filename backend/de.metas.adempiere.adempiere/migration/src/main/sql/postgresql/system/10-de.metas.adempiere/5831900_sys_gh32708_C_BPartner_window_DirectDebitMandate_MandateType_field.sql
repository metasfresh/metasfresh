-- SEPA direct debit: show the new mandate type in the business partner window (window 123 -> tab 548409 "Lastschrift Mandat").
-- The field is placed in the same element group as the mandate reference, directly after it (single view and grid).
-- ID server allocations: script 5831900, AD_Field 786269, AD_UI_Element 655453

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Mandatstyp
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateType
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593765,786269 /*From ID Server*/,0,548409,TO_TIMESTAMP('2026-10-28 11:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,4,'D','Y','N','N','N','N','N','N','N','Mandatstyp',TO_TIMESTAMP('2026-10-28 11:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100);

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=786269
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585569);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=786269;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(786269);

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Mandatstyp
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateType
-- SeqNo / SeqNoGrid 25: directly after Mandatsreferenz (20) and before Mandatsdatum (30)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,786269,0,548409,553486,655453 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-28 11:00:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Mandatstyp',25,25,0,TO_TIMESTAMP('2026-10-28 11:00:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100);
