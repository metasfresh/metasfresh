/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- ============================================================
-- GERMAN TRANSLATION TABLES (de_DE)
-- ============================================================

-- ad_element_trl
UPDATE ad_element_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    printname   = REGEXP_REPLACE(REGEXP_REPLACE(printname, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_DE'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR printname ~ '\mTU\M' OR printname ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_field_trl
UPDATE ad_field_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_DE'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_tab_trl
UPDATE ad_tab_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_DE'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_process_trl
UPDATE ad_process_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_DE'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_process_para_trl
UPDATE ad_process_para_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_DE'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_window_trl
UPDATE ad_window_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_DE'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_message_trl
UPDATE ad_message_trl
SET msgtext = REGEXP_REPLACE(REGEXP_REPLACE(msgtext, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_DE'
  AND (msgtext ~ '\mTU\M' OR msgtext ~ '\mLU\M')
;



-- ============================================================
-- BASE TABLES
-- ============================================================

-- ad_element
UPDATE ad_element
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    printname   = REGEXP_REPLACE(REGEXP_REPLACE(printname, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE name ~ '\mTU\M'
   OR name ~ '\mLU\M'
   OR printname ~ '\mTU\M'
   OR printname ~ '\mLU\M'
   OR description ~ '\mTU\M'
   OR description ~ '\mLU\M'
;

-- ad_column
UPDATE ad_column
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE name ~ '\mTU\M'
   OR name ~ '\mLU\M'
   OR description ~ '\mTU\M'
   OR description ~ '\mLU\M'
;

-- ad_field
UPDATE ad_field
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE name ~ '\mTU\M'
   OR name ~ '\mLU\M'
   OR description ~ '\mTU\M'
   OR description ~ '\mLU\M'
;

-- ad_tab
UPDATE ad_tab
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE name ~ '\mTU\M'
   OR name ~ '\mLU\M'
   OR description ~ '\mTU\M'
   OR description ~ '\mLU\M'
;

-- ad_process
UPDATE ad_process
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE name ~ '\mTU\M'
   OR name ~ '\mLU\M'
   OR description ~ '\mTU\M'
   OR description ~ '\mLU\M'
;

-- ad_process_para
UPDATE ad_process_para
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE name ~ '\mTU\M'
   OR name ~ '\mLU\M'
   OR description ~ '\mTU\M'
   OR description ~ '\mLU\M'
;

-- ad_window
UPDATE ad_window
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE name ~ '\mTU\M'
   OR name ~ '\mLU\M'
   OR description ~ '\mTU\M'
   OR description ~ '\mLU\M'
;

-- ad_message
UPDATE ad_message
SET msgtext = REGEXP_REPLACE(REGEXP_REPLACE(msgtext, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE msgtext ~ '\mTU\M'
   OR msgtext ~ '\mLU\M'
;

-- ============================================================
-- GERMAN TRANSLATION TABLES (de_CH)
-- ============================================================

-- ad_element_trl
UPDATE ad_element_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    printname   = REGEXP_REPLACE(REGEXP_REPLACE(printname, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_CH'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR printname ~ '\mTU\M' OR printname ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_field_trl
UPDATE ad_field_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_CH'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_tab_trl
UPDATE ad_tab_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_CH'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_process_trl
UPDATE ad_process_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_CH'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_process_para_trl
UPDATE ad_process_para_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_CH'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_window_trl
UPDATE ad_window_trl
SET name        = REGEXP_REPLACE(REGEXP_REPLACE(name, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g'),
    description = REGEXP_REPLACE(REGEXP_REPLACE(description, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_CH'
  AND (name ~ '\mTU\M' OR name ~ '\mLU\M'
    OR description ~ '\mTU\M' OR description ~ '\mLU\M')
;

-- ad_message_trl
UPDATE ad_message_trl
SET msgtext = REGEXP_REPLACE(REGEXP_REPLACE(msgtext, '\mTU\M', 'Kolli', 'g'), '\mLU\M', 'Pal', 'g')
WHERE ad_language = 'de_CH'
  AND (msgtext ~ '\mTU\M' OR msgtext ~ '\mLU\M')
;
