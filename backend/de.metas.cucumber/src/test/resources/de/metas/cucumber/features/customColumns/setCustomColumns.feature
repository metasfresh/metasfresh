@from:cucumber
@flaky
@ghActions:run_on_executor5
Feature: Setting customColumns via SetCustomColumns method

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has current date and time
    And metasfresh contains M_PricingSystems
      | Identifier | Name              | Value                     | OPT.IsActive |
      | ps_1       | PricingSystemName | PricingPricingSystemValue | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | PriceListName | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                 | ValidFrom  |
      | plv_1      | pl_1                      | PriceListVersionName | 2022-08-01 |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |

  Scenario: Use I_C_Order and I_S_ResourceType to set custom columns (AD_Column-IsRestAPICustomColumn=Y) covering the following target class and displayType combination:
  _BigDecimal
  _String
  _Integer
  _Boolean
  _Timestamp (displayType=Time)
  _Timestamp (displayType=DateTime)
  _Timestamp (displayType=Date)

    #dev-note used I_C_Order and I_S_ResourceType just for convenience, the logic is not related at all with any given table.
    And metasfresh contains C_BPartners:
      | Identifier | Name              | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | bpartner   | BPartner_05082022 | N            | Y              | ps_1                          |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order      | true    | bpartner                 | 2022-08-05  |

    And metasfresh contains S_ResourceType:
      | S_ResourceType_ID.Identifier | Name | Value | M_Product_Category_ID.Identifier | UomCode | IsTimeSlot |
      | resourceType                 | Name | Value | standard_category                | MJ      | true       |

    And update AD_Column:
      | TableName      | ColumnName    | OPT.IsRestAPICustomColumn |
      | C_Order        | BPartnerName  | true                      |
      | C_Order        | EMail         | true                      |
      | C_Order        | IsDropShip    | true                      |
      | C_Order        | DateOrdered   | true                      |
      | C_Order        | DatePromised  | true                      |
      | C_Order        | Volume        | true                      |
      | S_ResourceType | TimeSlotStart | true                      |
      | S_ResourceType | TimeSlotEnd   | true                      |
      | S_ResourceType | ChargeableQty | true                      |

    And the metasfresh cache is reset

    When set custom columns for C_Order:
      | C_Order_ID.Identifier | OPT.BPartnerName | OPT.IsDropShip | OPT.DateOrdered | OPT.DatePromised         | OPT.Volume | OPT.EMail |
      | order                 | BPartnerName     | true           | 2022-08-05      | 2022-08-05T14:38:40.108Z | 2.1234     | null      |

    When set custom columns for S_ResourceType:
      | S_ResourceType_ID.Identifier | OPT.TimeSlotStart | OPT.TimeSlotEnd | OPT.ChargeableQty |
      | resourceType                 | 12:25             | 15:00           | 10                |

    Then validate customColumns:
      | OPT.C_Order_ID.Identifier | OPT.S_ResourceType_ID.Identifier | CustomColumnJSONValue                                                                                                                  |
      | order                     |                                  | {"BPartnerName":"BPartnerName","DateOrdered":"2022-08-05","DatePromised":"2022-08-05T14:38:40.108Z","IsDropShip":true,"Volume":2.1234} |
      |                           | resourceType                     | {"ChargeableQty":10,"TimeSlotEnd":"15:00:00","TimeSlotStart":"12:25:00"}                                                               |

    And set custom columns for C_Order expecting error:
      | C_Order_ID.Identifier | OPT.DeliveryInfo | OPT.ErrorMessage                                                                                                   |
      | order                 | DeliveryInfo     | C_Order.DeliveryInfo ist nicht als benutzerdefinierte REST API-Spalte markiert (AD_Column.IsRestAPICustomColumn=N) |

