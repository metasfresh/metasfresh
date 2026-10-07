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
  @F4550
  @F00120
  @topic:orderCandidate
  Scenario: two of three lines have a GTIN that is only on packing instructions not valid on the delivery date; both are reported, nothing is created
    # line 10: GTIN is the product's own GTIN and is also on packing instructions (partner / without partner) of two products
    # line 20: GTIN only on packing instructions (partner / without partner) that are valid long before the delivery date
    # line 30: GTIN only on packing instructions (partner / without partner) that become valid after the delivery date
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
