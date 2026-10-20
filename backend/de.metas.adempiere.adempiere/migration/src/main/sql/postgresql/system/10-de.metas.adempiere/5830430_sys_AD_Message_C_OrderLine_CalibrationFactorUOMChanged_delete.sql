-- C_OrderLine_CalibrationFactorUOMChanged is obsolete: the uncalibrated qty is converted when the line UOM changes
DELETE FROM AD_Message_Trl WHERE AD_Message_ID = 545964;
DELETE FROM AD_Message WHERE AD_Message_ID = 545964;
