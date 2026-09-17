@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
@ghActions:run_on_executor4
Feature: Purchase order with complex payment term
## F00600: Purchase Order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And load M_Shipper:
      | Identifier  | Name |
      | shipper_DHL | Dhl  |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_purchase | ps_1               | CH           | CHF           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_purchase | pl_purchase    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_purchase           | product      | 9.98     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps_1               |
      | shipper    | N        | N          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation  | vendor        | CH           | Y               | Y               |
      | shipperLocation | shipper       | CH           | Y               | Y               |
    And metasfresh contains C_BP_BankAccount
      | Identifier         | C_Currency_ID | C_BPartner_ID | IBAN                       |
      | vendor_CHF_account | CHF           | vendor        | CH93 0076 2011 6238 5295 7 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Name      | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | de_ch_tax  | Normal                        | de_ch_tax | 2021-04-02 | 2.5  | DE                       | CH                        |
      | ch_ch_tax  | Normal                        | ch_ch_tax | 2021-04-02 | 2.5  | CH                       | CH                        |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  Scenario: Purchase Order with complex Payment Term has order pay schedules after completion
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO      |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB1       | pt_PO            | 25      | 1          | OD                | 10    |
      | PTB2       | pt_PO            | 75      | 0          | LC                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO      | Y         | Y       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po1        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po1_l1     | po1        | product      | 10         |
    And the order identified by po1 is completed
    Then the order identified by po1 has following pay schedules
    # In the last line, dueamt is computed as total - previous due amounts, to avoid rounding issues
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB1                   | 2025-10-10 | 25.58  | WP     |
      | PTB2                   | 9999-12-01 | 76.72  | PR     |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  Scenario: Purchase Order with complex Payment Term has order pay schedules after completion (due date after day light saving change)
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO      |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB1       | pt_PO            | 25      | 7          | OD                | 10    |
      | PTB2       | pt_PO            | 75      | 0          | LC                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO      | Y         | Y       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po1        | N       | vendor        | 2025-10-20  | POO         | wh             | pt_PO            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po1_l1     | po1        | product      | 10         |
    And the order identified by po1 is completed
    Then the order identified by po1 has following pay schedules
    # In the last line, dueamt is computed as total - previous due amounts, to avoid rounding issues
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB1                   | 2025-10-27 | 25.58  | WP     |
      | PTB2                   | 9999-12-01 | 76.72  | PR     |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  Scenario: Order pay schedules are updated when LC date, BL date, ETA date are changed
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO_2    |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB21      | pt_PO_2          | 25      | 1          | OD                | 10    |
      | PTB22      | pt_PO_2          | 25      | 0          | LC                | 20    |
      | PTB23      | pt_PO_2          | 25      | 0          | BL                | 30    |
      | PTB24      | pt_PO_2          | 25      | 0          | ET                | 40    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO_2    | Y         | Y       |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po2        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO_2          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po2_l1     | po2        | product      | 10         |
    And the order identified by po2 is completed
    Then the order identified by po2 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB21                  | 2025-10-10 | 25.58  | WP     |
      | PTB22                  | 9999-12-01 | 25.58  | PR     |
      | PTB23                  | 9999-12-01 | 25.58  | PR     |
      | PTB24                  | 9999-12-01 | 25.56  | PR     |
    And update order
      | Identifier | LC_Date    |
      | po2        | 2025-10-15 |
    Then the order identified by po2 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB21                  | 2025-10-10 | 25.58  | WP     |
      | PTB22                  | 2025-10-15 | 25.58  | WP     |
      | PTB23                  | 9999-12-01 | 25.58  | PR     |
      | PTB24                  | 9999-12-01 | 25.56  | PR     |
    And metasfresh contains Transport Order
      | Identifier      | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID |
      | shipperTransp_1 | shipper_DHL  | shipper             | shipperLocation     |
    And metasfresh contains M_Package
      | Identifier | M_Shipper_ID |
      | Pckg       | shipper_DHL  |
    And metasfresh contains M_ShippingPackage
      | Identifier | C_Order_ID | M_ShipperTransportation_ID | M_Package_ID | C_BPartner_Location_ID |
      | shPckg     | po2        | shipperTransp_1            | Pckg         | shipperLocation        |
    And update transport order
      | M_ShipperTransportation_ID | ETA        | BLDate     |
      | shipperTransp_1            | 2025-10-19 | 2025-10-25 |
    And the transport order identified by shipperTransp_1 is completed
    Then the order identified by po2 has following pay schedules
      | Identifier | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | OPS1       | PTB21                  | 2025-10-10 | 25.58  | WP     |
      | OPS2       | PTB22                  | 2025-10-15 | 25.58  | WP     |
      | OPS3       | PTB23                  | 2025-10-25 | 25.58  | WP     |
      | OPS4       | PTB24                  | 2025-10-19 | 25.56  | WP     |
    # NOTE — Split-payment iter-2 cleanup. The original scenario had two extra blocks beyond
    # the LC/BL/ETA date stamping above:
    #   (a) an order-side pay-selection block (pay-selection-line referencing C_Order_ID +
    #       C_OrderPaySchedule_ID directly, no invoice). That mechanism was added by an earlier
    #       feature PR and is deleted in iter-2 — the proforma invoice is now the only entry
    #       point for LC payments. Coverage for "purchase order partially paid via a proforma
    #       payment" lives in `splitPaymentLC.feature` S1 (proforma allocation → pay-selection
    #       → payment → IsPartiallyPaid=Y).
    #   (b) a receipt → invoice-candidate → invoice block whose final assertion
    #       (`IsPartiallyPaid=Y, OpenAmt=25.58`) only held because (a)'s payments had auto-
    #       allocated against the invoice. Without (a), the invoice has no allocations and the
    #       partial-paid state changes — covering this requires the partial-receipt + invoice
    #       work scoped for split-payment feature (see https://github.com/metasfresh/me03/issues/29369), where the proforma-payment partial-paid flow will be
    #       re-tested end-to-end.
    # Both blocks are intentionally out of scope here. The scenario now covers exactly what
    # its title promises: pay-schedule LC/BL/ETA dates updating when the order's reference
    # dates change.

  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  Scenario: Order pay schedules are updated when Invoice Date is changed
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO_3    |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB31      | pt_PO_3          | 25      | 1          | OD                | 10    |
      | PTB32      | pt_PO_3          | 75      | 0          | IV                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO_3    | Y         | Y       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po3        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO_3          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po3_l1     | po3        | product      | 10         |
    And the order identified by po3 is completed
    Then the order identified by po3 has following pay schedules
    # In the last line, dueamt is computed as total - previous due amounts, to avoid rounding issues
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB31                  | 2025-10-10 | 25.58  | WP     |
      | PTB32                  | 9999-12-01 | 76.72  | PR     |
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | receiptSchedule_2    | po3        | po3_l1         | vendor        | vendorLocation         | product      | 10         | wh             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID | M_HU_ID          | M_ReceiptSchedule_ID | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID |
      | huLuTuConfig_2             | processedTopHU_2 | receiptSchedule_2    | N               | 10          | 101                     | 1000006       |
    And create material receipt
      | M_HU_ID          | M_ReceiptSchedule_ID | M_InOut_ID |
      | processedTopHU_2 | receiptSchedule_2    | inOut_2    |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | invoice_candidate_2    | po3_l1         |
    And update invoice candidates
      | C_Invoice_Candidate_ID |
      | invoice_candidate_2    |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID |
      | invoice_candidate_2    |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | invoice_candidate_2    |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | invoice_candidate_2    |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice_2    | invoice_candidate_2    |
    Then the order identified by po3 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB31                  | 2025-10-10 | 25.58  | WP     |
      | PTB32                  | 2025-04-01 | 76.72  | WP     |


  # ---------------------------------------------------------------------------------------------
  # S30954_1..S30954_3 own the BL-date -> pay-schedule propagation, S30954_5 the ETA-date one.
  # They are the ONLY home for it.
  #
  # Why they are meaningful: the "update transport order" step writes through the model layer
  # (M_ShipperTransportation_StepDef#updateTransportOrder -> record.setBLDate(...)/setETA(...) -> saveRecord),
  # so saving the record fires the real @ModelChange interceptor
  # de.metas.shipping.model.validator.M_ShipperTransportation#syncOrderDatesOnEdit, which propagates
  # onto the C_Order and in turn fires C_Order#updateOrderPaySchedules. That is exactly the
  # production chain — nothing here is stubbed, and no scenario may be rewritten to poke
  # C_OrderPaySchedule (or any table on the chain) directly: doing so would bypass the very
  # interceptors these scenarios exist to prove, and they would keep passing after the chain broke.
  #
  # What these scenarios deliberately do NOT cover: whether the WebUI even lets a user type the B/L
  # or ETA date once the transport order is completed. The step above goes straight to saveRecord and never
  # touches the WebUI Document layer, where DocumentReadonly#computeFieldReadonly blanks every field
  # of a Processed='Y' document unless its AD_Column.IsAlwaysUpdateable='Y'. That read-only gate is
  # covered — once, in a real browser — by the Playwright spec
  # e2e/frontend-webui/tests/spec/transport-order-dates-editable-when-completed.spec.js.
  # Keep the split: model/business chain here, WebUI editability there, no overlap.
  # ---------------------------------------------------------------------------------------------
  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@F00600
