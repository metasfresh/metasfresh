-- M_ShipperTransportation.SalesRep_ID lookup must not depend on @IsSOTrx@
--
-- WHY
--   5820850_sys_M_ShipperTransportation_Drop_IsSOTrx.sql dropped M_ShipperTransportation.IsSOTrx and
--   replaced it with TransportDirection. The SalesRep_ID lookup still resolved through AD_Reference
--   190, whose WhereClause is
--       (AD_User.IsSystemUser = 'Y' OR '@IsSOTrx@'='N')
--   so the context variable it names no longer exists and WindowHealthCheckCommand reports both
--   M_ShipperTransportation windows (540020, 541657) as NOK. The cleanup of that change was
--   incomplete: the meaning of the flag moved to TransportDirection, the lookup did not follow.
--
-- WHAT THIS DOES
--   Introduces an AD_User table reference whose restriction is the TransportDirection equivalent of
--   the old clause, and repoints ONLY M_ShipperTransportation.SalesRep_ID (AD_Column 551101) at it.
--
--   The equivalence is not invented here — it is the one 5821070_..._catchup_backfill.sql used to
--   migrate the data:
--       IsSOTrx = 'N' (purchase) -> 'Incoming', or 'Dropship' when all orders are drop-ship
--       IsSOTrx = 'Y' (sales)    -> 'Outgoing'
--   so  '@IsSOTrx@'='N'  becomes  '@TransportDirection@' IN ('Incoming','Dropship').
--   Behaviour is therefore preserved: system users always, plus every user on an incoming or
--   drop-ship transport.
--
-- WHAT THIS DELIBERATELY DOES *NOT* DO
--   * AD_Reference 190 is left untouched. Many other columns still resolve through it, and they all
--     fall into one of two harmless groups:
--       - the table HAS IsSOTrx (C_Invoice, C_Order, M_InOut, M_RMA, I_Invoice, I_Order,
--         C_Invoice_Candidate) -> @IsSOTrx@ resolves normally, nothing to fix;
--       - the table lacks IsSOTrx but its windows are listed in
--         MissingContextVariables.KNOWN_MISSING_CONTEXT_VARIABLES_IN_LOOKUPS (C_Project 130/286/
--         540668/540680/541015, M_Product 140/344/53010/540410, C_RfQ 315, M_Movement 170,
--         C_SalesRegion 152, W_Store 350, M_Material_Tracking 540226, C_Phonecall_Schedule 540607,
--         RV_R_Group_Prospect 540013, ... 39 SalesRep_ID entries in total) -> the health check
--         deliberately suppresses the warning there.
--     M_ShipperTransportation is the only column in NEITHER group, which is exactly why windows
--     540020 and 541657 are the only two reported NOK. Repointing this one column therefore closes
--     the whole defect without touching AD_Reference 190 or the allowlist.
--   * IsAutoApplyValidationRule on AD_Column 551101 is not changed. The sibling C_BPartner fix set it
--     to 'Y' in 5787080_c_bpartner_sales_rep_fix_LookupDescriptor.sql and reverted it to 'N' in
--     5787160_c_bpartner_sales_rep_fix_LookupDescriptor2.sql; 'N' is the corrected end state.
--   * It does not repoint the column at AD_Reference 540401 ('AD_User.IsSystemUser = ''Y''').
--     That would make the health check pass by DELETING the second arm of the condition, narrowing
--     the picker on incoming/drop-ship transports from every user to system users only.
--
--   AD_Ref_Table's presentation columns (AD_Key, AD_Display, IsValueDisplayed, OrderByClause,
--   AD_Window_ID) are SELECTed from AD_Reference 190's own row rather than hardcoded, so the new
--   reference renders identically to the old one on every instance.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_Reference     542139 (AD_User - SalesRep systemuser or incoming transport)
--   AD_MigrationScript prefix 5823430

-- 2026-09-09 12:52:39
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542139 /*From ID Server*/,TO_TIMESTAMP('2026-09-09 12:52:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_User - SalesRep systemuser or incoming transport',TO_TIMESTAMP('2026-09-09 12:52:39','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2026-09-09 12:52:39
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=542139 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2026-09-09 12:52:39
-- Presentation copied from AD_Reference 190's row; only the WhereClause differs.
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Key,AD_Display,AD_Window_ID,IsValueDisplayed,OrderByClause,WhereClause,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy)
SELECT rt.AD_Client_ID,
       rt.AD_Org_ID,
       542139 /*From ID Server*/,
       rt.AD_Table_ID,
       rt.AD_Key,
       rt.AD_Display,
       rt.AD_Window_ID,
       rt.IsValueDisplayed,
       rt.OrderByClause,
       '(AD_User.IsSystemUser = ''Y'' OR ''@TransportDirection@'' IN (''Incoming'',''Dropship''))',
       TO_TIMESTAMP('2026-09-09 12:52:40','YYYY-MM-DD HH24:MI:SS'),
       100,
       'D',
       'Y',
       TO_TIMESTAMP('2026-09-09 12:52:40','YYYY-MM-DD HH24:MI:SS'),
       100
FROM   AD_Ref_Table rt
WHERE  rt.AD_Reference_ID = 190
;

-- 2026-09-09 12:52:39
-- Column: M_ShipperTransportation.SalesRep_ID
UPDATE AD_Column SET AD_Reference_Value_ID=542139 /*From ID Server*/,Updated=TO_TIMESTAMP('2026-09-09 12:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551101
;
