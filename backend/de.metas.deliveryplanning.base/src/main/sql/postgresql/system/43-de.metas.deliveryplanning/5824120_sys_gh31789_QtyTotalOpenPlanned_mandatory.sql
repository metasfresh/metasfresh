-- M_Delivery_Planning.QtyTotalOpenPlanned becomes mandatory with a 0 default, matching the siblings it was
-- always meant to sit beside.
--
-- 5822040 added it as AD METADATA ONLY and said so: "no maintenance/interceptor is added here, so the column
-- starts and stays NULL until a later change makes the figure live. That is why it is nullable with no
-- default, unlike its stored, already-mandatory siblings QtyOrdered/QtyTotalOpen."
--
-- That later change has since landed: recomputeOpenQuantitiesForOrderLine writes this figure together with
-- QtyTotalOpen, from one openTotals() call, so the two are produced and stored as a pair. The reason for the
-- nullability has therefore expired, and the column is now the odd one out - PlannedDischargeQuantity and
-- ActualDischargeQuantity both carry DEFAULT 0 and NOT NULL.
--
-- 0 is the correct default rather than a filler: the figure is "how much of the order line nobody has planned
-- yet", and a planning is created covering the full quantity, so at creation nothing of the line is open and
-- unplanned. That is also why generateDeliveryPlanning seeds QtyTotalOpen and needs no separate seed here -
-- the default expresses the same fact.
--
-- Safe to enforce: 0 of 6224 rows on the deep_tundra stack are null, and the DEFAULT is applied before the
-- NOT NULL so any row the AFTER_NEW recompute has not reached yet - including a planning with no order line,
-- where that recompute returns early - carries 0 rather than blocking the migration.

UPDATE M_Delivery_Planning SET QtyTotalOpenPlanned = 0 WHERE QtyTotalOpenPlanned IS NULL
;

/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ALTER COLUMN QtyTotalOpenPlanned SET DEFAULT 0')
;

/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ALTER COLUMN QtyTotalOpenPlanned SET NOT NULL')
;

UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-09-11 02:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593466
;
