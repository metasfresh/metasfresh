@from:cucumber
Feature: Changing the posting type of a completed GL journal keeps it posted
##
## "Change posting type" (process GL_Journal_ChangePostingType) deletes the journal's accounting entries (Fact_Acct) and sets
## GL_Journal.Posted='N'. A completed journal must then be posted again with the new posting type; today nothing queues the re-posting.
##

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-01-15T10:00:00+01:00[Europe/Berlin]
    And all periods are open
    And metasfresh contains C_ElementValues:
      | Identifier |
      | gjDebit    |
      | gjCredit   |
    And C_ElementValue "gjDebit" allows posting of statistical amounts
    And C_ElementValue "gjCredit" allows posting of statistical amounts

  @Id:S33766_01
  Scenario: a completed GL journal is posted again after its posting type was changed from Statistical to Actual
    Given metasfresh contains GL_Journal:
      | Identifier | DateDoc    | PostingType |
      | gj         | 2024-01-10 | S           |
    And metasfresh contains GL_JournalLines:
      | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
      | gj            | gjDebit       | gjCredit      | 100.00 |
    And the GL journal "gj" is completed
    And the GL journal "gj" is eventually posted with PostingType "S" and 2 Fact_Acct lines

    When the GL_Journal_ChangePostingType process is invoked for GL journal "gj" with PostingType "A"

    Then the GL journal "gj" is eventually posted with PostingType "A" and 2 Fact_Acct lines
