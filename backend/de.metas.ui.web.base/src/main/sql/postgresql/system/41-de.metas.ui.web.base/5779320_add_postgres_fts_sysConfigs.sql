/*
 * #%L
 * de.metas.ui.web.base
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

-- Run mode: SWING_CLIENT

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled
-- SysConfig Value: N
-- 2025-12-03T12:04:36.025Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541776,'S',TO_TIMESTAMP('2025-12-03 12:04:35.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Postgres FTS feature activation. ops.update_c_bpartner_fts_if_active() needs to be run after change!','D','Y','de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterDescriptorsProviderFactory.enabled',TO_TIMESTAMP('2025-12-03 12:04:35.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance
-- SysConfig Value: 0.96
-- 2025-12-03T12:48:37.113Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541777,'S',TO_TIMESTAMP('2025-12-03 12:48:36.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,' Sets the tolerance for the (trigram) fuzzy search, which helps find matches even when the user makes a typo. `pg_trgm`
 - **How it Works:** This value defines the **maximum allowed "distance"** between the user''s search term and the indexed text. Distance measures dissimilarity, where `0.0` is a perfect match and `1.0` is no match at all.
 - **The Challenge:** Because a user''s search term (e.g., "John Smith") is much shorter than the full indexed text it''s compared against, the distance for even a perfect match will naturally be very high (e.g. `0.96`).
 - **Recommendation:** You must set this threshold to a similarly **high value** to allow these matches to be found.
     - A threshold of `0.7` will find only very close matches.
     - A threshold of `0.96` is more lenient and will find matches with more significant typos or variations.',
'D','Y','de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance',TO_TIMESTAMP('2025-12-03 12:48:36.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'0.96')
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit
-- SysConfig Value: 5000
-- 2025-12-04T09:43:22.433Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541778,'S',TO_TIMESTAMP('2025-12-04 09:43:22.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Controls the maximum number of typo-tolerant matches to consider during the initial phase of a search.
**How it Works:** When a user searches, the system runs a "fuzzy" search in parallel to find results with potential typos. This setting limits how many of these potential matches are retrieved from the database for further processing.
**Important:** This limit is applied  before any other filters (such as security or business-specific view filters). If a record is not within this initial set of fuzzy matches, it will be excluded from the final results, even if the user would otherwise have access to it.
**The Trade-off:**
- A **higher value** increases the chance of finding a result despite significant typos, but may impact performance on very large systems.
- A **lower value** is faster but might miss relevant results if the user''s search term is very different from the indexed text.

This limit only applies when the fuzzy search feature is enabled (i.e., when the `Distance` threshold is configured to a value less than 1).
','D','Y','de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit',TO_TIMESTAMP('2025-12-04 09:43:22.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1000')
;
