-- Run mode: SWING_CLIENT

-- Element: IsAutoInvoiceFlatrateTerm
-- 2025-11-10T09:55:01.332Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auto Invoice Contract', PrintName='Auto Invoice Contract',Updated=TO_TIMESTAMP('2025-11-10 09:55:01.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580157 AND AD_Language='en_US'
;

-- 2025-11-10T09:55:01.335Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T09:55:01.541Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580157,'en_US')
;

-- Element: IsAutoInvoiceFlatrateTerm
-- 2025-11-10T09:55:23.156Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Automatische Abrechnung Vertrag', PrintName='Automatische Abrechnung Vertrag',Updated=TO_TIMESTAMP('2025-11-10 09:55:23.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580157 AND AD_Language='de_CH'
;

-- 2025-11-10T09:55:23.165Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T09:55:23.342Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580157,'de_CH')
;

-- Element: IsAutoInvoiceFlatrateTerm
-- 2025-11-10T09:55:28.352Z
UPDATE AD_Element_Trl SET Name='Automatische Abrechnung Vertrag', PrintName='Automatische Abrechnung Vertrag',Updated=TO_TIMESTAMP('2025-11-10 09:55:28.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580157 AND AD_Language='fr_CH'
;

-- 2025-11-10T09:55:28.353Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T09:55:28.530Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580157,'fr_CH')
;

-- Element: IsAutoInvoiceFlatrateTerm
-- 2025-11-10T09:55:34.461Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Automatische Abrechnung Vertrag', PrintName='Automatische Abrechnung Vertrag',Updated=TO_TIMESTAMP('2025-11-10 09:55:34.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580157 AND AD_Language='de_DE'
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- 2025-11-10T09:55:34.462Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-10T09:55:35.094Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580157,'de_DE')
;

-- 2025-11-10T09:55:35.095Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580157,'de_DE')
;

