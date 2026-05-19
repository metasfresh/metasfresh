@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00104
@ghActions:run_on_executor1
Feature: Manual delivery / order stop on C_BPartner

  When Finance flags a business partner with IsDeliveryStop=Y (with a reason),
  the system creates a manual M_Shipment_Constraint and blocks the partner's
  sales and purchase orders from being completed. Shipment and receipt schedules
  for the partner are flagged with IsDeliveryStop=Y so downstream
  shipment / receipt generation is gated. Clearing the flag re-opens the partner.
  A separately-existing dunning-sourced constraint stays active and keeps the
  partner blocked even after the manual constraint is cleared.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-05-19T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier |
      | product    |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_so      |
      | ps_po      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_so              | DE                    | EUR                 | true  | false         | 2              |
      | pl_po      | ps_po              | DE                    | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so     | pl_so          |
      | plv_po     | pl_po          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.0     | PCE               | Normal                        |
      | pp_po      | plv_po                 | product      |  8.0     | PCE               | Normal                        |

  @from:cucumber
  Scenario: TC-1 — Setting IsDeliveryStop=Y on a customer blocks sales-order completion; clearing it lets the order complete
    Given metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps_so              |

    # Finance flags the partner — must come with a reason; the C_BPartner_DeliveryStop
    # interceptor will create the M_Shipment_Constraint automatically.
    And the following c_bpartner is changed
      | Identifier | IsDeliveryStop | DeliveryStopReason     |
      | customer   | Y              | Customer asked to wait |
    Then validate M_Shipment_Constraint:
      | Bill_BPartner_ID | IsManual | IsDeliveryStop | DeliveryStopReason     |
      | customer         | Y        | Y              | Customer asked to wait |

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID |
      | order_so   | true    | customer      | 2026-05-19  | ps_so              |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_so | order_so   | product      | 10         |

    # Hard block: assertNotDeliveryStopped throws at TIMING_BEFORE_PREPARE.
    Then the order identified by order_so cannot be completed

    # Clear the flag → manual constraint deactivates → SO completes.
    When the following c_bpartner is changed
      | Identifier | IsDeliveryStop |
      | customer   | N              |
    Then there is no active manual M_Shipment_Constraint:
      | Bill_BPartner_ID |
      | customer         |
    And the order identified by order_so is completed

  @from:cucumber
  Scenario: TC-2 — Shipment schedules for a blocked partner carry IsDeliveryStop=Y
    Given metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps_so              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID |
      | order_so   | true    | customer      | 2026-05-19  | ps_so              |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_so | order_so   | product      | 10         |
    When the order identified by order_so is completed
    Then after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID | IsToRecompute |
      | shipmentSchedule | orderLine_so   | N             |

    # Flag the partner — interceptor invalidates the shipment schedule.
    When the following c_bpartner is changed
      | Identifier | IsDeliveryStop | DeliveryStopReason  |
      | customer   | Y              | Frozen by finance   |
    And recompute shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule                 |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsDeliveryStop |
      | shipmentSchedule      | true           |

    # Clear the flag → recompute → schedule unblocks.
    When the following c_bpartner is changed
      | Identifier | IsDeliveryStop |
      | customer   | N              |
    And recompute shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule                 |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID |
      | shipmentSchedule      |
    Then after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID | IsDeliveryStop |
      | shipmentSchedule      | false          |

  @from:cucumber
  Scenario: TC-3 — Setting IsDeliveryStop=Y on a vendor blocks purchase-order completion; clearing it lets the PO complete
    Given metasfresh contains C_BPartners:
      | Identifier | IsVendor | M_PricingSystem_ID |
      | vendor     | Y        | ps_po              |

    And the following c_bpartner is changed
      | Identifier | IsDeliveryStop | DeliveryStopReason     |
      | vendor     | Y              | Vendor in dispute      |
    Then validate M_Shipment_Constraint:
      | Bill_BPartner_ID | IsManual | IsDeliveryStop | DeliveryStopReason |
      | vendor           | Y        | Y              | Vendor in dispute  |

    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | DocBaseType | DeliveryRule | DeliveryViaRule |
      | order_po   | false   | vendor        | 2026-05-19  | ps_po              | POO         | F            | S               |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_po | order_po   | product      | 10         |

    # Hard block: the C_Order.assertNotDeliveryStopped guard now covers POs too.
    Then the order identified by order_po cannot be completed

    # Clear the flag → manual constraint deactivates → PO completes.
    When the following c_bpartner is changed
      | Identifier | IsDeliveryStop |
      | vendor     | N              |
    Then there is no active manual M_Shipment_Constraint:
      | Bill_BPartner_ID |
      | vendor           |
    And the order identified by order_po is completed

  @from:cucumber
  Scenario: TC-4 — Receipt schedules for a blocked vendor carry IsDeliveryStop=Y (gating both HU and non-HU receipt generation)
    Given metasfresh contains C_BPartners:
      | Identifier | IsVendor | M_PricingSystem_ID |
      | vendor     | Y        | ps_po              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | DocBaseType | DeliveryRule | DeliveryViaRule |
      | order_po   | false   | vendor        | 2026-05-19  | ps_po              | POO         | F            | S               |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_po | order_po   | product      | 10         |
    When the order identified by order_po is completed
    Then after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID | IsDeliveryStop |
      | receiptSchedule      | order_po   | orderLine_po   | vendor        | vendor                 | product      | 10         | warehouseStd   | false          |

    # Flag the partner — M_Shipment_Constraint interceptor updates the receipt schedule
    # synchronously (updateReceiptScheduleDeliveryStop runs in TYPE_AFTER_NEW/CHANGE).
    When the following c_bpartner is changed
      | Identifier | IsDeliveryStop | DeliveryStopReason  |
      | vendor     | Y              | Vendor in dispute   |
    Then after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID | IsDeliveryStop |
      | receiptSchedule      | order_po   | orderLine_po   | vendor        | vendor                 | product      | 10         | warehouseStd   | true           |

    # Clear the flag → receipt schedule unblocks.
    When the following c_bpartner is changed
      | Identifier | IsDeliveryStop |
      | vendor     | N              |
    Then after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID | IsDeliveryStop |
      | receiptSchedule      | order_po   | orderLine_po   | vendor        | vendor                 | product      | 10         | warehouseStd   | false          |

  @from:cucumber
  Scenario: TC-6 — A dunning-sourced constraint keeps the partner blocked after the manual constraint is cleared
    Given metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | ps_so              |

    # Both routes — manual (BPartner-flagged) AND system (dunning-sourced) — are active.
    And the following c_bpartner is changed
      | Identifier | IsDeliveryStop | DeliveryStopReason     |
      | customer   | Y              | Customer asked to wait |
    And metasfresh contains M_Shipment_Constraint sourced from dunning:
      | Bill_BPartner_ID | DeliveryStopReason      |
      | customer         | Dunning level 3 reached |
    Then validate M_Shipment_Constraint:
      | Bill_BPartner_ID | IsManual | IsDeliveryStop | DeliveryStopReason      |
      | customer         | Y        | Y              | Customer asked to wait  |
      | customer         | N        | Y              | Dunning level 3 reached |

    # Finance clears the manual flag — the dunning constraint survives by design
    # (deactivateManualDeliveryStop only touches IsManual=Y rows).
    When the following c_bpartner is changed
      | Identifier | IsDeliveryStop |
      | customer   | N              |
    Then there is no active manual M_Shipment_Constraint:
      | Bill_BPartner_ID |
      | customer         |
    And validate M_Shipment_Constraint:
      | Bill_BPartner_ID | IsManual | IsDeliveryStop | DeliveryStopReason      |
      | customer         | N        | Y              | Dunning level 3 reached |

    # Sales orders for the partner remain blocked — the surviving dunning constraint
    # is enough to fail the assertNotDeliveryStopped check.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID |
      | order_so   | true    | customer      | 2026-05-19  | ps_so              |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_so | order_so   | product      | 10         |
    Then the order identified by order_so cannot be completed
