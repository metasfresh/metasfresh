@from:cucumber
Feature: In effect invoice candidates

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-10-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Products:
      | Identifier | Name                |
      | product_SO | product_SO_14092022 |
      | product_PO | product_PO_14092022 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value       |
      | ps_1       | pricing_system_name_SO | 14092022_SO |
      | ps_2       | pricing_system_name_PO | 14092022_PO |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_SO      | ps_1                          | DE                        | EUR                 | PriceListName_SO | true  | false         | 2              |
      | pl_PO      | ps_2                          | DE                        | EUR                 | PriceListName_PO | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_SO     | pl_SO                     | salesOrder-PLV    | 2022-08-01 |
      | plv_PO     | pl_PO                     | purchaseOrder-PLV | 2022-08-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_SO                            | product_SO              | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_PO                            | product_PO              | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier        | Name                          | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier | OPT.InvoiceRule | OPT.PO_InvoiceRule |
      | bpartner_Customer | BPartnerNameCustomer_14092022 | Y              | N            | ps_1                          | D               |                    |
      | bpartner_Vendor   | BPartnerNameVendor_14092022   | N              | Y            | ps_2                          |                 | I                  |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | bpartnerLocation_1 | bpartner_Customer        | true         | true         |
      | bpartnerLocation_2 | bpartner_Vendor          | true         | true         |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouse                 | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locator                 | warehouse                 | Hauptlager |


  @from:cucumber
  @Id:S0182_100
  Scenario: create sales order, complete it and validate that the IC created is in effect, reactivate it and validate that IC went out of effect then complete it again, close it and generate the invoice
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_1    | true    | bpartner_Customer        | 2022-09-14  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | product_SO              | 100        |

    When the order identified by order_1 is completed

    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_1 | orderLine_1               | N             |
    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_1                     | orderLine_1               | 0                | 0            | true           |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | shipmentSchedule_1               | 50                        |
    When 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | shipmentSchedule_1               | D            | true                | false       |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | shipmentSchedule_1               | inOut_1               |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_1                     | orderLine_1               | 50               | 50           | true           |

    When the order identified by order_1 is reactivated

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | 50           | false          | C_OrderLine   | orderLine_1              | product_SO                  |
    And process invoice candidate expecting error
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |

    When the order identified by order_1 is completed

    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | 50           | true           | C_OrderLine   | orderLine_1              | product_SO                  |

    When the order identified by order_1 is closed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_1                     | 50           | true           | C_OrderLine   | orderLine_1              | product_SO                  |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_1                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoiceCand_1                     |


  @from:cucumber
  @Id:S0182_200
  Scenario: create sales order, complete it and validate that the IC created is in effect, then void it and validate that IC went out of effect
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | order_2    | true    | bpartner_Customer        | 2022-09-14  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_2 | order_2               | product_SO              | 100        |

    When the order identified by order_2 is completed

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_2                     | orderLine_2               | 0                | 0            | true           |

    When the order identified by order_2 is voided

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_2                     | 0            | false          | C_OrderLine   | orderLine_2              | product_SO                  |


  @from:cucumber
  @Id:S0182_300
  Scenario: create purchase order, complete it and validate that the IC created is in effect, reactivate it and validate that IC went out of effect then complete it again and generate the invoice
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType |
      | order_3    | false   | bpartner_Vendor          | 2022-09-14  | po_ref_mock     | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_3 | order_3               | product_PO              | 100        |

    When the order identified by order_3 is completed

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_3                     | orderLine_3               | 100          | true           |

    When the order identified by order_3 is reactivated

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_3                     | 0            | false          | C_OrderLine   | orderLine_3              | product_PO                  |
    And process invoice candidate expecting error
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_3                     |

    When the order identified by order_3 is completed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_3                     | 100          | true           | C_OrderLine   | orderLine_3              | product_PO                  |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_3                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_3               | invoiceCand_3                     |


  @from:cucumber
  @Id:S0182_400
  Scenario: create purchase order, complete it and validate that the IC created is in effect, then void it and validate that IC went out of effect
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType |
      | order_4    | false   | bpartner_Vendor          | 2022-09-14  | po_ref_mock     | POO             |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_4 | order_4               | product_PO              | 100        |

    When the order identified by order_4 is completed

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_4                     | orderLine_4               | 100          | true           |

    When the order identified by order_4 is voided

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_4                     | 0            | false          | C_OrderLine   | orderLine_4              | product_PO                  |

  @from:cucumber
  @Id:S0182_500
  Scenario: create shipment, complete it and validate that the IC created is in effect, reactivate it and validate that IC went out of effect then complete it again, close it and generate the invoice
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_5               | true    | bpartner_Customer        | bpartnerLocation_1                | warehouse                 | A            | P               | I               | 2022-09-14   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier | OPT.IsManualPackingMaterial |
      | inOutLine_5               | inOut_5               | product_SO                  | 100        | 100         | PCE     | locator                     | Y                           |

    When the shipment identified by inOut_5 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_5                     | inOutLine_5                   | 100              | 100          | true           |

    When the shipment identified by inOut_5 is reactivated

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_5                     | 0            | false          | M_InOutLine   | inOutLine_5              | product_SO                  |

    When the shipment identified by inOut_5 is completed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_5                     | 100          | true           | M_InOutLine   | inOutLine_5              | product_SO                  |

    When the shipment identified by inOut_5 is closed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_5                     | 100          | true           | M_InOutLine   | inOutLine_5              | product_SO                  |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_5                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_5               | invoiceCand_5                     |


  @from:cucumber
  @Id:S0182_600
  Scenario: create shipment, complete it and validate that the IC created is in effect, then revert it and validate that IC went out of effect
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_6               | true    | bpartner_Customer        | bpartnerLocation_1                | warehouse                 | A            | P               | I               | 2022-09-14   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier | OPT.IsManualPackingMaterial |
      | inOutLine_6               | inOut_6               | product_SO                  | 100        | 100         | PCE     | locator                     | Y                           |

    When the shipment identified by inOut_6 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_6                     | inOutLine_6                   | 100              | 100          | true           |

    When the shipment identified by inOut_6 is reversed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_6                     | 0            | false          | M_InOutLine   | inOutLine_6              | product_SO                  |


  @from:cucumber
  @Id:S0182_700
  Scenario: create shipment, complete it and validate that the IC created is in effect, then void it and validate that IC went out of effect
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_7               | true    | bpartner_Customer        | bpartnerLocation_1                | warehouse                 | A            | P               | I               | 2022-09-14   | C-           | 5            | MMS             | MS             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier | OPT.IsManualPackingMaterial |
      | inOutLine_7               | inOut_7               | product_SO                  | 100        | 100         | PCE     | locator                     | Y                           |

    When the shipment identified by inOut_7 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_7                     | inOutLine_7                   | 100              | 100          | true           |

    When the shipment identified by inOut_7 is reactivated

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_7                     | 0            | false          | M_InOutLine   | inOutLine_7              | product_SO                  |

    When the shipment identified by inOut_7 is voided

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_7                     | 0            | false          | M_InOutLine   | inOutLine_7              | product_SO                  |


  @from:cucumber
  @Id:S0182_800
  Scenario: create receipt, complete it and validate that the IC created is in effect, reactivate it and validate that IC went out of effect then complete it again, close it and generate the invoice
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_8               | false   | bpartner_Vendor          | bpartnerLocation_2                | warehouse                 | A            | P               | I               | 2022-09-14   | V+           | 5            | MMR             | MR             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier | OPT.IsManualPackingMaterial |
      | inOutLine_8               | inOut_8               | product_PO                  | 100        | 100         | PCE     | locator                     | Y                           |

    When the material receipt identified by inOut_8 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_8                     | inOutLine_8                   | 100              | 100          | true           |

    When the material receipt identified by inOut_8 is reactivated

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_8                     | 0            | false          | M_InOutLine   | inOutLine_8              | product_PO                  |

    When the material receipt identified by inOut_8 is completed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_8                     | 100          | true           | M_InOutLine   | inOutLine_8              | product_PO                  |

    When the material receipt identified by inOut_8 is closed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_8                     | 100          | true           | M_InOutLine   | inOutLine_8              | product_PO                  |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCand_8                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_8               | invoiceCand_8                     |


  @from:cucumber
  @Id:S0182_900
  Scenario: create receipt, complete it and validate that the IC created is in effect, then revert it and validate that IC went out of effect
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_9               | false   | bpartner_Vendor          | bpartnerLocation_2                | warehouse                 | A            | P               | I               | 2022-09-14   | V+           | 5            | MMR             | MR             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier | OPT.IsManualPackingMaterial |
      | inOutLine_9               | inOut_9               | product_PO                  | 100        | 100         | PCE     | locator                     | Y                           |

    When the material receipt identified by inOut_9 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_9                     | inOutLine_9                   | 100              | 100          | true           |

    When the material receipt identified by inOut_9 is reversed

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_9                     | 0            | false          | M_InOutLine   | inOutLine_9              | product_PO                  |


  @from:cucumber
  @Id:S0182_1000
  Scenario: create receipt, complete it and validate that the IC created is in effect, then void it and validate that IC went out of effect
    Given metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | IsSOTrx | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Warehouse_ID.Identifier | DeliveryRule | DeliveryViaRule | FreightCostRule | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | inOut_10              | false   | bpartner_Vendor          | bpartnerLocation_2                | warehouse                 | A            | P               | I               | 2022-09-14   | V+           | 5            | MMR             | MR             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | MovementQty | UomCode | OPT.M_Locator_ID.Identifier | OPT.IsManualPackingMaterial |
      | inOutLine_10              | inOut_10              | product_PO                  | 100        | 100         | PCE     | locator                     | Y                           |

    When the material receipt identified by inOut_10 is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_InOutLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice | OPT.IsInEffect |
      | invoiceCand_10                    | inOutLine_10                  | 100              | 100          | true           |

    When the material receipt identified by inOut_10 is reactivated

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_10                    | 0            | false          | M_InOutLine   | inOutLine_10             | product_PO                  |

    When the material receipt identified by inOut_10 is voided

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsInEffect | OPT.TableName | OPT.Record_ID.Identifier | OPT.M_Product_ID.Identifier |
      | invoiceCand_10                    | 0            | false          | M_InOutLine   | inOutLine_10             | product_PO                  |

