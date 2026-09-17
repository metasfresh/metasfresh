-- Fix AD_Message Values for Package Licensing report column translations.
-- PostgreSQL returns lowercase column names from unquoted RETURNS TABLE aliases.
-- AD_Message lookup (IMsgBL.translatable → Msg.translate → MessagesMap.getByAdMessage)
-- is case-sensitive (HashMap key), so the Values must match the lowercase SQL output.
-- AD_Element lookup is case-insensitive (UPPER(ColumnName)=UPPER(?)), so it's not affected.

-- Fix VendorName → vendorname
UPDATE AD_Message SET Value = 'vendorname', Updated = '2026-04-13 10:00', UpdatedBy = 0
WHERE AD_Message_ID = 545656;

-- Fix VendorCountryCode → vendorcountrycode
UPDATE AD_Message SET Value = 'vendorcountrycode', Updated = '2026-04-13 10:00', UpdatedBy = 0
WHERE AD_Message_ID = 545657;

-- Fix IsVendorPackageLicensingExempt → isvendorpackagelicensingexempt
UPDATE AD_Message SET Value = 'isvendorpackagelicensingexempt', Updated = '2026-04-13 10:00', UpdatedBy = 0
WHERE AD_Message_ID = 545658;

-- Add missing MaterialType message (was deleted in review fix 5797740 because the Value
-- collided with an AD_Element ColumnName — but the AD_Element is 'MaterialType' which
-- matches case-insensitively. However, the AD_Element lookup uses UPPER() so it works.
-- The issue is that no AD_Element with ColumnName='MaterialType' exists on this system.
-- So we need an AD_Message with lowercase value.)
INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Value, Updated, UpdatedBy)
VALUES (0, 545659 /*From ID Server*/, 0, '2026-04-13 10:00', 0, 'D', 'Y', 'Materialart', 'I', 'materialtype', '2026-04-13 10:00', 0);
INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 545659, m.MsgText, m.MsgTip, 'N', 0, 0, m.Created, 0, m.Updated, 0, 'Y'
FROM AD_Language l, AD_Message m WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND m.AD_Message_ID=545659
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Message_ID=545659);
UPDATE AD_Message_Trl SET MsgText='Material Type', IsTranslated='Y', Updated='2026-04-13 10:00', UpdatedBy=0 WHERE AD_Message_ID=545659 AND AD_Language='en_US';
