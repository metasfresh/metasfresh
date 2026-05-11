-- 2026-05-11T18:30:00.000Z
-- me03#28882 — Add 'Manual' value to InvoiceRule reference list (AD_Reference_ID=150)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544228 /*From ID Server*/,150,TO_TIMESTAMP('2026-05-11 18:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Manual',TO_TIMESTAMP('2026-05-11 18:30:00','YYYY-MM-DD HH24:MI:SS'),100,'M','Manual')
;

-- 2026-05-11T18:30:00.000Z
-- me03#28882 — Seed AD_Ref_List_Trl rows for all system languages
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544228 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-05-11T18:30:00.000Z
-- me03#28882 — German translation (de_CH)
UPDATE AD_Ref_List_Trl SET Name='Manuell',Updated=TO_TIMESTAMP('2026-05-11 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544228
;

-- 2026-05-11T18:30:00.000Z
-- me03#28882 — German translation (de_DE)
UPDATE AD_Ref_List_Trl SET Name='Manuell',Updated=TO_TIMESTAMP('2026-05-11 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544228
;

-- 2026-05-11T18:30:00.000Z
-- me03#28882 — Dutch translation (nl_NL) — mirrors the AfterPick precedent which used the German term for nl_NL
UPDATE AD_Ref_List_Trl SET Name='Manuell',Updated=TO_TIMESTAMP('2026-05-11 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=544228
;

-- 2026-05-11T18:30:00.000Z
-- me03#28882 — Mark the en_US row as translated (the INSERT above carries the English default 'Manual')
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-11 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544228
;

-- 2026-05-11T18:30:00.000Z
-- me03#28882 — Backfill translations for any other active languages (idempotent)
SELECT add_missing_translations()
;
