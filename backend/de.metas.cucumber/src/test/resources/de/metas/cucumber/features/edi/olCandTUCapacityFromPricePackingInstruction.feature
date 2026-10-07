@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@topic:orderCandidate
Feature: order candidate in TU whose packing instruction comes from the price
## F00350: EDI
  An ORDERS line identifies the product by its article GTIN and orders it in TU, without a capacity.
  No packing instruction carries that GTIN, so the candidate is created without one;
  the validation then takes the packing instruction from the product price.
  The candidate's TU capacity must then come from that packing instruction.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @Id:S32404_10
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  @topic:orderCandidate
  Scenario: product identified by its article GTIN, ordered in TU without capacity; packing instruction from the price
    Given metasfresh contains M_Products:
      | Identifier   | Name                       | IsStocked |
      | p_S32404_10  | olCandTUCapacity_S32404_10 | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32404_10             | 4000000324041 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_S32404_10             | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name                | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | piip_S32404_10                     | PCE                   | 3008003                    | p_S32404_10             | 3   | 2021-04-01 | false                  | false                 | Karton x 3 PCE S32404_10 | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S32404_10  | 2002141                           | p_S32404_10             | 10.0     | KGM               | Normal                        | piip_S32404_10                         |
    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32404_10  | customer_S32404_10  | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32404_10  | 4000000324058 | customer_S32404_10       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32404_10",
            "externalLineId": "S32404_10_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000324058",
                "bpartnerLocationIdentifier": "gln-4000000324058"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000324041",
            "qty": 1,
            "uomCode": "TU",
            "poReference": "S32404_10",
            "line": 1,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_S32404_10       |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | IsError |
      | olCand_S32404_10       | p_S32404_10             | piip_S32404_10                         | N       |
