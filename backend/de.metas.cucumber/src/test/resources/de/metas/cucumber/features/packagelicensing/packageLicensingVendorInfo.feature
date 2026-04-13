@from:cucumber
@ghActions:run_on_executor2
Feature: Package Licensing Vendor Info and Domestic Filter (gh#29223)
  Verifies vendor info columns on the detail report, vendor breakdown in the
  product report, the domestic purchase filter toggle in the summary report,
  and BPartner packaging licensing exemption with date range.

  Background:
    Given infrastructure and metasfresh are running

  @Id:S29223_10
  Scenario: S29223_10 - Detail Report shows vendor info on purchase receipts
  Tests that:
  - Purchase receipts show VendorName and VendorCountryCode
  - Sales shipments show NULL vendor columns
  - IsVendorPackageLicensingExempt is NULL when no exemption is set

    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_v10      | PLV10 Test Product |
    And package licensing master data is set up:
      | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_v10                   | AT          | PG_V10           | SM_V10_Glas                | 0.100                | OM_V10_Kunststoff          | 0.020                |
    And package licensing InOut test data is set up:
      | M_Product_ID.Identifier | DocumentNo       | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty |
      | p_v10                   | PLV10_RECEIPT_AT | 2026-05-10   | V+           | N        | CO        | AT                   | AT                     | 100         |
      | p_v10                   | PLV10_RECEIPT_DE | 2026-05-12   | V+           | N        | CO        | AT                   | DE                     | 80          |
      | p_v10                   | PLV10_RECEIPT_NL | 2026-05-14   | V+           | N        | CO        | AT                   | NL                     | 60          |
      | p_v10                   | PLV10_SHIP_DE    | 2026-05-20   | C-           | Y        | CO        | AT                   | DE                     | 30          |

    When the Package Licensing InOut Report is executed with C_Country_ID for country code "AT" and date range "2026-05-01" to "2026-05-31"

    Then the Package Licensing InOut Report result contains:
      | DocumentNo       | PurchaseQty | VendorCountryCode | IsVendorPackageLicensingExempt |
      | PLV10_RECEIPT_AT | 100         | AT                |                                |
      | PLV10_RECEIPT_DE | 80          | DE                |                                |
      | PLV10_RECEIPT_NL | 60          | NL                |                                |
      | PLV10_SHIP_DE    |             |                   |                                |
    And the Package Licensing InOut Report result has non-null "VendorName" for DocumentNo "PLV10_RECEIPT_AT"
    And the Package Licensing InOut Report result has non-null "VendorName" for DocumentNo "PLV10_RECEIPT_DE"
    And the Package Licensing InOut Report result has non-null "VendorName" for DocumentNo "PLV10_RECEIPT_NL"

  @Id:S29223_20
  Scenario: S29223_20 - Product Report aggregates by product and vendor
  Tests that:
  - When a product has multiple vendors, each vendor gets its own row
  - ForeignSalesQty and TotalSalesQty are shown only on the first vendor row per product

    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_v20      | PLV20 Test Product |
    And package licensing master data is set up:
      | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_v20                   | AT          | PG_V20           | SM_V20_Glas                | 0.100                | OM_V20_Kunststoff          | 0.020                |
    And package licensing InOut test data is set up:
      | M_Product_ID.Identifier | DocumentNo       | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty |
      | p_v20                   | PLV20_RECEIPT_DE | 2026-06-10   | V+           | N        | CO        | AT                   | DE                     | 80          |
      | p_v20                   | PLV20_RECEIPT_NL | 2026-06-12   | V+           | N        | CO        | AT                   | NL                     | 60          |
      | p_v20                   | PLV20_SHIP_CH    | 2026-06-20   | C-           | Y        | CO        | AT                   | CH                     | 30          |

    When the Package Licensing Product Report is executed with C_Country_ID for country code "AT" and date range "2026-06-01" to "2026-06-30"

    Then the Package Licensing Product Report result contains:
      | ProductName        | VendorCountryCode | PurchaseQty | ForeignSalesQty | TotalSalesQty | IsVendorPackageLicensingExempt |
      | PLV20 Test Product | DE                | 80          | 30              | 30            |                                |
      | PLV20 Test Product | NL                | 60          |                 |               |                                |

  @Id:S29223_30
  Scenario: S29223_30 - Summary Report excludes domestic purchases when IsExcludeDomesticPurchases=Y
  Tests that domestic vendor (AT) purchases are excluded from the Mengenmeldung calculation.
  Foreign vendor (DE) purchases and sales (no vendor info) are included.
  Expected Haushalt: (80 - 30) * 0.100 = 5.000
  Expected Gewerbe: (80 - 30) * 0.020 / 10 = 0.100

    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_v30      | PLV30 Test Product |
    And package licensing master data is set up:
      | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_v30                   | AT          | PG_V30           | Glas                       | 0.100                | Kunststoff                 | 0.020                |
    And packaging instruction factor test data is set up:
      | M_Product_ID.Identifier | Qty | IsDefaultForProduct |
      | p_v30                   | 10  | Y                   |
    And package licensing InOut test data is set up:
      | M_Product_ID.Identifier | DocumentNo       | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty |
      | p_v30                   | PLV30_RECEIPT_AT | 2026-07-10   | V+           | N        | CO        | AT                   | AT                     | 100         |
      | p_v30                   | PLV30_RECEIPT_DE | 2026-07-12   | V+           | N        | CO        | AT                   | DE                     | 80          |
      | p_v30                   | PLV30_SHIP_DE    | 2026-07-20   | C-           | Y        | CO        | AT                   | DE                     | 30          |

    When the Package Licensing Summary Report is executed with C_Country_ID for country code "AT" and date range "2026-07-01" to "2026-07-31" and IsExcludeDomesticPurchases "Y"

    Then the Package Licensing Summary Report result contains:
      | ProductGroup | PackagingType | MaterialName | Weight |
      | PG_V30       | Haushalt      | Glas         | 5.000  |
      | PG_V30       | Gewerbe       | Kunststoff   | 0.100  |

  @Id:S29223_40
  Scenario: S29223_40 - Summary Report includes all vendors when IsExcludeDomesticPurchases=N
  Tests that all vendors (including domestic) are included when the filter is off.
  Expected Haushalt: (100 + 80 - 30) * 0.100 = 15.000
  Expected Gewerbe: (100 + 80 - 30) * 0.020 / 10 = 0.300

    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_v40      | PLV40 Test Product |
    And package licensing master data is set up:
      | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_v40                   | AT          | PG_V40           | Glas                       | 0.100                | Kunststoff                 | 0.020                |
    And packaging instruction factor test data is set up:
      | M_Product_ID.Identifier | Qty | IsDefaultForProduct |
      | p_v40                   | 10  | Y                   |
    And package licensing InOut test data is set up:
      | M_Product_ID.Identifier | DocumentNo       | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty |
      | p_v40                   | PLV40_RECEIPT_AT | 2026-08-10   | V+           | N        | CO        | AT                   | AT                     | 100         |
      | p_v40                   | PLV40_RECEIPT_DE | 2026-08-12   | V+           | N        | CO        | AT                   | DE                     | 80          |
      | p_v40                   | PLV40_SHIP_DE    | 2026-08-20   | C-           | Y        | CO        | AT                   | DE                     | 30          |

    When the Package Licensing Summary Report is executed with C_Country_ID for country code "AT" and date range "2026-08-01" to "2026-08-31" and IsExcludeDomesticPurchases "N"

    Then the Package Licensing Summary Report result contains:
      | ProductGroup | PackagingType | MaterialName | Weight  |
      | PG_V40       | Haushalt      | Glas         | 15.000  |
      | PG_V40       | Gewerbe       | Kunststoff   | 0.300   |

  @Id:S29223_50
  Scenario: S29223_50 - BPartner exemption with date range
  Tests that IsVendorPackageLicensingExempt is set based on the BPartner's exemption
  date range relative to the InOut's MovementDate.
  - Sep receipt (before exemption): NULL
  - Oct receipt (during exemption Oct 1-31): Y
  - Nov receipt (after exemption): NULL

    Given metasfresh contains M_Products:
      | Identifier | Name               |
      | p_v50      | PLV50 Test Product |
    And package licensing master data is set up:
      | M_Product_ID.Identifier | CountryCode | ProductGroupName | SmallPackagingMaterialName | SmallPackagingWeight | OuterPackagingMaterialName | OuterPackagingWeight |
      | p_v50                   | AT          | PG_V50           | SM_V50_Glas                | 0.100                | OM_V50_Kunststoff          | 0.020                |
    And package licensing InOut test data is set up:
      | M_Product_ID.Identifier | DocumentNo        | MovementDate | MovementType | IsSOTrx | DocStatus | WarehouseCountryCode | DestinationCountryCode | MovementQty | SharedBPartnerKey |
      | p_v50                   | PLV50_SEP_RECEIPT | 2026-09-15   | V+           | N        | CO        | AT                   | NL                     | 50          | vendor_v50_nl     |
      | p_v50                   | PLV50_OCT_RECEIPT | 2026-10-15   | V+           | N        | CO        | AT                   | NL                     | 60          | vendor_v50_nl     |
      | p_v50                   | PLV50_NOV_RECEIPT | 2026-11-15   | V+           | N        | CO        | AT                   | NL                     | 70          | vendor_v50_nl     |
    And package licensing BPartner exemption is set up:
      | SharedBPartnerKey | IsPackageLicensingExempt | PackageLicensingExemptFrom | PackageLicensingExemptTo |
      | vendor_v50_nl     | Y                        | 2026-10-01                 | 2026-10-31               |

    When the Package Licensing InOut Report is executed with C_Country_ID for country code "AT" and date range "2026-09-01" to "2026-11-30"

    Then the Package Licensing InOut Report result contains:
      | DocumentNo        | PurchaseQty | VendorCountryCode | IsVendorPackageLicensingExempt |
      | PLV50_SEP_RECEIPT | 50          | NL                |                                |
      | PLV50_OCT_RECEIPT | 60          | NL                | Y                              |
      | PLV50_NOV_RECEIPT | 70          | NL                |                                |