@Id:S30954_1
  Scenario: BL date entered after transport order completion recomputes the shipping line
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO_4    |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB41      | pt_PO_4          | 10      | 1          | OD                | 10    |
      | PTB42      | pt_PO_4          | 90      | 5          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO_4    | Y         | Y       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po4        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO_4          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po4_l1     | po4        | product      | 10         |
    And the order identified by po4 is completed
    Then the order identified by po4 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB41                  | 2025-10-10 | 10.23  | WP     |
      | PTB42                  | 9999-12-01 | 92.07  | PR     |

    And metasfresh contains Transport Order
      | Identifier      | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID |
      | shipperTransp_2 | shipper_DHL  | shipper             | shipperLocation     |
    And metasfresh contains M_Package
      | Identifier | M_Shipper_ID |
      | Pckg2      | shipper_DHL  |
    And metasfresh contains M_ShippingPackage
      | Identifier | C_Order_ID | M_ShipperTransportation_ID | M_Package_ID | C_BPartner_Location_ID |
      | shPckg2    | po4        | shipperTransp_2            | Pckg2        | shipperLocation        |
    And the transport order identified by shipperTransp_2 is completed
    Then the order identified by po4 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB41                  | 2025-10-10 | 10.23  | WP     |
      | PTB42                  | 9999-12-01 | 92.07  | PR     |

    And update transport order
      | M_ShipperTransportation_ID | BLDate     |
      | shipperTransp_2            | 2025-10-20 |
    Then the order identified by po4 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB41                  | 2025-10-10 | 10.23  | WP     |
      | PTB42                  | 2025-10-25 | 92.07  | WP     |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@F00600
