-- Show the Password field for HTTP endpoints authenticating with OAuth (v1).
--
-- The scripted-adapter route that prepares an OAuth request builds the token request from client id,
-- client secret, username AND password, so an HTTP + OAuth endpoint needs a password. The window did
-- not show the field for that combination: the condition covered HTTP + Basic, SFTP + PASSWORD and
-- HTTP + OAuth2, but not HTTP + OAuth. LoginUsername, ClientId and ClientSecret already list OAuth,
-- so Password was the only one of the four missing it.
--
-- Parentheses around every term, and the sub-conditions joined with | only: the existing terms were
-- parenthesised for exactly that reason, because operator precedence is off by default and the
-- expression is evaluated left to right.
--
-- AD_Column.MandatoryLogic is deliberately NOT extended. The token request omits any of the four
-- credentials that is blank rather than failing, so an OAuth endpoint may legitimately authenticate
-- without a password; making it mandatory would block saving those. Shown-and-optional is the state
-- this needs.

UPDATE AD_Field
SET DisplayLogic = '(@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'') | (@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD'') | (@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth2'') | (@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth'')',
    Updated      = TO_TIMESTAMP('2026-09-24 11:05:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Field_ID = 755950;
