@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00703_Invoice_Rule
@F00703
@ghActions:run_on_executor5
Feature: InvoiceRule Manual
## F00703: Invoice Rule — Manual (me03#28882)
## TC1: order with InvoiceRule=Manual, default run (IsInvoiceManualRule=N) → candidate NOT invoiced
## TC2: order with InvoiceRule=Manual, IsInvoiceManualRule=Y run          → candidate IS invoiced
## TC3: order with InvoiceRule=Immediate (regression guard)               → candidate IS invoiced

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00703_Invoice_Rule
  @F00703
  Scenario: TC1 - InvoiceRule=Manual, default run (IsInvoiceManualRule=N) => no invoice created
    Given metasfresh has date and time 2024-06-01T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                      |
      | p_1        | ir_manual_tc1_product     |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                         | Value                         | OPT.IsActive |
      | ps_1       | ir_manual_tc1_pricingsystem  | ir_manual_tc1_pricingsys_val  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | ir_manual_tc1_pricelist | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | ir_manual_tc1_pricelist_v1 | 2024-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | ir_manual_tc1_bpartner | N            | Y              | ps_1                          | M               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456781001 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference        |
      | o_1        | true    | endcustomer_1            | 2024-06-01  | ir_manual_tc1_ref      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 10           |
    Then process invoice candidates and verify C_Invoice_Candidate is not processed after 5s
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00703_Invoice_Rule
  @F00703
  Scenario: TC2 - InvoiceRule=Manual, IsInvoiceManualRule=Y => invoice IS created
    Given metasfresh has date and time 2024-06-01T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                      |
      | p_1        | ir_manual_tc2_product     |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                         | Value                         | OPT.IsActive |
      | ps_1       | ir_manual_tc2_pricingsystem  | ir_manual_tc2_pricingsys_val  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | ir_manual_tc2_pricelist | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | ir_manual_tc2_pricelist_v1 | 2024-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | ir_manual_tc2_bpartner | N            | Y              | ps_1                          | M               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456782001 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference        |
      | o_1        | true    | endcustomer_1            | 2024-06-01  | ir_manual_tc2_ref      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier | OPT.IsInvoiceManualRule |
      | ic_1                              | Y                       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | processed | DocStatus |
      | invoice_1               | endcustomer_1            | l_1                               | ir_manual_tc2_ref | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | p_1                     | 10          | true      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00703_Invoice_Rule
  @F00703
  Scenario: TC3 - InvoiceRule=Immediate (regression guard) => invoice IS created with default run
    Given metasfresh has date and time 2024-06-01T10:00:00+02:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                      |
      | p_1        | ir_manual_tc3_product     |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                         | Value                         | OPT.IsActive |
      | ps_1       | ir_manual_tc3_pricingsystem  | ir_manual_tc3_pricingsys_val  | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                    | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | ir_manual_tc3_pricelist | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                       | ValidFrom  |
      | plv_1      | pl_1                      | ir_manual_tc3_pricelist_v1 | 2024-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | ir_manual_tc3_bpartner | N            | Y              | ps_1                          | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456783001 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference        |
      | o_1        | true    | endcustomer_1            | 2024-06-01  | ir_manual_tc3_ref      |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic_1                              | ol_1                      | 10           |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic_1                              | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference   | processed | DocStatus |
      | invoice_1               | endcustomer_1            | l_1                               | ir_manual_tc3_ref | true      | CO        |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1               | invoice_1               | p_1                     | 10          | true      |
