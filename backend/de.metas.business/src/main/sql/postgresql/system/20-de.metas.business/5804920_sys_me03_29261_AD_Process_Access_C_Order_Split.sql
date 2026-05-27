-- Grant process access to standard test/admin roles.
-- The System role is auto-granted on AD_Process insert by the model interceptor;
-- explicitly seeding GardenAdmin + metasfresh Admin so test users see the action.

INSERT INTO AD_Process_Access (
    AD_Role_ID, AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy, IsReadWrite
)
SELECT r.AD_Role_ID, 585625 /*From ID Server*/, 0, 0, 'Y',
       NOW(), 100, NOW(), 100, 'Y'
FROM AD_Role r
WHERE r.Name IN ('System', 'GardenAdmin', 'metasfresh Admin')
  AND NOT EXISTS (
      SELECT 1 FROM AD_Process_Access pa
      WHERE pa.AD_Role_ID = r.AD_Role_ID
        AND pa.AD_Process_ID = 585625 /*From ID Server*/
  );
