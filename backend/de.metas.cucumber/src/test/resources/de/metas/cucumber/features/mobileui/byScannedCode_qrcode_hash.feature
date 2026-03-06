@from:cucumber
@ghActions:run_on_executor7
Feature: byScannedCode endpoints accept QR codes with # characters (me03#28652)

  All metasfresh QR codes use the format TYPE#VERSION#JSON_PAYLOAD.
  The byScannedCode endpoints previously used GET+@RequestParam, which caused
  the # characters to be interpreted as URL fragment delimiters, truncating
  the QR code before it reached the server.
  Fix: Changed all three to POST+@RequestBody.
  These scenarios verify that QR codes with # characters are correctly
  processed by each endpoint when called directly.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-03-06T10:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set mobile UI picking profile
      | IsAllowPickingAnyHU | CreateShipmentPolicy |
      | Y                   | CREATE_AND_COMPLETE  |

  @from:cucumber
  Scenario: POST picking/hu/byScannedCode accepts QR codes with # characters
    Given metasfresh contains M_PricingSystems
      | Identifier |
      | ps_hash    |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_hash    | ps_hash            | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_hash   | pl_hash        |
    And metasfresh contains M_Products:
      | Identifier  |
      | prod_hash_p |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_hash    | plv_hash               | prod_hash_p  | 10.0     | PCE                | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier  | IsVendor | IsCustomer | M_PricingSystem_ID |
      | bp_hash_p   | N        | Y          | ps_hash            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN                | C_BPartner_ID | IsBillToDefault | IsShipTo |
      | bpl_hash_p  | GLN_hash_28652_p   | bp_hash_p     | true            | true     |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_hash_p                | 2026-03-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_hash_p                | invLine_hash_p                | prod_hash_p  | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_hash_p'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_hash_p                | hu_hash_p          |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier | HUQRCode.Identifier |
      | hu_hash_p          | qr_hash_p           |
    And call POST picking/hu/byScannedCode with HU QR code containing hash:
      | HUQRCode.Identifier |
      | qr_hash_p           |

  @from:cucumber
  Scenario: POST distribution/hu/byScannedCode accepts QR codes with # characters
    Given metasfresh contains M_Products:
      | Identifier  |
      | prod_hash_d |
    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_hash_d                | 2026-03-01   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_hash_d                | invLine_hash_d                | prod_hash_d  | 0       | 10       | PCE          |
    And complete inventory with inventoryIdentifier 'inv_hash_d'
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invLine_hash_d                | hu_hash_d          |
    And generate QR Codes for HUs
      | M_HU_ID.Identifier | HUQRCode.Identifier |
      | hu_hash_d          | qr_hash_d           |
    And call POST distribution/hu/byScannedCode with HU QR code containing hash:
      | HUQRCode.Identifier |
      | qr_hash_d           |

  @from:cucumber
  Scenario: POST material/warehouses/resolveLocator accepts QR codes with # characters
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | wh_hash                   | StdWarehouse |
    And call POST material/warehouses/resolveLocator with locator QR code containing hash:
      | M_Warehouse_ID.Identifier |
      | wh_hash                   |
