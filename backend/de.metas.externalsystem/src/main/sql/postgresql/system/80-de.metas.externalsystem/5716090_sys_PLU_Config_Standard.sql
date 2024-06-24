-- 2024-01-29T10:20:07.530Z
INSERT INTO LeichMehl_PluFile_ConfigGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:20:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,'LeichMehl Standard Configuration',TO_TIMESTAMP('2024-01-29 12:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2024-01-29T10:24:15.137Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:24:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540000,'@JsonPath=/orderId','PP','PP_Order_ID','numberField',TO_TIMESTAMP('2024-01-29 12:24:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:26:26.985Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540001,'@JsonPath=/orgCode@','PP','OrgValue','textArea',TO_TIMESTAMP('2024-01-29 12:26:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:33:37.059Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:33:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540002,'@JsonPath=/documentNo@','PP','DocumentNo','textArea',TO_TIMESTAMP('2024-01-29 12:33:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:34:03.040Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:34:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540003,'@JsonPath=/description@','PP','Description','textArea',TO_TIMESTAMP('2024-01-29 12:34:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:34:35.114Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:34:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540004,'@JsonPath=/dateOrdered@','PP','DateOrdered','date',TO_TIMESTAMP('2024-01-29 12:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:35:00.915Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:35:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540005,'@JsonPath=/datePromised@','PP','DatePromised','date',TO_TIMESTAMP('2024-01-29 12:35:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:35:52.489Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:35:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540006,'@JsonPath=/dateStartSchedule@','PP','DateStartSchedule','date',TO_TIMESTAMP('2024-01-29 12:35:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:38:57.540Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540007,'@JsonPath=/finishGoodProduct@','PP','finishGoodProduct','textArea',TO_TIMESTAMP('2024-01-29 12:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:39:04.921Z
UPDATE LeichMehl_PluFile_Config SET Description=' ?????????????',Updated=TO_TIMESTAMP('2024-01-29 12:39:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540007
;

-- 2024-01-29T10:39:49.614Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:39:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540008,'@JsonPath=/qtyToProduce@','PP','QtyOrdered','numberField',TO_TIMESTAMP('2024-01-29 12:39:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:40:23.029Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:40:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540009,'@JsonPath=/productId@','PP','M_Product_ID','numberField',TO_TIMESTAMP('2024-01-29 12:40:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:40:47.620Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:40:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540010,'@JsonPath=/bpartnerId@','PP','C_BPartner_ID','numberField',TO_TIMESTAMP('2024-01-29 12:40:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:41:08.562Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 12:41:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540011,'@JsonPath=/components@','PP','components','numberField',TO_TIMESTAMP('2024-01-29 12:41:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T10:41:13.339Z
UPDATE LeichMehl_PluFile_Config SET Description='????????????',Updated=TO_TIMESTAMP('2024-01-29 12:41:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540011
;



-- 2024-01-29T13:21:57.621Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:21:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540012,'@JsonPath=/id@','P','M_Product_ID2','numberField',TO_TIMESTAMP('2024-01-29 15:21:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:22:03.541Z
UPDATE LeichMehl_PluFile_Config SET Description='????????????????',Updated=TO_TIMESTAMP('2024-01-29 15:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540012
;

-- 2024-01-29T13:23:00.750Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:23:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540013,'@JsonPath=/productCategoryId@','P','M_Product_Category_ID','numberField',TO_TIMESTAMP('2024-01-29 15:23:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:23:59.155Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:23:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540014,'@JsonPath=/productNo@','P','ProductNo','textArea',TO_TIMESTAMP('2024-01-29 15:23:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:24:31.263Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:24:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540015,'@JsonPath=/name@','P','ProductName','textArea',TO_TIMESTAMP('2024-01-29 15:24:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:25:02.696Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:25:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540016,'@JsonPath=/description@','P','ProductDescription','textArea',TO_TIMESTAMP('2024-01-29 15:25:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:27:30.105Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540017,'@JsonPath=/ean@','P','UPC','EAN128',TO_TIMESTAMP('2024-01-29 15:27:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:27:58.260Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:27:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540018,'@JsonPath=/externalId@','P','ExternalId','numberField',TO_TIMESTAMP('2024-01-29 15:27:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:28:41.617Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540019,'@JsonPath=/uom@','P','UOMSymbol','textArea',TO_TIMESTAMP('2024-01-29 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:29:20.509Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:29:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540020,'@JsonPath=/manufacturerId@','P','Manufacturer_ID','numberField',TO_TIMESTAMP('2024-01-29 15:29:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:31:38.046Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:31:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540021,'@JsonPath=/manufacturerName@','P','Manufacturer','textArea',TO_TIMESTAMP('2024-01-29 15:31:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:32:23.157Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:32:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540022,'@JsonPath=/manufacturerNumber@','P','ManufacturerArticleNumber','textArea',TO_TIMESTAMP('2024-01-29 15:32:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:32:51.981Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:32:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540023,' @JsonPath=/discontinuedFrom@','P','DiscontinuedFrom','date',TO_TIMESTAMP('2024-01-29 15:32:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:33:10.408Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:33:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540024,'@JsonPath=/bpartners@','P','bpartners','textArea',TO_TIMESTAMP('2024-01-29 15:33:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:33:14.545Z
UPDATE LeichMehl_PluFile_Config SET Description='????????????',Updated=TO_TIMESTAMP('2024-01-29 15:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540024
;

-- 2024-01-29T13:34:38.656Z
INSERT INTO LeichMehl_PluFile_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,LeichMehl_PluFile_ConfigGroup_ID,LeichMehl_PluFile_Config_ID,Replacement,ReplacementSource,TargetFieldName,TargetFieldType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-01-29 15:34:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',540000,540025,'@JsonPath=/albertaProductInfo@','P','albertaProductInfo','textArea',TO_TIMESTAMP('2024-01-29 15:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T13:34:51.604Z
UPDATE LeichMehl_PluFile_Config SET Description='???????????',Updated=TO_TIMESTAMP('2024-01-29 15:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540025
;


-- 2024-01-29T14:58:43.274Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='GTIN',Updated=TO_TIMESTAMP('2024-01-29 16:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540017
;

-- 2024-01-29T14:59:14.833Z
UPDATE LeichMehl_PluFile_ConfigGroup SET Name='Leich + Mehl Standard Configuration',Updated=TO_TIMESTAMP('2024-01-29 16:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_ConfigGroup_ID=540000
;



-- 2024-01-29T14:59:50.523Z
UPDATE LeichMehl_PluFile_ConfigGroup SET Name='Leich + Mehl Standard',Updated=TO_TIMESTAMP('2024-01-29 16:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_ConfigGroup_ID=540000
;




-- 2024-01-29T16:19:01.138Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Produktionsauftrag',
Updated=TO_TIMESTAMP('2024-01-29 18:19:01','YYYY-MM-DD HH24:MI:SS'),
UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540000
;

-- 2024-01-29T16:23:08.568Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Sektion_Suchschluessel',Updated=TO_TIMESTAMP('2024-01-29 18:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540001
;

-- 2024-01-29T16:24:01.777Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Nr',Updated=TO_TIMESTAMP('2024-01-29 18:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540002
;

-- 2024-01-29T16:25:11.666Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Beschreibung',Updated=TO_TIMESTAMP('2024-01-29 18:25:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540003
;

-- 2024-01-29T16:25:46.681Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Auftragsdatum',Updated=TO_TIMESTAMP('2024-01-29 18:25:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540004
;

-- 2024-01-29T16:26:38.266Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Zugesagter_Termin',Updated=TO_TIMESTAMP('2024-01-29 18:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540005
;

-- 2024-01-29T16:28:13.388Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Geplanter_Beginn',Updated=TO_TIMESTAMP('2024-01-29 18:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540006
;

-- 2024-01-29T16:28:23.637Z
DELETE FROM LeichMehl_PluFile_Config WHERE LeichMehl_PluFile_Config_ID=540007
;

-- 2024-01-29T16:31:39.183Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Menge',Updated=TO_TIMESTAMP('2024-01-29 18:31:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540008
;

-- 2024-01-29T16:32:24.087Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Produkt',Updated=TO_TIMESTAMP('2024-01-29 18:32:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540009
;

-- 2024-01-29T16:33:15.556Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Geschaeftspartner',Updated=TO_TIMESTAMP('2024-01-29 18:33:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540010
;

-- 2024-01-29T16:33:20.100Z
DELETE FROM LeichMehl_PluFile_Config WHERE LeichMehl_PluFile_Config_ID=540011
;

-- 2024-01-29T16:33:24.914Z
DELETE FROM LeichMehl_PluFile_Config WHERE LeichMehl_PluFile_Config_ID=540012
;

-- 2024-01-29T16:34:23.207Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Produkt_Kategorie',Updated=TO_TIMESTAMP('2024-01-29 18:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540013
;

-- 2024-01-29T16:34:59.523Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Produktnummer',Updated=TO_TIMESTAMP('2024-01-29 18:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540014
;

-- 2024-01-29T16:35:28.976Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Produktname',Updated=TO_TIMESTAMP('2024-01-29 18:35:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540015
;

-- 2024-01-29T16:36:01.467Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Produktbeschreibung',Updated=TO_TIMESTAMP('2024-01-29 18:36:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540016
;

-- 2024-01-29T16:36:46.490Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Externe_ID',Updated=TO_TIMESTAMP('2024-01-29 18:36:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540018
;

-- 2024-01-29T16:37:44.528Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Masseinheit',Updated=TO_TIMESTAMP('2024-01-29 18:37:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540019
;

-- 2024-01-29T16:39:50.798Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Hersteller',Updated=TO_TIMESTAMP('2024-01-29 18:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540020
;

-- 2024-01-29T16:40:36.997Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Hersteller_Name',Updated=TO_TIMESTAMP('2024-01-29 18:40:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540021
;

-- 2024-01-29T16:41:13.354Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Hersteller_Nr',Updated=TO_TIMESTAMP('2024-01-29 18:41:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540022
;

-- 2024-01-29T16:42:07.422Z
UPDATE LeichMehl_PluFile_Config SET TargetFieldName='Auslaufdatum',Updated=TO_TIMESTAMP('2024-01-29 18:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE LeichMehl_PluFile_Config_ID=540023
;

-- 2024-01-29T16:42:13.069Z
DELETE FROM LeichMehl_PluFile_Config WHERE LeichMehl_PluFile_Config_ID=540024
;

-- 2024-01-29T16:42:15.630Z
DELETE FROM LeichMehl_PluFile_Config WHERE LeichMehl_PluFile_Config_ID=540025
;

