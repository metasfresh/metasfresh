-- Quantity calibration: a component with calibrated Qty 0 keeps its order line, so a group is never refused for having
-- no components left. Removes the message C_CompensationGroup_CalibrationRule_AllComponentsLeftOut (AD_Message 545910,
-- migration 5828220); the other calibration-rule messages 545907..545909 stay.

DELETE FROM AD_Message_Trl WHERE AD_Message_ID = 545910;
DELETE FROM AD_Message WHERE AD_Message_ID = 545910;
