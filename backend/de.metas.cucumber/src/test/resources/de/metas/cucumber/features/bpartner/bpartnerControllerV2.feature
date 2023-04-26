@from:cucumber
Feature:credit limit delete using metasfresh api

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'

  @from:cucumber
  Scenario: delete all credit limits for a given BPartner
    And metasfresh contains C_BPartners:
      | Identifier | Name         | OPT.IsVendor | OPT.IsCustomer |
      | bpartner   | BPartnerName | N            | Y              |
    And metasfresh contains C_BPartner_CreditLimit:
      | C_BPartner_CreditLimit_ID.Identifier | C_BPartner_ID.Identifier | Amount | ApprovedBy_ID | Processed | OPT.DateFrom |
      | creditLimit_1                        | bpartner                 | 12.5   | 100           | false     | 2022-10-31   |
      | creditLimit_2                        | bpartner                 | 9      | 100           | true      | 2022-08-15   |
      | creditLimit_3                        | bpartner                 | 4      | 100           | false     | 2022-10-01   |

    And store bpartner endpointPath /api/v2/bpartner/001/:bpartner/credit-limit?includingProcessed=true in context

    When a 'DELETE' request is sent to metasfresh REST-API with endpointPath from context and fulfills with '200' status code

    Then validate no credit limit records found for: bpartner

