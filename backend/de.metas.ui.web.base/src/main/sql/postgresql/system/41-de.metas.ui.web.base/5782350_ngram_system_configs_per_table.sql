-- Run mode: SWING_CLIENT

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.partner
-- SysConfig Value: 0.96
-- 2025-12-19T07:54:57.195Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.partner',Updated=TO_TIMESTAMP('2025-12-19 07:54:57.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541777
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.product
-- SysConfig Value: 0.96
-- 2025-12-19T07:55:34.070Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541784,'S',TO_TIMESTAMP('2025-12-19 07:55:33.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,' Sets the tolerance for the (trigram) fuzzy search, which helps find matches even when the user makes a typo. `pg_trgm`
 - **How it Works:** This value defines the **maximum allowed "distance"** between the user''s search term and the indexed text. Distance measures dissimilarity, where `0.0` is a perfect match and `1.0` is no match at all.
 - **The Challenge:** Because a user''s search term (e.g., "John Smith") is much shorter than the full indexed text it''s compared against, the distance for even a perfect match will naturally be very high (e.g. `0.96`).
 - **Recommendation:** You must set this threshold to a similarly **high value** to allow these matches to be found.
     - A threshold of `0.7` will find only very close matches.
     - A threshold of `0.96` is more lenient and will find matches with more significant typos or variations.','D','Y','de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.product',TO_TIMESTAMP('2025-12-19 07:55:33.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'0.96')
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.invoice
-- SysConfig Value: 0.96
-- 2025-12-19T07:56:16.674Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541785,'S',TO_TIMESTAMP('2025-12-19 07:56:16.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,' Sets the tolerance for the (trigram) fuzzy search, which helps find matches even when the user makes a typo. `pg_trgm`
 - **How it Works:** This value defines the **maximum allowed "distance"** between the user''s search term and the indexed text. Distance measures dissimilarity, where `0.0` is a perfect match and `1.0` is no match at all.
 - **The Challenge:** Because a user''s search term (e.g., "John Smith") is much shorter than the full indexed text it''s compared against, the distance for even a perfect match will naturally be very high (e.g. `0.96`).
 - **Recommendation:** You must set this threshold to a similarly **high value** to allow these matches to be found.
     - A threshold of `0.7` will find only very close matches.
     - A threshold of `0.96` is more lenient and will find matches with more significant typos or variations.','D','Y','de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.Distance.invoice',TO_TIMESTAMP('2025-12-19 07:56:16.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'0.99')
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.partner
-- SysConfig Value: 5000
-- 2025-12-19T07:57:05.244Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Name='de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.partner',Updated=TO_TIMESTAMP('2025-12-19 07:57:05.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541778
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.product
-- SysConfig Value: 1000
-- 2025-12-19T07:57:56.984Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541786,'S',TO_TIMESTAMP('2025-12-19 07:57:56.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Controls the maximum number of typo-tolerant matches to consider during the initial phase of a search.
**How it Works:** When a user searches, the system runs a "fuzzy" search in parallel to find results with potential typos. This setting limits how many of these potential matches are retrieved from the database for further processing.
**Important:** This limit is applied  before any other filters (such as security or business-specific view filters). If a record is not within this initial set of fuzzy matches, it will be excluded from the final results, even if the user would otherwise have access to it.
**The Trade-off:**
- A **higher value** increases the chance of finding a result despite significant typos, but may impact performance on very large systems.
- A **lower value** is faster but might miss relevant results if the user''s search term is very different from the indexed text.

This limit only applies when the fuzzy search feature is enabled (i.e., when the `Distance` threshold is configured to a value less than 1).
','D','Y','de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.product',TO_TIMESTAMP('2025-12-19 07:57:56.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1000')
;

-- SysConfig Name: de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.invoice
-- SysConfig Value: 1000
-- 2025-12-19T07:58:41.576Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541787,'S',TO_TIMESTAMP('2025-12-19 07:58:41.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Controls the maximum number of typo-tolerant matches to consider during the initial phase of a search.
**How it Works:** When a user searches, the system runs a "fuzzy" search in parallel to find results with potential typos. This setting limits how many of these potential matches are retrieved from the database for further processing.
**Important:** This limit is applied  before any other filters (such as security or business-specific view filters). If a record is not within this initial set of fuzzy matches, it will be excluded from the final results, even if the user would otherwise have access to it.
**The Trade-off:**
- A **higher value** increases the chance of finding a result despite significant typos, but may impact performance on very large systems.
- A **lower value** is faster but might miss relevant results if the user''s search term is very different from the indexed text.

This limit only applies when the fuzzy search feature is enabled (i.e., when the `Distance` threshold is configured to a value less than 1).
','D','Y','de.metas.ui.web.document.filter.provider.fullTextSearch.PostgresFTSDocumentFilterConverter.NgramLimit.invoice',TO_TIMESTAMP('2025-12-19 07:58:41.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1000')
;
