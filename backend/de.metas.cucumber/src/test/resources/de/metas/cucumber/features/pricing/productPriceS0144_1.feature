@from:cucumber
@ghActions:run_on_executor6
Feature: Product price validation (S0144_1)

  Background:
#  Prerequisite:
#  - Attributes `Alter` and `Label` are added on attributeSet `attributeSet_02062022`
#  - AttributeSet `attributeSet_02062022` is set on product category `Standard`
#  - Product category `Standard` is set on the testing product `product_02062022`

    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-30T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Attribute:
      | M_Attribute_ID.Identifier | Value   |
      | attr_age                  | Age     |
      | attr_Label                | 1000002 |
    And add M_AttributeSet:
      | M_AttributeSet_ID.Identifier | Name                  | MandatoryType |
      | attributeSet_02062022        | attributeSet_02062022 | N             |
    And add M_AttributeUse:
      | M_AttributeUse_ID.Identifier | M_AttributeSet_ID.Identifier | M_Attribute_ID.Identifier | SeqNo |
      | attributeUse_age             | attributeSet_02062022        | attr_age                  | 10    |
      | attributeUse_Label           | attributeSet_02062022        | attr_Label                | 20    |

    And metasfresh contains M_PricingSystems
      | Identifier  | Name        | Value       |
      | ps_02062022 | ps_02062022 | ps_02062022 |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name           | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO_02062022 | ps_02062022                   | DE                        | EUR                 | pl_SO_02062022 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name             | ValidFrom  |
      | plv_SO_02062022 | pl_SO_02062022            | PLVName_02062022 | 2022-05-01 |

    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    # set attributeSet on the productCategory for the product in question
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_02062022            |
    And metasfresh contains M_Products:
      | Identifier       | Name             | OPT.M_Product_Category_ID.Identifier |
      | product_02062022 | product_02062022 | standard_category                    |

    # add CU-TU allocation for `product_02062022`
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
      | huProductTU_X                      | huPiItemTU                 | product_02062022        | 8   | 2022-05-10 |
      | huProductTU_Y                      | huPiItemTU                 | product_02062022        | 10  | 2022-05-10 |

    # @Id:S0144.1_110
    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.1_110":
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
      | pp_S0144.1_110 | plv_SO_02062022                   | product_02062022        | 110      | PCE               | Normal                        | true                     | ppASI_S0144.1_110                        | N                 | 20        |

    # @Id:S0144.1_120
    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.1_120":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"04"
      }
    ]
  }
  """

    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice | OPT.SeqNo |
      | pp_S0144.1_120 | plv_SO_02062022                   | product_02062022        | 120      | PCE               | Normal                        | true                     | ppASI_S0144.1_120                        | N                 | 30        |

    # @Id:S0144.1_130
    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.1_130":
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
      | pp_S0144.1_130 | plv_SO_02062022                   | product_02062022        | 130      | PCE               | Normal                        | true                     | ppASI_S0144.1_130                        | N                 | 40        | huProductTU_X                          |

    # @Id:S0144.1_140
    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.1_140":
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
      | pp_S0144.1_140 | plv_SO_02062022                   | product_02062022        | 140      | PCE               | Normal                        | true                     | ppASI_S0144.1_140                        | N                 | 50        | huProductTU_Y                          |

    # @Id:S0144.1_150
    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.1_150":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"04"
      }
    ]
  }
  """
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice | OPT.SeqNo | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S0144.1_150 | plv_SO_02062022                   | product_02062022        | 150      | PCE               | Normal                        | true                     | ppASI_S0144.1_150                        | N                 | 60        | huProductTU_X                          |

    # @Id:S0144.1_160
    And metasfresh contains M_AttributeSetInstance with identifier "ppASI_S0144.1_160":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"04"
      }
    ]
  }
  """
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice | OPT.SeqNo | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S0144.1_160 | plv_SO_02062022                   | product_02062022        | 160      | PCE               | Normal                        | true                     | ppASI_S0144.1_160                        | N                 | 70        | huProductTU_X                          |

    # @Id:S0144.1_170
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.UseScalePrice | OPT.SeqNo | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | pp_S0144.1_170 | plv_SO_02062022                   | product_02062022        | 170      | PCE               | Normal                        | false                    | N                 | 80        | huProductTU_Y                          |


    And metasfresh contains C_BPartners:
      | Identifier        | Name             | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier |
      | bpartner_02062022 | BPartner02062022 | Y              | Y            | ps_02062022                   |


  @from:cucumber
  @Id:S0144.1_110
  Scenario: Product price `IsAttributeDependant = true` and has ASI set, then on order line price is matched with `ppASI_S0144.1_110`
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.1_110 | true    | bpartner_02062022        | 2022-06-01  | po_S0144.1_110  |

    And metasfresh contains M_AttributeSetInstance with identifier "ol_S0144.1_110ASI":
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
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_S0144.1_110 | order_S0144.1_110     | product_02062022        | 2          | ol_S0144.1_110ASI                        |

    # validate that ASI is preserved from productPrice on order line
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_S0144.1_110            | order_S0144.1_110     | 2022-06-01      | product_02062022        | 2          | 0            | 0           | 110   | 0        | EUR          | false     | ppASI_S0144.1_110                        |


  @from:cucumber
  @Id:S0144.1_120
  Scenario: Product price `IsAttributeDependant = true` and has ASI set, then on order line price is matched with `ppASI_S0144.1_120`
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.1_120 | true    | bpartner_02062022        | 2022-06-01  | po_S0144.1_120  |

    And metasfresh contains M_AttributeSetInstance with identifier "ol_S0144.1_120ASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"04"
      }
    ]
  }
  """
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_S0144.1_120 | order_S0144.1_120     | product_02062022        | 2          | ol_S0144.1_120ASI                        |

    # validate that ASI is preserved from productPrice on order line
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_S0144.1_120            | order_S0144.1_120     | 2022-06-01      | product_02062022        | 2          | 0            | 0           | 120   | 0        | EUR          | false     | ppASI_S0144.1_120                        |


  @from:cucumber
  @Id:S0144.1_130
  Scenario: Product price `IsAttributeDependant = true`, has ASI and packingMaterial set, then on order line price is matched with ASI `ppASI_S0144.1_130` and packing material `huProductTU_X`
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.1_130 | true    | bpartner_02062022        | 2022-06-01  | po_S0144.1_130  |

    And metasfresh contains M_AttributeSetInstance with identifier "ol_S0144.1_130ASI":
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
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_130 | order_S0144.1_130     | product_02062022        | 2          | ol_S0144.1_130ASI                        | huProductTU_X                          |

    # validate that ASI is preserved from productPrice on order line
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_130            | order_S0144.1_130     | 2022-06-01      | product_02062022        | 2          | 0            | 0           | 130   | 0        | EUR          | false     | ppASI_S0144.1_130                        | huProductTU_X                          |

  @from:cucumber
  @Id:S0144.1_140
  Scenario: Product price `IsAttributeDependant = true`, has ASI and packingMaterial set, then on order line price is matched with ASI `ppASI_S0144.1_140` and packing material `huProductTU_Y`
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.1_140 | true    | bpartner_02062022        | 2022-06-01  | po_S0144.1_140  |

    And metasfresh contains M_AttributeSetInstance with identifier "ol_S0144.1_140ASI":
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
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_140 | order_S0144.1_140     | product_02062022        | 3          | ol_S0144.1_140ASI                        | huProductTU_Y                          |

    # validate that ASI is preserved from productPrice on order line
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_140            | order_S0144.1_140     | 2022-06-01      | product_02062022        | 3          | 0            | 0           | 140   | 0        | EUR          | false     | ppASI_S0144.1_140                        | huProductTU_Y                          |

  @from:cucumber
  @Id:S0144.1_150
  Scenario: Product price `IsAttributeDependant = true`, has ASI and packingMaterial set, then on order line price is matched with ASI `ppASI_S0144.1_150` and packing material `huProductTU_X`
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.1_150 | true    | bpartner_02062022        | 2022-06-01  | po_S0144.1_150  |

    And metasfresh contains M_AttributeSetInstance with identifier "ol_S0144.1_150ASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000015",
          "valueStr":"04"
      }
    ]
  }
  """
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_150 | order_S0144.1_150     | product_02062022        | 4          | ol_S0144.1_150ASI                        | huProductTU_X                          |

    # validate that ASI is preserved from productPrice on order line
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_150            | order_S0144.1_150     | 2022-06-01      | product_02062022        | 4          | 0            | 0           | 150   | 0        | EUR          | false     | ppASI_S0144.1_150                        | huProductTU_X                          |


  @from:cucumber
  @Id:S0144.1_160
  Scenario: Product price `IsAttributeDependant = true`, has ASI and packingMaterial set, then on order line price is matched with ASI `ppASI_S0144.1_160` and packing material `huProductTU_X`
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.1_160 | true    | bpartner_02062022        | 2022-06-01  | po_S0144.1_160  |

    And metasfresh contains M_AttributeSetInstance with identifier "ol_S0144.1_160ASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      },
      {
        "attributeCode":"1000015",
          "valueStr":"04"
      }
    ]
  }
  """
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_160 | order_S0144.1_160     | product_02062022        | 5          | ol_S0144.1_160ASI                        | huProductTU_X                          |

    # validate that ASI is preserved from productPrice on order line
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_AttributeSetInstance_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_160            | order_S0144.1_160     | 2022-06-01      | product_02062022        | 5          | 0            | 0           | 160   | 0        | EUR          | false     | ppASI_S0144.1_160                        | huProductTU_X                          |


  @from:cucumber
  @Id:S0144.1_170
  Scenario: Product price `IsAttributeDependant = false` and packingMaterial set, then on order line price is matched with packing material `huProductTU_Y`
    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_S0144.1_170 | true    | bpartner_02062022        | 2022-06-01  | po_S0144.1_170  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_170 | order_S0144.1_170     | product_02062022        | 7          | huProductTU_Y                          |

    And metasfresh contains M_AttributeSetInstance with identifier "ol_S0144.1_170ASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"null"
      }
    ]
  }
  """
    And update C_OrderLine:
      | C_OrderLine_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | ol_S0144.1_170            | ol_S0144.1_170ASI                        |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_170            | order_S0144.1_170     | 2022-06-01      | product_02062022        | 7          | 0            | 0           | 170   | 0        | EUR          | false     | huProductTU_Y                          |