@from:cucumber
@ghActions:run_on_executor3
Feature: M_Product.SyncEANAndGTIN sysconfig controls GTIN/UPC/EAN_CU sync
  The sysconfig M_Product.SyncEANAndGTIN controls whether GTIN, UPC, and EAN_CU fields
  are automatically synchronized on M_Product and C_BPartner_Product.
  When Y (default), fields are synced. When N, fields can be maintained independently.
  EAN13_ProductCode behavior is always active regardless of the sysconfig value.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-03-10T13:30:00+01:00[Europe/Berlin]

  @from:cucumber
  @Id:S0612_010
  Scenario: TC-01 M_Product - Sync ON - GTIN auto-fills UPC
    Given set sys config boolean value true for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc01     |
    When update M_Product:
      | Identifier | GTIN          |
      | p_tc01     | 4012345678901 |
    Then M_Product is verified:
      | Identifier | GTIN          | UPC           |
      | p_tc01     | 4012345678901 | 4012345678901 |

  @from:cucumber
  @Id:S0612_020
  Scenario: TC-02 M_Product - Sync ON - UPC auto-fills GTIN
    Given set sys config boolean value true for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc02     |
    When update M_Product:
      | Identifier | UPC           |
      | p_tc02     | 9876543210987 |
    Then M_Product is verified:
      | Identifier | GTIN          | UPC           |
      | p_tc02     | 9876543210987 | 9876543210987 |

  @from:cucumber
  @Id:S0612_030
  Scenario: TC-03 M_Product - Sync OFF - GTIN does NOT auto-fill UPC
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc03     |
    When update M_Product:
      | Identifier | GTIN          |
      | p_tc03     | 4012345678901 |
    Then M_Product is verified:
      | Identifier | GTIN          | UPC  |
      | p_tc03     | 4012345678901 | null |

  @from:cucumber
  @Id:S0612_040
  Scenario: TC-04 M_Product - Sync OFF - UPC does NOT auto-fill GTIN
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc04     |
    When update M_Product:
      | Identifier | UPC           |
      | p_tc04     | 9876543210987 |
    Then M_Product is verified:
      | Identifier | GTIN | UPC           |
      | p_tc04     | null | 9876543210987 |

  @from:cucumber
  @Id:S0612_050
  Scenario: TC-05 M_Product - Sync OFF - Clearing UPC does NOT clear GTIN
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc05     |
    # Set both fields independently (sync OFF, so no auto-fill)
    And update M_Product:
      | Identifier | GTIN          |
      | p_tc05     | 4012345678901 |
    And update M_Product:
      | Identifier | UPC           |
      | p_tc05     | 9876543210987 |
    # Clear UPC
    When update M_Product:
      | Identifier | UPC  |
      | p_tc05     | null |
    Then M_Product is verified:
      | Identifier | GTIN          | UPC  |
      | p_tc05     | 4012345678901 | null |

  @from:cucumber
  @Id:S0612_060
  Scenario: TC-06 M_Product - Sync OFF - Clearing GTIN does NOT clear UPC
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc06     |
    And update M_Product:
      | Identifier | GTIN          |
      | p_tc06     | 4012345678901 |
    And update M_Product:
      | Identifier | UPC           |
      | p_tc06     | 9876543210987 |
    # Clear GTIN
    When update M_Product:
      | Identifier | GTIN |
      | p_tc06     | null |
    Then M_Product is verified:
      | Identifier | GTIN | UPC           |
      | p_tc06     | null | 9876543210987 |

  @from:cucumber
  @Id:S0612_070
  Scenario: TC-07 M_Product - Sync OFF - EAN13_ProductCode does not clear GTIN and UPC
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc07     |
    And update M_Product:
      | Identifier | GTIN          |
      | p_tc07     | 4012345678901 |
    And update M_Product:
      | Identifier | UPC           |
      | p_tc07     | 9876543210987 |
    When update M_Product:
      | Identifier | EAN13_ProductCode |
      | p_tc07     | 123456789         |
    Then M_Product is verified:
      | Identifier | GTIN          | UPC           | EAN13_ProductCode |
      | p_tc07     | 4012345678901 | 9876543210987 | 123456789         |

  @from:cucumber
  @Id:S0612_080
  Scenario: TC-08 M_Product - Sync ON - EAN13_ProductCode clears GTIN and UPC
    Given set sys config boolean value true for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc08     |
    And update M_Product:
      | Identifier | GTIN          |
      | p_tc08     | 4012345678901 |
    When update M_Product:
      | Identifier | EAN13_ProductCode |
      | p_tc08     | 123456789         |
    Then M_Product is verified:
      | Identifier | GTIN | UPC  | EAN13_ProductCode |
      | p_tc08     | null | null | 123456789         |

  @from:cucumber
  @Id:S0612_090
  Scenario: TC-09 C_BPartner_Product - Sync OFF - GTIN does NOT auto-fill CU-EAN or CU-UPC
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc09     |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer |
      | bp_tc09    | Y        | Y          |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID | C_BPartner_ID | M_Product_ID |
      | bpp_tc09              | bp_tc09       | p_tc09       |
    When update C_BPartner_Product:
      | Identifier | GTIN          |
      | bpp_tc09   | 4012345678901 |
    Then C_BPartner_Product is verified:
      | C_BPartner_Product_ID.Identifier | GTIN          | EAN_CU | UPC  |
      | bpp_tc09                         | 4012345678901 | null   | null |

  @from:cucumber
  @Id:S0612_100
  Scenario: TC-10 C_BPartner_Product - Sync OFF - CU-EAN does NOT auto-fill GTIN or CU-UPC
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc10     |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer |
      | bp_tc10    | Y        | Y          |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID | C_BPartner_ID | M_Product_ID |
      | bpp_tc10              | bp_tc10       | p_tc10       |
    When update C_BPartner_Product:
      | Identifier | EAN_CU        |
      | bpp_tc10   | 4012345678901 |
    Then C_BPartner_Product is verified:
      | C_BPartner_Product_ID.Identifier | GTIN | EAN_CU        | UPC  |
      | bpp_tc10                         | null | 4012345678901 | null |

  @from:cucumber
  @Id:S0612_110
  Scenario: TC-11 C_BPartner_Product - Sync OFF - EAN13_ProductCode does not clear all fields
    Given set sys config boolean value false for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc11     |
    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer |
      | bp_tc11    | Y        | Y          |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID | C_BPartner_ID | M_Product_ID |
      | bpp_tc11              | bp_tc11       | p_tc11       |
    And update C_BPartner_Product:
      | Identifier | GTIN          |
      | bpp_tc11   | 4012345678901 |
    And update C_BPartner_Product:
      | Identifier | EAN_CU        |
      | bpp_tc11   | 4012345678902 |
    And update C_BPartner_Product:
      | Identifier | UPC           |
      | bpp_tc11   | 4012345678903 |
    When update C_BPartner_Product:
      | Identifier | EAN13_ProductCode |
      | bpp_tc11   | 6789              |
    Then C_BPartner_Product is verified:
      | C_BPartner_Product_ID.Identifier | GTIN          | EAN_CU        | UPC           | EAN13_ProductCode |
      | bpp_tc11                         | 4012345678901 | 4012345678902 | 4012345678903 | 6789              |

  @from:cucumber
  @Id:S0612_120
  Scenario: TC-12 Sysconfig missing - behaves as sync ON
    # Delete the sysconfig to simulate "missing"
    Given set sys config String value null for sys config M_Product.SyncEANAndGTIN
    And metasfresh contains M_Products:
      | Identifier |
      | p_tc12     |
    When update M_Product:
      | Identifier | GTIN          |
      | p_tc12     | 4012345678901 |
    Then M_Product is verified:
      | Identifier | GTIN          | UPC           |
      | p_tc12     | 4012345678901 | 4012345678901 |
