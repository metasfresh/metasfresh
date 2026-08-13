-- Sync AD_ViewSource / AD_ViewSource_Column after the view join rewrite (5818980):
-- - Deactivate stale AD_ViewSource_Column for M_Delivery_Planning.ReleaseNo (was the old join key)
-- - Add AD_ViewSource for M_ShipperTransportation so that changes to the DI header
--   (docstatus, loading date, delivery address) invalidate the view cache correctly.
--
-- AD_ViewSource 540000: source=M_Delivery_Planning, view=M_Delivery_Planning_Delivery_Instructions_V
-- AD_ViewSource_Column 540000: M_Delivery_Planning.ReleaseNo (585025) — no longer the join key
-- New AD_ViewSource 540002: source=M_ShipperTransportation, parent_link=M_Delivery_Planning_Delivery_Instructions_V.M_ShipperTransportation_ID (585501),
--                            source_link=M_ShipperTransportation.M_ShipperTransportation_ID (540426)

INSERT INTO AD_MigrationScript(AD_MigrationScript_ID, Name, Description, Script_Type, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES(5818990 /*From ID Server*/, '5818990_sys_gh30630_fix_M_Delivery_Planning_Delivery_Instructions_V_viewsources',
       'Sync AD_ViewSource after view join rewrite: deactivate stale ReleaseNo ViewSource_Column, add M_ShipperTransportation ViewSource',
       'SQL', 'Y',
       TO_TIMESTAMP('2026-08-13 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-13 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Deactivate stale AD_ViewSource_Column for M_Delivery_Planning.ReleaseNo
-- (was the join key in old view; new view joins via M_ShipperTransportation_ID instead)
UPDATE AD_ViewSource_Column
SET IsActive = 'N',
    Updated  = TO_TIMESTAMP('2026-08-13 10:01:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
WHERE AD_ViewSource_Column_ID = 540000;

-- Add AD_ViewSource for M_ShipperTransportation:
-- when DI header data changes (docstatus, loadingdate, etc.), invalidate view cache
-- via the M_ShipperTransportation_ID link on M_Delivery_Planning
INSERT INTO AD_ViewSource (AD_Client_ID, AD_Org_ID, AD_Table_ID, AD_ViewSource_ID,
                           Created, CreatedBy, IsActive,
                           IsInvalidateOnAfterChange, IsInvalidateOnAfterDelete,
                           IsInvalidateOnAfterNew, IsInvalidateOnBeforeChange,
                           IsInvalidateOnBeforeNew,
                           Parent_LinkColumn_ID, Source_LinkColumn_ID, Source_Table_ID,
                           Updated, UpdatedBy)
VALUES (0, 0,
        542280,   -- AD_Table_ID: M_Delivery_Planning_Delivery_Instructions_V
        540002 /*From ID Server*/,
        TO_TIMESTAMP('2026-08-13 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
        'Y', 'N', 'N', 'Y', 'N',
        585501,   -- Parent_LinkColumn_ID: M_Delivery_Planning_Delivery_Instructions_V.M_ShipperTransportation_ID
        540426,   -- Source_LinkColumn_ID: M_ShipperTransportation.M_ShipperTransportation_ID
        540030,   -- Source_Table_ID: M_ShipperTransportation
        TO_TIMESTAMP('2026-08-13 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100);
