## `Leich + Mehl device` configuration
- `SOAP-Envelope` = no
- `XML-Header` = yes
- `data verwenden` = no


## metasfresh to LeichMehl
### Current data mapping

#### 1.`JsonExternalSystemRequest` computed from `window = 541024 - ExternalSystem_Config_LeichMehl`
- `JsonExternalSystemRequest.parameters.PP_Order_ID` - id of the selected manufacturing order to export
- `JsonExternalSystemRequest.parameters.Product_BaseFolderName` = `ExternalSystem_Config_LeichMehl.Product_BaseFolderName`
- `JsonExternalSystemRequest.parameters.TCP_PortNumber` = `ExternalSystem_Config_LeichMehl.TCP_PortNumber`
- `JsonExternalSystemRequest.parameters.TCP_Host` = `ExternalSystem_Config_LeichMehl.TCP_Host`
- `JsonExternalSystemRequest.parameters.ConfigMappings` see 1.1
- `JsonExternalSystemRequest.parameters.PluFileConfig` see 1.2

1.1 `ConfigMappings` - computed from product mapping set on window `541540 - tab ExternalSystem_Config_LeichMehl_ProductMapping` send on `JsonExternalSystemRequest.parameters.ConfigMappings`
- `JsonExternalSystemLeichMehlConfigProductMapping.pluFile` = `ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File` - file name of the plu file to be exported
- `JsonExternalSystemLeichMehlConfigProductMapping.productId` = `productId` from the selected manufacturing order

1.2 `JsonExternalSystemLeichMehlPluFileConfigs` - computed from plu file configs `LeichMehl_PluFile_Config` set on window `541116 - ExternalSystem_Config_LeichMehl` send on `JsonExternalSystemRequest.parameters.PluFileConfig`
- `JsonExternalSystemLeichMehlPluFileConfig.targetFieldName` = `LeichMehl_PluFile_Config.TargetFieldName`
    - Specifies the name of the field from the PLU file which will be updated.
- `JsonExternalSystemLeichMehlPluFileConfig.targetFieldType` = `LeichMehl_PluFile_Config.TargetFieldType`
    - Specifies the type of the field from the PLU file which will be updated
- `JsonExternalSystemLeichMehlPluFileConfig.replacePattern` = `LeichMehl_PluFile_Config.ReplaceRegExp`
    - Regular expression used when replacing the value from the matched field. With this configuration a full replacement can be done or a partial one.
        - Examples:
            - full replacement: given that there is a `value` = `Test sentence.`, `ReplaceRegExp` = `:*` and the new value is `TEST`, after replacement is performed `value` = `TEST`.
            - partial replacement: given that there is a `value` = `Test sentence.`, `ReplaceRegExp` = `.*(Test).*` and the new value is `Dummy test`, after replacement is performed `value` = `Dummy test sentence.`
- `JsonExternalSystemLeichMehlPluFileConfig.replacement` = `LeichMehl_PluFile_Config.Replacement`
    - Specifies the replacement value for the target field identified at the given JsonPath from the source object.
- `JsonExternalSystemLeichMehlPluFileConfig.replacementSource` = `LeichMehl_PluFile_Config.ReplacementSource`
    - Specifies the source object from where the replacement value for the target field will be taken.


#### 2. pull manufacturing order
-  pulled from metasfresh performing a `GET` request to endpoint `/api/v2/manufacturing/orders/{JsonExternalSystemRequest.parameters.PP_Order_ID}`
- all the values are compute from `PP_Order` table

|metasfresh-json response|metasfresh| Obs |
|--|--|--|
|JsonResponseManufacturingOrder|PP_Order| Obs |
|`orderId`|`PP_Order_ID`| --|
|`orgCode`|`AD_Org.Value`|computed from `PP_Order.AD_Org_ID`|
|`documentNo`|`DocumentNo`| -- |
|`description`|`Description`|-- |
|`finishGoodProduct`| -- | computed as `JsonProduct`, see 2.1, where `M_Product` is retrieved from `PP_Order.M_Product_ID`|
|`qtyToProduce`| -- | computed as `JsonQuantity`, see 2.2 |
|`dateOrdered`| `DateOrdered` | -- |
|`datePromised`| `DatePromised` | -- |
|`dateStartSchedule`| `DateStartSchedule` | -- |
|`productId`| `M_Product_ID` | -- |
|`bpartnerId`| `C_BPartner_ID` | computed as `List<JsonResponseManufacturingOrderBOMLine>`, see 2.3 |
|`components`|-- | -- |

