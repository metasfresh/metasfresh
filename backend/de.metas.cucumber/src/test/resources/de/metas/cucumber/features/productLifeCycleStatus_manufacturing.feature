@from:cucumber
@allure.label.epic:E0380_Masterdata_Products
@allure.label.feature:F6000_Maintain_Product_Data
@ghActions:run_on_executor4
Feature: product life-cycle status enforcement on manufacturing orders
## F6000: Maintain Product Data
# A product's life-cycle status (BBS-Status) gates whether it may be manufactured:
# - "G" (Gesperrt) blocks manufacturing.
# The PP_Order model interceptor rejects the order at creation, before it is ever saved.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]

  @Id:S31039_TC2
  Scenario: a Gesperrt product's manufacturing order is rejected at creation
    # a blocked finished good plus its component
    And metasfresh contains M_Products:
      | Identifier    | ProductLifeCycleStatus |
      | finishedGood  | G                      |
      | component     |                        |

    And metasfresh contains PP_Product_BOM
      | Identifier | M_Product_ID.Identifier | ValidFrom  |
      | bom        | finishedGood            | 2021-01-02 |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID.Identifier | M_Product_ID.Identifier | ValidFrom  | QtyBatch |
      | bom_line   | bom                          | component               | 2021-01-02 | 10       |
    And the PP_Product_BOM identified by bom is completed

    And load S_Resource:
      | S_Resource_ID.Identifier | S_Resource_ID |
      | testResource             | 540006        |

    # the completed BOM lets createOrder reach the save; the interceptor's life-cycle guard then rejects it
    And create PP_Order expecting error:
      | PP_Order_ID.Identifier | DocBaseType | M_Product_ID.Identifier | QtyEntered | S_Resource_ID.Identifier | DateOrdered             | DatePromised            | DateStartSchedule       | ErrorCode                         |
      | ppOrder_blocked        | MOP         | finishedGood            | 10         | testResource             | 2021-04-17T23:59:00.00Z | 2021-04-17T23:59:00.00Z | 2021-04-17T23:59:00.00Z | M_Product_BBSStatus_ActionBlocked |
