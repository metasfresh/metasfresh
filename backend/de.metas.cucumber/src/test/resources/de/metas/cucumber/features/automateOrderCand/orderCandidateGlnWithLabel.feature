@from:cucumber
@topic:orderCandidate
@ghActions:run_on_executor3
Feature: Order Candidate with GLN and Lookup Label (glnl-) identifier
  As an API user
  I want to create order candidates using BPartner GLN with lookup label
  So that I can differentiate between BPartners sharing the same GLN

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-11-02T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    # Pricing System Setup
    And metasfresh contains M_PricingSystems
      | Identifier | Name                     | Value                    | OPT.IsActive |
      | ps_glnl    | pricing_system_glnl_test | pricing_system_glnl_test | true         |

    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name         | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_glnl    | ps_glnl                       | DE                        | EUR                 | pl_glnl_test | true  | false         | 2              | true         |

    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name          | ValidFrom  |
      | plv_glnl   | pl_glnl                   | plv_glnl_test | 2025-11-01 |

    # Product Setup
    And metasfresh contains M_Products:
      | Identifier   | Name              |
      | product_glnl | product_glnl_test |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_glnl    | plv_glnl                          | product_glnl            | 10.0     | PCE               | Normal                        |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0482_100
  Scenario: Create order candidates for different BPartners sharing same GLN using glnl- identifier
    # Setup: Create 2 BPartners with same GLN but different Lookup Labels
    Given metasfresh contains C_BPartners without locations:
      | Identifier    | Name                | IsCustomer | M_PricingSystem_ID.Identifier | Lookup_Label | GLN           | deliveryRule |
      | bpartner_divA | Division A Customer | Y          | ps_glnl                       | DIVISION_A   | 1234567890123 | F            |
      | bpartner_divB | Division B Customer | Y          | ps_glnl                       | DIVISION_B   | 1234567890123 | F            |

    And metasfresh contains C_BPartner_Locations:
      | Identifier      | GLN           | C_BPartner_ID.Identifier |
      | bplocation_divA | 1234567890123 | bpartner_divA            |
      | bplocation_divB | 1234567890123 | bpartner_divB            |

    # Test: Create order candidate for Division A
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
      """
      {
        "orgCode": "001",
        "externalHeaderId": "GLNL_TEST_001",
        "externalLineId": "GLNL_LINE_001",
        "externalSystemCode": "Shopware6",
        "dataSource": "int-Test",
        "bpartner": {
          "bpartnerIdentifier": "glnl-1234567890123_DIVISION_A",
          "bpartnerLocationIdentifier": "glnl-1234567890123_DIVISION_A"
        },
        "dateRequired": "2025-11-15",
        "dateOrdered": "2025-11-02",
        "orderDocType": "SalesOrder",
        "paymentTerm": "val-1000002",
        "productIdentifier": "val-product_glnl_test",
        "qty": 5,
        "price": 10.00,
        "currencyCode": "EUR",
        "discount": 0,
        "poReference": "PO_GLNL_DIV_A",
        "deliveryViaRule": "S",
        "deliveryRule": "F"
      }
      """
    And after not more than 60s, C_OLCand is found
      | C_OLCand_ID.Identifier | Processed | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.POReference | QtyEntered | M_Product_ID | ExternalSystem.Value |
      | olcand_divA            | false     | bpartner_divA                | bplocation_divA                       | PO_GLNL_DIV_A   | 5          | product_glnl | Shopware6            |

    # Test: Create order candidate for Division B
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
      """
      {
        "orgCode": "001",
        "externalHeaderId": "GLNL_TEST_002",
        "externalLineId": "GLNL_LINE_002",
        "externalSystemCode": "Shopware6",
        "dataSource": "int-Test",
        "bpartner": {
          "bpartnerIdentifier": "glnl-1234567890123_DIVISION_B",
          "bpartnerLocationIdentifier": "glnl-1234567890123_DIVISION_B"
        },
        "dateRequired": "2025-11-15",
        "dateOrdered": "2025-11-02",
        "orderDocType": "SalesOrder",
        "paymentTerm": "val-1000002",
        "productIdentifier": "val-product_glnl_test",
        "qty": 3,
        "price": 10.00,
        "currencyCode": "EUR",
        "discount": 0,
        "poReference": "PO_GLNL_DIV_B",
        "deliveryViaRule": "S",
        "deliveryRule": "F"
      }
      """

    Then after not more than 60s, C_OLCand is found
      | C_OLCand_ID.Identifier | Processed | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.POReference | QtyEntered | M_Product_ID | ExternalSystem.Value |
      | olcand_divB            | false     | bpartner_divB                | bplocation_divB                       | PO_GLNL_DIV_B   | 3          | product_glnl | Shopware6            |

    # Validate both OLCands are created correctly with no errors
    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsError | M_Product_ID | Processed |
      | olcand_divA            | bpartner_divA            | bplocation_divA                   | false   | product_glnl | false     |
      | olcand_divB            | bpartner_divB            | bplocation_divB                   | false   | product_glnl | false     |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0482_200
  Scenario: GLN with label works correctly when only one BPartner has that label
    # Setup: Create single BPartner with GLN and label
    Given metasfresh contains C_BPartners without locations:
      | Identifier         | Name                 | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.Lookup_Label | GLN           | deliveryRule |
      | bpartner_warehouse | Warehouse 1 Customer | Y              | ps_glnl                       | WAREHOUSE_1      | 9876543210987 | F            |

    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN           | C_BPartner_ID.Identifier |
      | bplocation_warehouse | 9876543210987 | bpartner_warehouse       |

    # Test: Create order candidate with glnl- identifier
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
      """
      {
        "orgCode": "001",
        "externalHeaderId": "GLNL_TEST_WH1",
        "externalLineId": "GLNL_LINE_WH1",
        "externalSystemCode": "Shopware6",
        "dataSource": "int-Test",
        "bpartner": {
          "bpartnerIdentifier": "glnl-9876543210987_WAREHOUSE_1",
          "bpartnerLocationIdentifier": "glnl-9876543210987_WAREHOUSE_1"
        },
        "dateRequired": "2025-11-15",
        "dateOrdered": "2025-11-02",
        "orderDocType": "SalesOrder",
        "paymentTerm": "val-1000002",
        "productIdentifier": "val-product_glnl_test",
        "qty": 7,
        "price": 10.00,
        "currencyCode": "EUR",
        "discount": 0,
        "poReference": "PO_GLNL_WH1",
        "deliveryViaRule": "S",
        "deliveryRule": "F"
      }
      """

    Then after not more than 60s, C_OLCand is found
      | C_OLCand_ID.Identifier | Processed | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.POReference | QtyEntered | M_Product_ID | ExternalSystem.Value |
      | olcand_wh1             | false     | bpartner_warehouse           | bplocation_warehouse                  | PO_GLNL_WH1     | 7          | product_glnl | Shopware6            |

    And validate C_OLCand:
      | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsError | M_Product_ID |
      | olcand_wh1             | bpartner_warehouse       | bplocation_warehouse              | false   | product_glnl |

  @from:cucumber
  @topic:orderCandidate
  @Id:S0482_300
  Scenario: Process order candidates to orders with glnl- identifiers
    # Setup: Create 2 BPartners with same GLN but different Lookup Labels
    Given metasfresh contains C_BPartners without locations:
      | Identifier    | Name                | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.Lookup_Label | GLN           | deliveryRule |
      | bpartner_divA | Division A Customer | Y              | ps_glnl                       | DIVISION_A       | 1234567890123 | F            |
      | bpartner_divB | Division B Customer | Y              | ps_glnl                       | DIVISION_B       | 1234567890123 | F            |

    And metasfresh contains C_BPartner_Locations:
      | Identifier      | GLN           | C_BPartner_ID.Identifier |
      | bplocation_divA | 1234567890123 | bpartner_divA            |
      | bplocation_divB | 1234567890123 | bpartner_divB            |

    # Create order candidates for both divisions
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
      """
      {
        "orgCode": "001",
        "externalHeaderId": "GLNL_E2E_001",
        "externalLineId": "GLNL_E2E_LINE_001",
        "externalSystemCode": "Shopware6",
        "dataSource": "int-Test",
        "bpartner": {
          "bpartnerIdentifier": "glnl-1234567890123_DIVISION_A",
          "bpartnerLocationIdentifier": "glnl-1234567890123_DIVISION_A"
        },
        "dateRequired": "2025-11-15",
        "dateOrdered": "2025-11-02",
        "orderDocType": "SalesOrder",
        "paymentTerm": "val-1000002",
        "productIdentifier": "val-product_glnl_test",
        "qty": 15,
        "price": 10.00,
        "currencyCode": "EUR",
        "discount": 0,
        "poReference": "PO_E2E_DIV_A",
        "deliveryViaRule": "S",
        "deliveryRule": "F"
      }
      """

    Then a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
      """
      {
        "orgCode": "001",
        "externalHeaderId": "GLNL_E2E_002",
        "externalLineId": "GLNL_E2E_LINE_002",
        "externalSystemCode": "Shopware6",
        "dataSource": "int-Test",
        "bpartner": {
          "bpartnerIdentifier": "glnl-1234567890123_DIVISION_B",
          "bpartnerLocationIdentifier": "glnl-1234567890123_DIVISION_B"
        },
        "dateRequired": "2025-11-15",
        "dateOrdered": "2025-11-02",
        "orderDocType": "SalesOrder",
        "paymentTerm": "val-1000002",
        "productIdentifier": "val-product_glnl_test",
        "qty": 13,
        "price": 10.00,
        "currencyCode": "EUR",
        "discount": 0,
        "poReference": "PO_E2E_DIV_B",
        "deliveryViaRule": "S",
        "deliveryRule": "F"
      }
      """

    # Verify OLCands are created correctly
    Then after not more than 60s, C_OLCand is found
      | C_OLCand_ID.Identifier | C_BPartner_ID | OPT.C_BPartner_Location_ID.Identifier | QtyEntered | M_Product_ID | ExternalSystem.Value |
      | olcand_e2e_divA        | bpartner_divA                | bplocation_divA                       | 15          | product_glnl | Shopware6            |
      | olcand_e2e_divB        | bpartner_divB                | bplocation_divB                       | 13          | product_glnl | Shopware6            |

    # Process order candidates to orders for Division A
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
      """
      {
        "externalHeaderId": "GLNL_E2E_001",
        "externalSystemCode": "Shopware6",
        "ship": false,
        "invoice": false,
        "closeOrder": false
      }
      """
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_e2e_divA        |

    # Process order candidates to orders for Division B
    When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
      """
      {
        "externalHeaderId": "GLNL_E2E_002",
        "externalSystemCode": "Shopware6",
        "ship": false,
        "invoice": false,
        "closeOrder": false
      }
      """
    Then process metasfresh response
      | C_Order_ID.Identifier |
      | order_e2e_divB        |

    # Validate both orders are created correctly with correct BPartners
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus |
      | order_e2e_divA        | bpartner_divA            | bplocation_divA                   | true      | CO        |
      | order_e2e_divB        | bpartner_divB            | bplocation_divB                   | true      | CO        |
