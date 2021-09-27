
/*
 * #%L
 * de.metas.salescandidate.base
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

-- 2021-08-05T13:29:51.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_OLCandAggAndOrder (AD_Client_ID,AD_Column_OLCand_ID,AD_Org_ID,C_OLCandAggAndOrder_ID,C_OLCandProcessor_ID,Created,CreatedBy,GroupBy,IsActive,SplitOrder,Updated,UpdatedBy) VALUES (1000000,575254,1000000,540016,1000003,TO_TIMESTAMP('2021-08-05 16:29:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','N',TO_TIMESTAMP('2021-08-05 16:29:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-05T13:29:58.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OLCandAggAndOrder SET OrderBySeqNo=190,Updated=TO_TIMESTAMP('2021-08-05 16:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_OLCandAggAndOrder_ID=540016
;

