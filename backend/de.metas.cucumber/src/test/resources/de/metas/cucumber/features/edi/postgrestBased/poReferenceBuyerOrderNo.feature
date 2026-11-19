@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor3
Feature: Sales order flag - the order reference is the buyer's order number
## F00350: EDI
  A sales order gets IsPOReferenceBuyerOrderNo = Y when it is created from order candidates
  of a data source ticked as EDI channel and carries a POReference.
  Changing the POReference resets the flag; copying the order does not carry it.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh contains M_PricingSystems
      | Identifier           |
      | poRefBuyerPricingSys |
    And metasfresh contains M_PriceLists
      | Identifier         | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name               | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | poRefBuyerPriceLst | poRefBuyerPricingSys          | DE                        | EUR                 | poRefBuyerPriceLst | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name                | ValidFrom  |
      | poRefBuyerPriceLstV | poRefBuyerPriceLst        | poRefBuyerPriceLstV | 2022-04-01 |
    And metasfresh contains M_Products:
      | Identifier | Name              |
      | product    | poRefBuyerProduct |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product | poRefBuyerPriceLstV               | product                 | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier | Name               | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID | GLN           | deliveryRule |
      | customer   | poRefBuyerCustomer | Y              | N            | poRefBuyerPricingSys          | customerLocation           | 4337010330019 | F            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | customerLocation | 4337010330019 | customer                 | Y               | Y               |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario Outline: Order from order candidates of the shipped EDI-ORDERS data source <dataSource> is flagged
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "<header>",
            "externalLineId": "<header>_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-<dataSource>",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234567",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "<header>",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | poReference | OPT.AD_InputDataSource_ID.InternalName | IsPOReferenceBuyerOrderNo |
      | order_1               | customer                 | 0021234567  | <dataSource>                           | true                      |

    Examples:
      | dataSource                | header                  |
      | DEST.de.metas.edi.ecosio  | poRefBuyerHeaderEcosio  |
      | DEST.de.metas.edi.eddyson | poRefBuyerHeaderEddyson |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Order entered manually with a POReference is not flagged
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | order_1    | true    | customer      | 2022-04-16  | 0027654321  |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0027654321  | false                     |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Order from order candidates of a data source that is not an EDI channel is not flagged
    Given metasfresh contains AD_InputDataSource:
      | Identifier | Name                   | Value                  | InternalName           | IsEDIChannel |
      | dataSource | poRefBuyerPlainChannel | poRefBuyerPlainChannel | poRefBuyerPlainChannel | N            |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderPlain",
            "externalLineId": "poRefBuyerHeaderPlain_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerPlainChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234568",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderPlain",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | OPT.AD_InputDataSource_ID.InternalName | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234568  | poRefBuyerPlainChannel                 | false                     |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Order from an EDI-channel order candidate whose POReference was cleared is not flagged
    Given metasfresh contains AD_InputDataSource:
      | Identifier | Name                     | Value                    | InternalName             | IsEDIChannel |
      | dataSource | poRefBuyerClearedChannel | poRefBuyerClearedChannel | poRefBuyerClearedChannel | Y            |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderCleared",
            "externalLineId": "poRefBuyerHeaderCleared_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerClearedChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234569_toBeCleared",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And update C_OLCand:
      | C_OLCand_ID | POReference |
      | olCand_1    | null        |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderCleared",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    Then validate the created orders
      | C_Order_ID.Identifier | OPT.AD_InputDataSource_ID.InternalName | IsPOReferenceBuyerOrderNo |
      | order_1               | poRefBuyerClearedChannel               | false                     |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Changing the POReference on a flagged order resets the flag
    Given metasfresh contains AD_InputDataSource:
      | Identifier | Name                   | Value                  | InternalName           | IsEDIChannel |
      | dataSource | poRefBuyerResetChannel | poRefBuyerResetChannel | poRefBuyerResetChannel | Y            |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderReset",
            "externalLineId": "poRefBuyerHeaderReset_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerResetChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234569",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderReset",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234569  | true                      |
    When update order
      | C_Order_ID.Identifier | POReference |
      | order_1               | 0029999991  |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0029999991  | false                     |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Changing the POReference on the order candidate resets the flag of the order
    Given metasfresh contains AD_InputDataSource:
      | Identifier | Name                       | Value                      | InternalName               | IsEDIChannel |
      | dataSource | poRefBuyerCandidateChannel | poRefBuyerCandidateChannel | poRefBuyerCandidateChannel | Y            |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderCandidate",
            "externalLineId": "poRefBuyerHeaderCandidate_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerCandidateChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234570",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderCandidate",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234570  | true                      |
    When update C_OLCand:
      | C_OLCand_ID | POReference |
      | olCand_1    | 0029999992  |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0029999992  | false                     |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: User ticks the flag on a manually entered order
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | order_1    | true    | customer      | 2022-04-16  | 0027654321  |
    When update order
      | C_Order_ID.Identifier | IsPOReferenceBuyerOrderNo |
      | order_1               | true                      |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0027654321  | true                      |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: User changes the POReference and ticks the flag in the same save
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | POReference |
      | order_1    | true    | customer      | 2022-04-16  | 0027654321  |
    When update order
      | C_Order_ID.Identifier | POReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0027654322  | true                      |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0027654322  | true                      |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Copy of a flagged order is not flagged
    Given metasfresh contains AD_InputDataSource:
      | Identifier | Name                  | Value                 | InternalName          | IsEDIChannel |
      | dataSource | poRefBuyerCopyChannel | poRefBuyerCopyChannel | poRefBuyerCopyChannel | Y            |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderCopy",
            "externalLineId": "poRefBuyerHeaderCopy_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerCopyChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234571",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderCopy",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234571  | true                      |
    When C_Order is cloned
      | C_Order_ID.Identifier | ClonedOrder.C_Order_ID.Identifier |
      | order_1               | clonedOrder                       |
    Then validate the created orders
      | C_Order_ID.Identifier | IsPOReferenceBuyerOrderNo |
      | clonedOrder           | false                     |
      | order_1               | true                      |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Copy of a flagged order made the way a contract change does it keeps the POReference and is not flagged
    Given metasfresh contains AD_InputDataSource:
      | Identifier | Name                        | Value                       | InternalName                | IsEDIChannel |
      | dataSource | poRefBuyerLegacyCopyChannel | poRefBuyerLegacyCopyChannel | poRefBuyerLegacyCopyChannel | Y            |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderLegacyCopy",
            "externalLineId": "poRefBuyerHeaderLegacyCopy_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerLegacyCopyChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234573",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderLegacyCopy",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234573  | true                      |
    When C_Order header is copied the way a contract change does it
      | C_Order_ID.Identifier | CopiedOrder.C_Order_ID.Identifier |
      | order_1               | copiedOrder                       |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | copiedOrder           | 0021234573  | false                     |
      | order_1               | 0021234573  | true                      |


# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
# ############################################################################################################################################
  Scenario: Unticking the EDI channel keeps the flag of existing orders and affects only later orders
    Given metasfresh contains AD_InputDataSource:
      | Identifier | Name                    | Value                   | InternalName            | IsEDIChannel |
      | dataSource | poRefBuyerUntickChannel | poRefBuyerUntickChannel | poRefBuyerUntickChannel | Y            |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderUntick",
            "externalLineId": "poRefBuyerHeaderUntick_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerUntickChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234572",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_1               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderUntick",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |
    And validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234572  | true                      |
    When metasfresh contains AD_InputDataSource:
      | Identifier | Name                    | Value                   | InternalName            | IsEDIChannel |
      | dataSource | poRefBuyerUntickChannel | poRefBuyerUntickChannel | poRefBuyerUntickChannel | N            |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_1               | 0021234572  | true                      |
    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "poRefBuyerHeaderUntickAfter",
            "externalLineId": "poRefBuyerHeaderUntickAfter_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-poRefBuyerUntickChannel",
            "bpartner": {
                "bpartnerIdentifier": "gln-4337010330019",
                "bpartnerLocationIdentifier": "gln-4337010330019"
            },
            "dateRequired": "2022-05-02",
            "dateOrdered": "2022-04-16",
            "orderDocType": "SalesOrder",
            "paymentTerm": "val-1000002",
            "productIdentifier": "val-poRefBuyerProduct",
            "qty": 2,
            "currencyCode": "EUR",
            "discount": 0,
            "poReference": "0021234573",
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""
    And process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_2               |
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "poRefBuyerHeaderUntickAfter",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier |
      | order_2               |
    Then validate the created orders
      | C_Order_ID.Identifier | poReference | IsPOReferenceBuyerOrderNo |
      | order_2               | 0021234573  | false                     |
