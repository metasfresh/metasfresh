@from:cucumber
@ghActions:run_on_executor3
@allure.label.epic:E0390_Masterdata_Partner
@allure.label.feature:F00900_Business_Partner
@allure.label.feature:F00919_Automatic_DebitorCreditor_Number
Feature: BPartner debtor/creditor number generation via REST V2 upsert

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  @Id:S25082_TC1
  Scenario: TC1 - customer gets next debtor number; response carries it
    Given a debtor sequence for org "001" starting at 10000
    When I upsert a "non-company" "customer" "TC1-cust"
    Then responseItems[0].responseBPartnerItem.debtorId is within 10000..10099

  @from:cucumber
  @Id:S25082_TC2
  Scenario: TC2 - vendor gets next creditor number; response carries it
    Given a creditor sequence for org "001" starting at 20000
    When I upsert a "non-company" "vendor" "TC2-vend"
    Then responseItems[0].responseBPartnerItem.creditorId is within 20000..20099

  @from:cucumber
  @Id:S25082_TC3
  Scenario: TC3 - explicit debtor number is used verbatim
    Given a debtor sequence for org "001" starting at 30000
    When I upsert a "non-company" "customer" "TC3-explicit" with debtorId 31000
    Then responseItems[0].responseBPartnerItem.debtorId is 31000

  @from:cucumber
  @Id:S25082_TC4
  Scenario: TC4 - duplicate explicit debtor number is rejected
    Given a debtor sequence for org "001" starting at 40000
    When I upsert a "non-company" "customer" "TC4-first" with debtorId 41000
    Then responseItems[0].responseBPartnerItem.debtorId is 41000
    When I upsert a "non-company" "customer" "TC4-dup" with debtorId 41000
    Then the upsert is rejected

  @from:cucumber
  @Id:S25082_TC5
  Scenario: TC5 - update of existing partner leaves debtor number unchanged, sequence not consumed
    Given a debtor sequence for org "001" starting at 50000
    When I upsert a "non-company" "customer" "TC5-update"
    Then responseItems[0].responseBPartnerItem.debtorId is within 50000..50099
    When I upsert a "non-company" "customer" "TC5-update"
    Then responseItems[0].responseBPartnerItem.debtorId is within 50000..50099

  @from:cucumber
  @Id:S25082_TC6
  Scenario: TC6 - partner with neither customer nor vendor role has no debtor or creditor number
    Given a debtor sequence for org "001" starting at 60000
    And a creditor sequence for org "001" starting at 60500
    When I upsert a "non-company" "neither" "TC6-neither"
    Then responseItems[0].responseBPartnerItem.debtorId is null
    And responseItems[0].responseBPartnerItem.creditorId is null

  @from:cucumber
  @Id:S25082_TC7
  Scenario: TC7 - no sequence configured means no number assigned (not an error)
    When I upsert a "non-company" "customer" "TC7-noconfig"
    Then responseItems[0].responseBPartnerItem.debtorId is null

  @from:cucumber
  @Id:S25082_TC8
  Scenario: TC8 - two orgs draw from independent sequences
    Given metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name    | Value |
      | org_tc8_a            | TC8OrgA | TC8A  |
      | org_tc8_b            | TC8OrgB | TC8B  |
    And a debtor sequence for org "TC8A" starting at 80000
    And a debtor sequence for org "TC8B" starting at 80000
    When I upsert a "non-company" "customer" "TC8-bpA" in org "TC8A"
    Then responseItems[0].responseBPartnerItem.debtorId is within 80000..80099
    When I upsert a "non-company" "customer" "TC8-bpB" in org "TC8B"
    Then responseItems[0].responseBPartnerItem.debtorId is within 80000..80099

  @from:cucumber
  @Id:S25082_TC9
  Scenario: TC9 - override function replaces sequence-based generation
    Given org "001" uses number resolver "metas_bpartner_numbgen_test_override"
    When I upsert a "non-company" "customer" "TC9-override"
    Then responseItems[0].responseBPartnerItem.debtorId is 999

  @from:cucumber
  @Id:S25082_TC10
  Scenario: TC10 - explicit number above current advances sequence; next generated is greater than explicit
    Given a debtor sequence for org "001" starting at 10000
    When I upsert a "non-company" "customer" "TC10-advance" with debtorId 15000
    Then responseItems[0].responseBPartnerItem.debtorId is 15000
    When I upsert a "non-company" "customer" "TC10-next"
    Then responseItems[0].responseBPartnerItem.debtorId is within 15001..15099
