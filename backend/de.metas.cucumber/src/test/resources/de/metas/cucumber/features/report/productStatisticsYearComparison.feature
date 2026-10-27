@from:cucumber
@ghActions:run_on_executor2
Feature: Product Statistics Year Comparison - reference replay
  ## Replays a legacy reference printout (year N = 2026 against year N-1 = 2025) with neutral data and expects the report
  ## to reproduce its figures exactly.
  ## - Quantity: one order and one back-dated, completed customer shipment per month (stock UoM = PCE), integer quantities.
  ##   Stock: one PI-less inventory of 238281 PCE = 93341 (2026) + 144935 (2025) + 5 (extra shipment, returned).
  ## - Amount: one completed single-line sales invoice "1 x amount" per month on a NET price list (on a tax-included list
  ##   LineNetAmt would be gross). The invoice's DateAcct is the pinned clock date, so the clock is pinned per month
  ##   before each invoice is created.
  ## - Special months (chosen so that the sums still equal the reference values):
  ##   2025-03 amount 166529.58 = invoice 170000.00 minus credit note 3470.42 (credit note lowers the amount only);
  ##   2026-02 quantity 8994: an extra shipment of 5, returned in full by the customer (customer return) and not credited,
  ##   so the quantity stays at the reference figure.
  ## - Deviation is on quantity, rounded to 2 decimals; no deviation total.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-01-02T10:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And all periods are open
    And metasfresh contains C_Calendar "PSYC_Calendar" with fiscal years:
      | C_Year_ID | FiscalYear |
      | yearPrev  | 2025       |
      | yearN     | 2026       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | IsQualityReturnWarehouse |
      | warehouseQR    | Y                        |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And metasfresh contains C_TaxCategory
      | Identifier |
      | psycTaxCat |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | psycTax19  | psycTaxCat       | 19   | DE                       | DE                        |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | psycPS     |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | psycPL     | psycPS             | DE           | CHF           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | psycPLV    | psycPL         |
    And metasfresh contains M_Products:
      | Identifier |
      | psycRef    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | psycPLV                | psycRef      | 10.00    | PCE      | psycTaxCat       |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name           | IsCustomer | M_PricingSystem_ID | AllowConsolidateInOut |
      | psycBP     | PSYC customer  | Y          | psycPS             | N                     |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | psycBPLoc  | psycBP        | DE           | Y               | Y               |

  @from:cucumber
  Scenario: TC1 reference case - 2026 against 2025
    Given metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invRef         | invRef_l1          | 2025-01-02   | warehouseStd   | psycRef      | 0       | 238281   | PCE          |

    # 2025-01: quantity 7095, amount 116991.65
    And metasfresh has date and time 2025-01-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202501     | Y       | psycBP        | 2025-01-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202501_l1  | so202501     | psycRef      | 7095      |
    And the order identified by so202501 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202501  | so202501_l1      | N             | 7095        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202501             | D            | false               | false       | 7095                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202501             | ship202501   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202501   | 2025-01-15   |
    And the shipment identified by ship202501 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202501    | psycBP        | 2025-01-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202501_l1 | inv202501      | psycRef      | 1 PCE       | 116991.65 | psycTax19    |
    And the invoice identified by inv202501 is completed

    # 2025-02: quantity 9085, amount 148717.36
    And metasfresh has date and time 2025-02-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202502     | Y       | psycBP        | 2025-02-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202502_l1  | so202502     | psycRef      | 9085      |
    And the order identified by so202502 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202502  | so202502_l1      | N             | 9085        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202502             | D            | false               | false       | 9085                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202502             | ship202502   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202502   | 2025-02-15   |
    And the shipment identified by ship202502 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202502    | psycBP        | 2025-02-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202502_l1 | inv202502      | psycRef      | 1 PCE       | 148717.36 | psycTax19    |
    And the invoice identified by inv202502 is completed

    # 2025-03: quantity 10252, amount 166529.58
    And metasfresh has date and time 2025-03-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202503     | Y       | psycBP        | 2025-03-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202503_l1  | so202503     | psycRef      | 10252      |
    And the order identified by so202503 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202503  | so202503_l1      | N             | 10252        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202503             | D            | false               | false       | 10252                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202503             | ship202503   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202503   | 2025-03-15   |
    And the shipment identified by ship202503 is completed
    # Special month (credit note): the amount 166529.58 is an invoice of 170000.00 minus a price-correction credit note of 3470.42.
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202503    | psycBP        | 2025-03-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202503_l1 | inv202503      | psycRef      | 1 PCE       | 170000.00 | psycTax19    |
    And the invoice identified by inv202503 is completed
    And create credit memo for C_Invoice
      | CreditMemo | C_Invoice_ID | CreditMemo.PriceEntered |
      | cm202503     | inv202503      | 3470.42                 |
    And the invoice identified by cm202503 is completed

    # 2025-04: quantity 14602, amount 237589.25
    And metasfresh has date and time 2025-04-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202504     | Y       | psycBP        | 2025-04-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202504_l1  | so202504     | psycRef      | 14602      |
    And the order identified by so202504 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202504  | so202504_l1      | N             | 14602        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202504             | D            | false               | false       | 14602                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202504             | ship202504   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202504   | 2025-04-15   |
    And the shipment identified by ship202504 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202504    | psycBP        | 2025-04-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202504_l1 | inv202504      | psycRef      | 1 PCE       | 237589.25 | psycTax19    |
    And the invoice identified by inv202504 is completed

    # 2025-05: quantity 11729, amount 188683.65
    And metasfresh has date and time 2025-05-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202505     | Y       | psycBP        | 2025-05-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202505_l1  | so202505     | psycRef      | 11729      |
    And the order identified by so202505 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202505  | so202505_l1      | N             | 11729        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202505             | D            | false               | false       | 11729                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202505             | ship202505   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202505   | 2025-05-15   |
    And the shipment identified by ship202505 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202505    | psycBP        | 2025-05-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202505_l1 | inv202505      | psycRef      | 1 PCE       | 188683.65 | psycTax19    |
    And the invoice identified by inv202505 is completed

    # 2025-06: quantity 14826, amount 237174.51
    And metasfresh has date and time 2025-06-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202506     | Y       | psycBP        | 2025-06-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202506_l1  | so202506     | psycRef      | 14826      |
    And the order identified by so202506 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202506  | so202506_l1      | N             | 14826        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202506             | D            | false               | false       | 14826                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202506             | ship202506   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202506   | 2025-06-15   |
    And the shipment identified by ship202506 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202506    | psycBP        | 2025-06-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202506_l1 | inv202506      | psycRef      | 1 PCE       | 237174.51 | psycTax19    |
    And the invoice identified by inv202506 is completed

    # 2025-07: quantity 13709, amount 220377.41
    And metasfresh has date and time 2025-07-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202507     | Y       | psycBP        | 2025-07-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202507_l1  | so202507     | psycRef      | 13709      |
    And the order identified by so202507 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202507  | so202507_l1      | N             | 13709        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202507             | D            | false               | false       | 13709                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202507             | ship202507   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202507   | 2025-07-15   |
    And the shipment identified by ship202507 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202507    | psycBP        | 2025-07-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202507_l1 | inv202507      | psycRef      | 1 PCE       | 220377.41 | psycTax19    |
    And the invoice identified by inv202507 is completed

    # 2025-08: quantity 14171, amount 227864.84
    And metasfresh has date and time 2025-08-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202508     | Y       | psycBP        | 2025-08-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202508_l1  | so202508     | psycRef      | 14171      |
    And the order identified by so202508 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202508  | so202508_l1      | N             | 14171        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202508             | D            | false               | false       | 14171                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202508             | ship202508   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202508   | 2025-08-15   |
    And the shipment identified by ship202508 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202508    | psycBP        | 2025-08-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202508_l1 | inv202508      | psycRef      | 1 PCE       | 227864.84 | psycTax19    |
    And the invoice identified by inv202508 is completed

    # 2025-09: quantity 13750, amount 220653.64
    And metasfresh has date and time 2025-09-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202509     | Y       | psycBP        | 2025-09-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202509_l1  | so202509     | psycRef      | 13750      |
    And the order identified by so202509 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202509  | so202509_l1      | N             | 13750        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202509             | D            | false               | false       | 13750                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202509             | ship202509   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202509   | 2025-09-15   |
    And the shipment identified by ship202509 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202509    | psycBP        | 2025-09-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202509_l1 | inv202509      | psycRef      | 1 PCE       | 220653.64 | psycTax19    |
    And the invoice identified by inv202509 is completed

    # 2025-10: quantity 10608, amount 173311.59
    And metasfresh has date and time 2025-10-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202510     | Y       | psycBP        | 2025-10-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202510_l1  | so202510     | psycRef      | 10608      |
    And the order identified by so202510 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202510  | so202510_l1      | N             | 10608        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202510             | D            | false               | false       | 10608                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202510             | ship202510   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202510   | 2025-10-15   |
    And the shipment identified by ship202510 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202510    | psycBP        | 2025-10-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202510_l1 | inv202510      | psycRef      | 1 PCE       | 173311.59 | psycTax19    |
    And the invoice identified by inv202510 is completed

    # 2025-11: quantity 8454, amount 138884.82
    And metasfresh has date and time 2025-11-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202511     | Y       | psycBP        | 2025-11-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202511_l1  | so202511     | psycRef      | 8454      |
    And the order identified by so202511 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202511  | so202511_l1      | N             | 8454        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202511             | D            | false               | false       | 8454                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202511             | ship202511   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202511   | 2025-11-15   |
    And the shipment identified by ship202511 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202511    | psycBP        | 2025-11-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202511_l1 | inv202511      | psycRef      | 1 PCE       | 138884.82 | psycTax19    |
    And the invoice identified by inv202511 is completed

    # 2025-12: quantity 16654, amount 284978.08
    And metasfresh has date and time 2025-12-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202512     | Y       | psycBP        | 2025-12-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202512_l1  | so202512     | psycRef      | 16654      |
    And the order identified by so202512 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202512  | so202512_l1      | N             | 16654        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202512             | D            | false               | false       | 16654                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202512             | ship202512   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202512   | 2025-12-15   |
    And the shipment identified by ship202512 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202512    | psycBP        | 2025-12-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202512_l1 | inv202512      | psycRef      | 1 PCE       | 284978.08 | psycTax19    |
    And the invoice identified by inv202512 is completed

    # 2026-01: quantity 6837, amount 112686.46
    And metasfresh has date and time 2026-01-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202601     | Y       | psycBP        | 2026-01-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202601_l1  | so202601     | psycRef      | 6837      |
    And the order identified by so202601 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202601  | so202601_l1      | N             | 6837        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202601             | D            | false               | false       | 6837                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202601             | ship202601   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202601   | 2026-01-15   |
    And the shipment identified by ship202601 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202601    | psycBP        | 2026-01-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202601_l1 | inv202601      | psycRef      | 1 PCE       | 112686.46 | psycTax19    |
    And the invoice identified by inv202601 is completed

    # 2026-02: quantity 8994, amount 147013.42
    And metasfresh has date and time 2026-02-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202602     | Y       | psycBP        | 2026-02-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202602_l1  | so202602     | psycRef      | 8994      |
    And the order identified by so202602 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202602  | so202602_l1      | N             | 8994        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202602             | D            | false               | false       | 8994                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202602             | ship202602   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202602   | 2026-02-15   |
    And the shipment identified by ship202602 is completed
    # Special month (customer return): an extra shipment of 5, returned by the customer in full and not credited,
    # so the month's quantity stays 8994. (The return step returns a whole shipment, hence the separate shipment.)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202602x     | Y       | psycBP        | 2026-02-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202602x_l1  | so202602x     | psycRef      | 5      |
    And the order identified by so202602x is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202602x  | so202602x_l1      | N             | 5        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202602x             | D            | false               | false       | 5                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202602x             | ship202602x   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202602x   | 2026-02-15   |
    And the shipment identified by ship202602x is completed
    And generate customer return from shipment
      | M_InOut_ID | CustomerReturn_ID |
      | ship202602x  | ret202602x          |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ret202602x   | 2026-02-20   |
    And the return inOut identified by ret202602x is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202602    | psycBP        | 2026-02-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202602_l1 | inv202602      | psycRef      | 1 PCE       | 147013.42 | psycTax19    |
    And the invoice identified by inv202602 is completed

    # 2026-03: quantity 13616, amount 222918.41
    And metasfresh has date and time 2026-03-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202603     | Y       | psycBP        | 2026-03-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202603_l1  | so202603     | psycRef      | 13616      |
    And the order identified by so202603 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202603  | so202603_l1      | N             | 13616        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202603             | D            | false               | false       | 13616                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202603             | ship202603   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202603   | 2026-03-15   |
    And the shipment identified by ship202603 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202603    | psycBP        | 2026-03-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202603_l1 | inv202603      | psycRef      | 1 PCE       | 222918.41 | psycTax19    |
    And the invoice identified by inv202603 is completed

    # 2026-04: quantity 11258, amount 181886.58
    And metasfresh has date and time 2026-04-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202604     | Y       | psycBP        | 2026-04-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202604_l1  | so202604     | psycRef      | 11258      |
    And the order identified by so202604 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202604  | so202604_l1      | N             | 11258        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202604             | D            | false               | false       | 11258                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202604             | ship202604   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202604   | 2026-04-15   |
    And the shipment identified by ship202604 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202604    | psycBP        | 2026-04-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202604_l1 | inv202604      | psycRef      | 1 PCE       | 181886.58 | psycTax19    |
    And the invoice identified by inv202604 is completed

    # 2026-05: quantity 12819, amount 205993.68
    And metasfresh has date and time 2026-05-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202605     | Y       | psycBP        | 2026-05-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202605_l1  | so202605     | psycRef      | 12819      |
    And the order identified by so202605 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202605  | so202605_l1      | N             | 12819        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202605             | D            | false               | false       | 12819                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202605             | ship202605   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202605   | 2026-05-15   |
    And the shipment identified by ship202605 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202605    | psycBP        | 2026-05-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202605_l1 | inv202605      | psycRef      | 1 PCE       | 205993.68 | psycTax19    |
    And the invoice identified by inv202605 is completed

    # 2026-06: quantity 13291, amount 213655.02
    And metasfresh has date and time 2026-06-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202606     | Y       | psycBP        | 2026-06-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202606_l1  | so202606     | psycRef      | 13291      |
    And the order identified by so202606 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202606  | so202606_l1      | N             | 13291        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202606             | D            | false               | false       | 13291                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202606             | ship202606   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202606   | 2026-06-15   |
    And the shipment identified by ship202606 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202606    | psycBP        | 2026-06-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202606_l1 | inv202606      | psycRef      | 1 PCE       | 213655.02 | psycTax19    |
    And the invoice identified by inv202606 is completed

    # 2026-07: quantity 14861, amount 238201.95
    And metasfresh has date and time 2026-07-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202607     | Y       | psycBP        | 2026-07-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202607_l1  | so202607     | psycRef      | 14861      |
    And the order identified by so202607 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202607  | so202607_l1      | N             | 14861        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202607             | D            | false               | false       | 14861                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202607             | ship202607   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202607   | 2026-07-15   |
    And the shipment identified by ship202607 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202607    | psycBP        | 2026-07-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202607_l1 | inv202607      | psycRef      | 1 PCE       | 238201.95 | psycTax19    |
    And the invoice identified by inv202607 is completed

    # 2026-08: quantity 11665, amount 187158.12
    And metasfresh has date and time 2026-08-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so202608     | Y       | psycBP        | 2026-08-15  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so202608_l1  | so202608     | psycRef      | 11665      |
    And the order identified by so202608 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched202608  | so202608_l1      | N             | 11665        |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched202608             | D            | false               | false       | 11665                                           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched202608             | ship202608   |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship202608   | 2026-08-15   |
    And the shipment identified by ship202608 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv202608    | psycBP        | 2026-08-15   | true    | CHF           | psycPL         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price  | C_Tax_ID$set |
      | inv202608_l1 | inv202608      | psycRef      | 1 PCE       | 187158.12 | psycTax19    |
    And the invoice identified by inv202608 is completed

    # Today is after the last document, inside year N
    And metasfresh has date and time 2026-10-27T10:00:00+01:00[Europe/Berlin]

    Then the product statistics year comparison report for org "-", year "yearN", product "psycRef" returns:
      | M_Product_ID | Qty_01 | Qty_02 | Qty_03 | Qty_04 | Qty_05 | Qty_06 | Qty_07 | Qty_08 | Qty_09 | Qty_10 | Qty_11 | Qty_12 | Qty_Total | Amt_01    | Amt_02    | Amt_03    | Amt_04    | Amt_05    | Amt_06    | Amt_07    | Amt_08    | Amt_09 | Amt_10 | Amt_11 | Amt_12 | Amt_Total  | PrevQty_01 | PrevQty_02 | PrevQty_03 | PrevQty_04 | PrevQty_05 | PrevQty_06 | PrevQty_07 | PrevQty_08 | PrevQty_09 | PrevQty_10 | PrevQty_11 | PrevQty_12 | PrevQty_Total | PrevAmt_01 | PrevAmt_02 | PrevAmt_03 | PrevAmt_04 | PrevAmt_05 | PrevAmt_06 | PrevAmt_07 | PrevAmt_08 | PrevAmt_09 | PrevAmt_10 | PrevAmt_11 | PrevAmt_12 | PrevAmt_Total | Dev_01 | Dev_02 | Dev_03 | Dev_04 | Dev_05 | Dev_06 | Dev_07 | Dev_08 | Dev_09  | Dev_10  | Dev_11  | Dev_12  | Currency |
      | psycRef      | 6837   | 8994   | 13616  | 11258  | 12819  | 13291  | 14861  | 11665  |        |        |        |        | 93341     | 112686.46 | 147013.42 | 222918.41 | 181886.58 | 205993.68 | 213655.02 | 238201.95 | 187158.12 |        |        |        |        | 1509513.64 | 7095       | 9085       | 10252      | 14602      | 11729      | 14826      | 13709      | 14171      | 13750      | 10608      | 8454       | 16654      | 144935        | 116991.65  | 148717.36  | 166529.58  | 237589.25  | 188683.65  | 237174.51  | 220377.41  | 227864.84  | 220653.64  | 173311.59  | 138884.82  | 284978.08  | 2361756.38    | -3.64  | -1.00  | 32.81  | -22.90 | 9.29   | -10.35 | 8.40   | -17.68 | -100.00 | -100.00 | -100.00 | -100.00 | CHF |

  @from:cucumber
  Scenario: TC2 shipments, returns and credit notes count; drafts, reversals, vendor documents, repair/empties receipts and pallets do not
    ## One product, one month (2026-03). Counted: shipments 8 + 2 + 1 (sample, never invoiced), minus the 2 pcs returned by the customer
    ## (real customer-return flow) = quantity 9. Amount: invoice 10 x 10.00 = 100.00, minus the credit memo of the return (2 x 10.00 = 20.00),
    ## minus a price-correction credit note 5.00 = 75.00. Not counted: a draft shipment (created last), a reversed shipment, a vendor receipt,
    ## a draft, a voided, a reversed and a purchase invoice, C+ receipts with doc subtype SR (repair) and EC (empties correction), and a line
    ## with IsPackagingMaterial=Y on a shipment of the same product. A pallet product (own product category, which this scenario
    ## temporarily sets as the packing material category) is on order, shipment and invoice; an all-products run must contain no row for it.
    ## One shipment (A) and the invoice of the 8 + 2 pcs are closed (CL) after completion and still count.
    ## Test setup: the credit memo candidate of the return is processed with an explicit DateInvoiced, because the process would otherwise
    ## date the invoice by the real current date (the report month follows DateAcct).
    Given metasfresh has date and time 2026-03-10T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains M_Product_Categories:
      | Identifier   |
      | tc2PalletCat |
    And metasfresh contains M_Products:
      | Identifier |
      | tc2Prod    |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | tc2Pallet  | tc2PalletCat          |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | psycPLPO   | psycPS             | DE           | CHF           | false | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | psycPLVPO  | psycPLPO       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | psycPLV                | tc2Prod      | 10.00    | PCE      | psycTaxCat       |
      | psycPLV                | tc2Pallet    | 3.00     | PCE      | psycTaxCat       |
      | psycPLVPO              | tc2Prod      | 6.00     | PCE      | psycTaxCat       |
    And temporarily set sys config 'PackingMaterialProductCategoryID' to the M_Product_Category identified by 'tc2PalletCat'
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | invTc2Prod     | invTc2Prod_l1      | 2026-03-01   | warehouseStd   | tc2Prod      | 0       | 200      | PCE          |
      | invTc2Pallet   | invTc2Pallet_l1    | 2026-03-01   | warehouseStd   | tc2Pallet    | 0       | 200      | PCE          |
    And load M_Locator:
      | Identifier | M_Warehouse_ID | Value      |
      | tc2Locator | warehouseStd   | Hauptlager |
    And metasfresh contains C_BPartners without locations:
      | Identifier   | Name        | IsVendor | IsCustomer | M_PricingSystem_ID |
      | psycVendor   | PSYC vendor | Y        | N          | psycPS             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | psycVendorLoc   | psycVendor    | DE           | Y               | Y               |

    # Shipments 8 and 2, invoiced together 100.00
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_A | Y       | psycBP        | 2026-03-10  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_A_l1 | so_A | tc2Prod | 8 |
    And the order identified by so_A is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched_A | so_A_l1 | N | 8 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_A | D | false | false | 8 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched_A | ship_A |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship_A | 2026-03-10 |
    And the shipment identified by ship_A is completed
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_B | Y       | psycBP        | 2026-03-10  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_B_l1 | so_B | tc2Prod | 2 |
    And the order identified by so_B is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched_B | so_B_l1 | N | 2 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_B | D | false | false | 2 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched_B | ship_B |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship_B | 2026-03-10 |
    And the shipment identified by ship_B is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_main | psycBP | 2026-03-10 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_main_l1 | inv_main | tc2Prod | 10 PCE | 10.00 | psycTax19 |
    And the invoice identified by inv_main is completed
    # Price-correction credit note 5.00 (10 x 0.50), no return
    And create credit memo for C_Invoice
      | CreditMemo | C_Invoice_ID | CreditMemo.PriceEntered |
      | cm_price   | inv_main     | 0.50                    |
    And the invoice identified by cm_price is completed

    # The 2 pcs of shipment B are returned by the customer (real flow) and credited through the return's credit memo candidate 20.00
    And metasfresh has date and time 2026-03-12T10:00:00+01:00[Europe/Berlin]
    And generate customer return from shipment
      | M_InOut_ID | CustomerReturn_ID |
      | ship_B     | ret_B             |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ret_B      | 2026-03-12   |
    And the return inOut identified by ret_B is completed
    And after not more than 60s, credit memo candidates are found:
      | M_InOut_ID.Identifier | C_Invoice_Candidate_ID.Identifier | OPT.M_Product_ID.Identifier |
      | ret_B                 | ic_ret_B                          | tc2Prod                     |
    # DateInvoiced is given, because the process otherwise dates the invoice by the real current date (DateAcct decides the report month)
    And process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.DateInvoiced |
      | ic_ret_B                          | 2026-03-12       |

    # Sample shipment of 1 pc, never invoiced; a packaging-material line of the same product is added while it is still a draft
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_S | Y       | psycBP        | 2026-03-10  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_S_l1 | so_S | tc2Prod | 1 |
    And the order identified by so_S is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched_S | so_S_l1 | N | 1 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_S | D | false | false | 1 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched_S | ship_S |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship_S | 2026-03-10 |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | OPT.IsPackagingMaterial | MovementQty |
      | ship_S_pack               | ship_S                | tc2Prod                     | 7          | tc2Locator                  | PCE     | true                    | 7           |
    And the shipment identified by ship_S is completed

    # Excluded: a reversed shipment
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_R | Y       | psycBP        | 2026-03-10  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_R_l1 | so_R | tc2Prod | 4 |
    And the order identified by so_R is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched_R | so_R_l1 | N | 4 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_R | D | false | false | 4 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched_R | ship_R |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship_R | 2026-03-10 |
    And the shipment identified by ship_R is completed
    And the shipment identified by ship_R is reversed

    # Pallet product on order, shipment and invoice (own category = packing material category)
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_P | Y       | psycBP        | 2026-03-10  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_P_l1 | so_P | tc2Pallet | 3 |
    And the order identified by so_P is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched_P | so_P_l1 | N | 3 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_P | D | false | false | 3 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched_P | ship_P |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship_P | 2026-03-10 |
    And the shipment identified by ship_P is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_pallet | psycBP | 2026-03-10 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_pallet_l1 | inv_pallet | tc2Pallet | 3 PCE | 3.00 | psycTax19 |
    And the invoice identified by inv_pallet is completed

    # Excluded: C+ receipts that are no returns (service repair SR, empties correction EC) and a vendor receipt
    And metasfresh contains M_InOut:
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsSOTrx | DeliveryRule | DeliveryViaRule | FreightCostRule | M_Warehouse_ID.Identifier | MovementDate | MovementType | PriorityRule | OPT.DocBaseType | OPT.DocSubType |
      | recv_SR               | psycBP                   | psycBPLoc                         | true    | F            | S               | I               | warehouseStd              | 2026-03-10   | C+           | 5            | MMR             | SR             |
      | recv_EC               | psycBP                   | psycBPLoc                         | true    | F            | S               | I               | warehouseStd              | 2026-03-10   | C+           | 5            | MMR             | EC             |
      | recv_vendor           | psycVendor               | psycVendorLoc                     | false   | F            | S               | I               | warehouseStd              | 2026-03-10   | V+           | 5            | MMR             | MR             |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | QtyEntered | OPT.M_Locator_ID.Identifier | UomCode | MovementQty |
      | recv_SR_l1                | recv_SR               | tc2Prod                     | 11         | tc2Locator                  | PCE     | 11          |
      | recv_EC_l1                | recv_EC               | tc2Prod                     | 13         | tc2Locator                  | PCE     | 13          |
      | recv_vendor_l1            | recv_vendor           | tc2Prod                     | 17         | tc2Locator                  | PCE     | 17          |
    And the return inOut identified by recv_SR is completed
    And the return inOut identified by recv_EC is completed
    And the material receipt identified by recv_vendor is completed

    # Excluded invoices: reversed, draft, voided, purchase
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_rev | psycBP | 2026-03-10 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_rev_l1 | inv_rev | tc2Prod | 1 PCE | 50.00 | psycTax19 |
    And the invoice identified by inv_rev is completed
    And the invoice identified by inv_rev is reversed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_draft | psycBP | 2026-03-10 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_draft_l1 | inv_draft | tc2Prod | 1 PCE | 40.00 | psycTax19 |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_void | psycBP | 2026-03-10 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_void_l1 | inv_void | tc2Prod | 1 PCE | 60.00 | psycTax19 |
    And the invoice identified by inv_void is voided
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_purchase | psycVendor | 2026-03-10 | false | CHF | psycPLPO |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_purchase_l1 | inv_purchase | tc2Prod | 1 PCE | 30.00 | psycTax19 |
    And the invoice identified by inv_purchase is completed

    # One shipment (A) and the invoice of the 8 + 2 pcs are closed after completion
    And the shipment identified by ship_A is closed
    And the invoice identified by inv_main is closed

    # Excluded: a draft shipment, created last
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_D | Y       | psycBP        | 2026-03-10  | psycPS             | warehouseStd   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_D_l1 | so_D | tc2Prod | 6 |
    And the order identified by so_D is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sched_D | so_D_l1 | N | 6 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sched_D | D | false | false | 6 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sched_D | ship_D |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | ship_D | 2026-03-10 |

    And metasfresh has date and time 2026-10-27T10:00:00+01:00[Europe/Berlin]

    Then the product statistics year comparison report for org "-", year "yearN", product "tc2Prod" returns:
      | M_Product_ID | Qty_01 | Qty_02 | Qty_03 | Qty_04 | Qty_05 | Qty_06 | Qty_07 | Qty_08 | Qty_09 | Qty_10 | Qty_11 | Qty_12 | Qty_Total | Amt_01 | Amt_02 | Amt_03 | Amt_04 | Amt_05 | Amt_06 | Amt_07 | Amt_08 | Amt_09 | Amt_10 | Amt_11 | Amt_12 | Amt_Total | PrevQty_Total | PrevAmt_Total | Currency |
      | tc2Prod      |        |        | 9      |        |        |        |        |        |        |        |        |        | 9         |        |        | 75.00  |        |        |        |        |        |        |        |        |        | 75.00     |               |               | CHF      |

    And the product statistics year comparison report for org "-", year "yearN", product "-" contains no rows for products:
      | M_Product_ID |
      | tc2Pallet    |

  @from:cucumber
  Scenario: TC3 deviation edge cases and TC4 all-products run (product sold only in the previous year, order by value)
    ## Three products, shipments only (quantity), 2025 and 2026. Month A/B/C/D of tc3aEdge: Jan 2026 4 vs none -> 100.00; Feb 9 vs 9 -> blank;
    ## Mar none vs 5 -> -100.00; Apr none vs none -> blank. tc3bNoPrev has no previous-year sale at all (its previous-year cells stay empty although
    ## the previous C_Year exists, and the row is still returned). tc3cPrevOnly was sold only in 2025 (appears in the all-products run, -100.00).
    ## Order by value: the generated product value is <identifier>_<timestamp>, so the identifier prefix decides the sort order of the run-unique values:
    ## tc3a < tc3b < tc3c. The products are created in the reverse order (c, b, a), so M_Product_ID order is the opposite of value order and the
    ## step's relative-order check (expected rows are listed a, b, c) fails if the report sorted by M_Product_ID or in any other way than by value.
    And metasfresh contains M_Products:
      | Identifier |
      | tc3cPrevOnly |
      | tc3bNoPrev |
      | tc3aEdge |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | psycPLV | tc3cPrevOnly | 10.00 | PCE | psycTaxCat |
      | psycPLV | tc3bNoPrev | 10.00 | PCE | psycTaxCat |
      | psycPLV | tc3aEdge | 10.00 | PCE | psycTaxCat |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_tc3cPrevOnly | inv_tc3cPrevOnly_l1 | 2025-01-02 | warehouseStd | tc3cPrevOnly | 0 | 100 | PCE |
      | inv_tc3bNoPrev | inv_tc3bNoPrev_l1 | 2025-01-02 | warehouseStd | tc3bNoPrev | 0 | 100 | PCE |
      | inv_tc3aEdge | inv_tc3aEdge_l1 | 2025-01-02 | warehouseStd | tc3aEdge | 0 | 100 | PCE |
    And metasfresh has date and time 2025-09-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_e2502 | Y | psycBP | 2025-02-10 | psycPS | warehouseStd |
      | so_e2503 | Y | psycBP | 2025-03-05 | psycPS | warehouseStd |
      | so_p2506 | Y | psycBP | 2025-06-10 | psycPS | warehouseStd |
      | so_p2509 | Y | psycBP | 2025-09-10 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_e2502_l1 | so_e2502 | tc3aEdge | 9 |
      | so_e2503_l1 | so_e2503 | tc3aEdge | 5 |
      | so_p2506_l1 | so_p2506 | tc3cPrevOnly | 6 |
      | so_p2509_l1 | so_p2509 | tc3cPrevOnly | 2 |
    And the order identified by so_e2502 is completed
    And the order identified by so_e2503 is completed
    And the order identified by so_p2506 is completed
    And the order identified by so_p2509 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_e2502 | so_e2502_l1 | N | 9 |
      | sch_e2503 | so_e2503_l1 | N | 5 |
      | sch_p2506 | so_p2506_l1 | N | 6 |
      | sch_p2509 | so_p2509_l1 | N | 2 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_e2502 | D | false | false | 9 |
      | sch_e2503 | D | false | false | 5 |
      | sch_p2506 | D | false | false | 6 |
      | sch_p2509 | D | false | false | 2 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_e2502 | sh_e2502 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_e2503 | sh_e2503 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_p2506 | sh_p2506 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_p2509 | sh_p2509 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_e2502 | 2025-02-10 |
      | sh_e2503 | 2025-03-05 |
      | sh_p2506 | 2025-06-10 |
      | sh_p2509 | 2025-09-10 |
    And the shipment identified by sh_e2502 is completed
    And the shipment identified by sh_e2503 is completed
    And the shipment identified by sh_p2506 is completed
    And the shipment identified by sh_p2509 is completed
    And metasfresh has date and time 2026-02-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_e2601 | Y | psycBP | 2026-01-04 | psycPS | warehouseStd |
      | so_e2602 | Y | psycBP | 2026-02-10 | psycPS | warehouseStd |
      | so_n2602 | Y | psycBP | 2026-02-07 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_e2601_l1 | so_e2601 | tc3aEdge | 4 |
      | so_e2602_l1 | so_e2602 | tc3aEdge | 9 |
      | so_n2602_l1 | so_n2602 | tc3bNoPrev | 7 |
    And the order identified by so_e2601 is completed
    And the order identified by so_e2602 is completed
    And the order identified by so_n2602 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_e2601 | so_e2601_l1 | N | 4 |
      | sch_e2602 | so_e2602_l1 | N | 9 |
      | sch_n2602 | so_n2602_l1 | N | 7 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_e2601 | D | false | false | 4 |
      | sch_e2602 | D | false | false | 9 |
      | sch_n2602 | D | false | false | 7 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_e2601 | sh_e2601 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_e2602 | sh_e2602 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_n2602 | sh_n2602 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_e2601 | 2026-01-04 |
      | sh_e2602 | 2026-02-10 |
      | sh_n2602 | 2026-02-07 |
    And the shipment identified by sh_e2601 is completed
    And the shipment identified by sh_e2602 is completed
    And the shipment identified by sh_n2602 is completed

    # TC3: A 4 vs none = 100.00, B 9 vs 9 = blank, C none vs 5 = -100.00, D none vs none = blank
    Then the product statistics year comparison report for org "-", year "yearN", product "tc3aEdge" returns:
      | M_Product_ID | Qty_01 | Qty_02 | Qty_03 | Qty_04 | Qty_Total | Amt_Total | PrevQty_01 | PrevQty_02 | PrevQty_03 | PrevQty_Total | Dev_01 | Dev_02 | Dev_03 | Dev_04 |
      | tc3aEdge | 4 | 9 |  |  | 13 |  |  | 9 | 5 | 14 | 100.00 |  | -100.00 |  |
    # TC3: no previous-year sales: all previous-year cells empty, the row is returned; deviation 100.00 where there is a current quantity
    Then the product statistics year comparison report for org "-", year "yearN", product "tc3bNoPrev" returns:
      | M_Product_ID | Qty_02 | Qty_Total | PrevQty_01 | PrevQty_02 | PrevQty_03 | PrevQty_04 | PrevQty_05 | PrevQty_06 | PrevQty_07 | PrevQty_08 | PrevQty_09 | PrevQty_10 | PrevQty_11 | PrevQty_12 | PrevQty_Total | PrevAmt_Total | Dev_01 | Dev_02 |
      | tc3bNoPrev | 7 | 7 |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  | 100.00 |

    # TC4: all-products run: tc3cPrevOnly appears with empty current-year cells and -100.00 in each month with previous-year sales; rows ordered by value (a, b, c)
    Then the product statistics year comparison report for org "-", year "yearN", product "-" returns:
      | M_Product_ID | Qty_06 | Qty_09 | Qty_Total | PrevQty_06 | PrevQty_09 | PrevQty_Total | Dev_01 | Dev_06 | Dev_09 |
      | tc3aEdge |  |  | 13 |  |  | 14 | 100.00 |  |  |
      | tc3bNoPrev |  |  | 7 |  |  |  |  |  |  |
      | tc3cPrevOnly |  |  |  | 6 | 2 | 8 |  | -100.00 | -100.00 |

  @from:cucumber
  Scenario: TC8 shipped and invoiced in different months; invoiced-only and shipped-only products
    ## TC8: tc8Prod is shipped on 2026-11-30 and invoiced with accounting date 2026-12-02: the quantity counts in November, the amount in December.
    ## The invoice's DateAcct is the pinned clock date, so the clock is pinned to 2026-12-02 before the invoice is created.
    ## Invoiced but never shipped (rf4Invoiced): amount set, quantity blank. Only shipped, never invoiced (rf4Sample): quantity set, amount blank.
    And metasfresh contains M_Products:
      | Identifier |
      | tc8Prod |
      | rf4Invoiced |
      | rf4Sample |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | psycPLV | tc8Prod | 10.00 | PCE | psycTaxCat |
      | psycPLV | rf4Invoiced | 10.00 | PCE | psycTaxCat |
      | psycPLV | rf4Sample | 10.00 | PCE | psycTaxCat |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_tc8Prod | inv_tc8Prod_l1 | 2025-01-02 | warehouseStd | tc8Prod | 0 | 100 | PCE |
      | inv_rf4Sample | inv_rf4Sample_l1 | 2025-01-02 | warehouseStd | rf4Sample | 0 | 100 | PCE |
    And metasfresh has date and time 2026-03-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_s03 | Y | psycBP | 2026-03-15 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_s03_l1 | so_s03 | rf4Sample | 4 |
    And the order identified by so_s03 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_s03 | so_s03_l1 | N | 4 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_s03 | D | false | false | 4 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_s03 | sh_s03 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_s03 | 2026-03-15 |
    And the shipment identified by sh_s03 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_rf4 | psycBP | 2026-03-15 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_rf4_l1 | inv_rf4 | rf4Invoiced | 3 PCE | 10.00 | psycTax19 |
    And the invoice identified by inv_rf4 is completed
    And metasfresh has date and time 2026-11-30T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_t11 | Y | psycBP | 2026-11-30 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_t11_l1 | so_t11 | tc8Prod | 5 |
    And the order identified by so_t11 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_t11 | so_t11_l1 | N | 5 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_t11 | D | false | false | 5 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_t11 | sh_t11 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_t11 | 2026-11-30 |
    And the shipment identified by sh_t11 is completed
    And metasfresh has date and time 2026-12-02T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_t12 | psycBP | 2026-12-02 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_t12_l1 | inv_t12 | tc8Prod | 5 PCE | 10.00 | psycTax19 |
    And the invoice identified by inv_t12 is completed

    Then the product statistics year comparison report for org "-", year "yearN", product "tc8Prod" returns:
      | M_Product_ID | Qty_11 | Qty_12 | Qty_Total | Amt_11 | Amt_12 | Amt_Total |
      | tc8Prod | 5 |  | 5 |  | 50.00 | 50.00 |
    # Invoiced but never shipped: amount 3 x 10.00, quantity blank
    And the product statistics year comparison report for org "-", year "yearN", product "rf4Invoiced" returns:
      | M_Product_ID | Qty_03 | Qty_Total | Amt_03 | Amt_Total |
      | rf4Invoiced |  |  | 30.00 | 30.00 |
    # Only shipped (sample), never invoiced: quantity 4, amount blank
    And the product statistics year comparison report for org "-", year "yearN", product "rf4Sample" returns:
      | M_Product_ID | Qty_03 | Qty_Total | Amt_03 | Amt_Total |
      | rf4Sample | 4 | 4 |  |  |

  @from:cucumber
  Scenario: A year without a previous C_Year in its calendar has an empty previous year, although the product has documents in the year before
    ## The product has a shipment and an invoice in 2025 (they exist in the org's calendar) and in 2026. A separate test calendar holds ONLY 2026, so the
    ## chosen C_Year has no previous year: PrevQty_*/PrevAmt_* are NULL and Dev_* is 100.00 where the current quantity is > 0 (2025 is NOT taken by date).
    ## Contrast: the same product reported for the Background calendar (2025 and 2026) shows the 2025 figures as previous year.
    And metasfresh contains C_Calendar "PSYC_Calendar_single" with fiscal years:
      | C_Year_ID  | FiscalYear |
      | yearSingle | 2026       |
    And metasfresh contains M_Products:
      | Identifier |
      | tcSingle |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | psycPLV | tcSingle | 10.00 | PCE | psycTaxCat |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_tcSingle | inv_tcSingle_l1 | 2025-01-02 | warehouseStd | tcSingle | 0 | 100 | PCE |
    And metasfresh has date and time 2025-06-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_g2506 | Y | psycBP | 2025-06-15 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_g2506_l1 | so_g2506 | tcSingle | 8 |
    And the order identified by so_g2506 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_g2506 | so_g2506_l1 | N | 8 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_g2506 | D | false | false | 8 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_g2506 | sh_g2506 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_g2506 | 2025-06-15 |
    And the shipment identified by sh_g2506 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_g2506 | psycBP | 2025-06-15 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_g2506_l1 | inv_g2506 | tcSingle | 8 PCE | 10.00 | psycTax19 |
    And the invoice identified by inv_g2506 is completed
    And metasfresh has date and time 2026-03-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_g2603 | Y | psycBP | 2026-03-15 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_g2603_l1 | so_g2603 | tcSingle | 6 |
    And the order identified by so_g2603 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_g2603 | so_g2603_l1 | N | 6 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_g2603 | D | false | false | 6 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_g2603 | sh_g2603 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_g2603 | 2026-03-15 |
    And the shipment identified by sh_g2603 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_g2603 | psycBP | 2026-03-15 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_g2603_l1 | inv_g2603 | tcSingle | 6 PCE | 10.00 | psycTax19 |
    And the invoice identified by inv_g2603 is completed
    And metasfresh has date and time 2026-08-15T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_g2608 | Y | psycBP | 2026-08-15 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_g2608_l1 | so_g2608 | tcSingle | 2 |
    And the order identified by so_g2608 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_g2608 | so_g2608_l1 | N | 2 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_g2608 | D | false | false | 2 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_g2608 | sh_g2608 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_g2608 | 2026-08-15 |
    And the shipment identified by sh_g2608 is completed
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID | M_PriceList_ID |
      | inv_g2608 | psycBP | 2026-08-15 | true | CHF | psycPL |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced | Price | C_Tax_ID$set |
      | inv_g2608_l1 | inv_g2608 | tcSingle | 2 PCE | 10.00 | psycTax19 |
    And the invoice identified by inv_g2608 is completed

    Then the product statistics year comparison report for org "-", year "yearSingle", product "tcSingle" returns:
      | M_Product_ID | Qty_03 | Qty_08 | Qty_Total | Amt_03 | Amt_08 | Amt_Total | PrevQty_01 | PrevQty_02 | PrevQty_03 | PrevQty_04 | PrevQty_05 | PrevQty_06 | PrevQty_07 | PrevQty_08 | PrevQty_09 | PrevQty_10 | PrevQty_11 | PrevQty_12 | PrevQty_Total | PrevAmt_03 | PrevAmt_06 | PrevAmt_08 | PrevAmt_Total | Dev_03 | Dev_06 | Dev_08 |
      | tcSingle | 6 | 2 | 8 | 60.00 | 20.00 | 80.00 |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |  | 100.00 |  | 100.00 |
    # Same product, calendar with the previous year: 2025 is the previous year
    And the product statistics year comparison report for org "-", year "yearN", product "tcSingle" returns:
      | M_Product_ID | Qty_03 | Qty_08 | Qty_Total | Amt_03 | Amt_08 | Amt_Total | PrevQty_06 | PrevQty_Total | PrevAmt_06 | PrevAmt_Total | Dev_03 | Dev_06 | Dev_08 |
      | tcSingle | 6 | 2 | 8 | 60.00 | 20.00 | 80.00 | 8 | 8 | 80.00 | 80.00 | 100.00 | -100.00 | 100.00 |

  @from:cucumber
  Scenario: Month boundary at 23:59, shipment and larger return in one month, negative previous-year month
    ## rf3Cur (2026): ship 5 in January; ship 2 in February (movement date = last day, 2026-02-28); the 5 are returned by the customer in February
    ## (customer return of the January shipment) -> February = 2 - 5 = -3, January = 5. The return is created while the clock is pinned to
    ## 2026-02-28T23:59 Europe/Berlin and takes the clock as its MovementDate (the 'update M_InOut' step knows dates only). Note: MovementDate is a
    ## date column of the application, the save truncates the time (stored 2026-02-28 00:00:00, checked in the DB), so a 23:59 value cannot be
    ## produced through the application; the boundary covered here is: documents dated the LAST day of the month count in that month, not in March.
    ## Month assignment uses the date part (::date) of MovementDate / DateAcct, so any time part of a date-time value is irrelevant: last-day dates count in that month.
    ## rf3Prev: the same construction in 2025 (January 5, February 2 - 5 = -3 = previous-year month), and 6 shipped in February 2026 -> deviation
    ## (6 / -3 - 1) * 100 = -300.00 (computed as is, AC9). January: previous 5, current none -> -100.00.
    And metasfresh contains M_Products:
      | Identifier |
      | rf3Cur |
      | rf3Prev |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | psycPLV | rf3Cur | 10.00 | PCE | psycTaxCat |
      | psycPLV | rf3Prev | 10.00 | PCE | psycTaxCat |
    And metasfresh contains single line completed inventories
      | M_Inventory_ID | M_InventoryLine_ID | MovementDate | M_Warehouse_ID | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_rf3Cur | inv_rf3Cur_l1 | 2025-01-02 | warehouseStd | rf3Cur | 0 | 100 | PCE |
      | inv_rf3Prev | inv_rf3Prev_l1 | 2025-01-02 | warehouseStd | rf3Prev | 0 | 100 | PCE |
    And metasfresh has date and time 2025-02-20T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_p2501 | Y | psycBP | 2025-01-10 | psycPS | warehouseStd |
      | so_p2502 | Y | psycBP | 2025-02-10 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_p2501_l1 | so_p2501 | rf3Prev | 5 |
      | so_p2502_l1 | so_p2502 | rf3Prev | 2 |
    And the order identified by so_p2501 is completed
    And the order identified by so_p2502 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_p2501 | so_p2501_l1 | N | 5 |
      | sch_p2502 | so_p2502_l1 | N | 2 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_p2501 | D | false | false | 5 |
      | sch_p2502 | D | false | false | 2 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_p2501 | sh_p2501 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_p2502 | sh_p2502 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_p2501 | 2025-01-10 |
      | sh_p2502 | 2025-02-10 |
    And the shipment identified by sh_p2501 is completed
    And the shipment identified by sh_p2502 is completed
    # rf3Prev: the 5 of January 2025 are returned in February 2025 (movement date = clock 2025-02-20)
    And generate customer return from shipment
      | M_InOut_ID | CustomerReturn_ID |
      | sh_p2501 | ret_p2502 |
    And the return inOut identified by ret_p2502 is completed
    And metasfresh has date and time 2026-02-20T10:00:00+01:00[Europe/Berlin]
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_PricingSystem_ID | M_Warehouse_ID |
      | so_c2601 | Y | psycBP | 2026-01-10 | psycPS | warehouseStd |
      | so_c2602 | Y | psycBP | 2026-02-28 | psycPS | warehouseStd |
      | so_p2602 | Y | psycBP | 2026-02-10 | psycPS | warehouseStd |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_c2601_l1 | so_c2601 | rf3Cur | 5 |
      | so_c2602_l1 | so_c2602 | rf3Cur | 2 |
      | so_p2602_l1 | so_p2602 | rf3Prev | 6 |
    And the order identified by so_c2601 is completed
    And the order identified by so_c2602 is completed
    And the order identified by so_p2602 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute | QtyToDeliver |
      | sch_c2601 | so_c2601_l1 | N | 5 |
      | sch_c2602 | so_c2602_l1 | N | 2 |
      | sch_p2602 | so_p2602_l1 | N | 6 |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | sch_c2601 | D | false | false | 5 |
      | sch_c2602 | D | false | false | 2 |
      | sch_p2602 | D | false | false | 6 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_c2601 | sh_c2601 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_c2602 | sh_c2602 |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | sch_p2602 | sh_p2602 |
    And update M_InOut:
      | M_InOut_ID | MovementDate |
      | sh_c2601 | 2026-01-10 |
      | sh_c2602 | 2026-02-28 |
      | sh_p2602 | 2026-02-10 |
    And the shipment identified by sh_c2601 is completed
    And the shipment identified by sh_c2602 is completed
    And the shipment identified by sh_p2602 is completed
    # Clock = 23:59 local on the last day of February 2026: the return's MovementDate
    And metasfresh has date and time 2026-02-28T23:59:00+01:00[Europe/Berlin]
    And generate customer return from shipment
      | M_InOut_ID | CustomerReturn_ID |
      | sh_c2601 | ret_c2602 |
    And the return inOut identified by ret_c2602 is completed

    Then the product statistics year comparison report for org "-", year "yearN", product "rf3Cur" returns:
      | M_Product_ID | Qty_01 | Qty_02 | Qty_03 | Qty_Total | PrevQty_Total |
      | rf3Cur | 5 | -3 |  | 2 |  |
    # Negative previous-year month: previous -3, current 6 -> -300.00
    And the product statistics year comparison report for org "-", year "yearN", product "rf3Prev" returns:
      | M_Product_ID | Qty_01 | Qty_02 | Qty_Total | PrevQty_01 | PrevQty_02 | PrevQty_Total | Dev_01 | Dev_02 |
      | rf3Prev |  | 6 | 6 | 5 | -3 | 2 | -100.00 | -300.00 |
