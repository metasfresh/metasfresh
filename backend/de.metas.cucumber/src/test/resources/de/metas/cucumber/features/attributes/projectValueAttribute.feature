@from:cucumber
@allure.label.epic:E2300_Attributes
@allure.label.feature:F67000_Attributes
@ghActions:run_on_executor2
Feature: ProjectValue must not be injected onto project-less records

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-03-01T08:00:00+01:00[Europe/Berlin]

  @Id:S_ProjVal_10
  Scenario: A new sales order line with no project gains no ProjectValue attribute instance
  ## _Given ProjectValue is storage-relevant and a product uses an attribute set with one instance attribute
  ## _When a sales order line is created for that product with NO project and an ASI already assigned
  ## _Then the line's ASI holds no ProjectValue attribute instance

    Given metasfresh contains M_Attributes:
      | Identifier     | Value          | AttributeValueType | IsStorageRelevant |
      | projectValAttr | ProjectValue   | S                  | Y                 |
      | sizeAttr       | Artikelgroesse | S                  | Y                 |
    And add M_AttributeSet:
      | Identifier |
      | attrSet    |
    And add M_AttributeUse:
      | M_AttributeSet_ID | M_Attribute_ID | SeqNo |
      | attrSet           | sizeAttr       | 10    |
    And metasfresh contains M_Products:
      | Identifier | OPT.M_AttributeSet_ID.Identifier |
      | product    | attrSet                          |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx |
      | pl         | ps                 | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | plv                    | product      | 10.00    | PCE               |
    And metasfresh contains M_AttributeSetInstance with identifier "asi":
      """
      {
        "attributeInstances":[
          {
            "attributeCode":"Artikelgroesse",
            "valueStr":"21"
          }
        ]
      }
      """
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer | M_PricingSystem_ID |
      | bp         | true       | ps                 |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered |
      | order      | true    | bp            | 2026-03-01  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | OPT.M_AttributeSetInstance_ID |
      | orderLine  | order      | product      | 1          | asi                           |

    Then validate C_OrderLine:
      | C_OrderLine_ID | OPT.M_AttributeSetInstance_ID |
      | orderLine      | asi                           |
    And validate M_AttributeInstance is absent:
      | C_OrderLine_ID | AttributeCode |
      | orderLine      | ProjectValue  |
