@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: Warehouse drop-ship lines ship from the destination country where the organisation is registered
  A German organisation sells goods that leave from a drop-ship warehouse (IsDropShipWarehouse=Y) directly
  in the destination country. When the organisation holds a fiscal representation (VAT registration) in the
  destination country and its switch IsDropShipTaxFromDestCountry is on, the sales line is taxed like a
  delivery within that country (GR->GR) instead of an intra-EU supply from Germany (DE->GR 0 %).

  The rule must hold on every tax path of the line: the order line, the invoice line created from the
  shipment, and an invoice line added by hand to an invoice that names the drop-ship warehouse. Without a
  registration in the destination country, from a normal warehouse, or with the switch off, nothing changes.
  Changing the warehouse of a drafted invoice to or from the drop-ship warehouse re-taxes its lines, and the
  seller VAT ID and fiscal representation the invoice stores follow the new tax.

  The registration is valid 2026-07-11 .. 2026-07-13 only, so it does not affect any other feature.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-12T10:00:00+02:00[Europe/Berlin]
    And all periods are open
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled

    # own tax category, so no other feature's taxes interfere; an empty IsFiscalRepresentation cell stores NULL.
    # TypeOfDestCountry is given so that the C_Tax interceptor keeps To_Country_ID on a new record
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | IsFiscalRepresentation |
      | taxGRGR24  | taxCategory      | 24   | GR                       | GR                        | DOMESTIC            | true                   |
      | taxDEGR0   | taxCategory      | 0    | DE                       | GR                        | WITHIN_COUNTRY_AREA |                        |
      | taxDENL0   | taxCategory      | 0    | DE                       | NL                        | WITHIN_COUNTRY_AREA |                        |

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_DE      | ps_1                          | DE           | EUR           | true  | false         |
      | pl_GR      | ps_1                          | GR           | EUR           | true  | false         |
      | pl_NL      | ps_1                          | NL           | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2026-01-01 |
      | plv_GR     | pl_GR                     | 2026-01-01 |
      | plv_NL     | pl_NL                     | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID |
      | pp_DE      | plv_DE                            | product                 | 100.0    | PCE               | taxCategory      |
      | pp_GR      | plv_GR                            | product                 | 100.0    | PCE               | taxCategory      |
      | pp_NL      | plv_NL                            | product                 | 100.0    | PCE               | taxCategory      |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_GR              | GR          |
      | location_NL              | NL          |
      | location_DE              | DE          |
      | location_representative  | GR          |

    # customerGR bills and receives in Greece; customerNL bills from its German head office and receives in the Netherlands;
    # taxAgentGR is the organisation's fiscal representative in Greece
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | InvoiceRule |
      | customerGR | ps_1                          | Y          | D           |
      | customerNL | ps_1                          | Y          | D           |
      | taxAgentGR | ps_1                          | N          | D           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier         | GLN           | C_BPartner_ID.Identifier | IsShipTo | IsShipToDefault | IsBillTo | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | customerGR_GR      | 0123456789101 | customerGR               | Y        | Y               | Y        | Y               | location_GR                  |
      | customerNL_billing | 0123456789102 | customerNL               | N        | N               | Y        | Y               | location_DE                  |
      | customerNL_NL      | 0123456789103 | customerNL               | Y        | Y               | N        | N               | location_NL                  |
      | taxAgentGR_GR      | 0123456789104 | taxAgentGR               | Y        | Y               | Y        | Y               | location_representative      |

    # the organisation's VAT registration in Greece
    And metasfresh contains C_Fiscal_Representation:
      | Identifier | To_Country_ID.CountryCode | VATaxID     | C_BPartner_Representative_ID | C_BPartner_Location_ID | ValidFrom  | ValidTo    |
      | regGR      | GR                        | EL999999999 | taxAgentGR                   | taxAgentGR_GR          | 2026-07-11 | 2026-07-13 |

    # both warehouses are in Germany (default: the organisation's address)
    And metasfresh contains M_Warehouse:
      | Identifier  | IsDropShipWarehouse |
      | wh_dropship | Y                   |
      | wh_DE       | N                   |

  @Id:S32533_TC25
  Scenario: drop-ship warehouse to Greece with registration: order line and invoice line from the shipment are taxed GR->GR
    Given the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-07-12   | wh_dropship    |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-07-12  | customerGR_GR                         | wh_dropship                   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    When the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | product                 | 10         | taxGRGR24               |

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
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxGRGR24 |

  @Id:S32533_TC25i
  Scenario: drop-ship warehouse to the Netherlands without registration there: DE->NL on order and invoice line
    Given the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-07-12   | wh_dropship    |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerNL               | 2026-07-12  | customerNL_NL                         | wh_dropship                   |
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
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus | M_Warehouse_ID.Identifier |
      | shipment              | customerNL               | customerNL_NL                     | true      | CO        | wh_dropship               |

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

  @Id:S32533_TC25ii
  Scenario: normal German warehouse to Greece with registration: DE->GR 0 % on order and invoice line
    Given the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-07-12   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-07-12  | customerGR_GR                         | wh_DE                         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    When the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | product                 | 10         | taxDEGR0                |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier       | C_OrderLine_ID.Identifier | IsToRecompute |
      | shipmentSchedule | orderLine                 | N             |
    And shipment is generated for the following shipment schedule
      | M_InOut_ID.Identifier | M_ShipmentSchedule_ID.Identifier | quantityTypeToUse | isCompleteShipment |
      | shipment              | shipmentSchedule                 | D                 | Y                  |
    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus | M_Warehouse_ID.Identifier |
      | shipment              | customerGR               | customerGR_GR                     | true      | CO        | wh_DE                     |

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
      | invoiceL1                   | invoice                 | product                 | 10          | taxDEGR0 |

  @Id:S32533_TC25v
  Scenario: switch off: drop-ship warehouse to Greece with registration keeps DE->GR 0 % on the order line
    Given the organisation 1000000 has IsDropShipTaxFromDestCountry 'N' for this scenario
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-07-12  | customerGR_GR                         | wh_dropship                   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    When the order identified by order is completed
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | product                 | 10         | taxDEGR0                |

  @Id:S32533_TC25vi
  Scenario: line added by hand to an invoice from the drop-ship warehouse to Greece with registration is taxed GR->GR
    Given the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | M_PriceList_ID | M_Warehouse_ID | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code |
      | invoice    | customerGR    | customerGR_GR          | pl_GR          | wh_dropship    | Ausgangsrechnung        | 2026-07-12   | Spot                     | true    | EUR                 |
    When metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoiceL1  | invoice      | product      | 10 PCE      |
    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxGRGR24 |

  @Id:S32533_TC25vii
  Scenario: the warehouse of a drafted invoice for goods shipped from the German warehouse to Greece is changed to the drop-ship warehouse: the line is re-taxed GR->GR and the invoice stores the registration
    Given the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-07-12   | wh_DE          |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-07-12  | customerGR_GR                         | wh_DE                         |
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
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDEGR0 |
    And validate created invoices
      | Identifier | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    |               |                            |

    When update C_Invoice:
      | Identifier | M_Warehouse_ID |
      | invoice    | wh_dropship    |
    And the invoice identified by invoice is completed

    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxGRGR24 |
    And validate created invoices
      | Identifier | DocStatus | GrandTotal | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        | 1240 EUR   | EL999999999   | regGR                      |

  @Id:S32533_TC25viii
  Scenario: the warehouse of a drafted invoice for goods shipped from the drop-ship warehouse to Greece is changed to the German warehouse: the line is re-taxed DE->GR and the invoice no longer stores the registration
    Given the organisation 1000000 has IsDropShipTaxFromDestCountry 'Y' for this scenario
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inventory                 | 2026-07-12   | wh_dropship    |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inventory                 | inventoryLine                 | product                 | 0       | 10       | PCE          |
    And the inventory identified by inventory is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | customerGR               | 2026-07-12  | customerGR_GR                         | wh_dropship                   |
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
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID  |
      | invoiceL1                   | invoice                 | product                 | 10          | taxGRGR24 |
    And validate created invoices
      | Identifier | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | EL999999999   | regGR                      |

    When update C_Invoice:
      | Identifier | M_Warehouse_ID |
      | invoice    | wh_DE          |
    And the invoice identified by invoice is completed

    Then validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | C_Tax_ID |
      | invoiceL1                   | invoice                 | product                 | 10          | taxDEGR0 |
    And validate created invoices
      | Identifier | DocStatus | GrandTotal | SellerVATaxID | C_Fiscal_Representation_ID |
      | invoice    | CO        | 1000 EUR   |               |                            |
