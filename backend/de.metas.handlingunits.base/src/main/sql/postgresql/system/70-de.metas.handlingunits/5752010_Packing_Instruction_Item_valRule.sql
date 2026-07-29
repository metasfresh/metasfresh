/*
 * #%L
 * de.metas.handlingunits.base
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

--old value: M_HU_PI_Item.ItemType='MI'
-- Name: M_HU_PI_Item MI
-- 2025-04-14T08:52:28.248Z
UPDATE AD_Val_Rule SET Code='M_HU_PI_Item.M_HU_PI_Item_ID IN (SELECT pi.M_HU_PI_Item_ID FROM M_HU_PI_Version pv INNER JOIN M_HU_PI_Item pi ON pv.M_HU_PI_Version_ID = pi.M_HU_PI_Version_ID WHERE pi.ItemType=''MI'' AND pv.isActive = ''Y'')',Updated=TO_TIMESTAMP('2025-04-14 10:52:28.247','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540189
;

