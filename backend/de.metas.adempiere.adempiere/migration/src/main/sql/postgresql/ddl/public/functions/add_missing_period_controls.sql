
DROP FUNCTION IF EXISTS add_missing_period_controls();

-- Function: add_missing_period_controls
-- Purpose: Creates period controls for any new docBaseTypes that don't have period controls yet
-- Based on: de.metas.calendar.impl.PeriodBL.createPeriodControls and org.compiere.process.DocumentTypeVerify.createPeriodControls
CREATE OR REPLACE FUNCTION add_missing_period_controls()
    RETURNS TABLE
            (
                c_period_id     numeric,
                docbasetype     char(3),
                periods_created int
            )
AS
$$
BEGIN
    -- Insert period controls for missing docBaseTypes per period
    -- Uses the most common PeriodAction, PeriodStatus, and Processing values from existing controls in the same period
    -- Returns the inserted records directly
    RETURN QUERY
        WITH inserted_records AS (
            INSERT INTO c_periodcontrol (c_periodcontrol_id,
                                         ad_client_id,
                                         ad_org_id,
                                         c_period_id,
                                         docbasetype,
                                         isactive,
                                         periodaction,
                                         periodstatus,
                                         processing,
                                         created,
                                         createdby,
                                         updated,
                                         updatedby)
                SELECT NEXTVAL('c_periodcontrol_seq'),
                       p.ad_client_id,
                       0,                                       -- AD_Org_ID = 0 (all orgs)
                       p.c_period_id,
                       rl.value,                                -- the missing docBaseType
                       'Y',                                     -- IsActive
                       COALESCE(most_common.periodaction, 'N'), -- Use most common or NoAction
                       COALESCE(most_common.periodstatus, 'N'), -- Use most common or NeverOpened
                       COALESCE(most_common.processing, 'N'),   -- Use most common or N
                       NOW(),                                   -- Created
                       99,                                      -- CreatedBy = Migration
                       NOW(),                                   -- Updated
                       99                                       -- UpdatedBy = Migration
                FROM c_period p
                         CROSS JOIN ad_ref_list rl
                         LEFT JOIN LATERAL (
                    -- Get the most common values for this period
                    SELECT MODE() WITHIN GROUP (ORDER BY pc.periodaction) AS periodaction,
                           MODE() WITHIN GROUP (ORDER BY pc.periodstatus) AS periodstatus,
                           MODE() WITHIN GROUP (ORDER BY pc.processing)   AS processing
                    FROM c_periodcontrol pc
                    WHERE pc.c_period_id = p.c_period_id
                    ) most_common ON TRUE
                WHERE p.isactive = 'Y'
                  AND rl.ad_reference_id = 183
                  AND rl.isactive = 'Y'
                  AND NOT EXISTS (SELECT 1
                                  FROM c_periodcontrol pc
                                  WHERE pc.c_period_id = p.c_period_id
                                    AND pc.docbasetype = rl.value)
                RETURNING c_periodcontrol.c_period_id, c_periodcontrol.docbasetype)
        SELECT ir.c_period_id,
               ir.docbasetype,
               1::int AS periods_created
        FROM inserted_records ir
        ORDER BY ir.c_period_id, ir.docbasetype;

END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION add_missing_period_controls() IS
    'Creates period controls for new docBaseTypes that are missing period controls.
    Checks AD_Ref_List (reference_id=183) for valid docBaseTypes and creates period controls
    per period using the most common PeriodAction, PeriodStatus, and Processing values from existing controls.
    Based on de.metas.calendar.impl.PeriodBL.createPeriodControls'
;
