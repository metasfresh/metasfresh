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
    And C_ElementValue "gjDebit" allows posting of actual and statistical amounts
    And C_ElementValue "gjCredit" allows posting of actual and statistical amounts
    And the income summary account of the primary accounting schema allows posting of actual and statistical amounts

  @Id:S33766_01
  Scenario: a year-end closing journal is posted again after its posting type was changed from Statistical to Actual
    # real year-end flow: expense/revenue balances booked as Actual are closed by a Statistical journal created by GL_Journal_GenerateYearEnding
    Given metasfresh contains GL_Journal:
      | Identifier | DateDoc    | PostingType |
      | gjActual   | 2024-01-10 | A           |
    And metasfresh contains GL_JournalLines:
      | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
      | gjActual      | gjDebit       | gjCredit      | 100.00 |
    And the GL journal "gjActual" is completed
    And the GL journal "gjActual" is eventually posted with PostingType "A" and 2 Fact_Acct lines
    And the GL_Journal_GenerateYearEnding process is invoked for DateAcct "2024-12-31" and the created GL journal is registered as "gj"
    And the GL journal "gj" is completed
    And the GL journal "gj" is eventually posted with PostingType "S" and 4 Fact_Acct lines

    When the GL_Journal_ChangePostingType process is invoked for GL journal "gj" with PostingType "A"

    Then the GL journal "gj" is eventually posted with PostingType "A" and 4 Fact_Acct lines

  @Id:S33766_02
  Scenario: the completed journals of a batch are posted again after the posting type of the batch was changed from Statistical to Actual
    Given metasfresh contains GL_JournalBatch:
      | Identifier | PostingType |
      | gjBatch    | S           |
    And metasfresh contains GL_Journal:
      | Identifier | DateDoc    | PostingType | GL_JournalBatch_ID |
      | gjS1       | 2024-01-10 | S           | gjBatch            |
      | gjS2       | 2024-01-11 | S           | gjBatch            |
    And metasfresh contains GL_JournalLines:
      | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
      | gjS1          | gjDebit       | gjCredit      | 100.00 |
      | gjS2          | gjDebit       | gjCredit      | 200.00 |
    And the GL journal "gjS1" is completed
    And the GL journal "gjS2" is completed
    And the GL journal "gjS1" is eventually posted with PostingType "S" and 2 Fact_Acct lines
    And the GL journal "gjS2" is eventually posted with PostingType "S" and 2 Fact_Acct lines

    When the GL_Journal_ChangePostingType process is invoked for GL journal batch "gjBatch" with PostingType "A"

    Then the GL journal batch "gjBatch" has GL_JournalBatch.PostingType "A"
    And the GL journal "gjS1" is eventually posted with PostingType "A" and 2 Fact_Acct lines
    And the GL journal "gjS2" is eventually posted with PostingType "A" and 2 Fact_Acct lines

  @Id:S33766_03
  Scenario: reactivating a posted GL journal does not post it again
    Given metasfresh contains GL_Journal:
      | Identifier | DateDoc    | PostingType |
      | gjReact    | 2024-01-10 | A           |
    And metasfresh contains GL_JournalLines:
      | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
      | gjReact       | gjDebit       | gjCredit      | 100.00 |
    And the GL journal "gjReact" is completed
    And the GL journal "gjReact" is eventually posted with PostingType "A" and 2 Fact_Acct lines
    And the posting requests of GL journal "gjReact" are remembered

    When the GL journal "gjReact" is reactivated

    Then the GL journal "gjReact" is not posted and has no Fact_Acct lines
    And GL journal "gjReact" got no new posting request and no note

  @Id:S33766_04
  Scenario: a drafted journal in a batch is not queued for posting when the posting type of the batch is changed
    Given metasfresh contains GL_JournalBatch:
      | Identifier | PostingType |
      | gjBatch5   | S           |
    And metasfresh contains GL_Journal:
      | Identifier | DateDoc    | PostingType | GL_JournalBatch_ID |
      | gjDone     | 2024-01-10 | S           | gjBatch5           |
      | gjDraft    | 2024-01-11 | S           | gjBatch5           |
    And metasfresh contains GL_JournalLines:
      | GL_Journal_ID | Account_DR_ID | Account_CR_ID | Amount |
      | gjDone        | gjDebit       | gjCredit      | 100.00 |
      | gjDraft       | gjDebit       | gjCredit      | 200.00 |
    And the GL journal "gjDone" is completed
    And the GL journal "gjDone" is eventually posted with PostingType "S" and 2 Fact_Acct lines
    And the posting requests of GL journal "gjDraft" are remembered

    When the GL_Journal_ChangePostingType process is invoked for GL journal batch "gjBatch5" with PostingType "A"

    Then the GL journal "gjDraft" has GL_Journal.PostingType "A"
    And the GL journal "gjDraft" is not posted and has no Fact_Acct lines
    And GL journal "gjDraft" got no new posting request and no note
    And the GL journal "gjDone" is eventually posted with PostingType "A" and 2 Fact_Acct lines
