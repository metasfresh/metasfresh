drop index if exists C_BPartner_Location_ExternalId;
drop index if exists AD_User_ExternalId;
drop index if exists C_OLCand_ExternalId;

create unique index C_BPartner_Location_ExternalId on C_BPartner_Location(C_BPartner_ID, ExternalId) where ExternalId is not null and IsActive='Y';
create unique index AD_User_ExternalId on AD_User(C_BPartner_ID, ExternalId) where C_BPartner_ID is not null and ExternalId is not null and IsActive='Y';
create unique index C_OLCand_ExternalId on C_OLCand(ExternalId) where ExternalId is not null and IsActive='Y';

