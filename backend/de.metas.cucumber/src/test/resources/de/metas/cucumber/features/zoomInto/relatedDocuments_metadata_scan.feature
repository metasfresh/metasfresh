@from:cucumber
@ghActions:run_on_executor6
Feature: Related Documents metadata scan
  Verify that key source tables have related documents configured
  and that expected target windows are present in the candidate list.

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  Scenario: Key source tables have minimum related document targets
    Then the following source tables have at least the listed related document targets:
      | SourceTable | MinTargetCount |
      | C_Order     | 3              |
      | C_Invoice   | 2              |
      | M_InOut     | 2              |
      | C_BPartner  | 2              |

  @from:cucumber
  Scenario: C_Order related documents include expected target windows
    Then related documents for source table "C_Order" include targets:
      | TargetWindowName    |
      | Invoice (Customer)  |
      | Shipment (Customer) |
