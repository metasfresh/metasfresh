-- me03#29258: The AD_Element 583236 / IsAllowSkippingRejectedReason carried a
-- misleading label "Abweichende Menge erlauben" and a description that claimed
-- it controlled under-picking. In fact it only adds an "Ohne Grund" option to
-- the rejection-reason dropdown so the picker does not have to choose a reason
-- when picking a lower quantity. Renaming the checkbox + description + both
-- AD_Column records to match the actual behavior.

-- AD_Element (base / de_DE)
UPDATE AD_Element
   SET Name         = 'Ohne Grund',
       PrintName    = 'Ohne Grund',
       Description  = 'Wenn aktiviert kann der Kommissionierer eine geringere Menge ohne Angabe eines Grundes erfassen (der Dropdown erhält eine zusätzliche Option «Ohne Grund»).',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583236;

-- AD_Element_Trl: de_DE, de_CH — keep same German text, untranslated flag
UPDATE AD_Element_Trl
   SET Name         = 'Ohne Grund',
       PrintName    = 'Ohne Grund',
       Description  = 'Wenn aktiviert kann der Kommissionierer eine geringere Menge ohne Angabe eines Grundes erfassen (der Dropdown erhält eine zusätzliche Option «Ohne Grund»).',
       IsTranslated = 'N',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583236
   AND AD_Language IN ('de_DE', 'de_CH');

-- AD_Element_Trl: en_US — proper English translation
UPDATE AD_Element_Trl
   SET Name         = 'Without reason',
       PrintName    = 'Without reason',
       Description  = 'When enabled, the picker can record a lower quantity without specifying a reason (the dropdown gets an additional «Without reason» option).',
       IsTranslated = 'Y',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583236
   AND AD_Language = 'en_US';

-- AD_Element_Trl: fr_CH — keep the placeholder consistent with the German base
UPDATE AD_Element_Trl
   SET Name         = 'Ohne Grund',
       PrintName    = 'Ohne Grund',
       Description  = 'Wenn aktiviert kann der Kommissionierer eine geringere Menge ohne Angabe eines Grundes erfassen (der Dropdown erhält eine zusätzliche Option «Ohne Grund»).',
       IsTranslated = 'N',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583236
   AND AD_Language = 'fr_CH';

-- AD_Column: MobileUI_UserProfile_Picking.IsAllowSkippingRejectedReason (id 588938)
-- AD_Column: MobileUI_UserProfile_Picking_Job.IsAllowSkippingRejectedReason (id 589655)
UPDATE AD_Column
   SET Name        = 'Ohne Grund',
       Description = 'Wenn aktiviert kann der Kommissionierer eine geringere Menge ohne Angabe eines Grundes erfassen (der Dropdown erhält eine zusätzliche Option «Ohne Grund»).',
       Updated     = now(),
       UpdatedBy   = 100
 WHERE AD_Column_ID IN (588938, 589655);
