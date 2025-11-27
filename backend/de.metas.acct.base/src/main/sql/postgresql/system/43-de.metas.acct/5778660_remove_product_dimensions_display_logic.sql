/*
 * #%L
 * de.metas.acct.base
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

-- Field: Produkt(140,D) -> Produkt(180,D) ->  Länge (cm)
-- Column: M_Product.LengthInCm
-- 2025-11-27T08:07:12.809Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-11-27 08:07:12.808000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755969
;

-- Field: Produkt(140,D) -> Produkt(180,D) -> Höhe (cm)
-- Column: M_Product.HeightInCm
-- 2025-11-27T08:07:31.146Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-11-27 08:07:31.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755970
;

-- Field: Produkt(140,D) -> Produkt(180,D) -> Breite (cm)
-- Column: M_Product.WidthInCm
-- 2025-11-27T08:12:22.652Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-11-27 08:12:22.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755971
;

