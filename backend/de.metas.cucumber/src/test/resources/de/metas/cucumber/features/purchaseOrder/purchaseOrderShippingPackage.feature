@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
@ghActions:run_on_executor7
Feature: Purchase order to transportation order
## F00600: Purchase Order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier       |
      | purchasedProduct |

    And load M_Shipper:
      | Identifier  | Name |
      | shipper_DHL | Dhl  |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_PO      |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name   | ValidFrom  |
      | plv_PO     | pl_PO                     | plv_PO | 2022-03-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | purchasedProduct        | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | supplier   | Y            | N              | ps_PO                         |

  @from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@F00600
  Scenario: Create a new purchase order, no M_ShippingPackage/M_Package are created. They are created only by manually invoking process. Once transportation order is completed, the order cannot be reactivated.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference  | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | M_Shipper_ID |
      | order_PO   | N       | supplier                 | 2022-06-11  | po_ref_S0156_100 | POO             | ps_PO                             | supplier                              | A                | S                   | shipper_DHL  |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO | order_PO              | purchasedProduct        | 10         |

    And metasfresh contains Transport Order
      | Identifier          | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | transportationOrder | shipper_DHL  | supplier            | supplier            | Incoming           |

    And the order identified by order_PO is completed

    And validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO              | order_PO              | purchasedProduct        | 10         | 0            | 0           | 10    | 0        | EUR          | true      | 10              |

    And metasfresh contains exactly 0 M_ShippingPackages for transportation order: transportationOrder

    When the order identified by order_PO is reactivated
    And the order identified by order_PO is completed

    Then metasfresh contains exactly 0 M_ShippingPackages for transportation order: transportationOrder

    And C_Order_AddTo_M_ShipperTransportation is invoked for order order_PO and transportation order: transportationOrder

    And metasfresh contains exactly 1 M_ShippingPackages for transportation order: transportationOrder

    And the transport order identified by transportationOrder is completed

    And the order identified by order_PO cannot be reactivated

  @from:cucumber
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  @F00600
  Scenario: Shipping packages survive PO reactivation and are synced on re-complete
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference    | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | M_Shipper_ID |
      | order_PO2  | N       | supplier                 | 2022-06-11  | po_ref_S0156_200   | POO             | ps_PO                             | supplier                              | A                | S                   | shipper_DHL  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO2 | order_PO2             | purchasedProduct        | 5          |

    And metasfresh contains Transport Order
      | Identifier           | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | transportationOrder2 | shipper_DHL  | supplier            | supplier            | Incoming           |

    And the order identified by order_PO2 is completed

    # Assign shipping packages to the transportation order
    And C_Order_AddTo_M_ShipperTransportation is invoked for order order_PO2 and transportation order: transportationOrder2
    And metasfresh contains exactly 1 M_ShippingPackages for transportation order: transportationOrder2

    # Reactivate and re-complete the PO — shipping packages must survive
    When the order identified by order_PO2 is reactivated
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: transportationOrder2

    When the order identified by order_PO2 is completed
    Then metasfresh contains exactly 1 M_ShippingPackages for transportation order: transportationOrder2

  @from:cucumber
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  @F00600
  @Id:S31789_TC_Q17_WrongDirectionAdd
  Scenario: Adding a purchase order to an OUTGOING transport order is refused
    # An outgoing transport order carries only sales/shipment documents; a purchase order is a receipt-side
    # document, so adding it here must be refused with a named message rather than silently creating shipping
    # packages on a transport order that will never receive a matching receipt.
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference    | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | M_Shipper_ID |
      | order_PO_Wrong | N      | supplier                 | 2022-06-11  | po_ref_S0156_300   | POO             | ps_PO                             | supplier                              | A                | S                   | shipper_DHL  |
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_Wrong | order_PO_Wrong        | purchasedProduct        | 5          |

    And metasfresh contains Transport Order
      | Identifier             | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | outgoingTransportOrder | shipper_DHL  | supplier             | supplier             | Outgoing           |

    And the order identified by order_PO_Wrong is completed

    And C_Order_AddTo_M_ShipperTransportation is invoked for order order_PO_Wrong and transportation order outgoingTransportOrder, and is refused:
      | ErrorAdMessage                           |
      | WrongTransportDirectionForPurchaseOrder  |

    And metasfresh contains exactly 0 M_ShippingPackages for transportation order: outgoingTransportOrder

  @from:cucumber
  @allure.label.epic:E0140_Purchasing
  @allure.label.feature:F00600_Purchase_Order
  @F00600
  @Id:S31789_TC_Q17_DropshipAllowed
  Scenario: Adding a purchase order to a DROPSHIP transport order still succeeds
    # Dropship carries a receipt leg too (goods never enter our own warehouse, but the purchase-side document
    # still links to it), so the direction guard must NOT block this path.
    Given metasfresh contains C_Orders:
      | Identifier      | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference   | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule | M_Shipper_ID |
      | order_PO_Dropship | N      | supplier                 | 2022-06-11  | po_ref_S0156_400  | POO             | ps_PO                             | supplier                              | A                | S                   | shipper_DHL  |
    And metasfresh contains C_OrderLines:
      | Identifier            | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO_Dropship | order_PO_Dropship     | purchasedProduct        | 5          |

    And metasfresh contains Transport Order
      | Identifier              | M_Shipper_ID | Shipper_BPartner_ID | Shipper_Location_ID | TransportDirection |
      | dropshipTransportOrder  | shipper_DHL  | supplier             | supplier             | Dropship           |

    And the order identified by order_PO_Dropship is completed

    And C_Order_AddTo_M_ShipperTransportation is invoked for order order_PO_Dropship and transportation order: dropshipTransportOrder

    And metasfresh contains exactly 1 M_ShippingPackages for transportation order: dropshipTransportOrder
