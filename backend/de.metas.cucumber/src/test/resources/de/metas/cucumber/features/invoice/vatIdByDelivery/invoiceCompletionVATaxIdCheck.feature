@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: The VAT IDs of a sales invoice are checked when it is completed
  A manually created sales invoice gets its buyer and seller VAT IDs at completion, from the addresses its
  lines were taxed with; lines with different VAT IDs on one manual invoice are rejected. A manual invoice none of
  whose lines resolves a VAT ID of an address or a fiscal representation completes as before and stores none.
  A prepared candidate invoice (created by invoicing with "complete invoices" = N) keeps the VAT IDs it got
  from its candidates; a line added to it by hand is taxed from the billing address (or, with the order line's tax,
  from the order line's delivery address) and is accepted, unless its tax - or the tax a doc type that overwrites
  the invoice date gives it at completion - is exempt on a VAT ID other than the one the invoice shows.

  Customer (German head office) is billed in Germany and receives goods in Germany and in the Netherlands;
  the Dutch address carries its own VAT ID. Customer AT is billed in Austria (Austrian VAT ID) and receives
  goods at a Dutch address with a Dutch VAT ID.

  All taxes are in an own tax category, no fiscal representation is involved and the scenarios run at 2026-10-02.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-10-02T10:00:00+02:00[Europe/Berlin]
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

    # own tax category, so no other feature's taxes interfere; the exempt rates require a VAT ID (RequiresTaxCertificate),
    # the domestic rate has an empty RequiresTaxCertificate, so it applies with and without a VAT ID.
    # TypeOfDestCountry is given so that the C_Tax interceptor keeps To_Country_ID on a new record
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | RequiresTaxCertificate |
      | taxDENL0   | taxCategory      | 0    | DE                       | NL                        | WITHIN_COUNTRY_AREA | true                   |
      | taxDENL19  | taxCategory      | 19   | DE                       | NL                        | WITHIN_COUNTRY_AREA | false                  |
      | taxDEAT0   | taxCategory      | 0    | DE                       | AT                        | WITHIN_COUNTRY_AREA | true                   |
      | taxDEAT19  | taxCategory      | 19   | DE                       | AT                        | WITHIN_COUNTRY_AREA | false                  |
      | taxDEDE19  | taxCategory      | 19   | DE                       | DE                        | DOMESTIC            |                        |

    And metasfresh contains M_Products:
      | Identifier | ProductType |
      | product    | I           |
      | service    | S           |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    # an order uses the price list of its delivery country, an invoice of a candidate the one of its billing country
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_DE      | ps_1                          | DE           | EUR           | true  | false         |
      | pl_NL      | ps_1                          | NL           | EUR           | true  | false         |
      | pl_AT      | ps_1                          | AT           | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2026-01-01 |
      | plv_NL     | pl_NL                     | 2026-01-01 |
      | plv_AT     | pl_AT                     | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_DE      | plv_DE                            | product                 | 100.0    | PCE               | taxCategory      |
      | pp_NL      | plv_NL                            | product                 | 100.0    | PCE               | taxCategory      |
      | pps_DE     | plv_DE                            | service                 | 50.0     | PCE               | taxCategory      |
      | pps_NL     | plv_NL                            | service                 | 50.0     | PCE               | taxCategory      |
      | pp_AT      | plv_AT                            | product                 | 100.0    | PCE               | taxCategory      |
      | pps_AT     | plv_AT                            | service                 | 50.0     | PCE               | taxCategory      |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_billing         | DE          |
      | location_DE              | DE          |
      | location_NL              | NL          |
      | location_AT_billing      | AT          |
      | location_AT_NL           | NL          |

    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     |
      | customer   | ps_1                          | Y          | D           | DE123456788 |
      | customerAT | ps_1                          | Y          | D           | ATU13585627 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customer_billing   | 0123456789301 | customer                 | N        | N               | Y        | Y               | location_billing             |
      | customer_DE        | 0123456789302 | customer                 | Y        | Y               | N        | N               | location_DE                  |
      | customer_NL        | 0123456789303 | customer                 | Y        | N               | N        | N               | location_NL                  |
      | customerAT_billing | 0123456789304 | customerAT               | N        | N               | Y        | Y               | location_AT_billing          |
      | customerAT_NL      | 0123456789305 | customerAT               | Y        | Y               | N        | N               | location_AT_NL               |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_billing                  | DE123456788    |
      | customer_DE                       | DE123456788    |
      | customer_NL                       | NL123456782B01 |
      | customerAT_billing                | ATU13585627    |
      | customerAT_NL                     | NL123456782B01 |

    And metasfresh contains M_Warehouse:
      | Identifier | IsDropShipWarehouse |
      | wh_DE      | N                   |

  @Id:S32533_TC10d
  Scenario: a manual invoice with lines for a German and a Dutch shipment is rejected; with Dutch lines only it stores the Dutch VAT ID
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order_DE   | true    | customer                 | 2026-10-02  | customer_DE                           | customer_billing                | wh_DE                         | F            |
      | order_NL   | true    | customer                 | 2026-10-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_DE | order_DE              | product                 | 10         |
      | orderLine_NL | order_NL              | product                 | 5          |
    And the order identified by order_DE is completed
    And the order identified by order_NL is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier          | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule_DE | orderLine_DE              | N             |
      | shipmentSchedule_NL | orderLine_NL              | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment_DE           | shipmentSchedule_DE              | D                 | Y                  |
      | shipment_NL           | shipmentSchedule_NL              | D                 | Y                  |
    And validate the created shipment lines
      | M_InOutLine_ID  | M_InOut_ID  | M_Product_ID | MovementQty |
      | shipmentLine_DE | shipment_DE | product      | 10          |
      | shipmentLine_NL | shipment_NL | product      | 5           |

    # each line is taxed from its shipment's address
    When metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | invoice_mixed | customer      | customer_billing       | pl_DE          | Ausgangsrechnung        | 2026-10-02   | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID  | M_Product_ID | QtyInvoiced | M_InOutLine_ID  | C_Tax_ID  |
      | mixedLine_DE  | invoice_mixed | product      | 10 PCE      | shipmentLine_DE | taxDEDE19 |
      | mixedLine_NL  | invoice_mixed | product      | 5 PCE       | shipmentLine_NL | taxDENL0  |
    Then the invoice identified by "invoice_mixed" cannot be completed with AD_Message "C_Invoice_MixedVATaxIds"

    When metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | invoice_NL | customer      | customer_billing       | pl_DE          | Ausgangsrechnung        | 2026-10-02   | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID  | C_Tax_ID |
      | nlLine     | invoice_NL   | product      | 5 PCE       | shipmentLine_NL | taxDENL0 |
    And the invoice identified by invoice_NL is completed
    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice_NL | CO        | NL123456782B01 |               |                            |

  @Id:S32533_TC10d2
  Scenario: a manual invoice for shipments to two partners with VAT IDs on the partners only (no address VAT ID, no fiscal representation) completes as before, without stored VAT IDs
    Given metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_partner1        | DE          |
      | location_partner2        | DE          |
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     |
      | partner1   | ps_1                          | Y          | D           | DE136695976 |
      | partner2   | ps_1                          | Y          | D           | DE811111113 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | partner1_location | 0123456789306 | partner1                 | Y        | Y               | Y        | Y               | location_partner1            |
      | partner2_location | 0123456789307 | partner2                 | Y        | Y               | Y        | Y               | location_partner2            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order_1    | true    | partner1                 | 2026-10-02  | partner1_location                     | wh_DE                         | F            |
      | order_2    | true    | partner2                 | 2026-10-02  | partner2_location                     | wh_DE                         | F            |
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
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty |
      | shipmentLine_1 | shipment_1 | product      | 10          |
      | shipmentLine_2 | shipment_2 | product      | 5           |

    When metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | partner1      | partner1_location      | pl_DE          | Ausgangsrechnung        | 2026-10-02   | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID | C_Tax_ID  |
      | invoiceL1  | invoice      | product      | 10 PCE      | shipmentLine_1 | taxDEDE19 |
      | invoiceL2  | invoice      | product      | 5 PCE       | shipmentLine_2 | taxDEDE19 |
    And the invoice identified by invoice is completed
    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        |              |               |                            |

  @Id:S32533_TC10i
  Scenario: a line added by hand to a prepared Dutch candidate invoice gets the German domestic tax; the invoice completes with the Dutch VAT ID
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-10-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
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
    When metasfresh contains C_InvoiceLines
      | Identifier      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | handAddedLine   | invoice      | service      | 3 PCE       |
    And the invoice identified by invoice is completed
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | candidateLine               | invoice                 | product                 | 10          | taxDENL0  |
      | handAddedLine               | invoice                 | service                 | 3           | taxDEDE19 |
    And validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        | NL123456782B01 |               |                            |

  @Id:S32533_TC10ii
  Scenario: a line added by hand to a prepared Dutch candidate invoice of a buyer billed in Austria is exempt on the Austrian VAT ID: completion is rejected
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customerAT               | 2026-10-02  | customerAT_NL                         | customerAT_billing              | wh_DE                         | F            |
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
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |

    # no tax given: the line is taxed from the Austrian billing address, i.e. exempt on the Austrian VAT ID
    When metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | handAddedLine | invoice      | service      | 3 PCE       | taxDEAT0 |
    Then the invoice identified by "invoice" cannot be completed with AD_Message "C_Invoice_ExemptLineVATaxIdMismatch"

  @Id:S32533_TC10iii
  Scenario: a line added by hand to a prepared Dutch candidate invoice with a forced exempt Dutch tax: completion is rejected
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-10-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
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

    # the exempt Dutch tax is forced on a line without shipment, which belongs to the German billing address
    When metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID$set |
      | handAddedLine | invoice      | service      | 3 PCE       | taxDENL0     |
    Then the invoice identified by "invoice" cannot be completed with AD_Message "C_Invoice_ExemptLineVATaxIdMismatch"

  @Id:S32533_TC10v
  Scenario: a prepared candidate invoice without stored VAT IDs (prepared before they were stored) completes with an exempt line added by hand, as before
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customerAT               | 2026-10-02  | customerAT_NL                         | customerAT_billing              | wh_DE                         | F            |
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
    # as if the invoice had been prepared before the VAT IDs were stored
    And update C_Invoice:
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    |              |               |                            |

    When metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID |
      | handAddedLine | invoice      | service      | 3 PCE       | taxDEAT0 |
    And the invoice identified by invoice is completed
    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        |              |               |                            |

  @Id:S32533_TC10vi
  Scenario: a doc type that overwrites the invoice date on completion would re-tax a line added by hand to an exempt rate on the Austrian VAT ID: completion is rejected
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customerAT               | 2026-10-02  | customerAT_NL                         | customerAT_billing              | wh_DE                         | F            |
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
      | C_Invoice_Candidate_ID.Identifier | OPT.IsCompleteInvoices | DateInvoiced |
      | invoiceCandidate                  | false                  | 2026-11-23   |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | invoiceCandidate                  | invoice                 | IP            |
    And validate created invoices
      | Identifier | DateInvoiced | BuyerVATaxID   |
      | invoice    | 2026-11-23   | NL123456782B01 |

    # the Austrian standard rate is forced: not exempt, so the line itself passes the check
    When metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_Tax_ID$set |
      | handAddedLine | invoice      | service      | 3 PCE       | taxDEAT19    |
    # completion sets the invoice date to today, which re-taxes the line from the Austrian billing address to the exempt Austrian rate
    And the doc type of invoice "invoice" has IsOverwriteDateOnComplete true for this scenario
    Then the invoice identified by "invoice" cannot be completed with AD_Message "C_Invoice_ExemptLineVATaxIdMismatch"

  @Id:S32533_TC10vii
  Scenario: a line added by hand that references the Dutch order line but is taxed from the Austrian billing address is exempt on the Austrian VAT ID: completion is rejected
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customerAT               | 2026-10-02  | customerAT_NL                         | customerAT_billing              | wh_DE                         | F            |
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
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |

    # no tax given: the line is taxed from the Austrian billing address (the order line is not used for the tax), i.e. exempt on the Austrian VAT ID
    When metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_OrderLine_ID | C_Tax_ID |
      | handAddedLine | invoice      | product      | 2 PCE       | orderLine      | taxDEAT0 |
    Then the invoice identified by "invoice" cannot be completed with AD_Message "C_Invoice_ExemptLineVATaxIdMismatch"

  @Id:S32533_TC10vii2
  Scenario: a line added by hand that references the Dutch order line and has its exempt Dutch tax was taxed from the Dutch delivery address: the invoice completes with the Dutch VAT ID
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customerAT               | 2026-10-02  | customerAT_NL                         | customerAT_billing              | wh_DE                         | F            |
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

    # the order line's tax, as creating the line from the order copies it
    When metasfresh contains C_InvoiceLines
      | Identifier    | C_Invoice_ID | M_Product_ID | QtyInvoiced | C_OrderLine_ID | C_Tax_ID$set |
      | handAddedLine | invoice      | product      | 2 PCE       | orderLine      | taxDENL0     |
    And the invoice identified by invoice is completed
    Then validate created invoices
      | Identifier | DocStatus | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        | NL123456782B01 |               |                            |
