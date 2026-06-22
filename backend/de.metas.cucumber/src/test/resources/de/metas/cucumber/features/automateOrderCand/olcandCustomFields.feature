@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00101
@topic:orderCandidate
@ghActions:run_on_executor3
Feature: OLCand sales REST API — custom and first-class fields
  As a sales integration,
  I send extra data on OLCand create (extendedProps via v1, promotionCode, isWithoutCharge, reason)
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
  Scenario: Generic custom-column round-trip — Description flagged on C_OLCand and C_Order propagates through process
    # Description exists on both C_OLCand and C_Order and is not overwritten by OLCandOrderFactory's
    # explicit field assignments. When flagged IsRestAPICustomColumn=Y on both tables, copyCustomColumns
    # transfers the value from the OLCand to the order automatically.
    # The flag is cleared at scenario end so sibling scenarios on the same executor are not affected.
    And update AD_Column:
      | TableName | ColumnName  | OPT.IsRestAPICustomColumn |
      | C_OLCand  | Description | true                      |
      | C_Order   | Description | true                      |
    And the metasfresh cache is reset
    And we wait for 2000 ms

    # v1 API used because it supports extendedProps (not available in v2).
    # The bpartner is created inline by the v1 upsert endpoint; a unique code ensures idempotency.
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v1/sales/order/candidates' and fulfills with '201' status code
    """
{
    "org": { "code": "001" },
    "externalLineId": "olcand_custom_col_line",
    "externalHeaderId": "olcand_custom_col_hdr",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.ordercandidate",
    "bpartner": {
        "bpartner": {
            "code": "S30446-TEST-BP",
            "name": "S30446 Test BPartner",
            "customer": true
        },
        "location": {
            "countryCode": "DE",
            "city": "Test City"
        }
    },
    "dateRequired": "2022-05-20",
    "dateOrdered": "2022-05-17",
    "poReference": "TEST-S30446-10",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": 2005577,
    "qty": 5,
    "price": 10,
    "currencyCode": "EUR",
    "discount": 0,
    "extendedProps": {
        "Description": "custom-round-trip-value"
    }
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "result": [
    {
      "extendedProps": {
        "Description": "custom-round-trip-value"
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

    Then validate customColumns:
      | OPT.C_Order_ID.Identifier | CustomColumnJSONValue                     |
      | order_1                   | {"Description":"custom-round-trip-value"} |

    # Restore the column flag so sibling scenarios on the same executor are not affected
    And update AD_Column:
      | TableName | ColumnName  | OPT.IsRestAPICustomColumn |
      | C_OLCand  | Description | false                     |
      | C_Order   | Description | false                     |
    And the metasfresh cache is reset

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00101
  @topic:orderCandidate
  @Id:S30446_20
  Scenario: First-class fields — promotionCode, isWithoutCharge, reason propagate from OLCand to order and order line
    # v1 API used because promotionCode, isWithoutCharge and reason are v1-only request fields.
    Given metasfresh contains C_PromotionCode:
      | Identifier | Value        |
      | promoCode  | PROMO-S30446 |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v1/sales/order/candidates' and fulfills with '201' status code
    """
{
    "org": { "code": "001" },
    "externalLineId": "olcand_firstclass_line",
    "externalHeaderId": "olcand_firstclass_hdr",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.ordercandidate",
    "bpartner": {
        "bpartner": {
            "code": "S30446-TEST-BP",
            "name": "S30446 Test BPartner",
            "customer": true
        },
        "location": {
            "countryCode": "DE",
            "city": "Test City"
        }
    },
    "dateRequired": "2022-05-20",
    "dateOrdered": "2022-05-17",
    "poReference": "TEST-S30446-20",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": 2005577,
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
      | C_Order_ID.Identifier | DateOrdered | DocBaseType | currencyCode | processed | DocStatus | C_PromotionCode_ID |
      | order_2               | 2022-05-17  | SOO         | EUR          | true      | CO        | promoCode          |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsWithoutCharge | OPT.Reason |
      | ol_2                      | order_2               | product_1               | 3          | true                | P          |

  @from:cucumber
  @allure.label.epic:E0100_Sales
  @allure.label.feature:F00101
  @topic:orderCandidate
  @Id:S30446_30
  Scenario: Unknown promotion code — API returns a validation error, no order is created
    # v1 API returns HTTP 400 (bad request) when promotionCode is not found.
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v1/sales/order/candidates' and fulfills with '400' status code
    """
{
    "org": { "code": "001" },
    "externalLineId": "olcand_badpromo_line",
    "externalHeaderId": "olcand_badpromo_hdr",
    "externalSystemCode": "Shopware6",
    "dataSource": "int-Shopware",
    "dataDest": "int-DEST.de.metas.ordercandidate",
    "bpartner": {
        "bpartner": {
            "code": "S30446-TEST-BP",
            "name": "S30446 Test BPartner",
            "customer": true
        },
        "location": {
            "countryCode": "DE",
            "city": "Test City"
        }
    },
    "dateRequired": "2022-05-20",
    "dateOrdered": "2022-05-17",
    "poReference": "TEST-S30446-30",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": 2005577,
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
  "errors": [{}]
}
    """
