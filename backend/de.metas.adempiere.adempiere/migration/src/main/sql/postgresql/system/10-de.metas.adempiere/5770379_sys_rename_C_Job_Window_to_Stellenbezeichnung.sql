-- Rename AD_Window 351 (C_Job) from 'Position' to 'Stellenbezeichnung'.
--
-- Background: script 5770382 introduced UNIQUE(Name, AD_Language) on AD_Window_Trl.
-- In customer databases two distinct windows both carry Name='Position'.
-- When add_missing_translations() copies missing language rows from AD_Window.Name it
-- hits the unique constraint for any language already covered by the other 'Position' window.
--
-- Fix: give the C_Job window a distinct name so add_missing_translations() no longer conflicts.
-- English (en_US, en_GB) uses 'Job Title'.

-- 1. Base window name (German / base language)
UPDATE AD_Window
SET Name      = 'Stellenbezeichnung',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Window_ID = 351
  AND Name = 'Position'
;

-- 2. Window translations — all languages except English
UPDATE AD_Window_Trl
SET Name      = 'Stellenbezeichnung',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Window_ID = 351
  AND Name = 'Position'
  AND AD_Language NOT IN ( 'en_US', 'en_GB')
;

-- 2b. English window translation → 'Job Title'
UPDATE AD_Window_Trl
SET Name      = 'Job Title',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Window_ID = 351
  AND Name = 'Position'
  AND AD_Language IN ( 'en_US', 'en_GB')
;

-- 3. AD_Element that drives the window name (element 574214)
UPDATE AD_Element
SET Name      = 'Stellenbezeichnung',
    PrintName = 'Stellenbezeichnung',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Element_ID = 574214
  AND Name = 'Position'
;

-- 4. AD_Element translations — all languages except English
UPDATE AD_Element_Trl
SET Name      = 'Stellenbezeichnung',
    PrintName = 'Stellenbezeichnung',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Element_ID = 574214
  AND Name = 'Position'
  AND AD_Language NOT IN ( 'en_US', 'en_GB')
;

-- 4b. English element translation → 'Job Title'
UPDATE AD_Element_Trl
SET Name      = 'Job Title',
    PrintName = 'Job Title',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Element_ID = 574214
  AND Name = 'Position'
  AND AD_Language IN ( 'en_US', 'en_GB')
;

-- 5. Menu entry for this window (base language)
UPDATE AD_Menu
SET Name      = 'Stellenbezeichnung',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Window_ID = 351
  AND Name = 'Position'
;

-- 6a. Menu translations — all languages except English
UPDATE AD_Menu_Trl
SET Name      = 'Stellenbezeichnung',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Menu_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Window_ID = 351)
  AND Name = 'Position'
  AND AD_Language NOT IN ('en_US', 'en_GB')
;

-- 6b. English menu translations → 'Job Title'
UPDATE AD_Menu_Trl
SET Name      = 'Job Title',
    Updated   = now(),
    UpdatedBy = 100
WHERE AD_Menu_ID IN (SELECT AD_Menu_ID FROM AD_Menu WHERE AD_Window_ID = 351)
  AND Name = 'Position'
  AND AD_Language IN ('en_US', 'en_GB')
;
