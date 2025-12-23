@from:cucumber
@ghActions:run_on_executor4
Feature: Purchase order with complex payment term

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
      | PTB2                   | 9999-01-01 | 76.72  | PR     |


  @from:cucumber
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
      | PTB2                   | 9999-01-01 | 76.72  | PR     |


  @from:cucumber
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
      | PTB22                  | 9999-01-01 | 25.58  | PR     |
      | PTB23                  | 9999-01-01 | 25.58  | PR     |
      | PTB24                  | 9999-01-01 | 25.56  | PR     |
    And update order
      | Identifier | LC_Date    |
      | po2        | 2025-10-15 |
    Then the order identified by po2 has following pay schedules
      | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | PTB21                  | 2025-10-10 | 25.58  | WP     |
      | PTB22                  | 2025-10-15 | 25.58  | WP     |
      | PTB23                  | 9999-01-01 | 25.58  | PR     |
      | PTB24                  | 9999-01-01 | 25.56  | PR     |
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
    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_CHF_account | CHF           |
    And metasfresh contains Pay Selection
      | Identifier | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySel_1   | org_CHF_account     | CT                  | 2025-10-20 |
    And "Create from..." is invoked for pay selection paySel_1, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | Y       |
    And the Pay selection identified by paySel_1 has exactly the following lines
      | C_Order_ID | C_OrderPaySchedule_ID | PayAmt | C_Payment_ID |
      | po2        | OPS1                  | 25.58  | -            |
      | po2        | OPS2                  | 25.58  | -            |
      | po2        | OPS4                  | 25.56  | -            |
    And the pay selection identified by paySel_1 is completed
    Then "Create Payments" is invoked for pay selection paySel_1
    And the Pay selection identified by paySel_1 has exactly the following lines
      | C_Order_ID | C_OrderPaySchedule_ID | PayAmt | C_Payment_ID |
      | po2        | OPS1                  | 25.58  | payment1     |
      | po2        | OPS2                  | 25.58  | payment2     |
      | po2        | OPS4                  | 25.56  | payment3     |

    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID | C_Order_ID | C_OrderLine_ID | C_BPartner_ID | C_BPartner_Location_ID | M_Product_ID | QtyOrdered | M_Warehouse_ID |
      | receiptSchedule_1    | po2        | po2_l1         | vendor        | vendorLocation         | product      | 10         | wh             |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID | M_HU_ID        | M_ReceiptSchedule_ID | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID |
      | huLuTuConfig               | processedTopHU | receiptSchedule_1    | N               | 10          | 101                     | 1000006       |
    And create material receipt
      | M_HU_ID        | M_ReceiptSchedule_ID | M_InOut_ID |
      | processedTopHU | receiptSchedule_1    | inOut      |
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | invoice_candidate_1    | po2_l1         |
    And update invoice candidates
      | C_Invoice_Candidate_ID |
      | invoice_candidate_1    |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID |
      | invoice_candidate_1    |
    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | invoice_candidate_1    |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | invoice_candidate_1    |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice_1    | invoice_candidate_1    |
    And validate created invoices
      | C_Invoice_ID | GrandTotal | IsPaid | IsPartiallyPaid | OpenAmt |
      | invoice_1    | 102.30 CHF | N      | Y               | 25.58   |

  @from:cucumber
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
      | PTB32                  | 9999-01-01 | 76.72  | PR     |
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