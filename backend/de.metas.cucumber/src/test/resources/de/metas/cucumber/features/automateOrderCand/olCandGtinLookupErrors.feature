@from:cucumber
@allure.label.epic:E0291_REST_API
@allure.label.feature:F4550_Sales_Order_Candidate_REST_API
@allure.label.feature:F00120_Sales_Order_Candidate
@F4550
@F00120
@topic:orderCandidate
Feature: order candidate bulk request with product identifiers that cannot be resolved
## F4550: Sales Order Candidate (REST API)
## F00120: Sales Order Candidate
  A bulk request identifies its products by GTIN, ordered in TU.
  The GTIN lookup only considers packing instructions that are valid on the delivery date.
  Every line whose product cannot be resolved is reported, each with the reason and the line it belongs to.
  Nothing is created for the request.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @Id:S32656_10
  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  Scenario: two of three lines have a GTIN that is only on packing instructions not valid on the delivery date; both are reported, nothing is created
    # line 10: GTIN is the product's own GTIN and is also on packing instructions (partner / without partner) of two products
    # line 20: GTIN only on packing instructions (partner / without partner) that become valid after the delivery date (2022-09-01)
    # line 30: GTIN only on packing instructions (partner / without partner) that become valid after the delivery date (2026-06-30 / 2026-08-01)
    Given metasfresh contains M_Products:
      | Identifier     | Name                   | IsStocked |
      | p_A_S32656_10  | gtinErrorsA_S32656_10  | true      |
      | p_A2_S32656_10 | gtinErrorsA2_S32656_10 | true      |
      | p_B_S32656_10  | gtinErrorsB_S32656_10  | true      |
      | p_C_S32656_10  | gtinErrorsC_S32656_10  | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_A_S32656_10           | 4000000326564 |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_10 | customer_S32656_10 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                 | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_10         | 4000000326595 | customer_S32656_10       | true                | true         |
      | handOverLocation_S32656_10 | 4000000326601 | customer_S32656_10       | false               | true         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier | Qty | ValidFrom  | GTIN          | REST.Context     |
      | piipA_partner_S32656_10            | PCE                   | 3008003                    | p_A_S32656_10           | customer_S32656_10       | 20  | 2023-02-01 | 4000000326564 | piipA_partner    |
      | piipA_noPartner_S32656_10          | PCE                   | 3008003                    | p_A_S32656_10           |                          | 20  | 2023-02-01 | 4000000326564 | piipA_noPartner  |
      | piipA2_partner_S32656_10           | PCE                   | 3008003                    | p_A2_S32656_10          | customer_S32656_10       | 20  | 2026-03-17 | 4000000326564 | piipA2_partner   |
      | piipA2_noPartner_S32656_10         | PCE                   | 3008003                    | p_A2_S32656_10          |                          | 20  | 2026-03-17 | 4000000326564 | piipA2_noPartner |
      | piipB_partner_S32656_10            | PCE                   | 3008003                    | p_B_S32656_10           | customer_S32656_10       | 20  | 2022-09-01 | 4000000326571 | piipB_partner    |
      | piipB_noPartner_S32656_10          | PCE                   | 3008003                    | p_B_S32656_10           |                          | 20  | 2022-09-01 | 4000000326571 | piipB_noPartner  |
      | piipC_partner_S32656_10            | PCE                   | 3008003                    | p_C_S32656_10           | customer_S32656_10       | 20  | 2026-06-30 | 4000000326588 | piipC_partner    |
      | piipC_noPartner_S32656_10          | PCE                   | 3008003                    | p_C_S32656_10           |                          | 20  | 2026-08-01 | 4000000326588 | piipC_noPartner  |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '400' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_10_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326595",
                "bpartnerLocationIdentifier": "gln-4000000326595"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326595",
                "bpartnerLocationIdentifier": "gln-4000000326595"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326601",
                "bpartnerLocationIdentifier": "gln-4000000326601"
            },
            "dateRequired": "2020-01-27",
            "dateOrdered": "2020-01-21",
            "dateCandidate": "2020-01-21",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326564",
            "qty": 18,
            "uomCode": "TU",
            "poReference": "S32656_10",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_10_ext",
            "externalLineId": "00020",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326595",
                "bpartnerLocationIdentifier": "gln-4000000326595"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326595",
                "bpartnerLocationIdentifier": "gln-4000000326595"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326601",
                "bpartnerLocationIdentifier": "gln-4000000326601"
            },
            "dateRequired": "2020-01-27",
            "dateOrdered": "2020-01-21",
            "dateCandidate": "2020-01-21",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326571",
            "qty": 20,
            "uomCode": "TU",
            "poReference": "S32656_10",
            "line": 20,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_10_ext",
            "externalLineId": "00030",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326595",
                "bpartnerLocationIdentifier": "gln-4000000326595"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326595",
                "bpartnerLocationIdentifier": "gln-4000000326595"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326601",
                "bpartnerLocationIdentifier": "gln-4000000326601"
            },
            "dateRequired": "2020-01-27",
            "dateOrdered": "2020-01-21",
            "dateCandidate": "2020-01-21",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326588",
            "qty": 7,
            "uomCode": "TU",
            "poReference": "S32656_10",
            "line": 30,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then the metasfresh REST-API responds with
