-- Normalize AD_Menu for "Import - Hauptbuchjournal" window (278)
--
-- Two branches independently created the same menu entry with different IDs:
--   - new_dawn_uat: AD_Menu_ID=542247 (script 5768920)
--   - soft_panda_hotfix: AD_Menu_ID=542298 (script 5789380)
--
-- This script ensures all instances end up with AD_Menu_ID=542247 (the new_dawn_uat canonical ID).
-- Three possible states:
--   1. Only 542247 exists (new_dawn_uat path) => nothing to do
--   2. Only 542298 exists (hotfix path) => create 542247, delete 542298
--   3. Both exist (merge path) => delete 542298

-- Step 1: If 542247 does not exist but 542298 does, create 542247 from 542298's data
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Window_ID,
                     Created, CreatedBy, Description, EntityType, InternalName,
                     IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
SELECT Action, AD_Client_ID, AD_Element_ID, 542247, AD_Org_ID, AD_Window_ID,
       Created, CreatedBy, Description, EntityType, InternalName,
       IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy
FROM AD_Menu
WHERE AD_Menu_ID = 542298
  AND NOT EXISTS (SELECT 1 FROM AD_Menu WHERE AD_Menu_ID = 542247)
;

-- Step 2: Copy translations from 542298 to 542247 (if 542247 translations don't exist yet)
INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
                         IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT AD_Language, 542247, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
       IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive
FROM AD_Menu_Trl
WHERE AD_Menu_ID = 542298
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl t2 WHERE t2.AD_Menu_ID = 542247 AND t2.AD_Language = AD_Menu_Trl.AD_Language)
;

-- Step 3: Ensure tree nodes exist for 542247 (copy from 542298 if needed)
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
       AD_Tree_ID, 542247, Parent_ID, SeqNo
FROM AD_TreeNodeMM
WHERE Node_ID = 542298
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM t2 WHERE t2.AD_Tree_ID = AD_TreeNodeMM.AD_Tree_ID AND t2.Node_ID = 542247)
;

-- Step 4: Clean up the duplicate 542298 (safe to run even if it doesn't exist)
DELETE FROM AD_TreeNodeMM WHERE Node_ID = 542298;
DELETE FROM AD_Menu_Trl WHERE AD_Menu_ID = 542298;
DELETE FROM AD_Menu WHERE AD_Menu_ID = 542298;
