-- 2026-04-24 https://github.com/metasfresh/me03/issues/29368
-- Guard the iter-2 invariant "at most 1 active allocation per order" at the DB level.
-- Multi-proforma-per-order is iter-3+ out-of-scope (REQUIREMENTS.md §7).
CREATE UNIQUE INDEX C_Proforma_Order_Alloc_UNQ_ActiveOrder
    ON C_Proforma_Order_Alloc (C_Order_ID)
    WHERE IsActive = 'Y';
