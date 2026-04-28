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

-- Element: null
-- 2025-09-16T08:44:50.735Z
UPDATE AD_Element_Trl SET Name='External System Configuration', PrintName='External System Configuration',Updated=TO_TIMESTAMP('2025-09-16 08:44:50.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=578733 AND AD_Language='en_US'
;

-- 2025-09-16T08:44:50.736Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-16T08:44:50.930Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578733,'en_US')
;

-- Element: null
-- 2025-09-16T08:45:00.592Z
UPDATE AD_Element_Trl SET Name='Externes System Konfiguration', PrintName='Externes System Konfiguration',Updated=TO_TIMESTAMP('2025-09-16 08:45:00.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=578733 AND AD_Language='de_CH'
;

-- 2025-09-16T08:45:00.593Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-16T08:45:00.771Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578733,'de_CH')
;

-- Element: null
-- 2025-09-16T08:45:07.417Z
UPDATE AD_Element_Trl SET Name='Externes System Konfiguration', PrintName='Externes System Konfiguration',Updated=TO_TIMESTAMP('2025-09-16 08:45:07.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=578733 AND AD_Language='de_DE'
;

-- 2025-09-16T08:45:07.418Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-16T08:45:07.899Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(578733,'de_DE')
;

-- 2025-09-16T08:45:07.900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578733,'de_DE')
;

