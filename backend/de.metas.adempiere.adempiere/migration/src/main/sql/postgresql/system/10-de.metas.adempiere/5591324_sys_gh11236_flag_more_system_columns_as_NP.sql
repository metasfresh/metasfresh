
UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('ExternalSystem_Config')
  AND ColumnName in ('Name', 'Type','CamelURL');
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('ExternalSystem_Config_Alberta')
  AND ColumnName in ('BaseURL', 'Tenant','ExternalSystemValue');

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('ExternalSystem_Config_Shopware6')
  AND ColumnName in ('BaseURL', 'Client_Id','ExternalSystemValue','FreightCost_NormalVAT_Rates', 'FreightCost_Reduced_VAT_Rates',
                     'JSONPathConstantBPartnerID','JSONPathConstantBPartnerLocationID', 'JSONPathSalesRepID');

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('ExternalSystem_Config_Shopware6Mapping')
  AND ColumnName in ('SW6_Customer_Group','SW6_Payment_Method', 'BPartner_IfExists', 'BPartner_IfNotExists',
                     'BPartnerLocation_IfExists','BPartnerLocation_IfNotExists','Description','PaymentRule')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('ExternalSystem_RuntimeParameter')
  AND ColumnName in ('Name','Value','External_Request')
;

UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('C_BPartner_AlbertaRole')
  and columnname in ('AlbertaRole');


UPDATE ad_column
SET personaldatacategory='NP'
WHERE ad_table_id = get_table_id('S_ExternalReference')
  and columnname in ('ExternalReference','ExternalSystem','Type','Version')
;