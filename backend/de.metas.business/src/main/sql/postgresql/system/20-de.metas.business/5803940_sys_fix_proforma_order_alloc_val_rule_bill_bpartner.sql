-- Fix the BPartner condition of the AD_Val_Rule that filters orders eligible for
-- proforma allocation, so it agrees with ProformaOrderAllocateCommand.validate():
-- the Java check compares the proforma invoice's C_BPartner_ID against the order's
-- *effective bill partner* (OrderBL.getEffectiveBillPartnerId =
--   COALESCE(NULLIF(Bill_BPartner_ID,0), C_BPartner_ID)), so the Val Rule must
-- use the same expression. Otherwise, orders with Bill_BPartner_ID set to a
-- different partner from C_BPartner_ID are invisible in the C_Order_ID lookup
-- even though the server-side validation would accept them.
--
-- Audit (2026-05-21) confirmed AD_Val_Rule_ID=540777 has a single callsite —
-- AD_Process_Para 543178 (C_Invoice_Proforma_Allocate_Order.C_Order_ID) — so the
-- rule's scope (single field, single intent) is preserved; this is a pure bug
-- fix per the AD_Val_Rule scope-preservation review rule in the
-- metasfresh-application-dictionary skill.
--
-- NOTE: subselect keywords (select/from/join/where) are intentionally LOWERCASE.
-- de.metas.security.impl.ParsedSql#parse splits on uppercase " FROM "/" WHERE "
-- and de.metas.security.impl.UserRolePermissionsSqlHelpers#buildAccessSQL uses
-- the parsed alias to compose the security-record subquery. Uppercase subselect
-- keywords here fool the alias extractor and produce SQL like
--     Record_ID=WHERE.C_Order_ID
-- which fails with "syntax error at or near WHERE". Lowercased keywords avoid it.

UPDATE AD_Val_Rule
SET Code =
        $$
            C_Order.DocStatus = 'CO'
            AND C_Order.IsSOTrx = 'N'
            AND COALESCE(NULLIF(C_Order.Bill_BPartner_ID,0), C_Order.C_BPartner_ID) = (select C_BPartner_ID from C_Invoice where C_Invoice_ID = @C_Invoice_ID/0@)
            AND C_Order.C_Currency_ID = (select C_Currency_ID from C_Invoice where C_Invoice_ID = @C_Invoice_ID/0@)
            AND EXISTS ( select 1 from C_OrderPaySchedule ops
                join C_PaymentTerm_Break ptb on ptb.C_PaymentTerm_Break_ID = ops.C_PaymentTerm_Break_ID
                where ops.C_Order_ID = C_Order.C_Order_ID AND ptb.ReferenceDateType = 'LC' )
            AND NOT EXISTS ( select 1 from C_Proforma_Order_Alloc poa
                where poa.C_Order_ID = C_Order.C_Order_ID AND poa.IsActive = 'Y' )
        $$,
    Updated = TO_TIMESTAMP('2026-05-21 18:30:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540777
;
