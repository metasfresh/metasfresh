@from:cucumber
@ghActions:run_on_executor7
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@F00230
Feature: mobileUI Picking - Pick catch weight TUs

  ## F00230: MobileUI Picking
  ## Catch weight products, picked as real (non-aggregated) TUs with one CU each, weight taken from the label.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.handlingunits.HUConstants.Fresh_QuickShipment
    And set sys config boolean value true for sys config de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PackCUsToTU

    And metasfresh contains M_PickingSlot:
      | Identifier | PickingSlot | IsDynamic |
      | 200.0      | 200.0       | Y         |

    And metasfresh contains M_Products:
      | Identifier | X12DE355 | GTIN           |
      | productA   | PCE      | 97311876341811 |
      | productB   | PCE      | 97311876341828 |
      | productC   | PCE      | 97311876341835 |
      | productD   | PCE      | 97311876341842 |
    And metasfresh contains C_UOM_Conversions
      | M_Product_ID.Identifier | FROM_C_UOM_ID.X12DE355 | TO_C_UOM_ID.X12DE355 | MultiplyRate | OPT.IsCatchUOMForProduct |
      | productA                | PCE                    | KGM                  | 0.10         | Y                        |
      | productB                | PCE                    | KGM                  | 0.10         | Y                        |
      | productC                | PCE                    | KGM                  | 0.10         | Y                        |
      | productD                | PCE                    | KGM                  | 0.10         | Y                        |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | TU         |
      | LU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name | HU_UnitType | IsCurrent |
      | TU                            | TU                    | TU   | TU          | Y         |
      | LU                            | LU                    | LU   | LU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | TU                         | TU                            | 0   | MI       |                                  |
      | LU                         | LU                            | 10  | HU       | TU                               |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | TUx4_A                             | TU                         | productA                | 4   | 2000-01-01 |
      | TUx3_C                             | TU                         | productC                | 3   | 2000-01-01 |
      | TUx4_D                             | TU                         | productD                | 4   | 2000-01-01 |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | PS         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | PL         | PS                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | PLV        | PL             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | productA     | 5.0      | KGM               | CatchWeight          | Normal                        |
      | PLV                    | productB     | 5.0      | KGM               | CatchWeight          | Normal                        |
      | PLV                    | productC     | 5.0      | KGM               | CatchWeight          | Normal                        |
      | PLV                    | productD     | 5.0      | KGM               | CatchWeight          | Normal                        |

    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy  | IsAllowCompletingPartialPickingJob | IsCatchWeightTUPickingEnabled |
      | Y                   | CREATE_COMPLETE_CLOSE | Y                                  | Y                             |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer   | customer | N            | Y              | PS                            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN       | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | customerLocation | Dummy_GLN | customer                 | true                | true         |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2024-03-20   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | lineA                         | productA                | 0       | 12       | PCE          |
      | inventory                 | lineB                         | productB                | 0       | 10       | PCE          |
      | inventory                 | lineC                         | productC                | 0       | 9        | PCE          |
      | inventory                 | lineD                         | productD                | 0       | 20       | PCE          |
    And complete inventory with inventoryIdentifier 'inventory'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID      |
      | lineA              | pickFromCU_A |
      | lineB              | pickFromCU_B |
      | lineC              | pickFromCU_C |
      | lineD              | pickFromCU_D |

    # Real (non-aggregated) source TUs, one CU each, on one LU per TU product
    And transform CU to new TUs
      | sourceCU     | cuQty | M_HU_PI_Item_Product_ID | OPT.resultedNewTUs                           |
      | pickFromCU_A | 12    | TUx4_A                  | pickFromTU_A1,pickFromTU_A2,pickFromTU_A3    |
      | pickFromCU_C | 9     | TUx3_C                  | pickFromTU_C1,pickFromTU_C2,pickFromTU_C3    |
      | pickFromCU_D | 20    | TUx4_D                  | pickFromTU_D1,pickFromTU_D2,pickFromTU_D3,pickFromTU_D4,pickFromTU_D5 |
    And aggregate TUs to new LU
      | sourceTUs                                                              | newLUs      |
      | pickFromTU_A1,pickFromTU_A2,pickFromTU_A3                              | pickFromLU_A |
      | pickFromTU_C1,pickFromTU_C2,pickFromTU_C3                              | pickFromLU_C |
      | pickFromTU_D1,pickFromTU_D2,pickFromTU_D3,pickFromTU_D4,pickFromTU_D5 | pickFromLU_D |







# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S33252_TC1
  Scenario: Pick TUs and CUs with a label weight, weight reaches shipment line, invoice candidate and invoice
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | SO         | true    | customer                 | 2024-03-26  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | LA         | SO                    | productA                | 8          | TUx4_A                                 |
      | LB         | SO                    | productB                | 3          |                                        |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ssA        | LA                        | N             |
      | ssB        | LB                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # A label scan picks exactly one TU, its weight is the label weight
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QRCode                     |
      | productA              | pickFromTU_A1 | LMQ#1#3.535#08.11.2025#500 |
      | productA              | pickFromTU_A2 | LMQ#1#3.535#08.11.2025#500 |
    # CUs are packed into a new TU on the LU
    And set picking target as new TU identified by TU
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QRCode                     |
      | productB              | pickFromCU_B | LMQ#1#3.535#08.11.2025#500 |
      | productB              | pickFromCU_B | LMQ#1#3.035#08.11.2025#500 |
      | productB              | pickFromCU_B | LMQ#1#3.025#08.11.2025#500 |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssA
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | M_TU_HU_ID    | Processed |
      | 3.535             | KGM          | 4         | 1     | pickFromTU_A1 | N         |
      | 3.535             | KGM          | 4         | 1     | pickFromTU_A2 | N         |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssB
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | Processed |
      | 3.535             | KGM          | 1         | N         |
      | 3.035             | KGM          | 1         | N         |
      | 3.025             | KGM          | 1         | N         |

    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | ssA                              | shipment              | CO            |

    # pick records of the TU product keep the label weight after the shipment is created
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssA
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | M_TU_HU_ID    | Processed | M_InOutLine_ID |
      | 3.535             | KGM          | 4         | 1     | pickFromTU_A1 | Y         | shipmentLineA  |
      | 3.535             | KGM          | 4         | 1     | pickFromTU_A2 | Y         | shipmentLineA  |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssB
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | Processed | M_InOutLine_ID |
      | 3.535             | KGM          | 1         | Y         | shipmentLineB  |
      | 3.035             | KGM          | 1         | Y         | shipmentLineB  |
      | 3.025             | KGM          | 1         | Y         | shipmentLineB  |

    # the weight of the picked TUs must be summed up onto the shipment line, like the weight of the picked CUs
    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch |
      | shipmentLineA | productA     | 8           | 7.070             |
      | shipmentLineB | productB     | 3           | 9.595             |

    # bind each IC to its own order line: IC creation per order line is async, so the IC id order is not the order line order
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | icA                               | LA                        |
      | icB                               | LB                        |
    And validate created C_InvoiceCandidate_InOutLine
      | C_InvoiceCandidate_InOutLine_ID.Identifier | C_Invoice_Candidate_ID | M_InOutLine_ID | QtyDeliveredInUOM_Catch |
      | icShipLineA                                | icA                    | shipmentLineA  | 7.070                   |
      | icShipLineB                                | icB                    | shipmentLineB  | 9.595                   |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyDeliveredInUOM |
      | icA                    | 7.070             |
      | icB                    | 9.595             |

    When process invoice candidates
      | C_Invoice_Candidate_ID |
      | icA,icB                |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | icA                               | invoice                 |
    And validate created invoice lines
      | C_InvoiceLine_ID | C_Invoice_ID | M_Product_ID | QtyInvoiced | OPT.QtyInvoicedInPriceUOM |
      | invoiceLineA     | invoice      | productA     | 8           | 7.070                     |
      | invoiceLineB     | invoice      | productB     | 3           | 9.595                     |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S33252_TC2
  Scenario: Pick a TU with a typed weight, rounded weight reaches pick record, shipment line and invoice candidate
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | SO         | true    | customer                 | 2024-03-26  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | LC         | SO                    | productC                | 3          | TUx3_C                                 |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ssC        | LC                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # one TU holding one CU of 3 Stk, the weight is typed in
    And pick lines
      | PickingLine.byProduct | PickFromHU    | QtyPicked | CatchWeight |
      | productC              | pickFromTU_C1 | 1         | 5.4125      |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssC
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | M_TU_HU_ID    | Processed |
      | 5.413             | KGM          | 3         | 1     | pickFromTU_C1 | N         |

    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | ssC                              | shipment              | CO            |
    # pick records keep the weight after the shipment is created
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssC
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | Processed | M_InOutLine_ID |
      | 5.413             | KGM          | 3         | 1     | Y         | shipmentLineC  |
    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch |
      | shipmentLineC | productC     | 3           | 5.413             |

    And after not more than 60s locate invoice candidates by order id:
      | C_Order_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | SO                    | icC                               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyDeliveredInUOM |
      | icC                    | 5.413             |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S33252_TC2a
  Scenario: Pick five TUs in one step with a typed total weight, weight is split over the TUs and summed on the shipment line
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | SO         | true    | customer                 | 2024-03-26  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | LD         | SO                    | productD                | 20         | TUx4_D                                 |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ssD        | LD                        | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    And set picking target as new LU identified by LU
    # one row picks all 5 TUs, the typed weight is the total
    And pick lines
      | PickingLine.byProduct | PickFromHU   | QtyPicked | CatchWeight |
      | productD              | pickFromLU_D | 5         | 39.589      |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssD
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | Processed |
      | 7.918             | KGM          | 4         | 1     | N         |
      | 7.918             | KGM          | 4         | 1     | N         |
      | 7.918             | KGM          | 4         | 1     | N         |
      | 7.918             | KGM          | 4         | 1     | N         |
      | 7.917             | KGM          | 4         | 1     | N         |

    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | OPT.DocStatus |
      | ssD                              | shipment              | CO            |
    # pick records keep the weight after the shipment is created
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssD
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | Processed | M_InOutLine_ID |
      | 7.918             | KGM          | 4         | 1     | Y         | shipmentLineD  |
      | 7.918             | KGM          | 4         | 1     | Y         | shipmentLineD  |
      | 7.918             | KGM          | 4         | 1     | Y         | shipmentLineD  |
      | 7.918             | KGM          | 4         | 1     | Y         | shipmentLineD  |
      | 7.917             | KGM          | 4         | 1     | Y         | shipmentLineD  |
    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch |
      | shipmentLineD | productD     | 20          | 39.589            |

    And after not more than 60s locate invoice candidates by order id:
      | C_Order_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | SO                    | icD                               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyDeliveredInUOM |
      | icD                    | 39.589            |


# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S33252_TC3c
  Scenario: Pick a whole TU with a typed weight and no LU target, the TU goes onto a new LU on completion and the weight reaches the shipment line
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | InvoiceRule |
      | SO         | true    | customer      | 2024-03-26  | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | LA         | SO         | productA     | 4          | TUx4_A                  |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ssA        | LA             | N             |

    And start picking job for sales order identified by SO
    And scan picking slot identified by 200.0
    # no picking target is set: the whole TU is picked on its own, without an LU
    And pick lines
      | PickingLine.byProduct | PickFromHU    | QtyPicked | CatchWeight |
      | productA              | pickFromTU_A1 | 1         | 8.000       |
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssA
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | M_TU_HU_ID    | Processed |
      | 8.000             | KGM          | 4         | 1     | pickFromTU_A1 | N         |

    And complete picking job
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | DocStatus |
      | ssA                   | shipment   | CO        |
    # completing the job puts the TU onto a new LU; the pick record keeps the weight
    And validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by ssA
      | QtyDeliveredCatch | Catch_UOM_ID | QtyPicked | QtyTU | M_TU_HU_ID    | M_LU_HU_ID | Processed | M_InOutLine_ID |
      | 8.000             | KGM          | 4         | 1     | pickFromTU_A1 | pickedLU   | Y         | shipmentLineA  |
    And validate the created shipment lines by id
      | Identifier    | M_Product_ID | movementqty | QtyDeliveredCatch |
      | shipmentLineA | productA     | 4           | 8.000             |
