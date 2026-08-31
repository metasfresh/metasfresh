@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
@ghActions:run_on_executor5
Feature: Reopening a receipt disposition reopens the purchase invoice candidate
## F00701: Invoice Candidates

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-07-26T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_PO      | ps_1               | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_PO     | pl_PO          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_PO                 | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID | IsVendor | IsCustomer |
      | bpartner_1 | ps_1               | Y        | N          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | bpartner_1               | Y                   | Y                   |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier |
      | LU                    |
      | TU                    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | LU_Version         | LU         | LU          | Y         |
      | TU_Version         | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.Included_HU_PI_ID.Identifier |
      | huPiItemLU                 | LU_Version                    | 10  | HU       | TU                               |
      | huPiItemTU                 | TU_Version                    |     | MI       |                                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | product_TU_10CU                    | huPiItemTU                 | product                 | 10  | 2021-01-01 |

  @Id:S0164_300
  @from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
  Scenario: Reopening a closed undelivered receipt disposition reopens the purchase invoice candidate
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered |
      | po1        | false   | bpartner_1    | POO         | 2022-07-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | po1_l1     | po1        | product      | 100        | 10           | product_TU_10CU         |
    And the order identified by po1 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule1                | po1                   | po1_l1                    | bpartner_1               | l_1                               | product                 | 100        | warehouseStd              | 10               |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice |
      | invoiceCand_1                     | po1                       | po1_l1                    | 0                | 0            |

    # Nothing was ever received; the disposition is simply closed, which closes the candidate.
    When the M_ReceiptSchedule identified by receiptSchedule1 is closed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed |
      | invoiceCand_1                     | 0            | true                 | true          |

    # Reopening the disposition must give the candidate back as open work.
    When the M_ReceiptSchedule identified by receiptSchedule1 is reactivated
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed |
      | invoiceCand_1                     | 0            | false                | false         |

  @Id:S0164_400
  @from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
  Scenario: Reactivating a purchase order only parks its receipt disposition and leaves the invoice candidate open
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered |
      | po2        | false   | bpartner_1    | POO         | 2022-07-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | po2_l1     | po2        | product      | 100        | 10           | product_TU_10CU         |
    And the order identified by po2 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule2                | po2                   | po2_l1                    | bpartner_1               | l_1                               | product                 | 100        | warehouseStd              | 10               |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice |
      | invoiceCand_2                     | po2                       | po2_l1                    | 0                | 0            |

    # Reactivating the PO closes the receipt schedule, but only to "park" it - the order line stays
    # open for editing. The candidate must stay open too, or it can never be invoiced again.
    When the order identified by po2 is reactivated

    # IsDeliveryClosed stays true while the order is parked: the parking path deliberately does not
    # reopen it (pre-existing, display-only). The next complete reopens it - see @Id:S0164_500.
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed |
      | invoiceCand_2                     | 0            | true                 | false         |

  @Id:S0164_500
  @from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01010.3_Match_Invoice
@F00701
  Scenario: Re-completing a reactivated purchase order reopens the parked receipt disposition and its invoice candidate
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DocBaseType | DateOrdered |
      | po3        | false   | bpartner_1    | POO         | 2022-07-26  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | po3_l1     | po3        | product      | 100        | 10           | product_TU_10CU         |
    And the order identified by po3 is completed
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyOrderedTU |
      | receiptSchedule3                | po3                   | po3_l1                    | bpartner_1               | l_1                               | product                 | 100        | warehouseStd              | 10               |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | C_OrderLine_ID.Identifier | OPT.QtyDelivered | QtyToInvoice |
      | invoiceCand_3                     | po3                       | po3_l1                    | 0                | 0            |

    # Nothing was ever received and the buyer closes the disposition, which closes the candidate.
    When the M_ReceiptSchedule identified by receiptSchedule3 is closed
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed |
      | invoiceCand_3                     | 0            | true                 | true          |

    # Reactivating the PO leaves the already-closed disposition (and hence the candidate) untouched:
    # the parking path only closes dispositions that are still open.
    When the order identified by po3 is reactivated
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed |
      | invoiceCand_3                     | 0            | true                 | true          |

    # Re-completing the PO reopens the parked disposition - and must give the candidate back as open work.
    When the order identified by po3 is completed
    Then after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.IsClosed | OPT.Processed |
      | receiptSchedule3                | po3                   | po3_l1                    | bpartner_1               | l_1                               | product                 | 100        | warehouseStd              | false        | false         |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed |
      | invoiceCand_3                     | 0            | false                | false         |