"""
{
    "errors": [
        {
            "message": "Line 20 (externalLineId=00020, externalHeaderId=S32656_10_ext): The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-4000000326571 -  could not be found. GTIN 4000000326571 is only on packing instructions that are not valid on the delivery date 2020-01-27: M_HU_PI_Item_Product_ID=@piipB_partner@ valid from 2022-09-01, M_HU_PI_Item_Product_ID=@piipB_noPartner@ valid from 2022-09-01."
        },
        {
            "message": "Line 30 (externalLineId=00030, externalHeaderId=S32656_10_ext): The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-4000000326588 -  could not be found. GTIN 4000000326588 is only on packing instructions that are not valid on the delivery date 2020-01-27: M_HU_PI_Item_Product_ID=@piipC_partner@ valid from 2026-06-30, M_HU_PI_Item_Product_ID=@piipC_noPartner@ valid from 2026-08-01."
        }
    ]
}
"""
    And exactly 0 C_OLCand exist for externalHeaderId 'S32656_10_ext'


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_21
  Scenario: one line with an unknown GLN; the request is rejected with the current message
    Given metasfresh contains M_Products:
      | Identifier    | Name                  | IsStocked |
      | p_S32656_21   | gtinErrors_S32656_21  | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32656_21             | 4000000326717 |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_21 | customer_S32656_21 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_21 | 4000000326724 | customer_S32656_21       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '400' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_21_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326731",
                "bpartnerLocationIdentifier": "gln-4000000326731"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326731",
                "bpartnerLocationIdentifier": "gln-4000000326731"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326731",
                "bpartnerLocationIdentifier": "gln-4000000326731"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326717",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_21_ext",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then the metasfresh REST-API responds with