2.1 `JsonProduct` computed from `M_Product`
|metasfresh-json response|metasfresh| Obs |
|--|--|--|
|JsonProduct|M_Product| Obs |
|`productNo`|`Value`|--|
|`name`|`Name`|--|
|`description`|`Description`|--|
|`documentNote`|`DocumentNote`|--|
|`packageSize`|`PackageSize`|--|
|`weight`|`Weight`|--|
|`stocked`|`IsStocked`|--|

2.2 `JsonQuantity` computed from `PP_Order` quantities
|metasfresh-json response|metasfresh| Obs |
|--|--|--|
|JsonConverter|PP_Order| Obs |
|`qty`| `QtyOrdered` | -- |
|`uomCode`| `C_UOM.X12DE355` | from `PP_Order.C_UOM_ID` |

2.3 `List<JsonResponseManufacturingOrderBOMLine>` computed from `PP_Order_BOMLine` retrieved as `PP_Order_BOMLine.PP_Order_ID`
|metasfresh-json response|metasfresh| Obs |
|--|--|--|
|JsonResponseManufacturingOrderBOMLine|PP_Order_BOMLine| Obs |
|`componentType`|`ComponentType`|  |
|`product`| -- |  computed as `JsonProduct`, see 2.1, where `M_Product` is retrieved from `PP_Order_BOMLine.M_Product_ID` |
|`qty`| -- |  computed as `JsonQuantity`, see 2.4 |

2.4 `JsonQuantity` computed from `PP_Order_BOMLine` quantities
|metasfresh-json response|metasfresh| Obs |
|--|--|--|
|JsonConverter|PP_Order_BOMLine| Obs |
|`qty`| `QtyRequiered` | -- |
|`uomCode`| `C_UOM.X12DE355` | from `PP_Order_BOMLine.C_UOM_ID` |

#### 3. pull product details from manufacturing order
-  pulled from metasfresh performing a `GET` request to endpoint `/api/v2/material/products/{JsonExternalSystemRequest.orgCode}/{JsonResponseManufacturingOrder.productId}`
- all the values are compute from `M_Product` table

|metasfresh-json response|metasfresh| Obs |
|--|--|--|
|JsonProduct|M_Product| Obs |
|`id`|`M_Product_ID`| --|
|`externalId`|`ExternalId`| --|
|`productNo`|`Value`| --|
|`productCategoryId`|`M_Product_Category_ID`| --|
|`manufacturerId`|`Manufacturer_ID`| --|
|`manufacturerNumber`|`ManufacturerArticleNumber`| --|
|`name`|`Name`| --|
|`description`|`Description`| --|
|`ean`|`UPC`| --|
|`uom`|`C_UOM.UOMSymbol`| from `M_Product.C_UOM_ID`|
|`bpartners`|--| computed as `List<JsonProductBPartner>`, see 3.1 |
|`albertaProductInfo`|--| does not apply for this flow |

3.1 `List<JsonProductBPartner>` computed from `C_BPartner_Product` retrieved as `C_BPartner_Product.M_Product_ID`

|metasfresh-json response|metasfresh| Obs |
|--|--|--|
|JsonProductBPartner|C_BPartner_Product| Obs |
|`bpartnerId`|`C_BPartner_ID`| --|
|`productNo`|`ProductNo`| --|
|`productName`|`ProductName`| --|
|`productDescription`|`ProductDescription`| --|
|`productCategory`|`ProductCategory`| --|
|`ean`|`UPC`| --|
|`vendor`|`UsedForVendor`| --|
|`currentVendor`|`IsCurrentVendor`| populated if `UsedForVendor = true` && `IsCurrentVendor = true`|
|`customer`|`UsedForCustomer`| --|
|`leadTimeInDays`|`DeliveryTime_Promised`| --|
|`excludedFromSale`|`IsExcludedFromSale`| --|
|`exclusionFromSaleReason`|`ExclusionFromSaleReason`| --|
|`excludedFromPurchase`|`IsExcludedFromPurchase`| --|
|`exclusionFromPurchaseReason`|`ExclusionFromPurchaseReason`| --|

