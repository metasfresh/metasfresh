/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- 2023-05-04T15:48:26.182981Z
INSERT INTO M_PricingSystem (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsSubscriptionOnly,M_PricingSystem_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2023-05-04 18:48:26.169','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N',540092,'Testpreisliste Beistellung',TO_TIMESTAMP('2023-05-04 18:48:26.169','YYYY-MM-DD HH24:MI:SS.US'),100,'1000008')
;

-- 2023-05-04T15:48:38.910225300Z
UPDATE M_PricingSystem SET Value='Testpreise Beistellung',Updated=TO_TIMESTAMP('2023-05-04 18:48:38.909','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE M_PricingSystem_ID=540092
;

-- SysConfig Name: de.metas.project.workorder.undertest.ProvisionPricingSystem
-- SysConfig Value: 2000839
-- 2023-05-04T15:46:19.668836Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541612,'S',TO_TIMESTAMP('2023-05-04 18:46:19.478','YYYY-MM-DD HH24:MI:SS.US'),100,'The pricing system used for orders having the document type material provision.','D','Y','de.metas.project.workorder.undertest.ProvisionPricingSystem',TO_TIMESTAMP('2023-05-04 18:46:19.478','YYYY-MM-DD HH24:MI:SS.US'),100,'2000839')
;

-- SysConfig Name: de.metas.project.workorder.undertest.ProvisionPricingSystem
-- SysConfig Value: 540092
-- SysConfig Value (old): 2000839
-- 2023-05-04T15:49:07.898476600Z
UPDATE AD_SysConfig SET ConfigurationLevel='0', Value='540092',Updated=TO_TIMESTAMP('2023-05-04 18:49:07.898','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_SysConfig_ID=541612
;