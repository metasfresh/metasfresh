
/*
 * #%L
 * de.metas.async
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

-- 2021-08-06T13:54:12.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540054,TO_TIMESTAMP('2021-08-06 16:54:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'C_OLCandToOrderWorkpackageProcessor',10,TO_TIMESTAMP('2021-08-06 16:54:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T13:54:20.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Priority='1',Updated=TO_TIMESTAMP('2021-08-06 16:54:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540054
;

-- 2021-08-06T13:54:55.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540066,540090,540054,TO_TIMESTAMP('2021-08-06 16:54:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-08-06 16:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T13:55:11.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET KeepAliveTimeMillis=1000,Updated=TO_TIMESTAMP('2021-08-06 16:55:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540054
;

-- 2021-08-06T13:55:16.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Priority=NULL,Updated=TO_TIMESTAMP('2021-08-06 16:55:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540054
;

-- 2021-08-06T13:55:19.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET PoolSize=1,Updated=TO_TIMESTAMP('2021-08-06 16:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540054
;

-- 2021-08-06T13:56:19.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Queue_Processor SET Description='Contains the processors that process an order candidate to an order;',Updated=TO_TIMESTAMP('2021-08-06 16:56:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Queue_Processor_ID=540054
;

