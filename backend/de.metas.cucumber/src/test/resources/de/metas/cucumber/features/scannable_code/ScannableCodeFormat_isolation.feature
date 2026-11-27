@from:cucumber
@ghActions:run_on_executor7
Feature: Scannable code format steps parse with the scenario's own formats only

  Active formats are global (the first format that reads a code wins), so a format that exists independently of the scenario,
  e.g. one added by a customer migration, must not change what step "parse scanned code and expect:" reports for the scenario's formats.
  The step makes the scenario's formats the only active ones while it parses and restores the others afterwards.

  @from:cucumber
  Scenario: a format that exists independently of the scenario does not change the parse result
    Given infrastructure and metasfresh are running
    And metasfresh contains C_ScannableCode_Format:
      | Identifier | Name                         | IsUnrelated |
      | other      | isolation test, other format | true        |
      | f1         | isolation test, scenario     | false       |
    # the other format is created first, so it has the lower ID and is tried first
    And metasfresh contains C_ScannableCode_Format_Part:
      | C_ScannableCode_Format_ID | StartNo | EndNo | DataType     | ConstantValue |
      | other                     | 1       | 4     | PRODUCT_CODE |               |
    And metasfresh contains C_ScannableCode_Format_Part:
      | C_ScannableCode_Format_ID | StartNo | EndNo | DataType     | ConstantValue |
      | f1                        | 1       | 4     | PRODUCT_CODE |               |
      | f1                        | 5       | 6     | CONSTANT     | XX            |
      | f1                        | 7       | 0     | PACK_ID      |               |
    Then parse scanned code and expect:
      | ScannedCode | ProductNo | PackId | IsMatching |
      | P100XX7     | P100      | 7      | true       |
      | P100YY7     |           |        | false      |
