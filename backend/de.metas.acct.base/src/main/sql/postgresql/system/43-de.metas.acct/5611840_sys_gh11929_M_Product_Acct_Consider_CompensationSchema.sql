

-- 2021-11-03T16:29:39.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) 
VALUES (0,0,541422,'O',TO_TIMESTAMP('2021-11-03 18:29:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Product_Acct_Consider_CompensationSchema',TO_TIMESTAMP('2021-11-03 18:29:39','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2021-11-03T17:56:47.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='When the value of this sys config is ''Y'', the invoice lines that are coming from order compensation groups will be booked on the account from the product category where the compensation schema of the group is set.
Example: 
- Product X is a discount product and has the account for revenue = ''1234'';
- Product X is used in the compensation group ''CGA'' based on the compensation schema ''SchemaA'', in a sales order line or order ''Order1'';
- The compensation schema ''SchemaA'' is set in the product category ''CatA''
- The product category ''CatA'' account for revenue =''5678''. Important: Make sure that there is one one product category with a specific schema ( introduce a unique constraint if not sure)
- An invoice is created based on the ''Order1''
- In the invoice accounting, the line for Product X will be booked on the account for revenue =''5678'' if this sys config is set to ''Y''
- In the invoice accounting, the line for Product X will be booked on the account for revenue =''1234'' if this sys config is set to ''N''',Updated=TO_TIMESTAMP('2021-11-03 19:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541422
;

