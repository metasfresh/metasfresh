@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00101
@topic:orderCandidate
@ghActions:run_on_executor3
Feature: OLCand sales REST API — custom and first-class fields
  As a sales integration,
  I send extra data on OLCand create (extendedProps, promotionCode, isWithoutCharge, reason)
  and verify it lands on the resulting order and order line.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And preexisting test data is put into tableData
      | C_BPartner_ID.Identifier | C_BPartner_ID | C_BPartner_Location_ID.Identifier | C_BPartner_Location_ID | M_Product_ID.Identifier | M_Product_ID |
      | bpartner_1               | 2156425       | bpartnerLocation_1                | 2205175                | product_1               | 2005577      |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00101
  @topic:orderCandidate
  @Id:S30446_10
  Scenario: Generic custom-column round-trip — header-level column flagged on C_OLCand and C_Order propagates through process
    # Flag Dt204_InternalInfo on C_OLCand and C_Order as IsRestAPICustomColumn=Y.
    # This column is a free-text field not overwritten by document completion logic.
    # The column is cleared at scenario end to avoid polluting sibling tests.
    And update AD_Column:
      | TableName | ColumnName         | OPT.IsRestAPICustomColumn |
      | C_OLCand  | Dt204_InternalInfo | true                      |
      | C_Order   | Dt204_InternalInfo | true                      |
    And the metasfresh cache is reset
    And we wait for 2000 ms

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v1/sales/order/candidates' and fulfills with '201' status code
    """
{
    "org": { "code": "DE06" },
    "externalLineId": "olcand_custom_col_line",
    "externalHeaderId": "olcand_custom_col_hdr",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.ordercandidate",
    "bpartner": {
        "bpartner": { "code": "G0001" }
    },
    "dateRequired": "2022-05-20",
    "dateOrdered": "2022-05-17",
    "poReference": "TEST-S30446-10",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "product": { "code": "P002737" },
    "qty": 5,
    "price": 10,
    "currencyCode": "EUR",
    "discount": 0,
    "extendedProps": {
        "Dt204_InternalInfo": "custom-round-trip-value"
    }
}
"""

    Then the metasfresh REST-API responds with
    """
{
  "result": [
    {
      "extendedProps": {
        "Dt204_InternalInfo": "custom-round-trip-value"
      }
    }
  ]
}
    """

    And process metasfresh response JsonOLCandCreateBulkResponse v1
      | C_OLCand_ID.Identifier |
      | olcand_1               |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
    """
{
    "externalHeaderId": "olcand_custom_col_hdr",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_1               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId        | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus |
      | order_1               | olcand_custom_col_hdr | bpartner_1               | bpartnerLocation_1                | 2022-05-17  | SOO         | EUR          | A            | P               | true      | CO        |

    Then validate customColumns:
      | OPT.C_Order_ID.Identifier | CustomColumnJSONValue                               |
      | order_1                   | {"Dt204_InternalInfo":"custom-round-trip-value"}    |

    # Restore the column flag so sibling scenarios on the same executor are not affected
    And update AD_Column:
      | TableName | ColumnName         | OPT.IsRestAPICustomColumn |
      | C_OLCand  | Dt204_InternalInfo | false                     |
      | C_Order   | Dt204_InternalInfo | false                     |
    And the metasfresh cache is reset

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00101
  @topic:orderCandidate
  @Id:S30446_20
  Scenario: First-class fields — promotionCode, isWithoutCharge, reason propagate from OLCand to order and order line
    Given metasfresh contains C_PromotionCode:
      | Identifier | Value         |
      | promoCode  | PROMO-S30446  |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v1/sales/order/candidates' and fulfills with '201' status code
    """
{
    "org": { "code": "DE06" },
    "externalLineId": "olcand_firstclass_line",
    "externalHeaderId": "olcand_firstclass_hdr",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.ordercandidate",
    "bpartner": {
        "bpartner": { "code": "G0001" }
    },
    "dateRequired": "2022-05-20",
    "dateOrdered": "2022-05-17",
    "poReference": "TEST-S30446-20",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "product": { "code": "P002737" },
    "qty": 3,
    "price": 10,
    "currencyCode": "EUR",
    "discount": 0,
    "promotionCode": "PROMO-S30446",
    "isWithoutCharge": true,
    "reason": "P"
}
"""

    # The v1 create response does not echo back first-class fields (promotionCode/isWithoutCharge/reason).
    # We verify propagation at the C_Order / C_OrderLine level below.
    And process metasfresh response JsonOLCandCreateBulkResponse v1
      | C_OLCand_ID.Identifier |
      | olcand_2               |

    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
    """
{
    "externalHeaderId": "olcand_firstclass_hdr",
    "externalSystemCode": "Shopware6",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""

    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_2               |

    And validate the created orders
      | C_Order_ID.Identifier | OPT.ExternalId        | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | processed | DocStatus | C_PromotionCode_ID |
      | order_2               | olcand_firstclass_hdr | bpartner_1               | bpartnerLocation_1                | 2022-05-17  | SOO         | EUR          | A            | P               | true      | CO        | promoCode          |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsWithoutCharge | OPT.Reason |
      | ol_2                      | order_2               | product_1               | 3          | true                | P          |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00101
  @topic:orderCandidate
  @Id:S30446_30
  Scenario: Unknown promotion code — API returns a validation error, no order is created
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v1/sales/order/candidates' and fulfills with '400' status code
    """
{
    "org": { "code": "DE06" },
    "externalLineId": "olcand_badpromo_line",
    "externalHeaderId": "olcand_badpromo_hdr",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.ordercandidate",
    "bpartner": {
        "bpartner": { "code": "G0001" }
    },
    "dateRequired": "2022-05-20",
    "dateOrdered": "2022-05-17",
    "poReference": "TEST-S30446-30",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "product": { "code": "P002737" },
    "qty": 1,
    "price": 10,
    "currencyCode": "EUR",
    "discount": 0,
    "promotionCode": "NO-SUCH-PROMO-CODE-S30446"
}
"""

    Then the metasfresh REST-API responds with
    """
{
  "errors": [
    {}
  ]
}
    """
