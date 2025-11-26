@from:cucumber
@ghActions:run_on_executor4
Feature: Invoices with pay schedules created from order pay schedules

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2025-10-31T13:30:13+01:00[Europe/Berlin]
    And load M_Shipper:
      | Identifier  | Name |
      | shipper_DHL | Dhl  |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_purchase | ps_1               | CH           | CHF           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_purchase | pl_purchase    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | plv_purchase           | product      | 9.98     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps_1               |
      | shipper    | N        | N          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation  | vendor        | CH           | Y               | Y               |
      | shipperLocation | shipper       | CH           | Y               | Y               |
    And metasfresh contains C_BP_BankAccount
      | Identifier         | C_Currency_ID | C_BPartner_ID | IBAN                       |
      | vendor_CHF_account | CHF           | vendor        | CH93 0076 2011 6238 5295 7 |
    And metasfresh contains C_Tax
      | Identifier | Rate | C_Country_ID | To_Country_ID |
      | de_ch_tax  | 2.5  | DE           | CH            |
      | ch_ch_tax  | 2.5  | CH           | CH            |
    And metasfresh contains C_PaymentTerm
      | Identifier   |
      | paymentTerm1 |
    And metasfresh contains C_PaymentTerm_Break
      | Identifier          | C_PaymentTerm_ID | Percent | OffsetDays | ReferenceDateType | SeqNo |
      | paymentTerm1_break1 | paymentTerm1     | 25      | 1          | OD                | 10    |
      | paymentTerm1_break2 | paymentTerm1     | 75      | 0          | LC                | 20    |
    And validate C_PaymentTerm:
      | Identifier   | IsComplex | IsValid |
      | paymentTerm1 | Y         | Y       |


  @from:cucumber
  Scenario: TEST
    When simple completed order with one line
      | C_Order_ID | C_BPartner_ID | DateOrdered | DocBaseType | M_Warehouse_ID | C_PaymentTerm_ID | InvoiceRule | C_OrderLine_ID | M_Product_ID | QtyEntered |
      | po1        | vendor        | 2025-10-09  | POO         | wh             | paymentTerm1     | I           | po1_l1         | product      | 10         |
    Then the order identified by po1 has following pay schedules
      | Identifier        | C_PaymentTerm_Break_ID | DueDate    | DueAmt | Status |
      | orderPaySchedule1 | paymentTerm1_break1    | 2025-10-10 | 25.58  | WP     |
      | orderPaySchedule2 | paymentTerm1_break2    | 9999-01-01 | 76.72  | PR     |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID | QtyToInvoice |
      | ic1                    | po1_l1         | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic1                    |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID |
      | ic1                    | invoice1     |
    Then validate created invoices
      | C_Invoice_ID | C_BPartner_ID | DocStatus |
      | invoice1     | vendor        | CO        |
    And the invoice identified by invoice1 has following pay schedules
      | C_OrderPaySchedule_ID | DueDate    | DueAmt    | IsValid |
      | orderPaySchedule1     | 2025-10-10 | 25.58 CHF | Y       |
      | orderPaySchedule2     | 9999-01-01 | 76.72 CHF | Y       |
