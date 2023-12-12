@from:cucumber
@ghActions:run_on_executor6
Feature: Product price validation (S0144_2)

  Background:
#  Prerequisite:
#  - Attributes `Alter` and `Label` are added on attributeSet `attributeSet_03062022`
#  - AttributeSet `attributeSet_03062022` is set on product category `Standard`
#  - Product category `Standard` is set on the product in question

    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value   |
      | attr_age                  | Age     |
      | attr_Label                | 1000002 |
    And add M_AttributeSet:
      | M_AttributeSet_ID.Identifier | Name                  | MandatoryType |
      | attributeSet_03062022        | attributeSet_03062022 | N             |
    And add M_AttributeUse:
      | M_AttributeUse_ID.Identifier | M_AttributeSet_ID.Identifier | M_Attribute_ID.Identifier | SeqNo |
      | attributeUse_age             | attributeSet_03062022        | attr_age                  | 10    |
      | attributeUse_Label           | attributeSet_03062022        | attr_Label                | 20    |

    And metasfresh contains M_PricingSystems
      | Identifier  | Name        | Value       |
      | ps_03062022 | ps_03062022 | ps_03062022 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO_03062022 | ps_03062022                   | DE                        | EUR                 | pl_SO_03062022 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | plv_SO_03062022 | pl_SO_03062022            | PLVNameSO_03062022 | 2022-05-03 |

    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |

    And metasfresh contains C_BPartners:
      | Identifier        | Name             | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier |
      | bpartner_03062022 | BPartner03062022 | Y              | Y            | ps_03062022                   |

  @from:cucumber
  @Id:S0144.2_100
  Scenario: Validate that productCategory.ASI is propagated on order line with default value if configured so (M_AttributeValue.IsNullFieldValue=Y)
    # disable all default values for attributes
    Given update all M_AttributeValue records by M_Attribute_ID
      | M_Attribute_ID.Identifier | IsNullFieldValue |
      | attr_age                  | false            |
      | attr_Label                | false            |

    # update `Age` attribute value to have default value
    And update M_AttributeValue:
      | M_AttributeValue_ID.Identifier | M_Attribute_ID.Identifier | Value | IsNullFieldValue |
      | attr_age_value_24              | attr_age                  | 24    | true             |

    # set attributeSet on the productCategory for the product in question
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_03062022            |
    And metasfresh contains M_Products:
      | Identifier          | Name                | OPT.M_Product_Category_ID.Identifier |
      | product_S0144.2_100 | product_S0144.2_100 | standard_category                    |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant |
      | pp_S0144.2_100 | plv_SO_03062022                   | product_S0144.2_100     | 100      | PCE               | Normal                        | false                    |

    When metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.2_100 | true    | bpartner_03062022        | 2022-06-02  | po_S0144.2_100  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_SO144.2_100 | order_S0144.2_100     | product_S0144.2_100     | 1          |
    # validate that `Age` attribute is propagated from productCategory with default value
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_Attribute_ID:M_AttributeInstance.Value |
      | ol_SO144.2_100            | order_S0144.2_100     | 2022-06-02      | product_S0144.2_100     | 1          | 0            | 0           | 100   | 0        | EUR          | false     | attr_age:24                                  |


  @from:cucumber
  @Id:S0144.2_110
  Scenario: Validate that productPrice attributes are preserved on order line prior to productCategory attributes
    # disable all default values for attributes
    Given update all M_AttributeValue records by M_Attribute_ID
      | M_Attribute_ID.Identifier | IsNullFieldValue |
      | attr_age                  | false            |
      | attr_Label                | false            |

    # update `Age` attribute value to have default value
    And update M_AttributeValue:
      | M_AttributeValue_ID.Identifier | M_Attribute_ID.Identifier | Value | IsNullFieldValue |
      | attr_age_value_10              | attr_age                  | 10    | true             |
    # set attributeSet on the productCategory for the product in question
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_03062022            |

    And metasfresh contains M_Products:
      | Identifier          | Name                | OPT.M_Product_Category_ID.Identifier |
      | product_S0144.2_110 | product_S0144.2_110 | standard_category                    |
    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.2_110":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice |
      | pp_S0144.2_110 | plv_SO_03062022                   | product_S0144.2_110     | 110      | PCE               | Normal                        | true                     | ppASI_S0144.2_110                        | N                 |

    When metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.2_110 | true    | bpartner_03062022        | 2022-06-02  | po_S0144.2_110  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_SO144.2_110 | order_S0144.2_110     | product_S0144.2_110     | 2          |
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_SO144.2_110            | order_S0144.2_110     | 2022-06-02      | product_S0144.2_110     | 2          | 0            | 0           | 110   | 0        | EUR          | false     | ppASI_S0144.2_110                        |


  @from:cucumber
  @Id:S0144.2_120
  @Id:S0150_200
  Scenario: Validate that productPrice attributes and packing material are preserved on order line
    Given metasfresh contains M_Products:
      | Identifier          | Name                |
      | product_S0144.2_120 | product_S0144.2_120 |
    # add CU-TU allocation for the product in question
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | product_S0144.2_120     | 8   | 2022-05-10 |

    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.2_120":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S0144.2_120 | plv_SO_03062022                   | product_S0144.2_120     | 120      | PCE               | Normal                        | true                     | ppASI_S0144.2_120                        | N                 | huProductTU                            |


    Given metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN            | C_BPartner_ID.Identifier |
      | bpLocation_03062022 | 03062660333443 | bpartner_03062022        |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalLineId": "sdwewwe",
    "externalHeaderId": "89676577",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-03062660333443",
        "bpartnerLocationIdentifier": "gln-03062660333443"
    },
    "dateRequired": "2022-06-05",
    "dateOrdered": "2022-06-05",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-product_S0144.2_120",
    "qty": 10,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_S0144.2_120",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "89676577",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_S0144.2_120     |
    And validate the created orders
      | C_Order_ID.Identifier | externalId | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference    | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | order_S0144.2_120     | 89676577   | bpartner_03062022        | bpLocation_03062022               | 2022-06-05  | SOO         | EUR          | F            | S               | po_S0144.2_120 | true      | CO        | Shopware                               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_SO144.2_120            | order_S0144.2_120     | product_S0144.2_120     | 0            | 10         | 0           | 120   | 0        | EUR          | true      | ppASI_S0144.2_120                        | huProductTU                            |

  @from:cucumber
  @Id:S0144.2_140
  Scenario: Validate that Age attribute set on productCategory.ASI has default value on order line if configured so (M_AttributeValue.IsNullFieldValue=Y)
    # disable all default values for attributes
    Given update all M_AttributeValue records by M_Attribute_ID
      | M_Attribute_ID.Identifier | IsNullFieldValue |
      | attr_age                  | false            |
      | attr_Label                | false            |

    # update `Age` attribute value to have default value
    And update M_AttributeValue:
      | M_AttributeValue_ID.Identifier | M_Attribute_ID.Identifier | Value | IsNullFieldValue |
      | attr_age_value_24              | attr_age                  | 24    | true             |

    # set attributeSet on the productCategory for the product in question
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_03062022            |
    And metasfresh contains M_Products:
      | Identifier          | Name                | OPT.M_Product_Category_ID.Identifier |
      | product_S0144.2_140 | product_S0144.2_140 | standard_category                    |
    # add CU-TU allocation for the product in question
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | product_S0144.2_140     | 8   | 2022-05-10 |

    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.UseScalePrice | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S0144.2_140 | plv_SO_03062022                   | product_S0144.2_140     | 140      | PCE               | Normal                        | false                    | N                 | huProductTU                            |

    When metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.2_140 | true    | bpartner_03062022        | 2022-06-02  | po_S0144.2_140  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_SO144.2_140 | order_S0144.2_140     | product_S0144.2_140     | 4          | huProductTU                            |

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_Attribute_ID:M_AttributeInstance.Value |
      | ol_SO144.2_140            | order_S0144.2_140     | 2022-06-02      | product_S0144.2_140     | 4          | 0            | 0           | 140   | 0        | EUR          | false     | attr_age:24                                  |

  @from:cucumber
  @Id:S0144.2_150
  Scenario: Validate that Age attribute set on productCategory.ASI doesn't have default value on order line if configured so (M_AttributeValue.IsNullFieldValue=N)
    # disable all default values for attributes
    Given update all M_AttributeValue records by M_Attribute_ID
      | M_Attribute_ID.Identifier | IsNullFieldValue |
      | attr_age                  | false            |
      | attr_Label                | false            |

     # set attributeSet on the productCategory for the product in question
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_03062022            |
    And metasfresh contains M_Products:
      | Identifier          | Name                | OPT.M_Product_Category_ID.Identifier |
      | product_S0144.2_150 | product_S0144.2_150 | standard_category                    |
    # add CU-TU allocation for the product in question
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | product_S0144.2_150     | 8   | 2022-05-10 |

    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.UseScalePrice | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S0144.2_150 | plv_SO_03062022                   | product_S0144.2_150     | 150      | PCE               | Normal                        | false                    | N                 | huProductTU                            |

    When metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.2_150 | true    | bpartner_03062022        | 2022-06-02  | po_S0144.2_150  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_SO144.2_150 | order_S0144.2_150     | product_S0144.2_150     | 5          | huProductTU                            |

    # validate that `Age` attribute is propagated from productCategory with default value
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_Attribute_ID:M_AttributeInstance.Value |
      | ol_SO144.2_150            | order_S0144.2_150     | 2022-06-02      | product_S0144.2_150     | 5          | 0            | 0           | 150   | 0        | EUR          | false     | attr_age:null                                |