@Id:S30954_2
  Scenario: BL date corrected after transport order completion recomputes the shipping line
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO_5    |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB51      | pt_PO_5          | 10      | 1          | OD                | 10    |
      | PTB52      | pt_PO_5          | 90      | 5          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO_5    | Y         | Y       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po5        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO_5          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po5_l1     | po5        | product      | 10         |
    And the order identified by po5 is completed
    Then the order identified by po5 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB51                  | 2025-10-10 | 10.23  | WP     |
      | PTB52                  | 9999-12-01 | 92.07  | PR     |

    And metasfresh contains Transport Order
      | Identifier      | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID |
      | shipperTransp_3 | shipper_DHL  | shipper             | shipperLocation     |
    And metasfresh contains M_Package
      | Identifier | M_Shipper_ID |
      | Pckg3      | shipper_DHL  |
    And metasfresh contains M_ShippingPackage
      | Identifier | C_Order_ID | M_ShipperTransportation_ID | M_Package_ID | C_BPartner_Location_ID |
      | shPckg3    | po5        | shipperTransp_3            | Pckg3        | shipperLocation        |
    And update transport order
      | M_ShipperTransportation_ID | BLDate     |
      | shipperTransp_3            | 2025-10-15 |
    And the transport order identified by shipperTransp_3 is completed
    Then the order identified by po5 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB51                  | 2025-10-10 | 10.23  | WP     |
      | PTB52                  | 2025-10-20 | 92.07  | WP     |

    And update transport order
      | M_ShipperTransportation_ID | BLDate     |
      | shipperTransp_3            | 2025-10-22 |
    Then the order identified by po5 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB51                  | 2025-10-10 | 10.23  | WP     |
      | PTB52                  | 2025-10-27 | 92.07  | WP     |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@F00600
