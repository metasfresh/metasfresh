@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor5
Feature: EDI order candidates — the parties of the order candidate end up on the order and the following documents

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-11-16T09:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |


  # ##########################################################################
  # ##########################################################################
  # ##########################################################################
  @Id:S34092_020
  Scenario: order candidate without invoice address gets the invoice address of the order location's bill-to relation

    # one partner with market, regional and central location; market bills to regional, the partner in general bills to central
    Given metasfresh contains C_BPartners without locations:
      | Identifier | Value               | IsCustomer | M_PricingSystem_ID |
      | customer   | S34092_020_customer | Y          | pricingSystem      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipTo | IsBillTo | IsShipToDefault | IsBillToDefault | Address1            | Postal | City         |
      | market     | 4000000020068 | customer      | true     | true     | Y               | Y               | Market street 020   | 4623   | Marketcity   |
      | regional   | 4000000020549 | customer      | true     | true     | N               | N               | Regional street 020 | 4623   | Regionalcity |
      | central    | 4000000020556 | customer      | true     | true     | N               | N               | Central street 020  | 4623   | Centralcity  |
    And metasfresh contains C_BPartner_EDI_Setting:
      | C_BPartner_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | customer      | true                 | 4000000020068         |
    And metasfresh contains C_BP_Relations:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | C_BPartnerRelation_ID | C_BPartnerRelation_Location_ID | IsBillTo |
      | relMarket  | customer      | market                 | customer              | regional                       | Y        |
      | relPartner | customer      |                        | customer              | central                        | Y        |
    And metasfresh contains M_Products:
      | Identifier | Name               | GTIN          |
      | product    | S34092_020_product | 4000000020151 |
    # the DESADV of the EDI recipient needs the TU packing of the product
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name            | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | huPip                              | PCE                   | 3008003                    | product                 | 10  | 2026-01-01 | false                  | false                 | S34092_020 x 10 PCE | true                    | false                     |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
"""
{
    "orgCode": "001",
    "externalHeaderId": "S34092_020_BGM",
    "externalLineId": "1",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-4000000020068",
        "bpartnerLocationIdentifier": "gln-4000000020068"
    },
    "dropShipBPartner": {
        "bpartnerIdentifier": "gln-4000000020068",
        "bpartnerLocationIdentifier": "gln-4000000020068"
    },
    "productIdentifier": "gtin-4000000020151",
    "qty": 3,
    "dateOrdered": "2026-11-16",
    "dateRequired": "2026-11-27",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "currencyCode": "EUR",
    "deliveryRule": "F",
    "poReference": "S34092_020_BGM"
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "S34092_020_BGM",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | null                  | null                    |

    Then validate the created orders
      | C_Order_ID | C_BPartner_Location_ID | Bill_BPartner_ID | Bill_Location_ID | IsDropShip | DocStatus |
      | order_1    | market                 | customer         | regional         | false      | CO        |
