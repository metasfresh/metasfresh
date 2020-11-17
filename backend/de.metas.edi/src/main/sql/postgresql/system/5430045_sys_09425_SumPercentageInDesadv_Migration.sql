

UPDATE EDI_Desadv 
SET EDI_DESADV_MinimumSumPercentage = 10;

--- also clean some old data: 17 desadvs from April 7 latest, have no documentNO set. I will set their status from Error to DontSend




UPDATE EDI_Desadv
SET EDI_ExportStatus = 'N' -- DontSend
WHERE EDI_ExportStatus = 'E' AND DocumentNo IS NULL;
