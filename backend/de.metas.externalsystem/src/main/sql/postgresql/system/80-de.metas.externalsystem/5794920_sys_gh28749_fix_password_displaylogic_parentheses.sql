-- Fix Password DisplayLogic: add parentheses for correct evaluation with UseOperatorPrecedence=N
-- Without parentheses, left-to-right evaluation causes Password to be hidden when HTTP+Basic is selected
-- because the trailing `& @SftpAuthType/X@='PASSWORD'` evaluates to false.

-- AD_Field.DisplayLogic
UPDATE AD_Field
SET DisplayLogic   = '(@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'') | (@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD'')',
    Updated        = TO_TIMESTAMP('2026-03-20 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy      = 100
WHERE AD_Field_ID = 755950;

-- AD_Column.MandatoryLogic
UPDATE AD_Column
SET MandatoryLogic = '(@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'') | (@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD'')',
    Updated        = TO_TIMESTAMP('2026-03-20 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy      = 100
WHERE AD_Column_ID = (SELECT AD_Column_ID
                      FROM AD_Column
                      WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'ExternalSystem_Endpoint')
                        AND ColumnName = 'Password');
