@from:cucumber
@ghActions:run_on_executor7
@allure.label.epic:E0160_Manufacturing_Execution
@allure.label.feature:F8030_MobileUI_Manufacturing
@F8030
Feature: mobileUI Manufacturing - configure the editable-attributes list via test masterdata

  ## F31771 (Task 10): proves the MobileUI_MFG_Config_Attribute child table - which the frontend-testing
  ## masterdata command (MobileConfigManufacturingCommand, used by Playwright's Backend.createMasterdata)
  ## writes through de.metas.manufacturing.config.MobileUIManufacturingConfigRepository - is also directly
  ## configurable from cucumber, via the equivalent step defs in this file.

  Background:
    Given infrastructure and metasfresh are running

# ######################################################################################################################
# ######################################################################################################################
  @from:cucumber
  @Id:S31771_TC10
  Scenario: Setting the mfg editable-attribute list creates ordered MobileUI_MFG_Config_Attribute rows, and REPLACES it on re-config
    And metasfresh contains M_Attributes:
      | Identifier | Value          | Name        |
      | sizeAttr   | TestSizeAttr   | Test Size   |
      | colorAttr  | TestColorAttr  | Test Color  |

    And metasfresh has mobileUI manufacturing editable attributes:
      | SeqNo | M_Attribute_ID.Identifier |
      | 10    | sizeAttr                  |
      | 20    | colorAttr                 |
    Then mobileUI manufacturing editable attributes are:
      | SeqNo | M_Attribute_ID.Identifier |
      | 10    | sizeAttr                  |
      | 20    | colorAttr                 |

    ## Re-config with a DIFFERENT list - drops sizeAttr, keeps colorAttr under a new SeqNo. Proves the list is
    ## REPLACED, not merely appended to: this would fail if the step only ever inserted new rows without
    ## deactivating the ones no longer listed.
    And metasfresh has mobileUI manufacturing editable attributes:
      | SeqNo | M_Attribute_ID.Identifier |
      | 10    | colorAttr                 |
    Then mobileUI manufacturing editable attributes are:
      | SeqNo | M_Attribute_ID.Identifier |
      | 10    | colorAttr                 |
