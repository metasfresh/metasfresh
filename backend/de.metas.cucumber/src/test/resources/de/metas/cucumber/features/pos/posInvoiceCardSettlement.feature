@from:cucumber
@allure.label.epic:E0500_Point_of_Sale_POS
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor7
Feature: POS invoice card settlement

  # Data shape: one sales invoice (ARI) mirroring the reference invoice used for the card-settlement test cases:
  # EUR, prices not tax-included, 2 lines at 7 % tax, grand total = open amount 16,07, payment term with value 1000,
  # no allocations. The reference probe returns only the total, so the line split 10,00 + 5,02 (net, tax 1,05) is
  # DERIVED from 16,07 at 7 %; it is not taken from the reference system.
  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-09-24T08:00:00+02:00[Europe/Berlin]
    And temporarily set sys config boolean value true for sys config 'de.metas.report.jasper.IsMockReportService'
    And metasfresh contains C_TaxCategory
      | Identifier    |
      | taxCategory07 |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax07      | taxCategory07    | 7    | DE                       | DE                        | false           |
    And metasfresh contains M_Products:
      | Identifier | X12DE355 |
      | product1   | PCE      |
      | product2   | PCE      |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | priceListVersion       | product1     | 10.00    | PCE      | taxCategory07    |
      | priceListVersion       | product2     | 5.02     | PCE      | taxCategory07    |
    And metasfresh contains organization bank accounts
      | Identifier | C_Currency_ID | IsCashBank |
      | cashbook   | EUR           | Y          |
    And metasfresh contains M_Warehouse:
      | Identifier |
      | warehouse  |
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.M_PricingSystem_ID |
      | walkInCustomer  |                        |
      | invoiceCustomer | pricingSystem          |
    And metasfresh contains C_POS:
      | Identifier | C_BP_BankAccount_ID | M_PricingSystem_ID | M_PriceList_ID | C_BPartner_ID  | C_BPartner_Location_ID | M_Warehouse_ID |
      | till       | cashbook            | pricingSystem      | priceList      | walkInCustomer | walkInCustomer         | warehouse      |
    And the cash journal of POS terminal till is opened with 100 by metasfresh
    And metasfresh contains C_PaymentTerm
      | Identifier | Value | Name      | NetDays |
      | term1000   | 1000  | Term 1000 | 0       |
    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID   | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency_ID | C_PaymentTerm_ID |
      | invoice1   | invoiceCustomer | 2026-09-24   | Spot                     | true    | EUR           | term1000         |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | invoiceL1  | invoice1     | product1     | 1 PCE       |
      | invoiceL2  | invoice1     | product2     | 1 PCE       |
    And the invoice identified by invoice1 is completed

  # ##########################################################################
  # Proves the cucumber SumUp API stub works end to end and that the settlement reference of a card checkout
  # survives a DB reload of the SumUp transaction.
  @from:cucumber
  @allure.label.epic:E0500_Point_of_Sale_POS
  @allure.label.feature:F18030_POS_Checkout
  @Id:POS_CARD_STUB_SMOKE
  Scenario: The SumUp API stub answers a settlement checkout and the transaction keeps its settlement reference after a reload
    Given POS terminal till is configured with SumUp and a default card reader
    And card settlement settlement1 exists for invoice invoice1 at POS terminal till by cashier metasfresh

    When a SumUp card checkout is sent for card settlement settlement1
    Then the SumUp stub received 1 checkout with amount 16.07 on reader reader-till
    And the SumUp transaction of card settlement settlement1 has status PENDING

    When SumUp API stub reports transaction of card settlement settlement1 as SUCCESSFUL
    And the SumUp transaction of card settlement settlement1 is updated from remote
    Then the SumUp transaction of card settlement settlement1 has status SUCCESSFUL
