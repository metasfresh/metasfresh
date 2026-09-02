DROP INDEX IF EXISTS PickingProfile_Filter_UQ
;

CREATE UNIQUE INDEX PickingProfile_Filter_UQ ON PickingProfile_Filter (mobileui_userprofile_picking_id, filtertype)
;

