@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00122
@topic:orderCandidate
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

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00122
  @topic:orderCandidate
  Scenario: explicit prepay order type completes waiting for payment
    Given metasfresh contains M_PricingSystems
      | Identifier         | Name               | Value              | OPT.IsActive |
      | ps_prepay_02112026 | ps_prepay_02112026 | ps_prepay_02112026 | true         |
    And metasfresh contains M_PriceLists
      | Identifier         | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_prepay_02112026 | ps_prepay_02112026            | DE                        | EUR                 | pl_prepay_02112026 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | plv_prepay_02112026 | pl_prepay_02112026        | plv_prepay_02112026 | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier          | Name                |
      | product_prepay_0211 | product_prepay_0211 |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_prepay_0211   | plv_prepay_02112026               | product_prepay_0211     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           |
      | prepay_customer_0211 | prepay_customer_0211 | Y              | N            | ps_prepay_02112026            | prepay_customer_loc_0211   | 4056789012340 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier               | GLN           | C_BPartner_ID.Identifier |
      | prepay_customer_loc_0211 | 4056789012340 | prepay_customer_0211     |
    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID |
      | SOO         | PR         | prepayType   |

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
            "productIdentifier": "val-product_prepay_0211",
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
      | olCand_prepay          |

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
      | order_prepay          |
    And validate the created orders
      | C_Order_ID.Identifier | externalId      | C_BPartner_ID.Identifier | DocBaseType | C_DocType_ID.Identifier | processed | DocStatus |
      | order_prepay          | prepay_02112026 | prepay_customer_0211     | SOO         | prepayType              | true      | WP        |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | M_Product_ID.Identifier | QtyEntered | IsError | OPT.Processed |
      | olCand_prepay          | prepay_customer_0211     | product_prepay_0211     | 2          | N       | Y             |
    And the business partner identified by prepay_customer_0211 has exactly 1 order
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
    Then the business partner identified by prepay_customer_0211 has exactly 1 order
