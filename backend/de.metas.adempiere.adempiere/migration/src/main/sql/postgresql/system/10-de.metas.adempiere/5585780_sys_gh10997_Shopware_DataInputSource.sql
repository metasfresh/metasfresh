/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2021 metas GmbH
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

-- 2021-04-16T16:31:41.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_InputDataSource (AD_Client_ID,AD_InputDataSource_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDestination,IsEdiEnabled,Name,Updated,UpdatedBy,Value) VALUES (1000000,540217,1000000,TO_TIMESTAMP('2021-04-16 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','Shopware',TO_TIMESTAMP('2021-04-16 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Shopware')
;

UPDATE AD_InputDataSource SET InternalName = 'Shopware' where AD_InputDataSource_ID = 540217
;