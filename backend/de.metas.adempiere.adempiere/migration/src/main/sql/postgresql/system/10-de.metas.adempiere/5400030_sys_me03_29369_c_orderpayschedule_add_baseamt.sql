-- 2026-04-27 https://github.com/metasfresh/me03/issues/29369
-- Iter 3: BaseAmt column on C_OrderPaySchedule. Generalises iter-2's invariant
--         from `DueAmt = order.GrandTotal × break%` to `DueAmt = BaseAmt × break%`.
--         For iter-2 (LC + single-Delivery rows) BaseAmt = order.GrandTotal — backfill matches existing math.
--         For iter-3 Delivery sub-rows, BaseAmt = receipt.with_tax (set by OrderPayScheduleDeliveryService at write time).
--         Invariant DueAmt = round(BaseAmt × break%, 2) is enforced by the Java sole-writer
--         (Phase 3 OrderPayScheduleSaver) — NOT a DB CHECK (PG CHECK with subquery on
--         C_PaymentTerm_Break does not re-evaluate cross-row; see ai-work/29369/pending-questions.md Q1).

/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE C_OrderPaySchedule ADD COLUMN BaseAmt numeric DEFAULT NULL');

-- Backfill: BaseAmt = order.GrandTotal for every existing row (iter-2 single-Delivery semantics).
-- Pre-iter-3 audit confirmed 0/98 drift (ai-work/29369/pending-questions.md "Pre-iter-3 audit").
UPDATE C_OrderPaySchedule ops
SET BaseAmt = o.GrandTotal
FROM C_Order o
WHERE o.C_Order_ID = ops.C_Order_ID AND ops.BaseAmt IS NULL;

-- Make NOT NULL after backfill.
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE C_OrderPaySchedule ALTER COLUMN BaseAmt SET NOT NULL');
