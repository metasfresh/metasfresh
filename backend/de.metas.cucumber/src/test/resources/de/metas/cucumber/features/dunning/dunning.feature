@from:cucumber
@ghActions:run_on_executor4
Feature: Invoice Dunning Test

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_sales   | ps_1               | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_sales  | pl_sales       |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | pp_2       | plv_sales              | product      | 10.0     | PCE      |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |

  @from:cucumber
  Scenario: Invoice Dunning Test
    And metasfresh contains C_Dunning:
      | Identifier        |
      | dunning_S0471_200 |
    And metasfresh contains C_DunningLevel:
      | Identifier             | C_Dunning_ID      | DaysAfterDue |
      | dunningLevel_S0471_200 | dunning_S0471_200 | 0            |
    And update C_BPartner:
      | Identifier | C_Dunning_ID      |
      | customer   | dunning_S0471_200 |
    And metasfresh contains C_Invoice:
      | Identifier    | C_BPartner_ID | PaymentRule | C_DocTypeTarget_ID.Name | DateInvoiced | IsSOTrx | C_Currency.ISO_Code |
      | salesInvoice1 | customer      | B           | Ausgangsrechnung        | 2021-04-08   | true    | EUR                 |
    And metasfresh contains C_InvoiceLines
      | C_Invoice_ID  | M_Product_ID | QtyInvoiced |
      | salesInvoice1 | product      | 1 PCE       |
    And the invoice identified by salesInvoice1 is completed

    And invoke "C_Dunning_Candidate_Create" process:
      | C_DunningLevel_ID      | DunningDate |
      | dunningLevel_S0471_200 | 2022-09-29  |
    And after not more than 60s, locate C_Dunning_Candidate:
      | Identifier           | TableName | Record_ID     |
      | dunningCandInvoice_1 | C_Invoice | salesInvoice1 |
    And invoke "C_Dunning_Candidate_Process" process:
      | Identifier   | C_Dunning_Candidate_ID | AutoProcess |
      | dunningDoc_1 | dunningCandInvoice_1   | false       |
    And validate C_DunningDoc:
      | C_DunningDoc_ID | C_BPartner_ID | C_DunningLevel_ID      | Processed |
      | dunningDoc_1    | customer      | dunningLevel_S0471_200 | N         |
