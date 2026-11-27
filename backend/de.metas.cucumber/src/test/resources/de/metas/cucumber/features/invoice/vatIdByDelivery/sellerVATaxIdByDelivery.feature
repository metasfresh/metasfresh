@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: The seller VAT ID of a sales invoice comes from the fiscal representation whose tax the lines got
  A German organisation is VAT-registered in Greece (C_Fiscal_Representation), either with an own Greek VAT ID
  or through a fiscal representative. A sales invoice line that got a tax flagged IsFiscalRepresentation
  ("by tax") is invoiced with the registration's VAT ID as the seller VAT ID, and the invoice stores that
  registration. Every other line keeps the organisation's main VAT ID (stored as empty). Lines with different
  seller VAT IDs never share one invoice.

  Every scenario sets its own date and creates its own registration, valid only around that date, so no
  registration leaks into another scenario or feature. All taxes are in own tax categories:
  - taxCategory: DE->GR 24 % IsFiscalRepresentation=Y (picked under a registration), DE->GR 0 % for a buyer with
  VAT ID, IsFiscalRepresentation=N (picked without a registration), GR->GR 24 % IsFiscalRepresentation=Y (drop-ship
  lines under a registration) and DE->DE 19 %.
  - taxCategoryByTax: DE->GR 0 % for a buyer with VAT ID with an empty IsFiscalRepresentation and SeqNo 10 (it
  stays in under a registration and wins over the DE->GR 24 % of SeqNo 20), and GR->GR 24 % IsFiscalRepresentation=Y.
  The customers use an own header aggregation with only organisation, bill partner, bill location, currency,
  invoice doc type and SO flag, so candidates of one customer go on one invoice if nothing else splits them.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

    # an empty IsFiscalRepresentation / RequiresTaxCertificate cell stores NULL (applies either way).
    # TypeOfDestCountry is given so that the C_Tax interceptor keeps To_Country_ID on a new record
    And metasfresh contains C_TaxCategory
      | Identifier       |
      | taxCategory      |
      | taxCategoryByTax |
    And metasfresh contains C_Tax
      | Identifier  | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | RequiresTaxCertificate | IsFiscalRepresentation | SeqNo |
      | taxDEGR24   | taxCategory      | 24   | DE                       | GR                        | WITHIN_COUNTRY_AREA |                        | true                   | 10    |
      | taxDEGR0    | taxCategory      | 0    | DE                       | GR                        | WITHIN_COUNTRY_AREA | true                   | false                  | 10    |
      | taxGRGR24   | taxCategory      | 24   | GR                       | GR                        | DOMESTIC            |                        | true                   | 10    |
      | taxDEDE19   | taxCategory      | 19   | DE                       | DE                        | DOMESTIC            |                        |                        | 10    |
      | byTaxDEGR0  | taxCategoryByTax | 0    | DE                       | GR                        | WITHIN_COUNTRY_AREA | true                   |                        | 10    |
      | byTaxDEGR24 | taxCategoryByTax | 24   | DE                       | GR                        | WITHIN_COUNTRY_AREA |                        | true                   | 20    |
      | byTaxGRGR24 | taxCategoryByTax | 24   | GR                       | GR                        | DOMESTIC            |                        | true                   | 10    |

    And metasfresh contains M_Products:
      | Identifier   | ProductType |
      | product      | I           |
      | service      | S           |
      | productByTax | I           |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_DE      | ps_1                          | DE           | EUR           | true  | false         |
      | pl_GR      | ps_1                          | GR           | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2026-01-01 |
      | plv_GR     | pl_GR                     | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_DE      | plv_DE                            | product                 | 100.0    | PCE               | taxCategory      |
      | pp_GR      | plv_GR                            | product                 | 100.0    | PCE               | taxCategory      |
      | pps_DE     | plv_DE                            | service                 | 50.0     | PCE               | taxCategory      |
      | pps_GR     | plv_GR                            | service                 | 50.0     | PCE               | taxCategory      |
      | ppt_GR     | plv_GR                            | productByTax            | 100.0    | PCE               | taxCategoryByTax |

    # header aggregation without C_Order_ID: candidates of one customer end up on one invoice unless something else splits them
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

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_GR              | GR          |
      | location_DE_billing      | DE          |
      | location_DE              | DE          |
      | location_DE_GR           | GR          |
      | location_representative  | GR          |

    # customerGR: a Greek customer, billed and delivered in Greece, Greek VAT ID on its address
    # customerDE: billed at its German head office, receives goods in Germany and at a Greek address without own VAT ID
    # taxAgentGR: the organisation's fiscal representative in Greece
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule | VATaxID     | SO_Invoice_Aggregation_ID.Identifier |
      | customerGR | ps_1                          | Y          | D           |             | aggHeader                            |
      | customerDE | ps_1                          | Y          | D           | DE123456788 | aggHeader                            |
      | taxAgentGR | ps_1                          | N          | D           | EL222222228 |                                      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customerGR_GR      | 0123456789301 | customerGR               | Y        | Y               | Y        | Y               | location_GR                  |
      | customerDE_billing | 0123456789302 | customerDE               | N        | N               | Y        | Y               | location_DE_billing          |
      | customerDE_DE      | 0123456789303 | customerDE               | Y        | Y               | N        | N               | location_DE                  |
      | customerDE_GR      | 0123456789304 | customerDE               | Y        | N               | N        | N               | location_DE_GR               |
      | taxAgentGR_GR      | 0123456789305 | taxAgentGR               | Y        | Y               | Y        | Y               | location_representative      |
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | VATaxID     |
      | customerGR_GR                     | EL111111114 |
      | customerDE_billing                | DE123456788 |
      | customerDE_DE                     | DE123456788 |

    # both warehouses are in Germany (default: the organisation's address)
    And metasfresh contains M_Warehouse:
      | Identifier  | IsDropShipWarehouse |
      | wh_DE       | N                   |
      | wh_dropship | Y                   |

  @Id:S32533_TC17
  Scenario: own Greek registration, goods shipped from the German warehouse with the registration's tax: the Greek VAT ID is the seller VAT ID
    Given metasfresh has date and time 2026-03-02T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL123456783 | 2026-03-01 | 2026-03-03 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-03-02   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-03-02  | customerGR_GR                         | wh_DE                         |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL111111114  | EL123456783   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDEGR24 |

  @Id:S32533_TC18
  Scenario: Greek registration through a fiscal representative: the representative's VAT ID is the seller VAT ID
    Given metasfresh has date and time 2026-03-12T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | C_BPartner_Representative_ID | C_BPartner_Location_ID | ValidFrom  | ValidTo    |
      | regGR      | GR                        | taxAgentGR                   | taxAgentGR_GR          | 2026-03-11 | 2026-03-13 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-03-12   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-03-12  | customerGR_GR                         | wh_DE                         |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL111111114  | EL222222228   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDEGR24 |

  @Id:S32533_TC19
  Scenario: no registration in Greece: tax-free EU supply and no seller VAT ID (the main one)
    Given metasfresh has date and time 2026-03-22T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-03-22   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-03-22  | customerGR_GR                         | wh_DE                         |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL111111114  |               |                            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDEGR0 |

  @Id:S32533_TC21
  Scenario: service to Greece invoiced immediately, registration and Greek tax rows: the registration's tax and the Greek VAT ID
    Given metasfresh has date and time 2026-04-02T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL123456783 | 2026-04-01 | 2026-04-03 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customerGR               | 2026-04-02  | customerGR_GR                         | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | service                 | 10         |
    When the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | service                 | 10         | taxDEGR24               |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL111111114  | EL123456783   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | service                 | 10          | taxDEGR24 |

  @Id:S32533_TC22b
  Scenario: drop-ship warehouse to Greece with registration: line taxed GR->GR and the Greek VAT ID is the seller VAT ID
    Given metasfresh has date and time 2026-04-12T10:00:00+02:00[Europe/Berlin]
    And the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL123456783 | 2026-04-11 | 2026-04-13 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-04-12   | wh_dropship    |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-04-12  | customerGR_GR                         | wh_dropship                   |
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
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus | M_Warehouse_ID.Identifier |
      | shipment              | customerGR               | customerGR_GR                     | true      | CO        | wh_dropship               |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL111111114  | EL123456783   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxGRGR24 |

  @Id:S32533_TC10c
  Scenario: one order with a line to the Greek and a line to the German address, same buyer VAT ID: two invoices, split by the seller VAT ID
    Given metasfresh has date and time 2026-04-22T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL123456783 | 2026-04-21 | 2026-04-23 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier | InvoiceRule |
      | order      | true    | customerDE               | 2026-04-22  | customerDE_DE                         | customerDE_billing              | wh_DE                         | I           |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Location_ID.Identifier |
      | orderLineDE | order                 | service                 | 10         | customerDE_DE                     |
      | orderLineGR | order                 | service                 | 5          | customerDE_GR                     |
    And the order identified by order is completed
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | icDE                              | orderLineDE               | 10           |
      | icGR                              | orderLineGR               | 5            |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | icDE, icGR                        |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | icDE                              | invoiceDE               |
      | icGR                              | invoiceGR               |
    And validate created invoices
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoiceDE  | DE123456788  |               |                            |
      | invoiceGR  | DE123456788  | EL123456783   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceLineDE               | invoiceDE               | service                 | 10          | taxDEDE19 |
      | invoiceLineGR               | invoiceGR               | service                 | 5           | taxDEGR24 |

  @Id:S32533_TC24
  Scenario: registration in Greece, but the German-stock line gets the tax-free EU supply rate: no seller VAT ID (the main one)
    Given metasfresh has date and time 2026-05-02T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL123456783 | 2026-05-01 | 2026-05-03 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-05-02   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | productByTax            | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-05-02  | customerGR_GR                         | wh_DE                         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | productByTax            | 10         |
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
      | Identifier | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL111111114  |               |                            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID   |
      | invoiceL1                   | invoice                 | productByTax            | 10          | byTaxDEGR0 |

  @Id:S32533_TC22e
  Scenario: one Greek customer, one delivery from the drop-ship warehouse and one from German stock: two invoices, split by the seller VAT ID
    Given metasfresh has date and time 2026-08-12T10:00:00+02:00[Europe/Berlin]
    And the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL123456783 | 2026-08-11 | 2026-08-13 |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventoryDropShip         | 2026-08-12   | wh_dropship    |
      | inventoryDE               | 2026-08-12   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventoryDropShip         | inventoryLineDropShip         | productByTax            | 0       | 10       | PCE          |
      | inventoryDE               | inventoryLineDE               | productByTax            | 0       | 10       | PCE          |
    And the inventory identified by inventoryDropShip is completed
    And the inventory identified by inventoryDE is completed
    And metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | orderDropShip | true    | customerGR               | 2026-08-12  | customerGR_GR                         | wh_dropship                   |
      | orderDE       | true    | customerGR               | 2026-08-12  | customerGR_GR                         | wh_DE                         |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLineDropShip | orderDropShip         | productByTax            | 10         |
      | orderLineDE       | orderDE               | productByTax            | 5          |
    And the order identified by orderDropShip is completed
    And the order identified by orderDE is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier               | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentScheduleDropShip | orderLineDropShip         | N             |
      | shipmentScheduleDE       | orderLineDE               | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipmentDropShip      | shipmentScheduleDropShip         | D                 | Y                  |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipmentDE            | shipmentScheduleDE               | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus | M_Warehouse_ID.Identifier |
      | shipmentDropShip      | customerGR               | customerGR_GR                     | true      | CO        | wh_dropship               |
      | shipmentDE            | customerGR               | customerGR_GR                     | true      | CO        | wh_DE                     |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | icDropShip                        | orderLineDropShip         | 10           |
      | icDE                              | orderLineDE               | 5            |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | icDropShip, icDE                  |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | icDropShip                        | invoiceDropShip         |
      | icDE                              | invoiceDE               |
    And validate created invoices
      | Identifier      | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoiceDropShip | EL111111114  | EL123456783   | regGR                      |
      | invoiceDE       | EL111111114  |               |                            |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID    |
      | invoiceLineDropShip         | invoiceDropShip         | productByTax            | 10          | byTaxGRGR24 |
      | invoiceLineDE               | invoiceDE               | productByTax            | 5           | byTaxDEGR0  |

  @Id:S32533_TC17m
  Scenario: manual invoice on a doc type that overwrites the invoice date on completion: the new date falls into the Greek registration, the line is re-taxed with the registration's tax and the invoice stores the Greek VAT ID
    Given metasfresh has date and time 2026-09-22T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL123456783 | 2026-09-22 | 2026-09-23 |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerGR    | customerGR_GR          | pl_GR          | Ausgangsrechnung        | 2026-09-21   | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | Identifier  | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoiceLine | invoice      | product      | 10 PCE      |
    # dated before the registration: the tax-free EU supply
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceLine                 | invoice                 | product                 | 10          | taxDEGR0 |
    And the doc type of invoice "invoice" has IsOverwriteDateOnComplete true for this scenario

    # completion sets the invoice date to today, inside the registration: the line gets the registration's tax,
    # and the VAT IDs are stored from that tax, not from the one the line had before
    When the invoice identified by invoice is completed

    Then validate created invoices
      | Identifier | DocStatus | DateInvoiced | BuyerVATaxID | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        | 2026-09-22   | EL111111114  | EL123456783   | regGR                      |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceLine                 | invoice                 | product                 | 10          | taxDEGR24 |
