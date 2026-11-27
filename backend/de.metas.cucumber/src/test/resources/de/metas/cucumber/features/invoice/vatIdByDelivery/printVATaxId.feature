@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: The printed sales invoice shows the stored buyer and seller VAT IDs
  The invoice description of the printed sales invoice shows the buyer VAT ID stored on the invoice
  (the VAT ID of the address the goods were delivered to), not the VAT ID of the billing partner.
  A later change of the address's VAT ID does not change a reprint.

  The footer of the printed sales invoice shows the seller VAT ID stored on the invoice: the organisation's
  own VAT ID in the country of a fiscal registration, or its main VAT ID when the invoice has none. With a
  registration through a fiscal representative the footer keeps the main VAT ID and adds a line with the
  representative's name, address and VAT ID.

  Every seller scenario sets its own date and creates its own registration, valid only around that date, so
  no registration leaks into another scenario or feature.

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
    # Greek taxes in an own category: DE->GR 24 % and GR->GR 24 % are picked under a registration in Greece,
    # DE->GR 0 % (buyer with VAT ID) without one
    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategoryGR |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | RequiresTaxCertificate | IsFiscalRepresentation |
      | taxDEGR24  | taxCategoryGR    | 24   | DE                       | GR                        | WITHIN_COUNTRY_AREA |                        | true                   |
      | taxDEGR0   | taxCategoryGR    | 0    | DE                       | GR                        | WITHIN_COUNTRY_AREA | true                   | false                  |
      | taxGRGR24  | taxCategoryGR    | 24   | GR                       | GR                        | DOMESTIC            |                        | true                   |

    And metasfresh contains M_Products:
      | Identifier | ProductType |
      | product    | I           |
      | service    | S           |
      | productGR  | I           |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_DE      | ps_1                          | DE           | EUR           | true  | false         |
      | pl_NL      | ps_1                          | NL           | EUR           | true  | false         |
      | pl_GR      | ps_1                          | GR           | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2026-01-01 |
      | plv_NL     | pl_NL                     | 2026-01-01 |
      | plv_GR     | pl_GR                     | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_DE      | plv_DE                            | product                 | 100.0    | PCE               | taxCategory      |
      | pp_NL      | plv_NL                            | product                 | 100.0    | PCE               | taxCategory      |
      | pps_DE     | plv_DE                            | service                 | 50.0     | PCE               | taxCategory      |
      | pps_NL     | plv_NL                            | service                 | 50.0     | PCE               | taxCategory      |
      | pp_GR      | plv_GR                            | productGR               | 100.0    | PCE               | taxCategoryGR    |

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
      | location_GR              | GR          |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1       | OPT.Postal | OPT.City |
      | location_representative  | GR          | Leoforos Syngrou 7 | 11743      | Athina   |

    # customer: billed at its German head office, receives goods in Germany and in the Netherlands
    # thirdParty: a Dutch partner the customer has goods drop-shipped to
    # customerGR: a Greek customer, billed and delivered in Greece
    # taxAgentGR: the organisation's fiscal representative in Greece
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     | SO_Invoice_Aggregation_ID.Identifier | SO_InvoiceLine_Aggregation_ID.Identifier |
      | customer   | ps_1                          | Y          | D           | DE111111125 | aggHeader                            | aggLinePerIC                             |
      | thirdParty | ps_1                          | Y          | D           |             |                                      |                                          |
      | customerGR | ps_1                          | Y          | D           |             | aggHeader                            |                                          |
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     | Name                |
      | taxAgentGR | ps_1                          | N          | D           | EL998877666 | Fiscal Agent Hellas |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customer_billing  | 0123456789201 | customer                 | N        | N               | Y        | Y               | location_billing             |
      | customer_DE       | 0123456789202 | customer                 | Y        | Y               | N        | N               | location_DE                  |
      | customer_DE_noVAT | 0123456789203 | customer                 | Y        | N               | N        | N               | location_DE_noVAT            |
      | customer_NL       | 0123456789204 | customer                 | Y        | N               | N        | N               | location_NL                  |
      | thirdParty_NL     | 0123456789205 | thirdParty               | Y        | Y               | Y        | Y               | location_thirdParty_NL       |
      | customerGR_GR     | 0123456789206 | customerGR               | Y        | Y               | Y        | Y               | location_GR                  |
      | taxAgentGR_GR     | 0123456789207 | taxAgentGR               | Y        | Y               | Y        | Y               | location_representative      |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID     |
      | customer_billing                  | DE111111125 |
      | customer_DE                       | DE111111125 |
      | customerGR_GR                     | EL094014201 |

    # both warehouses are in Germany (default: the organisation's address)
    And metasfresh contains M_Warehouse:
      | Identifier  | IsDropShipWarehouse |
      | wh_DE       | N                   |
      | wh_dropship | Y                   |

  @Id:S32533_TC1_Print
  Scenario: TC1 + TC11 - the printed invoice shows the stored Dutch VAT ID, also after the address's VAT ID changed
    Given update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL333333330B01 |
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
      | invoice    | customer_billing       | NL333333330B01 |

    # TC1: the print shows the stored Dutch VAT ID, not the billing partner's German one
    When The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    Then the PDF archived for the record identified by "invoice" contains text "NL333333330B01"
    And the PDF archived for the record identified by "invoice" does not contain text "DE111111125"

    # TC11: the VAT ID of the address changes later; the stored value and a reprint stay unchanged
    When update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID        |
      | customer_NL                       | NL111111110B01 |
    And The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    Then validate created invoices
      | Identifier | BuyerVATaxID   |
      | invoice    | NL333333330B01 |
    And the PDF archived for the record identified by "invoice" contains text "NL333333330B01"
    And the PDF archived for the record identified by "invoice" does not contain text "NL111111110B01"
    And the PDF archived for the record identified by "invoice" does not contain text "DE111111125"

  @Id:S32533_TC17_Print
  Scenario: TC17 (print) - own Greek registration, goods shipped from the German warehouse with the registration's tax: the footer shows the Greek VAT ID
    Given metasfresh has date and time 2026-05-12T10:00:00+02:00[Europe/Berlin]
    And the org partner of organisation 1000000 has VATaxID "DE136695976" for this scenario
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL094259216 | 2026-05-11 | 2026-05-13 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-05-12   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | productGR               | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-05-12  | customerGR_GR                         | wh_DE                         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | productGR               | 10         |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL094014201  | EL094259216   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | productGR               | 10          | taxDEGR24 |
    When The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    # the Greek VAT ID replaces the organisation's main VAT ID in the footer
    Then the PDF archived for the record identified by "invoice" contains text "EL094259216"
    And the PDF archived for the record identified by "invoice" does not contain text "DE136695976"
    And the PDF archived for the record identified by "invoice" does not contain text "Fiskalvertreter"

  @Id:S32533_TC18_Print
  Scenario: TC18 (print) - Greek registration through a fiscal representative: the footer keeps the main VAT ID and adds the representative
    Given metasfresh has date and time 2026-05-22T10:00:00+02:00[Europe/Berlin]
    And the org partner of organisation 1000000 has VATaxID "DE136695976" for this scenario
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | C_BPartner_Representative_ID | C_BPartner_Location_ID | ValidFrom  | ValidTo    |
      | regGR      | GR                        | taxAgentGR                   | taxAgentGR_GR          | 2026-05-21 | 2026-05-23 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-05-22   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | productGR               | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-05-22  | customerGR_GR                         | wh_DE                         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | productGR               | 10         |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL094014201  | EL998877666   | regGR                      |
    When The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    # the footer's VAT line shows the organisation's main VAT ID, the representative's VAT ID only in the representative block
    Then the visual line containing text "DE136695976" in the PDF archived for the record identified by "invoice" does not contain text "EL998877666"
    And the visual line containing text "Fiskalvertreter" in the PDF archived for the record identified by "invoice" is exactly "Fiskalvertreter: Fiscal Agent Hellas, Leoforos Syngrou 7, 11743 Athina, Griechenland | UID Nr.: EL998877666"
    And the PDF archived for the record identified by "invoice" has no overlapping text

  @Id:S32533_TC19_Print
  Scenario: TC19 (print) - no registration in Greece: the footer shows no Greek VAT ID
    Given metasfresh has date and time 2026-06-02T10:00:00+02:00[Europe/Berlin]
    And the org partner of organisation 1000000 has VATaxID "DE136695976" for this scenario
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-06-02   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | productGR               | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-06-02  | customerGR_GR                         | wh_DE                         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | productGR               | 10         |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL094014201  |               |                            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | productGR               | 10          | taxDEGR0 |
    When The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    # the footer shows the organisation's main VAT ID, as without the feature
    Then the PDF archived for the record identified by "invoice" contains text "DE136695976"
    And the PDF archived for the record identified by "invoice" does not contain text "EL094259216"
    And the PDF archived for the record identified by "invoice" does not contain text "Fiskalvertreter"

  @Id:S32533_TC22b_Print
  Scenario: TC22(b) (print) - drop-ship warehouse to Greece with registration: the footer shows the Greek VAT ID
    Given metasfresh has date and time 2026-06-12T10:00:00+02:00[Europe/Berlin]
    And the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL094259216 | 2026-06-11 | 2026-06-13 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-06-12   | wh_dropship    |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | productGR               | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-06-12  | customerGR_GR                         | wh_dropship                   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | productGR               | 10         |
    And the order identified by order is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shipmentSchedule                 | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus | M_Warehouse_ID.Identifier |
      | shipment              | customerGR               | customerGR_GR                     | true      | CO        | wh_dropship               |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL094014201  | EL094259216   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | productGR               | 10          | taxGRGR24 |
    When The jasper process is run
      | Value             | Record_ID |
      | Rechnung (Jasper) | invoice   |
    Then the PDF archived for the record identified by "invoice" contains text "EL094259216"
    And the PDF archived for the record identified by "invoice" does not contain text "Fiskalvertreter"
