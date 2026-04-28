/*
 * #%L
 * de.metas.salescandidate.base
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

-- Run mode: WEBUI

-- 2025-11-13T13:42:15.883Z
INSERT INTO C_OLCandAggAndOrder (AD_Client_ID,AD_Column_OLCand_ID,AD_Org_ID,C_OLCandAggAndOrder_ID,C_OLCandProcessor_ID,Created,CreatedBy,GroupBy,IsActive,SplitOrder,Updated,UpdatedBy) VALUES (1000000,591512,1000000,540027,1000003,TO_TIMESTAMP('2025-11-13 13:42:15.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','N',TO_TIMESTAMP('2025-11-13 13:42:15.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-13T13:42:22.823Z
UPDATE C_OLCandAggAndOrder SET SplitOrder='Y',Updated=TO_TIMESTAMP('2025-11-13 13:42:22.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540027
;

-- 2025-11-13T13:42:27.838Z
UPDATE C_OLCandAggAndOrder SET OrderBySeqNo=190,Updated=TO_TIMESTAMP('2025-11-13 13:42:27.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540027
;