"""
{
    "errors": [
        {
            "message": "No BPartner found for the given identifier!\nAdditional parameters:\n BPartnerIdentifier: gln-4000000326731"
        }
    ]
}
"""
    And exactly 0 C_OLCand exist for externalHeaderId 'S32656_21_ext'


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_30
  Scenario: three lines whose GTINs all resolve on packing instructions valid on the delivery date; the order candidates are created
    Given metasfresh contains M_Products:
      | Identifier     | Name                   | IsStocked |
      | pA_S32656_30   | gtinErrorsA_S32656_30  | true      |
      | pB_S32656_30   | gtinErrorsB_S32656_30  | true      |
      | pC_S32656_30   | gtinErrorsC_S32656_30  | true      |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | ppA_S32656_30  | 2002141                           | pA_S32656_30            | 10.0     | PCE               | Normal                        |
      | ppB_S32656_30  | 2002141                           | pB_S32656_30            | 10.0     | PCE               | Normal                        |
      | ppC_S32656_30  | 2002141                           | pC_S32656_30            | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_30 | customer_S32656_30 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                 | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_30         | 4000000326779 | customer_S32656_30       | true                | true         |
      | handOverLocation_S32656_30 | 4000000326786 | customer_S32656_30       | false               | true         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier | Qty | ValidFrom  | GTIN          |
      | piipA_S32656_30                    | PCE                   | 3008003                    | pA_S32656_30            | customer_S32656_30       | 20  | 2021-03-01 | 4000000326748 |
      | piipB_S32656_30                    | PCE                   | 3008003                    | pB_S32656_30            | customer_S32656_30       | 20  | 2021-03-01 | 4000000326755 |
      | piipC_S32656_30                    | PCE                   | 3008003                    | pC_S32656_30            | customer_S32656_30       | 20  | 2021-03-01 | 4000000326762 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_30_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326779",
                "bpartnerLocationIdentifier": "gln-4000000326779"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326779",
                "bpartnerLocationIdentifier": "gln-4000000326779"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326786",
                "bpartnerLocationIdentifier": "gln-4000000326786"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326748",
            "qty": 18,
            "uomCode": "TU",
            "poReference": "S32656_30_ext",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_30_ext",
            "externalLineId": "00020",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326779",
                "bpartnerLocationIdentifier": "gln-4000000326779"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326779",
                "bpartnerLocationIdentifier": "gln-4000000326779"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326786",
                "bpartnerLocationIdentifier": "gln-4000000326786"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326755",
            "qty": 20,
            "uomCode": "TU",
            "poReference": "S32656_30_ext",
            "line": 20,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_30_ext",
            "externalLineId": "00030",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326779",
                "bpartnerLocationIdentifier": "gln-4000000326779"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326779",
                "bpartnerLocationIdentifier": "gln-4000000326779"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326786",
                "bpartnerLocationIdentifier": "gln-4000000326786"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326762",
            "qty": 7,
            "uomCode": "TU",
            "poReference": "S32656_30_ext",
            "line": 30,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier                                    |
      | olCandA_S32656_30,olCandB_S32656_30,olCandC_S32656_30 |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | QtyEntered | IsError |
      | olCandA_S32656_30      | pA_S32656_30            | piipA_S32656_30                        | 18         | N       |
      | olCandB_S32656_30      | pB_S32656_30            | piipB_S32656_30                        | 20         | N       |
      | olCandC_S32656_30      | pC_S32656_30            | piipC_S32656_30                        | 7          | N       |
    And exactly 3 C_OLCand exist for externalHeaderId 'S32656_30_ext'


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_50
  Scenario: the product resolves but the promotion code is unknown; the request is rejected with the current message
    Given metasfresh contains M_Products:
      | Identifier    | Name                  | IsStocked |
      | p_S32656_50   | gtinErrors_S32656_50  | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32656_50             | 4000000326793 |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_50 | customer_S32656_50 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_50 | 4000000326809 | customer_S32656_50       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '400' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_50_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326809",
                "bpartnerLocationIdentifier": "gln-4000000326809"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326809",
                "bpartnerLocationIdentifier": "gln-4000000326809"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326809",
                "bpartnerLocationIdentifier": "gln-4000000326809"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326793",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_50_ext",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F",
            "promotionCode": "NO_SUCH_PROMO_S32656_50"
        }
    ]
}
"""

    Then the metasfresh REST-API responds with
"""
{
    "errors": [
        {
            "message": "Promotion code not found: NO_SUCH_PROMO_S32656_50\nAdditional parameters:\n Value: NO_SUCH_PROMO_S32656_50"
        }
    ]
}
"""
    And exactly 0 C_OLCand exist for externalHeaderId 'S32656_50_ext'


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_70
  Scenario: invoice candidate request with an unknown gtin- product identifier; the request is rejected with the current message
    Given metasfresh contains M_Products:
      | Identifier  | REST.Context.Value |
      | p_S32656_70 | productValue_70    |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | OPT.IsCustomer | M_PricingSystem_ID.Identifier | REST.Context.Value |
      | customer_S32656_70 | Y              | 2000837                       | customerValue_70   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | C_BPartner_ID.Identifier | C_Country_ID | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_S32656_70 | customer_S32656_70       | DE           | Y                   | Y                   |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "S32656_70_H",
            "externalLineId": "S32656_70_L1",
            "billPartnerIdentifier": "val-@customerValue_70@",
            "productIdentifier": "gtin-4000000326816",
            "dateOrdered": "2021-04-15",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort"
          }
        ]
      }
      """
    Then the metasfresh REST-API responds with
      """
      {
        "errors": [
          {
            "message": "The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-4000000326816 -  could not be found."
          }
        ]
      }
      """


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_20
  Scenario: product error on one line and an unknown GLN on another; both are reported, nothing is created
    # line 20: GTIN only on a packing instruction that becomes valid after the delivery date (2022-09-01)
    # line 30: unknown gln- partner
    Given metasfresh contains M_Products:
      | Identifier   | Name                  | IsStocked |
      | pA_S32656_20 | gtinErrorsA_S32656_20 | true      |
      | pB_S32656_20 | gtinErrorsB_S32656_20 | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | pB_S32656_20            | 4000000326854 |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_20 | customer_S32656_20 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_20 | 4000000326830 | customer_S32656_20       | true                | true         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier | Qty | ValidFrom  | GTIN          | REST.Context    |
      | piipA_S32656_20                    | PCE                   | 3008003                    | pA_S32656_20            | customer_S32656_20       | 20  | 2022-09-01 | 4000000326823 | piipA_S32656_20 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '400' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_20_ext",
            "externalLineId": "00020",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326830",
                "bpartnerLocationIdentifier": "gln-4000000326830"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326830",
                "bpartnerLocationIdentifier": "gln-4000000326830"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326830",
                "bpartnerLocationIdentifier": "gln-4000000326830"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326823",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_20_ext",
            "line": 20,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_20_ext",
            "externalLineId": "00030",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326847",
                "bpartnerLocationIdentifier": "gln-4000000326847"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326847",
                "bpartnerLocationIdentifier": "gln-4000000326847"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326847",
                "bpartnerLocationIdentifier": "gln-4000000326847"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326854",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_20_ext",
            "line": 30,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then the metasfresh REST-API responds with
