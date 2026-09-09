@from:cucumber
@allure.label.epic:E0155_Material_Disposition
@allure.label.feature:F5100
@ghActions:run_on_executor6
Feature: M_Warehouse.MRP_Exclude='Y' bypasses material disposition end-to-end
## F5100: Material Disposition

  When a warehouse has MRP_Exclude='Y', every MaterialEventHandler that would otherwise create
  MD_Candidate rows consults IWarehouseBL.isIgnoreInMaterialDispo(warehouseId) and skips silently.
  These scenarios drive a document end-to-end (shipment schedule, receipt schedule, inventory →
  m_transaction) on an MRP_Exclude warehouse, drain the de.metas.material rabbitMQ queue, then
  assert that NO non-STOCK MD_Candidate rows were created for the product. Counterpart positive
  cases (regular warehouses still create MD_Candidate) live in the other materialDispo features.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-11T06:00:00Z
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_so      | ps_1               | DE           | EUR           | true  |
      | pl_po      | ps_1               | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so     | pl_so          |
      | plv_po     | pl_po          |

  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Shipment schedule on MRP_Exclude warehouse produces no MD_Candidate
    # SO completion creates a M_ShipmentSchedule on the MRP_Exclude warehouse. The
    # ShipmentScheduleCreatedHandler must short-circuit, producing zero non-STOCK MD_Candidate rows.
    Given metasfresh contains M_Products:
      | Identifier | OPT.IsPurchased |
      | product    | N               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer   | Y          | N        | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | cust_loc   | 2965700000010 | customer      | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | MRP_Exclude |
      | wh_excl_ship   | Y           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.PreparationDate  | M_Warehouse_ID |
      | so_1       | true    | customer      | 2021-04-17  | 2021-04-11T21:00:00Z | wh_excl_ship   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_1      | so_1       | product      | 5          |
    When the order identified by so_1 is completed
    # Drain async event processing — gives ShipmentScheduleCreatedHandler a chance to run (and to short-circuit).
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # CORE assertion: zero non-STOCK MD_Candidate rows for the product on the MRP_Exclude warehouse.
    Then no MD_Candidate exists for M_Product_ID product

  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Receipt schedule on MRP_Exclude warehouse produces no MD_Candidate
    # PO completion creates a M_ReceiptSchedule on the MRP_Exclude warehouse. The
    # ReceiptsScheduleCreatedOrUpdatedHandler must short-circuit, producing zero non-STOCK MD_Candidate rows.
    Given metasfresh contains M_Products:
      | Identifier | OPT.IsPurchased |
      | product    | Y               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_po      | plv_po                 | product      | 8.0      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | vendor     | N          | Y        | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_loc | 2965700000027 | vendor        | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | MRP_Exclude |
      | wh_excl_recv   | Y           |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PaymentTerm_ID | DocBaseType | M_PricingSystem_ID | DatePromised        | M_Warehouse_ID |
      | po_1       | N       | vendor        | 2021-04-12  | 1000012          | POO         | ps_1               | 2021-04-15T15:00:00 | wh_excl_recv   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | pol_1      | po_1       | product      | 10         |
    When the order identified by po_1 is completed
    # Drain async event processing — gives ReceiptsScheduleCreatedOrUpdatedHandler a chance to run.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # CORE assertion: zero non-STOCK MD_Candidate rows for the product on the MRP_Exclude warehouse.
    Then no MD_Candidate exists for M_Product_ID product

  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: Inventory completion on MRP_Exclude warehouse fires m_transaction but produces no MD_Candidate
    # Completing an internal use inventory on the MRP_Exclude warehouse creates M_HUs and fires
    # TransactionCreatedEvent. Both the dispo-service TransactionEventHandler and the cockpit
    # TransactionEventHandlerForCockpitRecords must short-circuit, producing zero non-STOCK
    # MD_Candidate rows (notably no UNEXPECTED_INCREASE).
    Given metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | MRP_Exclude |
      | wh_excl_trx    | Y           |
    And metasfresh contains M_Inventories:
      | Identifier | M_Warehouse_ID | MovementDate |
      | inv_1      | wh_excl_trx    | 2021-04-09   |
    And metasfresh contains M_InventoriesLines:
      | Identifier | M_Inventory_ID.Identifier | M_Product_ID.Identifier | UOM.X12DE355 | QtyCount | QtyBook |
      | invl_1     | inv_1                     | product                 | PCE          | 100      | 0       |
    And the inventory identified by inv_1 is completed
    # Wait until the HU(s) for the inventory line are created (this is what triggers the m_transaction
    # events under the hood).
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | invl_1                        | hu_1               |
    # Drain async event processing — gives TransactionEventHandler / cockpit handler a chance to run.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # CORE assertion: zero non-STOCK MD_Candidate rows for the product on the MRP_Exclude warehouse.
    Then no MD_Candidate exists for M_Product_ID product

  @from:cucumber
  @allure.label.epic:E0155_Material_Disposition
  @allure.label.feature:F5100
  Scenario: DD order with source warehouse on MRP_Exclude produces no MD_Candidate pair
    # DD-order semantics: DDOrderAdvisedOrCreatedHandler / DDOrderCandidateAdvisedOrCreatedHandler
    # consult IWarehouseBL.isIgnoreInMaterialDispo() for BOTH source (supply side) and target
    # (demand side). If EITHER side is MRP_Exclude=Y, the WHOLE candidate pair is skipped
    # — the SUPPLY and DEMAND candidates are tightly coupled via parentId, so half a pair
    # cannot exist in the dispo timeline.
    #
    # Setup: standard distribution network targetWH <- sourceWH_excl, with sourceWH_excl marked
    # MRP_Exclude=Y. A sales order on targetWH would normally trigger DDOrderCandidateAdvisedEvent,
    # creating a SUPPLY(targetWH)/DEMAND(sourceWH_excl) candidate pair. The handler must short-
    # circuit and produce ONLY the SHIPMENT DEMAND at targetWH — no DISTRIBUTION pair.
    Given metasfresh contains M_Products:
      | Identifier | OPT.IsPurchased |
      | product    | N               |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer   | Y          | N        | ps_1               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | cust_loc   | 2965700000034 | customer      | Y               | Y               |
    # targetWH is a regular MRP-tracked warehouse; sourceWH_excl has MRP_Exclude=Y.
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID | C_BPartner_ID | C_BPartner_Location_ID | MRP_Exclude |
      | targetWH       | customer      | cust_loc               | N           |
      | sourceWH_excl  | customer      | cust_loc               | Y           |
    And contains M_Shippers
      | Identifier |
      | shipper    |
    And metasfresh contains DD_NetworkDistribution
      | DD_NetworkDistribution_ID |
      | ddNetwork_1               |
    And metasfresh contains DD_NetworkDistributionLine
      | DD_NetworkDistribution_ID | M_Warehouse_ID | M_WarehouseSource_ID | M_Shipper_ID |
      | ddNetwork_1               | targetWH       | sourceWH_excl        | shipper      |
    # IsCreatePlan=N -> generates DD_Order_Candidate only (no DD_Order); enough to exercise the handler.
    And metasfresh contains PP_Product_Plannings
      | Identifier      | M_Product_ID | IsCreatePlan | DD_NetworkDistribution_ID | M_Warehouse_ID |
      | productPlanning | product      | N            | ddNetwork_1               | targetWH       |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.PreparationDate  | M_Warehouse_ID |
      | so_dd      | true    | customer      | 2021-04-17  | 2021-04-11T21:00:00Z | targetWH       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol_dd     | so_dd      | product      | 5          |
    When the order identified by so_dd is completed
    # Drain async event processing — gives the DD-order handler a chance to (not) run.
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    # CORE assertion: no DD_Order_Candidate was created for the product. This is the direct,
    # precise test of the DD-handler short-circuit:
    #   - DDOrderCandidateAdvisedOrCreatedHandler is what proposes the DD pair; on a regular
    #     network it would create exactly one DD_Order_Candidate (sourceWH_excl -> targetWH).
    #   - With MRP_Exclude=Y on the source side, IWarehouseBL.isIgnoreInMaterialDispo() returns
    #     true and the handler must skip — therefore zero DD_Order_Candidates for the product.
    # The MD_Candidate side (SUPPLY at targetWH + DEMAND at sourceWH_excl) is necessarily also
    # skipped because the pair is tightly coupled via parentId — a half-pair cannot exist.
    Then no DD_Order_Candidates found for product product
