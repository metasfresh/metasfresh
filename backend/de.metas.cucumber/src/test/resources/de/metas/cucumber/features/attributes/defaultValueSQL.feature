@from:cucumber
@ghActions:run_on_executor6
Feature: Test M_Attribute.DefaultValueSQL feature

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Attributes:
      | Identifier      | AttributeValueType | DefaultValueSQL                                                                    |
      | attributeString | S                  | SELECT 'TableName=@TableName@$M_Product_ID=@M_Product_ID@$C_Order_ID=@C_Order_ID@' |
      | attributeNumber | N                  | SELECT 12345                                                                       |
      | attributeDate   | D                  | select '2022-06-01 13:30:13 Europe/Berlin'::timestamptz                            |
      | attributeDummy1 | S                  |                                                                                    |

    And add M_AttributeSet:
      | Identifier    | MandatoryType |
      | attributeSet1 | N             |
    And add M_AttributeUse:
      | M_AttributeSet_ID | M_Attribute_ID  | SeqNo |
      | attributeSet1     | attributeString | 10    |
      | attributeSet1     | attributeNumber | 20    |
      | attributeSet1     | attributeDate   | 30    |
      | attributeSet1     | attributeDummy1 | 40    |
    And metasfresh contains M_Product_Categories:
      | Identifier       | M_AttributeSet_ID |
      | productCategory1 | attributeSet1     |
    And metasfresh contains M_Products:
      | Identifier | M_Product_Category_ID |
      | product1   | productCategory1      |

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | priceList  | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID |
      | priceListVersion | priceList      |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | priceListVersion       | product1     | 100      | PCE      |

    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | customer   | Y          | pricingSystem      |


  @from:cucumber
  Scenario: Test M_Attribute.DefaultValueSQL feature
    When metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order1     | true    | customer      | 2022-06-02  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine1 | order1     | product1     | 1          |
    Then validate C_OrderLine:
      | C_OrderLine_ID | attribute:attributeString                                         | attribute:attributeNumber | attribute:attributeDate |
      | orderLine1     | TableName=C_OrderLine$M_Product_ID=@product1@$C_Order_ID=@order1@ | 12345                     | 2022-06-01              |
    And the order identified by order1 is completed
    And validate C_OrderLine:
      | C_OrderLine_ID | attribute:attributeString                                         | attribute:attributeNumber | attribute:attributeDate |
      | orderLine1     | TableName=C_OrderLine$M_Product_ID=@product1@$C_Order_ID=@order1@ | 12345                     | 2022-06-01              |
