UPDATE AD_Column
SET ReadOnlyLogic='@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR''',
    Updated=now(),UpdatedBy=100
WHERE AD_Table_ID=500221
;
