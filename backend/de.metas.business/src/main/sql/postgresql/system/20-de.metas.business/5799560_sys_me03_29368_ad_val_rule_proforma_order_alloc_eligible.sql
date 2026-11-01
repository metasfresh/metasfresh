-- 2026-04-24 https://github.com/metasfresh/me03/issues/29368
-- AD_Val_Rule: filter C_Order_ID parameter on the proforma allocation process
-- so that only orders eligible for LC-break allocation are offered in the lookup.
--
-- NOTE: subselect keywords (select/from/join/where) are intentionally LOWERCASE.
-- de.metas.security.impl.ParsedSql#parse splits on uppercase " FROM "/" WHERE "
-- and de.metas.security.impl.UserRolePermissionsSqlHelpers#buildAccessSQL uses
-- the parsed alias to compose the security-record subquery. Uppercase subselect
-- keywords here fool the alias extractor and produce SQL like
--     Record_ID=WHERE.C_Order_ID
-- which fails with "syntax error at or near WHERE". Lowercased keywords avoid it.

INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         Name, Type, Code, EntityType)
VALUES (540777 /*From ID Server*/, 0, 0, 'Y', '2026-04-24 21:00', 0, '2026-04-24 21:00', 0,
        'C_Invoice_Proforma_Allocate_Order.C_Order_ID.Eligible', 'S',
        $$
            C_Order.DocStatus = 'CO'
            AND C_Order.IsSOTrx = 'N'
            AND C_Order.C_BPartner_ID = (select C_BPartner_ID from C_Invoice where C_Invoice_ID = @C_Invoice_ID/0@)
            AND C_Order.C_Currency_ID = (select C_Currency_ID from C_Invoice where C_Invoice_ID = @C_Invoice_ID/0@)
            AND EXISTS ( select 1 from C_OrderPaySchedule ops
                join C_PaymentTerm_Break ptb on ptb.C_PaymentTerm_Break_ID = ops.C_PaymentTerm_Break_ID
                where ops.C_Order_ID = C_Order.C_Order_ID AND ptb.ReferenceDateType = 'LC' )
            AND NOT EXISTS ( select 1 from C_Proforma_Order_Alloc poa
                where poa.C_Order_ID = C_Order.C_Order_ID AND poa.IsActive = 'Y' )
        $$,
        'D')
;

-- Attach the new Val Rule to the C_Order_ID parameter on the allocation process.
-- Previously AD_Val_Rule_ID=540776 (broader filter); this replaces it with the stricter
-- eligibility rule that enforces currency + vendor + LC-break conditions at lookup time.
UPDATE AD_Process_Para
SET AD_Val_Rule_ID = 540777 /*From ID Server*/,
    Updated = '2026-04-24 21:00', UpdatedBy = 0
WHERE AD_Process_ID = (SELECT AD_Process_ID FROM AD_Process WHERE ClassName = 'de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order')
  AND ColumnName    = 'C_Order_ID'
;
