-- Roles window (AD_Window 111) - AD_Element 600 (Type / Art) Description correction: "context",
-- not "window".
--
-- Review finding L1 on 5825740 (review-findings-16d.md): the generic sentence that script gave
-- AD_Element 600 - "die moeglichen Werte haengen vom jeweiligen Fenster ab." / "the available
-- values depend on the respective window." - is literally false wherever a single window binds
-- element 600 from more than one table. Two active windows do exactly that: window 540100 "UI
-- Trigger" (tabs Action / AD_TriggerUI_Action, reference 540203, and Criterias /
-- AD_TriggerUI_Criteria, reference 540202) and window 541015 "Service/Reparatur Projekt" (tabs
-- Dokumente / reference 540751, Reparaturaufgabe / reference 541243, Reparaturprojekt Ausgaben /
-- reference 541251). A user on either window sees the same tooltip on two fields whose value sets
-- differ, while the tooltip attributes the difference to the window - the value set is actually
-- determined by the column's own reference list, i.e. by the table/tab (the context), not by the
-- window. Fix is one word: "Fenster" -> "Kontext", "window" -> "context". Everything else about
-- the sentence (already judged sound: true for all 32 columns / 36 active tab bindings) is
-- unchanged.
--
-- Standing exclusions carried forward from the earlier scripts of this stack (unaffected by this
-- change, restated for completeness):
--   * AD_Element 405's en_US Name ("readonly") stays unchanged (stated at 5825710:15-16).
--   * German text sitting in non-German-language rows (e.g. Name 'Art' in en_GB / it_CH) is a
--     separate, pre-existing content defect and is not addressed here (stated at 5825700:18-20).
--   * AD_Element 144 (Workflow) and 469 (Name) legitimately have en_US Name = de_DE Name: the
--     English term genuinely equals the German term, so that match is correct, not a gap. Any
--     completeness query over window 111 must carve those two out rather than invent a German word.

-- AD_Element 600 (Type) - de_DE / de_CH: "Fenster" -> "Kontext". Description only, IsTranslated
-- stays 'Y' (both rows were already a completed translation).
UPDATE AD_Element_Trl SET Description='Art des Eintrags; die möglichen Werte hängen vom jeweiligen Kontext ab.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 13:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language IN ('de_DE','de_CH');

-- en_US: "window" -> "context". Description only, IsTranslated stays 'Y'.
UPDATE AD_Element_Trl SET Description='Type of the record; the available values depend on the respective context.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language='en_US';

-- en_GB / it_CH: same wording correction; IsTranslated is intentionally left untouched (still 'N')
-- - their Name is still the German 'Art' (the standing non-German-row exclusion above), so these
-- rows remain an incomplete translation, exactly as 5825740 left them.
UPDATE AD_Element_Trl SET Description='Type of the record; the available values depend on the respective context.', Updated=TO_TIMESTAMP('2026-09-22 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=600 AND AD_Language IN ('en_GB','it_CH');

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'en_US');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'en_GB');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'en_GB');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(600, 'it_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(600, 'it_CH');