"""
{
    "errors": [
        {
            "message": "Line 20 (externalLineId=00020, externalHeaderId=S32656_20_ext): The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-4000000326823 -  could not be found. GTIN 4000000326823 is only on packing instructions that are not valid on the delivery date 2021-04-15: M_HU_PI_Item_Product_ID=@piipA_S32656_20@ valid from 2022-09-01."
        },
        {
            "message": "No BPartner found for the given identifier!\nAdditional parameters:\n BPartnerIdentifier: gln-4000000326847"
        }
    ]
}
"""
    And exactly 0 C_OLCand exist for externalHeaderId 'S32656_20_ext'


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_40
  Scenario: unknown val- product and a gtin- product that is not valid on the delivery date; both are reported with their line
    Given metasfresh contains M_Products:
      | Identifier   | Name                  | IsStocked |
      | pA_S32656_40 | gtinErrorsA_S32656_40 | true      |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_40 | customer_S32656_40 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_40 | 4000000326878 | customer_S32656_40       | true                | true         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier | Qty | ValidFrom  | GTIN          | REST.Context    |
      | piipA_S32656_40                    | PCE                   | 3008003                    | pA_S32656_40            | customer_S32656_40       | 20  | 2022-09-01 | 4000000326861 | piipA_S32656_40 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '400' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_40_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326878",
                "bpartnerLocationIdentifier": "gln-4000000326878"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326878",
                "bpartnerLocationIdentifier": "gln-4000000326878"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326878",
                "bpartnerLocationIdentifier": "gln-4000000326878"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "val-NO_SUCH_PRODUCT_S32656_40",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_40_ext",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_40_ext",
            "externalLineId": "00020",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326878",
                "bpartnerLocationIdentifier": "gln-4000000326878"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326878",
                "bpartnerLocationIdentifier": "gln-4000000326878"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326878",
                "bpartnerLocationIdentifier": "gln-4000000326878"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326861",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_40_ext",
            "line": 20,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then the metasfresh REST-API responds with
