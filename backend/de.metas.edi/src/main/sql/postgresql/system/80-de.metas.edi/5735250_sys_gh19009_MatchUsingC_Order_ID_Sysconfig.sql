/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- 2024-09-27T15:45:41.854Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541741,'O',TO_TIMESTAMP('2024-09-27 15:45:41.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','de.metas.edi.desadv.MatchUsingC_Order_ID',TO_TIMESTAMP('2024-09-27 15:45:41.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- 2024-09-27T15:58:46.414Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='Y - Two different Orders result in two different Desadvs (independently of the Business Partner, POReference and Sysconfig de.metas.edi.desadv.MatchUsingC_BPartner_ID value. When enabling this Sysconfig, make sure that the shipment schedule aggregation header has the Order ID set. In this way we make sure that we cannot have one shipment for two different orders.',Updated=TO_TIMESTAMP('2024-09-27 15:58:46.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541741
;

-- 2024-09-27T16:10:05.095Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Y - Two different Business Partners and the same POReference result in two different EDI Desadvs (independently of de.metas.edi.desadv.MatchUsingC_Order_ID)',Updated=TO_TIMESTAMP('2024-09-27 16:10:05.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541647
;

