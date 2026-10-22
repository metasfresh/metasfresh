@from:cucumber
@allure.label.epic:E0220_Financial
@allure.label.feature:F00969.2_Umsatzreport_Jasper
@ghActions:run_on_executor5
Feature: Daily Revenue based on Delivery
## Tagesumsatz (auf Basis Lieferung): one row per day, summing the net value of goods
## shipped out of the warehouse that day -- billed-or-not, valuing the invoiced portion
## of a delivery line at the actual invoice net and the open portion at the invoice
## candidate's PriceActual x open qty. Customer returns net the day down.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_ddr     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | PricePrecision |
      | pl_ddr     | ps_ddr                        | DE                        | EUR                 | true  | 3              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_ddr    | pl_ddr                    | 2026-04-01 |
    And metasfresh contains M_Products:
      | Identifier  |
      | product_ddr |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_ddr     | plv_ddr                           | product_ddr             | 5.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_ddr | N            | Y              | ps_ddr                        |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_ddr      | customer_ddr             | Y                   | Y                   |


  # ##########################################################################
  # TC1 -- reference case: one unbilled + one billed delivery, same day
  # ##########################################################################
  @from:cucumber
  @Id:S26873_TC1
  @allure.label.epic:E0220_Financial
  @allure.label.feature:F00969.2_Umsatzreport_Jasper
  Scenario: Unbilled and billed deliveries on the same day sum to the day's total
    Given metasfresh has date and time 2026-05-04T09:00:00+01:00[Europe/Berlin]

    # Delivery A: qty 10 @ 5.00 = 50.00, left unbilled (its IC carries NetAmtToInvoice = 50.00)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc1a | true    | customer_ddr             | 2026-05-04  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_tc1a | order_tc1a            | product_ddr             | 10         |
    And the order identified by order_tc1a is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc1a | orderLine_tc1a            | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc1a                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc1a                    | shipment_tc1a         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | NetAmtToInvoice |
      | ic_tc1a                           | orderLine_tc1a            | 10           | 50.00           |

    # Delivery B: qty 4 @ 5.00 = 20.00, invoiced -- its own order so its IC is independent of A's.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc1b | true    | customer_ddr             | 2026-05-04  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_tc1b | order_tc1b            | product_ddr             | 4          |
    And the order identified by order_tc1b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc1b | orderLine_tc1b            | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc1b                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc1b                    | shipment_tc1b         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_tc1b                           | orderLine_tc1b            | 4            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_tc1b                           |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_tc1b                           | invoice_tc1b            |
    And validate created invoice lines
      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice_tc1b            | product_ddr             | 4           | 20.00      |

    # The function under test does not exist yet -- this call fails (TDD RED).
    Then Docs_Daily_Delivery_Revenue_Report for C_BPartner "customer_ddr" between "2026-05-04" and "2026-05-04" returns:
      | Day        | DeliveryRevenueNet |
      | 2026-05-04 | 70.00              |


  # ##########################################################################
  # TC2 -- a customer return on the same day nets the day's total down
  # ##########################################################################
  @from:cucumber
  @Id:S26873_TC2
  @allure.label.epic:E0220_Financial
  @allure.label.feature:F00969.2_Umsatzreport_Jasper
  Scenario: A customer return on the delivery's day nets that day's total down
    Given metasfresh has date and time 2026-05-05T09:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Warehouse:
      | Identifier    | IsQualityReturnWarehouse |
      | warehouse_tc2 | Y                        |

    # Delivery A: qty 10 @ 5.00 = 50.00, unbilled
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc2a | true    | customer_ddr             | 2026-05-05  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_tc2a | order_tc2a            | product_ddr             | 10         |
    And the order identified by order_tc2a is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc2a | orderLine_tc2a            | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc2a                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc2a                    | shipment_tc2a         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | NetAmtToInvoice |
      | ic_tc2a                           | orderLine_tc2a            | 10           | 50.00           |

    # Delivery B: qty 4 @ 5.00 = 20.00, invoiced
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc2b | true    | customer_ddr             | 2026-05-05  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_tc2b | order_tc2b            | product_ddr             | 4          |
    And the order identified by order_tc2b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc2b | orderLine_tc2b            | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc2b                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc2b                    | shipment_tc2b         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_tc2b                           | orderLine_tc2b            | 4            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_tc2b                           |

    # Customer return of qty 2 @ 5.00 = 10.00, from delivery A, landing on the same day D.
    And generate customer return from shipment
      | M_InOut_ID.Identifier | CustomerReturn_ID |
      | shipment_tc2a         | return_tc2        |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | return_tc2 | 2026-05-05   |
    And the return inOut identified by return_tc2 is completed

    # Day total: 50.00 (open A) + 20.00 (invoiced B) - 10.00 (return) = 60.00
    Then Docs_Daily_Delivery_Revenue_Report for C_BPartner "customer_ddr" between "2026-05-05" and "2026-05-05" returns:
      | Day        | DeliveryRevenueNet |
      | 2026-05-05 | 60.00              |


  # ##########################################################################
  # TC3 -- a single delivered line is partially invoiced, cleanly-dividing price
  # ##########################################################################
  @from:cucumber
  @Id:S26873_TC3
  @allure.label.epic:E0220_Financial
  @allure.label.feature:F00969.2_Umsatzreport_Jasper
  Scenario: A partially-invoiced delivery line contributes invoiced-plus-open with no gap
    Given metasfresh has date and time 2026-05-06T09:00:00+01:00[Europe/Berlin]

    # One delivery, qty 10 @ 5.00 = 50.00 total; only qty 6 (30.00) gets invoiced, qty 4 (20.00) stays open.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc3  | true    | customer_ddr             | 2026-05-06  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_tc3 | order_tc3             | product_ddr             | 10         |
    And the order identified by order_tc3 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc3 | orderLine_tc3             | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc3                     | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc3                     | shipment_tc3          |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | NetAmtToInvoice |
      | ic_tc3                            | orderLine_tc3             | 10           | 50.00           |

    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | ic_tc3                            | 6                         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | NetAmtToInvoice |
      | ic_tc3                            | orderLine_tc3             | 6            | 30.00           |

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_tc3                            |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_tc3                            | invoice_tc3             |
    And validate created invoice lines
      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice_tc3             | product_ddr             | 6           | 30.00      |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | NetAmtToInvoice |
      | ic_tc3                            | orderLine_tc3             | 4            | 20.00           |

    # Day total: 30.00 (invoiced) + 20.00 (open) = 50.00 -- no double count, no gap.
    Then Docs_Daily_Delivery_Revenue_Report for C_BPartner "customer_ddr" between "2026-05-06" and "2026-05-06" returns:
      | Day        | DeliveryRevenueNet |
      | 2026-05-06 | 50.00              |


  # ##########################################################################
  # TC3b -- partial invoice, price that does NOT split cleanly to cents
  # ##########################################################################
  @from:cucumber
  @Id:S26873_TC3b
  @allure.label.epic:E0220_Financial
  @allure.label.feature:F00969.2_Umsatzreport_Jasper
  Scenario: A non-dividing price sums the invoiced actual plus the rounded open portion
    Given metasfresh has date and time 2026-05-07T09:00:00+01:00[Europe/Berlin]

    # One delivery, qty 7 @ 0.333; qty 5 invoiced (5 x 0.333 = 1.665, invoice rounds to 1.67 or 1.66
    # depending on the invoice's own rounding -- asserted from the actual invoice line, not recomputed),
    # qty 2 stays open (round(0.333 x 2) = round(0.666) = 0.67).
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc3b | true    | customer_ddr             | 2026-05-07  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | Price |
      | orderLine_tc3b | order_tc3b            | product_ddr             | 7          | 0.333 |
    And the order identified by order_tc3b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc3b | orderLine_tc3b            | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc3b                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc3b                    | shipment_tc3b         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_tc3b                           | orderLine_tc3b            | 7            |

    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice_Override |
      | ic_tc3b                           | 5                         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_tc3b                           | orderLine_tc3b            | 5            |

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_tc3b                           |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_tc3b                           | invoice_tc3b            |

    # Capture the actual invoiced net -- the open portion (round(0.333 x 2) = 0.67) is added to it below,
    # so the report total is NOT required to equal round(0.333 x 7) = 2.33 (see REQUIREMENTS.md TC3b).
    And validate created invoice lines
      | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | invoice_tc3b            | product_ddr             | 5           | 1.67       |

    # Day total: 1.67 (actual invoiced net) + 0.67 (round(0.333 x 2) open) = 2.34
    Then Docs_Daily_Delivery_Revenue_Report for C_BPartner "customer_ddr" between "2026-05-07" and "2026-05-07" returns:
      | Day        | DeliveryRevenueNet |
      | 2026-05-07 | 2.34               |


  # ##########################################################################
  # TC4 -- the C_BPartner_ID filter returns only the filtered partner's total
  # ##########################################################################
  @from:cucumber
  @Id:S26873_TC4
  @allure.label.epic:E0220_Financial
  @allure.label.feature:F00969.2_Umsatzreport_Jasper
  Scenario: Filtering by business partner excludes the other partner's deliveries
    Given metasfresh has date and time 2026-05-08T09:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_BPartners without locations:
      | Identifier    | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_tc4b | N            | Y              | ps_ddr                        |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_tc4b     | customer_tc4b            | Y                   | Y                   |

    # Partner A: qty 10 @ 5.00 = 50.00, unbilled
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc4a | true    | customer_ddr             | 2026-05-08  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_tc4a | order_tc4a            | product_ddr             | 10         |
    And the order identified by order_tc4a is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc4a | orderLine_tc4a            | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc4a                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc4a                    | shipment_tc4a         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | NetAmtToInvoice |
      | ic_tc4a                           | orderLine_tc4a            | 10           | 50.00           |

    # Partner B: qty 20 @ 5.00 = 100.00, unbilled -- a different day total if (wrongly) summed together.
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_tc4b | true    | customer_tc4b            | 2026-05-08  |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_tc4b | order_tc4b            | product_ddr             | 20         |
    And the order identified by order_tc4b is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_tc4b | orderLine_tc4b            | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | schedule_tc4b                    | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | schedule_tc4b                    | shipment_tc4b         |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | NetAmtToInvoice |
      | ic_tc4b                           | orderLine_tc4b            | 20           | 100.00          |

    # Filtered to partner A: only the 50.00 delivery, not partner B's 100.00.
    Then Docs_Daily_Delivery_Revenue_Report for C_BPartner "customer_ddr" between "2026-05-08" and "2026-05-08" returns:
      | Day        | DeliveryRevenueNet |
      | 2026-05-08 | 50.00              |

    # Filtered to partner B: only its own 100.00.
    Then Docs_Daily_Delivery_Revenue_Report for C_BPartner "customer_tc4b" between "2026-05-08" and "2026-05-08" returns:
      | Day        | DeliveryRevenueNet |
      | 2026-05-08 | 100.00             |

    # A date range excluding day D returns no row for D.
    Then Docs_Daily_Delivery_Revenue_Report between "2026-05-09" and "2026-05-09" returns:
      | Day | DeliveryRevenueNet |
