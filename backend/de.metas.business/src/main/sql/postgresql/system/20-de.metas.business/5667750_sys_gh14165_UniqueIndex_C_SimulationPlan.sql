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

-- 2022-12-08T15:48:04.474Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540716,0,542173,TO_TIMESTAMP('2022-12-08 17:48:04','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on active unprocessed main simulation','D','Y','Y','IDX_C_SimulationPlan_Unique_MainSimulation','N',TO_TIMESTAMP('2022-12-08 17:48:04','YYYY-MM-DD HH24:MI:SS'),100,'IsMainSimulation=''Y'' AND IsActive=''Y'' AND Processed=''N''')
;

-- 2022-12-08T15:48:04.485Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540716 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-12-08T15:48:17.598Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,585277,541287,540716,0,TO_TIMESTAMP('2022-12-08 17:48:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-12-08 17:48:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T15:48:24.865Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583493,541288,540716,0,TO_TIMESTAMP('2022-12-08 17:48:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2022-12-08 17:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T15:48:32.965Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583498,541289,540716,0,TO_TIMESTAMP('2022-12-08 17:48:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',30,TO_TIMESTAMP('2022-12-08 17:48:32','YYYY-MM-DD HH24:MI:SS'),100)
;

DROP INDEX IF EXISTS IDX_C_SimulationPlan_Unique_MainSimulation
;

-- 2022-12-08T15:48:41.765Z
CREATE UNIQUE INDEX IDX_C_SimulationPlan_Unique_MainSimulation ON C_SimulationPlan (IsMainSimulation,IsActive,Processed) WHERE IsMainSimulation='Y' AND IsActive='Y' AND Processed='N'
;

