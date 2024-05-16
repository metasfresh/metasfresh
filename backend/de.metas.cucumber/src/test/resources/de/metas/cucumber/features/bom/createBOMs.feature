@from:cucumber
@ghActions:run_on_executor3
Feature:bom create using metasfresh api
  Using default ad_orgId 1000000

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: create a ProductBOM record with existing ProductBOMVersions for productID

    Given metasfresh contains M_Products:
      | Identifier      | Value         | Name         |
      | finishedGood_Id | productValue1 | finishedGood |
      | component_1_Id  | productValue2 | component1   |
      | component_2_Id  | productValue3 | component2   |

    And metasfresh contains S_ExternalReferences:
      | ExternalSystem.Code | ExternalReference       | ExternalReferenceType.Code | RecordId.Identifier |
      | GRSSignum           | finishedGoodExternalRef | Product                    | finishedGood_Id     |
      | GRSSignum           | component1ExternalRef   | Product                    | component_1_Id      |
      | GRSSignum           | component2ExternalRef   | Product                    | component_2_Id      |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bom/version/001' and fulfills with '200' status code

    """
 {
    "uomCode": "KGM",
    "productIdentifier": "ext-GRSSignum-finishedGoodExternalRef",
    "name": "finishedGood",
    "isActive": true,
    "validFrom":"2021-10-29T11:09:47.000000Z",
    "bomLines": [
        {
            "productIdentifier": "ext-GRSSignum-component1ExternalRef",
            "qtyBom": {
                "qty": 5.0,
                "uomCode": "KGM"
            },
            "isQtyPercentage": true,
            "scrap": 20.15,
            "line": 1
        }
    ]
}
    """
    Then process metasfresh response:
      | PP_Product_BOM_ID.Identifier | PP_Product_BOMVersions_ID.Identifier |
      | bom_1                        | bv_1                                 |

    And verify that bomVersions was created for product
      | PP_Product_BOMVersions_ID.Identifier | M_Product_ID.Identifier | Name         |
      | bv_1                                 | finishedGood_Id         | finishedGood |

    And verify that bom was created for product
      | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | ProductValue  | UomCode | ValidFrom                   |
      | bom_1                        | finishedGood_Id         | bv_1                                 | productValue1 | KGM     | 2021-10-29T11:09:47.000000Z |

    And verify that bomLine was created for bom
      | OPT.PP_Product_BOMLine_ID.Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | Qty | UomCode | IsQtyPercentage | OPT.Scrap | Line |
      | boml_1                           | bom_1                        | component_1_Id          | 5.0 | KGM     | true            | 20.15     | 1    |


    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/bom/version/001' and fulfills with '200' status code
    """
 {
    "uomCode": "KGM",
    "productIdentifier": "ext-GRSSignum-finishedGoodExternalRef",
    "name": "finishedGood",
    "isActive": true,
    "validFrom":"2021-11-01T07:15:45.000000Z",
    "bomLines": [
        {
            "productIdentifier": "ext-GRSSignum-component2ExternalRef",
            "qtyBom": {
                "qty": 20.0,
                "uomCode": "KGM"
            },
            "isQtyPercentage": true,
            "scrap": 7.5,
            "line": 1
        }
    ]
}
    """
    Then process metasfresh response:
      | PP_Product_BOM_ID.Identifier | PP_Product_BOMVersions_ID.Identifier |
      | bom_2                        | bv_1                                 |

    And verify that bom was created for product
      | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | PP_Product_BOMVersions_ID.Identifier | ProductValue  | UomCode | ValidFrom                   |
      | bom_1                        | finishedGood_Id         | bv_1                                 | productValue1 | KGM     | 2021-10-29T11:09:47.000000Z |
      | bom_2                        | finishedGood_Id         | bv_1                                 | productValue1 | KGM     | 2021-11-01T07:15:45.000000Z |

    And verify that bomLine was created for bom
      | OPT.PP_Product_BOMLine_ID.Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | Qty  | UomCode | IsQtyPercentage | OPT.Scrap | Line |
      | boml_2                           | bom_2                        | component_2_Id          | 20.0 | KGM     | true            | 7.5       | 1    |