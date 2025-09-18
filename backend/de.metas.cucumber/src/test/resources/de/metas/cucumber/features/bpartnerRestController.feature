@from:cucumber
@ghActions:run_on_executor6
Feature:bpartner get using metasfresh api
  As a REST-API invoker
  I want want to be able to get bpartners updated since a certain time

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2048-01-02T08:00:00+01:00[Europe/Berlin]

    And remove external reference if exists:
      | ExternalSystem    | ExternalReference | Type             |
      | WooCommerce       | 1234              | BPartner         |
      | ProCareManagement | 2345              | BPartnerLocation |
      | Other             | 3456              | UserID           |



  @from:cucumber
  Scenario: get Partner request, as a REST-API invoker
  I want to be able to get products updated since a timestamp

    Given metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name | Value |
      | 001                  | 001  | 001   |

    And  metasfresh contains C_BPartners without locations:
      | Identifier | Name         | AD_Org_ID.Identifier |
      | 1234       | 1234 | 001                  |

    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | AD_Org_ID.Identifier |
      | 2345       | 1234                     | 001                  |


    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name | C_BPartner_ID.Identifier | AD_Org_ID.Identifier |
      | 3456                  | 3456 | 1234                     | 001                  |

#    And metasfresh contains S_ExternalReferences:
#      | ExternalSystem.Code    | ExternalReference | Type             |
#      | WooCommerce       | 1234              | BPartner         |
#      | ProCareManagement | 2345              | BPartnerLocation |
#      | Other             | 3456              | UserID           |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference | ExternalReferenceType.Code | RecordId.Identifier |
      | WooCommerce         | bp1234            | BPartner                   | 1234                |
      | ProCareManagement   | pbl2345           | BPartnerLocation           | 2345                |
      | Other               | u34565            | UserID                     | 3456                |

    When the metasfresh REST-API endpoint path 'api/v2/bpartner/byOrg/001/?since=2461507318000' receives a 'GET' request

    Then the metasfresh REST-API responds with
    """
   {
  "resultTimestamp": 2461561200000
,
  "totalSize": 1,
  "pageSize": 1,
  "items": [
    {
      "bpartner": {
        "metasfreshId": 1234,
        "code": "1234",
        "globalId": null,
        "active": true,
        "name": "1234",
        "companyName": "1234",
        "language": "de_DE",
        "group": "Standard",
        "vendor": false,
        "customer": false,
        "paymentRule": "OnCredit",
        "company": true,
        "vatId": null,
        "metasfreshUrl": "http://localhost:3000/window/123/2156426",
        "changeInfo": {
          "createdMillis": 2461561200000,
          "createdBy": 100,
          "lastUpdatedMillis": 2461561200000,
          "lastUpdatedBy": 100
        }
      },
      "locations": [
        {
          "metasfreshId": 2345,
          "name": 1234,
          "bpartnerName": null,
          "active": true,
          "postal": null,
          "city": null,
          "gln": null,
          "countryCode": "AD",
          "shipTo": false,
          "shipToDefault": false,
          "billTo": false,
          "billToDefault": false,
          "ephemeral": false,
          "visitorsAddress": false,
          "handoverLocation": false,
          "remitTo": false,
          "replicationLookupDefault": false,
          "vatId": null,
          "changeInfo": {
            "createdMillis": 2461561200000,
            "createdBy": 100,
            "lastUpdatedMillis": 2461561200000,
            "lastUpdatedBy": 100,
            "changeLogs": [
              {
                "fieldName": "shipToDefault",
                "updatedMillis": 2461561200000,
                "updatedBy": 100,
                "oldValue": "false",
                "newValue": "true"
              }
            ]
          }
        }
      ],
      "contacts": [
        {
          "metasfreshId": 2188224,
          "metasfreshBPartnerId": 2156426,
          "active": true,
          "name": "Smith, Maria",
          "firstName": "Maria",
          "lastName": "Smith",
          "email": "test@test.test",
          "newsletter": false,
          "shipToDefault": false,
          "billToDefault": false,
          "defaultContact": false,
          "sales": false,
          "salesDefault": false,
          "purchase": false,
          "purchaseDefault": false,
          "subjectMatter": false,
          "invoiceEmailEnabled": null,
          "metasfreshLocationId": 2205176,
          "changeInfo": {
            "createdMillis": 2461561200000,
            "createdBy": 100,
            "lastUpdatedMillis": 2461561200000,
            "lastUpdatedBy": 100,
            "changeLogs": []
          }
        }
      ]
    }
  ]
}
    """
