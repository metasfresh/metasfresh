@from:cucumber
@ghActions:run_on_executor6
Feature: Product price validation (not attribute dependant S0144_1)

  Background:
#  Prerequisite:
#  - Attributes `Alter` and `Label` are added on attributeSet `attributeSet_02062022`
#  - AttributeSet `attributeSet_02062022` is set on product category `Standard`
#  - Product category `Standard` is set on the testing product `product_S0144.1_02062022`

    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-30T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    Given load M_Attribute:
      | M_Attribute_ID.Identifier | Value   |
      | attr_age                  | Age     |
      | attr_Label                | 1000002 |
    # disable all default values for attributes
    And update all M_AttributeValue records by M_Attribute_ID
      | M_Attribute_ID.Identifier | IsNullFieldValue |
      | attr_age                  | false            |
      | attr_Label                | false            |
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
      | Identifier               | Name                     | OPT.M_Product_Category_ID.Identifier |
      | product_S0144.1_02062022 | product_S0144.1_02062022 | standard_category                    |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier  | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.IsAttributeDependant | OPT.SeqNo |
      | pp_S0144.1_100 | plv_SO_02062022                   | product_S0144.1_02062022 | 100      | PCE               | Normal                        | false                    | 10        |

    And metasfresh contains C_BPartners:
      | Identifier                | Name             | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner_S0144.1_02062022 | BPartner02062022 | Y              | ps_02062022                   |


  @from:cucumber
  @Id:S0144.1_100
  Scenario: Validate that Age attribute set on productCategory.ASI has no default value on order line if configured so (M_AttributeValue.IsNullFieldValue=N)
    And metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier  | DateOrdered | OPT.POReference |
      | order_S0144.1_100 | true    | bpartner_S0144.1_02062022 | 2022-06-01  | po_S0144.1_100  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered |
      | ol_S0144.1_100 | order_S0144.1_100     | product_S0144.1_02062022 | 1          |

    # validate that Age attribute propagated from productCategory on order line has null value
    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier  | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_Attribute_ID:M_AttributeInstance.Value |
      | ol_S0144.1_100            | order_S0144.1_100     | 2022-06-01      | product_S0144.1_02062022 | 1          | 0            | 0           | 100   | 0        | EUR          | false     | attr_age:null                                |


  @from:cucumber
  @Id:S0144.1_180
  Scenario: Product price is matched
    # add CU-TU allocation for `product_S0144.1_02062022`
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
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier  | Qty | ValidFrom  |
      | huProductTU_X                      | huPiItemTU                 | product_S0144.1_02062022 | 8   | 2022-05-10 |

    Given metasfresh contains C_Orders:
      | Identifier        | IsSOTrx | C_BPartner_ID.Identifier  | DateOrdered | OPT.POReference |
      | order_S0144.1_180 | true    | bpartner_S0144.1_02062022 | 2022-06-01  | po_S0144.1_180  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier  | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_180 | order_S0144.1_180     | product_S0144.1_02062022 | 8          | huProductTU_X                          |

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier  | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_S0144.1_180            | order_S0144.1_180     | 2022-06-01      | product_S0144.1_02062022 | 8          | 0            | 0           | 100   | 0        | EUR          | false     | huProductTU_X                          |
