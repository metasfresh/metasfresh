@from:cucumber
Feature:product get/create/update using metasfresh api

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And remove external reference if exists:
      | ExternalSystem | ExternalReference | Type     |
      | ALBERTA        | 345               | Product  |
      | ALBERTA        | 345               | BPartner |
      | ALBERTA        | 456               | BPartner |
    And no M_Quality_Attribute data is found
    And no product with value 'code345' exists

  @from:cucumber
  Scenario: create Product request, as a REST-API invoker
  I want to be able to upsert products

    Given metasfresh contains S_ExternalReferences
      | ExternalSystem | ExternalReference | Type     |
      | ALBERTA        | 345               | BPartner |
      | ALBERTA        | 456               | BPartner |

    And metasfresh contains M_SectionCode:
      | M_SectionCode_ID.Identifier | Value                   |
      | ALBERTA_345_sectionCode     | ALBERTA_345_sectionCode |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/products/001' and fulfills with '200' status code
  """
  {
  "requestItems": [
    {
      "productIdentifier": "ext-ALBERTA-345",
      "externalVersion": null,
      "externalReferenceUrl": "www.ExternalReferenceURL.com",
      "externalSystemConfigId": 540000,
      "isReadOnlyInMetasfresh": false,
      "requestProduct": {
        "code": "code345",
        "codeSet": true,
        "name": "Product_Test",
        "nameSet": true,
        "type": "ITEM",
        "typeSet": true,
        "uomCode": "PCE",
        "uomCodeSet": true,
        "ean": "ean_test",
        "eanSet": true,
        "gtin": "gtin_test",
        "gtinSet": true,
        "description": "test_description",
        "descriptionSet": true,
        "discontinued": null,
        "discontinuedSet": false,
        "active": true,
        "activeSet": true,
        "stocked": null,
        "stockedSet": false,
        "productCategoryIdentifier": null,
        "productCategoryIdentifierSet": false,
        "syncAdvise": null,
        "sectionCode":"ALBERTA_345_sectionCode",
        "sectionCodeSet": true,
        "purchased":true,
        "purchasedSet":true,
        "sapProductHierarchy": "HH",
        "sapProductHierarchySet": true,
        "bpartnerProductItems": [
          {
            "bpartnerIdentifier": "ext-ALBERTA-345",
            "bpartnerSet": true,
            "active": true,
            "activeSet": true,
            "seqNo": 10,
            "seqNoSet": true,
            "productNo": "test",
            "productNoSet": true,
            "description": "test",
            "descriptionSet": true,
            "cuEAN": "ean_test",
            "cuEANSet": true,
            "gtin": "gtin_test",
            "gtinSet": true,
            "customerLabelName": "test",
            "customerLabelNameSet": true,
            "ingredients": "test",
            "ingredientsSet": true,
            "currentVendor": true,
            "currentVendorSet": true,
            "excludedFromSales": true,
            "excludedFromSalesSet": true,
            "exclusionFromSalesReason": "Test",
            "exclusionFromSalesReasonSet": true,
            "excludedFromPurchase": null,
            "excludedFromPurchaseSet": false,
            "exclusionFromPurchaseReason": null,
            "exclusionFromPurchaseReasonSet": false,
            "dropShip": null,
            "dropShipSet": false,
            "usedForVendor": null,
            "usedForVendorSet": false
          },
          {
            "bpartnerIdentifier": "ext-ALBERTA-456",
            "bpartnerSet": true,
            "active": true,
            "activeSet": true,
            "seqNo": 10,
            "seqNoSet": true,
            "productNo": "test",
            "productNoSet": true,
            "description": "test",
            "descriptionSet": true,
            "cuEAN": "ean_test",
            "cuEANSet": true,
            "gtin": "gtin_test",
            "gtinSet": true,
            "customerLabelName": "test",
            "customerLabelNameSet": true,
            "ingredients": "test",
            "ingredientsSet": true,
            "currentVendor": null,
            "currentVendorSet": false,
            "excludedFromSales": null,
            "excludedFromSalesSet": false,
            "exclusionFromSalesReason": null,
            "exclusionFromSalesReasonSet": false,
            "excludedFromPurchase": true,
            "excludedFromPurchaseSet": true,
            "exclusionFromPurchaseReason": "test",
            "exclusionFromPurchaseReasonSet": true,
            "dropShip": null,
            "dropShipSet": false,
            "usedForVendor": null,
            "usedForVendorSet": false
          }
        ],
        "qualityAttributes": {
            "qualityAttributeLabels": [
              "BIO",
              "EU_NON_EU_AGRICULTURE"
            ],
            "syncAdvise": {
              "ifNotExists": "CREATE",
              "ifExists": "REPLACE"
            }
         }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
  """
    Then locate product by external identifier
      | M_Product_ID.Identifier | externalIdentifier |
      | p_1                     | ext-ALBERTA-345    |
    Then verify product info
      | M_Product_ID.Identifier | Value   | Name         | ProductType | C_UOM_ID.X12DE355 | UPC      | GTIN      | Description      | IsActive | OPT.M_SectionCode_ID.Value | OPT.IsPurchased | OPT.SAP_ProductHierarchy |
      | p_1                     | code345 | Product_Test | ITEM        | PCE               | ean_test | gtin_test | test_description | true     | ALBERTA_345_sectionCode    | true            | HH                       |
    Then locate bpartner by external identifier
      | C_BPartner_ID.Identifier | externalIdentifier |
      | bpartner_1               | ext-ALBERTA-345    |
      | bpartner_2               | ext-ALBERTA-456    |
    And locate bpartner product by product and bpartner
      | C_BPartner_Product_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier |
      | bp_1                             | p_1                     | bpartner_1               |
      | bp_2                             | p_1                     | bpartner_2               |
    And verify bpartner product info
      | C_BPartner_Product_ID.Identifier | IsActive | SeqNo | ProductNo | Description | EAN_CU   | GTIN      | CustomerLabelName | Ingredients | IsExcludedFromSale | ExclusionFromSaleReason | IsExcludedFromPurchase | ExclusionFromPurchaseReason | OPT.IsCurrentVendor |
      | bp_1                             | true     | 10    | test      | test        | ean_test | gtin_test | test              | test        | true               | Test                    | false                  | null                        | true                |
      | bp_2                             | true     | 10    | test      | test        | ean_test | gtin_test | test              | test        | false              | null                    | true                   | test                        | false               |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type    | ExternalReference | ExternalReferenceURL         | OPT.ExternalSystem_Config_ID | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | Product | 345               | www.ExternalReferenceURL.com | 540000                       | false                      |
    And validate created M_Quality_Attributes for product: p_1
      | OPT.QualityAttribute  |
      | BearbeitetBio         |
      | EU/Non-EU-Agriculture |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API '/api/v2/products/001' and fulfills with '200' status code