#    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/products/001' and fulfills with '200' status code
#  """
#  {
#  "requestItems": [
#    {
#      "productIdentifier": "ext-ALBERTA-345",
#      "externalVersion": null,
#      "externalReferenceUrl": "www.ExternalReferenceURL.com",
#      "requestProduct": {
#        "code": "code345",
#        "codeSet": true,
#        "name": "Product_Test",
#        "nameSet": true,
#        "type": "ITEM",
#        "typeSet": true,
#        "uomCode": "PCE",
#        "uomCodeSet": true,
#        "gtinSet": true, "gtin": "0575095404663",
#        "description": "test_description",
#        "descriptionSet": true,
#        "discontinued": null,
#        "discontinuedSet": false,
#        "active": true,
#        "activeSet": true,
#        "stocked": null,
#        "stockedSet": false,
#        "productCategoryIdentifier": null,
#        "productCategoryIdentifierSet": false,
#        "syncAdvise": null,
#        "bpartnerProductItems": [
#          {
#            "bpartnerIdentifier": "ext-ALBERTA-345",
#            "bpartnerSet": true,
#            "active": true,
#            "activeSet": true,
#            "seqNo": 10,
#            "seqNoSet": true,
#            "productNo": "test",
#            "productNoSet": true,
#            "description": "test",
#            "descriptionSet": true,
#            "gtinSet": true, "gtin": "1101899104400",
#            "customerLabelName": "test",
#            "customerLabelNameSet": true,
#            "ingredients": "test",
#            "ingredientsSet": true,
#            "currentVendor": null,
#            "currentVendorSet": false,
#            "excludedFromSales": true,
#            "excludedFromSalesSet": true,
#            "exclusionFromSalesReason": "Test",
#            "exclusionFromSalesReasonSet": true,
#            "excludedFromPurchase": null,
#            "excludedFromPurchaseSet": false,
#            "exclusionFromPurchaseReason": null,
#            "exclusionFromPurchaseReasonSet": false,
#            "dropShip": null,
#            "dropShipSet": false,
#            "usedForVendor": null,
#            "usedForVendorSet": false
#          },
#          {
#            "bpartnerIdentifier": "ext-ALBERTA-456",
#            "bpartnerSet": true,
#            "active": true,
#            "activeSet": true,
#            "seqNo": 10,
#            "seqNoSet": true,
#            "productNo": "test",
#            "productNoSet": true,
#            "description": "test",
#            "descriptionSet": true,
#            "gtinSet": true, "gtin": "4418546988533",
#            "customerLabelName": "test",
#            "customerLabelNameSet": true,
#            "ingredients": "test",
#            "ingredientsSet": true,
#            "currentVendor": null,
#            "currentVendorSet": false,
#            "excludedFromSales": null,
#            "excludedFromSalesSet": false,
#            "exclusionFromSalesReason": null,
#            "exclusionFromSalesReasonSet": false,
#            "excludedFromPurchase": true,
#            "excludedFromPurchaseSet": true,
#            "exclusionFromPurchaseReason": "test",
#            "exclusionFromPurchaseReasonSet": true,
#            "dropShip": null,
#            "dropShipSet": false,
#            "usedForVendor": null,
#            "usedForVendorSet": false
#          }
#        ],
#        "uomConversions": [
#          {
#            "fromUomCode": "PCE",
#            "toUomCode": "KGM",
#            "fromToMultiplier": 0.25
#          },
#          {
#            "fromUomCode": "PCE",
#            "toUomCode": "GRM",
#            "fromToMultiplier": 0.00025
#          }
#        ]
#      }
#    }
#  ],
#  "syncAdvise": {
#    "ifNotExists": "CREATE",
#    "ifExists": "UPDATE_MERGE"
#  }
#}
#  """
#    Then locate product by external identifier
#      | M_Product_ID.Identifier | externalIdentifier |
#      | p_1                     | ext-ALBERTA-345    |
#    Then verify product info
#      | M_Product_ID.Identifier | Value   | Name         | ProductType | C_UOM_ID.X12DE355 | UPC           | GTIN          | Description      | IsActive |
#      | p_1                     | code345 | Product_Test | ITEM        | PCE               | 0575095404663 | 0575095404663 | test_description | true     |
#    Then locate bpartner by external identifier
#      | C_BPartner_ID.Identifier | externalIdentifier |
#      | bpartner_1               | ext-ALBERTA-345    |
#      | bpartner_2               | ext-ALBERTA-456    |
#    And locate bpartner product by product and bpartner
#      | C_BPartner_Product_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier |
#      | bp_1                             | p_1                     | bpartner_1               |
#      | bp_2                             | p_1                     | bpartner_2               |
#    And verify bpartner product info
#      | C_BPartner_Product_ID.Identifier | IsActive | SeqNo | ProductNo | Description | EAN_CU        | GTIN          | CustomerLabelName | Ingredients | IsExcludedFromSale | ExclusionFromSaleReason | IsExcludedFromPurchase | ExclusionFromPurchaseReason |
#      | bp_1                             | true     | 10    | test      | test        | 1101899104400 | 1101899104400 | test              | test        | true               | Test                    | false                  | null                        |
#      | bp_2                             | true     | 10    | test      | test        | 4418546988533 | 4418546988533 | test              | test        | false              | null                    | true                   | test                        |
#    And verify that S_ExternalReference was created
#      | ExternalSystem | Type    | ExternalReference | ExternalReferenceURL         |
#      | ALBERTA        | Product | 345               | www.ExternalReferenceURL.com |
#    And validate C_UOM_Conversion:
#      | M_Product_ID.Identifier | C_UOM_ID.X12DE355 | C_UOM_To_ID.X12DE355 | MultiplyRate |
#      | p_1                     | PCE               | KGM                  | 0.25         |
#      | p_1                     | PCE               | GRM                  | 0.00025      |
#
#  @from:cucumber
#  Scenario: get Product, as a REST-API invoker
#  I want to be able to retrieve products
#
#    And metasfresh contains S_ExternalReferences
#      | ExternalSystem.Code | ExternalReference | Type     |
#      | ALBERTA             | 345               | BPartner |
#      | ALBERTA             | 456               | BPartner |
#
#    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/products/001' and fulfills with '200' status code
#  """
#  {
#  "requestItems": [
#    {
#      "productIdentifier": "ext-ALBERTA-345",
#      "externalVersion": null,
#      "externalReferenceUrl": "www.ExternalReferenceURL.com",
#      "requestProduct": {
#        "code": "code345_2",
#        "codeSet": true,
#        "name": "Product_Test_2",
#        "nameSet": true,
#        "type": "ITEM",
#        "typeSet": true,
#        "uomCode": "PCE",
#        "uomCodeSet": true,
#        "gtinSet": true, "gtin": "0575095404663",
#        "description": "test_description",
#        "descriptionSet": true,
#        "discontinued": null,
#        "discontinuedSet": false,
#        "active": true,
#        "activeSet": true,
#        "stocked": null,
#        "stockedSet": false,
#        "productCategoryIdentifier": null,
#        "productCategoryIdentifierSet": false,
#        "syncAdvise": null,
#        "bpartnerProductItems": [
#          {
#            "bpartnerIdentifier": "ext-ALBERTA-345",
#            "bpartnerSet": true,
#            "active": true,
#            "activeSet": true,
#            "seqNo": 10,
#            "seqNoSet": true,
#            "productNo": "test",
#            "productNoSet": true,
#            "description": "test",
#            "descriptionSet": true,
#            "gtinSet": true, "gtin": "1101899104400",
#            "customerLabelName": "test",
#            "customerLabelNameSet": true,
#            "ingredients": "test",
#            "ingredientsSet": true,
#            "currentVendor": null,
#            "currentVendorSet": false,
#            "excludedFromSales": true,
#            "excludedFromSalesSet": true,
#            "exclusionFromSalesReason": "testForSale",
#            "exclusionFromSalesReasonSet": true,
#            "excludedFromPurchase": true,
#            "excludedFromPurchaseSet": true,
#            "exclusionFromPurchaseReason": "testForPurchase",
#            "exclusionFromPurchaseReasonSet": true,
#            "dropShip": null,
#            "dropShipSet": false,
#            "usedForVendor": null,
#            "usedForVendorSet": false
#          }
#        ]
#      }
#    }
#  ],
#  "syncAdvise": {
#    "ifNotExists": "CREATE",
#    "ifExists": "UPDATE_MERGE"
#  }
#}
#  """
#
#    Then locate product by external identifier
#      | M_Product_ID.Identifier | externalIdentifier |
#      | p_1                     | ext-ALBERTA-345    |
#    Then verify product info
#      | M_Product_ID.Identifier | Value     | Name           | ProductType | C_UOM_ID.X12DE355 | UPC           | GTIN          | Description      | IsActive |
#      | p_1                     | code345_2 | Product_Test_2 | ITEM        | PCE               | 0575095404663 | 0575095404663 | test_description | true     |
#    Then locate bpartner by external identifier
#      | C_BPartner_ID.Identifier | externalIdentifier |
#      | bpartner_1               | ext-ALBERTA-345    |
#    And locate bpartner product by product and bpartner
#      | C_BPartner_Product_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier |
#      | bp_1                             | p_1                     | bpartner_1               |
#    And verify bpartner product info
#      | C_BPartner_Product_ID.Identifier | IsActive | SeqNo | ProductNo | Description | EAN_CU        | GTIN          | CustomerLabelName | Ingredients | IsExcludedFromSale | ExclusionFromSaleReason | IsExcludedFromPurchase | ExclusionFromPurchaseReason |
#      | bp_1                             | true     | 10    | test      | test        | 1101899104400 | 1101899104400 | test              | test        | true               | testForSale             | true                   | testForPurchase             |
#    And verify that S_ExternalReference was created
#      | ExternalSystem | Type    | ExternalReference | ExternalReferenceURL         |
#      | ALBERTA        | Product | 345               | www.ExternalReferenceURL.com |
#    When a 'GET' request with the below payload is sent to the metasfresh REST-API 'api/v2/products' and fulfills with '200' status code
#"""
#"""
#    Then validate get products response
#      | M_Product_ID.Identifier | Value     | Name           | UOMSymbol | GTIN          | Description      | C_BPartner_ID.Identifier | bpartners.ProductNo | bpartners.IsExcludedFromSale | bpartners.ExclusionFromSaleReason | bpartners.IsExcludedFromPurchase | bpartners.ExclusionFromPurchaseReason |
#      | p_1                     | code345_2 | Product_Test_2 | Stk       | 0575095404663 | test_description | bpartner_1               | test                | true                         | testForSale                       | true                             | testForPurchase                       |
#
#
#  @from:cucumber
#  Scenario: Retrieve product by external identifier
#
#    Given load M_Product_Category:
#      | M_Product_Category_ID.Identifier | Name     | Value    |
#      | standard_category                | Standard | Standard |
#
#    And metasfresh contains M_Products:
#      | Identifier | M_Product_Category_ID |
#      | product_1  | standard_category     |
#
#    And metasfresh contains C_BPartners:
#      | Identifier | IsVendor | IsCustomer |
#      | bpartner_1 | N        | Y          |
#
#    And metasfresh contains C_BPartner_Product
#      | C_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.IsExcludedFromSale | OPT.ExclusionFromSaleReason | OPT.IsExcludedFromPurchase | OPT.ExclusionFromPurchaseReason | OPT.ProductNo | GTIN          |
#      | bpartner_1               | product_1               | true                   | testForSale                 | true                       | testForPurchase                 | bpProductNo   | 1101899104400 |
#
#    And metasfresh contains S_ExternalReferences:
#      | ExternalSystem.Code | ExternalReference  | ExternalReferenceType.Code | RecordId.Identifier |
#      | LeichUndMehl        | productExternalRef | Product                    | product_1           |
#
#    When the metasfresh REST-API endpoint path 'api/v2/material/products/001/ext-LeichUndMehl-productExternalRef' receives a 'GET' request
#
#    Then validate retrieve product response
#      | M_Product_ID.Identifier | UomSymbol | M_Product_Category_ID.Identifier | C_BPartner_ID.Identifier | bpartners.ProductNo | bpartners.IsExcludedFromSale | bpartners.ExclusionFromSaleReason | bpartners.IsExcludedFromPurchase | bpartners.ExclusionFromPurchaseReason | bpartners.ean |
#      | product_1               | Stk       | standard_category                | bpartner_1               | bpProductNo         | true                         | testForSale                       | true                             | testForPurchase                       | 1101899104400 |
#