@Id:S30954_3
  Scenario: Draft transport order does not propagate its BL date to the shipping line
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO_6    |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB61      | pt_PO_6          | 10      | 1          | OD                | 10    |
      | PTB62      | pt_PO_6          | 90      | 5          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO_6    | Y         | Y       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po6        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO_6          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po6_l1     | po6        | product      | 10         |
    And the order identified by po6 is completed
    Then the order identified by po6 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB61                  | 2025-10-10 | 10.23  | WP     |
      | PTB62                  | 9999-12-01 | 92.07  | PR     |

    And metasfresh contains Transport Order
      | Identifier      | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID |
      | shipperTransp_4 | shipper_DHL  | shipper             | shipperLocation     |
    And metasfresh contains M_Package
      | Identifier | M_Shipper_ID |
      | Pckg4      | shipper_DHL  |
    And metasfresh contains M_ShippingPackage
      | Identifier | C_Order_ID | M_ShipperTransportation_ID | M_Package_ID | C_BPartner_Location_ID |
      | shPckg4    | po6        | shipperTransp_4            | Pckg4        | shipperLocation        |
    And update transport order
      | M_ShipperTransportation_ID | BLDate     |
      | shipperTransp_4            | 2025-10-18 |
    Then the order identified by po6 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB61                  | 2025-10-10 | 10.23  | WP     |
      | PTB62                  | 9999-12-01 | 92.07  | PR     |

    And the transport order identified by shipperTransp_4 is completed
    Then the order identified by po6 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB61                  | 2025-10-10 | 10.23  | WP     |
      | PTB62                  | 2025-10-23 | 92.07  | WP     |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@F00600
@Id:S30954_5
  Scenario: ETA corrected after transport order completion recomputes the shipping line
    When metasfresh contains C_PaymentTerm
      | Identifier |
      | pt_PO_8    |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB81      | pt_PO_8          | 10      | 1          | OD                | 10    |
      | PTB82      | pt_PO_8          | 90      | 5          | ET                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO_8    | Y         | Y       |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po8        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO_8          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po8_l1     | po8        | product      | 10         |
    And the order identified by po8 is completed
    Then the order identified by po8 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB81                  | 2025-10-10 | 10.23  | WP     |
      | PTB82                  | 9999-12-01 | 92.07  | PR     |

    And metasfresh contains Transport Order
      | Identifier      | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID |
      | shipperTransp_5 | shipper_DHL  | shipper             | shipperLocation     |
    And metasfresh contains M_Package
      | Identifier | M_Shipper_ID |
      | Pckg5      | shipper_DHL  |
    And metasfresh contains M_ShippingPackage
      | Identifier | C_Order_ID | M_ShipperTransportation_ID | M_Package_ID | C_BPartner_Location_ID |
      | shPckg5    | po8        | shipperTransp_5            | Pckg5        | shipperLocation        |
    And update transport order
      | M_ShipperTransportation_ID | ETA        |
      | shipperTransp_5            | 2025-10-15 |
    And the transport order identified by shipperTransp_5 is completed
    Then the order identified by po8 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB81                  | 2025-10-10 | 10.23  | WP     |
      | PTB82                  | 2025-10-20 | 92.07  | WP     |

    And update transport order
      | M_ShipperTransportation_ID | ETA        |
      | shipperTransp_5            | 2025-10-22 |
    Then the order identified by po8 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB81                  | 2025-10-10 | 10.23  | WP     |
      | PTB82                  | 2025-10-27 | 92.07  | WP     |


  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@allure.label.feature:F00994_Multiple_Levels_of_Payment
