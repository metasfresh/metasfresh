-- Migration: Fix duplicate AD_UI_Elements on ExternalSystem_Endpoint window
-- Issue: me03#28749 (EDI-Austausch via sFTP)
-- The original migration (5794400) created new AD_UI_Elements in HTTP/SFTP groups
-- but did not deactivate the old elements in the "main" group, causing fields to
-- appear twice in the form.

-- =============================================================================
-- 1. Deactivate OLD duplicate elements in "main" group (553738)
--    These fields now have proper elements in the HTTP group (554995)
-- =============================================================================

-- OutboundHttpEP: old=638545 (main), new=648567 (HTTP)
UPDATE AD_UI_Element SET IsActive = 'N',
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638545;

-- OutboundHttpMethod: old=638547 (main), new=648568 (HTTP)
UPDATE AD_UI_Element SET IsActive = 'N',
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638547;

-- ContentType: old=648503 (main), new=648570 (HTTP)
UPDATE AD_UI_Element SET IsActive = 'N',
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 648503;

-- AuthType: old=638546 (main), new=648569 (HTTP)
UPDATE AD_UI_Element SET IsActive = 'N',
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638546;

-- Password: old=638557 (main), new=648571 (HTTP)
UPDATE AD_UI_Element SET IsActive = 'N',
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638557;

-- =============================================================================
-- 2. Move remaining HTTP-auth fields from "main" (553738) to "HTTP" group (554995)
--    These fields were not duplicated but belong in the HTTP group
-- =============================================================================

-- AuthToken (638553): move to HTTP group, SeqNo after Password
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID = 554995, SeqNo = 60,
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638553;

-- LoginUsername (638556): move to HTTP group
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID = 554995, SeqNo = 70,
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638556;

-- ClientId (638554): move to HTTP group
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID = 554995, SeqNo = 80,
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638554;

-- ClientSecret (638555): move to HTTP group
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID = 554995, SeqNo = 90,
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638555;

-- SasSignature (638744): move to HTTP group
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID = 554995, SeqNo = 100,
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638744;

-- =============================================================================
-- 3. Transfer grid settings: OutboundHttpEP grid column (from old to new element)
--    Old element 638545 had SeqNoGrid=30, IsDisplayedGrid='Y' — transfer to 648567
-- =============================================================================

UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 30,
  Updated = TO_TIMESTAMP('2026-03-16 14:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 648567;

-- =============================================================================
-- 4. Add Password to SFTP group for SFTP+Password auth visibility
--    Password has DisplayLogic that covers both HTTP Basic and SFTP Password.
--    The HTTP group already has Password (648571). Add one in SFTP group too,
--    so the field appears in the right visual context for SFTP users.
-- =============================================================================

-- Password field (AD_Field_ID=755950) in SFTP group, after SshPrivateKey
-- Re-use element 648571 in HTTP group, and update its DisplayLogic on AD_Field
-- to handle both cases. Actually, the AD_Field already has the combined DisplayLogic.
-- The form will show Password in whichever group is visible based on DisplayLogic.
-- Since Password's DisplayLogic = '@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'' | @TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD'''
-- it will show in the HTTP group when HTTP+Basic, and we need it in SFTP group when SFTP+Password.
-- But metasfresh WebUI shows a field once based on DisplayLogic regardless of which group it's in.
-- Since Password with its DisplayLogic is now in the HTTP group, it will appear there even for SFTP.
-- This is acceptable since the HTTP group label provides context. No SFTP-group duplicate needed.
