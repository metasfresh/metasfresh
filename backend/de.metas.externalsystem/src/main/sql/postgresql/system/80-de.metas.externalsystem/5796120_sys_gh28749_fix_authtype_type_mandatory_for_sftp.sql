-- Fix: AuthType and Type columns must be nullable when TransportType=SFTP
-- Previously both were IsMandatory=Y with no MandatoryLogic, causing "null value in column"
-- errors when saving SFTP endpoints (AuthType and Type are HTTP-only concepts).

-- 1. Make AuthType conditionally mandatory (only when TransportType=HTTP)
UPDATE AD_Column
SET IsMandatory    = 'N',
    MandatoryLogic = '@TransportType/X@=''HTTP''',
    Updated        = TO_TIMESTAMP('2026-03-27 08:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy      = 100
WHERE AD_Column_ID = 591481; -- AuthType on ExternalSystem_Endpoint

-- Physical column: drop NOT NULL
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN AuthType DROP NOT NULL;

-- 2. Make Type conditionally mandatory (only when TransportType=HTTP)
--    Type only has value 'HTTP' and is not used in Java domain code — meaningless for SFTP.
UPDATE AD_Column
SET IsMandatory    = 'N',
    MandatoryLogic = '@TransportType/X@=''HTTP''',
    Updated        = TO_TIMESTAMP('2026-03-27 08:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy      = 100
WHERE AD_Column_ID = 591477; -- Type on ExternalSystem_Endpoint

-- Physical column: drop NOT NULL
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN Type DROP NOT NULL;

-- 3. Add DisplayLogic on Type field — hide when TransportType=SFTP (same as AuthType already has)
UPDATE AD_Field
SET DisplayLogic = '@TransportType/X@=''HTTP''',
    Updated      = TO_TIMESTAMP('2026-03-27 08:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy    = 100
WHERE AD_Field_ID = 755939; -- Type field on ExternalSystem_Endpoint window
