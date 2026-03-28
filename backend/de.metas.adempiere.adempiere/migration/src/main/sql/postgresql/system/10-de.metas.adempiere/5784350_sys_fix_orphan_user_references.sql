-- Fix orphan user references (UpdatedBy/CreatedBy pointing to non-existent users)
-- This fixes CI test failures where business rule triggers fail due to FK constraint
-- on ad_businessrule_event.triggering_user_id referencing a non-existent user.
--
-- The issue: Some records (e.g. C_BPartner 2155894 "metasfresh AG") have UpdatedBy
-- pointing to user IDs that don't exist in AD_User. When these records are modified,
-- the BusinessRule trigger tries to create an event with the old UpdatedBy value,
-- which fails the FK constraint.

-- Fix C_BPartner records where UpdatedBy references non-existent users
UPDATE C_BPartner bp
SET UpdatedBy = 100
WHERE NOT EXISTS (SELECT 1 FROM AD_User u WHERE u.AD_User_ID = bp.UpdatedBy);

UPDATE C_BPartner bp
SET CreatedBy = 100
WHERE NOT EXISTS (SELECT 1 FROM AD_User u WHERE u.AD_User_ID = bp.CreatedBy);

-- Fix C_BPartner_Location records
UPDATE C_BPartner_Location bpl
SET UpdatedBy = 100
WHERE NOT EXISTS (SELECT 1 FROM AD_User u WHERE u.AD_User_ID = bpl.UpdatedBy);

UPDATE C_BPartner_Location bpl
SET CreatedBy = 100
WHERE NOT EXISTS (SELECT 1 FROM AD_User u WHERE u.AD_User_ID = bpl.CreatedBy);

-- Fix AD_User records (self-referential)
UPDATE AD_User usr
SET UpdatedBy = 100
WHERE UpdatedBy != 0
  AND NOT EXISTS (SELECT 1 FROM AD_User u WHERE u.AD_User_ID = usr.UpdatedBy);

UPDATE AD_User usr
SET CreatedBy = 100
WHERE CreatedBy != 0
  AND NOT EXISTS (SELECT 1 FROM AD_User u WHERE u.AD_User_ID = usr.CreatedBy);
