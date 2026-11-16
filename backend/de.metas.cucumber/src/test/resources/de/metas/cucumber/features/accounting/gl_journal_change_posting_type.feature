@from:cucumber
@allure.label.epic:E0225_Accounting
@allure.label.feature:F01106_Re-Post_Documents
Feature: Changing the posting type of a completed GL journal keeps it posted
##
## "Change posting type" (process GL_Journal_ChangePostingType) deletes the journal's accounting entries (Fact_Acct) and sets
## GL_Journal.Posted='N'. A completed journal must then be posted again with the new posting type; today nothing queues the re-posting.
##

  Background:
    Given infrastructure and metasfresh are running
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2018-01-15T10:00:00+01:00[Europe/Berlin]
    # a year that no other feature books on: the year-end process closes ALL expense/revenue balances of the year, so the expected line count must not depend on other features run before in the same database
    And all periods are open
    And metasfresh contains C_ElementValues:
      | Identifier |
      | gjDebit    |
      | gjCredit   |
    And C_ElementValue "gjDebit" allows posting of actual and statistical amounts
    And C_ElementValue "gjCredit" allows posting of actual and statistical amounts
    And the income summary account of the primary accounting schema allows posting of actual and statistical amounts

  @Id:S33766_01
  Scenario: a year-end closing journal is posted again after its posting type was changed from Statistical to Actual
    # real year-end flow: expense/revenue balances booked as Actual are closed by a Statistical journal created by GL_Journal_GenerateYearEnding
    Given metasfresh contains GL_Journal:
      | Identifier | DateDoc    | PostingType |
      | gjActual   | 2018-01-10 | A           |
    And metasfresh contains GL_JournalLines:
      | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
      | gjActual      | gjDebit       | gjCredit      | 100.00 |
    And the GL journal "gjActual" is completed
    And the GL journal "gjActual" is eventually posted with PostingType "A" and 2 Fact_Acct lines
    And the GL_Journal_GenerateYearEnding process is invoked for DateAcct "2018-12-31" and the created GL journal is registered as "gj"
    And the GL journal "gj" is completed
    And the GL journal "gj" is eventually posted with PostingType "S" and 4 Fact_Acct lines

    When the GL_Journal_ChangePostingType process is invoked for GL journal "gj" with PostingType "A"

    Then the GL journal "gj" is eventually posted with PostingType "A" and 4 Fact_Acct lines

  @Id:S33766_03
  Scenario: reactivating a posted GL journal does not post it again
    Given metasfresh contains GL_Journal:
      | Identifier | DateDoc    | PostingType |
      | gjReact    | 2018-01-10 | A           |
    And metasfresh contains GL_JournalLines:
      | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
      | gjReact       | gjDebit       | gjCredit      | 100.00 |
    And the GL journal "gjReact" is completed
    And the GL journal "gjReact" is eventually posted with PostingType "A" and 2 Fact_Acct lines
    And the posting requests of documents gjReact are remembered

    When the GL journal "gjReact" is reactivated

    Then the documents gjReact are not posted
    And no Fact_Acct records exist for documents gjReact
    And documents gjReact got no new posting request and no note
