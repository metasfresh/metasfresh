/*
 * #%L
 * de.metas.edi
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

-- Column: C_BPartner.SalesRep_ID
-- 2026-02-05T16:14:07.508Z
UPDATE AD_Column SET AD_Reference_Value_ID=540401, IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2026-02-05 16:14:07.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=4431
;

-- Name: AD_User - SalesRep systemuser or isSOTrx = N
-- 2026-02-05T16:19:49.103Z
UPDATE AD_Reference SET Name='AD_User - SalesRep systemuser or isSOTrx = N',Updated=TO_TIMESTAMP('2026-02-05 16:19:49.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=190
;

-- 2026-02-05T16:19:49.283Z
UPDATE AD_Reference_Trl trl SET Name='AD_User - SalesRep systemuser or isSOTrx = N' WHERE AD_Reference_ID=190 AND AD_Language='de_DE'
;

