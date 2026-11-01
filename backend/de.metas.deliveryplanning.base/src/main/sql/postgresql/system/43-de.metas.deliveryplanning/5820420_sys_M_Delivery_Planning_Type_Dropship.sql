-- Delivery planning direction: adds the third value 'Dropship' to AD_Reference 541689
-- (M_Delivery_Planning_Types) and gives all three values their German labels --
-- Incoming / Eingehend, Outgoing / Ausgehend, Dropship / Streckengeschaeft (goods travel from the
-- vendor straight to the customer, the wording C_Order.IsDropShip already uses).
--
-- IDs allocated from idserver.metas.de on 2026-08-26:
--   AD_Ref_List 544356 (Dropship value on AD_Reference 541689)

-- 1) the new Dropship value; German in the base Name column
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Reference_ID, Value, Name, ValueName, Description, EntityType, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (544356 /*From ID Server*/, 541689, 'Dropship', 'Streckengeschäft', 'Dropship',
        'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert.',
        'D', 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-26 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-26 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2) seed AD_Ref_List_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544356
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 3) English override for the new value
UPDATE AD_Ref_List_Trl SET Name='Dropship',
       Description='On a dropship the goods are delivered from the vendor directly to the customer.',
       IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-26 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544356 AND AD_Language='en_US'
;

-- 4) mark the German rows of the new value as actively translated (text equals the base)
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-26 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544356 AND AD_Language IN ('de_DE','de_CH')
;

-- 5) German base labels for the two pre-existing values (543335 Incoming, 543336 Outgoing)
UPDATE AD_Ref_List SET Name='Eingehend', Updated=TO_TIMESTAMP('2026-08-26 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=543335
;
UPDATE AD_Ref_List SET Name='Ausgehend', Updated=TO_TIMESTAMP('2026-08-26 09:00:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=543336
;

-- 6) German translations for the two pre-existing values
UPDATE AD_Ref_List_Trl SET Name='Eingehend', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-26 09:00:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=543335 AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Ref_List_Trl SET Name='Ausgehend', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-26 09:00:31', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=543336 AND AD_Language IN ('de_DE','de_CH')
;

-- 7) the English texts of the two pre-existing values are final; flag them as translated
UPDATE AD_Ref_List_Trl SET Name='Incoming', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-26 09:00:40', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=543335 AND AD_Language='en_US'
;
UPDATE AD_Ref_List_Trl SET Name='Outgoing', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-26 09:00:41', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=543336 AND AD_Language='en_US'
;

-- 8) any remaining untranslated language row mirrors the German base text, as the seed in
--    step 2 does. IsTranslated stays 'N' so the row remains flagged as an unreviewed seed.
UPDATE AD_Ref_List_Trl trl SET Name=rl.Name, Updated=TO_TIMESTAMP('2026-08-26 09:00:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM AD_Ref_List rl
WHERE rl.AD_Ref_List_ID=trl.AD_Ref_List_ID
  AND rl.AD_Reference_ID=541689
  AND trl.IsTranslated='N'
;

-- 9) make sure no language row is missing for any value of this reference
SELECT add_missing_translations();

-- 10) fr_CH for the new Dropship value, per the convention stated once in
--     5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql: the en_US text,
--     IsTranslated='N'. Runs last, after steps 8 and 9 have mirrored the German base text into every
--     still-untranslated row -- German is unusable rather than merely untranslated for an fr_CH user.
--     Scoped to 544356: the two pre-existing values of this reference are not part of this change.
UPDATE AD_Ref_List_Trl trl
   SET Name         = en.Name,
       Description  = en.Description,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-08-26 09:01:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Ref_List_Trl en
 WHERE en.AD_Ref_List_ID = trl.AD_Ref_List_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Ref_List_ID = 544356
;
