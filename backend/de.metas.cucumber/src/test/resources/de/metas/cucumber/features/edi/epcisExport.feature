@from:cucumber
@ghActions:run_on_executor5
Feature: EPCIS JSON export via get_epcis_events_json_fn
  The SQL function builds EPCIS event JSON from the HU hierarchy (not DESADV).
  Pallets come from M_ShipmentSchedule_QtyPicked, crates from M_HU_Item,
  items from M_HU_Item_Storage, and CU GTIN from M_Product.GTIN.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-06-10T10:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    # Pricing
    And metasfresh contains M_PricingSystems:
      | Identifier  | IsSOPriceList |
      | ps_epcis    | true          |
    And metasfresh contains M_PriceLists:
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_epcis   | ps_epcis           | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions:
      | Identifier | M_PriceList_ID |
      | plv_epcis  | pl_epcis       |

    # Customer with EDI DESADV
    And metasfresh contains C_BPartners:
      | Identifier      | IsCustomer | M_PricingSystem_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | customer_epcis  | Y          | ps_epcis           | true                 | 7601234567890         |

    # Warehouse
    And metasfresh contains M_Warehouse:
      | Identifier   |
      | wh_epcis     |

    # Product with CU GTIN
    And metasfresh contains M_Products:
      | Identifier   | GTIN          |
      | prod_epcis   | 7640134460001 |
    And metasfresh contains C_UOM_Conversions:
      | M_Product_ID | FROM_C_UOM_ID | TO_C_UOM_ID | MultiplyRate |
      | prod_epcis   | PCE           | PCE          | 1            |
    And metasfresh contains M_ProductPrices:
      | Identifier   | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | pp_epcis     | plv_epcis              | prod_epcis   | 10.00    | PCE      | Normal            |

    # HU Packing Instructions: LU (pallet) containing TU (crate)
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID   | Name           |
      | huPI_LU       | EPCIS LU PI    |
      | huPI_TU       | EPCIS TU PI    |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent |
      | huPIV_LU           | huPI_LU    | LU          | Y         |
      | huPIV_TU           | huPI_TU    | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID | M_HU_PI_Version_ID | ItemType | Qty | Included_HU_PI_ID |
      | huPII_LU_TU      | huPIV_LU           | HU       | 10  | huPI_TU           |
      | huPII_TU_MI      | huPIV_TU           | MI       |     |                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | C_UOM_ID |
      | huPIP_epcis             | huPII_TU_MI     | prod_epcis   | 10  | PCE      |

  @from:cucumber
  @Id:EPCIS_010
  Scenario: EPCIS JSON export for shipment with individual TUs
    # Create sales order
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID  | DateOrdered | DatePromised   | M_Warehouse_ID | POReference     |
      | o_epcis    | true    | customer_epcis | 2025-06-10  | 2025-06-11Z    | wh_epcis       | PO-EPCIS-TEST-1 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_epcis   | o_epcis    | prod_epcis   | 20         | huPIP_epcis             |
    And the order identified by o_epcis is completed

    # Wait for shipment schedule
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier   | C_OrderLine_ID | IsToRecompute |
      | ss_epcis     | ol_epcis       | N             |

    # Setup SSCC18 code generator for deterministic values
    And setup the SSCC18 code generator with GS1ManufacturerCode 7613204, GS1ExtensionDigit 0 and next sequence number always=1000000.

    # Generate shipment
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_epcis              | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_epcis              | io_epcis   |

    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    # Call the EPCIS JSON export function
    When the EPCIS JSON export function is called for M_InOut identified by io_epcis

    # Validate top-level fields
    Then the EPCIS JSON has:
      | palletCount |
      | 1           |

    # Validate pallet
    And the EPCIS JSON pallet has:
      | palletIndex | crateCount |
      | 0           | 2          |

    # Validate items have cuGTIN from M_Product.GTIN
    And the EPCIS JSON item has:
      | palletIndex | crateIndex | itemIndex | cuGTIN        | uom |
      | 0           | 0          | 0         | 7640134460001 | PCE |
