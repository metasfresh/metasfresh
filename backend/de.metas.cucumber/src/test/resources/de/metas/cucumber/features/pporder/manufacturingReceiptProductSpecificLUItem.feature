@from:cucumber
@ghActions:run_on_executor5
@allure.label.epic:E3000_Manufacturing
@allure.label.feature:F5000_Handling_Unit
@F5000
Feature: Manufacturing receipt packs the pallet with the TU count of the received product
## A pallet (LU) has a generic LU->TU packing-instruction item (30 TUs) and,
## optionally, an item of its own for one product (40 TUs).
## The manufacturing receipt of that product builds pallets of 40 TUs, every other product keeps 30.
## One TU holds 10 PCE, so 1200 PCE are 120 TUs: 3 pallets of 40 or 4 pallets of 30.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And metasfresh contains M_Products:
      | Identifier      |
      | component       |
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

    And metasfresh contains PP_Product_BOM
      | Identifier  | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bomSpecific | specificProduct         | 2021-01-01 | bomVersionsSpecific                  |
      | bomOther    | otherProduct            | 2021-01-01 | bomVersionsOther                     |
    And metasfresh contains PP_Product_BOMLines
      | Identifier      | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bomLineSpecific | bomSpecific                  | component               | 2021-01-01 | 10       |
      | bomLineOther    | bomOther                     | component               | 2021-01-01 | 10       |
    And the PP_Product_BOM identified by bomSpecific is completed
    And the PP_Product_BOM identified by bomOther is completed


  # ##########################################################################################
  # ##########################################################################################
  @from:cucumber
  Scenario: Receipt of the product with its own LU item builds pallets of that item's TU count
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

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrderSpecific        | MOP         | specificProduct         | 1200       | testResource             | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | Y                |

    When receive HUs for PP_Order with its default M_HU_LUTU_Configuration:
      | PP_Order_ID     | M_HU_ID.Identifier                     |
      | ppOrderSpecific | specificLU_1,specificLU_2,specificLU_3 |

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
  Scenario: Receipt of another product keeps the generic LU item's TU count
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

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrderOther           | MOP         | otherProduct            | 1200       | testResource             | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | Y                |

    When receive HUs for PP_Order with its default M_HU_LUTU_Configuration:
      | PP_Order_ID  | M_HU_ID.Identifier                      |
      | ppOrderOther | otherLU_1,otherLU_2,otherLU_3,otherLU_4 |

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
    ## The first receipt stores the generic configuration (30 TUs per pallet) on the manufacturing order.
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

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrderLate            | MOP         | specificProduct         | 1200       | testResource             | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | 2025-04-01T23:59:00.00Z | Y                |

    And receive HUs for PP_Order with its default M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier                                              |
      | ppOrderLate | lateGenericLU_1,lateGenericLU_2,lateGenericLU_3,lateGenericLU_4 |

    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | Included_HU_PI_ID | M_Product_ID    |
      | latePalletSpecificItem     | latePalletPIVersion           | 40  | HU       | tuPI              | specificProduct |

    When receive HUs for PP_Order with its default M_HU_LUTU_Configuration:
      | PP_Order_ID | M_HU_ID.Identifier                                 |
      | ppOrderLate | lateSpecificLU_1,lateSpecificLU_2,lateSpecificLU_3 |

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

