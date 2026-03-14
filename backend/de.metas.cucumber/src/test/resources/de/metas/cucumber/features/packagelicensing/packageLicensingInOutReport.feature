@from:cucumber
@ghActions:run_on_executor2
Feature: Package Licensing InOut Report (gh#28487)
  Verifies that the SQL function report.Package_Licensing_InOut_Report()
  returns all movements (not just cross-border), with correct ForeignSalesQty,
  PurchaseQty, MovementQty, MaterialType, and PackagingInstructionFactor.
  Only completed/closed documents should appear.

  Background:
    Given infrastructure and metasfresh are running

  @Id:S28487_10
  Scenario: S28487_10 - Report shows all movements with correct qty columns and filters by DocStatus
  Tests that:
  - All movements appear (receipts + domestic shipments + foreign shipments)
  - ForeignSalesQty is populated ONLY for shipments to a foreign country
  - PurchaseQty is populated ONLY for purchase receipts (IsSoTrx=N)
  - MovementQty is negative for shipments (C-), positive for receipts (V+)
  - Draft documents (DocStatus=DR) are excluded
  - MaterialType and PackagingInstructionFactor are populated

    Given metasfresh contains M_Products:
      | Identifier | Name                  |
      | p_inout_1  | PLInOut Test Product 1 |
    And package licensing master data is set up:
      | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_inout_1               | AT          | Lebensmittel AT  | Glas AT                    | 0.150                | Kunststoffe AT             | 0.030                |
    And packaging instruction factor test data is set up:
      | M_Product_ID.Identifier | Qty | IsDefaultForProduct |
      | p_inout_1               | 20  | Y                   |
    And package licensing InOut test data is set up:
      | M_Product_ID.Identifier | DocumentNo          | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty |
      | p_inout_1               | PLIO_RECEIPT_AT      | 2026-01-15   | V+           | N        | CO        | AT                   | AT                     | 100         |
      | p_inout_1               | PLIO_SHIP_FOREIGN_DE | 2026-01-20   | C-           | Y        | CO        | AT                   | DE                     | 50          |
      | p_inout_1               | PLIO_SHIP_DOMESTIC   | 2026-01-25   | C-           | Y        | CO        | AT                   | AT                     | 30          |
      | p_inout_1               | PLIO_DRAFT_RECEIPT   | 2026-01-28   | V+           | N        | DR        | AT                   | AT                     | 200         |

    When the Package Licensing InOut Report is executed with C_Country_ID for country code "AT" and date range "2026-01-01" to "2026-02-01"

    Then the Package Licensing InOut Report result contains:
      | DocumentNo           | MovementQty | PurchaseQty | ForeignSalesQty | MaterialType    | SmallPackagingMaterial | SmallPackagingWeight | OuterPackagingMaterial | OuterPackagingWeight | PackagingInstructionFactor |
      | PLIO_RECEIPT_AT      | 100         | 100         |                 | Lebensmittel AT | Glas AT                | 0.150                | Kunststoffe AT         | 0.030                | 20                        |
      | PLIO_SHIP_FOREIGN_DE | -50         |             | 50              | Lebensmittel AT | Glas AT                | 0.150                | Kunststoffe AT         | 0.030                | 20                        |
      | PLIO_SHIP_DOMESTIC   | -30         |             |                 | Lebensmittel AT | Glas AT                | 0.150                | Kunststoffe AT         | 0.030                | 20                        |

    And the Package Licensing InOut Report result does not contain DocumentNo "PLIO_DRAFT_RECEIPT"

  @Id:S28487_20
  Scenario: S28487_20 - Packaging data resolved from parameter country, not warehouse country
  Tests that packaging master data (MaterialType, SmallPackagingMaterial, etc.) is resolved
  using the report's country parameter, not the warehouse's physical country.
  A receipt into a DE warehouse should still show AT packaging data when report runs with country=AT.

    Given metasfresh contains M_Products:
      | Identifier | Name                  |
      | p_inout_2  | PLInOut Test Product 2 |
    And package licensing master data is set up:
      | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_inout_2               | AT          | Tiernahrung AT   | PPK AT                     | 0.200                | Papier AT                  | 0.040                |
      | p_inout_2               | DE          | Tiernahrung DE   | PPK DE                     | 0.200                | Papier DE                  | 0.040                |
    And package licensing InOut test data is set up:
      | M_Product_ID.Identifier | DocumentNo       | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty |
      | p_inout_2               | PLIO_RECEIPT_DE  | 2026-02-10   | V+           | N        | CO        | DE                   | DE                     | 75          |

    When the Package Licensing InOut Report is executed with C_Country_ID for country code "AT" and date range "2026-02-01" to "2026-03-01"

    Then the Package Licensing InOut Report result contains:
      | DocumentNo      | MovementQty | PurchaseQty | ForeignSalesQty | MaterialType   | SmallPackagingMaterial | OuterPackagingMaterial |
      | PLIO_RECEIPT_DE | 75          | 75          |                 | Tiernahrung AT | PPK AT                 | Papier AT              |
