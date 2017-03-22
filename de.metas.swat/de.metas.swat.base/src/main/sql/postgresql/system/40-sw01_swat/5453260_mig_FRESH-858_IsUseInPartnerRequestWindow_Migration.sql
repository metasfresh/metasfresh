
UPDATE R_RequestType
SET IsUseForPartnerRequestWindow = 'Y'
WHERE IsActive = 'Y' AND Name <> 'eMail';