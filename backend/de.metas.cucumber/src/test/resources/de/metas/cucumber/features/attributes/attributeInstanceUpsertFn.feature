@from:cucumber
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
      | M_Attribute_ID | M_AttributeValue_ID | Value | IsNullFieldValue |
      | attr_list      | av_red              | red   | N                |
      | attr_list      | av_blue             | blue  | N                |

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
