@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00701_Sales_Invoice_Candidates
@F00701
@allure.label.feature:F00130_Shipment_Schedule
@F00130
@ghActions:run_on_executor7
Feature: Invoice candidates explicitly closed together with their undelivered, closed shipment schedule are reset to the computed rule
## F00701: Invoice Candidates
## F00130: Shipment Schedule (Lieferdisposition)
## Closing a disposition with nothing delivered used to close its invoice candidates explicitly (Processed_Override = Y),
## and only reopening the disposition cleared that again. Now a computed rule decides, and the reopen no longer clears explicit closes.
## The migration 5832360_sys_reset_C_Invoice_Candidate_Processed_Override_DeliveryClosed.sql resets the explicit closes written since
## migration 5821320_sys_Event_InvoicingError_message.sql was applied. Its scenarios run the migration file itself.
## The migration 5832370_sys_recompute_C_Invoice_Candidate_DeliveryClosed_at_deploy.sql queues older closed, never-delivered candidates
## that are not processed yet for one recompute at deploy, so the computed rule applies to them; its scenario runs the file's statements one by one.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2024-03-26T13:30:13+01:00[Europe/Berlin]

    And metasfresh contains M_Products:
      | Identifier | Name               | ProductType | OPT.X12DE355 |
      | product1   | product1_12_@Date@ |             | PCE          |
      | service1   | service1_12_@Date@ | S           | PCE          |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | PS         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx |
      | PL         | PS                 | DE                    | EUR                 | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | PLV        | PL             |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | InvoicableQtyBasedOn | C_TaxCategory_ID.InternalName |
      | PLV                    | product1     | 6.0      | PCE               | Nominal              | Normal                        |
      | PLV                    | service1     | 5.0      | PCE               | Nominal              | Normal                        |

    # the orders take their invoice rule from the business partner
    And metasfresh contains C_BPartners without locations:
      | Identifier | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | customer   | customer12_@Date@  | N            | Y              | PS                            | D               |
      | customerI  | customerI12_@Date@ | N            | Y              | PS                            | I               |
      | customerP  | customerP12_@Date@ | N            | Y              | PS                            | P               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN                 | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | location   | Dummy_GLN12_@Date@  | customer                 | true                | true         |
      | locationI  | Dummy_GLN12I_@Date@ | customerI                | true                | true         |
      | locationP  | Dummy_GLN12P_@Date@ | customerP                | true                | true         |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  @allure.label.feature:F00130_Shipment_Schedule
  @F00130
  Scenario: the reset reopens to the rule only the explicit closes of never-delivered closed lines written since the bound, not older, flatrate or service candidates
    Given temporarily set sys config boolean value true for sys config 'C_Invoice_Candidate_Close_IsToClear'
    And metasfresh contains C_UOMs:
      | Identifier  | X12DE355 | Name           | UOMSymbol | StdPrecision | CostingPrecision |
      | flatfee_uom | MEAL     | Meal_12_@Date@ | M         | 2            | 2                |
    And metasfresh contains M_Product_Categories:
      | Identifier  | Name                  | Value                 |
      | flatfee_cat | flatfee_cat_12_@Date@ | flatfee_cat_12_@Date@ |
    And metasfresh contains M_Products:
      | Identifier   | Name                   | ProductType | OPT.X12DE355 | OPT.M_Product_Category_ID.Identifier |
      | flatfee_prod | flatfee_prod_12_@Date@ | S           | MEAL         |                                      |
      | product3     | product3_12_@Date@     |             | PCE          | flatfee_cat                          |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | PLV                    | flatfee_prod | 1.0      | MEAL              | Normal                        |
      | PLV                    | product3     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier   | Name                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | flatCustomer | flatCustomer12_@Date@ | N            | Y              | PS                            | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier      | GLN                 | C_BPartner_ID.Identifier | OPT.IsBillToDefault | OPT.IsShipTo |
      | flatCustomerLoc | Dummy_GLN12C_@Date@ | flatCustomer             | true                | true         |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier | Name              | Type_Conditions | OPT.Type_Flatrate | OPT.M_Product_Flatrate_ID.Identifier | OPT.C_UOM_ID.Identifier |
      | conditions | flatfee_12_@Date@ | FlatFee         | RPTD              | flatfee_prod                         | flatfee_uom             |
    And metasfresh contains C_Flatrate_Matchings:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | SeqNo | M_Product_Category_Matching_ID.Identifier |
      | matching   | conditions                          | 10    | flatfee_cat                               |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | term       | conditions                          | flatCustomer                | 2024-01-01 | 2024-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by term is completed

    # L_A: closed together with its disposition (the old explicit close); L_B: closed by hand before the bound;
    # L_D: closed by hand after the bound; L_F: has a calculation error; L_S: service product, it has no shipment schedule
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
      | SO_E       | true    | customerI                | 2024-03-26  |
      | SO_P       | true    | customerP                | 2024-03-26  |
      | SO_C       | true    | flatCustomer             | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | L_A        | product1                | 6          |
      | SO                    | L_B        | product1                | 5          |
      | SO                    | L_D        | product1                | 4          |
      | SO                    | L_F        | product1                | 3          |
      | SO                    | L_S        | service1                | 4          |
      | SO_E                  | L_E        | product1                | 6          |
      | SO_P                  | L_P        | product1                | 6          |
      | SO_C                  | L_C        | product3                | 4          |
    And the order identified by SO is completed
    And the order identified by SO_E is completed
    And the order identified by SO_P is completed
    And the order identified by SO_C is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_A | L_A                       | N             |
      | schedule_B | L_B                       | N             |
      | schedule_D | L_D                       | N             |
      | schedule_F | L_F                       | N             |
      | schedule_E | L_E                       | N             |
      | schedule_P | L_P                       | N             |
      | schedule_C | L_C                       | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_A                              | L_A                       |
      | ic_B                              | L_B                       |
      | ic_D                              | L_D                       |
      | ic_F                              | L_F                       |
      | ic_S                              | L_S                       |
      | ic_E                              | L_E                       |
      | ic_P                              | L_P                       |
      | ic_C                              | L_C                       |
    And after not more than 120s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic_A                              |
      | ic_B                              |
      | ic_D                              |
      | ic_F                              |
      | ic_S                              |
      | ic_E                              |
      | ic_P                              |
      | ic_C                              |

    # a calculation error that every recompute finds again: the tax override is Tax_Not_Found (C_Tax_ID 100)
    And update C_Invoice_Candidate by plain SQL, without interceptors or recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID |
      | ic_F                              | 100                   |
    And after not more than 120s, C_Invoice_Candidates are invalidated and recomputed:
      | C_Invoice_Candidate_ID |
      | ic_F                   |

    When the C_Invoice_Candidate identified by ic_B is closed by hand
    And the C_Invoice_Candidate identified by ic_D is closed by hand
    And the M_ShipmentSchedule identified by schedule_A is closed
    And the M_ShipmentSchedule identified by schedule_B is closed
    And the M_ShipmentSchedule identified by schedule_D is closed
    And the M_ShipmentSchedule identified by schedule_F is closed
    And the M_ShipmentSchedule identified by schedule_E is closed
    And the M_ShipmentSchedule identified by schedule_P is closed
    And the M_ShipmentSchedule identified by schedule_C is closed
    # the explicit close that closing a never-delivered disposition used to write: the same call, right after the disposition close
    And the C_Invoice_Candidate identified by ic_A is closed by hand
    And the C_Invoice_Candidate identified by ic_F is closed by hand
    And the C_Invoice_Candidate identified by ic_E is closed by hand
    And the C_Invoice_Candidate identified by ic_P is closed by hand
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed_Override | OPT.IsError |
      | ic_A                              | true          | 0            | true                 | Y                      | false       |
      | ic_B                              | true          | 0            | true                 | Y                      | false       |
      | ic_D                              | true          | 0            | true                 | Y                      | false       |
      | ic_F                              | true          | 0            | true                 | Y                      | true        |
      | ic_S                              | false         | 4            | false                | null                   | false       |
      | ic_E                              | true          | 0            | true                 | Y                      | false       |
      | ic_P                              | true          | 0            | true                 | Y                      | false       |
      | ic_C                              | true          | 0            | true                 | Y                      | false       |
    # L_B's close is older than the bound (the record of 5821320_sys_Event_InvoicingError_message.sql); no database has that record before 2026-09
    And update C_Invoice_Candidate by plain SQL, without interceptors or recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.Updated          |
      | ic_B                              | 2026-01-01T00:00:00Z |
    # an update run still in flight could load a candidate before the reset and save its old processed state over it
    And after not more than 60s, there are 0 pending "UpdateInvalidInvoiceCandidatesWorkpackageProcessor" workpackages

    # the migration file itself
    When the migration script "backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/system/47-de.metas.invoicecandidate/5832360_sys_reset_C_Invoice_Candidate_Processed_Override_DeliveryClosed.sql" is executed in the DB

    # After delivery: still processed, now by the rule. Immediate keeps its ordered quantity, After pick its picked quantity (none);
    # a candidate with an error is not processed. The older close, the flatrate close and the service candidate are unchanged.
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.Processed_Override | OPT.IsError |
      | ic_A                              | true          | 0            | null                   | false       |
      | ic_B                              | true          | 0            | Y                      | false       |
      | ic_D                              | true          | 0            | null                   | false       |
      | ic_F                              | false         | 0            | null                   | true        |
      | ic_S                              | false         | 4            | null                   | false       |
      | ic_E                              | false         | 6            | null                   | false       |
      | ic_P                              | false         | 0            | null                   | false       |
      | ic_C                              | true          | 0            | Y                      | false       |

    When the M_ShipmentSchedule identified by schedule_A is reactivated
    And the M_ShipmentSchedule identified by schedule_B is reactivated
    And the M_ShipmentSchedule identified by schedule_D is reactivated
    And the M_ShipmentSchedule identified by schedule_C is reactivated
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.IsDeliveryClosed | OPT.Processed_Override |
      | ic_A                              | false         | false                | null                   |
      | ic_B                              | true          | false                | Y                      |
      | ic_D                              | false         | false                | null                   |
      | ic_C                              | true          | false                | Y                      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  @allure.label.feature:F00130_Shipment_Schedule
  @F00130
  Scenario: on a database without the record of 5821320_sys_Event_InvoicingError_message.sql the reset changes nothing
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | L_A        | product1                | 6          |
    And the order identified by SO is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_A | L_A                       | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_A                              | L_A                       |
    And the M_ShipmentSchedule identified by schedule_A is closed
    And the C_Invoice_Candidate identified by ic_A is closed by hand
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.IsDeliveryClosed | OPT.Processed_Override |
      | ic_A                              | true          | true                 | Y                      |
    And the AD_MigrationScript record of 5821320_sys_Event_InvoicingError_message.sql is deleted for this scenario

    When the migration script "backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/system/47-de.metas.invoicecandidate/5832360_sys_reset_C_Invoice_Candidate_Processed_Override_DeliveryClosed.sql" is executed in the DB

    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.Processed_Override |
      | ic_A                              | true          | Y                      |

  @from:cucumber
  @allure.label.epic:E0340_Invoicing
  @allure.label.feature:F00701_Sales_Invoice_Candidates
  @F00701
  @allure.label.feature:F00130_Shipment_Schedule
  @F00130
  Scenario: the deploy step processes older closed, never-delivered After-delivery candidates over several update runs, leaves Immediate and error candidates alone, and queues nothing when run again
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | SO         | true    | customer                 | 2024-03-26  |
      | SO_I       | true    | customerI                | 2024-03-26  |
    And metasfresh contains C_OrderLines:
      | C_Order_ID.Identifier | Identifier | M_Product_ID.Identifier | QtyEntered |
      | SO                    | L_1        | product1                | 1          |
      | SO                    | L_2        | product1                | 2          |
      | SO                    | L_3        | product1                | 3          |
      | SO                    | L_4        | product1                | 4          |
      | SO                    | L_5        | product1                | 5          |
      | SO                    | L_F        | product1                | 3          |
      | SO_I                  | L_I        | product1                | 6          |
    And the order identified by SO is completed
    And the order identified by SO_I is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | L_1                       | N             |
      | schedule_2 | L_2                       | N             |
      | schedule_3 | L_3                       | N             |
      | schedule_4 | L_4                       | N             |
      | schedule_5 | L_5                       | N             |
      | schedule_F | L_F                       | N             |
      | schedule_I | L_I                       | N             |
    And after not more than 120s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | ic_1                              | L_1                       |
      | ic_2                              | L_2                       |
      | ic_3                              | L_3                       |
      | ic_4                              | L_4                       |
      | ic_5                              | L_5                       |
      | ic_F                              | L_F                       |
      | ic_I                              | L_I                       |
    And after not more than 120s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
      | ic_2                              |
      | ic_3                              |
      | ic_4                              |
      | ic_5                              |
      | ic_F                              |
      | ic_I                              |
    And update invoice candidates
      | C_Invoice_Candidate_ID.Identifier | OPT.ApprovalForInvoicing |
      | ic_1                              | true                     |
      | ic_2                              | true                     |
      | ic_3                              | true                     |
      | ic_4                              | true                     |
      | ic_5                              | true                     |
      | ic_I                              | true                     |

    # a calculation error that every recompute finds again: the tax override is Tax_Not_Found (C_Tax_ID 100).
    # ic_F is not approved for invoicing: the recompute does not recompute an approved candidate's tax, so the error would not appear.
    And update C_Invoice_Candidate by plain SQL, without interceptors or recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID |
      | ic_F                              | 100                   |
    And after not more than 120s, C_Invoice_Candidates are invalidated and recomputed:
      | C_Invoice_Candidate_ID |
      | ic_F                   |

    And the M_ShipmentSchedule identified by schedule_1 is closed
    And the M_ShipmentSchedule identified by schedule_2 is closed
    And the M_ShipmentSchedule identified by schedule_3 is closed
    And the M_ShipmentSchedule identified by schedule_4 is closed
    And the M_ShipmentSchedule identified by schedule_5 is closed
    And the M_ShipmentSchedule identified by schedule_F is closed
    And the M_ShipmentSchedule identified by schedule_I is closed
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.IsDeliveryClosed | OPT.Processed_Override | OPT.IsError | OPT.ApprovalForInvoicing |
      | ic_1                              | true          | 0            | true                 | null                   | false       | true                     |
      | ic_2                              | true          | 0            | true                 | null                   | false       | true                     |
      | ic_3                              | true          | 0            | true                 | null                   | false       | true                     |
      | ic_4                              | true          | 0            | true                 | null                   | false       | true                     |
      | ic_5                              | true          | 0            | true                 | null                   | false       | true                     |
      | ic_F                              | false         | 0            | true                 | null                   | true        | false                    |
      | ic_I                              | false         | 6            | true                 | null                   | false       | true                     |

    # the After-delivery candidates as they were stored before the rule existed: closed, nothing delivered, not processed.
    # Written with plain SQL, so nothing invalidates them and only the deploy step's own update run can recompute them.
    And update C_Invoice_Candidate by plain SQL, without interceptors or recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed |
      | ic_1                              | false         |
      | ic_2                              | false         |
      | ic_3                              | false         |
      | ic_4                              | false         |
      | ic_5                              | false         |
    And validate C_Invoice_Candidate as stored right now, without waiting for its recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | OPT.ApprovalForInvoicing | OPT.IsToRecompute |
      | ic_1                              | false         | true                     | false             |
      | ic_2                              | false         | true                     | false             |
      | ic_3                              | false         | true                     | false             |
      | ic_4                              | false         | true                     | false             |
      | ic_5                              | false         | true                     | false             |
      | ic_F                              | false         | false                    | false             |
      | ic_I                              | false         | true                     | false             |
    # one update run takes at most 2 candidates, so the 5 After-delivery candidates need at least 3 runs, each scheduled by the one before
    And temporarily set sys config int value 2 for sys config 'de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor.MaxInvoiceCandidatesToUpdate'
    And after not more than 60s, there are 0 pending "UpdateInvalidInvoiceCandidatesWorkpackageProcessor" workpackages

    # the migration's statements one by one, to assert the queue before the update workpackage is enqueued;
    # each docstring must be part of the migration file. A workpackage inserted by plain SQL cannot be held, so the file is not run whole here.
    When the following statement of the migration script "backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/system/47-de.metas.invoicecandidate/5832370_sys_recompute_C_Invoice_Candidate_DeliveryClosed_at_deploy.sql" is executed in the DB:
    """
    INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID)
    SELECT ic.C_Invoice_Candidate_ID
    FROM C_Invoice_Candidate ic
    WHERE ic.IsActive = 'Y'
      AND ic.IsDeliveryClosed = 'Y'
      AND ic.Processed = 'N'
      AND ic.Processed_Override IS NULL
      AND ic.IsError = 'N'
      AND (COALESCE(ic.InvoiceRule_Override, ic.InvoiceRule) = 'O'
        OR (COALESCE(ic.InvoiceRule_Override, ic.InvoiceRule) IN ('D', 'C', 'S', 'M')
            AND EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID = ic.M_Product_ID AND p.ProductType = 'I')))
      AND ic.QtyDelivered = 0
      AND ic.QtyInvoiced = 0
      AND NOT EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.C_OrderLine_ID = ic.C_OrderLine_ID AND ol.QtyDelivered <> 0)
      AND NOT EXISTS (SELECT 1 FROM C_Invoice_Candidate_Recompute r WHERE r.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID)
    ;
    """
    Then validate C_Invoice_Candidate as stored right now, without waiting for its recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.IsToRecompute |
      | ic_1                              | true              |
      | ic_2                              | true              |
      | ic_3                              | true              |
      | ic_4                              | true              |
      | ic_5                              | true              |
      | ic_F                              | false             |
      | ic_I                              | false             |
    And the following statement of the migration script "backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/system/47-de.metas.invoicecandidate/5832370_sys_recompute_C_Invoice_Candidate_DeliveryClosed_at_deploy.sql" is executed in the DB:
    """
    DO
    $$
        DECLARE
            v_client  record;
            v_role_id numeric;
            v_user_id numeric;
        BEGIN
            FOR v_client IN SELECT ic.AD_Client_ID, COUNT(*) AS queued
                            FROM C_Invoice_Candidate_Recompute r
                                     JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID = r.C_Invoice_Candidate_ID
                            WHERE ic.AD_Client_ID > 0
                            GROUP BY ic.AD_Client_ID
                            ORDER BY ic.AD_Client_ID
                LOOP
                    v_role_id := NULL;
                    v_user_id := NULL;
                    SELECT ur.AD_Role_ID, ur.AD_User_ID
                    INTO v_role_id, v_user_id
                    FROM AD_User_Roles ur
                             JOIN AD_Role r ON r.AD_Role_ID = ur.AD_Role_ID
                             JOIN AD_User u ON u.AD_User_ID = ur.AD_User_ID
                    WHERE r.AD_Client_ID = v_client.AD_Client_ID
                      AND r.IsActive = 'Y'
                      AND r.IsManual = 'N'
                      AND r.IsAccessAllOrgs = 'Y'
                      AND r.UserLevel LIKE '%C%'
                      AND ur.IsActive = 'Y'
                      AND u.IsActive = 'Y'
                    ORDER BY (ur.AD_User_ID = 100) DESC, r.AD_Role_ID, ur.AD_User_ID
                    LIMIT 1;

                    INSERT INTO C_Queue_WorkPackage (C_Queue_WorkPackage_ID, C_Queue_PackageProcessor_ID,
                                                     AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID,
                                                     IsReadyForProcessing, IsActive, Created, CreatedBy, Updated, UpdatedBy)
                    VALUES (NEXTVAL('c_queue_workpackage_seq'),
                            (SELECT p.C_Queue_PackageProcessor_ID
                             FROM C_Queue_PackageProcessor p
                             WHERE p.Classname = 'de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor'),
                            v_client.AD_Client_ID, 0, v_role_id, COALESCE(v_user_id, 100),
                            'Y', 'Y', NOW(), 99, NOW(), 99);

                    RAISE NOTICE 'AD_Client_ID=%: % queued invoice candidates, update workpackage enqueued with AD_Role_ID=%, AD_User_ID=%',
                        v_client.AD_Client_ID, v_client.queued, v_role_id, COALESCE(v_user_id, 100);
                END LOOP;
        END
    $$
    ;
    """

    Then after not more than 120s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | ic_1                              |
      | ic_2                              |
      | ic_3                              |
      | ic_4                              |
      | ic_5                              |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.Processed | QtyToInvoice | OPT.Processed_Override | OPT.IsError | OPT.ApprovalForInvoicing |
      | ic_1                              | true          | 0            | null                   | false       | true                     |
      | ic_2                              | true          | 0            | null                   | false       | true                     |
      | ic_3                              | true          | 0            | null                   | false       | true                     |
      | ic_4                              | true          | 0            | null                   | false       | true                     |
      | ic_5                              | true          | 0            | null                   | false       | true                     |
      | ic_F                              | false         | 0            | null                   | true        | false                    |
      | ic_I                              | false         | 6            | null                   | false       | true                     |

    # running the migration's statements again queues none of the candidates (each docstring must be part of the migration file)
    When the following statement of the migration script "backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/system/47-de.metas.invoicecandidate/5832370_sys_recompute_C_Invoice_Candidate_DeliveryClosed_at_deploy.sql" is executed in the DB:
    """
    INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID)
    SELECT ic.C_Invoice_Candidate_ID
    FROM C_Invoice_Candidate ic
    WHERE ic.IsActive = 'Y'
      AND ic.IsDeliveryClosed = 'Y'
      AND ic.Processed = 'N'
      AND ic.Processed_Override IS NULL
      AND ic.IsError = 'N'
      AND (COALESCE(ic.InvoiceRule_Override, ic.InvoiceRule) = 'O'
        OR (COALESCE(ic.InvoiceRule_Override, ic.InvoiceRule) IN ('D', 'C', 'S', 'M')
            AND EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID = ic.M_Product_ID AND p.ProductType = 'I')))
      AND ic.QtyDelivered = 0
      AND ic.QtyInvoiced = 0
      AND NOT EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.C_OrderLine_ID = ic.C_OrderLine_ID AND ol.QtyDelivered <> 0)
      AND NOT EXISTS (SELECT 1 FROM C_Invoice_Candidate_Recompute r WHERE r.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID)
    ;
    """
    Then validate C_Invoice_Candidate as stored right now, without waiting for its recompute:
      | C_Invoice_Candidate_ID.Identifier | OPT.IsToRecompute |
      | ic_1                              | false             |
      | ic_2                              | false             |
      | ic_3                              | false             |
      | ic_4                              | false             |
      | ic_5                              | false             |
      | ic_F                              | false             |
      | ic_I                              | false             |
    And the following statement of the migration script "backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/system/47-de.metas.invoicecandidate/5832370_sys_recompute_C_Invoice_Candidate_DeliveryClosed_at_deploy.sql" is executed in the DB:
    """
    DO
    $$
        DECLARE
            v_client  record;
            v_role_id numeric;
            v_user_id numeric;
        BEGIN
            FOR v_client IN SELECT ic.AD_Client_ID, COUNT(*) AS queued
                            FROM C_Invoice_Candidate_Recompute r
                                     JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID = r.C_Invoice_Candidate_ID
                            WHERE ic.AD_Client_ID > 0
                            GROUP BY ic.AD_Client_ID
                            ORDER BY ic.AD_Client_ID
                LOOP
                    v_role_id := NULL;
                    v_user_id := NULL;
                    SELECT ur.AD_Role_ID, ur.AD_User_ID
                    INTO v_role_id, v_user_id
                    FROM AD_User_Roles ur
                             JOIN AD_Role r ON r.AD_Role_ID = ur.AD_Role_ID
                             JOIN AD_User u ON u.AD_User_ID = ur.AD_User_ID
                    WHERE r.AD_Client_ID = v_client.AD_Client_ID
                      AND r.IsActive = 'Y'
                      AND r.IsManual = 'N'
                      AND r.IsAccessAllOrgs = 'Y'
                      AND r.UserLevel LIKE '%C%'
                      AND ur.IsActive = 'Y'
                      AND u.IsActive = 'Y'
                    ORDER BY (ur.AD_User_ID = 100) DESC, r.AD_Role_ID, ur.AD_User_ID
                    LIMIT 1;

                    INSERT INTO C_Queue_WorkPackage (C_Queue_WorkPackage_ID, C_Queue_PackageProcessor_ID,
                                                     AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID,
                                                     IsReadyForProcessing, IsActive, Created, CreatedBy, Updated, UpdatedBy)
                    VALUES (NEXTVAL('c_queue_workpackage_seq'),
                            (SELECT p.C_Queue_PackageProcessor_ID
                             FROM C_Queue_PackageProcessor p
                             WHERE p.Classname = 'de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor'),
                            v_client.AD_Client_ID, 0, v_role_id, COALESCE(v_user_id, 100),
                            'Y', 'Y', NOW(), 99, NOW(), 99);

                    RAISE NOTICE 'AD_Client_ID=%: % queued invoice candidates, update workpackage enqueued with AD_Role_ID=%, AD_User_ID=%',
                        v_client.AD_Client_ID, v_client.queued, v_role_id, COALESCE(v_user_id, 100);
                END LOOP;
        END
    $$
    ;
    """
