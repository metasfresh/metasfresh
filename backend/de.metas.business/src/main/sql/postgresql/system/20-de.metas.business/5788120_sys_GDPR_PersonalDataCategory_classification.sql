-- =====================================================================================
-- GDPR PersonalDataCategory Classification - Comprehensive Update
-- =====================================================================================
-- Classifies ALL AD_Columns by their GDPR personal data category:
--   SP = Sensitive Personal (GDPR Article 9 special categories + PCI-DSS)
--   P  = Personal (GDPR Article 4 identifiable person data)
--   NP = Not Personal (everything else)
--
-- Before this migration, 26,643 out of 32,822 active columns had NULL
-- (unclassified) PersonalDataCategory. Only 17 columns were classified.
-- After this migration, ALL active columns have a classification.
-- =====================================================================================

-- =====================================================================================
-- PART 1: SP (Sensitive Personal)
-- =====================================================================================

-- Birthday / Date of Birth
UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName = 'Birthday'
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName = 'PatientBirthday'
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

-- Gender
UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName = 'Alberta_Gender'
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName = 'Gender'
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'AD_User_Alberta')
  AND IsActive = 'Y';

-- Identity Documents (SSN, Driver's License, National ID)
UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('A_Ident_SSN', 'A_Ident_DL', 'NationalCode', 'SSCode')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

-- Street-Level Addresses
UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Address1', 'Address2', 'Address3', 'Address4',
                     'A_Street', 'NonStdAddress', 'AddressLine1', 'AddressLine2')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Address', 'VisitorsAddress')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('C_BPartner_Location', 'C_BPartner_Location_QuickInput'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

-- Credit Card Numbers and Verification Codes (PCI-DSS)
UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('CreditCardNumber', 'CreditCardVV', 'CreditCardExpMM', 'CreditCardExpYY')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

-- Health & Medical Data (Alberta healthcare)
UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_BPartner_AlbertaPatient')
  AND ColumnName IN ('NumberOfInsured', 'PayerType', 'CopaymentFrom', 'CopaymentTo',
                     'IsIVTherapy', 'IsTransferPatient', 'DeactivationComment',
                     'DeactivationDate', 'DeactivationReason', 'DischargeDate')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName LIKE 'Alberta_Prescription%')
  AND ColumnName IN ('Therapy', 'PrescriptionType', 'Note', 'PatientBirthday', 'TherapyType', 'AccountingMonths')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

UPDATE AD_Column SET PersonalDataCategory = 'SP', Updated='2026-02-12 20:00'
WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('C_OLCand_AlbertaTherapy', 'C_OLCand_AlbertaTherapyType'))
  AND ColumnName IN ('Therapy', 'TherapyType')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory <> 'SP');

-- =====================================================================================
-- PART 2: P (Personal)
-- =====================================================================================

