@from:cucumber
@ghActions:run_on_executor6
Feature: Zoom-Into field-level reference scan
  Verify that every reference column (Table/TableDir/Search) on key tables
  resolves to a valid zoom-to window via RecordWindowFinder.

  Background:
    Given infrastructure and metasfresh are running

  @from:cucumber
  Scenario: C_Order reference fields all resolve to zoom-to windows
    When all reference fields of table "C_Order" are scanned for zoom-to targets, excluding columns:
      | ColumnName              |
      | Bill_Location_ID        |
      | C_BPartner_Location_ID  |
      | C_Campaign_ID           |
      | C_CashLine_ID           |
      | DropShip_Location_ID    |
      | HandOver_Location_ID    |
    Then every reference field resolves to a valid zoom-to window

  @from:cucumber
  Scenario: C_BPartner reference fields all resolve to zoom-to windows
    When all reference fields of table "C_BPartner" are scanned for zoom-to targets, excluding columns:
      | ColumnName                |
      | AD_Org_Mapping_ID         |
      | Default_Bill_Location_ID  |
      | Default_Ship_Location_ID  |
    Then every reference field resolves to a valid zoom-to window

  @from:cucumber
  Scenario: C_Invoice reference fields all resolve to zoom-to windows
    When all reference fields of table "C_Invoice" are scanned for zoom-to targets, excluding columns:
      | ColumnName                  |
      | Beneficiary_Location_ID     |
      | C_BPartner_Location_ID      |
      | C_Campaign_ID               |
      | C_CashLine_ID               |
      | C_DunningLevel_ID           |
      | C_RecurrentPaymentLine_ID   |
    Then every reference field resolves to a valid zoom-to window

  @from:cucumber
  Scenario: M_InOut reference fields all resolve to zoom-to windows
    When all reference fields of table "M_InOut" are scanned for zoom-to targets, excluding columns:
      | ColumnName              |
      | C_BPartner_Location_ID  |
      | C_Campaign_ID           |
      | DropShip_Location_ID    |
    Then every reference field resolves to a valid zoom-to window
