/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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
    
-- change from Fact_Acct to Fact_Acct_Transactions_View
-- without the fix there will be an SQL-error when switching to baselang de_CH, because there is already another AD_Reference_Trl with the name 'Fact_Acct'
UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='Fact_Acct_Transactions_View'
WHERE AD_Reference_ID = 540773  AND AD_Language = 'de_CH';

-- same for another trl
UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='Fact_Acct_Transactions_View'
WHERE AD_Reference_ID = 540773  AND AD_Language = 'it_CH';

UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='R_Request Due Type'
WHERE AD_Reference_ID = 222 AND AD_Language = 'fr_CH';

UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='C_Invoice_Candidate Purchase'
WHERE AD_Reference_ID = 541234 AND AD_Language = 'fr_CH';

UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='ArticleUnit'
WHERE AD_Reference_ID = 541280 AND AD_Language = 'fr_CH';

UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='ItemName'
WHERE AD_Reference_ID = 540735 AND AD_Language = 'fr_CH';

UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='C_Queue_WorkPackage'
WHERE AD_Reference_ID = 540645 AND AD_Language = 'fr_CH';

UPDATE AD_Reference_Trl
SET Updated='2025-09-22 14:43', UpdatedBy=99, Name='C_Queue_Workpackage'
WHERE AD_Reference_ID = 540527 AND AD_Language = 'fr_CH';


-- Index will be created in 5770380_sys_fix_AD_Reference_Trls.sql
-- CREATE UNIQUE INDEX IF NOT EXISTS AD_Reference_Trl_Name ON AD_Reference_Trl (AD_Language, Name);
-- COMMENT ON INDEX AD_Reference_Trl_Name IS 'Needed because It''s no enought to have a UC on AD_Reference-Nname. We also need to make sure that we don''t run into unique constraint errors when switching the base-language'
-- ;

-- If the index-creation fails on some DBs, you can use this select to identify the problematic AD_Reference_Trl records.
/*
SELECT base.ad_reference_id,
       base.name as ad_reference_name,
       trl.name as trl_name,
       trl.ad_language,
       trl2.name as trl2_name,
       trl2.ad_language,
       base2.ad_reference_id,
       base2.name as ad_reference2_name
FROM ad_reference base
         JOIN ad_reference_trl trl ON base.ad_reference_id = trl.ad_reference_id
         JOIN ad_reference_trl trl2 ON trl.ad_language = trl2.ad_language AND trl.name = trl2.name AND trl.ad_reference_id > trl2.ad_reference_id
         JOIN ad_reference base2 ON base2.ad_reference_id = trl2.ad_reference_id;
*/