-- Backfill the Cost Revaluation (DocBaseType 'CRD') C_PeriodControl for existing periods.
--
-- WHY: C_PeriodControl rows are created only at period-creation time (PeriodBL.createPeriodControls),
--      and only for the DocBaseTypes that have a C_DocType at that moment. The 'CRD' DocBaseType was
--      introduced later, so C_Periods created before it have NO 'CRD' control row. MPeriod.isOpen then
--      returns false ("Period Control not found for CRD") and any Cost Revaluation document fails to
--      post with "Period closed" — even when the period is open for every other DocBaseType.
--
-- WHAT: add a 'CRD' C_PeriodControl to every C_Period that has none. Status mirrors the maintained
--       add_missing_period_controls() backfill: use the period's MOST-COMMON PeriodAction / PeriodStatus /
--       Processing (fallback NoAction / NeverOpened / N). So an open period's CRD control comes out Open
--       (postable) while a closed period's stays Closed — each period keeps its existing posting posture.
--       (CRD-only + self-contained on purpose: right-sized for a hotfix; add_missing_period_controls()
--       covers all DocBaseTypes on newer branches, where this becomes an idempotent no-op for CRD.)
--
-- IDEMPOTENT: only active periods with NO 'CRD' control (active OR inactive) receive a row, so running
--             this script multiple times has no effect. A pre-existing 'CRD' control is left untouched.

DO $$
DECLARE
    r        RECORD;
    v_id     numeric(10);
    v_count  integer := 0;
BEGIN
    FOR r IN
        SELECT p.C_Period_ID,
               p.AD_Client_ID,
               COALESCE(mc.periodaction, 'N') AS periodaction,
               COALESCE(mc.periodstatus, 'N') AS periodstatus,
               COALESCE(mc.processing,   'N') AS processing
        FROM   C_Period p
        LEFT   JOIN LATERAL (
                   SELECT MODE() WITHIN GROUP (ORDER BY pc.periodaction) AS periodaction,
                          MODE() WITHIN GROUP (ORDER BY pc.periodstatus) AS periodstatus,
                          MODE() WITHIN GROUP (ORDER BY pc.processing)   AS processing
                   FROM   C_PeriodControl pc
                   WHERE  pc.C_Period_ID = p.C_Period_ID
               ) mc ON TRUE
        WHERE  p.IsActive = 'Y'
          AND  NOT EXISTS (
                   SELECT 1
                   FROM   C_PeriodControl pc
                   WHERE  pc.C_Period_ID = p.C_Period_ID
                     AND  pc.DocBaseType = 'CRD'
               )
    LOOP
        v_id := nextidfunc(229, 'N');   -- 229 = AD_Table_ID of C_PeriodControl (sanctioned ID allocator, not nextval)
        INSERT INTO C_PeriodControl (
            C_PeriodControl_ID, AD_Client_ID, AD_Org_ID, C_Period_ID, DocBaseType,
            PeriodStatus, PeriodAction, Processing, IsActive,
            Created, CreatedBy, Updated, UpdatedBy
        ) VALUES (
            v_id, r.AD_Client_ID, 0, r.C_Period_ID, 'CRD',
            r.periodstatus, r.periodaction, r.processing, 'Y',
            TO_TIMESTAMP('2026-08-27 10:00:00','YYYY-MM-DD HH24:MI:SS'), 99,
            TO_TIMESTAMP('2026-08-27 10:00:00','YYYY-MM-DD HH24:MI:SS'), 99
        );
        v_count := v_count + 1;
    END LOOP;

    RAISE NOTICE 'Backfilled % CRD C_PeriodControl row(s).', v_count;
END $$;
