/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- 2022-12-14T14:55:31.987Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583490,541291,540716,0,TO_TIMESTAMP('2022-12-14 16:55:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',40,TO_TIMESTAMP('2022-12-14 16:55:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-14T14:55:40.481Z
DROP INDEX IF EXISTS idx_c_simulationplan_unique_mainsimulation
;

-- 2022-12-14T14:55:40.496Z
CREATE UNIQUE INDEX IDX_C_SimulationPlan_Unique_MainSimulation ON C_SimulationPlan (IsMainSimulation,IsActive,Processed,AD_Org_ID) WHERE IsMainSimulation='Y' AND IsActive='Y' AND Processed='N'
;

