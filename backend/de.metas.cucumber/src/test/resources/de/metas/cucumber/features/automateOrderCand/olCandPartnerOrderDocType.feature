@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00100_Sales_Order_Window
@allure.label.feature:F00122_Sales_Order_Candidate_to_Order
@ghActions:run_on_executor3
Feature: Order type of the order created from an order candidate
  As a user
  I want an order candidate to create an order of the intended order type (e.g. a prepay order that waits for the payment)
  so that the order is not rejected only because it is not completed right away

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier | Name      | Value     | OPT.IsActive |
      | prepay_ps  | prepay_ps | prepay_ps | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | prepay_pl  | prepay_ps                     | DE                        | EUR                 | prepay_pl | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | prepay_plv | prepay_pl                 | prepay_plv | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier     | Name           |
      | prepay_product | prepay_product |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | prepay_pp  | prepay_plv                        | prepay_product          | 10.0     | PCE               | Normal                        |
    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID |
      | SOO         | PR         | prepayType   |
      | SOO         | SO         | standardType |

  Scenario: explicit prepay order type completes waiting for payment
    Given metasfresh contains C_BPartners:
      | Identifier      | Name            | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | prepay_customer | prepay_customer | Y              | N            | prepay_ps                     | prepay_customer_loc        | 4056789012340 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier |
      | prepay_customer_loc | 4056789012340 | prepay_customer          |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "prepay_02112026",
            "externalLineId": "prepay_02112026_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012340",
                "bpartnerLocationIdentifier": "gln-4056789012340"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "PrepayOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "prepay_02112026",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "prepay_02112026",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId      | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | prepay_02112026 | prepay_customer          | SOO         | prepayType              | true      | WP        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | prepay_customer          | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by prepay_customer has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "prepay_02112026",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then the business partner identified by prepay_customer has exactly 1 order

  Scenario: partner prepay type, candidate without type
    Given metasfresh contains C_BPartners:
      | Identifier     | Name           | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.SO_DocTypeTarget_ID.Identifier |
      | partner_prepay | partner_prepay | Y              | N            | prepay_ps                     | partner_prepay_loc         | 4056789012357 | prepayType                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier |
      | partner_prepay_loc | 4056789012357 | partner_prepay           |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_prepay_1",
            "externalLineId": "partner_prepay_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012357",
                "bpartnerLocationIdentifier": "gln-4056789012357"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_prepay_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_prepay_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId       | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_prepay_1 | partner_prepay           | SOO         | prepayType              | true      | WP        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_prepay           | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_prepay has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

  Scenario: candidate's own type wins
    Given metasfresh contains C_BPartners:
      | Identifier       | Name             | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.SO_DocTypeTarget_ID.Identifier |
      | partner_candtype | partner_candtype | Y              | N            | prepay_ps                     | partner_candtype_loc       | 4056789012364 | prepayType                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN           | C_BPartner_ID.Identifier |
      | partner_candtype_loc | 4056789012364 | partner_candtype         |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_candtype_1",
            "externalLineId": "partner_candtype_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012364",
                "bpartnerLocationIdentifier": "gln-4056789012364"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_candtype_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_candtype_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId         | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_candtype_1 | partner_candtype         | SOO         | standardType            | true      | CO        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_candtype         | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_candtype has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

  Scenario: partner without type uses the processor default
    Given metasfresh contains C_BPartners:
      | Identifier     | Name           | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | partner_notype | partner_notype | Y              | N            | prepay_ps                     | partner_notype_loc         | 4056789012371 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier |
      | partner_notype_loc | 4056789012371 | partner_notype           |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_notype_1",
            "externalLineId": "partner_notype_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012371",
                "bpartnerLocationIdentifier": "gln-4056789012371"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_notype_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_notype_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId       | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_notype_1 | partner_notype           | SOO         | standardType            | true      | CO        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_notype           | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_notype has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

  Scenario: bill partner differs, the buyer's type is used
    Given metasfresh contains C_BPartners:
      | Identifier    | Name          | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.SO_DocTypeTarget_ID.Identifier |
      | partner_buyer | partner_buyer | Y              | N            | prepay_ps                     | partner_buyer_loc          | 4056789012388 | prepayType                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier |
      | partner_buyer_loc | 4056789012388 | partner_buyer            |
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | partner_bill | partner_bill | Y              | N            | prepay_ps                     | partner_bill_loc           | 4056789012395 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier |
      | partner_bill_loc | 4056789012395 | partner_bill             |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_buyer_1",
            "externalLineId": "partner_buyer_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012388",
                "bpartnerLocationIdentifier": "gln-4056789012388"
            },
            "billBPartner": {
                "bpartnerIdentifier": "gln-4056789012395",
                "bpartnerLocationIdentifier": "gln-4056789012395"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_buyer_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_buyer_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId      | C_BPartner_ID.Identifier | Bill_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_buyer_1 | partner_buyer            | partner_bill                | SOO         | prepayType              | true      | WP        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_buyer            | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_buyer has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

  Scenario: inactive partner type uses the processor default
    Given metasfresh contains C_BPartners:
      | Identifier       | Name             | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.SO_DocTypeTarget_ID.Identifier |
      | partner_inactive | partner_inactive | Y              | N            | prepay_ps                     | partner_inactive_loc       | 4056789012401 | prepayType                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN           | C_BPartner_ID.Identifier |
      | partner_inactive_loc | 4056789012401 | partner_inactive         |
    And update C_DocType:
      | C_DocType_ID | IsActive |
      | prepayType   | false    |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_inactive_1",
            "externalLineId": "partner_inactive_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012401",
                "bpartnerLocationIdentifier": "gln-4056789012401"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_inactive_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_inactive_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId         | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_inactive_1 | partner_inactive         | SOO         | standardType            | true      | CO        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_inactive         | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_inactive has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

  Scenario: foreign-org partner type uses the processor default
    Given metasfresh contains C_BPartners:
      | Identifier         | Name               | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.SO_DocTypeTarget_ID.Identifier |
      | partner_foreignorg | partner_foreignorg | Y              | N            | prepay_ps                     | partner_foreignorg_loc     | 4056789012418 | prepayType                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier             | GLN           | C_BPartner_ID.Identifier |
      | partner_foreignorg_loc | 4056789012418 | partner_foreignorg       |
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name        |
      | org_foreign          | org_foreign |
    And update C_DocType:
      | C_DocType_ID | OPT.AD_Org_ID.Identifier |
      | prepayType   | org_foreign              |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_foreignorg_1",
            "externalLineId": "partner_foreignorg_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012418",
                "bpartnerLocationIdentifier": "gln-4056789012418"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_foreignorg_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_foreignorg_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId           | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_foreignorg_1 | partner_foreignorg       | SOO         | standardType            | true      | CO        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_foreignorg       | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_foreignorg has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

  Scenario: candidate-org partner type is used
    Given metasfresh contains C_BPartners:
      | Identifier      | Name            | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.SO_DocTypeTarget_ID.Identifier |
      | partner_candorg | partner_candorg | Y              | N            | prepay_ps                     | partner_candorg_loc        | 4056789012425 | prepayType                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier |
      | partner_candorg_loc | 4056789012425 | partner_candorg          |
    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | org_001              | 001   |
    And update C_DocType:
      | C_DocType_ID | OPT.AD_Org_ID.Identifier |
      | prepayType   | org_001                  |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_candorg_1",
            "externalLineId": "partner_candorg_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012425",
                "bpartnerLocationIdentifier": "gln-4056789012425"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_candorg_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_candorg_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId        | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_candorg_1 | partner_candorg          | SOO         | prepayType              | true      | WP        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_candorg          | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_candorg has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered

  Scenario: partner type that is not a sales order type uses the processor default
    Given load C_DocType:
      | DocBaseType | C_DocType_ID |
      | ARI         | invoiceType  |
    And metasfresh contains C_BPartners:
      | Identifier     | Name           | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | OPT.SO_DocTypeTarget_ID.Identifier |
      | partner_nonsoo | partner_nonsoo | Y              | N            | prepay_ps                     | partner_nonsoo_loc         | 4056789012432 | invoiceType                        |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier |
      | partner_nonsoo_loc | 4056789012432 | partner_nonsoo           |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
"""
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "partner_nonsoo_1",
            "externalLineId": "partner_nonsoo_1_0",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4056789012432",
                "bpartnerLocationIdentifier": "gln-4056789012432"
            },
            "dateRequired": "2021-12-02",
            "dateOrdered": "2021-11-20",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-prepay_product",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "partner_nonsoo_1",
            "deliveryViaRule": "S"
        }
    ]
}
"""
    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand                 |
    And the existing AD_Issue records are remembered

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "partner_nonsoo_1",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order                 |
    And validate the created orders
      | C_Order_ID.Identifier | externalId       | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order                 | partner_nonsoo_1 | partner_nonsoo           | SOO         | standardType            | true      | CO        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed | OPT.HasNote |
      | olCand                 | partner_nonsoo           | prepay_product          | 2          | N       | Y             | N           |
    And the business partner identified by partner_nonsoo has exactly 1 order
    And no AD_Issue was created since the existing AD_Issue records were remembered
