-- Fix the "C_Order(Quotation) -> C_Order(SO)" relation type (AD_RelationType 540260,
-- target reference AD_Reference 541184): from a quotation/proposal, "Related Documents"
-- must list the sales order(s) created from it, NOT the quotation itself.
--
-- Regression history:
--   * gh9969  (5567990, 2020-09) created the relation with the CORRECT where-clause
--             ( o.Ref_Proposal_ID = @C_Order_ID@ ): the created order points back to the
--             quotation via Ref_Proposal_ID.
--   * gh10718 (5580630, 2021-03) — an UNRELATED "Frame Agreement / Order Call doctypes"
--             migration — collaterally overwrote it to a self/same-BPartner match
--             ( o.C_DocType_ID=<x> AND C_Order.C_Order_ID = o.C_Order_ID AND @C_BPartner_ID@=o.C_BPartner_ID ).
--   * gh11822 (5610770, 2021-11) changed the hard-coded doctype but kept the broken
--             self/same-BPartner structure.
-- Result since 2021: the relation lists the customer's other quotations (incl. the record
-- itself) instead of the created order. The reverse direction (541185, SO -> Quotation,
-- via Ref_Proposal_ID) was never touched and is correct; the SO<->PO relations are correct.
-- This change restores ONLY 541184 to gh9969's linkage clause; no other relation is touched.

UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.Ref_Proposal_ID=@C_Order_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID)',Updated=TO_TIMESTAMP('2026-06-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541184
;
