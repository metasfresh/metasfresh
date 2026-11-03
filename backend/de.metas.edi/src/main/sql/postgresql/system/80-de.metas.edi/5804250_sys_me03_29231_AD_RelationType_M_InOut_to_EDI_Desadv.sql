-- Migration: AD_RelationType M_InOut -> EDI_Desadv (via junction)
-- Issue: https://github.com/metasfresh/me03/issues/29231
-- PR: https://github.com/metasfresh/metasfresh/pull/24042
--
-- Context: The PR moves from a single FK (M_InOut.EDI_Desadv_ID) to an N:M junction
-- table (EDI_Desadv_M_InOut) to support multiple DESADVs per shipment and vice-versa.
-- The old single-FK backed a "Zoom to related" UI gesture (Alt+6 references panel).
-- This migration restores that navigation by creating an AD_RelationType that goes via
-- the EDI_Desadv_M_InOut junction, so users can still navigate from an M_InOut record
-- to its related EDI_Desadv row(s) — regardless of whether it is 1:1 or 1:N.
--
-- IDs allocated from central ID server (http://idserver.metas.de):
--   AD_RelationType_ID : 540497
--   AD_Reference_ID (source, M_InOut) : 542094
--   AD_Reference_ID (target, EDI_Desadv) : 542095

-- ============================================================
-- 1. Source AD_Reference — identifies M_InOut documents
--    No where clause: any M_InOut can navigate to its DESADVs.
--    AD_Table_ID=319 (M_InOut), AD_Key=3521 (M_InOut_ID), AD_Window_ID=169 (M_InOut window)
-- ============================================================

-- 2024-01-01
INSERT INTO AD_Reference
    (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType,
     IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES
    (0, 0, 542094, NOW(), 100, 'de.metas.esb.edi',
     'Y', 'N', 'M_InOut_Source_For_EDI_Desadv', NOW(), 100, 'T')
;

INSERT INTO AD_Reference_Trl
    (AD_Language, AD_Reference_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Reference_ID = 542094
  AND NOT EXISTS (
      SELECT 1 FROM AD_Reference_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Reference_ID = t.AD_Reference_ID
  )
;

INSERT INTO AD_Ref_Table
    (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
     Created, CreatedBy, EntityType, IsActive, IsValueDisplayed,
     ShowInactiveValues, Updated, UpdatedBy, WhereClause, AD_Window_ID)
VALUES
    (0, 3521, 0, 542094, 319,
     NOW(), 100, 'de.metas.esb.edi', 'Y', 'N',
     'N', NOW(), 100, NULL, 169)
;

-- ============================================================
-- 2. Target AD_Reference — identifies EDI_Desadv documents
--    Where clause joins via the EDI_Desadv_M_InOut junction.
--    AD_Table_ID=540644 (EDI_Desadv), AD_Key=551724 (EDI_Desadv_ID),
--    AD_Window_ID=540256 (EDI Lieferavis (DESADV) window)
-- ============================================================

INSERT INTO AD_Reference
    (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType,
     IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES
    (0, 0, 542095, NOW(), 100, 'de.metas.esb.edi',
     'Y', 'N', 'EDI_Desadv_Target_For_M_InOut', NOW(), 100, 'T')
;

INSERT INTO AD_Reference_Trl
    (AD_Language, AD_Reference_ID, Description, Help, Name,
     IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Reference_ID = 542095
  AND NOT EXISTS (
      SELECT 1 FROM AD_Reference_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Reference_ID = t.AD_Reference_ID
  )
;

INSERT INTO AD_Ref_Table
    (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
     Created, CreatedBy, EntityType, IsActive, IsValueDisplayed,
     ShowInactiveValues, Updated, UpdatedBy, WhereClause, AD_Window_ID)
VALUES
    (0, 551724, 0, 542095, 540644,
     NOW(), 100, 'de.metas.esb.edi', 'Y', 'N',
     'N', NOW(), 100,
     'EXISTS (SELECT 1 FROM EDI_Desadv_M_InOut j WHERE j.EDI_Desadv_ID = EDI_Desadv.EDI_Desadv_ID AND j.M_InOut_ID = @M_InOut_ID / -1@ AND j.IsActive = ''Y'')',
     540256)
;

-- ============================================================
-- 3. AD_RelationType linking source to target
--    IsDirected=Y  — one-way: M_InOut -> EDI_Desadv
--    IsExplicit=N  — consistent with all other relation types in this system
--    Type='I'      — implicit (standard zoom-to-related)
-- ============================================================

INSERT INTO AD_RelationType
    (AD_Client_ID, AD_Org_ID, AD_RelationType_ID, Created, CreatedBy, EntityType,
     IsActive, IsDirected, IsExplicit, IsTableRecordIdTarget,
     Name, InternalName,
     AD_Reference_Source_ID, AD_Reference_Target_ID,
     Type, Updated, UpdatedBy)
VALUES
    (0, 0, 540497, NOW(), 100, 'de.metas.esb.edi',
     'Y', 'Y', 'N', 'N',
     'M_InOut -> EDI_Desadv (via junction)',
     'M_InOut_to_EDI_Desadv_via_junction',
     542094, 542095,
     'I', NOW(), 100)
;
