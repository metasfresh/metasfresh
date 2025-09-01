/*
 * #%L
 * de.metas.fresh.base
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

-- Run mode: SWING_CLIENT

-- Column: M_HU_PI_Item_Product.IsOrderInTuUomWhenMatched
-- 2025-08-31T10:02:41.258Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2025-08-31 10:02:41.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589626
;

-- 2025-08-31T10:02:57.304Z
INSERT INTO t_alter_column values('m_hu_pi_item_product','IsOrderInTuUomWhenMatched','CHAR(1)',null,'Y')
;


