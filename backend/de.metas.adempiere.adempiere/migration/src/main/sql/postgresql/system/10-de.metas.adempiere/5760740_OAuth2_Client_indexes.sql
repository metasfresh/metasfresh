DROP INDEX IF EXISTS oauth2_client_internalname_uq
;

CREATE UNIQUE INDEX oauth2_client_internalname_uq ON oauth2_client (internalname)
;

DROP INDEX IF EXISTS oauth2_client_name_uq
;

CREATE UNIQUE INDEX oauth2_client_name_uq ON oauth2_client (name)
;

