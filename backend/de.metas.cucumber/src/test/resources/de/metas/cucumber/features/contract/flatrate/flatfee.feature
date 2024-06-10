@from:cucumber
@topic:flatfeeContracts
Feature: Flatfee contract with Flatfee-Type "Reported-Qty"

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has current date and time
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @topic:flatFeeContractsWithReporterdQty
  @Id:S0433_1
  Scenario: Create Flatfee contract with reported quantities and make sure, a normal sales order is identified with the contract.
    Given taxCategory 'Normal' is updated to work with all productTypes
    And metasfresh contains C_UOMs:
      | Identifier          | X12DE355 | Name         | UOMSymbol | StdPrecision | CostingPrecision |
      | flatfee_uom_S0433_1 | MEAL     | Meal_S0433_1 | M         | 2            | 2                |
    And metasfresh contains M_Attributes:
      | Identifier          | Value               | Name                | AttributeValueType |
      | mealType_S0433_1    | MealType_S0433_1    | MealType_S0433_1    | L                  |
      | serviceType_S0433_1 | ServiceType_S0433_1 | ServiceType_S0433_1 | L                  |
    And metasfresh contains M_AttributeValues:
      | Identifier        | M_Attribute_ID.Identifier | Name      | Value     | IsNullFieldValue |
      | breakfast_S0433_1 | mealType_S0433_1          | Breakfast | Breakfast | false            |
      | lunch_S0433_1     | mealType_S0433_1          | Lunch     | Lunch     | false            |
      | dinner_S0433_1    | mealType_S0433_1          | Dinner    | Dinner    | false            |
      | special_S0433_1   | serviceType_S0433_1       | Standard  | Standard  | false            |
      | special_S0433_1   | serviceType_S0433_1       | Special   | Special   | false            |
    And add M_AttributeSet:
      | M_AttributeSet_ID.Identifier | Name                         | MandatoryType |
      | attributeSet_S0433_1         | flatfee_attributeSet_S0433_1 | N             |
    And add M_AttributeUse:
      | M_AttributeUse_ID.Identifier | M_AttributeSet_ID.Identifier | M_Attribute_ID.Identifier | SeqNo |
      | attributeUse_mealType        | attributeSet_S0433_1         | mealType_S0433_1          | 10    |
      | attributeUse_serviceType     | attributeSet_S0433_1         | serviceType_S0433_1       | 20    |
    And metasfresh contains M_AttributeSetInstance with identifier "ASI_Standard_S0433_1":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"ServiceType_S0433_1",
          "valueStr":"Standard"
      }
    ]
  }
  """

    And metasfresh contains M_AttributeSetInstance with identifier "ASI_Special_S0433_1":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"ServiceType_S0433_1",
          "valueStr":"Special"
      }
    ]
  }
  """
    And metasfresh contains M_Product_Categories:
      | Identifier                   | Name                         | Value                        |
      | transaction_category_S0433_1 | transaction_category_S0433_1 | transaction_category_S0433_1 |
    And metasfresh contains M_Products:
      | Identifier                  | Name                        | ProductType | OPT.X12DE355 | OPT.M_AttributeSet_ID.Identifier | OPT.M_Product_Category_ID.Identifier |
      | flatfee_product_S0433_1     | flatfee_product_S0433_1     | S           | MEAL         | attributeSet_S0433_1             |                                      |
      | transaction_product_S0433_1 | transaction_product_S0433_1 |             | PCE          |                                  | transaction_category_S0433_1         |

    And metasfresh contains M_PricingSystems
      | Identifier   | Name                         | Value                           | OPT.IsActive |
      | ps_1_S0433_1 | sales_pricing_system_S0433_1 | salesRep_pricing_system_S0433_1 | true         |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_so_S0433_1 | ps_1_S0433_1                  | DE                        | EUR                 | price_list_so_S0433_1 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID.Identifier | Name                   | ValidFrom  |
      | plv_so_S0433_1 | pl_so_S0433_1             | salesOrder-PLV_S0433_1 | 2022-05-15 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier     | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName | OPT.SeqNo | OPT.IsAttributeDependant | OPT.M_AttributeSetInstance_ID.Identifier | OPT.UseScalePrice |
      | pp_11_S0433_1 | plv_so_S0433_1                    | flatfee_product_S0433_1     | 2.0      | MEAL              | Normal                        | 10        | Y                        | ASI_Special_S0433_1                      | N                 |
      | pp_12_S0433_1 | plv_so_S0433_1                    | flatfee_product_S0433_1     | 3.0      | MEAL              | Normal                        | 20        | Y                        | ASI_Standard_S0433_1                     | N                 |
      | pp_13_S0433_1 | plv_so_S0433_1                    | flatfee_product_S0433_1     | 1.0      | MEAL              | Normal                        | 30        | N                        |                                          | N                 |
      | pp_2_S0433_1  | plv_so_S0433_1                    | transaction_product_S0433_1 | 10.0     | PCE               | Normal                        | 10        | N                        |                                          | N                 |

    And metasfresh contains C_Flatrate_Conditions:
      | Identifier                  | Name                 | Type_Conditions | OPT.Type_Flatrate | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_UOM_ID.Identifier |
      | flatFeeConditions_1_S0433_1 | flatfee-test_S0433_1 | FlatFee         | RPTD              | flatfee_product_S0433_1              | flatfee_uom_S0433_1     |
    And metasfresh contains C_Flatrate_Matchings:
      | Identifier         | C_Flatrate_Conditions_ID.Identifier | SeqNo | OPT.M_Product_Category_ID.Identifier |
      | matching_1_S0433_1 | flatFeeConditions_1_S0433_1         | 10    | transaction_category_S0433_1         |

    And metasfresh contains C_BPartners:
      | Identifier              | OPT.C_BPartner_Location_ID.Identifier | Name                    | M_PricingSystem_ID.Identifier | OPT.IsVendor | OPT.IsCustomer | OPT.IsSalesRep | OPT.C_PaymentTerm_ID |
      | shipToPartner_1_S0433_1 | shipToPartner_location_1_S0433_1      | shipToPartner_1_S0433_1 | ps_1_S0433_1                  | Y            | Y              | Y              | 1000009              |
    And metasfresh contains C_BPartner_Departments:
      | Identifier           | C_BPartner_ID.Identifier | Value | Name         |
      | department_1_S0433_1 | shipToPartner_1_S0433_1  | 1     | Department 1 |
      | department_2_S0433_1 | shipToPartner_1_S0433_1  | 2     | Department 2 |

    ##
    When metasfresh contains C_Flatrate_Terms:
      | Identifier                | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | flatFeeContract_1_S0433_1 | flatFeeConditions_1_S0433_1         | shipToPartner_1_S0433_1     | 2022-05-01 | 2023-04-30 | DR            | false         |
    And the C_Flatrate_Term identified by flatFeeContract_1_S0433_1 is completed
    Then the C_Flatrate_Term identified by flatFeeContract_1_S0433_1 has 12 C_Flatrate_DataEntries.

    ##
    When metasfresh contains C_Orders:
      | Identifier  | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1_S0433_1 | true    | shipToPartner_1_S0433_1  | 2022-06-01  |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier     | QtyEntered |
      | ol_1_S0433_1 | o_1_S0433_1           | transaction_product_S0433_1 | 10         |
    And the order identified by o_1_S0433_1 is completed

    Then after not more than 60s locate invoice candidates of order identified by o_1_S0433_1
      | C_Invoice_Candidate_ID.Identifier | M_Product_ID                |
      | invoice_candidate_1_S0433_1       | transaction_product_S0433_1 |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1_S0433_1       |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | QtyToInvoice | OPT.QtyOrdered | OPT.C_Order_ID.Identifier | OPT.IsToClear |
      | invoice_candidate_1_S0433_1       | 0            | 10             | o_1_S0433_1               | true          |

    