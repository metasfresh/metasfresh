Feature: EDI_DesadvPack and EDI_DesadvPack_Item

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Product:
      | M_Product_ID.Identifier | OPT.M_Product_ID |
      | convenienceSalate       | 2005577          |


  Scenario: As a user I want to verify the creation of EDI_DesadvPack and EDI_DesadvPack_Item records
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

     # Test Kunde 1
    Given the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  | OPT.DeliveryRule |
      | 2156425                  | true                     | bPartnerDesadvRecipientGLN | F                |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | 2156425                  | 2021-04-17  | po_ref_mock     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 10         |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 |
      | p_1                           | true                |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU |
      | pi_1                               | p_1                           | 10              | 10        | 10              | 0                   | 1         |


  Scenario: As a user I want to verify the creation of EDI_DesadvPack and EDI_DesadvPack_Item records
  in:
  C_OrderLine:
  - QtyEntered = 100
  - M_HU_PI_Item_Product_ID = 3010001 (IFCO 6410 x 10 Stk)

     # Test Kunde 1
    Given the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  | OPT.DeliveryRule |
      | 2156425                  | true                     | bPartnerDesadvRecipientGLN | F                |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | 2156425                  | 2021-04-17  | po_ref_mock     |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | 2005577                 | 100        | 3010001                                |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 |
      | p_1                           | true                |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU |
      | pi_1                               | p_1                           | 100             | 10        | 100             | 10                  | 10        |

  Scenario: As a user I want to verify the creation of EDI_DesadvPack and EDI_DesadvPack_Item records when having HUs
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

  M_ShipmentSchedule
  - QtyPickList = 5

     # Test Kunde 1
    Given the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  | OPT.DeliveryRule |
      | 2156425                  | true                     | bPartnerDesadvRecipientGLN | F                |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | convenienceSalate       | 10  | 2021-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo     |
      | huProduct_inventory       | 2021-04-16T00:00:00Z | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | OPT.M_Product_ID |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 5        | 2005577          |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 5     | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID | deliveryRule |
      | o_1        | true    | 2156425                  | 2021-04-17  | po_ref_mock     | 1000012              | F            |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 10         |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU          | s_s_1                            | 5         | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdLU          | s_s_1                            |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | PD           | true                | false       |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier |
      | p_1                           | true                | null                   |
      | p_2                           | true                | createdLU              |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU |
      | pi_1                               | p_1                           | 5               | 5         | 5               | 0                   | 1         |
      | pi_2                               | p_2                           | 5               | 5         | 5               | 5                   | 1         |

  Scenario: As a user I want to verify that EDI_DesadvPack and EDI_DesadvPack_Item records are erased when correcting the shipment
  in:
  C_OrderLine:
  - QtyEntered = 10
  - M_HU_PI_Item_Product_ID = 101 (No Packing Item)

  M_ShipmentSchedule
  - QtyPickList = 5

     # Test Kunde 1
    Given the following c_bpartner is changed
      | C_BPartner_ID.Identifier | OPT.IsEdiDesadvRecipient | OPT.EdiDesadvRecipientGLN  | OPT.DeliveryRule |
      | 2156425                  | true                     | bPartnerDesadvRecipientGLN | F                |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | huPackingLU           | huPackingLU     |
      | huPackingTU           | huPackingTU     |
      | huPackingVirtualPI    | No Packing Item |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name             | HU_UnitType | IsCurrent |
      | packingVersionLU              | huPackingLU           | packingVersionLU | LU          | Y         |
      | packingVersionTU              | huPackingTU           | packingVersionTU | TU          | Y         |
      | packingVersionCU              | huPackingVirtualPI    | No Packing Item  | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | packingVersionLU              | 10  | HU       | huPackingTU                      |
      | huPiItemTU                 | packingVersionTU              | 0   | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huProductTU                        | huPiItemTU                 | convenienceSalate       | 10  | 2021-01-01 |

    And metasfresh initially has M_Inventory data
      | M_Inventory_ID.Identifier | MovementDate         | DocumentNo     |
      | huProduct_inventory       | 2021-04-16T00:00:00Z | inventoryDocNo |
    And metasfresh initially has M_InventoryLine data
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | OPT.M_Product_ID |
      | huProduct_inventory       | huProduct_inventoryLine       | huProduct               | 0       | 5        | 2005577          |
    And complete inventory with inventoryIdentifier 'huProduct_inventory'
    And after not more than 30s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | huProduct_inventoryLine       | createdCU          |

    And transform CU to new TUs
      | sourceCU.Identifier | cuQty | M_HU_PI_Item_Product_ID.Identifier | resultedNewTUs.Identifier | resultedNewCUs.Identifier |
      | createdCU           | 5     | huProductTU                        | createdTU                 | newCreatedCU              |

    And after not more than 30s, M_HUs should have
      | M_HU_ID.Identifier | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | createdTU          | huProductTU                            |

    And transform TU to new LUs
      | sourceTU.Identifier | tuQty | M_HU_PI_Item_ID.Identifier | resultedNewLUs.Identifier |
      | createdTU           | 1     | huPiItemLU                 | createdLU                 |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.C_PaymentTerm_ID | deliveryRule |
      | o_1        | true    | 2156425                  | 2021-04-17  | po_ref_mock     | 1000012              | F            |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | 2005577                 | 10         |

    When the order identified by o_1 is completed

    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    When create M_PickingCandidate for M_HU
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier | QtyPicked | Status | PickStatus | ApprovalStatus |
      | createdLU          | s_s_1                            | 5         | IP     | P          | ?              |

    And process picking
      | M_HU_ID.Identifier | M_ShipmentSchedule_ID.Identifier |
      | createdLU          | s_s_1                            |

    And validate that there are no M_ShipmentSchedule_Recompute records after no more than 30 seconds for order 'o_1'

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | PD           | true                | false       |

    Then after not more than 30s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID.Identifier | IsManual_IPA_SSCC18 | OPT.M_HU_ID.Identifier |
      | p_1                           | true                | null                   |
      | p_2                           | true                | createdLU              |

    And after not more than 30s, the EDI_Desadv_Pack_Item has only the following records:
      | EDI_Desadv_Pack_Item_ID.Identifier | EDI_Desadv_Pack_ID.Identifier | OPT.MovementQty | OPT.QtyCU | OPT.QtyCUsPerLU | OPT.QtyItemCapacity | OPT.QtyTU |
      | pi_1                               | p_1                           | 5               | 5         | 5               | 0                   | 1         |
      | pi_2                               | p_2                           | 5               | 5         | 5               | 5                   | 1         |

    When load M_InOut:
      | M_InOut_ID.Identifier | M_InOutLine_ID.Identifier | QtyEntered | DocStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier |
      | shipment_1            | shipmentLine_1            | 10         | CO        | o_1                       | ol_1                          |

    And the shipment identified by shipment_1 is reversed

    And after not more than 30s, there are no records in EDI_Desadv_Pack_Item

    And after not more than 30s, there are no records in EDI_Desadv_Pack