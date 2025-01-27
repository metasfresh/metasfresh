DROP INDEX IF EXISTS MobileUI_UserProfile_DD_UQ
;

CREATE UNIQUE INDEX MobileUI_UserProfile_DD_UQ ON MobileUI_UserProfile_DD (IsActive) WHERE IsActive = 'Y'
;

