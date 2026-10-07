@from:cucumber
@allure.label.epic:E0291_REST_API
@allure.label.feature:F4550_Sales_Order_Candidate_REST_API
@allure.label.feature:F00120_Sales_Order_Candidate
@F4550
@F00120
@topic:orderCandidate
Feature: order candidate in TU whose packing instruction comes from the price
## F4550: Sales Order Candidate (REST API)
## F00120: Sales Order Candidate
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
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @F4550
  @F00120
  @topic:orderCandidate
  Scenario: product identified by its article GTIN, ordered in TU without capacity; packing instruction from the price
    Given metasfresh contains M_Products:
      | Identifier  | Name                       | IsStocked |
      | p_S32404_10 | olCandTUCapacity_S32404_10 | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32404_10             | 4000000324041 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_S32404_10             | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name                 | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | piip_S32404_10                     | PCE                   | 3008003                    | p_S32404_10             | 3   | 2021-04-01 | false                  | false                 | Karton x 3 PCE S32404_10 | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S32404_10 | 2002141                           | p_S32404_10             | 10.0     | KGM               | Normal                        | piip_S32404_10                         |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32404_10 | customer_S32404_10 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32404_10 | 4000000324058 | customer_S32404_10       | true                | true         |

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
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | IsError | IsManualQtyItemCapacity | QtyItemCapacityInternal |
      | olCand_S32404_10       | p_S32404_10             | piip_S32404_10                         | N       | N                       | 3                       |

  @Id:S32404_20
  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @F4550
  @F00120
  @topic:orderCandidate
  Scenario: the order carries its own capacity, but the packing instruction from the price has a finite capacity: the master data capacity is used
    Given metasfresh contains M_Products:
      | Identifier  | Name                       | IsStocked |
      | p_S32404_20 | olCandTUCapacity_S32404_20 | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32404_20             | 4000000324201 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_S32404_20             | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name                 | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | piip_S32404_20                     | PCE                   | 3008003                    | p_S32404_20             | 3   | 2021-04-01 | false                  | false                 | Karton x 3 PCE S32404_20 | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S32404_20 | 2002141                           | p_S32404_20             | 10.0     | KGM               | Normal                        | piip_S32404_20                         |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32404_20 | customer_S32404_20 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32404_20 | 4000000324218 | customer_S32404_20       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32404_20",
            "externalLineId": "S32404_20_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000324218",
                "bpartnerLocationIdentifier": "gln-4000000324218"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000324201",
            "qty": 1,
            "uomCode": "TU",
            "qtyItemCapacity": 5,
            "poReference": "S32404_20",
            "line": 1,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_S32404_20       |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | IsError | IsManualQtyItemCapacity | QtyItemCapacityInternal |
      | olCand_S32404_20       | p_S32404_20             | piip_S32404_20                         | N       | N                       | 3                       |

  @Id:S32404_30
  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @F4550
  @F00120
  @topic:orderCandidate
  Scenario: the price has no packing instruction: the capacity from the order is used
    Given metasfresh contains M_Products:
      | Identifier  | Name                       | IsStocked |
      | p_S32404_30 | olCandTUCapacity_S32404_30 | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32404_30             | 4000000324300 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_S32404_30             | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_S32404_30 | 2002141                           | p_S32404_30             | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32404_30 | customer_S32404_30 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32404_30 | 4000000324317 | customer_S32404_30       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32404_30",
            "externalLineId": "S32404_30_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000324317",
                "bpartnerLocationIdentifier": "gln-4000000324317"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000324300",
            "qty": 1,
            "uomCode": "TU",
            "qtyItemCapacity": 5,
            "poReference": "S32404_30",
            "line": 1,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_S32404_30       |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | IsError | IsManualQtyItemCapacity |
      | olCand_S32404_30       | p_S32404_30             | N       | Y                       |

  @Id:S32404_40
  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @F4550
  @F00120
  @topic:orderCandidate
  Scenario: the order names a packing instruction with unlimited capacity and carries its own capacity: the capacity from the order is used
    Given metasfresh contains M_Products:
      | Identifier  | Name                       | IsStocked |
      | p_S32404_40 | olCandTUCapacity_S32404_40 | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32404_40             | 4000000324409 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_S32404_40             | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | REST.Context       | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name                 | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | piip_S32404_40                     | piip_S32404_40     | PCE                   | 3008003                    | p_S32404_40             | 3   | 2021-04-01 | false                  | false                 | Karton x 3 PCE S32404_40 | false                   | false                     |
      | piip_inf_S32404_40                 | piip_inf_S32404_40 | PCE                   | 3008003                    | p_S32404_40             | 0   | 2021-04-01 | true                   | false                 | unlimited S32404_40      | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_S32404_40 | 2002141                           | p_S32404_40             | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32404_40 | customer_S32404_40 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32404_40 | 4000000324416 | customer_S32404_40       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32404_40",
            "externalLineId": "S32404_40_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000324416",
                "bpartnerLocationIdentifier": "gln-4000000324416"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000324409",
            "qty": 1,
            "uomCode": "TU",
            "packingMaterialId": @piip_inf_S32404_40@,
            "qtyItemCapacity": 5,
            "poReference": "S32404_40",
            "line": 1,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_S32404_40       |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier | IsError | IsManualQtyItemCapacity |
      | olCand_S32404_40       | p_S32404_40             | piip_inf_S32404_40                     | N       | Y                       |

  @Id:S32404_50
  @from:cucumber
  @allure.label.epic:E0291_REST_API
  @allure.label.feature:F4550_Sales_Order_Candidate_REST_API
  @allure.label.feature:F00120_Sales_Order_Candidate
  @F4550
  @F00120
  @topic:orderCandidate
  Scenario: the user sets a packing instruction override with finite capacity: the master data capacity is used from then on
    Given metasfresh contains M_Products:
      | Identifier  | Name                       | IsStocked |
      | p_S32404_50 | olCandTUCapacity_S32404_50 | true      |
    And update M_Product:
      | M_Product_ID.Identifier | GTIN          |
      | p_S32404_50             | 4000000324508 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate |
      | p_S32404_50             | PCE                    | KGM                  | 0.25         |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | REST.Context       | OPT.C_UOM_ID.X12DE355 | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.IsInfiniteCapacity | OPT.IsAllowAnyProduct | OPT.Name                 | OPT.IsDefaultForProduct | IsOrderInTuUomWhenMatched |
      | piip_S32404_50                     | piip_S32404_50     | PCE                   | 3008003                    | p_S32404_50             | 3   | 2021-04-01 | false                  | false                 | Karton x 3 PCE S32404_50 | false                   | false                     |
      | piip_inf_S32404_50                 | piip_inf_S32404_50 | PCE                   | 3008003                    | p_S32404_50             | 0   | 2021-04-01 | true                   | false                 | unlimited S32404_50      | false                   | false                     |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_S32404_50 | 2002141                           | p_S32404_50             | 10.0     | KGM               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier         | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_S32404_50 | customer_S32404_50 | N            | Y              | 2000837                       |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location_S32404_50 | 4000000324515 | customer_S32404_50       | true                | true         |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
  """
{
    "requests": [
        {
            "orgCode": "001",
            "externalHeaderId": "S32404_50",
            "externalLineId": "S32404_50_1",
            "externalSystemCode": "Shopware6",
            "dataSource": "int-Shopware",
            "bpartner": {
                "bpartnerIdentifier": "gln-4000000324515",
                "bpartnerLocationIdentifier": "gln-4000000324515"
            },
            "dateRequired": "2021-04-15",
            "dateOrdered": "2021-04-15",
            "dateCandidate": "2021-04-15",
            "orderDocType": "SalesOrder",
            "productIdentifier": "gtin-4000000324508",
            "qty": 1,
            "uomCode": "TU",
            "qtyItemCapacity": 5,
            "poReference": "S32404_50",
            "line": 1,
            "deliveryViaRule": "S",
            "deliveryRule": "F"
        }
    ]
}
"""

    Then process metasfresh response JsonOLCandCreateBulkResponse
      | C_OLCand_ID.Identifier |
      | olCand_S32404_50       |
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | IsError | IsManualQtyItemCapacity |
      | olCand_S32404_50       | p_S32404_50             | N       | Y                       |

    When update C_OLCand:
      | C_OLCand_ID.Identifier | OPT.M_HU_PI_Item_Product_Override_ID.Identifier |
      | olCand_S32404_50       | piip_S32404_50                                  |

    Then validate C_OLCand:
      | C_OLCand_ID.Identifier | M_Product_ID.Identifier | IsError | IsManualQtyItemCapacity | QtyItemCapacityInternal |
      | olCand_S32404_50       | p_S32404_50             | N       | N                       | 3                       |
