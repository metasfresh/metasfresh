@from:cucumber
@ghActions:run_on_executor7
Feature: Purchase order with complex payment term

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier          | Value               | Name                |
      | product_PO_17062022 | product_PO_17062022 | product_PO_17062022 |


    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value |
      | ps_PO      | ps_PO | ps_PO |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_PO     | pl_PO                     | plv_PO | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | product_PO_17062022     | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | Value                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | supplier_PO | supplier_PO_17062022 | supplier_PO_17062022 | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation_PO | 1706202211111 | supplier_PO              | Y                   | Y                   |
    And metasfresh contains C_PaymentTerm
      | Identifier | Name  | Value | OPT.IsComplex |
      | pt_PO      | pt_PO | pt_PO | Y             |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier | Description | C_PaymentTerm.Identifier | SeqNo | ReferenceDateType | Percent | OffsetDays |
      | ptb_PO_1   | pt_PO_1     | pt_PO                    | 10    | OD                | 50.0    | 2          |
      | ptb_PO_2   | pt_PO_2     | pt_PO                    | 20    | BL                | 50.0    | 0          |


  @from:cucumber
  Scenario: Create a new purchase order with complex payment term. Validate that order pay schedules were created accordingly
    Given metasfresh contains C_Orders:
      | Identifier       | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.C_PaymentTerm_ID.Identifier |
      | order_PO_PTC_100 | N       | supplier_PO              | 2025-10-10  | po_ref_PTC_100  | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   | pt_PO                           |
    And metasfresh contains C_OrderLines:
      | Identifier           | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_PTC_100 | order_PO_PTC_100      | product_PO_17062022     | 26         |

    When the order identified by order_PO_PTC_100 is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO_PTC_100      | order_PO_PTC_100      | product_PO_17062022     | 26         | 0            | 0           | 10    | 0        | EUR          | true      | 26              |
    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier | OPT.QtyMoved | OPT.Processed |
      | receiptSchedule_PO_PTC_100      | order_PO_PTC_100      | orderLine_PO_PTC_100      | supplier_PO              | supplierLocation_PO               | product_PO_17062022     | 26         | warehouseStd              | 0            | false         |
    And after not more than 30s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCand_PO_PO_PTC_100         | orderLine_PO_PTC_100      | 0            |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.QtyDelivered | OPT.IsDeliveryClosed |
      | invoiceCand_PO_PO_PTC_100         | order_PO_PTC_100          | orderLine_PO_PTC_100          | 0            | 26             | 0                | false                |


    And after not more than 30s, C_OrderPaySchedule are found:
      | C_OrderPaySchedule.Identifier | C_Order_ID.Identifier | C_PaymentTerm_Break_ID.Identifier | DueAmt | DueDate    | Percent | Status | ReferenceDateType |
      | orderPaySched_PO_PT_100_1     | order_PO_PTC_100      | ptb_PO_1                          | 130    | 2025-10-12 | 50      | WP     | OD                |
      | orderPaySched_PO_PT_100_2     | order_PO_PTC_100      | ptb_PO_2                          | 130    | 9999-01-01 | 50      | WP     | OD                |
