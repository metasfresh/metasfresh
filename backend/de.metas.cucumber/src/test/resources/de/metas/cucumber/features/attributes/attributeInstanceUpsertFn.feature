@from:cucumber
@allure.label.epic:E2300_Attributes
@allure.label.feature:F67044_Attribute
@ghActions:run_on_executor1
Feature: Generic SQL helper to UPSERT a single M_AttributeInstance
  The de_metas_attributes.upsert_attributeinstance function writes one attribute value
  into an M_AttributeSetInstance, typed per M_Attribute.AttributeValueType, creating the
  ASI on demand and refreshing its Description. get_attributeinstance_value reads it back.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-05-17T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Attributes:
      | Identifier | Value   | AttributeValueType |
      | attr_str   | teststr | S                  |
      | attr_num   | testnum | N                  |
      | attr_date  | testdat | D                  |
      | attr_list  | testlst | L                  |
    And metasfresh contains M_AttributeValues:
      | M_Attribute_ID | M_AttributeValue_ID | Value | Name | IsNullFieldValue |
      | attr_list      | av_red              | red   | Red  | N                |
      | attr_list      | av_blue             | blue  | Blue | N                |

  Scenario: UPSERT writes each value type and creates the ASI on demand
    # asi_1 does not exist yet -> the function creates it and returns its id
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value      |
      | asi_1                     | attr_str       | M          |
      | asi_1                     | attr_num       | 5          |
      | asi_1                     | attr_date      | 2024-01-15 |
      | asi_1                     | attr_list      | red        |
    Then validate de_metas_attributes.get_attributeinstance_value:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value      |
      | asi_1                     | attr_str       | M          |
      | asi_1                     | attr_num       | 5          |
      | asi_1                     | attr_date      | 2024-01-15 |
      | asi_1                     | attr_list      | red        |
    # cross-check the string value via the production read path (ImmutableAttributeSet)
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | AttributeCode | Value |
      | asi_1                     | teststr       | M     |

  Scenario: Re-invoking the same attribute updates the existing instance and refreshes the Description
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value   |
      | asi_2                     | attr_str       | initial |
    Then validate M_AttributeSetInstance:
      | M_AttributeSetInstance_ID | Description |
      | asi_2                     | initial     |
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value   |
      | asi_2                     | attr_str       | updated |
    Then validate de_metas_attributes.get_attributeinstance_value:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value   |
      | asi_2                     | attr_str       | updated |
    # exactly one attribute on the ASI -> the re-invoke updated, did not duplicate
    And validate M_AttributeInstance:
      | M_AttributeSetInstance_ID | Value   |
      | asi_2                     | updated |
    And validate M_AttributeSetInstance:
      | M_AttributeSetInstance_ID | Description |
      | asi_2                     | updated     |

  Scenario: A list value writes the attribute-value label into the Description
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_3                     | attr_list      | red   |
    Then validate M_AttributeSetInstance:
      | M_AttributeSetInstance_ID | Description |
      | asi_3                     | Red         |

  Scenario: An unknown list value is rejected
    Then invoke de_metas_attributes.upsert_attributeinstance expecting error:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value          |
      | asi_err                   | attr_list      | does_not_exist |

  Scenario: Clearing an attribute sets its value to null (incl. list attributes)
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_4                     | attr_str       | M     |
      | asi_4                     | attr_list      | red   |
    Then validate de_metas_attributes.get_attributeinstance_value:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_4                     | attr_str       | M     |
      | asi_4                     | attr_list      | red   |
    When invoke de_metas_attributes.clear_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID |
      | asi_4                     | attr_str       |
      | asi_4                     | attr_list      |
    # '-' asserts the value is now null
    Then validate de_metas_attributes.get_attributeinstance_value:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_4                     | attr_str       | -     |
      | asi_4                     | attr_list      | -     |

  Scenario: A date attribute renders as DD.MM.YYYY in the Description
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value      |
      | asi_d                     | attr_date      | 2024-01-15 |
    Then validate M_AttributeSetInstance:
      | M_AttributeSetInstance_ID | Description |
      | asi_d                     | 15.01.2024  |

  Scenario: cloneASI copies all attribute values into a new, independent ASI
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_src                   | attr_str       | M     |
      | asi_src                   | attr_list      | red   |
    When invoke de_metas_attributes.cloneASI:
      | Source_ID | M_AttributeSetInstance_ID |
      | asi_src   | asi_clone                 |
    Then validate de_metas_attributes.get_attributeinstance_value:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_clone                 | attr_str       | M     |
      | asi_clone                 | attr_list      | red   |
    # the clone is independent: editing it must not change the source ASI
    When invoke de_metas_attributes.upsert_attributeinstance:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_clone                 | attr_str       | X     |
    Then validate de_metas_attributes.get_attributeinstance_value:
      | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
      | asi_clone                 | attr_str       | X     |
      | asi_src                   | attr_str       | M     |
