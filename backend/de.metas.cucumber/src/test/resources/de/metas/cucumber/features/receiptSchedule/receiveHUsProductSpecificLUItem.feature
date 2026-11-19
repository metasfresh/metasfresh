@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E0370_Intralogistic_HUs
@allure.label.feature:F5000_Handling_Unit
@F5000
Feature: Receive HUs on a purchase receipt schedule packs the pallet with the TU count of the received product
## A pallet (LU) has a generic LU->TU packing-instruction item (30 TUs) and,
## optionally, an item of its own for one product (40 TUs).
## "Receive HUs" of that product builds pallets of 40 TUs, every other product keeps 30.
## One TU holds 10 PCE, so 1200 PCE are 120 TUs: 3 pallets of 40 or 4 pallets of 30.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier      |
      | specificProduct |
      | otherProduct    |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | tuPI                  |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | tuPIVersion                   | tuPI                  | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | tuPIItem                   | tuPIVersion                   | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | IsDefaultForProduct |
      | specificProductTU                  | tuPIItem                   | specificProduct         | 10  | 2021-01-01 | Y                   |
      | otherProductTU                     | tuPIItem                   | otherProduct            | 10  | 2021-01-01 | Y                   |

    And metasfresh contains M_PricingSystems
      | Identifier | Name | Value |
      | ps_PO      | ps   | ps    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl   | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name | ValidFrom  |
      | plv_PO     | pl_PO                     | plv  | 2021-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier  | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_specific | plv_PO                            | specificProduct         | 10.0     | PCE               | Normal                        |
      | pp_other    | plv_PO                            | otherProduct            | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | supplier   | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN          | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation | supplierGLN1 | supplier                 | Y                   | Y                   |


  # ##########################################################################################
  # ##########################################################################################
  @from:cucumber
  Scenario: Receiving the product with its own LU item builds pallets of that item's TU count
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | palletPI              |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | palletPIVersion               | palletPI              | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | Included_HU_PI_ID | M_Product_ID    |
      | palletGenericItem          | palletPIVersion               | 30  | HU       | tuPI              |                 |
      | palletSpecificItem         | palletPIVersion               | 40  | HU       | tuPI              | specificProduct |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order      | N       | supplier                 | 2025-04-01  | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID | QtyEnteredTU |
      | orderLine  | order                 | specificProduct         | 1200       | specificProductTU       | 120          |
    And the order identified by order is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule                 | order                 | orderLine                 | supplier                 | supplierLocation                  | specificProduct         | 1200       | warehouseStd              |

    When receive HUs for M_ReceiptSchedule with its default M_HU_LUTU_Configuration:
      | M_ReceiptSchedule_ID | M_HU_ID.Identifier                     |
      | receiptSchedule      | specificLU_1,specificLU_2,specificLU_3 |

    Then validate M_HUs:
      | M_HU_ID.Identifier | M_Product_ID    | Qty     |
      | specificLU_1       | specificProduct | 400 PCE |
      | specificLU_2       | specificProduct | 400 PCE |
      | specificLU_3       | specificProduct | 400 PCE |
    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_Parent  | IsAggregate | QtyTUs |
      | specificLU_1_TUs   | specificLU_1 | Y           | 40     |
      | specificLU_2_TUs   | specificLU_2 | Y           | 40     |
      | specificLU_3_TUs   | specificLU_3 | Y           | 40     |


  # ##########################################################################################
  # ##########################################################################################
  @from:cucumber
  Scenario: Receiving another product keeps the generic LU item's TU count
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | palletPI              |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | palletPIVersion               | palletPI              | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | Included_HU_PI_ID | M_Product_ID    |
      | palletGenericItem          | palletPIVersion               | 30  | HU       | tuPI              |                 |
      | palletSpecificItem         | palletPIVersion               | 40  | HU       | tuPI              | specificProduct |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order      | N       | supplier                 | 2025-04-01  | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID | QtyEnteredTU |
      | orderLine  | order                 | otherProduct            | 1200       | otherProductTU          | 120          |
    And the order identified by order is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule                 | order                 | orderLine                 | supplier                 | supplierLocation                  | otherProduct            | 1200       | warehouseStd              |

    When receive HUs for M_ReceiptSchedule with its default M_HU_LUTU_Configuration:
      | M_ReceiptSchedule_ID | M_HU_ID.Identifier                      |
      | receiptSchedule      | otherLU_1,otherLU_2,otherLU_3,otherLU_4 |

    Then validate M_HUs:
      | M_HU_ID.Identifier | M_Product_ID | Qty     |
      | otherLU_1          | otherProduct | 300 PCE |
      | otherLU_2          | otherProduct | 300 PCE |
      | otherLU_3          | otherProduct | 300 PCE |
      | otherLU_4          | otherProduct | 300 PCE |
    And validate M_HUs:
      | M_HU_ID.Identifier | M_HU_Parent | IsAggregate | QtyTUs |
      | otherLU_1_TUs      | otherLU_1   | Y           | 30     |
      | otherLU_2_TUs      | otherLU_2   | Y           | 30     |
      | otherLU_3_TUs      | otherLU_3   | Y           | 30     |
      | otherLU_4_TUs      | otherLU_4   | Y           | 30     |


  # ##########################################################################################
  # ##########################################################################################
  @from:cucumber
  Scenario: A stored LU/TU configuration built before the product's own LU item existed does not keep the generic TU count
    ## The first receipt stores the generic configuration (30 TUs per pallet) on the receipt schedule.
    ## The product's own LU item (40 TUs) is added afterwards; the next receipt has to use it.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | latePalletPI          |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | latePalletPIVersion           | latePalletPI          | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | Included_HU_PI_ID |
      | latePalletGenericItem      | latePalletPIVersion           | 30  | HU       | tuPI              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order      | N       | supplier                 | 2025-04-01  | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID | QtyEnteredTU |
      | orderLine  | order                 | specificProduct         | 1200       | specificProductTU       | 120          |
    And the order identified by order is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule                 | order                 | orderLine                 | supplier                 | supplierLocation                  | specificProduct         | 1200       | warehouseStd              |

    And receive HUs for M_ReceiptSchedule with its default M_HU_LUTU_Configuration:
      | M_ReceiptSchedule_ID | M_HU_ID.Identifier                                              | IsSaveLUTUConfiguration |
      | receiptSchedule      | lateGenericLU_1,lateGenericLU_2,lateGenericLU_3,lateGenericLU_4 | Y                       |

    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | Included_HU_PI_ID | M_Product_ID    |
      | latePalletSpecificItem     | latePalletPIVersion           | 40  | HU       | tuPI              | specificProduct |

    When receive HUs for M_ReceiptSchedule with its default M_HU_LUTU_Configuration:
      | M_ReceiptSchedule_ID | M_HU_ID.Identifier                                 |
      | receiptSchedule      | lateSpecificLU_1,lateSpecificLU_2,lateSpecificLU_3 |

    Then validate M_HUs:
      | M_HU_ID.Identifier | M_Product_ID    | Qty     |
      | lateGenericLU_1    | specificProduct | 300 PCE |
      | lateSpecificLU_1   | specificProduct | 400 PCE |
      | lateSpecificLU_2   | specificProduct | 400 PCE |
      | lateSpecificLU_3   | specificProduct | 400 PCE |
    And validate M_HUs:
      | M_HU_ID.Identifier   | M_HU_Parent      | IsAggregate | QtyTUs |
      | lateGenericLU_1_TUs  | lateGenericLU_1  | Y           | 30     |
      | lateSpecificLU_1_TUs | lateSpecificLU_1 | Y           | 40     |
      | lateSpecificLU_2_TUs | lateSpecificLU_2 | Y           | 40     |
      | lateSpecificLU_3_TUs | lateSpecificLU_3 | Y           | 40     |


  # ##########################################################################################
  # ##########################################################################################
  @from:cucumber
  Scenario: The "Using Config" dialog proposes the product's own LU item and its TU count
    ## The dialog starts from the default configuration and builds the configuration of the entered parameters.
    ## Accepting the proposed values must stay on the product's own LU item and its 40 TUs.
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | palletPI              |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | HU_UnitType | IsCurrent |
      | palletPIVersion               | palletPI              | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | Included_HU_PI_ID | M_Product_ID    |
      | palletGenericItem          | palletPIVersion               | 30  | HU       | tuPI              |                 |
      | palletSpecificItem         | palletPIVersion               | 40  | HU       | tuPI              | specificProduct |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order      | N       | supplier                 | 2025-04-01  | POO             | ps_PO                             |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | M_HU_PI_Item_Product_ID | QtyEnteredTU |
      | orderLine  | order                 | specificProduct         | 1200       | specificProductTU       | 120          |
    And the order identified by order is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule                 | order                 | orderLine                 | supplier                 | supplierLocation                  | specificProduct         | 1200       | warehouseStd              |

    When create M_HU_LUTU_Configuration for M_ReceiptSchedule:
      | M_HU_LUTU_Configuration_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_HU_PI_Item_Product_ID.TU.Identifier | M_HU_PI_ID.LU.Identifier |
      | dialogConfig                          | receiptSchedule                 | specificProductTU                     | palletPI                 |

    Then validate M_HU_LUTU_Configuration:
      | M_HU_LUTU_Configuration_ID.Identifier | QtyLU | QtyTU | QtyCUsPerTU | OPT.M_LU_HU_PI_Item_ID.Identifier |
      | dialogConfig                          | 3     | 40    | 10          | palletSpecificItem                |
