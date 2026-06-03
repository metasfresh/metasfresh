@from:cucumber
@allure.label.epic:E0280_Document_and_Email_Management
@allure.label.feature:F00280
@ghActions:run_on_executor7
Feature: Invoice notification email is delayed until carrier tracking URLs are present
  As a logistics operator,
  I want invoice notification emails to be held back until the carrier has confirmed
  the shipment by providing a tracking URL,
  so that customers always receive the tracking link together with their invoice.
  The gate is controlled by SysConfig delayNotificationUntilShipmentConfirmedByCarrier.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And set sys config boolean value true for sys config delayNotificationUntilShipmentConfirmedByCarrier
    And set sys config boolean value true for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    # printLabels would need a printer config; keep it off so the WP succeeds without one
    And set sys config boolean value false for sys config de.metas.shipper.gateway.printLabels.enabled
    And update C_Doc_Outbound_Config IsAutoSendDocument:
      | TableName | IsAutoSendDocument |
      | C_Invoice | true               |
    And load M_Shipper:
      | Identifier | Name   |
      | nShift     | nShift |
    And metasfresh contains Carrier_Configs:
      | M_Shipper_ID |
      | nShift       |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_CustomsTariff:
      | Identifier | Value    |
      | ct         | 87654321 |
    And metasfresh contains M_Products:
      | Identifier | Value              | Name               | WeightNet | WeightGross | M_CustomsTariff_ID |
      | product    | notifdelay_product | NotifDelay Product | 1 KGM     | 1.1 KGM     | ct                 |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains C_TaxCategory
      | Identifier  |
      | taxCategory |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax7       | taxCategory      | 7    | DE                       | DE                        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv                    | product      | 20.0     | PCE      | taxCategory      |
    And load C_UOM:
      | C_UOM_ID.Identifier | X12DE355 |
      | cm                  | CM       |
    And metasfresh contains M_Products:
      | Identifier      | WeightNet | WeightGross |
      | packing_product | 0.1 KGM   | 0.1 KGM     |
    And metasfresh contains M_HU_PackingMaterial:
      | Identifier | M_Product_ID    | Length | Width | Height | C_UOM_Dimension_ID.Identifier |
      | pm         | packing_product | 30     | 20    | 10     | cm                            |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID | MovementDate | M_Warehouse_ID |
      | inv            | 2022-12-12   | wh             |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID | M_Product_ID    | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_l_1    | inv            | product         | 0       | 100      | PCE          |
      | inv_l_2    | inv            | packing_product | 0       | 100      | PCE          |
    When the inventory identified by inv is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID | M_HU_ID |
      | inv_l_1            | hu_1    |
      | inv_l_2            | hu_2    |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID |
      | LU         |
      | TU         |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | LU_Version         | LU         | LU          | Y         |
      | TU_Version         | TU         | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | Qty | ItemType | OPT.Included_HU_PI_ID | OPT.M_HU_PackingMaterial_ID.Identifier |
      | huPiItemLU      | LU_Version         | 10  | HU       | TU                    | pm                                     |
      | huPiItemTU      | TU_Version         |     | MI       |                       |                                        |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | product_TU_10CU         | huPiItemTU      | product      | 10  | 2021-01-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Value               | Name                | IsVendor | IsCustomer | M_PricingSystem_ID | OPT.InvoiceRule |
      | customer   | notifdelay_customer | NotifDelay Customer | N        | Y          | ps                 | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault | Postal | City   | Address1 | OPT.EMail             |
      | customerLocation | 8888801111110 | customer      | CH           | Y               | Y               | 12345  | Zurich | Main St. | invoice@notiftest.com |
    # Stable Name + EMail so reruns reuse the same AD_User.
    # IsBillToContact_Default=true + IsInvoiceEmailEnabled=Y lets the invoice recipient lookup find this contact.
    And metasfresh contains AD_Users:
      | Identifier      | Name                     | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.EMail             | OPT.IsBillToContact_Default | OPT.IsInvoiceEmailEnabled |
      | customerContact | NotifDelay Customer User | customer                     | customerLocation                      | invoice@notiftest.com | true                        | Y                         |
    And metasfresh contains Carrier_Products:
      | Identifier | M_Shipper_ID |
      | cp1        | nShift       |
    And metasfresh contains Carrier_Goods_Types:
      | Identifier | M_Shipper_ID |
      | cgt1       | nShift       |
    And metasfresh contains Carrier_Services:
      | Identifier | M_Shipper_ID |
      | cs1        | nShift       |
      | cs2        | nShift       |

  @from:cucumber
  @Id:S26820_TC1
  Scenario: Invoice mail workpackage is skipped while TrackingURL is absent, then released once TrackingURL is set

    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 |
      | cp1                | cgt1                  | cs1                | cs2                 |
    And the nShift shipment service is stubbed to return a successful shipment creation response

    # ── order → shipment pipeline ─────────────────────────────────────────────
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so         | true    | customer      | 2022-12-12  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so_l1      | so         | product      | 10         |
    When the order identified by so is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss         | so_l1          | N             |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss                    | shipment   |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso        | shipment   |
    # The nShift stub delivers an AWB+TrackingURL; clear it to simulate the carrier not yet confirming
    And update Carrier_ShipmentOrder_Parcel TrackingURL:
      | Carrier_ShipmentOrder_ID | TrackingURL |
      | cso                      | null        |

    # ── invoice candidate → invoice ──────────────────────────────────────────
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic                                | so_l1                     | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic                                |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic                                | invoice                 |

    # ── assert: mail WP is held because TrackingURL is still blank ────────────
    And wait until de.metas.async rabbitMQ queue is empty or throw exception after 5 minutes
    Then after not more than 30s, MailWorkpackageProcessor workpackage for invoice is in state:
      | C_Invoice_ID | ExpectedState |
      | invoice      | skipped       |

    # ── set TrackingURL to release the gate ───────────────────────────────────
    And update Carrier_ShipmentOrder_Parcel TrackingURL:
      | Carrier_ShipmentOrder_ID | TrackingURL                    |
      | cso                      | https://track.example.com/test |
    And wait until de.metas.async rabbitMQ queue is empty or throw exception after 5 minutes
    # "released" = the WP ran past the delay gate (either processed or attempted to send);
    # we do not assert actual SMTP delivery here — that depends on the test infra mail config
    Then after not more than 60s, MailWorkpackageProcessor workpackage for invoice is in state:
      | C_Invoice_ID | ExpectedState |
      | invoice      | released      |

    # ── assert outbound log was produced ─────────────────────────────────────
    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType |
      | outboundLog                      | invoice              | C_Invoice     | ARI             |

  @from:cucumber
  @Id:S26820_TC2
  Scenario: Invoice mail workpackage is sent immediately when TrackingURL is already present

    Given the nShift ship advisor service is stubbed to return a successful response based on the request
      | Carrier_Product_ID | Carrier_Goods_Type_ID | Carrier_Service_ID | Carrier_Service_ID2 |
      | cp1                | cgt1                  | cs1                | cs2                 |
    And the nShift shipment service is stubbed to return a successful shipment creation response

    # ── order → shipment pipeline ─────────────────────────────────────────────
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_Shipper_ID |
      | so2        | true    | customer      | 2022-12-12  | wh             | nShift       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | so2_l1     | so2        | product      | 10         |
    When the order identified by so2 is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss2        | so2_l1         | N             |
    And shipment is generated for the following shipment schedule
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss2                   | shipment2  |
    And after not more than 60s, Carrier_ShipmentOrder is found:
      | Identifier | M_InOut_ID |
      | cso2       | shipment2  |
    # nShift stub already set a TrackingURL — leave it as-is (no clear step needed)
    And validate Carrier_ShipmentOrder_Parcels:
      | Carrier_ShipmentOrder_ID | awb | TrackingURL |
      | cso2                     | awb | trackingUrl |

    # ── invoice candidate → invoice ──────────────────────────────────────────
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | ic2                               | so2_l1                    | 10           |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | ic2                               |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | ic2                               | invoice2                |

    # ── assert: mail WP was NOT skipped — goes straight to sending ───────────
    And wait until de.metas.async rabbitMQ queue is empty or throw exception after 5 minutes
    Then after not more than 60s, MailWorkpackageProcessor workpackage for invoice is in state:
      | C_Invoice_ID | ExpectedState |
      | invoice2     | released      |

    # ── assert outbound log was produced ─────────────────────────────────────
    And after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | OPT.DocBaseType |
      | outboundLog2                     | invoice2             | C_Invoice     | ARI             |

  @from:cucumber
  @ghActions:run_on_executor7
  Scenario: reset settings to default
    Given set sys config boolean value false for sys config delayNotificationUntilShipmentConfirmedByCarrier
    And set sys config boolean value false for sys config de.metas.handlingunits.picking.addToDailyShipperTransportationOrder
    And set sys config boolean value true for sys config de.metas.shipper.gateway.printLabels.enabled
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config de.metas.report.jasper.IsMockReportService
    And update C_Doc_Outbound_Config IsAutoSendDocument:
      | TableName | IsAutoSendDocument |
      | C_Invoice | false              |
