-- Fix: all three status tabs over M_Picking_OrderBoard_v are peers (same flat view, no FK between them).
-- TabLevel=1 without Parent_Column_ID on a flat aggregation view produces unconstrained/broken child tabs.
-- All three tabs must remain TabLevel=0; they are differentiated solely by their WhereClause.

-- Tab: Auftrags-Board(581036,D) -> Wartend
-- Table: M_Picking_OrderBoard_v
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2026-07-02 13:45:45.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=581037
;

-- Tab: Auftrags-Board(581036,D) -> In Kommissionierung
-- Table: M_Picking_OrderBoard_v
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2026-07-02 13:46:05.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=581038
;

-- Tab: Auftrags-Board(581036,D) -> Packen
-- Table: M_Picking_OrderBoard_v
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2026-07-02 13:46:25.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=581039
;

