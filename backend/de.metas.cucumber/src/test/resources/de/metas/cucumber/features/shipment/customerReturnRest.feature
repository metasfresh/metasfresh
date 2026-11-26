@from:cucumber
@allure.label.epic:E0110_Sales
@allure.label.feature:F00200_Sales_Order
@allure.label.feature:F18030_POS_Checkout
@ghActions:run_on_executor6
Feature: Customer Return via REST API
## Verifies that POST /api/v2/receipts creates a customer return even when the
## returned line carries no shipment and no sales order.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

   #Ensure a quality return warehouse exists so that we can process customer returns
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | IsQualityReturnWarehouse |
      | wh_CRR         | Y                        |

    And metasfresh contains M_Products:
      | Identifier  | Value       |
      | product_CRR | product_CRR |

    And metasfresh contains C_BPartners:
      | Identifier   | Value        | OPT.IsVendor | OPT.IsCustomer |
      | bpartner_CRR | bpartner_CRR | N            | Y              |

    And update AD_SysConfig with C_BPartner_ID:
      | Name                                     | C_BPartner_ID |
      | sysconfig.customerReturn.unknownBpartner | bpartner_CRR  |

  ##############################################################################
  @from:cucumber
  @allure.label.epic:E0110_Sales
  @allure.label.feature:F00200_Sales_Order
  @allure.label.feature:F18030_POS_Checkout
  @Id:S28210_TC6
  Scenario: Customer return via REST with no shipment and no sales order
    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/receipts' and fulfills with '200' status code
    """
{
  "returnList": [
    {
      "orgCode": "001",
      "productSearchKey": "product_CRR",
      "movementQuantity": 5
    }
  ]
}
"""
    Then process single return response
      | M_InOut_ID |
      | return_CRR |

    And validate the created material receipt
      | M_InOut_ID | C_BPartner_ID | DocStatus | M_Warehouse_ID | MovementType |
      | return_CRR | bpartner_CRR  | CO        | wh_CRR         | C+           |

    And validate the created material receipt lines
      | M_InOut_ID | M_Product_ID | movementqty | processed |
      | return_CRR | product_CRR  | 5           | true      |