@F00600
@Id:S30954_4
  Scenario: Advance paid via a proforma marks the order-date step Paid on a no-LC payment term
    # No-Letter-of-Credit purchase term: OD 10% (order-date advance) + BL 90% (bill-of-lading material receipt).
    # The procurement worker pays the 10% advance up front via a purchase proforma invoice.
    # Advance step (OD) state walk: Awaiting_Pay (order completed) -> Paid (proforma allocated + paid).
    # BL step stays Pending throughout - the bill-of-lading date is not yet known (no goods receipt).
    When metasfresh contains C_PaymentTerm
      | Identifier   |
      | pt_PO_7      |
      | pt_immediate |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | PTB71      | pt_PO_7          | 10      | 1          | OD                | 10    |
      | PTB72      | pt_PO_7          | 90      | 5          | BL                | 20    |
    And validate C_PaymentTerm:
      | Identifier | IsComplex | IsValid |
      | pt_PO_7    | Y         | Y       |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_CHF_account | CHF           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID |
      | po7        | N       | vendor        | 2025-10-09  | POO         | wh             | pt_PO_7          |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | po7_l1     | po7        | product      | 10         |
    And the order identified by po7 is completed

    # Order completed: OD step is Awaiting_Pay (order date is known); BL step is Pending (BL date unknown).
    Then the order identified by po7 has following pay schedules
      | C_PaymentTerm_Break_ID | DueAmt | DueAmt_Actual | DueDate    | Status |
      | PTB71                  | 10.23  | null          | 2025-10-10 | WP     |
      | PTB72                  | 92.07  | null          | 9999-12-01 | PR     |

    # Vendor sends a proforma for the 10% advance (1 PCE at 9.98 CHF net -> 10.23 CHF incl. 2.5% tax).
    And metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | advanceInvoice | vendor        | Proforma-Rechnung (Lieferant) | 2025-10-10   | false   | CHF           | pt_immediate     |
    And metasfresh contains C_InvoiceLines
      | Identifier       | C_Invoice_ID   | M_Product_ID | QtyInvoiced | Price |
      | advanceInvoiceL1 | advanceInvoice | product      | 1 PCE       | 9.98  |
    And the invoice identified by advanceInvoice is completed

    # A completed but unpaid proforma does not change the pay schedule.
    And validate created invoices
      | Identifier     | IsPaid |
      | advanceInvoice | N      |
    Then the order identified by po7 has following pay schedules
      | C_PaymentTerm_Break_ID | DueAmt | DueAmt_Actual | DueDate    | Status |
      | PTB71                  | 10.23  | null          | 2025-10-10 | WP     |
      | PTB72                  | 92.07  | null          | 9999-12-01 | PR     |

    # Allocate the proforma to the order: the OD step captures the actual proforma amount (DueAmt_Actual).
    And I allocate proforma 'advanceInvoice' to order 'po7'
    Then the order identified by po7 has following pay schedules
      | C_PaymentTerm_Break_ID | DueAmt | DueAmt_Actual | DueDate    | Status |
      | PTB71                  | 10.23  | 10.23         | 2025-10-10 | WP     |
      | PTB72                  | 92.07  | null          | 9999-12-01 | PR     |

    # Pay the proforma in full (Proforma_Invoice_ID + IsPrepayment=Y). Completion marks the OD step Paid.
    And metasfresh contains C_Payment
      | Identifier     | C_BPartner_ID | PayAmt    | IsReceipt | C_BP_BankAccount_ID | Proforma_Invoice_ID |
      | advancePayment | vendor        | 10.23 CHF | false     | org_CHF_account     | advanceInvoice      |
    And the payment identified by advancePayment is completed
    Then validate payments
      | C_Payment_ID.Identifier | IsPrepayment | C_Invoice_ID | Proforma_Invoice_ID | PayAmt |
      | advancePayment          | Y            | null         | advanceInvoice      | 10.23  |

    # The proforma flips to IsPaid=Y (C_Payment AFTER_COMPLETE interceptor - proforma payments have no allocation lines).
    And validate created invoices
      | Identifier     | IsPaid |
      | advanceInvoice | Y      |

    # Final state: OD advance step Paid; BL step still Pending (open) awaiting the bill-of-lading date.
    Then the order identified by po7 has following pay schedules
      | C_PaymentTerm_Break_ID | DueAmt | DueAmt_Actual | DueDate    | Status |
      | PTB71                  | 10.23  | 10.23         | 2025-10-10 | P      |
      | PTB72                  | 92.07  | null          | 9999-12-01 | PR     |