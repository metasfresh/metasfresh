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

-- 2024-02-14T14:14:21.101Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=679029
;

-- Field: HU QR Code -> HU QR Code -> Handling Unit
-- Column: M_HU_QRCode.M_HU_ID
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_QRCode.M_HU_ID
-- 2024-02-14T14:14:21.109Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=679029
;

-- 2024-02-14T14:14:21.131Z
DELETE FROM AD_Field WHERE AD_Field_ID=679029
;

-- 2024-02-14T14:14:21.136Z
/* DDL */ SELECT public.db_alter_table('M_HU_QRCode','ALTER TABLE M_HU_QRCode DROP COLUMN IF EXISTS M_HU_ID')
;

-- Column: M_HU_QRCode.M_HU_ID
-- Column: M_HU_QRCode.M_HU_ID
-- 2024-02-14T14:14:21.199Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=579140
;

-- 2024-02-14T14:14:21.202Z
DELETE FROM AD_Column WHERE AD_Column_ID=579140
;

