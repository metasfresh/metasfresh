@from:cucumber
@topic:shipmentScheduleExport
@ghActions:run_on_executor7
Feature: Shipment schedule export rest-api
  Mostly covering the "shipBPartner"."contact" info differences when exporting oxid vs non-oxid (shopware) shipment candidates.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-25T13:30:13+01:00[Europe/Berlin]
    And deactivate all M_ShipmentSchedule records
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config int value 0 for sys config de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedAfterSeconds

    And load M_Shipper:
      | M_Shipper_ID.Identifier | Name | OPT.InternalName     |
      | shipper_test            | Siro | shipper_internalName |

    And metasfresh contains M_Products:
      | Identifier    | Name          | OPT.Description   |
      | product_25_02 | product_25_02 | dummy description |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_1       | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name          | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps_1                          | DE                        | EUR                 | price_list_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_so     | pl_so                     | plv_so | 2022-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product | plv_so                            | product_25_02           | 10.0     | Normal                        | PCE               |

    And metasfresh contains C_BPartners:
      | Identifier        | Name              | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName       | OPT.AD_Language |
      | customer_so_25_02 | customer_so_25_02 | ps_1                          | Y              | customer_so_25_02_cmp | de_DE           |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City |
      | shipLocation             | DE          | addr ship 10 | 123        | shipCity |
      | billLocation             | DE          | addr bill 11 | 456        | billCity |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | GLN                 | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.EMail         | OPT.BPartnerName | OPT.Phone |
      | shipBPLocation | ship_location_S0150 | customer_so_25_02        | shipLocation                 | true         | false        | ship@location.com | shipBPName       | 321       |
      | billBPLocation | bill_location_S0150 | customer_so_25_02        | billLocation                 | false        | true         | bill@location.com | billBPName       | 654       |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name     | OPT.EMail     | OPT.AD_Language | OPT.C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.Phone |
      | shipUser              | shipUser | ship@user.com | de_DE           | customer_so_25_02            | shipBPLocation               | 123       |
      | billUser              | billUser | bill@user.com | de_DE           | customer_so_25_02            | billBPLocation               | 456       |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | Type    | ExternalReference     | OPT.AD_User_ID.Identifier | OPT.M_Shipper_ID.Identifier |
      | shipUser_ref                      | Shopware6      | UserID  | shipUser_reference    | shipUser                  |                             |
      | billUser_ref                      | Shopware6      | UserID  | billUser_reference    | billUser                  |                             |
      | shipperTest_ref                   | Shopware6      | Shipper | shipperTest_reference |                           | shipper_test                |


  @Id:S0150_210
  Scenario: Export oxid shipment candidate
    Given load AD_User:
      | AD_User_ID.Identifier | Login      |
      | loginUser             | metasfresh |

    And update AD_SysConfig with login AD_User_ID
      | Name                                              | AD_User_ID.Identifier |
      | de.metas.rest_api.v2.shipping.c_olcand.OxidUserId | loginUser             |

    And reset all cache

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 45201,
    "externalLineId": 1111,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-ship_location_S0150",
        "bpartnerLocationIdentifier": "gln-ship_location_S0150",
        "contactIdentifier": "ext-Shopware6-shipUser_reference"
    },
    "billBPartner": {
        "bpartnerIdentifier": "gln-bill_location_S0150",
        "bpartnerLocationIdentifier": "gln-bill_location_S0150",
        "contactIdentifier": "ext-Shopware6-billUser_reference"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-product_25_02",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_45201",
    "deliveryViaRule": "S",
    "deliveryRule": "F",
    "shipper": "ext-Shopware6-shipperTest_reference"
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "45201",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | null                  | null                    |
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference      | processed | docStatus | OPT.AD_User_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | customer_so_25_02        | shipBPLocation                    | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_45201 | true      | CO        | shipUser                  | customer_so_25_02               | billBPLocation                  | billUser                    | Shopware                               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02      | product_25_02           | 1          | 0            | 0           | 10.0  | 0        | EUR          | true      |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | orderLine_1               | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.AD_InputDataSource_ID.InternalName |
      | schedule_1                       | customer_so_25_02            | shipBPLocation                        | customer_so_25_02               | billBPLocation                  | product_25_02               | PENDING          | order_1                   | orderLine_1                   | shipUser                  | billUser                    | Shopware                               |

    And update AD_User:
      | AD_User_ID.Identifier | OPT.EMail           | OPT.Phone |
      | shipUser              | shipUpdate@user.com | 12330     |
      | billUser              | billUpdate@user.com | 45660     |

    When the metasfresh REST-API endpoint path 'api/v2/shipments/shipmentCandidates' receives a 'GET' request

    Then validate JsonResponseShipmentCandidates
      | M_ShipmentSchedule_ExportAudit_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product.Identifier | OPT.POReference  | OPT.QtyOrdered | OPT.X12DE355 | OPT.M_Shipper.Identifier | OPT.OrderedQtyNetPrice | OPT.QtyToDeliverNetPrice | OPT.DeliveredQtyNetPrice | OPT.NrOfOLCandsWithSamePOReference |
      | schedule_export_1                            | schedule_1                       | product_25_02        | olCand_ref_45201 | 1              | PCE          | shipper_test             | 10.0                   | 10.0                     | 0.0                      | 1                                  |

    And validate JsonResponseShipmentCandidates.JsonCustomer
      | qualifier    | companyName | contactName | contactEmail        | contactPhone | street    | streetNo | postal | city     | countryCode | language | company |
      | shipBPartner | shipBPName  | shipUser    | billUpdate@user.com | 12330        | addr ship | 10       | 123    | shipCity | DE          | de_DE    | true    |
      | billBPartner | billBPName  | billUser    | billUpdate@user.com | 45660        | addr bill | 11       | 456    | billCity | DE          | de_DE    | true    |

    And validate M_ShipmentSchedule_ExportAudit:
      | M_ShipmentSchedule_ExportAudit_ID.Identifier | ExportStatus |
      | schedule_export_1                            | EXPORTED     |
    And validate M_ShipmentSchedule_ExportAudit_Item:
      | M_ShipmentSchedule_ExportAudit_Item_ID.Identifier | M_ShipmentSchedule_ExportAudit_ID.Identifier | M_ShipmentSchedule_ID.Identifier | ExportStatus |
      | schedule_export_item_1                            | schedule_export_1                            | schedule_1                       | EXPORTED     |

    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.AD_InputDataSource_ID.InternalName |
      | schedule_1                       | customer_so_25_02            | shipBPLocation                        | customer_so_25_02               | billBPLocation                  | product_25_02               | EXPORTED         | order_1                   | orderLine_1                   | shipUser                  | billUser                    | Shopware                               |

    And set sys config int value -1 for sys config de.metas.rest_api.v2.shipping.c_olcand.OxidUserId


  @Id:S0150_220
  Scenario: Export non-oxid shipment schedule from order candidate
    Given set sys config int value -1 for sys config de.metas.rest_api.v2.shipping.c_olcand.OxidUserId
    And reset all cache

    And a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
  """
{
    "orgCode": "001",
    "externalHeaderId": 96411,
    "externalLineId": 2222,
    "dataSource": "int-Shopware",
    "bpartner": {
        "bpartnerIdentifier": "gln-ship_location_S0150",
        "bpartnerLocationIdentifier": "gln-ship_location_S0150",
        "contactIdentifier": "ext-Shopware6-shipUser_reference"
    },
    "billBPartner": {
        "bpartnerIdentifier": "gln-bill_location_S0150",
        "bpartnerLocationIdentifier": "gln-bill_location_S0150",
        "contactIdentifier": "ext-Shopware6-billUser_reference"
    },
    "dateRequired": "2022-02-10",
    "dateOrdered": "2022-02-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-product_25_02",
    "qty": 1,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "olCand_ref_96411",
    "deliveryViaRule": "S",
    "deliveryRule": "F",
    "shipper": "ext-Shopware6-shipperTest_reference"
}
"""
    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "96411",
    "inputDataSourceName": "int-Shopware",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
"""
    And process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | null                  | null                    |
    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference      | processed | docStatus | OPT.AD_User_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.AD_InputDataSource_ID.InternalName |
      | order_1               | customer_so_25_02        | shipBPLocation                    | 2022-02-02  | SOO         | EUR          | F            | S               | olCand_ref_96411 | true      | CO        | shipUser                  | customer_so_25_02               | billBPLocation                  | billUser                    | Shopware                               |
    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | OPT.DateOrdered | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02      | product_25_02           | 1          | 0            | 0           | 10.0  | 0        | EUR          | true      |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | orderLine_1               | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.AD_InputDataSource_ID.InternalName |
      | schedule_1                       | customer_so_25_02            | shipBPLocation                        | customer_so_25_02               | billBPLocation                  | product_25_02               | PENDING          | order_1                   | orderLine_1                   | shipUser                  | billUser                    | Shopware                               |

    And update AD_User:
      | AD_User_ID.Identifier | OPT.EMail           | OPT.Phone |
      | shipUser              | shipUpdate@user.com | 12330     |
      | billUser              | billUpdate@user.com | 45660     |

    When the metasfresh REST-API endpoint path 'api/v2/shipments/shipmentCandidates' receives a 'GET' request

    Then validate JsonResponseShipmentCandidates
      | M_ShipmentSchedule_ExportAudit_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product.Identifier | OPT.POReference  | OPT.QtyOrdered | OPT.X12DE355 | OPT.M_Shipper.Identifier | OPT.OrderedQtyNetPrice | OPT.QtyToDeliverNetPrice | OPT.DeliveredQtyNetPrice | OPT.NrOfOLCandsWithSamePOReference |
      | schedule_export_1                            | schedule_1                       | product_25_02        | olCand_ref_96411 | 1              | PCE          | shipper_test             | 10.0                   | 10.0                     | 0.0                      | 1                                  |

    And validate JsonResponseShipmentCandidates.JsonCustomer
      | qualifier    | companyName | contactName | contactEmail      | contactPhone | street    | streetNo | postal | city     | countryCode | language | company |
      | shipBPartner | shipBPName  | shipBPName  | ship@location.com | 321          | addr ship | 10       | 123    | shipCity | DE          | de_DE    | true    |
      | billBPartner | billBPName  | billBPName  | bill@location.com | 654          | addr bill | 11       | 456    | billCity | DE          | de_DE    | true    |

    And validate M_ShipmentSchedule_ExportAudit:
      | M_ShipmentSchedule_ExportAudit_ID.Identifier | ExportStatus |
      | schedule_export_1                            | EXPORTED     |
    And validate M_ShipmentSchedule_ExportAudit_Item:
      | M_ShipmentSchedule_ExportAudit_Item_ID.Identifier | M_ShipmentSchedule_ExportAudit_ID.Identifier | M_ShipmentSchedule_ID.Identifier | ExportStatus |
      | schedule_export_item_1                            | schedule_export_1                            | schedule_1                       | EXPORTED     |

    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.AD_InputDataSource_ID.InternalName |
      | schedule_1                       | customer_so_25_02            | shipBPLocation                        | customer_so_25_02               | billBPLocation                  | product_25_02               | EXPORTED         | order_1                   | orderLine_1                   | shipUser                  | billUser                    | Shopware                               |

  Scenario: Export non-oxid shipment schedule from order
    Given set sys config int value -1 for sys config de.metas.rest_api.v2.shipping.c_olcand.OxidUserId

    And load M_AttributeSet:
      | M_AttributeSet_ID.Identifier   | Name               |
      | attributeSet_convenienceSalate | Convenience Salate |
    And load M_Product_Category:
      | M_Product_Category_ID.Identifier | Name     | Value    |
      | standard_category                | Standard | Standard |
    And update M_Product_Category:
      | M_Product_Category_ID.Identifier | OPT.M_AttributeSet_ID.Identifier |
      | standard_category                | attributeSet_convenienceSalate   |
    And metasfresh contains M_Products:
      | Identifier    | Name          | OPT.Description   | OPT.M_Product_Category_ID.Identifier |
      | product_25_02 | product_25_02 | dummy description | standard_category                    |

    And metasfresh contains M_AttributeSetInstance with identifier "olASI":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"1000002",
          "valueStr":"Bio"
      }
    ]
  }
  """

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.POReference      |
      | order_1    | true    | customer_so_25_02        | 2022-02-02  | shipBPLocation                        | shipUser                  | so_POReference_25_02 |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | OPT.M_AttributeSetInstance_ID.Identifier |
      | orderLine_1 | order_1               | product_25_02           | 1          | olASI                                    |

    And the order identified by order_1 is completed

    And after not more than 60s the order is found
      | C_Order_ID.Identifier | DocStatus |
      | order_1               | CO        |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | orderLine_1               | N             |
    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | schedule_1                       | customer_so_25_02            | shipBPLocation                        | customer_so_25_02               | shipBPLocation                  | product_25_02               | PENDING          | order_1                   | orderLine_1                   | shipUser                  | olASI                                    |

    When the metasfresh REST-API endpoint path 'api/v2/shipments/shipmentCandidates' receives a 'GET' request

    Then validate JsonResponseShipmentCandidates
      | M_ShipmentSchedule_ExportAudit_ID.Identifier | M_ShipmentSchedule_ID.Identifier | M_Product.Identifier | OPT.QtyOrdered | OPT.X12DE355 | OPT.OrderedQtyNetPrice | OPT.QtyToDeliverNetPrice | OPT.DeliveredQtyNetPrice | OPT.M_AttributeSetInstance_ID.Identifier | OPT.NrOfOLCandsWithSamePOReference | OPT.POReference      |
      | schedule_export_1                            | schedule_1                       | product_25_02        | 1              | PCE          | 10.0                   | 0.0                      | 0.0                      | olASI                                    | 0                                  | so_POReference_25_02 |
    And validate JsonResponseShipmentCandidates.JsonCustomer
      | qualifier    | companyName | contactName | contactEmail      | contactPhone | street    | streetNo | postal | city     | countryCode | language | company |
      | shipBPartner | shipBPName  | null        | null              | null         | addr ship | 10       | 123    | shipCity | DE          | de_DE    | true    |
      | billBPartner | shipBPName  | shipBPName  | ship@location.com | 321          | addr ship | 10       | 123    | shipCity | DE          | de_DE    | true    |

    And validate M_ShipmentSchedule_ExportAudit:
      | M_ShipmentSchedule_ExportAudit_ID.Identifier | ExportStatus |
      | schedule_export_1                            | EXPORTED     |
    And validate M_ShipmentSchedule_ExportAudit_Item:
      | M_ShipmentSchedule_ExportAudit_Item_ID.Identifier | M_ShipmentSchedule_ExportAudit_ID.Identifier | M_ShipmentSchedule_ID.Identifier | ExportStatus |
      | schedule_export_item_1                            | schedule_export_1                            | schedule_1                       | EXPORTED     |

    And after not more than 60s, validate shipment schedules:
      | M_ShipmentSchedule_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.Bill_BPartner_ID.Identifier | OPT.Bill_Location_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.ExportStatus | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
      | schedule_1                       | customer_so_25_02            | shipBPLocation                        | customer_so_25_02               | shipBPLocation                  | product_25_02               | EXPORTED         | order_1                   | orderLine_1                   | shipUser                  | olASI                                    |
