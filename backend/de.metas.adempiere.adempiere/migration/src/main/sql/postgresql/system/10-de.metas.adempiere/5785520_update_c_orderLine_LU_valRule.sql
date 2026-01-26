/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- Column: C_OrderLine.M_LU_HU_PI_ID
-- 2026-01-26T17:57:14.857Z
-- old: M_HU_PI Only LUs (Still used on Quick input. On Quick input LU is the main first set pi, because qty is set for it )
-- new: M_HU_PI LU matching M_HU_PI_Item_Product_ID
UPDATE AD_Column SET AD_Val_Rule_ID=540746,Updated=TO_TIMESTAMP('2026-01-26 17:57:14.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590569
;