"""
{
  "requestItems": [
    {
      "productIdentifier": "ext-ALBERTA-345",
      "requestProduct": {
        "code": "code345",
        "codeSet": true,
        "bpartnerProductItems": [
          {
            "bpartnerIdentifier": "ext-ALBERTA-345",
            "bpartnerSet": true,
            "currentVendor": true,
            "currentVendorSet": true
          },
          {
            "bpartnerIdentifier": "ext-ALBERTA-456",
            "bpartnerSet": true,
            "currentVendor": true,
            "currentVendorSet": true
          }
        ],
        "qualityAttributes": {
            "qualityAttributeLabels": [
              "HALAL"
            ],
            "syncAdvise": {
              "ifNotExists": "CREATE",
              "ifExists": "REPLACE"
            }
         }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
"""

    Then locate bpartner product by product and bpartner
      | C_BPartner_Product_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier |
      | bp_1                             | p_1                     | bpartner_1               |
      | bp_2                             | p_1                     | bpartner_2               |

    And verify bpartner product info
      | C_BPartner_Product_ID.Identifier | IsActive | SeqNo | ProductNo | Description | EAN_CU   | GTIN      | CustomerLabelName | Ingredients | IsExcludedFromSale | ExclusionFromSaleReason | IsExcludedFromPurchase | ExclusionFromPurchaseReason | OPT.IsCurrentVendor |
      | bp_1                             | true     | 10    | test      | test        | ean_test | gtin_test | test              | test        | true               | Test                    | false                  | null                        | false               |
      | bp_2                             | true     | 10    | test      | test        | ean_test | gtin_test | test              | test        | false              | null                    | true                   | test                        | true                |
    And validate created M_Quality_Attributes for product: p_1
      | OPT.QualityAttribute |
      | Halal                |

  @from:cucumber
  Scenario: get Product, as a REST-API invoker
  I want to be able to retrieve products

    And metasfresh contains S_ExternalReferences
      | ExternalSystem | ExternalReference | Type     |
      | ALBERTA        | 345               | BPartner |
      | ALBERTA        | 456               | BPartner |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/products/001' and fulfills with '200' status code
  """
  {
  "requestItems": [
    {
      "productIdentifier": "ext-ALBERTA-345",
      "externalVersion": null,
      "externalReferenceUrl": "www.ExternalReferenceURL.com",
      "externalSystemConfigId": 540000,
      "isReadOnlyInMetasfresh": true,
      "requestProduct": {
        "code": "code345_2",
        "codeSet": true,
        "name": "Product_Test_2",
        "nameSet": true,
        "type": "ITEM",
        "typeSet": true,
        "uomCode": "PCE",
        "uomCodeSet": true,
        "ean": "ean_test",
        "eanSet": true,
        "gtin": "gtin_test",
        "gtinSet": true,
        "description": "test_description",
        "descriptionSet": true,
        "discontinued": null,
        "discontinuedSet": false,
        "active": true,
        "activeSet": true,
        "stocked": null,
        "stockedSet": false,
        "productCategoryIdentifier": null,
        "productCategoryIdentifierSet": false,
        "syncAdvise": null,
        "bpartnerProductItems": [
          {
            "bpartnerIdentifier": "ext-ALBERTA-345",
            "bpartnerSet": true,
            "active": true,
            "activeSet": true,
            "seqNo": 10,
            "seqNoSet": true,
            "productNo": "test",
            "productNoSet": true,
            "description": "test",
            "descriptionSet": true,
            "cuEAN": "ean_test",
            "cuEANSet": true,
            "gtin": "gtin_test",
            "gtinSet": true,
            "customerLabelName": "test",
            "customerLabelNameSet": true,
            "ingredients": "test",
            "ingredientsSet": true,
            "currentVendor": null,
            "currentVendorSet": false,
            "excludedFromSales": true,
            "excludedFromSalesSet": true,
            "exclusionFromSalesReason": "testForSale",
            "exclusionFromSalesReasonSet": true,
            "excludedFromPurchase": true,
            "excludedFromPurchaseSet": true,
            "exclusionFromPurchaseReason": "testForPurchase",
            "exclusionFromPurchaseReasonSet": true,
            "dropShip": null,
            "dropShipSet": false,
            "usedForVendor": null,
            "usedForVendorSet": false
          }
        ]
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
  """

    Then locate product by external identifier
      | M_Product_ID.Identifier | externalIdentifier |
      | p_1                     | ext-ALBERTA-345    |
    Then verify product info
      | M_Product_ID.Identifier | Value     | Name           | ProductType | C_UOM_ID.X12DE355 | UPC      | GTIN      | Description      | IsActive |
      | p_1                     | code345_2 | Product_Test_2 | ITEM        | PCE               | ean_test | gtin_test | test_description | true     |
    Then locate bpartner by external identifier
      | C_BPartner_ID.Identifier | externalIdentifier |
      | bpartner_1               | ext-ALBERTA-345    |
    And locate bpartner product by product and bpartner
      | C_BPartner_Product_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier |
      | bp_1                             | p_1                     | bpartner_1               |
    And verify bpartner product info
      | C_BPartner_Product_ID.Identifier | IsActive | SeqNo | ProductNo | Description | EAN_CU   | GTIN      | CustomerLabelName | Ingredients | IsExcludedFromSale | ExclusionFromSaleReason | IsExcludedFromPurchase | ExclusionFromPurchaseReason |
      | bp_1                             | true     | 10    | test      | test        | ean_test | gtin_test | test              | test        | true               | testForSale             | true                   | testForPurchase             |
    And verify that S_ExternalReference was created
      | ExternalSystem | Type    | ExternalReference | ExternalReferenceURL         | OPT.ExternalSystem_Config_ID | OPT.IsReadOnlyInMetasfresh |
      | ALBERTA        | Product | 345               | www.ExternalReferenceURL.com | 540000                       | true                       |
    When a 'GET' request with the below payload is sent to the metasfresh REST-API 'api/v2/products' and fulfills with '200' status code
