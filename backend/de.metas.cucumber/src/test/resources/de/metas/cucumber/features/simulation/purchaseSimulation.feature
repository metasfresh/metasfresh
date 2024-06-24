@from:cucumber
@ghActions:run_on_executor7
Feature: create purchase simulation

  Background:
    Given the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-14T08:00:00+00:00

  @from:cucumber
  @Id:S0171.200
  Scenario: create purchase simulation, delete order line and validate there is no purchase candidate for deleted line
    Given metasfresh contains M_Products:
      | Identifier | Name                        |
      | p_1        | product_Purchase_06_07_2022 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                | Value                | OPT.Description            | OPT.IsActive |
      | ps_1       | pricing_system_name | pricing_system_value | pricing_system_description | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_1 | null            | true  | false         | 2              | true         |
      | pl_2       | ps_1                          | DE                        | EUR                 | price_list_name_2 | null            | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name               | ValidFrom  |
      | plv_1      | pl_1                      | priceListVersion_1 | 2021-04-01 |
      | plv_2      | pl_2                      | priceListVersion_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
      | pp_2       | plv_2                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchema
      | M_DiscountSchema_ID.Identifier | Name               | BreakValueType | DiscountType | ValidFrom  |
      | discountSchema_1               | DiscountSchemaName | Q              | P            | 2021-04-01 |
    And metasfresh contains C_BPartners:
      | Identifier | Name     | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | customer_1 | Customer | N            | Y              | ps_1                          |                                     |
      | vendor_1   | Vendor   | Y            | N              | ps_1                          | discountSchema_1                    |
    And metasfresh contains C_BPartner_Products:
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_1                 | p_1                     |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID.Identifier | IsCreatePlan | OPT.IsPurchased |
      | ppln_1     | p_1                     | true         | true            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.PreparationDate  |
      | o_1        | true    | customer_1               | 2021-04-04  | 2021-04-04T00:00:00Z |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 100        |
    When create and process 'simulated demand' for:
      | C_Order_ID.Identifier | C_OrderLine_ID.Identifier |
      | o_1                   | ol_1                      |
    Then after not more than 30s, the MD_Candidate table has only the following records
      | Identifier | MD_Candidate_Type | OPT.MD_Candidate_BusinessCase | M_Product_ID.Identifier | DateProjected        | Qty  | Qty_AvailableToPromise | OPT.simulated |
      | c_1        | DEMAND            | SHIPMENT                      | p_1                     | 2021-04-04T00:00:00Z | -100 | -100                   | true          |
      | c_2        | SUPPLY            | PURCHASE                      | p_1                     | 2021-04-04T00:00:00Z | 100  | 0                      | true          |
    And after not more than 30s, C_PurchaseCandidate found for orderLine ol_1
      | Identifier |
      | pc_1       |
    And delete C_OrderLine identified by ol_1, but keep its id into identifierIds table
    And no C_PurchaseCandidate found for orderLine ol_1
