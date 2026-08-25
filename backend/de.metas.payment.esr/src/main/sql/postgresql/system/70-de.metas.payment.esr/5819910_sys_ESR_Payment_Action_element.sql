-- ESR_ImportLine.ESR_Payment_Action: give the FIELD a real label, description and help.
--
-- WHY. AD_Element 542031 (ColumnName ESR_Payment_Action) still carries the raw column name as its
-- Name, so the WebUI shows the technical string "ESR_Payment_Action" as the field label:
--   AD_Element.Name        = 'ESR_Payment_Action',  Description = NULL, Help = NULL
--   AD_Element_Trl de_DE   = 'ESR_Payment_Action',  IsTranslated = 'N'
--   AD_Element_Trl de_CH   = 'ESR_Payment_Action',  IsTranslated = 'N'
--   AD_Element_Trl en_US   = 'Action',              IsTranslated = 'Y'   (vague, no description)
-- So a German user reads a Java-ish identifier and an English user reads an unqualified "Action";
-- neither gets any description or help, on the one field that decides how an incoming payment is
-- handled and that BLOCKS the import while it is empty.
--
-- The Help deliberately documents why the list can come up empty (no payment on the line), because
-- that is a dead end users otherwise hit with no explanation.
--
-- SCOPE. Element 542031 is used by exactly ONE AD_Column (548689, ESR_ImportLine) and by no AD_Field
-- via AD_Name_ID, so this rename cannot leak into an unrelated field's label.
--
-- Base language is German, so the German text is written to AD_Element_Trl (never to AD_Element
-- directly) and pushed outward by update_TRL_Tables_On_AD_Element_TRL_Update, which also refreshes
-- AD_Element itself and the dependent AD_Column/AD_Field translations.

-- 1. make sure every active system language has a row to update (a language added after the element
--    was created would otherwise have none). No IsActive filter in the guard: AD_Element_Trl's key is
--    (AD_Element_ID, AD_Language), so filtering it would hide a deactivated row and the INSERT would
--    then violate that key.
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Name,PrintName,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name,t.PrintName,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM   AD_Language l, AD_Element t
WHERE  l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=542031
  AND  NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2. German (base language) — de_DE and de_CH share the text.
UPDATE AD_Element_Trl SET
       Name='Zahlungsaktion',
       PrintName='Zahlungsaktion',
       Description='Legt fest, wie mit dem Zahlungseingang dieser Importzeile verfahren wird. Muss gesetzt sein, damit der Import abgeschlossen werden kann.',
       Help='Die Auswahl richtet sich nach Rechnung und offenem Betrag der Zeile: Überzahlung, Unterzahlung oder keine Rechnung. Ist der Zeile keine Zahlung zugeordnet, steht keine Aktion zur Auswahl.',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-25 16:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE  AD_Element_ID=542031 AND AD_Language IN ('de_DE','de_CH')
;

-- 3. English.
UPDATE AD_Element_Trl SET
       Name='Payment action',
       PrintName='Payment action',
       Description='Determines how the incoming payment on this import line is handled. Must be set before the import can be completed.',
       Help='The available choices depend on the line''s invoice and open amount: overpayment, underpayment, or no invoice. If the line has no payment, no action can be selected.',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-25 16:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE  AD_Element_ID=542031 AND AD_Language='en_US'
;

-- 3b. any OTHER active system language: take the corrected German text, honestly flagged as not yet
--     translated. Needed because statement 1 seeds from the base AD_Element row, which still holds the
--     old raw name at that point -- the same seed-before-rename ordering that left 5741590's rows stale.
--     A language that already claims a finished translation is left alone.
UPDATE AD_Element_Trl t SET
       Name=de.Name, PrintName=de.PrintName, Description=de.Description, Help=de.Help,
       IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-08-25 16:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM   AD_Element_Trl de
WHERE  de.AD_Element_ID=542031 AND de.AD_Language='de_DE'
  AND  t.AD_Element_ID=542031
  AND  t.AD_Language NOT IN ('de_DE','de_CH','en_US')
  AND  coalesce(t.IsTranslated,'N') <> 'Y'
;

-- 4. push AD_Element_Trl outward: refreshes AD_Element itself plus the dependent AD_Column/AD_Field
--    translations. Without this the field keeps showing its old label.
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542031);
