-- F01010.4 Invoice Accounting Overrides — German window + menu name (AC3)
--
-- Window 541659, its menu node (AD_Menu 542034, under Finanzen), its tab (AD_Tab 546735) and the
-- key column C_Invoice_Acct_ID (AD_Column 585476) are ALL backed by the same AD_Element 581915
-- (impact analysis: those four usages only, all this one feature — safe to rename the element).
-- A direct UPDATE of AD_Window/AD_Menu names is reverted by the after-migration sync
-- (update_TRL_Tables_On_AD_Element_TRL_Update propagates the element name back onto them), so the
-- name MUST be changed on the element and propagated. Base language is German.
-- en_US keeps the English name; the menu tree / parent (Finanzen) is untouched.

UPDATE AD_Element
SET    Name      = 'Rechnung-Konten-Überschreibung',
       PrintName = 'Rechnung-Konten-Überschreibung',
       Updated   = TO_TIMESTAMP('2026-06-18 09:35:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Element_ID = 581915;

UPDATE AD_Element_Trl
SET    Name         = 'Rechnung-Konten-Überschreibung',
       PrintName    = 'Rechnung-Konten-Überschreibung',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-06-18 09:35:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Element_ID = 581915 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET    Name         = 'Invoice Accounting Overrides',
       PrintName    = 'Invoice Accounting Overrides',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-06-18 09:35:02', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Element_ID = 581915 AND AD_Language = 'en_US';

-- Propagate the element name to AD_Window / AD_Menu / AD_Tab / AD_Column (and their _Trl rows)
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581915);
