CREATE INDEX IF NOT EXISTS M_Picking_Job_HUAlternative_M_Picking_Job_ID ON M_Picking_Job_HUAlternative (M_Picking_Job_ID);
CREATE INDEX IF NOT EXISTS M_Picking_Job_Line_M_Picking_Job_ID ON M_Picking_Job_Line (M_Picking_Job_ID);
CREATE INDEX IF NOT EXISTS M_Picking_Job_Step_M_Picking_Job_ID ON M_Picking_Job_Step (M_Picking_Job_ID);
CREATE INDEX IF NOT EXISTS M_Picking_Job_Step_HUAlternative_M_Picking_Job_ID ON M_Picking_Job_Step_HUAlternative (M_Picking_Job_ID);
CREATE INDEX IF NOT EXISTS M_Picking_Job_Step_PickedHU_M_Picking_Job_ID ON M_Picking_Job_Step_PickedHU (M_Picking_Job_ID);