"""
"""
    Then validate get products response
      | M_Product_ID.Identifier | Value     | Name           | UOMSymbol | UPC      | Description      | C_BPartner_ID.Identifier | bpartners.ProductNo | bpartners.IsExcludedFromSale | bpartners.ExclusionFromSaleReason | bpartners.IsExcludedFromPurchase | bpartners.ExclusionFromPurchaseReason |
      | p_1                     | code345_2 | Product_Test_2 | Stk       | ean_test | test_description | bpartner_1               | test                | true                         | testForSale                       | true                             | testForPurchase                       |


  @from:cucumber
  Scenario: Retrieve product by external identifier

    Given load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |

    And metasfresh contains M_Products:
      | Identifier | Value        | Name        | OPT.M_Product_Category_ID.Identifier |
      | product_1  | productValue | productName | standard_category                    |

    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsVendor | OPT.IsCustomer |
      | bpartner_1 | BPartnerName | N            | Y              |

    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.IsExcludedFromSale | OPT.ExclusionFromSaleReason | OPT.IsExcludedFromPurchase | OPT.ExclusionFromPurchaseReason | OPT.ProductNo | OPT.UPC |
      | bpartner_1               | product_1               | true                   | testForSale                 | true                       | testForPurchase                 | bpProductNo   | ean     |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference  | ExternalReferenceType.Code | RecordId.Identifier |
      | LeichUndMehl        | productExternalRef | Product                    | product_1           |

    When the metasfresh REST-API endpoint path 'api/v2/material/products/001/ext-LeichUndMehl-productExternalRef' receives a 'GET' request

    Then validate retrieve product response
      | M_Product_ID.Identifier | Name        | UomSymbol | M_Product_Category_ID.Identifier | C_BPartner_ID.Identifier | bpartners.ProductNo | bpartners.IsExcludedFromSale | bpartners.ExclusionFromSaleReason | bpartners.IsExcludedFromPurchase | bpartners.ExclusionFromPurchaseReason | bpartners.ean |
      | product_1               | productName | Stk       | standard_category                | bpartner_1               | bpProductNo         | true                         | testForSale                       | true                             | testForPurchase                       | ean           |

  @from:cucumber
  @Id:S0295_100
  Scenario: as a REST-API invoker
  I want to be able to create product warehouse assignments

    Given metasfresh contains M_Warehouse:
      | M_Warehouse_ID.Identifier | Value                     | Name                     |
      | warehouse_1               | warehouseValue_07122023_1 | warehouseName_07122023_1 |
      | warehouse_2               | warehouseValue_07122023_2 | warehouseName_07122023_2 |
      | warehouse_3               | warehouseValue_07122023_3 | warehouseName_07122023_3 |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/products/001' and fulfills with '200' status code
  """
  {
  "requestItems": [
    {
      "productIdentifier": "ext-ALBERTA-07122023",
      "requestProduct": {
        "code": "code346",
        "codeSet": true,
        "name": "Product_Test_07122023",
        "nameSet": true,
        "type": "ITEM",
        "typeSet": true,
        "uomCode": "PCE",
        "uomCodeSet": true,
        "discontinued": null,
        "discontinuedSet": false,
        "active": true,
        "activeSet": true,
        "stocked": null,
        "stockedSet": false,
        "productCategoryIdentifier": null,
        "productCategoryIdentifierSet": false,
        "syncAdvise": null,
        "purchased":true,
        "purchasedSet":true,
        "warehouseAssignments": {
          "warehouseIdentifiers": [ "name-warehouseName_07122023_1", "val-warehouseValue_07122023_2" ],
          "syncAdvise": {
              "ifNotExists": "CREATE",
              "ifExists": "REPLACE"
            }
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
  """

    Then locate product by external identifier
      | M_Product_ID.Identifier | externalIdentifier   |
      | p_1                     | ext-ALBERTA-07122023 |

    And locate warehouse assignments
      | M_Product_ID.Identifier | M_Product_Warehouse_ID.Identifier |
      | p_1                     | a_1,a_2                           |

    And validate warehouse assignments
      | M_Product_Warehouse_ID.Identifier | M_Warehouse_ID.Identifier | M_Product_ID.Identifier |
      | a_1                               | warehouse_1               | p_1                     |
      | a_2                               | warehouse_2               | p_1                     |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/products/001' and fulfills with '200' status code
  """
  {
  "requestItems": [
    {
      "productIdentifier": "ext-ALBERTA-07122023",
      "requestProduct": {
        "code": "code346",
        "codeSet": true,
        "name": "Product_Test_07122023",
        "nameSet": true,
        "type": "ITEM",
        "typeSet": true,
        "uomCode": "PCE",
        "uomCodeSet": true,
        "discontinued": null,
        "discontinuedSet": false,
        "active": true,
        "activeSet": true,
        "stocked": null,
        "stockedSet": false,
        "productCategoryIdentifier": null,
        "productCategoryIdentifierSet": false,
        "syncAdvise": null,
        "purchased":true,
        "purchasedSet":true,
        "warehouseAssignments": {
          "warehouseIdentifiers": [ "name-warehouseName_07122023_3"],
          "syncAdvise": {
              "ifNotExists": "CREATE",
              "ifExists": "REPLACE"
            }
        }
      }
    }
  ],
  "syncAdvise": {
    "ifNotExists": "CREATE",
    "ifExists": "UPDATE_MERGE"
  }
}
  """
    Then locate warehouse assignments
      | M_Product_ID.Identifier | M_Product_Warehouse_ID.Identifier |
      | p_1                     | a_3                               |

    And validate warehouse assignments
      | M_Product_Warehouse_ID.Identifier | M_Warehouse_ID.Identifier | M_Product_ID.Identifier |
      | a_3                               | warehouse_3               | p_1                     |