"""
{
    "errors": [
        {
            "message": "Line 10 (externalLineId=00010, externalHeaderId=S32656_40_ext): The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=val-NO_SUCH_PRODUCT_S32656_40 -  could not be found."
        },
        {
            "message": "Line 20 (externalLineId=00020, externalHeaderId=S32656_40_ext): The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-4000000326861 -  could not be found. GTIN 4000000326861 is only on packing instructions that are not valid on the delivery date 2021-04-15: M_HU_PI_Item_Product_ID=@piipA_S32656_40@ valid from 2022-09-01."
        }
    ]
}
"""
    And exactly 0 C_OLCand exist for externalHeaderId 'S32656_40_ext'


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_45
  Scenario: single line with a GTIN that is on no product; the error names the line and says the GTIN is unknown
    Given metasfresh contains M_Products:
      | Identifier  | Name                 | IsStocked |
      | p_S32656_45 | gtinErrors_S32656_45 | true      |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_45 | customer_S32656_45 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_45 | 4000000326892 | customer_S32656_45       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '400' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_45_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326892",
                "bpartnerLocationIdentifier": "gln-4000000326892"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326892",
                "bpartnerLocationIdentifier": "gln-4000000326892"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326892",
                "bpartnerLocationIdentifier": "gln-4000000326892"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326885",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_45_ext",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then the metasfresh REST-API responds with
"""
{
    "errors": [
        {
            "message": "Line 10 (externalLineId=00010, externalHeaderId=S32656_45_ext): The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-4000000326885 -  could not be found. No active packing instruction (M_HU_PI_Item_Product) of an active product for the ordering business partner or without business partner, no active partner product (C_BPartner_Product) and no active product (M_Product) carries GTIN 4000000326885."
        }
    ]
}
"""
    And exactly 0 C_OLCand exist for externalHeaderId 'S32656_45_ext'


  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################
  # ##########################################################################################

  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @Id:S32656_60
  Scenario: a line that was already created is skipped; only the new failing line is reported
    Given metasfresh contains M_Products:
      | Identifier   | Name                  | IsStocked |
      | pA_S32656_60 | gtinErrorsA_S32656_60 | true      |
      | pB_S32656_60 | gtinErrorsB_S32656_60 | true      |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | ppA_S32656_60 | 2002141                           | pA_S32656_60            | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32656_60 | customer_S32656_60 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32656_60 | 4000000326922 | customer_S32656_60       | true                | true         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | C_BPartner_ID.Identifier | Qty | ValidFrom  | GTIN          | REST.Context    |
      | piipA_S32656_60                    | PCE                   | 3008003                    | pA_S32656_60            | customer_S32656_60       | 20  | 2021-03-01 | 4000000326908 | piipA_S32656_60 |
      | piipB_S32656_60                    | PCE                   | 3008003                    | pB_S32656_60            | customer_S32656_60       | 20  | 2022-09-01 | 4000000326915 | piipB_S32656_60 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_60_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326908",
            "qty": 18,
            "uomCode": "TU",
            "poReference": "S32656_60_ext",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCandA_S32656_60      |
    And exactly 1 C_OLCand exist for externalHeaderId 'S32656_60_ext'

    # same header again: line 00010 already exists, line 00020 is new and its GTIN is not valid on the delivery date
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '400' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_60_ext",
            "externalLineId": "00010",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326908",
            "qty": 18,
            "uomCode": "TU",
            "poReference": "S32656_60_ext",
            "line": 10,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        },
        {
            "orgCode": "001",
            "externalHeaderId": "S32656_60_ext",
            "externalLineId": "00020",
            "externalSystemCode": "Other",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "dropShipBPartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "handOverBPartner": {
                "bpartnerIdentifier": "gln-4000000326922",
                "bpartnerLocationIdentifier": "gln-4000000326922"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000326915",
            "qty": 5,
            "uomCode": "TU",
            "poReference": "S32656_60_ext",
            "line": 20,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then the metasfresh REST-API responds with
"""
{
    "errors": [
        {
            "message": "Line 20 (externalLineId=00020, externalHeaderId=S32656_60_ext): The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-4000000326915 -  could not be found. GTIN 4000000326915 is only on packing instructions that are not valid on the delivery date 2021-04-15: M_HU_PI_Item_Product_ID=@piipB_S32656_60@ valid from 2022-09-01."
        }
    ]
}
"""
    And exactly 1 C_OLCand exist for externalHeaderId 'S32656_60_ext'
