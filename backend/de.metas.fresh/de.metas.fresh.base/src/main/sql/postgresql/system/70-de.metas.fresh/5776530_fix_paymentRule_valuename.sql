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

-- Reference: _Payment Rule
-- Value: F
-- old ValueName: Verrechnung
-- new ValueName: Settlement
-- 2025-11-12T15:10:37.510Z

UPDATE AD_Ref_List SET ValueName='Settlement',Updated=TO_TIMESTAMP('2025-11-12 15:10:37.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543788
;

-- Reference: _Payment Rule
-- Value: E
-- old ValueName: Rückerstattung
-- new ValueName: Reimbursement
-- 2025-11-12T15:10:33.122Z
UPDATE AD_Ref_List SET ValueName='Reimbursement',Updated=TO_TIMESTAMP('2025-11-12 15:10:33.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543787
;

