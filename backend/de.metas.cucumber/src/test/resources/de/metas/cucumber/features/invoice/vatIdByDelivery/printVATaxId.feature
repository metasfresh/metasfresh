@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: The printed sales invoice shows the stored buyer VAT ID
  The invoice description of the printed sales invoice shows the buyer VAT ID stored on the invoice
  (the VAT ID of the address the goods were delivered to), not the VAT ID of the billing partner.
  A later change of the address's VAT ID does not change a reprint.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-02T10:00:00+02:00[Europe/Berlin]
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled
    # render the real jasper report; the mock report service would archive a placeholder PDF instead
    And temporarily set sys config boolean value false for sys config 'de.metas.report.jasper.IsMockReportService'
    # the PDF steps read the archive back out of the database
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |

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
      | customer   | ps_1                          | Y          | D           | DE369391233 | aggHeader                            | aggLinePerIC                             |
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
      | customer_billing                  | DE369391233 |
      | customer_DE                       | DE369391233 |

    # both warehouses are in Germany (default: the organisation's address)
    And metasfresh contains M_Warehouse:
      | Identifier  | IsDropShipWarehouse |
      | wh_DE       | N                   |
      | wh_dropship | Y                   |

  @Id:S32533_TC1_Print
  Scenario: TC1 + TC11 - the printed invoice shows the stored Dutch VAT ID, also after the address's VAT ID changed
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL827764364B01 |
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
      | Identifier | C_BPartner_Location_ID | BuyerVATaxID   |
      | invoice    | customer_billing       | NL827764364B01 |

    # TC1: the print shows the stored Dutch VAT ID, not the billing partner's German one
    When The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    Then the PDF archived for the record identified by "invoice" contains text "NL827764364B01"
    And the PDF archived for the record identified by "invoice" does not contain text "DE369391233"

    # TC11: the VAT ID of the address changes later; the stored value and a reprint stay unchanged
    When update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL111111110B01 |
    And The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    Then validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL827764364B01 |
    And the PDF archived for the record identified by "invoice" contains text "NL827764364B01"
    And the PDF archived for the record identified by "invoice" does not contain text "NL111111110B01"
    And the PDF archived for the record identified by "invoice" does not contain text "DE369391233"
