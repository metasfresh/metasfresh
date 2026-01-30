/*
 * #%L
 * de.metas.ui.web.base
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

-- Run mode: SWING_CLIENT

-- UI Element: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> Main -> 10 -> main.AttributesKey (technical)
-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2026-01-30T10:03:02.974Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637935
;

-- 2026-01-30T10:03:02.975Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755072
;

-- Field: Material Cockpit v2(541963,D) -> MD_Cockpit QtyDemand and QtySupply(548476,D) -> AttributesKey (technical)
-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2026-01-30T10:03:02.979Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=755072
;

-- 2026-01-30T10:03:02.983Z
DELETE FROM AD_Field WHERE AD_Field_ID=755072
;

-- Column: QtyDemand_QtySupply_V.AttributesKey
-- 2026-01-30T10:03:03Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584385
;

-- 2026-01-30T10:03:03.005Z
DELETE FROM AD_Column WHERE AD_Column_ID=584385
;

