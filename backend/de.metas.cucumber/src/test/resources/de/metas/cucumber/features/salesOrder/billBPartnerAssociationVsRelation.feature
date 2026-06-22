@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00100_Sales_Order
@ghActions:run_on_executor1
Feature: Bill-to partner resolution: per-partner C_BP_Relation (IsBillTo=Y) beats association group

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S30351_10
  Scenario: Per-partner bill-to relation takes precedence over association group bill partner
    Given metasfresh contains C_BPartners:
      | Identifier     |
      | centralBilling |
      | memberBillTo   |
    And metasfresh contains C_BP_Groups:
      | Identifier | IsAssociation | Bill_BPartner_ID |
      | assocGroup | Y             | centralBilling   |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | C_BP_Group_ID |
      | memberBP   | Y          | assocGroup    |
    And metasfresh contains C_BP_Relations:
      | Identifier | C_BPartner_ID | C_BPartnerRelation_ID | C_BPartnerRelation_Location_ID | IsBillTo |
      | rel1       | memberBP      | memberBillTo          | memberBillTo                   | Y        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | salesOrder | true    | memberBP      | 2022-05-17  |
    Then validate the created orders
      | C_Order_ID.Identifier | Bill_BPartner_ID.Identifier |
      | salesOrder            | memberBillTo                |
