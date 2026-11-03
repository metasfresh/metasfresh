@from:cucumber
@ghActions:run_on_executor3
@allure.label.epic:E0390_Masterdata_Partner
@allure.label.feature:F00900_Business_Partner
@allure.label.feature:F00919_Automatic_DebitorCreditor_Number
Feature: BPartner debtor/creditor number generation via REST V2 upsert

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    # Create the records the scenarios depend on — the standard seed has only org 001 and no
    # "Test_System" external system (a polluted local stack may already carry them; CI does not).
    And metasfresh contains AD_Org:
      | AD_Org_ID.Identifier | Name | Value |
      | org002               | 002  | 002   |
    And metasfresh contains External System
      | Name        | Value       |
      | Test System | Test_System |
    And the BPartner number-generation config is reset
    # The master switch ships default-off; enable it (System level) so the interceptor runs for these scenarios.
    And set sys config boolean value true for sys config de.metas.bpartner.NumberGeneration_Enabled

  @from:cucumber
  @Id:S25082_TC1
  Scenario: TC1 - customer gets next debtor number; response carries it
    Given a debtor sequence for org "001" starting at 10000
    When I upsert a "non-company" "customer" "TC1-cust"
    Then responseItems[0].responseBPartnerItem.debtorId is 10000

  @from:cucumber
  @Id:S25082_TC2
  Scenario: TC2 - vendor gets next creditor number; response carries it
    Given a creditor sequence for org "001" starting at 20000
    When I upsert a "non-company" "vendor" "TC2-vend"
    Then responseItems[0].responseBPartnerItem.creditorId is 20000

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
    Then responseItems[0].responseBPartnerItem.debtorId is 50000
    When I upsert a "non-company" "customer" "TC5-update"
    Then responseItems[0].responseBPartnerItem.debtorId is 50000

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
    # Org 001 (seed) and 002 (created in the Background). Both debtor sequences start at 80000;
    # the upsert auto-creates each org's group by Value, and the per-org unique index
    # (debtorid, ad_org_id) plus per-org sysconfig keep the two orgs independent.
    Given a debtor sequence for org "001" starting at 80000
    And a debtor sequence for org "002" starting at 80000
    When I upsert a "non-company" "customer" "TC8-bpA" in org "001"
    Then responseItems[0].responseBPartnerItem.debtorId is 80000
    When I upsert a "non-company" "customer" "TC8-bpB" in org "002"
    Then responseItems[0].responseBPartnerItem.debtorId is 80000

  @from:cucumber
  @Id:S25082_TC9
  Scenario: TC9 - override function replaces sequence-based generation
    Given the override test function "metas_bpartner_numbgen_test_override" returns 999
    And org "001" uses number resolver "metas_bpartner_numbgen_test_override"
    When I upsert a "non-company" "customer" "TC9-override"
    Then responseItems[0].responseBPartnerItem.debtorId is 999

  @from:cucumber
  @Id:S25082_TC9b
  Scenario: TC9b - partner who is both customer and vendor gets both debtor and creditor numbers
    Given org "001" uses number resolver ""
    And a debtor sequence for org "001" starting at 91000
    And a creditor sequence for org "001" starting at 92000
    When I upsert a "non-company" "both" "TC9b-both"
    Then responseItems[0].responseBPartnerItem.debtorId is 91000
    And responseItems[0].responseBPartnerItem.creditorId is 92000

  @from:cucumber
  @Id:S25082_TC10
  Scenario: TC10 - explicit number above current advances sequence; next generated is greater than explicit
    Given a debtor sequence for org "001" starting at 10000
    When I upsert a "non-company" "customer" "TC10-advance" with debtorId 15000
    Then responseItems[0].responseBPartnerItem.debtorId is 15000
    When I upsert a "non-company" "customer" "TC10-next"
    Then responseItems[0].responseBPartnerItem.debtorId is 15001

  @from:cucumber
  @Id:S25082_TC12
  Scenario: TC12 - an error raised by the override function surfaces as a rejected upsert (not swallowed)
    Given the override test function "metas_bpartner_numbgen_test_raise" raises an error
    And org "001" uses number resolver "metas_bpartner_numbgen_test_raise"
    When I upsert a "non-company" "customer" "TC12-raise"
    Then the upsert is rejected

  @from:cucumber
  @Id:S25082_TC11
  Scenario: TC11 - cleanup - disable the feature toggle so it does not leak to sibling features on this executor
    # The toggle is a System-level (0,0) row; the Background re-enables it per scenario, so this final
    # scenario turns it back off, leaving the shared executor DB in its default-off state.
    Given set sys config boolean value false for sys config de.metas.bpartner.NumberGeneration_Enabled
