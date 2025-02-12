UPDATE MobileUI_UserProfile_Picking p
SET IsAllowAnyCustomer=(
    CASE
        WHEN EXISTS (SELECT 1 FROM MobileUI_UserProfile_Picking_BPartner bp WHERE bp.MobileUI_UserProfile_Picking_ID = p.MobileUI_UserProfile_Picking_ID)
            THEN 'N'
            ELSE 'Y'
    END)
;