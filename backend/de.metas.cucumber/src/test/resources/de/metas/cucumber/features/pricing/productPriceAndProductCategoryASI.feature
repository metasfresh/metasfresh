@from:cucumber
Feature: Product price with product category ASI settings validation

  Background:
#  Prerequisite:
#  - Attributes `Alter` and `Label` are added on attributeSet `attributeSet_03062022`
#  - AttributeSet `attributeSet_03062022` is set on product category `Standard`
#  - Product category `Standard` is set on the testing product `product_03062022`

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
      | pl_PO_03062022 | ps_03062022                   | DE                        | EUR                 | pl_PO_03062022 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | plv_SO_03062022 | pl_SO_03062022            | PLVNameSO_03062022 | 2022-05-03 |
      | plv_PO_03062022 | pl_PO_03062022            | PLVNamePO_03062022 | 2022-05-03 |

    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    # set attributeSet on the productCategory for the product in question
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_03062022            |
    And metasfresh contains M_Products:
      | Identifier       | Name             | OPT.M_Product_Category_ID.Identifier |
      | product_03062022 | product_03062022 | standard_category                    |

     # add CU-TU allocation for `product_03062022`
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name        |
      | huPackingTU           | huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | product_03062022        | 8   | 2022-05-10 |
    
    # add product prices
    # @Id:S0144.2_100
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.SeqNo |
      | pp_S0144.2_100 | plv_SO_03062022                   | product_03062022        | 100      | PCE               | Normal                        | false                    | 10        |

    # @Id:S0144.2_110
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
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice | OPT.SeqNo |
      | pp_S0144.2_110 | plv_SO_03062022                   | product_03062022        | 110      | PCE               | Normal                        | true                     | ppASI_S0144.2_110                        | N                 | 20        |

    # @Id:S0144.2_120
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
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice | OPT.SeqNo | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S0144.2_120 | plv_SO_03062022                   | product_03062022        | 120      | PCE               | Normal                        | true                     | ppASI_S0144.2_120                        | N                 | 30        | huProductTU                            |

   # @Id:S0144.2_140
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.UseScalePrice | OPT.SeqNo |
      | pp_S0144.2_140 | plv_SO_03062022                   | product_03062022        | 140      | PCE               | Normal                        | false                    | N                 | 40        |

   # @Id:S0144.2_150
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.UseScalePrice | OPT.SeqNo |
      | pp_S0144.2_150 | plv_SO_03062022                   | product_03062022        | 150      | PCE               | Normal                        | false                    | N                 | 50        |


