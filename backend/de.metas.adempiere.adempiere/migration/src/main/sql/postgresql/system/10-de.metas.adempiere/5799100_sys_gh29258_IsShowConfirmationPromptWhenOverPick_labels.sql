-- me03#29258: AD_Element 583289 / IsShowConfirmationPromptWhenOverPick
-- shipped with the label "Rückfragen bei Überkommissionierung" (DE) and
-- "Ask User when Over Picking" (EN) and no description. Both labels imply
-- the flag only controls whether a confirmation prompt is shown, but it
-- actually gates whether over-picking is allowed at all:
--
--   Flag ON  -> over-picking is allowed; the picker must confirm via prompt
--   Flag OFF -> over-picking is hard-blocked in the frontend ("N über max")
--
-- Wiring: PickLineScanScreen.jsx line 143 and PickStepScanScreen.jsx line 74
--   getConfirmationPromptForQty={isShowPromptWhenOverPicking ? getConfirmationPromptForQty : undefined}
-- ScanHUAndGetQtyComponent.jsx line 177 skips the hard qtyMax check iff the
-- prompt handler is provided.
--
-- This migration renames labels only; neither the column name nor code
-- changes, so existing profile values are preserved.

-- AD_Element (base / de_DE)
UPDATE AD_Element
   SET Name         = 'Überkomm. mit Rückfrage erl.',
       PrintName    = 'Überkomm. mit Rückfrage erl.',
       Description  = 'Wenn aktiviert, darf der Kommissionierer mehr als die Auftragsmenge erfassen und muss die Überkommissionierung über eine Rückfrage bestätigen. Wenn deaktiviert, wird die Überkommissionierung  blockiert («N über max»).',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583289;

-- AD_Element_Trl: de_DE, de_CH — same German text
UPDATE AD_Element_Trl
   SET Name         = 'Überkomm. mit Rückfrage erl.',
       PrintName    = 'Überkomm. mit Rückfrage erl.',
       Description  = 'Wenn aktiviert, darf der Kommissionierer mehr als die Auftragsmenge erfassen und muss die Überkommissionierung über eine Rückfrage bestätigen. Wenn deaktiviert, wird die Überkommissionierung  blockiert («N über max»).',
       IsTranslated = 'N',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583289
   AND AD_Language IN ('de_DE', 'de_CH');

-- AD_Element_Trl: en_US
UPDATE AD_Element_Trl
   SET Name         = 'Allow over-pick with prompt',
       PrintName    = 'Allow over-pick with prompt',
       Description  = 'When enabled, the picker can record more than the ordered qty; the over-delivery must be confirmed via a prompt. When disabled, over-picking is hard-blocked in the frontend ("N above max").',
       IsTranslated = 'Y',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583289
   AND AD_Language = 'en_US';

-- AD_Element_Trl: fr_CH — keep placeholder consistent with the German base
UPDATE AD_Element_Trl
   SET Name         = 'Überkomm. mit Rückfrage erl.',
       PrintName    = 'Überkomm. mit Rückfrage erl.',
       Description  = 'Wenn aktiviert, darf der Kommissionierer mehr als die Auftragsmenge erfassen und muss die Überkommissionierung über eine Rückfrage bestätigen. Wenn deaktiviert, wird die Überkommissionierung  blockiert («N über max»).',
       IsTranslated = 'N',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583289
   AND AD_Language = 'fr_CH';

-- AD_Column: MobileUI_UserProfile_Picking.IsShowConfirmationPromptWhenOverPick (id 589168)
-- AD_Column: MobileUI_UserProfile_Picking_Job.IsShowConfirmationPromptWhenOverPick (id 589660)
UPDATE AD_Column
   SET Name        = 'Überkomm. mit Rückfrage erl.',
       Description = 'Wenn aktiviert, darf der Kommissionierer mehr als die Auftragsmenge erfassen und muss die Überkommissionierung über eine Rückfrage bestätigen. Wenn deaktiviert, wird die Überkommissionierung  blockiert («N über max»).',
       Updated     = now(),
       UpdatedBy   = 100
 WHERE AD_Column_ID IN (589168, 589660);
