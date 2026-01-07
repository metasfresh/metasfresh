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

-- Column: C_Order.IncotermLocation
-- 2025-12-01T08:54:51.897Z
-- old: @C_Incoterms_ID/-1@>0 & @C_Incoterms_ID@!540001
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-12-01 08:54:51.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=501614
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Incoterms Stadt
-- Column: C_Order.IncotermLocation
-- 2025-12-01T09:14:54.094Z
-- old: @C_Incoterms_ID/-1@>0 & @C_Incoterms_ID@!540001
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-12-01 09:14:54.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=501628
;

--Also adjust custom windows
SELECT backup_table('AD_Field')
;

UPDATE AD_Field
SET DisplayLogic='@C_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-12-01 09:14:54.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=99
WHERE DisplayLogic='@C_Incoterms_ID/-1@>0 & @C_Incoterms_ID@!540001'
;
