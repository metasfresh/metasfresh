@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: Reversals and credit memos keep the VAT IDs of their invoice, copies do not
  The reversal of a sales invoice and a credit memo that references it show the buyer VAT ID, seller VAT ID and
  fiscal representation stored on that invoice, as they are (also when they are empty), even if their lines
  would resolve other VAT IDs from today's master data and even without shipment lines.
  A plain copy of an invoice does not carry the stored values; it gets its own when it is completed.

  A German customer is billed at its German head office (VAT ID on the partner and the billing address) and
  receives goods and services at a Dutch address with its own Dutch VAT ID, and goods at a German address without
  an own VAT ID.

  All taxes are in an own tax category and the scenarios run at 2026-09-02 (no fiscal representation of any
  other feature is valid then).

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-02T10:00:00+02:00[Europe/Berlin]
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

    # own tax category, so no other feature's taxes interfere; the domestic rate has an empty RequiresTaxCertificate,
    # so it applies with and without a buyer VAT ID.
    # TypeOfDestCountry is given so that the C_Tax interceptor keeps To_Country_ID on a new record
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | RequiresTaxCertificate |
      | taxDENL0   | taxCategory      | 0    | DE                       | NL                        | WITHIN_COUNTRY_AREA | true                   |
      | taxDENL19  | taxCategory      | 19   | DE                       | NL                        | WITHIN_COUNTRY_AREA | false                  |
      | taxDEDE19  | taxCategory      | 19   | DE                       | DE                        | DOMESTIC            |                        |

    And metasfresh contains M_Products:
      | Identifier | ProductType |
      | product    | I           |
      | service    | S           |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_DE      | ps_1                          | DE           | EUR           | true  | false         |
      | pl_NL      | ps_1                          | NL           | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2026-01-01 |
      | plv_NL     | pl_NL                     | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_DE      | plv_DE                            | product                 | 100.0    | PCE               | taxCategory      |
      | pp_NL      | plv_NL                            | product                 | 100.0    | PCE               | taxCategory      |
      | pps_DE     | plv_DE                            | service                 | 50.0     | PCE               | taxCategory      |
      | pps_NL     | plv_NL                            | service                 | 50.0     | PCE               | taxCategory      |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_billing         | DE          |
      | location_DE_noVAT        | DE          |
      | location_NL              | NL          |

    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     |
      | customer   | ps_1                          | Y          | D           | DE123456788 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customer_billing  | 0123456789401 | customer                 | N        | N               | Y        | Y               | location_billing             |
      | customer_DE_noVAT | 0123456789402 | customer                 | Y        | Y               | N        | N               | location_DE_noVAT            |
      | customer_NL       | 0123456789403 | customer                 | Y        | N               | N        | N               | location_NL                  |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_billing                  | DE123456788    |
      | customer_NL                       | NL333333330B01 |

    And metasfresh contains M_Warehouse:
      | Identifier | IsDropShipWarehouse |
      | wh_DE      | N                   |

  @Id:S32533_TC12a
  Scenario: the reversal of an invoice for goods shipped to the Dutch address keeps the Dutch VAT ID, although the address got another VAT ID meanwhile
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shipmentSchedule                 | D                 | Y                  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        | NL333333330B01 |               |                            |

    # the reversal's lines would now resolve the new VAT ID of the Dutch address
    When update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And the invoice identified by invoice is reversed
    And the reversal of invoice invoice is identified by reversal

    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | RE        | NL333333330B01 |               |                            |
      | reversal   | RE        | NL333333330B01 |               |                            |

  @Id:S32533_TC12b
  Scenario: a credit memo of an invoice for goods shipped to the Dutch address keeps the Dutch VAT ID, although the address got another VAT ID meanwhile
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shipmentSchedule                 | D                 | Y                  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   |
      | invoice    | CO        | NL333333330B01 |

    When update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And create credit memo for C_Invoice
      | CreditMemo | C_Invoice_ID |
      | creditMemo | invoice      |
    And the invoice identified by creditMemo is completed

    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | creditMemo | CO        | NL333333330B01 |               |                            |

  @Id:S32533_TC12c
  Scenario: reversal and credit memo of an invoice for a service to the Dutch address, invoiced immediately without shipment: the Dutch VAT ID
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order_1    | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | I           |
      | order_2    | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | service                 | 10         |
      | orderLine_2 | order_2               | service                 | 5          |
    And the order identified by order_1 is completed
    And the order identified by order_2 is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate_1                | orderLine_1               | 10           |
      | invoiceCandidate_2                | orderLine_2               | 5            |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_1                |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_1                | invoice_1               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_2                |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_2                | invoice_2               |
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   |
      | invoice_1  | CO        | NL333333330B01 |
      | invoice_2  | CO        | NL333333330B01 |

    When the invoice identified by invoice_1 is reversed
    And the reversal of invoice invoice_1 is identified by reversal
    And create credit memo for C_Invoice
      | CreditMemo | C_Invoice_ID |
      | creditMemo | invoice_2    |
    And the invoice identified by creditMemo is completed

    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | reversal   | RE        | NL333333330B01 |               |                            |
      | creditMemo | CO        | NL333333330B01 |               |                            |

  @Id:S32533_TC12d
  Scenario: reversal and credit memo of an invoice without stored VAT IDs (partner VAT ID ignored, delivery address without VAT ID) store none either
    Given temporarily set sys config boolean value true for sys config 'de.metas.tax.api.impl.TaxDAO.IgnorePartnerVATID'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order_1    | true    | customer                 | 2026-09-02  | customer_DE_noVAT                     | customer_billing                | wh_DE                         | F            |
      | order_2    | true    | customer                 | 2026-09-02  | customer_DE_noVAT                     | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | product                 | 10         |
      | orderLine_2 | order_2               | product                 | 5          |
    And the order identified by order_1 is completed
    And the order identified by order_2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier         | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_1 | orderLine_1               | N             |
      | shipmentSchedule_2 | orderLine_2               | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_1            | shipmentSchedule_1               | D                 | Y                  |
      | shipment_2            | shipmentSchedule_2               | D                 | Y                  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate_1                | orderLine_1               | 10           |
      | invoiceCandidate_2                | orderLine_2               | 5            |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_1                |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_1                | invoice_1               |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_2                |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_2                | invoice_2               |
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice_1  | CO        |              |               |                            |
      | invoice_2  | CO        |              |               |                            |

    When the invoice identified by invoice_1 is reversed
    And the reversal of invoice invoice_1 is identified by reversal
    And create credit memo for C_Invoice
      | CreditMemo | C_Invoice_ID |
      | creditMemo | invoice_2    |
    And the invoice identified by creditMemo is completed

    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | reversal   | RE        |              |               |                            |
      | creditMemo | CO        |              |               |                            |

  @Id:S32533_TC12e
  Scenario: the reversal of an invoice whose lines would resolve to different VAT IDs (Dutch candidate line, German line added by hand) completes with the invoice's stored values
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shipmentSchedule                 | D                 | Y                  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices |
      | invoiceCandidate                  | false                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    # no tax given: the line is taxed from the German billing address
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | handAddedLine | invoice      | service      | 3 PCE       |
    And the invoice identified by invoice is completed
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | candidateLine               | invoice                 | product                 | 10          | taxDENL0  |
      | handAddedLine               | invoice                 | service                 | 3           | taxDEDE19 |
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        | NL333333330B01 |               |                            |

    # the reversal's lines lose the shipment line: the candidate line resolves the Dutch VAT ID from its order line,
    # the line added by hand the German one from the billing address
    When the invoice identified by invoice is reversed
    And the reversal of invoice invoice is identified by reversal

    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | reversal   | RE        | NL333333330B01 |               |                            |

  @Id:S32533_TC15
  Scenario: the copy of an invoice for goods shipped to the Dutch address has no VAT IDs until it is completed; then it gets the ones of its own lines, here none
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shipmentSchedule                 | D                 | Y                  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   |
      | invoice    | CO        | NL333333330B01 |

    When the invoice identified by "invoice" is copied as "invoiceCopy"
    Then validate created invoices
      | Identifier  | DocStatus | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoiceCopy | DR        |              |               |                            |

    # the copied line has neither shipment line nor order line, so it was taxed from the German billing address,
    # whose VAT ID is the partner's own: nothing is stored and the copy shows the partner's VAT ID
    When the invoice identified by invoiceCopy is completed
    Then validate created invoices
      | Identifier  | DocStatus | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoiceCopy | CO        |              |               |                            |
    When temporarily set sys config boolean value false for sys config 'de.metas.report.jasper.IsMockReportService'
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And The jasper process is run
      | Value             | Record_ID   |
      | Rechnung (Jasper) | invoiceCopy |
    Then the PDF archived for the record identified by "invoiceCopy" contains text "DE123456788"
