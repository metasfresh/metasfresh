-- Boni: window "Zahlung" (195), tab 330 shows the payment bonus ("Zahlungsbonus", editable) and the read-only link
-- "Zahlungsbonus-Gutschrift" in the amounts group, right after Skonto and before the Unter-/Ueberzahlung switch.
-- Both fields appear in the form view only (like Skonto), not in the grid.
--
-- IDs allocated from idserver.metas.de on 2026-10-12:
--   AD_MigrationScript 5829210
--   AD_Field 785682 (C_Payment.PaymentBonusAmt), AD_Field 785683 (C_Payment.PaymentBonus_CreditMemo_ID)
--   AD_UI_Element 655010, AD_UI_Element 655011 (both in AD_UI_ElementGroup 540064)

-- Zahlungsbonus. Hiding it at 0 is deliberate: the bonus is a pre-filled proposal, hidden for customers without a bonus contract.
INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,DisplayLogic,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (785682 /*From ID Server*/,0,0,593733,330,TO_TIMESTAMP('2026-10-12 13:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','N','Y','N','N','Zahlungsbonus','@C_Invoice_ID/0@!0 & @PaymentBonusAmt/0@!0',235,0,0,TO_TIMESTAMP('2026-10-12 13:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785682
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585517)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (655010 /*From ID Server*/,0,0,785682,330,540064,'F',TO_TIMESTAMP('2026-10-12 13:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsbonus',65,0,0,TO_TIMESTAMP('2026-10-12 13:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Zahlungsbonus-Gutschrift (read-only link to the credit memo)
INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,DisplayLogic,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (785683 /*From ID Server*/,0,0,593734,330,TO_TIMESTAMP('2026-10-12 13:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','N','N','Y','N','Zahlungsbonus-Gutschrift','@PaymentBonus_CreditMemo_ID/0@>0',236,0,0,TO_TIMESTAMP('2026-10-12 13:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785683
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585536)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (655011 /*From ID Server*/,0,0,785683,330,540064,'F',TO_TIMESTAMP('2026-10-12 13:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsbonus-Gutschrift',66,0,0,TO_TIMESTAMP('2026-10-12 13:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;
