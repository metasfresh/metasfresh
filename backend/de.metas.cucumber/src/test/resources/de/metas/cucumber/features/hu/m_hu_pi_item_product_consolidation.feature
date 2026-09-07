@allure.label.epic:E0370_Intralogistic_HUs
@allure.label.feature:F5001_1_Consolidate_CU_TU_Allocation
@from:cucumber
@F5001_1
Feature: M_HU_PI_Item_Product consolidation SQL function tests
  Verifies that the m_hu_pi_item_product_consolidate() and
  m_hu_pi_item_product_consolidate_report() SQL functions correctly:
  - Normalize GTIN/EAN_TU fields
  - Consolidate duplicate records (same Product + GTIN + PI_Item)
  - Detect and report conflicts

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    # Common test data: product, UOM, BPartners, PI hierarchy
    And metasfresh contains M_Products:
      | Identifier     |
      | consol_product |
    And metasfresh contains C_BPartners:
      | Identifier  | Name              | IsCustomer | IsVendor |
      | consol_bp1  | Consolidation BP1 | Y          | N        |
      | consol_bp2  | Consolidation BP2 | Y          | N        |
    And metasfresh contains M_HU_PI:
      | Identifier | Name            |
      | consol_pi  | Consolidation PI |
    And metasfresh contains M_HU_PI_Version:
      | Identifier     | M_HU_PI_ID | HU_UnitType |
      | consol_pi_ver  | consol_pi  | TU          |
    And metasfresh contains M_HU_PI_Item:
      | Identifier      | M_HU_PI_Version_ID | ItemType |
      | consol_pi_item  | consol_pi_ver      | MI       |

  @from:cucumber
  Scenario: GTIN normalization — EAN_TU copied to GTIN, then EAN_TU cleared
    Given metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | EAN_TU        |
      | pip_ean1   | consol_product | consol_pi_item  | 10  | PCE               | 4006381333931 |
    When M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean='true' and p_consolidate='false'
    Then M_HU_PI_Item_Product identified by 'pip_ean1' has GTIN='4006381333931' and EAN_TU=null

  @from:cucumber
  Scenario: GTIN normalization — EAN_TU cleared when same as GTIN
    Given metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | EAN_TU        |
      | pip_dup1   | consol_product | consol_pi_item  | 10  | PCE               | 4006381333931 | 4006381333931 |
    When M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean='true' and p_consolidate='false'
    Then M_HU_PI_Item_Product identified by 'pip_dup1' has GTIN='4006381333931' and EAN_TU=null

  @from:cucumber
  Scenario: Consolidation — identical records merged, partner-less record survives
    Given metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          |
      | pip_nopart | consol_product | consol_pi_item  | 10  | PCE               | 4007817525074 |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | C_BPartner_ID |
      | pip_bp1    | consol_product | consol_pi_item  | 10  | PCE               | 4007817525074 | consol_bp1    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | C_BPartner_ID |
      | pip_bp2    | consol_product | consol_pi_item  | 10  | PCE               | 4007817525074 | consol_bp2    |
    When M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean='false' and p_consolidate='true'
    Then M_HU_PI_Item_Product identified by 'pip_nopart' has IsActive='Y'
    And M_HU_PI_Item_Product identified by 'pip_bp1' has IsActive='N'
    And M_HU_PI_Item_Product identified by 'pip_bp2' has IsActive='N'

  @from:cucumber
  Scenario: Consolidation — different date ranges produce widest range on survivor
    Given metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | ValidFrom  | ValidTo    | C_BPartner_ID |
      | pip_date1  | consol_product | consol_pi_item  | 10  | PCE               | 4015400346353 | 2020-01-01 | 2024-12-31 | consol_bp1    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | ValidFrom  | ValidTo    | C_BPartner_ID |
      | pip_date2  | consol_product | consol_pi_item  | 10  | PCE               | 4015400346353 | 2022-06-15 | 2026-06-30 | consol_bp2    |
    When M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean='false' and p_consolidate='true'
    Then the surviving M_HU_PI_Item_Product for GTIN '4015400346353' has ValidFrom='2020-01-01' and ValidTo='2026-06-30'

  @from:cucumber
  Scenario: Consolidation skipped — different Qty creates a field conflict
    Given metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | C_BPartner_ID |
      | pip_qty_10   | consol_product | consol_pi_item  | 10  | PCE               | 4005808262151 | consol_bp1    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | C_BPartner_ID |
      | pip_qty_20   | consol_product | consol_pi_item  | 20  | PCE               | 4005808262151 | consol_bp2    |
    When M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean='false' and p_consolidate='true'
    Then M_HU_PI_Item_Product identified by 'pip_qty_10' has IsActive='Y'
    And M_HU_PI_Item_Product identified by 'pip_qty_20' has IsActive='Y'
    When M_HU_PI_Item_Product consolidation report is called
    Then the consolidation report contains a conflict row for GTIN '4005808262151' with conflicting field 'Qty'

  @from:cucumber
  Scenario: Report — different PI conflict detected for same GTIN
    Given metasfresh contains M_HU_PI:
      | Identifier  | Name              |
      | consol_pi2  | Consolidation PI2 |
    And metasfresh contains M_HU_PI_Version:
      | Identifier      | M_HU_PI_ID | HU_UnitType |
      | consol_pi_ver2  | consol_pi2 | TU          |
    And metasfresh contains M_HU_PI_Item:
      | Identifier       | M_HU_PI_Version_ID | ItemType |
      | consol_pi_item2  | consol_pi_ver2     | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          |
      | pip_rpt_a    | consol_product | consol_pi_item  | 10  | PCE               | 4005900036131 |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_Product_ID   | M_HU_PI_Item_ID  | Qty | C_UOM_ID.X12DE355 | GTIN          |
      | pip_rpt_b    | consol_product | consol_pi_item2  | 10  | PCE               | 4005900036131 |
    When M_HU_PI_Item_Product consolidation report is called
    Then the consolidation report contains a conflict row for GTIN '4005900036131'

  @from:cucumber
  Scenario: Both flags off — no changes made
    Given metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_Product_ID   | M_HU_PI_Item_ID | Qty | C_UOM_ID.X12DE355 | GTIN          | EAN_TU        |
      | pip_noop     | consol_product | consol_pi_item  | 10  | PCE               | 4006381333931 | 4006381333931 |
    When M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean='false' and p_consolidate='false'
    Then M_HU_PI_Item_Product identified by 'pip_noop' has GTIN='4006381333931' and EAN_TU='4006381333931'
