@from:cucumber
@allure.label.epic:E0360_Transport_Extralogistik
@allure.label.feature:F29098.1_Customs_Invoice
@ghActions:run_on_executor5
Feature: customs invoice footer and detail weights

  # A customs invoice with 27 kg lines. Two lines are each linked to two shipment lines.
  # Gross weight per line = packing material weight x TUs + invoiced kg + shipped qty x (gross - net weight).
  # The footer total must equal the sum of the lines, so a shipment line linked to a customs line is counted once.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'


  # ####################################################################################################
  # ####################################################################################################
  # Scenario 1: footer and details of a customs invoice with shipment lines in packing instructions
  # ####################################################################################################
  # ####################################################################################################
  @from:cucumber
  Scenario: the footer weights of a customs invoice count every shipment line once
    Given metasfresh contains M_CustomsTariff:
      | Identifier | Value |
      | tariff_A   | CT-A  |
      | tariff_B   | CT-B  |
      | tariff_C   | CT-C  |
      | tariff_D   | CT-D  |
      | tariff_E   | CT-E  |
      | tariff_F   | CT-F  |
      | tariff_G   | CT-G  |
      | tariff_H   | CT-H  |
      | tariff_I   | CT-I  |
      | tariff_J   | CT-J  |
    # packing materials; the weight of the packing material product is the weight of one TU
    And metasfresh contains M_Products:
      | Identifier   | X12DE355 | WeightNet |
      | pm_A_product | PCE      | 0.4 KGM   |
      | pm_B_product | PCE      | 0.31 KGM  |
      | pm_C_product | PCE      | 0 KGM     |
      | pm_D_product | PCE      | 0 KGM     |
      | pm_E_product | PCE      | 0.23 KGM  |
      | pm_F_product | PCE      | 0.36 KGM  |
      | pm_G_product | PCE      | 0 KGM     |
    # goods sold by weight (KGM on the customs invoice), shipped in PCE
    And metasfresh contains M_Products:
      | Identifier | X12DE355 | WeightNet | WeightGross | M_CustomsTariff_ID |
      | product_01 | PCE      | 6 KGM     | 6.25 KGM    | tariff_F           |
      | product_02 | PCE      | 34 KGM    | 34.05 KGM   | tariff_I           |
      | product_03 | PCE      | 2.5 KGM   | 2.8 KGM     | tariff_H           |
      | product_04 | PCE      | 2.5 KGM   | 2.6 KGM     | tariff_H           |
      | product_05 | PCE      | 2 KGM     | 2.05 KGM    | tariff_I           |
      | product_06 | PCE      | 2.25 KGM  | 2.4 KGM     | tariff_I           |
      | product_07 | PCE      | 6.5 KGM   | 6.75 KGM    | tariff_A           |
      | product_08 | PCE      | 6.5 KGM   | 6.55 KGM    | tariff_J           |
      | product_09 | PCE      | 1.75 KGM  | 1.76 KGM    | tariff_I           |
      | product_10 | PCE      | 32 KGM    | 32.5 KGM    | tariff_I           |
      | product_11 | PCE      | 5 KGM     | 5.25 KGM    | tariff_H           |
      | product_12 | PCE      | 2.2 KGM   | 2.25 KGM    | tariff_H           |
      | product_13 | PCE      | 4.3 KGM   | 4.32 KGM    | tariff_E           |
      | product_14 | PCE      | 6.5 KGM   | 6.75 KGM    | tariff_G           |
      | product_15 | PCE      | 6 KGM     | 7 KGM       | tariff_F           |
      | product_16 | PCE      | 7 KGM     | 7.05 KGM    | tariff_D           |
      | product_17 | PCE      | 7.2 KGM   | 7.25 KGM    | tariff_C           |
      | product_18 | PCE      | 4 KGM     | 4.05 KGM    | tariff_D           |
      | product_19 | PCE      | 5 KGM     | 5.1 KGM     | tariff_E           |
      | product_20 | PCE      | 4.9 KGM   | 4.95 KGM    | tariff_G           |
      | product_21 | PCE      | 6 KGM     | 6.01 KGM    | tariff_D           |
      | product_22 | PCE      | 87 KGM    | 87.5 KGM    | tariff_H           |
      | product_23 | PCE      | 6.9 KGM   | 6.95 KGM    | tariff_B           |
      | product_24 | PCE      | 35 KGM    | 35.05 KGM   | tariff_I           |
      | product_25 | PCE      | 2.5 KGM   | 2.52 KGM    | tariff_H           |
      | product_26 | PCE      | 6.4 KGM   | 6.65 KGM    | tariff_E           |
      | product_27 | PCE      | 5.4 KGM   | 5.42 KGM    | tariff_D           |
    And metasfresh contains C_BPartners:
      | Identifier | IsCustomer |
      | customer   | Y          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | location   | 0123456789012 | customer      | Y               | Y               |
    # one packing instruction per packing material; the weight is read via the PI item of the shipment line's packing instruction
    And metasfresh contains M_HU_PI:
      | Identifier  |
      | huPI_pm_A   |
      | huPI_pm_B   |
      | huPI_pm_C   |
      | huPI_pm_D   |
      | huPI_pm_E   |
      | huPI_pm_F   |
      | huPI_pm_G   |
      | huPI_pmNone |
    And metasfresh contains M_HU_PI_Version:
      | Identifier       | M_HU_PI_ID  | HU_UnitType |
      | huVersion_pm_A   | huPI_pm_A   | TU          |
      | huVersion_pm_B   | huPI_pm_B   | TU          |
      | huVersion_pm_C   | huPI_pm_C   | TU          |
      | huVersion_pm_D   | huPI_pm_D   | TU          |
      | huVersion_pm_E   | huPI_pm_E   | TU          |
      | huVersion_pm_F   | huPI_pm_F   | TU          |
      | huVersion_pm_G   | huPI_pm_G   | TU          |
      | huVersion_pmNone | huPI_pmNone | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_A                    | pm_A_product |
      | pm_B                    | pm_B_product |
      | pm_C                    | pm_C_product |
      | pm_D                    | pm_D_product |
      | pm_E                    | pm_E_product |
      | pm_F                    | pm_F_product |
      | pm_G                    | pm_G_product |
    And metasfresh contains M_HU_PI_Item:
      | Identifier    | M_HU_PI_Version_ID | ItemType | Qty | M_HU_PackingMaterial_ID |
      | piItem_pm_A   | huVersion_pm_A     | PM       | 1   | pm_A                    |
      | piItem_pm_B   | huVersion_pm_B     | PM       | 1   | pm_B                    |
      | piItem_pm_C   | huVersion_pm_C     | PM       | 1   | pm_C                    |
      | piItem_pm_D   | huVersion_pm_D     | PM       | 1   | pm_D                    |
      | piItem_pm_E   | huVersion_pm_E     | PM       | 1   | pm_E                    |
      | piItem_pm_F   | huVersion_pm_F     | PM       | 1   | pm_F                    |
      | piItem_pm_G   | huVersion_pm_G     | PM       | 1   | pm_G                    |
      | piItem_pmNone | huVersion_pmNone   | MI       | 1   | null                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier | M_HU_PI_Item_ID | M_Product_ID | Qty |
      | pip_01     | piItem_pm_A     | product_01   | 1   |
      | pip_02     | piItem_pmNone   | product_02   | 1   |
      | pip_03     | piItem_pm_E     | product_03   | 1   |
      | pip_04     | piItem_pm_E     | product_04   | 1   |
      | pip_05     | piItem_pm_D     | product_05   | 1   |
      | pip_06     | piItem_pm_D     | product_06   | 1   |
      | pip_07     | piItem_pm_A     | product_07   | 1   |
      | pip_08     | piItem_pm_A     | product_08   | 1   |
      | pip_09     | piItem_pm_B     | product_09   | 1   |
      | pip_10     | piItem_pmNone   | product_10   | 1   |
      | pip_11     | piItem_pm_D     | product_11   | 1   |
      | pip_12     | piItem_pm_D     | product_12   | 1   |
      | pip_13     | piItem_pm_G     | product_13   | 1   |
      | pip_14     | piItem_pm_A     | product_14   | 1   |
      | pip_15     | piItem_pm_A     | product_15   | 1   |
      | pip_16     | piItem_pm_A     | product_16   | 1   |
      | pip_17     | piItem_pm_C     | product_17   | 1   |
      | pip_18     | piItem_pm_G     | product_18   | 1   |
      | pip_19     | piItem_pm_G     | product_19   | 1   |
      | pip_20     | piItem_pm_G     | product_20   | 1   |
      | pip_21     | piItem_pm_A     | product_21   | 1   |
      | pip_22     | piItem_pmNone   | product_22   | 1   |
      | pip_23     | piItem_pm_A     | product_23   | 1   |
      | pip_24     | piItem_pmNone   | product_24   | 1   |
      | pip_25     | piItem_pm_F     | product_25   | 1   |
      | pip_26     | piItem_pm_A     | product_26   | 1   |
      | pip_27     | piItem_pm_A     | product_27   | 1   |
    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | wh_std                    | StdWarehouse |
    And load M_Locator:
      | Identifier | Value      | M_Warehouse_ID |
      | loc_std    | Hauptlager | wh_std         |
    And metasfresh contains M_InOut:
      | M_InOut_ID | C_BPartner_ID | C_BPartner_Location_ID | IsSOTrx | DeliveryRule | DeliveryViaRule | FreightCostRule | M_Warehouse_ID.Identifier | MovementDate | MovementType | PriorityRule | DocBaseType | DocSubType |
      | shipment   | customer      | location               | true    | F            | S               | I               | wh_std                    | 2026-01-15   | C-           | 5            | MMS         | MS         |
    And metasfresh contains M_InOutLine
      | M_InOutLine_ID | M_InOut_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.M_Locator_ID.Identifier | QtyEntered | MovementQty | UomCode | QtyEnteredTU | M_HU_PI_Item_Product_ID |
      | shipLine_01    | shipment              | product_01                  | loc_std                      | 10         | 10          | PCE     | 10           | pip_01                  |
      | shipLine_02    | shipment              | product_02                  | loc_std                      | 15         | 15          | PCE     | 1            | pip_02                  |
      | shipLine_03    | shipment              | product_03                  | loc_std                      | 42         | 42          | PCE     | 14           | pip_03                  |
      | shipLine_04    | shipment              | product_04                  | loc_std                      | 30         | 30          | PCE     | 10           | pip_04                  |
      | shipLine_05    | shipment              | product_05                  | loc_std                      | 16         | 16          | PCE     | 2            | pip_05                  |
      | shipLine_06    | shipment              | product_06                  | loc_std                      | 64         | 64          | PCE     | 8            | pip_06                  |
      | shipLine_07    | shipment              | product_07                  | loc_std                      | 2          | 2           | PCE     | 2            | pip_07                  |
      | shipLine_08    | shipment              | product_08                  | loc_std                      | 10         | 10          | PCE     | 10           | pip_08                  |
      | shipLine_09    | shipment              | product_09                  | loc_std                      | 6          | 6           | PCE     | 1            | pip_09                  |
      | shipLine_10    | shipment              | product_10                  | loc_std                      | 4          | 4           | PCE     | 1            | pip_10                  |
      | shipLine_11    | shipment              | product_11                  | loc_std                      | 36         | 36          | PCE     | 18           | pip_11                  |
      | shipLine_12    | shipment              | product_12                  | loc_std                      | 8          | 8           | PCE     | 2            | pip_12                  |
      | shipLine_13    | shipment              | product_13                  | loc_std                      | 2          | 2           | PCE     | 2            | pip_13                  |
      | shipLine_14    | shipment              | product_14                  | loc_std                      | 3          | 3           | PCE     | 3            | pip_14                  |
      | shipLine_15    | shipment              | product_15                  | loc_std                      | 12         | 12          | PCE     | 12           | pip_15                  |
      | shipLine_16    | shipment              | product_16                  | loc_std                      | 3          | 3           | PCE     | 3            | pip_16                  |
      | shipLine_17    | shipment              | product_17                  | loc_std                      | 4          | 4           | PCE     | 4            | pip_17                  |
      | shipLine_18    | shipment              | product_17                  | loc_std                      | 8          | 8           | PCE     | 8            | pip_17                  |
      | shipLine_19    | shipment              | product_18                  | loc_std                      | 3          | 3           | PCE     | 3            | pip_18                  |
      | shipLine_20    | shipment              | product_19                  | loc_std                      | 30         | 30          | PCE     | 30           | pip_19                  |
      | shipLine_21    | shipment              | product_20                  | loc_std                      | 30         | 30          | PCE     | 30           | pip_20                  |
      | shipLine_22    | shipment              | product_21                  | loc_std                      | 4          | 4           | PCE     | 4            | pip_21                  |
      | shipLine_23    | shipment              | product_22                  | loc_std                      | 2          | 2           | PCE     | 1            | pip_22                  |
      | shipLine_24    | shipment              | product_23                  | loc_std                      | 40         | 40          | PCE     | 40           | pip_23                  |
      | shipLine_25    | shipment              | product_24                  | loc_std                      | 12         | 12          | PCE     | 1            | pip_24                  |
      | shipLine_26    | shipment              | product_25                  | loc_std                      | 124        | 124         | PCE     | 31           | pip_25                  |
      | shipLine_27    | shipment              | product_26                  | loc_std                      | 4          | 4           | PCE     | 4            | pip_26                  |
      | shipLine_28    | shipment              | product_27                  | loc_std                      | 4          | 4           | PCE     | 4            | pip_27                  |
      | shipLine_29    | shipment              | product_27                  | loc_std                      | 1          | 1           | PCE     | 1            | pip_27                  |
    And metasfresh contains C_Customs_Invoice:
      | Identifier | C_BPartner_ID | C_BPartner_Location_ID | DateInvoiced |
      | ci         | customer      | location               | 2026-01-16   |
    And metasfresh contains C_Customs_Invoice_Line:
      | Identifier | C_Customs_Invoice_ID | M_Product_ID | InvoicedQty | C_UOM_ID.X12DE355 |
      | cil_01     | ci                   | product_01   | 60.85       | KGM               |
      | cil_02     | ci                   | product_02   | 524.14      | KGM               |
      | cil_03     | ci                   | product_03   | 105.30      | KGM               |
      | cil_04     | ci                   | product_04   | 71.35       | KGM               |
      | cil_05     | ci                   | product_05   | 35.51       | KGM               |
      | cil_06     | ci                   | product_06   | 141.66      | KGM               |
      | cil_07     | ci                   | product_07   | 13.41       | KGM               |
      | cil_08     | ci                   | product_08   | 67.10       | KGM               |
      | cil_09     | ci                   | product_09   | 10.76       | KGM               |
      | cil_10     | ci                   | product_10   | 128.66      | KGM               |
      | cil_11     | ci                   | product_11   | 178.81      | KGM               |
      | cil_12     | ci                   | product_12   | 16.29       | KGM               |
      | cil_13     | ci                   | product_13   | 8.97        | KGM               |
      | cil_14     | ci                   | product_14   | 18.79       | KGM               |
      | cil_15     | ci                   | product_15   | 69.68       | KGM               |
      | cil_16     | ci                   | product_16   | 22.18       | KGM               |
      | cil_17     | ci                   | product_17   | 84.79       | KGM               |
      | cil_18     | ci                   | product_18   | 12.38       | KGM               |
      | cil_19     | ci                   | product_19   | 152.21      | KGM               |
      | cil_20     | ci                   | product_20   | 133.01      | KGM               |
      | cil_21     | ci                   | product_21   | 25.01       | KGM               |
      | cil_22     | ci                   | product_22   | 169.00      | KGM               |
      | cil_23     | ci                   | product_23   | 254.46      | KGM               |
      | cil_24     | ci                   | product_24   | 415.68      | KGM               |
      | cil_25     | ci                   | product_25   | 341.29      | KGM               |
      | cil_26     | ci                   | product_26   | 26.04       | KGM               |
      | cil_27     | ci                   | product_27   | 27.70       | KGM               |
    # cil_17 and cil_27 are each linked to two shipment lines
    And metasfresh contains M_InOutLine_To_C_Customs_Invoice_Line:
      | C_Customs_Invoice_Line_ID | M_InOutLine_ID |
      | cil_01                    | shipLine_01    |
      | cil_02                    | shipLine_02    |
      | cil_03                    | shipLine_03    |
      | cil_04                    | shipLine_04    |
      | cil_05                    | shipLine_05    |
      | cil_06                    | shipLine_06    |
      | cil_07                    | shipLine_07    |
      | cil_08                    | shipLine_08    |
      | cil_09                    | shipLine_09    |
      | cil_10                    | shipLine_10    |
      | cil_11                    | shipLine_11    |
      | cil_12                    | shipLine_12    |
      | cil_13                    | shipLine_13    |
      | cil_14                    | shipLine_14    |
      | cil_15                    | shipLine_15    |
      | cil_16                    | shipLine_16    |
      | cil_17                    | shipLine_17    |
      | cil_17                    | shipLine_18    |
      | cil_18                    | shipLine_19    |
      | cil_19                    | shipLine_20    |
      | cil_20                    | shipLine_21    |
      | cil_21                    | shipLine_22    |
      | cil_22                    | shipLine_23    |
      | cil_23                    | shipLine_24    |
      | cil_24                    | shipLine_25    |
      | cil_25                    | shipLine_26    |
      | cil_26                    | shipLine_27    |
      | cil_27                    | shipLine_28    |
      | cil_27                    | shipLine_29    |

    Then the customs invoice footer of ci is:
      | nettoTotalWeight | bruttoTotalWeight |
      | 3115.030         | 3236.340          |
    And the customs invoice details of ci sum to:
      | invoicedqty | bruttoweight |
      | 3115.030    | 3236.340     |


  # ####################################################################################################
  # ####################################################################################################
  # Scenario 2: multi-level bill of materials; cumulative quantity per level and the cost report
  # ####################################################################################################
  # ####################################################################################################
  @from:cucumber
  Scenario: the recursive BOM report returns every level with its cumulative quantity
    # top (1 pc) = 2 x intermediate + 3 x componentA; intermediate = 4 x componentB + 5 x componentC
    Given metasfresh contains M_Products:
      | Identifier    | X12DE355 |
      | top           | PCE      |
      | intermediate  | PCE      |
      | componentA    | PCE      |
      | componentB    | PCE      |
      | componentC    | PCE      |
    And metasfresh contains PP_Product_BOM
      | Identifier      | M_Product_ID |
      | bomIntermediate | intermediate |
      | bomTop          | top          |
    And metasfresh contains PP_Product_BOMLines
      | Identifier | PP_Product_BOM_ID | M_Product_ID | QtyBatch |
      | line_B     | bomIntermediate   | componentB   | 4        |
      | line_C     | bomIntermediate   | componentC   | 5        |
      | line_I     | bomTop            | intermediate | 2        |
      | line_A     | bomTop            | componentA   | 3        |
    And the PP_Product_BOM identified by bomIntermediate is completed
    And the PP_Product_BOM identified by bomTop is completed
    # unit cost of the leaf components (cost element of the primary accounting schema, the one the report reads)
    And update current costs
      | M_Product_ID | CurrentCostPrice |
      | componentA   | 5                |
      | componentB   | 2                |
      | componentC   | 3                |

    Then the BOM recursive explosion of bomTop returns:
      | Line    | cumulative_qty |
      | 1.      | 1              |
      | 1.1.    | 2              |
      | 1.1.1.  | 8              |
      | 1.1.2.  | 10             |
      | 1.2.    | 3              |
    # leaf cost = cumulative qty x unit cost (B 8 x 2, C 10 x 3, A 3 x 5); the BOM-node lines 1. and 1.1. are not asserted
    And the BOM recursive report of bomTop returns:
      | Line    | QtyBOM | Cost |
      | 1.      | 1      |      |
      | 1.1.    | 2      |      |
      | 1.1.1.  | 4      | 16   |
      | 1.1.2.  | 5      | 30   |
      | 1.2.    | 3      | 15   |
