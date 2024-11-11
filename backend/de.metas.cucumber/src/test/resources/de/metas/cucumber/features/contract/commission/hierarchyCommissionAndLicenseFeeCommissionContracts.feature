@from:cucumber
@topic:commissionContracts
@ghActions:run_on_executor3
Feature: Hierarchy commission and license fee commission combined

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2021-12-10T08:00:00+01:00[Europe/Berlin]
    And taxCategory 'Normal' is updated to work with all productTypes
    And metasfresh contains M_Products:
      | Identifier          | Name                           | ProductType | OPT.X12DE355 | Value |
      | commission_product  | commission_product_16112022_1  | S           | PTS          |       |
      | transaction_product | transaction_product_16112022_1 |             | PCE          |       |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                               | Value                              | OPT.IsActive |
      | ps_1       | salesRep_pricing_system_16112022_1 | salesRep_pricing_system_16112022_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                     | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_so      | ps_1                          | DE                        | EUR                 | price_list_so_16112022_1 | true  | false         | 2              | true         |
      | pl_po      | ps_1                          | DE                        | EUR                 | price_list_po_16112022_1 | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                            | ValidFrom  |
      | plv_so     | pl_so                     | salesOrder-PLV_72_16112022_1    | 2021-04-01 |
      | plv_po     | pl_po                     | purchaseOrder-PLV_72_16112022_1 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_2       | plv_so                            | commission_product      | 1.0      | PTS               | Normal                        |
      | pp_3       | plv_po                            | commission_product      | 1.0      | PTS               | Normal                        |
      | pp_4       | plv_so                            | transaction_product     | 10.0     | PCE               | Normal                        |
    And metasfresh contains I_C_LicenseFeeSettings:
      | C_LicenseFeeSettings_ID.Identifier | Name         | Commission_Product_ID.Identifier | PointsPrecision |
      | licenseFeeSettings_1               | licenseFee_1 | commission_product               | 2               |
    And metasfresh contains I_C_LicenseFeeSettingsLine:
      | C_LicenseFeeSettingsLine_ID.Identifier | C_LicenseFeeSettings_ID.Identifier | SeqNo | PercentOfBasePoints |
      | licenseFeeSettingsLine_1               | licenseFeeSettings_1               | 10    | 5                   |

  @from:cucumber
  @topic:commissionContracts
  @Id:S0150_170
  Scenario: Hierarchy commission and license fee commission combined having two sales rep in hierarchy
    And metasfresh contains C_HierarchyCommissionSettings:
      | C_HierarchyCommissionSettings_ID.Identifier | Name        | Commission_Product_ID.Identifier | IsSubtractLowerLevelCommissionFromBase |
      | hierarchySettings_1                         | hierarchy_1 | commission_product               | Y                                      |
    And metasfresh contains C_CommissionSettingsLines:
      | C_CommissionSettingsLine_ID.Identifier | C_HierarchyCommissionSettings_ID.Identifier | SeqNo | PercentOfBasePoints |
      | hierarchySettingsLine_1                | hierarchySettings_1                         | 10    | 10                  |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier             | Name            | Type_Conditions | OPT.C_HierarchyCommissionSettings_ID.Identifier | OPT.C_LicenseFeeSettings_ID.Identifier |
      | hierarchyConditions_1  | hierarchy-test  | Commission      | hierarchySettings_1                             |                                        |
      | licenseFeeConditions_1 | licenseFee-test | LicenseFee      |                                                 | licenseFeeSettings_1                   |

    And metasfresh contains C_BPartners:
      | Identifier     | OPT.C_BPartner_Location_ID.Identifier | Name           | M_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer | OPT.IsSalesRep | OPT.C_PaymentTerm_ID | OPT.C_BPartner_SalesRep_ID.Identifier | OPT.CompanyName    | OPT.GLN       |
      | super_salesRep | super_salesRep_location               | super_salesRep | ps_1                          | Y            | Y              | Y              | 1000009              |                                       | super_salesRep cmp |               |
      | salesRep_1     | salesRep_location_1                   | salesRep_1     | ps_1                          | Y            | Y              | Y              | 1000009              | super_salesRep                        | salesRep_1 cmp     |               |
      | customer_1     | customer_location_1                   | customer_1     | ps_1                          | Y            | Y              | Y              | 1000009              | salesRep_1                            | customer_1 cmp     | 1234567891234 |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier           | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | hierarchyContract_1  | hierarchyConditions_1               | salesRep_1                  | 2021-10-31 | 2022-10-30 | commission_product          |
      | hierarchyContract_2  | hierarchyConditions_1               | super_salesRep              | 2021-10-31 | 2022-10-30 | commission_product          |
      | licenseFeeContract_1 | licenseFeeConditions_1              | salesRep_1                  | 2021-10-31 | 2022-10-30 | commission_product          |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": "12114",
    "externalLineId": "111",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567891234",
        "bpartnerLocationIdentifier": "gln-1234567891234"
    },
    "dateRequired": "2021-12-02",
    "dateOrdered": "2021-11-20",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-transaction_product_16112022_1",
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
    "externalHeaderId": "12114",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""
    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.AD_InputDataSource_ID.InternalName |
      | invoice_1               | customer_1               | customer_location_1               | po_ref_mock     | 1000002     | true      | CO        | Shopware                               |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLine1_1              | invoice_1               | transaction_product     | 1           | true      |
    And locate invoice candidates for invoice: invoice_1
      | C_Invoice_Candidate_ID.Identifier | M_Product_ID.Identifier |
      | so_invoice_candidate              | transaction_product     |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtInvoiced |
      | so_invoice_candidate              | customer_1                  | transaction_product     | 10                 |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx | OPT.NetAmtInvoiced | OPT.AD_InputDataSource_ID.InternalName |
      | so_invoice_candidate              | customer_1                      | transaction_product         | 0                   | true        | 10                 | Shopware                               |
    And validate created commission instance
      | C_Commission_Instance_ID.Identifier | C_Order_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_Order_ID.Identifier | PointsBase_Forecasted | PointsBase_Invoiceable | PointsBase_Invoiced |
      | commissionInstance_1                | order_1               | customer_1                  | transaction_product           | 0                     | 0                      | 10                  |
    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_CommissionSettingsLine_ID.Identifier | OPT.C_LicenseFeeSettingsLine_ID.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | hierarchy_commissionShare_1      | salesRep_1                        | metasfresh                     | hierarchyContract_1           | commission_product               | 0              | hierarchySettingsLine_1                    |                                            | false   | false        | 0                    | 0                     | 1.00               | 1.00               | 0                 |
      | hierarchy_commissionShare_2      | super_salesRep                    | metasfresh                     | hierarchyContract_2           | commission_product               | 1              | hierarchySettingsLine_1                    |                                            | false   | false        | 0                    | 0                     | 0.90               | 0.90               | 0                 |
      | hierarchy_commissionShare_3      | metasfresh                        | salesRep_1                     | licenseFeeContract_1          | commission_product               | 0              |                                            | licenseFeeSettingsLine_1                   | true    | false        | 0                    | 0                     | 0.50               | 0.50               | 0                 |
    And validate commission fact for hierarchy_commissionShare_1
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | hierarchy_settlement_1                           | 1.00             | TO_SETTLE             |
      |                                                  | 1.00             | INVOICED              |
      |                                                  | -1.00            | INVOICEABLE           |
      |                                                  | 1.00             | INVOICEABLE           |
      |                                                  | -1.00            | FORECASTED            |
      |                                                  | 1.00             | FORECASTED            |
    And validate commission fact for hierarchy_commissionShare_2
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | hierarchy_settlement_2                           | 0.90             | TO_SETTLE             |
      |                                                  | 0.90             | INVOICED              |
      |                                                  | -0.90            | INVOICEABLE           |
      |                                                  | 0.90             | INVOICEABLE           |
      |                                                  | -0.90            | FORECASTED            |
      |                                                  | 0.90             | FORECASTED            |
    And validate commission fact for hierarchy_commissionShare_3
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | license_fee_settlement                           | 0.50             | TO_SETTLE             |
      |                                                  | 0.50             | INVOICED              |
      |                                                  | -0.50            | INVOICEABLE           |
      |                                                  | 0.50             | INVOICEABLE           |
      |                                                  | -0.50            | FORECASTED            |
      |                                                  | 0.50             | FORECASTED            |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx |
      | hierarchy_settlement_1            | salesRep_1                      | commission_product          | 1                   | false       |
      | hierarchy_settlement_2            | super_salesRep                  | commission_product          | 0.9                 | false       |
      | license_fee_settlement            | salesRep_1                      | commission_product          | 0.5                 | true        |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | hierarchy_settlement_1            |
      | hierarchy_settlement_2            |
      | license_fee_settlement            |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceSettled_1        | hierarchy_settlement_1            |
      | invoiceSettled_2        | hierarchy_settlement_2            |
      | invoiceSettled_3        | license_fee_settlement            |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtInvoiced |
      | hierarchy_settlement_1            | salesRep_1                  | commission_product      | 1                  |
      | hierarchy_settlement_2            | super_salesRep              | commission_product      | 0.9                |
      | license_fee_settlement            | salesRep_1                  | commission_product      | 0.5                |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx | OPT.NetAmtInvoiced |
      | hierarchy_settlement_1            | salesRep_1                      | commission_product          | 0                   | false       | 1                  |
      | hierarchy_settlement_2            | super_salesRep                  | commission_product          | 0                   | false       | 0.9                |
      | license_fee_settlement            | salesRep_1                      | commission_product          | 0                   | true        | 0.5                |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.DocSubType |
      | invoiceSettled_1        | salesRep_1               | salesRep_location_1               | 10 Tage 1 % | true      | CO        | CA             |
      | invoiceSettled_2        | super_salesRep           | super_salesRep_location           | 10 Tage 1 % | true      | CO        | CA             |
      | invoiceSettled_3        | salesRep_1               | salesRep_location_1               | 10 Tage 1 % | true      | CO        | LS             |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLineSettled_1_1      | invoiceSettled_1        | commission_product      | 1.00        | true      |
      | invoiceLineSettled_2_1      | invoiceSettled_2        | commission_product      | 0.90        | true      |
      | invoiceLineSettled_3_1      | invoiceSettled_3        | commission_product      | 0.50        | true      |
    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_CommissionSettingsLine_ID.Identifier | OPT.C_LicenseFeeSettingsLine_ID.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | hierarchy_commissionShare_1      | salesRep_1                        | metasfresh                     | hierarchyContract_1           | commission_product               | 0              | hierarchySettingsLine_1                    |                                            | false   | false        | 0                    | 0                     | 1.00               | 0                  | 1.00              |
      | hierarchy_commissionShare_2      | super_salesRep                    | metasfresh                     | hierarchyContract_2           | commission_product               | 1              | hierarchySettingsLine_1                    |                                            | false   | false        | 0                    | 0                     | 0.90               | 0                  | 0.90              |
      | hierarchy_commissionShare_3      | metasfresh                        | salesRep_1                     | licenseFeeContract_1          | commission_product               | 0              |                                            | licenseFeeSettingsLine_1                   | true    | false        | 0                    | 0                     | 0.50               | 0                  | 0.50              |
    And validate commission fact for hierarchy_commissionShare_1
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | hierarchy_settlement_1                           | 1.00             | SETTLED               |
      | hierarchy_settlement_1                           | -1.00            | TO_SETTLE             |
      | hierarchy_settlement_1                           | 1.00             | TO_SETTLE             |
      |                                                  | 1.00             | INVOICED              |
      |                                                  | -1.00            | INVOICEABLE           |
      |                                                  | 1.00             | INVOICEABLE           |
      |                                                  | -1.00            | FORECASTED            |
      |                                                  | 1.00             | FORECASTED            |
    And validate commission fact for hierarchy_commissionShare_2
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | hierarchy_settlement_2                           | 0.90             | SETTLED               |
      | hierarchy_settlement_2                           | -0.90            | TO_SETTLE             |
      | hierarchy_settlement_2                           | 0.90             | TO_SETTLE             |
      |                                                  | 0.90             | INVOICED              |
      |                                                  | -0.90            | INVOICEABLE           |
      |                                                  | 0.90             | INVOICEABLE           |
      |                                                  | -0.90            | FORECASTED            |
      |                                                  | 0.90             | FORECASTED            |
    And validate commission fact for hierarchy_commissionShare_3
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | license_fee_settlement                           | 0.50             | SETTLED               |
      | license_fee_settlement                           | -0.50            | TO_SETTLE             |
      | license_fee_settlement                           | 0.50             | TO_SETTLE             |
      |                                                  | 0.50             | INVOICED              |
      |                                                  | -0.50            | INVOICEABLE           |
      |                                                  | 0.50             | INVOICEABLE           |
      |                                                  | -0.50            | FORECASTED            |
      |                                                  | 0.50             | FORECASTED            |

  @from:cucumber
  @topic:commissionContracts
  @Id:S0150_180
  Scenario: Hierarchy commission and license fee commission combined having the salesRep as direct customer
    And metasfresh contains C_HierarchyCommissionSettings:
      | C_HierarchyCommissionSettings_ID.Identifier | Name                        | Commission_Product_ID.Identifier | IsCreateShareForOwnRevenue |
      | hierarchySettings_own_rev_1                 | hierarchySettings_own_rev_1 | commission_product               | Y                          |
    And metasfresh contains C_CommissionSettingsLines:
      | C_CommissionSettingsLine_ID.Identifier | C_HierarchyCommissionSettings_ID.Identifier | SeqNo | PercentOfBasePoints |
      | hierarchySettingsLine_own_rev_1        | hierarchySettings_own_rev_1                 | 10    | 10                  |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                    | Name            | Type_Conditions | OPT.C_HierarchyCommissionSettings_ID.Identifier | OPT.C_LicenseFeeSettings_ID.Identifier |
      | hierarchyConditions_own_rev_1 | hierarchy-test  | Commission      | hierarchySettings_own_rev_1                     |                                        |
      | licenseFeeConditions_1        | licenseFee-test | LicenseFee      |                                                 | licenseFeeSettings_1                   |

    And metasfresh contains C_BPartners:
      | Identifier          | OPT.C_BPartner_Location_ID.Identifier | Name                | M_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer | OPT.IsSalesRep | OPT.C_PaymentTerm_ID | OPT.C_BPartner_SalesRep_ID.Identifier | OPT.CompanyName         | OPT.GLN       |
      | customer_salesRep_1 | customer_salesRep_location_1          | customer_salesRep_1 | ps_1                          | Y            | Y              | Y              | 1000009              |                                       | customer_salesRep_1 cmp | 1234567891235 |

    And metasfresh contains C_Flatrate_Terms:
      | Identifier                  | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.M_Product_ID.Identifier |
      | hierarchyContract_own_rev_1 | hierarchyConditions_own_rev_1       | customer_salesRep_1         | 2021-10-31 | 2022-10-30 | commission_product          |
      | licenseFeeContract_1        | licenseFeeConditions_1              | customer_salesRep_1         | 2021-10-31 | 2022-10-30 | commission_product          |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": "23433",
    "externalLineId": "111",
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-1234567891235",
        "bpartnerLocationIdentifier": "gln-1234567891235"
    },
    "dateRequired": "2021-12-02",
    "dateOrdered": "2021-11-20",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-transaction_product_16112022_1",
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
    "externalHeaderId": "23433",
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
      | so_invoice_candidate              | customer_salesRep_1         | transaction_product     | 10                 |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx | OPT.NetAmtInvoiced | OPT.AD_InputDataSource_ID.InternalName |
      | so_invoice_candidate              | customer_salesRep_1             | transaction_product         | 0                   | true        | 10                 | Shopware                               |
    And validate created commission instance
      | C_Commission_Instance_ID.Identifier | C_Order_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_Order_ID.Identifier | PointsBase_Forecasted | PointsBase_Invoiceable | PointsBase_Invoiced |
      | commissionInstance_1                | order_1               | customer_salesRep_1         | transaction_product           | 0                     | 0                      | 10                  |
    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_CommissionSettingsLine_ID.Identifier | OPT.C_LicenseFeeSettingsLine_ID.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | commissionShare_so               | metasfresh                        | customer_salesRep_1            | licenseFeeContract_1          | commission_product               | 0              |                                            | licenseFeeSettingsLine_1                   | true    | false        | 0                    | 0                     | 0.50               | 0.50               | 0                 |
      | commissionShare_po               | customer_salesRep_1               | metasfresh                     | hierarchyContract_own_rev_1   | commission_product               | 0              | hierarchySettingsLine_own_rev_1            |                                            | false   | false        | 0                    | 0                     | 0                  | 0                  | 0                 |
    And validate commission fact for commissionShare_po
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
    And validate commission fact for commissionShare_so
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | settlement_so                                    | 0.50             | TO_SETTLE             |
      |                                                  | 0.50             | INVOICED              |
      |                                                  | -0.50            | INVOICEABLE           |
      |                                                  | 0.50             | INVOICEABLE           |
      |                                                  | -0.50            | FORECASTED            |
      |                                                  | 0.50             | FORECASTED            |

    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx |
      | settlement_so                     | customer_salesRep_1             | commission_product          | 0.5                 | true        |
    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | settlement_so                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoiceSettled_so       | settlement_so                     |
    And recompute invoice candidates if required
      | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | M_Product_ID.Identifier | OPT.NetAmtInvoiced |
      | settlement_so                     | customer_salesRep_1         | commission_product      | 0.5                |
    And validate invoice candidate
      | C_Invoice_Candidate_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.NetAmtToInvoice | OPT.IsSOTrx | OPT.NetAmtInvoiced |
      | settlement_so                     | customer_salesRep_1             | commission_product          | 0                   | true        | 0.5                |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | paymentTerm | processed | docStatus | OPT.DocSubType |
      | invoiceSettled_so       | customer_salesRep_1      | customer_salesRep_location_1      | 10 Tage 1 % | true      | CO        | LS             |
    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | qtyinvoiced | processed |
      | invoiceLineSettled_so       | invoiceSettled_so       | commission_product      | 0.50        | true      |

    And validate commission deed for commission instance commissionInstance_1
      | C_Commission_Share_ID.Identifier | C_BPartner_SalesRep_ID.Identifier | C_BPartner_Payer_ID.Identifier | C_Flatrate_Term_ID.Identifier | Commission_Product_ID.Identifier | LevelHierarchy | OPT.C_CommissionSettingsLine_ID.Identifier | OPT.C_LicenseFeeSettingsLine_ID.Identifier | IsSOTrx | IsSimulation | PointsSum_Forecasted | PointsSum_Invoiceable | PointsSum_Invoiced | PointsSum_ToSettle | PointsSum_Settled |
      | commissionShare_so               | metasfresh                        | customer_salesRep_1            | licenseFeeContract_1          | commission_product               | 0              |                                            | licenseFeeSettingsLine_1                   | true    | false        | 0                    | 0                     | 0.50               | 0                  | 0.50              |
      | commissionShare_po               | customer_salesRep_1               | metasfresh                     | hierarchyContract_own_rev_1   | commission_product               | 0              | hierarchySettingsLine_own_rev_1            |                                            | false   | false        | 0                    | 0                     | 0                  | 0                  | 0                 |
    And validate commission fact for commissionShare_po
      | C_Commission_Fact_ID.Identifier | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
    And validate commission fact for commissionShare_so
      | OPT.C_Invoice_Candidate_Commission_ID.Identifier | CommissionPoints | Commission_Fact_State |
      | settlement_so                                    | 0.50             | SETTLED               |
      | settlement_so                                    | -0.50            | TO_SETTLE             |
      | settlement_so                                    | 0.50             | TO_SETTLE             |
      |                                                  | 0.50             | INVOICED              |
      |                                                  | -0.50            | INVOICEABLE           |
      |                                                  | 0.50             | INVOICEABLE           |
      |                                                  | -0.50            | FORECASTED            |
      |                                                  | 0.50             | FORECASTED            |
