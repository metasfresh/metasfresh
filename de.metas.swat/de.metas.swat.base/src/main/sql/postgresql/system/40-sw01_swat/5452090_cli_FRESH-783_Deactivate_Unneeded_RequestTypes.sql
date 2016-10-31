UPDATE R_RequestType
SET IsActive = 'N'
WHERE InternalName IS NULL 
	AND Name <> 'eMail';