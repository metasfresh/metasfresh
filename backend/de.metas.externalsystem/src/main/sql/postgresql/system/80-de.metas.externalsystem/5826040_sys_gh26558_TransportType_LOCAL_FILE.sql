-- Add LOCAL_FILE transport type to ExternalSystem_Endpoint.TransportType reference list
-- This enables local file system operations for external system endpoints

-- 0. Widen the TransportType CHECK constraint to include LOCAL_FILE
-- The constraint was created by 5794400_sys_gh28749_add_sftp_columns.sql and only allowed 'HTTP' and 'SFTP'
ALTER TABLE ExternalSystem_Endpoint DROP CONSTRAINT IF EXISTS ck_endpoint_transporttype;
ALTER TABLE ExternalSystem_Endpoint ADD CONSTRAINT ck_endpoint_transporttype
	CHECK (TransportType IN ('HTTP', 'SFTP', 'LOCAL_FILE'));

-- 1. Insert new ref-list entry (German base language, matching siblings' shape)
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Reference_ID, Value, ValueName, Name, AD_Client_ID, AD_Org_ID, EntityType, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (544371 /*From ID Server*/, 542077, 'LOCAL_FILE', 'LOCAL_FILE', 'Lokale Datei', 0, 0, 'de.metas.externalsystem', 'Y', TO_TIMESTAMP('2026-09-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-23 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- 2. Seed AD_Ref_List_Trl rows for all active system languages (copies base German text)
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, IsTranslated, IsActive, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, 'N', 'Y', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544371 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- 3. Override en_US translation with English text
UPDATE AD_Ref_List_Trl SET Name='Local File', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-23 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544371 /*From ID Server*/;
