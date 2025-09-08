drop index if exists MobileUI_UserProfile_MFG_uq;
create unique index MobileUI_UserProfile_MFG_uq on MobileUI_UserProfile_MFG(ad_user_id);