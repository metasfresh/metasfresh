@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: The buyer VAT ID of a sales invoice comes from the delivery address of its lines
  A German customer is billed at its German head office (VAT ID on the partner and the billing address)
  and receives goods at a German and at a Dutch address; the Dutch address carries its own Dutch VAT ID.
  The invoice stores the buyer VAT ID of the address the lines were delivered to (taxed with), which
  for an intra-EU supply is the VAT ID that justifies the 0 % tax. Lines with different buyer VAT IDs
  never share one invoice, not even in a manually set invoicing group.

  All taxes are in an own tax category, the scenarios run at 2026-09-02 (no fiscal representation of
  any other feature is valid then) and the customer uses an own header aggregation with only
  organisation, bill partner, bill location, currency, invoice doc type and SO flag, so one sales
  order's lines would go on one invoice if nothing else split them.

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

    # header aggregation without C_Order_ID: lines of one order end up on one invoice unless something else splits them;
    # line aggregation "per invoice candidate": the lines of two shipments of one order line become one invoice line
    And metasfresh contains C_Aggregations:
      | Identifier | TableName           | EntityType                | AggregationUsageLevel |
      | aggHeader  | C_Invoice_Candidate | de.metas.invoicecandidate | H                     |
    And metasfresh contains C_AggregationItems:
      | Identifier | C_Aggregation_ID.Identifier | EntityType                | Type | ColumnName          |
      | aggOrg     | aggHeader                   | de.metas.invoicecandidate | COL  | AD_Org_ID           |
      | aggBillBP  | aggHeader                   | de.metas.invoicecandidate | COL  | Bill_BPartner_ID    |
      | aggBillLoc | aggHeader                   | de.metas.invoicecandidate | COL  | Bill_Location_ID    |
      | aggCurr    | aggHeader                   | de.metas.invoicecandidate | COL  | C_Currency_ID       |
      | aggDocType | aggHeader                   | de.metas.invoicecandidate | COL  | C_DocTypeInvoice_ID |
      | aggSOTrx   | aggHeader                   | de.metas.invoicecandidate | COL  | IsSOTrx             |
    And load C_Aggregations:
      | Identifier   | C_Aggregation_ID |
      | aggLinePerIC | 540002           |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_billing         | DE          |
      | location_DE              | DE          |
      | location_DE_noVAT        | DE          |
      | location_NL              | NL          |
      | location_thirdParty_NL   | NL          |

    # customer: billed at its German head office, receives goods in Germany and in the Netherlands
    # thirdParty: a Dutch partner the customer has goods drop-shipped to
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     | SO_Invoice_Aggregation_ID.Identifier | SO_InvoiceLine_Aggregation_ID.Identifier |
      | customer   | ps_1                          | Y          | D           | DE123456788 | aggHeader                            | aggLinePerIC                             |
      | thirdParty | ps_1                          | Y          | D           |             |                                      |                                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customer_billing  | 0123456789201 | customer                 | N        | N               | Y        | Y               | location_billing             |
      | customer_DE       | 0123456789202 | customer                 | Y        | Y               | N        | N               | location_DE                  |
      | customer_DE_noVAT | 0123456789203 | customer                 | Y        | N               | N        | N               | location_DE_noVAT            |
      | customer_NL       | 0123456789204 | customer                 | Y        | N               | N        | N               | location_NL                  |
      | thirdParty_NL     | 0123456789205 | thirdParty               | Y        | Y               | Y        | Y               | location_thirdParty_NL       |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID     |
      | customer_billing                  | DE123456788 |
      | customer_DE                       | DE123456788 |

    # both warehouses are in Germany (default: the organisation's address)
    And metasfresh contains M_Warehouse:
      | Identifier  | IsDropShipWarehouse |
      | wh_DE       | N                   |
      | wh_dropship | Y                   |

  @Id:S32533_TC1
  Scenario: goods shipped to the Dutch address: the invoice stores the Dutch VAT ID that justifies the 0 % line
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
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
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus |
      | shipment              | customer_NL                       | true      | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | C_BPartner_Location_ID | BuyerVATaxID   | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | customer_billing       | NL123456782B01 |               |                            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDENL0 |

  @Id:S32533_TC2
  Scenario: goods shipped to the German address: the invoice stores the German VAT ID
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_DE                           | customer_billing                | wh_DE                         | F            |
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
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID |
      | invoice    | DE123456788  |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDEDE19 |

  @Id:S32533_TC3
  Scenario: delivery address without VAT ID: no buyer VAT ID is stored, so the invoice shows the partner's VAT ID as before
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_DE_noVAT                     | customer_billing                | wh_DE                         | F            |
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
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    # the delivery address has no VAT ID of its own and there is no fiscal representation: the invoice is grouped and stores
    # no values as without per-address VAT IDs; its outputs fall back to the partner's VAT ID
    And validate created invoices
      | Identifier | BuyerVATaxID |
      | invoice    |              |

    # what the buyer sees is unchanged: the printed invoice shows the partner's VAT ID (the fallback for no stored value)
    When temporarily set sys config boolean value false for sys config 'de.metas.report.jasper.IsMockReportService'
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    Then the PDF archived for the record identified by "invoice" contains text "DE123456788"

  @Id:S32533_TC3b
  Scenario: partner VAT ID ignored by sys config and delivery address without VAT ID: no buyer VAT ID is stored
    Given temporarily set sys config boolean value true for sys config 'de.metas.tax.api.impl.TaxDAO.IgnorePartnerVATID'
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_DE_noVAT                     | customer_billing                | wh_DE                         | F            |
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
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID |
      | invoice    |              |

  @Id:S32533_TC4
  Scenario: service invoiced immediately, without shipment, to the Dutch address: the invoice stores the Dutch VAT ID
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | service                 | 10          | taxDENL0 |

  @Id:S32533_TC6_Valid
  Scenario: Dutch VAT ID checked Valid: 0 % line and the Dutch VAT ID on the invoice
    Given no VATaxID_CheckLog records exist for VATaxID 'NL123456782B01'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID        | VATaxIDStatus |
      | NL123456782B01 | Valid         |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And the VAT-ID online checker was called for VATaxID 'NL123456782B01'
    And validate C_BPartner_Location VAT-ID status:
      | C_BPartner_Location_ID | VATaxIDStatus | HasTaxCertificate |
      | customer_NL            | Valid         | true              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | service                 | 10          | taxDENL0 |

  @Id:S32533_TC6_NotChecked
  Scenario: Dutch VAT ID never checked: 0 % line and the Dutch VAT ID on the invoice
    Given metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And validate C_BPartner_Location VAT-ID status:
      | C_BPartner_Location_ID | VATaxIDStatus | HasTaxCertificate |
      | customer_NL            | NotChecked    | true              |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | service                 | 10          | taxDENL0 |

  @Id:S32533_TC6_Invalid
  Scenario: Dutch VAT ID checked Invalid: no 0 % line
    Given no VATaxID_CheckLog records exist for VATaxID 'NL123456782B01'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID        | VATaxIDStatus |
      | NL123456782B01 | Invalid       |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And the VAT-ID online checker was called for VATaxID 'NL123456782B01'
    And validate C_BPartner_Location VAT-ID status:
      | C_BPartner_Location_ID | VATaxIDStatus | HasTaxCertificate |
      | customer_NL            | Invalid       | false             |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | service                 | 10          | taxDENL19 |

  # characterization of today's behaviour: GREEN without this feature's code
  @Id:S32533_TC7
  Scenario: billed in Germany, delivered to the Netherlands: order line and invoice line get the intra-EU rate of the delivery address
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    When the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | product                 | 10         | taxDENL0                |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shipmentSchedule                 | D                 | Y                  |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDENL0 |

  @Id:S32533_TC10a
  Scenario: one order with a line to the German and a line to the Dutch address: two invoices, one per buyer VAT ID
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_DE                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Location_ID.Identifier |
      | orderLineDE | order                 | service                 | 10         | customer_DE                       |
      | orderLineNL | order                 | service                 | 5          | customer_NL                       |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | icDE                              | orderLineDE               | 10           |
      | icNL                              | orderLineNL               | 5            |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | icDE, icNL                        |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | icDE                              | invoiceDE               |
      | icNL                              | invoiceNL               |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoiceDE  | DE123456788    |
      | invoiceNL  | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceLineDE               | invoiceDE               | service                 | 10          | taxDEDE19 |
      | invoiceLineNL               | invoiceNL               | service                 | 5           | taxDENL0  |

  @Id:S32533_TC10b
  Scenario: the German line is put into the Dutch line's invoicing group by hand: still two invoices
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_DE                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Location_ID.Identifier |
      | orderLineDE | order                 | service                 | 10         | customer_DE                       |
      | orderLineNL | order                 | service                 | 5          | customer_NL                       |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | icDE                              | orderLineDE               | 10           |
      | icNL                              | orderLineNL               | 5            |
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | HeaderAggregation_Override.FromCandidate |
      | icDE                              | icNL                                     |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | icDE, icNL                        |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | icDE                              | invoiceDE               |
      | icNL                              | invoiceNL               |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoiceDE  | DE123456788    |
      | invoiceNL  | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceLineDE               | invoiceDE               | service                 | 10          | taxDEDE19 |
      | invoiceLineNL               | invoiceNL               | service                 | 5           | taxDENL0  |

  @Id:S32533_TC13
  Scenario: order drop-shipped to a Dutch third party: the invoice stores the third party's VAT ID
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | thirdParty_NL                     | NL112233442B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule | IsDropShip | DropShip_BPartner_ID | DropShip_Location_ID |
      | order      | true    | customer                 | 2026-09-02  | customer_DE                           | customer_billing                | wh_DE                         | F            | true       | thirdParty           | thirdParty_NL        |
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
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus |
      | shipment              | thirdParty_NL                     | true      | CO        |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | C_BPartner_Location_ID | BuyerVATaxID   |
      | invoice    | customer_billing       | NL112233442B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDENL0 |

  @Id:S32533_TC14
  Scenario: one order line shipped to the Dutch address in two shipments, invoiced as one line: the Dutch VAT ID
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | DeliveryRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | F            |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | shipmentSchedule                 | 4                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule                 |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment1             | shipmentSchedule                 | D                 | Y                  |
    And recompute shipment schedules
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule                 |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule                 |

    And update shipment schedules
      | M_ShipmentSchedule_ID.Identifier | OPT.QtyToDeliver_Override |
      | shipmentSchedule                 | 6                         |
    And after not more than 60s, shipment schedule is recomputed
      | M_ShipmentSchedule_ID.Identifier |
      | shipmentSchedule                 |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment1, shipment2  | shipmentSchedule                 | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus |
      | shipment1             | customer_NL                       | true      | CO        |
      | shipment2             | customer_NL                       | true      | CO        |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate invoice lines for invoice:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | product                 | 10          | taxDENL0 |

  @Id:S32533_TC20
  Scenario: order header to the German address, order line to the Dutch address, no shipment: the Dutch VAT ID
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_DE                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Location_ID.Identifier |
      | orderLine  | order                 | service                 | 10         | customer_NL                       |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | service                 | 10          | taxDENL0 |

  @Id:S32533_TC22a
  Scenario: service from the drop-ship warehouse to the Dutch address: the Dutch VAT ID, line tax DE->NL unchanged
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_dropship                   | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate                  | orderLine                 | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate                  | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | service                 | 10          | taxDENL0 |

  @Id:S32533_TC22c
  Scenario: one line from the German warehouse and one from the drop-ship warehouse to the Dutch address: one invoice
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL123456782B01 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customer                 | 2026-09-02  | customer_NL                           | customer_billing                | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_Warehouse_ID.Identifier |
      | orderLineDE       | order                 | service                 | 10         | wh_DE                         |
      | orderLineDropShip | order                 | service                 | 5          | wh_dropship                   |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | icDE                              | orderLineDE               | 10           |
      | icDropShip                        | orderLineDropShip         | 5            |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | icDE, icDropShip                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | icDE                              | invoice                 |
    And validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL123456782B01 |
    And validate invoice lines for invoice:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLineDE               | service                 | 10          | taxDENL0 |
      | invoiceLineDropShip         | service                 | 5           | taxDENL0 |
