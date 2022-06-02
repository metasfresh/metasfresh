/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- CREATE AND ASSIGN VALIDATION RULE
-- 2022-05-13T13:01:08.435Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540579,'C_Project.AD_Org_ID in (@AD_Org_ID/-1@, 0)',TO_TIMESTAMP('2022-05-13 15:01:08','YYYY-MM-DD HH24:MI:SS'),100,'Select projects that correspond with the selected organisation','D','Y','C_Project_from_organsiation','S',TO_TIMESTAMP('2022-05-13 15:01:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-13T13:01:36.771Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540579,Updated=TO_TIMESTAMP('2022-05-13 15:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3459
;

-- 2022-05-13T13:02:40.388Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540579,Updated=TO_TIMESTAMP('2022-05-13 15:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=668767
;