-- Make sure only one font / AD_Client can be default.
drop index if exists AD_PrintFont_Default;
create unique index AD_PrintFont_Default on AD_PrintFont(AD_Client_ID, IsDefault) where IsActive='Y' and IsDefault='Y';

-- Make sure only one AD_PrintPaper / AD_Client can be default.
drop index if exists AD_PrintPaper_Default;
create unique index AD_PrintPaper_Default on AD_PrintPaper(AD_Client_ID, IsDefault) where IsActive='Y' and IsDefault='Y';

-- Make sure only one AD_PrintColor / AD_Client can be default.
drop index if exists AD_PrintColor_Default;
create unique index AD_PrintColor_Default on AD_PrintColor(AD_Client_ID, IsDefault) where IsActive='Y' and IsDefault='Y';

