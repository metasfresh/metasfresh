-- me03 #29261: Order Line Split
-- AD_Process_Access: grant process access to admin roles
-- IDs from ID server (http://idserver.metas.de):
-- AD_Process -> 585622

-- 2026-05-26T00:00:00.000Z
INSERT INTO AD_Process_Access (
    AD_Role_ID, AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsReadWrite
)
SELECT r.AD_Role_ID, 585622, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-05-26 00:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Role r
WHERE r.Name IN ('System', 'GardenAdmin', 'metasfresh Admin')
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Access pa WHERE pa.AD_Role_ID = r.AD_Role_ID AND pa.AD_Process_ID = 585622)
;
