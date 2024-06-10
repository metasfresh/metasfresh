@from:cucumber
@topic:commissionContracts
@ghActions:run_on_executor3
Feature: Trade margin commission contract
  As a user
  I have a trade margin contract, when order is processed commission points and commission deed are computed accordingly

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2021-12-02T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  @topic:commissionContracts
  @Id:S0150_190
  Scenario: Margin commission combined having one sales rep and one customer
    Given taxCategory 'Normal' is updated to work with all productTypes
    And metasfresh contains M_Products:
      | Identifier          | Name                | ProductType | OPT.X12DE355 |
      | commission_product  | commission_product  | S           | PTS          |
      | transaction_product | transaction_product |             | PCE          |
    And metasfresh contains M_PricingSystems
      | Identifier     | Name           | Value          | OPT.IsActive |
      | customer_so_ps | customer_so_ps | customer_so_ps | true         |
      | salesRep_so_ps | salesRep_so_ps | salesRep_so_ps | true         |
      | salesRep_po_ps | salesRep_po_ps | salesRep_po_ps | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name         | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | customer_so_pl | customer_so_ps                | DE                        | EUR                 | price_list_1 | true  | false         | 2              | true         |
      | salesRep_so_pl | salesRep_so_ps                | DE                        | EUR                 | price_list_2 | true  | false         | 2              | true         |
      | salesRep_po_pl | salesRep_po_ps                | DE                        | EUR                 | price_list_3 | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name            | ValidFrom  |
      | customer_so_plv | customer_so_pl            | customer_so_plv | 2021-11-01 |
      | salesRep_so_plv | salesRep_so_pl            | salesRep_so_plv | 2021-11-01 |
      | salesRep_po_plv | salesRep_po_pl            | salesRep_po_plv | 2021-11-01 |
    And metasfresh contains M_ProductPrices
      | Identifier                | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | salesRep_cp_po_price      | salesRep_po_plv                   | commission_product      | 1.0      | PTS               | Normal                        |
      | customer_product_so_price | customer_so_plv                   | transaction_product     | 25.0     | PCE               | Normal                        |
      | salesRep_product_so_price | salesRep_so_plv                   | transaction_product     | 15.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier      | OPT.C_BPartner_Location_ID.Identifier | Name            | M_PricingSystem_ID.Identifier | OPT.PO_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer | OPT.IsSalesRep | OPT.C_PaymentTerm_ID | OPT.C_BPartner_SalesRep_ID.Identifier | OPT.CompanyName     | OPT.GLN       |
      | margin_salesRep | margin_salesRep_location              | margin_salesRep | salesRep_so_ps                | salesRep_po_ps                     | Y            | Y              | Y              | 1000009              |                                       | margin_salesRep cmp |               |
      | margin_customer | margin_customer_location              | margin_customer | customer_so_ps                |                                    | N            | Y              | N              | 1000009              | margin_salesRep                       | margin_customer cmp | 1234567891237 |
    And metasfresh contains C_Customer_Trade_Margin:
      | C_Customer_Trade_Margin_ID.Identifier | Name     | Commission_Product_ID.Identifier | PointsPrecision |
      | marginSettings_1                      | margin_1 | commission_product               | 2               |
    And metasfresh contains C_Customer_Trade_Margin_Line:
      | C_Customer_Trade_Margin_Line_ID.Identifier | C_Customer_Trade_Margin_ID.Identifier | SeqNo | Margin |
      | marginSettingsLine_1                       | marginSettings_1                      | 10    | 50     |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier         | Name        | Type_Conditions  | OPT.C_Customer_Trade_Margin_ID.Identifier |
      | marginConditions_1 | margin-test | MarginCommission | marginSettings_1                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier       | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | marginContract_1 | marginConditions_1                  | margin_salesRep             | 2021-11-01 | 2022-10-30 | commission_product          |
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": "2522",
    "externalLineId": "111",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567891237",
        "bpartnerLocationIdentifier": "gln-1234567891237"
    },
    "dateRequired": "2021-12-02",
    "dateOrdered": "2021-11-20",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-transaction_product",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "po_ref_mock",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "2522",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |
    And locate invoice candidates for invoice: invoice_1
      | C_Invoice_Candidate_ID.Identifier | M_Product_ID.Identifier |
      | so_invoice_candidate              | transaction_product     |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtInvoiced |
      | so_invoice_candidate              | margin_customer             | transaction_product     | 20                 |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx | OPT.NetAmtInvoiced | OPT.AD_InputDataSource_ID.InternalName |
      | so_invoice_candidate              | margin_customer                 | transaction_product         | 0                   | true        | 20                 | Shopware                               |

    And validate created commission instance
      | C_Commission_Instance_ID.Identifier | C_Order_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_Order_ID.Identifier | PointsBase_Forecasted | PointsBase_Invoiceable | PointsBase_Invoiced |
      | commissionInstance_1                | order_1               | margin_customer             | transaction_product           | 0                     | 0                      | 20                  |
    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_Customer_Trade_Margin_Line_ID.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | commissionShare_1                | margin_salesRep                   | metasfresh                     | marginContract_1              | commission_product               | 0              | marginSettingsLine_1                           | false   | false        | 0                    | 0                     | 5.00               | 5.00               | 0                 |
    And validate commission fact for commissionShare_1
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | settlement_1                                     | 5.00             | TO_SETTLE             |
      |                                                  | 5.00             | INVOICED              |
      |                                                  | -5.00            | INVOICEABLE           |
      |                                                  | 5.00             | INVOICEABLE           |
      |                                                  | -5.00            | FORECASTED            |
      |                                                  | 5.00             | FORECASTED            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx |
      | settlement_1                      | margin_salesRep                 | commission_product          | 5                   | false       |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | settlement_1                      |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceSettled_1        | settlement_1                      |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx | OPT.NetAmtInvoiced |
      | settlement_1                      | margin_salesRep                 | commission_product          | 0                   | false       | 5                  |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.DocSubType |
      | invoiceSettled_1        | margin_salesRep          | margin_salesRep_location          | 10 Tage 1 % | true      | CO        | CA             |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLineSettled_1        | invoiceSettled_1        | commission_product      | 5.00        | true      |
    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_Customer_Trade_Margin_Line_ID.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | commissionShare_1                | margin_salesRep                   | metasfresh                     | marginContract_1              | commission_product               | 0              | marginSettingsLine_1                           | false   | false        | 0                    | 0                     | 5.00               | 0                  | 5.00              |
    And validate commission fact for commissionShare_1
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | settlement_1                                     | 5.00             | SETTLED               |
      | settlement_1                                     | -5.00            | TO_SETTLE             |
      | settlement_1                                     | 5.00             | TO_SETTLE             |
      |                                                  | 5.00             | INVOICED              |
      |                                                  | -5.00            | INVOICEABLE           |
      |                                                  | 5.00             | INVOICEABLE           |
      |                                                  | -5.00            | FORECASTED            |
      |                                                  | 5.00             | FORECASTED            |
