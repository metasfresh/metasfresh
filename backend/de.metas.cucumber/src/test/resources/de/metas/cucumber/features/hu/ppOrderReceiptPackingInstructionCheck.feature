@from:cucumber
@allure.label.epic:E0370_Intralogistic_HUs
@allure.label.feature:F5000_Handling_Unit
@F5000
@ghActions:run_on_executor5
Feature: Manufacturing order receipt follows the packing instruction
  # Packing instruction: 15 pieces per TU. Pallet: capacity 4 TUs.
  # The receipt is refused when its LU/TU configuration contradicts the packing instruction.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-01-03T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier       |
      | receiptProduct   |
      | receiptComponent |
      | receiptCoProduct |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | huPackingTU           |
      | huPackingLU           |
      | huPackingLUNoCapacity |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID            | HU_UnitType | IsCurrent |
      | packingVersionTU   | huPackingTU           | TU          | Y         |
      | packingVersionLU   | huPackingLU           | LU          | Y         |
      | packingVersionLU0  | huPackingLUNoCapacity | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemTU      | packingVersionTU   | 0   | MI       |                                  |
      | huPiItemLU      | packingVersionLU   | 4   | HU       | huPackingTU                      |
      | huPiItemLU0     | packingVersionLU0  | 0   | HU       | huPackingTU                      |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID     | Qty | ValidFrom  |
      | pipMain                 | huPiItemTU      | receiptProduct   | 15  | 2022-01-01 |
      | pipCoProduct            | huPiItemTU      | receiptCoProduct | 15  | 2022-01-01 |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  | PP_Product_BOMVersions_ID.Identifier |
      | bomReceipt | receiptProduct          | 2021-01-02 | bomVersionsReceipt                   |
    And metasfresh contains PP_Product_BOMLines
      | Identifier       | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch | ComponentType |
      | bomLineComponent | bomReceipt                   | receiptComponent        | 2021-01-02 | 10       | CO            |
      | bomLineCoProduct | bomReceipt                   | receiptCoProduct        | 2021-01-02 | -3       | CP            |
    And the PP_Product_BOM identified by bomReceipt is completed

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    And create PP_Order:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | completeDocument |
      | ppOrderReceipt         | MOP         | receiptProduct          | 200        | testResource             | 2022-01-05T23:59:00.00Z | 2022-01-05T23:59:00.00Z | 2022-01-05T23:59:00.00Z | Y                |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt is refused when 45 pieces are put into one TU on one pallet
    # operators' habit: target quantity entered as quantity per TU
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ErrorMessage_AD_Message                                       | OPT.ErrorMessage_Contains |
      | ppOrderReceipt | N               | 1     | N               | 1     | N               | 45          | pipMain                 | huPackingLU   | M_HU_LUTU_Configuration_QtyCUsPerTU_NotMatchingPackingInstruction | 15                        |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt is refused when the quantity per TU is above the packing instruction
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ErrorMessage_AD_Message                                       | OPT.ErrorMessage_Contains |
      | ppOrderReceipt | N               | 1     | N               | 3     | N               | 16          | pipMain                 | huPackingLU   | M_HU_LUTU_Configuration_QtyCUsPerTU_NotMatchingPackingInstruction | 15                        |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt is refused when the quantity per TU is below the packing instruction
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ErrorMessage_AD_Message                                       | OPT.ErrorMessage_Contains |
      | ppOrderReceipt | N               | 1     | N               | 3     | N               | 14          | pipMain                 | huPackingLU   | M_HU_LUTU_Configuration_QtyCUsPerTU_NotMatchingPackingInstruction | 15                        |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt is refused when the quantity per TU is a fraction below the packing instruction
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ErrorMessage_AD_Message                                       | OPT.ErrorMessage_Contains |
      | ppOrderReceipt | N               | 1     | N               | 3     | N               | 14.5        | pipMain                 | huPackingLU   | M_HU_LUTU_Configuration_QtyCUsPerTU_NotMatchingPackingInstruction | 15                        |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt is refused when there are more TUs per pallet than the pallet capacity
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ErrorMessage_AD_Message                     | OPT.ErrorMessage_Contains |
      | ppOrderReceipt | N               | 1     | N               | 5     | N               | 15          | pipMain                 | huPackingLU   | M_HU_LUTU_Configuration_QtyTU_ExceedsLUCapacity | 4                         |


  ##############################################################################################
  ##############################################################################################
  Scenario: Co-product receipt is refused when the quantity per TU differs from the packing instruction
    Given after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderLineCoProduct           | ppOrderReceipt         | receiptCoProduct        | -600         | false           | PCE               | CP            |
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | PP_Order_BOMLine_ID  | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ErrorMessage_AD_Message                                       | OPT.ErrorMessage_Contains |
      | ppOrderReceipt | ppOrderLineCoProduct | N               | 1     | N               | 1     | N               | 45          | pipCoProduct            | huPackingLU   | M_HU_LUTU_Configuration_QtyCUsPerTU_NotMatchingPackingInstruction | 15                        |


  ##############################################################################################
  ##############################################################################################
  Scenario: Co-product receipt is refused when there are more TUs per pallet than the pallet capacity
    Given after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderLineCoProduct           | ppOrderReceipt         | receiptCoProduct        | -600         | false           | PCE               | CP            |
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | PP_Order_BOMLine_ID  | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ErrorMessage_AD_Message                     | OPT.ErrorMessage_Contains |
      | ppOrderReceipt | ppOrderLineCoProduct | N               | 1     | N               | 5     | N               | 15          | pipCoProduct            | huPackingLU   | M_HU_LUTU_Configuration_QtyTU_ExceedsLUCapacity | 4                         |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt is accepted when quantity per TU and TUs per pallet match the packing instruction
    # one pallet with 4 TUs x 15 pieces
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ExpectedHUCount | OPT.ExpectedTUsPerLU | OPT.ExpectedQtyCUsPerTU |
      | ppOrderReceipt | fullPallet         | N               | 1     | N               | 4     | N               | 15          | pipMain                 | huPackingLU   | 1                   | 4                    | 15                      |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt of two pallets with fewer TUs than the pallet capacity is accepted
    # two pallets, each with 3 TUs x 15 pieces
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ExpectedHUCount | OPT.ExpectedTUsPerLU | OPT.ExpectedQtyCUsPerTU |
      | ppOrderReceipt | firstPallet        | N               | 2     | N               | 3     | N               | 15          | pipMain                 | huPackingLU   | 2                   | 3                    | 15                      |


  ##############################################################################################
  ##############################################################################################
  Scenario: Pallet with capacity 0 does not limit the number of TUs
    # one pallet with 7 TUs x 15 pieces
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID         | OPT.ExpectedHUCount | OPT.ExpectedTUsPerLU | OPT.ExpectedQtyCUsPerTU |
      | ppOrderReceipt | palletNoCapacity   | N               | 1     | N               | 7     | N               | 15          | pipMain                 | huPackingLUNoCapacity | 1                   | 7                    | 15                      |


  ##############################################################################################
  ##############################################################################################
  Scenario: Co-product receipt is accepted when the LU/TU configuration matches the packing instruction
    # two pallets, each with 3 TUs x 15 pieces
    Given after not more than 60s, PP_Order_BomLines are found
      | PP_Order_BOMLine_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | QtyRequiered | IsQtyPercentage | C_UOM_ID.X12DE355 | ComponentType |
      | ppOrderLineCoProduct           | ppOrderReceipt         | receiptCoProduct        | -600         | false           | PCE               | CP            |
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | PP_Order_BOMLine_ID  | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | OPT.ExpectedHUCount | OPT.ExpectedTUsPerLU | OPT.ExpectedQtyCUsPerTU |
      | ppOrderReceipt | ppOrderLineCoProduct | coProductPallet    | N               | 2     | N               | 3     | N               | 15          | pipCoProduct            | huPackingLU   | 2                   | 3                    | 15                      |


  ##############################################################################################
  ##############################################################################################
  Scenario: Packing instruction with quantity 0 and no infinite flag is treated like unlimited capacity
    # written by SQL: the model validation would reject it on save; own TU item, so it does not overlap pipMain
    Given metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | huPackingTUQty0       |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID      | HU_UnitType | IsCurrent |
      | packingVersionQty0 | huPackingTUQty0 | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType |
      | huPiItemQty0    | packingVersionQty0 | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product with quantity 0 and no infinite capacity, inserted by SQL:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID   |
      | pipZeroQty              | huPiItemQty0    | receiptProduct |
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | OPT.ExpectedHUCount |
      | ppOrderReceipt | tuZeroQty          | N               | 0     | N               | 1     | N               | 22          | pipZeroQty              | 1                   |
    # one TU holding the entered quantity
    Then validate M_HUs:
      | M_HU_ID.Identifier | M_HU_PI_Version_ID | OPT.M_HU_Parent | OPT.M_Product_ID | OPT.Qty |
      | tuZeroQty          | packingVersionQty0 | null            | receiptProduct   | 22 PCE |


  ##############################################################################################
  ##############################################################################################
  Scenario: Receipt without packing item is not checked
    # 101 = "No Packing Item"
    When receive HUs for PP_Order with M_HU_LUTU_Configuration:
      | PP_Order_ID    | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | OPT.ExpectedHUCount |
      | ppOrderReceipt | looseCUs           | N               | 0     | N               | 1     | N               | 9           | 101                     | 1                   |
    # loose CUs holding the entered quantity
    Then validate M_HUs:
      | M_HU_ID.Identifier | OPT.M_HU_Parent | OPT.M_Product_ID | OPT.Qty |
      | looseCUs           | null            | receiptProduct   | 9 PCE   |
