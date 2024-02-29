@from:cucumber
@topic:orderDocOutbound
Feature: Validate order doc outbound log creation
  Especially tracing "C_Doc_Outbound_Log.CurrentEMailAddress"

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-01T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService

    And metasfresh contains M_Products:
      | Identifier        | Name              |
      | addr_test_product | addr_test_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_1       | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-01-30 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product | plv_so                            | addr_test_product       | 10.0     | Normal                        | PCE               |
    And metasfresh contains C_BPartners:
      | Identifier    | Name          | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | sale_bpartner | sale_bpartner | ps_1                          | Y              |

  Scenario: Create sales order and validate email from doc outbound log - from order
    Given metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.EMail          |
      | bpLocation | 1111123456789 | sale_bpartner            | true         | true         | location@email.com |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name   | OPT.EMail      | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | bpUser                | bpUser | user@email.com | sale_bpartner                | bpLocation                            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.POReference   | OPT.DeliveryRule | OPT.DeliveryViaRule | OPT.EMail          |
      | order_1    | true    | sale_bpartner            | 2022-02-02  | bpLocation                            | bpUser                    | order_ref_1128101 | F                | S                   | location@email.com |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | addr_test_product       | 1          |

    # change email now, to verify the mail is not coming from master data
    And update C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.EMail                   |
      | bpLocation                        | bpLocationUpdated@email.com |

    And the order identified by order_1 is completed

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference       | processed | docStatus | OPT.EMail          |
      | order_1               | sale_bpartner            | bpLocation                        | 2022-02-02  | SOO         | EUR          | F            | S               | order_ref_1128101 | true      | CO        | location@email.com |

    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.CurrentEMailAddress | OPT.C_BPartner_ID.Identifier | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLog                 | order_1              | C_Order       | user@email.com          | sale_bpartner                | SOO             | CO            |

    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLogLine                  | orderOutboundLog                 | order_1              | C_Order       | SOO             | CO            |


  Scenario: Create sales order and validate email from doc outbound log - from business partner contact
    Given metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | bpLocation | 2222223456789 | sale_bpartner            | true         | true         |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name             | OPT.EMail           | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | bpUser                | bpUser_secondary | secondary@email.com | sale_bpartner                | bpLocation                            |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.POReference   | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_1    | true    | sale_bpartner            | 2022-02-02  | bpLocation                            | bpUser                    | order_ref_2085101 | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | addr_test_product       | 1          |

    And the order identified by order_1 is completed

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference       | processed | docStatus | OPT.EMail |
      | order_1               | sale_bpartner            | bpLocation                        | 2022-02-02  | SOO         | EUR          | F            | S               | order_ref_2085101 | true      | CO        | null      |

    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.CurrentEMailAddress | OPT.C_BPartner_ID.Identifier | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLog                 | order_1              | C_Order       | secondary@email.com     | sale_bpartner                | SOO             | CO            |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType | OPT.DocStatus |
      | orderOutboundLogLine                  | orderOutboundLog                 | order_1              | C_Order       | SOO             | CO            |

