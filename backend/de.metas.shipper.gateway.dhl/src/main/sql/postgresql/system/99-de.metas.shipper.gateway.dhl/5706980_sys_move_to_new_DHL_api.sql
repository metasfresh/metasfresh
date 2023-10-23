-- Field: Lieferweg(142,D) -> DHL Konfiguration(542055,de.metas.shipper.gateway.dhl) -> Kennwort
-- Column: DHL_Shipper_Config.Signature
-- 2023-10-13T12:50:05.600943600Z
UPDATE AD_Field SET AD_Name_ID=498, Description='', Help='The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".', Name='Kennwort',Updated=TO_TIMESTAMP('2023-10-13 15:50:05.6','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=589550
;

-- 2023-10-13T12:50:05.604093400Z
UPDATE AD_Field_Trl trl SET Description='',Help='The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".',Name='Kennwort' WHERE AD_Field_ID=589550 AND AD_Language='de_DE'
;

-- 2023-10-13T12:50:05.636524500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(498) 
;

-- 2023-10-13T12:50:05.650735500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589550
;

-- 2023-10-13T12:50:05.651784Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(589550)
;

-- Column: DHL_Shipper_Config.dhl_api_url
-- 2023-10-13T12:50:54.666766400Z
UPDATE AD_Column SET DefaultValue='https://api-eu.dhl.com',Updated=TO_TIMESTAMP('2023-10-13 15:50:54.666','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=568997
;

