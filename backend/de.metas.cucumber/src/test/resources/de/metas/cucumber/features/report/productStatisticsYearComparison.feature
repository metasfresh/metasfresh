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