-- Person Names (Firstname, Lastname)
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Firstname', 'Lastname')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Name fields in person-related tables
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Name', 'Name2', 'Name3')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'C_BPartner', 'C_BPartner_Location', 'C_BP_BankAccount',
    'HR_Employee', 'I_BPartner', 'I_User',
    'MKTG_ContactPerson', 'MKTG_Campaign_ContactPerson'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Company names in person context
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Companyname', 'CompanyName')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'C_BPartner', 'C_BPartner_QuickInput', 'C_BPartner_Export', 'I_BPartner'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- BPartnerName variants
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('BPartnerName', 'BPartnerName2', 'BPName', 'BPValue')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'C_BPartner_Location', 'C_BPartner_Location_QuickInput',
    'C_BPartner_QuickInput', 'C_BPartner_Export', 'I_BPartner', 'I_User'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- ContactName in various tables
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('ContactName', 'ContactDescription', 'Bill_ContactName', 'Bill_Name', 'Bill_Name2')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- A_Name (card/account owner name)
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName = 'A_Name'
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Delivery company names
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('GO_DeliverToCompanyName', 'GO_DeliverToCompanyName2', 'GO_PickupCompanyName')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Title / Salutation / Greeting
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName = 'Title'
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'C_BPartner_Contact_QuickInput', 'C_BPartner_Alberta', 'I_BPartner',
    'C_Dunning_Header_v', 'C_Invoice_Header_v', 'C_Order_Header_v',
    'C_Project_Header_v', 'C_RfQResponse_v', 'DD_Order_Header_v', 'M_InOut_Header_v'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('TitleShort', 'AlbertaTitle')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'C_BPartner', 'C_BPartner_Alberta'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('BPContactGreeting', 'Greeting', 'Letter_Salutation')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Email Addresses
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('EMail', 'EMail2', 'EMail3', 'EMailUser', 'A_EMail',
                     'b00email', 'b00email2',
                     'DHL_RecipientEmailAddress', 'DHL_Receiver_Email',
                     'RecipientEmailAddress', 'DK_Consignee_EMail',
                     'Bill_EMail', 'Receiver_Email',
                     'C_BPartner_Location_Email')
  AND IsActive = 'Y'
  AND AD_Table_ID NOT IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_MailBox', 'AD_WF_Node', 'AD_Client',
    'W_Store', 'W_Store_Trl',
    'C_BP_EDI',
    'C_Doc_Outbound_Log', 'C_Doc_Outbound_Log_Line', 'C_Mail'
  ))
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName = 'EMailUserPW'
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Phone / Mobile / Fax Numbers
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Phone', 'Phone2', 'MobilePhone', 'Fax', 'Fax2', 'ISDN',
                     'Bill_Phone', 'Pharma_Phone', 'Pharma_Fax', 'RetourFax',
                     'b00fax1', 'b00fax2',
                     'C_BPartner_Location_Phone', 'C_BPartner_Location_Fax', 'C_BPartner_Location_Mobile')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- City, Postal Code, Region
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('City', 'Postal', 'Postal_Add', 'POBox',
                     'A_City', 'A_Zip', 'A_Country', 'A_State',
                     'DHL_Receiver_City', 'DHL_Receiver_ZipCode',
                     'DHL_Receiver_CountryISO2Code', 'DHL_Receiver_CountryISO3Code',
                     'DHL_Shipper_City', 'DHL_Shipper_ZipCode',
                     'DHL_Shipper_CountryISO2Code', 'DHL_Shipper_CountryISO3Code',
                     'DK_Consignee_City', 'DK_Consignee_ZipCode', 'DK_Consignee_Country',
                     'DK_Sender_City', 'DK_Sender_ZipCode', 'DK_Sender_Country',
                     'ConsigneeZip', 'ConsigneeCountryCode',
                     'DefaultShipTo_City', 'DefaultShipTo_Postal')
  AND IsActive = 'Y'
  AND AD_Table_ID NOT IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'C_Country', 'C_Postal', 'I_Postal', 'DPD_Country', 'DPD_Route'
  ))
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Region', 'RegionName', 'CountryName')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'C_BPartner', 'I_BPartner'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Combined Address Strings
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('BPartnerAddress', 'BPartnerAddress_Override',
                     'BillToAddress', 'DeliveryToAddress', 'HandOverAddress')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Bank Account / Financial Data
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('IBAN', 'QR_IBAN', 'AccountNo', 'BankAccountNo', 'RoutingNo', 'SwiftCode',
                     'SEPA_CreditorIdentifier', 'ImportedBillPartnerIBAN',
                     'ESR_RenderedAccountNo', 'ESR_RenderedReceiver',
                     'CreditCardType', 'CreditCardValue',
                     'CreditorId', 'DebtorId')
  AND IsActive = 'Y'
  AND AD_Table_ID NOT IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'A_Depreciation_Exp'
  ))
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Tax & Registration Numbers
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('TaxID', 'VATaxID', 'EORI', 'DUNS', 'CommercialRegisterNumber', 'Registry',
                     'WasteDisposerNo', 'WasteProducerNo', 'ReferenceNo', 'CustomerNoAtVendor',
                     'SalesPartnerCode')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'C_BPartner', 'C_BPartner_Location', 'C_BPartner_QuickInput',
    'I_BPartner'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- User Credentials & Security
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Password', 'ProcurementPassword', 'passwordportal',
                     'PasswordResetCode', 'UserPIN', 'SecretKey_2FA')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'AD_User_Login', 'I_BPartner'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- System/integration passwords and secrets
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Password', 'DelisPassword', 'GO_AuthPassword', 'ProxyPassword',
                     'ApiKey', 'ApplicationToken', 'AuthToken',
                     'Client_Secret', 'ClientSecret', 'MSGRAPH_ClientSecret',
                     'PayPal_ClientSecret', 'PayPal_Token', 'SasSignature')
  AND AD_Table_ID NOT IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'AD_User_Login', 'I_BPartner',
    'AD_MailBox', 'AD_System'
  ))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Login username for external systems
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName = 'LoginUsername'
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'ExternalSystem_Outbound_Endpoint')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- IP Address (when tracking individuals)
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName = 'IP_Address'
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'CM_WebAccessLog')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName = 'LockedFromIP'
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Memos, Comments, Descriptions in person context
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('Memo', 'Memo_Delivery', 'Memo_HU', 'Memo_Invoicing',
                     'Comments', 'Description', 'ShortDescription', 'SO_Description', 'BPInfo',
                     'AD_User_Memo1', 'AD_User_Memo2', 'AD_User_Memo3', 'AD_User_Memo4',
                     'C_BPartner_Memo')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'AD_User', 'C_BPartner', 'I_BPartner'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- GDPR-specific: Contact Limitation
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('ContactLimitation', 'ContactLimitationReason')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- GLN (Global Location Number)
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('GLN', 'EdiDesadvRecipientGLN', 'EdiInvoicRecipientGLN')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'C_BPartner', 'C_BPartner_Location', 'C_BPartner_Location_QuickInput', 'I_BPartner'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- URLs (personal websites)
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('URL', 'URL2', 'URL3')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'C_BPartner', 'I_BPartner'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Login failure tracking
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('LoginFailureCount', 'LoginFailureDate', 'IsAccountLocked',
                     'PasswordChangeDate', 'EMailVerify', 'EMailVerifyDate')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('AD_User', 'RV_UserAccountLock'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Pharma-specific personal identifiers
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('b00name1', 'b00name2', 'b00name3', 'b00sname',
                     'b00email', 'b00email2', 'b00fax1', 'b00fax2')
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- Delivery notes in person context
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName IN ('GO_DeliverToNote', 'GO_PickupNote', 'DeliveryNote', 'DeliveryInfo')
  AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN (
    'GO_DeliveryOrder', 'Alberta_Order'))
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- EftMemo (Electronic Funds Transfer memo)
UPDATE AD_Column SET PersonalDataCategory = 'P', Updated='2026-02-12 20:00'
WHERE ColumnName = 'EftMemo'
  AND IsActive = 'Y'
  AND (PersonalDataCategory IS NULL OR PersonalDataCategory NOT IN ('P', 'SP'));

-- =====================================================================================
-- PART 3: Set all remaining NULL columns to NP (Not Personal)
-- =====================================================================================
UPDATE AD_Column SET PersonalDataCategory = 'NP', Updated='2026-02-12 20:00'
WHERE PersonalDataCategory IS NULL
  AND IsActive = 'Y';
