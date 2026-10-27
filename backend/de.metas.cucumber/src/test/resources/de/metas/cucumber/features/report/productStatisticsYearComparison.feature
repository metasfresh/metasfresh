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
