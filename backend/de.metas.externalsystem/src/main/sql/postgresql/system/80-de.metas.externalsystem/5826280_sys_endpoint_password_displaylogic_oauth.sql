-- Show the Password field for HTTP endpoints authenticating with OAuth (v1).
--
-- The scripted-adapter route builds its OAuth token request from client id, client secret, username AND
-- password; LoginUsername, ClientId and ClientSecret already list OAuth, Password did not.
--
-- Every term is parenthesised and the terms joined with | only: operator precedence is off by default, so
-- the expression is evaluated left to right.
--
-- AD_Column.MandatoryLogic is deliberately NOT extended -- the token request omits a blank credential
-- rather than failing, so making Password mandatory would reject configurations this client can send.
--
-- IDs allocated from idserver.metas.de on 2026-09-24:
--   AD_MigrationScript 5826280 (this script)

UPDATE AD_Field
SET DisplayLogic = '(@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'') | (@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD'') | (@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth2'') | (@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth'')',
    Updated      = TO_TIMESTAMP('2026-09-24 11:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Field_ID = 755950;
